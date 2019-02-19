package com.cn.lx.service;

import com.cn.lx.entity.AdPlan;
import com.cn.lx.exception.AdException;
import com.cn.lx.vo.AdPlanGetRequest;
import com.cn.lx.vo.AdPlanRequest;
import com.cn.lx.vo.AdPlanResponse;

import java.util.List;

public interface IAdPlanService {

    /**创建推广计划*/
    AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException;

    /**批量获取*/
    List<AdPlan> getAdPlanByIdS(AdPlanGetRequest request) throws AdException;

    /**更新广告计划*/
    AdPlanResponse updateAdPlan(AdPlanRequest request) throws  AdException;

    /**删除广告计划，没有返回值
     * */
    void deleteAdPlan (AdPlanRequest request) throws  AdException;
}
