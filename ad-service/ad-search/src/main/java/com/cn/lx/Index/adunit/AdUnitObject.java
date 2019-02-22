package com.cn.lx.Index.adunit;

import com.cn.lx.Index.adplan.AdPlanObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdUnitObject {

    private Long unitId;
    private Integer unitStatus;
    private Integer positionType;
    private Long planId;

    //为了方便获取索引对象
    private AdPlanObject adPlanObject;

    //实现update方法
    public void update(AdUnitObject newUnitObject){
        if(null != newUnitObject.getUnitId()){
            this.unitId = newUnitObject.getUnitId();
        }
        if(null != newUnitObject.getPlanId()){
            this.planId = newUnitObject.getPlanId();
        }
        if(null != newUnitObject.getPositionType()){
            this.positionType = newUnitObject.getPositionType();
        }
        if(null != newUnitObject.getUnitStatus()){
            this.unitStatus = newUnitObject.getUnitStatus();
        }
        if(null != newUnitObject.getAdPlanObject()){
            this.adPlanObject = newUnitObject.getAdPlanObject();
        }
    }
}
