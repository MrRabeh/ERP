package ims.model.dao.Impl.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;








import ims.model.dao.ModelDaoJDBC;
import ims.model.entities.Article;
import ims.model.entities.AvoirClient;
import ims.model.entities.Avoir_Articles;
import ims.model.entities.BonCommande;
import ims.model.entities.BonLivraison;
import ims.model.entities.Boncommande_Article;
import ims.model.entities.Boncommande_Offre;
import ims.model.entities.Caisse;
import ims.model.entities.CategorieIISociete;
import ims.model.entities.Charge;
import ims.model.entities.Client;
import ims.model.entities.Cnss;
import ims.model.entities.Contact;
import ims.model.entities.CoordonneeBancaire;
import ims.model.entities.Departement;
import ims.model.entities.Employee;
import ims.model.entities.Facture;
import ims.model.entities.FactureFournisseur;
import ims.model.entities.Facture_Article;
import ims.model.entities.Facture_Avoir;
import ims.model.entities.Fournisseur;
import ims.model.entities.Langue;
import ims.model.entities.LicenceApplication;
import ims.model.entities.LigneCaisse;
import ims.model.entities.Offre_Article;
import ims.model.entities.OrdreVirement;
import ims.model.entities.Pays;
import ims.model.entities.Pointage;
import ims.model.entities.PrixArticleBC;
import ims.model.entities.PrixArticlesOffre;
import ims.model.entities.ListeBoncommandeFactureOffre;
import ims.model.entities.ObjetPrix;
import ims.model.entities.Offre;
import ims.model.entities.Produit;
import ims.model.entities.Projet;
import ims.model.entities.Rubrique;
import ims.model.entities.Salaires;
import ims.model.entities.Secteur;
import ims.model.entities.Stock;
import ims.model.entities.Stock_Livraison;
import ims.model.entities.Tache;
import ims.model.entities.Ticker;
import ims.model.entities.Timesheet;
import ims.model.entities.TypeArticle;
import ims.model.entities.TypeCharge;
import ims.model.entities.TypePaiement;
import ims.model.entities.TypePriorite;
import ims.model.entities.TypeTicker;
import ims.model.entities.Utilisateur;
import ims.model.entities.UtilisateurHelpDesk;
import ims.model.entities.Ville;
import ims.model.entities.years;

public class MangementTablesImpl implements ModelDaoJDBC {

	private String url;
	private String user;
	private String passwd;
    private Connection conn;
    private SimpleDateFormat ft= new SimpleDateFormat("dd-MM-yyyy");
    private SimpleDateFormat ftheur= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private SimpleDateFormat ftheurseulement= new SimpleDateFormat("HH:mm:ss");
	
    public MangementTablesImpl() {
		super();
		
		  url = "jdbc:sqlserver://192.168.30.15;databaseName=erpims";
	      user = "dbsystemims";
	      passwd = "IMS@2014";
	    
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
	public int getTailleByDate(String table,String Attribute,String date) {
		int numbre=0;
		String[] tab=new String[2];
		try {
			classforname();
		      conn = DriverManager.getConnection(url, user, passwd);
		      //Création d'un objet Statement
		      Statement state = conn.createStatement();
		      //L'objet ResultSet contient le résultat de la requête SQL
		      ResultSet result;
		      if(table.equals("Offre"))
		      result = state.executeQuery("SELECT "+Attribute+" FROM "+table+" where YEAR(dateoffre)="+date +" and activer=1");
		      else if(table.equals("Facture"))
		    	result = state.executeQuery("SELECT "+Attribute+" FROM "+table+" where YEAR(datefacture)="+date+" and activer=1");
		      else if(table.equals("BonLivraison"))
			    	result = state.executeQuery("SELECT "+Attribute+" FROM "+table+" where YEAR(datebl)="+date+" and activer=1");
		      else if(table.equals("Ticker"))
		    	  result = state.executeQuery("SELECT "+Attribute+" FROM "+table);
		      else if(table.equals("SuiviTicker"))
		    	  result = state.executeQuery("SELECT "+Attribute+" FROM "+table);
		      else if(table.equals("AvoirClient")){
		    	  result = state.executeQuery("SELECT "+Attribute+" FROM "+table+" where YEAR(dateavoir)="+date+" and activer=1");
		      }
		    	  
		      else 
			    	result = state.executeQuery("SELECT "+Attribute+" FROM "+table+" where YEAR(datecommande)="+date+" and activer=1");
		      //On récupère les MetaData
		      ResultSetMetaData resultMeta = result.getMetaData();
		      
		      while(result.next()){         
		        for(int i = 1; i <= resultMeta.getColumnCount(); i++)
		          {
		        	if(table.equals("Offre"))
		        	tab=result.getObject(i).toString().split("OP");
		        	else if(table.equals("Facture"))
		        		tab=result.getObject(i).toString().split("FA");
		        	else if(table.equals("BonLivraison"))
		        		tab=result.getObject(i).toString().split("BL");
		        	else if(table.equals("Ticker"))
		        		tab=result.getObject(i).toString().split("r");
		        	else if(table.equals("AvoirClient"))
		        		tab=result.getObject(i).toString().split("AV");
		        	else
		        		tab=result.getObject(i).toString().split("BC");
		          }
		      }
		      if(tab[1]!=null)
		    	  numbre=Integer.parseInt(tab[1]);
		      else
		    	  numbre=0;
		      result.close();
		      state.close();
		      conn.close();
		    } catch (Exception e) {
		    	closeconnexion();
		      e.printStackTrace();
		    }      
		return numbre;
	}

	@Override
	public int getexsiteRefArticle(String ref) {
		int numbre=0;
		try {
		      classforname();     
		      //Création d'un objet Statement
		      conn = DriverManager.getConnection(url, user, passwd);
		      Statement state = conn.createStatement();
		      //L'objet ResultSet contient le résultat de la requête SQL
		      ResultSet result = state.executeQuery("SELECT idArticle FROM Article where reference like '"+ref+"'");
		      while(result.next())        
		    	  numbre++;
		      result.close();
		      state.close();
		      conn.close();
		      return numbre;
		    } catch (Exception e) {
		    	closeconnexion();
		      e.printStackTrace();
		      return 0;
		    }      
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

	@Override
	public int getexsiteclient(String societe) {
		int numbre=0;
		try {
		      classforname();    
		      conn = DriverManager.getConnection(url, user, passwd);
		      //Création d'un objet Statement
		      Statement state = conn.createStatement();
		      //L'objet ResultSet contient le résultat de la requête SQL
		      ResultSet result = state.executeQuery("SELECT idclient FROM Client where societe='"+societe+"'");
		      //On récupère les MetaData
		      ResultSetMetaData resultMeta = result.getMetaData();
		      while(result.next()){         
		        for(int i = 1; i <= resultMeta.getColumnCount();i++)
		          {
		        	numbre=result.getInt(i);
		        	break;
		          }
		      }
		      result.close();
		      state.close();
		      conn.close();
		    } catch (Exception e) {
		    	closeconnexion();
		      e.printStackTrace();
		    }      
		return numbre;
	}

	@Override
	public int getexsitefournisseur(String societe) {
		int numbre=0;
		try {
		      classforname();   
		      conn = DriverManager.getConnection(url, user, passwd);
		      //Création d'un objet Statement
		      Statement state = conn.createStatement();
		      //L'objet ResultSet contient le résultat de la requête SQL
		      ResultSet result = state.executeQuery("SELECT idfournisseur FROM Fournisseur where nomSociete='"+societe+"'");
		      //On récupère les MetaData
		      ResultSetMetaData resultMeta = result.getMetaData();
		      while(result.next()){         
		        for(int i = 1; i <= resultMeta.getColumnCount(); i++)
		          {
		        	numbre=result.getInt(i);
		        	break;
		          }
		      }

		      result.close();
		      state.close();
		      conn.close();
		    } catch (Exception e) {
		    	closeconnexion();
		      e.printStackTrace();
		    }      
		return numbre;
	}

	@Override
	public int getExisteArticleContratOrServiceInOffre(int idOffre) {
		int numbre=0;
		try {
		      classforname(); 
		      conn = DriverManager.getConnection(url, user, passwd);
		      //Création d'un objet Statement
		      Statement state = conn.createStatement();
		      //L'objet ResultSet contient le résultat de la requête SQL
		      ResultSet result = state.executeQuery("SELECT OffreID FROM Offre_Article "
		      		+ "inner join Article on Article.idArticle=Offre_Article.articleID "
		      		+ "inner join TypeArticle on TypeArticle.idtype=Article.typeArticleID "
		      		+ "where LOWER(TypeArticle.typearticle)='contrat' AND OffreID="+idOffre);
		      //On récupère les MetaData
		      ResultSetMetaData resultMeta = result.getMetaData();
		      while(result.next()){         
		        for(int i = 1; i <= resultMeta.getColumnCount(); i++)
		          {
		        	numbre=result.getInt(i);
		        	break;
		          }
		      }

		      result.close();
		      state.close();
		      conn.close();
		    } catch (Exception e) {
		    	closeconnexion();
		      e.printStackTrace();
		    }      
		return numbre;
	}

	@Override
	public int getexsitefacture(String numero) {
		int numbre=0;
		try {
		      classforname(); 
		      conn = DriverManager.getConnection(url, user, passwd);
		      //Création d'un objet Statement
		      Statement state = conn.createStatement();
		      //L'objet ResultSet contient le résultat de la requête SQL
		      ResultSet result = state.executeQuery("SELECT id FROM Facture where numero_facture='"+numero+"'");
		      //On récupère les MetaData
		      ResultSetMetaData resultMeta = result.getMetaData();
		      while(result.next()){         
		        for(int i = 1; i <= resultMeta.getColumnCount(); i++)
		          {
		        	numbre=result.getInt(i);
		        	break;
		          }
		      }

		      result.close();
		      state.close();
		      conn.close();
		    } catch (Exception e) {
		    	closeconnexion();
		      e.printStackTrace();
		    }      
		return numbre;
	}

	@Override
	public int getexsiteoffre(String numero) {
		int numbre=0;
		try {
		      classforname(); 
		      conn = DriverManager.getConnection(url, user, passwd);
		      //Création d'un objet Statement
		      Statement state = conn.createStatement();
		      //L'objet ResultSet contient le résultat de la requête SQL
		      ResultSet result = state.executeQuery("SELECT id FROM Offre where Numero_offre='"+numero+"'");
		      //On récupère les MetaData
		      ResultSetMetaData resultMeta = result.getMetaData();
		      while(result.next()){         
		        for(int i = 1; i <= resultMeta.getColumnCount(); i++)
		          {
		        	numbre=result.getInt(i);
		        	break;
		          }
		      }

		      result.close();
		      state.close();
		      conn.close();
		    } catch (Exception e) {
		    	closeconnexion();
		      e.printStackTrace();
		    }      
		return numbre;
	}
	
	@Override
	public int getExsiteProduit(String produit) {
		int numbre=0;
		try {
		      classforname(); 
		      conn = DriverManager.getConnection(url, user, passwd);
		      //Création d'un objet Statement
		      Statement state = conn.createStatement();
		      //L'objet ResultSet contient le résultat de la requête SQL
		      ResultSet result = state.executeQuery("SELECT id FROM Produit where typeproduit='"+produit+"'");
		      //On récupère les MetaData
		      ResultSetMetaData resultMeta = result.getMetaData();
		      while(result.next()){         
		        for(int i = 1; i <= resultMeta.getColumnCount(); i++)
		          {
		        	numbre=result.getInt(i);
		        	break;
		          }
		      }  
		      result.close();
		      state.close();
		      conn.close();
		    } catch (Exception e) {
		    	closeconnexion();
		      e.printStackTrace();
		    }      
		return numbre;
	}

	@Override
	public int getExisteCategorieArticle(String cat) {
		int numbre=0;
		try {
		      classforname(); 
		      conn = DriverManager.getConnection(url, user, passwd);
		      //Création d'un objet Statement
		      Statement state = conn.createStatement();
		      //L'objet ResultSet contient le résultat de la requête SQL
		      ResultSet result = state.executeQuery("SELECT idcategorie FROM CategorieArticle where categorie='"+cat+"'");
		      //On récupère les MetaData
		      ResultSetMetaData resultMeta = result.getMetaData();
		      while(result.next()){         
		        for(int i = 1; i <= resultMeta.getColumnCount(); i++)
		          {
		        	numbre=result.getInt(i);
		        	break;
		          }
		      }

		      result.close();
		      state.close();
		      conn.close();
		    } catch (Exception e) {
		    	closeconnexion();    
		      e.printStackTrace();
		    }      
		return numbre;
	}

	@Override
	public int getExisteTypeArticle(String type) {
		int numbre=0;
		try {
		      classforname(); 
		      conn = DriverManager.getConnection(url, user, passwd);
		      //Création d'un objet Statement
		      Statement state = conn.createStatement();
		      //L'objet ResultSet contient le résultat de la requête SQL
		      ResultSet result = state.executeQuery("SELECT idtype FROM TypeArticle where typearticle='"+type+"'");
		      //On récupère les MetaData
		      ResultSetMetaData resultMeta = result.getMetaData();
		      while(result.next()){         
		        for(int i = 1; i <= resultMeta.getColumnCount(); i++)
		          {
		        	numbre=result.getInt(i);
		        	break;
		          }
		      }

		      result.close();
		      state.close();
		      conn.close();
		    } catch (Exception e) {
		    	closeconnexion();
		      e.printStackTrace();
		    }      
		return numbre;
	}

	@Override
	public int getcontratClient(int idclient) {
		int numbre=0;
		try {
		      classforname();   
		      conn = DriverManager.getConnection(url, user, passwd);
		      //Création d'un objet Statement
		      Statement state = conn.createStatement();
		      //L'objet ResultSet contient le résultat de la requête SQL
		      ResultSet result = state.executeQuery("SELECT id FROM Offre where Numero_offre like 'CONTRAT%' and ClientID='"+idclient+"'");
		      //On récupère les MetaData
		      ResultSetMetaData resultMeta = result.getMetaData();
		      while(result.next()){         
		        for(int i = 1; i <= resultMeta.getColumnCount(); i++)
		          {
		        	numbre=result.getInt(i);
		        	break;
		          }
		      }

		      result.close();
		      state.close();
		      conn.close();
		    } catch (Exception e) {
		    	closeconnexion();    
		      e.printStackTrace();
		    }      
		return numbre;
	}

	@Override
	public int gestionstock(Stock_Livraison object) {
	      try {
			classforname();    
			conn = DriverManager.getConnection(url, user, passwd);
			Date datenow=new Date();
			String reqsql="UPDATE Stock SET qte=qte-"+object.getQte()+",datelivrer='"+ft.format(datenow)+"' ,ClientFinalID="+object.getLivraison().getClient().getIdclient()+"  WHERE id="+object.getStock().getId()+"";
			System.out.println("req==>"+reqsql);
			PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		preparedStatement.executeUpdate();
		conn.close();
		return 1;
			} catch (Exception e) {
				closeconnexion();    
				e.printStackTrace();
				return 0;
			} 
	}
	
	@Override
	public void retourStock(int id, float qte) {
		System.out.println("DAO");
	      try {
			classforname();    
			conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("DAO 2");
	      
		PreparedStatement preparedStatement = conn.prepareStatement("UPDATE Stock SET qte=qte+"+qte+" WHERE id="+id+"");
		System.out.println("DAO 3");
		// execute update SQL stetement
		preparedStatement.executeUpdate();
		System.out.println("DAO 4");
		System.out.println("Record is updated retour to stock table! ");
		conn.close();
			} catch (Exception e) {
				closeconnexion();    
				e.printStackTrace();
			} 
		
	}

	@Override
	public int liraison_facture(int factureID, int livraisonid) {
		System.out.println("DAO");
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("DAO 2");
	      
		PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO facture_livraison (factureID,livraisonID) VALUES("+factureID+","+livraisonid+")");
		System.out.println("DAO 3");
		// execute update SQL stetement
		int result=preparedStatement.executeUpdate();
		System.out.println("DAO 4");
		System.out.println("Record is insert into facturelivraison table!");
		conn.close();
		return result;
			} catch (Exception e) {
				closeconnexion();    
				e.printStackTrace();
				return 0;
			} 
	}

	@Override
	public int updateOffre(Offre object) {
		System.out.println("DAO");
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("DAO 2");
	      
	     
	      String req="UPDATE Offre SET numcotation='"+object.getNumcotation()+"' , totalmarge="+object.getTotalmarge()+", civilite='"+object.getCivilite()+"', description='"+object.getDescription().replace("'", "''")+"', Numero_offre='"+object.getRef().replace("'", "''")+"' ,disponibilite='"+object.getDisponibilite().replace("'", "''")+"',validiteoffre='"+object.getValiditeoffre().replace("'", "''")+"',modalitepaiment='"+object.getModalitepaiment().replace("'", "''")+"',typeoffreID="+object.getTypeoffre().getId()+", ClientID="+object.getClient().getIdclient()+", dateoffre='"+ft.format(object.getDateoffre())+"', etatoffre='"+object.getEtatoffre().replace("'", "''")+"'";
	    		
	      if(object.getDatearreteContrat()!=null)
			  req+=" ,datearreteContrat='"+ft.format(object.getDatearreteContrat())+"' ";
	      
	    		  if(object.getBu()!=null)
	    			  req+=" ,BusinessUnitsID="+object.getBu().getId();
	    		 if(object.getEmp()!=null)
	    			 req+=" ,ContactCommercialID="+object.getEmp().getId();
	    		 
	    		req+=" WHERE id="+object.getId();
	      System.out.println(req);
	      PreparedStatement preparedStatement = conn.prepareStatement(req);
		System.out.println("DAO 3");
		// execute update SQL stetement
		int result=preparedStatement.executeUpdate();
		System.out.println("DAO 4");
		System.out.println("Record is updated to Offre table!");
		conn.close();
		return result;
			} catch (Exception e) {
				closeconnexion(); 
				e.printStackTrace();
				return 0;
			} 
	}

	@Override
	public int updateBoncommande(int idcommande, String date,
			String numerocommande, int idfournisseur,
			String numoffrefounrnisseur, String datefournisseur,double fraitransport,
			String echance, Date dateechance,double tva,int idclient,int idtypepaiement) {

	      try {
			classforname();    
	      conn = DriverManager.getConnection(url, user, passwd);
	      String reqsql="";
	     System.out.println("prepare REQ");
	       reqsql="UPDATE BonCommande SET numercommande='"+numerocommande+"'"+
		    		  " ,echeance='"+echance+"'";
	      if(date!=null){
	    	  reqsql+= " ,datecommande='"+date+"'";
	      }else
	    	  reqsql+= " ,datecommande=NULL";
	      System.out.println("prepare REQ ..");
	      if(datefournisseur!=null)
	    	  reqsql+=" ,dateoffre='"+datefournisseur+"'";
	      else
	    	  reqsql+=" ,dateoffre=NULL";
	      
	      System.out.println("prepare REQ ....");
	      
	       reqsql+=" ,numeroOffreFournisseur='"+numoffrefounrnisseur+"'"+
 		  " ,FournisseurID="+idfournisseur+
 		  " ,tva="+tva+
 		  " ,fraitransport="+fraitransport+
 		  " ,ClientID="+idclient+
 		  " ,typePaiementID="+idtypepaiement;
 		  if(dateechance!=null)
 			 reqsql+= " ,DateEchance='"+ft.format(dateechance)+"'";
 		  
 		 reqsql+=" WHERE commandeID="+idcommande+"";
	       System.out.println("prepare REQ ........");
		System.out.println(reqsql);
		System.out.println("prepare REQ EXECUTE ");
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		System.out.println("DAO 3");
		// execute update SQL stetement
		int result=preparedStatement.executeUpdate();
		System.out.println("DAO 4");
		System.out.println("Record is updated to BonCommande fournisseur table!");
		conn.close();
		return result;
			} catch (Exception e) {
				closeconnexion();    
				e.printStackTrace();
				System.err.println("ERREUR SYSTEM "+e.getMessage());
				return 0;
			} 
	}

	@Override
	public int updateOffreforArticles(double totalht, double tva, double totalttc,
			int idoffre) {
		System.out.println("DAO");
	      try {
			classforname(); 
			conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("DAO 2");
	      String reqsql="UPDATE Offre SET TotalTTC="+totalttc+
	    		  " ,totalHT="+totalht+
	    		  " ,TVA="+tva+
	    		  " WHERE id="+idoffre+"";
	      System.out.println(reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		System.out.println("DAO 3");
		// execute update SQL stetement
		int result=preparedStatement.executeUpdate();
		System.out.println("DAO 4");
		System.out.println("Record is updated to Offre table!");
		conn.close();
		return result;
			} catch (Exception e) {
				closeconnexion();    
				e.printStackTrace();
				return 0;
			} 
	}

	@Override
	public int updatePrixBonCommande(double fraitransport,double totalnet,double totalttc, double totalht, double tva,
			int idcommande) {
		System.out.println("DAO");
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("DAO 2");
	      
			if(fraitransport>0)
				totalnet=totalnet+fraitransport;
			
	      String reqsql="UPDATE BonCommande SET totalttc="+totalttc+
	    		  " ,totalht="+totalht+
	    		  " ,tva="+tva+
	    		  " ,totalnet="+totalnet+
	    		  " WHERE commandeID="+idcommande+"";
	      System.out.println(reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		System.out.println("DAO 3");
		// execute update SQL stetement
		int result=preparedStatement.executeUpdate();
		System.out.println("DAO 4");
		System.out.println("Record is updated to Commande table!");
		conn.close();
		return result;
			} catch (Exception e) {
				closeconnexion();    
				e.printStackTrace();
				return 0;
			} 
	}

	@Override
	public int insertOffre_facture(int idoffre, int idfacture) {
		System.out.println("DAO");
	      try {
			classforname();   
			conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("DAO 2");
	      
		PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO Facture_Offre (offreID,factureID) VALUES("+idoffre+","+idfacture+")");
		System.out.println("DAO 3");
		// execute update SQL stetement
		int result=preparedStatement.executeUpdate();
		System.out.println("DAO 4");
		System.out.println("Record is insert to Facture_Offre table!");
		conn.close();
		return result;
			} catch (Exception e) {
				closeconnexion();    
				e.printStackTrace();
				return 0;
			} 
	}
	
	@Override
	public int AddArticleToFacture(Facture_Article factArticle,Facture fact) {
		System.out.println("-----------------------AddArticleToFacture en Cours----------------------------");
	      try {
			classforname();  
			PreparedStatement preparedStatement;
if(factArticle.getDesignation()==null)
	factArticle.setDesignation("");
	      conn = DriverManager.getConnection(url, user, passwd);
	      Statement state = conn.createStatement();
	      ResultSet resultselect = state.executeQuery("SELECT MAX(id) AS ID FROM Facture_Article");
	      String req;
	      resultselect.next();
	      System.out.println("------PREPARE REQ-------------");
		    
	      if(resultselect.getInt("ID")==0){
	    	  if(fact.getFacturetype().getTypefacture().toLowerCase().equals("contrat"))
	    		  req="INSERT INTO Facture_Article (id,pu,pt,Coefficient,periodicite,FactureID,articleID,qantite,chaineqantite,designation,categorieArticleID,marge,prixmarge,OffreArticleID)"
	     	      		+ " VALUES(1,"+factArticle.getMontantglobal()+","+factArticle.getPt()+","+factArticle.getCoefficient()+","+factArticle.getMensualite()+","+fact.getId()+","+factArticle.getArticle().getIdArticle()+","+factArticle.getQte()+",'"+factArticle.getLab()+"','"+factArticle.getDesignation().replace("'", "''")+"',"+factArticle.getCategorieArticle().getIdcategorie()+","+factArticle.getMarge()+","+factArticle.getMensualite()+")";
	    
	    	  else
	    		  req="INSERT INTO Facture_Article (id,pu,pt,Coefficient,periodicite,FactureID,articleID,qantite,chaineqantite,designation,categorieArticleID,marge,prixmarge,OffreArticleID)"
	     	      		+ " VALUES(1,"+factArticle.getMontantglobal()+","+factArticle.getPt()+","+factArticle.getCoefficient()+","+factArticle.getMensualite()+","+fact.getId()+","+factArticle.getArticle().getIdArticle()+","+factArticle.getQte()+",'"+factArticle.getLab()+"','"+factArticle.getDesignation().replace("'", "''")+"',"+factArticle.getCategorieArticle().getIdcategorie()+","+factArticle.getMarge()+","+factArticle.getPrixmarge()+","+factArticle.getObjetOffreArticle().getId()+")";
	    
	      }
	    	    else{
	    	    	if(fact.getFacturetype().getTypefacture().toLowerCase().equals("contrat"))
	    	    		req="INSERT INTO Facture_Article (id,pu,pt,Coefficient,periodicite,FactureID,articleID,qantite,chaineqantite,designation,categorieArticleID,marge,prixmarge)"
	    		  	      		+ " VALUES(((SELECT MAX(id) FROM Facture_Article)+1),"+factArticle.getMontantglobal()+","+factArticle.getPt()+","+factArticle.getCoefficient()+","+factArticle.getMensualite()+","+fact.getId()+","+factArticle.getArticle().getIdArticle()+","+factArticle.getQte()+",'"+factArticle.getLab()+"','"+factArticle.getDesignation().replace("'", "''")+"',"+factArticle.getCategorieArticle().getIdcategorie()+","+factArticle.getMarge()+","+factArticle.getMensualite()+")";
	    	    	else
				    	  req="INSERT INTO Facture_Article (id,pu,pt,Coefficient,periodicite,FactureID,articleID,qantite,chaineqantite,designation,categorieArticleID,marge,prixmarge,OffreArticleID)"
				  	      		+ " VALUES(((SELECT MAX(id) FROM Facture_Article)+1),"+factArticle.getMontantglobal()+","+factArticle.getPt()+","+factArticle.getCoefficient()+","+factArticle.getMensualite()+","+fact.getId()+","+factArticle.getArticle().getIdArticle()+","+factArticle.getQte()+",'"+factArticle.getLab()+"','"+factArticle.getDesignation().replace("'", "''")+"',"+factArticle.getCategorieArticle().getIdcategorie()+","+factArticle.getMarge()+","+factArticle.getPrixmarge()+","+factArticle.getObjetOffreArticle().getId()+")";
				  	      
	      }
	      System.out.println(req);
	    	  preparedStatement=conn.prepareStatement(req);
		int result=preparedStatement.executeUpdate();
		System.out.println("Record is insert to Facture_Article table!");
		conn.close();
		return result;
			} catch (Exception e) {
				closeconnexion();    
				e.printStackTrace();
				return 0;
			} 
	}

	@Override
	public int AddArticleToFacture(Offre_Article offreArticle,Facture fact,double coeff,double period) {
		System.out.println("-----------------------AddArticleToFacture en Cours----------------------------");
	      try {
			classforname();  
			PreparedStatement preparedStatement;
if(offreArticle.getDesignation()==null)
	offreArticle.setDesignation("");
	      conn = DriverManager.getConnection(url, user, passwd);
	      Statement state = conn.createStatement();
	      ResultSet resultselect = state.executeQuery("SELECT MAX(id) AS ID FROM Facture_Article");
	      String req;
	      resultselect.next();
	      System.out.println("ID OffreArticle");
	      System.out.println(offreArticle.getId());
	      if(resultselect.getInt("ID")==0){
	    	  if(offreArticle.getId()!=0)
	    		  req="INSERT INTO Facture_Article (id,pu,pt,Coefficient,periodicite,FactureID,articleID,qantite,chaineqantite,designation,categorieArticleID,marge,prixmarge,OffreArticleID)"
	     	      		+ " VALUES(1,"+offreArticle.getPu_calculer()+","+offreArticle.getPt_calculer()+","+coeff+","+period+","+fact.getId()+","+offreArticle.getArticle().getIdArticle()+","+offreArticle.getQtef()+",'"+offreArticle.getLbqte()+"','"+offreArticle.getDesignation().replace("'", "''")+"',"+offreArticle.getCategorieArticle().getIdcategorie()+","+offreArticle.getMarge()+","+offreArticle.getPrixmarge()+","+offreArticle.getId()+")";
	    	  else
	    		  req="INSERT INTO Facture_Article (id,pu,pt,Coefficient,periodicite,FactureID,articleID,qantite,chaineqantite,designation,categorieArticleID,marge,prixmarge)"
		     	      		+ " VALUES(1,"+offreArticle.getPu_calculer()+","+offreArticle.getPt_calculer()+","+coeff+","+period+","+fact.getId()+","+offreArticle.getArticle().getIdArticle()+","+offreArticle.getQtef()+",'"+offreArticle.getLbqte()+"','"+offreArticle.getDesignation().replace("'", "''")+"',"+offreArticle.getCategorieArticle().getIdcategorie()+","+offreArticle.getMarge()+","+offreArticle.getPrixmarge()+")";
		    	 
	      }
	    	    else{
	    	    	System.out.println(resultselect.getString("ID"));
	    	    	 if(offreArticle.getId()!=0)
	    	    	req="INSERT INTO Facture_Article (id,pu,pt,Coefficient,periodicite,FactureID,articleID,qantite,chaineqantite,designation,categorieArticleID,marge,prixmarge,OffreArticleID)"
	  	      		+ " VALUES(((SELECT MAX(id) FROM Facture_Article)+1),"+offreArticle.getPu_calculer()+","+offreArticle.getPt_calculer()+","+coeff+","+period+","+fact.getId()+","+offreArticle.getArticle().getIdArticle()+","+offreArticle.getQtef()+",'"+offreArticle.getLbqte()+"','"+offreArticle.getDesignation().replace("'", "''")+"',"+offreArticle.getCategorieArticle().getIdcategorie()+","+offreArticle.getMarge()+","+offreArticle.getPrixmarge()+","+offreArticle.getId()+")";
	    	    	 else
	    	    		 req="INSERT INTO Facture_Article (id,pu,pt,Coefficient,periodicite,FactureID,articleID,qantite,chaineqantite,designation,categorieArticleID,marge,prixmarge)"
	    	 	  	      		+ " VALUES(((SELECT MAX(id) FROM Facture_Article)+1),"+offreArticle.getPu_calculer()+","+offreArticle.getPt_calculer()+","+coeff+","+period+","+fact.getId()+","+offreArticle.getArticle().getIdArticle()+","+offreArticle.getQtef()+",'"+offreArticle.getLbqte()+"','"+offreArticle.getDesignation().replace("'", "''")+"',"+offreArticle.getCategorieArticle().getIdcategorie()+","+offreArticle.getMarge()+","+offreArticle.getPrixmarge()+")";
	    	 	    	    	
	      }
	    	  preparedStatement=conn.prepareStatement(req);
		int result=preparedStatement.executeUpdate();
		System.out.println("Record is insert to Facture_Article table!");
		conn.close();
		return result;
			} catch (Exception e) {
				closeconnexion();    
				e.printStackTrace();
				return 0;
			} 
	}

	@Override
	public int updatePrixfacture(Facture fact) {
	      try {
	    	  System.out.println("----- updatePrixfacture --------");
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      String sqltotalht="(SELECT SUM(periodicite) AS totalht from Facture_Article where FactureID="+fact.getId()+")";
	      
	      Statement state = conn.createStatement();
	      
	      ResultSet resultselect = state.executeQuery(sqltotalht);
	      String reqsql;
	      resultselect.next();
	      if(resultselect.getDouble("totalht")==0){
	    	  reqsql="UPDATE Facture SET totalht=0"+
		    		  " ,totalttc=0"+
		    		  " ,tva=0"+
		    		  " ,totalttcpaye=(0-"+fact.getRemise()+")"+
		    		  " ,montantrester="+fact.getMontantrester()+
		    		  " ,coefficientfacture="+fact.getCoefficientfacture()+
		    		  " ,remise="+fact.getRemise()+
		    		  " ,prixmarge=0";
		    		  if(fact.getBu()!=null)
		    			  reqsql+=" ,BusinessUnitsID="+fact.getBu().getId();
		     reqsql+=" WHERE id="+fact.getId()+"";
	      }else{
	    	  String sqltva="0";
	    	  if(fact.isExoneration()==false)
		       sqltva ="ROUND(( SELECT SUM(periodicite) from Facture_Article where FactureID="+fact.getId()+")*0.2,2)";
		      String sqltotalttc="ROUND("+sqltotalht+"+"+sqltva+",2)";
		      
		      String Sqlprixmarge="SELECT SUM(prixmarge) AS total from Facture_Article where FactureID="+fact.getId();
		      
		      Statement state2 = conn.createStatement();
		      System.out.println("resultat Prix MArge");
		      ResultSet resultprixmarge = state2.executeQuery(Sqlprixmarge);
		      System.out.println("exec resultat Prix MArge");
		      resultprixmarge.next();
		      System.out.println("resultat Prix MArge");
		      if(resultprixmarge.getDouble("total")==0)
		    	  fact.setPrixmarge(0);
		      else
		    	  fact.setPrixmarge(resultprixmarge.getDouble("total"));
		      reqsql="UPDATE Facture SET totalht="+sqltotalht+
		    		  " ,totalttc="+sqltotalttc+
		    		  " ,tva="+sqltva+
		    		  " ,totalttcpaye=("+sqltotalttc+"-"+fact.getRemise()+")"+
		    		  " ,montantrester="+fact.getMontantrester()+
		    		  " ,coefficientfacture="+fact.getCoefficientfacture()+
		    		  " ,remise="+fact.getRemise()+
		    		  " ,prixmarge="+fact.getPrixmarge();
		      if(fact.getBu()!=null)
    			  reqsql+=" ,BusinessUnitsID="+fact.getBu().getId();
		      
		      reqsql+=" WHERE id="+fact.getId()+"";
	      }

	      System.out.println(reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		System.out.println("executeUpdate");
		int result=preparedStatement.executeUpdate();

		System.out.println("Record is updated to Commande table!");
		conn.close();
		return result;
			} catch (Exception e) {
				closeconnexion();    
				e.printStackTrace();
				return 0;
			} 
	}

	@Override
	public int updatefacture(Facture facture) {
			System.out.println("DAO");
			if(facture.getDescription()==null)
				facture.setDescription("");
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("DAO 2");
	      int result;
	      String reqsql="";
    	  SimpleDateFormat ft= new SimpleDateFormat("dd-MM-yyyy");
	      
	    	  reqsql="UPDATE Facture SET numero_facture='"+facture.getRef()+"'"+
		    		  " ,datefacture='"+ft.format(facture.getDatefacture())+"'"+
		    		  " ,dateEchance='"+ft.format(facture.getDateechance())+"'"+
		    		  " ,clientID="+facture.getClient().getIdclient()+
		    		  " ,NumeroBCClient='"+facture.getNumeroBCClient()+"'"+
		    		  " ,coefficientfacture="+facture.getCoefficientfacture()+
		    		  " ,conditiondepayment='"+facture.getConditiondepayment().replace("'", "''")+"'"+
		    		  " ,description='"+facture.getDescription().replace("'", "''")+"'"+
		    		  " ,reglement='"+facture.getReglement().replace("'", "''")+"'"+
		    		  " ,BanqueID="+facture.getBanque().getId()+
		    		  " ,yearsID="+facture.getYears().getId();
	    	  
	    	  if(facture.getDateboncommande()!=null)
	    		  reqsql+=" ,dateboncommande='"+ft.format(facture.getDateboncommande())+"'";
	    	  if(facture.getDateechancep()!=null)
	    		  reqsql+=" ,dateEprevisionel='"+ft.format(facture.getDateechancep())+"'";
	    	  
    		  if(facture.getBu()!=null)
    			  reqsql+=" ,BusinessUnitsID="+facture.getBu().getId();

	    	  
		    	reqsql+=" WHERE id="+facture.getId()+"";
		    	
		      System.out.println(reqsql);
		      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
			System.out.println("executeUpdate");
			result=preparedStatement.executeUpdate();
			System.out.println("DAO 4");
			System.out.println("Record is updated to Commande table!");
			conn.close();
		return result;
			} catch (Exception e) {
				closeconnexion();    
				e.printStackTrace();
				return 0;
			} 
	}

	@Override
	public int insertFactureLivraion(int idfacture, int idlivraison) {
		System.out.println("DAO");
	      try {
			classforname();    
	      System.out.println("DAO 2");
	      conn = DriverManager.getConnection(url, user, passwd);
	      Statement state = conn.createStatement();
	      ResultSet resultselect = state.executeQuery("SELECT MAX(id) AS ID FROM Facture_Livraison");
	      String req;
	      resultselect.next();
	      if(resultselect.getInt("ID")==0){
		      req="INSERT INTO Facture_Livraison "
						+ "(id,livraisonID,factureID) "
						+ "VALUES(1,"+idlivraison+","+idfacture+")";
	      }else{
		      req="INSERT INTO Facture_Livraison "
						+ "(id,livraisonID,factureID) "
						+ "VALUES(((SELECT MAX(id) FROM Facture_Livraison)+1),"+idlivraison+","+idfacture+")";
	      }
		System.out.println(req);
	      PreparedStatement preparedStatement = conn.prepareStatement(req);
		System.out.println("DAO 3");
		// execute update SQL stetement
		int result=preparedStatement.executeUpdate();
		System.out.println("DAO 4");
		System.out.println("Record is insert to Facture_Livraison table!");
		conn.close();
		return result;
			} catch (Exception e) {
				closeconnexion();    
				e.printStackTrace();
				return 0;
			} 
	}

	@Override
	public void closeconnexion() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void getconnexion(){
		try {
			conn = DriverManager.getConnection(url, user, passwd);
		} catch (SQLException e1) {
			closeconnexion();
			e1.printStackTrace();
		}
	}
	
	@Override
	public int insertstocklivraison(Stock_Livraison stockliv){
  	  
		classforname();
		getconnexion();
	      try {
	      Statement state = conn.createStatement();
	      ResultSet resultselect = state.executeQuery("SELECT MAX(id) AS ID FROM Stock_Livraison");
	      String resql;
	      resultselect.next();
	      if(resultselect.getInt("ID")==0){
		       resql="INSERT INTO Stock_Livraison "
						+ "(id,qte,stockID,livraisonID,designationclient,numeroserielivre) "
						+ "VALUES(1,"+stockliv.getQte()+","+stockliv.getStock().getId()+","+stockliv.getLivraison().getId()+",'"+stockliv.getDesignationclient()+"','"+stockliv.getNumeroserielivre()+"')";
	      }else{
	    	   resql="INSERT INTO Stock_Livraison "
						+ "(id,qte,stockID,livraisonID,designationclient,numeroserielivre) "
						+ "VALUES(((SELECT MAX(id) FROM Stock_Livraison)+1),"+stockliv.getQte()+","+stockliv.getStock().getId()+","+stockliv.getLivraison().getId()+",'"+stockliv.getDesignationclient()+"','"+stockliv.getNumeroserielivre()+"')";

	      }
	    System.out.println(resql);
		PreparedStatement preparedStatement = conn.prepareStatement(resql);
		System.out.println("preparedStatement execute");
		int result=preparedStatement.executeUpdate();
		System.out.println("update success");
		conn.close();
		return result;
			} catch (Exception e) {
				closeconnexion();
				return 0;
			}
	
	      }

	@Override
	public int getMaxIdStockLivraison() {
			int numbre;
			try {
			      classforname();     
			      //Création d'un objet Statement
			      conn = DriverManager.getConnection(url, user, passwd);
			      Statement state = conn.createStatement();
			      //L'objet ResultSet contient le résultat de la requête SQL
			      ResultSet result = state.executeQuery("select max(id) from Stock_Livraison");
			      	if (result.next()) {
			      			numbre = result.getInt(1);
			      		}else
			      			numbre=0;
			      System.out.println("NUMBRE="+numbre);
			      conn.close();
			      return numbre;
			    } catch (Exception e) {
			    	closeconnexion();
			      e.printStackTrace();
			      return 0;
			    }      
	}

	@Override
	public int updateStock(Stock stock) {
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      String reqsql="UPDATE Stock SET NumSerie='"+stock.getNumserie()+"'"+
	    		  " ,qte="+stock.getQte()+""+
	    		  " ,fournisseurID="+stock.getFournisseur().getIdfournisseur()+
	    		  " ,ClientID="+stock.getClient().getIdclient()+""+
	    		  " WHERE id="+stock.getId()+"";
	      
	      System.out.println(" updateStock REQ =>"+reqsql);
	      
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);

		int result=preparedStatement.executeUpdate();

		System.out.println("Record is updated to Stock table!");
		conn.close();
		return result;
			}
	      catch (SQLException e) {
	    	  System.out.println("erreur SQL");
	    	  System.out.println(e.getMessage());
				closeconnexion();
				e.printStackTrace();
				return 0;
			} 
	      catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			} 
	}

	@Override
	public int updateClient(Client client) {
		client.setAdresse(client.getAdresse().replace("'", "''"));
		client.setSociete(client.getSociete().replace("'", "''"));
		System.out.println("DAO");
		String conatctID="";
		 if(client.getContactprincipal()!=null)
		  conatctID=" ,contactID="+client.getContactprincipal().getCodeclient()+"";
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("DAO 2");
	      String reqsql="UPDATE Client SET societe='"+client.getSociete()+"'"+
	    		  " ,adresse='"+client.getAdresse()+"'"+
	    		  " ,tel='"+client.getTel()+"'"+
	    		  " ,fax='"+client.getFax()+"'"+
	    		  " ,email='"+client.getEmail()+"'"+
	    		  " ,langueID="+client.getLangue().getIdlangue()+
	    		  " ,CategorieID="+client.getCategorie().getId()+""+
	    		  " ,SecteurID="+client.getSecteur().getId()+""+
	    		  " ,cpaysID="+client.getPays().getIdpays()+""+
	    		  " ,GroupeClientID="+client.getGroupeclient().getId()+""+
	    		  " ,cvilleID="+client.getVille().getIdville()+""+
	              conatctID+ 
	    		  " WHERE idclient="+client.getIdclient()+"";
	      System.out.println(reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		System.out.println("DAO 3");
		// execute update SQL stetement
		int result=preparedStatement.executeUpdate();
		System.out.println("DAO 4");
		System.out.println("Record is updated to Client table!");
		conn.close();
		return result;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			}
	}

	@Override
	public int updateFournisseur(Fournisseur fournisseur) {
		fournisseur.setAdresse(fournisseur.getAdresse().replace("'", "''"));
		fournisseur.setNomsociete(fournisseur.getNomsociete().replace("'", "''"));
		System.out.println("DAO");
		
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("DAO 2");
	      String reqsql="UPDATE Fournisseur SET nomSociete='"+fournisseur.getNomsociete()+"'"+
	    		  " ,adresse='"+fournisseur.getAdresse()+"'"+
	    		  " ,fpaysID="+fournisseur.getPays().getIdpays()+""+
	    		  " ,fvilleID="+fournisseur.getVille().getIdville()+""+
	    		  " WHERE idfournisseur="+fournisseur.getIdfournisseur()+"";
	      System.out.println(reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		System.out.println("DAO 3");
		// execute update SQL stetement
		int result=preparedStatement.executeUpdate();
		System.out.println("DAO 4");
		System.out.println("Record is updated to Fournisseur table!");
		conn.close();
		return result;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			}
	}

	@Override
	public List<ListeBoncommandeFactureOffre> getListeDetailFacture() {
		List<ListeBoncommandeFactureOffre> liste=new ArrayList<ListeBoncommandeFactureOffre>();
		try {
		      classforname();    
		      conn = DriverManager.getConnection(url, user, passwd);
		      //Création d'un objet Statement
		      Statement state = conn.createStatement();
		      //L'objet ResultSet contient le résultat de la requête SQL
		      ResultSet result = state.executeQuery("select  Numero_offre,numercommande,BonLivraison.numeroref,numero_facture,Client.societe from Facture"
		      		+ " LEFT join Facture_Offre on Facture_Offre.factureID=Facture.id"
		      		+ " LEFT  join Offre on Offre.id=Facture_Offre.offreID"
		      		+ " LEFT  join Boncommande_Offre on Boncommande_Offre.OffreID=Facture_Offre.offreID"
		      		+ " LEFT  join BonCommande on BonCommande.commandeID=Boncommande_Offre.CommandeID"
		      		+ " LEFT join Facture_Livraison on Facture_Livraison.factureID=Facture.id"
		      		+ " LEFT join BonLivraison on BonLivraison.id=Facture_Livraison.livraisonID"
		      		+ " LEFT join Client on Client.idclient=Offre.clientID"
		      		+ " where Facture.activer=1 and Facture.annuler=0 and"
		      		+ " BonCommande.activer=1 and BonCommande.annuler=0");
		      int i=1;
		      while(result.next()){ 
		        		liste.add(new ListeBoncommandeFactureOffre(i, result.getString(1), result.getString(2), result.getString(3),result.getString(4),result.getString(5)));
		        	i++;
		      }

		      result.close();
		      state.close();
		      conn.close();
		    } catch (Exception e) {
		    	closeconnexion();
		      e.printStackTrace();
		    }      
		return liste;
	}

	@Override
	public int deleteArticleToBonlivraion(int id) {
		System.out.println("DAO");
	      try {
			classforname();    
			conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("DAO 2");
	      
		PreparedStatement preparedStatement = conn.prepareStatement("delete Stock_Livraison WHERE id="+id+"");
		System.out.println("DAO 3");
		// execute update SQL stetement
		preparedStatement.executeUpdate();
		System.out.println("DAO 4");
		System.out.println("Record is delete to Stock_Livraison table!");
		conn.close();
		return 1;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			} 
	}
	
	@Override
	public int deleteOffre_Facture(int idOffre,int idfacture) {
		System.out.println("DAO");
	      try {
			classforname();    
			conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("DAO 2");
	      
		PreparedStatement preparedStatement = conn.prepareStatement("delete Facture_Offre WHERE offreID="+idOffre+" and factureID="+idfacture);
		System.out.println("DAO 3");
		// execute update SQL stetement
		preparedStatement.executeUpdate();
		System.out.println("DAO 4");
		System.out.println("Record is delete to Facture_Offre table!");
		conn.close();
		return 1;
			} catch (Exception e) {
				
				e.printStackTrace();
				return 0;
			} 
	}

	@Override
	public int updateBonlivraison(int id, String ref, Date datebl,
			int idclient, String livrepar, String receptionpar) {
		System.out.println("DAO");
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("DAO 2");
	      String reqsql="UPDATE BonLivraison SET datebl='"+ft.format(datebl)+"'"+
	    		  " ,clientID="+idclient+""+
	    		  " ,numeroref='"+ref+"'"+
	    		  " ,receptionpar='"+receptionpar+"'"+
	    		  " ,livrepar='"+livrepar+"'"+
	    		  " WHERE id="+id+"";
	      System.out.println(reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		System.out.println("DAO 3");
		// execute update SQL stetement
		int result=preparedStatement.executeUpdate();
		System.out.println("DAO 4");
		System.out.println("Record is updated to Client table!");
		conn.close();
		return result;
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
	}

	@Override
	public void desactiverOffre(int id) {
		System.out.println("DAO");
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("DAO 2");
	      String reqsql="UPDATE Offre SET Numero_offre=Numero_offre+'_tmp', activer=0 WHERE id="+id+"";
	      System.out.println(reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		System.out.println("DAO 3");
		// execute update SQL stetement
		preparedStatement.executeUpdate();
		System.out.println("DAO 4");
		System.out.println("Record is updated to Offre table!");
		conn.close();
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
			}
		
	}

	@Override
	public void AnnulerOffre(int id, int valeur) {
		System.out.println("DAO");
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("DAO 2");
	      String reqsql="UPDATE Offre SET annuler="+valeur+" WHERE id="+id+"";
	      System.out.println(reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		System.out.println("DAO 3");
		// execute update SQL stetement
		preparedStatement.executeUpdate();
		System.out.println("DAO 4");
		System.out.println("Record is updated to Offre table!");
		conn.close();
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
			}
		
	}

	@Override
	public int updateArticle(int idArticle, String designation, String ref,
			int idproduit, int idtype) {
		System.out.println("DAO");
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("DAO 2");
	      String reqsql="UPDATE Article SET reference='"+ref.replace("'", "''")+"',designationFournisseur='"+designation.replace("'", "''")+"',typeArticleID="+idtype+",ProduitID="+idproduit+" WHERE idArticle="+idArticle+"";
	      System.out.println(reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		System.out.println("DAO 3");
		// execute update SQL stetement
		int res=preparedStatement.executeUpdate();
		System.out.println("DAO 4");
		System.out.println("Record is updated to Offre table!");
		conn.close();
		return res;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			}
	}
	
	@Override
	public int updateCategorieArticle(int idcategorie, String categorie) {
		System.out.println("DAO");
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("DAO 2");
	      String reqsql="UPDATE CategorieArticle SET categorie='"+categorie+"' WHERE idcategorie="+idcategorie+"";
	      System.out.println(reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		System.out.println("DAO 3");
		// execute update SQL stetement
		int res=preparedStatement.executeUpdate();
		System.out.println("DAO 4");
		System.out.println("Record is updated to CategorieArticle table!");
		conn.close();
		return res;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			}
	}

	@Override
	public int updateTypeArticle(int idtype, String type) {
		System.out.println("DAO");
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("DAO 2");
	      String reqsql="UPDATE TypeArticle SET typearticle='"+type+"' WHERE idtype="+idtype+"";
	      System.out.println("REQ UPDATE TYPARTICLE =>"+reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		System.out.println("DAO 3");
		// execute update SQL stetement
		int res=preparedStatement.executeUpdate();
		System.out.println("DAO 4");
		System.out.println("Record is updated to TypeArticle table!");
		conn.close();
		return res;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			}
	}
	
	@Override
	public int updateUser(Utilisateur objetuser) {
		System.out.println("DAO");
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("DAO 2");
	      String reqsql="UPDATE Utilisateur SET cle='"+objetuser.getCle().replace("'", "''")+"', empID="+objetuser.getEmp().getId()+", typeuser='"+objetuser.getTypeuser()+"',Login='"+objetuser.getLogin()+"',Password='"+objetuser.getPassword()+"',email='"+objetuser.getEmail().replace("'", "''")+"' WHERE iduser="+objetuser.getIduser()+"";
	      System.out.println("REQ UPDATE Utilisateur =>"+reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		System.out.println("DAO 3");
		// execute update SQL stetement
		int res=preparedStatement.executeUpdate();
		System.out.println("DAO 4");
		System.out.println("Record is updated to Utilisateur table!");
		conn.close();
		return res;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			}
	}

	@Override
	public boolean deleteArticleToBoncommande(int id) {
		System.out.println("DAO");
	      try {
			classforname();    
			conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("DAO 2");
	      
		PreparedStatement preparedStatement = conn.prepareStatement("delete Boncommande_Article WHERE id="+id);
		System.out.println("DAO 3");
		// execute update SQL stetement
		preparedStatement.executeUpdate();
		System.out.println("DAO 4");
		System.out.println("Record is delete to Boncommande_Article table!");
		conn.close();
		return true;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return false;
			} 
	}

	@Override
	public int deleteFacture_Livraison(int id) {
	      try {
			classforname();    
			conn = DriverManager.getConnection(url, user, passwd);

		PreparedStatement preparedStatement = conn.prepareStatement("delete Facture_Livraison WHERE id="+id+"");
		// execute update SQL stetement
		preparedStatement.executeUpdate();
		System.out.println("Record is delete to Facture_Livraison table!");
		conn.close();
		return 1;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			} 
		
	}
	
	

	@Override
	public int deleteUser(int iduser) {
		System.out.println("DAO");
	      try {
			classforname();    
			conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("DAO 2");
	      String req="delete Utilisateur WHERE iduser="+iduser;
	      
	      System.out.println(req);
	      
		PreparedStatement preparedStatement = conn.prepareStatement(req);
		System.out.println("DAO 3");
		// execute update SQL stetement
		preparedStatement.executeUpdate();
		System.out.println("DAO 4");
		System.out.println("Record is delete to Utilisateur table!");
		conn.close();
		return 1;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			} 
	}

	@Override
	public int update(Pays pays) {
		System.out.println("Pays");
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("DAO 2");
	      String reqsql="UPDATE Pays SET pays='"+pays.getPays().replace("'", "''")+"' WHERE idpays="+pays.getIdpays()+"";
	      System.out.println("REQ UPDATE Pays =>"+reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		System.out.println("DAO 3");
		// execute update SQL stetement
		int res=preparedStatement.executeUpdate();
		System.out.println("DAO 4");
		System.out.println("Record is updated to Pays table!");
		conn.close();
		return res;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			}
	}

	@Override
	public years getyears(int years) {
		years objyears=null;
		try {
		      classforname();    
		      conn = DriverManager.getConnection(url, user, passwd);
		      //Création d'un objet Statement
		      Statement state = conn.createStatement();
		      //L'objet ResultSet contient le résultat de la requête SQL
		      ResultSet result = state.executeQuery("SELECT id FROM years where  YEAR(years.years)='"+years+"'");
		      while(result.next()){  
		        	System.out.println(result.getString(1));
		        	objyears=new years(result.getInt(1));
		      }

		      result.close();
		      state.close();
		      conn.close();
		    } catch (Exception e) {
		    	closeconnexion();  
		      e.printStackTrace();
		    }      
		return objyears;
	}

	@Override
	public int updateComptabiliteFacture(Facture facture) {

	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      String reqsql="UPDATE Facture SET ";
	      
	      if(facture.getDateechance()!=null)
	    	  reqsql+="dateEchance='"+ft.format(facture.getDateechance())+"'";
	      else
	    	  reqsql+="id="+facture.getId();
	      
	      reqsql+=" ,Etat='"+facture.getEtat()+"' , montantregler="+facture.getMontantregler()+" , montantrester="+facture.getMontantrester()+" , reglement='"+facture.getReglement()+"' , constater='"+facture.getConstater()+"'";
	      
	      if(facture.getDateconstater()!=null)
	    	  reqsql+=" ,dateconstater='"+ft.format(facture.getDateconstater())+"'";
	      else
	    	  reqsql+=" ,id="+facture.getId();
	      
	      if(facture.getDateechancep()!=null)
	      reqsql+=" ,dateEprevisionel='"+ft.format(facture.getDateechancep())+"'";
	      else
	    	  reqsql+=" ,id="+facture.getId();
	      
	      if(facture.getDatereglement()!=null)
	          reqsql+=" ,datereglement='"+ft.format(facture.getDatereglement())+"'";
	      else
	    	  reqsql+=" ,id="+facture.getId();
	      
	      		reqsql+=" WHERE id="+facture.getId()+"";
	      System.out.println("REQ UPDATE Facture =>"+reqsql);
	      
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		System.out.println("DAO 3");
		// execute update SQL stetement
		int res=preparedStatement.executeUpdate();
		System.out.println("DAO 4");
		System.out.println("Record is updated to Facture table!");
		conn.close();
		return res;
			} catch (Exception e) {
				closeconnexion();  
				e.printStackTrace();
				return 0;
			}
	}

	@Override
	public int Annuler(BonCommande commande) {
		System.out.println("DAO");
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("DAO 2");
	      String reqsql="UPDATE BonCommande SET annuler=1 WHERE commandeID="+commande.getId()+"";
	      System.out.println(reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		// execute update SQL stetement
		int r=preparedStatement.executeUpdate();
		System.out.println("DAO 4");
		System.out.println("Record is updated to BonCommande table!");
		conn.close();
		return r;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			}
	}

	@Override
	public int Annuler(Facture facture) {
		System.out.println("DAO");
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("DAO 2");
	      String reqsql="UPDATE Facture SET annuler=1 WHERE id="+facture.getId()+"";
	      System.out.println(reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		System.out.println("DAO 3");
		// execute update SQL stetement
		int r=preparedStatement.executeUpdate();
		System.out.println("DAO 4");
		System.out.println("Record is updated to Facture table!");
		conn.close();
		return r;
			} catch (Exception e) {   
				closeconnexion();
				e.printStackTrace();
				return 0;
			}
	}
	
	@Override
	public List<PrixArticlesOffre> getArticleOffre(Offre offre) {

		List<PrixArticlesOffre> liste=new ArrayList<PrixArticlesOffre>();
		try {
		      classforname();    
		      conn = DriverManager.getConnection(url, user, passwd);
		      //Création d'un objet Statement
		      Statement state = conn.createStatement();
		      //L'objet ResultSet contient le résultat de la requête SQL
		      ResultSet result = state.executeQuery("SELECT reference,Offre_Article.designation,chaineqantite,PU_HT2,PT_HT2,TypeOffre.type,Rubrique.description,Offre_Article.optionnel,Offre_Article.gratuite"
		      		+ " from Offre_Article "
		      		+ " inner join Offre on Offre_Article.OffreID=Offre.id"
		      		+ " inner join TypeOffre on Offre.typeoffreID=TypeOffre.id"
		      		+ " inner join Article on Offre_Article.articleID=Article.idArticle"
		      		+ " inner join Rubrique on Offre_Article.rubriqueId=Rubrique.id"
		      		+ " WHERE OffreID="+offre.getId()
		      		+ " Order by Rubrique.classement ,Offre_Article.id");
		      while(result.next()){
		        	liste.add(new PrixArticlesOffre(result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5),result.getString(6),result.getString(7),result.getInt(8),result.getInt(9)));
		      }   
		      result.close();
		      state.close();
		      conn.close();
		      return liste;
		    } catch (Exception e) {
		    	closeconnexion();  
		      e.printStackTrace();
		      return null;
		    }      
	}

	@Override
	public List<PrixArticleBC> getArticleBC(BonCommande bc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjetPrix gettotalPrix(Offre offre) {
		ObjetPrix total = null;
		classforname();    
		try {
		      
		      conn = DriverManager.getConnection(url, user, passwd);
		      //Création d'un objet Statement
		      Statement state = conn.createStatement();
		      //L'objet ResultSet contient le résultat de la requête SQL
		      ResultSet result = state.executeQuery("SELECT totalHT,TotalTTC,TVA from offre where id="+offre.getId()+"");
		      while(result.next()){  
		        	System.out.println(result.getString(1));
		        	total=new ObjetPrix(result.getString(1), result.getString(2), result.getString(3)) ;
		      }   
		      result.close();
		      state.close();
		      conn.close();
		      return total;
		    } catch (Exception e) {
		    	closeconnexion();
		      e.printStackTrace();
		      return null;
		    }      
	}

	@Override
	public ObjetPrix gettotalPrix(BonCommande bc) {
		ObjetPrix total = null;
		try {
		      classforname();    
		      conn = DriverManager.getConnection(url, user, passwd);
		      //Création d'un objet Statement
		      Statement state = conn.createStatement();
		      //L'objet ResultSet contient le résultat de la requête SQL
		      ResultSet result = state.executeQuery("SELECT totalht,totalttc,tva from BonCommande where commandeID="+bc.getId()+"");
		      while(result.next()){  
		        	System.out.println(result.getString(1));
		        	total=new ObjetPrix(result.getString(1), result.getString(2), result.getString(3)) ;
		      }   
		      result.close();
		      state.close();
		      conn.close();
		      return total;
		    } catch (Exception e) {
		    	closeconnexion();
		      e.printStackTrace();
		      return null;
		    }  
	}

	//----------------------Insert des Article----------------------------//
	@Override
	public int insertOffre_Article(Offre_Article obj) {
		int gratuite=0;
		int opt=0;
		if(obj.isGratuite())
			gratuite=1;
		if(obj.isOptionnel())
			opt=1;
		
		int mrg;
		int isdh;
		classforname(); 
	      try {
		 
			conn = DriverManager.getConnection(url, user, passwd);
	      if(obj.isMargetotal()==true)
	    	  mrg=1;
	      else
	    	  mrg=0;
	      if(obj.isPrixfournisseurisDH()==true)
	    	  isdh=1;
	      else
	    	  isdh=0;
	      Statement state = conn.createStatement();
	      ResultSet resultselect = state.executeQuery("SELECT MAX(id) AS ID FROM Offre_Article");
	      String req;
	      resultselect.next();
	      if(resultselect.getInt("ID")==0){
	    	  req="INSERT INTO Offre_Article (id,qantite,nombrepersonne,chaineqantite,PU_HT,PT_HT,designation,Marge,prixmarge,PU_HT2,PT_HT2,OffreID,articleID,categorieArticleID,ismargetotal,rubriqueID,puDH_fournisseur,ptDH_fournisseur,taux,retenu,prixfournisseurisDH,optionnel,gratuite,etat) "
						+" VALUES(1,"+obj.getQtef()+","+obj.getNombrepersonne()+",'"+obj.getLbqte()+"',"+obj.getPu()+","+obj.getPt()+",'"+obj.getDesignation().replace("'", "''")+"',"+obj.getMarge()+","+obj.getPrixmarge()+","+obj.getPu_calculer()+","+obj.getPu_calculer()+"*"+obj.getQtef()+","+obj.getOffre().getId()+","+obj.getArticle().getIdArticle()+","+obj.getCategorieArticle().getIdcategorie()+","+mrg+","+obj.getRubrique().getId()+","+obj.getPuDH_fournisseur()+","+obj.getPtDH_fournisseur()+","+obj.getTaux()+","+obj.getRetenu()+","+isdh+","+opt+","+gratuite+",'En cours')";
		      
	      }else{
	    	  req="INSERT INTO Offre_Article (id,qantite,nombrepersonne,chaineqantite,PU_HT,PT_HT,designation,Marge,prixmarge,PU_HT2,PT_HT2,OffreID,articleID,categorieArticleID,ismargetotal,rubriqueID,puDH_fournisseur,ptDH_fournisseur,taux,retenu,prixfournisseurisDH,optionnel,gratuite,etat) "
						+" VALUES((SELECT MAX(id) FROM Offre_Article)+1,"+obj.getQtef()+","+obj.getNombrepersonne()+",'"+obj.getLbqte()+"',"+obj.getPu()+","+obj.getPt()+",'"+obj.getDesignation().replace("'", "''")+"',"+obj.getMarge()+","+obj.getPrixmarge()+","+obj.getPu_calculer()+","+obj.getPu_calculer()+"*"+obj.getQtef()+","+obj.getOffre().getId()+","+obj.getArticle().getIdArticle()+","+obj.getCategorieArticle().getIdcategorie()+","+mrg+","+obj.getRubrique().getId()+","+obj.getPuDH_fournisseur()+","+obj.getPtDH_fournisseur()+","+obj.getTaux()+","+obj.getRetenu()+","+isdh+","+opt+","+gratuite+",'En cours')"; 
	      }
	       PreparedStatement preparedStatement = conn.prepareStatement(req);
		preparedStatement.executeUpdate();
		System.out.println("Record is insert to Offre_Article table!");
		conn.close();
		return 1;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			} 
	}

	@Override
	public int insertBoncommandeArticle(Boncommande_Article obj) {
		classforname();  
	      try {
	    	  conn = DriverManager.getConnection(url, user, passwd);
		      Statement state = conn.createStatement();
		      ResultSet resultselect = state.executeQuery("SELECT MAX(id) AS ID FROM Boncommande_Article");
		      String req;
		      resultselect.next();
		      if(resultselect.getInt("ID")==0){
		    	  req="INSERT INTO Boncommande_Article (id,qantite,PU_HT,PT_HT,designation,commandeID,articleID) "
							+" VALUES(1,"+obj.getQte()+","+obj.getPu()+","+obj.getPt()+",'"+obj.getDesignation().replace("'", "''")+"',"+obj.getCommande().getId()+","+obj.getArticle().getIdArticle()+")"; 
		      }else{
	       req="INSERT INTO Boncommande_Article (id,qantite,PU_HT,PT_HT,designation,commandeID,articleID) "
					+" VALUES((SELECT MAX(id) FROM Boncommande_Article)+1,"+obj.getQte()+","+obj.getPu()+","+obj.getPt()+",'"+obj.getDesignation().replace("'", "''")+"',"+obj.getCommande().getId()+","+obj.getArticle().getIdArticle()+")";
		      }
	      PreparedStatement preparedStatement = conn.prepareStatement(req);
		// execute update SQL stetement
		preparedStatement.executeUpdate();
		System.out.println("Record is insert to Boncommande_Article table!");
		conn.close();
		return 1;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			} 
	}

	@Override
	public int insertFactureArticle(Facture_Article obj) {
		classforname();  
	      try {
			conn = DriverManager.getConnection(url, user, passwd);
		      Statement state = conn.createStatement();
		      System.out.println("Prepare SELECT REQ Facture_Article");
		      ResultSet resultselect = state.executeQuery("SELECT MAX(id) AS ID FROM Facture_Article");
		      String req;
		      resultselect.next();
		      System.out.println("Prepare INSERT REQ Facture_Article");
		      if(resultselect.getInt("ID")==0){
		    	  req="INSERT INTO Facture_Article (id,qantite,chaineqantite,pu,pt,Coefficient,periodicite,designation,FactureID,articleID,categorieArticleID,prixmarge,marge,OffreArticleID) "
		  	      		+ " VALUES(1,"+obj.getQte()+",'"+obj.getLab()+"',"+obj.getMontantglobal()+","+obj.getPt()+","+obj.getCoefficient()+","+obj.getMensualite()+",'"+obj.getDesignation().replace("'", "''")+"',"+obj.getFacture().getId()+","+obj.getArticle().getIdArticle()+","+obj.getCategorieArticle().getIdcategorie()+","+obj.getPrixmarge()+","+obj.getMarge()+","+obj.getObjetOffreArticle().getId()+")";
		  	      
		      }else{
		    	  req="INSERT INTO Facture_Article (id,qantite,chaineqantite,pu,pt,Coefficient,periodicite,designation,FactureID,articleID,categorieArticleID,prixmarge,marge,OffreArticleID) "
		  	      		+ " VALUES((SELECT MAX(id) FROM Facture_Article)+1,"+obj.getQte()+",'"+obj.getLab()+"',"+obj.getMontantglobal()+","+obj.getPt()+","+obj.getCoefficient()+","+obj.getMensualite()+",'"+obj.getDesignation().replace("'", "''")+"',"+obj.getFacture().getId()+","+obj.getArticle().getIdArticle()+","+obj.getCategorieArticle().getIdcategorie()+","+obj.getPrixmarge()+","+obj.getMarge()+","+obj.getObjetOffreArticle().getId()+")";
		  	      
		      }
		      System.out.println("Requete :");
		      System.out.println(req);
	      PreparedStatement preparedStatement = conn.prepareStatement(req);
		preparedStatement.executeUpdate();
		System.out.println("Record is insert to Facture_Article table!");
		
		return 1;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			} 
	}

	@Override
	public boolean deletestock(Stock stock) {

	      try {
			classforname();    
			conn = DriverManager.getConnection(url, user, passwd);
		PreparedStatement preparedStatement = conn.prepareStatement("delete Stock WHERE id="+stock.getId()+"");
		preparedStatement.executeUpdate();
		
		return true;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return false;
			} 
	}

	@Override
	public int AddOffreToSansfactBC(Offre obj,String typeoffre) {
		System.out.println("DAO");
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      String req="INSERT INTO Facture_Offre (offreID,factureID) values ("+obj.getId()+",1)";
	      System.out.println(req);
	      PreparedStatement preparedStatement = conn.prepareStatement(req);
		// execute update SQL stetement
		preparedStatement.executeUpdate();
	      Statement state = conn.createStatement();
	      ResultSet resultselect = state.executeQuery("SELECT MAX(idboncommandeoffre) as ID from Boncommande_Offre");
	      resultselect.next();
	      if(typeoffre.equals("formation") || typeoffre.equals("certification") || typeoffre.equals("prestation")){
		      if(resultselect.getInt("ID")==0)
		    	  req="INSERT INTO Boncommande_Offre values (1,"+obj.getId()+",180)";
		      else
		    	  req="INSERT INTO Boncommande_Offre values ((SELECT MAX(idboncommandeoffre) from Boncommande_Offre)+1,"+obj.getId()+",180)";
		 } 
		 else if(typeoffre.equals("contrat")){
			 if(resultselect.getInt("ID")==0)
				 req="INSERT INTO Boncommande_Offre values (1,"+obj.getId()+",179)";
			 else
				 req="INSERT INTO Boncommande_Offre values ((SELECT MAX(idboncommandeoffre) from Boncommande_Offre)+1,"+obj.getId()+",179)";
		 }
		 else{
			 if(resultselect.getInt("ID")==0)
				 req="INSERT INTO Boncommande_Offre values (1,"+obj.getId()+",1)";
			 else
			    req="INSERT INTO Boncommande_Offre values ((SELECT MAX(idboncommandeoffre) from Boncommande_Offre)+1,"+obj.getId()+",1)";
		 }
	      preparedStatement = conn.prepareStatement(req);
		preparedStatement.executeUpdate();
		System.out.println("Record is updated to Facture_Offre Boncommande_Offre table!");
		conn.close();
		return 1;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			} 
	}

	@Override
	public int removeOffreTosansBoncomande(Offre offre) {
	      try {
			classforname();    
			conn = DriverManager.getConnection(url, user, passwd);
			String req="delete Boncommande_Offre WHERE CommandeID=1 and OffreID="+offre.getId()+"";
			System.out.println(req);
		PreparedStatement preparedStatement = conn.prepareStatement(req);
		// execute update SQL stetement
		preparedStatement.executeUpdate();
		System.out.println("Record is delete to Boncommande_Offre table!");
		conn.close();
		return 1;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			} 
	}

	@Override
	public int VerifyOffreTosansBoncommande(Offre objremove) {
		try {
		      classforname();    
		      conn = DriverManager.getConnection(url, user, passwd);
		      //Création d'un objet Statement
		      Statement state = conn.createStatement();
		      //L'objet ResultSet contient le résultat de la requête SQL
		      ResultSet result = state.executeQuery("SELECT * from Boncommande_Offre where OffreID="+objremove.getId()+"");
		      if(result.next()){
		    	  System.out.println("Offre deja liee");
		      }
		      else{
			      ResultSet resultselect = state.executeQuery("SELECT MAX(idboncommandeoffre) AS ID from Boncommande_Offre");
			      String req;
			      resultselect.next();
			      if(resultselect.getInt("ID")==0)
			    	  req="INSERT INTO Boncommande_Offre values (1,"+objremove.getId()+",1)";
			      else
			    	  req="INSERT INTO Boncommande_Offre values ((SELECT MAX(idboncommandeoffre) from Boncommande_Offre)+1,"+objremove.getId()+",1)";
			      
			      PreparedStatement preparedStatement = conn.prepareStatement(req);

				preparedStatement.executeUpdate();
				System.out.println("Record is updated to Facture_Offre Boncommande_Offre table!");
				
			      String reqsql="UPDATE Offre SET etatoffre='en cours' where id="+objremove.getId();
			      System.out.println(reqsql);
			      PreparedStatement prepared = conn.prepareStatement(reqsql);
			      prepared.executeUpdate();
		      }
		      result.close();
		      state.close();
		      conn.close();
		      return 1;
		    } catch (Exception e) {
		    	closeconnexion();
		      e.printStackTrace();
		      return 0;
		    } 
	}

	@Override
	public int removeOffreToSansFacture(Offre offre) {

	      try {
			classforname();    
			conn = DriverManager.getConnection(url, user, passwd);

		PreparedStatement preparedStatement = conn.prepareStatement("delete Facture_Offre WHERE factureID=1 and OffreID="+offre.getId()+"");
		// execute update SQL stetement
		preparedStatement.executeUpdate();
		System.out.println("Record is delete to Facture_Offre table!");
		conn.close();
		return 1;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			} 
	}
	
	@Override
	public int VerifyOffreTosansFactureforAdd(Offre tempooffre) {
		try {
		      classforname();    
		      conn = DriverManager.getConnection(url, user, passwd);
		      Statement state = conn.createStatement();
		      String selectreq="SELECT * from Facture_Offre where OffreID="+tempooffre.getId()+"";
		      ResultSet result = state.executeQuery(selectreq);
		      System.out.println(selectreq);
		      if(result.next()==true){
		    	  System.out.println("Offre deja liee");
		    	  String  req="delete Facture_Offre where factureID=1 AND offreID="+tempooffre.getId();
			    	PreparedStatement preparedStatement = conn.prepareStatement(req);
			    	System.out.println("REQ =>"+req);
					preparedStatement.executeUpdate();
					
					req="update Offre set etatoffre='Facturée' where id="+tempooffre.getId();
			    	preparedStatement = conn.prepareStatement(req);
			    	System.out.println("REQ  =>"+req);
					preparedStatement.executeUpdate();
		      }
		      else{
		    	  
		    	String  req="INSERT INTO Facture_Offre values ("+tempooffre.getId()+",1)";
			      
		    	PreparedStatement preparedStatement = conn.prepareStatement(req);
		    	System.out.println("REQ ="+req);
				preparedStatement.executeUpdate();
				

		      }
		      result.close();
		      state.close();
		      conn.close();
		      return 1;
		    } catch (Exception e) {
		    	closeconnexion();
		      e.printStackTrace();
		      return 0;
		    } 
	}

	@Override
	public int VerifyOffreTosansFacture(Offre tempooffre) {
		try {
		      classforname();    
		      conn = DriverManager.getConnection(url, user, passwd);
		      Statement state = conn.createStatement();
		      String selectreq="SELECT * from Facture_Offre where OffreID="+tempooffre.getId()+"";
		      ResultSet result = state.executeQuery(selectreq);
		      System.out.println(selectreq);
		      if(result.next()==true){
		    	  System.out.println("Offre deja liee");
		    	  
		      }
		      else{
		    	  
		    	String  req="INSERT INTO Facture_Offre values ("+tempooffre.getId()+",1)";
			      
		    	PreparedStatement preparedStatement = conn.prepareStatement(req);
		    	System.out.println("REQ ="+req);
				preparedStatement.executeUpdate();
				
				selectreq="SELECT * from Boncommande_Offre where OffreID="+tempooffre.getId()+" AND ( CommandeID<>1 AND CommandeID<>180) ";
			    result = state.executeQuery(selectreq);
			      if(result.next()==true)
			    	  req="update Offre set etatoffre='concrétisée' where id="+tempooffre.getId();
			      else
			    	  req="update Offre set etatoffre='En cours' where id="+tempooffre.getId();
			      
		    	preparedStatement = conn.prepareStatement(req);
		    	System.out.println("REQ  =>"+req);
				preparedStatement.executeUpdate();
		      }
		      result.close();
		      state.close();
		      conn.close();
		      return 1;
		    } catch (Exception e) {
		    	closeconnexion();
		      e.printStackTrace();
		      return 0;
		    } 
	}

	@Override
	public int updateFacture_Offrecontrat(int idfacture, int idoffre) {
		System.out.println("DAO");
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("DAO 2");
	      String reqsql="UPDATE Facture_Offre SET offreID="+idoffre+" WHERE factureID="+idfacture+"";
	      System.out.println(reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		System.out.println("DAO 3");
		// execute update SQL stetement
		int r=preparedStatement.executeUpdate();
		System.out.println("DAO 4");
		System.out.println("Record is updated to Facture_Offre table!");
		conn.close();
		return r;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			}
	}
	
	@Override
	public boolean updateContactFournisseur(Contact contact) {
	      try {
			classforname(); 
			conn = DriverManager.getConnection(url, user, passwd);
	      String reqsql="UPDATE Contact SET nom='"+contact.getNom().replace("'", "''")+"',prenom='"+contact.getPrenom().replace("'", "''")+"',gsm1='"+contact.getGsm1().replace("'", "''")+"',tel='"+contact.getTel().replace("'", "''")+"',faxclient='"+contact.getFaxclient().replace("'", "''")+"',email1='"+contact.getEmail()+"', fournisseurID="+contact.getFournisseur().getIdfournisseur()+" WHERE codeclient="+contact.getCodeclient()+"";
	 
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);

		// execute update SQL stetement
		int r=preparedStatement.executeUpdate();
		preparedStatement.close();
		conn.close();
		 System.out.println("UPDATE CONTACT SUCCESS");
		return true;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return false;
			}
	}

	@Override
	public int updateContact(Contact contact) {
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      String reqsql="UPDATE Contact SET nom='"+contact.getNom().replace("'", "''")+"',prenom='"+contact.getPrenom().replace("'", "''")+"',adresse='"+contact.getAdr().replace("'", "''")+"',fonction='"+contact.getFonction().replace("'", "''")+"',dept='"+contact.getDept().replace("'", "''")+"',tel='"+contact.getTel().replace("'", "''")+"',faxclient='"+contact.getFaxclient().replace("'", "''")+"',gsm1='"+contact.getGsm1().replace("'", "''")+"',email1='"+contact.getEmail()+"',profil='"+contact.getProfil()+"',paysID="+contact.getClient().getPays().getIdpays()+",villeID="+contact.getClient().getVille().getIdville()+", clientID="+contact.getClient().getIdclient()+" WHERE codeclient="+contact.getCodeclient()+"";
	      System.out.println(reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);

		// execute update SQL stetement
		int r=preparedStatement.executeUpdate();
	      preparedStatement.close();
	      conn.close();
	      System.out.println("UPDATE CONTACT SUCCESS");
		return r;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			}
	}

	@Override
	public int UpdateCompatbiliteBC(BonCommande boncommande) {

	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);

	      String reqsql="UPDATE BonCommande SET";
	      if(boncommande.getDateechance()!=null)
	    	  reqsql+= "DateEchance='"+ft.format(boncommande.getDateechance())+"',";
	      
	      reqsql+="Etat='"+boncommande.getEtat()+"' , Reglement='"+boncommande.getReglement()+"', constater='"+boncommande.getConstater()+"' WHERE commandeID="+boncommande.getId()+"";
	      System.out.println("REQ UPDATE BonCommande =>"+reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
	      
		int res=preparedStatement.executeUpdate();
		
		System.out.println("Record is updated to BonCommandes table!");
		conn.close();
		return res;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			}
	}

	@Override
	public List<BonCommande> getNumbcForFacture(Facture facture) {
		List<BonCommande> liste=new ArrayList<BonCommande>();
		try {
		      classforname();    
		      conn = DriverManager.getConnection(url, user, passwd);
		      //Création d'un objet Statement
		      Statement state = conn.createStatement();
		      //L'objet ResultSet contient le résultat de la requête SQL
		      ResultSet result = state.executeQuery("select numercommande,Fournisseur.nomSociete,BonCommande.totalttc,BonCommande.echeance,BonCommande.datecommande,BonCommande.DateEchance,BonCommande.Etat,BonCommande.Reglement,BonCommande.commandeID from Facture"
		      		+ " LEFT join Facture_Offre on Facture_Offre.factureID=Facture.id"
		      		+ " LEFT  join Offre on Offre.id=Facture_Offre.offreID"
		      		+ " LEFT  join Boncommande_Offre on Boncommande_Offre.OffreID=Facture_Offre.offreID"
		      		+ " LEFT  join BonCommande on BonCommande.commandeID=Boncommande_Offre.CommandeID"
		      		+ " INNER join Fournisseur on Fournisseur.idfournisseur=BonCommande.FournisseurID"
		      		+ " LEFT join Facture_Livraison on Facture_Livraison.factureID=Facture.id"
		      		+ " LEFT join BonLivraison on BonLivraison.id=Facture_Livraison.livraisonID"
		      		+ " where Facture.activer=1 and Facture.annuler=0 and"
		      		+ " Offre.activer=1 and Offre.annuler=0 and"
		      		+ " BonCommande.activer=1 and BonCommande.annuler=0 and Facture.id="+facture.getId());
		      int i=1;
		      while(result.next()){
		    	 	BonCommande tempo=new BonCommande();
		
		        	tempo.setNumercommande(result.getString(1));
		        	Fournisseur f=new Fournisseur();
		        	f.setNomsociete(result.getString(2));
		        	tempo.setFournisseur(f);
		        	tempo.setTotalttc(result.getDouble(3));
		        	tempo.setEcheance(result.getString(4));
		        	tempo.setDatecommande(result.getDate(5));
		        	tempo.setDateechance(result.getDate(6));
		        	tempo.setEtat(result.getString(7));
		        	tempo.setReglement(result.getString(8));
		        	tempo.setId(result.getInt(9));
		        	liste.add(tempo);
		        	i++;
		      }
		      result.close();
		      state.close();
		      conn.close();
		      return liste;
		    } catch (Exception e) {
		    	closeconnexion();
		      e.printStackTrace();
		      return null;
		    }      
		
	}

	@Override
	public List<Facture> getNumfactForBC(BonCommande bc) {
		List<Facture> liste=new ArrayList<Facture>();
		try {
		      classforname();    
		      conn = DriverManager.getConnection(url, user, passwd);
		      //Création d'un objet Statement
		      Statement state = conn.createStatement();
		      //L'objet ResultSet contient le résultat de la requête SQL
		      ResultSet result = state.executeQuery("select numero_facture,Client.societe,Facture.totalttc,Facture.conditiondepayment,Facture.datefacture,Facture.DateEchance,Facture.Etat,Facture.reglement,Facture.montantregler,Facture.montantrester,Facture.id from Facture " +
		      		" LEFT join Facture_Offre on Facture_Offre.factureID=Facture.id" +
		      		" LEFT  join Offre on Offre.id=Facture_Offre.offreID" +
		      		" LEFT  join Boncommande_Offre on Boncommande_Offre.OffreID=Facture_Offre.offreID" +
		      		" LEFT  join BonCommande on BonCommande.commandeID=Boncommande_Offre.CommandeID" +
		      		" INNER join Client on Client.idclient=Facture.clientID" +
		      		" LEFT join Facture_Livraison on Facture_Livraison.factureID=Facture.id" +
		      		" LEFT join BonLivraison on BonLivraison.id=Facture_Livraison.livraisonID" +
		      		" where Facture.activer=1 and Facture.annuler=0 and " +
		      		" Offre.activer=1 and Offre.annuler=0 and " +
		      		" BonCommande.activer=1 and BonCommande.annuler=0 and BonCommande.commandeID="+bc.getId());
		      while(result.next()){
		    	 
		    	  	Facture tempo=new Facture();
		
		        	tempo.setRef(result.getString(1));
		        	Client c=new Client();
		        	c.setSociete(result.getString(2));
		        	tempo.setClient(c);
		        	tempo.setTotalttc(result.getDouble(3));
		        	tempo.setConditiondepayment(result.getString(4));
		        	tempo.setDatefacture(result.getDate(5));
		        	tempo.setDateechance(result.getDate(6));
		        	tempo.setEtat(result.getString(7));
		        	tempo.setReglement(result.getString(8));
		        	tempo.setMontantregler(result.getDouble(9));
		        	tempo.setMontantrester(result.getDouble(10));
		        	tempo.setId(result.getInt(11));
		        	liste.add(tempo);
		        	System.out.println(result.getString(1)+"-"+result.getString(2)+"---"+result.getDouble(3)+"--"+result.getString(4)+"--"+result.getDate(5)+"----"+result.getDate(6)+"--"+result.getString(7)+"----"+result.getString(8)+"---"+result.getDouble(9)+"---"+result.getDouble(10));
		      }
		      result.close();
		      state.close();
		      conn.close();
		      return liste;
		    } catch (Exception e) {
		    	closeconnexion();
		      e.printStackTrace();
		      return null;
		    }      
	}

	@Override
	public int updatecaisse(Caisse caisse) {
		System.out.println("DAO");
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("DAO 2");
	      String reqsql="UPDATE Caisse SET debutcaisse='"+ft.format(caisse.getDatedebutcaisse())+"',fincaisse='"+ft.format(caisse.getDatefincaisse())+"' where id="+caisse.getId();
	      System.out.println("REQ UPDATE caisse =>"+reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		System.out.println("DAO 3");
		// execute update SQL stetement
		int res=preparedStatement.executeUpdate();
		System.out.println("DAO 4");
		System.out.println("Record is updated to caisse table!");
		conn.close();
		return res;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			}
	}

	@Override
	public int updatelignecaisse(LigneCaisse ligne) {
		System.out.println("DAO");
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("DAO 2");
	      String reqsql="UPDATE LigneCaisse SET  reglement='"+ligne.getReglement().replace("'", "''")+"', dateligne='"+ft.format(ligne.getDateligne())+"',entree="+ligne.getEntree()+",sortie="+ligne.getSortie()+",details='"+ligne.getDetails()+"'";
	      
	      if(ligne.getEmp()!=null){
	    	  if(ligne.getEmp().getId()>0)
	    	      reqsql+= ", empID="+ligne.getEmp().getId()+"";
	      }
	      reqsql+= " where id="+ligne.getId();
	      System.out.println("REQ UPDATE LigneCaisse =>"+reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		System.out.println("DAO 3");
		// execute update SQL stetement
		int res=preparedStatement.executeUpdate();
		System.out.println("DAO 4");
		System.out.println("Record is updated to LigneCaisse table!");
		conn.close();
		return res;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			}
	}

	@Override
	public int updatePrixCaisse(Caisse caisse) {
	      try {
	    	  String sumentree;
	    	  String sumsorti;
	    	  classforname();  
	    	  conn = DriverManager.getConnection(url, user, passwd);
	    	  if(caisse.getEntreetotal()!=0)
	    		  sumentree="(SELECT SUM(entree) from LigneCaisse where caisseID="+caisse.getId()+")";
	    	  else
	    		  sumentree="0";
	    	  if(caisse.getSortietotal()!=0)
	    		  sumsorti="(SELECT SUM(sortie) from LigneCaisse where caisseID="+caisse.getId()+")";
	    	  else
	    		  sumsorti="0";
	    	  String reqsql="UPDATE Caisse SET entreetotal="+sumentree+",sortietotal="+sumsorti+",restetotal="+sumentree+"-"+sumsorti+" where id="+caisse.getId();
	    	  PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
	    	  int res=preparedStatement.executeUpdate();
	    	  if(res!=0){
	    		  String reqLigne="update LigneCaisse SET entree=(SELECT restetotal from Caisse where id="+caisse.getId()+") where CaissePresedantID="+caisse.getId();
	    		  preparedStatement = conn.prepareStatement(reqLigne);
		    	  preparedStatement.executeUpdate();
		    	  Statement state = conn.createStatement();
		    	  String statreq="SELECT caisseID from LigneCaisse where CaissePresedantID="+caisse.getId();
		    	  ResultSet resultselect = state.executeQuery(statreq);
			      resultselect.next();
			      System.out.println("NEXT =>"+resultselect.getInt("caisseID"));
			      int caisseIDsuivant=resultselect.getInt("caisseID");
			      Caisse caissesuivant=new Caisse();
			      caissesuivant.setId(caisseIDsuivant);
			      if(caisse.getEntreetotal()!=0)
		    		  sumentree="(SELECT SUM(entree) from LigneCaisse where caisseID="+caisseIDsuivant+")";
		    	  else
		    		  sumentree="0";
		    	  if(caisse.getSortietotal()!=0)
		    		  sumsorti="(SELECT SUM(sortie) from LigneCaisse where caisseID="+caisseIDsuivant+")";
		    	  else
		    		  sumsorti="0";
		    	  reqsql="UPDATE Caisse SET entreetotal="+sumentree+",sortietotal="+sumsorti+",restetotal="+sumentree+"-"+sumsorti+" where id="+caisseIDsuivant;
		    	  preparedStatement = conn.prepareStatement(reqsql);
		    	  preparedStatement.executeUpdate();
	    	  }
	    	  	
	    	  conn.close();
	    	  return res;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			}
	}


	@Override
	public boolean deleteArticleOffre(int id) {

	      try {
			classforname();    
			conn = DriverManager.getConnection(url, user, passwd);

	      
		PreparedStatement preparedStatement = conn.prepareStatement("delete Offre_Article WHERE id="+id+"");

		// execute update SQL stetement
		preparedStatement.executeUpdate();

		conn.close();
		return true;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return false;
			} 
	}

	@Override
	public boolean deleteDepartement(int id) {

	      try {
			classforname();    
			conn = DriverManager.getConnection(url, user, passwd);

	      
		PreparedStatement preparedStatement = conn.prepareStatement("delete Departement WHERE id="+id+"");

		preparedStatement.executeUpdate();

		conn.close();
		return true;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return false;
			} 
	}

	@Override
	public boolean deleteEmployee(int id) {

	      try {
			classforname();    
			conn = DriverManager.getConnection(url, user, passwd);

	      
		PreparedStatement preparedStatement = conn.prepareStatement("delete Employee WHERE id="+id+"");

		preparedStatement.executeUpdate();

		conn.close();
		return true;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return false;
			} 
	}
	

	@Override
	public int updateprixOffre(int id,TypePaiement devise) {
		System.out.println("update Prix Offre DAO");
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
			Statement state = conn.createStatement();
		      ResultSet resultselect = state.executeQuery("SELECT SUM(PT_HT2) AS somme from Offre_Article where OffreID="+id +" and optionnel<>1");
		      resultselect.next();
		      String reqsql;
			      if(resultselect.getInt("somme")==0)
			    	  reqsql="  update Offre SET totalHT=0, TVA=0, TotalTTC=0 where id="+id;
			      else{
			    	  if(devise.getTypepaiement().equals("MAD"))
			    		  reqsql="  update Offre SET "
			    		      		+ "totalHT=(SELECT SUM(PT_HT2) from Offre_Article where OffreID="+id+" and optionnel<>1),"
			    		      		+ "TVA=ROUND((SELECT SUM(PT_HT2) from Offre_Article where OffreID="+id+" and optionnel<>1)*0.2,2),"
			    		      		+ "TotalTTC=ROUND((SELECT SUM(PT_HT2) from Offre_Article where OffreID="+id+" and optionnel<>1)+(SELECT SUM(PT_HT2) from Offre_Article where OffreID="+id+" and optionnel<>1)*0.2,2),"
			    		      		+ "totalmarge=ROUND((SELECT SUM(prixmarge) from Offre_Article where OffreID="+id+" and optionnel<>1),2) "
			    		      		+ " where id="+id;
			    	  else
			    		  reqsql="  update Offre SET "
			    		      		+ " totalHT=(SELECT SUM(PT_HT2) from Offre_Article where OffreID="+id+" and optionnel<>1),"
			    		      		+ " TVA=0,"
			    		      		+ " TotalTTC=ROUND((SELECT SUM(PT_HT2) from Offre_Article where OffreID="+id+" and optionnel<>1),2),"
			    		      		+ " totalmarge=ROUND((SELECT SUM(prixmarge) from Offre_Article where OffreID="+id+" and optionnel<>1),2) "
			    		      		+ "  where id="+id;
			      }
	    
			      
	      System.out.println(reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);

		int res=preparedStatement.executeUpdate();

		conn.close();
		System.out.println("close success");
		return res;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			}
	}

	@Override
	public int updateEmployee(Employee employee) {

	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);

	      String reqsql="update Employee SET Nom='"+employee.getNom().replace("'", "''")+"' ,Prenom='"+employee.getPrenom().replace("'", "''")+"',comptebancaire='"+employee.getComptebancaire().replace("'", "''")+"',CNSS='"+employee.getCnss().replace("'", "''")+"',salaire="+employee.getSalaire()+",CIN='"+employee.getCin().replace("'", "''")+"',profil='"+employee.getProfil().replace("'", "''")+"',[departementID]="+employee.getDepartement().getId()+" where id="+employee.getId();
	      

	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);

		int res=preparedStatement.executeUpdate();

		conn.close();
		return res;
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
	}

	@Override
	public int updateDepartement(Departement dept) {

	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);

	      String reqsql="update Departement SET Nom='"+dept.getNom()+"'  where id="+dept.getId();
	      
	      System.out.println("REQ UPDATE Departement =>"+reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);

		int res=preparedStatement.executeUpdate();

		conn.close();
		return res;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			}
	}

	@Override
	public int updateOffre_Article(Offre_Article ligne) {
		int op=0;
		if(ligne.isOptionnel()==true)
			op=1;
		
		int isdh;
		if(ligne.isPrixfournisseurisDH()==true)
			isdh=1;
		else
			isdh=0;
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      
	      String reqsql="update Offre_Article SET  optionnel="+op+", etat='"+ligne.getEtat()+"', puDH_fournisseur="+ligne.getPuDH_fournisseur()+", ptDH_fournisseur="+ligne.getPtDH_fournisseur()+",retenu="+ligne.getRetenu()+", taux="+ligne.getTaux()+", prixfournisseurisDH="+isdh+", qantite="+ligne.getQtef()+",chaineqantite='"+ligne.getLbqte()+"',PU_HT="+ligne.getPu()+",PT_HT="+ligne.getPt()+",designation='"+ligne.getDesignation().replace("'", "''")+"',Marge="+ligne.getMarge()+",prixmarge="+ligne.getPrixmarge()+",PU_HT2="+ligne.getPu_calculer()+",PT_HT2="+ligne.getPt_calculer()+",articleID="+ligne.getArticle().getIdArticle()+",categorieArticleID="+ligne.getCategorieArticle().getIdcategorie()+", rubriqueID="+ligne.getRubrique().getId()+"  where id="+ligne.getId();
	      
	      System.out.println(reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);

		int res=preparedStatement.executeUpdate();

		conn.close();
		return res;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			}
	}

	@Override
	public int insertFactureAvoir(int idfacture, int avoirID) {
		try {
		      classforname();    
		      conn = DriverManager.getConnection(url, user, passwd);

		      Statement state = conn.createStatement();
		      ResultSet resultselect = state.executeQuery("SELECT MAX(id) AS ID from Facture_Avoir");
		      String req;
		      resultselect.next();
			 if(resultselect.getInt("ID")==0)
				  req="INSERT INTO Facture_Avoir (id,FactureID,avoirID) values (1,"+idfacture+","+avoirID+")";
			 else
		    	  req="INSERT INTO Facture_Avoir (id,FactureID,avoirID) values (((SELECT MAX(id) from Facture_Avoir)+1),"+idfacture+","+avoirID+")";

			      PreparedStatement preparedStatement = conn.prepareStatement(req);
				// execute update SQL stetement
				preparedStatement.executeUpdate();
				
				System.out.println("Record is INSERT to Facture_Avoir  table!");
		      state.close();
		      conn.close();
		      return 1;
		    } catch (Exception e) {
		    	closeconnexion();
		      e.printStackTrace();
		      return 0;
		    } 
	}

	@Override
	public int udpate(Cnss cnss) {
		System.out.println("update Cnss");
		int constat=0;
		if(cnss.isContstater()==true)
			constat=1;
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("DAO 2");
	      String reqsql="update Cnss SET montant="+cnss.getMontant()+",datecnss='"+ft.format(cnss.getDatecnss())+"', dateconstat='"+ft.format(cnss.getDateconstat())+"',yearsID="+cnss.getYears().getId()+",empID="+cnss.getEmp().getId()+" ,contstater="+constat+"  where id="+cnss.getId();
	      
	      System.out.println("REQ UPDATE Cnss =>"+reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		System.out.println("DAO 3");
		// execute update SQL stetement
		int res=preparedStatement.executeUpdate();
		System.out.println("DAO 4");
		System.out.println("Record is updated to Cnss table!");
		conn.close();
		return res;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			}
	}

	@Override
	public int udpate(Salaires salaire) {
		System.out.println("update salaire");
		
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("DAO 2");
	      String reqsql="update Salaires SET  montant="+salaire.getMontant()+",datesalaire='"+ft.format(salaire.getDatesalaire())+"',yearsID="+salaire.getYears().getId()+",empID="+salaire.getEmp().getId()+" where id="+salaire.getId();
	      
	      System.out.println("REQ UPDATE Salaires =>"+reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		System.out.println("DAO 3");
		// execute update SQL stetement
		int res=preparedStatement.executeUpdate();
		System.out.println("DAO 4");
		System.out.println("Record is updated to Salaires table!");
		conn.close();
		return res;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			}
	}

	@Override
	public int update(TypeCharge typecharge) {
		System.out.println("update TypeCharge");		
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("DAO 2");
	      String reqsql="update TypeCharge SET  typecharge='"+typecharge.getTypecharge().replace("'", "''")+"',description='"+typecharge.getDescription().replace("'", "''")+"' where id="+typecharge.getId();
	      
	      System.out.println("REQ UPDATE TypeCharge =>"+reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		System.out.println("DAO 3");
		// execute update SQL stetement
		int res=preparedStatement.executeUpdate();
		System.out.println("DAO 4");
		System.out.println("Record is updated to TypeCharge table!");
		conn.close();
		return res;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			}
	}

	@Override
	public int update(Charge charge) {
		System.out.println("update Charge");
		int constat=0;
		if(charge.isConstater()==true)
			constat=1;
		
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("DAO 2");
	      String reqsql="";
	      if(charge.getDatecontsater()!=null)
	    	  reqsql="update Charge SET  reglement='"+charge.getReglement().replace("'","''")+"',  montant="+charge.getMontant()+",datecharge='"+ft.format(charge.getDatecharge())+"' ,dateconstater='"+ft.format(charge.getDatecontsater())+"' , constater="+constat+" , yearsID="+charge.getYears().getId()+", TypeChargeID="+charge.getTypecharge().getId()+" where id="+charge.getId();
	      else
	    	  reqsql="update Charge SET  reglement='"+charge.getReglement().replace("'","''")+"',  montant="+charge.getMontant()+",datecharge='"+ft.format(charge.getDatecharge())+"' , constater="+constat+" , yearsID="+charge.getYears().getId()+", TypeChargeID="+charge.getTypecharge().getId()+" where id="+charge.getId();
	      
	      System.out.println("REQ UPDATE Charge =>"+reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		System.out.println("DAO 3");
		// execute update SQL stetement
		int res=preparedStatement.executeUpdate();
		System.out.println("DAO 4");
		System.out.println("Record is updated to Charge table!");
		conn.close();
		return res;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			}
	}

	@Override
	public int delete(Cnss cnss) {
		System.out.println("Cnss");
	      try {
			classforname();    
			conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("DAO 2");
	      
		PreparedStatement preparedStatement = conn.prepareStatement("delete Cnss WHERE id="+cnss.getId()+"");
		System.out.println("DAO 3");
		// execute update SQL stetement
		preparedStatement.executeUpdate();
		System.out.println("DAO 4");
		System.out.println("Record is delete to Cnss table!");
		conn.close();
		return 1;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			} 
	}

	@Override
	public int delete(Salaires salaire) {
		System.out.println("Salaires");
	      try {
			classforname();    
			conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("DAO 2");
	      
		PreparedStatement preparedStatement = conn.prepareStatement("delete Salaires WHERE id="+salaire.getId()+"");
		System.out.println("DAO 3");
		// execute update SQL stetement
		preparedStatement.executeUpdate();
		System.out.println("DAO 4");
		System.out.println("Record is delete to Salaires table!");
		conn.close();
		return 1;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			} 
	}

	@Override
	public int delete(Charge charge) {
	      try {
			classforname();    
			conn = DriverManager.getConnection(url, user, passwd);
		PreparedStatement preparedStatement = conn.prepareStatement("delete Charge WHERE id="+charge.getId()+"");
		preparedStatement.executeUpdate();
		System.out.println("Record is delete to Charge table!");
		conn.close();
		return 1;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			} 
	}

	@Override
	public int update(Avoir_Articles avoirarticle) {
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      String reqsql="update Avoir_Articles SET  avoirclientID="+avoirarticle.getAvoir().getId()+",articleID='"+avoirarticle.getArticle().getIdArticle()+"' ,categorieArticleID='"+avoirarticle.getCategorieArticle().getIdcategorie()+"' , designation='"+avoirarticle.getDesignation().replace("'", "''")+"' , pu="+avoirarticle.getPu()+", pt="+avoirarticle.getPt()+",qte="+avoirarticle.getQte()+",chaineqte='"+avoirarticle.getChaineqte()+"' where id="+avoirarticle.getId();
	      System.out.println("REQ UPDATE Avoir_Articles =>"+reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		int res=preparedStatement.executeUpdate();
		System.out.println("Record is updated to Avoir_Articles table!");
		conn.close();
		return res;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			}
	}

	@Override
	public int update(AvoirClient avoirclient) {
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      String reqsum="(SELECT SUM(pt) FROM Avoir_Articles where avoirclientID="+avoirclient.getId()+")";
	      String reqsql="update AvoirClient SET numero_avoir='"+avoirclient.getNumero_avoir().replace("'", "''")+"',dateavoir='"+ft.format(avoirclient.getDateavoir())+"',ClientID="+avoirclient.getClient().getIdclient()+",reglement='"+avoirclient.getReglement().replace("'", "''")+"',typepaiementID="+avoirclient.getTypepaiement().getId()+", totalht="+reqsum+",tva=("+reqsum+"*0.2),totalttc=("+reqsum+"+("+reqsum+"*0.2)),totalttcpaye=("+reqsum+"+("+reqsum+"*0.2))  where id="+avoirclient.getId();	    
	      System.out.println("REQ UPDATE AvoirClient =>"+reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		int res=preparedStatement.executeUpdate();
		System.out.println("Record is updated to AvoirClient table!");
		conn.close();
		return res;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			}
	}

	@Override
	public int delete(Avoir_Articles avoirarticle) {
	      try {
			classforname();    
			conn = DriverManager.getConnection(url, user, passwd);
		PreparedStatement preparedStatement = conn.prepareStatement("delete Avoir_Articles WHERE id="+avoirarticle.getId()+"");
		preparedStatement.executeUpdate();
		System.out.println("Record is delete to Avoir_Articles table!");
		return 1;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			} 
	}

	@Override
	public int delete(Facture_Avoir facture_avoir) {
	      try {
			classforname();    
			conn = DriverManager.getConnection(url, user, passwd);
		PreparedStatement preparedStatement = conn.prepareStatement("delete facture_avoir WHERE FactureID="+facture_avoir.getFacture().getId()+" and avoirID="+facture_avoir.getAvoir().getId());
		preparedStatement.executeUpdate();
		System.out.println("Record is delete to facture_avoir table!");
		conn.close();
		return 1;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			} 
	}

	@Override
	public int confirmer(Facture facttempo) {
	     try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("DAO 2");
	      String reqsql="UPDATE Facture SET annuler=0 WHERE id="+facttempo.getId()+"";
	      System.out.println(reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		int r=preparedStatement.executeUpdate();
		System.out.println("Record is updated to Facture table!");
		conn.close();
		return r;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			}
	}

	@Override
	public int update(BonCommande commande) {
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      Statement state = conn.createStatement();
	      String reqht="(SELECT SUM(PT_HT) as somme_pt from Boncommande_Article where commandeID="+commande.getId()+")";
	      ResultSet resultselect = state.executeQuery(reqht);
	      resultselect.next();
	      String reqsql;
		      if(resultselect.getInt("somme_pt")==0){
		  	        reqsql="UPDATE BonCommande SET totalht=0,tva=0,totalttc=0,totalnet=0+fraitransport WHERE commandeID="+commande.getId()+"";
		  	     }
		      else{
	      String reqtva=reqht+"*0.2";
	      String reqttc="("+reqht+"+"+reqtva+")";
	      String reqnet="("+reqttc+"+fraitransport)";
	      String reqnetsanstva="("+reqht+"+fraitransport)";
	     reqsql="";
	      System.out.println("Type Paiement"+commande.getTypepaiement());
	      if(commande.getTypepaiement().getTypepaiement().equals("MAD"))
	        reqsql="UPDATE BonCommande SET totalht="+reqht+",tva="+reqtva+",totalttc="+reqttc+",totalnet="+reqnet+" WHERE commandeID="+commande.getId()+"";
	      else
	    	  reqsql="UPDATE BonCommande SET totalht="+reqht+",tva=0,totalttc="+reqht+",totalnet="+reqnetsanstva+" WHERE commandeID="+commande.getId()+"";
		      }  
	      System.out.println(reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);

		int r=preparedStatement.executeUpdate();

		System.out.println("Record is updated to BonCommande table!");
		conn.close();
		return r;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			}
	}

	@Override
	public int deletesalairesfromOrdre(OrdreVirement ordre) {

	      try {
			classforname();    
			conn = DriverManager.getConnection(url, user, passwd);
		PreparedStatement preparedStatement = conn.prepareStatement("delete Salaires WHERE OrdreID="+ordre.getId());
		preparedStatement.executeUpdate();
		System.out.println("Record is delete to Salaires for Ordre table!");
		conn.close();
		return 1;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			} 
	}

	@Override
	public int update(OrdreVirement ordre) {
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      String reqsql="UPDATE OrdreVirement SET montantGlobal="+ordre.getMontantGlobal()+",datevirement='"+ft.format(ordre.getDatevirement())+"',moisordre='"+ordre.getMoisordre()+"',yearsId="+ordre.getYears().getId()+",BanqueID="+ordre.getBanque().getId()+" WHERE id="+ordre.getId()+"";
	      System.out.println(reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		int r=preparedStatement.executeUpdate();
		System.out.println("Record is updated to OrdreVirement table!");
		conn.close();
		return r;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			}
	}

	@Override
	public int update(Produit produit) {

	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      String reqsql="UPDATE Produit SET typeproduit='"+produit.getTypeproduit().replace("'", "''")+"' WHERE id="+produit.getId()+"";
	      System.out.println(reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		int r=preparedStatement.executeUpdate();
		System.out.println("Record is updated to Produit table!");
		conn.close();
		return r;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			}
	}

	@Override
	public int update(FactureFournisseur factf) {
		String reqht="(SELECT SUM(pt) from FactureFournisseur_articles where facturefournisseurID="+factf.getId()+")";
		String reqtva="0";
		if(factf.getTypepaiement().getTypepaiement().equals("MAD"))
			reqtva="("+reqht+"*0.2)";
	    	
		System.out.println(" TVA "+reqtva);
		try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);	      
	      String reqsql="UPDATE FactureFournisseur SET numAvoir='"+factf.getNumAvoir()+"',montantdavoire="+factf.getMontantdavoire()+",yearsID="+factf.getObjetyears().getId()+", CommandeID="+factf.getCommandefournissuer().getId()+",montantregler="+factf.getMontantregler()+",montantrester=(("+factf.getFraitransport()+"+("+reqht+"+"+reqtva+"-"+factf.getMontantdavoire()+"))-"+factf.getMontantregler()+")";
	      if(factf.getDatereception()!=null)
	    	  reqsql+=",datereception='"+ft.format(factf.getDatereception())+"'";
	      if(factf.getDateconstater()!=null)
	    	  reqsql+=",dateconstater='"+ft.format(factf.getDateconstater())+"'";
	      if(factf.getDatepevisionnel()!=null)
	    	  reqsql+=",datepevisionnel='"+ft.format(factf.getDatepevisionnel())+"'";
	      
	      if(factf.getDateechance()!=null)
	    	  reqsql+=",dateechance='"+ft.format(factf.getDateechance())+"'";
	      
	      reqsql+=",constater='"+factf.getConstater()+"',etat='"+factf.getEtat()+"', typePaiementID="+factf.getTypepaiement().getId()+", totalttc=("+reqht+"+"+reqtva+"-"+factf.getMontantdavoire()+"),totalNet=("+factf.getFraitransport()+"+("+reqht+"+"+reqtva+")-"+factf.getMontantdavoire()+"),tva="+reqtva+",totalht="+reqht+" , num_facture='"+factf.getNum_facture().replace("'", "''")+"' , datefacture='"+ft.format(factf.getDatefacture())+"',reglement='"+factf.getReglement().replace("'", "''")+"' ,fraitransport="+factf.getFraitransport()+" WHERE id="+factf.getId()+"";
	      System.out.println(reqsql);
	      PreparedStatement prepared = conn.prepareStatement(reqsql);
		int r=prepared.executeUpdate();
		prepared.close();
		conn.close();
		return r;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			}
	}

	@Override
	public int delete(OrdreVirement ordre) {
	      try {
			classforname();    
			conn = DriverManager.getConnection(url, user, passwd);
	      PreparedStatement preparedStatement = conn.prepareStatement("delete Salaires WHERE OrdreID="+ordre.getId());		
			preparedStatement.executeUpdate();
		 preparedStatement = conn.prepareStatement("delete OrdreVirement WHERE id="+ordre.getId());
		preparedStatement.executeUpdate();
		System.out.println("Record is delete to OrdreVirement for Ordre table!");
		conn.close();
		return 1;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			} 
	}

	@Override
	public int delete(Contact contacttempo) {
	      try {
			classforname();    
			conn = DriverManager.getConnection(url, user, passwd);
			PreparedStatement preparedStatement = conn.prepareStatement("delete Contact WHERE codeclient="+contacttempo.getCodeclient());
		preparedStatement.executeUpdate();
		System.out.println("Record is delete to Contact table!");
		conn.close();
		return 1;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			} 
	}

	@Override
	public int deleteArticlesToFactFournisseur(FactureFournisseur factf) {
	      try {
			classforname();    
			conn = DriverManager.getConnection(url, user, passwd);
			PreparedStatement preparedStatement = conn.prepareStatement("delete FactureFournisseur_articles WHERE facturefournisseurID="+factf.getId());
		preparedStatement.executeUpdate();
		System.out.println("Record is delete to FactureFournisseur_articles table!");
		conn.close();
		return 1;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			} 
	}

	@Override
	public int update(Boncommande_Article ligne) {
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
			String reqsql="UPDATE Boncommande_Article SET PT_HT="+ligne.getPt()+",qantite="+ligne.getQte()+",PU_HT="+ligne.getPu()+" WHERE id="+ligne.getId()+"";
			System.out.println(reqsql);
			PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
			preparedStatement.executeUpdate();
			System.out.println("Record is updated to Boncommande_Article table!");
			preparedStatement.close();
			conn.close();
			return 1;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			}
	}

	@Override
	public int update(CategorieIISociete categorieII) {
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      String reqsql="UPDATE CategorieIISociete SET categorie='"+categorieII.getCategorie()+"' WHERE id="+categorieII.getId()+"";
	      System.out.println(reqsql);
	      PreparedStatement prepared = conn.prepareStatement(reqsql);
		int r=prepared.executeUpdate();
		System.out.println("Record is updated to CategorieIISociete table!");
		conn.close();
		return r;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			}
	}

	@Override
	public int update(Secteur secteur) {
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      String reqsql="UPDATE Secteur SET secteur='"+secteur.getSecteur()+"', categorieID="+secteur.getCategorie().getId()+" WHERE idsecteur="+secteur.getId()+"";
	      System.out.println(reqsql);
	      PreparedStatement prepared = conn.prepareStatement(reqsql);
		int r=prepared.executeUpdate();
		System.out.println("Record is updated to Secteur table!");
		conn.close();
		return r;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			}
	}

	@Override
	public int update(TypePaiement typepaiementtempo) {
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      String reqsql="UPDATE TypePaiement SET typepaiement='"+typepaiementtempo.getTypepaiement()+"' WHERE id="+typepaiementtempo.getId()+"";
	      System.out.println(reqsql);
	      PreparedStatement prepared = conn.prepareStatement(reqsql);
		int r=prepared.executeUpdate();
		System.out.println("Record is updated to Secteur table!");
		conn.close();
		return r;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return 0;
			}
	}

	@Override
	public boolean delete(TypePaiement typepaiementsupp) {
		System.out.println("TypePaiement");
	      try {
			classforname();    
			conn = DriverManager.getConnection(url, user, passwd);
	      String req="delete TypePaiement WHERE id="+typepaiementsupp.getId()+"";
	      System.out.println(req);
		PreparedStatement preparedStatement = conn.prepareStatement(req);
		preparedStatement.executeUpdate();
		System.out.println("Record is delete to TypePaiement table!");
		conn.close();
		return true;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return false;
			} 
	}

	@Override
	public boolean delete(TypeArticle typearticle) {
		System.out.println("TypeArticle");
	      try {
			classforname();    
			conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("DAO 1");
			PreparedStatement preparedStatement = conn.prepareStatement("delete TypeArticle WHERE idtype="+typearticle.getIdtype());
		preparedStatement.executeUpdate();
		System.out.println("Record is delete to TypeArticle table!");
		conn.close();
		return true;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return false;
			} 
	}

	@Override
	public boolean delete(Article article) {
	      try {
			classforname();    
			conn = DriverManager.getConnection(url, user, passwd);
			PreparedStatement preparedStatement = conn.prepareStatement("delete Article WHERE idArticle="+article.getIdArticle());
		preparedStatement.executeUpdate();
		System.out.println("Record is delete to Article table!");
		conn.close();
		return true;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return false;
			} 
	}

	@Override
	public boolean update(CoordonneeBancaire banquetempo) {
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      String reqsql="UPDATE CoordonneeBancaire SET numerobancaire='"+banquetempo.getNumerobancaire().replace("'","''")+"', banque='"+banquetempo.getBanque().replace("'", "''")+"' , agence='"+banquetempo.getAgence().replace("'", "''")+"' WHERE id="+banquetempo.getId()+"";
	      System.out.println(reqsql);
	      PreparedStatement prepared = conn.prepareStatement(reqsql);
		int r=prepared.executeUpdate();
		System.out.println("Record is updated to Secteur table!");
		conn.close();
		return true;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return false;
			}
	}

	@Override
	public boolean delete(Pays countrytempo) {
	      try {
			classforname();    
			conn = DriverManager.getConnection(url, user, passwd);
			String reqsql="delete Pays WHERE idpays="+countrytempo.getIdpays();
			PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		preparedStatement.executeUpdate();
		System.out.println("Record is delete to Pays table!");
		conn.close();
		return true;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return false;
			} 
	}

	@Override
	public boolean update(Ville ville) {
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      String reqsql="UPDATE Ville SET ville='"+ville.getVille().replace("'","''")+"', paysID='"+ville.getPays().getIdpays()+"'  WHERE idville="+ville.getIdville()+"";
	      System.out.println(reqsql);
	      PreparedStatement prepared = conn.prepareStatement(reqsql);
		int r=prepared.executeUpdate();
		System.out.println("Record is updated to Ville table!");
		conn.close();
		return true;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return false;
			}
	}

	@Override
	public boolean update(Langue vlangue) {
		System.out.println("Langue");
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      String reqsql="UPDATE Langue SET langue='"+vlangue.getLangue().replace("'","''")+"'  WHERE idlangue="+vlangue.getIdlangue()+"";
	      System.out.println(reqsql);
	      PreparedStatement prepared = conn.prepareStatement(reqsql);
		int r=prepared.executeUpdate();
		System.out.println("Record is updated to Langue table!");
		conn.close();
		return true;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return false;
			}
	}

	@Override
	public boolean delete(Ville ville) {
		System.out.println("Ville");
	      try {
			classforname();    
			conn = DriverManager.getConnection(url, user, passwd);
			String reqsql="delete Ville WHERE idville="+ville.getIdville();
	      System.out.println("req=>"+reqsql);
			PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		// execute update SQL stetement
		preparedStatement.executeUpdate();
		System.out.println("Record is delete to Ville table!");
		conn.close();
		return true;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return false;
			} 
	}

	@Override
	public boolean delete(UtilisateurHelpDesk objetuser) {
		System.out.println("UtilisateurHelpDesk");
	      try {
			classforname();    
			conn = DriverManager.getConnection(url, user, passwd);
			String reqsql="delete UtilisateurHelpDesk WHERE id="+objetuser.getId();
	      System.out.println("req=>"+reqsql);
			PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		preparedStatement.executeUpdate();
		System.out.println("Record is delete to UtilisateurHelpDesk table!");
		conn.close();
		return true;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return false;
			} 
	}

	@Override
	public boolean update(UtilisateurHelpDesk objetuser) {
		System.out.println("Langue");
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      String reqsql="UPDATE UtilisateurHelpDesk SET nom='"+objetuser.getNom().replace("'","''")+"',login='"+objetuser.getLogin().replace("'", "''")+"',password='"+objetuser.getPassword().replace("'", "''")+"',clientID="+objetuser.getClient().getIdclient()+", email='"+objetuser.getEmail().replace("'", "''")+"' WHERE id="+objetuser.getId()+"";
	      System.out.println(reqsql);
	      PreparedStatement prepared = conn.prepareStatement(reqsql);
		int r=prepared.executeUpdate();
		System.out.println("Record is updated to Langue table!");
		conn.close();
		return true;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return false;
			}
	}

	@Override
	public boolean delete(LicenceApplication licenceappstempo) {
		System.out.println("LicenceApplication");
	      try {
			classforname();    
			conn = DriverManager.getConnection(url, user, passwd);
			String reqsql="delete LicenceApplication WHERE id="+licenceappstempo.getId();
	      System.out.println("req=>"+reqsql);
			PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		// execute update SQL stetement
		preparedStatement.executeUpdate();
		System.out.println("DAO 2");
		System.out.println("Record is delete to LicenceApplication table!");
		conn.close();
		return true;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return false;
			} 
	}

	@Override
	public boolean update(Ticker ticker) {
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      String reqsql="UPDATE Ticker SET statut='"+ticker.getStatut().replace("'","''")+"'";
	      
	      if(ticker.getPriorite()!=null)
	    	  reqsql+=", prioriteID="+ticker.getPriorite().getId();
	      
	      if(ticker.getDateresolution()!=null)
	    	  reqsql+=" ,dateresolution='"+ftheur.format(ticker.getDateresolution())+"'";
	      
	      reqsql+="  WHERE id="+ticker.getId()+"";
	      System.out.println(reqsql);
	      PreparedStatement prepared = conn.prepareStatement(reqsql);
		// execute update SQL stetement
		int r=prepared.executeUpdate();
		System.out.println("Record is updated to Ticker table!");
		conn.close();
		return true;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return false;
			}
	}

	@Override
	public boolean getUsercle(String cle) {
		int numbre=0;
		try {
		      classforname();   
		      conn = DriverManager.getConnection(url, user, passwd);
		      Statement state = conn.createStatement();
		      ResultSet result = state.executeQuery("SELECT iduser FROM Utilisateur where cle='"+cle+"'");

		      ResultSetMetaData resultMeta = result.getMetaData();
		      while(result.next()){         
		        for(int i = 1; i <= resultMeta.getColumnCount(); i++)
		          {
		        	numbre=result.getInt(i);
		        	break;
		          }
		      }

		      result.close();
		      state.close();
		      conn.close();
		    } catch (Exception e) {
		    	closeconnexion();
		      e.printStackTrace();
		    }
		
		boolean bool=true;
		if(numbre==0)
			bool=false;
		
		return bool;
	}

	@Override
	public boolean FermerTicker(Ticker ticker) {
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      String reqsql="UPDATE Ticker SET fermerticker=1  WHERE id="+ticker.getId()+"";
	      System.out.println(reqsql);
	      PreparedStatement prepared = conn.prepareStatement(reqsql);
		int r=prepared.executeUpdate();
		System.out.println("Record is fermerticker to Ticker table!");
		conn.close();
		return true;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return false;
			}
	}

	@Override
	public boolean update(TypePriorite typeprioriteTempo) {
		 try {
				classforname();  
				conn = DriverManager.getConnection(url, user, passwd);
		      String reqsql="UPDATE TypePriorite SET priorite='"+typeprioriteTempo.getPriorite().replace("'", "''")+"'  WHERE id="+typeprioriteTempo.getId()+"";
		      System.out.println(reqsql);
		      PreparedStatement prepared = conn.prepareStatement(reqsql);
			int r=prepared.executeUpdate();
			System.out.println("Record is priorite to TypePriorite table!");
			conn.close();
			return true;
				} catch (Exception e) {
					closeconnexion();
					e.printStackTrace();
					return false;
				}
	}

	@Override
	public boolean update(TypeTicker typetickertempo) {
		 try {
				classforname();  
				conn = DriverManager.getConnection(url, user, passwd);
		      String reqsql="UPDATE TypeTicker SET typeticker='"+typetickertempo.getTypeticker().replace("'", "''")+"'  WHERE id="+typetickertempo.getId()+"";
		      System.out.println(reqsql);
		      PreparedStatement prepared = conn.prepareStatement(reqsql);
			int r=prepared.executeUpdate();
			System.out.println("Record is typeticker to TypeTicker table!");
			conn.close();
			return true;
				} catch (Exception e) {
					closeconnexion();
					e.printStackTrace();
					return false;
				}
	}

	@Override
	public boolean delete(TypeTicker typetickertempo) {
	      try {
			classforname();    
			conn = DriverManager.getConnection(url, user, passwd);
			String reqsql="delete TypeTicker WHERE id="+typetickertempo.getId();
	      System.out.println("req=>"+reqsql);
			PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		preparedStatement.executeUpdate();
		System.out.println("DAO 2");
		System.out.println("Record is delete to TypeTicker table!");
		conn.close();
		return true;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return false;
			} 
	}

	@Override
	public boolean delete(TypePriorite typeprioriteTempo) {
	      try {
			classforname();    
			conn = DriverManager.getConnection(url, user, passwd);
			String reqsql="delete TypePriorite WHERE id="+typeprioriteTempo.getId();
	      System.out.println("req=>"+reqsql);
			PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		preparedStatement.executeUpdate();
		System.out.println("DAO 2");

		conn.close();
		return true;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return false;
			} 
	}

	@Override
	public boolean update(Tache tache) {
		try {
		classforname();  
		conn = DriverManager.getConnection(url, user, passwd);
      String reqsql="UPDATE Tache SET  projetID="+tache.getProjet().getIdprojet()+", tachename='"+tache.getTachename().replace("'", "''")+"',datedebut='"+ftheur.format(tache.getDatedebut())+"',heurdebart='"+ftheurseulement.format(tache.getDatedebut())+"',datefin='"+ftheur.format(tache.getDatefin())+"',heureFin='"+ftheur.format(tache.getDatefin())+"',commentaire='"+tache.getCommentaire()+"' where idtache="+tache.getIdtache();
      System.out.println(reqsql);
      PreparedStatement prepared = conn.prepareStatement(reqsql);

	int r=prepared.executeUpdate();

	conn.close();
	return true;
		} catch (Exception e) {
			closeconnexion();
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Projet projet) {
		try {
		classforname();  
		conn = DriverManager.getConnection(url, user, passwd);
      String reqsql="UPDATE Projet SET projectname='"+projet.getProjectname().replace("'", "''")+"' ,datedebut='"+ft.format(projet.getDatedebut())+"',dateFin='"+ft.format(projet.getDateFin())+"',clientID="+projet.getClient().getIdclient()+",empID="+projet.getEmp().getId()+" where idprojet="+projet.getIdprojet();
      System.out.println(reqsql);
      PreparedStatement prepared = conn.prepareStatement(reqsql);
	int r=prepared.executeUpdate();
	conn.close();
	return true;
		} catch (Exception e) {
			closeconnexion();
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Rubrique rubriquetempo) {
		try {
		classforname();  
		conn = DriverManager.getConnection(url, user, passwd);
      String reqsql="UPDATE Rubrique SET classement='"+rubriquetempo.getClassement()+"',description='"+rubriquetempo.getDescription().replace("'", "''")+"' , ref='"+rubriquetempo.getRef().replace("'", "''")+"' where id="+rubriquetempo.getId();
      System.out.println(reqsql);
      PreparedStatement prepared = conn.prepareStatement(reqsql);
	int r=prepared.executeUpdate();
	conn.close();
	return true;
		} catch (Exception e) {
			closeconnexion();
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Timesheet timesheet) {
		try {
		classforname();  
		conn = DriverManager.getConnection(url, user, passwd);
      String reqsql="UPDATE Timesheet SET heur='"+timesheet.getHeur().replace("'", "''")+"' , datetimesheet='"+ftheur.format(timesheet.getDatetimesheet())+"',jourID="+timesheet.getJour().getIdjour()+",tacheID="+timesheet.getTache().getIdtache()+",userID="+timesheet.getUser().getIduser()+" where idtimesheet="+timesheet.getIdtimesheet();
      System.out.println(reqsql);
      PreparedStatement prepared = conn.prepareStatement(reqsql);
	int r=prepared.executeUpdate();
	conn.close();
	return true;
		} catch (Exception e) {
			closeconnexion();
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Pointage pointage) {
		try {
		classforname();  
		conn = DriverManager.getConnection(url, user, passwd);
      String reqsql="UPDATE Pointage SET nom='"+pointage.getNom().replace("'", "''")+"' , datePointage='"+ftheur.format(pointage.getDatePointage())+"' , periode='"+pointage.getPeriode().replace("'", "''")+"', entree='"+pointage.getEntree().replace("'", "''")+"',sortie='"+pointage.getSortie().replace("'", "''")+"'  where idpointage="+pointage.getIdpointage();
      System.out.println(reqsql);
      PreparedStatement prepared = conn.prepareStatement(reqsql);
	int r=prepared.executeUpdate();
	conn.close();
	return true;
		} catch (Exception e) {
			closeconnexion();
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean concretiseroffre(Offre offre) {
		try {
		classforname();  
		conn = DriverManager.getConnection(url, user, passwd);
	     Statement state = conn.createStatement();
		  ResultSet resultselect = state.executeQuery("SELECT * from Facture_Offre inner join Facture on Facture.id=Facture_Offre.factureID where (numero_facture like '%FA%' AND numero_facture like '%sans%') AND offreID="+offre.getId());
		  String reqsql;
		  if(resultselect.next()){
			  reqsql="UPDATE Offre SET etatoffre='concrétisée' where id="+offre.getId();
		}else{
			 reqsql="UPDATE Offre SET etatoffre='Facturée' where id="+offre.getId();
		}
      System.out.println(reqsql);
      PreparedStatement prepared = conn.prepareStatement(reqsql);
	int r=prepared.executeUpdate();
	conn.close();
	return true;
		} catch (Exception e) {
			closeconnexion();
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(CategorieIISociete categorieII) {
		 try {
				classforname();    
				conn = DriverManager.getConnection(url, user, passwd);
				PreparedStatement preparedStatement = conn.prepareStatement("delete CategorieIISociete WHERE id="+categorieII.getId());
			preparedStatement.executeUpdate();
			conn.close();
			return true;
				} catch (Exception e) {
					closeconnexion();
					e.printStackTrace();
					return false;
				} 
	}

	@Override
	public boolean delete(BonCommande commande) {
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      String reqsql="UPDATE BonCommande SET numercommande=numercommande+'_tmp', activer=0 WHERE commandeID="+commande.getId()+"";
	      System.out.println(reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		preparedStatement.executeUpdate();
		conn.close();
		return true;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return false;
			}
	}

	@Override
	public boolean delete(BonLivraison livraison) {
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      String reqsql="UPDATE BonLivraison SET numeroref=numeroref+'_tmp', activer=0 WHERE id="+livraison.getId()+"";
	      System.out.println(reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		preparedStatement.executeUpdate();
		conn.close();
		return true;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return false;
			}
	}

	@Override
	public boolean delete(Facture facture) {
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      String reqsql="UPDATE Facture SET numero_facture=numero_facture+'_tmp', activer=0 WHERE id="+facture.getId()+"";
	      System.out.println(reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		preparedStatement.executeUpdate();
		conn.close();
		return true;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return false;
			}
	}

	@Override
	public boolean delete(FactureFournisseur factf) {
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      String reqsql="UPDATE FactureFournisseur SET num_facture=num_facture+'_tmp', activer=0 WHERE id="+factf.getId()+"";
	      System.out.println(reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		preparedStatement.executeUpdate();
		conn.close();
		return true;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return false;
			}
	}

	@Override
	public boolean delete(AvoirClient avoirclient) {
		 try {
				classforname();  
				conn = DriverManager.getConnection(url, user, passwd);
				String reqsql="UPDATE AvoirClient SET numero_avoir=numero_avoir+'_tmp', activer=0 WHERE id="+avoirclient.getId()+"";
				System.out.println(reqsql);
				PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
				preparedStatement.executeUpdate();
				conn.close();
				return true;
				} catch (Exception e) {
					closeconnexion();
					e.printStackTrace();
					return false;
				}
	}

	@Override
	public boolean deleteFacture_Livraisonbyfacture(int id) {
	      try {
			classforname();    
			conn = DriverManager.getConnection(url, user, passwd);
		PreparedStatement preparedStatement = conn.prepareStatement("delete Facture_Livraison WHERE factureID="+id+"");
		// execute update SQL stetement
		preparedStatement.executeUpdate();
		System.out.println("Record is delete to Facture_Livraison table!");
		conn.close();
		return true;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return false;
			} 
	}

	@Override
	public int getNombreFactureFounrisseurByFournisseur(Fournisseur fournisseur) {
		try {
		      classforname();    
		      conn = DriverManager.getConnection(url, user, passwd);

		      Statement state = conn.createStatement();
		      String str_req="SELECT COUNT(*) AS nbr " +
		      		"FROM FactureFournisseur inner join BonCommande " +
		      		"on BonCommande.commandeID=FactureFournisseur.CommandeID " +
		      		"where FournisseurID="+fournisseur.getIdfournisseur()+" and FactureFournisseur.activer=1 and FactureFournisseur.annuler=0";
		      ResultSet resultselect = state.executeQuery(str_req);
		      resultselect.next();
		      int resultat=resultselect.getInt("nbr");
		      state.close();
		      conn.close();
		      return resultat;
		    } catch (Exception e) {
		    	closeconnexion();
		      e.printStackTrace();
		      return 0;
		    } 
	}

	@Override
	public List<Article> getCountMaxAchat() {
		List<Article> liste=new ArrayList<Article>();
		try {
		      classforname();    
		      conn = DriverManager.getConnection(url, user, passwd);
		      //Création d'un objet Statement
		      Statement state = conn.createStatement();
		      ResultSet result = state.executeQuery("SELECT Article.reference,count(*) as nbr " +
		      		"FROM [erpims].[dbo].[Facture_Article] inner join Article on Article.idArticle=Facture_Article.articleID" +
		    		  " inner join Facture on Facture.id=Facture_Article.FactureID " +
		    		  " where Facture.activer=1 and Facture.annuler=0"+
		      		  " group by Article.reference order by count(*) desc");
		      Article art;
		      while(result.next()){
		    	    art=new Article();
		    	    art.setRef(result.getString(1));
		    	    art.setNbrAchat(result.getInt(2));
		        	liste.add(art);
		      }
		      result.close();
		      state.close();
		      conn.close();
		      return liste;
		    } catch (Exception e) {
		    	closeconnexion();
		      e.printStackTrace();
		      return null;
		    }      
	}

	@Override
	public int getCountFactureConstater() {
		try {
		      classforname();    
		      conn = DriverManager.getConnection(url, user, passwd);

		      Statement state = conn.createStatement();
		      String str_req="SELECT count(*) as nbr FROM Facture " +
			      		" where LOWER(Facture.constater)='oui' and Facture.activer=1 and Facture.annuler=0";
		      ResultSet resultselect = state.executeQuery(str_req);
		      resultselect.next();
		      int resultat=resultselect.getInt("nbr");
		      state.close();
		      conn.close();
		      return resultat;
		    } catch (Exception e) {
		    	closeconnexion();
		      e.printStackTrace();
		      return 0;
		    } 
	}

	@Override
	public int getCountFactureNonconstater() {
		try {
		      classforname();    
		      conn = DriverManager.getConnection(url, user, passwd);

		      Statement state = conn.createStatement();
		      String str_req="SELECT count(*) as nbr FROM Facture " +
			      		" where LOWER(Facture.constater)='non' and Facture.activer=1 and Facture.annuler=0";
		      ResultSet resultselect = state.executeQuery(str_req);
		      resultselect.next();
		      int resultat=resultselect.getInt("nbr");
		      state.close();
		      conn.close();
		      return resultat;
		    } catch (Exception e) {
		    	closeconnexion();
		      e.printStackTrace();
		      return 0;
		    } 
	}

	@Override
	public boolean delete(Produit produit) {
		  try {
				classforname(); 
				System.out.println("Produit");
				conn = DriverManager.getConnection(url, user, passwd);
			PreparedStatement preparedStatement = conn.prepareStatement("delete Produit WHERE id="+produit.getId()+"");
			preparedStatement.executeUpdate();
			conn.close();
			return true;
				} catch (Exception e) {
					closeconnexion();
					e.printStackTrace();
					return false;
				} 
	}

	@Override
	public boolean delete(Secteur secteurtempo) {
		  try {
				classforname(); 
				System.out.println("Secteur");
				conn = DriverManager.getConnection(url, user, passwd);
			PreparedStatement preparedStatement = conn.prepareStatement("delete Secteur WHERE idsecteur="+secteurtempo.getId()+"");
			preparedStatement.executeUpdate();
			conn.close();
			return true;
				} catch (Exception e) {
					closeconnexion();
					e.printStackTrace();
					return false;
				} 
	}

	@Override
	public boolean delete(Fournisseur fournisseur) {
		try {
			classforname(); 
			System.out.println("Fournisseur");
			conn = DriverManager.getConnection(url, user, passwd);
		PreparedStatement preparedStatement = conn.prepareStatement("delete Fournisseur WHERE idfournisseur="+fournisseur.getIdfournisseur()+"");
		preparedStatement.executeUpdate();
		conn.close();
		return true;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return false;
			} 
	}

	@Override
	public int getCaisseForMonth(String string) {
		int idcaisse=0;
		try {
		      classforname();     
		      //Création d'un objet Statement
		      conn = DriverManager.getConnection(url, user, passwd);
		      Statement state = conn.createStatement();
		      //L'objet ResultSet contient le résultat de la requête SQL
		      ResultSet result = state.executeQuery("SELECT id FROM Caisse where CONCAT(RIGHT('0'+CAST(MONTH(debutcaisse) AS varchar(2)),2),'/',YEAR(debutcaisse)) like '"+string+"'");
		      while(result.next())        
		    	  idcaisse=result.getInt(1);
		      result.close();
		      state.close();
		      conn.close();
		      return idcaisse;
		    } catch (Exception e) {
		    	closeconnexion();
		      e.printStackTrace();
		      return 0;
		    }    
	}

	@Override
	public boolean deleteArticleToFacture(Facture_Article facturearticle) {
		  try {
				classforname(); 
				System.out.println("Facture_Article");
				conn = DriverManager.getConnection(url, user, passwd);
			PreparedStatement preparedStatement = conn.prepareStatement("delete Facture_Article WHERE id="+facturearticle.getId()+"");
			preparedStatement.executeUpdate();
			conn.close();
			return true;
				} catch (Exception e) {
					closeconnexion();
					e.printStackTrace();
					return false;
				} 
	}

	@Override
	public boolean insertFactureOffre(int factureID, int offreID) {
		System.out.println("DAO");
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("DAO 2");
	      
		PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO Facture_Offre (factureID,offreID) VALUES("+factureID+","+offreID+")");
		System.out.println("DAO 3");
		// execute update SQL stetement
		int result=preparedStatement.executeUpdate();
		System.out.println("DAO 4");
		System.out.println("Record is updated to Facture_Offre table!");
		conn.close();
		if(result==1)
			return true;
		else
			return false;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return false;
			} 
	}

	@Override
	public boolean insertBCSansOffre(BonCommande commande) {
		try {
		      classforname();    
		      conn = DriverManager.getConnection(url, user, passwd);
		      //Création d'un objet Statement
		      Statement state = conn.createStatement();

			      ResultSet resultselect = state.executeQuery("SELECT MAX(idboncommandeoffre) AS ID from Boncommande_Offre");
			      String req;
			      resultselect.next();
			      if(resultselect.getInt("ID")==0)
			    	  req="INSERT INTO Boncommande_Offre values (1,1,"+commande.getId()+")";
			      else
			    	  req="INSERT INTO Boncommande_Offre values ((SELECT MAX(idboncommandeoffre) from Boncommande_Offre)+1,1,"+commande.getId()+")";
			      
			      PreparedStatement preparedStatement = conn.prepareStatement(req);

				preparedStatement.executeUpdate();
				System.out.println("Record is updated to Boncommande_Offre Boncommande_Offre table!");
		      state.close();
		      conn.close();
		      return true;
		    } catch (Exception e) {
		    	closeconnexion();
		      e.printStackTrace();
		      return false;
		    } 
	}

	@Override
	public int getIDLastCaisse() {
		int idcaisse=0;
		try {
		      classforname();     
		      //Création d'un objet Statement
		      conn = DriverManager.getConnection(url, user, passwd);
		      Statement state = conn.createStatement();
		      //L'objet ResultSet contient le résultat de la requête SQL
		      ResultSet result = state.executeQuery("SELECT MAX(id) as maxid FROM Caisse");
		      if(result.next())        
		    	  idcaisse=result.getInt(1);
		      result.close();
		      state.close();
		      conn.close();
		      return idcaisse;
		    } catch (Exception e) {
		    	closeconnexion();
		      e.printStackTrace();
		      return 0;
		    }    
	}

	@Override
	public List<LigneCaisse> getLigneCaisse(String choixmois, int year) {
		List<LigneCaisse> liste=new ArrayList<LigneCaisse>();
		try {
		      classforname();    
		      conn = DriverManager.getConnection(url, user, passwd);
		      //Création d'un objet Statement
		      Statement state = conn.createStatement();
		      String req="SELECT * FROM LigneCaisse";
		      if(year==0){
		    	  if(!choixmois.equals("0"))
			    	  req+="where MONTH(dateligne)="+choixmois;
		      }else{
		    	  if(choixmois.equals("0"))
			    	   req+=" where YEAR(dateligne)="+year;
		    	  else
		    		  req+=" where YEAR(dateligne)="+year+" AND MONTH(dateligne)="+choixmois;
		      }
		     
		      System.out.println(req);
		      ResultSet result = state.executeQuery(req);
		      LigneCaisse obj;
		      while(result.next()){
		    	    obj=new LigneCaisse();
		    	    obj.setId(result.getInt(1));
		    	    obj.setEntree(result.getDouble("entree"));
		    	    obj.setSortie(result.getDouble("sortie"));
		    	    obj.setDetails(result.getString("details"));
		    	    obj.setDateligne(result.getDate("dateligne"));
		        	liste.add(obj);
		      }
		      result.close();
		      state.close();
		      conn.close();
		      return liste;
		    } catch (Exception e) {
		    	closeconnexion();
		      e.printStackTrace();
		      return null;
		    }      
	}

	@Override
	public double getSommeCaisse(String typesomme) {
		double sum=0;
		try {
		      classforname();     
		      //Création d'un objet Statement
		      conn = DriverManager.getConnection(url, user, passwd);
		      Statement state = conn.createStatement();
		      ResultSet result;
		      if(typesomme.equals("e"))
		    	  result = state.executeQuery("SELECT SUM(entree) FROM LigneCaisse");
		      else
		    	 result = state.executeQuery("SELECT SUM(sortie) FROM LigneCaisse");
		      
		      if(result.next())        
		    	  sum=result.getDouble(1);
		      result.close();
		      state.close();
		      conn.close();
		      return sum;
		    } catch (Exception e) {
		    	closeconnexion();
		      e.printStackTrace();
		      return 0;
		    }    
	}

	@Override
	public Offre updateAllPrixMarge(double appmargetotal, Offre calcule) {
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
			
			
			///////////////////////////////////
			System.out.println("prix marge FULL ALL");
			String reqsql="UPDATE Offre_Article SET Marge="+appmargetotal+", prixmarge=(PU_HT*"+appmargetotal+")/100 WHERE OffreID="+calcule.getId()+" AND ismargetotal<>1 AND prixfournisseurisDH=1 ";
			System.out.println(reqsql);
			PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
			// execute update SQL stetement
			preparedStatement.executeUpdate();
			System.out.println("DAO Marge FULL ALL Success");
			
			/////////////////////////////
			
			
			///////////////////////////////////
	      System.out.println("PU_HT 2");
	      reqsql="UPDATE Offre_Article SET PU_HT2=PU_HT+prixmarge WHERE OffreID="+calcule.getId()+" AND ismargetotal<>1 AND prixfournisseurisDH=1";
	      System.out.println(reqsql);
	      preparedStatement = conn.prepareStatement(reqsql);
	      // execute update SQL stetement
	      preparedStatement.executeUpdate();
	      System.out.println("DAO Marge Success");
		
		////////////////////////////////////////
	    System.out.println("Prix Total");
	    reqsql="UPDATE Offre_Article SET PT_HT2=PU_HT2*qantite WHERE OffreID="+calcule.getId()+"";
	    System.out.println(reqsql);
	    preparedStatement = conn.prepareStatement(reqsql);
		//execute update SQL stetement
		preparedStatement.executeUpdate();
		System.out.println("DAO Prix Total Success");
		////////////////////////////////////////
		
		////////////////////////////////////////
	    System.out.println("Prix Offre");
	    
	    Statement state = conn.createStatement();
	      ResultSet resultselect = state.executeQuery("SELECT SUM(prixmarge) AS PMARGE,SUM(PT_HT2) AS PT FROM Offre_Article");
	      resultselect.next();
	    reqsql="UPDATE Offre SET totalmarge="+resultselect.getDouble("PMARGE")+",totalHT="+resultselect.getDouble("PT")+",TVA="+resultselect.getDouble("PT")+"*0.2,TotalTTC=("+resultselect.getDouble("PT")+"+("+resultselect.getDouble("PT")+"*0.2)) WHERE id="+calcule.getId()+"";
	    System.out.println(reqsql);
	    preparedStatement = conn.prepareStatement(reqsql);
		//execute update SQL stetement
		preparedStatement.executeUpdate();
		System.out.println("DAO Prix Total Success");
		////////////////////////////////////////
		
		System.out.println("Record is updated to Offre_Article table !");
		conn.close();
		return calcule;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return null;
			}
	}

	@Override
	public boolean updateObjectif(int yearsID) {

		try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
			
			
			String reqsql="update Facture SET totalht=(SELECT 15000000-SUM(totalht) FROM [erpims].[dbo].[Facture] where yearsID="+yearsID+" and numero_facture not like 'objectif'),"
					+ "prixmarge=(SELECT 3300000-SUM(prixmarge) FROM [erpims].[dbo].[Facture] where yearsID="+yearsID+" and numero_facture not like 'objectif') "
					+ "where numero_facture like 'objectif'";
			System.out.println(reqsql);
			PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
			// execute update SQL stetement
			preparedStatement.executeUpdate();
			System.out.println("OBJECTIF Success");
			
			/////////////////////////////
			return true;
		} catch (Exception e) {
			closeconnexion();
			return false;
		}
	}

	@Override
	public boolean updateEtatOffre(Offre calcule) {
		try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
			
			
			///////////////////////////////////
			System.out.println("UPDATE ETAT OFFRE");
			
			String reqsql="update offre SET etatoffre='"+calcule.getEtatoffre()+"' where id="+calcule.getId();
			System.out.println(reqsql);
			PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
			// execute update SQL stetement
			preparedStatement.executeUpdate();
			System.out.println("UPDATE Success");
			
			/////////////////////////////
			
			///////////////////////////////////
			System.out.println("UPDATE ETAT Article by OFFRE");
			
			reqsql="update Offre_Article SET etat='"+calcule.getEtatoffre()+"' where OffreID="+calcule.getId();
			System.out.println(reqsql);
			preparedStatement = conn.prepareStatement(reqsql);
			// execute update SQL stetement
			preparedStatement.executeUpdate();
			System.out.println("UPDATE article BY OFFRE Success");
			
			/////////////////////////////
			
			return true;
		} catch (Exception e) {
			closeconnexion();
			return false;
		}
	}

	@Override
	public boolean factureeOffre(Offre offre) {
		try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      String reqsql="UPDATE Offre SET etatoffre='facturée' where id="+offre.getId();
	      System.out.println(reqsql);
	      PreparedStatement prepared = conn.prepareStatement(reqsql);
		int r=prepared.executeUpdate();
		conn.close();
		return true;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return false;
			}
	}
	
	@Override
	public boolean contratOffre(Offre offre) {
		try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      String reqsql="UPDATE Offre SET etatoffre='contrat' where id="+offre.getId();
	      System.out.println(reqsql);
	      PreparedStatement prepared = conn.prepareStatement(reqsql);
		  prepared.executeUpdate();
		conn.close();
		return true;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return false;
			}
	}

	@Override
	public boolean factureeCommande(BonCommande commande) {
		try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      String reqsql="UPDATE BonCommande SET Etat='facturée' where commandeID="+commande.getId();
	      System.out.println(reqsql);
	      PreparedStatement prepared = conn.prepareStatement(reqsql);
		prepared.executeUpdate();
		conn.close();
		return true;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return false;
			}
	}

	@Override
	public boolean Encourscommande(BonCommande commandeModifier,
			FactureFournisseur factf) {
		try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      String reqsql;
	      
	      Statement state = conn.createStatement();
	      ResultSet resultselect = state.executeQuery("SELECT * from FactureFournisseur where CommandeID="+commandeModifier.getId());
	      if(resultselect.next())
	    	  reqsql="UPDATE BonCommande SET Etat='Facturée' where CommandeID="+commandeModifier.getId();
	      else{
	    	  ResultSet  select = state.executeQuery("SELECT * from BonCommande where CommandeID="+commandeModifier.getId() +" AND constater='Oui'");
	    	  if(select.next())
	    		  reqsql="UPDATE BonCommande SET Etat='livré' where CommandeID="+commandeModifier.getId();
	    	  else
	    		  reqsql="UPDATE BonCommande SET Etat='En cours' where CommandeID="+commandeModifier.getId();
	      }
	    	  
	      System.out.println(reqsql);
	      PreparedStatement prepared = conn.prepareStatement(reqsql);
		prepared.executeUpdate();
		conn.close();
		return true;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return false;
			}
	}

	@Override
	public boolean constaterBonCommande(BonCommande commandefournissuer) {
		try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      String reqsql="UPDATE BonCommande SET constater='Oui' where commandeID="+commandefournissuer.getId();
	      System.out.println("==>"+reqsql);
	      PreparedStatement prepared = conn.prepareStatement(reqsql);
		prepared.executeUpdate();
		conn.close();
		return true;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return false;
			}
	}

	@Override
	public boolean NonconstaterBonCommande(BonCommande commandefournissuer) {
		try {
		classforname();  
		conn = DriverManager.getConnection(url, user, passwd);
      String reqsql="UPDATE BonCommande SET constater='Non' where commandeID="+commandefournissuer.getId();
      System.out.println("==>"+reqsql);
      PreparedStatement prepared = conn.prepareStatement(reqsql);
	prepared.executeUpdate();
	conn.close();
	return true;
		} catch (Exception e) {
			closeconnexion();
			e.printStackTrace();
			return false;
		}	
	}

	@Override
	public boolean updateclassementRubrique(int rubriqueID) {
		System.out.println("DAO");
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("DAO 2");
	      String reqsql="UPDATE Rubrique SET classement="+rubriqueID+" where id="+rubriqueID;
	      System.out.println("REQ UPDATE Rubrique =>"+reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		System.out.println("DAO 3");
		// execute update SQL stetement
		int res=preparedStatement.executeUpdate();
		System.out.println("DAO 4");
		System.out.println("Record is updated to Rubrique table!");
		conn.close();
		if(res==1)
		return true ;
		else
			return false;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return false;
			}
	}

	@Override
	public boolean insertBC_Offre(Boncommande_Offre bn) {
	      try {
			classforname();  
			conn = DriverManager.getConnection(url, user, passwd);
	     String req="INSERT INTO Boncommande_Offre values (((SELECT MAX(idboncommandeoffre) FROM Boncommande_Offre)+1),"+bn.getOffre().getId()+","+bn.getCommande().getId()+")";
	      System.out.println("prepare =>"+req);
		PreparedStatement preparedStatement = conn.prepareStatement(req);
		// execute update SQL stetement
		int result=preparedStatement.executeUpdate();
		System.out.println("Record is updated to Boncommande_Offre table!");
		conn.close();
		if(result==1)
			return true;
		else
			return false;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return false;
			} 
	}

	@Override
	public boolean updateContactPrincipalForClient(Contact contacttempo) {
		
		System.out.println("Update Contact Principal For Client");
	      try {
		  classforname();  
		  conn = DriverManager.getConnection(url, user, passwd);
	      String reqsql="UPDATE Client SET contactID="+contacttempo.getCodeclient()+" where idclient="+contacttempo.getClient().getIdclient();
	      System.out.println(reqsql);
	      PreparedStatement preparedStatement = conn.prepareStatement(reqsql);
		  preparedStatement.executeUpdate();
		  conn.close();
		return true;
			} catch (Exception e) {
				closeconnexion();
				e.printStackTrace();
				return false;
			}
	}
}
