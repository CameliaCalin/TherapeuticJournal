package com.example.TherapeuticJournal.api.Activity;

import com.example.TherapeuticJournal.Repository.ActivityRepository;
import com.example.TherapeuticJournal.api.Activity.dto.*;
import com.example.TherapeuticJournal.domain.Activity.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityController(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    // Read all activities
    @GetMapping("/all")
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    // Read a single activity by ID
    @GetMapping("/{id}")
    public ResponseEntity<Activity> getActivityById(@PathVariable int id) {
        Optional<Activity> optionalActivity = activityRepository.findById(id);
        return optionalActivity.map(activity -> ResponseEntity.ok().body(activity))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new activity
    @PostMapping
    public ResponseEntity<Activity> createActivity(@RequestBody CreateActivityDto createActivityDto) {
        Activity activity = new Activity();
        activity.setName(createActivityDto.getName());
        activity.setDescription(createActivityDto.getDescription());
        activity.setDuration(createActivityDto.getDuration());
        Activity savedActivity = activityRepository.save(activity);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedActivity);
    }

    // Update an existing activity
    @PutMapping("/{id}")
    public ResponseEntity<Activity> updateActivity(@PathVariable int id, @RequestBody UpdateActivityDto updateActivityDto) {
        Optional<Activity> optionalActivity = activityRepository.findById(id);
        if (optionalActivity.isPresent()) {
            Activity activity = optionalActivity.get();
            activity.setName(updateActivityDto.getName());
            activity.setDescription(updateActivityDto.getDescription());
            activity.setDuration(updateActivityDto.getDuration());
            return ResponseEntity.ok().body(activityRepository.save(activity));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete an activity
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteActivity(@PathVariable int id) {
        Optional<Activity> optionalActivity = activityRepository.findById(id);
        if (optionalActivity.isPresent()) {
            activityRepository.deleteById(id);
            return ResponseEntity.ok().body("Activity with ID " + id + " deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}
