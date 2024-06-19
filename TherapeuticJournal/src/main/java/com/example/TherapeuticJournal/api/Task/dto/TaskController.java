package com.example.TherapeuticJournal.api.Task.dto;

import com.example.TherapeuticJournal.Repository.TaskRepository;
import com.example.TherapeuticJournal.api.Task.dto.CreateTaskDto;
import com.example.TherapeuticJournal.api.Task.dto.ReadTaskDto;

import com.example.TherapeuticJournal.domain.Activity.Task;
import com.example.TherapeuticJournal.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Endpoint to get all tasks
    @GetMapping("/all")
    public List<ReadTaskDto> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(this::convertToReadTaskDto)
                .collect(Collectors.toList());
    }

    // Endpoint to get a task by ID
    @GetMapping("/{id}")
    public ReadTaskDto getTaskById(@PathVariable Integer id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Task not found with ID: " + id));
        return convertToReadTaskDto(task);
    }

    // Endpoint to create a new task
    @PostMapping("/add")
    public Task createTask(@RequestBody CreateTaskDto createTaskDto) {
        Task task = new Task();
        task.setName(createTaskDto.getName());
        task.setDescription(createTaskDto.getDescription());
        return taskRepository.save(task);
    }

    // Endpoint to update an existing task
    @PutMapping("/modify/{id}")
    public Task updateTask(@PathVariable Integer id, @RequestBody CreateTaskDto createTaskDto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Task not found with ID: " + id));
        task.setName(createTaskDto.getName());
        task.setDescription(createTaskDto.getDescription());
        return taskRepository.save(task);
    }

    // Endpoint to delete a task by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Integer id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Task not found with ID: " + id));
        taskRepository.delete(task);
        return ResponseEntity.ok("Task with ID " + id + " deleted successfully");
    }

    // Endpoint to search tasks by name and description
    @GetMapping("/search")
    public List<ReadTaskDto> searchTasks(@RequestParam(required = false) String name,
                                         @RequestParam(required = false) String description) {
        List<Task> tasks;
        if (name != null && description != null) {
            tasks = taskRepository.findByNameAndDescription(name, description);
        } else if (name != null) {
            tasks = taskRepository.findByName(name);
        } else if (description != null) {
            tasks = taskRepository.findByDescription(description);
        } else {
            tasks = taskRepository.findAll();
        }
        return tasks.stream()
                .map(this::convertToReadTaskDto)
                .collect(Collectors.toList());
    }

    // Endpoint for a more complex query using @RequestParam
    @GetMapping("/filter")
    public List<ReadTaskDto> filterTasks(@RequestParam(required = false) String name,
                                         @RequestParam(required = false) String description) {
        List<Task> tasks = taskRepository.findByFilters(name, description);
        return tasks.stream()
                .map(this::convertToReadTaskDto)
                .collect(Collectors.toList());
    }

    // Utility method to convert Task entity to ReadTaskDto
    private ReadTaskDto convertToReadTaskDto(Task task) {
        return new ReadTaskDto(task.getId(), task.getName(), task.getDescription());
    }
}
