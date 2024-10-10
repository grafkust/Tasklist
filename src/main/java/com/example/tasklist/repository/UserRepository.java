package com.example.tasklist.repository;

import com.example.tasklist.domain.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    String FIND_TASK_AUTHOR = """
            SELECT u.id as id,
            u.name as name,
            u.username as username,
            u.password as password
            FROM users_tasks ut JOIN users u ON ut.user_id = u.id WHERE ut.task_id = :taskId
            """;

    String IS_TASK_OWNER = "SELECT exists(SELECT 1 FROM users_tasks WHERE user_id = :userId AND task_id = :taskId)";

    Optional<User> findByUsername(String username);

    Optional<User> findById(Long id);

    @Query(value = FIND_TASK_AUTHOR, nativeQuery = true)
    Optional<User> findTaskAuthor(@Param("taskId") Long taskId);

    @Query(value = IS_TASK_OWNER, nativeQuery = true)
    boolean isTaskOwner(@Param("userId") Long userId, @Param("taskId") Long taskId);

    void deleteByUsername(String username);
}
