package com.example.demo.dto.mapper;

import com.example.demo.dto.MateriaAlunoDTO;
import com.example.demo.dto.MentoriaDTO;
import com.example.demo.model.*;

public class MentoriaMapper {

    public static Mentoria toMentoria (MentoriaDTO mentoriaDTO, Aluno aluno , Mentor mentor){
        Mentoria mentoria = new Mentoria(aluno, mentor);
        mentoria.setId( mentoriaDTO.getId());
        mentoria.setActive( mentoria.getActive());
        return  mentoria;
    }



    public static  MentoriaDTO toMentoriaDTO (Mentoria mentoria){
        MentoriaDTO mentoriaDTO = new MentoriaDTO();
        mentoriaDTO.setId(mentoria.getId());
        mentoriaDTO.setId_mentor(mentoria.getMentor().getId());
        mentoriaDTO.setId_aluno(mentoria.getAluno().getId());
        return mentoriaDTO;
    }
}
