package top.xcyyds.wxfbackendclient.module.post.service.impl;

import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.apache.el.util.ReflectionUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.pojo.dto.SummaryMediaAttachment;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.pojo.dto.UploadMediaResponse;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.pojo.entity.MediaAttachment;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.service.impl.SelfOSSService;
import top.xcyyds.wxfbackendclient.module.post.pojo.dto.AddPostRequest;
import top.xcyyds.wxfbackendclient.module.post.pojo.dto.ListPostsRequest;
import top.xcyyds.wxfbackendclient.module.post.pojo.dto.ListPostsResponse;
import top.xcyyds.wxfbackendclient.module.post.pojo.dto.SummaryPost;
import top.xcyyds.wxfbackendclient.module.post.pojo.entity.Post;
import top.xcyyds.wxfbackendclient.module.post.pojo.enums.PostType;
import top.xcyyds.wxfbackendclient.module.post.pojo.enums.SortType;
import top.xcyyds.wxfbackendclient.module.post.repository.PostRepository;
import top.xcyyds.wxfbackendclient.module.post.service.IPostService;
import top.xcyyds.wxfbackendclient.module.user.persistence.repository.UserRepository;
import top.xcyyds.wxfbackendclient.module.user.pojo.dto.GetUserInfoRequest;
import top.xcyyds.wxfbackendclient.module.user.pojo.dto.GetUserInfoResponse;
import top.xcyyds.wxfbackendclient.module.user.pojo.dto.SummaryAuthorInfo;
import top.xcyyds.wxfbackendclient.module.user.service.impl.UserService;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Long.min;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-29
 * @Description:
 * @Version:
 */
@Slf4j
@Service("PostService")
public class PostService implements IPostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private SelfOSSService selfOSSService;
    @Autowired
    private UserService userService;


    @Override
    public SummaryPost getPostDetail(long postId) {
        Post post=postRepository.findById(postId).orElse(null);
        return convert2SummaryPost(post,true);
    }

    @Override
    public ListPostsResponse listPosts(ListPostsRequest request) {
        //构建动态查询条件
        Specification<Post>spec=buildSpecification(request);

        //构建分页和排序
        Pageable pageable= buildPageable(request);

        //执行查询
        Page<Post>postPage=postRepository.findAll(spec,pageable);

        //构建返回信息并返回
        return buildListPostsResponse(postPage,request);
    }

    private ListPostsResponse buildListPostsResponse(Page<Post> postPage, ListPostsRequest request) {
        ListPostsResponse listPostsResponse=new ListPostsResponse();
        listPostsResponse.setPageSize(min(postPage.getTotalElements(),request.getPageSize()));
        listPostsResponse.setTotalPosts(postPage.getTotalElements());

        List<SummaryPost>summaryPosts=postPage.getContent().stream()
                .map(post->convert2SummaryPost(post,true))
                .collect(Collectors.toList());
        listPostsResponse.setPosts(summaryPosts);

        if(!postPage.getContent().isEmpty()){
            Post lastPost=postPage.getContent().get(postPage.getContent().size()-1);
            listPostsResponse.setTimeCursor(lastPost.getCreateTime().toString());
        }

        return listPostsResponse;
    }

    private Pageable buildPageable(ListPostsRequest request) {
        Sort sort;

        switch(SortType.valueOf(request.getSortType())){
            case TIME_DESCENDING: //按时间降序，即由新到旧排序帖子
            default:
                sort=Sort.by(Sort.Direction.DESC,"createTime");
                break;
        }

        return PageRequest.of(0,(int)request.getPageSize(),sort);
    }

    private Specification<Post> buildSpecification(ListPostsRequest request) {
        return (root,query,cb)->{
            List<Predicate>predicates=new ArrayList<>();

            //基础过滤条件-状态正常的帖子
            predicates.add(cb.equal(root.get("status"),1L));

            //帖子类型过滤
            if(StringUtils.hasText(request.getPostType())){
                try{
                    PostType postType=PostType.valueOf(request.getPostType());
                    predicates.add(cb.equal(root.get("postType"),postType));
                }catch (IllegalArgumentException e){
                    throw new IllegalArgumentException("不支持的帖子类型");
                }
            }

            //时间游标处理
            if(StringUtils.hasText(request.getTimeCursor())){
//                // 自定义格式解析
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
//                LocalDateTime localDateTime = LocalDateTime.parse(request.getTimeCursor(), formatter);
//
//                // 添加时区（示例使用 UTC）
//                OffsetDateTime cursorTime = localDateTime.atOffset(ZoneOffset.UTC);
                OffsetDateTime cursorTime= OffsetDateTime.parse(request.getTimeCursor());
                predicates.add(cb.lessThan(root.get("createTime"),cursorTime));
            }else{
                //当timeCursor为空时不需要此条件，自动获取最新的，因为有buildPageable函数的按时间排序
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Override
    public SummaryPost addPost(AddPostRequest request) {
        Post post=convert2Post(request);

        post=postRepository.save(post);

        //log.info("帖子创建成功：{}",post);
        return convert2SummaryPost(post, true);
    }

    @Override
    public Post getPost(long postId) {
        return postRepository.findByPostId(postId);
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
    public SummaryPost convert2SummaryPost(Post post,boolean isComplete){
        SummaryPost summaryPost=new SummaryPost();
        if (post==null){
            return summaryPost;
        }
        //获取作者基础信息
        SummaryAuthorInfo summaryAuthorInfo=userService.getSummaryAuthorInfoByPublicId(post.getPublicId());
//        GetUserInfoRequest getUserInfoRequest=new GetUserInfoRequest();
//        getUserInfoRequest.setPublicId(post.getPublicId());
//        GetUserInfoResponse getUserInfoResponse=userService.getUserInfo(getUserInfoRequest);
//        SummaryAuthorInfo summaryAuthorInfo=new SummaryAuthorInfo();
//        BeanUtils.copyProperties(getUserInfoResponse,summaryAuthorInfo);


        //获取媒体附件列表
        List<SummaryMediaAttachment> summaryMediaAttachments=new ArrayList<>();
        for(MediaAttachment mediaAttachment:post.getMediaAttachments()){
            SummaryMediaAttachment summaryMediaAttachment=new SummaryMediaAttachment();
            summaryMediaAttachment.setStoragePath(mediaAttachment.getStoragePath());
            //summaryMediaAttachment.setMediaType(mediaAttachment.getMediaType().toString());//待修改，此处直接返回了类型的代号
            //summaryMediaAttachment.setFileSize(mediaAttachment.getFileSize());
            summaryMediaAttachments.add(summaryMediaAttachment);
        }




        //设置SummaryPost的成员
        summaryPost.setComplete(isComplete);
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
