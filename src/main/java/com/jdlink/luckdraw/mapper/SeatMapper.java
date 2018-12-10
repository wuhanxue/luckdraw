package com.jdlink.luckdraw.mapper;

import com.jdlink.luckdraw.pojo.Seat;
import com.jdlink.luckdraw.pojo.Winner;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 自定义作为座位数据库操作
 */
@Mapper
public interface SeatMapper {

    /**
     * 根据桌号和座位号更新中奖状态
     * @param seat
     */
    @Update("update main_seat set is_join=0 where seat_table_id=#{tableId} and seat_location_id=#{locationId}")
    void updateIsJoin(Seat seat);

    /**
     * 根据桌号和座位号获取员工信息
     * @param seat
     * @return
     */
    @Select("select * from main_seat where seat_table_id=#{tableId} and seat_location_id=#{locationId}")
    Seat getSeatByLocation(Seat seat);

}