package com.example.demo.dto.mapper;

import com.example.demo.dto.MentorDTO;
import com.example.demo.model.Aluno;
import com.example.demo.model.Mentor;

import java.util.ArrayList;
import java.util.List;

public class MentorMapper {

    public static Mentor toMentor(MentorDTO mentorDTO){
        Mentor mentor = new Mentor(mentorDTO.getNome(), mentorDTO.getPais());
        mentor.setId(mentorDTO.getId());
        mentor.setActive(mentor.getActive());
        return mentor;
    }

    public static MentorDTO toMentorDTO(Mentor mentor){
        MentorDTO mentorDTO = new MentorDTO();
        mentorDTO.setId(mentor.getId());
        mentorDTO.setNome(mentor.getNome());
        mentorDTO.setPais(mentor.getPais());
        //Lista de alunos ativos
        List<Long> alunos = new ArrayList<>();
        for (Aluno aluno: mentor.getAlunos()) {
            if(aluno.getActive()){
                alunos.add(aluno.getId());
            }
        }
        mentorDTO.setId_alunos(alunos);
        return mentorDTO;
    }
}