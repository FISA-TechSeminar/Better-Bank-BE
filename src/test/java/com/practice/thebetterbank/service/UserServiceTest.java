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
        User[] users = new User[] { User.builder().id(1L).username("이조은").build(),
        User.builder().id(2L).username("서민지").build(),
        User.builder().id(3L).username("이영주").build(),
        User.builder().id(4L).username("홍윤기").build()};
        for(User user : users) {
            userService.save(user);
        }

    }

}
