package com.example.demo.service;


import com.example.demo.dto.MateriaDTO;
import com.example.demo.dto.mapper.MateriaMapper;
import com.example.demo.dto.mapper.MentorMapper;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Materia;
import com.example.demo.model.Mentor;
import com.example.demo.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MateriaService {

    @Autowired
    MateriaRepository materiaRepository;

    @Autowired
    MateriaAlunoService materiaAlunoService;

    @Autowired
    MentorService mentorService;
    @Autowired
    AvaliacaoService avaliacaoService;

    public List<MateriaDTO> getMaterias(){
        return materiaRepository.findByActive(true).get().parallelStream().map(MateriaMapper::toMateriaDTO).collect(Collectors.toList());
    }

    public Optional<MateriaDTO> getMateriaByIndex(Long id){
        return materiaRepository.findByActiveAndId(true, id).map(MateriaMapper::toMateriaDTO);
    }

    public MateriaDTO createMateria(MateriaDTO materiaDTO){
        //ver se tem id_mentor
        mentorService.getMentorByIndex(materiaDTO.getId_mentor()).ifPresentOrElse(
                mentorDTO -> {
                        materiaDTO.setId(materiaRepository.save(MateriaMapper.toMateria(materiaDTO, MentorMapper.toMentor(mentorDTO))).getId());
                },
                () -> {
                    materiaDTO.setId(materiaRepository.save(MateriaMapper.toMateria(materiaDTO)).getId());
                }
                );
        return materiaDTO;
    }

    public Boolean deleteMateria(Long id){
        //deleta de materia_aluno
        materiaRepository.findByActiveAndId(true,id).ifPresentOrElse(
                materia -> {
                    inativaDepentencias(materia);
                    materia.setActive(false);
                    materiaRepository.save(materia);
                }, () -> { throw  new ResourceNotFoundException("Materia não encontrada");});
        return true;
    }

    public MateriaDTO putMateria(MateriaDTO materiaDTO){
        materiaRepository.findByActiveAndId(true,materiaDTO.getId()).ifPresentOrElse(
                materia -> {
                    mentorService.getMentorByIndex(materiaDTO.getId_mentor()).ifPresentOrElse(
                            mentorDTO -> {
                                materiaRepository.save(MateriaMapper.toMateria(materiaDTO, MentorMapper.toMentor(mentorDTO)));
                            }, () ->{
                                materiaRepository.save(MateriaMapper.toMateria(materiaDTO));
                            });
                }, () -> {throw  new ResourceNotFoundException("Materia não ecnontrada");});
        return materiaDTO;
    }

    private void inativaDepentencias(Materia materia){
        //avaliação
        avaliacaoService.deleteAvaliacaoByMateria(materia);
        //materiaAluno
        materiaAlunoService.deleteMateriaAlunoByMateria(materia);
    }

    protected void inativaMentorDeMateria(Mentor mentor){
        materiaRepository.findByActiveAndMentor(true,mentor).ifPresentOrElse(
                materias -> {
                    for (Materia materia: materias ) {
                        materia.setMentor(null);
                        materiaRepository.save(materia);
                    }
                },()->{
                    //não tem
                });
    }
}
