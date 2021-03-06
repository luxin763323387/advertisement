package com.cn.lx.controller;

import com.alibaba.fastjson.JSON;
import com.cn.lx.annotation.IgnoreResponseAdvice;
import com.cn.lx.client.SponsorClient;
import com.cn.lx.client.vo.AdPlan;
import com.cn.lx.client.vo.AdPlanGetRequest;
import com.cn.lx.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RestController
public class SearchController {

    private final RestTemplate restTemplate;
    private final SponsorClient sponsorClient;

    @Autowired
    public SearchController(RestTemplate restTemplate, SponsorClient sponsorClient) {
        this.restTemplate = restTemplate;
        this.sponsorClient = sponsorClient;
    }

    @IgnoreResponseAdvice
    @PostMapping("/getAdPlans")
    public CommonResponse<List<AdPlan>> getAdPlans(@RequestBody AdPlanGetRequest request){
        log.info("[SearchController] -> getAdPlans",JSON.toJSONString(request));
        return sponsorClient.getAdPlans(request);
    }


    @SuppressWarnings("all")
    @IgnoreResponseAdvice
    @PostMapping("/getAdPlanByRibbon")
    public CommonResponse<List<AdPlan>> getAdPlanByRibbon(@RequestBody AdPlanGetRequest request){
        log.info("[SearchController] -> getAdPlanByRibbon", JSON.toJSONString(request));
        return restTemplate.postForEntity(
                "http://eureka-client-ad-sponsor/ad-sponsor/get/adPlan",request,
                CommonResponse.class
        ).getBody();
    }
}
