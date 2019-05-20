package ims.model.dao.Impl;

import java.util.List;




import org.hibernate.classic.Session;

import ims.model.dao.ModelDao;
import ims.model.entities.AvoirClient;

public class AvoirClientDaoImpl implements ModelDao<AvoirClient>{

	@Override
	public boolean insert(AvoirClient object) {
		
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
	public boolean update(AvoirClient object) {
	      //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	     Session session=DataIms.sessionFactory.getCurrentSession();
	        try {
				session.getTransaction().begin();
				session.update(object);
				session.getTransaction().commit();
				session.close();
				return true;
			} catch (Exception e) {
	                        session.close();
	                            e.printStackTrace();
	                         System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
				return false;
			}
	}

	@Override
	public boolean delete(int id) {
		 throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		   
	}

	@Override
	public boolean delete(AvoirClient object) {
		  // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	     try {
			   	Session session=DataIms.sessionFactory.getCurrentSession();
				session.getTransaction().begin();
				session.delete(object);
				session.getTransaction().commit();
				session.close();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
	}

	@Override
	public AvoirClient get(int id) {
		Session session=DataIms.sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			AvoirClient obj=(AvoirClient)session.load(AvoirClient.class,id);
			return obj;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<AvoirClient> getbyObjet(int id) {
		  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AvoirClient> get() {
        List<AvoirClient> objs;
      Session session=DataIms.getSessionFactory().getCurrentSession();
     try {
          session.getTransaction().begin();
          objs=((List<AvoirClient>)session.createQuery("select obj from obj in class AvoirClient where obj.activer=true order by obj.dateavoir desc").list());
          return objs;
      } catch (Exception e) {
          session.close();
          System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
          return null;
      }
	}

	@Override
	public AvoirClient getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AvoirClient getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AvoirClient> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AvoirClient getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
