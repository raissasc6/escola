package com.example.demo.dto.mapper;

import com.example.demo.dto.AvaliacaoDTO;
import com.example.demo.model.*;

import java.util.ArrayList;
import java.util.List;

public class AvaliacaoMapper {
    public static Avaliacao toAvaliacao (AvaliacaoDTO avaliacaoDTO){
        Avaliacao avaliacao = new Avaliacao(avaliacaoDTO.getNome(),avaliacaoDTO.getMes());
        avaliacao.setId(avaliacaoDTO.getId());
        avaliacao.setActive(avaliacao.getActive());
        return avaliacao;
    }

    public static Avaliacao toAvaliacao (AvaliacaoDTO avaliacaoDTO, Materia materia){
        Avaliacao avaliacao = new Avaliacao(avaliacaoDTO.getNome(), avaliacaoDTO.getMes(), materia);
        avaliacao.setId(avaliacaoDTO.getId());
        avaliacao.setActive(avaliacao.getActive());
        return avaliacao;
    }


    public static AvaliacaoDTO toAvaliacaoDTO (Avaliacao avaliacao){
        AvaliacaoDTO avaliacaoDTO = new AvaliacaoDTO();
        avaliacaoDTO.setId(avaliacao.getId());
        avaliacaoDTO.setNome(avaliacao.getNome());
        avaliacaoDTO.setMes(avaliacao.getMes());
        if(avaliacao.getMateria() != null){
            avaliacaoDTO.setId_materia(avaliacao.getMateria().getId());
        }
        //Lista de alunos ativos em avaliações ativas
        List<Long> alunos = new ArrayList<>();
        if(avaliacao.getAlunos() != null) {
            for (Avaliacao_Aluno avaliacao_aluno : avaliacao.getAlunos()) {
                if (avaliacao_aluno.getActive()) {
                    alunos.add(avaliacao_aluno.getAluno().getId());
                }
            }
            avaliacaoDTO.setId_alunos(alunos);
        }
        return avaliacaoDTO;
    }
}