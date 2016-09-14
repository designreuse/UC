package com.yealink.ofweb.modules.fileshare.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yealink.ofweb.modules.fileshare.request.PageRequest;
import com.yealink.ofweb.modules.fileshare.service.FileServerManagerService;
import com.yealink.ofweb.modules.fileshare.util.PropertiesUtils;
import com.yealink.uc.platform.annotations.LoginRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件服务器管理
 * author:pengzhiyuan
 * Created on:2016/7/27.
 */
@Controller
@RequestMapping("/fileshare/manager")
public class FileServerManagerController {

    @Autowired
    private FileServerManagerService fileServerManagerService;

    /**
     * 进入文件服务器管理页面
     * @param model
     * @return
     */
    @LoginRequired
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public String init(Model model) {
        return "fileshare/fileServerManager";
    }

    /**
     * 获取文件服务器列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/getFileServerList", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    @ResponseBody
    @LoginRequired
    public String getFileServerList(HttpServletRequest request) {
        ObjectMapper objMapper = new ObjectMapper();
        String result = "";
        try {
            List<Map<String, String>> mapList = new ArrayList<>();

//            Map<String, String> map1 = new HashMap<String, String>();
//            map1.put("jid","fs.yealink.com");
//            map1.put("host","10.3.17.122");
//            map1.put("port","3333");
//            map1.put("cpuCombined","20%");
//            map1.put("serverMemRate","70%");
//            map1.put("diskIoRate","5%");
//            map1.put("diskUsage","10%");
//            map1.put("connNum","12");
//            mapList.add(map1);

            mapList = fileServerManagerService.getUsableFileServerList();
            result = objMapper.writeValueAsString(mapList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取文件服务器会话列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/getSessionList", method = RequestMethod.GET)
    @ResponseBody
    @LoginRequired
    public String getSessionList(HttpServletRequest request, PageRequest<Map<String, String>> pageRequest) {
        String jid = request.getParameter("jid");
        if (jid == null || jid.equals("") ) {
            return "";
        }
        String result = "";
        // 测试用
//        result="[{\"id\": \"11\",\"digest\": \"11---\", \"clientSocketAddress\": \"10.3.11.11\", \"filename\": \"test.txt\", \"senddate\": \"2016-07-29T12:12:12\"}," +
//                "{\"id\": \"22\",\"digest\": \"22---\", \"clientSocketAddress\": \"10.3.11.12\", \"filename\": \"test1.txt\", \"senddate\": \"2016-07-29T13:12:12\"}," +
//                "{\"id\": \"33\", \"digest\": \"33---\",\"clientSocketAddress\": \"10.3.11.13\", \"filename\": \"test2.txt\", \"senddate\": \"2016-07-29T14:12:12\"}," +
//                "{\"id\": \"44\",\"digest\": \"44---\", \"clientSocketAddress\": \"10.3.11.14\", \"filename\": \"test3.txt\", \"senddate\": \"2016-07-29T15:12:12\"}," +
//                "{\"id\": \"55\", \"digest\": \"55---\",\"clientSocketAddress\": \"10.3.11.15\", \"filename\": \"test4.txt\", \"senddate\": \"2016-07-29T16:12:12\"}," +
//                "{\"id\": \"66\", \"digest\": \"66---\",\"clientSocketAddress\": \"10.3.11.16\", \"filename\": \"test5.txt\", \"senddate\": \"2016-07-29T17:12:12\"}]";

        ObjectMapper objMapper = new ObjectMapper();
        try {
            result = PropertiesUtils.getString(fileServerManagerService.getAllConnectionInfo(jid, pageRequest));
            if (!result.equals("")) {
                Map<String, Object> resultMap  = objMapper.readValue(result, Map.class);

                long total = Long.valueOf(String.valueOf(resultMap.get("total")));
                List<Map<String,String>> rows = (List<Map<String,String>>)resultMap.get("rows");
                // 设置总记录数
                pageRequest.setTotal(total);
                pageRequest.setRows(rows);
                // 转化为json
                result = objMapper.writeValueAsString(pageRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 关闭会话
     * @param paramJson
     * @return
     */
    @RequestMapping(value = "/closeSession", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    @ResponseBody
    @LoginRequired
    public String closeSession(HttpServletRequest request,@RequestParam String jid, @RequestBody  String paramJson) {
        ObjectMapper objMapper = new ObjectMapper();
        String result = "0"; //1-成功 0-失败
        try {
            Map<String,String> paramMap = objMapper.readValue(paramJson, Map.class);
            String digest = paramMap.get("digest");
            String response = fileServerManagerService.closeConnByDigest(jid, digest);
            if (response != null) {
                result="1";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取历史服务器状态信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/getServerStatInfo", method = RequestMethod.GET)
    @ResponseBody
    @LoginRequired
    public String getServerStatInfo(HttpServletRequest request) {
        String jid = request.getParameter("jid");
        String result = "";
        ObjectMapper objMapper = new ObjectMapper();
        int interval = 5;
        int total = 60;
        try {
            Map<String, Object> cpuMap = new HashMap<String, Object>();
            Map<String, Object> memMap = new HashMap<String, Object>();
            Map<String, Object> ioMap = new HashMap<String, Object>();
            List<Map<String, Object>> mapList = new ArrayList<>();

            if (jid == null || jid.equals("") ) {
                // 默认为0
                Float[] zeroArr = {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f};
                List<Float> zeroList = new ArrayList<Float>();
                zeroList.addAll(Arrays.asList(zeroArr));
                cpuMap.put("data", zeroList);
                memMap.put("data", zeroList);
                ioMap.put("data", zeroList);
            }
            else {
                // 根据JID获取该服务器状态信息
                List<Map<String, Object>> curMapList = fileServerManagerService.queryFileServerStat(jid, interval, total);
                // cpu、内存和磁盘IO 列表
                List<Float> cpuList = new ArrayList<Float>();
                List<Float> memList = new ArrayList<Float>();
                List<Float> ioList = new ArrayList<Float>();


//                Float[] cpuArr = {20f, 30f, 25f, 50f, 90f, 70f, 10f, 22f, 44f, 80f, 11f, 50f, 33f};
//                Float[] memArr = {70f, 80f, 90f, 86f, 66f, 70f, 40f, 60f, 44f, 80f, 39f, 77f,80f};
//                Float[] ioArr = {2.5f, 5.5f, 10f, 12.44f, 30.51f, 40.33f, 40f, 60.22f, 20.33f, 30f, 39f, 20.11f,12f};
//                cpuList.addAll(Arrays.asList(cpuArr));
//                memList.addAll(Arrays.asList(memArr));
//                ioList.addAll(Arrays.asList(ioArr));

                // 获取到历史状态信息
                if (curMapList!=null && curMapList.size()>0) {
                    for (Map curMap : curMapList) {
                        String cpuRate = PropertiesUtils.getString(curMap.get("cpuRate"));
                        String memRate = PropertiesUtils.getString(curMap.get("memRate"));
                        String ioRate = PropertiesUtils.getString(curMap.get("ioRate"));
                        if (!cpuRate.equals("")) {
                            cpuList.add(Float.valueOf(cpuRate));
                        } else {
                            cpuList.add(0f);
                        }
                        if (!memRate.equals("")) {
                            memList.add(Float.valueOf(memRate));
                        } else {
                            memList.add(0f);
                        }
                        if (!ioRate.equals("")) {
                            ioList.add(Float.valueOf(ioRate));
                        } else {
                            ioList.add(0f);
                        }
                    }
                    cpuMap.put("data", cpuList);
                    memMap.put("data", memList);
                    ioMap.put("data", ioList);
                }
            }
            mapList.add(cpuMap);
            mapList.add(memMap);
            mapList.add(ioMap);
            result = objMapper.writeValueAsString(mapList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 进入文件服务器资源监测页面
     * @param request
     * @return
     */
    @RequestMapping(value = "/initTime", method = RequestMethod.GET)
    @LoginRequired
    public String initTime(HttpServletRequest request) {
        return "fileshare/fileServerState";
    }

    /**
     * 获取服务器实时状态信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/getFileServerTimeInfo", method = RequestMethod.GET)
    @ResponseBody
    @LoginRequired
    public String getFileServerTimeInfo(HttpServletRequest request) {
        String jid = request.getParameter("jid");
        String result = "";
        ObjectMapper objMapper = new ObjectMapper();
        try {
            // 根据JID获取该服务器状态信息
            Map<String, String> stateMap = fileServerManagerService.getFileServerInfoByJid(jid);

            // cpu、内存和磁盘IO 列表
            Map<String, Float> stateNumberMap = new HashMap<String, Float>();

            String cpuRate = PropertiesUtils.getString(stateMap.get("cpuCombined"));
            String memRate = PropertiesUtils.getString(stateMap.get("serverMemRate"));
            String ioRate = PropertiesUtils.getString(stateMap.get("diskIoRate"));
            if (!cpuRate.equals("")) {
                stateNumberMap.put("cpuRate", Float.valueOf(cpuRate.replace("%","")));
            } else {
                stateNumberMap.put("cpuRate", 0f);
            }
            if (!memRate.equals("")) {
                stateNumberMap.put("memRate", Float.valueOf(memRate.replace("%","")));
            } else {
                stateNumberMap.put("memRate", 0f);
            }
            if (!ioRate.equals("")) {
                stateNumberMap.put("ioRate", Float.valueOf(ioRate.replace("%","")));
            } else {
                stateNumberMap.put("ioRate", 0f);
            }
            result = objMapper.writeValueAsString(stateNumberMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
