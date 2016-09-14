package com.yealink.ofweb.modules.muc.service;

import com.yealink.dataservice.client.util.Result;
import com.yealink.uc.service.modules.muc.request.SearchMucRoomRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by yl1227 on 2016/8/22.
 */
@Service
public class MucRoomService {
    @Value("${muc_request_url_prefix}")
    private String mucRequestUrlPrefix;

    public Result list(SearchMucRoomRequest searchMucRoomRequest){
        StringBuilder uri = new StringBuilder(mucRequestUrlPrefix);
        uri.append("muc_room");
        if (searchMucRoomRequest.getKeyword()!=null){
            uri.append("?keyword=");
            uri.append(searchMucRoomRequest.getKeyword());
        }
        if (searchMucRoomRequest.getPageSize()>0){
            if (uri.toString().indexOf("?")>0){
                uri.append("&pageSize=");
                uri.append(searchMucRoomRequest.getPageSize());
            }else {
                uri.append("?pageSize=");
                uri.append(searchMucRoomRequest.getPageSize());
            }
        }
        if (searchMucRoomRequest.getPageNo()>0){
            if (uri.toString().indexOf("?")>0){
                uri.append("&pageNo=");
                uri.append(searchMucRoomRequest.getPageNo());
            }else {
                uri.append("?pageNo=");
                uri.append(searchMucRoomRequest.getPageNo());
            }
        }
        if (searchMucRoomRequest.getMucRoomType()!=null){
            if (uri.toString().indexOf("?")>0){
                uri.append("&roomType=");
                uri.append(searchMucRoomRequest.getMucRoomType());
            }else {
                uri.append("?roomType=");
                uri.append(searchMucRoomRequest.getMucRoomType());
            }

        }
        if (searchMucRoomRequest.getMember()!=null){
            if (uri.toString().indexOf("?")>0){
                uri.append("&member=");
                uri.append(searchMucRoomRequest.getMember());
            }else {
                uri.append("?member=");
                uri.append(searchMucRoomRequest.getMember());
            }
        }
        if (searchMucRoomRequest.getUser()!=null){
            if (uri.toString().indexOf("?")>0){
                uri.append("&user=");
                uri.append(searchMucRoomRequest.getUser());
            }else {
                uri.append("?user=");
                uri.append(searchMucRoomRequest.getUser());
            }
        }
        RestTemplate restTemplate = new RestTemplate();
        try {
            Result result = restTemplate.getForObject(uri.toString(),Result.class);
            return result;
        }catch (Exception e){
            Result result = new Result();
            result.setRet(-1);
            result.setMsg(e.getMessage());
            return result;
        }
    }
}
