package com.example.TherapeuticJournal.domain.EntryType;

import jakarta.persistence.*;

@Entity
@Table(name = "entrytype", schema = "public")
public class EntryType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     int id;
     String type;
     String description;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
