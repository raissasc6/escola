package com.example.demo.dto.mapper;

import com.example.demo.dto.AvaliacaoAlunoDTO;
import com.example.demo.model.Aluno;
import com.example.demo.model.Avaliacao;
import com.example.demo.model.Avaliacao_Aluno;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AvaliacaoAlunoMapper {
    @Mappings({
            @Mapping(target="id", source="avaliacaoAlunoDTO.id"),
            @Mapping(target="active", ignore = true)
    })
    Avaliacao_Aluno toAvaliacaoAluno (AvaliacaoAlunoDTO avaliacaoAlunoDTO, Aluno aluno, Avaliacao avaliacao);
    @Mappings({
            @Mapping(target="id", source="avaliacao_aluno.id"),
            @Mapping(target="id_avaliacao", source="avaliacao_aluno.avaliacao.id"),
            @Mapping(target="id_aluno", source="avaliacao_aluno.aluno.id")
    })
    AvaliacaoAlunoDTO toAvaliacaoAlunoDTO (Avaliacao_Aluno avaliacao_aluno);
}
