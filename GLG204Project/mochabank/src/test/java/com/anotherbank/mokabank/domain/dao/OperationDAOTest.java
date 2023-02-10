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
import com.anotherbank.mochabank.domain.dao.OperationRepository;
import com.anotherbank.mochabank.domain.model.Account;
import com.anotherbank.mochabank.domain.model.Operation;
import com.anotherbank.mochabank.exception.CheckException;
import com.anotherbank.mochabank.exception.CreateException;
import com.anotherbank.mochabank.exception.FinderException;
import com.anotherbank.mokabank.configuration.TestConfig;

/**
 * This class tests the OperationDAO class.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@SpringBootTest(classes = MochabankApplication.class)
public class OperationDAOTest {
	
	@Autowired
	private OperationRepository operationRepository;

    //==================================
    //=            Test cases          =
    //==================================
    /**
     * This test tries to find an object with a invalid identifier.
     * 
     * @throws Exception	is thrown if an error occurs during the test.
     */
	 @Test
    public void testDomainFindOperationWithInvalidValues() throws Exception {
        // Finds an object with a unknown identifier
        final Long id = 333L;
        try {
        	findOperation(id);
            fail("Object with unknonw id should not be found");
        } catch (NoSuchElementException e) {
        }

        // Finds an object with a null identifier
        try {
        	operationRepository.findById(null).get();
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
    public void testDomainFindAllOperations() throws Exception {
        // First findAll
        int firstSize = findAllOperations();
        
        // Create an object
        Account accountA = new Account("accountName","bankId", "bankName", 20L);
        Operation operation = createOperation(accountA);
        
        // Ensures that the object exists
        try {
        	findOperation(operation.getId());
        } catch (NoSuchElementException e) {
            fail("Object has been created it should be found");
        }

        // Second findAll
        int secondSize = findAllOperations();
        
        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Cleans the test environment
        removeOperation(operation.getId());

        try {
        	findOperation(operation.getId());
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
    public void testDomainCreateOperation() throws Exception {
		 Operation operation = null;

        // Creates an object
		 Account accountA = new Account("accountName","bankId", "bankName", 20L);
		 operation = createOperation(accountA);

        // Ensures that the object exists
        try {
        	findOperation(operation.getId());
        } catch (NoSuchElementException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkOperation(operation);

        // Cleans the test environment
        removeOperation(operation.getId());        

        try {
            findOperation(operation.getId());
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
    public void testDomainDeleteUnknownOperation() throws Exception {
        // Removes an unknown object
        try {
            removeOperation(90000L);
            fail("Deleting an unknown object should break");
        } catch (EmptyResultDataAccessException e) {
        }
    }

    //==================================
    //=         Private Methods        =
    //==================================
    private Operation findOperation(final Long id) throws NoSuchElementException {
        final Operation operation = operationRepository.findById(id).get();
        return operation;
    }

    private int findAllOperations() throws FinderException {
        try {        	
            return ((Collection<Operation>) operationRepository.findAll()).size();
        } catch (Exception e) {
            return 0;
        }
    }

    private Operation createOperation(final Account accountA) throws CreateException, CheckException {
        final Operation operation = new Operation("description", "type", accountA, 5L, true);
        operationRepository.save(operation);
        return operation;
    }


    private void removeOperation(final Long id) throws EmptyResultDataAccessException {    	    	
    	operationRepository.deleteById(id);    	
    }

    
    private void checkOperation(final Operation operation) {
        assertEquals("description", "description", operation.getDescription());
        assertEquals("type", "type", operation.getType());
    }
    
}
