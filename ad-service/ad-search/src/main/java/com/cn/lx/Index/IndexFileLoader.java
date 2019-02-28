package com.cn.lx.Index;

import com.alibaba.fastjson.JSON;
import com.cn.lx.dump.DConstant;
import com.cn.lx.dump.table.*;
import com.cn.lx.handler.AdLevelDataHandler;
import com.cn.lx.mysql.constant.OpType;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Component
@DependsOn("datatable")
public class IndexFileLoader {

    @PostConstruct
    //init方法 实现全量所有的加载
    public void init(){

        List<String> adPlanStrings = loadDumpData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR,
                        DConstant.AD_PLAN)
        );
        //遍历adPlanStrings，在便利的过程中调用AdLevelDataHandler
        adPlanStrings.forEach(p -> AdLevelDataHandler.handleLevel2(
                JSON.parseObject(p, AdPlanTable.class),
                OpType.ADD
        ));

        List<String> adCreativeStrings = loadDumpData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR,
                        DConstant.AD_CREATIVE)
        );
        adCreativeStrings.forEach(p -> AdLevelDataHandler.handleLevel2(
                JSON.parseObject(p, AdCreativeTable.class),
                OpType.ADD
        ));

        List<String> adUnitStrings = loadDumpData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR,
                        DConstant.AD_UNIT)
        );
        adUnitStrings.forEach(p -> AdLevelDataHandler.handleLevel3(
                JSON.parseObject(p, AdUnitTable.class),
                OpType.ADD
        ));

        List<String> adCreativeUnitStrings = loadDumpData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR,
                        DConstant.AD_CREATIVE_UNIT)
        );
        adCreativeUnitStrings.forEach(p -> AdLevelDataHandler.handleLevel3(
                JSON.parseObject(p, AdCreativeUnitTable.class),
                OpType.ADD
        ));

        List<String> adUnitDistrictStrings = loadDumpData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR,
                        DConstant.AD_UNIT_DISTRICT)
        );
        adUnitDistrictStrings.forEach(p -> AdLevelDataHandler.handleLevel4(
                JSON.parseObject(p, AdUnitDistrictTable.class),
                OpType.ADD
        ));

        List<String> adUnitItStrings = loadDumpData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR,
                        DConstant.AD_UNIT_IT)
        );
        adUnitItStrings.forEach(p -> AdLevelDataHandler.handleLevel4(
                JSON.parseObject(p, AdUnitItTable.class),
                OpType.ADD
        ));

        List<String> adUnitKeywordStrings = loadDumpData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR,
                        DConstant.AD_UNIT_KEYWORD)
        );
        adUnitKeywordStrings.forEach(p -> AdLevelDataHandler.handleLevel4(
                JSON.parseObject(p, AdUnitKeywordTable.class),
                OpType.ADD
        ));
    }



    //将写入到文件中的数据读取出来
    private List<String> loadDumpData(String fileName){
        try(BufferedReader br = Files.newBufferedReader(
                Paths.get(fileName)
        )){
            return br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
