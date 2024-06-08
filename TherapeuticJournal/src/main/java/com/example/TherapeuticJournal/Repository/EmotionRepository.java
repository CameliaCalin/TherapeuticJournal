package com.example.TherapeuticJournal.Repository;

import com.example.TherapeuticJournal.domain.Emotion.Emotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmotionRepository extends JpaRepository<Emotion, Integer> {

    @Query("SELECT e FROM Emotion e " +
            "WHERE (:name IS NULL OR e.name = :name) " +
            "AND (:intensity IS NULL OR e.intensity = :intensity) " +
            "AND (:description IS NULL OR e.description = :description)")
    List<Emotion> findByCriteria(String name, Integer intensity, String description);

    // Metoda pentru a obține toate comentariile unei emoții după id-ul emoției

}

