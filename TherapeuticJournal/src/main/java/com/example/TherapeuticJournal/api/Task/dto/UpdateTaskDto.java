package com.example.TherapeuticJournal.api.Task.dto;

public class UpdateTaskDto {
    private String name;
    private String description;

    // Constructor
    public UpdateTaskDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
