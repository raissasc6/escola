package com.example.demo.dto;

import com.example.demo.model.Aluno;
import com.example.demo.model.Materia;
import com.example.demo.model.Mentor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MateriaDTO {

    private Long id;
    private String nome;
    private Long id_mentor;
    private List<Long> id_avaliacoes;
    private List<Long> id_alunos;


}
