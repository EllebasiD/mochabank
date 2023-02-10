package com.externalbank.otherbank.domain.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.externalbank.otherbank.domain.dao.AccountRepository;
import com.externalbank.otherbank.domain.dao.OperationRepository;
import com.externalbank.otherbank.domain.dto.OperationDTO;
import com.externalbank.otherbank.domain.model.Account;
import com.externalbank.otherbank.domain.model.Operation;
import com.externalbank.otherbank.exception.CheckException;
import com.externalbank.otherbank.exception.CreateException;
import com.externalbank.otherbank.exception.DuplicateKeyException;
import com.externalbank.otherbank.exception.FinderException;
import com.externalbank.otherbank.logging.Trace;

/**
* This class is a facade for all Operation services.
* 
* @author Isabelle Deligniere
*
*/

@Service
@Transactional
public class OperationServiceImpl extends TransactionServiceImpl implements OperationService {
	// ======================================
    // =             Attributes             =
    // ======================================
    
	// Used for logging
    protected final transient String _cname = this.getClass().getName();
    
    @Autowired
    private OperationRepository operationRepository;  
    
    @Autowired
    private AccountRepository accountRepository;
    

    // ======================================
    // =            Constructors            =
    // ======================================
    public OperationServiceImpl() {}

	// ======================================
    // =           Business methods         =
    // ======================================
    /**
     * This method creates an Operation.
     * 
     * @param operationDTO 	the operation to create.
     * @return operationDTO	the created operation.
     * @throws CreateException is thrown if an error occurs during creation.
     * @throws CheckException is thrown if an error occurs during checking.
     */
	@Override
	@Transactional
	public OperationDTO createOperation(OperationDTO operationDTO) throws CreateException, CheckException {
		final String mname = "createOperation";
	    Trace.entering(_cname, mname, operationDTO);

	    if (operationDTO == null)
	        throw new CreateException("Operation object is null");
	    // Checks if Operation already exists
	    try {
			if(findOperation(operationDTO.getId())!=null)
				throw new DuplicateKeyException();
		} catch (FinderException  | CheckException e) {}
	        
	    Account accountA = null;
	    Account accountB = null;
	        
	    // Find the AccountA
	    if(accountRepository.findById(operationDTO.getAccountAId()).isPresent()) {
	       	accountA = accountRepository.findById(operationDTO.getAccountAId()).get();
	    }else {
			throw new CreateException("AccountA must exist to create an operation");
		}
	    // Find the AccountB
	    if(operationDTO.getAccountBId() != null) {
	      	if(accountRepository.findById(operationDTO.getAccountBId()).isPresent()) {
		       	accountB = accountRepository.findById(operationDTO.getAccountBId()).get();
		    }else {
				throw new CreateException("AccountB must exist to create an operation");
			}
	    }
	        
	    try {
			if(findOperation(operationDTO.getId())!=null)
				 throw new DuplicateKeyException();
		} catch (FinderException  | CheckException e) {}

	    // Transforms Operation DTO into domain object
	    Operation operation = null;
	    if(accountB ==null) {
	       	operation = new Operation(operationDTO.getDescription(), operationDTO.getType(), 
	       			accountA, operationDTO.getAmount(), operationDTO.getResult());
	    }else {
	       	operation = new Operation(operationDTO.getDescription(), operationDTO.getType(), 
	       			accountA, accountB, operationDTO.getAmount(), operationDTO.getResult());
        }
	     	
        operation.checkData();
	    	
        // Creates the object
        operationRepository.save(operation);
        // Transforms domain object into DTO
        final OperationDTO result = transformOperation2DTO(operation);

        Trace.exiting(_cname, mname, result);
	 
        return result;
	}
	/**
     * This method finds an operation by its identifier.
     * 
     * @param operationId		the identifier of the operation.
     * @throws FinderException is thrown if an error occurs during searching.
     * @throws CheckException is thrown if an error occurs during checking.
     * @return operationDTO		the operation.
     */
	@Override
	@Transactional(readOnly=true)
	public OperationDTO findOperation(long operationId) throws FinderException, CheckException {
		final String mname = "findOperation";
	    Trace.entering(_cname, mname, operationId);

	    checkId(operationId);
	    	
	    // Finds the object
	    Operation operation = null;
	    if( ! operationRepository.findById(operationId).isPresent())
	       	throw new FinderException("Operation must exist to be found");
	    else 
	       	operation = operationRepository.findById(operationId).get();

	    // Transforms domain object into DTO
	    final OperationDTO operationDTO = transformOperation2DTO(operation);

	    Trace.exiting(_cname, mname, operationDTO);
	        
	    return operationDTO;
	}
	/**
     * This method finds all the operations.
     * 
     * @return operationsDTO	all the operations.
     * @throws FinderException is thrown if an error occurs during searching.
     */
	@Override
	@Transactional(readOnly=true)
	public Iterable<OperationDTO> findOperations() throws FinderException {
		final String mname = "findOperations";
        Trace.entering(_cname, mname);

        // Finds all the objects
        final Iterable<Operation> operations = operationRepository.findAll();

        // Transforms domain objects into DTOs
        Iterable<OperationDTO> operationsDTO = transformOperations2DTOs(operations);
        
        Integer integer = Integer.valueOf(((Collection<OperationDTO>) operationsDTO).size());
        Trace.exiting(_cname, mname, integer);
        return operationsDTO;
	}
    
	
	
	// ======================================
    // =          Private Methods           =
    // ======================================
    /**
     * This method transforms an Operation object to an OperationDTO object.
     * 
     * @param operation		the Operation object.
     * @return operationDTO	the OperationDTO object.
     */
	private OperationDTO transformOperation2DTO(Operation operation) {
		final OperationDTO operationDTO = new OperationDTO();		
		operationDTO.setId(operation.getId());
		operationDTO.setTransactionDate(operation.getTransactionDate());
		operationDTO.setDescription(operation.getDescription());
		operationDTO.setType(operation.getType());		
		operationDTO.setAccountAId(operation.getAccountA().getId());
		if(operation.getAccountB() != null) {
			operationDTO.setAccountBId(operation.getAccountB().getId());
		}
		operationDTO.setAmount(operation.getAmount());
		operationDTO.setResult(operation.getResult());	  	 
                
        return operationDTO;
	}
	/**
     * This method transforms an iterable collection of Operation objects to an iterable collection of OperationDTO objects.
     * 
     * @param operations 		the iterable collection of Operation objects.
     * @return operationsDTO		the iterable collection of OperationDTO objects
     */
	private Iterable<OperationDTO> transformOperations2DTOs(Iterable<Operation> operations) {
		final Collection<OperationDTO> operationsDTO = new ArrayList<>();
        for (Iterator<Operation> iterator = operations.iterator(); iterator.hasNext();) {
            final Operation operation = (Operation) iterator.next();
            operationsDTO.add(transformOperation2DTO(operation));
        }
		
        return operationsDTO;
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
