package com.example.TherapeuticJournal.Repository;

import com.example.TherapeuticJournal.domain.Activity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
