/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.model.dao.Impl;

import ims.model.dao.ModelDao;
import ims.model.entities.Offre_Article;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;

/**
 *
 * @author rabeh
 */
public class OffreArticleDaoImpl implements ModelDao<Offre_Article>,Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	@Override
    public boolean insert(Offre_Article object) {
        Session session=DataIms.getSessionFactory().getCurrentSession();
       
    try {
            session.getTransaction().begin();
            session.save(object);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            System.out.println("Probleme insert Offre_Article message->"+e.getMessage());
            System.out.println("Probleme insert Offre_Article Localized->"+e.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public boolean update(Offre_Article object) {
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
    public boolean delete(Offre_Article object) {
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
    public Offre_Article get(int id) {
       Session session=DataIms.sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			session.clear();
			Offre_Article obj=(Offre_Article)session.load(Offre_Article.class,id);
			return obj;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Offre_Article> getbyObjet(int id) {
       List<Offre_Article> lignes=null;
		try {
			Session session=DataIms.sessionFactory.getCurrentSession();
	        session.beginTransaction();
	        Query q=session.createQuery("select obj from obj in class Offre_Article where obj.offre.id=:id ORDER BY obj.id ASC");
	        q.setParameter("id",id);
	        lignes=((List<Offre_Article>)q.list());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lignes;
    }

    
    
    @SuppressWarnings("unchecked")
	@Override
    public List<Offre_Article> get() {
        List<Offre_Article> objets;
        Session session=DataIms.getSessionFactory().getCurrentSession();
       try {
            session.getTransaction().begin();
            session.clear();
            objets=((List<Offre_Article>)session.createQuery("from Offre_Article").list());
            return objets;
        } catch (Exception e) {
            System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
            return new ArrayList<Offre_Article>();
        }
    }

	@Override
	public Offre_Article getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Offre_Article getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Offre_Article> getByNames(String... name) {
		List<Offre_Article> list=null;
		 Session session=DataIms.getSessionFactory().getCurrentSession();
         session.beginTransaction();
         session.clear();
         Query q;
         if(name.length==3){
        	 
        	 String condition1="obj.id!=0";
         	String condition2="obj.id!=0";
         	
         	if(name[1]!=null){
    			if(!name[1].equals(""))
    			condition1="obj.offre.years.id=:idannee";
    		}
         	if(name[2]!=null){
            	if(!name[2].equals("0"))
            		condition2="obj.article.idArticle=:idart";
            	}
         	String reqsql="select obj from obj in class Offre_Article where "+condition1+" and "+condition2;
         	Query q1=session.createQuery(reqsql);
         	
         	System.out.println(reqsql);
         	if(name[1]!=null){ 
	         	if(!name[1].equals(""))   
	    	       	 q1.setParameter("idannee",Integer.parseInt(name[1]));
	         	}
         	
         	if(name[2]!=null){
    	       	if(!name[2].equals("0"))
                q1.setParameter("idart",Integer.parseInt(name[2]));
        	}
         	list=((List<Offre_Article>)q1.list());
            System.out.println("--------------------ligne size=="+list.size());
         }
         return list;
	}

	@Override
	public Offre_Article getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}
    
    
}
