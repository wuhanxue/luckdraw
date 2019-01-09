package com.jdlink.luckdraw.dao;

import com.jdlink.luckdraw.pojo.Seat;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 座位数据对象获取
 */
public interface SeatDAO extends JpaRepository<Seat, Integer> {

    Seat getByLocationIdAndTableIdAndJoin(int LocationId,int tableId,boolean join);

    List<Seat> getByTableIdAndJoin(int tableId,boolean join);

}
