/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.model.dao.Impl;

import ims.model.dao.ModelDao;
import ims.model.entities.CategorieArticle;



/**
 *
 * @author rabeh
 */
import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;
/**
 *
 * @author rabeh
 */
public class CategorieArticleDaoImpl implements ModelDao<CategorieArticle>{

    
    @Override
    public boolean insert(CategorieArticle object) {
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
    public boolean update(CategorieArticle object) {
           //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
     try {
			Session session=DataIms.sessionFactory.getCurrentSession();
			session.getTransaction().begin();
			session.update(object);
			session.getTransaction().commit();
			session.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(CategorieArticle object) {
         //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public CategorieArticle get(int id) {
        
            Session session=DataIms.sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			CategorieArticle obj=(CategorieArticle)session.load(CategorieArticle.class,id);
			return obj;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }


	@SuppressWarnings("unchecked")
	@Override
    public List<CategorieArticle> get() {
         try {
            Session session=DataIms.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.clear();
            return ((List<CategorieArticle>)session.createQuery("from CategorieArticle").list());
            
        } catch (Exception e) {
            System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
            return null;
        }
    }

    @Override
    public List<CategorieArticle> getbyObjet(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	@Override
	public CategorieArticle getByName(String name) {
		CategorieArticle object=null;
		try {
			Session session=DataIms.sessionFactory.getCurrentSession();
	        session.beginTransaction();
	        Query q=session.createQuery("select obj from obj in class CategorieArticle where obj.categorie=:ref");
	        q.setParameter("ref",name);
	        object=(CategorieArticle)q.uniqueResult();
	        object=(CategorieArticle)session.load(CategorieArticle.class,object.getIdcategorie());
	        session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	@Override
	public CategorieArticle getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CategorieArticle> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CategorieArticle getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}
    
    
}
