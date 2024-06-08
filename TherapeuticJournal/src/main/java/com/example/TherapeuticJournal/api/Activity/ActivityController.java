package com.example.TherapeuticJournal.api.Activity;

import com.example.TherapeuticJournal.Repository.ActivityRepository;
import com.example.TherapeuticJournal.api.Activity.dto.CreateActivityDto;
import com.example.TherapeuticJournal.api.Activity.dto.UpdateActivityDto;
import com.example.TherapeuticJournal.domain.Activity.Activity;
import com.example.TherapeuticJournal.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityController(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    // Get all Activities
    @GetMapping("/all")
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    // Get a single Activity by ID
    @GetMapping("/{id}")
    public Activity getActivityById(@PathVariable Integer id) throws ChangeSetPersister.NotFoundException {
        return activityRepository.findById(id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
    }

    // Create a new Activity
    @PostMapping("/add")
    public Activity createActivity(@RequestBody CreateActivityDto createActivityDto) {
        // Validate the input DTO
        validateCreateActivityDto(createActivityDto);

        Activity activity = new Activity();
        activity.setName(createActivityDto.getName());
        activity.setDescription(createActivityDto.getDescription());
        activity.setDuration(createActivityDto.getDuration());

        return activityRepository.save(activity);
    }

    // Update an existing Activity
    @PutMapping("/{id}")
    public Activity updateActivity(@PathVariable Integer id, @RequestBody UpdateActivityDto updateActivityDto) {
        // Validate the input DTO
        validateUpdateActivityDto(updateActivityDto);

        Activity activity = activityRepository.findById(id).orElseThrow(() -> new BadRequestException("Activity not found with ID: " + id));
        activity.setName(updateActivityDto.getName());
        activity.setDescription(updateActivityDto.getDescription());
        activity.setDuration(updateActivityDto.getDuration());

        return activityRepository.save(activity);
    }

    // Delete an Activity by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteActivity(@PathVariable Integer id) {
        if (!activityRepository.existsById(id)) {
            throw new BadRequestException("Activity not found with ID: " + id);
        }
        activityRepository.deleteById(id);
        return ResponseEntity.ok().body("Activity with ID " + id + " deleted successfully");
    }

    // Get Activities by name and duration
    @GetMapping
    public List<Activity> getActivitiesByNameAndDuration(@RequestParam(required = false) String name,
                                                         @RequestParam(required = false) Integer duration) {
        return activityRepository.findByNameAndDuration(name, duration);
    }

    // Helper method to validate CreateActivityDto
    private void validateCreateActivityDto(CreateActivityDto createActivityDto) {
        if (createActivityDto.getName() == null) {
            throw new BadRequestException("Activity name must not be null");
        }
        if (createActivityDto.getDescription() == null) {
            throw new BadRequestException("Activity description must not be null");
        }
        if (createActivityDto.getDuration() == Integer.parseInt(null)) {
            throw new BadRequestException("Activity duration must not be null");
        }
    }

    // Helper method to validate UpdateActivityDto
    private void validateUpdateActivityDto(UpdateActivityDto updateActivityDto) {
        if (updateActivityDto.getName() == null) {
            throw new BadRequestException("Activity name must not be null");
        }
        if (updateActivityDto.getDescription() == null) {
            throw new BadRequestException("Activity description must not be null");
        }
        if (updateActivityDto.getDuration() == Integer.parseInt(null)) {
            throw new BadRequestException("Activity duration must not be null");
        }
    }
}


