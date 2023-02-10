package com.externalbank.otherbank.authentication.domain.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.externalbank.otherbank.authentication.domain.dao.ProspectCustomerRepository;
import com.externalbank.otherbank.authentication.domain.dto.ProspectCustomerDTO;
import com.externalbank.otherbank.authentication.domain.model.ProspectCustomer;
import com.externalbank.otherbank.authentication.domain.model.Role;
import com.externalbank.otherbank.exception.CheckException;
import com.externalbank.otherbank.exception.CreateException;
import com.externalbank.otherbank.exception.DuplicateKeyException;
import com.externalbank.otherbank.exception.FinderException;
import com.externalbank.otherbank.exception.RemoveException;
import com.externalbank.otherbank.exception.UpdateException;
import com.externalbank.otherbank.logging.Trace;

/**
* This class is a facade for all prospect customer services.
* 
* @author Isabelle Deligniere
*/

@Service
@Transactional
public class ProspectCustomerServiceImpl extends UserServiceImpl implements ProspectCustomerService{
	// ======================================
    // =             Attributes             =
    // ======================================
    
	// Used for logging
    protected final transient String _cname = this.getClass().getName();
    
    @Autowired
    private ProspectCustomerRepository prospectCustomerRepository;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
        
    

    // ======================================
    // =            Constructors            =
    // ======================================
    public ProspectCustomerServiceImpl() {}

	// ======================================
    // =           Business methods         =
    // ======================================
    /**
     * This method creates a ProspectCustomer.
     * 
     * @param prospectCustomerDTO 	the prospect customer.
     * @throws CreateException is thrown if an error occurs during creation.
     * @throws CheckException is thrown if an error occurs during checking.
     */
	@Override
	@Transactional
	public ProspectCustomerDTO createProspectCustomer(ProspectCustomerDTO prospectCustomerDTO) throws CreateException, CheckException {
		final String mname = "createProspectCustomer";
	    Trace.entering(_cname, mname, prospectCustomerDTO);

	    if (prospectCustomerDTO == null)
	        throw new CreateException("ProspectCustomer object is null");
	    // Checks if ProspectCustomer already exists
	    try {
			if(findProspectCustomer(prospectCustomerDTO.getUsername())!=null)
				 throw new DuplicateKeyException();
		} catch (FinderException | CheckException e) {}
	             
	    // Transforms DTO into domain object
	    final ProspectCustomer prospectCustomer = new ProspectCustomer(prospectCustomerDTO.getUsername(), prospectCustomerDTO.getFirstname(), prospectCustomerDTO.getLastname());
	    // Sets the role to user
	    Role role = new Role();
	    role.setId(3);
	    role.setName("ROLE_PROSPECTCUSTOMER");
	    prospectCustomer.setRole(role);
	    // Sets the address
	    prospectCustomer.setCity(prospectCustomerDTO.getCity());
	    prospectCustomer.setBirthdate(prospectCustomerDTO.getBirthdate());
	    prospectCustomer.setStreet1(prospectCustomerDTO.getStreet1());
	    prospectCustomer.setStreet2(prospectCustomerDTO.getStreet2());
	    prospectCustomer.setPhoneNumber(prospectCustomerDTO.getPhoneNumber());
	    prospectCustomer.setZipcode(prospectCustomerDTO.getZipcode());
	    prospectCustomer.setEmail(prospectCustomerDTO.getEmail());
	    // Sets the company if there is one
	    if(prospectCustomerDTO.getCompany() != null){
	       	prospectCustomerDTO.setCompany(prospectCustomerDTO.getCompany());
	    }
	        
	    // Checks the length of the password,  applies the hashing function, then sets the password
	    if(prospectCustomerDTO.getPassword().length() >3) {
	       	prospectCustomer.setPassword(bCryptPasswordEncoder.encode(prospectCustomerDTO.getPassword()));
	    }else {
	    	throw new CreateException("Password is too short") ;
	    }	    	       
	     	        	        
	    prospectCustomer.checkData();
	    	
	    // Creates the object
	    prospectCustomerRepository.save(prospectCustomer);
	    // Transforms domain object into DTO
	    final ProspectCustomerDTO result = transformProspectCustomer2DTO(prospectCustomer);

	    Trace.exiting(_cname, mname, result);
	 
	    return result;
	}
	/**
     * This method finds a prospect customer by his identifier.
     * 
     * @param prospectCustomerId		the identifier of the prospect customer.
     * @throws FinderException is thrown if an error occurs during searching.
     * @throws CheckException is thrown if an error occurs during checking.
     * @return prospectCustomerDTO		the prospect customer.
     */
	@Override
	@Transactional(readOnly=true)
	public ProspectCustomerDTO findProspectCustomer(String prospectCustomerId) throws FinderException, CheckException {
		final String mname = "findProspectCustomer";
	    Trace.entering(_cname, mname, prospectCustomerId);

	    checkId(prospectCustomerId);
	    	
	    // Finds the object
	    ProspectCustomer prospectCustomer = null;
	    if( ! prospectCustomerRepository.findById(prospectCustomerId).isPresent())
	       	throw new FinderException("Prospect Customer must exist to be found");
	    else 
	       	prospectCustomer = prospectCustomerRepository.findById(prospectCustomerId).get();

	    // Transforms domain object into DTO
	    final ProspectCustomerDTO prospectCustomerDTO = transformProspectCustomer2DTO(prospectCustomer);

	    Trace.exiting(_cname, mname, prospectCustomerDTO);
	        
	    return prospectCustomerDTO;
	}
	/**
     * This method deletes a prospect customer giving his identifier.
     * 
     * @param prospectCustomerId	the identifier of the prospect customer.
     * @throws RemoveException is thrown if an error occurs during removing.
     * @throws CheckException is thrown if an error occurs during checking.
     */
	@Override
	@Transactional
	public void deleteProspectCustomer(String prospectCustomerId) throws RemoveException, CheckException {
		final String mname = "deleteProspectCustomer";
        Trace.entering(_cname, mname, prospectCustomerId);

    	checkId(prospectCustomerId);
    	
    	ProspectCustomer prospectCustomer;
        // Checks if the object exists
        if( ! prospectCustomerRepository.findById(prospectCustomerId).isPresent())
        	throw new RemoveException("ProspectCustomer must exist to be deleted");
        else 
        	prospectCustomer = prospectCustomerRepository.findById(prospectCustomerId).get();
        // Deletes the object
        prospectCustomerRepository.delete(prospectCustomer);
            	
	}
	 /**
     * This method updates a prospect customer.
     * 
     * @param prospectCustomerDTO	the prospect customer.
     * @throws UpdateException is thrown if an error occurs during updating.
     * @throws CheckException is thrown if an error occurs during checking.
     */
	@Override
	public void updateProspectCustomer(ProspectCustomerDTO prospectCustomerDTO) throws UpdateException, CheckException {
		final String mname = "updateProspectCustomer";
        Trace.entering(_cname, mname, prospectCustomerDTO);

        if (prospectCustomerDTO == null)
            throw new UpdateException("ProspectCustomer object is null");

    	checkId(prospectCustomerDTO.getUsername());

    	final ProspectCustomer prospectCustomer;

        // Checks if the object exists
    	if (!prospectCustomerRepository.findById(prospectCustomerDTO.getUsername()).isPresent()) 
        	throw new UpdateException("ProspectCustomer must exist to be deleted");
    	else
    		prospectCustomer = (ProspectCustomer)prospectCustomerRepository.findById(prospectCustomerDTO.getUsername()).get();
    	
        // Transforms DTO into domain object
        //	Sets the role to prospect customer
	    Role role = new Role();
	    role.setId(3);
	    role.setName("ROLE_PROSPECTCUSTOMER");
	    prospectCustomer.setRole(role);
	    // Sets identity
	    prospectCustomer.setFirstname(prospectCustomerDTO.getFirstname());
	    prospectCustomer.setLastname(prospectCustomerDTO.getLastname());
	    prospectCustomer.setUsername(prospectCustomerDTO.getUsername());
	    prospectCustomer.setBirthdate(prospectCustomerDTO.getBirthdate());
        // Sets the address
	    prospectCustomer.setCity(prospectCustomerDTO.getCity());
	    prospectCustomer.setStreet1(prospectCustomerDTO.getStreet1());
	    prospectCustomer.setStreet2(prospectCustomerDTO.getStreet2());
	    prospectCustomer.setPhoneNumber(prospectCustomerDTO.getPhoneNumber());
	    prospectCustomer.setZipcode(prospectCustomerDTO.getZipcode());
	    prospectCustomer.setEmail(prospectCustomerDTO.getEmail());
	    // Sets the company if there is one
	    if(prospectCustomerDTO.getCompany() != null){
        	prospectCustomer.setCompany(prospectCustomerDTO.getCompany());
        }
	    
        // Checks the length of the password,  applies the hashing function, then sets the password
    	if(prospectCustomerDTO.getPassword().length() >3) {
    		prospectCustomer.setPassword(bCryptPasswordEncoder.encode(prospectCustomerDTO.getPassword()));
    	}else {
    		throw new UpdateException("Password is too short") ;
    	}            
        
    	prospectCustomer.checkData();

        // Updates the object
    	prospectCustomerRepository.save(prospectCustomer);

	}
	/**
     * This method finds all the prospect customers.
     * 
     * @throws FinderException is thrown if an error occurs during searching.
     */
	@Override
	@Transactional(readOnly=true)
	public Iterable<ProspectCustomerDTO> findProspectCustomers() throws FinderException {
		final String mname = "findProspectCustomers";
        Trace.entering(_cname, mname);

        // Finds all the objects
        final Iterable<ProspectCustomer> prospectCustomers = prospectCustomerRepository.findAll();

        // Transforms domain objects into DTOs
        Iterable<ProspectCustomerDTO> prospectCustomersDTO = transformProspectCustomers2DTOs(prospectCustomers);
        
        Integer integer = Integer.valueOf(((Collection<ProspectCustomerDTO>) prospectCustomersDTO).size());
        Trace.exiting(_cname, mname, integer);
        return prospectCustomersDTO;
	}
    

	
	// ======================================
    // =          Private Methods           =
    // ======================================
    /**
     * This method transforms a ProspectCustomer object to a ProspectCustomerDTO object.
     * 
     * @param prospectCustomer		the ProspectCustomer object.
     * @return prospectCustomerDTO	the ProspectCCustomerDTO object.
     */
	private ProspectCustomerDTO transformProspectCustomer2DTO(ProspectCustomer prospectCustomer) {
		final ProspectCustomerDTO prospectCustomerDTO = new ProspectCustomerDTO();
		prospectCustomerDTO.setUsername(prospectCustomer.getUsername());
		prospectCustomerDTO.setFirstname(prospectCustomer.getFirstname());
		prospectCustomerDTO.setLastname(prospectCustomer.getLastname());
		prospectCustomerDTO.setPassword(prospectCustomer.getPassword());
		prospectCustomerDTO.setBirthdate(prospectCustomer.getBirthdate());
		
        if(prospectCustomer.getAddress() != null) {
        	prospectCustomerDTO.setCity(prospectCustomer.getCity());
        	
        	prospectCustomerDTO.setEmail(prospectCustomer.getEmail());
        	prospectCustomerDTO.setLastname(prospectCustomer.getLastname());
        	prospectCustomerDTO.setStreet1(prospectCustomer.getStreet1());
        	prospectCustomerDTO.setStreet2(prospectCustomer.getStreet2());
        	prospectCustomerDTO.setPhoneNumber(prospectCustomer.getPhoneNumber());
        	prospectCustomerDTO.setZipcode(prospectCustomer.getZipcode());
        }
        // Sets the company if there is one
        if(prospectCustomer.getCompany() != null){
        	prospectCustomerDTO.setCompany(prospectCustomer.getCompany());
        }
        
        if(prospectCustomer.getRole() != null) {
        	prospectCustomerDTO.setRoleId(prospectCustomer.getRole().getId());
        	prospectCustomerDTO.setRoleName(prospectCustomer.getRole().getName());
        }
        
        return prospectCustomerDTO;
	}
	/**
     * This method transforms an iterable collection of ProspectCustomer objects to an iterable collection of ProspectCustomerDTO objects.
     * 
     * @param prospectCustomers 		the iterable collection of ProspectCustomer objects.
     * @return prospectCustomersDTO		the iterable collection of ProspectCustomerDTO objects
     */
	private Iterable<ProspectCustomerDTO> transformProspectCustomers2DTOs(Iterable<ProspectCustomer> prospectCustomers) {
		final Collection<ProspectCustomerDTO> prospectCustomersDTO = new ArrayList<>();
        for (Iterator<ProspectCustomer> iterator = prospectCustomers.iterator(); iterator.hasNext();) {
            final ProspectCustomer prospectCustomer = (ProspectCustomer) iterator.next();
            prospectCustomersDTO.add(transformProspectCustomer2DTO(prospectCustomer));
        }
		
        return prospectCustomersDTO;
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


