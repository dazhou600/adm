package com.bgcode.cms.entity;

import java.util.ArrayList;
import java.util.List;

public class NavNode extends NavCtgr{
 
	private List<NavCtgr> subNodes;

	public NavNode(NavCtgr ctgr){
		this.bid= ctgr.getBid();
		this.href=ctgr.getHref();
		this.level=ctgr.getBid();
		this.lft=ctgr.getLft();
		this.rgt=ctgr.getRgt();
		this.subNodes=new ArrayList<NavCtgr>();
	}

	public List<NavCtgr> getSubNodes() {
		return subNodes;
	}

	public void setSubNodes(List<NavCtgr> subNodes) {
		this.subNodes = subNodes;
	}
	

}
