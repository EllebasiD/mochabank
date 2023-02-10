package com.anotherbank.mochabank.domain.dto;

import java.io.Serializable;

/**
 * This class encapsulates all the data for an address.
 *
 * @author Isabelle Deligniere
 */
@SuppressWarnings("serial")
public final class AddressDTO implements Serializable {
	// ======================================
    // =             Attributes             =
    // ======================================
    private String street1;
    private String street2;
    private String city;
    private String zipcode;

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    /**
	 * This method sets the street of an address (number included).
	 * @param street1	the street of an address.
	 */
    public void setStreet1(final String street1) {
    	this.street1 = street1;
    }
    /**
	 * This method gets the street of an address (number included).
	 * @return street1	the street of an address.
	 */
    public String getStreet1() {
        return street1;
    }
    /**
	 * This method sets the complement of the street of an address (if street1 need a complement).
	 * @param street2	the complement of the street of an address.
	 */
    public void setStreet2(final String street2) {
    	this.street2 = street2;
    }
    /**
	 * This method gets the complement of the street of an address.
	 * @return street2	the complement of the street of an address.
	 */
    public String getStreet2() {
        return street2;
    }
    /**
	 * This method sets the city of an address.
	 * @param city	the city of an address.
	 */
    public void setCity(final String city) {
    	this.city = city;
    }
    /**
	 * This method gets the city of an address.
	 * @return city	the city of an address.
	 */
    public String getCity() {
        return city;
    }
    /**
	 * This method sets the zip code of an address.
	 * @param zipcode	the zip code of an address.
	 */
    public void setZipcode(final String zipcode) {
    	this.zipcode = zipcode;
    }
    /**
	 * This method gets the zip code of an address.
	 * @return zipcode	the zip code of an address.
	 */
    public String getZipcode() {
        return zipcode;
    }
}
