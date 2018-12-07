package com.jdlink.luckdraw.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
public class UploadController {

    @RequestMapping("/uploadPage")
    public String uploadPage(){
        return "uploadPage";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(HttpServletRequest req, @RequestParam("file")MultipartFile file, Model m) {
        try{
          //  String fileName = System.currentTimeMillis()+file.getOriginalFilename(); // 文件名设置为当前时间加上传的文件名
            String fileName =file.getOriginalFilename();
            // 获取文件的真实路径然后拼接前面的文件名，uploaded是存放文件的目录名
            String destFileName = req.getServletContext().getRealPath("")+"image"+ File.separator+fileName;
            // 初始化目录(第一次上传目录不存在需要初始化)
            File destFile = new File(destFileName);
            destFile.getParentFile().mkdirs(); // 调用父目录实例并创建目录
            file.transferTo(destFile); // 将file复制给destFile
            m.addAttribute("fileName",fileName);
        }catch (FileNotFoundException e){
            e.printStackTrace();
            return "上传失败，"+e.getMessage();
        }catch (IOException e){
            e.printStackTrace();
            return "上传失败，"+e.getMessage();
        }
        return "showImg";
    }
}
