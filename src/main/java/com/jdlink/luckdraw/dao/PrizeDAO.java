package com.jdlink.luckdraw.dao;

import com.jdlink.luckdraw.pojo.Prize;
import com.jdlink.luckdraw.pojo.Seat;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 奖品业务逻辑
 */

//@Transactional//执行修改方法时一定要添加这个注解和@Modifying注解
public interface PrizeDAO extends JpaRepository<Prize,Integer> {

    /**
     * 根据编号获取
     * @param id 奖品编号
     * @return 奖品对象
     */
    Prize getById(int id);

    /**
     * 根据中奖方式获取奖品
     * @param mode
     * @return
     */
    List<Prize> getByMode(int mode);

}
