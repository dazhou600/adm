//主页导航
function getNavbarDOM(navbars){
	var navs = JSON.parse(navbars);
	//console.log(navbars);
	var countnavs =navs.length;
	var outstr='[';
	//var c = a.substring(0,a.lastIndexOf('ba')); //这样就获取到了前面的字符串。
	for (var i = 0;i< countnavs; i++) {
		if(i<(countnavs-1)){
			if(navs[i].level<navs[i+1].level){
				//如果后面跟着子节点
				outstr = les(navs[i],outstr);
			}else if(navs[i].level>navs[i+1].level){
				//后面没有子节点返回上面
				outstr = equals(navs[i],outstr,1);
				outstr = addlabel(navs[i].level-navs[i+1].level,outstr);
			}else{
				outstr = equals(navs[i],outstr,2);
			}
		}else{
			outstr = equals(navs[i],outstr,1);
			outstr = addlabel(navs[i].level-2,outstr);
			//if(outstr.lastIndexOf(",")==outstr.length){
				outstr= outstr.substring(0,outstr.lastIndexOf(','))
			//}
			
		}
	}
	outstr += "]"
	//var navtree = JSON.parse(outstr);
	console.log(outstr);
	//$('#navbars').html(outstr);
}
//后面有子节点
function les(navbar,outstr){
	var tempstr = delRgtBrace(JSON.stringify(navbar));
	outstr += tempstr+",\""+navbar.rname+"\": [";
	return outstr;
}
/*
 * 同一层加入样式
 * isLast 是当前层级的最后一个?
 * http://www.bejson.com/
 */
function equals(navbar,outstr,isLast){
	outstr += JSON.stringify(navbar);
	if(isLast==2){
		outstr +=",";
	}
	return outstr;
}

//去掉右边大括号 }
function delRgtBrace(str){
	return str.substring(0,str.lastIndexOf("}"));
}

//加入结束标签
function addlabel(level,outstr){
	for(var j=0;j<level;j++){
		outstr +="]}"
	}
	outstr +=",";
	return outstr;
}