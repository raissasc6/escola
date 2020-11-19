package com.example.demo.controller;

import com.example.demo.dto.ProgramaDTO;
import com.example.demo.model.Programa;
import com.example.demo.model.Programa;
import com.example.demo.service.ProgramaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/programa")
public class ProgramaController {

    @Autowired
    ProgramaService programaService;

    @GetMapping
    public ResponseEntity<List<ProgramaDTO>> getProgramas(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(programaService.getProgramas());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgramaDTO> getPrograma(@PathVariable Long id){
        return programaService.getProgramaByIndex(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProgramaDTO> createPrograma(@RequestBody ProgramaDTO programaDTO){
        return  ResponseEntity.status(HttpStatus.CREATED).body( programaService.createPrograma(programaDTO));
    }

    @DeleteMapping("/{id}")
    //@ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity deletePrograma (@PathVariable Long id){
        if(programaService.deletePrograma(id)){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public  ResponseEntity<ProgramaDTO> putPrograma (@RequestBody ProgramaDTO programaDTO){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body( programaService.putPrograma(programaDTO));
    }

}
