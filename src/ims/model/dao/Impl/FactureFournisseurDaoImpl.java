package ims.model.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import ims.model.dao.ModelDao;
import ims.model.entities.FactureFournisseur;

public class FactureFournisseurDaoImpl implements ModelDao<FactureFournisseur> {

	@Override
	public boolean insert(FactureFournisseur object) {
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
	public boolean update(FactureFournisseur object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(FactureFournisseur object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FactureFournisseur get(int id) {
    	Session session=DataIms.sessionFactory.getCurrentSession();
	try {
		session.getTransaction().begin();
		session.clear();
		FactureFournisseur obj=(FactureFournisseur)session.load(FactureFournisseur.class,id);
		return obj;
	}catch (Exception e) {
		e.printStackTrace();
		return null;
	}
	}

	@Override
	public List<FactureFournisseur> getbyObjet(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FactureFournisseur> get() {
		 try {
	            Session session=DataIms.getSessionFactory().getCurrentSession();
	            session.beginTransaction();
	            session.clear();
	            List<FactureFournisseur> factures=((List<FactureFournisseur>)session.createQuery("select obj from obj in class FactureFournisseur where obj.activer=true order by obj.datefacture desc").list());
	            return factures;
	        } catch (Exception e) {
	            System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
	            return new ArrayList<FactureFournisseur>();
	        }
	}

	@Override
	public FactureFournisseur getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FactureFournisseur getByID(int id) {
    	Session session=DataIms.sessionFactory.getCurrentSession();
	try {
		session.getTransaction().begin();
		session.clear();
		FactureFournisseur obj=(FactureFournisseur)session.load(FactureFournisseur.class,id);
		return obj;
	}catch (Exception e) {
		e.printStackTrace();
		return null;
	}
	}

	@Override
	public List<FactureFournisseur> getByNames(String... name) {
		List<FactureFournisseur> lignes=null;
        Session session=DataIms.getSessionFactory().getCurrentSession();
        session.beginTransaction();
       if(name.length==2){
        	 Query q=session.createQuery("select obj from obj in class FactureFournisseur where obj.objetyears.id=:annee");
             q.setParameter("annee",Integer.parseInt(name[1]));
             //q.setParameter("annee",name[2]);
             lignes=((List<FactureFournisseur>)q.list());
        }
       else if(name.length==5){
       	String condition1="obj.id!=0";
       	String condition2="obj.id!=0";
       	String condition3="obj.id!=0";
       	String condition4="obj.id!=0";
       	if(name[1]!=null){
       	if(!name[1].equals(""))
       		condition1="obj.objetyears.id=:idannee";
       	}
       	if(name[2]!=null){
       	if(!name[2].equals(""))
       		condition2="obj.commandefournissuer.fournisseur.idfournisseur=:idf";
       	}
       	if(name[3]!=null){
       	if(!name[3].equals(""))
       		condition3="obj.etat=:etat";
       	}
       	if(name[4]!=null){
          	if(!name[4].equals(""))
       		condition4="obj.constater=:constater";
       	}
       		
      	 Query q=session.createQuery("select obj from obj in class FactureFournisseur where "+condition1+" and "+condition2+" and "+condition3+" and "+condition4);
      	if(name[1]!=null){  	
      	 if(!name[1].equals(""))   
	       	 q.setParameter("idannee",Integer.parseInt(name[1]));
      	}
      	if(name[2]!=null){
	       	if(!name[2].equals(""))
           q.setParameter("idf",Integer.parseInt(name[2]));
      	}
      	if(name[3]!=null){
	     	if(!name[3].equals(""))
           q.setParameter("etat",name[3].replace("e", "é").replace("h", "e"));
      	}
      	if(name[4]!=null){
	     	if(!name[4].equals(""))
           q.setParameter("constater",name[4]);
      	}
           lignes=((List<FactureFournisseur>)q.list());
      }
       if(lignes!=null)
    	   System.out.println("size==>"+lignes.size());
        return lignes;
	}

	@Override
	public FactureFournisseur getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
