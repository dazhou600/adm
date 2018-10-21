package com.bgcode.cms.dao;

//导航存储过程
public interface NavCtgrPrcdu {

	int delNav(int bid);

	int addNav(int pid, int pst, String rname);
}
