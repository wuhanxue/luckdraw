package com.jdlink.luckdraw.web;

import com.jdlink.luckdraw.dao.SeatDAO;
import com.jdlink.luckdraw.pojo.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 座位控制器
 */
@Controller
public class SeatController {

    /**
     * 座位数据对象获取
     */
    @Autowired
    SeatDAO seatDAO;

    /**
     * 列出所有座位
     * @param m 模型
     * @return 所有座位
     * @throws Exception 异常
     */
    @GetMapping("/seat")
    public String listSeat(Model m) throws Exception {
        List<Seat> seatList = seatDAO.findAll();
        m.addAttribute("seatList", seatList);
        return "configSeat";
    }
}
