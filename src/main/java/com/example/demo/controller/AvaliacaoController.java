package com.example.demo.controller;

import com.example.demo.dto.AvaliacaoAlunoDTO;
import com.example.demo.dto.AvaliacaoDTO;
import com.example.demo.dto.MateriaAlunoDTO;
import com.example.demo.service.AvaliacaoAlunoService;
import com.example.demo.service.AvaliacaoService;
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
@RequestMapping({"/avaliacao"})
public class AvaliacaoController {

    @Autowired
    AvaliacaoService avaliacaoService;
    @Autowired
    AvaliacaoAlunoService avaliacaoAlunoService;

    @ApiOperation(value="Retorna avaliacao ativos")
    @GetMapping
    public ResponseEntity<List<AvaliacaoDTO>> getAvaliacao() throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(avaliacaoService.getAvaliacoes());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }
    @ApiOperation(value="Retorna avaliacao id")
    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoDTO> getAvaliacao(@PathVariable("id") Long id){
        return avaliacaoService.getAvaliacaoByIndex(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @ApiOperation(value="Cria avaliacao")
    @PostMapping
    public ResponseEntity<AvaliacaoDTO> postAvaliacao(@RequestBody AvaliacaoDTO dto){
        return  ResponseEntity.status(HttpStatus.CREATED).body(avaliacaoService.createAvaliacao(dto));
    }
    @ApiOperation(value="Deleta avaliacao")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteAvaliacaoByIndex(@PathVariable("id") long id) {
        if(avaliacaoService.deleteAvaliacao(id)){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    @ApiOperation(value="Altera avaliacao")
    @PutMapping
    public ResponseEntity<AvaliacaoDTO> putAvaliacao(@RequestBody AvaliacaoDTO avaliacaoDTO) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(avaliacaoService.putAvaliacao(avaliacaoDTO));
    }
    @ApiOperation(value="Retorna paginas de avaliacao")
    @GetMapping(value = "/paginate")
    public Page<AvaliacaoDTO> paginateAvaliacao(@PageableDefault(direction = Sort.Direction.DESC, sort = "id", size = 10) Pageable pageable){
        return avaliacaoService.paginateAll(pageable);
    }

    //AvaliacaoAluno
    @ApiOperation(value="Retorna  avaliacao-aluno ativos")
    @GetMapping("/aluno")
    public ResponseEntity<List<AvaliacaoAlunoDTO>> getAvaliacaoAluno(){
        return ResponseEntity.status(HttpStatus.OK).body(avaliacaoAlunoService.getAvaliacoesAlunos());
    }
    @ApiOperation(value="Retorna avaliacao-aluno por id")
    @GetMapping("/aluno/{id}")
    public ResponseEntity<AvaliacaoAlunoDTO> getAvaliacaoAluno(@PathVariable("id") Long id){
        // return ResponseEntity.ok(avaliacaoService.getAvaliacaoByIndex(id));
        return avaliacaoAlunoService.getAvaliacaoAlunoByIndex(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @ApiOperation(value="Cria avaliacao-aluno")
    @PostMapping("/aluno")
    public ResponseEntity<AvaliacaoAlunoDTO> postAvaliacaoAluno(@RequestBody AvaliacaoAlunoDTO dto) throws Exception {
        return  ResponseEntity.status(HttpStatus.CREATED).body(avaliacaoAlunoService.createAvaliacaoAluno(dto));
    }
    @ApiOperation(value="Deleta avaliacao-aluno")
    @DeleteMapping("/aluno/{id}")
    public ResponseEntity deleteAvaliacaoAlunoByIndex(@PathVariable("id") long id) {
        if(avaliacaoAlunoService.deleteAvaliacaoAluno(id)){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    @ApiOperation(value="Altera avaliacao-aluno")
    @PutMapping("/aluno")
    public ResponseEntity<AvaliacaoAlunoDTO> putAvaliacaoAluno(@RequestBody AvaliacaoAlunoDTO avaliacaoAlunoDTO) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(avaliacaoAlunoService.putAvaliacaoAluno(avaliacaoAlunoDTO));
    }

    @ApiOperation(value="Retorna paginas de avaliacao-aluno")
    @GetMapping(value = "/aluno/paginate")
    public Page<AvaliacaoAlunoDTO> paginateAvaliacaoAluno(@PageableDefault(direction = Sort.Direction.DESC, sort = "id", size = 10) Pageable pageable){
        return avaliacaoAlunoService.paginateAll(pageable);
    }
}
