package com.example.demo.dto;

import com.example.demo.model.Aluno;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlunoDTO {

    private Long id;
    private String nome;
    private String classe;
    private Long id_mentor;
    private Long id_programa;

}
