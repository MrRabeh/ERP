package ims.model.dao.Impl.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ims.model.dao.ModelDao;
import ims.model.entities.Offre;

public class DevisDaoImp implements ModelDao<Offre> {

	private String url;
	private String user;
	private String passwd;
    private Connection conn;
    private SimpleDateFormat ft= new SimpleDateFormat("dd-MM-yyyy");
    private SimpleDateFormat ftheur= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private SimpleDateFormat ftheurseulement= new SimpleDateFormat("HH:mm:ss");
 
	
	public DevisDaoImp() {
		super();
		
		  url = "jdbc:sqlserver://localhost;databaseName=erpims";
	      user = "dbsystemims";
	      passwd = "IMS@2014";
	      /*
	      url = "jdbc:mysql://127.0.0.1/erpims";
	      user = "root";
	      passwd = "";
	      */
	}
	
	 private void classforname(){
	    	try {
	    		
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				/*
				Class.forName("com.mysql.jdbc.Driver");
				*/
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    
	    }
	 
	@Override
	public boolean insert(Offre object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Offre object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Offre object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Offre get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Offre> getbyObjet(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Offre> get() {
		List<Offre> listeoffre =new ArrayList<Offre>();
		try {
			Offre offre;
		      classforname();     
		      //Création d'un objet Statement
		      conn = DriverManager.getConnection(url, user, passwd);
		      Statement state = conn.createStatement();
		      //L'objet ResultSet contient le résultat de la requête SQL
		      ResultSet result = state.executeQuery("SELECT * FROM Offre");
		      while(result.next()) {
		    	  offre=new Offre();
		    	  offre.setActiver(true);
		    	  offre.setCivilite(result.getString("civilite"));
		    	  offre.setDateoffre(result.getDate("dateoffre"));
		    	  listeoffre.add(offre);
		      }
		      result.close();
		      state.close();
		      conn.close();
		      return listeoffre;
		    } catch (Exception e) {
		      e.printStackTrace();
		      return listeoffre;
		    }      
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Offre getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public SimpleDateFormat getFt() {
		return ft;
	}

	public void setFt(SimpleDateFormat ft) {
		this.ft = ft;
	}

	public SimpleDateFormat getFtheur() {
		return ftheur;
	}

	public void setFtheur(SimpleDateFormat ftheur) {
		this.ftheur = ftheur;
	}

	public SimpleDateFormat getFtheurseulement() {
		return ftheurseulement;
	}

	public void setFtheurseulement(SimpleDateFormat ftheurseulement) {
		this.ftheurseulement = ftheurseulement;
	}

}
