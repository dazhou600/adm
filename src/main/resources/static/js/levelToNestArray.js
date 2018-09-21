//把左右无限level转成zTree嵌套数组
function getNavAsNestArray(navbars){
	var navs = JSON.parse(navbars);
	var countnavs =navs.length;
	var outstr='[';
	for (var i = 0;i< countnavs; i++) {
		if(i<(countnavs-1)){
			if(navs[i].level<navs[i+1].level){
				//如果后面跟着子节点
				outstr = les(navs[i],outstr);
			}else if(navs[i].level>navs[i+1].level){
				//后面没有子节点返回上面
				outstr = equals(navs[i],outstr,1);
				outstr = addlabel(navs[i].level-navs[i+1].level,outstr,2);
			}else{
				outstr = equals(navs[i],outstr,2);
			}
		}else{
			outstr = equals(navs[i],outstr,1);
			outstr = addlabel(navs[i].level-2,outstr,1);
		}
	}
	outstr += "]"
	var navtree = eval(outstr);
	return navtree;
	//console.log(navtree);
	//$('#navbars').html(outstr);
}
//后面有子节点
function les(navbar,outstr){
	var tempstr = delRgtBrace(JSON.stringify(navbar));
	//outstr += tempstr+",\""+navbar.rname+"\": [";
	outstr += tempstr+",name:\""+navbar.rname+"\",children: [";
	return outstr;
}
/*
 * 同一层加入样式
 * isLast 是当前层级的最后一个?
 * http://www.bejson.com/
 */
function equals(navbar,outstr,isLast){
	var tpStr = delRgtBrace(JSON.stringify(navbar));
	outstr += tpStr+",name:\""+navbar.rname+"\"}";
	if(isLast==2){
		outstr +=",";
	}
	return outstr;
}

//去掉右边大括号 }
function delRgtBrace(str){
	return str.substring(0,str.lastIndexOf("}"));
}
/*
 * 加入结束标签
 * isEnd 是否最后一个元素
 */

function addlabel(level,outstr,isEnd){
	for(var j=0;j<level;j++){
		outstr +="]}"
	}
	if(isEnd==2){
	outstr +="," ;
	}
	return outstr;
}