package top.xcyyds.wxfbackendclient.module.notification.pojo.dto;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-12
 * @Description:
 * @Version:
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateSubscriptionConfigRequest {
    private String userPublicId;
    private long configId;
    private boolean isAllow;
    @JsonProperty(value="isAllow")
    public boolean isAllow() {
        return isAllow;
    }

    public void setIsAllow(boolean isAllow) {
        this.isAllow = isAllow;
    }
}
