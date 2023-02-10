package com.anotherbank.mokabank.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.anotherbank.mochabank.domain.model.Account;
import com.anotherbank.mochabank.domain.model.Operation;
import com.anotherbank.mochabank.exception.CheckException;

/**
 * This class tests the Operation class.
 */
public class OperationTest {
	//==================================
    //=            Test cases          =
    //==================================

    /**
     * This test tries to create an object with valid values.
     */
	@Test
    public void testCreateValidOperation() {

        // Creates a valid operation 
        try {
        	final Account accountA = new Account("Compte courant", "1", "Mocha bank", 1000L);        	
        	final Operation operation = new Operation("Virement", "Débit", accountA, 200L, true);
        	assertEquals("Virement", operation.getDescription());
        	assertEquals("Débit", operation.getType());
        	assertEquals(accountA, operation.getAccountA());
        	assertEquals(200L, operation.getAmount());
        	assertEquals(true, operation.getResult());
        	operation.checkData();
        } catch (CheckException e) {
            fail("Operation data is OK!");
        }
        
        try {
        	final Account accountA = new Account("Compte courant", "1", "Mocha bank", 1000L);
        	final Account accountB = new Account("Compte courant", "4", "Mocha bank", 500L);
        	final Operation operation = new Operation("Virement", "Débit", accountA, accountB, 200L, true);
        	assertEquals("Virement", operation.getDescription());
        	assertEquals("Débit", operation.getType());
        	assertEquals(accountA, operation.getAccountA());
        	assertEquals(accountB, operation.getAccountB());
        	assertEquals(200L, operation.getAmount());
        	assertEquals(true, operation.getResult());
        	operation.checkData();
        }catch (CheckException e) {
            fail("Operation data is OK!");
        }
        
    }

    /**
     * This test tries to create an object with invalid values.
     * 
     * @throws Exception	is thrown if an error occurs during the test.
     */
	@Test
    public void testCreateOperationWithInvalidValues() throws Exception {

        // Creates objects with empty values
        try {
        	final Account accountA = new Account("Compte courant", "1", "Mocha bank", 100L);
        	final Operation operation = new Operation("", "Débit", accountA, 200L, true);
            operation.checkData();
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        	assertEquals("Invalid operation's description", e.getMessage());
        }
        try {
        	final Account accountA = new Account("Compte courant", "1", "Mocha bank", 100L);
        	final Operation operation = new Operation("Virement", "", accountA, 200L, true);
        	operation.checkData();
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        	assertEquals("Invalid operation's type", e.getMessage());
        }

        // Creates objects with null values
        try {
        	final Account accountA = new Account("Compte courant", "1", "Mocha bank", 100L);
        	final Operation operation = new Operation(null, "Débit", accountA, 200L, true);
        	operation.checkData();
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        	assertEquals("Invalid operation's description", e.getMessage());
        }
        
        try {
        	final Account accountA = new Account("Compte courant", "1", "Mocha bank", 100L);
        	final Operation operation = new Operation("Virement", null, accountA, 200L, true);
        	operation.checkData();
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        	assertEquals("Invalid operation's type", e.getMessage());
        }
        
        try {
        	final Account accountA = null;
        	final Operation operation = new Operation("Virement", "Débit", accountA, 200L, true);
        	operation.checkData();
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        	assertEquals("Invalid operation's account A", e.getMessage());
        }  
    }
}
