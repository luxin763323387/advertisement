package com.cn.lx.service.impl;

import com.cn.lx.constant.Constants;
import com.cn.lx.dao.AdPlanRepository;
import com.cn.lx.dao.AdUnitRepository;
import com.cn.lx.dao.unit_condition.AdUnitDistrictRepository;
import com.cn.lx.dao.unit_condition.AdUnitItRepository;
import com.cn.lx.dao.unit_condition.AdUnitKeywordRepository;
import com.cn.lx.dao.unit_condition.CreativeUnitRepository;
import com.cn.lx.entity.AdPlan;
import com.cn.lx.entity.AdUnit;
import com.cn.lx.entity.unit_condition.AdUnitDistrict;
import com.cn.lx.entity.unit_condition.AdUnitIt;
import com.cn.lx.entity.unit_condition.AdUnitKeyword;
import com.cn.lx.entity.unit_condition.CreativeUnit;
import com.cn.lx.exception.AdException;
import com.cn.lx.service.AdUnitService;
import com.cn.lx.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdUnitServiceImpl implements AdUnitService {

    private final AdUnitDistrictRepository adUnitDistrictRepository;
    private final AdPlanRepository adPlanRepository;
    private final AdUnitRepository adUnitRepository;
    private final AdUnitKeywordRepository adUnitKeywordRepository;
    private final AdUnitItRepository adUnitItRepository;
    private final CreativeUnitRepository creativeUnitRepository;

    @Autowired
    public AdUnitServiceImpl(AdPlanRepository adPlanRepository, AdUnitRepository adUnitRepository, AdUnitKeywordRepository adUnitKeywordRepository, AdUnitItRepository adUnitItRepository, AdUnitDistrictRepository adUnitDistrictRepository, CreativeUnitRepository creativeUnitRepository) {
        this.adPlanRepository = adPlanRepository;
        this.adUnitRepository = adUnitRepository;
        this.adUnitKeywordRepository = adUnitKeywordRepository;
        this.adUnitItRepository = adUnitItRepository;
        this.adUnitDistrictRepository = adUnitDistrictRepository;
        this.creativeUnitRepository = creativeUnitRepository;
    }

    @Override
    @Transactional
    public AdUnitResponse createUnit(AdUnitRequest request) throws AdException {

        if(!request.creativeValidate()){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        //根据id查找
        Optional<AdPlan> adPlan = adPlanRepository.findById(request.getPlanId());
        //判断是否存在，不存在抛出异常
        if(adPlan.isPresent()){
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        AdUnit oldUnit = adUnitRepository.findByPlanIdAndUnitName(request.getPlanId(), request.getUnitName());
        if (oldUnit != null){
            throw new AdException(Constants.ErrorMsg.SAME_NAME_ERROR);
        }

        AdUnit newUnit = adUnitRepository.save(new AdUnit(request.getPlanId(), request.getUnitName(),
                request.getPositionType(), request.getBudget()));
        return new AdUnitResponse(newUnit.getId(), newUnit.getUnitName());
    }

    @Override
    public AdUnitKeywordResponse createUnitKeyword(AdUnitKeywordRequest request) throws AdException {
        List<Long> unitIds = request.getUnitKeywords().stream()
                .map(AdUnitKeywordRequest.UnitKeyword::getUnitId)
                .collect(Collectors.toList());
        if(!isRelatedUnitExits(unitIds)){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<Long> ids = Collections.emptyList();
        List<AdUnitKeyword> adUnitKeywords = new ArrayList<>();
        if(!CollectionUtils.isEmpty(request.getUnitKeywords())){
            request.getUnitKeywords().forEach(i -> adUnitKeywords.add(
               new AdUnitKeyword(i.getUnitId(),i.getKeyword())
            ));
            ids = adUnitKeywordRepository.saveAll(adUnitKeywords).stream()
                    .map(AdUnitKeyword::getId)
                    .collect(Collectors.toList());
        }
        return new AdUnitKeywordResponse(ids);
    }

    @Override
    public AdUnitItResponse createUnitIt(AdUnitItRequest request) throws AdException {
        List<Long> unitIds = request.getUnitIts().stream()
                .map(AdUnitItRequest.UnitIt::getUnitId)
                .collect(Collectors.toList());
        if(!isRelatedUnitExits(unitIds)){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<AdUnitIt> adUnitIts = new ArrayList<>();
        request.getUnitIts().forEach(i -> adUnitIts.add(
                new AdUnitIt(i.getUnitId(),i.getItTag())
        ));
        List<Long> ids = adUnitItRepository.saveAll(adUnitIts).stream()
                .map(AdUnitIt::getId)
                .collect(Collectors.toList());
        return new AdUnitItResponse(ids);
    }

    @Override
    public AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictRequest request) throws AdException {
        List<Long> unitIds = request.getUnitDistricts().stream()
                .map(AdUnitDistrictRequest.UnitDistrict::getUnitId)
                .collect(Collectors.toList());
        if(!isRelatedUnitExits(unitIds)){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<AdUnitDistrict> adUnitDistricts = new ArrayList<>();
        request.getUnitDistricts().forEach(i -> adUnitDistricts.add(
                new AdUnitDistrict(i.getUnitId(), i.getProvince(), i.getCity())
        ));
        List<Long> ids = adUnitDistrictRepository.saveAll(adUnitDistricts).stream()
                .map(AdUnitDistrict::getId)
                .collect(Collectors.toList());
        return new AdUnitDistrictResponse(ids);
    }

    @Override
    public CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request) throws AdException {
        List<Long> unitIds = request.getUnitItems().stream()
                .map(CreativeUnitRequest.CreativeUnitItem::getUnitId)
                .collect(Collectors.toList());
        List<Long> creativeIds = request.getUnitItems().stream()
                .map(CreativeUnitRequest.CreativeUnitItem::getCreativeId)
                .collect(Collectors.toList());
        if(!isRelatedUnitExits(unitIds) && !isRelatedCreativeExist(creativeIds)){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<CreativeUnit> creativeUnits = new ArrayList<>();
        request.getUnitItems().forEach(i -> creativeUnits.add(
                new CreativeUnit(i.getCreativeId(),i.getUnitId())
        ));
        List<Long> ids = creativeUnitRepository.saveAll(creativeUnits).stream()
                .map(CreativeUnit::getId)
                .collect(Collectors.toList());
        return new CreativeUnitResponse(ids);
    }

    private boolean isRelatedUnitExits(List<Long> unitIds){
        //判断集合是否为空
        if(CollectionUtils.isEmpty(unitIds)){
            return  false;
        }
        //因为unitIds 可能有重复 所以用hashset比较
        return adUnitRepository.findAllById(unitIds).size() ==
                new HashSet<>(unitIds).size();
    }

    private boolean isRelatedCreativeExist(List<Long> creativeIds){
        if(CollectionUtils.isEmpty(creativeIds)) {
            return false;
        }

        return creativeUnitRepository.findAllById(creativeIds).size() ==
                new HashSet<>(creativeIds).size();
    }

}
