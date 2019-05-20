/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.model.dao.Impl;

import ims.model.dao.ModelDao;
import ims.model.entities.Client;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rabeh
 */
@Transactional
public class ClientDao implements ModelDao<Client>{

    @Override
    public boolean insert(Client object) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public boolean update(Client object) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
			return false;
		}
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Client object) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public Client get(int id) {
        Session session=DataIms.sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			session.clear();
			Client obj=(Client)session.load(Client.class,id);
			return obj;
		}catch (Exception e) {
			session.close();
			e.printStackTrace();
			return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Client> get() {
        Session session=DataIms.getSessionFactory().getCurrentSession();
           try {

            session.beginTransaction();
            session.clear();
            return ((List<Client>)session.createQuery("from Client").list());
            
        } catch (Exception e) {
			session.close();
            System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
            return null;
        }
    }

    @Override
    public List<Client> getbyObjet(int id) {
        Session session=DataIms.getSessionFactory().getCurrentSession();
    	 List<Client> lignes=null;
			try {
		        session.beginTransaction();
		        Query q=session.createQuery("select obj from obj in class Client where obj.groupeclient.id=:id");
		        q.setParameter("id",id);
		        lignes=((List<Client>)q.list());
			} catch (Exception e) {
				session.close();
				e.printStackTrace();
			}
			return lignes;
           }

	@Override
	public Client getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Client getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Client getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Client> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}
    
}
