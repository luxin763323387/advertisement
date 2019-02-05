package com.cn.lx.utils;

import com.cn.lx.exception.AdException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateUtils;

import java.util.Date;

public class CommUtils {

    private static String[ ] parsePatterns = {
            "yyyy-MM-dd","yyyy/MM/dd","yyyy.MM.dd"
    };

    public static String md5(String value){
        return DigestUtils.md5Hex(value).toUpperCase();
    }

    public static Date String2Date(String data) throws AdException{

        try {
            return DateUtils.parseDate(data, parsePatterns);
        } catch (Exception e){
            throw new AdException(e.getMessage()) ;
        }
    }
}
