package com.cn.lx.dao;

import com.cn.lx.entity.AdUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdUserRepository extends JpaRepository<AdUser,Long> {

    AdUser findyUsername (String username);
}
