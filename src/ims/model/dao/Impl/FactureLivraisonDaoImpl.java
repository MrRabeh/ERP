package ims.model.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import ims.model.dao.ModelDao;
import ims.model.entities.Facture_Livraison;

public class FactureLivraisonDaoImpl implements ModelDao<Facture_Livraison>{

	@Override
	public boolean insert(Facture_Livraison object) {
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
	public boolean update(Facture_Livraison object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Facture_Livraison object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Facture_Livraison get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Facture_Livraison> getbyObjet(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Facture_Livraison> get() {
		   try {
	            Session session=DataIms.getSessionFactory().getCurrentSession();
	            session.beginTransaction();
	            session.clear();
	            List<Facture_Livraison> facturelivraion=((List<Facture_Livraison>)session.createQuery("from Facture_Livraison").list());
	            return facturelivraion;
	        } catch (Exception e) {
	            System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
	            return new ArrayList<Facture_Livraison>();
	        }
	}

	@Override
	public Facture_Livraison getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Facture_Livraison getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Facture_Livraison> getByNames(String... name) {
		List<Facture_Livraison> lignes=null;
	       Session session=DataIms.getSessionFactory().getCurrentSession();
	       session.beginTransaction();
		 if(name.length==2){
			 if(name[0].equals("facture")){
				  Query q=session.createQuery("select obj from obj in class Facture_Livraison where obj.facture.id=:id");
				  q.setParameter("id",Integer.parseInt(name[1]));
			        lignes=((List<Facture_Livraison>)q.list());
			        }
			 }
		      
		 return lignes;
	}

	@Override
	public Facture_Livraison getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
