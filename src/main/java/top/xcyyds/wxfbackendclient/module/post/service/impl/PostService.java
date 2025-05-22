package top.xcyyds.wxfbackendclient.module.post.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.*;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.transport.TransportException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.apache.el.util.ReflectionUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;
import top.xcyyds.wxfbackendclient.common.ContentState;
import top.xcyyds.wxfbackendclient.module.like.pojo.enums.TargetType;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.MediaAttachmentRepository;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.pojo.dto.AddMediaAttachmentRequest;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.pojo.dto.SummaryMediaAttachment;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.pojo.dto.UploadMediaResponse;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.pojo.entity.MediaAttachment;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.pojo.entity.MediaAttachmentEsDocument;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.service.impl.MediaAttachmentService;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.service.impl.SelfOSSService;
import top.xcyyds.wxfbackendclient.module.notification.pojo.dto.CreateReminderRequest;
import top.xcyyds.wxfbackendclient.module.notification.pojo.dto.SubscribeRequest;
import top.xcyyds.wxfbackendclient.module.notification.pojo.entity.SubscriptionActionType;
import top.xcyyds.wxfbackendclient.module.notification.service.INotificationService;
import top.xcyyds.wxfbackendclient.module.post.pojo.dto.*;
import top.xcyyds.wxfbackendclient.module.post.pojo.entity.Post;
import top.xcyyds.wxfbackendclient.module.post.pojo.entity.PostEsDocument;
import top.xcyyds.wxfbackendclient.module.post.pojo.enums.PostType;
import top.xcyyds.wxfbackendclient.module.post.pojo.enums.SortType;
import top.xcyyds.wxfbackendclient.module.post.repository.PostRepository;
import top.xcyyds.wxfbackendclient.module.post.service.IPostService;
import top.xcyyds.wxfbackendclient.module.user.persistence.repository.UserRepository;
import top.xcyyds.wxfbackendclient.module.user.pojo.dto.GetUserInfoRequest;
import top.xcyyds.wxfbackendclient.module.user.pojo.dto.GetUserInfoResponse;
import top.xcyyds.wxfbackendclient.module.user.pojo.dto.SummaryAuthorInfo;
import top.xcyyds.wxfbackendclient.module.user.service.impl.UserService;
import top.xcyyds.wxfbackendclient.util.EsSearchAfterUtil;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Long.min;
//import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

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

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @Autowired
    @Qualifier("NotificationService")
    private INotificationService notificationService;

    final List<String> subscribeActionTypeNames=List.of("COMMENT","LIKE");
    @Autowired
    private RestClient.Builder builder;

    @Autowired
    private MediaAttachmentRepository mediaAttachmentRepository;
    @Autowired
    private MediaAttachmentService mediaAttachmentService;

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
        createSubscription(post);
        return convert2SummaryPost(post, true);
    }

    private void createSubscription(Post post){
        SubscribeRequest subscribeRequest=new SubscribeRequest();
        subscribeRequest.setUserInternalId(userService.getInternalIdByPublicId(post.getPublicId()));
        subscribeRequest.setTargetType(TargetType.POST);
        subscribeRequest.setTargetId(post.getPostId());
        List<SubscriptionActionType>subscriptionActionTypes=new ArrayList<>();
        for (String subscribeActionTypeName:subscribeActionTypeNames){
            SubscriptionActionType subscriptionActionType=notificationService.getSubscriptionActionType(subscribeActionTypeName);
            subscriptionActionTypes.add(subscriptionActionType);
        }
        subscribeRequest.setActions(subscriptionActionTypes);
        notificationService.subscribe(subscribeRequest);
    }

    @Override
    public Post getPost(long postId) {
        return postRepository.findByPostId(postId);
    }

    @Override
    public ListPostsResponse searchPosts(SearchPostRequest request) {
        try{
            SearchRequest.Builder builder=new SearchRequest.Builder()
                    .index("post")
                    .size((int)request.getPageSize())
                    .query(buildQuery(request))//构建查询条件
                    ;

            //添加排序规则
            List<SortOptions>sortOptions=buildSortType(request.getSortType(),request.getSortOrder());
            sortOptions.forEach(builder::sort);
            List<FieldValue>searchAfterValue=EsSearchAfterUtil.deserialize(request.getTimeCursor());
            //添加时间游标
            Optional.ofNullable(searchAfterValue).filter(
                    sa->!sa.isEmpty()
                    )
                    .ifPresent(
                            sa->builder.searchAfter(sa)
                    );

            //执行es搜索
            SearchResponse<PostEsDocument> response = elasticsearchClient.search(
                    builder.build(),
                    PostEsDocument.class
            );

            //获取搜索结果
            List<Hit<PostEsDocument>>hits=response.hits().hits();

            //若搜索结果为空，则返回null
            if (hits.isEmpty()){
                return null;
            }

            //获取时间游标
            List<FieldValue>searchAfterValues=hits.get(hits.size()-1).sort();

            //构建返回信息
            ListPostsResponse listPostsResponse=new ListPostsResponse();
            List<SummaryPost>summaryPosts=new ArrayList<>();
            for (Hit<PostEsDocument> hit:hits){
                SummaryPost summaryPost=convert2SummaryPost(hit.source(),true);
                summaryPosts.add(summaryPost);
            }
            log.debug("搜索到{}条帖子",summaryPosts.size());
            listPostsResponse.setPosts(summaryPosts);
            listPostsResponse.setTotalPosts(response.hits().total().value());
            listPostsResponse.setPageSize((long) response.hits().hits().size());
            listPostsResponse.setTimeCursor(EsSearchAfterUtil.serialize(searchAfterValues));

            return listPostsResponse;
        }catch (ElasticsearchException e) {
            // 处理服务端明确的错误（如索引不存在、查询语法错误）
            log.error("Elasticsearch 服务端错误: {}", e.getMessage());
            throw new RuntimeException("搜索失败："+e);
        } catch (TransportException e) {
            // 处理网络层错误（如连接超时、SSL 证书问题）
            log.error("传输层错误: {}", e.getMessage());
            throw new RuntimeException("搜索失败："+e);
        }
        catch (Exception e){

            throw new RuntimeException("搜索失败："+e);
        }

    }

    /*
     * 将PostEsDocument转换为SummaryPost
     */
    private SummaryPost convert2SummaryPost(PostEsDocument post,boolean withFullContent){
        SummaryPost summaryPost=new SummaryPost();
        BeanUtils.copyProperties(post,summaryPost);
        summaryPost.setPostId(Long.parseLong(post.getPostId()));
        List<SummaryMediaAttachment>summaryMediaAttachments=new ArrayList<>();
        for (MediaAttachmentEsDocument mediaAttachment:post.getMediaAttachments()){
            SummaryMediaAttachment summaryMediaAttachment=new SummaryMediaAttachment();
            BeanUtils.copyProperties(mediaAttachment,summaryMediaAttachment);
            summaryMediaAttachments.add(summaryMediaAttachment);
        }
        summaryPost.setSummaryMediaAttachment(summaryMediaAttachments);
        summaryPost.setComplete(withFullContent);
        summaryPost.setAuthorInfo(userService.getSummaryAuthorInfoByPublicId(post.getPublicId()));

        return summaryPost;
    }

    /*
     * 构建es查询条件
     */
    private Query buildQuery(SearchPostRequest request){
        BoolQuery.Builder builder=new BoolQuery.Builder();

        //若用户查询时指定了关键词
        if (request.getKeyword()!=null&&!"".equals(request.getKeyword())) {
            builder.must(
                    m -> m.match(
                            ma -> ma.field("content").query(request.getKeyword())
                    )
            );
        }else {//若用户查询时没有指定了关键词
            builder.must(m->m.matchAll(ma->ma));
        }

        //过滤状态为发布的帖子
        builder.filter(
                f->f.term(t->t.field("status").value(ContentState.PUBLISHED.getCode()))
        );

        //若用户查询时指定了帖子类型
        if (request.getPostType()!=null&&PostType.isValid(request.getPostType())){
            builder.filter(
                    f->f.term(t->t.field("postType").value(request.getPostType()))
            );
        }

        //若用户查询时指定了用户对外的publicId
        if (request.getUserPublicId()!=null&&!request.getUserPublicId().isEmpty()){
            builder.filter(
                    f->f.term(t->t.field("publicId").value(request.getUserPublicId()))
            );
        }

        Query query=Query.of(b->b.bool(builder.build()));
        return query;

    }

    /*
     * 构建es查询排序条件
     */
    private List<SortOptions> buildSortType(String sortTypeStr,String sortOrderStr) {
        List<SortOptions>sortOptions=new ArrayList<>();

        // 根据sortTypeStr和sortOrderStr构建排序条件变量
        SortType sortType=SortType.valueOf(sortTypeStr);
        SortOrder sortOrder="DESC".equals(sortOrderStr)?SortOrder.Desc:SortOrder.Asc;

        //根据sortType和sortOrder构建排序条件
        switch (sortType){
            case TIME_DESCENDING:
            case TIME_ASCENDING: {
                sortOptions.add(SortOptions.of(so -> so.field(f -> f.field(sortType.getEsFieldNameCreateTime(TargetType.POST)).order(sortOrder))
                ));
//                //若创建时间相同则使用id进行排序
//                sortOptions.add(SortOptions.of(so -> so.field(f -> f.field(sortType.getEsFieldNameId(TargetType.POST)).order(sortOrder))));
                break;
            }
            case HOT: {
                long currentTimeMillis = System.currentTimeMillis();
                String scriptSource = """
                        long createTimeMillis = doc['createTime'].value.toInstant().toEpochMilli();
                        double ageDays=(params.currentTimeMillis-createTimeMillis)/86400000.0;
                        double decayFactor=Math.exp(-0.2*ageDays);
                        (doc['viewCount'].value*0.2+doc['likeCount'].value*0.3+doc['replyCount'].value*0.5)*decayFactor
                        """;
                sortOptions.add(SortOptions.of(s -> s
                        .script(s1 -> s1
                                .script(s2 -> s2
                                        .source(scriptSource)
                                        .lang("painless")
                                        .params(Map.of("currentTimeMillis", JsonData.of(currentTimeMillis)))
                                )
                                .order(
                                        sortOrder
                                ).type(ScriptSortType.Number)
                        )
                ));
                break;
            }

        }

        //若创建时间相同/热度相同则使用id进行排序
        sortOptions.add(SortOptions.of(so -> so.field(f -> f.field(sortType.getEsFieldNameId(TargetType.POST)).order(sortOrder))));
        return sortOptions;
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
            AddMediaAttachmentRequest addMediaAttachmentRequest=new AddMediaAttachmentRequest();
            addMediaAttachmentRequest.setTarget(post);
            addMediaAttachmentRequest.setMediaAttachment(request.getMediaAttachments().get(i));
            addMediaAttachmentRequest.setTargetType(TargetType.POST);
            addMediaAttachmentRequest.setPublicId(post.getPublicId());

            MediaAttachment mediaAttachment=mediaAttachmentService.addMediaAttachment(addMediaAttachmentRequest);
//            UploadMediaResponse uploadMediaResponse=selfOSSService.uploadMedia(request.getMediaAttachments().get(i),"/pic");
//            MediaAttachment mediaAttachment=new MediaAttachment();
//            mediaAttachment.setPost(post);
//            mediaAttachment.setPublicId(post.getPublicId());
//            mediaAttachment.setStoragePath(uploadMediaResponse.getMediaPath());
//            mediaAttachment.setUploadTime(beijingTime);
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
