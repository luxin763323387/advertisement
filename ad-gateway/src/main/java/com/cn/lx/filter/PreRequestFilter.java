package com.cn.lx.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PreRequestFilter extends ZuulFilter {

    @Override
    //定义ilter类型(4种)
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    //表示filter执行的顺序 越小表示权重越大
    public int filterOrder() {
        return 0;
    }

    @Override
    //true表示执行  false表示不执行
    public boolean shouldFilter() {
        return true;
    }

    @Override
    //filter 执行的具体操作
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.set("startTime",System.currentTimeMillis());
        return null;
    }
}
