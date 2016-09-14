package com.yealink.imweb.modules.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by yl1240 on 2016/7/20.
 */
@Controller
@RequestMapping("/ckedit")
public class CkeditController {

    //ck
    @RequestMapping(value = "/uploadPic", method = RequestMethod.POST)
    public void uploadPic(HttpServletRequest request, HttpServletResponse response, String CKEditorFuncNum,
                          @RequestParam(value = "upload", required = true) MultipartFile upload , String CKEditor) throws IOException {
        String picType = "gif|jpg|png";
        String filePath = request.getServletContext().getRealPath("/images/ckedit/");
        String contextPath = request.getServletContext().getContextPath();
        String oriFileName = upload.getOriginalFilename();
        String destFileName = oriFileName;
        int lastIndex = oriFileName.lastIndexOf(".");
        //文件格式检验
        if(lastIndex == -1){
            outResult(response,"alert('文件类型不识别,缺少后缀！')");
            return;
        }
        String picSuffix = oriFileName.substring(lastIndex + 1);//文件后缀
        String fileName = oriFileName.substring(0,lastIndex);//文件名
        if(!picType.contains(picSuffix)){
            outResult(response,"alert('图片格式不支持，只支持：" + picType+"')");
            return;
        }
        //生成文件名：保证名字唯一
        long time = (new Date()).getTime();//主要用于图片回收管理
        destFileName = CKEditor + "_" + fileName + "_" + UUID.randomUUID().toString() + "_"+ time  +"." + picSuffix;

        //判断文件大小
        Long picSize = upload.getSize();//字节
        if(picSize > 1*1024*1024){
            outResult(response,"alert('超过文件大小限制，最大大小为1M，图片大小为"+picSize+"B')");
            return;
        }
        //保存文件
        File file = new File(filePath, destFileName);
        if(!file.exists()){
            file.mkdirs();
        }
        try {
            upload.transferTo(file);
        } catch (Exception e) {
            e.printStackTrace();
            outResult(response,"alert('服务器异常！')");
            return;
        }
        //显示图片：用相对路径
        String jsPath = contextPath + "/images/ckedit/" + destFileName;
        outResult(response,"window.parent.CKEDITOR.tools.callFunction(" + CKEditorFuncNum + ",'" + jsPath + "','上传成功')");
    }
    //输出结果
    private void outResult(HttpServletResponse response,String msg) throws IOException {
        response.setContentType("text/html; charset=utf-8");//一定要设置，否则返回js当普通文本不执行
        PrintWriter out = response.getWriter();
        out.println("<script type=\"text/javascript\">");
        out.println(msg);
        out.println("</script>");
    }

}
