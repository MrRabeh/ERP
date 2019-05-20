/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.model.entities;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author rabeh
 */
public class Langue implements java.io.Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idlangue;
    private String langue;
    private Set<Client> clients=new HashSet<Client>();
    public Langue() {
    }

    public Langue(String langue) {
        this.langue = langue;
    }

    
    public Langue(int idlangue, String langue) {
        this.idlangue = idlangue;
        this.langue = langue;
    }

    public int getIdlangue() {
        return idlangue;
    }

    public void setIdlangue(int idlangue) {
        this.idlangue = idlangue;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }
    
    
    
    
}
