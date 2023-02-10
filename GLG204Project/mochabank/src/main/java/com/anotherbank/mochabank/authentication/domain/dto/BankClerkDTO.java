package com.anotherbank.mochabank.authentication.domain.dto;

import java.io.Serializable;

/**
 * This class follows the Data Transfer Object design pattern.
 * It is a client view of a BankClerk. This class only
 * transfers data from a distant service to a client.
 * 
 *  @author Isabelle Deligniere
 */
@SuppressWarnings("serial")
public final class BankClerkDTO extends UserDTO implements Serializable {
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
    public BankClerkDTO() {
    }

    public BankClerkDTO(final String username) {
    	setUsername(username);
    }
    
    public BankClerkDTO(final String username, final String firstname, final String lastname) {
        setUsername(username);
        setFirstname(firstname);
        setLastname(lastname);
    }

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    /**
     * This method sets the user name of a bank clerk.
     * @param username	the user name of a bank clerk.
     */
    public void setUsername(final String username) {
    	this.username = username;
    }
    /**
     * This method gets the user name of a bank clerk.
     * @return id	 the user name of a bank clerk.
     */
    public String getUsername() {
        return username;
    }
    /**
     * This method sets the first name of a bank clerk.
     * @param firstname	the first name of a bank clerk.
     */
    public void setFirstname(final String firstname) {
    	this.firstname = firstname;
    }
    /**
     * This method gets the first name of a bank clerk.
     * @return firstname	the first name of a bank clerk.
     */
    public String getFirstname() {
        return firstname;
    }
    /**
	 * This method sets the last name of a bank clerk.
	 * @param lastname	the last name of a bank clerk.
	 */
    public void setLastname(final String lastname) {
    	this.lastname = lastname;
    }
    /**
	 * This method gets the last name of a bank clerk.
	 * @return lastname	the last name of a bank clerk.
	 */
    public String getLastname() {
        return lastname;
    }
    /**
     * This method sets the password of a bank clerk.
     * @param password	the password of a bank clerk.
     */
    public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * This method gets the password of a bank clerk.
	 * @return password	the password of a bank clerk.
	 */
	public String getPassword() {
		return password;
	}
    /**
     * This method sets the email of a bank clerk.
     * @param email	the email of a bank clerk.
     */
    public void setEmail(final String email) {
    	this.email = email;
    }
    /**
     * This method gets the email of a bank clerk.
     * @return email	the email of a bank clerk.
     */
    public String getEmail() {
        return email;
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
	 * This method sets the role name of a bank clerk role.
	 * 
	 * @param roleName		the role name of the bank clerk role.
	 */
	public void setRoleName(final String roleName) {
		this.roleName = roleName;
	}
	/**
	 * This method returns the role name of a bank clerk role.
	 * 
	 * @return roleName		the role name of the bank clerk role.
	 */
	public String getRoleName() {
		return roleName;
	}
    /**
	 * This method displays a bank clerk.
	 * @return the bank clerk.
	 */
    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("BankClerkDTO{");
        buf.append("username=").append(getUsername());
        buf.append(",firstname=").append(getFirstname());
        buf.append(",lastname=").append(getLastname());
        buf.append(",password=").append(getPassword());
        buf.append(",email=").append(getEmail());
		buf.append(",Role Name=").append(getRoleName());
        buf.append('}');
        return buf.toString();
    }
}
