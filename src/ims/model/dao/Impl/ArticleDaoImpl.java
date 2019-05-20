/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.model.dao.Impl;

import ims.model.dao.ModelDao;
import ims.model.entities.Article;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rabeh
 */
@Transactional
public class ArticleDaoImpl implements ModelDao<Article>{
    
    
      @Override
    public boolean insert(Article object) {
          Session session=DataIms.getSessionFactory().getCurrentSession();
         try {

            session.beginTransaction();
            session.save(object);
            session.getTransaction().commit();
           
            return true;
        } catch (Exception e) {
        	 session.close();
            System.err.println("EROR ARTICLE IN HIBERNATE->"+e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Article object) {
		Session session=DataIms.sessionFactory.getCurrentSession();
       try {
	
			session.getTransaction().begin();
			session.update(object);
			session.getTransaction().commit();
	
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
    public boolean delete(Article object) {
	   	Session session=DataIms.sessionFactory.getCurrentSession();
         try {

			session.getTransaction().begin();
			session.delete(object);
			session.getTransaction().commit();

			return true;
		} catch (Exception e) {
			session.close();
			e.printStackTrace();
			return false;
		}
    }

    @Override
    public Article get(int id) {
       Session session=DataIms.sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			Article obj=(Article)session.load(Article.class,id);
			return obj;
		}catch (Exception e) {
			session.close();
			e.printStackTrace();
			return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Article> get() {
        Session session=DataIms.getSessionFactory().getCurrentSession();
       try {
    	    List<Article> lignes=null;
            session.beginTransaction();
            session.clear();
	        Query q=session.createQuery("from Article");
	        lignes=((List<Article>)q.list());
            return lignes;
            
        } catch (Exception e) {
			session.close();
            System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
            return new ArrayList<Article>();
        }
    }

    @Override
    public List<Article> getbyObjet(int id) {
    	System.out.println("DAO Article for Produit ID="+id);
		Session session=DataIms.sessionFactory.getCurrentSession();
    	List<Article> objects=null;
		try {

	        session.beginTransaction();
	        Query q=session.createQuery("select obj from obj in class Article where obj.typeproduit.id=:ProduitID");
	        q.setParameter("ProduitID",id);
	        objects=((List<Article>)q.list());
		} catch (Exception e) {
			session.close();
			e.printStackTrace();
		}
		return objects;
         }

	@Override
	public Article getByName(String name) {
		System.out.println("DAO Article "+name);
		Article object=null;
		Session session=DataIms.sessionFactory.getCurrentSession();
		try {

	        session.beginTransaction();
	        Query q=session.createQuery("select obj from obj in class Article where obj.ref=:ref");
	        q.setParameter("ref",name);
	        object=(Article)q.uniqueResult();
	        object=(Article)session.load(Article.class,object.getIdArticle());
		} catch (Exception e) {
			session.close();
			e.printStackTrace();
		}
		return object;
	}

	@Override
	public Article getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Article getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Article> getByNames(String... name) {
        Session session=DataIms.getSessionFactory().getCurrentSession();
	       try {
	    	    List<Article> lignes=null;
	
	            session.beginTransaction();
	            session.clear();
	            Query q;
	            if(name[0].equals("1"))
	            	{
	            	
	            	q=session.createQuery("select obj from obj in class Article where obj.typearticle.idtype=:type");
	            	q.setParameter("type",Integer.parseInt(name[0]));
	            	}
	            else
		           {
	            	q=session.createQuery("select obj from obj in class Article where obj.typearticle.type=:type");
	            	q.setParameter("type",name[0]);
		           }
		        lignes=((List<Article>)q.list());
	            return lignes;
	            
	        } catch (Exception e) {
	        	session.close();
	            System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
	            return new ArrayList<Article>();
	        }
	}

    
}
