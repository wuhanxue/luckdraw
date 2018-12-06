package com.jdlink.luckdraw.dao;

import com.jdlink.luckdraw.pojo.Prize;
import com.jdlink.luckdraw.pojo.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 奖品业务逻辑
 */
public interface PrizeDAO extends JpaRepository<Prize,Integer> {
}
