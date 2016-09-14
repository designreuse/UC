<%--
  Created by IntelliJ IDEA.
  User: chenkl
  Date: 2016/8/2
  Time: 10:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title><spring:message code="server.info.title" text="server.info.title"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/3rdLibrary/bootstrap-table/css/bootstrap-table.css" />
    <style type="text/css">
        .info-header {
            background-color: #eee;
            font-size: 10pt;
            padding-left: 20px;
            height: 30px;
        }
        .info-table {
            margin-right: 12px;
        }
        .info-table .c1 {
            text-align: right;
            vertical-align: top;
            color: #666;
            font-weight: bold;
            font-size: 9pt;
            white-space: nowrap;
            padding-top: 10px;
            padding-bottom: 5px;
        }
        .info-table .c2 {
            font-size: 9pt;
            width: 90%;
            padding-top:10px;
            padding-bottom: 5px;
        }
    </style>

</head>
<body>
    <div class="container-fluid">
        <div style="padding-left: 200px;padding-right: 200px;">
            <table border="0" cellpadding="2" cellspacing="2" width="100%" class="info-table panel panel-default">
                <thead>
                <tr>
                    <th colspan="2" align="left" class="info-header"><spring:message code="index.properties"/></th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td class="c1"><spring:message code="index.uptime"/></td>
                    <td class="c2" id="startTimeTd">
                    </td>
                </tr>
                <tr>
                    <td class="c1"><spring:message code="index.version"/></td>
                    <td class="c2" id="versionTd">
                    </td>
                </tr>
                <tr>
                    <td class="c1"><spring:message code="index.home"/></td>
                    <td class="c2" id="mainDirectoryTd">
                    </td>
                </tr>
                <tr>
                    <td class="c1">
                        <spring:message code="index.server_name"/>
                    </td>
                    <td class="c2" id="serverNameTd">
                    </td>
                </tr>
                <tr><td>&nbsp;</td></tr>
                </tbody>
                <thead>
                <tr>
                    <th colspan="2" align="left" class="info-header"><spring:message code="index.environment"/></th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td class="c1"><spring:message code="index.jvm"/></td>
                    <td class="c2" id="vendorTd">
                    </td>
                </tr>
                <tr>
                    <td class="c1"><spring:message code="index.app"/></td>
                    <td class="c2" id="serverInfoTd">
                    </td>
                </tr>
                <tr>
                    <td class="c1">
                        <spring:message code="index.host_name"/>
                    </td>
                    <td class="c2" id="hostNameTd">
                    </td>
                </tr>
                <tr>
                    <td class="c1"><spring:message code="index.os"/></td>
                    <td class="c2" id="osarchTd">

                    </td>
                </tr>
                <tr>
                    <td class="c1"><spring:message code="index.local"/></td>
                    <td class="c2" id="localeTd">

                    </td>
                </tr>
                <tr>
                    <td class="c1"><spring:message code="index.memory"/></td>
                    <td  class="c2" id="mermoryDiv">
                    </td>
                </tr>
                </tbody>
            </table>

        </div>
        <div class="div-list-table div-list-table-layout" style="height: 500px;padding-left: 200px;padding-right: 200px;">
            <table   class="table fixed-table table-hover table-condensed" style="text-align: left;">
                <thead>
                    <tr>
                        <th data-field="interfaceName"><spring:message code="ports.interface"/></th>
                        <th data-field="port"><spring:message code="ports.port"/></th>
                        <th data-field="type"><spring:message code="ports.type"/></th>
                        <th data-field="tlsPolicy"><spring:message code="ports.description"/></th>
                    </tr>
                </thead>
                <tbody id="serverPortTableBody">

                </tbody>
        </table>
        </div>

    </div>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/artTemplate.js"></script>
    <script>
        $(function () {
            getProperties();
            getPorts();
        });
        function getProperties(){
            var url=$("#projectContext").val()+"/server/properties";
            $.ajax({
                url: url,
                cache: false,
                dataType: "json",
                type: "get",
                async: false,
                error: function (xmlHttpRequest, error) {

                },
                success: function (result) {
                    if (result.ret >= 0 && result.data) {
                        var temp = result.data;
                        $("#startTimeTd").text(temp.uptimeDisplay+"--started "+ temp.startTimeFormat);
                        $("#versionTd").text(temp.version);
                        $("#mainDirectoryTd").text(temp.mainDirectory);
                        $("#serverNameTd").text(temp.serverName);
                        $("#vendorTd").text(temp.jvmVersion+"--"+temp.vendor);
                        $("#serverInfoTd").text(temp.serverInfo);
                        $("#hostNameTd").text(temp.hostname);
                        $("#osarchTd").text(temp.osname+"/"+temp.osarch);
                        $("#localeTd").text(temp.local);
                        var percentFree = ((temp.maxMemory - temp.usedMemory)/temp.maxMemory)*100.0;
                        var percent = 100-Math.round(percentFree);
                        $("#percent").val(percent);
                        $("#mermoryDiv").text(Math.round(temp.usedMemory*100)/100+"MB of "+temp.maxMemory+"MB ("+percent+"%) used");

                    }
                }
            });
        }

        function getPorts(){
            var url=$("#projectContext").val()+"/server/ports";
            $.ajax({
                url: url,
                cache: false,
                dataType: "json",
                type: "get",
                async: false,
                error: function (xmlHttpRequest, error) {

                },
                success: function (result) {
                    if (result.ret >= 0 && result.data) {
                        var html = template('portsTemp', result);
                        document.getElementById('serverPortTableBody').innerHTML = html;
                    }
                }
            });
        }
    </script>

    <script id="portsTemp" type="text/html">
        {{each data as prop index}}
        <tr class="
			{{if index % 2 ===0}}table-striped-tr-odd
			{{else}}table-striped-tr-even
			{{/if}}">
            <td width="5%" class="text-align-center"><label title="{{prop.interfaceName}}">{{prop.interfaceName}}</label></td>
            <td width="10%" class="text-align-center"><label title="{{prop.port}}">{{ prop.port}}</label></td>
            <td width="10%" class="text-align-center"><label title="{{prop.typeName}}">{{ prop.typeName}}</label></td>
            <td>
                {{if prop.type === 'SOCKET_C2S' && prop.tlsPolicy !== 'legacyMode'}}
                <spring:message code="ports.client_to_server.desc"/>
                <spring:message code="ports.plaintext.desc"/>
                {{/if}}
                {{if prop.type === 'SOCKET_C2S' && prop.tlsPolicy === 'legacyMode'}}
                <spring:message code="ports.client_to_server.desc_old_ssl"/>
                <spring:message code="ports.legacymode.desc"/>
                {{/if}}
                {{if prop.type === 'SOCKET_S2S'}}
                <spring:message code="ports.server_to_server.desc"/>
                <spring:message code="ports.legacymode.desc"/>
                {{/if}}
                {{if prop.type === 'COMPONENT' && prop.tlsPolicy !== 'legacyMode'}}
                <spring:message code="ports.external_components.desc"/>
                <spring:message code="ports.plaintext.desc"/>
                {{/if}}
                {{if prop.type === 'COMPONENT' && prop.tlsPolicy === 'legacyMode'}}
                <spring:message code="ports.external_components.desc_old_ssl"/>
                <spring:message code="ports.legacymode.desc"/>
                {{/if}}
                {{if prop.type === 'CONNECTION_MANAGER' && prop.tlsPolicy !== 'legacyMode'}}
                <spring:message code="ports.connection_manager.desc"/>
                <spring:message code="ports.plaintext.desc"/>
                {{/if}}
                {{if prop.type === 'CONNECTION_MANAGER' && prop.tlsPolicy === 'legacyMode'}}
                <spring:message code="ports.connection_manager.desc_old_ssl"/>
                <spring:message code="ports.legacymode.desc"/>
                {{/if}}
                {{if prop.type === 'WEBADMIN' && prop.tlsPolicy !== 'legacyMode'}}
                <spring:message code="ports.admin_console.desc_unsecured"/>
                {{/if}}
                {{if prop.type === 'WEBADMIN' && prop.tlsPolicy === 'legacyMode'}}
                <spring:message code="ports.admin_console.desc_secured"/>
                {{/if}}
                {{if prop.type === 'BOSH_C2S' && prop.tlsPolicy !== 'legacyMode'}}
                <spring:message code="ports.http_bind.desc_unsecured"/>
                {{/if}}
                {{if prop.type === 'BOSH_C2S' && prop.tlsPolicy === 'legacyMode'}}
                <spring:message code="ports.http_bind.desc_secured"/>
                {{/if}}
            </td>
        </tr>
        {{/each}}
    </script>
</body>
</html>
