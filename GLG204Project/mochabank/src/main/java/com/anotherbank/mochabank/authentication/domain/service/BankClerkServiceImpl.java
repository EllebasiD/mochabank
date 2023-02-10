package com.anotherbank.mochabank.authentication.domain.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anotherbank.mochabank.authentication.domain.dao.BankClerkRepository;
import com.anotherbank.mochabank.authentication.domain.dao.RoleRepository;
import com.anotherbank.mochabank.authentication.domain.dto.BankClerkDTO;
import com.anotherbank.mochabank.authentication.domain.model.BankClerk;
import com.anotherbank.mochabank.exception.CheckException;
import com.anotherbank.mochabank.exception.CreateException;
import com.anotherbank.mochabank.exception.DuplicateKeyException;
import com.anotherbank.mochabank.exception.FinderException;
import com.anotherbank.mochabank.exception.RemoveException;
import com.anotherbank.mochabank.exception.UpdateException;
import com.anotherbank.mochabank.logging.Trace;

/**
* This class is a facade for all bank clerk services.
* 
* @author Isabelle Deligniere
*/

@Service
@Transactional
public class BankClerkServiceImpl extends UserServiceImpl implements BankClerkService{
	// ======================================
    // =             Attributes             =
    // ======================================
    
	// Used for logging
    protected final transient String _cname = this.getClass().getName();
    
    @Autowired
    private BankClerkRepository bankClerkRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
        
    

    // ======================================
    // =            Constructors            =
    // ======================================
    public BankClerkServiceImpl() {}

	// ======================================
    // =           Business methods         =
    // ======================================
    /**
     * This method creates a BankClerk.
     * 
     * @param bankClerkDTO 	the bank clerk.
     * @throws CreateException is thrown if an error occurs during creation.
     * @throws CheckException is thrown if an error occurs during checking.
     */
	@Override
	@Transactional
	public BankClerkDTO createBankClerk(BankClerkDTO bankClerkDTO) throws CreateException, CheckException {
		final String mname = "createBankClerk";
	    Trace.entering(_cname, mname, bankClerkDTO);

	    if (bankClerkDTO == null)
	        throw new CreateException("Bank clerk object is null");
	    // Checks if BankClerk already exists
	    try {
	    	if(findBankClerk(bankClerkDTO.getUsername())!=null)
				 throw new DuplicateKeyException();
		} catch (FinderException  | CheckException e) {}
	             
	    // Transforms DTO into domain object
	    final BankClerk bankClerk = new BankClerk(bankClerkDTO.getUsername(), bankClerkDTO.getFirstname(), bankClerkDTO.getLastname());
	    // Sets the role to user
	    bankClerk.setRole(roleRepository.findByName(bankClerkDTO.getRoleName()));
	        
	    bankClerk.setEmail(bankClerkDTO.getEmail());
	        
	    // Checks the length of the password,  applies the hashing function, then sets the password
        if(bankClerkDTO.getPassword().length() >3) {
        	bankClerk.setPassword(bCryptPasswordEncoder.encode(bankClerkDTO.getPassword()));
    	}else {
    		throw new CreateException("Password is too short") ;
    	}	    	       
	     	        	        
	    bankClerk.checkData();
	    	
	    // Creates the object
	    bankClerkRepository.save(bankClerk);
	    // Transforms domain object into DTO
	    final BankClerkDTO result = transformBankClerk2DTO(bankClerk);

	    Trace.exiting(_cname, mname, result);
	 
	    return result;
	}
	/**
     * This method finds a bank clerk by his identifier.
     * 
     * @param bankClerkId		the identifier of the customer.
     * @throws FinderException is thrown if an error occurs during searching.
     * @throws CheckException is thrown if an error occurs during checking.
     * @return bankClerkDTO		the bank clerk.
     */
	@Override
	@Transactional(readOnly=true)
	public BankClerkDTO findBankClerk(String bankClerkId) throws FinderException, CheckException {
		final String mname = "findBankClerk";
	    Trace.entering(_cname, mname, bankClerkId);

	    checkId(bankClerkId);
	    	
	    // Finds the object
	    BankClerk bankClerk = null;
	    if( ! bankClerkRepository.findById(bankClerkId).isPresent())
	       	throw new FinderException("Bank clerk must exist to be found");
	    else 
	       	bankClerk = bankClerkRepository.findById(bankClerkId).get();

	    // Transforms domain object into DTO
	    final BankClerkDTO bankClerkDTO = transformBankClerk2DTO(bankClerk);

	    Trace.exiting(_cname, mname, bankClerkDTO);
	        
	    return bankClerkDTO;
	}
	/**
     * This method deletes a bank clerk giving his identifier.
     * 
     * @param bankClerkId	the identifier of the customer.
     * @throws RemoveException is thrown if an error occurs during removing.
     * @throws CheckException is thrown if an error occurs during checking.
     */
	@Override
	@Transactional
	public void deleteBankClerk(String bankClerkId) throws RemoveException, CheckException {
		final String mname = "deleteBankClerk";
        Trace.entering(_cname, mname, bankClerkId);

    	checkId(bankClerkId);
    	
    	BankClerk bankClerk;
        // Checks if the object exists
        if( ! bankClerkRepository.findById(bankClerkId).isPresent())
        	throw new RemoveException("Bank clerk must exist to be deleted");
        else 
        	bankClerk=bankClerkRepository.findById(bankClerkId).get();
        // Deletes the object
        bankClerkRepository.delete(bankClerk);
            	
	}
	 /**
     * This method updates a bank clerk.
     * 
     * @param bankClerkDTO	the bank clerk.
     * @throws UpdateException is thrown if an error occurs during updating.
     * @throws CheckException is thrown if an error occurs during checking.
     */
	@Override
	public void updateBankClerk(BankClerkDTO bankClerkDTO) throws UpdateException, CheckException {
		final String mname = "updateBankClerk";
        Trace.entering(_cname, mname, bankClerkDTO);

        if (bankClerkDTO == null)
            throw new UpdateException("Bank clerk object is null");

    	checkId(bankClerkDTO.getUsername());

    	final BankClerk bankClerk;

        // Checks if the object exists
    	if (!bankClerkRepository.findById(bankClerkDTO.getUsername()).isPresent()) 
        	throw new UpdateException("Bank clerk must exist to be deleted");
    	else
    		bankClerk = (BankClerk)bankClerkRepository.findById(bankClerkDTO.getUsername()).get();
    	
        // Transforms DTO into domain object
	    // Sets identity
	    bankClerk.setFirstname(bankClerkDTO.getFirstname());
	    bankClerk.setLastname(bankClerkDTO.getLastname());
	    bankClerk.setUsername(bankClerkDTO.getUsername());
	    
        // Sets the address
	    bankClerk.setEmail(bankClerkDTO.getEmail());
    	
        // Checks the length of the password,  applies the hashing function, then sets the password
	    if(bankClerkDTO.getPassword().length()<4)
        	throw new UpdateException("password's length exception (mini. of 4 char. required)");
        bankClerk.setPassword(bCryptPasswordEncoder.encode(bankClerkDTO.getPassword()));	             
        
    	bankClerk.checkData();

        // Updates the object
    	bankClerkRepository.save(bankClerk);

	}
	/**
     * This method finds all the bankClerks.
     * 
     * @throws FinderException is thrown if an error occurs during searching.
     */
	@Override
	@Transactional(readOnly=true)
	public Iterable<BankClerkDTO> findBankClerks() throws FinderException {
		final String mname = "findBankClerks";
        Trace.entering(_cname, mname);

        // Finds all the objects
        final Iterable<BankClerk> bankClerks = bankClerkRepository.findAll();

        // Transforms domain objects into DTOs
        Iterable<BankClerkDTO> bankClerksDTO = transformBankClerks2DTOs(bankClerks);
        
        Integer integer = Integer.valueOf(((Collection<BankClerkDTO>) bankClerksDTO).size());
        Trace.exiting(_cname, mname, integer);
        return bankClerksDTO;
	}
    

	
	// ======================================
    // =          Private Methods           =
    // ======================================
    /**
     * This method transforms a BankClerk object to a BankClerkDTO object.
     * 
     * @param bankClerk		the BankClerk object.
     * @return bankClerkDTO	the BankClerkDTO object.
     */
	private BankClerkDTO transformBankClerk2DTO(final BankClerk bankClerk) {
		final BankClerkDTO bankClerkDTO = new BankClerkDTO();
		bankClerkDTO.setUsername(bankClerk.getUsername());
		bankClerkDTO.setFirstname(bankClerk.getFirstname());
		bankClerkDTO.setLastname(bankClerk.getLastname());
		bankClerkDTO.setPassword(bankClerk.getPassword());
		bankClerkDTO.setEmail(bankClerk.getEmail());
    	        
        if(bankClerk.getRole() != null) {
        	bankClerkDTO.setRoleId(bankClerk.getRole().getId());
        	bankClerkDTO.setRoleName(bankClerk.getRole().getName());
        }
        
        return bankClerkDTO;
	}
	/**
     * This method transforms an iterable collection of BankClerk objects to an iterable collection of BankClerkDTO objects.
     * 
     * @param bankClerks 		the iterable collection of BankClerk objects.
     * @return bankClerksDTO		the iterable collection of BankClerkDTO objects
     */
	private Iterable<BankClerkDTO> transformBankClerks2DTOs(final Iterable<BankClerk> bankClerks) {
		final Collection<BankClerkDTO> bankClerksDTO = new ArrayList<>();
        for (Iterator<BankClerk> iterator = bankClerks.iterator(); iterator.hasNext();) {
            final BankClerk bankClerk = (BankClerk) iterator.next();
            bankClerksDTO.add(transformBankClerk2DTO(bankClerk));
        }
		
        return bankClerksDTO;
	}
	/**
     * This method checks the validity of the identifier. 
     * 
     * @param id	the identifier.
     * @throws CheckException is thrown if an error occurs during checking.
     */
    private void checkId(final String username) throws CheckException {
    	if ( username == null || username.equals("") )
    		throw new CheckException("Id should not be null or empty");    	
    }

	
}
