package com.cn.lx.mysql.dto;

import com.cn.lx.mysql.constant.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MysqlRowData {

    //表名
    private String tableName;

    //层级业务
    private String level;

    //操作类型
    private OpType opType;

    private List<Map<String, String>> fieldValueMap = new ArrayList<>();
}
