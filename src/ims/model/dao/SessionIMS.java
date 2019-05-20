package ims.model.dao;

import ims.model.dao.Impl.DataIms;

import org.hibernate.classic.Session;

public class SessionIMS {

	public static Session session=DataIms.getSessionFactory().getCurrentSession();
	
	
}
