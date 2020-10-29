package com.example.demo.model;


import com.example.demo.dto.AlunoDTO;
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
public class Aluno implements Serializable {

    @Id //para marcar a PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String classe;

    //@Column(nullable = false, columnDefinition = "TINYINT(1) default 1") private Boolean active;
    private Boolean active = true;

    @ManyToOne
    @JoinColumn(name = "id_mentor")
    private Mentor mentor;

    @ManyToOne
    @JoinColumn(name = "id_programa")
    private Programa programa;

    @OneToMany(mappedBy = "aluno")
    private List<Avaliacao_Aluno> avaliacoes;

    @OneToMany(mappedBy = "aluno")
    private List<Materia_Aluno> materias;


    public Aluno(String nome, String classe) {
        this.nome = nome;
        this.classe = classe;
    }

    public Aluno(String nome, String classe, Mentor mentor, Programa programa) {
        this.nome = nome;
        this.classe = classe;
        this.mentor = mentor;
        this.programa = programa;
    }
}
