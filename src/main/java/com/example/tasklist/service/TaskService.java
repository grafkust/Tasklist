package com.example.tasklist.service;

import com.example.tasklist.domain.exception.ResourceNotFoundException;
import com.example.tasklist.domain.model.task.Task;
import com.example.tasklist.domain.model.user.User;
import com.example.tasklist.repository.TaskRepository;
import com.example.tasklist.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;

    public Task getById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("task not found"));

    }

    public List<Task> getAllByUserId(Long id) {
        return taskRepository.findAllByUserId(id);

    }

    public Task update(Task task) {
        return taskRepository.save(task);
    }

    public Task create(Task task, Long userId) {
        Task newTask = taskRepository.save(task);
        taskRepository.assignTask(userId, newTask.getId());

        return task;
    }

    public void delete(Long id){
        taskRepository.deleteById(id);
    }








}
