package com.example.tasklist.service;

import com.example.tasklist.domain.exception.ResourceNotFoundException;
import com.example.tasklist.domain.model.user.User;
import com.example.tasklist.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Cacheable(value = "UserService::getById", key = "#id")
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));
    }


    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));
    }

    @Transactional
    @Caching(put = {
            @CachePut(value = "UserService::getById", key = "#user.id"),
            @CachePut(value = "UserService::getByUsername", key = "#user.username")
    })
    public User update(User user) {

        Long id = user.getId();
        String oldUsername = getById(id).getUsername();

        if (!user.getUsername().equals(oldUsername)) {
            if (userRepository.findByUsername(user.getUsername()).isPresent())
                throw new IllegalStateException("A user with this username already exists");
        }

        if (!user.getPassword().equals(user.getPasswordConfirmation()))
            throw new IllegalStateException("The password and the confirmation of the password do not match");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    @Caching(cacheable = {
            @Cacheable(value = "UserService::getById", condition = "#user.id!=null", key = "#user.id"),
            @Cacheable(value = "UserService::getByUsername", condition = "#user.username!=null", key = "#user.username")
    })
    public User create(User user) {
        checkData(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public boolean isTaskOwner(Long userId, Long taskId) {
        return userRepository.isTaskOwner(userId, taskId) != 0;
    }

    @Transactional
    @CacheEvict(value = "UserService::getById", key = "#id")
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private void checkData(User user) {
        if (!user.getPassword().equals(user.getPasswordConfirmation()))
            throw new IllegalStateException("The password and the confirmation of the password do not match");
        if (userRepository.findByUsername(user.getUsername()).isPresent())
            throw new IllegalStateException("A user with this username already exists");
    }
}
