package com.example.TherapeuticJournal.api.EntryType;

import com.example.TherapeuticJournal.Repository.EntryTypeRepository;
import com.example.TherapeuticJournal.api.EntryType.dto.*;
import com.example.TherapeuticJournal.domain.EntryType.EntryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/entrytypes")
public class EntryTypeController {

    private final EntryTypeRepository entryTypeRepository;

    @Autowired
    public EntryTypeController(EntryTypeRepository entryTypeRepository) {
        this.entryTypeRepository = entryTypeRepository;
    }

    // Create
    @PostMapping("/add")
    public EntryType createEntryType(@RequestBody CreateEntryTypeDto createEntryTypeDto) {
        EntryType entryType = new EntryType();
        entryType.setType(createEntryTypeDto.getType());
        entryType.setDescription(createEntryTypeDto.getDescription());
        return entryTypeRepository.save(entryType);
    }

    // Read All
    @GetMapping("/all")
    public List<ReadEntryTypeDto> getAllEntryTypes() {
        return entryTypeRepository.findAll().stream().map(this::convertToReadDto).collect(Collectors.toList());
    }

    // Read by ID
    @GetMapping("/{id}")
    public ResponseEntity<ReadEntryTypeDto> getEntryTypeById(@PathVariable int id) {
        Optional<EntryType> optionalEntryType = entryTypeRepository.findById(id);
        return optionalEntryType.map(entryType -> ResponseEntity.ok().body(convertToReadDto(entryType)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<EntryType> updateEntryType(@PathVariable int id, @RequestBody UpdateEntryTypeDto updateEntryTypeDto) {
        Optional<EntryType> optionalEntryType = entryTypeRepository.findById(id);
        if (optionalEntryType.isPresent()) {
            EntryType entryType = optionalEntryType.get();
            entryType.setType(updateEntryTypeDto.getType());
            entryType.setDescription(updateEntryTypeDto.getDescription());
            return ResponseEntity.ok().body(convertToDto(entryTypeRepository.save(entryType)));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEntryType(@PathVariable int id) {
        Optional<EntryType> optionalEntryType = entryTypeRepository.findById(id);
        if (optionalEntryType.isPresent()) {
            entryTypeRepository.deleteById(id);
            return ResponseEntity.ok().body("EntryType with ID " + id + " deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Helper method to convert EntryType entity to EntryTypeDto
    private EntryType convertToDto(EntryType entryType) {
        EntryType entryTypeDto = new EntryType();
        entryTypeDto.setId(entryType.getId());
        entryTypeDto.setType(entryType.getType());
        entryTypeDto.setDescription(entryType.getDescription());
        return entryTypeDto;
    }

    // Helper method to convert EntryType entity to ReadEntryTypeDto
    private ReadEntryTypeDto convertToReadDto(EntryType entryType) {
        ReadEntryTypeDto entryTypeDto = new ReadEntryTypeDto();
        entryTypeDto.setId(entryType.getId());
        entryTypeDto.setType(entryType.getType());
        entryTypeDto.setDescription(entryType.getDescription());
        return entryTypeDto;
    }
}
