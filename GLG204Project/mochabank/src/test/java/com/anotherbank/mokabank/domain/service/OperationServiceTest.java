package com.anotherbank.mokabank.domain.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.rmi.RemoteException;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.anotherbank.mochabank.MochabankApplication;
import com.anotherbank.mochabank.domain.dao.OperationRepository;
import com.anotherbank.mochabank.domain.dto.OperationDTO;
import com.anotherbank.mochabank.domain.model.Account;
import com.anotherbank.mochabank.domain.model.Operation;
import com.anotherbank.mochabank.domain.service.OperationService;
import com.anotherbank.mochabank.exception.CheckException;
import com.anotherbank.mochabank.exception.CreateException;
import com.anotherbank.mochabank.exception.FinderException;
import com.anotherbank.mochabank.exception.ObjectNotFoundException;
import com.anotherbank.mochabank.exception.RemoveException;
import com.anotherbank.mokabank.configuration.TestConfig;

/**
 * This class tests the OperationService class.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@SpringBootTest(classes = MochabankApplication.class)
public class OperationServiceTest {

	private static Logger logger = LogManager.getLogger(OperationServiceTest.class);

	// For non implemented operations like delete which is needed to clean the test environment
	@Autowired
	private OperationRepository operationRepository;
	
	@Autowired
	private OperationService operationService;
	
	//==================================
    //=            Test cases          =
    //==================================
    /**
     * This test tries to find an object with a invalid identifier.
     * 
     * @throws Exception	is thrown if an error occurs during the test.
     */
	@Test
    public void testServiceFindOperationWithInvalidValues() throws Exception {
        // Finds an object with a unknown identifier
        final Long id = 333L;
        try {
        	operationService.findOperation(id);
            fail("Object with unknonw id should not be found");
        } catch (FinderException e) {
        }

    }

    /**
     * This test ensures that the method findAll works. It does a first findAll, creates
     * a new object and does a second findAll.
     * 
     * @throws Exception	is thrown if an error occurs during the test.
     */
	@Test
    public void testServiceFindAllOperations() throws Exception {
        final Long id = 1L;

        // First findAll
        final int firstSize = findAllOperations();
        logger.info("firstSize is ... "+firstSize);
        // Creates an object
        OperationDTO operationDTO = createOperation(id);

        // Ensures that the object exists
        try {
            findOperation(operationDTO.getId());
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Second findAll
        final int secondSize = findAllOperations();

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Cleans the test environment
        deleteOperation(operationDTO.getId());

        try {
            findOperation(operationDTO.getId());
            fail("Object has been deleted it shouldn't be found");
        } catch (FinderException e) {
        }
    }

    /**
     * This method ensures that creating an object works. It first finds the object,
     * makes sure it doesn't exist, creates it and checks it then exists.
     * 
     * @throws Exception	is thrown if an error occurs during the test.
     */
	@Test
    public void testServiceCreateOperation() throws Exception {
        final Long id = 1L;
        OperationDTO operationDTO = null;

        // Creates an object
        operationDTO = createOperation(id);

        // Ensures that the object exists
        try {
        	findOperation(operationDTO.getId());
        } catch (FinderException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkOperation(operationDTO);

        // Cleans the test environment
        deleteOperation(operationDTO.getId());

        try {
            findOperation(operationDTO.getId());
            fail("Object has been deleted it shouldn't be found");
        } catch (FinderException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid values.
     * 
     * @throws Exception	is thrown if an error occurs during the test.
     */
    @Test
    public void testServiceCreateOperationWithInvalidValues() throws Exception {
    	OperationDTO operationDTO;

        // Creates an object with a null parameter
        try {
        	operationService.createOperation(null);
            fail("Object with null parameter should not be created");
        } catch (CreateException e) {
        }

        // Creates an object with empty values
        Long id = 1L;
        Long amount = 3L;
        
        try {
        	operationDTO = new OperationDTO(new String(), new String(), id, amount, true);
        	operationService.createOperation(operationDTO);
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with null values
        try {
        	operationDTO = new OperationDTO(null, null, id, amount, true);
        	operationService.createOperation(operationDTO);
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        }
    }

 
    //==================================
    //=          Private Methods       =
    //==================================

    private OperationDTO findOperation(final Long id) throws FinderException, CheckException {
        final OperationDTO operationDTO = operationService.findOperation(id);
        return operationDTO;
    }

    private int findAllOperations() throws FinderException, RemoteException {
        try {
            return ((Collection<OperationDTO>) operationService.findOperations()).size();
        } catch (ObjectNotFoundException e) {
        	logger.warn("exception is ... "+e.getMessage());
            return 0;
        }
    }

    private OperationDTO createOperation(final Long id) throws CreateException, CheckException, RemoteException {       
    	final Account accountA = new Account("accountName", "bankId", "bankName", 20L);
    	final Operation operation = new Operation("description", "type", accountA, 5L, true);
    	final OperationDTO operationDTO = new OperationDTO(operation.getDescription(), operation.getType(), operation.getAccountA().getId(), operation.getAmount(), operation.getResult());    	
    	operationDTO.setId(id);
    	operationService.createOperation(operationDTO);
    	return operationDTO;
    }

    private void deleteOperation(final Long id) throws RemoveException, CheckException, RemoteException {
    	Operation operation;
        // Checks if the object exists
        if( ! operationRepository.findById(id).isPresent())
        	throw new RemoveException("Operation must exist to be deleted");
        else 
        	operation=operationRepository.findById(id).get();
        // Deletes the object
        operationRepository.delete(operation);
    }

    private void checkOperation(final OperationDTO operationDTO) {
        assertEquals("description", "description", operationDTO.getDescription());
        assertEquals("type", "type", operationDTO.getType());
    }
}
