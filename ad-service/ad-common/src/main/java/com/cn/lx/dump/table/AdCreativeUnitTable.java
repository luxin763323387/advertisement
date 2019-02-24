package com.cn.lx.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NegativeOrZero;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdCreativeUnitTable {

    private Long adId;
    private Long unitId;
}
