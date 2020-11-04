package com.example.demo.dto.mapper;

import com.example.demo.dto.AlunoDTO;
import com.example.demo.dto.ProgramaDTO;
import com.example.demo.model.Aluno;
import com.example.demo.model.Programa;

import java.util.ArrayList;
import java.util.List;

public class ProgramaMapper {

    public static Programa toPrograma (ProgramaDTO programaDTO){
        Programa programa = new Programa(programaDTO.getNome(), programaDTO.getAno());
        programa.setId(programaDTO.getId());
        programa.setActive(programa.getActive());
        return programa;
    }

    public static ProgramaDTO toProgramaDTO (Programa programa){
        ProgramaDTO programaDTO = new ProgramaDTO();
        programaDTO.setId(programa.getId());
        programaDTO.setNome(programa.getNome());
        programaDTO.setAno(programa.getAno());
        //Lista de alunos ativos
        List<Long> alunos = new ArrayList<>();
        if(programa.getAlunos() != null) {
            for (Aluno aluno : programa.getAlunos()) {
                if (aluno.getActive()) {
                    alunos.add(aluno.getId());
                }
            }
            programaDTO.setId_alunos(alunos);
        }
        return programaDTO;
    }

}
