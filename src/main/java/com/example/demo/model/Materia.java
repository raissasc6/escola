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
public class Materia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Boolean active = true;


    @ManyToOne
    @JoinColumn(name = "id_mentor")
    private Mentor mentor;

    @OneToMany(mappedBy = "materia")
    private List<Avaliacao> avaliacoes;

    @OneToMany(mappedBy = "materia")
    private List<Materia_Aluno> alunos;



    public Materia (String nome){
        this.nome = nome;
    }

    public Materia (String nome, Mentor mentor){
        this.nome = nome;
        this.mentor = mentor;
    }

}
