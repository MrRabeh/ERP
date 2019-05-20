package ims.model.dao.Impl;

import ims.model.dao.ModelDao;
import ims.model.entities.BonCommande;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;

public class BonCommandeDaoImpl implements ModelDao<BonCommande> {

	@Override
	public boolean insert(BonCommande object) {
        Session session=DataIms.getSessionFactory().getCurrentSession();
		 try {

	            session.beginTransaction();
	            System.out.println("save OR UPDATE COMMANDE ...");
	            session.saveOrUpdate(object);
	            session.getTransaction().commit();
	            System.out.println("save OR UPDATE COMMANDE Success");
	            session.close();
	            return true;
	        } catch (Exception e) {
	            session.close();
	            System.err.println("ERROR Bon Commande IN HIBERNATE->"+e.getMessage());
	            return false;
	        }
	}

	@Override
	public boolean update(BonCommande object) {
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(BonCommande object) {
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
	public BonCommande get(int id) {
		 Session session=DataIms.sessionFactory.getCurrentSession();
			try {
				session.getTransaction().begin();
				session.clear();
				BonCommande obj=(BonCommande)session.load(BonCommande.class,id);
				return obj;
			}catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	}

	@Override
	public List<BonCommande> getbyObjet(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BonCommande> get() {
		  try {
	            Session session=DataIms.getSessionFactory().getCurrentSession();
	            session.beginTransaction();
	            session.clear();
	            return ((List<BonCommande>)session.createQuery("select obj from obj IN class BonCommande where obj.activer=true and obj.numercommande not LIKE 'Sans%' and obj.numercommande not in ('contrat','service') order by obj.datecommande desc").list());
	            
	        } catch (Exception e) {
	            System.err.println("ERROR BonCommande IN HIBERNATE->"+e.getMessage());
	            return new ArrayList<BonCommande>();
	        }
	}

	@Override
	public BonCommande getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BonCommande getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BonCommande> getByNames(String... name) {
		List<BonCommande> lignes=null;
		 Session session=DataIms.getSessionFactory().getCurrentSession();
	     session.beginTransaction();
		if(name.length==5){
	        	String condition1="obj.id!=0";
	        	String condition2="obj.id!=0";
	        	String condition3="obj.id!=0";
	        	String condition4="obj.id!=0";
	        	if(name[1]!=null){
	        	if(!name[1].equals(""))
	        		condition1="obj.years.id=:idannee";
	        	}
	        	if(name[2]!=null){
	        	if(!name[2].equals(""))
	        		condition2="obj.fournisseur.idfournisseur=:idfournisseur";
	        	}
	        	if(name[3]!=null){
	           	if(!name[3].equals(""))
	        		condition3="obj.etat=:etat";
	        	}
	        	if(name[4]!=null){
	           	if(!name[4].equals(""))
	        		condition4="obj.constater=:constater";
	        	}
	        		
	       	 Query q=session.createQuery("select obj from obj in class BonCommande where "+condition1+" and "+condition2+" and "+condition3+" and "+condition4);
	       	if(name[1]!=null){  	
	       	 if(!name[1].equals(""))   
		       	 q.setParameter("idannee",Integer.parseInt(name[1]));
	       	}
	       	if(name[2]!=null){
		       	if(!name[2].equals(""))
	            q.setParameter("idfournisseur",Integer.parseInt(name[2]));
	       	}
	       	if(name[3]!=null){
		     	if(!name[3].equals(""))
	            q.setParameter("etat",name[3].replace("e", "é").replace("h", "e"));
	       	}
	       	if(name[4]!=null){
		     	if(!name[4].equals(""))
	            q.setParameter("constater",name[4]);
	       	}
	            lignes=((List<BonCommande>)q.list());
	       }
		return lignes;
	}

	@Override
	public BonCommande getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
