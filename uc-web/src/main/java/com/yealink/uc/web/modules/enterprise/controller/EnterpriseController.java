package com.yealink.uc.web.modules.enterprise.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.yealink.uc.common.modules.enterprise.entity.Enterprise;
import com.yealink.uc.platform.annotations.LoginRequired;
import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.platform.response.Response;
import com.yealink.uc.platform.utils.MessageUtil;
import com.yealink.uc.platform.utils.SessionUtil;
import com.yealink.uc.platform.web.permission.annotations.PermissionRequired;
import com.yealink.uc.platform.web.permission.vo.Operation;
import com.yealink.uc.platform.web.permission.vo.Resource;
import com.yealink.uc.service.modules.account.vo.UCAccountView;
import com.yealink.uc.web.modules.common.constant.SessionConstant;
import com.yealink.uc.web.modules.enterprise.request.EditEnterpriseRequest;
import com.yealink.uc.web.modules.enterprise.service.EnterpriseService;
import com.yealink.uc.web.modules.enterprise.service.ImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * @author ChNan
 */

@Controller
@LoginRequired
public class EnterpriseController {
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    ImageService imageService;

    @Value("${file.service.url}")
    String fileServiceUrl;

    @RequestMapping(value = "/enterprise/forwardToEdit", method = RequestMethod.GET)
    @PermissionRequired(resource = Resource.ENTERPRISE, operation = Operation.READ)
    public String forwardToEnterpriseInfo(HttpServletRequest request, Model model) {
        Enterprise sessionEnterprise = SessionUtil.get(request, SessionConstant.CURRENT_ENTERPRISE);
        Enterprise enterprise = enterpriseService.getEnterprise(sessionEnterprise.get_id());
        model.addAttribute("enterprise", enterprise);
        model.addAttribute("fileServiceUrl", fileServiceUrl);
        return "enterprise/enterprise_edit";
    }

    @ResponseBody
    @RequestMapping(value = "/enterprise/edit", method = RequestMethod.POST)
    @PermissionRequired(resource = Resource.ENTERPRISE, operation = Operation.UPDATE)
    public Response editEnterpriseInfo(HttpServletRequest request, @Valid EditEnterpriseRequest editEnterpriseRequest, BindingResult result) throws IOException, NoSuchAlgorithmException {
        if (result.hasErrors()) {
            return Response.buildResponse(false, MessageUtil.createBindingErrorMessage(result));
        }
        if (editEnterpriseRequest.getLogo() == null) {
            if (!(request instanceof MultipartHttpServletRequest))
                throw new BusinessHandleException("企业Logo不能为空");

            MultipartFile multipartFile = ((MultipartHttpServletRequest) request).getFile("enterpriseLogo");
            if (multipartFile == null) {
                throw new BusinessHandleException("企业Logo不能为空");
            }
            UCAccountView ucAccount = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
            String logoId = imageService.uploadImage(ucAccount.getUserName(), "Enterprise", multipartFile);
            editEnterpriseRequest.setLogo(logoId);
        }
        boolean isSuccess = enterpriseService.editEnterprise(editEnterpriseRequest);

        String resultMsg = isSuccess ? "更新成功" : "更新失败";
        return Response.buildResponse(isSuccess, resultMsg);
    }


}
