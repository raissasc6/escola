package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Programa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    //@DateTimeFormat(pattern = "yyyy")
    //@Column(name = "ano", precision = 4)
    //private String ano;
    private Year ano;
    private Boolean active = true;

    @OneToMany(mappedBy = "programa")
    private List<Aluno> alunos;

    public Programa (String nome, Year ano){
        this.nome = nome;
        this.ano = ano;
    }
}
