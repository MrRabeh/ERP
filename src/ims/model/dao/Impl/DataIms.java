/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.model.dao.Impl;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author rabeh
 */
public class DataIms {
public static final SessionFactory sessionFactory; 
	static {        
		try {            
		// Création de la SessionFactory à partir de hibernate.cfg.xml         
		sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();  
			//sessionFactory = new Configuration().configure("hibernatemysql.cfg.xml").buildSessionFactory();  
		} catch (Exception ex) {
		System.err.println("Initial SessionFactory creation failed." + ex);            
		throw new ExceptionInInitializerError(ex);        
		}    
		}
	public static SessionFactory getSessionFactory() {        
		return sessionFactory;    
		}
}
