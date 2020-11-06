package com.example.demo.dto.mapper;

import com.example.demo.dto.MateriaDTO;
import com.example.demo.dto.MentorDTO;
import com.example.demo.model.*;

import java.util.ArrayList;
import java.util.List;

public class MateriaMapper {
    public static Materia toMateria (MateriaDTO materiaDTO){
        Materia materia = new Materia(materiaDTO.getNome());
        materia.setId(materiaDTO.getId());
        materia.setActive(materia.getActive());
        return materia;
    }

    public static Materia toMateria (MateriaDTO materiaDTO, Mentor mentor){
        Materia materia = new Materia(materiaDTO.getNome(), mentor);
        materia.setId(materiaDTO.getId());
        materia.setActive(materia.getActive());
        return materia;
    }


    public static MateriaDTO toMateriaDTO (Materia materia){
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setId(materia.getId());
        materiaDTO.setNome(materia.getNome());
        //se mentor
        if(materia.getMentor() == null){
            materiaDTO.setId_mentor(null);
        }else{
            materiaDTO.setId_mentor(materia.getMentor().getId());
        }
        //Lista de alunos ativos em materias q está ativo
        List<Long> alunos = new ArrayList<>();
        for (Materia_Aluno materia_aluno: materia.getAlunos()) {
            if(materia_aluno.getActive()){
                alunos.add(materia_aluno.getAluno().getId());
            }
        }
        materiaDTO.setId_alunos(alunos);
        //Lista de Avaliações ativos em materias q está ativo
        List<Long> avaliacoes = new ArrayList<>();
        for (Avaliacao avaliacao: materia.getAvaliacoes()) {
            if(avaliacao.getActive()){
                avaliacoes.add(avaliacao.getId());
            }
        }
        materiaDTO.setId_avaliacoes(avaliacoes);
        return materiaDTO;
    }
}