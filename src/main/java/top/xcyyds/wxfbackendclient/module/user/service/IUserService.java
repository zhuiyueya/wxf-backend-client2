package top.xcyyds.wxfbackendclient.module.user.service;

import top.xcyyds.wxfbackendclient.module.user.pojo.dto.*;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-22
 * @Description:
 * @Version:
 */

public interface IUserService {
    GetUserSelfInfoResponse getUserSelfInfo(GetUserSelfInfoRequest request);

    GetUserSelfInfoResponse updateUserSelfNickName(UpdateUserSelfNickNameRequest updateUserSelfNickNameRequest);

    GetUserSelfInfoResponse updateUserSelfMajor(UpdateUserSelfMajorRequest updateUserSelfMajorRequest);

    GetUserSelfInfoResponse updateUserDepartment(UpdateUserSelfDepartmentRequest updateUserSelfDepartmentRequest);

    GetUserSelfInfoResponse updateUserAvatar(UpdateUserSelfAvatarRequest updateUserSelfAvatarRequest);

    GetUserInfoResponse getUserInfo(GetUserInfoRequest getUserInfoRequest);

    SummaryAuthorInfo getSummaryAuthorInfoByPublicId(String publicId);

    long getInternalIdByPublicId(String publicId);

}
