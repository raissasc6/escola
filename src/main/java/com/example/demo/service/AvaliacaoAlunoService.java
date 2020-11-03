package com.example.demo.service;

import com.example.demo.dto.AvaliacaoAlunoDTO;
import com.example.demo.dto.mapper.AlunoMapper;
import com.example.demo.dto.mapper.AvaliacaoAlunoMapper;
import com.example.demo.dto.mapper.AvaliacaoMapper;
import com.example.demo.model.Aluno;
import com.example.demo.model.Avaliacao;
import com.example.demo.model.Avaliacao_Aluno;
import com.example.demo.repository.Avaliacao_AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AvaliacaoAlunoService {

    @Autowired
    Avaliacao_AlunoRepository avaliacaoAlunoRepository;

    @Autowired
    AvaliacaoService avaliacaoService;
    @Autowired
    AlunoService alunoService;

    public List<AvaliacaoAlunoDTO> getAvaliacoesAlunos (){
        return avaliacaoAlunoRepository.findByActive(true).get().parallelStream().map(AvaliacaoAlunoMapper::toAvaliacaoAlunoDTO).collect(Collectors.toList());
    }

    public Optional<AvaliacaoAlunoDTO> getAvaliacaoAlunoByIndex(Long id){
        return  avaliacaoAlunoRepository.findByActiveAndId(true,id).map(AvaliacaoAlunoMapper::toAvaliacaoAlunoDTO);
    }

    public List<AvaliacaoAlunoDTO> getAvaliacoesAlunoByAluno(Aluno aluno){
        return  avaliacaoAlunoRepository.findByActiveAndAluno(true,aluno).get().parallelStream().map(AvaliacaoAlunoMapper::toAvaliacaoAlunoDTO).collect(Collectors.toList());
    }

    public List<AvaliacaoAlunoDTO> getAvaliacoesAlunoByAvaliacao(Avaliacao avaliacao){
        return  avaliacaoAlunoRepository.findByActiveAndAvaliacao(true,avaliacao).get().parallelStream().map(AvaliacaoAlunoMapper::toAvaliacaoAlunoDTO).collect(Collectors.toList());
    }

    public AvaliacaoAlunoDTO createAvaliacaoAluno(AvaliacaoAlunoDTO avaliacaoAlunoDTO) throws Exception{
        //exite aluno e avaliacao
        avaliacaoService.getAvaliacaoByIndex(avaliacaoAlunoDTO.getId_avaliacao()).ifPresentOrElse(
                avaliacaoDTO -> {
                        //se existe aluno

                        alunoService.getAlunoByIndex(avaliacaoAlunoDTO.getId_aluno()).ifPresentOrElse(
                                alunoDTO -> {
                                    avaliacaoAlunoDTO.setId(avaliacaoAlunoRepository.save(AvaliacaoAlunoMapper.toAvaliacaoAluno(avaliacaoAlunoDTO, AlunoMapper.toAluno(alunoDTO), AvaliacaoMapper.toAvaliacao(avaliacaoDTO))).getId());
                                },()->{System.out.println("Aluno não encontrado");});

                }, ()-> {System.out.println("Avaliação não encontrada");});
        return avaliacaoAlunoDTO;
    }

    public void deleteAvaliacaoAluno(Long id){
        avaliacaoAlunoRepository.findByActiveAndId(true,id).ifPresentOrElse(
                avaliacao_aluno -> {
                    avaliacao_aluno.setActive(false);
                    avaliacaoAlunoRepository.save(avaliacao_aluno);
                },() -> {System.out.println("Avaliação de Aluno não encontrada");});
    }

    public AvaliacaoAlunoDTO putAvaliacaoAluno(AvaliacaoAlunoDTO avaliacaoAlunoDTO){
        avaliacaoAlunoRepository.findByActiveAndId(true,avaliacaoAlunoDTO.getId()).ifPresentOrElse(
                avaliacao_aluno -> {
                    //se aluno

                        alunoService.getAlunoByIndex(avaliacaoAlunoDTO.getId_aluno()).ifPresentOrElse(alunoDTO -> {
                            //se avaliacao
                            avaliacaoService.getAvaliacaoByIndex(avaliacaoAlunoDTO.getId_avaliacao()).ifPresentOrElse(avaliacaoDTO -> {
                                avaliacaoAlunoRepository.save(
                                        AvaliacaoAlunoMapper.toAvaliacaoAluno(avaliacaoAlunoDTO,AlunoMapper.toAluno(alunoDTO),AvaliacaoMapper.toAvaliacao(avaliacaoDTO)));
                            },()->{System.out.println("Avaliação não encontrada");});
                        },()->{System.out.println("Aluno nao encontrado");});

                }, ()->{System.out.println("Avaliacao de aluno não encontrado");});
        return avaliacaoAlunoDTO;
    }

    protected void deleteAvaliacoesByAluno(Aluno aluno){
        avaliacaoAlunoRepository.findByActiveAndAluno(true,aluno).ifPresentOrElse(
                avaliacao_alunos -> {
                    for (Avaliacao_Aluno avaliacaoAluno: avaliacao_alunos   ) {
                        avaliacaoAluno.setActive(false);
                        avaliacaoAlunoRepository.save(avaliacaoAluno);
                    }
                },()-> {
                    //Aluno não possui avaliação
                });
    }

    protected void deleteAvaliacoesByAvaliacao(Avaliacao avaliacao){
        avaliacaoAlunoRepository.findByActiveAndAvaliacao(true,avaliacao).ifPresentOrElse(
                avaliacao_alunos -> {
                    for (Avaliacao_Aluno avaliacaoAluno: avaliacao_alunos   ) {
                        avaliacaoAluno.setActive(false);
                        avaliacaoAlunoRepository.save(avaliacaoAluno);
                    }
                },()-> {
                    //Aluno não possui avaliação
                });
    }
}
