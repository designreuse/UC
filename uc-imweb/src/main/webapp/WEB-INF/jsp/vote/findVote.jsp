<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="yealink" uri="http://www.yealinkuc.com/tag"%>
<!doctype html>
<html>
<head>
    <title>未完成的投票</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/css/normalize.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/3rdLibrary/jquery-ui/jquery-ui.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/3rdLibrary/kkpager/kkpager_blue.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/css/im-web.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/jquery/jquery.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/jquery-ui/jquery-ui.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/kkpager/kkpager.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/chartjs/Chart.js"></script>
    <style>
        .div-staffInfo{
            width: 76px;
            height: 100px;
        }
        .div-voteInfo{
            width: 360px;
            height: 100px;
        }
        .div-operInfo{
            width: 132px;
            height: 100px;
            padding: 0;
        }
        .div-status{
            background-color: #96B871;
        }
        .statistics{
        }
        .progressbar{
            height: 20px;
        }
        .my-select{
            margin-top:8px;
            margin-bottom:8px;
        }
        .canvas-pic{
            width: 400px;
        }
    </style>
</head>
<body>
<div class="div-outer">
    <div class="menu">
        <ul>
            <li><a href="${pageContext.request.contextPath}/vote/preCreateVote">发起投票</a></li>
            <li><a href="${pageContext.request.contextPath}/vote/findVote?findType=1">未完成的投票</a></li>
            <li><a href="${pageContext.request.contextPath}/vote/findVote?findType=2">已完成的投票</a></li>
            <li><a href="${pageContext.request.contextPath}/vote/findVote?findType=3">我发起的投票</a></li>
        </ul>
    </div>
    <div class="div-content">
        <input type="hidden" id="findType" value="${findType}"/>
        <div class="div-line">
            <c:if test="${empty voteList}">
            <div class="div-col">没有记录 </div>
            </c:if>
            <c:forEach var="vote" items="${voteList}">
                <input type="hidden" id="voteId_<c:out value='${vote.lineNum}'/>" value="${vote.voteId}"/>
                <input type="hidden" id="oriStaffId_<c:out value='${vote.lineNum}'/>" value="${vote.oriStaffId}"/>
                <input type="hidden" id="oriStaffName_<c:out value='${vote.lineNum}'/>" value="${vote.oriStaffName}"/>
                <input type="hidden" id="oriStaffAvatar_<c:out value='${vote.lineNum}'/>" value="${vote.oriStaffAvatar}"/>
                <input type="hidden" id="isAnonymous_<c:out value='${vote.lineNum}'/>" value="${vote.isAnonymous}"/>
                <div class="div-line">
                    <div class="div-col div-staffInfo">
                        <%--<img src="${pageContext.request.contextPath}/images/user.png"/>--%>
                        <c:choose>
                            <c:when test="${vote.oriStaffAvatar == null}">
                                <img src="${pageContext.request.contextPath}/images/user.png"/>
                            </c:when>
                            <c:otherwise>
                                <img src="${vote.oriStaffAvatar}"/>
                            </c:otherwise>
                        </c:choose>
                        <div><c:out value='${vote.oriStaffName}'/></div>
                    </div>
                    <div class="div-col div-voteInfo">
                        <table>
                            <col width="30%" />
                            <col width="70%" />
                            <tr>
                                <td colspan="2"><c:out value='${vote.voteTheme}'/></td>
                            </tr>
                            <tr>
                                <td>发起时间</td>
                                <td><yealink:date value ="${vote.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            </tr>
                            <tr>
                                <td>结束时间</td>
                                <td><yealink:date value ="${vote.finishDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <input type="hidden" id="isMultiSelect_<c:out value='${vote.lineNum}'/>" value="${vote.isMultiSelect}"/>
                                    <span>
                                        <c:if test="${vote.isAnonymous}">
                                            匿名;&nbsp;
                                        </c:if>
                                        <c:if test="${vote.isPublic}">
                                            显示详情;&nbsp;
                                        </c:if>
                                        <c:choose>
                                            <c:when test="${vote.isMultiSelect}">
                                                多选择;
                                                最少<span id="minItem_<c:out value='${vote.lineNum}'/>"><c:out value='${vote.minItem}'/></span>项;
                                                最多<span id="maxItem_<c:out value='${vote.lineNum}'/>"><c:out value='${vote.maxItem}'/></span>项
                                            </c:when>
                                            <c:when test="${!vote.isMultiSelect}">
                                                单选;&nbsp;
                                            </c:when>
                                            <c:otherwise>
                                                未知状态
                                            </c:otherwise>
                                        </c:choose>
                                    </span>
                                    <span class="oper-showDetail" data-line-num="${vote.lineNum}">
                                        <a href="#">显示详情</a>
                                    </span>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="div-col div-operInfo">
                        <div class="div-line">
                            <div class="div-col" style="border: 1px solid darkseagreen;">
                                <div class="div-line div-status">
                                     <span id="voteStatus_${vote.lineNum}">
                                        <c:choose>
                                            <c:when test="${vote.voteStatus=='00'}">
                                                未参与
                                            </c:when>
                                            <c:when test="${vote.voteStatus=='01'}">
                                                未投票
                                            </c:when>
                                            <c:when test="${vote.voteStatus=='02'}">
                                                已投票
                                            </c:when>
                                            <c:when test="${vote.voteStatus=='03'}">
                                                未完成
                                            </c:when>
                                            <c:when test="${vote.voteStatus=='04'}">
                                                已完成
                                            </c:when>
                                            <c:otherwise>
                                                未知状态：<c:out value='${vote.voteStatus}'/>
                                            </c:otherwise>
                                        </c:choose>
                                     </span>
                                    <span class="oper-del <c:if test="${vote.isMyOri == false}">hidden</c:if>" data-line-num="${vote.lineNum}">
                                        <img src="${pageContext.request.contextPath}/images/del.png"/>
                                    </span>
                                </div>
                                <div>
                                    邀请人数：<span><c:out value='${vote.relVoteCnt}'/></span>人
                                </div>
                                <div>
                                    投票人数：<span id="voteCnt_${vote.lineNum}"><c:out value='${vote.votedCnt}'/></span>人
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="div-line hidden" id="showDetail_<c:out value='${vote.lineNum}'/>">
                    <div class="bg">
                        <table>
                            <tr>
                                <td>投票说明</td>
                                <td><c:out value='${vote.voteDesc}' escapeXml="false"/></td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>
                                    <c:forEach var="voteItem" items="${vote.voteItemStatisticsList}">
                                        <div>
                                            <c:choose>
                                                <c:when test="${vote.isMultiSelect}">
                                                    <input class="voteItem_isMySelect_${vote.lineNum}" type="checkbox" name="voteItem.isMySelect" value="${voteItem.voteItem}"
                                                           <c:if test="${voteItem.isMySelect}">checked="checked"</c:if>
                                                           <c:if test="${vote.voteStatus != '01'}">disabled="disabled"</c:if> />
                                                </c:when>
                                                <c:when test="${!vote.isMultiSelect}">
                                                    <input class="voteItem_isMySelect_${vote.lineNum}" type="radio" name="voteItem.isMySelect" value="${voteItem.voteItem}"
                                                           <c:if test="${voteItem.isMySelect}">checked="checked" </c:if>
                                                           <c:if test="${vote.voteStatus != '01'}">disabled="disabled"</c:if> />
                                                </c:when>
                                            </c:choose>
                                            <c:out value='${voteItem.voteItem}'/>
                                            <c:if test="${voteItem.isHavePicture}">
                                                <img src="${pageContext.request.contextPath}/<c:out value='${voteItem.picturePath}'/>" class="img-rounded">&nbsp;
                                            </c:if>
                                            <c:out value='${voteItem.itemDesc}'/>
                                        </div>
                                    </c:forEach>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="bg my-select <c:if test="${vote.voteStatus == '01' || vote.voteStatus == '00'}">hidden</c:if>" id="mySelect_<c:out value='${vote.lineNum}'/>">
                        我的选择：<span><c:out value='${vote.mySelectItem}'/></span>
                    </div>
                    <div class="bg my-select <c:if test="${vote.voteStatus != '01'}">hidden</c:if>" id="submit_<c:out value='${vote.lineNum}'/>">
                        <input class="submitVote" type="submit" value="提交" data-line-num="${vote.lineNum}"/>
                    </div>
                    <div  class="<c:if test="${vote.voteStatus == '01'}">hidden</c:if>" id="voteResult_<c:out value='${vote.lineNum}'/>">
                        <div>
                            <div class="statistics" id="statistics_<c:out value='${vote.lineNum}'/>">
                                <ul>
                                    <li><a class="oper-tablePic" data-line-num="${vote.lineNum}" href="#tablePic_<c:out value='${vote.lineNum}'/>">表格</a></li>
                                    <li><a class="oper-piePic" data-line-num="${vote.lineNum}" href="#piePic_<c:out value='${vote.lineNum}'/>">饼图</a></li>
                                    <li><a class="oper-barPic" data-line-num="${vote.lineNum}" href="#barPic_<c:out value='${vote.lineNum}'/>">柱状图</a></li>
                                </ul>
                                <div id="tablePic_<c:out value='${vote.lineNum}'/>">
                                    <table class="tab-grid" id="table_percent_<c:out value='${vote.lineNum}'/>">
                                        <col width="20%" />
                                        <col width="10%" />
                                        <col width="50%" />
                                        <col width="20%" />
                                        <tr>
                                            <td>选项</td>
                                            <td>小计</td>
                                            <td colspan="2">比例</td>
                                        </tr>
                                        <c:forEach var="voteItem" items="${vote.voteItemStatisticsList}">
                                            <tr>
                                                <td><c:out value='${voteItem.voteItem}'/></td>
                                                <td><c:out value='${voteItem.selectCnt}'/></td>
                                                <td><div class="progressbar" style="vertical-align: middle;height: 20px" ></div></td>
                                                <td><span></span>%</td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </div>
                                <div  class="canvas-pic" id="piePic_<c:out value='${vote.lineNum}'/>">
                                    <canvas id="pieChart_<c:out value='${vote.lineNum}'/>"></canvas>
                                </div>
                                <div  class="canvas-pic" id="barPic_<c:out value='${vote.lineNum}'/>">
                                    <canvas class="canvas-pic" id="barChart_<c:out value='${vote.lineNum}'/>"></canvas>
                                </div>
                            </div>
                        </div>
                        <div class="<c:if test="${vote.isPublic == false}"><c:if test="${vote.isMyOri == false}">hidden</c:if></c:if>">
                            <div class="oper-showVoteDetail" data-line-num="${vote.lineNum}"><a href="#">显示投票详情</a></div>
                            <div class="bg hidden" id="showVoteDetail_${vote.lineNum}">
                                <table style="width: 80%" id="votedStaff_<c:out value='${vote.lineNum}'/>">
                                    <c:forEach var="voteStaff" items="${vote.voteStaffList}">
                                        <tr>
                                            <td rowspan="2">
                                                <c:choose>
                                                    <c:when test="${voteStaff.avatar == null}">
                                                        <img src="${pageContext.request.contextPath}/images/user-small.png"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img src="${voteStaff.avatar}"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${vote.isAnonymous}">
                                                        匿名
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:out value='${voteStaff.staffName}'/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td><yealink:date value ="${voteStaff.voteDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                        </tr>
                                        <tr>
                                            <td>投票给</td>
                                            <td>${voteStaff.voteItems}</td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="div-content">
        <div  id="kkpager" style="clear: both;float: right"></div>
    </div>
    <div class="div-bottom"></div>
    <div class="div-bottom-cancel"></div>

</div>

<script>

    //生成分页控件
    kkpager.generPageHtml({
        //设置
        isShowTotalPage: false,//不显示总页数
        isShowCurrPage:false,//不显示当前页
        isGoPage:false,//不显示跳转页
        pno : ${pager.pageNum},
        mode : 'link', //可选，默认就是link
        //总页码
        total : ${pager.totalPage},
        //总数据条数
        totalRecords : ${pager.total},
        //链接前部
        hrefFormer : contextPath +"/vote/findVote?findType="+$("#findType").val(),
        //链接算法
        getLink : function(n){
            var link;
            //这里是默认算法，算法适用于比如：
            //hrefFormer=http://www.xx.com/news/20131212
            //hrefLatter=.html
            //那么首页（第1页）就是http://www.xx.com/news/20131212.html
            //第2页就是http://www.xx.com/news/20131212_2.html
            //第n页就是http://www.xx.com/news/20131212_n.html
            //return this.hrefFormer + '_' + n + this.hrefLatter;
            link = this.hrefFormer+"&pageNum="+n;
            return link;
        }

    });

   /*
    //生成分页控件
    kkpager.generPageHtml({
        //设置
        isShowTotalPage: false,//不显示总页数
        isShowCurrPage:false,//不显示当前页
        isGoPage:false,//不显示跳转页
        pno : '${p.pageNo}',
        mode : 'click', //设置为click模式
        //总页码
        total : '${p.totalPage}',
        //总数据条数
        totalRecords : '${p.totalCount}',
        //点击页码、页码输入框跳转、以及首页、下一页等按钮都会调用click
        //适用于不刷新页面，比如ajax
        click : function(n){
            //这里可以做自已的处理
            //...
            //处理完后可以手动条用selectPage进行页码选中切换
            this.selectPage(n);
        },
        //getHref是在click模式下链接算法，一般不需要配置，默认代码如下
        getHref : function(n){
            return '#';
        }

    });

    */


    $(document).ready(function(){
        //初始化：统计图表
        $(".statistics").each(function(){
            $(this).tabs();
        })
        //删除操作
        $(document).delegate(".oper-del","click",function(){
            if(!confirm("是否删除")){
                return false;
            };
            var lineNum = $(this).attr("data-line-num");
            var voteId = $("#voteId_"+lineNum).val();
            var findType = $("#findType").val();
            $.ajax({
                type: "POST",
                url: contextPath + "/vote/delVoting",
                data: JSON.stringify({"voteId":voteId,"findType":findType}),
                dateType:"json",
                contentType: "application/json; charset=utf-8",
                async: false,
                success: function(data) {
                    window.location.reload();
                    alert("删除成功！");
                },
                error: function(data) {
                    //中间发生异常，具体查看xhr.responseText
                    alert("error:" + data.responseText);
                }
            });

        })
        //显示与隐藏详情
        $(document).delegate(".oper-showDetail", "click", function() {
            var showDetail = $(this).children("a");
            if(showDetail.text() == "显示详情"){
                showDetail.text("收起详情");
            }else {
                showDetail.text("显示详情");
            }
            var lineNum = $(this).attr("data-line-num");
            $("#showDetail_" + lineNum).toggle();
            //进度条处理
            genProgressbar(lineNum);

        });
        //显示与隐藏投票详情
        $(document).delegate(".oper-showVoteDetail", "click", function() {
            var showDetail = $(this).children("a");
            if(showDetail.text() == "显示投票详情"){
                showDetail.text("隐藏投票详情");
            }else {
                showDetail.text("显示投票详情");
            }
            var lineNum = $(this).attr("data-line-num");
            $("#showVoteDetail_" + lineNum).toggle();
        });
        //生成图表
        $(".oper-piePic,.oper-barPic").click(function(){
            var itemList = new Array();
            var cntList = new Array();
            var lineNum = $(this).attr("data-line-num");
            var tableDivSelector = "#tablePic_"+lineNum;
            $(tableDivSelector).find("table tr:gt(0)").each(function(){
                var item = $(this).children("td:eq(0)").text();
                var cnt = $(this).children("td:eq(1)").text();
                itemList.push(item);
                cntList.push(cnt);
            });
            //
            var className = $(this).attr("class");
            //图类型
            var picType;
            if(className.indexOf("pie")>0){
                picType = "pie";
            }else{
                picType = "bar";
            }
            //生成图
            var canvasId = picType+"Chart_"+lineNum;
            genChart(canvasId, picType, itemList, cntList);
        });
        //检验
        function verifyCheckCnt(checkedCnt,lineNum){
            var isMultiSelect =$("#isMultiSelect_"+lineNum).val();
            var minItem =parseInt($("#minItem_"+lineNum).text());
            var maxItem =parseInt($("#maxItem_"+lineNum).text());
            if(isMultiSelect == "true"){
                if(checkedCnt<minItem || checkedCnt>maxItem){
                    alert("最小："+minItem+"项；"+"最大："+maxItem+"项");
                    return false;
                }
            }else {
                if(checkedCnt == 0){
                    alert("必须输入");
                    return false;
                }
            }
            return true;
        }
        //创建投票
        $(".submitVote").click(function(){
            var lineNum = $(this).attr("data-line-num");
            var voteItems ="";
            var checkedInputs  = $(".voteItem_isMySelect_" + lineNum + ":checked");
            //检验
            if(!verifyCheckCnt(checkedInputs.length,lineNum)){
                return false;
            }
            checkedInputs.each(function(){
                voteItems = voteItems + $(this).val() + ";";
            })
            var data = {
                "voteId" : $("#voteId_"+lineNum).val(),
                "voteItems":voteItems,
            };
            $.ajax({
                type: "POST",
                url: contextPath + "/vote/submitVoting",
                data: JSON.stringify(data),
                dateType:"json",
                contentType: "application/json; charset=utf-8",
                async: false,
                success: function(result) {
                    //var result = jQuery.parseJSON(data);
                    if(!result.success){
                        alert(result.message);
                    }else{
                        alert("请求成功!");
                        var data = result.data;
                        //显示我的选择
                        $("#mySelect_" + lineNum).show().children("span").text(voteItems);
                        //显示结果清单
                        $("#voteResult_" + lineNum).show();
                        //增加投票人数
                        var voteCnt = $("#voteCnt_"+lineNum);
                        voteCnt.empty().text(voteCnt.text()+1);
                        //修改投票状态
                        var voteStatus = $("#voteStatus_"+lineNum);
                        voteStatus.empty().text("已投票");
                        //更新表格数据
                        var trs = $("#tablePic_"+lineNum).find("table tr:gt(0)");
                        var total = 0;
                        trs.each(function(){
                            var item = $(this).children("td:eq(0)");
                            var itemCnt = item.next();
                            var idx = voteItems.indexOf(item.text());
                            if(idx != -1){
                                itemCnt.empty().text(itemCnt.text()+1);;
                            }
                        });
                        //更进度状态
                        genProgressbar(lineNum);
                        //隐藏提交
                        $("#submit_"+lineNum).hide();
                        //增加详情
                        genDetail(lineNum,voteItems,data);
                        //禁用选择
                        forbidden(lineNum);
                        //隐藏显示详情
                        hiddenVoteDetail(lineNum);
                    }
                },
                error: function(data) {
                    //中间发生异常，具体查看xhr.responseText
                    alert("error:" + data.responseText);
                }
            });

        });

    });

    //隐藏显示详情
    function hiddenVoteDetail(lineNum){

    }
    //禁用选择
    function forbidden(lineNum){
        $(".voteItem_isMySelect_"+lineNum).each(function(){
            $(this).attr("disabled","disabled");
        })
    }
    //增加详情
    function genDetail(lineNum,voteItems,data){
        var isAnonymous = $("#isAnonymous_"+lineNum).val();
        var avatar = '<img src="${pageContext.request.contextPath}/images/user-small.png" width="20px"/>'
        var voteStaffName = $("#oriStaffName_"+lineNum).val();
        if(isAnonymous == "true"){
            voteStaffName = "匿名";
        }
        var voteDate = data.voteDate;
        var voteItems =voteItems;
        var htmlContext ='<tr><td rowspan="2">'+avatar+'</td><td>'+voteStaffName+'</td><td>'+voteDate+'</td></tr><tr><td>投票给</td><td>'+voteItems+'</td></tr>';
        $("#votedStaff_"+lineNum).append(htmlContext);
        $("#none_votedStaff_"+lineNum).hide;
    }
    //生成统计进度百分比
    function genProgressbar(lineNum){
        var progressbar =  $("#table_percent_"+lineNum).find(".progressbar");
        //计算总数
        var total = 0;
        progressbar.each(function(){
            var cnt = $(this).parent().prev()
            total = total + parseInt(cnt.text());//要转换为数字才行
        })
        //统计百分比
        progressbar.each(function(){
            //当前值
            var val;
            if(total == 0){
                val = 0;
            }else{
                val = parseInt($(this).parent().prev().text()*100/total);//要转换为数字才行
            }
            //更新百分比
            $(this).parent().next().find("span").text(val);
            //生成进度条
            $(this).progressbar({
                value: false
            });
            $(this).progressbar( "option", {
                value: val
            });
            //设置颜色
            progressbarValue = $(this).find( ".ui-progressbar-value" );
            progressbarValue.css({
                "background": '#409988'
            });
        })
    }


    //生成饼图或柱状图
    function genChart(canvasId,picType,itemList,cntList){
        //备选颜色
        var bgColor = new Array (
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 206, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(153, 102, 255, 0.2)',
                'rgba(255, 159, 64, 0.2)'
        );
        var bdColor = new Array (
                'rgba(255,99,132,1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)',
                'rgba(153, 102, 255, 1)',
                'rgba(255, 159, 64, 1)'
        );
        var colorCnt = bgColor.length;
        //当前颜色
        var curBgColor = new Array();
        var bdColor = new Array();
        for(var i =0;i<itemList.length;i++){
            curBgColor.push(bgColor[i%colorCnt]);
            bdColor.push(bdColor[i%colorCnt]);
        }
        //饼图绘制
        var ctx = document.getElementById(canvasId);
        var myChart = new Chart(ctx, {
            type: picType,
            data: {
                labels: itemList,
                datasets: [{
                    label: itemList,
                    data: cntList,
                    backgroundColor: curBgColor,
                    borderColor: bdColor,
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero:true
                        }
                    }]
                }
            }
        });
    }
</script>
</body>
</html>
