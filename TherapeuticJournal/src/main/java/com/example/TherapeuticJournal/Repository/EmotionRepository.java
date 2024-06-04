package com.example.TherapeuticJournal.Repository;

import com.example.TherapeuticJournal.domain.Emotion.Emotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmotionRepository extends JpaRepository<Emotion, Integer> {
    /*@Query("""
            SELECT p FROM Product p
            """)
    List<Emotion> findAllEmotions();*/

}

