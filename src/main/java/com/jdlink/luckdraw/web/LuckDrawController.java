package com.jdlink.luckdraw.web;

import com.jdlink.luckdraw.dao.LuckDrawDAO;
import com.jdlink.luckdraw.pojo.Seat;
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

        List<Seat> employeeList= luckDrawDAO.findAll();
        m.addAttribute("employeeList" , employeeList);
        return "luckDraw";  // 地址栏不会变
    }
}
