package com.cn.lx.Index.district;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitDistrictObject {

    private Long unitId;
    private String province;
    private String city;

    //<String, Set<Long>>
    // province-city 用连字符 作为key
    //Set<Long> 是unitId
}
