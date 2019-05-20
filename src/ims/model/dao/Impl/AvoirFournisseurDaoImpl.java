package ims.model.dao.Impl;

import java.util.List;




import org.hibernate.classic.Session;

import ims.model.dao.ModelDao;
import ims.model.entities.AvoirFournisseur;

public class AvoirFournisseurDaoImpl implements ModelDao<AvoirFournisseur>{

	@Override
	public boolean insert(AvoirFournisseur object) {
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
	public boolean update(AvoirFournisseur object) {
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
	public boolean delete(AvoirFournisseur object) {
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
	public AvoirFournisseur get(int id) {
		Session session=DataIms.sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			AvoirFournisseur obj=(AvoirFournisseur)session.load(AvoirFournisseur.class,id);
			return obj;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<AvoirFournisseur> getbyObjet(int id) {
		  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AvoirFournisseur> get() {
        List<AvoirFournisseur> objs;
      Session session=DataIms.getSessionFactory().getCurrentSession();
     try {
          session.getTransaction().begin();
          objs=((List<AvoirFournisseur>)session.createQuery("from AvoirFournisseur").list());
          return objs;
      } catch (Exception e) {
          session.close();
          System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
          return null;
      }
	}

	@Override
	public AvoirFournisseur getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AvoirFournisseur getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AvoirFournisseur> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AvoirFournisseur getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
