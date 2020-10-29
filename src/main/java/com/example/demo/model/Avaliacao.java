package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Month;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Avaliacao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Boolean active = true;
    private Month mes;

    @ManyToOne
    @JoinColumn(name = "id_materia")
    private Materia materia;

    @OneToMany(mappedBy = "avaliacao")
    private List<Avaliacao_Aluno> alunos;



    public Avaliacao (String nome,  Month mes){
        this.nome = nome;
        this.mes = mes;
    }

    public Avaliacao (String nome,  Month mes, Materia materia){
        this.nome = nome;
        this.mes = mes;
        this.materia = materia;
    }

}
