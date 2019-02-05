package com.cn.lx.dao;

import com.cn.lx.entity.AdUnit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreativeRepository extends JpaRepository<AdUnit, Long> {

}
