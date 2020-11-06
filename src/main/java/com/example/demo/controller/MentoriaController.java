package com.example.demo.controller;

import com.example.demo.dto.MentoriaDTO;
import com.example.demo.service.MentoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/mentoria"})
public class MentoriaController {

    @Autowired
    private MentoriaService mentoriaService;

    @GetMapping
    public ResponseEntity<List<MentoriaDTO>> getMentorias() throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(mentoriaService.getMentorias());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MentoriaDTO> getMentoriaByIndex(@PathVariable Long id){
        return mentoriaService.getMentoriaByIndex(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MentoriaDTO> createMentoria(@RequestBody MentoriaDTO mentoria) {
        return  ResponseEntity.status(HttpStatus.CREATED).body(mentoriaService.createMentoria(mentoria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity  deleteMentoria (@PathVariable Long id){
        if(mentoriaService.deleteMentoria(id)){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public  ResponseEntity<MentoriaDTO>  putMentoria (@RequestBody MentoriaDTO mentoriaDTO){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(mentoriaService.putMentoria(mentoriaDTO));
    }

}
