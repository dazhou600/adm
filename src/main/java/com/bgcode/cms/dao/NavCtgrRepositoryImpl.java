package com.bgcode.cms.dao;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;


public class NavCtgrRepositoryImpl implements NavCtgrPrcdu{

	@PersistenceContext
    private EntityManager em;
	
	@Override
	public int delNav(int bid) {
		StoredProcedureQuery query = em.createStoredProcedureQuery("delSubNode");
		query.registerStoredProcedureParameter("type_id", Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("errnb", Integer.class, ParameterMode.OUT);
		query.setParameter("type_id", bid);
		query.execute();
		//System.out.println(query);
		return (int) query.getOutputParameterValue("errnb");
		 
	}

	@Override
	public int addNav(int pid, int pst, String rname) {
		StoredProcedureQuery query = em.createStoredProcedureQuery("addSubnode");
		query.registerStoredProcedureParameter("start_id", Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("crwz", Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("rname", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("errnb", Integer.class, ParameterMode.OUT);
		query.setParameter("start_id", pid);
		query.setParameter("crwz", pst);
		query.setParameter("rname", rname);
		query.execute();
		return (int) query.getOutputParameterValue("errnb");
	}

	@Override
	public int movNav(int pid, int tid) {
		StoredProcedureQuery query = em.createStoredProcedureQuery("up_dw_Node");
		query.registerStoredProcedureParameter("ysid", Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("mbid", Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("msg", Integer.class, ParameterMode.OUT);
		query.setParameter("ysid", pid);
		query.setParameter("mbid", tid);
		query.execute();
		return (int) query.getOutputParameterValue("msg");
	}

}
