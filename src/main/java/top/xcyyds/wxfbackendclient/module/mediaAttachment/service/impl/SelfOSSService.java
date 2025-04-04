package top.xcyyds.wxfbackendclient.module.mediaAttachment.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.pojo.dto.UploadMediaResponse;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.service.IMediaStorageService;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Set;


/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-31
 * @Description:
 * @Version:
 */
@Service
@Slf4j
public class SelfOSSService implements IMediaStorageService {
    // 图片服务器地址，与前端配置保持一致
    private static final String IMAGE_SERVER = "http://8.148.21.215:5000";

    // 同时允许图片和视频
    private static final String[] ALLOWED_EXTENSIONS = {"png", "jpg", "jpeg", "mp4", "mov", "avi"};

    // 最大文件大小（100MB）
    private static final long MAX_FILE_SIZE = 100 * 1024 * 1024;

    private final HttpClient httpClient;

    public SelfOSSService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.NORMAL) // 显式声明跟随重定向
                .build();
    }

    /**
     * 上传图片到图片服务器
     *
     * @param multipartFile Spring的MultipartFile对象
     * @param subpath 上传的子路径，例如 "products/123"
     * @return 上传成功后的图片URL
     * @throws IOException 如果文件读取失败
     * @throws InterruptedException 如果HTTP请求被中断
     * @throws IllegalArgumentException 如果文件类型不允许或文件大小超过限制
     */
    public String uploadImage(MultipartFile multipartFile, String subpath) throws IOException, InterruptedException {
        // 获取原始文件名
        String originalFilename = multipartFile.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new IllegalArgumentException("文件名不能为空");
        }

        // 验证文件类型
        if (!isAllowedFileType(originalFilename)) {
            throw new IllegalArgumentException("仅允许上传PNG/JPG/JPEG图片或MP4/MOV/AVI视频");
        }

        // 验证文件大小
        if (multipartFile.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("文件大小不能超过10MB");
        }

        // 构建上传URL
        String uploadUrl = IMAGE_SERVER + "/upload/" + subpath;

        // 读取文件内容
        byte[] fileContent = multipartFile.getBytes();

        // 构建multipart请求
        String boundary = "----WebKitFormBoundary" + System.currentTimeMillis();
        String lineEnd = "\r\n";
        String twoHyphens = "--";

        StringBuilder requestBody = new StringBuilder();
        requestBody.append(twoHyphens).append(boundary).append(lineEnd);
        requestBody.append("Content-Disposition: form-data; name=\"file\"; filename=\"")
                .append(originalFilename).append("\"").append(lineEnd);
        requestBody.append("Content-Type: ").append(multipartFile.getContentType() != null ?
                multipartFile.getContentType() : getContentType(originalFilename)).append(lineEnd);
        requestBody.append(lineEnd);

        // 组合请求体
        byte[] headerBytes = requestBody.toString().getBytes();
        byte[] footerBytes = (lineEnd + twoHyphens + boundary + twoHyphens + lineEnd).getBytes();

        byte[] requestBodyBytes = new byte[headerBytes.length + fileContent.length + footerBytes.length];
        System.arraycopy(headerBytes, 0, requestBodyBytes, 0, headerBytes.length);
        System.arraycopy(fileContent, 0, requestBodyBytes, headerBytes.length, fileContent.length);
        System.arraycopy(footerBytes, 0, requestBodyBytes, headerBytes.length + fileContent.length, footerBytes.length);

        // 创建HTTP请求
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uploadUrl))
                .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                .PUT(HttpRequest.BodyPublishers.ofByteArray(requestBodyBytes))
                .build();

        // 发送请求
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // 检查响应状态
        if (response.statusCode() != 200) {
            throw new IOException("上传失败，服务器返回状态码: " + response.statusCode() + ", 响应内容: " + response.body());
        }

        // 使用Jackson解析JSON
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.body().toString());

        // 检查并获取url字段
        if (jsonNode.has("url")) {
            // 返回图片URL
            return jsonNode.get("url").asText();
        } else {
            throw new IOException("响应中未找到URL字段");
        }
    }

    /**
     * 获取图片URL
     *
     * @param imagePath 图片路径
     * @return 完整的图片URL
     */
    public static String getImageUrl(String imagePath) {
        // 如果没有图片路径，返回默认图片
        if (imagePath == null || imagePath.isEmpty()) {
            return IMAGE_SERVER + "/get/default/no-image.jpg";
        }

        // 确保路径以正确的格式开始
        if (imagePath.startsWith("/")) {
            imagePath = imagePath.substring(1);
        }

        // 返回完整的图片URL，包含 'get' 路径
        return IMAGE_SERVER + "/get/" + imagePath;
    }

    /**
     * 检查文件类型是否允许
     *
     * @param filename 文件名
     * @return 如果文件类型允许则返回true
     */
    private boolean isAllowedFileType(String filename) {
        String extension = getFileExtension(filename).toLowerCase();
        for (String allowedExt : ALLOWED_EXTENSIONS) {
            if (allowedExt.equals(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取文件扩展名
     *
     * @param filename 文件名
     * @return 文件扩展名
     */
    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < filename.length() - 1) {
            return filename.substring(dotIndex + 1);
        }
        return "";
    }

    /**
     * 获取文件的Content-Type
     *
     * @param filename 文件名
     * @return Content-Type
     */
    private String getContentType(String filename) {
        String extension = getFileExtension(filename).toLowerCase();
        switch (extension) {
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "mp4":
                return "video/mp4";
            case "mov":
                return "video/quicktime";
            case "avi":
                return "video/x-msvideo";
            default:
                return "application/octet-stream";
        }
    }

    @Override
    public UploadMediaResponse uploadMedia(MultipartFile mediaAttachments, String subPath) {
        try{
            String url= uploadImage(mediaAttachments, subPath);
            UploadMediaResponse uploadMediaResponse=new UploadMediaResponse();
            uploadMediaResponse.setMediaPath(url);
            return uploadMediaResponse;
        } catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}
