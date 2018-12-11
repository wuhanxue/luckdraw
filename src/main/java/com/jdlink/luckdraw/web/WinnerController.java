package com.jdlink.luckdraw.web;

import com.jdlink.luckdraw.dao.WinnerDAO;
import com.jdlink.luckdraw.pojo.Winner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 中奖者控制器
 */
@Controller
public class WinnerController {
    /**
     * 中奖者数据获取模型
     */
    @Autowired
    WinnerDAO winnerDAO;

    /**
     * 列出所有中奖者
     * @param m 模型
     * @return 所有中奖者
     * @throws Exception 异常
     */
    @GetMapping("/winner")
    public String listWinner(Model m) throws Exception {
        // 筛选所有中奖者对象，做成列表
        List<Winner> winnerList = winnerDAO.findAll();
        m.addAttribute("winnerList", winnerList);
        return "configWinnerList";
    }
}
