package com.jdlink.luckdraw.mapper;

import com.jdlink.luckdraw.pojo.Seat;
import com.jdlink.luckdraw.pojo.Winner;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

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
     * 根据编号找出信息
     */
    @Select("select * from main_seat where id=#{id}")
    Seat getById(int id);

    /**
     * 根据桌号和座位号获取未中奖的员工信息
     * @param seat
     * @return
     */
    @Select("select * from main_seat where seat_table_id=#{tableId} and seat_location_id=#{locationId} and is_join=1")
    Seat getSeatByLocation(Seat seat);

    /**
     * 根据桌号获取未中奖者信息
     * @param tableId
     * @return
     */
    @Select("select * from main_seat where is_join=1 and seat_table_id=#{tableId}")
    List<Seat> getSeatsByTableId(int tableId);

    /**
     * 根据编号修改信息
     */
    @Update("update main_seat set seat_table_id=#{tableId},seat_location_id=#{locationId},employee_department=#{department},employee_name=#{name},gmt_modify_time=NOW() where id=#{id} ")
    void updateById(Seat seat);

    /**
     * 重置中奖，令所有人参与抽奖
     */
    @Update("update main_seat set is_join=1, gmt_modify_time=NOW()")
    void resetSeat();
}