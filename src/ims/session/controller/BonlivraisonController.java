package ims.session.controller;

import ims.model.entities.BonLivraison;
import ims.model.entities.Client;
import ims.model.entities.Facture;
import ims.model.entities.Facture_Article;
import ims.model.entities.Offre;
import ims.model.entities.Pays;
import ims.model.entities.Stock;
import ims.model.entities.Stock_Livraison;
import ims.model.entities.Ville;
import ims.service.ModelService;
import ims.service.ModelServiceJDBC;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.RowEditEvent;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;



@ManagedBean
@SessionScoped
public class BonlivraisonController implements Serializable {

	
	   /**
		* creator RABEH TARIK
	    **/
			private static final long serialVersionUID = 1L;
			
			 @ManagedProperty(value = "#{managerDataBase}")
			 private ManagerDataBase managerApplication;
			 
			 
			public ManagerDataBase getManagerApplication() {
				return managerApplication;
			}

			public void setManagerApplication(ManagerDataBase managerApplication) {
				this.managerApplication = managerApplication;
			}

			@ManagedProperty(value="#{BonLivraisonManager}")
			private ModelService<BonLivraison> manager;
		
			@ManagedProperty(value="#{FactArticleManage}")
		    private ModelService<Facture_Article> managerarticle;
		    
		    @ManagedProperty(value="#{FactureManager}")
		    private ModelService<Facture> managerfacture;
		    
		    @ManagedProperty(value="#{OffreManager}")
			private ModelService<Offre> managerO;
			
			@ManagedProperty(value="#{PaysManager}")
		    private ModelService<Pays> managerp;
			
			@ManagedProperty(value="#{VilleManager}")
			private ModelService<Ville> managerv;
		
			@ManagedProperty(value="#{StockManager}")
			private ModelService<Stock> managerstock;
			
			@ManagedProperty(value="#{StockLivraisonManager}")
			private ModelService<Stock_Livraison> managerstocklivraison;
			
			@ManagedProperty(value="#{ClientManager}")
			private ModelService<Client> managerclient;
			
		    @ManagedProperty(value="#{JDBCManager}")
		    private ModelServiceJDBC managerjdbc;
		    
			private BonLivraison livraison;
			private List<BonLivraison> livraisons;
			private List<BonLivraison> filtredlivraisons;
			private int idoffre=1;
			private int idclient=1;
			
		    private int qte;
		    private String designationclient;
		    private int idstock;
		    private Stock stock;
		    private List<Stock_Livraison> stocklivraisons=new ArrayList<Stock_Livraison>();
		    private Stock_Livraison stocklivraison;
		    private Stock_Livraison stocklivraisontemp;
		    
		    private int idauto;
		    
		    private List<Stock> stocksup0;
		    

		    
		    private String ref;
		    private Date datebl;
		    private String livrepar;
		    private String receptionpar;
		    
		    //----------------------
		    private String str_livraison;
		    //-----------------
			
		    @PostConstruct
		    public void init() {
		    	System.out.println("------------INIT BL---------");
				livraisons=manager.getObject();
			  	System.out.println("-------FIN--INIT BL---------");
		    }
		    
		    @PreDestroy
		    public void destroy(){
		    	System.out.println("****destroy****");
		    }
		    
		    public void eventchangeArticleforClient(){
		    	FacesMessage msg;
		    	try {
		    		System.out.println("---eventchangeArticleforClien---");
					stocksup0=managerstock.getByNames("stock",""+idclient);
					System.out.println("taille =>"+stocksup0.size());
				} catch (Exception e) {
					msg = new FacesMessage("Exception "+e.getLocalizedMessage());
					 FacesContext.getCurrentInstance().addMessage(null, msg);
				}
		    }
		   
			public String preparecreate(){
				stocksup0=null;
				livraison=new BonLivraison();
				stocklivraisons=new ArrayList<Stock_Livraison>();
				qte=0;
				designationclient="";
				livrepar="";
				receptionpar="";
				datebl=new Date();
				Calendar cal = Calendar.getInstance();
				datebl=cal.getTime();
				return "insertBonLivraison?faces-redirect=true";
			}
			
			public String prepareView(){
				livraison=manager.getObject(livraison.getId());
				idclient=livraison.getClient().getIdclient();
				stocklivraisons=new ArrayList<Stock_Livraison>();
				stocklivraisons.addAll(livraison.getStocks());
		    	return "ViewBonLivraison?faces-redirect=true";
		    }
			
			public String prepareList(){
				System.out.println("---------------------GET MAX STOCK LIVRAISON----------------");
		   		livraison=new BonLivraison();
		   	
		   		idauto=managerjdbc.getMaxIdSockLivraison()+1;
		   		
		   		System.out.println(idauto);
		   		stocklivraisons=new ArrayList<Stock_Livraison>();
		   		stocklivraison=new Stock_Livraison();
		    	return "index?faces-redirect=true";
		    }
				
			public void delete(){
				  FacesMessage msg;
				try {
					livraisons=null;
					boolean op=managerjdbc.deleteBnlivraison(livraison);
					if(op==true)
						msg = new FacesMessage("Bon Livraison Supprimer Avec Success");
					else
						msg = new FacesMessage("Probleme de de suppression");
					 FacesContext.getCurrentInstance().addMessage(null, msg);
				} catch (Exception e) {
					msg = new FacesMessage("Exception","Contacter Administration");
					 FacesContext.getCurrentInstance().addMessage(null, msg);
				}
			}
			
			public void deletelignearticle(){
				FacesMessage  msg;
			        try {
			        	stocklivraisons.remove(stocklivraisontemp);
			        	int i=0;
			        	while(i<stocksup0.size() && !stocksup0.get(i).getNumserie().equals(stocklivraisontemp.getStock().getNumserie()))
			        		i++;
			        	System.out.println("size"+stocksup0.size());
			        	System.out.println("i="+i);
			        	if(i==stocksup0.size()){
			        		stocklivraisontemp.getStock().setQte(stocklivraisontemp.getQte());
			        		stocksup0.add(stocklivraisontemp.getStock());
			        	}
			        	else{
			        		stocksup0.get(i).setQte(stocksup0.get(i).getQte()+stocklivraisontemp.getQte());
			        	}
			        	msg = new FacesMessage("Article est rejeter");
			              FacesContext.getCurrentInstance().addMessage(null, msg);
			              stocklivraison=null;
			            } catch (Exception e) {
			                msg = new FacesMessage("Exception "+e.getLocalizedMessage());
			           FacesContext.getCurrentInstance().addMessage(null, msg);
			            } 
			}
			
			public void deletearticle(){
				FacesMessage  msg;
			        try {
			        	System.out.println("delete article");
			        	managerjdbc.deleteArticleToBonLivraison(stocklivraison);
			        	stocklivraisons.remove(stocklivraison); 
			        	managerjdbc.retourstock(stocklivraison.getStock().getId(), stocklivraison.getQte());
			        	msg = new FacesMessage("Article est rejeter");
			              FacesContext.getCurrentInstance().addMessage(null, msg);
			            } catch (Exception e) {
			                msg = new FacesMessage("ERORR SUPPRESSION");
			           FacesContext.getCurrentInstance().addMessage(null, msg);
			            } 
			        msg = new FacesMessage("BL est Bien Modifier");
			        FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			
			public void vider(){
				receptionpar="";
				ref="";
				livrepar="";
				stocklivraisons=null;
				stocklivraisons=new ArrayList<Stock_Livraison>();
				designationclient="";
			}
			
			 public String annuler(){
				    
		        	try {
		        		System.out.println("index?faces-redirect=true");
		        		return "index?faces-redirect=true";
		    		} catch (Exception e) {
		                return "index?faces-redirect=true";
		    		}
		    }

			public void insert(){
				stocklivraison=null;
		        FacesMessage msg;
		        managerApplication.setLivraisons(null);
		        try {
		        	livraison.setRef(ref);
		        	livraison.setDatebl(datebl);
		        	livraison.setLivrepar(livrepar);
		        	livraison.setReceptionpar(receptionpar);
		        	livraison.setClient(managerclient.getObject(idclient));
		        	livraison.setYears(managerjdbc.getYears(Calendar.getInstance().get(Calendar.YEAR)));
		        	livraison.setActiver(true);
		        	manager.insertObject(livraison);
			        for(int i=0;i<stocklivraisons.size();i++)
			        {
			        	stocklivraisons.get(i).setLivraison(livraison);
			        	stocklivraisons.get(i).setId(idauto);
			        	boolean op=managerstocklivraison.insertObject(stocklivraisons.get(i));
			        	
			        	managerjdbc.gestionstock(stocklivraisons.get(i));
			        	idauto++;
			        }
			        
			       managerApplication.setStocks(null);
			       managerApplication.setStockslivrer(null);
			        msg = new FacesMessage("Bon Livraison est bien créé");
				       FacesContext.getCurrentInstance().addMessage(null, msg);
				       vider();
		        } catch (Exception e) {
		       msg = new FacesMessage("Exception "+e.getLocalizedMessage());
		       FacesContext.getCurrentInstance().addMessage(null, msg);
		        	}
			}
			
			public void insertart(){
				System.out.println("insertart");
				FacesMessage msg;

					stock=managerstock.getObject(idstock);
					
				       for(int i=0;i<stocksup0.size();i++){
				    	   if(stocksup0.get(i).getId()==idstock){
								if(stocksup0.get(i).getQte()<qte){
									   msg = new FacesMessage("qte est superieur a la qte de stock");
								       FacesContext.getCurrentInstance().addMessage(null, msg);
								}
								else{
									System.out.println(" en train de sortir l'article ... ");
										Stock_Livraison tempo=new Stock_Livraison();
										tempo.setQte(qte);
										System.out.println(" Qte IS "+qte);
										String numeroserielivre="";
										if(!stock.getNumserie().equals("")){
											String[] tableNumeroSerie=stock.getNumserie().split(",");
											for(int j=0;j<qte-1;j++)
												numeroserielivre+=tableNumeroSerie[j]+",";
											numeroserielivre+=tableNumeroSerie[qte-1];
											
											tempo.setNumeroserielivre(numeroserielivre);
											
										}else{
											tempo.setNumeroserielivre(numeroserielivre);
										}
										System.out.println("num serie is "+ numeroserielivre);
										tempo.setStock(stock);
										if(designationclient.equals("")){
											stock.getArticle().getDesignation().replace("'", "''");
											tempo.setDesignationclient(stock.getArticle().getDesignation());
										}
										else
											tempo.setDesignationclient(designationclient.replace("'", "''"));
										System.out.println("prepare ADD");
										stocklivraisons.add(tempo);
									   msg = new FacesMessage("article est livrer");
								       FacesContext.getCurrentInstance().addMessage(null, msg);
								       System.out.println("article est livrer");
								    		   stocksup0.get(i).setQte(stocksup0.get(i).getQte()-qte);
								    		   
												if(stocksup0.get(i).getQte()==0)
										    		  stocksup0.remove(i);
								}
				    	   }
				       }
				     
					qte=1;
					designationclient="";
			}
			
			public void insertartmodif(){
				FacesMessage msg;
				if(idstock!=0){
						stock=managerstock.getObject(idstock);
						System.out.println("Article Modif");
						if(stock.getQte()<qte){
							   msg = new FacesMessage("qte est superieur a la qte de stock");
						       FacesContext.getCurrentInstance().addMessage(null, msg);
						}
						else{
							System.out.println("Article ADD MODIF");
							Stock_Livraison tempo=new Stock_Livraison();
							tempo.setLivraison(livraison);
							tempo.setQte(qte);
							
							String numeroserielivre="";
							if(!stock.getNumserie().equals("")){
								String[] tableNumeroSerie=stock.getNumserie().split(",");;
								for(int j=0;j<qte-1;j++)
									numeroserielivre+=tableNumeroSerie[j]+",";
								numeroserielivre+=tableNumeroSerie[qte-1];
								
								tempo.setNumeroserielivre(numeroserielivre);
							}else
								tempo.setNumeroserielivre(numeroserielivre);
							
							
							tempo.setStock(stock);
							if(designationclient.equals(""))
								tempo.setDesignationclient(stock.getArticle().getDesignation().replace("'", "''"));
							else
								tempo.setDesignationclient(designationclient.replace("'", "''"));
							
								stocklivraisons.add(tempo);
								managerjdbc.insertstocklivraison(tempo);
								managerjdbc.gestionstock(tempo);
							   msg = new FacesMessage("article est livrer avec Success");
						       FacesContext.getCurrentInstance().addMessage(null, msg);
						       stocksup0=null;
						}
						qte=1;
						designationclient="";
				}else{
					 msg = new FacesMessage("Article n'existe pas");
				       FacesContext.getCurrentInstance().addMessage(null, msg);
				}

			}
					
			public void update() {
		        FacesMessage msg;
		        managerApplication.setLivraisons(null);
		        try {
		        	livraison.setClient(managerclient.getObject(idclient));
		        	System.out.println("ID Bon Livraison==>"+livraison.getId());
		        	managerjdbc.updateBonLivraison(livraison);
		            msg = new FacesMessage("livraison est bien Modifier","");
		            FacesContext.getCurrentInstance().addMessage(null, msg);
		        } catch (Exception e) {
		           msg = new FacesMessage("ERROR livraison","");
		       FacesContext.getCurrentInstance().addMessage(null, msg);
		        }
		       
		   }
			
		    public void onRowEdit(RowEditEvent event) {
		         FacesMessage msg;
		         try {
		        	 manager.updateObject(((BonLivraison) event.getObject()));
		             msg = new FacesMessage("Article est bien ModifiÃ©",""+((BonLivraison)event.getObject()).getRef());
		             FacesContext.getCurrentInstance().addMessage(null, msg);
		         } catch (Exception e) {
		            msg = new FacesMessage("Article est mal Modifier",""+((BonLivraison) event.getObject()).getRef());
		        FacesContext.getCurrentInstance().addMessage(null, msg);
		         }
		        
		    }
		     
		    public void onRowCancel(RowEditEvent event) {
	
		    	FacesMessage msg = new FacesMessage("Modification Ã©tÃ© AnnulÃ©",""+((BonLivraison) event.getObject()).getRef());
		        FacesContext.getCurrentInstance().addMessage(null, msg);
		    }
		    
		    public void createPdf(String dest){
		    	
		    	 	Font ftext=new Font(Font.FontFamily.HELVETICA,8,Font.NORMAL,new BaseColor(Color.black));
		    	 	Font ftext10=new Font(Font.FontFamily.HELVETICA,10,Font.NORMAL,new BaseColor(Color.black));
		            Font ftextgra=new Font(Font.FontFamily.HELVETICA,8,Font.BOLD,new BaseColor(Color.black));
		 	   	
		            System.out.println("Imprimer......");
			 	   	DateFormat format=new SimpleDateFormat("dd/MM/yy");
			 	   	System.out.println("Imprimer*************** SUCCES");
			 	   	String webroot= ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("\\resources\\images");
			        System.out.println("webroot");
			    	System.out.println(webroot); 
			    	System.out.println("webroot FIN");
			    	Paragraph saute=new Paragraph("\n");
			    	float left = 36;
			         float right = 36;
			         float top = 60;
			         float bottom = 100;
			         
		        Document document = new Document(PageSize.A4,left, right, top, bottom);
		        HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
		        try {
		        	System.out.println("ID===>"+livraison.getId());
		            response.setContentType("application/pdf");
		            response.setHeader("Content-Disposition", "inline; filename="+livraison.getRef()+".pdf");
	
	
		            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
		            document.open();
		            document.setMargins(left, right,108, bottom);
		            document.add(saute);
		            document.add(saute);
		            document.add(saute);
		            System.out.println("Imprimer......Bon livraison");
		            PdfPTable tableinfo = new PdfPTable(1);
		            PdfPCell l = new PdfPCell(new Phrase("Date BL:",ftextgra));
		            l.setHorizontalAlignment(Element.ALIGN_LEFT);
		            l.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
		            l.setBorder(Rectangle.NO_BORDER);
		            tableinfo.addCell(l);
		            PdfPCell lval = new PdfPCell(new Phrase(format.format(livraison.getDatebl()),ftext));
		            lval.setHorizontalAlignment(Element.ALIGN_CENTER);
		            tableinfo.addCell(lval);
		            
		            l = new PdfPCell(new Phrase("Client:",ftextgra));
		            l.setHorizontalAlignment(Element.ALIGN_LEFT);
		            l.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
		            l.setBorder(Rectangle.NO_BORDER);
		            tableinfo.addCell(l);
		            String societe="";
		            if(livraison.getClient().getSociete()!=null){
		            	societe=livraison.getClient().getSociete();
		            }
		            String adresse="";
		            if(livraison.getClient().getAdresse()!=null)
		            	adresse=livraison.getClient().getAdresse();
		            if(livraison.getClient().getVille().getVille()!=null)
		            	adresse+="\n"+livraison.getClient().getVille().getVille();
		            lval = new PdfPCell(new Phrase(societe,ftext));
		            lval.setHorizontalAlignment(Element.ALIGN_CENTER);
		            tableinfo.addCell(lval);
		            
		            lval = new PdfPCell(new Phrase(adresse,ftext));
		            lval.setHorizontalAlignment(Element.ALIGN_CENTER);
		            tableinfo.addCell(lval);
		            tableinfo.setHorizontalAlignment(tableinfo.ALIGN_RIGHT);
			        tableinfo.setWidthPercentage(30);
			        document.add(tableinfo);
			        Font fnumbl=new Font(Font.FontFamily.COURIER,15,Font.BOLD,new BaseColor(Color.black));
		            document.add(saute);
		            Paragraph Numerocommande=new Paragraph("Bon de Livraison N° : "+livraison.getRef(),fnumbl);
		            Numerocommande.setIndentationLeft(53);
		            document.add(Numerocommande);
		            document.add(saute);
		            PdfPCell c1;
		            PdfPTable table = new PdfPTable(3);
		            table.setWidths(new float[]{50,200,20});
		            table.setHorizontalAlignment(Element.ALIGN_RIGHT);
		            table.setWidthPercentage(90);
		            c1 = new PdfPCell(new Phrase("Réference",ftextgra));
		            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		            c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
		            
		            table.addCell(c1);
		            
		            c1 = new PdfPCell(new Phrase("Désignation",ftextgra));
		            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		            c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
		            table.addCell(c1);
		            
		            c1 = new PdfPCell(new Phrase("Qté",ftextgra));
		            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		            c1.setVerticalAlignment(Element.ALIGN_CENTER);
		            c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
		            table.addCell(c1);
		            table.setHeaderRows(1);
		            
		            List<Stock_Livraison> sotcklivraison=managerstocklivraison.getObjects(livraison.getId());
		            int taillebl=sotcklivraison.size();
		            for(int i=0;i<sotcklivraison.size();i++)
		            {
		            	
		            	c1 = new PdfPCell((new Phrase(sotcklivraison.get(i).getStock().getArticle().getRef(),ftext)));
			            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			            if(taillebl<10)
			            	c1.setPadding(7);
			            table.addCell(c1);
			            String numseri="";
			            if(sotcklivraison.get(i).getStock().getNumserie().equals(""))
			            	c1=new PdfPCell(new Phrase(sotcklivraison.get(i).getDesignationclient(),ftext));
			            else
			            {
			            	numseri="\nNuméro Serie : \n";
			            	numseri+=sotcklivraison.get(i).getNumeroserielivre();
			            	c1=new PdfPCell(new Phrase(sotcklivraison.get(i).getDesignationclient()+"\n"+numseri,ftext));
			            	}
			            
			            if(taillebl<10){
			            	c1.setPaddingTop(7);
			            	c1.setPaddingBottom(7);
			            }
			            table.addCell(c1);
			            
			            c1 = new PdfPCell(new Phrase(sotcklivraison.get(i).getQte()+"",ftext));
			            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			            if(taillebl<10){
			            	c1.setPaddingTop(7);
			            	c1.setPaddingBottom(7);
			            }
			            table.addCell(c1);
		            }
		            document.add(table);
		            Font ftest10gr=new Font(Font.FontFamily.HELVETICA,10,Font.BOLD,new BaseColor(Color.black));
		            Paragraph livrer=new Paragraph("Livré par :                                                                                 Receptioné par :",ftest10gr);
		            livrer.setAlignment(Element.ALIGN_LEFT);
		            livrer.setIndentationLeft(53);
		            document.add(livrer);
		            Paragraph livrerres=new Paragraph(livraison.getLivrepar()+"                                                                                                    "+livraison.getReceptionpar(),ftext10);
		            livrerres.setAlignment(Element.ALIGN_LEFT);
		            livrerres.setIndentationLeft(53);
		            document.add(livrerres);
		            System.out.println("Imprimer***************FIN SUCCES");
		            document.addAuthor("IMS Technology");
		            document.addTitle("Bon Commande");
		            document.addSubject("Bon de livraison N°"+livraison.getRef());
		            document.addCreator("RABEH");
		            document.close();
		            FacesContext.getCurrentInstance().responseComplete();
		        } catch (DocumentException | IOException de) {
		            System.out.println("ERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROR PDF");
		            document.close();
		            FacesContext.getCurrentInstance().responseComplete();
		        }
		    }
		    
		    /****************PDF************************/
			 
		    public void imprimer(){
		    String dest="D:/IMS.pdf";
		    File file = new File(dest);
		    file.getParentFile().mkdirs();
		    try { 	
		    	createPdf(dest);
		    	createPdfImage();
		    	 
		    	
		    } catch (IOException | DocumentException e) {
		    	// TODO Auto-generated catch block
		    	e.printStackTrace();
		    }

		    }
		    
		    public void imprimersansfond(){
			    String dest="D:/IMS.pdf";
			    File file = new File(dest);
			    file.getParentFile().mkdirs();
			    try { 	
			    	createPdf(dest);
			    	createPdfImageSansFond();
			    	 
			    	
			    } catch (IOException | DocumentException e) {
			    	// TODO Auto-generated catch block
			    	e.printStackTrace();
			    }

			    }



		    public void createPdfImage() throws FileNotFoundException, DocumentException, IOException{

					PdfReader reader;			
					reader = new PdfReader("D:/IMS.pdf");
					
					int n = reader.getNumberOfPages(); 
					// Create a stamper that will copy the document to a new file 
					//PdfStamper stamp = new PdfStamper(reader, new FileOutputStream("D:/boncmd.pdf")); 
					HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
					PdfStamper stamp = new PdfStamper(reader,response.getOutputStream()); 
						String webroot= ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("\\resources\\images");
					int i = 1; 
					com.itextpdf.text.pdf.PdfContentByte under; 
					com.itextpdf.text.pdf.PdfContentByte over; 
					Image img = Image.getInstance(webroot+"\\fondlogoancien.png"); 
					img.scaleAbsoluteHeight(PageSize.A4.getHeight());
					img.scaleAbsoluteWidth(PageSize.A4.getWidth());
					img.setAbsolutePosition(0, 0);
					BaseFont bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.WINANSI, BaseFont.EMBEDDED); 
					
					
					for(i=1;i<=n;i++){ 
					//img under the existing page 
					under = stamp.getUnderContent(i); 
					under.addImage(img);
					// Text over the existing page 
					over = stamp.getOverContent(i); 
					over.beginText(); 
					over.setFontAndSize(bf, 15);
					over.endText(); 
					} 
					stamp.close();	

		    	}
		    
		    public void createPdfImageSansFond() throws FileNotFoundException, DocumentException, IOException{

				PdfReader reader;			
				reader = new PdfReader("D:/IMS.pdf");
				
				int n = reader.getNumberOfPages(); 
				// Create a stamper that will copy the document to a new file 
				//PdfStamper stamp = new PdfStamper(reader, new FileOutputStream("D:/boncmd.pdf")); 
				HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
				PdfStamper stamp = new PdfStamper(reader,response.getOutputStream()); 
					String webroot= ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("\\resources\\images");
				int i = 1; 
				com.itextpdf.text.pdf.PdfContentByte under; 
				com.itextpdf.text.pdf.PdfContentByte over; 
				Image img = Image.getInstance(webroot+"\\fondlogoancien.png"); 
				img.scaleAbsoluteHeight(PageSize.A4.getHeight());
				img.scaleAbsoluteWidth(PageSize.A4.getWidth());
				img.setAbsolutePosition(0, 0);
				BaseFont bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.WINANSI, BaseFont.EMBEDDED); 
				
				
				for(i=1;i<=n;i++){ 
				//img under the existing page 
				under = stamp.getUnderContent(i); 
				//under.addImage(img);
				// Text over the existing page 
				over = stamp.getOverContent(i); 
				over.beginText(); 
				over.setFontAndSize(bf,15);
				over.endText(); 
				} 
				stamp.close();	
				
				}
		
			public ModelService<BonLivraison> getManager() {
				return manager;
			}
			public void setManager(ModelService<BonLivraison> manager) {
				this.manager = manager;
			}
			public BonLivraison getLivraison() {
				return livraison;
			}
			public void setLivraison(BonLivraison livraison) {
				this.livraison = livraison;
			}
			public List<BonLivraison> getLivraisons() {
					livraisons=managerApplication.getLivraisons();
				return livraisons;
			}
			public void setLivraisons(List<BonLivraison> livraisons) {
				this.livraisons = livraisons;
			}
			public List<BonLivraison> getFiltredlivraisons() {
				return filtredlivraisons;
			}
			public void setFiltredlivraisons(List<BonLivraison> filtredlivraisons) {
				this.filtredlivraisons = filtredlivraisons;
			}
		
			public ModelService<Offre> getManagerO() {
				return managerO;
			}
		
			public void setManagerO(ModelService<Offre> managerO) {
				this.managerO = managerO;
			}

		
			public ModelService<Facture_Article> getManagerarticle() {
				return managerarticle;
			}

			public void setManagerarticle(ModelService<Facture_Article> managerarticle) {
				this.managerarticle = managerarticle;
			}

			public int getIdoffre() {
				return idoffre;
			}
		
			public void setIdoffre(int idoffre) {
				this.idoffre = idoffre;
			}
		
			public ModelService<Pays> getManagerp() {
				return managerp;
			}
		
			public void setManagerp(ModelService<Pays> managerp) {
				this.managerp = managerp;
			}
		
			public ModelService<Ville> getManagerv() {
				return managerv;
			}
		
			public void setManagerv(ModelService<Ville> managerv) {
				this.managerv = managerv;
			}
			
			public ModelService<Facture> getManagerfacture() {
				return managerfacture;
			}

			public void setManagerfacture(ModelService<Facture> managerfacture) {
				this.managerfacture = managerfacture;
			}

			public ModelService<Stock> getManagerstock() {
				return managerstock;
			}

			public void setManagerstock(ModelService<Stock> managerstock) {
				this.managerstock = managerstock;
			}
			

			public int getQte() {
				return qte;
			}

			public void setQte(int qte) {
				this.qte = qte;
			}

			public int getIdstock() {
				return idstock;
			}

			public void setIdstock(int idstock) {
				this.idstock = idstock;
			}

			public Stock getStock() {
				return stock;
			}

			public void setStock(Stock stock) {
				this.stock = stock;
			}

			public List<Stock_Livraison> getStocklivraisons() {
				return stocklivraisons;
			}

			public void setStocklivraisons(List<Stock_Livraison> stocklivraisons) {
				this.stocklivraisons = stocklivraisons;
			}

			public Stock_Livraison getStocklivraison() {
				return stocklivraison;
			}

			public void setStocklivraison(Stock_Livraison stocklivraison) {
				this.stocklivraison = stocklivraison;
			}

			public ModelService<Stock_Livraison> getManagerstocklivraison() {
				return managerstocklivraison;
			}

			public void setManagerstocklivraison(
					ModelService<Stock_Livraison> managerstocklivraison) {
				this.managerstocklivraison = managerstocklivraison;
			}

			public ModelServiceJDBC getManagerjdbc() {
				return managerjdbc;
			}

			public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
				this.managerjdbc = managerjdbc;
			}

			public String getRef() {
		    	Calendar cal = Calendar.getInstance();
		    	String chaine=""+cal.get(Calendar.YEAR);
		    	String[] tab=chaine.split("0");
		    	int lastnumbre=managerjdbc.getLastNumbre("BonLivraison","numeroref",chaine);
		    	lastnumbre++;
		    	String nombre=String.format("%03d",lastnumbre);
		    	ref=tab[1]+"BL"+nombre;
				return ref;
			}

			public void setRef(String ref) {
				this.ref = ref;
			}

			public Date getDatebl() {
				return datebl;
			}

			public void setDatebl(Date datebl) {
				this.datebl = datebl;
			}

			public String getLivrepar() {
				return livrepar;
			}

			public void setLivrepar(String livrepar) {
				this.livrepar = livrepar;
			}

			public String getReceptionpar() {
				return receptionpar;
			}

			public void setReceptionpar(String receptionpar) {
				this.receptionpar = receptionpar;
			}

			public ModelService<Client> getManagerclient() {
				return managerclient;
			}

			public void setManagerclient(ModelService<Client> managerclient) {
				this.managerclient = managerclient;
			}

			public int getIdclient() {
				return idclient;
			}

			public void setIdclient(int idclient) {
				this.idclient = idclient;
			}

			public int getIdauto() {
				return idauto;
			}

			public void setIdauto(int idauto) {
				this.idauto = idauto;
			}

			public String getDesignationclient() {
				return designationclient;
			}

			public void setDesignationclient(String designationclient) {
				this.designationclient = designationclient;
			}

			public List<Stock> getStocksup0() {
				
				if(stocksup0==null){
				
					System.out.println("stock est NULL");
					stocksup0=managerstock.getByNames("stock");
				}
					 
				return stocksup0;
			}

			public void setStocksup0(List<Stock> stocksup0) {
				this.stocksup0 = stocksup0;
			}

			public Stock_Livraison getStocklivraisontemp() {
				return stocklivraisontemp;
			}

			public void setStocklivraisontemp(Stock_Livraison stocklivraisontemp) {
				this.stocklivraisontemp = stocklivraisontemp;
			}

			public String getStr_livraison() {
				str_livraison="Bon Livraison Client";
				return str_livraison;
			}

			public void setStr_livraison(String str_livraison) {
				this.str_livraison = str_livraison;
			}

			
}
