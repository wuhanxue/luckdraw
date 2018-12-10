package com.jdlink.luckdraw.web;

import com.jdlink.luckdraw.dao.PrizeDAO;
import com.jdlink.luckdraw.mapper.PrizeMapper;
import com.jdlink.luckdraw.pojo.Prize;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 奖品控制器
 */
@Controller
public class PrizeController {

    @Autowired
    PrizeDAO prizeDAO;
    @Autowired
    PrizeMapper prizeMapper;

    @GetMapping("/drawSetting")
    public String listEmployee(Model m) throws Exception {
        List<Prize> prizeList= prizeDAO.findAll();
      for (int i=0;i<prizeList.size();i++){
          if(prizeList.get(i).getMode()!=null){
              if(prizeList.get(i).getMode()==true){
                  prizeList.get(i).setModeName("随机抽取");
              }
              else {
                  prizeList.get(i).setModeName("按桌抽取");
              }
          }


      }
        m.addAttribute("prizeList" ,prizeList);
        return "configPrize";  // 地址栏不会变
    }

    @PostMapping("prize")
    @ResponseBody
    public String addPrize(Model m,@RequestBody Prize prize) throws Exception {
          JSONObject res=new JSONObject();
         prize.setCreateTime(new Date());
         try{
             prizeDAO.save(prize);
             res.put("status", "success");
             res.put("message", "添加成功");
         }
         catch (Exception e){
             e.printStackTrace();
             res.put("status", "fail");
             res.put("message", "添加失败");
         }
//        m.addAttribute("seatList" ,seatList);
            return res.toString();  // 地址栏不会变
    }

    @DeleteMapping("prize")
    @ResponseBody
    public String deletePrize(Model m,int id) throws Exception {
        JSONObject res=new JSONObject();
        try{
            prizeDAO.deleteById(id);
            res.put("status", "success");
            res.put("message", "删除成功");
        }
        catch (Exception e){
            e.printStackTrace();
            res.put("status", "fail");
            res.put("message", "删除失败");


        }
//        m.addAttribute("seatList" ,seatList);
        return res.toString();  // 地址栏不会变
    }

    @PostMapping(value = "/saveImg")
    public String saveImg(HttpServletRequest req, @RequestParam("file")MultipartFile file, Model m) {
        JSONObject res=new JSONObject();
        try{
            String fileName = System.currentTimeMillis()+file.getOriginalFilename(); // 文件名设置为当前时间加上传的文件名
            // 获取文件的真实路径然后拼接前面的文件名，uploaded是存放文件的目录名
            String destFileName = req.getServletContext().getRealPath("")+"image"+ File.separator+fileName;
            // 初始化目录(第一次上传目录不存在需要初始化)

            File destFile = new File(destFileName);
            destFile.getParentFile().mkdirs(); // 调用父目录实例并创建目录
            file.transferTo(destFile); // 将file复制给destFile

            //将路径进行保存
            //1获取刚刚添加进去的编号
          int id=  prizeMapper.getNewestId();
          //2更新路径
            prizeMapper.updateImgUrl(id,fileName);
            res.put("status", "success");
            res.put("message", "图片添加成功");
        }catch (FileNotFoundException e){
            e.printStackTrace();
            res.put("status", "fail");

            res.put("message", "图片添加失败");
        }catch (IOException e){
            e.printStackTrace();
            return "上传失败，"+e.getMessage();
        }
        return "redirect:drawSetting";
    }
}
