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
import com.anotherbank.mochabank.domain.dao.ScheduledOperationRepository;
import com.anotherbank.mochabank.domain.dto.ScheduledOperationDTO;
import com.anotherbank.mochabank.domain.model.Account;
import com.anotherbank.mochabank.domain.model.ScheduledOperation;
import com.anotherbank.mochabank.domain.service.ScheduledOperationService;
import com.anotherbank.mochabank.exception.CheckException;
import com.anotherbank.mochabank.exception.CreateException;
import com.anotherbank.mochabank.exception.FinderException;
import com.anotherbank.mochabank.exception.ObjectNotFoundException;
import com.anotherbank.mochabank.exception.RemoveException;
import com.anotherbank.mokabank.configuration.TestConfig;

/**
 * This class tests the ScheduledOperationService class.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@SpringBootTest(classes = MochabankApplication.class)
public class ScheduledOperationServiceTest {
	
	private static Logger logger = LogManager.getLogger(ScheduledOperationServiceTest.class);

	// For non implemented operations like delete which is needed to clean the test environment
	@Autowired
	private ScheduledOperationRepository scheduledOperationRepository;
	
	@Autowired
	private ScheduledOperationService scheduledOperationService;
	
	//==================================
    //=            Test cases          =
    //==================================
    /**
     * This test tries to find an object with a invalid identifier.
     * 
     * @throws Exception	is thrown if an error occurs during the test.
     */
	@Test
    public void testServiceFindScheduledOperationWithInvalidValues() throws Exception {
        // Finds an object with a unknown identifier
        final Long id = 333L;
        try {
        	scheduledOperationService.findScheduledOperation(id);
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
    public void testServiceFindAllScheduledOperations() throws Exception {
        final Long id = 1L;

        // First findAll
        final int firstSize = findAllScheduledOperations();
        logger.info("firstSize is ... "+firstSize);
        // Creates an object
        ScheduledOperationDTO scheduledOperationDTO = createScheduledOperation(id);

        // Ensures that the object exists
        try {
            findScheduledOperation(scheduledOperationDTO.getId());
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Second findAll
        final int secondSize = findAllScheduledOperations();

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Cleans the test environment
        deleteScheduledOperation(scheduledOperationDTO.getId());

        try {
            findScheduledOperation(scheduledOperationDTO.getId());
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
    public void testServiceCreateScheduledOperation() throws Exception {
        final Long id = 1L;
        ScheduledOperationDTO scheduledOperationDTO = null;

        // Creates an object
        scheduledOperationDTO = createScheduledOperation(id);

        // Ensures that the object exists
        try {
        	findScheduledOperation(scheduledOperationDTO.getId());
        } catch (FinderException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkScheduledOperation(scheduledOperationDTO);

        // Cleans the test environment
        deleteScheduledOperation(scheduledOperationDTO.getId());
        

        try {
            findScheduledOperation(scheduledOperationDTO.getId());
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
    public void testServiceCreateScheduledOperationWithInvalidValues() throws Exception {
    	ScheduledOperationDTO scheduledOperationDTO;

        // Creates an object with a null parameter
        try {
        	scheduledOperationService.createScheduledOperation(null);
            fail("Object with null parameter should not be created");
        } catch (CreateException e) {
        }

        // Creates an object with empty values
        Long idA = 1L;
        Long idB = 2L;
        Long amount = 3L;
        
        try {
        	scheduledOperationDTO = new ScheduledOperationDTO(new String(), new String(), idA, idB, amount, new String(), true);
        	scheduledOperationService.createScheduledOperation(scheduledOperationDTO);
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with null values
        try {
        	scheduledOperationDTO = new ScheduledOperationDTO(null, null, idA, idB, amount, null, true);
        	scheduledOperationService.createScheduledOperation(scheduledOperationDTO);
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        }
    }

 
    //==================================
    //=          Private Methods       =
    //==================================

    private ScheduledOperationDTO findScheduledOperation(final Long id) throws FinderException, CheckException {
        final ScheduledOperationDTO scheduledOperationDTO = scheduledOperationService.findScheduledOperation(id);
        return scheduledOperationDTO;
    }

    private int findAllScheduledOperations() throws FinderException, RemoteException {
        try {
            return ((Collection<ScheduledOperationDTO>) scheduledOperationService.findScheduledOperations()).size();
        } catch (ObjectNotFoundException e) {
        	logger.warn("exception is ... "+e.getMessage());
            return 0;
        }
    }

    private ScheduledOperationDTO createScheduledOperation(final Long id) throws CreateException, CheckException, RemoteException {       
    	final Account accountA = new Account("accountNameA", "bankIdA", "bankNameA", 20L);
    	final Account accountB = new Account("accountNameB", "bankIdB", "bankNameB", 40L);
    	final ScheduledOperation scheduledOperation = new ScheduledOperation("description", "type", accountA, accountB, 5L, "cronExpression", true);
    	final ScheduledOperationDTO scheduledOperationDTO = new ScheduledOperationDTO(scheduledOperation.getDescription(), scheduledOperation.getType(), scheduledOperation.getAccountA().getId(), scheduledOperation.getAccountB().getId(), scheduledOperation.getAmount(),  scheduledOperation.getCronExpression(), scheduledOperation.getResult());    	
    	scheduledOperationDTO.setId(id);
    	scheduledOperationService.createScheduledOperation(scheduledOperationDTO);
    	return scheduledOperationDTO;
    }

    private void deleteScheduledOperation(final Long id) throws RemoveException, CheckException, RemoteException {
    	ScheduledOperation scheduledOperation;
        // Checks if the object exists
        if( ! scheduledOperationRepository.findById(id).isPresent())
        	throw new RemoveException("Operation must exist to be deleted");
        else 
        	scheduledOperation=scheduledOperationRepository.findById(id).get();
        // Deletes the object
        scheduledOperationRepository.delete(scheduledOperation);
    }

    private void checkScheduledOperation(final ScheduledOperationDTO scheduledOperationDTO) {
        assertEquals("description", "description", scheduledOperationDTO.getDescription());
        assertEquals("type", "type", scheduledOperationDTO.getType());
    }
}
