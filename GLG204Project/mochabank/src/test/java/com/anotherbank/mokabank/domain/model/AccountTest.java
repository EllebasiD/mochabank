package com.anotherbank.mokabank.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.anotherbank.mochabank.domain.model.Account;
import com.anotherbank.mochabank.exception.CheckException;

/**
 * This class tests the Account class.
 */
public class AccountTest {
	//==================================
    //=            Test cases          =
    //==================================

    /**
     * This test tries to create an object with valid values.
     */
	@Test
    public void testCreateValidAccount() {

        // Creates a valid account 
        try {
        	final Account account = new Account("Compte courant", "1", "Mocha bank", 100L);
        	assertEquals("Compte courant", account.getAccountName());
        	assertEquals("1", account.getBankId());
        	assertEquals("Mocha bank", account.getBankName());
        	assertEquals(100L, account.getBalance());
        	account.checkData();
        } catch (CheckException e) {
            fail("Account data is OK!");
        }
    }

    /**
     * This test tries to create an object with invalid values.
     * 
     * @throws Exception	is thrown if an error occurs during the test.
     */
	@Test
    public void testCreateAccountWithInvalidValues() throws Exception {

        // Creates objects with empty values
        try {
            final Account account = new Account("Compte courant", "", "Mocha bank", 100L);
            account.checkData();
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        	assertEquals("Invalid bank id", e.getMessage());
        }
        try {
            final Account account = new Account("Compte courant", "1", "", 100L);
            account.checkData();
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        	assertEquals("Invalid bank name", e.getMessage());
        }
        
        try {
            final Account account = new Account("", "1", "Mocha bank", 100L);
            account.checkData();
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        	assertEquals("Invalid account name", e.getMessage());
        }

        // Creates objects with null values
        try {
            final Account account = new Account("Compte courant", null, "Mocha bank", 100L);
            account.checkData();
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        	assertEquals("Invalid bank id", e.getMessage());
        }
        
        try {
            final Account account = new Account(null, "1", "Mocha bank", 100L);
            account.checkData();
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        	assertEquals("Invalid account name", e.getMessage());
        }
        
        try {
            final Account account = new Account("Compte courant", "1", null, 100L);
            account.checkData();
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        	assertEquals("Invalid bank name", e.getMessage());
        }
    }
}
