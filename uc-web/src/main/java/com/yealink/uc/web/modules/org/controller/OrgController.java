package com.yealink.uc.web.modules.org.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.yealink.uc.common.modules.enterprise.entity.Enterprise;
import com.yealink.uc.common.modules.org.entity.Org;
import com.yealink.uc.common.modules.org.vo.OrgTreeNode;
import com.yealink.uc.common.modules.staff.entity.Staff;
import com.yealink.uc.common.modules.stafforgmapping.entity.StaffOrgMapping;
import com.yealink.uc.platform.annotations.LoginRequired;
import com.yealink.uc.platform.response.Response;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.platform.utils.MessageUtil;
import com.yealink.uc.platform.utils.SessionUtil;
import com.yealink.uc.platform.web.permission.annotations.PermissionRequired;
import com.yealink.uc.platform.web.permission.vo.Operation;
import com.yealink.uc.platform.web.permission.vo.Resource;
import com.yealink.uc.service.modules.account.vo.UCAccountView;
import com.yealink.uc.service.modules.org.vo.OrgTreeNodeView;
import com.yealink.uc.web.modules.common.constant.SessionConstant;
import com.yealink.uc.web.modules.org.request.CreateOrgRequest;
import com.yealink.uc.web.modules.org.request.DeleteOrgRequest;
import com.yealink.uc.web.modules.org.request.EditOrgRequest;
import com.yealink.uc.web.modules.org.request.MoveOrgRequest;
import com.yealink.uc.web.modules.org.request.ReIndexOrgRequest;
import com.yealink.uc.web.modules.org.service.OrgService;
import com.yealink.uc.web.modules.org.service.OrgTreeService;
import com.yealink.uc.web.modules.staff.service.QueryStaffService;
import com.yealink.uc.web.modules.staff.service.SearchStaffService;
import com.yealink.uc.web.modules.stafforgmapping.service.StaffOrgMappingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ChNan
 */
@Controller
@LoginRequired
public class OrgController {
    @Autowired
    OrgService orgService;
    @Autowired
    StaffOrgMappingService staffOrgMappingService;
    @Autowired
    SearchStaffService staffService;

    @Autowired
    QueryStaffService queryStaffService;
    @Autowired
    OrgTreeService orgTreeService;
    @RequestMapping(value = "/org/index", method = RequestMethod.GET)
    @PermissionRequired(resource = Resource.ORG, operation = Operation.READ)
    public String orgIndex(HttpServletRequest request) {
        return "org/org_index";
    }

    @RequestMapping(value = "/org/treeNodesWithForbiddenStaff", method = RequestMethod.POST)
    @ResponseBody
    public Response showTreeNodesWithForbiddenStaff(HttpServletRequest request) {
        Enterprise enterprise = SessionUtil.get(request, SessionConstant.CURRENT_ENTERPRISE);
        List<OrgTreeNode> nodes = orgTreeService.treeNodesWithForbiddenStaff(enterprise);
        return new Response<>(nodes, true);
    }

    @RequestMapping(value = "/org/showTreeNodesForIndex", method = RequestMethod.POST)
    @ResponseBody
    public Response showTreeNodesForIndex(HttpServletRequest request) {
        Enterprise enterprise = SessionUtil.get(request, SessionConstant.CURRENT_ENTERPRISE);
        List<OrgTreeNode> nodes = orgService.showOrgStaffTreeNodesForIndex(enterprise);
        return new Response<>(nodes, true);
    }

    @RequestMapping(value = "/org/showTreeNodesForMove", method = RequestMethod.POST)
    @ResponseBody
    public Response showTreeNodesForMove(HttpServletRequest request, Long moveOrgId) {
        UCAccountView ucAccount = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        List<OrgTreeNode> nodes = orgService.createTreeNodesForMove(moveOrgId, ucAccount.getEnterpriseId());
        return new Response<>(nodes, true);
    }

    @RequestMapping(value = "/org/showOrgStaffTreeNodes", method = RequestMethod.POST)
    @ResponseBody
    public Response showOrgStaffTreeNodes(HttpServletRequest request) {
        Enterprise enterprise = SessionUtil.get(request, SessionConstant.CURRENT_ENTERPRISE);
        List<OrgTreeNodeView> treeViewNodes = orgService.showOrgStaffTreeNodes(enterprise.get_id());
        List<OrgTreeNode> nodes = Lists.transform(treeViewNodes, new Function<OrgTreeNodeView, OrgTreeNode>() {
            @Override
            public OrgTreeNode apply(final OrgTreeNodeView input) {
                return DataConverter.copy(new OrgTreeNode(), input);
            }
        });
        return new Response<>(nodes, true);
    }

    @RequestMapping(value = "/org/forwardToCreate/{orgId}", method = RequestMethod.GET)
    public String forwardToCreateOrg(HttpServletRequest request, Model model, @PathVariable Long orgId) {
        UCAccountView ucAccount = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        Org org = orgService.getOrg(orgId, ucAccount.getEnterpriseId());
        model.addAttribute("parentOrgId", org.get_id());
        model.addAttribute("parentOrgName", org.getName());
        return "org/org_create";
    }

    @RequestMapping(value = "/org/forwardToEdit/{orgId}", method = RequestMethod.GET)
    public String forwardToEditOrg(HttpServletRequest request, Model model, @PathVariable Long orgId) {
        UCAccountView ucAccount = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        Org org = orgService.getOrg(orgId, ucAccount.getEnterpriseId());
        List<Long> staffIdList = staffOrgMappingService.findStaffIdListByOrg(orgId);
        List<Staff> staffList = queryStaffService.findStaffs(staffIdList, ucAccount.getEnterpriseId());
        List<StaffOrgMapping> stafforgMappings = staffOrgMappingService.findHasTitleByOrg(orgId);
        model.addAttribute("org", org);
        model.addAttribute("staffList", staffList);
        model.addAttribute("stafforgMappings", stafforgMappings);
        if (!org.getParentId().equals(OrgTreeNode.ROOT_ORG_PID)) {
            Org parentOrg = orgService.getOrg(org.getParentId(), ucAccount.getEnterpriseId());
            model.addAttribute("parentOrg", parentOrg);
        }
        return "org/org_edit";
    }

    @RequestMapping(value = "/org/forwardToMove/{orgId}", method = RequestMethod.GET)
    public String forwardToMoveOrg(HttpServletRequest request, Model model, @PathVariable Long orgId) {
        UCAccountView ucAccount = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        Org org = orgService.getOrg(orgId, ucAccount.getEnterpriseId());
        model.addAttribute("org", org);
        String moveOrgName = org.getName();
        if (org.getName().length() > 13) {
            moveOrgName = org.getName().substring(0, 10) + "...";
        }
        model.addAttribute("move_org_name", moveOrgName);
        model.addAttribute("move_org_id", orgId);
        model.addAttribute("move_type", OrgTreeNode.NODE_TYPE_ORG);
        return "org/org_move";
    }

    @RequestMapping(value = "/org/forwardToDelete", method = RequestMethod.GET)
    public String forwardToDeleteOrg() {
        return "org/org_delete";
    }

    @RequestMapping(value = "/org/create", method = RequestMethod.POST)
    @ResponseBody
    public Response createOrg(HttpServletRequest request,
                              @Valid CreateOrgRequest createOrgRequest,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Response.buildResponse(false, MessageUtil.createBindingErrorMessage(bindingResult));
        }
        Enterprise enterprise = SessionUtil.get(request, SessionConstant.CURRENT_ENTERPRISE);
        boolean isCreateSuccess = orgService.createOrg(createOrgRequest, enterprise);
        if (isCreateSuccess) {
            return Response.buildResponse(true, "创建成功");
        } else {
            return Response.buildResponse(false, "创建失败");
        }
    }

    @RequestMapping(value = "/org/edit", method = RequestMethod.POST)
    @ResponseBody
    public Response editOrg(HttpServletRequest request, @RequestBody @Valid EditOrgRequest editOrgRequest,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Response.buildResponse(false, MessageUtil.createBindingErrorMessage(bindingResult));
        }
        UCAccountView ucAccount = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        boolean isEditSuccess = orgService.editOrg(editOrgRequest, ucAccount.getEnterpriseId());
        if (isEditSuccess) {
            return Response.buildResponse(true, "编辑成功");
        } else {
            return Response.buildResponse(false, "编辑失败");
        }
    }

    @RequestMapping(value = "/org/move", method = RequestMethod.POST)
    @ResponseBody
    public Response moveOrg(HttpServletRequest request, @Valid MoveOrgRequest moveOrgRequest,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Response.buildResponse(false, MessageUtil.createBindingErrorMessage(bindingResult));
        }
        UCAccountView ucAccount = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        boolean isMoveSuccess = orgService.moveOrg(moveOrgRequest, ucAccount.getEnterpriseId());
        if (isMoveSuccess) {
            return Response.buildResponse(true, "移动成功");
        } else {
            return Response.buildResponse(false, "移动失败");
        }
    }

    @RequestMapping(value = "/org/delete", method = RequestMethod.POST)
    @ResponseBody
    public Response deleteOrg(HttpServletRequest request, @Valid DeleteOrgRequest deleteOrgRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Response.buildResponse(false, MessageUtil.createBindingErrorMessage(bindingResult));
        }
        UCAccountView ucAccount = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        boolean isDeleteSuccess = orgService.deleteOrg(deleteOrgRequest.getOrgIds(), ucAccount.getEnterpriseId());
        if (isDeleteSuccess) {
            return Response.buildResponse(true, "删除成功");
        } else {
            return Response.buildResponse(false, "删除失败");
        }
    }

    @RequestMapping(value = "/org/reIndex", method = RequestMethod.POST)
    @ResponseBody
    public Response reIndexOrg(HttpServletRequest request,
                               @RequestBody @Valid ReIndexOrgRequest reIndexOrgRequest,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Response.buildResponse(false, MessageUtil.createBindingErrorMessage(bindingResult));
        }
        UCAccountView ucAccount = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        boolean isReIndexSuccess = orgService.reIndex(reIndexOrgRequest, ucAccount.getEnterpriseId());
        if (isReIndexSuccess) {
            return Response.buildResponse(true, "排序成功");
        } else {
            return Response.buildResponse(false, "排序失败");
        }
    }
}
