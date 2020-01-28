package nl.quintor.app.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import nl.quintor.app.dto.UserRegistrationDTO;
import nl.quintor.app.exception.UsernameAlreadyExistsException;
import nl.quintor.app.model.RoleModel;
import nl.quintor.app.model.UserAccountModel;
import nl.quintor.app.repository.RoleModelRepository;
import nl.quintor.app.repository.UserAccountModelRepository;
import nl.quintor.app.transformer.UserTransformer;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service(value = "JwtUserDetailsService")
public class JwtUserDetailsService implements UserDetailsService {


    @Autowired
    private UserAccountModelRepository userAccountModelRepository;

    @Autowired
    private RoleModelRepository roleModelRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private Logger log;


    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccountModel user = userAccountModelRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No username found.");
        }

        if (!user.getEnabled()){
            return null;
        }

        return new User(user.getUsername(), user.getPassword(), getAuthorities(user.getRoleModels()));
    }

    @Transactional
    public UserAccountModel save(UserRegistrationDTO user) throws UsernameAlreadyExistsException, JsonProcessingException {

        if(userAccountModelRepository.findByUsername(user.getUsername()) != null){
            throw new UsernameAlreadyExistsException("This username is already taken.");
        }

        UserTransformer transformer = new UserTransformer();
        UserAccountModel userAccountModel = transformer.userRegistrationDTOToUserAccountModel(user);
        // We received a plaintext password during the transformation, now it will be encrypted.
        userAccountModel.setPassword(bcryptEncoder.encode(userAccountModel.getPassword()));
        // Request should not contain the roles, we're manually setting the user as a ROLE_USER.
        RoleModel roleModel = roleModelRepository.findByName("ROLE_USER");
        //roleModel.getUsers().add(userAccountModel);
        userAccountModel.getRoleModels().add(roleModel);

        return userAccountModelRepository.save(userAccountModel);
    }

    @Transactional
    public void enableUser(String username) {
        UserAccountModel user = userAccountModelRepository.findByUsername(username);
        user.setEnabled(true);
        userAccountModelRepository.save(user);
    }

    @Transactional
    public void disableUser(String username) {
        UserAccountModel user = userAccountModelRepository.findByUsername(username);
        user.setEnabled(false);
        userAccountModelRepository.save(user);
    }

    // Dangerous to use as potential records can be corrupted. Most likely returns a SQL Exception as well.
    @Transactional
    public void hardDeleteUser(Long userId) {
        userAccountModelRepository.delete(userAccountModelRepository.findById(userId));
    }

    /**
     * User can only request their own user information unless certain roles are present (Such as admin.. etc)
     * Depending on business logic, this method can also be used for editing the user.
     * @param id supply a user id to check wether the logged in/authenticated user is allowed to edit this user.
     * @return true when allowed or false when not allowed.
     */
    public Boolean canRequestUser(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // The user has proper role privileges to edit this user.
        if(auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) ||
        auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_YOURROLEHERE"))){
            return true;
            // returns true if the logged-in/authenticated user ID equals the ID of the user that is being requested.
        } else return id.equals(getCurrentLoggedInUserId());
    }

    public Long getCurrentLoggedInUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserAccountModel userAccountModel = userAccountModelRepository.findByUsername(username);
        return userAccountModel.getId();
    }

    @Transactional
    private UserAccountModel wipeUser(UserAccountModel user) {
        String uuid = UUID.randomUUID().toString();
        user.setEmail(uuid);
        user.setName(uuid);
        user.setUsername(uuid);
        return userAccountModelRepository.save(user);
    }

    private List<GrantedAuthority> getAuthorities(Set<RoleModel> roles) {
        return roles.stream()
                .map(RoleModel::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @PostConstruct
    public void initializeRoleModels(){
        RoleModel compareRoleModel = new RoleModel("USER");
        RoleModel persistedRoleModel = roleModelRepository.findByName("ROLE_USER");
        if(persistedRoleModel == null){
            roleModelRepository.save(compareRoleModel);
        }
    }
}
