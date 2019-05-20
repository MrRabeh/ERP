package ims.model.dao.Impl;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import ims.model.dao.ModelDao;
import ims.model.entities.Boncommande_Article;

public class BonCommandeArticleDaoImpl implements ModelDao<Boncommande_Article> {

	@Override
	public boolean insert(Boncommande_Article object) {
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
	public boolean update(Boncommande_Article object) {
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
	public boolean delete(Boncommande_Article object) {
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
	public Boncommande_Article get(int id) {
		Session session=DataIms.sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			session.clear();
			Boncommande_Article obj=(Boncommande_Article)session.load(Boncommande_Article.class,id);
			return obj;
		}catch (Exception e) {
			session.close();
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Boncommande_Article> getbyObjet(int id) {
		Session session=DataIms.sessionFactory.getCurrentSession();
		List<Boncommande_Article> lignes=null;
			try {

		        session.beginTransaction();
		        Query q=session.createQuery("select obj from obj in class Boncommande_Article where obj.commande.id=:id");
		        q.setParameter("id",id);
		        lignes=((List<Boncommande_Article>)q.list());
			} catch (Exception e) {
				session.close();
				e.printStackTrace();
			}
			return lignes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Boncommande_Article> get() {
	    List<Boncommande_Article> objs;
	      Session session=DataIms.getSessionFactory().getCurrentSession();
	     try {
	          session.getTransaction().begin();
	          session.clear();
	          objs=((List<Boncommande_Article>)session.createQuery("from Boncommande_Article").list());
	          return objs;
	      } catch (Exception e) {
	          session.close();
	          System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
	          return null;
	      }
	}

	@Override
	public Boncommande_Article getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boncommande_Article getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Boncommande_Article> getByNames(String... name) {
		 List<Boncommande_Article> list=new ArrayList<Boncommande_Article>();
		Session session=DataIms.getSessionFactory().getCurrentSession();
		
		try {
			  session.beginTransaction();
		         session.clear();
		         Query q;
		         if(name.length==3){
		        	 
		        	 String condition1="obj.id!=0";
		         	String condition2="obj.id!=0";
		         	
		         	if(name[1]!=null){
		    			if(!name[1].equals(""))
		    			condition1="obj.commande.years.id=:idannee";
		    		}
		         	if(name[2]!=null){
		            	if(!name[2].equals("0"))
		            		condition2="obj.article.idArticle=:idart";
		            	}
		         	Query q1=session.createQuery("select obj from obj in class Boncommande_Article where "+condition1+" and "+condition2);
		         	
		         	if(name[1]!=null){ 
			         	if(!name[1].equals(""))   
			    	       	 q1.setParameter("idannee",Integer.parseInt(name[1]));
			         	}
		         	
		         	if(name[2]!=null){
		    	       	if(!name[2].equals("0"))
		                q1.setParameter("idart",Integer.parseInt(name[2]));
		        	}
		         	list=((List<Boncommande_Article>)q1.list());
		            System.out.println("--------------------ligne size=="+list.size());
		         }
		         return list;
		} catch (Exception e) {
			return null;
		}
       
	}

	@Override
	public Boncommande_Article getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
