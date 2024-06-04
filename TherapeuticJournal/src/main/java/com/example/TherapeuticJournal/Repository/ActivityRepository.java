package com.example.TherapeuticJournal.Repository;

import com.example.TherapeuticJournal.domain.Activity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {
}