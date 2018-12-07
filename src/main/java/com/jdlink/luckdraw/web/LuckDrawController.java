package com.jdlink.luckdraw.web;

//import com.google.gson.Gson;
import com.jdlink.luckdraw.dao.SeatDAO;
import com.jdlink.luckdraw.mapper.SeatMapper;
import com.jdlink.luckdraw.mapper.WinnerMapper;
import com.jdlink.luckdraw.pojo.Seat;


import com.jdlink.luckdraw.pojo.Winner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽奖页面的后台方法
 */
@Controller
public class LuckDrawController {

    /**
     * 操作seat类的接口
     */
    @Autowired SeatDAO seatDAO;
    @Autowired SeatMapper seatMapper;
    @Autowired
    WinnerMapper winnerMapper;

    /**
     * 获取所有未中奖过的人的数据列表
     * @param m
     * @return
     * @throws Exception
     */
    @GetMapping("/luckDraw")
    public String listEmployee(Model m) throws Exception {
        List<Seat> seatList = new ArrayList<>();
        List<Seat> seatList1= seatDAO.findAll();
        for(Seat seat : seatList1){// 将不参加的人数过滤
            if(seat.isJoin())seatList.add(seat);
        }
        m.addAttribute("seatList" ,seatList);
        return "luckDraw";  // 地址栏不会变
    }

    /**
     * 更新中奖名单数据
     * @param seat
     * @throws Exception
     */
    @PutMapping("/luckDraw")
    public void updateCategory(@RequestBody Seat seat) throws Exception {
        seatMapper.updateIsJoin(seat); // 更新是否参加下一次抽奖状态为0
        Winner winner = new Winner();
        winner.setSeatId(seatMapper.getSeatByLocation(seat).getId()); // 设置位置表ID
        winner.setPrizeId(1);                                         // 设置奖品表ID
        winner.setReceive(false);                                     // 设置
        winnerMapper.addWinner(winner);                                 // 插入新中奖者
    }

    @GetMapping("/showWinnerList")
    public String showWinnerList(){
        return "showWinnerList";
    }

}
