package com.cn.lx.mysql.dto;

import com.cn.lx.mysql.constant.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableTemplate {

    private String tableName;
    private String level;

    /**操作类型，字段顺序*/
    private Map<OpType, List<String>> opTypeFieldSetMap = new HashMap<>();

    /**
     * 字段索引  ->z字段名
     * */
    private Map<Integer,String> posMap = new HashMap<>();
}
