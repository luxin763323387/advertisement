package com.cn.lx.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdUnitKeywordRequest  {

    List<UnitKeyword> unitKeywords;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UnitKeyword{

        private Long unitId;
        private String keyword;
    }
}
