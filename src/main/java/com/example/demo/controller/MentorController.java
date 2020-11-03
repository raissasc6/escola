package com.example.demo.controller;


import com.example.demo.dto.AlunoDTO;
import com.example.demo.dto.MentorDTO;
import com.example.demo.model.Mentor;
import com.example.demo.service.MentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/mentor"})
public class MentorController {

    @Autowired
    private MentorService mentorService;

    @GetMapping
    public ResponseEntity<List<MentorDTO>> getMentores(){
        return new ResponseEntity<List<MentorDTO>>(mentorService.getMentores(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MentorDTO> getMentorByIndex(@PathVariable Long id){
        return mentorService.getMentorByIndex(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MentorDTO> createMentor(@RequestBody MentorDTO mentorDTO){
        return  ResponseEntity.status(HttpStatus.CREATED).body(mentorService.createMentor(mentorDTO));
    }

    @DeleteMapping("/{id}")
    //@ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity deleteMentor (@PathVariable Long id){
        if(mentorService.deleteMentor(id)){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public  ResponseEntity<MentorDTO>  putMentor (@RequestBody MentorDTO mentorDTO){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(mentorService.putMentor(mentorDTO));
    }

}
