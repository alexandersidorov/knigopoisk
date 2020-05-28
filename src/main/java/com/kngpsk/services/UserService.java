package com.kngpsk.services;

import com.kngpsk.domain.Role;
import com.kngpsk.domain.User;
import com.kngpsk.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(s);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public boolean addUser(User user){

        User userFromDB = userRepo.findByUsername(user.getUsername());

        if(userFromDB!=null){
            return false;
        }

        user.setActive(false);

        if(userRepo.findAll().size()==0){
            Set<Role>roles = new HashSet<>();
            roles.add(Role.USER);
            roles.add(Role.ADMIN);
            roles.add(Role.MODERATOR);
            user.setRoles(roles);
        }
        else user.setRoles(Collections.singleton(Role.USER));

        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);

        sendMessage(user);

        return true;
    }

    private void sendMessage(User user) {
        if(!StringUtils.isEmpty(user.getEmail())){
            String message = String.format("Hello %s my friend !!!\n"+
                            "For activation visit http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );

            mailSender.send(user.getEmail(),"Activation code",message);

        }
    }

    public boolean activateUser(String code){

        User user = userRepo.findByActivationCode(code);

        if(user==null){
            return false;
        }
        user.setActivationCode(null);
        user.setActive(true);
        userRepo.save(user);

        return true;
    }

    public List<User> findAll(){
        return userRepo.findAll();
    }

    public void saveUser(User user, String username, Map<String, String> form) {

        user.getRoles().clear();

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        for(String key:form.keySet()){
            if(roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }

        user.setUsername(username);
        userRepo.save(user);
    }

    public void updateProfile(User user, String password, String email) {

        String userEmail = user.getEmail();
        boolean isEmailChanged = (email != null && !email.equals(userEmail)) || (email != null && !userEmail.equals(email));

        if (isEmailChanged) {
            user.setEmail(email);
            if (!StringUtils.isEmpty(email)) {
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }

        if (!StringUtils.isEmpty(password)) {
            user.setPassword(passwordEncoder.encode(password));
        }

        userRepo.save(user);

        if (isEmailChanged) {
            sendMessage(user);
        }
    }

//    public void subscribe(User currentUser, User user) {
//        user.getSubscribers().add(currentUser);
//        userRepo.save(user);
//    }
//
//    public void unsubscribe(User currentUser, User user) {
//        user.getSubscribers().remove(currentUser);
//        userRepo.save(user);
//    }

}
