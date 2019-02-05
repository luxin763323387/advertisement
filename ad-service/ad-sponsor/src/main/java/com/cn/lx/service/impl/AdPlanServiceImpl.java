package com.cn.lx.service.impl;

import com.cn.lx.constant.CommonStatus;
import com.cn.lx.constant.Constants;
import com.cn.lx.dao.AdPlanRepository;
import com.cn.lx.dao.AdUserRepository;
import com.cn.lx.entity.AdPlan;
import com.cn.lx.entity.AdUser;
import com.cn.lx.exception.AdException;
import com.cn.lx.service.IAdPlanService;
import com.cn.lx.utils.CommUtils;
import com.cn.lx.vo.AdPlanGetRequest;
import com.cn.lx.vo.AdPlanRequest;
import com.cn.lx.vo.AdPlanResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AdPlanServiceImpl implements IAdPlanService {

    private final AdPlanRepository adPlanRepository;

    private final AdUserRepository adUserRepository;

    @Autowired
    public AdPlanServiceImpl(AdPlanRepository adPlanRepository, AdUserRepository adUserRepository) {
        this.adPlanRepository = adPlanRepository;
        this.adUserRepository = adUserRepository;
    }

    @Override
    @Transactional
    public AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException {
        //请求参数判断
        if (!request.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        //关联User是否存在
        Optional<AdUser> adUser = adUserRepository.findById(request.getUserId());
        if (!adUser.isPresent()) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        //判断名字是否相同
        AdPlan oldAdPlan = adPlanRepository.findByUserIdAndPlanName(request.getUserId(), request.getPlanName());
        if (oldAdPlan != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_PLAN_ERROR);
        }
        AdPlan newAdPlan = adPlanRepository.save(new AdPlan(
                request.getUserId(), request.getPlanName(),
                CommUtils.String2Date(request.getStarDate()),
                CommUtils.String2Date(request.getEndDate())));
        return new AdPlanResponse(newAdPlan.getId(), newAdPlan.getPlanName());
    }


    @Override
    public List<AdPlan> getAdPlanByIdS(AdPlanGetRequest request) throws AdException {
        if(!request.validate()){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<AdPlan> adPlanList = adPlanRepository.findAllByIdAndUserId(request.getIds(), request.getUserId());
        return adPlanList;
    }

    @Override
    @Transactional
    public AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException {
        if(!request.updateValidate()){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        AdPlan plan = adPlanRepository.findByIdAndUserId(request.getId(), request.getUserId());
        if(plan == null){
            throw  new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        //如果名字不为空则更新
        if(request.getPlanName() != null){
            plan.setPlanName(request.getPlanName());
        }

        //如果起始时间不为空则更新
        if(request.getStarDate() != null){
            plan.setStartDate(CommUtils.String2Date(request.getStarDate()));
        }

        //如果结束时间不为空则更新
        if(request.getEndDate() != null){
            plan.setEndDate(CommUtils.String2Date(request.getEndDate()));
        }

        plan.setUpdateTime(new Date());
        AdPlan save = adPlanRepository.save(plan);
        return  new AdPlanResponse(save.getId(), save.getPlanName());
    }

    @Override
    @Transactional
    public void deleteAdPlan(AdPlanRequest request) throws AdException {
        if(!request.deleteValidate()){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        AdPlan plan = adPlanRepository.findByIdAndUserId(request.getId(), request.getUserId());
        if(plan == null){
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }
        plan.setPlanStatus(CommonStatus.INVALID.getStatus());
        plan.setUpdateTime(new Date());
        adPlanRepository.save(plan);
    }
}
