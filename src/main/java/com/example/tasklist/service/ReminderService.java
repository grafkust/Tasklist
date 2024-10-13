package com.example.tasklist.service;

import com.example.tasklist.domain.mail.MailType;
import com.example.tasklist.domain.model.task.Task;
import com.example.tasklist.domain.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class ReminderService {

    private final TaskService taskService;
    private final UserService userService;
    private final MailService mailService;
    private final Duration DURATION = Duration.ofHours(1);

    @Scheduled(cron = "0 0 * * * *")
    public void remindForTask() {
        List<Task> tasks = taskService.getAllSoonTasks(DURATION);
        tasks.forEach(task -> {
            User user = userService.getTaskAuthor(task.getId());
            Properties properties = new Properties();
            properties.setProperty("task.title", task.getTitle());
            properties.setProperty("task.description", task.getDescription());
            mailService.sendEmail(user, MailType.REMINDER, properties);
        });
    }

}
