package com.example.demo.dto.mapper;

import com.example.demo.dto.MateriaAlunoDTO;
import com.example.demo.model.Aluno;
import com.example.demo.model.Materia;
import com.example.demo.model.Materia_Aluno;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MateriaAlunoMapper {
    @Mappings({
            @Mapping(target="id", source="materiaAlunoDTO.id"),
            @Mapping(target="aluno", source="aluno"),
            @Mapping(target="materia", source="materia"),
            @Mapping(target="active", ignore = true)
    })
    Materia_Aluno toMateriaAluno (MateriaAlunoDTO materiaAlunoDTO, Aluno aluno , Materia materia);
    @Mappings({
            @Mapping(target="id", source="materia_aluno.id"),
            @Mapping(target="id_materia", source="materia_aluno.materia.id"),
            @Mapping(target="id_aluno", source="materia_aluno.aluno.id")
    })
    MateriaAlunoDTO toMateriaAlunoDTO (Materia_Aluno materia_aluno);
}
