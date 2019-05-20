package ims.model.dao.Impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import ims.model.dao.ModelDao;
import ims.model.entities.Boncommande_Offre;

public class BonCommande_OffreDaoImpl implements ModelDao<Boncommande_Offre> {

	@Override
	public boolean insert(Boncommande_Offre object) {
		// TODO Auto-generated method stub
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
	public boolean update(Boncommande_Offre object) {
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Boncommande_Offre object) {
	   	Session session=DataIms.sessionFactory.getCurrentSession();
	     try {

				session.getTransaction().begin();
				session.delete(object);
				session.getTransaction().commit();
				session.close();
				return true;
			} catch (Exception e) {
				session.close();
				e.printStackTrace();
				return false;
			}
	}

	@Override
	public Boncommande_Offre get(int id) {
		Session session=DataIms.sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			session.clear();
			Boncommande_Offre obj=(Boncommande_Offre)session.load(Boncommande_Offre.class,id);
			return obj;
		}catch (Exception e) {
			session.close();
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Boncommande_Offre> getbyObjet(int id) {
		List<Boncommande_Offre> lignes=null;
		Session session=DataIms.sessionFactory.getCurrentSession();
		try {
	        session.beginTransaction();
	        Query q=session.createQuery("select obj from obj in class Boncommande_Offre where obj.commande.id=:id");
	        q.setParameter("id",id);
	        lignes=((List<Boncommande_Offre>)q.list());
		} catch (Exception e) {
			session.close();
			e.printStackTrace();
		}
		return lignes;
	}

	@Override
	public List<Boncommande_Offre> get() {
        List<Boncommande_Offre> objs;
      Session session=DataIms.getSessionFactory().getCurrentSession();
     try {
          session.getTransaction().begin();
          session.clear();
          objs=((List<Boncommande_Offre>)session.createQuery("from Boncommande_Offre").list());
          return objs;
      } catch (Exception e) {
          session.close();
          System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
          return null;
      }
	}

	@Override
	public Boncommande_Offre getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boncommande_Offre getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Boncommande_Offre> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boncommande_Offre getByIids(int... id) {
		Boncommande_Offre ligne=null;
		Session session=DataIms.sessionFactory.getCurrentSession();
		try {

	        session.beginTransaction();
	        Query q=session.createQuery("select obj from obj in class Boncommande_Offre where obj.commande.id=:id and obj.offre.id=:idoffre");
	        q.setParameter("id",id[0]);
	        q.setParameter("idoffre",id[1]);
	        ligne=(Boncommande_Offre)q.uniqueResult();
		} catch (Exception e) {
			session.close();
			e.printStackTrace();
		}
		return ligne;
	}

}
