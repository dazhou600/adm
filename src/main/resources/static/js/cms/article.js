var mngPage = '/admin/cms/ArticleMng';
// 上传图片保存到临时文件夹
function upArticle(file, header, token) {
	var data = new FormData();
	data.append('file', file);
	$.ajax({
		async : true,
		data : data,
		type : "POST",
		url : "/admin/cms/upArticlePic",
		dataType : 'json',
		cache : false,
		contentType : false,
		processData : false,
		beforeSend : function(xhr) {
			xhr.setRequestHeader(header, token);
		},
		success : function(msg) {
			if (msg.error != null) {
				alert(msg.error);
			} else if (msg.filename != null) {
				if (msg.warning != null) {
					alert(msg.warning);
				}
				$('#summernote').summernote('insertImage', msg.filename);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(XMLHttpRequest.status);
		}

	});
}

// 删除图片
function removePic(imgsrc) {
	$.ajax({
		type : "POST",
		url : '/admin/cms/delArticlePic/',
		data : {
			'imgSrc' : imgsrc
		},
		dataType : "json",
		beforeSend : function(xhr) {
			xhr.setRequestHeader(header, token);
		},
		success : function(data) {
			if (data.msg != null) {
				alert(data.msg);
			}
		},
		error : function() {
			alert("失败！");
			window.location.reload();
		}

	})
}

// 存储或修改文章
function saveEditArticle(data, header, token) {
	$.ajax({
		data : data,
		type : "POST",
		url : '/admin/cms/saveArticle',
		dataType : 'json',
		contentType : "application/json;charset=UTF-8",
		beforeSend : function(xhr) {
			xhr.setRequestHeader(header, token);
		},
		success : function(msg) {
			if (msg.errors != null) {
				alert(msg.errors);
				window.location.href = mngPage;
			} else {
				alert(msg.seccess + ' 保存成功!');
				window.location.href = mngPage;
			}
		},
		error : function() {
			alert("失败！");
			window.location.href = mngPage;
		}
	});
}
// 删除文章内容和图片
function deleteArticle(row, header, token) {
		$.ajax({
			url : '/admin/cms/delArticle/' + row[0],
			dataType : 'json',
			contentType : "application/json;charset=UTF-8",
			beforeSend : function(xhr) {
				xhr.setRequestHeader(header, token);
			},
			success : function(msg) {
				if (msg.error != null) {
					alert(msg.error);
				} else {
					alert(msg.msg);
				}
			},
			error : function() {
				alert("失败！");
				window.location.href=mngPage;
			}
		});
}
/*传入html字符串源码即可*/
function htmlEscape(text){
  return text.replace(/[<>"]/g, function(match, pos, originalText){
    switch(match){
    case "<": return "&lt;";
    case ">":return "&gt;";
   // case "&":return "&amp;";
    case "\"":return "&#39;";
  }
  });
}