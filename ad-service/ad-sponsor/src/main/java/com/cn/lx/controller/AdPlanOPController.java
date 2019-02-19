package com.cn.lx.controller;

import com.alibaba.fastjson.JSON;
import com.cn.lx.entity.AdPlan;
import com.cn.lx.exception.AdException;
import com.cn.lx.service.impl.AdPlanServiceImpl;
import com.cn.lx.vo.AdPlanGetRequest;
import com.cn.lx.vo.AdPlanRequest;
import com.cn.lx.vo.AdPlanResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class AdPlanOPController {

    private final AdPlanServiceImpl adPlanService;

    @Autowired
    public AdPlanOPController(AdPlanServiceImpl adPlanService) {
        this.adPlanService = adPlanService;
    }

    //创建AdPlan
    @PostMapping("/create/adPlan")
    public AdPlanResponse createAdPlan (@RequestBody AdPlanRequest request) throws AdException {
        log.info("[AdPlanOPController] -> createAdPlan");
        JSON.toJSONString(request);
        return adPlanService.createAdPlan(request);
    }

    //查询List
    @PostMapping("/get/adPlan")
    public List<AdPlan> getAdPlan(@RequestBody AdPlanGetRequest request)throws AdException{
        log.info("[AdPlanOPController] -> getAdPlan");
        JSON.toJSONString(request);
        return adPlanService.getAdPlanByIdS(request);
    }

    //更新AdPlan
    @PutMapping("/update/adPlan")
    public AdPlanResponse updateAdPlan(@RequestBody AdPlanRequest request) throws AdException{
        log.info("[AdPlanOPController] -> updateAdPlan");
        JSON.toJSONString(request);
        return adPlanService.updateAdPlan(request);
    }

    //
    @DeleteMapping("/delete/adPlan")
    public void deleteAdPlan(
            @RequestBody AdPlanRequest request) throws AdException {
        log.info("ad-sponsor: deleteAdPlan -> {}",
                JSON.toJSONString(request));
        adPlanService.deleteAdPlan(request);
    }
}
