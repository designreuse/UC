<%--
  Created by IntelliJ IDEA.
  User: yl1240
  Date: 2016/7/6
  Time: 8:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!doctype html>
<html>
<head>
    <title>发起投票</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/css/normalize.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/3rdLibrary/jquery-ui/jquery-ui.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/3rdLibrary/remodal/dist/remodal.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/3rdLibrary/remodal/dist/remodal-default-theme.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/css/im-web.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/jquery/jquery.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/jquery/json-forms.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/jquery-ui/jquery-ui.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/ckeditor/ckeditor.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/common.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/remodal/dist/remodal.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/jquery/ajaxfileupload.js"></script>
</head>

<body>
    <form class="form-horizontal" id="voteForm" role="form">
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
                <div>
                    <table style="width: 100%">
                        <col width="92px" />
                        <col width="517px" />
                        <tr>
                            <td>
                                <label for="relStaffName"><input type="button" value="选择参与人" id="selectStaff"/></label>
                            </td>
                            <td>
                                <input id="selectedOriStaffName" name="selectedOriStaffName" readonly="readonly" type="text" value="" style="width: 100%"/>
                                <input id="relStaffId" name="relStaffId" type="hidden" value=""/>
                                <input id="checkedTreeId" name="checkedTreeId" type="hidden" value=""/>
                                <input id="selectedOriStaffId" name="selectedOriStaffId" type="hidden" value=""/>
                                <input id="relStaffName" name="relStaffName" type="hidden" value=""/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="voteTheme">投票主题：</label>
                            </td>
                            <td>
                                <input id="voteTheme" name="voteTheme" type="text" value="" maxlength="256" style="width: 80%"/> (<span id="themeLen">0</span>/256)
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="voteDesc">投票设置：</label>
                            </td>
                            <td>
                                <label for="voteDesc"><a href="#" id="descLabel">添加详细说明</a></label>
                                <div id="descDiv" style="display: none">
                                    <textarea id="voteDesc" name="voteDesc" rows="10" cols="50"></textarea>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                               &nbsp;
                            </td>
                            <td>
                                <table class="bg" style="width: 100%">
                                    <col width="88px" />
                                    <col width="429px" />
                                    <tr>
                                        <td>
                                            <label>选项设置：</label>
                                        </td>
                                        <td>
                                            <input id="isMultiSelect1" name="isMultiSelect" type="radio" value="false"/>单选项
                                            <input id="isMultiSelect2" name="isMultiSelect" type="radio" value="true" checked="checked"/>多选项
                                            <span id="rangItem">
                                            最少<select class="form-control input-sm" id="minItem" name="minItem"><option value="2" selected="selected">2</option></select>项
                                            最多<select class="form-control input-sm" id="maxItem" name="maxItem"><option value="2" selected="selected">2</option></select>
                                            </span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <label for="finishDate">结束日期：</label>
                                        </td>
                                        <td>
                                            <input class="form-control" id="finishDate" name="finishDate" readonly="readonly" type="text" value=""/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <label>其它设置：</label>
                                        </td>
                                        <td>
                                            <input  id="isAnonymous" name="isAnonymous" type="checkbox" value="true" checked="checked"/>匿名
                                            <input id="isPublic" name="isPublic" type="checkbox" value="true" checked="checked"/>显示投票详情
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                &nbsp;
                            </td>
                            <td>
                                <table class="bg" id="voteItem" style="width: 100%">
                                    <col width="266px" />
                                    <col width="66px" />
                                    <col width="57px" />
                                    <thead>
                                    <tr>
                                        <td>选项文字</td>
                                        <td>图片</td>
                                        <td>说明</td>
                                        <td>操作</td>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td><input name="voteItemList[0][voteItem]" type="text" value="" maxlength="128" class="VoteItemChange"/></td>
                                        <td><input name="voteItemList[0][isHavePicture]" type="checkbox" class="selectPic"/>
                                            <img src="${pageContext.request.contextPath}/images/pic.png" style="display: none" data-remodal-target="pic-modal-0"/>
                                            <div class="remodal popDiv" data-remodal-id="pic-modal-0" style="width: 430px;height: 200px">
                                                <button data-remodal-action="close" class="remodal-close"></button>
                                                <input type="hidden" name="voteItemIndex" value="0"/>
                                                <input class="selectFile" type="file" id="picFile0" name="picFile"/>
                                                <input type="hidden" name="voteItemList[0][picturePath]" value=""/>
                                                <div>
                                                    <img src="" id="showPic-0" style="display: none"/>
                                                </div>
                                            </div>
                                        </td>
                                        <td>
                                            <img class="inputDesc" src="${pageContext.request.contextPath}/images/desc.png" data-remodal-target="desc-modal-0"/>
                                            <div class="remodal popDiv" data-remodal-id="desc-modal-0" style="width: 500px">
                                                <button data-remodal-action="close" class="remodal-close"></button>
                                                <textarea name="voteItemList[0][itemDesc]" cols="50" rows="10" maxlength="256" value=""></textarea>
                                            </div>
                                        </td>
                                        <td>
                                            <input  name="voteItemList[0][itemIndex]"  type="hidden" value="" maxlength="128"/>
                                            <img class="addItem" src="${pageContext.request.contextPath}/images/add.png" />
                                            <img class="delItem" src="${pageContext.request.contextPath}/images/sub.png" />
                                            <img class="downItem" src="${pageContext.request.contextPath}/images/up.png" />
                                            <img class="downItem" src="${pageContext.request.contextPath}/images/down.png" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><input name="voteItemList[1][voteItem]" type="text" value="" maxlength="128" class="VoteItemChange"/></td>
                                        <td><input name="voteItemList[1][isHavePicture]" type="checkbox" class="selectPic"/>
                                            <img src="${pageContext.request.contextPath}/images/pic.png" style="display: none" data-remodal-target="pic-modal-1"/>
                                            <div class="remodal popDiv" data-remodal-id="pic-modal-1" style="width: 430px;height: 200px">
                                                <button data-remodal-action="close" class="remodal-close"></button>
                                                <input type="hidden" name="voteItemIndex" value="1"/>
                                                <input class="selectFile" type="file" id="picFile1" name="picFile"/>
                                                <input type="hidden" name="voteItemList[1][picturePath]" value=""/>
                                                <div>
                                                    <img src="" id="showPic-1" style="display: none"/>
                                                </div>
                                            </div>
                                        </td>
                                        <td>
                                            <img class="inputDesc" src="${pageContext.request.contextPath}/images/desc.png" data-remodal-target="desc-modal-1"/>
                                            <div class="remodal popDiv" data-remodal-id="desc-modal-1" style="width: 500px">
                                                <button data-remodal-action="close" class="remodal-close"></button>
                                                <textarea name="voteItemList[1][itemDesc]" cols="50" rows="10" maxlength="256" value=""></textarea>
                                            </div>
                                        </td>
                                        <td>
                                            <input  name="voteItemList[1][itemIndex]"  type="hidden" value="" maxlength="128"/>
                                            <img class="addItem" src="${pageContext.request.contextPath}/images/add.png" />
                                            <img class="delItem" src="${pageContext.request.contextPath}/images/sub.png" />
                                            <img class="downItem" src="${pageContext.request.contextPath}/images/up.png" />
                                            <img class="downItem" src="${pageContext.request.contextPath}/images/down.png" />
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </td>
                        </tr>
                    </table>
                </div>
                <div style="text-align: center;">
                </div>
        </div>
        <div class="div-bottom"><button type="submit">创建</button></div>
        <div class="div-bottom-cancel"></div>
    </div>
    </form>
    <script>
        //打开窗口
        function openWindow(url,width,height,name,options){
            var left = (screen.width-width)/2;
            var top = (screen.height-height)/2;
            if(options ==null)
                options ="";
            var newWin =  window.open(url,name,"left=" +left+",top="+top+",width="+width+",height="+height+","+options);
            newWin.focus();
            return newWin;

        }
        $(document).ready(function(){

            $("#selectStaff").click(function(){
                var checkedTreeId = $("#checkedTreeId").val();
                var selectedOriStaffId = $("#selectedOriStaffId").val();
                var selectedOriStaffName = $("#selectedOriStaffName").val();
                var param = "?checkedTreeId="+checkedTreeId+"&selectedOriStaffId="+selectedOriStaffId +"&selectedOriStaffName="+selectedOriStaffName;
                //选择参与人
                var href = contextPath + "/staff/selectStaff"+param;
                href = encodeURI(href);
                openWindow(href,500,400,"选择参与人1","location=no,menubar=no,resizable=no,scrollbars=no,status=no,titlebar=no,toolbar=no");
            })
            //表格操作
            /* //排序
             var myTextExtraction = function(node){
             return node.childNodes[0].value;
             }
             $("#voteItem").tablesorter( {
             //第一行排序,其它不排序，不显示图标
             headers:{
             1:{sorter: false},
             2:{sorter: false},
             3:{sorter: false},
             },
             sortList: [[0,0]],
             //猎取td内，input组件的value值
             textExtraction: myTextExtraction
             } );*/
            //选项文字值变更要更新数据模型
            $(document).delegate(".VoteItemChange", "change", function() {
                $("#voteItem").trigger("update");
            });
            //增加
            var i=1;
            var j=2;
            $(document).delegate(".addItem", "click", function() {
                ++i;
                $('<tr><td><input name="voteItemList['+i+'][voteItem]" type="text" value="" maxlength="128" class="VoteItemChange"/></td><td><input name="voteItemList['+i+'][isHavePicture]" type="checkbox" class="selectPic"/>&nbsp;<img src="${pageContext.request.contextPath}/images/pic.png" style="display: none" data-remodal-target="pic-modal-'+i+'"/><div class="remodal popDiv" data-remodal-id="pic-modal-'+i+'" style="width: 430px;height: 200px"><button data-remodal-action="close" class="remodal-close"></button><input type="hidden" name="voteItemIndex" value="'+i+'"/><input class="selectFile" type="file" id="picFile'+i+'" name="picFile"/><input type="hidden" name="voteItemList['+i+'][picturePath]" value=""/><div><img src="" id="showPic-'+i+'" style="display: none"/></div></div></td><td><img class="inputDesc" src="${pageContext.request.contextPath}/images/desc.png" data-remodal-target="desc-modal-'+i+'"/><div class="remodal popDiv" data-remodal-id="desc-modal-'+i+'" style="width: 500px"><button data-remodal-action="close" class="remodal-close"></button><textarea name="voteItemList['+i+'][itemDesc]" cols="50" rows="10" maxlength="256" value=""></textarea></div></td><td><input  name="voteItemList['+i+'][itemIndex]"  type="hidden" value="" maxlength="128"/><img class="addItem" src="${pageContext.request.contextPath}/images/add.png" /> <img class="delItem" src="${pageContext.request.contextPath}/images/sub.png" />&nbsp;<img class="downItem" src="${pageContext.request.contextPath}/images/up.png" />&nbsp;<img class="downItem" src="${pageContext.request.contextPath}/images/down.png" /></td></tr>')
                        .insertAfter( $(this).parent().parent());
                //触发update事件，更新排序缓存数据
    //                $("#voteItem").trigger("update");
                //增加最多与最少项
                ++j;
                $("#minItem").append('<option value="'+j+'">'+j+'</option>');
                $("#maxItem").append('<option value="'+j+'">'+j+'</option>');
                //重新绑定弹出层事件
                $.initRemodal();

            });
            //减少
            $(document).delegate(".delItem", "click", function() {
                if($("#voteItem").find("tr").length < 4){
                    alert('至少2项！');
                    return;
                }
                $(this).parent().parent().remove();
                //触发update事件，更新排序缓存数据
    //                $("#voteItem").trigger("update");
                //增加最多与最少项
                $("#minItem option:last").remove();
                $("#maxItem option:last").remove();
                --j;
            });
            //上移
            $(document).delegate(".upItem", "click", function() {
                $(this).parent().parent().prev("tr").before($(this).parent().parent());
            });
            //下移
            $(document).delegate(".downItem", "click", function() {
                $(this).parent().parent().next("tr").after($(this).parent().parent());
            });

            //选择图片
            $(document).delegate(".selectPic", "change", function() {
                $(this).siblings("img").toggle();
                if($(this).is(":checked")){
                    $(this).siblings("img").click();
                }
            });

            //文件选中
            $(document).delegate(".selectFile", "change", function() {
                //显示图片
                var imgPath = $(this).siblings("[name*='picturePath']");
                var voteItemIndex = $(this).siblings("[name='voteItemIndex']").val();
                var imgShow = $("#showPic-"+voteItemIndex);
                ajaxFileUpload(this, imgPath, imgShow);
            });

            //弹出关闭
            $(document).delegate(".remodal", "closing", function(e) {
                //获得索引
                var index = $(this).children("[name='voteItemIndex']").val();
                //获得图片复选框对象
                var checkboxSelector = "[name='voteItemList["+index+"][isHavePicture]']"
                var picFileSelector = "#picFile"+ index;
                if(isEmpty($(picFileSelector).val())){
                    $(checkboxSelector).click();
                }
            });


            //上传图片
            function ajaxFileUpload(file, imgPath,imgShow) {
                $.ajaxFileUpload({
                    url: contextPath+'/vote/uploadPic',
                    type: 'post',
                    secureuri: false, //一般设置为false
                    fileElementId: file.id, // 上传文件的id、name属性名
                    dataType: 'text/html', //返回值类型，一般设置为json、application/json
                    success: function(data, status){
                        //设置图片路径
                        imgPath.val(data);
                        imgShow.attr("src","${pageContext.request.contextPath}/" + data);
                        imgShow.show();
                        alert("上传图片成功！");
                    },
                    error: function(data, status, e){
                        alert(e);
                    }
                });
            }
            //实时检验
            var oldVal;
            $("#minItem,#maxItem").click(function(){
                oldVal = $(this).val();
            })
            $("#minItem,#maxItem").change(function(event){
                var mix = $("#minItem").val();
                var max = $("#maxItem").val();
                if(mix > max){
                    //返回原值
                    $(this).val(oldVal);
                    alert("[最少项]不能大于[最大项]");
                    return false;

                }
            })
            //投票主题：实时显示字数
            $("#voteTheme").keyup(function(){
                var length = $('#voteTheme').val().length;
                $("#themeLen").text(length);
            });
            //日期选择
            $( "#finishDate" ).datepicker({
                dateFormat: "yy-mm-dd",
                minDate: -0,
            });
            $( "#finishDate" ).datepicker({ minDate: -20, maxDate: "+1M +10D" });
            $( "#finishDate" ).datepicker( "setDate", new Date());//设置当前日期
            //富文本编辑
            var editor = CKEDITOR.replace("voteDesc");
            $(document).delegate("#descLabel", "click", function() {
                if($(this).text() == "添加详细说明"){
                    $(this).text("隐藏详细说明")
                }else{
                    $(this).text("添加详细说明")
                };
                $("#descDiv").toggle();
            });
            //单选项与多选项显示与隐藏
            $(document).delegate("#isMultiSelect1,#isMultiSelect2", "click", function() {
                $("#rangItem").toggle();
            });
            //提交表单
            $("form").submit(function(){
                if(validate()==false){
                    return false
                };
                //ckedit提交前值处理
                CKupdate();
                //弹出层不在form里面，但在body里面,所以不能用：var vote = JSONForms.encode(document.getElementById("voteForm"));
                var body = document.getElementsByTagName("body")[0];
                var vote = JSONForms.encode(body);
                var voteItemList = vote.voteItemList;
                //设置选项索引
                for(i=0;i < voteItemList.length;i++){
                    voteItemList[i].itemIndex = i;
                }
                console.log(JSON.stringify(vote));
                $.ajax({
                    type: "POST",
                    url: contextPath + "/vote/createVote",
                    data: JSON.stringify(vote),//将JavaScript对象转为与之对应的格式完好的JSON字符串
                    dateType:"json",//服务器返回值类型
                    contentType: "application/json; charset=utf-8",//发送信息至服务器时内容编码类型
                    async: false,
                    success: function(result) {
                        var result = $.parseJSON(result);//将格式完好的JSON字符串转为与之对应的JavaScript对象
                        if(result.success){
                            alert(result.message);
                        }else{
                            alert(result.message);
                        }
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
//                        alert(jqXHR.responseText);
//                        alert(jqXHR.status);
//                        alert(jqXHR.readyState);
//                        alert(jqXHR.statusText);
//                        alert(textStatus);
//                        alert(errorThrown);
                        alert("error:" + jqXHR.responseText);
                    }
                });
                return false;
            });
        });

        //检验
        function  validate(){
            //检验
            if(isEmpty($("#voteTheme").val())){
                alert("[投票主题]不能为空");
                $("#voteTheme").focus();
                return false;
            }

            if(haveRepeat("voteItem","[voteItem]")==true){
                alert("[选项文字]不允许重复!");
                return false;
            }
            if(haveEmpty("voteItem","[voteItem]")==true){
                alert("[选项文字]不允许为空!");
                return false;
            }
            return true;
        }

        function openDialog(selector) {
            Avgrund.show( selector );
        }
        function closeDialog() {
            Avgrund.hide();
        }
        //处理CKEDITOR的值
        function CKupdate() {
            for (instance in CKEDITOR.instances)
                CKEDITOR.instances[instance].updateElement();
        }
        //设置参与人
        function setRelStaffInfo(relStaffId,relStaffName,checkedTreeId,selectedOriStaffId,selectedOriStaffName){
            $("#relStaffId").val(relStaffId);
            $("#relStaffName").val(relStaffName);
            $("#checkedTreeId").val(checkedTreeId);
            $("#selectedOriStaffId").val(selectedOriStaffId);
            $("#selectedOriStaffName").val(selectedOriStaffName);
        }
    </script>
</body>
</html>
