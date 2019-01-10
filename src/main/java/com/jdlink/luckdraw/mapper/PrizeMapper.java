package com.jdlink.luckdraw.mapper;

import com.jdlink.luckdraw.pojo.Prize;
import org.apache.ibatis.annotations.Delete;
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

    /**
     * 更新奖品数
     * @param id
     * @param number
     */
    @Update("update config_prize set prize_number=prize_number - #{1} where id=#{0}")
    void updateNumber(int id, int number);

    /**
     * 根据编号找出信息
     */
    @Select("select * from config_prize where id=#{id}")
    Prize getById(int id);

    /**
     * 根据编号修改信息
     */
    @Update("update config_prize set prize_name=#{name},prize_number=#{number},prize_mode=#{mode},gmt_modify_time=NOW(),prize_level=#{level} where id=#{id} ")
    void  updateById(Prize prize);

    @Delete("delete from config_prize where id=#{0};")
    void deleteById(int id);
}
