package ims.model.entities;

public class ImagesbyTicker {
	
	private int id;
	private String cheminimage;
	private Ticker ticker;
	public ImagesbyTicker() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCheminimage() {
		return cheminimage;
	}
	public void setCheminimage(String cheminimage) {
		this.cheminimage = cheminimage;
	}
	public Ticker getTicker() {
		return ticker;
	}
	public void setTicker(Ticker ticker) {
		this.ticker = ticker;
	}
	
	

}
