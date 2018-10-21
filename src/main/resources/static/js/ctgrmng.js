var log, className = "dark";
	function beforeDrag(treeId, treeNodes) {
		return false;
	}
	function beforeEditName(treeId, treeNode) {
		className = (className === "dark" ? "":"dark");
		var zTree = $.fn.zTree.getZTreeObj("navtree");
		zTree.selectNode(treeNode);
		setTimeout(function() {
			if (confirm("进入节点 -- " + treeNode.name + " 的编辑状态吗？")) {
				setTimeout(function() {
					zTree.editName(treeNode);
				}, 0);
			}
		}, 0);
		return false;
	}
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
	            	 alert(msg.msg);
                     if (msg.msg = "666") {
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
	function beforeRename(treeId, treeNode, newName, isCancel) {
		className = (className === "dark" ? "":"dark");
		if (newName.length == 0) {
			setTimeout(function() {
				var zTree = $.fn.zTree.getZTreeObj("navtree");
				zTree.cancelEditName();
				alert("节点名称不能为空.");
			}, 0);
			return false;
		}
		return true;
	}
	function selectAll() {
		var zTree = $.fn.zTree.getZTreeObj("navtree");
		zTree.setting.edit.editNameSelectAll =  $("#selectAll").attr("checked");
	}
	function addHoverDom(treeId, treeNode) {
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
		var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
			+ "' title='add node' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_"+treeNode.tId);
		if (btn) btn.bind("click", function(){
						$("#modal-default").modal();
						$("#addInfo").html("将在 <b>" + treeNode.name +"</b> 下 第 <b><select>"
	+"<option>1</option><option>2</option><option>3</option><option>4</option><option>5</option></select></b>");
		});
	};

	function removeHoverDom(treeId, treeNode) {
		$("#addBtn_"+treeNode.tId).unbind().remove();
	};
	//绑定增加函数
	$("#addSubmit").click(function(){
		 var treeObj = $.fn.zTree.getZTreeObj("navtree");
		 var nodes = treeObj.getSelectedNodes();
         $.ajax({
             async: true,
            url:'/admin/cms/navctgr/add/'+nodes[0].bid+',0,'+$("#inputrname").val(),
            //data: {"pid": ''+nodes.bid+'0'+$("#inputrname").val()},
             //url:'/admin/cms/navctgr/'+nodes.bid+'/0/'+$("#inputrname").val(),
           //  contentType : "application/x-www-form-urlencoded; charset=utf-8",
             //data:$("#addSubmit").serialize(),
             success: function (msg) {
                 if (msg.msg = "666") {
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
     });