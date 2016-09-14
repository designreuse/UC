package com.yealink.ofweb.modules.fileshare.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yealink.ofweb.modules.fileshare.service.FileServerManagerService;
import com.yealink.ofweb.modules.fileshare.util.Constants;
import com.yealink.ofweb.modules.fileshare.util.DateUtil;
import com.yealink.uc.platform.annotations.LoginRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 文件共享统计
 * author:pengzhiyuan
 * Created on:2016/8/5.
 */
@Controller
@RequestMapping("/fileshare/stat")
public class FileShareStatController {

    @Autowired
    private FileServerManagerService fileServerManagerService;
    @Autowired
    private MessageSource messageSource;

    /**
     * 进入统计页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    @LoginRequired
    public String init(Model model) {
        // 设置页面发送时间 起始和结束时间，默认是最近7天
        String nowDate = DateUtil.getNowDate();
        String beginDate = DateUtil.getDateAddDate(-7);
        model.addAttribute("nowDate", nowDate);
        model.addAttribute("beginDate", beginDate);
        return "fileshare/fileShareStat";
    }

    /**
     * 统计
     * @param request
     * @return
     */
    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    @ResponseBody
    @LoginRequired
    public String stat(HttpServletRequest request) {
        String result = "";
        String fileType = request.getParameter("fileType");
        String beginSendDate = request.getParameter("beginSendDate");
        String endSendDate = request.getParameter("endSendDate");
        ObjectMapper objMapper = new ObjectMapper();
        List<Map<String, Object>> resultMapList = new ArrayList<Map<String, Object>>();
        try {
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("fileType", fileType);
            paramMap.put("beginSendDate", beginSendDate);
            paramMap.put("endSendDate", endSendDate);
            Map<String, Long> countMap = fileServerManagerService.statFileSendRecord(paramMap);

            // 选择了文件类别
            if (fileType != null && !fileType.equals("")) {
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("name", getNameByFileType(fileType));
                resultMap.put("value", countMap.get(fileType));
                resultMapList.add(resultMap);
            } else {
                Iterator<Map.Entry<String, Long>> it = countMap.entrySet().iterator();
                Map<String, Object> resultMap = null;
                while (it.hasNext()) {
                    resultMap = new HashMap<>();
                    Map.Entry<String, Long> entry = it.next();
                    resultMap.put("name", getNameByFileType(entry.getKey()));
                    resultMap.put("value", entry.getValue());
                    resultMapList.add(resultMap);
                }
            }
            result = objMapper.writeValueAsString(resultMapList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据文件类别获取其名称
     * @param fileType
     * @return
     */
    private String getNameByFileType(String fileType) {
        String name = "";
        if (Constants.FILE_TYPE_ONLINE.equals(fileType)) {
            name=messageSource.getMessage("fileshare.label.stat.onlinefile",null, Locale.getDefault());
//            name = "在线文件";
        } else if (Constants.FILE_TYPE_OFFLINE.equals(fileType)) {
            name=messageSource.getMessage("fileshare.label.stat.offlinefile",null, Locale.getDefault());
//            name = "离线文件";
        } else if (Constants.FILE_TYPE_GROUP.equals(fileType)) {
            name=messageSource.getMessage("fileshare.label.stat.groupfile",null, Locale.getDefault());
//            name = "群文件";
        } else if (Constants.FILE_TYPE_CHAT.equals(fileType)) {
            name=messageSource.getMessage("fileshare.label.stat.msgfile",null, Locale.getDefault());
//            name = "消息图片";
        } else if (Constants.FILE_TYPE_AVATAR.equals(fileType)) {
            name=messageSource.getMessage("fileshare.label.stat.avatarfile",null, Locale.getDefault());
//            name = "头像文件";
        }
        return name;
    }

}
