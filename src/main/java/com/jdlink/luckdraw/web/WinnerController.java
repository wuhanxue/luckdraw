package com.jdlink.luckdraw.web;

import com.jdlink.luckdraw.dao.PrizeDAO;
import com.jdlink.luckdraw.dao.SeatDAO;
import com.jdlink.luckdraw.dao.WinnerDAO;
import com.jdlink.luckdraw.pojo.Prize;
import com.jdlink.luckdraw.pojo.Seat;
import com.jdlink.luckdraw.pojo.Winner;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    /**
     * 删除中奖者方法
     * @param m 传递参数模型
     * @param id 中奖者主键
     * @return 成功与否
     * @throws Exception 异常信息
     */
    @DeleteMapping("/winner")
    @ResponseBody
    public String deleteWinner(Model m, int id) throws Exception {
        JSONObject res = new JSONObject();
        try{
            winnerDAO.deleteById(id);
            res.put("status", "success");
            res.put("message", "删除成功");
        }
        catch (Exception e){
            e.printStackTrace();
            res.put("status", "fail");
            res.put("message", "删除失败");
        }
        return res.toString();  // 地址栏不会变
    }

    @DeleteMapping("/allWinner")
    @ResponseBody
    public String deleteAllWinner(Model m) throws Exception {
        JSONObject res = new JSONObject();
        try{
            winnerDAO.deleteAll();
            res.put("status", "success");
            res.put("message", "清空成功");
        }
        catch (Exception e){
            e.printStackTrace();
            res.put("status", "fail");
            res.put("message", "清空失败");
        }
        return res.toString();  // 地址栏不会变
    }


    /**
     * 导出(带表头字段)
     *
     * @param name
     * @param response
     * @param sqlWords
     * @return
     */
    @RequestMapping("exportExcel")
    @ResponseBody
    public String exportExcel(String name, HttpServletResponse response, String sqlWords) {
        JSONObject res = new JSONObject();
        try {
            DBUtil db = new DBUtil();
            // 设置表头
            String tableHead = "编号/姓名/桌号/座位号/奖品等级/奖品名称";
            name = "中奖者名单";   //重写文件名
            db.exportExcel2(name, response, sqlWords, tableHead);//HttpServletResponse response
            res.put("status", "success");
            res.put("message", "导出成功");
        } catch (IOException ex) {
            ex.printStackTrace();
            res.put("status", "fail");
            res.put("message", "导出失败，请重试！");
        }
        return res.toString();
    }

}
