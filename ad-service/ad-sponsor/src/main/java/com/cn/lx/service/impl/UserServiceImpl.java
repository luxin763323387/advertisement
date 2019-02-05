package com.cn.lx.service.impl;

import com.cn.lx.constant.Constants;
import com.cn.lx.dao.AdUserRepository;
import com.cn.lx.entity.AdUser;
import com.cn.lx.exception.AdException;
import com.cn.lx.service.IUserService;
import com.cn.lx.utils.CommUtils;
import com.cn.lx.vo.CreateUserRequest;
import com.cn.lx.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    private final AdUserRepository adUserRepository;
    @Autowired
    public UserServiceImpl(AdUserRepository adUserRepository) {
        this.adUserRepository = adUserRepository;
    }

    @Override
    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) throws AdException {

        //验证传入的参数是否正确
        if(!request.validate()){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        //判断名字是否与数据库重复
        AdUser oldUser = adUserRepository.findByUsername(request.getUsername());
        if(oldUser != null){
            throw new AdException(Constants.ErrorMsg.SAME_NAME_ERROR);
        }

        //获取新的用户，获取token
        AdUser newUser = adUserRepository.save(new AdUser(
                request.getUsername(), CommUtils.md5(request.getUsername())
        ));
        return new CreateUserResponse(newUser.getId(), newUser.getUsername(), newUser.getToken(),
                                                               newUser.getCreateTime(), newUser.getUpdateTime());
    }
}
