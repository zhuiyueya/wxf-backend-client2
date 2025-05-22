package top.xcyyds.wxfbackendclient.module.user.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.pojo.dto.UploadMediaResponse;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.service.IMediaStorageService;
import top.xcyyds.wxfbackendclient.module.user.persistence.repository.DepartmentRepository;
import top.xcyyds.wxfbackendclient.module.user.persistence.repository.MajorRepository;
import top.xcyyds.wxfbackendclient.module.user.persistence.repository.UserRepository;
import top.xcyyds.wxfbackendclient.module.user.persistence.repository.UserSchoolEnrollInfoRepository;
import top.xcyyds.wxfbackendclient.module.user.pojo.dto.*;
import top.xcyyds.wxfbackendclient.module.user.pojo.entity.*;
import top.xcyyds.wxfbackendclient.module.user.service.IUserService;

import java.util.Optional;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-22
 * @Description:实现用户相关业务逻辑
 * @Version:v1
 */

@Slf4j
@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserSchoolEnrollInfoRepository userSchoolEnrollInfoRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private MajorRepository majorRepository;
    @Autowired
    private IMediaStorageService mediaStorageService;

    @Override
    public GetUserSelfInfoResponse getUserSelfInfo(GetUserSelfInfoRequest request) {
        // to do...
        //通过publicId和internalId的映射表，快速获取internalId，并使用internalId进行查询，加快处理速度
        User user=userRepository.findByPublicId(request.getPublicId());

//        response.setMajorName(user.getEnrollInfo().getClazz().getMajor().getName());
//        response.setDepartmentName(user.getEnrollInfo().getClazz().getMajor().getDepartment().getName());

        return convertToGetUserSelfInfoResponse(user);
    }

    @Override
    public GetUserSelfInfoResponse updateUserSelfNickName(UpdateUserSelfNickNameRequest updateUserSelfNickNameRequest) {
        User user=userRepository.findByPublicId(updateUserSelfNickNameRequest.getPublicId());
        user.setNickName(updateUserSelfNickNameRequest.getNickName());
        return convertToGetUserSelfInfoResponse(userRepository.save(user));
        //return userRepository.updateUserNickName(updateUserSelfNickNameRequest.getPublicId(), updateUserSelfNickNameRequest.getNickName());
    }

    @Override
    public GetUserSelfInfoResponse updateUserSelfMajor(UpdateUserSelfMajorRequest updateUserSelfMajorRequest) {
        User user=userRepository.findByPublicId(updateUserSelfMajorRequest.getPublicId());

        //若该用户未设置过学籍相关信息，则先创建
        if (user.getEnrollInfo()==null){
            user.setEnrollInfo(new UserSchoolEnrollInfo());
        }
        //若该用户未设置过班级等相关信息，则先创建
        if(user.getEnrollInfo().getClazz()==null){
            user.getEnrollInfo().setClazz(new Clazz());
        }
        //若该用户未设置过专业相关信息，则先创建
        if(user.getEnrollInfo().getClazz().getMajor()==null){
            user.getEnrollInfo().getClazz().setMajor(new Major());
        }
        Major major=majorRepository.findByMajorId(updateUserSelfMajorRequest.getMajorId());
        user.getEnrollInfo().getClazz().setMajor(major);
        return convertToGetUserSelfInfoResponse(userRepository.save(user));
    }

    @Override
    public GetUserSelfInfoResponse updateUserDepartment(UpdateUserSelfDepartmentRequest updateUserSelfDepartmentRequest) {
        User user=userRepository.findByPublicId(updateUserSelfDepartmentRequest.getPublicId());
        //若该用户未设置过学籍相关信息，则先创建
        if (user.getEnrollInfo()==null){
            user.setEnrollInfo(new UserSchoolEnrollInfo());
        }
        //若该用户未设置过班级等相关信息，则先创建
        if(user.getEnrollInfo().getClazz()==null){
            user.getEnrollInfo().setClazz(new Clazz());
        }
        //若该用户未设置过专业相关信息，则先创建
        if(user.getEnrollInfo().getClazz().getMajor()==null){
            user.getEnrollInfo().getClazz().setMajor(new Major());
        }
        if(user.getEnrollInfo().getClazz().getMajor().getDepartment()==null){
            user.getEnrollInfo().getClazz().getMajor().setDepartment(new Department());
        }

        Department department=departmentRepository.findByDepartmentId(updateUserSelfDepartmentRequest.getDepartmentId());


        user.getEnrollInfo().getClazz().getMajor().setDepartment(department);
        return convertToGetUserSelfInfoResponse(userRepository.save(user));
    }

    @Override
    public GetUserInfoResponse getUserInfo(GetUserInfoRequest getUserInfoRequest) {
        User user=userRepository.findByPublicId(getUserInfoRequest.getPublicId());
        if (user==null){
            return null;
        }
        GetUserInfoResponse getUserInfoResponse=new GetUserInfoResponse();
        BeanUtils.copyProperties(user,getUserInfoResponse);
        return getUserInfoResponse;
    }

    @Override
    public SummaryAuthorInfo getSummaryAuthorInfoByPublicId(String publicId) {
        GetUserInfoRequest getUserInfoRequest=new GetUserInfoRequest();
        getUserInfoRequest.setPublicId(publicId);
        GetUserInfoResponse getUserInfoResponse=getUserInfo(getUserInfoRequest);
        if (getUserInfoResponse==null){
            return null;
        }
        SummaryAuthorInfo summaryAuthorInfo=new SummaryAuthorInfo();
        BeanUtils.copyProperties(getUserInfoResponse,summaryAuthorInfo);
        return summaryAuthorInfo;
    }

    @Override
    public long getInternalIdByPublicId(String publicId) {
        return userRepository.findByPublicId(publicId).getInternalId();
    }

    @Override
    //更新用户头像的url
    public GetUserSelfInfoResponse updateUserAvatar(UpdateUserSelfAvatarRequest updateUserSelfAvatarRequest) {
        User user=userRepository.findByPublicId(updateUserSelfAvatarRequest.getPublicId());
        UploadMediaResponse uploadResponse = mediaStorageService.uploadMedia(updateUserSelfAvatarRequest.getMultipartFile(),"/avatar");//写死，后可修改
        user.setAvatar(uploadResponse.getMediaPath());
        return convertToGetUserSelfInfoResponse(userRepository.save(user));
    }


    private GetUserSelfInfoResponse convertToGetUserSelfInfoResponse(User user) {
        GetUserSelfInfoResponse response = new GetUserSelfInfoResponse();

        //设置返回信息
        response.setPublicId(user.getPublicId());
        response.setAvatar(user.getAvatar());
        response.setLevel(user.getLevel());
        response.setMoney(user.getMoney());
        response.setRole(user.getRole());
        response.setNickName(user.getNickName());
        response.setPostCount(user.getPostCount());

        // 设置 MajorName
        Optional.ofNullable(user)
                .map(User::getEnrollInfo)
                .map(UserSchoolEnrollInfo::getClazz)
                .map(Clazz::getMajor)
                .map(Major::getName)
                .ifPresentOrElse(
                        name -> response.setMajorName(name),
                        () -> response.setMajorName("")  // 默认值或空字符串
                );

        // 设置 DepartmentName
        Optional.ofNullable(user)
                .map(User::getEnrollInfo)
                .map(UserSchoolEnrollInfo::getClazz)
                .map(Clazz::getMajor)
                .map(Major::getDepartment)
                .map(Department::getName)
                .ifPresentOrElse(
                        name -> response.setDepartmentName(name),
                        () -> response.setDepartmentName("")
                );
        return response;
    }
}
