package com.cn.lx.controller;

import com.alibaba.fastjson.JSON;
import com.cn.lx.entity.unit_condition.AdUnitIt;
import com.cn.lx.exception.AdException;
import com.cn.lx.service.AdUnitService;
import com.cn.lx.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AdUnitOPController {

    private final AdUnitService adUnitService;

    @Autowired
    public AdUnitOPController(AdUnitService adUnitService) {
        this.adUnitService = adUnitService;
    }

    //创建广告单元
    @PostMapping("/create/adUnit")
    public AdUnitResponse createAdUnit(@RequestBody AdUnitRequest request)throws AdException {
        log.info("[AdUnitOPController] -> createAdUnit");
        JSON.toJSONString(request);
    return adUnitService.createUnit(request);
    }

    //创建广告单元关键词
    @PostMapping("/create/adUnitKeyword")
    public AdUnitKeywordResponse createAdUnitKeyword(@RequestBody AdUnitKeywordRequest request)throws AdException {
        log.info("[AdUnitOPController] ->createAdUnitKeyword ");
        JSON.toJSONString(request);
        return adUnitService.createUnitKeyword(request);
    }

    //创建广告兴趣单元
    @PostMapping("/create/adUnitIt")
    public AdUnitItResponse createAdUnitIt (@RequestBody AdUnitItRequest request) throws AdException{
        log.info("[AdUnitOPController] ->createAdUnitIt ");
        JSON.toJSONString(request);
        return adUnitService.createUnitIt(request);
    }

    //创建广告地域单元
    @PostMapping("/create/adUnitDistrict")
    public AdUnitDistrictResponse createAdUnitDistrict (@RequestBody AdUnitDistrictRequest request) throws AdException{
        log.info("[AdUnitOPController] ->createAdUnitDistrict ");
        JSON.toJSONString(request);
        return adUnitService.createUnitDistrict(request);
    }

    //创建广告创意推广单元
    @PostMapping("/create/creativeUnit")
    public CreativeUnitResponse createCreativeUnit (@RequestBody CreativeUnitRequest request) throws AdException{
        log.info("[AdUnitOPController] ->createCreativeUnit ");
        JSON.toJSONString(request);
        return adUnitService.createCreativeUnit(request);
    }
}
