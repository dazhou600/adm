var log, className = "dark";
	function beforeEditName(treeId, treeNode) {
		className = (className === "dark" ? "":"dark");
		var zTree = $.fn.zTree.getZTreeObj("navtree");
		zTree.selectNode(treeNode);
		setTimeout(function() {
			if (confirm("进入节点 -- " + treeNode.name + " 的编辑状态吗？")) {
				$("#edit-modal").modal();
				$("#editname").val(treeNode.name);
				$("#editadrs").val(treeNode.href);
			}
		}, 0);
		return false;
	}
	//绑定编辑节点函数
	$("#editSubmit").click(function(){
		 var treeObj = $.fn.zTree.getZTreeObj("navtree");
		 var nodes = treeObj.getSelectedNodes();
		 var nm = $("#editname").val();
		 var adrs = $("#editadrs").val();
         $.ajax({
             async: true,
            url:'/admin/cms/navctgr/edit/'+nodes[0].bid+','+nm+','+adrs,
             success: function (msg) {
                 if (msg.msg >0) {
             		getIninNavtreeDataByAjax();
                     alert('更新成功!');
                 } else {
                     alert('更新失败!');
                     window.location.reload();
                 }
               },
             error: function () {
            	 alert("失败！");
             }
         })
     });
	//删除节点
	function beforeRemove(treeId, treeNode) {
		className = (className === "dark" ? "":"dark");
		var zTree = $.fn.zTree.getZTreeObj("navtree");
		zTree.selectNode(treeNode);//??
		var flag=confirm("确认删除 节点 -- " + treeNode.name + " 吗？");
		 if(true==flag){
	         $.ajax({
	             async: true,
	            url:'/admin/cms/navctgr/del/'+treeNode.bid,
	            dataType : 'json',
	            success: function (msg) {
                     if (msg.msg === 666) {
                         alert('删除成功!');
                     } else {
                         alert('删除失败!');
                         window.location.reload();
                     }
	               },
	             error: function () {
	            	 alert("失败！");
	            	 window.location.reload();
	             }
	         });
	     }else{return false;}
	}  	
	//兄弟节点计数
	var cbrth;
	//增加节点
	function addHoverDom(treeId, treeNode) {
		var sObj = $("#" + treeNode.tId + "_span");
		//节点处于编辑名称状态或tId 对应的节点 JSON 数据对象已经存在
		if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
		var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
			+ "' title='add node' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		//获取父节点ID
		var nodes = $.fn.zTree.getZTreeObj("navtree").getNodesByParam("level",treeNode.level+1 , treeNode);
		cbrth = nodes.length ;
		var apenStr = "将在 <b>" + treeNode.name +"</b> 下 第 <b><select>";
		for(var i=1;i<cbrth+1;i++){
			apenStr += "<option>"+i+"</option>";
		}

		apenStr +="<option selected='selected'>末尾</option>";
		apenStr += "</select></b>";
		var btn = $("#addBtn_"+treeNode.tId);
		if (btn) btn.bind("click", function(){
						$("#modal-default").modal();
						$("#addInfo").html(apenStr);
		});
	};

	function removeHoverDom(treeId, treeNode) {
		$("#addBtn_"+treeNode.tId).unbind().remove();
	};
	//绑定增加函数
	$("#addSubmit").click(function(){
		 var treeObj = $.fn.zTree.getZTreeObj("navtree");
		 var nodes = treeObj.getSelectedNodes();
		 var pos = $("#addInfo>b>select>option:selected").val();
		 if(pos&&pos==="末尾"){
			 pos=cbrth+1;
		 }
	var reg = /^[0-9]+$/;
	//判断是否是正确数字
	if(reg.test(pos)){
         $.ajax({
             async: true,
            url:'/admin/cms/navctgr/add/'+nodes[0].bid+','+pos+','+$("#inputrname").val(),
             success: function (msg) {
                 if (msg.msg === 666) {
             		getIninNavtreeDataByAjax();
                     alert('增加成功!');
                 } else {
                     alert('增加失败!');
                     window.location.reload();
                 }
               },
             error: function () {
            	 alert("失败！");
             }
         })
			}else{alert("请输入正确数字!")}
     });

	//移动节点
	function beforeDrop(treeId, treeNodes, targetNode, moveType, isCopy){
		if(targetNode === null){
			alert("targetNode: 不能为空！")
			return false;
		}
		if(targetNode.name === "主页"){
			alert("'主页'不能有子节点，也不能移动！");
			return false;
		}
		var flag=confirm("确认移动"+treeNodes[0].name +"到"+ targetNode.name + " 吗？");
		 if(true==flag){
	         $.ajax({
	             async: true,
	            url:'/admin/cms/navctgr/mov/'+treeNodes[0].bid+'/'+targetNode.bid,
	            dataType : 'json',
	            success: function (msg) {
	            	//alert("MSG: "+msg.msg);
                     if (msg.msg === 666) {
                         alert('移动成功!');
                     } else {
                         alert('移动失败!');
                         window.location.reload();
                     }
	               },
	             error: function () {
	            	 alert("粗错啦！");
	            	 window.location.reload();
	             }
	         });
	     }else{return false;}
	}
	
	//如果返回 false，zTree 将终止拖拽，也无法触发 onDrag / beforeDrop / onDrop 事件回调函数
	function zTreeBeforeDrag(treeId, treeNodes) {
		//console.log(treeNodes[0].name);
		if(treeNodes[0].name === "主页"){
			alert("'主页'不能移动！");
			return false;
		}
		return true;
	};
	//含特殊符号addArticle.html从迁移过来
	function onClick(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("ctgrtree"),
		nodes = zTree.getSelectedNodes(),
		v = nodes[0].name;
		$("#ctgrsel").attr("value", v);
	}