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

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

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
        List<Seat> seatList1 = seatDAO.findAll(); // 获取所有人
//        JsonConfig jsonConfig = new JsonConfig();  //建立配置文件：防止因数据结构相互嵌套导致的死循环异常
//        jsonConfig.setIgnoreDefaultExcludes(false);  //设置默认忽略
//        jsonConfig.setExcludes(new String[]{"winners"}); // 将winners字段忽略，避免引起嵌套
//        JSONArray data = JSONArray.fromObject(seatList1,jsonConfig);  //加载配置文件
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
           // JSONArray data = JSONArray.fromObject(prizes.toArray(new Prize[prizes.size()]));
            JsonConfig jsonConfig = new JsonConfig();  //建立配置文件：防止因数据结构相互嵌套导致的死循环异常
            jsonConfig.setIgnoreDefaultExcludes(false);  //设置默认忽略
            jsonConfig.setExcludes(new String[]{"winners"}); // 将winners字段忽略，避免引起嵌套
            JSONArray data = JSONArray.fromObject(prizes,jsonConfig);  //加载配置文件
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
     * @return
     */
    @GetMapping("/luckDrawSetting")
    public String luckDrawSetting(){
        seatMapper.updateAllIsSeatWin();   // 更新按桌抽奖状态为0
        // 清空按桌抽取中奖桌号数据
        return "luckDrawSetting";
    }

    /**
     * 更新中奖名单数据
     * @param
     */
    @RequestMapping("updateWinner")
    @ResponseBody
    public void updateWinner(String seats){
        try {
            JSONArray ary = JSONArray.fromObject(seats);  // 字符串转化为array数组
            List<Seat> seatList = (List<Seat>) JSONArray.toCollection(ary, Seat.class);  // array转化为seat数组
            int maxNumber = winnerMapper.maxNumber() + 1;                 // 获取这是第几次抽奖
            int prizeId = -1;
            int number = seatList.size();   // 获取中奖人数
            int i = 0;
            for (Seat seat : seatList) {
                if(i == 0){
                    if(seat.getWinners().getNumber() == 1){
                        maxNumber--;     // 再抽一次设置为同一次抽奖
                    }
                }
                i++;
                Winner winner = new Winner();
                prizeId = seat.getWinners().getPrizeId();
                winner.setPrizeId(prizeId);                                   // 设置奖品表ID
                winner.setReceive(false);                                     // 设置
                winner.setNumber(maxNumber);
                winner.setCreationTime(new Date());
                winner.setModifyTime(new Date());
                Prize prize = prizeDAO.getById(prizeId);
                Seat seat1 = seatDAO.getByLocationIdAndTableIdAndJoin(seat.getLocationId(),seat.getTableId(),true);  // 根据座位号桌号获取未中奖员工数据
                if(seat1 != null){   // 如果该座位号上有人
                    winner.setSeatId(seat1.getId()); // 设置位置表ID
                 //   Seat seat2 = seatDAO.getOne(seat1.getId());
                    seatMapper.updateIsJoin(seat1);                             // 更新是否参加下一次抽奖状态为0,即中奖者不再参与下一次抽奖
                    if(prize != null && prize.getMode() == 0) {  // 如果奖品是按桌抽奖

                        seat1.setTableWin(true);
                        seatMapper.updateIsSeatWin(seat1);
                    }
                    winnerDAO.save(winner);                                       // 插入新中奖者
                }else{   // 如果该座位号上没人则重新在该桌其他位置上抽奖(针对按桌抽奖)
                    // 获取该桌号未中奖者list
                    List<Seat> seatList1 = seatDAO.getByTableIdAndJoin(seat.getTableId(),true);
                    if(seatList1.size() > 0) {
                        int luckNumber = (int) (Math.random() * seatList1.size());  // 随机生成中奖者
                        Seat seat2 = seatList1.get(luckNumber);
                        if(seat2 != null){   // 如果该座位号上有人
                            winner.setSeatId(seat2.getId()); // 设置位置表ID
                            winnerDAO.save(winner);                                       // 插入新中奖者
                          // Seat seat3= seatDAO.getOne(seat2.getId());
                            seatMapper.updateIsJoin(seat2);                             // 更新是否参加下一次抽奖状态为0,即中奖者不再参与下一次抽奖
                            if(prize != null && prize.getMode() == 0) {  // 如果奖品是按桌抽奖
                                seat2.setTableWin(true);
                                seatMapper.updateIsSeatWin(seat2);
                            }
                        }
                    }else {  // 如果该桌所有人均中奖则奖品不减少
                        number--;
                    }
                }
            }
            prizeMapper.updateNumber(prizeId, number);                                // 更新奖品剩余数量
            //  return "redirect:showWinnerList";
        }catch (Exception e){
            e.printStackTrace();
        }
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
        List<Integer> winnerPrizeIdList= winnerMapper.findLastWinnerPrizeIdList();   //获取最近一次中奖者的prizeID
        List<Seat> seatList1 = new ArrayList<>();
        for(Integer id : winnerSeatIdList){   // 按顺序查询中奖者名单
            Seat seat = seatDAO.getOne(id);    // 根据seatId获取seat数据
            seatList1.add(seat);
        }
        if(seatList1.size() == winnerPrizeIdList.size() && seatList1.size() == winnerSeatIdList.size())
        for(int i = 0; i < winnerSeatIdList.size(); i++) {   // 设置每个桌位号对应的奖品
           Prize prize = prizeDAO.getById(winnerPrizeIdList.get(i));
           Winner winner = new Winner();
           winner.setPrize(prize);
           seatList1.get(i).setWinners(winner);
        }
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

    /**
     * 获取随机抽奖及桌位抽奖累计中奖次数不超过limit次的桌号数组
     * @param
     */
    @RequestMapping("getLessTwoWinTableList")
    @ResponseBody
    public String getLessTwoWinTableList(){
        JSONObject res = new JSONObject();
        try {
            List<Integer> tableList = seatMapper.getTableList();   // 获取所有桌号
            int prizeTotal = seatMapper.getRandomPrizeTotal();      // 获取随机抽奖奖品总数
            int limit = prizeTotal/tableList.size();           // 每桌随机抽奖最多中奖数为：随机奖品总数/桌数取整
            List<Integer> winTableList = seatMapper.getWinTableList();   // 获取所有随机抽奖中奖的桌号
            for(int i = 0; i < winTableList.size(); i++) {  // 筛选随机中奖数超过limit的桌号
                if(Collections.frequency(winTableList, winTableList.get(i)) > limit) {  // 获取winTableList中桌号出现次数大于limit次的桌号
                    tableList.remove(winTableList.get(i));   // 将中奖次数超过limit次的桌号删除
                }
            }
            //新建一个对象并给它赋值
            JSONArray data = JSONArray.fromObject(tableList);  //加载配置文件
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
     * 获取按桌抽奖未中奖的桌号数组
     * @param
     */
    @RequestMapping("getNotWinTableList")
    @ResponseBody
    public String getNotWinTableList(){
        JSONObject res = new JSONObject();
        try {
            List<Integer> tableList = seatMapper.getNotWinTableList();   // 获取所有桌号
            //新建一个对象并给它赋值
            JSONArray data = JSONArray.fromObject(tableList);  //加载配置文件
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

}
