package com.anotherbank.mochabank.authentication.domain.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import com.anotherbank.mochabank.exception.CheckException;

/**
 * This class represents an user for the Mocha Bank company.
 * 
 * @author Isabelle Deligniere
 */
@SuppressWarnings("serial")
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class User implements Serializable {
	// ======================================
    // =             Attributes             =
    // ======================================
	@Id
	@Column(name="id")
	private String username;
    private String firstname;
    private String lastname;
    private String email;
	@NotEmpty   
	private String password;   
    @ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "role_fk", referencedColumnName="id")
	private Role role;
	
	// ======================================
    // =            Constructors            =
    // ======================================
    
    public User() {}

    public User(final String username) {
    	setUsername(username);
    }

    public User(final String username, final String firstname, final String lastname) {
    	setUsername(username);
    	setFirstname(firstname);
    	setLastname(lastname);
    }

    // ======================================
    // =           Business methods         =
    // ======================================
    /**
     * This method checks the integrity of the object data.
     * @throws CheckException if data is invalid
     */
    public void checkData() throws CheckException {
        if (firstname == null || "".equals(firstname))
            throw new CheckException("Invalid User first name");
        if (lastname == null || "".equals(lastname))
            throw new CheckException("Invalid User last name");
    }

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    /**
     * This method sets the user name of an user.
     * @param username	the user name of an user.
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
	 * This method sets the last name of an user.
	 * @param lastname	the last name of an user.
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
	 * This method sets the role of an user.
	 * @param role	the role of an user.
	 */
	public void setRole(final Role role) {
		this.role = role;
	}
	/**
	 * This method gets the role of an user.
	 * @return role	the role of an user.
	 */
	public Role getRole() {
		return role;
	}
	/**
	 * This method displays an user.
	 * @return the user.
	 */
	public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("\n\tUser {");
        buf.append("\n\t\tId=").append(getUsername());
        buf.append("\n\t\tFirst Name=").append(getFirstname());
        buf.append("\n\t\tLast Name=").append(getLastname());
        buf.append(",password=").append(getPassword());
        buf.append("\n\t\temail=").append(getEmail());
        buf.append("\n\t}");
        return buf.toString();
    }
}
