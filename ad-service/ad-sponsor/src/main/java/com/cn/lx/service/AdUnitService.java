package com.cn.lx.service;

import com.cn.lx.exception.AdException;
import com.cn.lx.vo.*;
import org.springframework.util.CollectionUtils;

public interface AdUnitService  {

    //创建广告单元
    AdUnitResponse createUnit (AdUnitRequest request ) throws AdException;

    //创建广告单元关键字
    AdUnitKeywordResponse createUnitKeyword(AdUnitKeywordRequest request) throws AdException;

    //创建广告单元兴趣制度
    AdUnitItResponse createUnitIt(AdUnitItRequest request) throws AdException;

    //创建广告单元地域维度
    AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictRequest request)throws  AdException;

    //创建创意单元
    CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request)
            throws AdException;
}
