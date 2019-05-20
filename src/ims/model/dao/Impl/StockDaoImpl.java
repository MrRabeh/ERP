package ims.model.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import ims.model.dao.ModelDao;
import ims.model.entities.Stock;

public class StockDaoImpl implements ModelDao<Stock> {

	
	
	@Override
	public boolean insert(Stock object) {
        Session session=DataIms.getSessionFactory().getCurrentSession();
	    try {

            session.beginTransaction();
            session.save(object);
            session.getTransaction().commit();

            return true;
        } catch (Exception e) {
            session.close();
            System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
            return false;
        }
	}

	@Override
	public boolean update(Stock object) {
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Stock object) {
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
	public Stock get(int id) {
		   Session session=DataIms.sessionFactory.getCurrentSession();
			try {
				session.getTransaction().begin();
				session.clear();
				Stock obj=(Stock)session.load(Stock.class,id);
				return obj;
			}catch (Exception e) {
				session.close();
				e.printStackTrace();
				return null;
			}
	}

	@Override
	public List<Stock> getbyObjet(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Stock> get() {
        Session session=DataIms.getSessionFactory().getCurrentSession();
		   try {
	    	    List<Stock> lignes=null;

	            session.beginTransaction();
	            session.clear();
		        Query q=session.createQuery("from Stock");
		        lignes=((List<Stock>)q.list());
	            return lignes;
	            
	        } catch (Exception e) {
				session.close();
	            System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
	            return new ArrayList<Stock>();
	        }
	}

	@Override
	public Stock getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stock getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Stock> getByNames(String... name) {
	
		List<Stock> lignes=null;
        Session session=DataIms.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
        	 if(name.length==1){
          	   System.out.println("PREPARE QUERY STOCK .");
          	   if(name[0].equals("stock")){
          		   System.out.println("PREPARE QUERY STOCK ..");
          		   lignes=session.createQuery("select obj from obj in class Stock where obj.qte>0").list();
                     System.out.println("QUERY STOCK Success");
          	   }
          	   if(name[0].equals("livrer")){
          		   System.out.println("PREPARE QUERY STOCK ==0");
          		   lignes=session.createQuery("select obj from obj in class Stock where obj.qte=0").list();
          		   System.out.println("QUERY STOCK Success");
          	   }
              }else if(name.length==2){
              	System.out.println("length ==> 2");
               	   if(name[0].equals("stock")){
               		  System.out.println(name[0]+" ID CLIENT ==>"+Integer.parseInt(name[1]));
                   	 Query q=session.createQuery("select obj from obj in class Stock where obj.client.idclient=:idclient AND obj.qte>0");
                        System.out.println("ID CLIENT ==>"+Integer.parseInt(name[1]));
                   	 q.setParameter("idclient",Integer.parseInt(name[1]));
                        lignes=q.list();
               	   }
               	   if(name[0].equals("livrer")){
                     	 Query q=session.createQuery("select obj from obj in class Stock where obj.client.idclient=:idclient AND obj.qte=0");
                     	 q.setParameter("idclient",Integer.parseInt(name[1]));
                          lignes=q.list();
                 	   }
         
              }
             System.out.println("FIN GET STOCK");
		} catch (Exception e) {
			session.close();
		}
      
       return lignes;
	}

	@Override
	public Stock getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
