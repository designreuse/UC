<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
,
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
  <meta http-equiv="Cache-Control" content="no-store" />
  <meta http-equiv="Pragma" content="no-cache" />
  <meta http-equiv="Expires" content="0" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta http-equiv="X-Frame-Options" content="SAMEORIGIN / DENY ">
  <title><spring:message code="index.title.name"/></title>
  <link rel="shortcut icon" href="images/cloud.ico"/>
  <link rel="stylesheet" href="styles/css/style.min.css">
</head>
<body class="login-body">
<div class="wrap">
  <base href="">
  <div class="navbar navbar-language navbar-fixed-top" role="navigation">
    <div class="navbar-right navbar-language-select-login navbar-offset-right">
      <form class="navbar-form form-inline">
        <div class="form-group">
          <a class="login-a-about" target="blank" href="http://www.yealink.cn">
            <spring:message code="index.about"/>
          </a>
          <a class="login-a-about" target="blank" href="http://www.yealink.cn/product_list.aspx?ProductsCateID=363&parentcateid=363&cateid=363&BaseInfoCateId=363&Cate_Id=363&index=1">
            <spring:message code="index.about.vms"/>
          </a>
          <a class="login-a-about" target="blank" href="http://support.yealink.com/documentFront/forwardToDocumentFrontDisplayPage?BaseInfoCateId=1313&NewsCateId=1313&CateId=1313#">
            <spring:message code="index.support"/>
          </a>
        </div>

        <div class="form-group div-language-select">
          <div class="dropdown">
            <div class="navbar-language-select" id="languageMenu"
                 data-toggle="dropdown">
              <label>
                中文
              </label>
              <i class="caret"></i>
            </div>
            <ul class="dropdown-menu" role="menu" aria-labelledby="languageMenu">
              <li role="presentation">
                <a role="menuitem" tabindex="-1" onclick="changeLang('en')">English</a>
              </li>
              <li role="presentation">
                <a role="menuitem" tabindex="-1" onclick="changeLang('zh-CN')">中文</a>
              </li>
            </ul>
          </div>
        </div>
      </form>
    </div>
  </div>

  <div class="navbar navbar-default" role="navigation">
    <div class="navbar-header col-sm-offset-2">
    </div>
  </div>
  <script type="text/javascript" src="js/i18n/message-zh-CN.js"></script>
  <div class="modal fade" id="promptModal" tabindex="-1" role="dialog" data-backdrop="static"
       aria-hidden="true">
    <div class="modal-dialog custom-modal-size">
      <div class="modal-content">
        <div class="modal-header custom-modal-header">
          <button id="closePswBtn" type="button" class="close custom-close" data-dismiss="modal"
                  aria-hidden="true">&times;</button>
          <h4 class="modal-title custom-modal-title">
            提示
          </h4>
        </div>
        <div class="modal-body  custom-confirm-modal-body" id="promptModalBody">

        </div>
        <div class="modal-footer  custom-modal-footer">
          <div class="col-sm-offset-4  col-sm-4 custom-button-col-no-padding">
            <button type="button" class="btn btn-success btn-lg btn-block" id="promptModalOkBtn"
                    data-dismiss="modal">确定
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>

  <div class="modal fade" data-backdrop="static" data-keyboard="false"
       aria-hidden="true" id="modalProgressBar">
    <div class="modal-dialog div-progress-bar-dialog-location custom-modal-size">
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title custom-modal-title custom-no-padding-left" id="progressBarTitle">
          </h4>
        </div>
        <div class="modal-body">
          <div class="progress progress-striped active">
            <div class="progress-bar progress-bar-success div-progress-bar-dialog-body" role="progressbar">
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="login-blank-div"></div>
  <div class="login-content">
    <div class="login-form">
      <%-- 根据java安全编程规范3.3.1完整性检查，添加csrfToken --%>
      <input type="hidden" id="csrfToken" value="${sessionScope.csrfToken }">

      <div id="div-login-welcome">
        <spring:message code="login.label.welcome"/>
      </div>

      <%-- form tag is used for username auto complete --%>
      <form onsubmit="return false;">
        <div class="form-horizontal">
          <div class="has-error login-error-group">
            <label id="errorLoginMessage" class="control-label login-error-msg-label"> </label>
          </div>
          <div class="form-group" id="divUsername">
            <div class="col-sm-12">
              <input class="form-control input-no-radius" id="username" maxlength="128" name="username"
                     title="<spring:message code="login.input.prompt.username"/>"
                     placeholder="<spring:message code="login.input.place.username"  text="login.input.place.username"/>"/>
            </div>
          </div>
          <div class="form-group" id="divPassword">
            <div class="col-sm-12">
              <input type="password" class="form-control input-no-radius"
                     id="password" maxlength="32"
                     title="<spring:message code="login.input.prompt.password"/>"
                     placeholder="<spring:message code="login.input.place.password"  text="login.input.place.password"/>"
                     autocomplete="off"/>
            </div>
          </div>
          <div class="form-group" id="divCaptcha" <c:if test="${sessionScope.login_error_count eq 0}">style="display: none" </c:if>>
            <div class="col-sm-12">
              <div class="block input-icon input-img-right">
                <input class="form-control input-no-radius" id="captcha" maxlength="4" autocomplete="off"
                       title="<spring:message code="login.input.prompt.captcha"/>"
                       placeholder="<spring:message code="login.input.prompt.captcha"  text="login.input.prompt.captcha"/>"/>

                <img alt="<spring:message code="login.alt.captcha"/>" src="${pageContext.request.contextPath}/images/loading.gif"
                     id="img-captcha" align="middle" onclick="reloadCaptcha();"
                     title="<spring:message code="login.label.captcha.change"/>" class="login-captcha-height">
              </div>
            </div>
          </div>

          <div class="form-group">
            <div class="col-sm-12">
              <div class="checkbox pull-left login-label-remember">
                <label>
                  <input type="checkbox" id="rememberMe" name="rememberMe" class="login-input-rememberMe">
                  <spring:message code="login.label.remembeme"/>
                </label>
              </div>
              <div class="pull-right">
                <a href="${pageContext.request.contextPath}/account/password/forwardToForgot" class="login-a-forgot-pwd pull-right">
                  <spring:message code="login.label.forget.password" text="login.label.forget.password"/>
                </a>
              </div>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-12">
              <button id="submitLoginBtn" class="btn btn-success btn-lg btn-block btn-lg-adjusted">
                <spring:message code="system.common.button.login"/>
              </button>
            </div>
          </div>
        </div>
      </form>
      <input type="hidden" id="basePath" value="${pageContext.request.contextPath}">
    </div>
    <div class="login-copyright">
      ${sessionScope.copyright }
    </div>
  </div>

  <%-- 项目中浏览器版本提示框 --%>
  <div id="browserTips" class="browser-tips">
    <spring:message code="system.common.tooltip.browser.version.low" text="system.common.tooltip.browser.version.low"/>
  </div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/login.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/ZeroClipboard/ZeroClipboard.min.js"></script>
<!--[if lt IE 9]>
<script src="${pageContext.request.contextPath}/3rdLibrary/ie-compatible/ie.compatible.min.js"></script>
<![endif]-->
<script type="text/javascript">
  $(function () {
    //prompt ie6 && ie7 version low
    if (IEVersion() == 6 || IEVersion() == 7) {
      $('#browserTips').show();
    }
    readyLogin();
  });

</script>
</body>
</html>