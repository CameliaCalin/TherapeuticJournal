package com.example.TherapeuticJournal.api.EntryType;

import com.example.TherapeuticJournal.Repository.EntryTypeRepository;
import com.example.TherapeuticJournal.api.EntryType.dto.CreateEntryTypeDto;
import com.example.TherapeuticJournal.api.EntryType.dto.UpdateEntryTypeDto;
import com.example.TherapeuticJournal.domain.EntryType.EntryType;
import com.example.TherapeuticJournal.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entrytypes")
public class EntryTypeController {

    private final EntryTypeRepository entryTypeRepository;

    @Autowired
    public EntryTypeController(EntryTypeRepository entryTypeRepository) {
        this.entryTypeRepository = entryTypeRepository;
    }

    // Get all EntryTypes
    @GetMapping("/all")
    public List<EntryType> getAllEntryTypes() {
        return entryTypeRepository.findAll();
    }

    // Get a single EntryType by ID
    @GetMapping("/{id}")
    public EntryType getEntryTypeById(@PathVariable Integer id) throws ChangeSetPersister.NotFoundException {
        return entryTypeRepository.findById(id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
    }

    // Create a new EntryType
    @PostMapping("/add")
    public EntryType createEntryType(@RequestBody CreateEntryTypeDto createEntryTypeDto) {
        if (createEntryTypeDto.getType() == null || createEntryTypeDto.getDescription() == null) {
            throw new BadRequestException("Type and Description must not be null");
        }
        EntryType entryType = new EntryType();
        entryType.setType(createEntryTypeDto.getType());
        entryType.setDescription(createEntryTypeDto.getDescription());
        return entryTypeRepository.save(entryType);
    }

    // Update an existing EntryType
    @PutMapping("/{id}")
    public EntryType updateEntryType(@PathVariable Integer id, @RequestBody UpdateEntryTypeDto updateEntryTypeDto) {
        if (updateEntryTypeDto.getType() == null || updateEntryTypeDto.getDescription() == null) {
            throw new BadRequestException("Type and Description must not be null");
        }
        EntryType entryType = entryTypeRepository.findById(id).orElseThrow(() -> new BadRequestException("EntryType not found with ID: " + id));
        entryType.setType(updateEntryTypeDto.getType());
        entryType.setDescription(updateEntryTypeDto.getDescription());
        return entryTypeRepository.save(entryType);
    }

    // Delete an EntryType by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEntryType(@PathVariable Integer id) {
        if (!entryTypeRepository.existsById(id)) {
            throw new BadRequestException("EntryType not found with ID: " + id);
        }
        entryTypeRepository.deleteById(id);
        return ResponseEntity.ok().body("EntryType with ID " + id + " deleted successfully");
    }
}
