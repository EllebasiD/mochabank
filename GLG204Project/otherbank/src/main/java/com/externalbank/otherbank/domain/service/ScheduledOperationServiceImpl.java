package com.externalbank.otherbank.domain.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.externalbank.otherbank.domain.dao.AccountRepository;
import com.externalbank.otherbank.domain.dao.ScheduledOperationRepository;
import com.externalbank.otherbank.domain.dto.ScheduledOperationDTO;
import com.externalbank.otherbank.domain.model.Account;
import com.externalbank.otherbank.domain.model.ScheduledOperation;
import com.externalbank.otherbank.exception.CheckException;
import com.externalbank.otherbank.exception.CreateException;
import com.externalbank.otherbank.exception.DuplicateKeyException;
import com.externalbank.otherbank.exception.FinderException;
import com.externalbank.otherbank.logging.Trace;

/**
* This class is a facade for all ScheduledOperation services.
* 
* @author Isabelle Deligniere
*
*/

@Service
@Transactional
public class ScheduledOperationServiceImpl extends TransactionServiceImpl implements ScheduledOperationService {
	// ======================================
    // =             Attributes             =
    // ======================================
    
	// Used for logging
    protected final transient String _cname = this.getClass().getName();
    
    @Autowired
    private ScheduledOperationRepository scheduledOperationRepository; 
    
    @Autowired
    private AccountRepository accountRepository;

    // ======================================
    // =            Constructors            =
    // ======================================
    public ScheduledOperationServiceImpl() {}

	// ======================================
    // =           Business methods         =
    // ======================================
    /**
     * This method creates a scheduled operation.
     * 
     * @param scheduledOperationDTO 	the ScheduledOperation.
     * @throws CreateException is thrown if an error occurs during creation.
     * @throws CheckException is thrown if an error occurs during checking.
     */
	@Override
	@Transactional
	public ScheduledOperationDTO createScheduledOperation(ScheduledOperationDTO scheduledOperationDTO) throws CreateException, CheckException {
		final String mname = "createScheduledOperation";
	    Trace.entering(_cname, mname, scheduledOperationDTO);

	    if (scheduledOperationDTO == null)
	        throw new CreateException("Scheduled Operation object is null");
	    // Checks if ScheduledOperation already exists
	    try {
			if(findScheduledOperation(scheduledOperationDTO.getId())!=null)
				throw new DuplicateKeyException();
		} catch (FinderException  | CheckException e) {}
	             
	    Account accountA = null;
	    Account accountB = null;
	        
	    // Find the AccountA
	    if(accountRepository.findById(scheduledOperationDTO.getAccountAId()).isPresent()) {
	       	accountA = accountRepository.findById(scheduledOperationDTO.getAccountAId()).get();
	    }else {
			throw new CreateException("AccountA must exist to create an operation");
		}
	    // Find the AccountB
	    if(scheduledOperationDTO.getAccountBId() != null) {
	    	if(accountRepository.findById(scheduledOperationDTO.getAccountBId()).isPresent()) {
		       	accountB = accountRepository.findById(scheduledOperationDTO.getAccountBId()).get();
		    }else {
				throw new CreateException("AccountA must exist to create an operation");
			}
	    }
	        
	    try {
			if(findScheduledOperation(scheduledOperationDTO.getId())!=null)
				 throw new DuplicateKeyException();
		} catch (FinderException  | CheckException e) {}

	    // Transforms ScheduledOperation DTO into domain object
	    ScheduledOperation scheduledOperation = null;
	    scheduledOperation = new ScheduledOperation(scheduledOperationDTO.getDescription(), scheduledOperationDTO.getType(), 
	    		accountA, accountB,scheduledOperationDTO.getAmount(), scheduledOperationDTO.getCronExpression(), scheduledOperationDTO.getResult());
	             
	    scheduledOperation.checkData();
	    	
	    // Creates the object
	    scheduledOperationRepository.save(scheduledOperation);
	    // Transforms domain object into DTO
	    final ScheduledOperationDTO result = transformScheduledOperation2DTO(scheduledOperation);

	    Trace.exiting(_cname, mname, result);
	 
	    return result;
	}
	/**
     * This method finds a scheduled operation by its identifier.
     * 
     * @param scheduledOperationId		the identifier of the operation.
     * @throws FinderException is thrown if an error occurs during searching.
     * @throws CheckException is thrown if an error occurs during checking.
     * @return scheduledOperationDTO		the ScheduledOperation.
     */
	@Override
	@Transactional(readOnly=true)
	public ScheduledOperationDTO findScheduledOperation(long scheduledOperationId) throws FinderException, CheckException {
		final String mname = "findScheduledOperation";
	    Trace.entering(_cname, mname, scheduledOperationId);

	    checkId(scheduledOperationId);
	    	
	    // Finds the object
	    ScheduledOperation scheduledOperation = null;
	    if( ! scheduledOperationRepository.findById(scheduledOperationId).isPresent())
	       	throw new FinderException("ScheduledOperation must exist to be found");
	    else 
	       	scheduledOperation = scheduledOperationRepository.findById(scheduledOperationId).get();

	    // Transforms domain object into DTO
	    final ScheduledOperationDTO scheduledOperationDTO = transformScheduledOperation2DTO(scheduledOperation);

	    Trace.exiting(_cname, mname, scheduledOperationDTO);
	       
	    return scheduledOperationDTO;
	}
	/**
     * This method finds all the scheduled operations.
     * 
     * @throws FinderException is thrown if an error occurs during searching.
     */
	@Override
	@Transactional(readOnly=true)
	public Iterable<ScheduledOperationDTO> findScheduledOperations() throws FinderException {
		final String mname = "findScheduledOperations";
        Trace.entering(_cname, mname);

        // Finds all the objects
        final Iterable<ScheduledOperation> scheduledOperations = scheduledOperationRepository.findAll();

        // Transforms domain objects into DTOs
        Iterable<ScheduledOperationDTO> scheduledOperationsDTO = transformScheduledOperations2DTOs(scheduledOperations);
        
        Integer integer = Integer.valueOf(((Collection<ScheduledOperationDTO>) scheduledOperationsDTO).size());
        Trace.exiting(_cname, mname, integer);
        return scheduledOperationsDTO;
	}
    
	
	// ======================================
    // =          Private Methods           =
    // ======================================
    /**
     * This method transforms a ScheduledOperation object to a ScheduledOperationDTO object.
     * 
     * @param scheduledOperation		the ScheduledOperation object.
     * @return scheduledOperationDTO	the ScheduledOperationDTO object.
     */
	private ScheduledOperationDTO transformScheduledOperation2DTO(ScheduledOperation scheduledOperation) {
		final ScheduledOperationDTO scheduledOperationDTO = new ScheduledOperationDTO();		
		scheduledOperationDTO.setId(scheduledOperation.getId());
		scheduledOperationDTO.setTransactionDate(scheduledOperation.getTransactionDate());
		scheduledOperationDTO.setDescription(scheduledOperation.getDescription());
		scheduledOperationDTO.setType(scheduledOperation.getType());		
		scheduledOperationDTO.setAccountAId(scheduledOperation.getAccountA().getId());
		if(scheduledOperation.getAccountB() != null) {
			scheduledOperationDTO.setAccountBId(scheduledOperation.getAccountB().getId());
		}
		scheduledOperationDTO.setAmount(scheduledOperation.getAmount());
		scheduledOperationDTO.setCronExpression(scheduledOperation.getCronExpression());
		scheduledOperationDTO.setResult(scheduledOperation.getResult());	  	 
                
        return scheduledOperationDTO;
	}
	/**
     * This method transforms an iterable collection of ScheduledOperation objects to an iterable collection of ScheduledOperationDTO objects.
     * 
     * @param scheduledOperations 		the iterable collection of ScheduledOperation objects.
     * @return scheduledOperationsDTO		the iterable collection of ScheduledOperationDTO objects
     */
	private Iterable<ScheduledOperationDTO> transformScheduledOperations2DTOs(Iterable<ScheduledOperation> scheduledOperations) {
		final Collection<ScheduledOperationDTO> scheduledOperationsDTO = new ArrayList<>();
        for (Iterator<ScheduledOperation> iterator = scheduledOperations.iterator(); iterator.hasNext();) {
            final ScheduledOperation scheduledOperation = (ScheduledOperation) iterator.next();
            scheduledOperationsDTO.add(transformScheduledOperation2DTO(scheduledOperation));
        }
		
        return scheduledOperationsDTO;
	}
	/**
     * This method checks the validity of the identifier. 
     * 
     * @param id	the identifier.
     * @throws CheckException is thrown if an error occurs during checking.
     */
	private void checkId(final long id) throws CheckException {
    	if ( id == 0)
    		throw new CheckException("Id should not be 0");    	
    }

}

