//全局参数contextPath
var pathName = document.location.pathname;
var index = pathName.substr(1).indexOf("/");
var contextPath = pathName.substr(0,index+1);

$(function () {
    var useragent =  navigator.userAgent;
    if(useragent.indexOf('MSIE') != -1){
        var tt = useragent.substring(useragent.indexOf('MSIE') + 5 );//MSIE XX.0;
        var version = tt.substring(0,tt.indexOf(".0;"));
        if(version < 9){
            window.noHtml5 = true;
            //alert('当前IE版本过低,只支持IE9及以上版本!');
            //return;
        }
    }
    //全局ajaxError事件
    $.ajaxSetup({
        error: function(jqXHR, textStatus, errorThrown){
            alert(jqXHR.responseText);
        },
        success:function(){

        },
        beforeSend:function(){

        },
        complete:function(){

        }
    });
    //全局图片载入失败事件
    $('img').error(function(){
        $(this).attr('src','../../images/pic.png');
    })

    //日期format方法
    Date.prototype.pattern=function(fmt) {
        var o = {
            "M+" : this.getMonth()+1, //月份
            "d+" : this.getDate(), //日
            "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时
            "H+" : this.getHours(), //小时
            "m+" : this.getMinutes(), //分
            "s+" : this.getSeconds(), //秒
            "q+" : Math.floor((this.getMonth()+3)/3), //季度
            "S" : this.getMilliseconds() //毫秒
        };
        var week = {
            "0" : "/u65e5",
            "1" : "/u4e00",
            "2" : "/u4e8c",
            "3" : "/u4e09",
            "4" : "/u56db",
            "5" : "/u4e94",
            "6" : "/u516d"
        };
        if(/(y+)/.test(fmt)){
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
        }
        if(/(E+)/.test(fmt)){
            fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);
        }
        for(var k in o){
            if(new RegExp("("+ k +")").test(fmt)){
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
            }
        }
        return fmt;
    };

    //这个控件只载入一次。
    CKEDITOR.replace("voteDesc");

    //页面对象初始化
    vote.init();

    //保持session的定时器
    var ii = setInterval(function(){
        $.get(contextPath+"/session/freshSession",function(data,status) {
            if (status != "success") {
                alert("Service Error");
            }
        });
    },1000 * 60 * 25);//1000为1秒钟
});

var vote = {
    vote1CommitData:{},
    staffTree:null,
    init:function(){
        this.initEvernt();
    },
    initEvernt:function(){
        //tab页的点击事件，获取tab标签
        $('#myTab a').off('click').on('click',function (e) {
            e.preventDefault();
            //这里做数据动态载入
            var _this = $(this);
            if(_this.attr('href') == '#vote1'){
                vote.vote1TabEvent();
            }else{
                vote.vote2TabEvent(_this.attr('href'));
            }
        });

        //页面载入的时候触发第一个tab的点击事件
        $('#myTab a:first').trigger('click');
    },
    vote1TabEvent:function(){
        var _this = this;
        //富文本编辑器
        $('#m-detailOptions .m-addDetail').hide();

        $('.m-showadddetail').off('mouseup').on('mouseup',function(){
            if($('#m-detailOptions .m-addDetail').is(':hidden')){
              $('#m-detailOptions .m-addDetail').show();
            }else{
              $('#m-detailOptions .m-addDetail').hide();
            }
        })

        //日期控件
        $( "#finishDate" ).datepicker({
          dateFormat: "yy-mm-dd",
          minDate: -0
        });
        $( "#finishDate" ).datepicker({ minDate: -20, maxDate: "+1M +10D" });
        $( "#finishDate" ).datepicker( "setDate", new Date());//设置当前日期

        $('input[name="optionsRadiosinline"]').off('click').on('click',function(){
            if($(this).prop('id') == 'isMultiSelect'){
                $('#vote1 .m-itemLimit').show();
            }else{
                $('#vote1 .m-itemLimit').hide();
            }
        })

        $('#vote1 input[name="voteTheme"]').off('blur').on('blur',function(){
            var len = $(this).val().length;
            if(len > 256){
                $(this).val(len.substring(0,256));
                alert('输入主题长度过长!');
                $('#vote1 .themeLen').text('256');
            }else{
                $('#vote1 .themeLen').text(len);
            }
        });
        //选项表格的事件
        _this.vote1TabEventTableImg();

        //提交按钮点击事件
        $('#vote1 .m-submit').off('mouseup').on('mouseup',function(e){
          if(vote.validateVote1()){
              vote.submitVote1();
          }
        });

        $('#vote1 .m-staffBtn').off('mouseup').on('mouseup',function(){
            //参与人树形只载入一次够了
            if(!vote.staffTree){
                vote.loadStaffTree();
            }
            $('#m-staffModal').modal('show');
        })
    },
    loadStaffTree:function(){
        vote.staffTree = $('#m-staffModal .m-staffTree').load(contextPath + '/html/vote/staff.html',function(){
            //按组织框架
            orgStaff.initEvent();
            orgStaff.loadTree();
            //按群组
            roomStaff.initEvent();
            roomStaff.loadTree();
            //确认
            $("#confirmSelectStaff").click(function(){
                var staff;//当前员工对象
                var staffIdList="";//员工Id
                var staffNameList="";//员工姓名
                var type = $("#staffSelect .tab-pane.fade.active.in").attr("id");//选择类型
                if(type == selectType[0]){
                    staff = orgStaff;
                }else{
                    staff = roomStaff;
                }
                var zTree = $.fn.zTree.getZTreeObj(staff.treeId);
                var nodes = zTree.getCheckedNodes(true);//所有已选中的员工节点
                $(nodes).each(function () {
                    if(!this.isParent && this.type == staff.staffSuffix){
                        if(staffIdList.indexOf(";"+this[staff.id]) == -1){//去重
                            staffIdList = staffIdList + this[staff.id] + ";";
                            staffNameList = staffNameList + this[staff.name] + ";";
                        }
                    }
                })

                //显示已选择列表
                var selectedIdList = "";//树ID不重复
                var selectedNameList = "";
                var liList = $(staff.staffListSelector);
                $(liList).each(function(index, element){
                    if(selectedIdList.indexOf(this[staff.id]) == -1){
                        selectedIdList = selectedIdList + this[staff.id] + ";";
                        selectedNameList = selectedNameList + $(this).text() + ";";
                    }
                })
                $('#vote1 .m-staffVal').data('staffIdList',staffIdList.replace(new RegExp(/_staff/g),''));
                $('#vote1 .m-staffVal').data('staffNameList',staffNameList);
                $('#vote1 .m-staff').val(selectedNameList);

                $('#m-staffModal').modal('hide');
            })
        });
    },
    vote1TabEventTableImg:function(){
        //图标的悬停点击事件
        $('#m-detailOptions img')
        .off('mouseover').on('mouseover',function (e) {
            $(this).attr('src',$(this).attr('src').replace('.png','_xt.png'));
        })
        .off('mouseout').on('mouseout',function (e) {
            $(this).attr('src',$(this).attr('src').replace('_xt.','.'));
        })
        .off('mousedown').on('mousedown',function (e) {
            $(this).attr('src',$(this).attr('src').replace('_xt.','_dj.'));
        })
        .off('mouseup').on('mouseup',function (e) {
            e.preventDefault();
            $(this).attr('src',$(this).attr('src').replace('_dj.','.'));
            //点击触发事件在这里定义
            var _thisImg = $(this);
            var _thisTr = $(this).parents('tr')
            var tp = _thisImg.attr('tp');
            if(tp == 'del'){
                var total = _thisImg.parents('tbody').find('tr').length;
                if(total == 2){
                    alert('至少应该有两个选项!')
                }else{
                    if(total > 2) {
                        $('#vote1 select[name="minItem"]').find('option:last').remove();
                        $('#vote1 select[name="maxItem"]').find('option:last').remove();
                    }
                    _thisImg.parents('tr').remove();
                }
            }else if(tp == 'add'){
                var emptyTr = $('<tr></tr>');
                var nowTime = new Date().getTime();
                $('<td><input type="text" class="form-control input-sm" placeholder="请输入选项内容" size="10" ><div class="m-data"></div></td>'
                    +'<td>'
                    +'<input type="checkbox" id="m-option'+nowTime+'"  />&nbsp;'
                    +' <img tp="uploadImg" src="../../images/pic.png"/></td>'
                    +'<td><img tp="editDetail" src="../../images/desc.png"/></td>'
                    +'<td>'
                    +'<img tp="add" src="../../images/add.png"/> '
                    +'<img tp="del" src="../../images/sub.png"/> '
                    +'<img tp="down" src="../../images/down.png"/> '
                    +'<img tp="up" src="../../images/up.png"/> '
                    +'</td>').appendTo(emptyTr);

                _thisImg.parents('tr').after(emptyTr);
                //刷新最少跟最多两个选择框的值
                var ll = _thisImg.parents('tbody').find('tr').length;
                $('#vote1 select[name="minItem"]').append('<option value ="'+ll+'">'+ll+'</option>');
                $('#vote1 select[name="maxItem"]').append('<option value ="'+ll+'">'+ll+'</option>');
                //插入的行要做事件reload
                vote.vote1TabEventTableImg();
            }else if(tp == 'up' ){
                if (_thisTr.index() != 0) {
                    _thisTr.prev().before(_thisTr);
                }
            }else if(tp  == 'down') {
                var trLength = _thisImg.parents('tbody').find('tr').length;
                if (_thisTr.index() != trLength - 1) {
                    _thisTr.next().after(_thisTr);
                }
            }else if(tp == 'editDetail'){
                vote.showDetailModal(_thisImg);
            }else if(tp == "uploadImg"){
                vote.showImgUpModal(_thisImg);
            }
            else if(tp == "sort"){
            }
        });

        $('#m-optionTable input:checkbox').off('change').on('change',function(){
            if($(this).prop('checked')){
                vote.showImgUpModal($(this).parent().find('img'));
            }
        })
    },
    showDetailModal:function(imgObj){
        //显示输入选项详情modal
        var dataDiv = imgObj.parents('tr').find('div');
        var data = dataDiv.data('descData');
        $('#myModal textarea').val(data);
        $('#myModal').modal({backdrop: 'static', keyboard: false});

        $('#myModal').off('hide.bs.modal').on('hide.bs.modal',function(e){
            //写缓存值 写入
            dataDiv.data('descData',$('#myModal textarea').val());
        });
    },
    showImgUpModal:function(imgObj){
        //显示上传图片modal
        if(imgObj.parent().find('input:checkbox').prop('checked')){
            $('#m-imgUpModal').modal({backdrop: 'static', keyboard: false});
            $('#picFile1').val('');

            $('#m-imgUpModal .m-preView').hide();

            $('#picFile1').off('change').on('change',function(evt){
                if (!window.FileReader) return;
                var files = evt.target.files;
                var f = files[0];

                if (!f.type.match('image.*')) {
                    alert('请上传图片!')
                    $('#picFile1').val('');
                    return;
                }
                var fsize = f.size;
                if(fsize > (10 * 1024 *1024)){
                    alert('请勿上传超过10M大小的图片!')
                    $('#picFile1').val('');
                    return;
                }

                var reader = new FileReader();
                reader.onload = (function(theFile) {
                    $('#m-imgUpModal .m-preView').show()
                    var fileSize = (Math.round(theFile.size * 100 / 1024) / 100).toString() + 'KB';
                    return function(e) {
                        // img 元素
                        $('#previewImage').attr('src',e.target.result) ;
                        $('#m-imgUpModal .m-preView').find('b').text(fileSize);
                        console.dir(e.target)
                    };

                })(f);
                reader.readAsDataURL(f);
            })

            //重新绑定上传图片事件，主要是图片对象发生改变
            $('#m-imgUpModal .m-btn-upload').off('mouseup').on('mouseup',function(){
                if($('#m-imgUpModal input').val()){
                    var imgVal = $('#m-imgUpModal input').val();
                    var suffix = imgVal.substring(imgVal.lastIndexOf('.')+1).toLowerCase();
                    if(suffix == 'png' || suffix == 'jpg'){
                        vote.ajaxFileUpload('#m-imgUpModal',imgObj,$('#m-imgUpModal input').attr('id'))
                    }else{
                        alert('只能上传png,jpg格式图片!');
                    }
                }else{
                    alert('请上传图片!')
                }
            })

        }
    },
    ajaxFileUpload:function(modalId,imgObj,id){
        $.ajaxFileUpload({
            url: contextPath + '/vote/uploadPic',
            type: 'post',
            secureuri: false, //一般设置为false
            fileElementId: id, // 上传文件的id、name属性名
            dataType: 'application/json', //返回值类型，一般设置为json、application/json
            success: function(data, status){
                $('#m-imgUpModal').modal('hide');
                imgObj.parents('tr').find('div').data('imgPath',data);
                vote.showBiggerImage(imgObj,data);
            }
        });
    },
    validateVote1:function(){
        //验证vote1上提交表单
        var vote1Obj = {};
        this.vote1CommitData = {};

        vote1Obj.relStaffId = $('#vote1 .m-staffVal').data('staffIdList');
        vote1Obj.relStaffName = $('#vote1 .m-staffVal').data('staffNameList');
        if(!vote1Obj.relStaffId || vote1Obj.relStaffId == ''){
            alert('请选择参与者!');
            return false;
        }

        var voteTheme = $('#vote1 input[name="voteTheme"]').val();
        if(!voteTheme || $.trim(voteTheme) == ''){
            alert('主题不能为空!');
            return false;
        }else{
            vote1Obj.voteTheme = voteTheme;
        }

        //详细说明取值
        for (instance in CKEDITOR.instances){
            CKEDITOR.instances[instance].updateElement();
        }

        var voteDesc = $('#voteDesc').val();
        if(!voteDesc || $.trim(voteDesc) == ''){
            alert('详细说明不能为空!');
            return false;
        }else{
            vote1Obj.voteDesc = voteDesc;
        }

        vote1Obj.isMultiSelect = $('#isMultiSelect').prop('checked');
        vote1Obj.minItem = $('#vote1 [name="minItem"]').val();
        vote1Obj.maxItem = $('#vote1 [name="maxItem"]').val();

        if(vote1Obj.isMultiSelect && (vote1Obj.minItem > vote1Obj.maxItem)){
            alert('选项设置错误,最少选项不能大于最多选项!');
            return false;
        }

        var finishDate = $('#vote1 input[name="finishDate"]').val();
        if(!finishDate || $.trim(finishDate) == ''){
            alert('请选择结束时间!');
            return false;
        }else{
            vote1Obj.finishDate = finishDate;
        }

        vote1Obj.isAnonymous = $('#vote1 [name="isAnonymous"]').prop('checked');
        vote1Obj.isPublic = $('#vote1 [name="isPublic"]').prop('checked');

        var voteItemList = [];
        var checkFlag = true;
        //行对象遍历，属性检查

        $.each($('#vote1 tbody').find('tr'),function(index){
            var itemObj = {};
            itemObj.itemIndex = index + 1;
            var voteItem = $(this).find('input[type="text"]');
            if(!voteItem || voteItem.val() == ''){
                alert('请输入选项值!');
                voteItem.focus();
                checkFlag = false;
                return false;
            }else{
                itemObj.voteItem = voteItem.val();
            }
            var isHavePicture = $(this).find('input[type="checkbox"]').prop('checked');
            itemObj.isHavePicture = isHavePicture;
            var picturePath = $(this).find('div').data('imgPath');
            if(isHavePicture && (!picturePath || picturePath == '')){
                alert('请上传图片!');
                checkFlag = false;
                return;
            }else{
                itemObj.picturePath = picturePath;
            }
            var itemDesc =  $(this).find('div').data('descData') ;
            itemObj.itemDesc = itemDesc;
            voteItemList.push(itemObj);
        });
        if(!checkFlag){
            return false;
        }else{
            vote1Obj.voteItemList = voteItemList;
            this.vote1CommitData = vote1Obj;
        }
        return true;
    },
    submitVote1:function(){
        //提交vote1数据
        var commitData = this.vote1CommitData;
        $.ajax({
            type: "POST",
            url:  contextPath + "/vote/createVote",
            data: JSON.stringify(commitData),//将JavaScript对象转为与之对应的格式完好的JSON字符串
            dateType:"json",//服务器返回值类型
            contentType: "application/json; charset=utf-8",//发送信息至服务器时内容编码类型
            async: false,
            success: function(result) {
                if(result.success){
                    alert(result.message);
                }else{
                    alert(result.message);
                }
            }
        });
    },
    vote2TabEvent:function( voteId){
        //vote2 3 4复用，传入href值
        $( voteId + ' .m-vote2Table').empty();
        $( voteId + 'm-pagging').empty();
        var findType= voteId.substring(voteId.length-1);
        $.ajax({
            type: "GET",
            url:  contextPath +"/vote/findVote",
            data: 'findType=' + (findType -1 ),
            dateType:"json",//服务器返回值类型
            contentType: "application/json; charset=utf-8",//发送信息至服务器时内容编码类型
            async: false,
            success: function(result) {
                if(result.success){
                    //载入数据成功，创建table 跟 分页
                    vote.initVote2Table(voteId,result.pageModel.records);
                    if(result.pageModel.totalPages && result.pageModel.totalPages!=0 ){
                        vote.intVote2Paging(voteId,result.pageModel);
                    }
                }else{
                    alert(result.message);
                }
            }
        });
    },
    initVote2Table:function(voteId,result){
        var  findType= voteId.substring(voteId.length-1);
        console.dir(result);
        $.each(result,function(index,item){
            var createDate = new Date(item.createDate);
            var finishDate = new Date(item.finishDate);
            var nowTime = new Date().getTime();
            var dataFmtPatten = 'yyyy-MM-dd HH:mm:ss';

            var voteStatus =  '';
            if(item.voteStatus == '00'){
                voteStatus = '未参与';
            }else if(item.voteStatus == '01'){
                voteStatus = '未投票';
            }else if(item.voteStatus == '02'){
                voteStatus = '已投票';
            }else if(item.voteStatus == '03'){
                voteStatus = '未完成';
            }else if(item.voteStatus == '04'){
                voteStatus = '已完成';
            }

            var tableHtml =
                '<blockquote>'
                +'<div class = "row">'
                +'<div class="col-xs-2">'
                +'<img  src="'+item.oriStaffAvatar+'" class="m-staffavatarimg"/>'
                +'<br>'
                +'<p>'+item.oriStaffName+'</p>'
                +'</div>'
                +'<div class="col-xs-6">'
                +'<ul class="list-unstyled m-fontsize14">'
                +'<li>'+item.voteTheme+'</li>'
                +'<li>开始时间：'+createDate.pattern(dataFmtPatten)+'</li>'
                +'<li>结束时间：'+finishDate.pattern(dataFmtPatten)+'</li>'
                +'<li>' + (item.isAnonymous?'匿名':'公开') +';' +(item.isMultiSelect?('多选;最少'+item.minItem+'项;最多'+item.maxItem+'项'):'单选') +'&nbsp;'
                +'<a  data-toggle="collapse" href="#'+nowTime+'" aria-expanded="false" aria-controls="'+nowTime+'">查看详情</a></li>'
                +'</ul>'
                +'</div>'
                +'<div class="col-xs-4">'
                +'<div class="panel panel-default m-fontsize14">'
                +'<div class="panel-heading">'
                + voteStatus;

            //如果是我发起的投票，显示可以删除的图标
            if(item.isMyOri){
                tableHtml = tableHtml + '<span class="glyphicon glyphicon-trash pull-right m-remove" aria-hidden="true"></span>';
            }

            tableHtml = tableHtml +'</div>'
                +'<div class="panel-body">'
                +'邀请人数：'+item.relVoteCnt+'人<br>'
                +'投票人数：'+item.votedCnt+'人'
                +'</div>'
                +'</div>'
                +'</div>'
                +'</div>'
                +'</blockquote>'
                +'<div class="collapse" id="'+nowTime+'">'
                +'<div class="well">'
                +'<div class="row"><div class="col-xs-2"><div><label>投票说明</label></div></div>'
                +'<div class="col-xs-8 m-voteItemTable" ><div>'
                +item.voteDesc;

            var itemTr = '';//选项图标界面的行列内容
            var itemList = [];//各选项名称
            var cntList = [];//各选项计数
            var itemFmt = {};//前端渲染对象
            //遍历选项list
            $.each(item.voteItemStatisticsList,function(ii,it){
                itemFmt[it._id] = it.voteItem;

                tableHtml += '<input class="m-voteItem" value="'+it._id +'" type="checkbox" ' + (it.isMySelect?' checked ':'') + (item.voteStatus!='01'?' disabled ':'') + '/>&nbsp;&nbsp;'
                    +  (it.isHavePicture ? ('<img  src="../../'+it.picturePath+'" class="m-itemimg"/>') : '')
                    + (it.voteItem?it.voteItem:'未输入描述');
                //最后一个前面要
                if(ii != item.voteItemStatisticsList.length){
                    tableHtml += '<br>'
                }

                /*选项汇总的进度条直接生成*/
                var processWidth = 0;
                if(item.votedCnt != 0){
                    processWidth = parseInt(it.selectCnt * 100 / item.votedCnt) ;
                }
                itemTr += '<tr>'
                    +'<td>'+it.voteItem+'</td><td>'+it.selectCnt+'</td>'
                    +'<td><div class="progress"><div class="progress-bar" role="progressbar" aria-valuenow="'+processWidth+'" aria-valuemin="0" aria-valuemax="100" style="width: '+processWidth+'%;">' +
                    '<span class="sr-only"></span></div>'+processWidth+'%</div>' +
                    '</td></tr>';

                /*柱状图跟饼图的数据获取*/
                itemList.push(it.voteItem);
                cntList.push(it.selectCnt);
            });

            tableHtml += '</div></div>';

            //如果状态是未投票，则生成未投票按钮
            if(item.voteStatus == '01'){
                tableHtml = tableHtml + '<div class="col-xs-2">'
                    +'<button type="button" class="btn btn-default btn-yealink m-voteBtn" >投票</button>'
                    +'</div>';
            }

            /*选项 柱状图 饼图*/
            tableHtml = tableHtml + '<div class="col-xs-12"><ul  class="nav nav-tabs">'
                + '<li class="active"><a href="#'+nowTime+'1" data-toggle="tab">表格</a></li>'
            //支持H5则显示图形
            if(!window.noHtml5){
                tableHtml = tableHtml + '<li><a href="#'+nowTime+'2" data-toggle="tab">饼图</a></li>'
                tableHtml = tableHtml + '<li><a href="#'+nowTime+'3" data-toggle="tab">柱状图</a></li>'
            }
            tableHtml = tableHtml + '</ul>'
                +'<div  class="tab-content"> '
                +'<div class="tab-pane fade in active" id="'+nowTime+'1">'
                +'<br>'
                +'<table class="table table-condensed m-optionTable">'
                +'<thead><tr><th class="m-voteitemtitle">选项</th><th>小计</th><th>比例</th></tr></thead>'
                +'<tbody>'
                + itemTr
                +'</tbody></table>'
                +'</div>'
            //支持H5则显示图形
            if(!window.noHtml5){
                tableHtml = tableHtml + '<div class="tab-pane fade" id="'+nowTime+'2">'
                    +'<div class="m-canvassize">'
                    +'<canvas id="m-chart1'+nowTime+'"></canvas>'
                    +'</div>'
                    +'</div>'

                tableHtml = tableHtml + '<div class="tab-pane fade" id="'+nowTime+'3">'
                    +'<div class="m-canvassize" >'
                    +'<canvas id="m-chart2'+nowTime+'"></canvas>'
                    +'</div>'
                    +'</div>'
            }
            tableHtml = tableHtml + '</div></div>';

            /*根据权限生成投票详情*/
            if(item.isMyOri ||  item.isPublic){
                tableHtml = tableHtml + '<div class="col-xs-12"><a href="#" class="m-voteDetailBtn yealinkGreen">显示投票详情</a></div>'
                +'<div class="col-xs-12 m-voteDetail m-hide">'
                +'<table class="table table-condensed m-voteDetailTable">'
                +'<thead><tr><th class="m-voteitemtitle" >投票人</th><th>投票时间</th><th>投票给</th></tr></thead>'
                +'<tbody>';
                $.each(item.voteStaffList,function(iii,iit){
                    tableHtml += '<tr><td>'  +(item.isAnonymous?'匿名':('<img  src="'+iit.avatar+'" class="m-avatarimg"/>'+iit.staffName))+'</td><td>'+ (new Date(iit.voteDate).pattern(dataFmtPatten))+'</td>';
                    var fmtedVoteItems = iit.voteItems;
                    for(var i in itemFmt){
                        //fmtedVoteItems.replaceAll(i+';',itemFmt[i]);
                        fmtedVoteItems = fmtedVoteItems.split(i+';').join(itemFmt[i]+';');
                    }
                    tableHtml += '<td>'+fmtedVoteItems+'</td>';
                    tableHtml += '</tr>';
                });
                tableHtml = tableHtml +'</tbody></table></div>';
            }

            tableHtml += '</div></div></div>';
            var itemObj = $(tableHtml);

            /* 对象事件绑定 */
            //提交投票按钮
            if(item.voteStatus == '01'){
                itemObj.find('.m-voteBtn').off('mouseup').on('mouseup',function(){
                    var itList = itemObj.find(".m-voteItem:checked");
                    if(!item.isMultiSelect){
                        if(itList.length != 1){
                            alert('只能选择一项!');
                            return;
                        }
                    }else{
                        if(itList.length < item.minItem || itList.length > item.maxItem){
                            alert('最少选择'+ item.minItem +'项,最多选择'+ item.maxItem +'项!');
                            return;
                        }
                    }
                    var itValue ="";
                    itList.each(function(){
                        itValue = itValue + $(this).val() + ";";
                    })
                    vote.submitVoteItem(voteId,{
                        "voteId" : item.voteId,
                        "voteItems":itValue
                    })
                })
            }
            //如果是我发起的或允许查看详情，则可显示投票详情
            if(item.isMyOri ||  item.isPublic){
                itemObj.find('.m-voteDetailBtn').off('mouseup').on('mouseup',function(){
                    itemObj.find('.m-voteDetail').toggle();
                })
            }
            //如果是我发起的投票，可以删除
            if(item.isMyOri){
                itemObj.find('.m-remove').off('mouseup').on('mouseup',function(){
                    if(!confirm('确定删除投票  ['+item.voteTheme+']?')){
                        return;
                    };
                    $.ajax({
                        type: "POST",
                        url: contextPath +"/vote/delVoting",
                        data: JSON.stringify({"voteId":item.voteId,"findType":findType}),
                        dateType:"json",
                        contentType: "application/json; charset=utf-8",
                        async: false,
                        success: function(data) {
                            alert("删除成功！");
                            //删除完成直接remove(),不刷新表格
                            itemObj.remove();
                            //不需要更新分页，点击tab的时候会载入一次
                            //if($(voteId +  ' .m-vote2Table > div').length == 0){
                            //    $( voteId + 'm-pagging').empty();
                            //}
                        }
                    });
                })
            }
            //图标的浮层显示
            if(itemObj.find('img')){
                vote.showBiggerImage(itemObj.find('img'));
            }
            //载入行
            itemObj.appendTo($(voteId + ' .m-vote2Table'));
            //载入后生成饼图跟柱状图
            if(!window.noHtml5){
                vote.genChart('m-chart1' + nowTime,'pie',itemList,cntList);
                vote.genChart('m-chart2' + nowTime,'bar',itemList,cntList);
            }
        })

    },
    intVote2Paging:function(voteId,result){
        var options = {
            bootstrapMajorVersion: 3, //版本
            size:'small',
            currentPage: result.pageNo, //当前页数
            totalPages: result.totalPages, //总页数
            itemTexts: function (type, page, current) {
                switch (type) {
                    case "first":
                        return "首页";
                    case "prev":
                        return "上一页";
                    case "next":
                        return "下一页";
                    case "last":
                        return "末页";
                    case "page":
                        return page;
                }
            },//点击事件，用于通过Ajax来刷新整个list列表
            onPageClicked: function (event, originalEvent, type, page) {
                var  findType= voteId.substring(voteId.length-1);
                $.ajax({
                    type: "GET",
                    url:  contextPath +"/vote/findVote",
                    data: 'findType=' + (findType -1 )+'&pageNo=' + page,
                    dateType:"json",//服务器返回值类型
                    contentType: "application/json; charset=utf-8",//发送信息至服务器时内容编码类型
                    async: false,
                    success: function(res) {
                        if(res.success){
                            $( voteId + ' .m-vote2Table').empty();
                            vote.initVote2Table(voteId,res.pageModel.records);

                            if(res.pageModel.totalPages && res.pageModel.totalPages!=0 ){
                                vote.intVote2Paging(voteId,res.pageModel);
                            }
                        }else{
                            alert(res.message);
                        }
                    }
                });
            }
        };
        $(voteId + 'm-pagging').bootstrapPaginator(options);
    },
    submitVoteItem:function(voteId,data){
        //进行投票
        $.ajax({
            type: "POST",
            url:  contextPath +"/vote/submitVoting",
            data: JSON.stringify(data),
            dateType:"json",
            contentType: "application/json; charset=utf-8",
            async: false,
            success: function(result) {
                //var result = jQuery.parseJSON(data);
                if(!result.success){
                    alert(result.message);
                }else{
                    alert('投票成功!');
                    $('#myTab a[href="'+voteId+'"]').trigger('click');
                }
            }
        });
    },
    genChart:function(canvasId,picType,itemList,cntList){
        //如果不支持H5，直接返回，不生成图标了
        if(window.noHtml5){
            return;
        }
        //生成饼图跟柱状图方法
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
            }
        });
    },
    showBiggerImage:function(imgObj,srcc){
        //浮层显示大图片
        var x = 22;
        var y = 20;
        imgObj.hover(function(e){
            $('#bigimage').find('img').attr('src',(srcc?srcc:$(this).attr('src')));
            $('#bigimage').show()
            $(this).stop().fadeTo('slow',0.5);
            widthJudge(e);
            $("#bigimage").fadeIn('fast');
        },function(){
            $(this).stop().fadeTo('slow',1);
            $("#bigimage").hide();
        });

        //鼠标在图片父节点内部移动，移动浮层显示的大图片
        imgObj.parent().mousemove(function(e){
            widthJudge(e);
        });

        //动态修改浮层大图片的位置
        function widthJudge(e){
            var marginRight = document.documentElement.clientWidth - e.pageX;
            var imageWidth = $("#bigimage").width();
            if(marginRight < imageWidth){
                x = imageWidth + 22;
                $("#bigimage").css({top:(e.pageY - y ) + 'px',left:(e.pageX - x ) + 'px'});
            }else{
                x = 22;
                $("#bigimage").css({top:(e.pageY - y ) + 'px',left:(e.pageX + x ) + 'px'});
            };
        }
    }
}

