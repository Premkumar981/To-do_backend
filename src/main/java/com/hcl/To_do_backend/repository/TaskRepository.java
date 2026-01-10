package com.hcl.To_do_backend.repository;

import com.hcl.To_do_backend.entity.Task;
import com.hcl.To_do_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUser(User user);

    void deleteByIdAndUser(Long id, User user);
}
