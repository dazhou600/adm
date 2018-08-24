//主页导航
function getNavbarDOM(navbars){
	var navs = JSON.parse(navbars);
	console.log(navs);
	var countnavs =navs.length;
	var outstr='';
	for (var i = 0;i< countnavs; i++) {
		if(i<(countnavs-1)){
			if(navs[i].level<navs[i+1].level){
				//如果后面跟着子节点
				outstr = les(navs[i],outstr);
			}else if(navs[i].level>navs[i+1].level){
				outstr = equals(navs[i],outstr);
				outstr = addlabel(navs[i].level-navs[i+1].level,outstr);
			}else{
				outstr = equals(navs[i],outstr);
			}
		}else{
			outstr = equals(navs[i],outstr);
			outstr = addlabel(navs[i].level-1,outstr);
		}
	}
	$('#navbars').html(outstr);
}
function greate(){
	
}
function les(navbar,outstr){
	console.log("nav***"+outstr);
	navbar.level>2?outstr=outstr +"<li class='dropdown-submenu'>"
    +"<a href='#'>"+navbar.rname+"</a>"
    +"<ul class='dropdown-menu'>"
	:outstr=outstr +"<li class='dropdown'>"
    +"<a href='#' class='dropdown-toggle' data-toggle='dropdown' role='button' aria-haspopup='true' aria-expanded='false'>"+navbar.rname+" <span class='caret'></span></a>"
     +"<ul class='dropdown-menu'>";
	return outstr;
}
function equals(navbar,outstr){
	outstr=outstr +"<li><a href='#'>"+navbar.rname+"<span class='sr-only'>(current)</span></a></li>";
	return outstr;
}
function addlabel(level,outstr){
	for(var j=0;j<level;j++){
		outstr=outstr+"</ul></li>"
	}
	return outstr;
}