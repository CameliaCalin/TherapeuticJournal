package com.example.TherapeuticJournal.api.Emotion;

import com.example.TherapeuticJournal.Repository.CommentRepository;
import com.example.TherapeuticJournal.Repository.CommentRepository;
import com.example.TherapeuticJournal.Repository.EmotionRepository;
import com.example.TherapeuticJournal.api.Emotion.dto.CreateCommentDto;
import com.example.TherapeuticJournal.api.Emotion.dto.CreateCommentDto;
import com.example.TherapeuticJournal.api.Emotion.dto.CreateEmotionDto;
import com.example.TherapeuticJournal.api.Emotion.dto.UpdateEmotionDto;
import com.example.TherapeuticJournal.domain.Emotion.Comment;
import com.example.TherapeuticJournal.domain.Emotion.Emotion;
import com.example.TherapeuticJournal.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emotions")
public class EmotionController {

    private final EmotionRepository emotionRepository;
    private final CommentRepository commentRepository; // Adaugă repository-ul pentru comentarii


       @Autowired
    public EmotionController(EmotionRepository emotionRepository, CommentRepository commentRepository) {
        this.emotionRepository = emotionRepository;
        this.commentRepository = commentRepository; // Initializează repository-ul pentru comentarii
    }

    // Get all Emotions
    @GetMapping("/all")
    public List<Emotion> getAllEmotions() {
        return emotionRepository.findAll();
    }

    // Get a single Emotion by ID
    @GetMapping("/{id}")
    public Emotion getEmotionById(@PathVariable Integer id) throws ChangeSetPersister.NotFoundException {
        return emotionRepository.findById(id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
    }

    // Create a new Emotion
    @PostMapping("/add")
    public Emotion addEmotion(@RequestBody CreateEmotionDto createEmotionDto) {
        if (createEmotionDto.getName() == null || createEmotionDto.getIntensity() == null || createEmotionDto.getDescription() == null) {
            throw new BadRequestException("Name, Intensity, and Description must not be null");
        }
        Emotion emotion = new Emotion();
        emotion.setName(createEmotionDto.getName());
        emotion.setIntensity(createEmotionDto.getIntensity());
        emotion.setDescription(createEmotionDto.getDescription());
        return emotionRepository.save(emotion);
    }

    // Update an existing Emotion
    @PutMapping("/{id}")
    public Emotion updateEmotion(@PathVariable Integer id, @RequestBody UpdateEmotionDto updateEmotionDto) {
        if (updateEmotionDto.getName() == null || updateEmotionDto.getIntensity() == null || updateEmotionDto.getDescription() == null) {
            throw new BadRequestException("Name, Intensity, and Description must not be null");
        }
        Emotion emotion = emotionRepository.findById(id).orElseThrow(() -> new BadRequestException("Emotion not found with ID: " + id));
        emotion.setName(updateEmotionDto.getName());
        emotion.setIntensity(updateEmotionDto.getIntensity());
        emotion.setDescription(updateEmotionDto.getDescription());
        return emotionRepository.save(emotion);
    }

    // Delete an Emotion by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmotion(@PathVariable Integer id) {
        if (!emotionRepository.existsById(id)) {
            throw new BadRequestException("Emotion not found with ID: " + id);
        }
        emotionRepository.deleteById(id);
        return ResponseEntity.ok().body("Emotion with ID " + id + " deleted successfully");
    }

    @GetMapping
    public List<Emotion> getEmotionsByCriteria(@RequestParam(value = "name", required = false) String name,
                                               @RequestParam(value = "intensity", required = false) String intensity,
                                               @RequestParam(value = "description", required = false) String description) {
        if (name != null || intensity != null || description != null) {
            // Folosim metoda findBy din repository pentru a crea interogarea pe baza criteriilor
            return emotionRepository.findByCriteria(name, Integer.valueOf(intensity), description);
        } else {
            // Dacă nu sunt specificate criterii, returnăm toate emoțiile
            return emotionRepository.findAll();
        }
    }

    // Adăugarea unui comentariu la o emoție existentă
    @PostMapping("/{emotionId}/comments")
    public ResponseEntity<Comment> addCommentToEmotion(@PathVariable Integer emotionId, @RequestBody CreateCommentDto createCommentDto) throws ChangeSetPersister.NotFoundException {
        Emotion emotion = emotionRepository.findById(emotionId).orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        Comment comment = new Comment();
        comment.setText(createCommentDto.getText());
        comment.setEmotion(emotion);

        Comment savedComment = commentRepository.save(comment);

        return ResponseEntity.ok().body(savedComment);
    }

    // Obținerea tuturor comentariilor unei emoții după id-ul emoției
    @GetMapping("/{emotionId}/comments")
    public List<Comment> getCommentsByEmotionId(@PathVariable Integer emotionId) {
        return commentRepository.findCommentsByEmotionId(emotionId);
    }

}
