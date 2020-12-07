package com.example.demo.controller;

import com.example.demo.dto.MentorDTO;
import com.example.demo.dto.MentoriaDTO;
import com.example.demo.service.MentoriaService;
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
@RequestMapping({"/mentoria"})
public class MentoriaController {

    @Autowired
    private MentoriaService mentoriaService;

    @ApiOperation(value="Retorna mentorias ativas")
    @GetMapping
    public ResponseEntity<List<MentoriaDTO>> getMentorias() throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(mentoriaService.getMentorias());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @ApiOperation(value="Retorna mentoria por id")
    @GetMapping("/{id}")
    public ResponseEntity<MentoriaDTO> getMentoriaByIndex(@PathVariable Long id){
        return mentoriaService.getMentoriaByIndex(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @ApiOperation(value="Cria mentoria")
    @PostMapping
    public ResponseEntity<MentoriaDTO> createMentoria(@RequestBody MentoriaDTO mentoria) {
        return  ResponseEntity.status(HttpStatus.CREATED).body(mentoriaService.createMentoria(mentoria));
    }
    @ApiOperation(value="Deleta mentoria")
    @DeleteMapping("/{id}")
    public ResponseEntity  deleteMentoria (@PathVariable Long id){
        if(mentoriaService.deleteMentoria(id)){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    @ApiOperation(value="Altera mentoria")
    @PutMapping
    public  ResponseEntity<MentoriaDTO>  putMentoria (@RequestBody MentoriaDTO mentoriaDTO){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(mentoriaService.putMentoria(mentoriaDTO));
    }
    @ApiOperation(value="Retorna paginas de mentorias")
    @GetMapping(value = "/paginate")
    public Page<MentoriaDTO> paginateMentoria(@PageableDefault(direction = Sort.Direction.DESC, sort = "id", size = 10) Pageable pageable){
        return mentoriaService.paginateAll(pageable);
    }
}
