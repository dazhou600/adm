package com.bgcode.cms.entity;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class NavNodeTest {
	private List<NavCtgr> navs;

	private List<NavCtgr> subNodes;

	@Before
	public void setUp() throws Exception {
		navs = new ArrayList<NavCtgr>();
		navs.add(new NavCtgr(1, "gjd", "//roott", 1, 1, 10));
		navs.add(new NavCtgr(1, "修车", "//roott", 1, 1, 10));
		navs.add(new NavCtgr(1, "租车", "//roott", 1, 1, 10));
		navs.add(new NavCtgr(1, "gjd", "//roott", 1, 1, 10));

	}

	@Test
	public void test() {
		NavNode rot = null;
		for (int i = 0; i < navs.size(); i++) {
			if (i == 0) {
				rot = new NavNode(navs.get(i));
			} else {
				if(navs.get(i).level==rot.getLevel()+1){
					rot.getSubNodes().add(new NavNode(navs.get(i)));
				}else{
					NavNode nn = new NavNode(navs.get(i));
					findPrtNode(rot,nn.getLft(),nn.getRgt(),nn.getLevel()).getSubNodes().add(nn);
				}
			}
		}

	}
	public NavNode findPrtNode(NavNode rot,int lft,int rgt,int level){
		rot.getSubNodes();
		return null;
		
	}
	public NavNode crtPrtNode(){
		return null;
		
	}
}
