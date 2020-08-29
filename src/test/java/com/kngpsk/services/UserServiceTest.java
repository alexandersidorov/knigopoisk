package com.kngpsk.services;

import com.kngpsk.domain.Role;
import com.kngpsk.domain.User;
import com.kngpsk.repos.UserRepo;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private MailSender mailSender;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void addUser() {
        User user = new User();
        user.setEmail("needsomebody@tolove.ru");

        boolean isCreated = userService.addUser(user);
        Assert.assertTrue(isCreated);
        Assert.assertNotNull(user.getActivationCode());
        Assert.assertThat(user.getRoles(),CoreMatchers.hasItem(Role.USER));

        Mockito.verify(userRepo,Mockito.times(1)).save(user);
        Mockito.verify(mailSender,Mockito.times(1)).send(
                ArgumentMatchers.eq(user.getEmail()),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()
        );
    }

    @Test
    public void addUserFail(){
        User user = new User();
        user.setUsername("JohnDoe");

        Mockito.doReturn(new User())
                .when(userRepo)
                .findByUsername("JohnDoe");

        boolean isCreated = userService.addUser(user);

        Assert.assertFalse(isCreated);

        Mockito.verify(userRepo,Mockito.times(0)).save(ArgumentMatchers.any(User.class));
        Mockito.verify(mailSender,Mockito.times(0)).send(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()
        );
    }
}