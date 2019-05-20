/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.service.impl;

import ims.model.dao.ModelDao;
import ims.model.entities.Employee;
import ims.service.ModelService;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedProperty;

import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rabeh
 */
@Transactional(readOnly = true)
public class ServiceEmployeeImpl implements ModelService<Employee> ,Serializable{

     
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{EmployeeHibernate}")
    ModelDao<Employee> modeldao;
     
    @Override
    public boolean insertObject(Employee object) {
        return modeldao.insert(object);
    }

    @Override
    public boolean updateObject(Employee object) {
       return modeldao.update(object);
    
    }

    @Override
    public boolean deleteObject(Employee object) {
       return modeldao.delete(object);
    }

    @Override
    public boolean deleteObject(int id) {
       return modeldao.delete(id);
    }

    @Override
    public List<Employee> getObject() {
       return modeldao.get();
    }

    @Override
    public Employee getObject(int id) {
      return (Employee)modeldao.get(id);
    }

    public ModelDao<Employee> getModeldao() {
        return modeldao;
    }

    public void setModeldao(ModelDao<Employee> modeldao) {
        this.modeldao = modeldao;
    }

    @Override
    public List<Employee> getObjects(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	@Override
	public Employee getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Employee> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}
    
    
    
}
