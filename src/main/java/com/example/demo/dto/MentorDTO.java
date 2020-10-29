package com.example.demo.dto;

import com.example.demo.model.Aluno;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.OneToMany;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MentorDTO {

    private Long id;
    private String nome;
    private String pais;
    private List<Long> id_alunos;
}
