function Staff(type,id,name,pid){
    //选择类型
    this.type = type;
    this.staffSuffix = "staff";
    //树配置
    this.id = id;
    this.name = name;
    this.pid = pid
    this.setting = {
        view: {
            selectedMulti: false
        },
        check: {
            enable: true,
            autoCheckTrigger:false
        },
        data: {
            simpleData: {
                enable: true,
                idKey: this.id,
                pIdKey: this.pid,
            }
        },
        callback: {
            onCheck: this.zTreeOnCheck
        }
    };
    //已选择员工
    this.selectedStaffId = "";
    //页面Id
    this.treeId = this.type + "_tree";
    this.searchListId = this.type + "_search_list";
    //页面选择器
    this.typeSelector = "#" + this.type;
    this.staffSelector = this.typeSelector + "_staff_List";
    this.staffListSelector = this.typeSelector + "_staff_List li";
    this.treeIdSelector = this.typeSelector + "_tree";
    this.searchSelector = this.typeSelector + "_search";
    this.searchDivSelector = this.typeSelector + "_search_div";
    this.searchListSelector = this.typeSelector + "_search_list";
    //操作
    this.delSelector = this.typeSelector + "_del";
    this.clearSelector = this.typeSelector + "_clear";
    //获得ajax的url
    this.getUrl = function(){
        if(type == "org"){
            return $("#projectContext").val()+"/warning/showOrgStaffTreeNodes";
        }
    }

}
Staff.prototype = {
    //初始化
    initEvent:function(){
        var _self = this;
        //增加背景色与鼠标手形
        $(_self.typeSelector).delegate(_self.staffListSelector, "mouseover", function() {
            var _this = $(this);
            _this.addClass("selectedItem");
            _this.css("cursor","pointer");
        });
        //删除背景色
        $(_self.typeSelector).delegate(_self.staffListSelector, "mouseout", function() {
            var _this = $(this);
            var id = _this.attr("id");
            if(_self.selectedStaffId != id){//
                _this.removeClass("selectedItem");
            }
        });
        //选中
        $(_self.typeSelector).delegate(_self.staffListSelector, "click", function() {
            var _this = $(this);
            var id = _this.attr("id");
            _this.parent().children().removeClass("selectedItem");
            _this.addClass("selectedItem");
            _self.selectedStaffId = id;
        });
        //查询
        $(_self.searchSelector).keyup(function(){
            var val = $(this).val();
            _self.searchNode(val);
        });
        //隐藏
        $(document).click(function (e) {
            var curId = $(e.target).attr("id");
            if(curId != _self.searchListId){
                $(_self.searchDivSelector).hide();
            }
        });
        //查询勾选
        $(_self.searchListSelector).change(function(){
            var zTree = $.fn.zTree.getZTreeObj(_self.treeId);
            $(_self.searchSelector).val("");
            var idVal = $(this).val()[0];//返回的是数组
            var nodes = zTree.getNodesByParam(_self.id,idVal,null);
            $(nodes).each(function(index, element){
                zTree.checkNode(element, true, true,true);
            })
        })
        //删除
        $(_self.delSelector).click(function(){
            var zTree = $.fn.zTree.getZTreeObj(_self.treeId);
            var id = _self.selectedStaffId;
            var nodes = zTree.getNodesByParam(_self.id,id,null)[0];
            zTree.checkNode(nodes, false, true,true);
        })
        //清空
        $(_self.clearSelector).click(function(){
            $(_self.staffListSelector).empty();
            var zTree = $.fn.zTree.getZTreeObj(_self.treeId);
            var nodes = _self.getRoots(_self.treeId);
            $(nodes).each(function(index, element){
                zTree.checkNode(element, false, true,true);
            })
        })
    },
    zTreeOnCheck :function(event, treeId, treeNode){
        var type = treeId.substring(0,3);
        var _self;
        var _other;
        //关联操作
        if(type == "org"){
            _self = orgStaff;
            _other = orgStaff;
        }
        //触发相同ID的
        _self.loopDealNodesKeyId(treeId, treeNode);
        //勾选或取消
        _self.showCheckNodes(treeId);
        //级联处理
        _self.cascadingDeal(treeNode,_other);
        //已选择列表重新渲染
        _other.showCheckNodes(_other.treeId);
    },
    //级联处理
    cascadingDeal:function(treeNode,_other){
        var _self = this;
        if(treeNode.isParent){
            var childNodes = treeNode.children;
            $(childNodes).each(function(index, element){
                _self.cascadingDeal(element,_other);
            })
        }else{
            //为叶子节点
            _self.cascadingLeafDeal(treeNode,_other);
        }
    },
    //处理叶子节点
    cascadingLeafDeal:function(treeNode,_other){
        var _self = this;
        var zTree = $.fn.zTree.getZTreeObj(_other.treeId);
        var nodes = zTree.getNodesByParam(_other.id,treeNode[_self.id],null);
        $(nodes).each(function(index, element){
            if(element.checked != treeNode.checked){
                zTree.checkNode(element, treeNode.checked, true,false);//不能触发事件
            }
        });
    },
    //显示已选中列表
    showCheckNodes:function(treeId){
        var _self = this;
        $(_self.staffSelector).empty();
        var nodes = _self.getRoots(treeId);
        $(nodes).each(function(index, element){
            _self.addNodes(element);
        })
    },
    //获取根节点
    getRoots:function(treeId) {
        var zTree = $.fn.zTree.getZTreeObj(treeId);
        //返回根节点集合
        var nodes = zTree.getNodesByFilter(function (node) { return node.level == 0 });
        return nodes
    },
    //增加所有节点
    addNodes:function(treeNode){
        var _self = this;
        if(treeNode.isParent){
            if(treeNode.check_Child_State == 2){
                _self.addNode(treeNode);
            }else{
                var childNodes = treeNode.children;
                $(childNodes).each(function(index, element){
                    _self.addNodes(element);
                })
            }
        }else {
            if(treeNode.checked){
                _self.addNode(treeNode);
            }
        }
    },
    //增加节点
    addNode:function(treeNode){
        var _self = this;
        if(!_self.isSelected(treeNode)){
            var li="<li id='"+treeNode[_self.id]+"' style='padding-left: 10px;padding-top: 10px;'>"+treeNode[_self.name]+"</li>";
            $(_self.staffSelector).append(li);
        }
    },
    //是否列表中已存在
    isSelected:function(treeNode){
        var _self = this;
        var isSelected = false;
        var zTree = $.fn.zTree.getZTreeObj(_self.treeId);
        var nodes = zTree.getNodesByParam(_self.id,treeNode[_self.id],null);
        $(nodes).each(function(index, element){
            if(element.tId != treeNode.tId){
                if(element.checked){
                    //其它节点的父节点已全选中
                    if(element.getParentNode().check_Child_State ==2){
                        isSelected = true;
                    }else {
                        if(_self.isHaveAdd(element)){//如果其它节点为子节点，如果已加到列表中，就不用加了
                            isSelected = true;
                        }
                    }
                }
            }
        })
        return isSelected;
    },
    //是否右边有相同的节点
    isHaveAdd:function(treeNode){
        var _self = this;
        var isHaveAdd = false;
        var nodes = $(_self.staffListSelector);
        $(nodes).each(function(index, element){
            if(element.id == treeNode[_self.id]){
                isHaveAdd = true;
            }
        })
        return isHaveAdd;
    },
    //循环处理相同keyId
    loopDealNodesKeyId:function(treeId, treeNode){
        var _self = this;
        if(treeNode.isParent){
            var childNodes = treeNode.children;
            $(childNodes).each(function(index, element){
                _self.loopDealNodesKeyId(treeId,element);
            })
        }else{
            _self.dealNodesKeyId(treeId,treeNode,treeNode.checked);
        }
    },
    //处理相同的keyId
    dealNodesKeyId:function(treeId,treeNode,checked){
        var _self = this;
        var zTree = $.fn.zTree.getZTreeObj(treeId);
        var nodes = zTree.getNodesByParam(_self.id,treeNode[_self.id],null);
        $(nodes).each(function(index, element){
            if(checked){//选中处理
                if(!element.checked){//没选中的选中
                    zTree.checkNode(element, true, true,false);
                }
            }else{//取消处理
                if(element.checked){//选中的取消
                    zTree.checkNode(element, false, true,false);//不触发onCheck事件
                }
            }

        })
    },
    //查询
    searchNode :function(searchStr){
        var _self = this;
        var zTree = $.fn.zTree.getZTreeObj(_self.treeId);
        $(_self.searchListSelector).empty();
        //获得符合条件的节点
        var nodeList = zTree.getNodesByParamFuzzy(_self.name, searchStr);
        //处理每个节点
        $(nodeList).each(function(index,element){
            var id=element[_self.id];
            var name=element[_self.name];
            var isExist = false;
            //判断是否已加入相同的id
            $(_self.searchListSelector + " option").each(function(){
                var optVal = $(this).val();
                if(id == optVal){
                    isExist = true;
                }
            })
            //不存在则加入
            if(!isExist){
                var opt="<option value='"+id+"'>"+name+"</option>";
                $(_self.searchListSelector).append(opt);
            }
        });
        //显示与隐藏
        if(searchStr==""){
            $(_self.searchDivSelector).hide();
        }else{
            $(_self.searchDivSelector).show();
        }
    },
    //异步加载树
    loadTree:function(){
        var _self = this;
        var treeNodes = new Array();
        var url = _self.getUrl();
        $.ajax({
            async: true,
            type: "GET",
            dataType: "json",
            url:  url,
            success: function(result) {
                if(result.success){
                    var data = result.data;
                    jQuery.each(data, function(i, item) {
                        treeNodes.push(
                            new StaffNode( item.id + "_" + item.type,
                                item.name,
                                item.pid + "_" + item.parentType,
                                item.type,
                                item.parentType,
                                item.isParent
                            ));
                    });
                    $.fn.zTree.init($(_self.treeIdSelector), _self.setting, treeNodes);
                }else{
                    alert(result.message);
                }
            },
            error: function() {
                alert('Ajax Error!');
            }
        });
    }


}
//树节点
function StaffNode(id,name, pid, type, parentType, isParent) {
    this.id = id;
    this.name = name;
    this.pid = pid;
    this.type = type;
    this.parentType = parentType;
    this.isParent = isParent;
}
