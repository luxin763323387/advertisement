package com.cn.lx.service;

import com.alibaba.fastjson.JSON;
import com.cn.lx.Application;
import com.cn.lx.constant.CommonStatus;
import com.cn.lx.dao.AdPlanRepository;
import com.cn.lx.dao.AdUnitRepository;
import com.cn.lx.dao.CreativeRepository;
import com.cn.lx.dao.unit_condition.AdUnitDistrictRepository;
import com.cn.lx.dao.unit_condition.AdUnitItRepository;
import com.cn.lx.dao.unit_condition.AdUnitKeywordRepository;
import com.cn.lx.dao.unit_condition.CreativeUnitRepository;
import com.cn.lx.dump.DConstant;
import com.cn.lx.dump.table.*;
import com.cn.lx.entity.AdPlan;
import com.cn.lx.entity.AdUnit;
import com.cn.lx.entity.Creative;
import com.cn.lx.entity.unit_condition.AdUnitDistrict;
import com.cn.lx.entity.unit_condition.AdUnitIt;
import com.cn.lx.entity.unit_condition.AdUnitKeyword;
import com.cn.lx.entity.unit_condition.CreativeUnit;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
//指定启动程序，web环境为none
@SpringBootTest(classes = {Application.class},
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
@Slf4j
public class DumpDataService {

    @Autowired
    private AdUnitDistrictRepository adUnitDistrictRepository;
    @Autowired
    private AdUnitItRepository adUnitItRepository;
    @Autowired
    private AdUnitKeywordRepository adUnitKeywordRepository;
    @Autowired
    private AdPlanRepository adPlanRepository;
    @Autowired
    private AdUnitRepository adUnitRepository;
    @Autowired
    private CreativeRepository creativeRepository;
    @Autowired
    private CreativeUnitRepository creativeUnitRepository;


        @Test
        public void dumpAdTableData(){
            dumpAdPlanTable(
                    String.format("%s%s", DConstant.DATA_ROOT_DIR,DConstant.AD_PLAN)
            );
            dumpAdUnitTable(
                    String.format("%s%s", DConstant.DATA_ROOT_DIR,DConstant.AD_UNIT)
            );
            dumpAdCreativeTable(
                    String.format("%s%s", DConstant.DATA_ROOT_DIR,DConstant.AD_CREATIVE)
            );
            dumpAdUnitDistrictTable(
                    String.format("%s%s", DConstant.DATA_ROOT_DIR,DConstant.AD_UNIT_DISTRICT)
            );
            dumpAdUnitKeywordTable(
                    String.format("%s%s", DConstant.DATA_ROOT_DIR,DConstant.AD_UNIT_KEYWORD)
            );
            dumpAdUnitItTable(
                    String.format("%s%s", DConstant.DATA_ROOT_DIR,DConstant.AD_UNIT_IT)
            );
        }

        //当前AdPlan数据有效的把他变成有效的文件 JSON的形式
        private void dumpAdPlanTable(String fileName){
            List<AdPlan> adPlans = adPlanRepository.findByPlanStatus(CommonStatus.VALID.getStatus());
            if(CollectionUtils.isEmpty(adPlans)){
                return;
            }
            List<AdPlanTable> adPlanTables = new ArrayList<>();
            //遍历adPlans，将adPlans转换到adPlanTables
            adPlans.forEach(p -> adPlanTables.add(
                    new AdPlanTable(
                            p.getId(),
                            p.getUserId(),
                            p.getPlanStatus(),
                            p.getStartDate(),
                            p.getEndDate()
                    )
            ));

            //通过path获取文件路径
            Path path = Paths.get(fileName);
            try(BufferedWriter writer = Files.newBufferedWriter(path)){
                for(AdPlanTable adPlanTable : adPlanTables){
                    writer.write(JSON.toJSONString(adPlanTable));
                    writer.newLine();
                }
            }catch (IOException e) {
                log.error("dumpAdPlanTables error");
            }
        }

        //将AdUnit以JSON的形式打印
        private void dumpAdUnitTable(String fileName){
            List<AdUnit> adUnits = adUnitRepository.findByUnitStatus(CommonStatus.VALID.getStatus());
            if(CollectionUtils.isEmpty(adUnits)){
                return;
            }


            List<AdUnitTable> adUnitTables = new ArrayList<>();
            //遍历adUnits 将adUnits站换到adUnitTables
            adUnits.forEach(p -> adUnitTables.add(
                    new AdUnitTable(
                            p.getId(),
                            p.getPositionType(),
                            p.getUnitStatus(),
                            p.getPlanId()
                    )
            ));

            //通过path获取文件路径
            Path path = Paths.get(fileName);
            try(BufferedWriter writer = Files.newBufferedWriter(path)){
                  for(AdUnitTable adUnitTable : adUnitTables){
                      writer.write(JSON.toJSONString(adUnitTable));
                      writer.newLine();
                  }
            } catch (IOException e) {
                log.error("dumpAdUnitTables error");
            }
        }

        //将Creative以JSON的形式打印
        private void dumpCreativeTable(String fileName){
            List<Creative> creatives = creativeRepository.findAll();
            if(CollectionUtils.isEmpty(creatives)){
                return;
            }

            List<AdCreativeTable> adCreativeTables = new ArrayList<>();
            //遍历creatives，将里面的转换到adCreativeTables
            creatives.forEach(p -> adCreativeTables.add(
                    new AdCreativeTable(
                            p.getId(),
                            p.getName(),
                            p.getType(),
                            p.getMaterialType(),
                            p.getHeight(),
                            p.getWidth(),
                            p.getAuditStatus(),
                            p.getUrl()
                    )
            ));

            Path path = Paths.get(fileName);
            try(BufferedWriter writer = Files.newBufferedWriter(path)){
                for(AdCreativeTable adCreativeTable : adCreativeTables){
                    writer.write(JSON.toJSONString(adCreativeTable));
                    writer.newLine();
                }
            } catch (IOException e) {
               log.error("dumpCreativeTables error");
            }
        }

        private void dumpAdCreativeTable (String fileName){
            List<CreativeUnit> creativeUnits = creativeUnitRepository.findAll();
            if(CollectionUtils.isEmpty(creativeUnits)){
                return;
            }
            List<AdCreativeUnitTable> adCreativeUnitTables = new ArrayList<>();
            //遍历creativeUnits，转换到adCreativeUnitTables
            creativeUnits.forEach(p -> adCreativeUnitTables.add(
                    new AdCreativeUnitTable(
                            p.getCreativeId(),
                            p.getUnitId()
                    )
            ));

            Path path = Paths.get(fileName);
            try(BufferedWriter writer = Files.newBufferedWriter(path)){
               for(AdCreativeUnitTable adCreativeUnitTable : adCreativeUnitTables){
                   writer.write(String.valueOf(adCreativeUnitTable));
                   writer.newLine();
               }
            } catch (IOException e) {
                log.error("dumpadCreativeUnitTables error");
            }
        }

        private void dumpAdUnitItTable (String fileName){
            List<AdUnitIt> adUnitIts = adUnitItRepository.findAll();
            if(CollectionUtils.isEmpty(adUnitIts)){
                return;
            }
            List<AdUnitItTable> adUnitItTables = new ArrayList<>();
            //遍历creativeUnits，转换到adCreativeUnitTables
            adUnitIts.forEach(p -> adUnitItTables.add(
                    new AdUnitItTable(
                            p.getUnitId(),
                            p.getItTag()
                    )
            ));

            Path path = Paths.get(fileName);
            try(BufferedWriter writer = Files.newBufferedWriter(path)){
                for(AdUnitItTable adUnitItTable : adUnitItTables){
                    writer.write(String.valueOf(adUnitItTable));
                    writer.newLine();
                }
            } catch (IOException e) {
                log.error("dumpAdUnitItTable error");
            }
        }

        private void dumpAdUnitDistrictTable (String fileName){
            List<AdUnitDistrict> adUnitDistricts = adUnitDistrictRepository.findAll();
            if(CollectionUtils.isEmpty(adUnitDistricts)){
                return;
            }
            List<AdUnitDistrictTable> adUnitDistrcrtTables = new ArrayList<>();
            //遍历creativeUnits，转换到adCreativeUnitTables
            adUnitDistricts.forEach(p -> adUnitDistrcrtTables.add(
                    new AdUnitDistrictTable(
                            p.getUnitId(),
                            p.getProvince(),
                            p.getCity()
                    )
            ));

            Path path = Paths.get(fileName);
            try(BufferedWriter writer = Files.newBufferedWriter(path)){
                for(AdUnitDistrictTable adUnitDistrictTable : adUnitDistrcrtTables){
                    writer.write(String.valueOf(adUnitDistrictTable));
                    writer.newLine();
                }
            } catch (IOException e) {
                log.error("dumpAdUnitDistrictTable error");
            }
        }

    private void dumpAdUnitKeywordTable (String fileName){
        List<AdUnitKeyword> adUnitKeywords = adUnitKeywordRepository.findAll();
        if(CollectionUtils.isEmpty(adUnitKeywords)){
            return;
        }
        List<AdUnitKeywordTable> adUnitKeywordTables = new ArrayList<>();
        //遍历creativeUnits，转换到adCreativeUnitTables
        adUnitKeywords.forEach(p -> adUnitKeywordTables.add(
                new AdUnitKeywordTable(
                        p.getUnitId(),
                         p.getKeyword()
                )
        ));

        Path path = Paths.get(fileName);
        try(BufferedWriter writer = Files.newBufferedWriter(path)){
            for(AdUnitKeywordTable adUnitKeywordTable : adUnitKeywordTables){
                writer.write(String.valueOf(adUnitKeywordTable));
                writer.newLine();
            }
        } catch (IOException e) {
            log.error("dumpAdUnitKeywordTable error");
        }
    }


}
