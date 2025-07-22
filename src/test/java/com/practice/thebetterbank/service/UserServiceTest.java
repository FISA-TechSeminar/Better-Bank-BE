package com.practice.thebetterbank.service;


import com.practice.thebetterbank.entity.User;
import com.practice.thebetterbank.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void saveUserTest() {
        User user = new User();
    }

}
