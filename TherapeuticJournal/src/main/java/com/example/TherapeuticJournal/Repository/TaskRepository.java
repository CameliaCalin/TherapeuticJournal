package com.example.TherapeuticJournal.Repository;

import com.example.TherapeuticJournal.domain.Activity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findByNameAndDescription(String name, String description);

    List<Task> findByName(String name);

    List<Task> findByDescription(String description);

    // Example of a more complex query method
    @Query("SELECT t FROM Task t WHERE " +
            "(:name IS NULL OR t.name = :name) AND " +
            "(:description IS NULL OR t.description = :description)")
    List<Task> findByFilters(@Param("name") String name, @Param("description") String description);
}
