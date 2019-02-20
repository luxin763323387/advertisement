package com.cn.lx.client;

import com.cn.lx.client.vo.AdPlan;
import com.cn.lx.client.vo.AdPlanGetRequest;
import com.cn.lx.vo.CommonResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SponsorClientHystrix implements SponsorClient {
    @Override
    public CommonResponse<List<AdPlan>> getAdPlans(AdPlanGetRequest request) {
        return new CommonResponse<>(-1,"eureka-client-ad-sponsor error");
    }
}
