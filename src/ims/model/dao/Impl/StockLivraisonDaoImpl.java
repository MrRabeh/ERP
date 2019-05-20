package ims.model.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import ims.model.dao.ModelDao;
import ims.model.entities.Stock_Livraison;

public class StockLivraisonDaoImpl implements ModelDao<Stock_Livraison> {

	@Override
	public boolean insert(Stock_Livraison object) {
        Session session=DataIms.getSessionFactory().getCurrentSession();
        try {

            session.beginTransaction();
            System.out.println("insert stock livraison DAO");
            session.save(object);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
        	session.close();
            System.err.println("EROR STOCK LIVRAISON IN HIBERNATE->"+e.getMessage());
            return false;
        }
	}

	@Override
	public boolean update(Stock_Livraison object) {
        Session session=DataIms.getSessionFactory().getCurrentSession();
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Stock_Livraison object) {
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
	public Stock_Livraison get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Stock_Livraison> getbyObjet(int id) {
		List<Stock_Livraison> lignes=null;
		Session session=DataIms.sessionFactory.getCurrentSession();
		try {

	        session.beginTransaction();
	        Query q=session.createQuery("select obj from obj in class Stock_Livraison where obj.livraison.id=:id");
	        q.setParameter("id",id);
	        lignes=((List<Stock_Livraison>)q.list());
		} catch (Exception e) {
			session.close();
			e.printStackTrace();
		}
		return lignes;
	}

	@Override
	public List<Stock_Livraison> get() {
        Session session=DataIms.getSessionFactory().getCurrentSession();
	       try {
	    	    List<Stock_Livraison> lignes=null;

	            session.beginTransaction();
	            session.clear();
		        Query q=session.createQuery("from Stock_Livraison");
		        lignes=((List<Stock_Livraison>)q.list());
	            return lignes;
	            
	        } catch (Exception e) {
	        	session.close();
	            System.err.println("ERROR IN HIBERNATE->"+e.getMessage());
	            return new ArrayList<Stock_Livraison>();
	        }
	}

	@Override
	public Stock_Livraison getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stock_Livraison getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Stock_Livraison> getByNames(String... name) {
		 List<Stock_Livraison> list=new ArrayList<Stock_Livraison>();
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
		    			condition1="obj.livraison.years.id=:idannee";
		    		}
		         	if(name[2]!=null){
		            	if(!name[2].equals("0"))
		            		condition2="obj.stock.article.idArticle=:idart";
		            	}
		         	Query q1=session.createQuery("select obj from obj in class Stock_Livraison where "+condition1+" and "+condition2);
		         	
		         	if(name[1]!=null){ 
			         	if(!name[1].equals(""))   
			    	       	 q1.setParameter("idannee",Integer.parseInt(name[1]));
			         	}
		         	
		         	if(name[2]!=null){
		    	       	if(!name[2].equals("0"))
		                q1.setParameter("idart",Integer.parseInt(name[2]));
		        	}
		         	list=((List<Stock_Livraison>)q1.list());
		            System.out.println("--------------------ligne size=="+list.size());
		         }
		         return list;
			} catch (Exception e) {
				session.close();
				return null;
			}
	        
	}

	@Override
	public Stock_Livraison getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
