package com.anotherbank.mochabank.authentication.domain.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anotherbank.mochabank.authentication.domain.dao.CustomerRepository;
import com.anotherbank.mochabank.authentication.domain.dao.RoleRepository;
import com.anotherbank.mochabank.authentication.domain.dto.CustomerDTO;
import com.anotherbank.mochabank.authentication.domain.model.Customer;
import com.anotherbank.mochabank.domain.dao.AccountRepository;
import com.anotherbank.mochabank.domain.model.Account;
import com.anotherbank.mochabank.exception.CheckException;
import com.anotherbank.mochabank.exception.CreateException;
import com.anotherbank.mochabank.exception.DuplicateKeyException;
import com.anotherbank.mochabank.exception.FinderException;
import com.anotherbank.mochabank.exception.RemoveException;
import com.anotherbank.mochabank.exception.UpdateException;
import com.anotherbank.mochabank.logging.Trace;

/**
* This class is a facade for all customer services.
* 
* @author Isabelle Deligniere
*/

@Service
@Transactional
public class CustomerServiceImpl extends UserServiceImpl implements CustomerService{
	// ======================================
    // =             Attributes             =
    // ======================================
    
	// Used for logging
    protected final transient String _cname = this.getClass().getName();
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
        
    

    // ======================================
    // =            Constructors            =
    // ======================================
    public CustomerServiceImpl() {}

	// ======================================
    // =           Business methods         =
    // ======================================
    /**
     * This method creates a Customer.
     * 
     * @param customerDTO 	the customer.
     * @throws CreateException is thrown if an error occurs during creation.
     * @throws CheckException is thrown if an error occurs during checking.
     */
	@Override
	@Transactional
	public CustomerDTO createCustomer(CustomerDTO customerDTO) throws CreateException, CheckException {
		final String mname = "createCustomer";
	    Trace.entering(_cname, mname, customerDTO);

	    if (customerDTO == null)
	        throw new CreateException("Customer object is null");
	    // Checks if Customer already exists
	    try {
			if(findCustomer(customerDTO.getUsername())!=null)
				 throw new DuplicateKeyException();
		} catch (FinderException | CheckException e) {}
	             
	    // Transforms DTO into domain object
	    final Customer customer = new Customer(customerDTO.getUsername(), customerDTO.getFirstname(), customerDTO.getLastname());
	    // Sets the role to user
		customer.setRole(roleRepository.findByName(customerDTO.getRoleName()));
	    // Sets the informations
	    customer.setPhoneNumber(customerDTO.getPhoneNumber());
	    customer.setEmail(customerDTO.getEmail());
	    customer.setBirthdate(customerDTO.getBirthdate());
	    customer.setStreet1(customerDTO.getStreet1());
	    customer.setStreet2(customerDTO.getStreet2());
	    customer.setCity(customerDTO.getCity());
	    customer.setZipcode(customerDTO.getZipcode());
	    // Sets the company if there is one
	    if(customerDTO.getCompany() != null){
	      	customer.setCompany(customerDTO.getCompany());
	    }	        
	    
	    // Checks the length of the password,  applies the hashing function, then sets the password
        if(customerDTO.getPassword().length() >3) {
        	customer.setPassword(bCryptPasswordEncoder.encode(customerDTO.getPassword()));
    	}else {
    		throw new CreateException("Password is too short") ;
    	}   	       
	     	        	        
	    customer.checkData();
	    	
	    // Creates the object
	    customerRepository.save(customer);
	    // Transforms domain object into DTO
	    final CustomerDTO result = transformCustomer2DTO(customer);

	    Trace.exiting(_cname, mname, result);
	 
	    return result;
	}
	/**
     * This method finds a customer by his identifier.
     * 
     * @param customerId		the identifier of the customer.
     * @throws FinderException is thrown if an error occurs during searching.
     * @throws CheckException is thrown if an error occurs during checking.
     * @return customerDTO		the customer.
     */
	@Override
	@Transactional(readOnly=true)
	public CustomerDTO findCustomer(String customerId) throws FinderException, CheckException {
		final String mname = "findCustomer";
	    Trace.entering(_cname, mname, customerId);

	    checkId(customerId);
	    	
	    // Finds the object
	    Customer customer = null;
	    if( ! customerRepository.findById(customerId).isPresent())
	       	throw new FinderException("Customer must exist to be found");
	    else 
	       	customer = customerRepository.findById(customerId).get();

	    // Transforms domain object into DTO
	    final CustomerDTO customerDTO = transformCustomer2DTO(customer);

	    Trace.exiting(_cname, mname, customerDTO);
	        
	    return customerDTO;
	}
	/**
     * This method finds a customer by his account identifier.
     * 
     * @param accountId		the identifier of the customer account.
     * @throws FinderException is thrown if an error occurs during searching.
     * @throws CheckException is thrown if an error occurs during checking.
     * @return customerDTO		the customer.
     */
	@Override
	@Transactional(readOnly=true)	
	public CustomerDTO findCustomerByAccountId(final long accountId) throws FinderException, CheckException {
		final String mname = "findCustomerByAccountId";
        Trace.entering(_cname, mname, accountId);
        
        // Finds the object
        Account account = null;
        if( ! accountRepository.findById(accountId).isPresent())
        	throw new FinderException("Account must exist to be found");
        else 
        	account = accountRepository.findById(accountId).get();
        
    	Customer customer = customerRepository.findByAccount(account);

        // Transforms domain object into DTO
        final CustomerDTO customerDTO = transformCustomer2DTO(customer);

        Trace.exiting(_cname, mname, customerDTO);        
                		
		return customerDTO;
	}
	/**
     * This method deletes a customer giving his identifier.
     * 
     * @param customerId	the identifier of the customer.
     * @throws RemoveException is thrown if an error occurs during removing.
     * @throws CheckException is thrown if an error occurs during checking.
     */
	@Override
	@Transactional
	public void deleteCustomer(String customerId) throws RemoveException, CheckException {
		final String mname = "deleteCustomer";
        Trace.entering(_cname, mname, customerId);

    	checkId(customerId);
    	
    	Customer customer;
        // Checks if the object exists
        if( ! customerRepository.findById(customerId).isPresent())
        	throw new RemoveException("Customer must exist to be deleted");
        else 
        	customer=customerRepository.findById(customerId).get();
        // Deletes the object
        customerRepository.delete(customer);
            	
	}
	 /**
     * This method updates a customer.
     * 
     * @param customerDTO	the customer.
     * @throws UpdateException is thrown if an error occurs during updating.
     * @throws CheckException is thrown if an error occurs during checking.
     */
	@Override
	public void updateCustomer(CustomerDTO customerDTO) throws UpdateException, CheckException {
		final String mname = "updateCustomer";
        Trace.entering(_cname, mname, customerDTO);

        if (customerDTO == null)
            throw new UpdateException("Customer object is null");

    	checkId(customerDTO.getUsername());

    	final Customer customer;

        // Checks if the object exists
    	if (!customerRepository.findById(customerDTO.getUsername()).isPresent()) 
        	throw new UpdateException("Customer must exist to be deleted");
    	else
    		customer = (Customer)customerRepository.findById(customerDTO.getUsername()).get();
    	
        // Transforms DTO into domain object
	    // Sets identity
	    customer.setFirstname(customerDTO.getFirstname());
	    customer.setLastname(customerDTO.getLastname());
	    customer.setUsername(customerDTO.getUsername());
	    customer.setBirthdate(customerDTO.getBirthdate());
        // Sets the address
	    customer.setCity(customerDTO.getCity());
	    customer.setStreet1(customerDTO.getStreet1());
	    customer.setStreet2(customerDTO.getStreet2());
	    customer.setZipcode(customerDTO.getZipcode());
	    customer.setEmail(customerDTO.getEmail());
	    customer.setPhoneNumber(customerDTO.getPhoneNumber());
    	// Sets the company if there is one
	    if(customerDTO.getCompany() != null){
        	customer.setCompany(customerDTO.getCompany());
        }
	    
        // Checks the length of the password,  applies the hashing function, then sets the password
	    if(customerDTO.getPassword().length()<4)
        	throw new UpdateException("password's length exception (mini. of 4 char. required)");
        customer.setPassword(bCryptPasswordEncoder.encode(customerDTO.getPassword()));      
        
    	customer.checkData();

        // Updates the object
    	customerRepository.save(customer);

	}
	/**
     * This method finds all the customers.
     * 
     * @throws FinderException is thrown if an error occurs during searching.
     */
	@Override
	@Transactional(readOnly=true)
	public Iterable<CustomerDTO> findCustomers() throws FinderException {
		final String mname = "findCustomers";
        Trace.entering(_cname, mname);

        // Finds all the objects
        final Iterable<Customer> customers = customerRepository.findAll();

        // Transforms domain objects into DTOs
        Iterable<CustomerDTO> customersDTO = transformCustomers2DTOs(customers);
        
        Integer integer = Integer.valueOf(((Collection<CustomerDTO>) customersDTO).size());
        Trace.exiting(_cname, mname, integer);
        return customersDTO;
	}
    

	
	// ======================================
    // =          Private Methods           =
    // ======================================
    /**
     * This method transforms a Customer object to a CustomerDTO object.
     * 
     * @param customer		the Customer object.
     * @return customerDTO	the CustomerDTO object.
     */
	private CustomerDTO transformCustomer2DTO(Customer customer) {
		final CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setUsername(customer.getUsername());
		customerDTO.setFirstname(customer.getFirstname());
		customerDTO.setLastname(customer.getLastname());
		customerDTO.setPassword(customer.getPassword());
		customerDTO.setBirthdate(customer.getBirthdate());
    	customerDTO.setEmail(customer.getEmail());
    	customerDTO.setPhoneNumber(customer.getPhoneNumber());
		
        if(customer.getAddress() != null) {
        	customerDTO.setStreet1(customer.getStreet1());
        	customerDTO.setStreet2(customer.getStreet2());
        	customerDTO.setCity(customer.getCity());
        	customerDTO.setZipcode(customer.getZipcode());
        }
        // Sets the company if there is one
        if(customer.getCompany() != null){
        	customerDTO.setCompany(customer.getCompany());
        }
        
        if(customer.getRole() != null) {
        	customerDTO.setRoleId(customer.getRole().getId());
        	customerDTO.setRoleName(customer.getRole().getName());
        }
        
        return customerDTO;
	}
	/**
     * This method transforms an iterable collection of Customer objects to an iterable collection of CustomerDTO objects.
     * 
     * @param customers 		the iterable collection of Customer objects.
     * @return customersDTO		the iterable collection of CustomerDTO objects
     */
	private Iterable<CustomerDTO> transformCustomers2DTOs(Iterable<Customer> customers) {
		final Collection<CustomerDTO> customersDTO = new ArrayList<>();
        for (Iterator<Customer> iterator = customers.iterator(); iterator.hasNext();) {
            final Customer customer = (Customer) iterator.next();
            customersDTO.add(transformCustomer2DTO(customer));
        }
		
        return customersDTO;
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

