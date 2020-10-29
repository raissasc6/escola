package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Mentor implements  Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String pais;
    private Boolean active = true;
    @OneToMany(mappedBy = "mentor")
    private List<Aluno> alunos;

    public Mentor(String nome, String pais) {
        this.nome = nome;
        this.pais = pais;
    }


}
