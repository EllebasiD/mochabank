package com.anotherbank.mochabank.authentication.domain.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anotherbank.mochabank.authentication.domain.dao.UserRepository;
import com.anotherbank.mochabank.authentication.domain.dto.UserDTO;
import com.anotherbank.mochabank.authentication.domain.model.Role;
import com.anotherbank.mochabank.authentication.domain.model.User;
import com.anotherbank.mochabank.exception.CheckException;
import com.anotherbank.mochabank.exception.FinderException;
import com.anotherbank.mochabank.logging.Trace;

/**
* This class is a facade for all user services.
* 
* @author Isabelle Deligniere
*/

@Service
@Primary
@Transactional
public class UserServiceImpl implements UserService {

	// ======================================
    // =             Attributes             =
    // ======================================
    
	// Used for logging
    protected final transient String _cname = this.getClass().getName();
    
    @Autowired
    private UserRepository userRepository;  
    
    // ======================================
    // =            Constructors            =
    // ======================================
    public UserServiceImpl() {}

	// ======================================
    // =           Business methods         =
    // ======================================
	/**
     * This method finds an user by his identifier.
     * 
     * @param userId		the identifier of the user.
     * @throws FinderException is thrown if an error occurs during searching.
     * @throws CheckException is thrown if an error occurs during checking.
     * @return userDTO		the user.
     */
	@Override
	@Transactional(readOnly=true)
	public UserDTO findUser(String userId) throws FinderException, CheckException {
		final String mname = "findUser";
	    Trace.entering(_cname, mname, userId);

	    checkId(userId);
	    	
	    // Finds the object
	    User user = null;
	    if( ! userRepository.findById(userId).isPresent())
	       	throw new FinderException("User must exist to be found");
	    else 
	       	user = userRepository.findById(userId).get();

	    // Transforms domain object into DTO
	    final UserDTO userDTO = transformUser2DTO(user);

	    Trace.exiting(_cname, mname, userDTO);
	        
	    return userDTO;
	}
	/**
     * This method finds all the users.
     * 
     * @throws FinderException is thrown if an error occurs during searching.
     */
	@Override
	@Transactional(readOnly=true)
	public Iterable<UserDTO> findUsers() throws FinderException {
		final String mname = "findUsers";
        Trace.entering(_cname, mname);

        // Finds all the objects
        final Iterable<User> users = userRepository.findAll();

        // Transforms domain objects into DTOs
        Iterable<UserDTO> usersDTO = transformUsers2DTOs(users);
        
        Integer integer = Integer.valueOf(((Collection<UserDTO>) usersDTO).size());
        Trace.exiting(_cname, mname, integer);
        return usersDTO;
	}
    /**
     * This method finds all the users by role.
     * 
     * @param role		the role.
     * @return the users.
     */
	@Override
	@Transactional(readOnly=true)
	public Iterable<UserDTO> findUsersByRole(Role role) {
		final String mname = "findUsersByRole";
        Trace.entering(_cname, mname);     	
      
        // Finds all the objects
        final Iterable<User> users = userRepository.findAll();
        
        final Collection<User> usersByRole = new ArrayList<>();
        for (Iterator<User> iterator = users.iterator(); iterator.hasNext();) {
        	final User user = (User) iterator.next();
            if(user.getRole().getName().equals(role.getName())) {
            	usersByRole.add(user);
            }
        }
                
        // Transforms domain objects into DTOs
        Iterable<UserDTO> usersDTOByRole = transformUsers2DTOs(usersByRole);
        
        Integer integer = Integer.valueOf(((Collection<UserDTO>) usersDTOByRole).size());
        Trace.exiting(_cname, mname, integer);
        return usersDTOByRole;
	}

	
	// ======================================
    // =          Private Methods           =
    // ======================================
    /**
     * This method transforms an User object to an UserDTO object.
     * 
     * @param user		the User object.
     * @return userDTO	the USerDTO object.
     */
	private UserDTO transformUser2DTO(User user) {
		final UserDTO userDTO = new UserDTO();
		userDTO.setUsername(user.getUsername());
		userDTO.setFirstname(user.getFirstname());
		userDTO.setLastname(user.getLastname());
		userDTO.setPassword(user.getPassword());
		userDTO.setEmail(user.getEmail());
        	
        if(user.getRole() != null) {
        	userDTO.setRoleId(user.getRole().getId());
            userDTO.setRoleName(user.getRole().getName());
        }
        
        return userDTO;
	}
	/**
     * This method transforms an iterable collection of User objects to an iterable collection of UserDTO objects.
     * 
     * @param customers 		the iterable collection of User objects.
     * @return customersDTO		the iterable collection of UserDTO objects
     */
	private Iterable<UserDTO> transformUsers2DTOs(Iterable<User> users) {
		final Collection<UserDTO> usersDTO = new ArrayList<>();
        for (Iterator<User> iterator = users.iterator(); iterator.hasNext();) {
            final User user = (User) iterator.next();
            usersDTO.add(transformUser2DTO(user));
        }
		
        return usersDTO;
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
