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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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

    @Autowired
    FileSaver fileSaver;

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

    public List<User> findUsers(){

        List<User> listAll = userRepo.findAll();
        List<User> retList = new ArrayList<>();
        for(User user:listAll){
            if(user.isAdmin() || user.isModerator() )continue;
            else retList.add(user);
        }
        return retList;
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

    public void updateProfile(User user, String username, String password, String email, MultipartFile avatar) throws IOException {

        //обновление имени
        String name = user.getUsername();
        boolean isNameChanged = (username != null && !name.equals(username));
        if(isNameChanged)user.setUsername(username);

        //обновление почты
        String userEmail = user.getEmail();
        boolean isEmailChanged = (email != null && !email.equals(userEmail)) || (email != null && !userEmail.equals(email));

        if (isEmailChanged) {
            user.setEmail(email);
            user.setActive(false);
            if (!StringUtils.isEmpty(email)) {
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }

        //обновление пароля
        if (!StringUtils.isEmpty(password)) {
            user.setPassword(passwordEncoder.encode(password));
        }

        //обновление аватара
        if(avatar!=null){
            String userPathAvatar = user.getAvatar();
            if(userPathAvatar!=null) {
                File oldAvatar = new File(userPathAvatar);
                oldAvatar.delete();
            }
            fileSaver.saveFile(user,avatar);
        }

        userRepo.save(user);

        if (isEmailChanged) {
            sendMessage(user);
        }
    }

    public void subscribe(User currentUser, User user) {
        user.getSubscribers().add(currentUser);
        userRepo.save(user);
    }

    public void unsubscribe(User currentUser, User user) {
        user.getSubscribers().remove(currentUser);
        userRepo.save(user);
    }

}
