package com.jdlink.luckdraw.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 自定义数据库操作(奖品)
 */
@Mapper
public interface PrizeMapper {

    /**
     * 找出最新添加的数据编号
     */
    @Select("select id from config_prize order by gmt_create_time desc limit 0,1")
    int getNewestId();

    /**
     * 根据奖品编号更新图片路径
     */
    @Update("update config_prize set prize_image_url=#{1} where id=#{0}")
    void updateImgUrl(int id,String fileName);
}
