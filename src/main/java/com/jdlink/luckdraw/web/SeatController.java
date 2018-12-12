package com.jdlink.luckdraw.web;

import com.jdlink.luckdraw.dao.SeatDAO;
import com.jdlink.luckdraw.mapper.SeatMapper;
import com.jdlink.luckdraw.pojo.Seat;
import com.jdlink.luckdraw.util.CommonUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 座位控制器
 */
@Controller
public class SeatController {

    /**
     * 座位数据对象获取DAO
     */
    @Autowired
    SeatDAO seatDAO;
    /**
     * 座位数据对象映射器
     */
    @Autowired
    SeatMapper seatMapper;

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

    /**
     * 删除座位方法
     * @param m 传递参数模型
     * @param id 座位主键
     * @return 成功与否
     * @throws Exception 异常信息
     */
    @DeleteMapping("/seat")
    @ResponseBody
    public String deleteSeat(Model m, int id) throws Exception {
        JSONObject res=new JSONObject();
        try{
            seatDAO.deleteById(id);
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

    @DeleteMapping("/allSeat")
    @ResponseBody
    public String deleteAllSeat(Model m) throws Exception {
        JSONObject res = new JSONObject();
        try {
            seatDAO.deleteAll();
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
     * 根据编号获取座位信息并跳转到编辑界面
     * @param m 数据模型
     * @param id 座位主键
     * @return 成功与否
     * @throws Exception 异常信息
     */
    @RequestMapping("/editSeat")
    public String editSeat(Model m, int id) throws Exception {
        try {
            // 通过主键获取座位对象
            Seat seat = seatDAO.getOne(id);
            // 设置数据
            m.addAttribute("seat", seat);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "configSeatEdit";
    }

    /**
     * 更新座位信息
     * @param m 数据模型参数
     * @param seat 更新后的座位信息
     * @return 成功与否
     * @throws Exception 异常信息
     */
    @PutMapping("/seat")
    @ResponseBody
    public String updateSeat(Model m, @RequestBody Seat seat) throws Exception {
        JSONObject res = new JSONObject();
        try {
            // 更新座位信息
            seatMapper.updateById(seat);
            res.put("status", "success");
            res.put("message", "更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            res.put("status", "fail");
            res.put("message", "更新失败");
        }
        return res.toString();
    }

    /**
     * 中奖重置方法
     * @param m 参数数据模型
     * @return 成功与否
     * @throws Exception 异常信息
     */
    @RequestMapping("/resetSeat")
    @ResponseBody
    public String resetSeat(Model m) throws Exception {
        JSONObject res = new JSONObject();
        try {
            // 更新座位信息
            seatMapper.resetSeat();
            res.put("status", "success");
            res.put("message", "重置成功");
        } catch (Exception e) {
            e.printStackTrace();
            res.put("status", "fail");
            res.put("message", "重置失败");
        }
        return res.toString();
    }

    /**
     * 导入Excel方法
     */
    @PostMapping("importExcel")
    public String importExcel(HttpServletRequest req, @RequestParam("file") MultipartFile file, Model m) {
        try {
            // 获取文件中的信息
            List<Object[][]> fileData = CommonUtil.getInstance().getExcelFileData(file);
            String preDept = "";
            // 桌数计数器
            int tableCount = 1;
            // 位置计数器
            int locationCount = 0;
            List<Seat> seatList = new ArrayList<>();
            // 设置第一张表的人员
            Object[][] sheetOneData = fileData.get(0);
            for (int i = 1; i < sheetOneData.length; i++) {
                Seat seat = new Seat();
                Object[] sheetOneItem = sheetOneData[i];
                // 检查姓名是否空
                if (sheetOneItem[1] != "null") {
                    seat.setName(sheetOneItem[1].toString().trim());
                    // 检查部门是否空
                    if (sheetOneItem[0] != "null") {
                        seat.setDepartment(sheetOneItem[0].toString().trim());
                        // 赋值上一个员工所属部门的名称
                        preDept = seat.getDepartment();
                    } else {
                        // 设置部门名称同上
                        seat.setDepartment(preDept);
                    }
                } else {
                    continue;
                }

                // 设置桌号和座号，座号为1-10，桌号满10加1
                int locationId = ++locationCount;
                seat.setLocationId(locationId);
                seat.setTableId(tableCount);
                if (locationCount == 10) {
                    locationCount = 0;
                    tableCount++;
                }
                // 设置数据
                seat.setJoin(true);
                seat.setCreationTime(new Date());
                seat.setModifyTime(new Date());
                seatList.add(seat);
            }

            // 插入所有数据
            seatDAO.saveAll(seatList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:seat";
    }
}
