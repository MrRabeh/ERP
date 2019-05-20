/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.model.dao.Impl;

import ims.model.dao.ModelDao;
import ims.model.entities.Fournisseur;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rabeh
 */
@Transactional
public class FournisseurDao implements ModelDao<Fournisseur> {

    @Override
    public List<Fournisseur> getbyObjet(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean insert(Fournisseur object) {
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
            System.err.println("ERROR insert FOURNISSEUR IN HIBERNATE->"+e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Fournisseur object) {
        
    	Session session=DataIms.sessionFactory.getCurrentSession();
        try {
			session.getTransaction().begin();
			session.merge(object);
			session.getTransaction().commit();
			session.close();
			return true;
		} catch (Exception e) {
                        session.close();
                            e.printStackTrace();
                         System.err.println("ERORR update fournisseur IN HIBERNATE->"+e.getMessage());
			return false;
		}
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Fournisseur object) {
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
    public Fournisseur get(int id) {
        	Session session=DataIms.sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			session.clear();
			Fournisseur obj=(Fournisseur)session.load(Fournisseur.class,id);
			return obj;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Fournisseur> get() {
               List<Fournisseur> objs;
        Session session=DataIms.getSessionFactory().getCurrentSession();
       try {
            session.getTransaction().begin();
            session.clear();
            objs=((List<Fournisseur>)session.createQuery("from Fournisseur").list());
            return objs;
        } catch (Exception e) {
            System.err.println("EROR FOURNISSEUR IN HIBERNATE->"+e.getMessage());
            return null;
        }
    }

	@Override
	public Fournisseur getByName(String name) {
		System.out.println("DAO Fournisseur "+name);
		Fournisseur object=null;
		try {
			Session session=DataIms.sessionFactory.getCurrentSession();
	        session.beginTransaction();
	        Query q=session.createQuery("select obj from obj in class Fournisseur where obj.nomsociete=:ref");
	        q.setParameter("ref",name);
	        object=(Fournisseur)q.uniqueResult();
	        object=(Fournisseur)session.load(Fournisseur.class,object.getIdfournisseur());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	@Override
	public Fournisseur getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Fournisseur> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Fournisseur getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}
    
    
}
