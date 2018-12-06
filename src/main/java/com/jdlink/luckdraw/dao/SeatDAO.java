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
//@Repository
public interface SeatDAO extends JpaRepository<Seat, Integer> {

//    @Modifying
//    @Query(value = "update main_seat set is_join=0 where seat_table_id=:tableId and seat_location_id=:locationId")
//    void updateIsJoin(@Param("tableId")int tableId, @Param("locationId")int locationId);
}
