package com.jdlink.luckdraw.web;

//import com.google.gson.Gson;
import com.jdlink.luckdraw.dao.SeatDAO;
import com.jdlink.luckdraw.dao.WinnerDAO;
import com.jdlink.luckdraw.mapper.SeatMapper;
import com.jdlink.luckdraw.mapper.WinnerMapper;
import com.jdlink.luckdraw.pojo.Seat;


import com.jdlink.luckdraw.pojo.Winner;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    SeatDAO seatDAO;
    @Autowired
    WinnerDAO winnerDAO;
    @Autowired
    SeatMapper seatMapper;
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
        List<Seat> seatList1= seatDAO.findAll(); // 获取所有人
        for(Seat seat : seatList1){              // 将不参加的人数过滤
            if(seat.isJoin())seatList.add(seat);
        }
        m.addAttribute("seatList" ,seatList);
        return "luckDraw";  // 地址栏不会变
    }

    @GetMapping("/luckDrawSetting")
    public String luckDrawSetting(){
        return "luckDrawSetting";
    }

    /**
     * 更新中奖名单数据
     * @param
     */
    @PutMapping("/updateWinner")
    public void updateWinner(String seats) throws Exception{
            JSONArray ary = JSONArray.fromObject(seats);  // 字符串转化为array数组
            List<Seat> seatList = (List<Seat>) JSONArray.toCollection(ary, Seat.class);  // array转化为seat数组
            int maxNumber = winnerMapper.maxNumber() + 1;                 // 获取这是第几次抽奖
            for (Seat seat : seatList) {
                seatMapper.updateIsJoin(seat);                             // 更新是否参加下一次抽奖状态为0
                Winner winner = new Winner();
                winner.setSeatId(seatMapper.getSeatByLocation(seat).getId()); // 设置位置表ID
                winner.setPrizeId(1);                                         // 设置奖品表ID
                winner.setReceive(false);                                     // 设置
                winner.setNumber(maxNumber);
                winnerMapper.addWinner(winner);                                // 插入新中奖者
            }
//      //  List<Integer> winnerIdList= winnerMapper.findLastWinnerIdList(); // 获取最近一次中奖者ID
//        List<Integer> winnerSeatIdList= winnerMapper.findLastWinnerSeatIdList();   //获取最近一次中奖者的seatID
////        List<Winner> winnerList = winnerDAO.findAllById(winnerIdList);       // 根据winnerId获取winner数据
//        List<Seat> seatList1 = seatDAO.findAllById(winnerSeatIdList);    // 根据seatId获取seat数据
//        m.addAttribute("seatList" ,seatList1);
            //   return "showWinnerList";              // 重定向：跳转中奖名单页面

    }

    /**
     * 获取中奖名单数据
     * @param m
     * @return
     * @throws Exception
     */
    @GetMapping("/showWinnerList")
    public String loadWinnerList(Model m) throws Exception {
        //  List<Integer> winnerIdList= winnerMapper.findLastWinnerIdList(); // 获取最近一次中奖者ID
        List<Integer> winnerSeatIdList= winnerMapper.findLastWinnerSeatIdList();   //获取最近一次中奖者的seatID
//        List<Winner> winnerList = winnerDAO.findAllById(winnerIdList);       // 根据winnerId获取winner数据
        List<Seat> seatList1 = seatDAO.findAllById(winnerSeatIdList);    // 根据seatId获取seat数据
        m.addAttribute("seatList" ,seatList1);
        return "showWinnerList";  // 地址栏不会变
    }

}
