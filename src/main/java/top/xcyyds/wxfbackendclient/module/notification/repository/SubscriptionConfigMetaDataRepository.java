package top.xcyyds.wxfbackendclient.module.notification.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import top.xcyyds.wxfbackendclient.module.notification.pojo.dto.SummarySubscriptionConfig;
import top.xcyyds.wxfbackendclient.module.notification.pojo.entity.SubscriptionActionType;
import top.xcyyds.wxfbackendclient.module.notification.pojo.entity.SubscriptionConfigMetaData;

import java.util.List;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-20
 * @Description:
 * @Version:
 */

public interface SubscriptionConfigMetaDataRepository extends JpaRepository<SubscriptionConfigMetaData, Long> {
    /**
     * 查询所有默认配置，当用户自定义配置中存在时替换掉默认配置对应的那条
     */
//    @Query("select md.configId AS configId,md.action.actionName AS actionName,"+
//    "COALESCE(usc.isAllow,md.defaultAllow) AS isAllow "+
//    "FROM SubscriptionConfigMetaData md "+
//    "LEFT JOIN SubscriptionConfig usc "+
//    "ON md.configId = usc.subscriptionConfigMetaDataId AND usc.userInternalId = :userInternalId")
    /**
     * 正确的：subscription_action_type_id
     * 错误的：md.configId
     *
     * SELECT
     *     md.config_id AS configId,
     *     md.action AS actionName,
     *     COALESCE(usc.is_allow, md.default_allow) AS isAllow
     * FROM
     *     subscription_config_meta_data md
     * LEFT JOIN
     *     subscription_config usc
     *     ON md.config_id = usc.subscription_config_meta_data_id
     *     AND usc.user_internal_id = 147802359269949440;
     */
//    @Query("select md.configId AS configId,md.action.actionName AS actionName,"+
//            "COALESCE(usc.isAllow,md.defaultAllow) AS isAllow "+
//            "FROM SubscriptionConfigMetaData md "+
//            "LEFT JOIN SubscriptionConfig usc "+
//            "ON md.configId = usc.subscriptionConfigMetaDataId AND usc.userInternalId = :userInternalId "
//    )
//    @Query("SELECT md.configId AS configId, " +
//            "a1_.actionName AS actionName, " + // 使用显式 JOIN 后的别名
//            "COALESCE(usc.isAllow, md.defaultAllow) AS isAllow " +
//            "FROM SubscriptionConfigMetaData md " +
//            "LEFT JOIN SubscriptionConfig usc " +
//            "ON md.configId = usc.subscriptionConfigMetaDataId " +
//            "AND usc.userInternalId = :userInternalId " +
//            "JOIN SubscriptionActionType a1_ " + // 显式关联 action_type 表
//            "ON a1_.subscriptionActionTypeId = md.action") // 关联字段需匹配实体映射
    @Query("SELECT" +
            "    md.configId AS configId," +
            "    md.action.actionName AS actionName," +
            "    CASE" +
            "        WHEN usc.isAllow IS NOT NULL THEN usc.isAllow  " +
            "        ELSE md.defaultAllow                     " +
            "    END AS isAllow " +
            "FROM" +
            "    SubscriptionConfigMetaData md " +
            "LEFT JOIN" +
            "    SubscriptionConfig usc " +
            "    ON md.configId = usc.subscriptionConfigMetaDataId AND usc.userInternalId = :userInternalId")
    List<SummarySubscriptionConfigProjection>getUserConfigs(@Param("userInternalId") long userInternalId);
//    @Query("SELECT NEW top.xcyyds.wxfbackendclient.module.notification.pojo.dto.SummarySubscriptionConfig(" +
//            "md.configId, md.action.actionName, COALESCE(usc.isAllow, md.defaultAllow)) " +
//            "FROM SubscriptionConfigMetaData md " +
//            "LEFT JOIN SubscriptionConfig usc " +
//            "ON md.configId = usc.subscriptionConfigMetaDataId AND usc.userInternalId = :userInternalId")
//    List<SummarySubscriptionConfig> getUserConfigs(@Param("userInternalId") long userInternalId);

    SubscriptionConfigMetaData findByConfigId(long configId);

//    @Query("select md.configId AS configId,md.action.actionName AS actionName,"+
//            "COALESCE(usc.isAllow,md.defaultAllow) AS isAllow "+
//            "FROM SubscriptionConfigMetaData md "+
//            "LEFT JOIN SubscriptionConfig usc "+
//            "ON md.configId = usc.subscriptionConfigMetaDataId AND usc.userInternalId = :userInternalId "+
//            "WHERE md.action = :action"
//           )
@Query("SELECT" +
        "    md.configId AS configId," +
        "    md.action.actionName AS actionName," +
        "    CASE" +
        "        WHEN usc.isAllow IS NOT NULL THEN usc.isAllow  " +
        "        ELSE md.defaultAllow                     " +
        "    END AS isAllow " +
        "FROM" +
        "    SubscriptionConfigMetaData md " +
        "LEFT JOIN" +
        "    SubscriptionConfig usc " +
        "    ON md.configId = usc.subscriptionConfigMetaDataId AND usc.userInternalId = :userInternalId "+
        "WHERE md.action = :action")
    SummarySubscriptionConfigProjection getUserConfigByAction(@Param("userInternalId") long userInternalId, @Param("caction")SubscriptionActionType action);
}
