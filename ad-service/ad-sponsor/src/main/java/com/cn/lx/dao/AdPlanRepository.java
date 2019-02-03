package com.cn.lx.dao;

import com.cn.lx.entity.AdPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdPlanRepository extends JpaRepository<AdPlan, Long> {

    AdPlan findByIdAndUserId(Long id, Long userId);

}
