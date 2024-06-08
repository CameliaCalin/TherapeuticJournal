package com.example.TherapeuticJournal.api.Activity.dto;

import com.example.TherapeuticJournal.Repository.ActivityRepository;
import com.example.TherapeuticJournal.Repository.TaskRepository;
import com.example.TherapeuticJournal.domain.Activity.Task;
import com.example.TherapeuticJournal.domain.Activity.Activity;
import com.example.TherapeuticJournal.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskRepository taskRepository;
    private final ActivityRepository activityRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository, ActivityRepository activityRepository) {
        this.taskRepository = taskRepository;
        this.activityRepository = activityRepository;
    }

    // Create a new Task
    @PostMapping("/add")
    public Task createTask(@RequestParam Integer activityId, @RequestBody CreateTaskDto createTaskDto) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new BadRequestException("Activity not found with ID: " + activityId));

        Task task = new Task();
        task.setName(createTaskDto.getName());
        task.setDescription(createTaskDto.getDescription());
        task.setActivity(activity);

        return taskRepository.save(task);
    }

    // Update an existing Task
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Integer id, @RequestBody UpdateTaskDto updateTaskDto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Task not found with ID: " + id));

        task.setName(updateTaskDto.getName());
        task.setDescription(updateTaskDto.getDescription());

        return taskRepository.save(task);
    }

    // Delete a Task
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Integer id) {
        if (!taskRepository.existsById(id)) {
            throw new BadRequestException("Task not found with ID: " + id);
        }
        taskRepository.deleteById(id);
        return ResponseEntity.ok().body("Task with ID " + id + " deleted successfully");
    }
}
