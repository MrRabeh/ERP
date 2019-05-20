package ims.service.impl;

import ims.model.dao.ModelDao;
import ims.model.entities.Timesheet;
import ims.service.ModelService;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedProperty;

public class ServiceTimesheetImpl implements ModelService<Timesheet>,Serializable{

	private static final long serialVersionUID = 1L;
	 @ManagedProperty(value="#{TimesheetHibernate}")
    ModelDao<Timesheet> daotimesheet;

    public ServiceTimesheetImpl() {
    }

    @Override
    public boolean insertObject(Timesheet timesheet) {
        return daotimesheet.insert(timesheet);
    }

    @Override
    public boolean updateObject(Timesheet timesheet) {
       return daotimesheet.update(timesheet);
    }

    @Override
    public List<Timesheet> getObject() {
        return daotimesheet.get();
    }

    @Override
    public Timesheet getObject(int id) {
       return (Timesheet)daotimesheet.get(id);
    }

    public ModelDao<Timesheet> getDaoTimesheet() {
        return daotimesheet;
    }

    public void setDaotimesheet(ModelDao<Timesheet> daotimesheet) {
        this.daotimesheet = daotimesheet;
    }

    @Override
    public boolean deleteObject(int id) {
        return daotimesheet.delete(id);
    }
    @Override
    public boolean deleteObject(Timesheet timesheet) {
        return daotimesheet.delete(timesheet);
    }

    @Override
    public List<Timesheet> getObjects(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	@Override
	public Timesheet getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timesheet getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Timesheet> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timesheet getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}
    
    
}