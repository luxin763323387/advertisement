package com.cn.lx.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdUnitRequest {

    private Long planId;
    private String unitName;

    private Integer positionType; //位置类型
    private Long budget; //预算

    public boolean creativeValidate(){
        return planId != null && !StringUtils.isEmpty(unitName) && positionType != null && budget != null;
    }

}
