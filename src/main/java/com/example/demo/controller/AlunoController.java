package com.example.demo.controller;


import com.example.demo.dto.AlunoDTO;
import com.example.demo.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping({"/aluno"})
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping
    public ResponseEntity<List<AlunoDTO>> getAluno(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(alunoService.getAlunos());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoDTO> getAluno(@PathVariable("id") Long id){
        return alunoService.getAlunoByIndex(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AlunoDTO> postAluno(@RequestBody AlunoDTO dto){
        return  ResponseEntity.status(HttpStatus.CREATED).body(alunoService.criaAluno(dto));
    }

    @DeleteMapping("/{id}")
    //@ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity deleteAlunoByIndex(@PathVariable("id") long id) {
        if(alunoService.deleteAluno(id)){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<AlunoDTO> putAluno(@RequestBody AlunoDTO alunoDTO) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(alunoService.putAluno(alunoDTO));
    }



}
