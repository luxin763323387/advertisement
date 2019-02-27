package com.cn.lx.sender;

import com.cn.lx.mysql.dto.MysqlRowData;

public interface ISender {

    void sender(MysqlRowData rowData);
}
