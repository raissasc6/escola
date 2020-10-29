package com.example.demo.dto.mapper;

import com.example.demo.dto.MateriaAlunoDTO;
import com.example.demo.model.Aluno;
import com.example.demo.model.Materia;
import com.example.demo.model.Materia_Aluno;

public class MateriaAlunoMapper {

    public static Materia_Aluno toMateriaAluno (MateriaAlunoDTO materiaAlunoDTO, Aluno aluno , Materia materia){
        Materia_Aluno materia_aluno = new Materia_Aluno(aluno, materia);
        materia_aluno.setId(materiaAlunoDTO.getId());
        materia_aluno.setActive(materia_aluno.getActive());
        return materia_aluno;
    }

    public static MateriaAlunoDTO toMateriaAlunoDTO (Materia_Aluno materia_aluno){
        MateriaAlunoDTO materiaAlunoDTO = new MateriaAlunoDTO();
        materiaAlunoDTO.setId(materia_aluno.getId());
        materiaAlunoDTO.setId_aluno(materia_aluno.getAluno().getId());
        materiaAlunoDTO.setId_materia(materia_aluno.getMateria().getId());
        return materiaAlunoDTO;
    }

}
