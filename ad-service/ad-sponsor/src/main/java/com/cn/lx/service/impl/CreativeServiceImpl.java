package com.cn.lx.service.impl;

import com.cn.lx.constant.CommonStatus;
import com.cn.lx.dao.CreativeRepository;
import com.cn.lx.entity.Creative;
import com.cn.lx.exception.AdException;
import com.cn.lx.service.CreativeService;
import com.cn.lx.vo.CreativeRequest;
import com.cn.lx.vo.CreativeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class CreativeServiceImpl implements CreativeService {

    @Autowired
    private CreativeRepository creativeRepository;

    @Override
    public CreativeResponse createCreative(CreativeRequest request) throws AdException {
        //个人方法
  /*      Creative creative1 = request2Entity(request);

        Creative creative = creativeRepository.save(
                creative1
        );
        return new CreativeResponse(creative.getId(),creative.getName());
    }

        private Creative request2Entity(CreativeRequest request){
            Creative creative = new Creative();
            creative.setName(request.getName());
            creative.setType(request.getType());
            creative.setMaterialType(request.getMaterialType());
            creative.setHeight(request.getHeight());
            creative.setWidth(request.getWidth());
            creative.setSize(request.getSize());
            creative.setDuration(request.getDuration());
            creative.setAuditStatus(CommonStatus.VALID.getStatus());
            creative.setUserId(request.getUserId());
            creative.setUrl(request.getUrl());
            creative.setCreateTime(new Date());
            creative.setUpdateTime(creative.getCreateTime());
            return creative;
    }*/

        //其他方法
        Creative creative = creativeRepository.save(
                request.convertToEntity()
        );
        return new CreativeResponse(creative.getId(), creative.getName());
    }
}
