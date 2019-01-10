package com.jdlink.luckdraw.mapper;

import com.jdlink.luckdraw.pojo.Winner;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 自定义中奖者数据库操作
 */
@Mapper
public interface WinnerMapper {

    /**
     * 新增中奖者
     * @param winner
     */
    @Insert("insert into main_winning (seat_id,prize_id,is_receive,win_number,gmt_create_time,gmt_modify_time) values (#{seatId},#{prizeId},#{receive},#{number},NOW(),NOW())")
    void addWinner(Winner winner);

    /**
     * 获取这是第几次抽奖
     * @return
     */
    @Select("select COUNT(DISTINCT(win_number)) from main_winning")
    int maxNumber();

    /**
     * 获取最近一次进行的中奖名单
     * @return
     */
    @Select("select id from main_winning where win_number = (select COUNT(DISTINCT(win_number)) from main_winning)")
    List<Integer> findLastWinnerIdList();

    /**
     * 获取最近一次进行的中奖者的seatID
     * @return
     */
    @Select("select seat_id from main_winning where win_number = (select COUNT(DISTINCT(win_number)) from main_winning) ORDER BY gmt_create_time DESC,id DESC")
    List<Integer> findLastWinnerSeatIdList();

    /**
     * 获取最近一次进行的中奖者的prizeID
     * @return
     */
    @Select("select prize_id from main_winning where win_number = (select COUNT(DISTINCT(win_number)) from main_winning) ORDER BY gmt_create_time DESC,id DESC")
    List<Integer> findLastWinnerPrizeIdList();
}