package com.jdlink.luckdraw.dao;

import com.jdlink.luckdraw.pojo.Seat;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 座位数据对象获取
 */
public interface SeatDAO extends JpaRepository<Seat, Integer> {
}
