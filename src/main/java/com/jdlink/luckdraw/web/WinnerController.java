package com.jdlink.luckdraw.web;

import com.jdlink.luckdraw.dao.PrizeDAO;
import com.jdlink.luckdraw.dao.SeatDAO;
import com.jdlink.luckdraw.dao.WinnerDAO;
import com.jdlink.luckdraw.pojo.Prize;
import com.jdlink.luckdraw.pojo.Seat;
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
    @Autowired
    PrizeDAO prizeDAO;
    @Autowired
    SeatDAO seatDAO;

    /**
     * 列出所有中奖者
     * @param m 模型
     * @return 所有中奖者
     * @throws Exception 异常信息
     */
    @GetMapping("/winner")
    public String listWinner(Model m) throws Exception {
        // 筛选所有中奖者对象，做成列表
        List<Winner> winnerList = winnerDAO.findAll();
        for (Winner winner : winnerList) {
            Prize prize = prizeDAO.getOne(winner.getPrizeId());
            winner.setPrize(prize);
            Seat seat = seatDAO.getOne(winner.getSeatId());
            winner.setSeat(seat);
        }
        m.addAttribute("winnerList", winnerList);
        return "configWinnerList";
    }
}
