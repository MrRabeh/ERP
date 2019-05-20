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
public class Utilisateur implements java.io.Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int iduser=1;
    private String Login="";
    private String Password="";
    private String typeuser="";
    private Employee emp;
    private String cle="";
    private String email;
    
    private Set<Timesheet> timesheets=new HashSet<Timesheet>();

    public Utilisateur() {
    }
     

    public Utilisateur(int iduser, String Login, String Password, String typeuser) {
        this.iduser = iduser;
        this.Login = Login;
        this.Password = Password;
        this.typeuser = typeuser;
    }

    public Utilisateur(String Login, String Password, String typeuser) {
        this.Login = Login;
        this.Password = Password;
        this.typeuser = typeuser;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String Login) {
        this.Login = Login;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getTypeuser() {
        return typeuser;
    }

    public void setTypeuser(String typeuser) {
        this.typeuser = typeuser;
    }


	public Set<Timesheet> getTimesheets() {
		return timesheets;
	}


	public void setTimesheets(Set<Timesheet> timesheets) {
		this.timesheets = timesheets;
	}


	public String getCle() {
		return cle;
	}


	public void setCle(String cle) {
		this.cle = cle;
	}

	public Employee getEmp() {
		return emp;
	}


	public void setEmp(Employee emp) {
		this.emp = emp;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
    
    
}
