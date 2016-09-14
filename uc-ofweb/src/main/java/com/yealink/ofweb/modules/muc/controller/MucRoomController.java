package com.yealink.ofweb.modules.muc.controller;

import com.yealink.dataservice.client.util.Result;
import com.yealink.uc.platform.annotations.LoginRequired;
import com.yealink.uc.platform.response.Response;
import com.yealink.uc.service.modules.muc.api.MucRESTService;
import com.yealink.uc.service.modules.muc.request.SearchMucRoomRequest;
import com.yealink.uc.service.modules.muc.response.ListMucRoomResponse;
import com.yealink.uc.service.modules.org.api.OrgRESTService;
import com.yealink.uc.service.modules.org.response.ListOrgTreesRESTResponse;
import com.yealink.uc.service.modules.org.vo.OrgTreeNodeView;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sean on 2016/7/26.
 */
@Controller
public class MucRoomController {
    @Value("${muc_request_url_prefix}")
    private String mucRequestUrlPrefix;
    private String orgRequest;
    @Autowired
    OrgRESTService orgRESTService;
    @Autowired
    MucRESTService mucRESTService;

    @LoginRequired
    @RequestMapping(value="/muc/index",method = RequestMethod.GET)
    public String goMain(){
        return "muc/muc_main";
    }

    @LoginRequired
    @RequestMapping(value="/muc/mucRoom",method = RequestMethod.GET)
    @ResponseBody
    public ListMucRoomResponse list(HttpServletRequest request){
        SearchMucRoomRequest searchBean = new SearchMucRoomRequest();
        searchBean.setKeyword(request.getParameter("keyword"));
        if (request.getParameter("pageSize")!=null){
            searchBean.setPageSize(Integer.parseInt(request.getParameter("pageSize")));
        }
        if (request.getParameter("pageNo")!=null){
            searchBean.setPageNo(Integer.parseInt(request.getParameter("pageNo")));
        }
        if (request.getParameter("roomType")!=null && !request.getParameter("roomType").equals("")){
            searchBean.setMucRoomType(Integer.parseInt(request.getParameter("roomType")));
        }
//        if (request.getParameter("member")!=null){
            searchBean.setMember(1);
//        }
        if (request.getParameter("user")!=null){
            searchBean.setUser(Long.parseLong(request.getParameter("user")));
        }
        ListMucRoomResponse listMucRoomResponse = mucRESTService.listMuc(searchBean);
        return listMucRoomResponse;
    }

    /**
     * 修改群组信息
     * @param request
     * @return
     */
    @RequestMapping(value = "muc/mucRoom",method = RequestMethod.PUT)
    @ResponseBody
    @LoginRequired
    public Result edit(HttpServletRequest request){
        StringBuilder uri=new StringBuilder(mucRequestUrlPrefix);
        uri.append("muc_room/");

        String queryStr = request.getQueryString();
        String[] paramArr = queryStr.split("&");
        String jid = paramArr[0].substring(paramArr[0].indexOf("=")+1);
        String caption = paramArr[1].substring(paramArr[1].indexOf("=")+1);
        String desc = paramArr[2].substring(paramArr[2].indexOf("=")+1);

        Map<String,Object> param = new HashMap<>();
        param.put("jid",jid);
        param.put("caption",caption);
        param.put("desc",desc);

        RestTemplate template = new RestTemplate();
        try {
            HttpEntity<Map> paramMap = new HttpEntity<Map>(param,null);
            ResponseEntity<Result> responseEntity  =  template.exchange(uri.toString(), HttpMethod.PUT,paramMap,Result.class);
            return responseEntity.getBody();
        }catch (Exception e){
            Result ret=new Result();
            ret.setRet(-1);
            ret.setMsg(e.getMessage());
            return ret;
        }
    }

    /**删除群组
     * @param request
     * @return
     */
    @RequestMapping(value = "/muc/mucRoom",method = RequestMethod.DELETE)
    @ResponseBody
    @LoginRequired
    public Result delete(HttpServletRequest request){
        StringBuilder uri=new StringBuilder(mucRequestUrlPrefix);
        uri.append("muc_room/");
        String param = request.getQueryString();
        if (param != null){
            String roomJid = request.getQueryString().substring(param.indexOf("=")+1);
            uri.append(roomJid);
        }

        RestTemplate template=new RestTemplate();
        try {
            HttpMessageConverterExtractor<Result> responseExtractor =
                    new HttpMessageConverterExtractor<Result>(Result.class, template.getMessageConverters());
            Result result = template.execute(uri.toString(), HttpMethod.DELETE, null,responseExtractor);
            return result;
        }catch(Exception e){
            Result ret=new Result();
            ret.setRet(-2);
            ret.setMsg(e.getMessage());
            return ret;
        }
    }

    /**
     * 获取群成员列表
     * @param request
     * @return
     */
    @RequestMapping(value = "muc/mucMember" ,method = RequestMethod.GET)
    @ResponseBody
    @LoginRequired
    public Result listRoomMembers(HttpServletRequest request){
        StringBuilder uri=new StringBuilder(mucRequestUrlPrefix);
        uri.append("muc_member/");
        uri.append(request.getParameter("roomJid"));

        RestTemplate template = new RestTemplate();
        try {
            Result result = template.getForObject(uri.toString(),Result.class);
            return result;
        }catch (Exception e){
            Result result = new Result();
            result.setRet(-1);
            result.setMsg(e.getMessage());
            return result;
        }
    }

    /**
     * 添加群成员
     * @param request
     * @return
     */
    @RequestMapping(value = "muc/mucMember",method = RequestMethod.POST)
    @ResponseBody
    @LoginRequired
    public Result addRoomMembers(HttpServletRequest request){
        StringBuilder uri = new StringBuilder(mucRequestUrlPrefix);
        uri.append("muc_member/");
        String membersIdStr = request.getParameter("membersId");
        if (!membersIdStr.equals("")){
            String[] membersId = membersIdStr.split(",");

            RestTemplate template = new RestTemplate();
            Result result = new Result();
            StringBuilder message = new StringBuilder();

            for (String id : membersId){
                //param {"roomJID":"jdjdjd@chat.yealink.com","member":"zhangsan@yealink.com","role":"member"}
                String memberId = id.substring(0,id.indexOf("_"));
                Map<String,Object> param = new HashMap<>();
                param.put("roomJID",request.getParameter("roomJid"));
                param.put("member",memberId);
                param.put("role",request.getParameter("role"));
                JSONObject jsonObj = JSONObject.fromObject(param);
                HttpEntity<Map> paramMap = new HttpEntity<Map>(jsonObj,null);
                ResponseEntity<Result> responseEntity  =  template.exchange(uri.toString(), HttpMethod.POST,paramMap,Result.class);

                Result ret = responseEntity.getBody();
                if (ret.getRet()<0){
                    message.append(id);
                    message.append(",");
                }
            }
            if (message.toString().equals("")){
                result.setRet(1);
            }else {
                message.append("以上用户ID添加失败,请重试...");
                result.setMsg(message.toString());
                result.setRet(-1);
            }
            return result;
        }else {
            return new Result();
        }

    }

    /**
     * 修改成员角色
     * @param request
     * @return
     */
    @RequestMapping(value = "muc/mucMember",method = RequestMethod.PUT)
    @ResponseBody
    @LoginRequired
    public Result modifyMember(HttpServletRequest request){
        StringBuilder uri = new StringBuilder(mucRequestUrlPrefix);
        uri.append("muc_member/");
        // muc/mucMember?roomJid=*** & member=*** & role=***
        String paramStr = request.getQueryString();
        String[] paramArr = paramStr.split("&");
        String roomJid = paramArr[0].substring(paramArr[0].indexOf("=")+1);
        String memberId = paramArr[1].substring(paramArr[1].indexOf("=")+1);
        String role = paramArr[2].substring(paramArr[2].indexOf("=")+1);
        Map<String,Object> params = new HashMap<>();
        params.put("roomJID",roomJid);
        params.put("member",memberId);
        params.put("role",role);

        RestTemplate template = new RestTemplate();
        try{
            HttpEntity<Map> paramMap = new HttpEntity<Map>(params,null);
            ResponseEntity<Result> responseEntity = template.exchange(uri.toString(),HttpMethod.PUT,paramMap,Result.class);
            return responseEntity.getBody();
        }catch (Exception e){
            Result result = new Result();
            result.setRet(-1);
            result.setMsg(e.getMessage());
            return result;
        }
    }

    /**
     * 删除群成员
     * @param request
     * @return
     */
    @RequestMapping(value = "muc/mucMember",method = RequestMethod.DELETE)
    @ResponseBody
    @LoginRequired
    public Result deleteMember(HttpServletRequest request){
        StringBuilder uri=new StringBuilder(mucRequestUrlPrefix);
        uri.append("muc_member/");
        uri.append(request.getParameter("roomJid"));
        uri.append("/");
        uri.append(request.getParameter("memberJid"));

        RestTemplate template=new RestTemplate();
        try {
            HttpMessageConverterExtractor<Result> responseExtractor =
                    new HttpMessageConverterExtractor<Result>(Result.class, template.getMessageConverters());
            Result result = template.execute(uri.toString(), HttpMethod.DELETE, null,responseExtractor);
            return result;
        }catch(Exception e){
            Result ret=new Result();
            ret.setRet(-2);
            ret.setMsg(e.getMessage());
            return ret;
        }
    }

    /**
     * 获取组织架构
     * @param request
     * @return
     */
    @RequestMapping(value = "muc/createTreeNodes", method = RequestMethod.POST)
    @ResponseBody
    @LoginRequired
    public Response showStaff(HttpServletRequest request) {
        ListOrgTreesRESTResponse showOrgTreesResponse = orgRESTService.listOrgTrees(1L);
        List<OrgTreeNodeView> notesList = showOrgTreesResponse.getOrgTreeNodeList();
        return new Response<>(notesList, true);
    }
}
