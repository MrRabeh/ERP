package ims.model.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import ims.model.dao.ModelDao;
import ims.model.entities.Facture;
import ims.model.entities.Offre;

public class FactureDaoImpl implements ModelDao<Facture> {

	@Override
	public boolean insert(Facture object) {
        Session session=DataIms.getSessionFactory().getCurrentSession();
	       try {
	  
	            session.beginTransaction();
	            int id=(Integer)session.save(object);
	            object.setId(id);
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
	public boolean update(Facture object) {
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
	public boolean delete(Facture object) {
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
	public Facture get(int id) {
		  Session session=DataIms.sessionFactory.getCurrentSession();
			try {
				session.getTransaction().begin();
				session.clear();
				Facture obj=(Facture)session.load(Facture.class,id);
				return obj;
			}catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	}

	@Override
	public List<Facture> getbyObjet(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Facture> get() {
		   try {
	            Session session=DataIms.getSessionFactory().getCurrentSession();
	            session.beginTransaction();
	            session.clear();
	            List<Facture> factures=((List<Facture>)session.createQuery("select obj from obj in class Facture where obj.activer=true and obj.ref not like 'sans%' and obj.ref not like'im%' order by obj.datefacture desc").list());
	            return factures;
	        } catch (Exception e) {
	            System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
	            return new ArrayList<Facture>();
	        }
	}

	@Override
	public Facture getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Facture getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Facture getByIids(int... id) {
	   	Session session=DataIms.sessionFactory.getCurrentSession();
		session.getTransaction().begin();
		Facture fact=(Facture)session.load(Facture.class, id[0]);
		Offre offre=(Offre)session.load(Offre.class, id[1]);
		fact.AddOffre(offre);
		session.getTransaction().commit();
		session.close();
		return fact;
	}

	@Override
	public List<Facture> getByNames(String... name) {
		List<Facture> lignes=new ArrayList<Facture>();
		Session session=DataIms.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.clear();
        if(name.length==1){
        	System.out.println("PREPARE QUERY .");
        Query q=session.createQuery("select obj from obj in class Facture where obj.facturetype.typefacture=:typefacture AND obj.activer=true order by obj.id desc");
        System.out.println("PREPARE QUERY ..");
        q.setParameter("typefacture",name[0]);
        System.out.println("PREPARE QUERY ...");
        lignes=(List<Facture>)q.list();
        System.out.println("PREPARE QUERY ....");
        }
        else if(name.length==4){
        	Query q;
        		q=session.createQuery("select obj from obj in class Facture where obj.facturetype.typefacture=:typefacture and obj.years.id=:annee AND obj.activer=true order by obj.id desc");
                System.out.println("typefacture==>"+name[1]);
        		q.setParameter("typefacture",name[1]);
                q.setParameter("annee",Integer.parseInt(name[3]));
                System.out.println("annee==>"+Integer.parseInt(name[3]));
                lignes=(List<Facture>)q.list();
                
                List<Facture> facture_enlever=new ArrayList<Facture>();
        	if(name[2].equals("Acquisition")){
        		System.out.println("Acquisition");
        		for(int i=0;i<lignes.size();i++){
        			if(lignes.get(i).getListoffres().size()>0){
	        			if(lignes.get(i).getListoffres().get(0).getTypeoffre().getType()!=null){
	        				if(!lignes.get(i).getListoffres().get(0).getTypeoffre().getType().toLowerCase().equals("acquisition")){
	            				System.out.println("enlever");
	            				facture_enlever.add(lignes.get(i));
	            			}
	        			}
        			}
        		}
        		lignes.removeAll(facture_enlever);
        	}else{
        		System.out.println("sans Acquisition");
        		System.out.println("SIZE==>"+lignes.size());
        		for(int i=0;i<lignes.size();i++){
        			System.out.println("Fact N° "+i+" =>"+lignes.get(i).getRef());
        			if(lignes.get(i).getListoffres()!=null){
        				System.out.println("OFFREe EXIST° "+i+" =>"+lignes.get(i).getListoffres());
        				if(lignes.get(i).getListoffres().size()>0){
        					if(lignes.get(i).getListoffres().get(0).getTypeoffre()!=null){
        						if(lignes.get(i).getListoffres().get(0).getTypeoffre().getType()!=null){
        							System.out.println("ADD FACTURE ENLEVER");
        							facture_enlever.add(lignes.get(i));
        					}
        				}
        				}
        			}
        			System.out.println("FIN fact N° "+i+" =>"+lignes.get(i).getRef());
        		}
        		System.out.println("facture_enlever");
        		lignes.removeAll(facture_enlever);
        	}
        }
        else if(name.length==6){
        	String condition1="obj.id!=0";
        	String condition2="obj.id!=0";
        	String condition3="obj.id!=0";
        	String condition4="obj.id!=0";
        	String condition5="obj.id!=0";
        	
        	if(name[1]!=null){
        			if(!name[1].equals(""))
        			condition1="obj.years.id=:idannee";
        		}
        	if(name[2]!=null){
        	if(!name[2].equals(""))
        		condition2="obj.client.idclient=:idclient";
        	}
        	if(name[3]!=null){
           	if(!name[3].equals(""))
        		condition3="obj.etat=:etat";
        	}
        	if(name[4]!=null){
           	if(!name[4].equals(""))
        		condition4="obj.constater=:constater";
        	}
        	if(name[5]!=null){
               	if(!name[5].equals(""))
            		condition5="obj.client.groupeclient.id=:idgroup";
            	}
       	 Query q=session.createQuery("select obj from obj in class Facture where obj.activer=true AND "+condition5+" AND "+condition1+" and "+condition2+" and "+condition3+" and "+condition4 +" order by obj.datefacture desc");
     	System.out.println("----------"+q+"-------------------");  	
     	if(name[1]!=null){ 
     	if(!name[1].equals(""))   
	       	 q.setParameter("idannee",Integer.parseInt(name[1]));
     	}
    	if(name[2]!=null){
	       	if(!name[2].equals(""))
            q.setParameter("idclient",Integer.parseInt(name[2]));
    	}
    	if(name[3]!=null){
	     	if(!name[3].equals(""))
            q.setParameter("etat",name[3].replace("e", "é").replace("h", "e"));
    	}
    	if(name[4]!=null){
	     	if(!name[4].equals(""))
            q.setParameter("constater",name[4]);
    	}
    	if(name[5]!=null){
	     	if(!name[5].equals(""))
            q.setParameter("idgroup",Integer.parseInt(name[5]));
	     	System.out.println("ID Group "+name[5]);
    	}
            lignes=((List<Facture>)q.list());
       }	
        return lignes;
	}

}
