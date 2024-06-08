package com.example.TherapeuticJournal.Repository;

import com.example.TherapeuticJournal.domain.Emotion.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findCommentsByEmotionId(Integer emotionId);
    // Alte metode specifice comentariilor, dacă sunt necesare
}
