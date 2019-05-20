/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.model.dao.Impl;

import ims.model.entities.CategorieIISociete;
import ims.model.dao.ModelDao;

import java.util.List;

import org.hibernate.classic.Session;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rabeh
 */
@Transactional
public class CategorieIIDao implements ModelDao<CategorieIISociete>{

    @Override
    public boolean insert(CategorieIISociete object) {
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
    public boolean update(CategorieIISociete object) {
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
			return false;
		}
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(CategorieIISociete object) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public CategorieIISociete get(int id) {
        Session session=DataIms.sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			CategorieIISociete obj=(CategorieIISociete)session.load(CategorieIISociete.class,id);
			return obj;
		}catch (Exception e) {
			session.close();
			e.printStackTrace();
			return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<CategorieIISociete> get() {
         List<CategorieIISociete> objs;
        Session session=DataIms.getSessionFactory().getCurrentSession();
       try {
            session.getTransaction().begin();
            session.clear();
            objs=((List<CategorieIISociete>)session.createQuery("from CategorieIISociete").list());
            return objs;
        } catch (Exception e) {
            session.close();
            System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
            return null;
        }
    }

    @Override
    public List<CategorieIISociete> getbyObjet(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	@Override
	public CategorieIISociete getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CategorieIISociete getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public CategorieIISociete getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CategorieIISociete> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}
    
}
