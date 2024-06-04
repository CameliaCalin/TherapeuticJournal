package com.example.TherapeuticJournal.domain.Activity;

import jakarta.persistence.*;

@Entity
@Table(name = "activity", schema = "public")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     int id;
     String name;
     String description;
     int duration;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
