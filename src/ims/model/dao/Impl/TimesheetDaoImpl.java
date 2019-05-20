package ims.model.dao.Impl;

import java.util.List;





import org.hibernate.classic.Session;

import ims.model.dao.ModelDao;
 
import ims.model.entities.Timesheet;

public class TimesheetDaoImpl implements ModelDao<Timesheet>{

	@Override
	public boolean insert(Timesheet object) {
		 //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
	public boolean update(Timesheet object) {
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
	public boolean delete(Timesheet object) {
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
	public Timesheet get(int id) {
		Session session=DataIms.sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			Timesheet obj=(Timesheet)session.load(Timesheet.class,id);
			return obj;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Timesheet> getbyObjet(int id) {
		  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Timesheet> get() {
        List<Timesheet> objs;
      Session session=DataIms.getSessionFactory().getCurrentSession();
     try {
          session.getTransaction().begin();
          session.clear();
          objs=((List<Timesheet>)session.createQuery("from Timesheet ").list());
          return objs;
      } catch (Exception e) {
          session.close();
          System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
          return null;
      }
	}

	@Override
	public Timesheet getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timesheet getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Timesheet> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timesheet getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
