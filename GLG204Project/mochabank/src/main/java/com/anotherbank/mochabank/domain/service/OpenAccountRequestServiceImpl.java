package com.anotherbank.mochabank.domain.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anotherbank.mochabank.authentication.domain.dto.ProspectCustomerDTO;
import com.anotherbank.mochabank.authentication.domain.model.ProspectCustomer;
import com.anotherbank.mochabank.domain.dao.OpenAccountRequestRepository;
import com.anotherbank.mochabank.domain.dto.OpenAccountRequestDTO;
import com.anotherbank.mochabank.domain.model.OpenAccountRequest;
import com.anotherbank.mochabank.exception.CheckException;
import com.anotherbank.mochabank.exception.CreateException;
import com.anotherbank.mochabank.exception.DuplicateKeyException;
import com.anotherbank.mochabank.exception.FinderException;
import com.anotherbank.mochabank.exception.RemoveException;
import com.anotherbank.mochabank.logging.Trace;

/**
* This class is a facade for all openAccountRequest services.
* 
* @author Isabelle Deligniere
*
*/

@Service
@Transactional
public class OpenAccountRequestServiceImpl implements OpenAccountRequestService {
	// ======================================
    // =             Attributes             =
    // ======================================
    
	// Used for logging
    protected final transient String _cname = this.getClass().getName();
    
    @Autowired
    private OpenAccountRequestRepository openAccountRequestRepository;      

    // ======================================
    // =            Constructors            =
    // ======================================
    public OpenAccountRequestServiceImpl() {}

	// ======================================
    // =           Business methods         =
    // ======================================
    /**
     * This method creates an OpenAccountRequest.
     * 
     * @param openAccountRequestDTO 	the openAccountRequest to create.
     * @return openAccountRequestDTO	the created openAccountRequestDTO.
     * 
     * @throws CreateException is thrown if an error occurs during creation.
     * @throws CheckException is thrown if an error occurs during checking.
     */
	@Override
	@Transactional
	public OpenAccountRequestDTO createOpenAccountRequest(OpenAccountRequestDTO openAccountRequestDTO) throws CreateException, CheckException {
		final String mname = "createOpenAccountRequest";
	    Trace.entering(_cname, mname, openAccountRequestDTO);

	    if (openAccountRequestDTO == null)
	        throw new CreateException("OpenAccountRequest object is null");
	    // Checks if OpenAccountRequest already exists
	    try {
	    	if(findOpenAccountRequest(openAccountRequestDTO.getId())!=null)
	    		throw new DuplicateKeyException();
		} catch (FinderException  | CheckException e) {}
	             
	    // Transforms DTO into domain object
	    OpenAccountRequest openAccountRequest = null;
	    if(openAccountRequestDTO.getProspectCustomer() != null) {
	       	ProspectCustomer prospectCustomer = new ProspectCustomer(openAccountRequestDTO.getProspectCustomer().getUsername(), openAccountRequestDTO.getProspectCustomer().getFirstname(), openAccountRequestDTO.getProspectCustomer().getLastname());
	       	openAccountRequest = new OpenAccountRequest(openAccountRequestDTO.getId(), prospectCustomer, openAccountRequestDTO.getAccountOffer());
	    }else {
	       	throw new CreateException("ProspectCustomer object is null");
	    }	        	        
	        
	    // Sets the date	         
	    Date today = Calendar.getInstance().getTime(); 
	    openAccountRequest.setOpenAccountRequestDate(today);
	        
	    // Sets the status
	    openAccountRequest.setAccepted(false);   
	     	
	    openAccountRequest.checkData();
	    	
	    // Creates the object
	    openAccountRequestRepository.save(openAccountRequest);
	    // Transforms domain object into DTO
	    final OpenAccountRequestDTO result = transformOpenAccountRequest2DTO(openAccountRequest);

	    Trace.exiting(_cname, mname, result);
	 
	    return result;
	}
	/**
     * This method finds an openAccountRequest by its identifier.
     * 
     * @param openAccountRequestId		the identifier of the openAccountRequest.
     * @return openAccountRequestDTO	the searched openAccountRequestDTO.
     * 
     * @throws FinderException is thrown if an error occurs during searching.
     * @throws CheckException is thrown if an error occurs during checking.
     * @return openAccountRequestDTO		the openAccountRequest.
     */
	@Override
	@Transactional(readOnly=true)
	public OpenAccountRequestDTO findOpenAccountRequest(String openAccountRequestId) throws FinderException, CheckException {
		final String mname = "findOpenAccountRequest";
	    Trace.entering(_cname, mname, openAccountRequestId);

	    checkId(openAccountRequestId);
	    	
	    // Finds the object
	    OpenAccountRequest openAccountRequest = null;
	    if( ! openAccountRequestRepository.findById(openAccountRequestId).isPresent())
	       	throw new FinderException("OpenAccountRequest must exist to be found");
	    else 
	       	openAccountRequest = openAccountRequestRepository.findById(openAccountRequestId).get();

	    // Transforms domain object into DTO
	    final OpenAccountRequestDTO openAccountRequestDTO = transformOpenAccountRequest2DTO(openAccountRequest);

	    Trace.exiting(_cname, mname, openAccountRequestDTO);
	        
	    return openAccountRequestDTO;
	}
	/**
     * This method deletes an openAccountRequest giving its identifier.
     * 
     * @param openAccountRequestId	the identifier of the openAccountRequest.
     * @throws RemoveException is thrown if an error occurs during removing.
     * @throws CheckException is thrown if an error occurs during checking.
     */
	@Override
	@Transactional
	public void deleteOpenAccountRequest(String openAccountRequestId) throws RemoveException, CheckException {
		final String mname = "deleteOpenAccountRequest";
        Trace.entering(_cname, mname, openAccountRequestId);

    	checkId(openAccountRequestId);
    	
    	OpenAccountRequest openAccountRequest;
        // Checks if the object exists
        if( ! openAccountRequestRepository.findById(openAccountRequestId).isPresent())
        	throw new RemoveException("OpenAccountRequest must exist to be deleted");
        else 
        	openAccountRequest=openAccountRequestRepository.findById(openAccountRequestId).get();
        // Deletes the object
        openAccountRequestRepository.delete(openAccountRequest);
            	
	}
	/**
     * This method finds all the openAccountRequests.
     * 
     * @return openAccountRequestDTOs	all the openAccountRequestDTO.
     * 
     * @throws FinderException is thrown if an error occurs during searching.
     */
	@Override
	@Transactional(readOnly=true)
	public Iterable<OpenAccountRequestDTO> findOpenAccountRequests() throws FinderException {
		final String mname = "findOpenAccountRequests";
        Trace.entering(_cname, mname);

        // Finds all the objects
        final Iterable<OpenAccountRequest> openAccountRequests = openAccountRequestRepository.findAll();

        // Transforms domain objects into DTOs
        Iterable<OpenAccountRequestDTO> openAccountRequestsDTO = transformOpenAccountRequests2DTOs(openAccountRequests);
        
        Integer integer = Integer.valueOf(((Collection<OpenAccountRequestDTO>) openAccountRequestsDTO).size());
        Trace.exiting(_cname, mname, integer);
        return openAccountRequestsDTO;
	}
    
	
	// ======================================
    // =          Private Methods           =
    // ======================================
    /**
     * This method transforms an OpenAccountRequest object to an OpenAccountRequestDTO object.
     * 
     * @param openAccountRequest		the OpenAccountRequest object.
     * @return openAccountRequestDTO	the OpenAccountRequestDTO object.
     */
	private OpenAccountRequestDTO transformOpenAccountRequest2DTO(OpenAccountRequest openAccountRequest) {
		final OpenAccountRequestDTO openAccountRequestDTO = new OpenAccountRequestDTO();		
		openAccountRequestDTO.setId(openAccountRequest.getId());
		openAccountRequestDTO.setOpenAccountRequestDate(openAccountRequest.getOpenAccountRequestDate());
		final ProspectCustomerDTO prospectCustomerDTO = new ProspectCustomerDTO(openAccountRequest.getProspectCustomer().getUsername(), openAccountRequest.getProspectCustomer().getFirstname(), openAccountRequest.getProspectCustomer().getLastname());
		openAccountRequestDTO.setProspectCustomer(prospectCustomerDTO);		
		openAccountRequestDTO.setAccountOffer(openAccountRequest.getAccountOffer());
    	openAccountRequest.setAccepted(false); 
                
        return openAccountRequestDTO;
	}
	/**
     * This method transforms an iterable collection of OpenAccountRequest objects to an iterable collection of OpenAccountRequestDTO objects.
     * 
     * @param openAccountRequests 		the iterable collection of OpenAccountRequest objects.
     * @return openAccountRequestsDTO		the iterable collection of OpenAccountRequestDTO objects
     */
	private Iterable<OpenAccountRequestDTO> transformOpenAccountRequests2DTOs(Iterable<OpenAccountRequest> openAccountRequests) {
		final Collection<OpenAccountRequestDTO> openAccountRequestsDTO = new ArrayList<>();
        for (Iterator<OpenAccountRequest> iterator = openAccountRequests.iterator(); iterator.hasNext();) {
            final OpenAccountRequest openAccountRequest = (OpenAccountRequest) iterator.next();
            openAccountRequestsDTO.add(transformOpenAccountRequest2DTO(openAccountRequest));
        }
		
        return openAccountRequestsDTO;
	}
	/**
     * This method checks the validity of the identifier. 
     * 
     * @param id	the identifier.
     * @throws CheckException is thrown if an error occurs during checking.
     */
    private void checkId(final String id) throws CheckException {
    	if ( id == null || id.equals("") )
    		throw new CheckException("Id should not be null or empty");    	
    }

}
