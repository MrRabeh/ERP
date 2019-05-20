package ims.model.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import ims.model.dao.ModelDao;
import ims.model.entities.Facture_Article;

public class Facture_ArticleDaoImpl implements ModelDao<Facture_Article> {

	@Override
	public boolean insert(Facture_Article object) {
	      Session session=DataIms.getSessionFactory().getCurrentSession();
		 try {
	      
	            session.beginTransaction();
	            System.out.println("Facture Article DAO");
	            session.save(object);
	            session.getTransaction().commit();
	            session.close();
	            return true;
	        } catch (Exception e) {
	            session.close();
	            System.err.println("Exception Facture Article IN HIBERNATE ->"+e.getMessage());
	            return false;
	        }
	}

	@Override
	public boolean update(Facture_Article object) {
		try {
			Session session=DataIms.sessionFactory.getCurrentSession();
			session.getTransaction().begin();
			System.out.println("DAO UPDATE Facture_Article");

			session.merge(object);
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Facture_Article object) {
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
	public Facture_Article get(int id) {
		  Session session=DataIms.sessionFactory.getCurrentSession();
				try {
					session.getTransaction().begin();
					session.clear();
					Facture_Article obj=(Facture_Article)session.load(Facture_Article.class,id);
					return obj;
				}catch (Exception e) {
					e.printStackTrace();
					return null;
				}
	}

	@Override
	public List<Facture_Article> getbyObjet(int id) {
		 List<Facture_Article> lignes=null;
			try {
				Session session=DataIms.sessionFactory.getCurrentSession();
		        session.beginTransaction();
		        Query q=session.createQuery("select obj from obj in class Facture_Article where obj.facture.id=:id");
		        q.setParameter("id",id);
		        lignes=((List<Facture_Article>)q.list());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return lignes;
	}

	@Override
	public List<Facture_Article> get() {
		try {
            Session session=DataIms.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.clear();
            return ((List<Facture_Article>)session.createQuery("from Facture_Article").list());
            
        } catch (Exception e) {
            System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
            return new ArrayList<Facture_Article>();
        }
	}

	@Override
	public Facture_Article getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Facture_Article getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Facture_Article getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Facture_Article> getByNames(String... name) {
		 List<Facture_Article> list=new ArrayList<Facture_Article>();
			Session session=DataIms.getSessionFactory().getCurrentSession();
	         session.beginTransaction();
	         session.clear();
	         Query q;
	         if(name.length==3){
	        	 
	        	 String condition1="obj.id!=0";
	         	String condition2="obj.id!=0";
	         	
	         	if(name[1]!=null){
	    			if(!name[1].equals(""))
	    			condition1="obj.facture.years.id=:idannee";
	    		}
	         	if(name[2]!=null){
	            	if(!name[2].equals("0"))
	            		condition2="obj.article.idArticle=:idart";
	            	}
	         	Query q1=session.createQuery("select obj from obj in class Facture_Article where "+condition1+" and "+condition2);
	         	
	         	if(name[1]!=null){ 
		         	if(!name[1].equals(""))   
		    	       	 q1.setParameter("idannee",Integer.parseInt(name[1]));
		         	}
	         	
	         	if(name[2]!=null){
	    	       	if(!name[2].equals("0"))
	                q1.setParameter("idart",Integer.parseInt(name[2]));
	        	}
	         	list=((List<Facture_Article>)q1.list());
	            System.out.println("--------------------ligne size=="+list.size());
	         }
	         return list;
	}

}
