package top.xcyyds.wxfbackendclient.module.notification.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.xcyyds.wxfbackendclient.module.notification.pojo.dto.*;
import top.xcyyds.wxfbackendclient.module.notification.pojo.entity.*;
import top.xcyyds.wxfbackendclient.module.notification.pojo.enums.NotifyType;
import top.xcyyds.wxfbackendclient.module.notification.producer.ReminderProducer;
import top.xcyyds.wxfbackendclient.module.notification.repository.*;
import top.xcyyds.wxfbackendclient.module.notification.service.INotificationService;
import top.xcyyds.wxfbackendclient.module.post.service.IPostService;
import top.xcyyds.wxfbackendclient.module.user.service.impl.UserService;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-12
 * @Description:
 * @Version:v1
 */
@Slf4j
@Service("NotificationService")
public class NotificationService implements INotificationService {

    @Autowired
    private NotifyRepository notifyRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserNotifyStatusRepository userNotifyStatusRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ReminderProducer reminderProducer;

    @Autowired
    private SubscriptionActionTypeRepository subscriptionActionTypeRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserNotifyRepository userNotifyRepository;

    @Autowired
    private SubscriptionConfigRepository subscriptionConfigRepository;

    @Autowired
    private SubscriptionConfigMetaDataRepository subscriptionConfigMetaDataRepository;

    @Override
    public ListUserNotifysResponse listUserNotify(GetUserNotifyRequest request) {
        //将publicId转换为internalId
        long internalId= userService.getInternalIdByPublicId(request.getUserPublicId());

        CriteriaBuilder cb=entityManager.getCriteriaBuilder();
        CriteriaQuery<UserNotify> query=cb.createQuery(UserNotify.class);
        Root<UserNotify> root=query.from(UserNotify.class);

        List<Predicate> predicates=new ArrayList<>();
        //等于发送请求用户对应id
        predicates.add(cb.equal(root.get("userInternalId"), internalId));
        //若指定了通知类型，则加条件
        if(request.getNotifyType()!=null)
            //指定通知类型
            predicates.add(cb.equal(root.get("notify").get("notifyType"), request.getNotifyType()));

        //时间游标处理
        if(StringUtils.hasText(request.getTimeCursor())){
            OffsetDateTime cursorTime=OffsetDateTime.parse(request.getTimeCursor());
            predicates.add(cb.lessThan(root.get("createdAt"),cursorTime));
        }
        //排序
        query.where(predicates.toArray(new Predicate[0]))
                .orderBy(cb.desc(root.get("createdAt")));

        //设置最大查询数量并执行查询
        List<UserNotify>userNotifies=entityManager.createQuery(query)
                .setMaxResults((int)request.getPageSize())
                .getResultList();

        return convertToListUserNotifysResponse(userNotifies);
    }

    private ListUserNotifysResponse convertToListUserNotifysResponse(List<UserNotify> userNotifies) {
        ListUserNotifysResponse response=new ListUserNotifysResponse();
        if (userNotifies.size()==0){
            response.setPageSize(0);
            return response;
        }

        List<GetUserNotifyResponse>getUserNotifyResponses=new ArrayList<>();

        for(UserNotify userNotify:userNotifies){
            GetUserNotifyResponse getUserNotifyResponse=new GetUserNotifyResponse();
            NotifyType notifyType=userNotify.getNotify().getNotifyType();

            getUserNotifyResponse.setNotifyType(notifyType);
            getUserNotifyResponse.setCreatedAt(userNotify.getNotify().getCreatedAt());
            getUserNotifyResponse.setContent(userNotify.getNotify().getContent());
            getUserNotifyResponse.setUserNotifyId(userNotify.getUserNotifyId());
            getUserNotifyResponse.setTargetId(userNotify.getNotify().getTargetId());
            getUserNotifyResponse.setTargetType(userNotify.getNotify().getTargetType());
            getUserNotifyResponse.setSenderPublicId(userNotify.getNotify().getSenderPublicId());
            getUserNotifyResponse.setSourceId(userNotify.getNotify().getSourceId());
            getUserNotifyResponse.setSourceType(userNotify.getNotify().getSourceType());
            getUserNotifyResponse.setAction(userNotify.getNotify().getAction());
            getUserNotifyResponse.setState(userNotify.getState());

            getUserNotifyResponses.add(getUserNotifyResponse);
        }
        response.setNotifies(getUserNotifyResponses);
        response.setPageSize(userNotifies.size());
        response.setTimeCursor(userNotifies.get(userNotifies.size()-1).getNotify().getCreatedAt());
        return response;
    }

    @Override
    public PullReminderResponse pullReminder(PullReminderRequest request) {
        long internalId= userService.getInternalIdByPublicId(request.getUserPublicId());
        UserNotifyStatus userNotifyStatus=updateUserNotifyStatus(0,1,0,internalId);

        PullReminderResponse pullReminderResponse=new PullReminderResponse();
        pullReminderResponse.setTotalUnreadCount(userNotifyStatus.getTotalUnreadCount());
        return pullReminderResponse;
    }

    @Override
    public CreateReminderResponse createReminder(CreateReminderRequest request) {
        Notify notify=new Notify();
        //指定为东八区时间（偏移量“+08：00"）
        OffsetDateTime beijingTime = OffsetDateTime.now(ZoneOffset.ofHours(8));
        SubscriptionActionType subscriptionActionType=getSubscriptionActionType(request.getAction());
        notify.setAction(subscriptionActionType);
        notify.setTargetId(request.getTargetId());
        notify.setTargetType(request.getTargetType());
        notify.setNotifyType(NotifyType.REMINDER);
        notify.setSenderPublicId(request.getSenderPublicId());
        notify.setCreatedAt(beijingTime);
        notify.setSourceId(request.getSourceId());
        notify.setSourceType(request.getSourceType());
        notify.setContent(subscriptionActionType.getDisplayTemplate());

        notifyRepository.save(notify);

        CreateReminderResponse response=new CreateReminderResponse();
        log.debug("创建提醒成功，提醒id为{},即将把通知自动push到消息队列以同步到userNotify",notify.getNotifyId());
        reminderProducer.sendReminder(notify.getNotifyId());
        return response;
    }

    @Override
    public SubscribeResponse subscribe(SubscribeRequest request) {
        //指定为东八区时间（偏移量“+08：00"）
        OffsetDateTime beijingTime = OffsetDateTime.now(ZoneOffset.ofHours(8));

        Subscription subscription=new Subscription();

        subscription.setCreatedAt(beijingTime);
        subscription.setTargetId(request.getTargetId());
        subscription.setTargetType(request.getTargetType());
        subscription.setActions(request.getActions());
        subscription.setUserInternalId(request.getUserInternalId());
        subscription=subscriptionRepository.save(subscription);

        SubscribeResponse response=new SubscribeResponse();
        response.setSubscriptionId(subscription.getSubscriptionId());
        return response;
    }

    @Override
    public CancelSubscriptionResponse cancelSubscription(CancelSubscriptionRequest request) {
        return null;
    }

    @Override
    public UpdateSubscriptionConfigResponse updateSubscriptionConfig(UpdateSubscriptionConfigRequest request) {

        long internalId= userService.getInternalIdByPublicId(request.getUserPublicId());
        SubscriptionConfig subscriptionConfig=subscriptionConfigRepository.findByUserInternalIdAndSubscriptionConfigMetaDataId(internalId,request.getConfigId());
        SubscriptionConfigMetaData subscriptionConfigMetaData=subscriptionConfigMetaDataRepository.findByConfigId(request.getConfigId());
        if(subscriptionConfig==null){
            subscriptionConfig=new SubscriptionConfig();
            subscriptionConfig.setUserInternalId(internalId);
            subscriptionConfig.setSubscriptionConfigMetaDataId(subscriptionConfigMetaData.getConfigId());
            subscriptionConfig.setAllow(request.isAllow());
        }else {
            subscriptionConfig.setAllow(request.isAllow());
        }
        subscriptionConfig=subscriptionConfigRepository.save(subscriptionConfig);
        UpdateSubscriptionConfigResponse response=new UpdateSubscriptionConfigResponse();
        response.setConfigId(subscriptionConfig.getSubscriptionConfigMetaDataId());
        response.setIsAllow(subscriptionConfig.isAllow());

        response.setActionName(subscriptionConfigMetaData.getAction().getActionName());
        return response;
    }

    @Override
    public GetSubscriptionConfigResponse getSubscriptionConfig(GetSubscriptionConfigRequest request) {
        long internalId= userService.getInternalIdByPublicId(request.getUserPublicId());

        List<SummarySubscriptionConfigProjection>summarySubscriptionConfigs=subscriptionConfigMetaDataRepository.getUserConfigs(internalId);


        GetSubscriptionConfigResponse response=new GetSubscriptionConfigResponse();
        response.setConfigs(convertToSummarySubscriptionConfigList(summarySubscriptionConfigs));
        return response;
    }

    private static List<SummarySubscriptionConfig> convertToSummarySubscriptionConfigList(List<SummarySubscriptionConfigProjection> summarySubscriptionConfigs){
        for(SummarySubscriptionConfigProjection summarySubscriptionConfigProjection:summarySubscriptionConfigs){
            log.debug("summarySubscriptionConfigProjection:{},{},{}",summarySubscriptionConfigProjection.getIsAllow(),summarySubscriptionConfigProjection.getActionName(),summarySubscriptionConfigProjection.getConfigId());
        }
        return summarySubscriptionConfigs.stream()
        .map(summarySubscriptionConfigProjection ->
            new SummarySubscriptionConfig(summarySubscriptionConfigProjection.getConfigId(),
                    summarySubscriptionConfigProjection.getActionName(),
                    summarySubscriptionConfigProjection.getIsAllow())).collect(Collectors.toList());
    }

    @Override
    public ReadUserNotifyResponse readUserNotify(ReadUserNotifyRequest request) {
        //将publicId转换为internalId
        long internalId= userService.getInternalIdByPublicId(request.getUserPublicId());
        updateUserNotifyStatus(0,0,1,internalId);

        ReadUserNotifyResponse response=new ReadUserNotifyResponse();

        return response;
    }

    @Override
    public SubscriptionActionType getSubscriptionActionType(String actionName) {
        return subscriptionActionTypeRepository.findByActionName(actionName);
    }

    @Override
    public Notify getNotifyByNotifyId(long notifyId) {
        return notifyRepository.findByNotifyId(notifyId);
    }

    @Override
    public List<Long> getSubscriptionUserInternalIdsByNotify(Notify notify) {
        //查询订阅了该通知的用户
        List<Subscription>subscriptionss=subscriptionRepository.findAllByTargetIdAndTargetType(notify.getTargetId(),notify.getTargetType());

        List<Long>userInternalIds=new ArrayList<>();
        for (Subscription subscription:subscriptionss){
            //查询对应用户总的订阅配置：根据该用户对于该种action的配置
            SummarySubscriptionConfigProjection subscriptionConfig=subscriptionConfigMetaDataRepository.getUserConfigByAction(subscription.getUserInternalId(),notify.getAction());
            log.debug("用户该项配置为subscriptionConfig:{}|{}",notify.getAction(),subscriptionConfig.getIsAllow());
            if(subscriptionConfig.getIsAllow()){
                userInternalIds.add(subscription.getUserInternalId());
            }
        }

        return userInternalIds;
    }

    @Override
    public void saveUserNotify(UserNotify userNotify) {
        userNotifyRepository.save(userNotify);
    }

    @Override
    public UserNotifyStatus updateUserNotifyStatus(long totalVersionOffset, long readNotifyVersionOffset, long totalUnreadCount, long userInternalId) {
        UserNotifyStatus userNotifyStatus=userNotifyStatusRepository.findByUserInternalId(userInternalId);

        //用户初次使用找不到则进行创建
        if (userNotifyStatus==null){
            userNotifyStatus=new UserNotifyStatus();
            userNotifyStatus.setUserInternalId(userInternalId);
            userNotifyStatus.setTotalNotifyVersion(0);
            userNotifyStatus.setReadNotifyVersion(0);
            userNotifyStatus.setTotalUnreadCount(0);
        }
        //逻辑：创建时总的版本=总的版本+1，拉取时将总的版本-已读的版本数得到未读消息数，read消息时再将已读版本数+未读消息数，这样即使用户获取完未读数量但没点进去之间这段空余时间有新的消息也不会消失
        //或者说：应该在创建userNotidy时才将总版本+1，这样才能避免用户看到未读消息>1，但是点进去没有拉取到
        if (totalVersionOffset>0){
            userNotifyStatus.setTotalNotifyVersion(userNotifyStatus.getTotalNotifyVersion()+totalVersionOffset);
        }else if(readNotifyVersionOffset>0){
            userNotifyStatus.setTotalUnreadCount(userNotifyStatus.getTotalNotifyVersion()-userNotifyStatus.getReadNotifyVersion());
        }else if (totalUnreadCount>0){
            userNotifyStatus.setReadNotifyVersion(userNotifyStatus.getReadNotifyVersion()+userNotifyStatus.getTotalUnreadCount());
            userNotifyStatus.setTotalUnreadCount(userNotifyStatus.getTotalNotifyVersion()-userNotifyStatus.getReadNotifyVersion());
        }


        return userNotifyStatusRepository.save(userNotifyStatus);
    }


}
