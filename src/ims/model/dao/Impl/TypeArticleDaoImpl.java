/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.model.dao.Impl;

import ims.model.dao.ModelDao;

import ims.model.entities.TypeArticle;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;
/**
 *
 * @author rabeh
 */
public class TypeArticleDaoImpl implements ModelDao<TypeArticle>{

    @Override
    public List<TypeArticle> getbyObjet(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean insert(TypeArticle object) {
       try {
            Session session=DataIms.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.save(object);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {
            System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(TypeArticle object) {
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
    public boolean delete(TypeArticle object) {
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
    public TypeArticle get(int id) {
        
          Session session=DataIms.sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			TypeArticle obj=(TypeArticle)session.load(TypeArticle.class,id);
			return obj;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<TypeArticle> get() {
      try {
            Session session=DataIms.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.clear();
            return ((List<TypeArticle>)session.createQuery("from TypeArticle").list());
            
        } catch (Exception e) {
            System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
            return null;
        }
    }

	@Override
	public TypeArticle getByName(String name) {
		System.out.println("DAO TypeArticle "+name);
		TypeArticle object=null;
		try {
			Session session=DataIms.sessionFactory.getCurrentSession();
	        session.beginTransaction();
	        Query q=session.createQuery("select obj from obj in class TypeArticle where obj.type=:ref");
	        q.setParameter("ref",name);
	        object=(TypeArticle)q.uniqueResult();
	        object=(TypeArticle)session.load(TypeArticle.class,object.getIdtype());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	@Override
	public TypeArticle getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypeArticle> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeArticle getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}
    
}
