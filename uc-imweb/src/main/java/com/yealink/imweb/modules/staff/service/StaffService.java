package com.yealink.imweb.modules.staff.service;

import com.yealink.imweb.modules.staff.vo.MucRoomTreeNodeView;
import com.yealink.uc.service.modules.muc.api.MucRESTService;
import com.yealink.uc.service.modules.muc.request.SearchMucRoomRequest;
import com.yealink.uc.service.modules.muc.response.ListMucRoomResponse;
import com.yealink.uc.service.modules.muc.vo.MucRoomMember;
import com.yealink.uc.service.modules.muc.vo.MucRoomView;
import com.yealink.uc.service.modules.org.api.OrgRESTService;
import com.yealink.uc.service.modules.org.response.ListOrgTreesRESTResponse;
import com.yealink.uc.service.modules.org.vo.OrgTreeNodeView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yl1240 on 2016/7/8.
 */
@Service
public class StaffService {

    Logger logger = LoggerFactory.getLogger(StaffService.class);

    @Autowired
    OrgRESTService orgRESTService;

    @Autowired
    MucRESTService mucRESTService;


    public List<OrgTreeNodeView> showOrgStaffTreeNodes(Long enterpriseId) {
        logger.info("Go in to showOrgStaffTreeNodes");
        ListOrgTreesRESTResponse listOrgTreesResponse = orgRESTService.listOrgTrees(enterpriseId);
        return listOrgTreesResponse.getOrgTreeNodeList();
    }

    public List<MucRoomTreeNodeView> showRoomStaffTreeNodes(Long userId) {
        SearchMucRoomRequest searchMucRoomRequest = new SearchMucRoomRequest();
        searchMucRoomRequest.setUser(userId);
        searchMucRoomRequest.setMember(1);
        searchMucRoomRequest.setPageSize(10000);//取出所有记录
        ListMucRoomResponse listRoomTreesResponse = mucRESTService.listMuc(searchMucRoomRequest);
        List<MucRoomView> MucRoomView =  listRoomTreesResponse.getMucRoomViews();
        return convertToResult(MucRoomView);
    }

    public List<MucRoomTreeNodeView> convertToResult(List<MucRoomView> mucRoomViewList){
        List<MucRoomTreeNodeView> treeNodeViewList;
        if(mucRoomViewList == null || mucRoomViewList.size() == 0){
            treeNodeViewList = null;
        }
        //树列表
        treeNodeViewList = new ArrayList<>();
        //处理群
        Map<Integer,String> mucRoomType = new HashMap<>();
        int index = 0;
        for(int i = 0;i<mucRoomViewList.size();i++){
            MucRoomTreeNodeView ucRoomTreeNodeView = new MucRoomTreeNodeView();
            String parentType = "room";
            MucRoomView mucRoomView = mucRoomViewList.get(i);
            Long roomId  = mucRoomView.getRoomId();
            Integer roomType  = mucRoomView.getRoomType();
            //根节点
            if(!mucRoomType.containsKey(roomType)){
                index = index + 1;
                MucRoomTreeNodeView rootNode = new MucRoomTreeNodeView();
                rootNode.setId(Long.valueOf(roomType));
                //群组或讨论群
                if(rootNode.getId() == 0){
                    rootNode.setName("讨论组");
                }else {
                    rootNode.setName("群组");
                }
                //rootNode.setName("类型"+roomType);
                rootNode.setType("root");
                rootNode.setPid(null);
                rootNode.setParentType(null);
                rootNode.setIndex(index);
                rootNode.setParent(true);
                mucRoomType.put(roomType,"");
                treeNodeViewList.add(rootNode);
                mucRoomType.put(roomType,"");
            }
            //父节点
            ucRoomTreeNodeView.setId(roomId);
            ucRoomTreeNodeView.setName(mucRoomView.getRoomNaturalName());
            ucRoomTreeNodeView.setType(parentType);
            ucRoomTreeNodeView.setParent(true);
            ucRoomTreeNodeView.setIndex(i+1);
            ucRoomTreeNodeView.setPid(Long.valueOf(roomType));
            ucRoomTreeNodeView.setParentType("root");
            treeNodeViewList.add(ucRoomTreeNodeView);
            //处理成员
            List<MucRoomMember> mucRoomMemberList = mucRoomView.getMembers();
            //增加根节点
            if(mucRoomMemberList != null && mucRoomMemberList.size() > 0){
                for(int j = 0;j<mucRoomMemberList.size();j++){
                    String childType = "staff";
                    MucRoomMember mucRoomMember = mucRoomMemberList.get(j);
                    ucRoomTreeNodeView = new MucRoomTreeNodeView();
                    //子节点
                    Long userId  =mucRoomMember.getUserId();
                    ucRoomTreeNodeView.setId(userId);
                    ucRoomTreeNodeView.setName(mucRoomMember.getName());
                    ucRoomTreeNodeView.setType(childType);
                    ucRoomTreeNodeView.setIndex(j+1);
                    //父节点信息
                    ucRoomTreeNodeView.setParent(false);
                    ucRoomTreeNodeView.setPid(roomId);
                    ucRoomTreeNodeView.setParentType(parentType);
                    treeNodeViewList.add(ucRoomTreeNodeView);
                }
            }
        }
        return treeNodeViewList;
    }
}
