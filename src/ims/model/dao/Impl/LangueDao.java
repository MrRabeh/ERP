/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.model.dao.Impl;

import ims.model.dao.ModelDao;
import ims.model.entities.Langue;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.classic.Session;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rabeh
 */
@Transactional
public class LangueDao implements ModelDao<Langue>{

    @Override
    public List<Langue> getbyObjet(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean insert(Langue object) {
         Session session=DataIms.getSessionFactory().getCurrentSession();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public boolean update(Langue object) {
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
    public boolean delete(Langue object) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public Langue get(int id) {
          Session session=DataIms.sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			Langue obj=(Langue)session.load(Langue.class,id);
			return obj;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Langue> get() {
              List<Langue> langues;
        Session session=DataIms.getSessionFactory().getCurrentSession();
       try {
            session.getTransaction().begin();
            session.clear();
            langues=((List<Langue>)session.createQuery("from Langue").list());
            return langues;
        } catch (Exception e) {
            System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
            return new ArrayList<Langue>();
        }
    }

	@Override
	public Langue getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Langue getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Langue> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Langue getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}
    
}
