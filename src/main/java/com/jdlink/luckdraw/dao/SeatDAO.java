package com.jdlink.luckdraw.dao;

import com.jdlink.luckdraw.pojo.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 座位数据对象获取
 */
public interface SeatDAO extends JpaRepository<Seat, Integer> {
}
