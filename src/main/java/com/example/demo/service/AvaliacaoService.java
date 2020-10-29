package com.example.demo.service;

import com.example.demo.dto.AvaliacaoDTO;
import com.example.demo.dto.mapper.AvaliacaoMapper;
import com.example.demo.dto.mapper.MateriaMapper;
import com.example.demo.model.Avaliacao;
import com.example.demo.model.Materia;
import com.example.demo.repository.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AvaliacaoService {
    @Autowired
    AvaliacaoRepository avaliacaoRepository;

    @Autowired
    AvaliacaoAlunoService avaliacaoAlunoService;

    @Autowired
    MateriaService materiaService;

    public List<AvaliacaoDTO> getAvaliacoes(){
        return avaliacaoRepository.findByActive(true).get().parallelStream().map(AvaliacaoMapper::toAvaliacaoDTO).collect(Collectors.toList());
    }

    public Optional<AvaliacaoDTO> getAvaliacaoByIndex(Long id){
        return avaliacaoRepository.findByActiveAndId(true,id).map(AvaliacaoMapper::toAvaliacaoDTO);
    }

    public AvaliacaoDTO createAvaliacao(AvaliacaoDTO avaliacaoDTO){
        //ter ou nao materia
        materiaService.getMateriaByIndex(avaliacaoDTO.getId_materia()).ifPresentOrElse(
                materiaDTO -> {
                    avaliacaoDTO.setId(avaliacaoRepository.save(AvaliacaoMapper.toAvaliacao(avaliacaoDTO, MateriaMapper.toMateria(materiaDTO))).getId());
                },()-> {
                    avaliacaoDTO.setId(avaliacaoRepository.save(AvaliacaoMapper.toAvaliacao(avaliacaoDTO)).getId());
                }
        );
        return avaliacaoDTO;
    }

    public void deleteAvaliacao (Long id){
        //avaliação_aluno
        avaliacaoRepository.findByActiveAndId(true,id).ifPresentOrElse(
                avaliacao -> {
                    inativaDependencias(avaliacao);
                    avaliacao.setActive(false);
                    avaliacaoRepository.save(avaliacao);
                },()->{ System.out.println("Avaliacao não encontrada");});
    }

    public AvaliacaoDTO putAvaliacao(AvaliacaoDTO avaliacaoDTO){
        //materia
        avaliacaoRepository.findByActiveAndId(true,avaliacaoDTO.getId()).ifPresentOrElse(
                avaliacao -> {
                    materiaService.getMateriaByIndex(avaliacaoDTO.getId_materia()).ifPresentOrElse(
                            materiaDTO -> {
                                avaliacaoRepository.save(AvaliacaoMapper.toAvaliacao(avaliacaoDTO,MateriaMapper.toMateria(materiaDTO)));
                            }, ()-> {
                                avaliacaoRepository.save(AvaliacaoMapper.toAvaliacao(avaliacaoDTO));
                            }
                    );
                }, ()-> { System.out.println("Avaliacao nao encontrada");}
        );
        return avaliacaoDTO;
    }

    private void inativaDependencias(Avaliacao avaliacao){
        //AvaliaçãoAluno
        avaliacaoAlunoService.deleteAvaliacoesByAvaliacao(avaliacao);
    }

    protected void deleteAvaliacaoByMateria(Materia materia){
        avaliacaoRepository.findByActiveAndMateria(true,materia).ifPresentOrElse(
                avaliacoes -> {
                    for (Avaliacao avaliacao: avaliacoes) {
                        inativaDependencias(avaliacao);
                        avaliacao.setActive(false);
                        avaliacaoRepository.save(avaliacao);
                    }
                },()->{//Não existe
                });
    }
}
