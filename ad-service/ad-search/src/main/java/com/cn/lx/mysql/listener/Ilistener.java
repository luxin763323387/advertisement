package com.cn.lx.mysql.listener;

import com.cn.lx.mysql.dto.BinlogRowData;

public interface Ilistener {

    //不同的表可以定义不同的更新方法
    void register();

    //实现增量索引的更新
    void onEvent(BinlogRowData eventData);

}
