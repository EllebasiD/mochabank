package com.externalbank.otherbank.authentication.domain.dto;

import java.io.Serializable;

/**
 * This class follows the Data Transfert Object design pattern.
 * It is a client view of a User. This class only
 * transfers data from a distant service to a client.
 * 
 * @author Isabelle Deligniere
 */
@SuppressWarnings("serial")
public class UserDTO implements Serializable{
	// ======================================
    // =             Attributes             =
    // ======================================
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Integer roleId;
    private String roleName;

    // ======================================
    // =            Constructors            =
    // ======================================
    public UserDTO() {
    }
    
    public UserDTO(final String username) {
    	setUsername(username);
    }
    
    public UserDTO(final String username, final String firstname, final String lastname) {
        setUsername(username);
        setFirstname(firstname);
        setLastname(lastname);
    }

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    /**
     * This method sets the user name of a user.
     * @param username	the user name of a user.
     */
    public void setUsername(final String username) {
    	this.username = username;
    }
    /**
     * This method gets the user name of an user.
     * @return username	 the user name of an user.
     */
    public String getUsername() {
        return username;
    }
    /**
     * This method sets the first name of an user.
     * @param firstname	the first name of an user.
     */
    public void setFirstname(final String firstname) {
    	this.firstname = firstname;
    }
    /**
     * This method gets the first name of an user.
     * @return firstname	the first name of an user.
     */
    public String getFirstname() {
        return firstname;
    }
    /**
     * This method sets the first name of an user.
     * @param lastname	the first name of an user.
     */
    public void setLastname(final String lastname) {
    	this.lastname = lastname;
    }
    /**
     * This method gets the last name of an user.
     * @return lastname	the last name of an user.
     */
    public String getLastname() {
        return lastname;
    }
    /**
     * This method sets the email of an user.
     * @param email	the email of an user.
     */
    public void setEmail(final String email) {
    	this.email = email;
    }
    /**
     * This method gets the email of an user.
     * @return email	the email of an user.
     */
    public String getEmail() {
        return email;
    }
    /**
     * This method sets the password of an user.
     * @param password	the password of an user.
     */
    public void setPassword(String password) {
		this.password = password;
	}
    /**
	 * This method gets the password of an user.
	 * @return password	the password of an user.
	 */
    public String getPassword() {
		return password;
	}
	/**
	 * This method sets the role name of a user role.
	 * 
	 * @param roleId		the role identifier of the user role.
	 */
	public void setRoleId(final Integer roleId) {
		this.roleId = roleId;
	}
	/**
	 * This method returns the role identifier of a user role.
	 * 
	 * @return roleId		the role identifier of the user role.
	 */
	public Integer getRoleId() {

		return roleId;
	}
    /**
	 * This method sets the role name of an user role.
	 * 
	 * @param roleName		the role name of the user role.
	 */
    public void setRoleName(String roleName) {
  		this.roleName = roleName;
  	}
    /**
	 * This method returns the role name of an user role.
	 * 
	 * @return roleName		the role name of the user role.
	 */
	public String getRoleName() {
		return roleName;
	}
    /**
	 * This method displays an user.
	 * @return the user.
	 */
    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("UserDTO{");
        buf.append("username=").append(getUsername());
        buf.append(",firstname=").append(getFirstname());
        buf.append(",lastname=").append(getLastname());
        buf.append(",email=").append(getEmail());
        buf.append('}');
        return buf.toString();
    }
}
