package com.example.tasklist.repository;

import com.example.tasklist.domain.model.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    String FIND_ALL_BY_USER_ID = """
            SELECT * FROM tasks t
            JOIN users_tasks ut ON ut.task_id = t.id
            WHERE ut.user_id = :userId
            """;

    String ASSIGN_TASK = """
            INSERT INTO users_tasks (user_id, task_id)
            VALUES (:userId, :taskId)
            """;

    @Query(value = FIND_ALL_BY_USER_ID, nativeQuery = true)
    List<Task> findAllByUserId(@Param("userId") Long userId);
    

    @Modifying
    @Query(value = ASSIGN_TASK, nativeQuery = true)
    void assignTask(@Param("userId") Long userId, @Param("taskId") Long taskId);



}
