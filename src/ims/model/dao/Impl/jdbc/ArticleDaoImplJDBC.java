package ims.model.dao.Impl.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import ims.model.dao.ModelDao;
import ims.model.entities.Article;

public class ArticleDaoImplJDBC implements ModelDao<Article> {

	private String url;
	private String user;
	private String passwd;
	private PreparedStatement preparedStatement;
	private Connection conn;
	
	public ArticleDaoImplJDBC() {
		super();
		  url = "jdbc:sqlserver://192.168.30.15;databaseName=erpims";
	      user = "dbims";
	      passwd = "ims@2014";
	}

	@Override
	public boolean insert(Article object) {
		return true;
	}

	@Override
	public boolean update(Article object) {
		try {
			System.out.println("DAO");
		      Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");     
		      conn = DriverManager.getConnection(url, user, passwd);
		      System.out.println("DAO 2");
			preparedStatement = conn.prepareStatement("UPDATE Article SET reference ='"+object.getRef()+"' ,designationFournisseur ='"+object.getDesignation()+"' ,designationClient = '"+object.getDesignation()+"' WHERE idArticle = "+object.getIdArticle());
			System.out.println("DAO 3");
			// execute update SQL stetement
			preparedStatement.executeUpdate();
			System.out.println("DAO 4");
			System.out.println("Record is updated to article table!");
         return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("DAO 5");
				return false;
		} 
		finally {
			 
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("DAO 6");
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("DAO 7");
		}
	}

	@Override
	public boolean delete(int id) {
		try {
			System.out.println("DAO");
		      Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");     
		      conn = DriverManager.getConnection(url, user, passwd);
		      System.out.println("DAO 2");
			preparedStatement = conn.prepareStatement("DELETE FROM Article WHERE id ="+id);
			System.out.println("DAO 3");
			// execute update SQL stetement
			preparedStatement.executeUpdate();
			System.out.println("DAO 4");
			System.out.println("Record is delete to article table!");
         return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("DAO 5");
				return false;
		} 
	}

	@Override
	public boolean delete(Article object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Article get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Article> getbyObjet(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Article> get() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Article getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Article getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Article> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Article getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
