package top.xcyyds.wxfbackendclient.module.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.xcyyds.wxfbackendclient.module.user.persistence.repository.UserRepository;
import top.xcyyds.wxfbackendclient.module.user.persistence.repository.UserSchoolEnrollInfoRepository;
import top.xcyyds.wxfbackendclient.module.user.pojo.dto.GetUserSelfInfoRequest;
import top.xcyyds.wxfbackendclient.module.user.pojo.dto.GetUserSelfInfoResponse;
import top.xcyyds.wxfbackendclient.module.user.pojo.entity.User;
import top.xcyyds.wxfbackendclient.module.user.pojo.entity.UserSchoolEnrollInfo;
import top.xcyyds.wxfbackendclient.module.user.service.IUserService;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-22
 * @Description:实现用户相关业务逻辑
 * @Version:v1
 */

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserSchoolEnrollInfoRepository userSchoolEnrollInfoRepository;
    @Override
    public GetUserSelfInfoResponse getUserSelfInfo(GetUserSelfInfoRequest request) {
        // to do...
        //通过publicId和internalId的映射表，快速获取internalId，并使用internalId进行查询，加快处理速度
        User user=userRepository.findByPublicId(request.getPublicId());
        GetUserSelfInfoResponse response = new GetUserSelfInfoResponse();

        //设置返回信息
        response.setPublicId(user.getPublicId());
        response.setAvator(user.getAvatar());
        response.setLevel(user.getLevel());
        response.setMoney(user.getMoney());
        response.setRole(user.getRole());
        response.setNickName(user.getNickName());
        response.setPostCount(user.getPostCount());

        response.setMajorName(user.getEnrollInfo().getClazz().getMajor().getName());
        response.setDepartmentName(user.getEnrollInfo().getClazz().getMajor().getDepartment().getName());

        return response;
    }
}
