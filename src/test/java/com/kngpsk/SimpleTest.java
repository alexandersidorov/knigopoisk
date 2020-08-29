package com.kngpsk;

import com.kngpsk.controllers.MainController;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleTest {

    @Autowired
    MainController mainController;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void firstSimpleTest() throws Exception{
        Assert.assertNotNull(mainController);
    }

    @Test
    public void testMVC() throws Exception{
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("KnigoPoisk - all books of the world!")));
    }

    @Test
    public void loginTest() throws Exception{
        mockMvc.perform(get("/user"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    public void correctLoginTest() throws Exception{
        mockMvc.perform(formLogin().user("admin").password("1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void badCred() throws Exception{
        mockMvc.perform(post("login")
                .param("user","Batman"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

}
