package com.example.demo.controller;

import com.example.demo.dto.MateriaAlunoDTO;
import com.example.demo.dto.MateriaDTO;
import com.example.demo.service.MateriaAlunoService;
import com.example.demo.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/materia"})
public class MateriaController {

    @Autowired
    MateriaService materiaService;
    @Autowired
    MateriaAlunoService materiaAlunoService;

    @GetMapping
    public ResponseEntity<List<MateriaDTO>> getMaterias() throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(materiaService.getMaterias());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MateriaDTO> getMateria(@PathVariable("id") Long id){
        // return ResponseEntity.ok(materiaService.getMateriaByIndex(id));
        return materiaService.getMateriaByIndex(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MateriaDTO> postMateria(@RequestBody MateriaDTO dto){
        return  ResponseEntity.status(HttpStatus.CREATED).body(materiaService.createMateria(dto));
    }

    @DeleteMapping("/{id}")
    //@ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity deleteMateriaByIndex(@PathVariable("id") long id) {
        if(materiaService.deleteMateria(id)){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
        //lançar not found se não achar ou bad request
    }

    @PutMapping
    public ResponseEntity<MateriaDTO> putMateria(@RequestBody MateriaDTO materiaDTO) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(materiaService.putMateria(materiaDTO));
    }

    //Materia aluno

    @GetMapping("/aluno")
    public ResponseEntity<List<MateriaAlunoDTO>> getMateriasAlunos() throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(materiaAlunoService.getMateriaAluno());

    }

    @GetMapping("/aluno/{id}")
    public ResponseEntity<MateriaAlunoDTO> getMateriaAluno(@PathVariable("id") Long id){
        return materiaAlunoService.getMateriaAlunoByIndex(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/aluno")
    public ResponseEntity<MateriaAlunoDTO> postMateriaAluno(@RequestBody MateriaAlunoDTO dto)  {
        return  ResponseEntity.status(HttpStatus.CREATED).body(materiaAlunoService.createMateriaAluno(dto));
    }

    @DeleteMapping("/aluno")
    //@ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity deleteMateriaAlunoByIndex(@PathVariable("id") long id) {
        if(materiaAlunoService.deleteMateriaAluno(id)){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/aluno")
    public ResponseEntity<MateriaAlunoDTO> putMateriaAluno(@RequestBody MateriaAlunoDTO materiaAlunoDTO) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(materiaAlunoService.putMateriaAluno(materiaAlunoDTO));
    }

}
