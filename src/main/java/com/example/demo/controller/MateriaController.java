package com.example.demo.controller;

import com.example.demo.dto.MateriaAlunoDTO;
import com.example.demo.dto.MateriaDTO;
import com.example.demo.dto.ProgramaDTO;
import com.example.demo.service.MateriaAlunoService;
import com.example.demo.service.MateriaService;
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
@RequestMapping({"/materia"})
public class MateriaController {

    @Autowired
    MateriaService materiaService;
    @Autowired
    MateriaAlunoService materiaAlunoService;

    @ApiOperation(value="Retorna materias ativas")
    @GetMapping
    public ResponseEntity<List<MateriaDTO>> getMaterias() throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(materiaService.getMaterias());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @ApiOperation(value="Retorna materias por ID")
    @GetMapping("/{id}")
    public ResponseEntity<MateriaDTO> getMateria(@PathVariable("id") Long id){
        return materiaService.getMateriaByIndex(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @ApiOperation(value="Cria materias")
    @PostMapping
    public ResponseEntity<MateriaDTO> postMateria(@RequestBody MateriaDTO dto){
        return  ResponseEntity.status(HttpStatus.CREATED).body(materiaService.createMateria(dto));
    }

    @ApiOperation(value="Deleta materia")
    @DeleteMapping("/{id}")
    //@ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity deleteMateriaByIndex(@PathVariable("id") long id) {
        if(materiaService.deleteMateria(id)){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    @ApiOperation(value="Altera materias-aluno")
    @PutMapping
    public ResponseEntity<MateriaDTO> putMateria(@RequestBody MateriaDTO materiaDTO) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(materiaService.putMateria(materiaDTO));
    }

    @ApiOperation(value="Retorna paginas de materias")
    @GetMapping(value = "/paginate")
    public Page<MateriaDTO> paginateMateria(@PageableDefault(direction = Sort.Direction.DESC, sort = "id", size = 10) Pageable pageable){
        return materiaService.paginateAll(pageable);
    }


    //Materia aluno
    @ApiOperation(value="Retorna materias-alunos ativas")
    @GetMapping("/aluno")
    public ResponseEntity<List<MateriaAlunoDTO>> getMateriasAlunos() throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(materiaAlunoService.getMateriaAluno());

    }
    @ApiOperation(value="Retorna materias-aluno por ID")
    @GetMapping("/aluno/{id}")
    public ResponseEntity<MateriaAlunoDTO> getMateriaAluno(@PathVariable("id") Long id){
        return materiaAlunoService.getMateriaAlunoByIndex(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @ApiOperation(value="Cria materias-aluno")
    @PostMapping("/aluno")
    public ResponseEntity<MateriaAlunoDTO> postMateriaAluno(@RequestBody MateriaAlunoDTO dto)  {
        return  ResponseEntity.status(HttpStatus.CREATED).body(materiaAlunoService.createMateriaAluno(dto));
    }
    @ApiOperation(value="Deleta materias-aluno")
    @DeleteMapping("/aluno")
    public ResponseEntity deleteMateriaAlunoByIndex(@PathVariable("id") long id) {
        if(materiaAlunoService.deleteMateriaAluno(id)){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    @ApiOperation(value="Altera materias-aluno")
    @PutMapping("/aluno")
    public ResponseEntity<MateriaAlunoDTO> putMateriaAluno(@RequestBody MateriaAlunoDTO materiaAlunoDTO) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(materiaAlunoService.putMateriaAluno(materiaAlunoDTO));
    }

    @ApiOperation(value="Retorna paginas de materia-aluno")
    @GetMapping(value = "/aluno/paginate")
    public Page<MateriaAlunoDTO> paginateMateriaAluno(@PageableDefault(direction = Sort.Direction.DESC, sort = "id", size = 10) Pageable pageable){
        return materiaAlunoService.paginateAll(pageable);
    }

}
