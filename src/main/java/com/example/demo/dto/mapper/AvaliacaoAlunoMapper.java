package com.example.demo.dto.mapper;


import com.example.demo.dto.AvaliacaoAlunoDTO;
import com.example.demo.model.Aluno;
import com.example.demo.model.Avaliacao;
import com.example.demo.model.Avaliacao_Aluno;

public class AvaliacaoAlunoMapper {

    public static Avaliacao_Aluno toAvaliacaoAluno (AvaliacaoAlunoDTO avaliacaoAlunoDTO,Aluno aluno, Avaliacao avaliacao){
        //busca aluno
        Avaliacao_Aluno avaliacaoAluno = new Avaliacao_Aluno(aluno, avaliacao, avaliacaoAlunoDTO.getNota());
        avaliacaoAluno.setId(avaliacaoAlunoDTO.getId());
        avaliacaoAluno.setActive(avaliacao.getActive());
        return avaliacaoAluno;
    }

    public static  AvaliacaoAlunoDTO toAvaliacaoAlunoDTO (Avaliacao_Aluno avaliacao_aluno){
        AvaliacaoAlunoDTO avaliacaoAlunoDTO = new AvaliacaoAlunoDTO();
        avaliacaoAlunoDTO.setId(avaliacao_aluno.getId());
        avaliacaoAlunoDTO.setId_aluno(avaliacao_aluno.getAluno().getId());
        avaliacaoAlunoDTO.setId_avaliacao(avaliacao_aluno.getAvaliacao().getId());
        avaliacaoAlunoDTO.setNota(avaliacao_aluno.getNota());
        return avaliacaoAlunoDTO;
    }
}
