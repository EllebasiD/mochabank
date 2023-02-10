package com.anotherbank.mokabank.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.anotherbank.mochabank.domain.model.Account;
import com.anotherbank.mochabank.domain.model.ScheduledOperation;
import com.anotherbank.mochabank.exception.CheckException;

/**
 * This class tests the ScheduledOperation class.
 */
public class ScheduledOperationTest {
	//==================================
    //=            Test cases          =
    //==================================

    /**
     * This test tries to create an object with valid values.
     */
	@Test
    public void testCreateValidScheduledOperation() {

        // Creates a valid operation 
		//final String description, final String type, final Account accountA, final Account accountB
		//final long amount, final String cronExpression, final boolean result
        try {
        	final Account accountA = new Account("Compte courant", "1", "Mocha bank", 1000L);
        	final Account accountB = new Account("Compte courant", "4", "Mocha bank", 500L);
        	String cronExpression = "0 * * * * *";
        	final ScheduledOperation scheduledOperation = new ScheduledOperation("Virement", "Débit", accountA, accountB, 200L, cronExpression, true);
        	assertEquals("Virement", scheduledOperation.getDescription());
        	assertEquals("Débit", scheduledOperation.getType());
        	assertEquals(accountA, scheduledOperation.getAccountA());
        	assertEquals(accountB, scheduledOperation.getAccountB());
        	assertEquals(200L, scheduledOperation.getAmount());
        	assertEquals(200L, scheduledOperation.getAmount());
        	assertEquals(cronExpression, scheduledOperation.getCronExpression());
        	scheduledOperation.checkData();
        }catch (CheckException e) {
            fail("ScheduledOperation data is OK!");
        }
        
    }

    /**
     * This test tries to create an object with invalid values.
     * 
     * @throws Exception	is thrown if an error occurs during the test.
     */
	@Test
    public void testCreateScheduledOperationWithInvalidValues() throws Exception {

        // Creates objects with empty values
        try {
        	final Account accountA = new Account("Compte courant", "1", "Mocha bank", 1000L);
        	final Account accountB = new Account("Compte courant", "4", "Mocha bank", 500L);
        	String cronExpression = "0 * * * * *";
        	final ScheduledOperation scheduledOperation = new ScheduledOperation("", "Débit", accountA, accountB, 200L, cronExpression, true);
        	scheduledOperation.checkData();
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        	assertEquals("Invalid scheduled operation's description", e.getMessage());
        }
        try {
        	final Account accountA = new Account("Compte courant", "1", "Mocha bank", 1000L);
        	final Account accountB = new Account("Compte courant", "4", "Mocha bank", 500L);
        	String cronExpression = "0 * * * * *";
        	final ScheduledOperation scheduledOperation = new ScheduledOperation("Virement", "", accountA, accountB, 200L, cronExpression, true);
        	scheduledOperation.checkData();
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        	assertEquals("Invalid scheduled operation's type", e.getMessage());
        } 
        
        try {
        	final Account accountA = new Account("Compte courant", "1", "Mocha bank", 1000L);
        	final Account accountB = new Account("Compte courant", "4", "Mocha bank", 500L);
        	String cronExpression = "";
        	final ScheduledOperation scheduledOperation = new ScheduledOperation("Virement", "Débit", accountA, accountB, 200L, cronExpression, true);
        	scheduledOperation.checkData();
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        	assertEquals("Invalid scheduled operation's cronExpression", e.getMessage());
        } 
        
        // Creates objects with null values
        try {
        	final Account accountA = new Account("Compte courant", "1", "Mocha bank", 1000L);
        	final Account accountB = new Account("Compte courant", "4", "Mocha bank", 500L);
        	String cronExpression = "0 * * * * *";
        	final ScheduledOperation scheduledOperation = new ScheduledOperation(null, "Débit", accountA, accountB, 200L, cronExpression, true);
        	scheduledOperation.checkData();
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        	assertEquals("Invalid scheduled operation's description", e.getMessage());
        }
        
        try {
        	final Account accountA = new Account("Compte courant", "1", "Mocha bank", 1000L);
        	final Account accountB = new Account("Compte courant", "4", "Mocha bank", 500L);
        	String cronExpression = "0 * * * * *";
        	final ScheduledOperation scheduledOperation = new ScheduledOperation("Virement", null, accountA, accountB, 200L, cronExpression, true);
        	scheduledOperation.checkData();
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        	assertEquals("Invalid scheduled operation's type", e.getMessage());
        }
        
        try {
        	final Account accountA = null;
        	final Account accountB = new Account("Compte courant", "4", "Mocha bank", 500L);
        	String cronExpression = "0 * * * * *";
        	final ScheduledOperation scheduledOperation = new ScheduledOperation("Virement", "Débit", accountA, accountB, 200L, cronExpression, true);
        	scheduledOperation.checkData();
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        	assertEquals("Invalid scheduled operation's account A", e.getMessage());
        }  
        
        try {
        	final Account accountA = new Account("Compte courant", "1", "Mocha bank", 1000L);
        	final Account accountB = null;
        	String cronExpression = "0 * * * * *";
        	final ScheduledOperation scheduledOperation = new ScheduledOperation("Virement", "Débit", accountA, accountB, 200L, cronExpression, true);
        	scheduledOperation.checkData();
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        	assertEquals("Invalid scheduled operation's account B", e.getMessage());
        } 
        
        try {
        	final Account accountA = new Account("Compte courant", "1", "Mocha bank", 1000L);
        	final Account accountB = new Account("Compte courant", "4", "Mocha bank", 500L);
        	String cronExpression = null;
        	final ScheduledOperation scheduledOperation = new ScheduledOperation("Virement", "Débit", accountA, accountB, 200L, cronExpression, true);
        	scheduledOperation.checkData();
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        	assertEquals("Invalid scheduled operation's cronExpression", e.getMessage());
        } 
    }
}
