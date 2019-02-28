package com.cn.lx.Index;

import lombok.Getter;

@Getter
public enum DateLevel {

    LEVEL2("2","level2"),
    LEVEL3("3","level3"),
    LEVEL4("4","level4");

    private String leve;
    private String desc;

    DateLevel(String level,String desc){
        this.leve = level;
        this.desc = desc;
    }
}
