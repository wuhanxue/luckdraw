package com.jdlink.luckdraw.web;

//import com.google.gson.Gson;
import com.jdlink.luckdraw.dao.PrizeDAO;
import com.jdlink.luckdraw.dao.SeatDAO;
import com.jdlink.luckdraw.dao.WinnerDAO;
import com.jdlink.luckdraw.mapper.PrizeMapper;
import com.jdlink.luckdraw.mapper.SeatMapper;
import com.jdlink.luckdraw.mapper.WinnerMapper;
import com.jdlink.luckdraw.pojo.Prize;
import com.jdlink.luckdraw.pojo.Seat;


import com.jdlink.luckdraw.pojo.Winner;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    PrizeDAO prizeDAO;
    @Autowired
    SeatMapper seatMapper;
    @Autowired
    WinnerMapper winnerMapper;
    @Autowired
    PrizeMapper prizeMapper;


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

    /**
     * 以json形式获取所有奖品数据返回给页面
     * @return
     */
    @RequestMapping("/luckDrawSetting1")
    @ResponseBody
    public String luckDrawSetting1(){
        JSONObject res = new JSONObject();
        try {
            List<Prize> prizes = prizeDAO.findAll();   // 获取所有奖品数据
            //新建一个对象并给它赋值
            JSONArray data = JSONArray.fromObject(prizes.toArray(new Prize[prizes.size()]));
            res.put("data", data);
            res.put("status", "success");
            res.put("message", "获取数据成功");
        } catch (Exception e) {
            e.printStackTrace();
            res.put("status", "fail");
            res.put("message", "获取数据失败");
        }
        return res.toString();
    }

    /**
     * 获取所有奖品数据
     * @param m
     * @return
     */
    @GetMapping("/luckDrawSetting")
    public String luckDrawSetting(Model m){
        return "luckDrawSetting";
    }

    /**
     * 更新中奖名单数据
     * @param
     */
    @PutMapping("/updateWinner")
    public String updateWinner(String seats)throws Exception{
        JSONArray ary = JSONArray.fromObject(seats);  // 字符串转化为array数组
        List<Seat> seatList = (List<Seat>) JSONArray.toCollection(ary, Seat.class);  // array转化为seat数组
        int maxNumber = winnerMapper.maxNumber() + 1;                 // 获取这是第几次抽奖
        int prizeId = -1;
        int number = 0;
        for (Seat seat : seatList) {
            seatMapper.updateIsJoin(seat);                             // 更新是否参加下一次抽奖状态为0
            Winner winner = new Winner();
            winner.setSeatId(seatMapper.getSeatByLocation(seat).getId()); // 设置位置表ID
            prizeId = seat.getWinners().getPrizeId();
            winner.setPrizeId(prizeId);           // 设置奖品表ID
            winner.setReceive(false);                                     // 设置
            winner.setNumber(maxNumber);
            // winnerMapper.addWinner(winner);                                // 插入新中奖者
            winnerDAO.save(winner);                                     // 插入新中奖者
            number = seat.getWinners().getPrize().getNumber();         // 更新奖品剩余数
        }
        prizeMapper.updateNumber(prizeId,number);                                // 更新奖品剩余数量
        return "showWinnerList";
    }

    /**
     * 获取中奖名单数据
     * @param m
     * @return
     * @throws Exception
     */
    @GetMapping("/showWinnerList")
    public String loadWinnerList(Model m) throws Exception {
        List<Integer> winnerSeatIdList= winnerMapper.findLastWinnerSeatIdList();   //获取最近一次中奖者的seatID
        List<Seat> seatList1 = seatDAO.findAllById(winnerSeatIdList);    // 根据seatId获取seat数据
        m.addAttribute("seatList" ,seatList1);
        return "showWinnerList";  // 地址栏不会变
    }

    /**
     * 设置抽奖数据并跳转抽奖页面
     * @param prizeId
     * @param winNumber
     * @return
     */
    @GetMapping("/toLuckDraw")
    public String toLuckDraw(int prizeId, int winNumber,Model m){
        Prize prize = prizeDAO.getOne(prizeId);  // 获取奖品数据
        m.addAttribute("prize" ,prize);          // 设置奖品数据
        m.addAttribute("winNumber" ,winNumber);   // 设置获奖人数
        return "redirect:luckDraw";  // 跳转抽奖页面
    }

}
