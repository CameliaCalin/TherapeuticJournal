package com.example.TherapeuticJournal.domain.Emotion;

import com.example.TherapeuticJournal.domain.Emotion.Emotion;
import jakarta.persistence.*;

@Entity
@Table(name = "comment", schema = "public")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String text;

    // Constructori, getteri și setteri

    public Comment() {
    }

    public Comment(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setEmotion(Emotion emotion) {
    }
}
