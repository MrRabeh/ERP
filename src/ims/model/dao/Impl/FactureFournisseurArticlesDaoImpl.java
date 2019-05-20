package ims.model.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import ims.model.dao.ModelDao;
import ims.model.entities.FactureFournisseur_articles;

public class FactureFournisseurArticlesDaoImpl implements
		ModelDao<FactureFournisseur_articles> {

	@Override
	public boolean insert(FactureFournisseur_articles object) {
		// TODO Auto-generated method stub
        Session session=DataIms.getSessionFactory().getCurrentSession();
	       try {

	            session.getTransaction().begin();
	            session.save(object);
	            session.getTransaction().commit();
	            session.close();
	            return true;
	        } catch (Exception e) {
	            session.close();
	            System.err.println("ERRORRRRRRRRRRRRRRR IN INSERT Facture_Livraison->"+e.getMessage());
	            return false;
	        }
	}

	@Override
	public boolean update(FactureFournisseur_articles object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(FactureFournisseur_articles object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FactureFournisseur_articles get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FactureFournisseur_articles> getbyObjet(int id) {
		 List<FactureFournisseur_articles> lignes=null;
			try {
				Session session=DataIms.sessionFactory.getCurrentSession();
		        session.beginTransaction();
		        Query q=session.createQuery("select obj from obj in class FactureFournisseur_articles where obj.facture.id=:id");
		        q.setParameter("id",id);
		        lignes=((List<FactureFournisseur_articles>)q.list());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return lignes;
	}

	@Override
	public List<FactureFournisseur_articles> get() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FactureFournisseur_articles getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FactureFournisseur_articles getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FactureFournisseur_articles> getByNames(String... name) {
		 List<FactureFournisseur_articles> list=new ArrayList<FactureFournisseur_articles>();
		Session session=DataIms.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.clear();
        Query q;
        if(name.length==3){
       	 
       	 String condition1="obj.id!=0";
        	String condition2="obj.id!=0";
        	
        	if(name[1]!=null){
   			if(!name[1].equals(""))
   			condition1="obj.facture.objetyears.id=:idannee";
   		}
        	if(name[2]!=null){
           	if(!name[2].equals("0"))
           		condition2="obj.article.idArticle=:idart";
           	}
        	Query q1=session.createQuery("select obj from obj in class FactureFournisseur_articles where "+condition1+" and "+condition2);
        	
        	if(name[1]!=null){ 
	         	if(!name[1].equals(""))   
	    	       	 q1.setParameter("idannee",Integer.parseInt(name[1]));
	         	}
        	
        	if(name[2]!=null){
   	       	if(!name[2].equals("0"))
               q1.setParameter("idart",Integer.parseInt(name[2]));
       	}
        	list=((List<FactureFournisseur_articles>)q1.list());
           System.out.println("--------------------ligne size=="+list.size());
        }
        return list;
	}

	@Override
	public FactureFournisseur_articles getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
