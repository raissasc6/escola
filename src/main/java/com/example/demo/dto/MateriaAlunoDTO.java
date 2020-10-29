package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MateriaAlunoDTO {
    private Long id;
    private Long id_aluno;
    private Long id_materia;
}
