package top.xcyyds.wxfbackendclient.module.post.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.el.util.ReflectionUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.pojo.dto.SummaryMediaAttachment;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.pojo.dto.UploadMediaResponse;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.pojo.entity.MediaAttachment;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.service.impl.SelfOSSService;
import top.xcyyds.wxfbackendclient.module.post.pojo.dto.AddPostRequest;
import top.xcyyds.wxfbackendclient.module.post.pojo.dto.ListPostsRequest;
import top.xcyyds.wxfbackendclient.module.post.pojo.dto.ListPostsResponse;
import top.xcyyds.wxfbackendclient.module.post.pojo.dto.SummaryPost;
import top.xcyyds.wxfbackendclient.module.post.pojo.entity.Post;
import top.xcyyds.wxfbackendclient.module.post.repository.PostRepository;
import top.xcyyds.wxfbackendclient.module.post.service.IPostService;
import top.xcyyds.wxfbackendclient.module.user.persistence.repository.UserRepository;
import top.xcyyds.wxfbackendclient.module.user.pojo.dto.GetUserInfoRequest;
import top.xcyyds.wxfbackendclient.module.user.pojo.dto.GetUserInfoResponse;
import top.xcyyds.wxfbackendclient.module.user.pojo.dto.SummaryAuthorInfo;
import top.xcyyds.wxfbackendclient.module.user.service.impl.UserService;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-29
 * @Description:
 * @Version:
 */
@Slf4j
@Service
public class PostService implements IPostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private SelfOSSService selfOSSService;
    @Autowired
    private UserService userService;


    @Override
    public SummaryPost getPost(String postId) {
        return null;
    }

    @Override
    public ListPostsResponse listPosts(ListPostsRequest request) {
        return null;
    }

    @Override
    public SummaryPost addPost(AddPostRequest request) {
        Post post=convert2Post(request);

        post=postRepository.save(post);

        //log.info("帖子创建成功：{}",post);
        return convert2SummaryPost(post);
    }


    private Post convert2Post(AddPostRequest request) {
        Post post=new Post();
        //指定为东八区时间（偏移量“+08：00"）
        OffsetDateTime beijingTime = OffsetDateTime.now(ZoneOffset.ofHours(8));

        post.setPostType(request.getPostType());
        post.setContent(request.getContent());
        post.setPublicId(request.getPublicId());
        post.setCollectCount(0L);
        post.setLikeCount(0L);
        post.setReplyCount(0L);
        post.setShareCount(0L);
        post.setViewCount(0L);
        post.setTotalAttachments(request.getMediaAttachments()!=null?request.getMediaAttachments().size():0L);
        post.setStatus(1L);
        post.setCreateTime(beijingTime);
        post.setHasMedia(request.getMediaAttachments()!=null);
        post.setMediaAttachments(new ArrayList<>());
        log.info("媒体附件的数量：{}",request.getMediaAttachments().size());
        for(int i=0;i<request.getMediaAttachments().size();i++){
            log.info("正在处理第{}个媒体附件：{}",i,request.getMediaAttachments().get(i).getOriginalFilename());
            UploadMediaResponse uploadMediaResponse=selfOSSService.uploadMedia(request.getMediaAttachments().get(i),"/pic");
            MediaAttachment mediaAttachment=new MediaAttachment();
            mediaAttachment.setPost(post);
            mediaAttachment.setPublicId(post.getPublicId());
            mediaAttachment.setStoragePath(uploadMediaResponse.getMediaPath());
            mediaAttachment.setUploadTime(beijingTime);
            post.getMediaAttachments().add(mediaAttachment);
        }

        return post;
    }
    public SummaryPost convert2SummaryPost(Post post){

        //获取作者基础信息
        GetUserInfoRequest getUserInfoRequest=new GetUserInfoRequest();
        getUserInfoRequest.setPublicId(post.getPublicId());
        GetUserInfoResponse getUserInfoResponse=userService.getUserInfo(getUserInfoRequest);
        SummaryAuthorInfo summaryAuthorInfo=new SummaryAuthorInfo();
        BeanUtils.copyProperties(getUserInfoResponse,summaryAuthorInfo);

        //获取媒体附件列表
        List<SummaryMediaAttachment> summaryMediaAttachments=new ArrayList<>();
        for(MediaAttachment mediaAttachment:post.getMediaAttachments()){
            SummaryMediaAttachment summaryMediaAttachment=new SummaryMediaAttachment();
            summaryMediaAttachment.setStoragePath(mediaAttachment.getStoragePath());
            //summaryMediaAttachment.setMediaType(mediaAttachment.getMediaType().toString());//待修改，此处直接返回了类型的代号
            //summaryMediaAttachment.setFileSize(mediaAttachment.getFileSize());
            summaryMediaAttachments.add(summaryMediaAttachment);
        }


        SummaryPost summaryPost=new SummaryPost();

        //设置SummaryPost的成员
        summaryPost.setPostId(post.getPostId());
        summaryPost.setContent(post.getContent());
        summaryPost.setPostType(post.getPostType());
        summaryPost.setCreateTime(post.getCreateTime());
        summaryPost.setHasMedia(post.getHasMedia());
        summaryPost.setTotalAttachments(post.getTotalAttachments());
        summaryPost.setCollectCount(post.getCollectCount());
        summaryPost.setLikeCount(post.getLikeCount());
        summaryPost.setReplyCount(post.getReplyCount());
        summaryPost.setShareCount(post.getShareCount());
        summaryPost.setViewCount(post.getViewCount());
        summaryPost.setAuthorInfo(summaryAuthorInfo);
        summaryPost.setSummaryMediaAttachment(summaryMediaAttachments);
        return summaryPost;
    }
}
