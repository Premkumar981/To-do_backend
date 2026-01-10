//package com.hcl.To_do_backend.controller;
//
//import com.hcl.To_do_backend.dto.TaskRequest;
//import com.hcl.To_do_backend.entity.Task;
//import com.hcl.To_do_backend.entity.User;
//import com.hcl.To_do_backend.repository.TaskRepository;
//import com.hcl.To_do_backend.repository.UserRepository;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/tasks")
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
//public class TaskController {
//
//    private final TaskRepository taskRepository;
//    private final UserRepository userRepository;
//
//    public TaskController(TaskRepository taskRepository,
//                          UserRepository userRepository) {
//        this.taskRepository = taskRepository;
//        this.userRepository = userRepository;
//    }
//
//    // ---------------- ADD TASK ----------------
//    @PostMapping
//    public Task addTask(@RequestBody TaskRequest request) {
//
//        Authentication auth = SecurityContextHolder
//                .getContext()
//                .getAuthentication();
//
//        if (auth == null || !auth.isAuthenticated()) {
//            throw new RuntimeException("Not authenticated");
//        }
//
//        String email = auth.getName();
//
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        Task task = Task.builder()
//                .description(request.getDescription())
//                .priority(request.getPriority())
//                .status(request.getStatus())
//                .deadline(request.getDeadline())
//                .user(user)
//                .build();
//
//        return taskRepository.save(task);
//    }
//
//    // ---------------- GET TASKS ----------------
//    @GetMapping
//    public List<Task> getTasks() {
//
//        Authentication auth = SecurityContextHolder
//                .getContext()
//                .getAuthentication();
//
//        if (auth == null || !auth.isAuthenticated()) {
//            throw new RuntimeException("Not authenticated");
//        }
//
//        String email = auth.getName();
//
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        return taskRepository.findByUser(user);
//    }
//}


package com.hcl.To_do_backend.controller;

import com.hcl.To_do_backend.dto.TaskRequest;
import com.hcl.To_do_backend.entity.Task;
import com.hcl.To_do_backend.entity.User;
import com.hcl.To_do_backend.repository.TaskRepository;
import com.hcl.To_do_backend.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class TaskController {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskController(TaskRepository taskRepository,
                          UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    // 🔹 COMMON METHOD: get logged-in user
    private User getLoggedInUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Not authenticated");
        }

        return userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // ---------------- ADD TASK ----------------
    @PostMapping
    public Task addTask(@RequestBody TaskRequest request) {

        User user = getLoggedInUser();

        Task task = Task.builder()
                .description(request.getDescription())
                .priority(request.getPriority())
                .status(request.getStatus())
                .deadline(request.getDeadline())
                .user(user)
                .build();

        return taskRepository.save(task);
    }

    // ---------------- GET TASKS ----------------
    @GetMapping
    public List<Task> getTasks() {

        User user = getLoggedInUser();
        return taskRepository.findByUser(user);
    }

    // ---------------- DELETE TASK ----------------
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {

        User user = getLoggedInUser();

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        // 🔒 Security check
        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        taskRepository.delete(task);
    }

    // ---------------- UPDATE TASK ----------------
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id,
                           @RequestBody TaskRequest request) {

        User user = getLoggedInUser();

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        // 🔒 Security check
        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setStatus(request.getStatus());
        task.setDeadline(request.getDeadline());

        return taskRepository.save(task);
    }
}
