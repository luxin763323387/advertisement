package com.cn.lx.mysql.listener;

import com.cn.lx.mysql.constant.Constant;
import com.cn.lx.mysql.constant.OpType;
import com.cn.lx.mysql.dto.BinlogRowData;
import com.cn.lx.mysql.dto.MysqlRowData;
import com.cn.lx.mysql.dto.TableTemplate;
import com.cn.lx.sender.ISender;
import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class IncrementListener implements Ilistener {

    @Resource(name = "")
    private ISender sender;

    private final AggregationListerner aggregationListerner;

    @Autowired
    public IncrementListener(AggregationListerner aggregationListerner) {
        this.aggregationListerner = aggregationListerner;
    }

    @Override
    @PostConstruct
    public void register() {
        log.info("IncrementListener register db and table info");
        Constant.table2Db.forEach((k,v) ->
                aggregationListerner.register(v,k,this));
    }

    @Override
    public void onEvent(BinlogRowData eventData) {

        TableTemplate table = eventData.getTable();
        EventType eventType = eventData.getEventType();

        //包装成最后数据要投递的数据
        MysqlRowData rowData = new MysqlRowData();
        rowData.setTableName(table.getTableName());
        rowData.setLevel(eventData.getTable().getLevel());
        OpType opType = OpType.to(eventType);
        rowData.setOpType(opType);

        // 取出模板中该操作对应的字段列表
        List<String> fieldList = table.getOpTypeFieldSetMap().get(opType);
        if (null == fieldList) {
            log.warn("{} not support for {}", opType, table.getTableName());
            return;
        }

        for(Map<String,String> afterMap : eventData.getAfter()){
            Map<String,String> _afterMap = new HashMap<>();
            for(Map.Entry<String,String> entry: afterMap.entrySet()){

                String colName = entry.getKey();
                String colValue = entry.getValue();

                _afterMap.put(colName, colValue);
            }
            rowData.getFieldValueMap().add(_afterMap);
        }

        sender.sender(rowData);
    }
}
