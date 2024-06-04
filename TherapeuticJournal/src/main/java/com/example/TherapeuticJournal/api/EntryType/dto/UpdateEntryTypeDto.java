package com.example.TherapeuticJournal.api.EntryType.dto;

public class UpdateEntryTypeDto {
    String type;
    String description;

    // Getteri și setteri
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
