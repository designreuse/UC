
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="sidebar">
    <ul>
        <li id="adminSidebar"><a href=""> <span>管理员设置</span></a> </li>
        <li id="warningSidebar"><a href="/ofweb/warning/warningSetting"> <span>告警设置</span></a> </li>
        <li id="systemSidebar"><a href="/ofweb/system/service/init"><span>系统服务</span></a></li>
        <li><a href=""><span>网络设置</span></a></li>
        <li id="mailSidebar"><a href="/ofweb/mailSetting/init"> <span>邮箱设置</span></a></li>
        <li class="submenu"> <a href=""> <span>安全设置</span></a>
            <ul>
                <li><a href="">证书管理</a></li>
                <li><a href="">防入侵设置</a></li>
            </ul>
        </li>
        <li><a href=""><span>诊断</span></a></li>
        <li><a href=""> <span>License</span></a></li>
        <li class="submenu"><a href=""> <span>存储管理</span></a>
            <ul>
                <li id="fileStorageSidebar"><a href="/ofweb/storage/file/init">文件存储</a></li>
                <li><a href="">备份/还原</a></li>
                <li><a href="">恢复出厂设置</a></li>
            </ul>
        </li>
        <li class="submenu"> <a href=""> <span>关于服务器 </span></a>
            <ul>
                <li id="statisticsSidebar">
                    <a href="/ofweb/system/statistics">服务器信息和状态
                    </a>

                </li>
                <li id="localeSidebar"><a href="/ofweb/locale/locale">时间/时区设置</a></li>
                <li><a href="">系统更新</a></li>
                <li id="auditSidebar"><a href="/ofweb/audit/list">操作日志</a></li>
            </ul>
        </li>
    </ul>
</div>

