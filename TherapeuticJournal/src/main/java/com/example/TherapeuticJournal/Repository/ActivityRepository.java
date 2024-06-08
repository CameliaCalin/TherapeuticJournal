package com.example.TherapeuticJournal.Repository;

import com.example.TherapeuticJournal.domain.Activity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    @Query("SELECT a FROM Activity a WHERE (:name IS NULL OR a.name = :name) AND (:duration IS NULL OR a.duration = :duration)")
    List<Activity> findByNameAndDuration(@Param("name") String name, @Param("duration") Integer duration);
}
