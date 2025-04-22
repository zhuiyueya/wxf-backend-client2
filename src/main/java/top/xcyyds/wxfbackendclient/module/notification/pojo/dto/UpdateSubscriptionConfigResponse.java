package top.xcyyds.wxfbackendclient.module.notification.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-12
 * @Description:
 * @Version:
 */
@Data
public class UpdateSubscriptionConfigResponse {
    private boolean isAllow;
    private String actionName;
    private long configId;
    @JsonProperty(value="isAllow")
    public boolean isAllow() {
        return isAllow;
    }

    public void setIsAllow(boolean isAllow) {
        this.isAllow = isAllow;
    }
}
