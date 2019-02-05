package com.cn.lx.service;

import com.cn.lx.exception.AdException;
import com.cn.lx.vo.CreateUserRequest;
import com.cn.lx.vo.CreateUserResponse;

public interface IUserService {

    /**创建用户*/
    CreateUserResponse createUser (CreateUserRequest request) throws AdException;


}
