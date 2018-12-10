package com.jdlink.luckdraw.web;

import com.jdlink.luckdraw.dao.SeatDAO;
import com.jdlink.luckdraw.pojo.Seat;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
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

    /**
     * 增加座位
     * @param m 模型
     * @param seat 座位对象
     * @return 成功与否
     */
    @PostMapping("/seat")
    @ResponseBody
    public String addSeat(Model m, @RequestBody Seat seat) throws Exception {
        JSONObject res = new JSONObject();
        try {
            // 获取当前时间
            Date now = new Date();
            // 设置当前时间
            seat.setCreationTime(now);
            // 设置修改时间
            seat.setModifyTime(now);
            // 设置参加下一次抽奖
            seat.setJoin(true);
            // 增加座位
            seatDAO.save(seat);
            res.put("status", "success");
            res.put("message", "添加成功");
        } catch (Exception e) {
            // 异常处理
            e.printStackTrace();
            res.put("status", "fail");
            res.put("message", "添加失败");
        }
        return res.toString();  // 地址栏不会变
    }
}
