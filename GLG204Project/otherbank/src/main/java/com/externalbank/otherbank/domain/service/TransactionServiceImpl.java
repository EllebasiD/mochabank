package com.externalbank.otherbank.domain.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.externalbank.otherbank.domain.dao.TransactionRepository;
import com.externalbank.otherbank.domain.dto.TransactionDTO;
import com.externalbank.otherbank.domain.model.Transaction;
import com.externalbank.otherbank.exception.CheckException;
import com.externalbank.otherbank.exception.FinderException;
import com.externalbank.otherbank.logging.Trace;

/**
* This class is a facade for all Transaction services.
* 
* @author Isabelle Deligniere
*
*/

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {
	// ======================================
    // =             Attributes             =
    // ======================================
    
	// Used for logging
    protected final transient String _cname = this.getClass().getName();
    
    @Autowired
    private TransactionRepository transactionRepository; 

    // ======================================
    // =            Constructors            =
    // ======================================
    public TransactionServiceImpl() {}

	// ======================================
    // =           Business methods         =
    // ======================================

	/**
     * This method finds a transaction by its identifier.
     * 
     * @param transactionId		the identifier of the transaction.
     * @throws FinderException is thrown if an error occurs during searching.
     * @throws CheckException is thrown if an error occurs during checking.
     * @return transactionDTO		the transaction.
     */
	@Override
	@Transactional(readOnly=true)
	public TransactionDTO findTransaction(long transactionId) throws FinderException, CheckException {
		final String mname = "findTransaction";
	    Trace.entering(_cname, mname, transactionId);

	    checkId(transactionId);
	    	
	    // Finds the object
	    Transaction transaction = null;
	    if( ! transactionRepository.findById(transactionId).isPresent())
	       	throw new FinderException("Transaction must exist to be found");
	    else 
	       	transaction = transactionRepository.findById(transactionId).get();

	    // Transforms domain object into DTO
	    final TransactionDTO transactionDTO = transformTransaction2DTO(transaction);

	    Trace.exiting(_cname, mname, transactionDTO);
	        
	    return transactionDTO;
	}
	/**
     * This method finds all the transactions.
     * 
     * @throws FinderException is thrown if an error occurs during searching.
     */
	@Override
	@Transactional(readOnly=true)
	public Iterable<TransactionDTO> findTransactions() throws FinderException {
		final String mname = "findTransactions";
        Trace.entering(_cname, mname);

        // Finds all the objects
        final Iterable<Transaction> transactions = transactionRepository.findAll();

        // Transforms domain objects into DTOs
        Iterable<TransactionDTO> transactionsDTO = transformTransactions2DTOs(transactions);
        
        Integer integer = Integer.valueOf(((Collection<TransactionDTO>) transactionsDTO).size());
        Trace.exiting(_cname, mname, integer);
        return transactionsDTO;
	}
    

	
	// ======================================
    // =          Private Methods           =
    // ======================================
    /**
     * This method transforms a Transaction object to a TransactionDTO object.
     * 
     * @param transaction		the Transaction object.
     * @return transactionDTO	the TransactionDTO object.
     */
	private TransactionDTO transformTransaction2DTO(Transaction transaction) {
		final TransactionDTO transactionDTO = new TransactionDTO();		
		transactionDTO.setId(transaction.getId());
		transactionDTO.setTransactionDate(transaction.getTransactionDate());
		transactionDTO.setDescription(transaction.getDescription());
		transactionDTO.setType(transaction.getType());		
		transactionDTO.setAccountAId(transaction.getAccountA().getId());
		if(transaction.getAccountB() != null) {
			transactionDTO.setAccountBId(transaction.getAccountB().getId());
		}
		transactionDTO.setAmount(transaction.getAmount());
		transactionDTO.setResult(transaction.getResult()); 	 
                
        return transactionDTO;
	}
	/**
     * This method transforms an iterable collection of Transaction objects to an iterable collection of TransactionDTO objects.
     * 
     * @param transactions 		the iterable collection of Transaction objects.
     * @return transactionsDTO		the iterable collection of TransactionDTO objects
     */
	private Iterable<TransactionDTO> transformTransactions2DTOs(Iterable<Transaction> transactions) {
		final Collection<TransactionDTO> transactionsDTO = new ArrayList<>();
        for (Iterator<Transaction> iterator = transactions.iterator(); iterator.hasNext();) {
            final Transaction transaction = (Transaction) iterator.next();
            transactionsDTO.add(transformTransaction2DTO(transaction));
        }
		
        return transactionsDTO;
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
