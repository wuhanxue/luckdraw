package com.jdlink.luckdraw.mapper;

import com.jdlink.luckdraw.pojo.Winner;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * 自定义中奖者数据库操作
 */
@Mapper
public interface WinnerMapper {

    /**
     * 新增中奖者
     * @param winner
     */
    @Insert("insert into main_winning (seat_id,prize_id,is_receive) values (#{seatId},#{prizeId},#{receive})")
    void addWinner(Winner winner);
}
