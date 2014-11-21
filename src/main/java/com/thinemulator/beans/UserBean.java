package com.thinemulator.beans;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserBean implements Serializable{

	 /**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = -1299234524292338425L;

	@NotNull
	    @Size(max = 64)
	     public String username;
	    
	    @Size(max = 64)
	    public String password;

	    @NotNull
	    @Size(max = 64)
	    public String email;
	    
	    public void setUsername(String username) {
	    	this.username = username;
	    }
	    
	    public String getUsername() {
	    	return this.username;
	    }
	    
	    public void setEmail(String email) {
	    	this.email = email;
	    }
	    
	    public String getEmail() {
	    	return this.email;
	    }
	    
	    public void setPassword(String password) {
	    	this.password = password;
	    }
	    
	    public String getPassword() {
	    	return this.password;
	    }
}
