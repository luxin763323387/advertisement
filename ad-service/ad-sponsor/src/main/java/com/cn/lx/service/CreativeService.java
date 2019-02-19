package com.cn.lx.service;

import com.cn.lx.exception.AdException;
import com.cn.lx.vo.CreateUserRequest;
import com.cn.lx.vo.CreativeRequest;
import com.cn.lx.vo.CreativeResponse;

public interface CreativeService {

    //创造创意
    CreativeResponse createCreative(CreativeRequest request)throws AdException;
}
