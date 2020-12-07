package com.example.demo.service;

import com.example.demo.dto.AvaliacaoAlunoDTO;
import com.example.demo.dto.AvaliacaoDTO;
import com.example.demo.dto.mapper.AvaliacaoMapper;
import com.example.demo.dto.mapper.MateriaMapper;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Avaliacao;
import com.example.demo.model.Avaliacao_Aluno;
import com.example.demo.model.Materia;
import com.example.demo.repository.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<AvaliacaoDTO> paginateAll(Pageable pageable) {
        Page<Avaliacao> page = avaliacaoRepository.findByActive(true,pageable);
        return  page.map(AvaliacaoMapper::toAvaliacaoDTO);
    }

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

    public Boolean deleteAvaliacao (Long id){
        //avaliação_aluno
        avaliacaoRepository.findByActiveAndId(true,id).ifPresentOrElse(
                avaliacao -> {
                    inativaDependencias(avaliacao);
                    avaliacao.setActive(false);
                    avaliacaoRepository.save(avaliacao);
                },()->{ throw  new ResourceNotFoundException("Avaliação não encontrada");});
        return true;
    }

    public AvaliacaoDTO putAvaliacao(AvaliacaoDTO avaliacaoDTO){
        //materia
        avaliacaoRepository.findByActiveAndId(true,avaliacaoDTO.getId()).ifPresentOrElse(
                avaliacao -> {
                    materiaService.getMateriaByIndex(avaliacaoDTO.getId_materia()).ifPresentOrElse(
                            materiaDTO -> {
                                avaliacaoRepository.save(AvaliacaoMapper.toAvaliacao(avaliacaoDTO,MateriaMapper.toMateria(materiaDTO)));
                            }, ()-> {
                                throw  new ResourceNotFoundException("Materia não encontrada");
                            }
                    );
                }, ()-> { throw  new ResourceNotFoundException("Avaliação não encontrada");}
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
