package ims.Exception;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class ImsErpException extends NullPointerException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public ImsErpException(){
		FacesMessage msg;
        msg = new FacesMessage("Exception dans l'application Contacter Administration","");
        FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void AfficherException(){
		 FacesMessage msg;
         msg = new FacesMessage("Exception dans l'application Contacter Administration","");
         FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}
