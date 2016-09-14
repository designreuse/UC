package com.yealink.uc.web.modules.staff.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.yealink.uc.common.modules.account.entity.Account;
import com.yealink.uc.common.modules.account.vo.AccountType;
import com.yealink.uc.common.modules.businessaccount.entity.BusinessAccount;
import com.yealink.uc.common.modules.businessaccount.vo.BusinessType;
import com.yealink.uc.common.modules.enterprise.entity.Enterprise;
import com.yealink.uc.common.modules.org.vo.OrgTreeNode;
import com.yealink.uc.common.modules.phone.Phone;
import com.yealink.uc.common.modules.staff.entity.Staff;
import com.yealink.uc.platform.annotations.LoginRequired;
import com.yealink.uc.platform.crypto.AuthFactory;
import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.platform.exception.FileExportException;
import com.yealink.uc.platform.response.Response;
import com.yealink.uc.platform.utils.ExcelFileExport;
import com.yealink.uc.platform.utils.MessageUtil;
import com.yealink.uc.platform.utils.SessionUtil;
import com.yealink.uc.platform.web.permission.annotations.PermissionRequired;
import com.yealink.uc.platform.web.permission.vo.Operation;
import com.yealink.uc.platform.web.permission.vo.Resource;
import com.yealink.uc.web.modules.account.service.AccountService;
import com.yealink.uc.web.modules.businessaccount.BusinessAccountService;
import com.yealink.uc.web.modules.common.constant.SessionConstant;
import com.yealink.uc.web.modules.enterprise.service.ImageService;
import com.yealink.uc.web.modules.org.service.OrgService;
import com.yealink.uc.web.modules.phone.PhoneService;
import com.yealink.uc.web.modules.security.role.service.RoleService;
import com.yealink.uc.web.modules.staff.entity.ExportStaff;
import com.yealink.uc.web.modules.staff.request.CreateStaffRequest;
import com.yealink.uc.web.modules.staff.request.EditStaffRequest;
import com.yealink.uc.web.modules.staff.request.MoveStaffRequest;
import com.yealink.uc.web.modules.staff.request.ReindexStaffRequest;
import com.yealink.uc.web.modules.staff.request.ReleaseOrgMappingRequest;
import com.yealink.uc.web.modules.staff.request.SearchStaffRequest;
import com.yealink.uc.web.modules.staff.response.FindStaffDetailResponse;
import com.yealink.uc.web.modules.staff.response.SearchStaffResponse;
import com.yealink.uc.web.modules.staff.response.StaffExtension;
import com.yealink.uc.web.modules.staff.response.UCAccountDetail;
import com.yealink.uc.web.modules.staff.service.CreateStaffService;
import com.yealink.uc.web.modules.staff.service.EditStaffService;
import com.yealink.uc.web.modules.staff.service.ExportStaffService;
import com.yealink.uc.web.modules.staff.service.ImportStaffService;
import com.yealink.uc.web.modules.staff.service.MailStaffService;
import com.yealink.uc.web.modules.staff.service.QueryStaffService;
import com.yealink.uc.web.modules.staff.service.SearchStaffService;
import com.yealink.uc.web.modules.staff.service.UpdateStaffStatusService;
import com.yealink.uc.web.modules.stafforgmapping.service.StaffOrgMappingService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * @author ChNan
 */
@Controller
@LoginRequired
public class StaffController {
    Logger logger = LoggerFactory.getLogger(StaffController.class);
    @Autowired
    SearchStaffService searchStaffService;
    @Autowired
    private CreateStaffService createStaffService;
    @Autowired
    private EditStaffService editStaffService;
    @Autowired
    private ImportStaffService importStaffService;
    @Autowired
    private UpdateStaffStatusService updateStaffStatusService;
    @Autowired
    private QueryStaffService queryStaffService;
    @Autowired
    private ExportStaffService exportStaffService;
    @Autowired
    OrgService orgService;
    @Autowired
    RoleService roleService;
    @Autowired
    private MailStaffService mailStaffService;
    @Autowired
    StaffOrgMappingService staffOrgMappingService;

    @Value("${file.service.url}")
    String fileServiceUrl;

    @Autowired
    ImageService imageService;
    @Autowired
    PhoneService phoneService;
    @Autowired
    AccountService accountService;
    @Autowired
    BusinessAccountService businessAccountService;

    @RequestMapping(value = "/staff/forwardToCreate", method = RequestMethod.GET)
    @PermissionRequired(resource = Resource.STAFF, operation = Operation.READ)
    public String forwardToCreateStaff(HttpServletRequest request, Model model) {
        Enterprise enterprise = SessionUtil.get(request, SessionConstant.CURRENT_ENTERPRISE);
        model.addAttribute("roles", roleService.findAllByEnterprise(enterprise.get_id()));
        return "staff/staff_create";
    }

    @RequestMapping(value = "/staff/forwardToEdit/{staffId}", method = RequestMethod.GET)
    @PermissionRequired(resource = Resource.STAFF, operation = Operation.READ)
    public String forwardToEditStaff(HttpServletRequest request, Model model, @PathVariable Long staffId) {
        Staff staff = queryStaffService.getStaff(staffId, getLoginEnterpriseId(request));
        BusinessAccount extensionAccount = businessAccountService.getByStaffAndType(staffId, BusinessType.EXTENSION.getCode());
        BusinessAccount imAccount = businessAccountService.getByStaffAndType(staffId, BusinessType.IM.getCode());
        Account ucAccount = accountService.getByStaffIdAndType(staffId, AccountType.PRIMARY.getCode());
        StaffExtension staffExtension = new StaffExtension();
        if (extensionAccount != null) {
            staffExtension = new StaffExtension(extensionAccount.getUsername(), extensionAccount.getEncryptedPassword(), extensionAccount.getPinCode(), extensionAccount.getStatus());
        }
        Phone phone = phoneService.getByStaff(staffId);

        FindStaffDetailResponse response = new FindStaffDetailResponse(
            new UCAccountDetail(ucAccount.getUsername(), AuthFactory.decryptPassword(imAccount.getEncryptedPassword()), ucAccount.getPinCode())
            , staff, phone, staffExtension
        );
        model.addAttribute("staffDetail", response);
        model.addAttribute("currentOrgs", staffOrgMappingService.findStaffOrgMapping(staffId, getLoginEnterpriseId(request)));
        model.addAttribute("fileServiceUrl", fileServiceUrl);
        return "staff/staff_edit";
    }

    @RequestMapping(value = "/staff/forwardToMove/{staffId}", method = RequestMethod.GET)
    @PermissionRequired(resource = Resource.STAFF, operation = Operation.READ)
    public String forwardToMoveStaff(HttpServletRequest request, Model model, @PathVariable Long staffId) {
        model.addAttribute("staff", queryStaffService.getStaff(staffId, getLoginEnterpriseId(request)));
        model.addAttribute("currentOrgs", staffOrgMappingService.findStaffOrgMapping(staffId, getLoginEnterpriseId(request)));
        return "staff/staff_move";
    }

    @ResponseBody
    @RequestMapping(value = "/staff/create", method = RequestMethod.POST)
    @PermissionRequired(resource = Resource.STAFF, operation = Operation.CREATE)
    public Response createStaff(HttpServletRequest request, @Valid CreateStaffRequest createStaffRequest, BindingResult bindingResult) throws IOException, NoSuchAlgorithmException {
        if (bindingResult.hasErrors()) {
            return Response.buildResponse(false, MessageUtil.createBindingErrorMessage(bindingResult));
        }
        Enterprise enterprise = SessionUtil.get(request, SessionConstant.CURRENT_ENTERPRISE);
        boolean isCreateSuccess = createStaffService.createStaff(createStaffRequest, enterprise);
        if (isCreateSuccess) {
            if (request instanceof MultipartHttpServletRequest) {
                MultipartFile multipartFile = ((MultipartHttpServletRequest) request).getFile("avatar");
                if (multipartFile != null) {
                    imageService.uploadAvatar(createStaffRequest.getUsername(), enterprise.getDomain(), multipartFile);
                }
            }
            return Response.buildResponse(true, "创建成功");
        } else {
            return Response.buildResponse(false, "创建失败");
        }
    }


    @ResponseBody
    @RequestMapping(value = "/staff/edit", method = RequestMethod.POST)
    @PermissionRequired(resource = Resource.STAFF, operation = Operation.UPDATE)
    public Response editStaff(HttpServletRequest request, @Valid @ModelAttribute EditStaffRequest editStaffRequest, BindingResult bindingResult) throws IOException, NoSuchAlgorithmException {
        if (bindingResult.hasErrors()) {
            return Response.buildResponse(false, MessageUtil.createBindingErrorMessage(bindingResult));
        }
        Enterprise enterprise = SessionUtil.get(request, SessionConstant.CURRENT_ENTERPRISE);
        boolean isEditSuccess = editStaffService.editStaff(editStaffRequest, enterprise.get_id());
        if (isEditSuccess) {
            if (request instanceof MultipartHttpServletRequest) {
                MultipartFile multipartFile = ((MultipartHttpServletRequest) request).getFile("avatar");
                if (multipartFile != null) {
                    imageService.uploadAvatar(editStaffRequest.getUsername(), enterprise.getDomain(), multipartFile);
                }
            }
            return Response.buildResponse(true, "编辑成功");
        } else {
            return Response.buildResponse(false, "编辑失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/staff/move", method = RequestMethod.POST)
    @PermissionRequired(resource = Resource.STAFF, operation = Operation.UPDATE)
    public Response moveStaff(HttpServletRequest request, @Valid MoveStaffRequest moveStaffRequest,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Response.buildResponse(false, MessageUtil.createBindingErrorMessage(bindingResult));
        }
        boolean isMoveSuccess = staffOrgMappingService.moveStaff(moveStaffRequest, getLoginEnterpriseId(request));
        if (isMoveSuccess) {
            return Response.buildResponse(true, "移动成功");
        } else {
            return Response.buildResponse(false, "移动失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/staff/delete", method = RequestMethod.POST)
    @PermissionRequired(resource = Resource.STAFF, operation = Operation.DELETE)
    public Response deleteStaff(HttpServletRequest request, @RequestParam List<Long> staffIds) {
        boolean isDeleteSuccess = updateStaffStatusService.deleteStaff(staffIds, getLoginEnterpriseId(request));
        if (isDeleteSuccess) {
            return Response.buildResponse(true, "删除成功");
        } else {
            return Response.buildResponse(false, "删除失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/staff/lock", method = RequestMethod.POST)
    @PermissionRequired(resource = Resource.STAFF, operation = Operation.UPDATE)
    public Response lockStaff(HttpServletRequest request, @RequestParam Long staffId) {
        boolean isLockSuccess = updateStaffStatusService.lockStaff(staffId, getLoginEnterpriseId(request));
        if (isLockSuccess) {
            return Response.buildResponse(true, "锁定成功");
        } else {
            return Response.buildResponse(false, "锁定失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/staff/unlock", method = RequestMethod.POST)
    @PermissionRequired(resource = Resource.STAFF, operation = Operation.UPDATE)
    public Response unlockStaff(HttpServletRequest request, @RequestParam Long staffId) {
        boolean isUnlockSuccess = updateStaffStatusService.unlockStaff(staffId, getLoginEnterpriseId(request));
        if (isUnlockSuccess) {
            return Response.buildResponse(true, "解锁成功");
        } else {

            return Response.buildResponse(false, "解锁失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/staff/recycle", method = RequestMethod.POST)
    @PermissionRequired(resource = Resource.STAFF, operation = Operation.UPDATE)
    public Response recycleStaff(HttpServletRequest request, @RequestParam Long staffId) {
        boolean isDeleteSuccess = updateStaffStatusService.recycleStaff(staffId, getLoginEnterpriseId(request));
        if (isDeleteSuccess) {
            return Response.buildResponse(true, "回收成功");
        } else {

            return Response.buildResponse(false, "回收失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/staff/revert", method = RequestMethod.POST)
    @PermissionRequired(resource = Resource.STAFF, operation = Operation.UPDATE)
    public Response revertStaff(HttpServletRequest request, @RequestParam List<Long> staffIds) {
        boolean isRevertSuccess = updateStaffStatusService.recoverStaffs(staffIds, getLoginEnterpriseId(request));
        if (isRevertSuccess) {
            return Response.buildResponse(true, "还原成功");
        } else {

            return Response.buildResponse(false, "还原失败");
        }
    }


    @ResponseBody
    @RequestMapping(value = "/staff/releaseOrgMapping", method = RequestMethod.POST)
    @PermissionRequired(resource = Resource.STAFF, operation = Operation.UPDATE)
    public Response releaseOrgMapping(HttpServletRequest request, @Valid ReleaseOrgMappingRequest releaseOrgMappingRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Response.buildResponse(false, MessageUtil.createBindingErrorMessage(bindingResult));
        }
        boolean isRevertSuccess = staffOrgMappingService.releaseOrgMapping(releaseOrgMappingRequest, getLoginEnterpriseId(request));
        if (isRevertSuccess) {
            return Response.buildResponse(true, "排序成功");
        } else {

            return Response.buildResponse(false, "排序失败");
        }
    }

    @RequestMapping(value = "/staff/forwardToImport")
    @PermissionRequired(resource = Resource.STAFF, operation = Operation.READ)
    public String forwardToImport(HttpServletRequest request) {
        return "staff/staff_import";
    }

    @RequestMapping(value = "/staff/import", method = RequestMethod.POST)
    @PermissionRequired(resource = Resource.STAFF, operation = Operation.CREATE)
    public String importStaff(HttpServletRequest request, @RequestParam(value = "importStaffFile") MultipartFile importStaffFile, Model model) {
        Enterprise loginedEnterprise = SessionUtil.get(request, SessionConstant.CURRENT_ENTERPRISE);
        try {
            Map<String, Object> importResult = importStaffService.importStaff(importStaffFile, loginedEnterprise);
            model.addAttribute("success", importResult.get("成功"));
            model.addAttribute("message", importResult.get("message"));
            model.addAttribute("errorImportStaffList", importResult.get("errorImportStaffList"));
        } catch (BusinessHandleException e) {
            model.addAttribute("success", false);
            model.addAttribute("message", e.getMessage());
        }
        return "staff/staff_import_result";
    }

    @RequestMapping(value = "/staff/export")
    @PermissionRequired(resource = Resource.STAFF, operation = Operation.READ)
    public void exportStaff(HttpServletRequest request, HttpServletResponse response) {
        String[] IMPORT_FILE_TABLE_HEAD = {"姓名", "用户名", "所属部门", "手机", "邮箱"};
        Enterprise loginEnterprise = SessionUtil.get(request, SessionConstant.CURRENT_ENTERPRISE);
        List<ExportStaff> exportStaffList = exportStaffService.exportStaffs(loginEnterprise.get_id());
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/csv;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;fileName="
            + loginEnterprise.getDomain() + ".xls");
        OutputStream os = null;
        try {
            List<String> titleList = Arrays.asList(IMPORT_FILE_TABLE_HEAD);
            List<List<String>> rowColData = new ArrayList<List<String>>();
            for (ExportStaff exportStaff : exportStaffList) {
                List<String> rowData = new ArrayList<String>();
                rowData.add(exportStaff.getName());
                rowData.add(exportStaff.getUsername());
                rowData.add(exportStaff.getOrg());
                rowData.add(exportStaff.getMobilePhone());
                rowData.add(exportStaff.getEmail());
                rowColData.add(rowData);
            }
            os = response.getOutputStream();
            new ExcelFileExport(os).export("导出人员列表", titleList, rowColData);
        } catch (IOException | FileExportException e) {
            logger.error(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/staff/reIndex", method = RequestMethod.POST)
    @PermissionRequired(resource = Resource.STAFF, operation = Operation.UPDATE)
    public Response reIndex(HttpServletRequest httpServletRequest, @Valid ReindexStaffRequest staffReindexRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Response.buildResponse(false, MessageUtil.createBindingErrorMessage(bindingResult));
        }
        boolean isReindexSuccess = staffOrgMappingService.reindexStaff(staffReindexRequest, getLoginEnterpriseId(httpServletRequest));
        if (isReindexSuccess) {
            return Response.buildResponse(true, "排序成功");
        } else {
            return Response.buildResponse(false, "排序失败");
        }
    }

    @RequestMapping(value = "/staff/downloadImportTemplate", method = RequestMethod.GET)
    @PermissionRequired(resource = Resource.STAFF, operation = Operation.CREATE)
    public void downloadOrgTemplate(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getSession().getServletContext().getRealPath("/");
        String templatePath = path + File.separator + "templates" + File.separator + "ImportTemplate.xls";
        File file = new File(templatePath);
        String fileName = file.getName();
        OutputStream os;
        InputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(templatePath));
            byte[] buffer = new byte[1024];
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Content_Length", String.valueOf(file.length()));
            os = response.getOutputStream();
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }

    @RequestMapping(value = "/staff/search", method = RequestMethod.POST)
    @PermissionRequired(resource = Resource.STAFF, operation = Operation.READ)
    @ResponseBody
    public SearchStaffResponse listNormalStaffs(HttpServletRequest request, @RequestBody @Valid SearchStaffRequest searchStaffRequest) {
        Enterprise loginedEnterprise = SessionUtil.get(request, SessionConstant.CURRENT_ENTERPRISE);
        SearchStaffResponse response = searchStaffService.searchStaffList(searchStaffRequest, loginedEnterprise.get_id());

        if (!searchStaffRequest.getOrgIdDetail().equals(OrgTreeNode.FORBIDDEN_ORG_ID)) {
            response.setOrg(orgService.getOrg(searchStaffRequest.getOrgIdDetail(), loginedEnterprise.get_id()));
        } else {
           response.setOrg(orgService.createForbiddenOrg());
        }
        return response;
    }


    @ResponseBody
    @RequestMapping(value = "/staff/batchMail", method = RequestMethod.POST)
    @PermissionRequired(resource = Resource.STAFF, operation = Operation.DELETE)
    public Response batchMail(HttpServletRequest request, @RequestParam List<Long> staffIds) {
        boolean isMailSuccess = mailStaffService.batchMail(staffIds, getLoginEnterpriseId(request));
        if (isMailSuccess) {
            return Response.buildResponse(true, "发送成功");
        } else {
            return Response.buildResponse(false, "发送失败");
        }
    }

    private Long getLoginEnterpriseId(HttpServletRequest request) {
        return ((Enterprise) SessionUtil.get(request, SessionConstant.CURRENT_ENTERPRISE)).get_id();
    }
}
