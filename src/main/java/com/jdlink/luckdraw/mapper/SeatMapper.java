package com.jdlink.luckdraw.mapper;

import com.jdlink.luckdraw.pojo.Seat;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SeatMapper {

    /**
     * 根据桌号和座位号更新中奖状态
     * @param seat
     */
    @Update("update main_seat set is_join=0 where seat_table_id=#{tableId} and seat_location_id=#{locationId}")
    void updateIsJoin(Seat seat);


}
