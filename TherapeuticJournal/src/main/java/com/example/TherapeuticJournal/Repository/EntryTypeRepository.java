package com.example.TherapeuticJournal.Repository;
import com.example.TherapeuticJournal.domain.EntryType.EntryType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryTypeRepository extends JpaRepository<EntryType, Integer> {
}
