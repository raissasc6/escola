package com.example.demo.dto;

import com.example.demo.model.Aluno;
import com.example.demo.model.Materia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Month;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvaliacaoDTO {

    private Long id;
    private String nome;
    private Month mes;
    private Long id_materia;
    private List<Long> id_alunos;
}
