package com.yealink.imweb.modules.vote.controller;

import com.yealink.imweb.modules.common.constant.Constant;
import com.yealink.imweb.modules.common.interceptor.vo.UserInfo;
import com.yealink.imweb.modules.vote.request.*;
import com.yealink.imweb.modules.vote.service.VoteService;
import com.yealink.uc.common.modules.staff.entity.Staff;
import com.yealink.uc.platform.response.PageModel;
import com.yealink.uc.platform.response.Response;
import com.yealink.uc.platform.utils.DateUtil;
import com.yealink.uc.platform.utils.JSONConverter;
import com.yealink.uc.platform.utils.MessageUtil;
import com.yealink.uc.service.modules.staff.api.StaffRESTService;
import com.yealink.uc.service.modules.staff.response.FindAllStaffRESTResponse;
import com.yealink.uc.service.modules.staff.vo.StaffView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.*;

/**
 * Created by yl1240 on 2016/7/5.
 */
@Controller
@RequestMapping("/vote")
public class VoteController {
    Logger logger = LoggerFactory.getLogger(VoteController.class);

    @Autowired
    VoteService voteService;

    @Autowired
    StaffRESTService staffRESTService;

    //初始化发起投票
    @RequestMapping(value = "/preCreateVote", method = RequestMethod.GET)
    public String preCreateVote(Model model,HttpServletRequest request,HttpServletResponse response) {
        //返回到JSP页面
//      return "vote/createVote";

        //通过验证，进入准备页面的时候，查询全部用户的头像资源，映射到hashmap(这样就不用每次都迭代查询)，放入session
        //这样做就可以减少在获取投票列表的时候每次都要调用接口查询单个头像资源的问题
        UserInfo info = (UserInfo) request.getSession().getAttribute("user");
        FindAllStaffRESTResponse res =  staffRESTService.findAllByEnterprise(info.getEnterpriseId());
        if(res != null && res.getStaffs()!=null && res.getStaffs().size()!= 0){
            List<StaffView> ll = res.getStaffs();
            Iterator<StaffView> it = ll.iterator();
            HashMap<Long,StaffView> mm = new HashMap<Long,StaffView>();
            while(it.hasNext()){
                StaffView vv = it.next();
                mm.put(vv.get_id(),vv);
            }
            request.getSession().setAttribute(Constant.ALL_STAFFS_MAP,mm);
        }else{
            request.getSession().setAttribute(Constant.ALL_STAFFS_MAP,new HashMap<Long,StaffView>());
        }
        //由于已经验证，直接重定向到vote.html
        return "redirect:/html/vote/vote.html";
    }

    //发起投票：由于采集用MongoDB,事务没法保证，如果程序中间有异常，一定会产生脏数据，潜在风险，数据库选型问题。
    @ResponseBody
    @RequestMapping(value = "/createVote",
                    method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    public Response createVote(@RequestBody @Valid CreateVoteRequest cvr, BindingResult bindingResult,HttpSession httpSession,HttpServletRequest request) throws ParseException {
        if (bindingResult.hasErrors()) {
            return Response.buildResponse(false, MessageUtil.createBindingErrorMessage(bindingResult));
        }
        //参与人事件通知时访问投票Url
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +request.getContextPath() +"/vote/findVote?findType=1";
        UserInfo userInfo = (UserInfo) httpSession.getAttribute(Constant.USER);
        boolean isSuccess = voteService.createVote(cvr,userInfo,url);
        if (isSuccess) {
            return Response.buildResponse(true, "Create success");
        } else {
            return Response.buildResponse(false, "Create failed");
        }
    }

    //上传图片
    @RequestMapping(value = "/uploadPic", method = RequestMethod.POST)
    public void uploadPic(HttpServletRequest request, HttpServletResponse response,MultipartFile picFile) throws IOException {
        long time = (new Date()).getTime();
        String filePath = request.getServletContext().getRealPath("/images/vote/");
        String contextPath = request.getServletContext().getContextPath();
        String oriFileName = picFile.getOriginalFilename();
        String destFileName = oriFileName;
        String[] sepFileName=null;
        if (oriFileName != null){
            sepFileName = oriFileName.split("\\.");
            //destFileName = sepFileName[0] + "_" + time + "." + sepFileName[1];
            destFileName = UUID.randomUUID() + "." + sepFileName[1];
        }
        File file = new File(filePath, destFileName);
        if(!file.exists()){
            file.mkdirs();
        }
        try {
            picFile.transferTo(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String jsPath = "images/vote/" + destFileName;
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println(jsPath);
    }


/*    //初始化投票
    @RequestMapping(value = "/findVote", method = RequestMethod.GET)
    public String findVote(Model model, VotePageRequest pageRequest, @RequestParam(value = "findType",required = true) Long findType,HttpSession httpSession) {

        UserInfo userInfo = (UserInfo) httpSession.getAttribute(Constant.USER);
        List<FindVoteRequest> voteList = voteService.findVote(pageRequest,userInfo.getUserId(),findType);
        model.addAttribute("findType",findType);
        model.addAttribute("voteList",voteList);
        model.addAttribute("pager",pageRequest);
        return "vote/findVote";
    }*/

    //初始化投票
    @ResponseBody
    @RequestMapping(value = "/findVote",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    public Response findVote(Model model, VotePageRequest pageRequest, @RequestParam(value = "findType",required = true) Long findType,HttpSession httpSession) {
        UserInfo userInfo = (UserInfo) httpSession.getAttribute(Constant.USER);
        HashMap<Long,StaffView> allStaffsMap = (HashMap<Long, StaffView>) httpSession.getAttribute(Constant.ALL_STAFFS_MAP);
        List<FindVoteRequest> voteList = voteService.findVote(pageRequest,userInfo.getUserId(),findType,allStaffsMap);
        return new Response<>(PageModel.createPageModel(pageRequest.getPageNo(), pageRequest.getPageSize(), pageRequest.getTotal(), voteList), true);
    }

    //提交投票
    @ResponseBody
    @RequestMapping(value = "/submitVoting",
                    method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
                    produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    public Response submitVoting(@RequestBody @Valid SubmitVoteRequest svr,BindingResult bindingResult,HttpSession httpSession) throws ParseException {
        if (bindingResult.hasErrors()) {
            return Response.buildResponse(false, MessageUtil.createBindingErrorMessage(bindingResult));
        }
        UserInfo userInfo = (UserInfo) httpSession.getAttribute(Constant.USER);
        //格式化时间
        Date date = new Date();
        boolean isSuccess = voteService.submitVoting(svr,userInfo.getUserId(),date.getTime());//用户ID
        svr.setVoteDate(DateUtil.toString(date,"yyyy-MM-dd HH:mm:ss"));
        svr.setVoteStaffName(userInfo.getName());
        svr.setAvatar(userInfo.getAvatar());
        if (true) {
            return new Response<SubmitVoteRequest>(svr,true);
        } else {
            return Response.buildResponse(false, "Submit failed");
        }
    }

    //删除投票
    @RequestMapping(value = "/delVoting", method = RequestMethod.POST)
    public String delVoting(@RequestBody DelVoteRequest dvr,BindingResult bindingResult,RedirectAttributes attr) {
        if (bindingResult.hasErrors()) {
            return JSONConverter.toJSON(Response.buildResponse(false, MessageUtil.createBindingErrorMessage(bindingResult)));
        }
        boolean isSuccess  = voteService.delVote(dvr.getVoteId());
        if (isSuccess) {
            attr.addAttribute("findType",dvr.getFindType());
            return "redirect:findVote";
        } else {
            return JSONConverter.toJSON(Response.buildResponse(false, "Create failed"));
        }
    }

}
