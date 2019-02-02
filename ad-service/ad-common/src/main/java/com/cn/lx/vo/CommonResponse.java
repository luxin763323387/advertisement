package com.cn.lx.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
//全参构造函数包括 data
public class CommonResponse<T> implements Serializable {

    private Integer code;
    private String message;
    private T data;

    public CommonResponse(Integer code, String message){
        this.code = code;
        this.message = message;
    }

 /*   //无参构造
    private CommonResponse(){}

    private CommonResponse<T>success(Integer code, String message, T data){
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setCode(0);
        commonResponse.setData(data);
        return commonResponse;
    }*/

}
