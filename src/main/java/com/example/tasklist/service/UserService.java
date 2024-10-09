package com.example.tasklist.service;

import com.example.tasklist.domain.exception.ResourceNotFoundException;
import com.example.tasklist.domain.model.user.User;
import com.example.tasklist.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));
    }

    public User update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User create(User user) {
        checkData(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public boolean isTaskOwner(Long userId, Long taskId) {
        return userRepository.isTaskOwner(userId, taskId);
    }

    public User getTaskAuthor(Long taskId){
        return userRepository.findTaskAuthor(taskId).orElseThrow(() -> new ResourceNotFoundException("task author not found"));
    }

    public void delete(Long taskId) {
        userRepository.deleteById(taskId);
    }


    private void checkData(User user) {
        if (!user.getPassword().equals(user.getPasswordConfirmation()))
            throw new IllegalStateException("Passwords do not match");
        if (userRepository.findByUsername(user.getUsername()).isPresent())
            throw new IllegalStateException("Username already exists");
    }
}
