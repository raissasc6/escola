package com.example.demo.dto.mapper;

import com.example.demo.dto.AlunoDTO;
import com.example.demo.model.Aluno;
import com.example.demo.model.Mentor;
import com.example.demo.model.Programa;

public class AlunoMapper {
    public static Aluno toAluno (AlunoDTO alunoDTO){
        Aluno aluno = new Aluno(alunoDTO.getNome(), alunoDTO.getClasse());
        aluno.setId(alunoDTO.getId());
        aluno.setActive(aluno.getActive());
        return aluno;
    }

    public static Aluno toAluno (AlunoDTO alunoDTO, Mentor mentor, Programa programa){
        Aluno aluno = new Aluno(alunoDTO.getNome(), alunoDTO.getClasse(), mentor, programa);
        aluno.setId(alunoDTO.getId());
        aluno.setActive(aluno.getActive());
        return aluno;
    }

    public static AlunoDTO toAlunoDTO(Aluno aluno){
        AlunoDTO alunoDTO = new AlunoDTO();
        alunoDTO.setId(aluno.getId());
        alunoDTO.setNome(aluno.getNome());
        alunoDTO.setClasse(aluno.getClasse());
        if(aluno.getMentor() == null){
                alunoDTO.setId_mentor(null);
        }else{
            alunoDTO.setId_mentor(aluno.getMentor().getId());
        }
        if(aluno.getPrograma() == null){
            alunoDTO.setId_programa(null);
        }else{
            alunoDTO.setId_programa(aluno.getPrograma().getId());
        }
        return alunoDTO;
    }
}
