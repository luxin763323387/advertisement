package com.cn.lx.controller;

import com.alibaba.fastjson.JSON;
import com.cn.lx.exception.AdException;
import com.cn.lx.service.impl.UserServiceImpl;
import com.cn.lx.vo.CreateUserRequest;
import com.cn.lx.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping
public class UserOPController {

    private final UserServiceImpl userService;

    @Autowired
    public UserOPController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/create/user")
    public CreateUserResponse creativeUser(@RequestBody CreateUserRequest request) throws AdException {
        log.info("[createUser] -> /create/user");
        JSON.toJSONString(request);
        return userService.createUser(request);
    }
}
