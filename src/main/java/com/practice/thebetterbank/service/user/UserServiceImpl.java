package com.practice.thebetterbank.service.user;

import com.practice.thebetterbank.entity.User;
import com.practice.thebetterbank.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public Optional<User> getUserById(Long id) {
        return Optional.ofNullable(userRepository.getUserById(id));
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
