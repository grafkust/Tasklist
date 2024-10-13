package com.example.tasklist.service;

import com.example.tasklist.domain.exception.ResourceNotFoundException;
import com.example.tasklist.domain.model.task.Status;
import com.example.tasklist.domain.model.task.Task;
import com.example.tasklist.domain.model.task.TaskImage;
import com.example.tasklist.repository.TaskRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;
    private final ImageService imageService;

    @Cacheable(value = "TaskService::getById",condition ="#id!=null" ,key = "#id")
    public Task getById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("task not found"));
    }

    public List<Task> getAllByUserId(Long id) {
        return taskRepository.findAllByUserId(id);
    }

    @Transactional
    @CachePut(value = "TaskService::update", key = "#task.id")
    public Task update(Task task) {
        return taskRepository.save(task);
    }

    @Transactional
    @Cacheable(value = "TaskService::getById",condition ="#task.id!=null" ,key = "#task.id")
    public Task create(Task task, Long userId) {

        if (task.getStatus() == null)
            task.setStatus(Status.TODO);

        Task newTask = taskRepository.save(task);
        taskRepository.assignTask(userId, newTask.getId());

        return task;
    }

    @Transactional
    @CacheEvict(value = "TaskService::getById", key = "#id")
    public void delete(Long id){
        taskRepository.deleteById(id);
    }

    @Transactional
    @CacheEvict(value = "TaskService::getById", key = "#id")
    public void uploadImage(Long id, TaskImage image) throws IOException {
        Task task = getById(id);
        String fileName = imageService.upload(image);
        task.getImages().add(fileName);
        taskRepository.save(task);
    }



    public List<Task> getAllSoonTasks(Duration duration) {
        LocalDateTime now = LocalDateTime.now();
        return taskRepository.findAllSoonTasks(Timestamp.valueOf(now), Timestamp.valueOf(now.plus(duration)));
    }
}
