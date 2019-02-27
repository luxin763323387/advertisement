package com.cn.lx.service;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import org.hibernate.sql.Delete;
import org.hibernate.sql.Update;

public class BinlogServiceTest {

    //        Write-----------
    //        WriteRowsEventData{tableId=229, includedColumns={0, 1, 2}, rows=[
    //        [12, 12, 奔驰]
    //    ]}
    //        Update ---------
    //        UpdateRowsEventData{tableId=229, includedColumnsBeforeUpdate={0, 1, 2}, includedColumns={0, 1, 2}, rows=[
    //            {before=[12, 12, 奔驰], after=[12, 12, 奥迪]}
    //    ]}
    //        Delete-----------
    //        DeleteRowsEventData{tableId=229, includedColumns={0, 1, 2}, rows=[
    //        [12, 12, 奥迪]
    //    ]}
    public static void main(String[]args) throws Exception{
        BinaryLogClient client = new BinaryLogClient(
                "192.168.0.104",
                3306,
                "root",
                "123456"
        );
        //client.setBinlogFilename("binlog.000003");
        client.registerEventListener(event -> {
            EventData data = event.getData();
            if(data instanceof UpdateRowsEventData){
                System.out.println("Update ---------");
                System.out.println(data.toString());
            }else if(data instanceof WriteRowsEventData){
                System.out.println("Write-----------");
                System.out.println(data.toString());
            }else if(data instanceof DeleteRowsEventData){
                System.out.println("Delete-----------");
                System.out.println(data.toString());
            }
        });
        client.connect();
    }
}
