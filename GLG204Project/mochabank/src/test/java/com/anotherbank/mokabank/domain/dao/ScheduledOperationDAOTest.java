package com.anotherbank.mokabank.domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.NoSuchElementException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.anotherbank.mochabank.MochabankApplication;
import com.anotherbank.mochabank.domain.dao.ScheduledOperationRepository;
import com.anotherbank.mochabank.domain.model.Account;
import com.anotherbank.mochabank.domain.model.ScheduledOperation;
import com.anotherbank.mochabank.exception.CheckException;
import com.anotherbank.mochabank.exception.CreateException;
import com.anotherbank.mochabank.exception.FinderException;
import com.anotherbank.mokabank.configuration.TestConfig;

/**
 * This class tests the ScheduledOperationDAO class.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@SpringBootTest(classes = MochabankApplication.class)
public class ScheduledOperationDAOTest {
	
	@Autowired
	private ScheduledOperationRepository scheduledOperationRepository;

    //==================================
    //=            Test cases          =
    //==================================
    /**
     * This test tries to find an object with a invalid identifier.
     * 
     * @throws Exception	is thrown if an error occurs during the test.
     */
	 @Test
    public void testDomainFindScheduledOperationWithInvalidValues() throws Exception {
        // Finds an object with a unknown identifier
        final Long id = 333L;
        try {
        	findScheduledOperation(id);
            fail("Object with unknonw id should not be found");
        } catch (NoSuchElementException e) {
        }

        // Finds an object with a null identifier
        try {
        	scheduledOperationRepository.findById(null).get();
            fail("Object with null id should not be found");
        } catch (Exception e) {
        }
    }

    /**
     * This test ensures that the method findAll works. It does a first findAll, creates
     * a new object and does a second findAll.
     * 
     * @throws Exception	is thrown if an error occurs during the test.
     */
	 @Test
    public void testDomainFindAllScheduledOperations() throws Exception {
        // First findAll
        int firstSize = findAllScheduledOperations();

        // Create an object
        final Account accountA = new Account("accountNameA", "bankIdA", "bankNameA", 20L);
        final Account accountB = new Account("accountNameB", "bankIdB", "bankNameB", 40L);
        ScheduledOperation scheduledOperation = createScheduledOperation(accountA, accountB);

        // Ensures that the object exists
        try {
        	findScheduledOperation(scheduledOperation.getId());
        } catch (NoSuchElementException e) {
            fail("Object has been created it should be found");
        }

        // Second findAll
        int secondSize = findAllScheduledOperations();

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Cleans the test environment
        removeScheduledOperation(scheduledOperation.getId());

        try {
        	findScheduledOperation(scheduledOperation.getId());
            fail("Object has been deleted it shouldn't be found");
        } catch (NoSuchElementException e) {
        }
    }

    /**
     * This method ensures that creating an object works. It first finds the object,
     * makes sure it doesn't exist, creates it and checks it then exists.
     * 
     * @throws Exception	is thrown if an error occurs during the test.
     */
	 @Test
    public void testDomainCreateScheduledOperation() throws Exception {
		 ScheduledOperation scheduledOperation = null;

        // Creates an object
		 final Account accountA = new Account("accountNameA", "bankIdA", "bankNameA", 20L);
		 final Account accountB = new Account("accountNameB", "bankIdB", "bankNameB", 40L);
		 scheduledOperation = createScheduledOperation(accountA, accountB);

        // Ensures that the object exists
        try {
        	findScheduledOperation(scheduledOperation.getId());
        } catch (NoSuchElementException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkScheduledOperation(scheduledOperation);

        // Cleans the test environment
        removeScheduledOperation(scheduledOperation.getId());

        try {
            findScheduledOperation(scheduledOperation.getId());
            fail("Object has been deleted it shouldn't be found");
        } catch (NoSuchElementException e) {
        }

    }

    /**
     * This test ensures that the system cannot remove an unknown object.
     * 
     * @throws Exception	is thrown if an error occurs during the test.
     */
	 @Test
    public void testDomainDeleteUnknownScheduledOperation() throws Exception {
        // Removes an unknown object
        try {
            removeScheduledOperation(90000L);
            fail("Deleting an unknown object should break");
        } catch (EmptyResultDataAccessException e) {
        }
    }

    //==================================
    //=         Private Methods        =
    //==================================
    private ScheduledOperation findScheduledOperation(final Long id) throws NoSuchElementException {
        final ScheduledOperation scheduledOperation = scheduledOperationRepository.findById(id).get();
        return scheduledOperation;
    }

    private int findAllScheduledOperations() throws FinderException {
        try {        	
            return ((Collection<ScheduledOperation>) scheduledOperationRepository.findAll()).size();
        } catch (Exception e) {
            return 0;
        }
    }

    private ScheduledOperation createScheduledOperation(final Account accountA, final Account accountB) throws CreateException, CheckException {        
        final ScheduledOperation scheduledOperation = new ScheduledOperation("description", "type", accountA, accountB, 5L, "cronExpression", true);
        scheduledOperationRepository.save(scheduledOperation);
        return scheduledOperation;
    }

    private void removeScheduledOperation(final Long id) throws EmptyResultDataAccessException {  	
    	scheduledOperationRepository.deleteById(id);
    }

    private void checkScheduledOperation(final ScheduledOperation scheduledOperation) {
        assertEquals("description", "description", scheduledOperation.getDescription());
        assertEquals("type", "type", scheduledOperation.getType());
        assertEquals("cronExpression", "cronExpression", scheduledOperation.getCronExpression());
    }
    
}
