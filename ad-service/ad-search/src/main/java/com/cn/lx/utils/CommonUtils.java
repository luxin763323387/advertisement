package com.cn.lx.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;


@Slf4j
public class CommonUtils {
    //当map中k不存在的时候可以new一个新的value
    public static <K,V>V getOrCreate(K key, Map<K,V> map, Supplier<V> factory){

      return map.computeIfAbsent(key, k -> factory.get());
    }

    public static String stringConcat(String...args){
        StringBuilder result =new StringBuilder();
        for(String arg : args){
            result.append(arg);
            result.append("-");
        }
        result.deleteCharAt(result.length()-1);
        return result.toString();
    }


        //    Write-----------
        //    WriteRowsEventData
        //
        //    {tableId=109, includedColumns={0, 1, 2, 3, 4, 5, 6, 7}, rows=[
        //    [1, 11, plan, 1, Fri Mar 01 04:02:21 CST 2019, Fri Mar 01 04:02:28 CST 2019, Thu Feb 28 08:00:00 CST 2019, Thu Feb 28 08:00:00 CST 2019]
        //]}
    public static Date parseStringData(String dataString){

        try{
            DateFormat dataFormat = new SimpleDateFormat(
                    "EEE MMM dd HH:mm:ss zzz yyy",
                    Locale.US
            );

            return DateUtils.addHours(
                    dataFormat.parse(dataString),
                    -8
            );
        }catch (ParseException e) {
            log.error("parseStringData error: {}", dataString);
            return null;
        }
    }
}
