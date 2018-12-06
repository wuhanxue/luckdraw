package com.jdlink.luckdraw.web;

//import com.google.gson.Gson;
import com.jdlink.luckdraw.dao.LuckDrawDAO;
import com.jdlink.luckdraw.pojo.Seat;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LuckDrawController {

    @Autowired
    LuckDrawDAO luckDrawDAO;

    @GetMapping("/luckDraw")
    public String listEmployee(Model m) throws Exception {
        List<Seat> seatList= luckDrawDAO.findAll();
        m.addAttribute("seatList" ,seatList);
        return "luckDraw";  // 地址栏不会变
    }

//    @GetMapping("luckDraw")
//    public String listEmployee() throws Exception {
//        JSONObject res = new JSONObject();
//        try {
//            // 取出查询客户
//            List<Seat> seatList = luckDrawDAO.findAll();
//            // 计算最后页位置
//            JSONArray array = JSONArray.fromArray(seatList.toArray(new Seat[seatList.size()]));
//            res.put("data", array);
//            res.put("status", "success");
//            res.put("message", "参与人员数据获取成功!");
//        } catch (Exception e) {
//            e.printStackTrace();
//            res.put("status", "fail");
//            res.put("message", "参与人员数据获取失败！");
//        }
//        // 返回结果
//        return res.toString();
//    }

}
