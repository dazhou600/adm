//主页导航
function getNavbarDOM(navbars){
	var navs = JSON.parse(navbars);
	//console.log(navs);
	var countnavs =navs.length;
	var outstr='';
	for (var i = 0;i< countnavs; i++) {
		if(i<(countnavs-1)){
			if(navs[i].level<navs[i+1].level){
				//如果后面跟着子节点
				outstr = les(navs[i],outstr);
			}else if(navs[i].level>navs[i+1].level){
				//后面没有子节点返回上面
				outstr = equals(navs[i],outstr,1);
				outstr = addlabel(navs[i].level-navs[i+1].level,outstr);
				outstr += '<li role="separator" class="divider"></li>';
			}else{
				outstr = equals(navs[i],outstr,2);
			}
		}else{
			outstr = equals(navs[i],outstr,1);
			outstr = addlabel(navs[i].level-1,outstr);
		}
	}
	$('#navbars').html(outstr);
}
//后面有子节点
function les(navbar,outstr){
	console.log("nav***"+outstr);
	navbar.level>2?outstr +="<li class='dropdown-submenu'>"
    +"<a href='#'>"+navbar.rname+"<span class='caret'></span></a>"
    +"<ul class='dropdown-menu'>"
	:outstr +="<li class='dropdown'>"
    +"<a href='#' class='dropdown-toggle' data-toggle='dropdown' role='button' aria-haspopup='true' aria-expanded='false'>"+navbar.rname+" <span class='caret'></span></a>"
     +"<ul class='dropdown-menu'>";
	return outstr;
}
/*
 * 同一层加入样式
 * isLast 是当前层级的最后一个?
 */
function equals(navbar,outstr,isLast){
	outstr +="<li><a href='#'>"+navbar.rname+"<span class='sr-only'>(current)</span></a></li>";
	if(isLast!==1){
		outstr += '<li role="separator" class="divider"></li>';
	}
	return outstr;
}
//加入结束标签
function addlabel(level,outstr){
	for(var j=0;j<level;j++){
		outstr +="</ul></li>"
	}
	return outstr;
}