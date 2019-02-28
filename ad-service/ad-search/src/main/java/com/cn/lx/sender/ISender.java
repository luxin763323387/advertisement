package com.cn.lx.sender;

import com.cn.lx.mysql.dto.MySqlRowData;


public interface ISender {



    void sender(MySqlRowData rowData);
}
