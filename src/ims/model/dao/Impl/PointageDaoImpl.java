/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.model.dao.Impl;

import ims.model.dao.ModelDao;
import ims.model.entities.Pointage;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.classic.Session;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rabeh
 */
@Transactional
public class PointageDaoImpl implements ModelDao<Pointage>{

    @Override
    public List<Pointage> getbyObjet(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean insert(Pointage pointage) {
         //To change body of generated methods, choose Tools | Templates.
        Session session=DataIms.getSessionFactory().getCurrentSession();
        try {
        	System.out.println("DAO OK");

            session.beginTransaction();
            session.save(pointage);
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
    public boolean update(Pointage pointage) {
        	try {
			Session session=DataIms.sessionFactory.getCurrentSession();
			session.getTransaction().begin();
			session.update(pointage);
			session.getTransaction().commit();
			session.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }

    @Override
    public Pointage get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Pointage> get() {
        List<Pointage> pointages;
        Session session=DataIms.getSessionFactory().getCurrentSession();
       try {
            session.getTransaction().begin();
            session.clear();
            pointages=((List<Pointage>)session.createQuery("from Pointage").list());
            return pointages;
        } catch (Exception e) {
            session.close();
            System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
            return new ArrayList<Pointage>();
        }
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Pointage pointage) {
          try {
		   	Session session=DataIms.sessionFactory.getCurrentSession();
			session.getTransaction().begin();
			session.delete(pointage);
			session.getTransaction().commit();
			session.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }

	@Override
	public Pointage getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pointage getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pointage> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pointage getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}
 
    
}
