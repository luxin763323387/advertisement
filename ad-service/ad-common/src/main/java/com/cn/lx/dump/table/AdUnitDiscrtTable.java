package com.cn.lx.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AdUnitDiscrtTable {

    private Long unitId;
    private String province;
    private String city;

}
