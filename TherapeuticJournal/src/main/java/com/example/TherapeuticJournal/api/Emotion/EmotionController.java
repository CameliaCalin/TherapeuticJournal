package com.example.TherapeuticJournal.api.Emotion;

import com.example.TherapeuticJournal.Repository.EmotionRepository;
import com.example.TherapeuticJournal.api.Emotion.dto.CreateEmotionDto;
import com.example.TherapeuticJournal.api.Emotion.dto.UpdateEmotionDto;
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

    @Autowired
    public EmotionController(EmotionRepository emotionRepository) {
        this.emotionRepository = emotionRepository;
    }

    @GetMapping("/all")
    public List<Emotion> getAllEmotions() {
        return emotionRepository.findAll();
    }

    @GetMapping("/{id}")
    public Emotion getEmotionById(@PathVariable Integer id) throws ChangeSetPersister.NotFoundException {
        return emotionRepository.findById(id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
    }

    @PostMapping("/add")
    public Emotion addEmotion(@RequestBody CreateEmotionDto createEmotionDto) {
        Emotion emotion = new Emotion();
        emotion.setName(createEmotionDto.getName());
        emotion.setIntensity(createEmotionDto.getIntensity());
        emotion.setDescription(createEmotionDto.getDescription());
        return emotionRepository.save(emotion);
    }

    @PutMapping("/{id}")
    public Emotion updateEmotion(@PathVariable Integer id, @RequestBody UpdateEmotionDto updateEmotionDto) {
        Emotion emotion = emotionRepository.findById(id).orElseThrow(() -> new BadRequestException("Emotion not found with ID: " + id));
        emotion.setName(updateEmotionDto.getName());
        emotion.setIntensity(updateEmotionDto.getIntensity());
        emotion.setDescription(updateEmotionDto.getDescription());
        return emotionRepository.save(emotion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmotion(@PathVariable Integer id) {
        emotionRepository.deleteById(id);
        return ResponseEntity.ok().body("Emotion with ID " + id + " deleted successfully");
    }


}
