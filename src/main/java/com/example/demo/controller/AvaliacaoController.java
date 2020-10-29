package com.example.demo.controller;

import com.example.demo.Exceptions.Exception;
import com.example.demo.dto.AvaliacaoAlunoDTO;
import com.example.demo.dto.AvaliacaoDTO;
import com.example.demo.service.AvaliacaoAlunoService;
import com.example.demo.service.AvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/avaliacao"})
public class AvaliacaoController {

    @Autowired
    AvaliacaoService avaliacaoService;
    @Autowired
    AvaliacaoAlunoService avaliacaoAlunoService;

    @GetMapping
    public ResponseEntity<List<AvaliacaoDTO>> getAvaliacao() throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(avaliacaoService.getAvaliacoes());

    }

    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoDTO> getAvaliacao(@PathVariable("id") Long id){
        // return ResponseEntity.ok(avaliacaoService.getAvaliacaoByIndex(id));
        return avaliacaoService.getAvaliacaoByIndex(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AvaliacaoDTO> postAvaliacao(@RequestBody AvaliacaoDTO dto){
        return  ResponseEntity.status(HttpStatus.CREATED).body(avaliacaoService.createAvaliacao(dto));
    }

    @DeleteMapping("/{id}")
    //@ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deleteAvaliacaoByIndex(@PathVariable("id") long id) {
        avaliacaoService.deleteAvaliacao(id);
        return ResponseEntity.ok("Deletado");
        //lançar not found se não achar ou bad request
    }

    @PutMapping
    public ResponseEntity<AvaliacaoDTO> putAvaliacao(@RequestBody AvaliacaoDTO avaliacaoDTO) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(avaliacaoService.putAvaliacao(avaliacaoDTO));
    }

    //AvaliacaoAluno
    @GetMapping("/aluno")
    public ResponseEntity<List<AvaliacaoAlunoDTO>> getAvaliacaoAluno(){
        return ResponseEntity.status(HttpStatus.OK).body(avaliacaoAlunoService.getAvaliacoesAlunos());
    }
    @GetMapping("/aluno/{id}")
    public ResponseEntity<AvaliacaoAlunoDTO> getAvaliacaoAluno(@PathVariable("id") Long id){
        // return ResponseEntity.ok(avaliacaoService.getAvaliacaoByIndex(id));
        return avaliacaoAlunoService.getAvaliacaoAlunoByIndex(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping("/aluno")
    public ResponseEntity<AvaliacaoAlunoDTO> postAvaliacaoAluno(@RequestBody AvaliacaoAlunoDTO dto) throws Exception {
        return  ResponseEntity.status(HttpStatus.CREATED).body(avaliacaoAlunoService.createAvaliacaoAluno(dto));
    }
    @DeleteMapping("/aluno/{id}")
    //@ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deleteAvaliacaoAlunoByIndex(@PathVariable("id") long id) {
        avaliacaoAlunoService.deleteAvaliacaoAluno(id);
        return ResponseEntity.ok("Deletado");
        //lançar not found se não achar ou bad request
    }

    @PutMapping("/aluno")
    public ResponseEntity<AvaliacaoAlunoDTO> putAvaliacaoAluno(@RequestBody AvaliacaoAlunoDTO avaliacaoAlunoDTO) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(avaliacaoAlunoService.putAvaliacaoAluno(avaliacaoAlunoDTO));
    }
}
