package com.cn.lx.dao;

import com.cn.lx.entity.AdUnit;
import com.cn.lx.entity.Creative;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreativeRepository extends JpaRepository<Creative, Long> {
}
