package com.example.TherapeuticJournal.api.Emotion.dto;

public class CreateCommentDto {
    private String text;
    private int emotionId;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getEmotionId() {
        return emotionId;
    }

    public void setEmotionId(int emotionId) {
        this.emotionId = emotionId;
    }
}
