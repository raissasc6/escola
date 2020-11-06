package com.example.demo.dto.mapper;

import com.example.demo.dto.AlunoDTO;
import com.example.demo.model.Aluno;
import com.example.demo.model.Mentor;
import com.example.demo.model.Programa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AlunoMapper {
    Aluno toAluno(AlunoDTO alunoDTO);
    @Mappings({
            @Mapping(target="id", source="alunoDTO.id"),
            @Mapping(target="nome", source="alunoDTO.nome"),
            @Mapping(target="active", ignore = true)
    })
    Aluno toAluno(AlunoDTO alunoDTO, Mentor mentor, Programa programa);
    @Mappings({
            @Mapping(target="id_mentor", source = "aluno.mentor.id"),
            @Mapping(target="id_programa", source = "aluno.programa.id")
    })
    AlunoDTO toAlunoDTO(Aluno aluno);
}
