package com.cn.lx.mysql.dto;

import com.cn.lx.mysql.constant.OpType;
import lombok.Data;

import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Data
//模板解析
public class ParseTemplate {

    private String database;

    private Map<String,TableTemplate> tableTemplateMap = new HashMap<>();

    //对Template模板进行解析
    public static ParseTemplate parse(Template _template){

        ParseTemplate template = new ParseTemplate();
        template.setDatabase(_template.getDatabases());

        for (JsonTable table : _template.getTableList()) {
                String name = table.getTableName();
                Integer level = table.getLevel();

            TableTemplate tableTemplate = new TableTemplate();
            tableTemplate.setTableName(name);
            tableTemplate.setLevel(level.toString());
            template.tableTemplateMap.put(name,tableTemplate);

            //遍历操作类型的对应列
            Map<OpType, List<String>> opTypeFieldMap = tableTemplate.getOpTypeFieldSetMap();
            for (JsonTable.Column column : table.getInsert()) {
                getAndCreateIfNeed(
                        OpType.ADD,
                        opTypeFieldMap,
                        ArrayList::new
                ).add(column.getColumn());
            }

            for (JsonTable.Column column : table.getUpdate()) {
                getAndCreateIfNeed(
                        OpType.UPDATE,
                        opTypeFieldMap,
                        ArrayList::new
                ).add(column.getColumn());
            }

            for(JsonTable.Column column : table.getDelete()){
                getAndCreateIfNeed(
                        OpType.DELETE,
                        opTypeFieldMap,
                        ArrayList::new
                ).add(column.getColumn());
            }
        }
        return template;
    }

    //从map按K取值，如果不存在的话创建，并加入到map中
    private static <T,R> R getAndCreateIfNeed(T key, Map<T,R> map, Supplier<R> factory){

        return map.computeIfAbsent(key,k -> factory.get());
    }
}
