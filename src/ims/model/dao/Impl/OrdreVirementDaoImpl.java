package ims.model.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.classic.Session;

import ims.model.dao.ModelDao;
import ims.model.entities.OrdreVirement;

public class OrdreVirementDaoImpl implements ModelDao<OrdreVirement> {

	@Override
	public boolean insert(OrdreVirement object) {
	       Session session=DataIms.getSessionFactory().getCurrentSession();
	       
	       try {
	       		   System.out.println("SAVE OFFRE");
	               session.getTransaction().begin();
	               Integer id=(Integer) session.save(object);
	               object.setId(id);
	               session.getTransaction().commit();
	               return true;
	           } catch (Exception e) {
	               System.err.println("ERRORRRRRRRRRRRRRRR INSERT OFFRE IN HIBERNATE->"+e.getMessage());
	               return false;
	           }
	}

	@Override
	public boolean update(OrdreVirement object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(OrdreVirement object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public OrdreVirement get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrdreVirement> getbyObjet(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrdreVirement> get() {
        List<OrdreVirement> objets;
     Session session=DataIms.getSessionFactory().getCurrentSession();
    try {
         session.beginTransaction();
         session.clear();
         objets=((List<OrdreVirement>)session.createQuery("from OrdreVirement").list());
         return objets;
     } catch (Exception e) {
         System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
         return new ArrayList<OrdreVirement>();
     }
	}

	@Override
	public OrdreVirement getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrdreVirement getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrdreVirement> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrdreVirement getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
