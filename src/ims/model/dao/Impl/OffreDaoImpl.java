/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.model.dao.Impl;

import ims.model.dao.ModelDao;
import ims.model.entities.Offre;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;

public class OffreDaoImpl implements ModelDao<Offre>{

    
    @Override
    public boolean insert(Offre object) {
         Session session=DataIms.getSessionFactory().getCurrentSession();
       
    try {
    		System.out.println("SAVE OFFRE");
            session.getTransaction().begin();
            session.save(object);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            System.err.println("ERRORR INSERT OFFRE IN HIBERNATE->"+e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Offre object) {
        Session session=DataIms.sessionFactory.getCurrentSession();
        try {	
			session.getTransaction().begin();
			session.update(object);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
    }

    @Override
    public boolean delete(Offre object) {
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
    public Offre get(int id) {
        Session session=DataIms.sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			session.clear();
			Offre obj=(Offre)session.load(Offre.class,id);
			return obj;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Offre> get() {
            List<Offre> objets;
        Session session=DataIms.getSessionFactory().getCurrentSession();
       try {
            session.beginTransaction();
            session.clear();
            objets=((List<Offre>)session.createQuery("select obj from obj in class Offre where obj.activer=true and obj.ref not like 'sans%' order by obj.ref desc").list());
            return objets;
        } catch (Exception e) {
            System.err.println("ERRORR IN HIBERNATE->"+e.getMessage());
            return new ArrayList<Offre>();
        }
    }

    @Override
    public List<Offre> getbyObjet(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	@Override
	public Offre getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Offre getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Offre> getByNames(String... name) {
	       try {
	    	    List<Offre> lignes=null;
	            Session session=DataIms.getSessionFactory().getCurrentSession();
	            session.beginTransaction();
	            session.clear();
	            Query q;
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
	            		condition2="obj.client.idclient=:idclient";
	            	}
	            	if(name[3]!=null){
	               	if(!name[3].equals("")){
	               		System.out.println("Etat "+name[3]);
	            		condition3="obj.etatoffre=:etat";
	               		}
	            	}
	            	if(name[4]!=null){
	               	if(!name[4].equals("")){
	               		System.out.println("Type "+name[4]);
	            		condition4="obj.typeoffre.id=:idtype";
	               		}
	            	}
	              
	           	 Query q1=session.createQuery("select obj from obj in class Offre where "+condition1+" and "+condition2+" and "+condition3+" and "+condition4);
	         	
	         	if(name[1]!=null){ 
	         	if(!name[1].equals(""))   
	    	       	 q1.setParameter("idannee",Integer.parseInt(name[1]));
	         	}
	        	if(name[2]!=null){
	    	       	if(!name[2].equals(""))
	                q1.setParameter("idclient",Integer.parseInt(name[2]));
	        	}
	        	if(name[3]!=null){
	    	     	if(!name[3].equals("")){
	    	     		System.out.println(name[3].replace("h", "é"));
	    	     		q1.setParameter("etat",name[3].replace("h", "é"));
	    	     	}
	        	}
	        	if(name[4]!=null){
	    	     	if(!name[4].equals(""))
	                q1.setParameter("idtype",Integer.parseInt(name[4]));
	        	}
	                lignes=((List<Offre>)q1.list());
	                System.out.println("--------------------ligne size=="+lignes.size());
	            }else{
	            	 if(!name[0].equals(""))
	 	            {
	 	            	q=session.createQuery("select obj from obj in class Offre where obj.typeoffre.type=:type");
	 	            	q.setParameter("type",name[0]);
	 		        lignes=((List<Offre>)q.list());
	 	            }
	            }
	            return lignes;
	        } catch (Exception e) {
	            System.err.println("ERRORRRRRRRRRRRRRRR getByNames IN HIBERNATE->"+e.getMessage());
	            return new ArrayList<Offre>();
	        }
	}

	@Override
	public Offre getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}
    
    
    
}
