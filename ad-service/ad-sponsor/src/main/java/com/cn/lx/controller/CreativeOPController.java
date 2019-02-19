package com.cn.lx.controller;

import com.alibaba.fastjson.JSON;
import com.cn.lx.exception.AdException;
import com.cn.lx.service.CreativeService;
import com.cn.lx.vo.CreativeRequest;
import com.cn.lx.vo.CreativeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CreativeOPController {

    private final CreativeService creativeService;

    @Autowired
    public CreativeOPController(CreativeService creativeService) {
        this.creativeService = creativeService;
    }

    @PostMapping("/create/creative")
    public CreativeResponse createCreative(
            @RequestBody CreativeRequest request
    ) throws AdException {
        log.info("ad-sponsor: createCreative -> {}",
                JSON.toJSONString(request));
        return creativeService.createCreative(request);
    }
}
