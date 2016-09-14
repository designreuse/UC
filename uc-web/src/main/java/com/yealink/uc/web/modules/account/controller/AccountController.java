package com.yealink.uc.web.modules.account.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.yealink.uc.common.modules.account.entity.Account;
import com.yealink.uc.common.modules.account.vo.AccountType;
import com.yealink.uc.common.modules.enterprise.entity.Enterprise;
import com.yealink.uc.platform.response.Response;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.platform.utils.MessageUtil;
import com.yealink.uc.platform.utils.SessionUtil;
import com.yealink.uc.service.modules.account.api.AccountRESTService;
import com.yealink.uc.service.modules.account.request.EditAccountPasswordRESTRequest;
import com.yealink.uc.service.modules.account.vo.UCAccountView;
import com.yealink.uc.web.modules.account.request.BindEmailRequest;
import com.yealink.uc.web.modules.account.request.EditAccountPasswordRequest;
import com.yealink.uc.web.modules.account.request.EditAccountRequest;
import com.yealink.uc.web.modules.account.request.ResetPasswordRequest;
import com.yealink.uc.web.modules.account.service.AccountService;
import com.yealink.uc.web.modules.common.annotation.CaptchaRequired;
import com.yealink.uc.platform.annotations.LoginRequired;
import com.yealink.uc.web.modules.common.constant.SessionConstant;
import com.yealink.uc.web.modules.enterprise.service.EnterpriseService;
import com.yealink.uc.web.modules.login.service.CSRFTokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ChNan
 */
@Controller
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private CSRFTokenService cSRFTokenService;
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private AccountRESTService accountRESTService;

    @RequestMapping(value = "/account/show", method = RequestMethod.GET)
    @LoginRequired
    public String showAccountInfo(HttpServletRequest request, Model model) {
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        Account account = accountService.getAccount(ucAccountView.get_id());
        Enterprise enterprise = enterpriseService.getEnterprise(account.getEnterpriseId());
        model.addAttribute("account", account);
        model.addAttribute("enterprise", enterprise);
        return "account/account_show";
    }

    @RequestMapping(value = "/account/edit", method = RequestMethod.POST)
    @ResponseBody
    @LoginRequired
    public Response editAccount(HttpServletRequest request, @Valid @ModelAttribute EditAccountRequest editAccountRequest,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Response.buildResponse(false, MessageUtil.createBindingErrorMessage(bindingResult));
        }
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        Account account = accountService.getAccount(ucAccountView.get_id());
        Enterprise enterprise = SessionUtil.get(request, SessionConstant.CURRENT_ENTERPRISE);
        boolean isEditSuccess = accountService.editAccount(editAccountRequest, account, enterprise);
        if (isEditSuccess) {
            return Response.buildResponse(true, "Edit success");
        } else {
            return Response.buildResponse(false, "Edit failed");
        }
    }

    @RequestMapping(value = "/account/password/forwardToEdit", method = RequestMethod.GET)
    @LoginRequired
    public String forwardToEditPassword() {
        return "account/account_password_edit";
    }

    @RequestMapping(value = "/account/password/edit", method = RequestMethod.POST)
    @ResponseBody
    @LoginRequired
    public Response editPassword(HttpServletRequest request, @Valid EditAccountPasswordRequest editAccountPasswordRequest,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Response.buildResponse(false, MessageUtil.createBindingErrorMessage(bindingResult));
        }
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        EditAccountPasswordRESTRequest editAccountPasswordRESTRequest = DataConverter.copy(new EditAccountPasswordRESTRequest(ucAccountView.get_id()), editAccountPasswordRequest);
        boolean success = accountRESTService.editPassword(editAccountPasswordRESTRequest);
        if (success) {
            return Response.buildResponse(true, "Edit success");
        } else {
            return Response.buildResponse(false, "Edit failed");
        }
    }

    @RequestMapping(value = "/account/password/forwardToForgot", method = RequestMethod.GET)
    public String forwardToForgotPassword(HttpServletRequest request) {
        cSRFTokenService.setCsrfToken(request, null);
        return "account/account_password_forgot";
    }

    @RequestMapping(value = "/account/password/submitResetApply", method = RequestMethod.POST)
    @ResponseBody
    @CaptchaRequired
    public Response submitResetPasswordApply(HttpServletRequest request, String mail) {
        List<Account> accountList = accountService.findAccountListByMail(mail);
        // avoid attack account not found
        if (accountList == null || accountList.isEmpty()) return Response.buildResponse(true, MessageUtil.getMessage("account.tips.forgot.password.result"));
        accountService.submitResetPasswordApply(accountList, mail);
        return Response.buildResponse(true, MessageUtil.getMessage("account.tips.forgot.password.result"));
    }

    @RequestMapping(value = "/account/password/forwardToReset", method = RequestMethod.GET)
    public String forwardToForgotPassword(HttpServletRequest request, @RequestParam String activeCode, Model model) {
        boolean activeCodeValid = accountService.checkForgotPwdActiveCode(activeCode);
        if (!activeCodeValid) {
            model.addAttribute("message", MessageUtil.getMessage("account.tips.invalid.reset.link"));
            return "account/account_password_link_overdue";
        }
        cSRFTokenService.setCsrfToken(request, null);
        model.addAttribute("activeCode", activeCode);
        return "account/account_password_reset";
    }

    @ResponseBody
    @RequestMapping(value = "/account/password/reset", method = RequestMethod.POST)
    public Response saveResetPassword(HttpServletRequest request, @Valid ResetPasswordRequest resetPasswordRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Response.buildResponse(false, MessageUtil.createBindingErrorMessage(bindingResult));
        }
        boolean resetPasswordSuccess = accountService.resetPassword(resetPasswordRequest.getActiveCode(), resetPasswordRequest.getNewPassword());
        if (resetPasswordSuccess) {
            return Response.buildResponse(true, MessageUtil.getMessage("account.tips.password.edit.success"));
        } else {
            return Response.buildResponse(true, MessageUtil.getMessage("account.tips.invalid.reset.link"));
        }
    }

    @RequestMapping(value = "/account/index")
    public String forwardToAccountList(HttpServletRequest request, Model model) {
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        List<Account> accountList = accountService.findAccountListByStaffId(ucAccountView.get_id());
        String bindEmail = null;
        String bindMobilePhone = null;
        for (Account account : accountList) {
            if (account.getType().equals(AccountType.EMAIL.getCode())) {
                bindEmail = account.getUsername();
            }
            if (account.getType().equals(AccountType.MOBILE.getCode())) {
                bindMobilePhone = account.getUsername();
            }
        }
        model.addAttribute("bindEmail", bindEmail);
        model.addAttribute("bindMobilePhone", bindMobilePhone);
        return "account/account_index";
    }

    @RequestMapping(value = "/account/forwardToBindEmail")
    public String forwardToBindEmail(HttpServletRequest request) {
        return "account/account_bind_email";
    }

    @ResponseBody
    @RequestMapping(value = "/account/bindEmail")
    public Response bindEmail(HttpServletRequest request, @Valid BindEmailRequest bindEmailRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Response.buildResponse(false, MessageUtil.createBindingErrorMessage(bindingResult));
        }
        UCAccountView accountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        long staffId = accountView.get_id();
        accountService.bindEmail(staffId, bindEmailRequest.getEmail());
        return Response.buildResponse(true, MessageUtil.getMessage("account.bind.email.success"));
    }

    @RequestMapping(value = "/account/bind/activeEmail")
    public String activeBindEmail(HttpServletRequest request, String activeCode, Model model) {
        accountService.activeBindEmail(activeCode);
        return "account/account_bind_result";
    }
}
