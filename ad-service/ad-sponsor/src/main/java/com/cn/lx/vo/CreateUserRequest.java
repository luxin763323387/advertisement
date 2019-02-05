package com.cn.lx.vo;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**创建一个用户需要提供CreateUserRequest的请求对象*/
public class CreateUserRequest {

    private String username;

    /**如果不为空就会返回 ->ture,,否则返回false*/
    public boolean validate(){
        return !StringUtils.isEmpty(username);
    }
}
