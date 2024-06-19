package com.example.TherapeuticJournal.api.Task.dto;

public class DeleteTaskDto {
    private int id;

    // Constructor
    public DeleteTaskDto(int id) {
        this.id = id;
    }

    // Getter and Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
