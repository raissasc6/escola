package com.example.demo.dto.mapper;

import com.example.demo.dto.MentoriaDTO;
import com.example.demo.model.Aluno;
import com.example.demo.model.Mentor;
import com.example.demo.model.Mentoria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MentoriaMapper {
    @Mappings({
            @Mapping(target="id", source="mentoriaDTO.id"),
            @Mapping(target="aluno", source="aluno"),
            @Mapping(target="mentor", source="mentor"),
            @Mapping(target="active", ignore = true)
    })
   Mentoria toMentoria (MentoriaDTO mentoriaDTO, Aluno aluno , Mentor mentor);
    @Mappings({
            @Mapping(target="id", source="mentoria.id"),
            @Mapping(target="id_aluno", source="mentoria.aluno.id"),
            @Mapping(target="id_mentor", source="mentoria.mentor.id"),
    })
   MentoriaDTO toMentoriaDTO (Mentoria mentoria);
}
