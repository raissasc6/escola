package com.example.demo.controller;


import com.example.demo.dto.AlunoDTO;
import com.example.demo.dto.MentorDTO;
import com.example.demo.model.Mentor;
import com.example.demo.service.MentorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping({"/mentor"})
public class MentorController {

    @Autowired
    private MentorService mentorService;


    @ApiOperation(value="retorna mentores ativos")
    @GetMapping
    public ResponseEntity<List<MentorDTO>> getMentores(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(mentorService.getMentores());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @ApiOperation(value="Retorna mentor por id")
    @GetMapping("/{id}")
    public ResponseEntity<MentorDTO> getMentorByIndex(@PathVariable Long id){
        return mentorService.getMentorByIndex(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @ApiOperation(value="Cria mentor")
    @PostMapping
    public ResponseEntity<MentorDTO> createMentor(@RequestBody MentorDTO mentorDTO){
        return  ResponseEntity.status(HttpStatus.CREATED).body(mentorService.createMentor(mentorDTO));
    }
    @ApiOperation(value="Deleta mentor")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteMentor (@PathVariable Long id){
        if(mentorService.deleteMentor(id)){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    @ApiOperation(value="Altera mentor")
    @PutMapping
    public  ResponseEntity<MentorDTO>  putMentor (@RequestBody MentorDTO mentorDTO){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(mentorService.putMentor(mentorDTO));
    }

    @ApiOperation(value="Retorna paginas de mentores")
    @GetMapping(value = "/paginate")
    public Page<MentorDTO> paginateMentor(@PageableDefault(direction = Sort.Direction.DESC, sort = "id", size = 10) Pageable pageable){
        return mentorService.paginateAll(pageable);
    }
}
