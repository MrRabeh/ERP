package ims.model.dao.Impl;

import java.util.List;

import org.hibernate.classic.Session;

import ims.model.dao.ModelDao;
import ims.model.entities.GroupeClient;

public class GroupeClientDaoImpl implements ModelDao<GroupeClient> {

	@Override
	public boolean insert(GroupeClient object) {
	    Session session=DataIms.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.save(object);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {
            session.close();
            System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
            return false;
        }
	}

	@Override
	public boolean update(GroupeClient object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(GroupeClient object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public GroupeClient get(int id) {
		Session session=DataIms.sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			session.clear();
			GroupeClient obj=(GroupeClient)session.load(GroupeClient.class,id);
			return obj;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<GroupeClient> getbyObjet(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GroupeClient> get() {
        List<GroupeClient> objs;
        Session session=DataIms.getSessionFactory().getCurrentSession();
       try {
    	   	System.out.println("GET DAO GROUPE");
            session.getTransaction().begin();
            session.clear();
            objs=((List<GroupeClient>)session.createQuery("from GroupeClient").list());
            return objs;
        } catch (Exception e) {
            System.err.println("EROR GROUPE CLIENT IN HIBERNATE->"+e.getMessage());
            return null;
        }
	}

	@Override
	public GroupeClient getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GroupeClient getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GroupeClient> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GroupeClient getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
