package com.example.demo.controller;

import com.example.demo.dto.MentorDTO;
import com.example.demo.dto.MentoriaDTO;
import com.example.demo.model.Mentor;
import com.example.demo.model.Mentoria;
import com.example.demo.service.MentoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/mentoria"})
public class MentoriaController {

    @Autowired
    private MentoriaService mentoriaService;

    @GetMapping
    public ResponseEntity<List<MentoriaDTO>> getMentorias() throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(mentoriaService.getMentorias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<MentoriaDTO>> getMentoriaByIndex(@PathVariable Long id){
        return ResponseEntity.ok(mentoriaService.getMentoriaByIndex(id));
    }

    @PostMapping
    public ResponseEntity<MentoriaDTO> createMentoria(@RequestBody MentoriaDTO mentoria) {

            mentoriaService.createMentoria(mentoria);
            return ResponseEntity.ok().build();

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteMentoria (@PathVariable Long id){
        mentoriaService.deleteMentoria(id);

    }

}
