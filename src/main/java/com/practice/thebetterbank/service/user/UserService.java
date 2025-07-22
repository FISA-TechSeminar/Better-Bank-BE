package com.practice.thebetterbank.service.user;

import com.practice.thebetterbank.entity.User;

import java.util.Optional;

public interface UserService {
    public Optional<User> getUserById(Long id);

    public void save(User user);
}
