package com.jdlink.luckdraw.web;

import com.jdlink.luckdraw.dao.PrizeDAO;
import com.jdlink.luckdraw.mapper.PrizeMapper;
import com.jdlink.luckdraw.pojo.Prize;
import net.sf.json.JSONArray;
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
import java.util.ArrayList;
import java.util.Date;
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

    /**
     *
     * @param //模型
     * @return 页面
     * @throws Exception
     * 奖项页面初始化
     */
    @GetMapping("/drawSetting")
    public String listEmployee(Model m) throws Exception {
        List<Prize> prizeList= prizeDAO.findAll();
        for (int i=0;i<prizeList.size();i++){
              if(prizeList.get(i).getMode()==0){
                  prizeList.get(i).setModeName("按桌抽取");
              }else if(prizeList.get(i).getMode()==1) {
                  prizeList.get(i).setModeName("随机抽取");
              }else {
                  prizeList.get(i).setModeName("桌位抽取");
              }
      }
        m.addAttribute("prizeList" ,prizeList);
        return "configPrize";  // 地址栏不会变
    }

    /**
     *
     * @param //模型
     * @param //奖品数据结构
     * @return //字符串
     * @throws Exception
     * 奖项增加方法
     */
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

    /**
     *
     * @param //模型对象
     * @param //id
     * @return //字符串
     * @throws Exception
     * 奖项删除方法
     */
    @DeleteMapping("prize")
    @ResponseBody
    public String deletePrize(Model m,int id) throws Exception {
        JSONObject res=new JSONObject();
        try{
            prizeMapper.deleteById(id);
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

    /**
     *
     * @param//模型
     * @param//奖品数据结构
     * @param m
     * @return
     * 奖项修改方法
     */
    @PutMapping("prize")
    @ResponseBody
    public String savePrize(Model m,@RequestBody Prize prize) throws Exception{
        JSONObject res=new JSONObject();
        try{

         //  prizeMapper.updateById(prize);
           prize.setId(prize.getId());//主键设置，用作更新
           prizeDAO.save(prize);
            res.put("status", "success");
            res.put("message", "更新成功");
        }
        catch (Exception e){
            e.printStackTrace();
            res.put("status", "fail");
            res.put("message", "更新失败");
        }

        return res.toString();
    }



    /**
     * 保存图片方法
     */
    @PostMapping("saveImg")
    public String saveImg(HttpServletRequest req, @RequestParam("file")MultipartFile file,@RequestParam("prizeId")int prizeId, Model m) {
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
            int id=0;
            if(prizeId==0){
                 id=  prizeMapper.getNewestId();
            }

            if(prizeId!=0){
                 id=  prizeId;
            }
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

    /**
     *
     * @param //奖品编号
     * @param //模型对象
     * @return
     * 点击编辑按钮方法
     */
     @RequestMapping("/editPrize")
    public String adjustPrize(int id, Model m){

        try {
        Prize prize= prizeDAO.getOne(id);
//        Prize prize1=prizeDAO.getById(id);

            m.addAttribute("data",prize);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return "configPrizeEdit";

   }

    /**
     * 根据抽奖方式获取奖品数组
     * @param
     */
    @RequestMapping("getPrizeListByMode")
    @ResponseBody
    public String getPrizeListByMode(int mode){
        JSONObject res = new JSONObject();
        try {
            List<Prize> prizeList = prizeDAO.getByMode(2);   // 获取所有桌号
            List<Prize> prizeList1 = new ArrayList<>();
            for(Prize p: prizeList) {
                if(p.getNumber() > 0){
                    prizeList1.add(p);
                }
            }
            //新建一个对象并给它赋值
            JSONArray data = JSONArray.fromObject(prizeList1);  //加载配置文件
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
