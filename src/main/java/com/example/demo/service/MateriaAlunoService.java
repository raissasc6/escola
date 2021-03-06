package com.example.demo.service;

import com.example.demo.dto.MateriaAlunoDTO;
import com.example.demo.dto.MateriaDTO;
import com.example.demo.dto.MentoriaDTO;
import com.example.demo.dto.mapper.AlunoMapper;
import com.example.demo.dto.mapper.MateriaAlunoMapper;
import com.example.demo.dto.mapper.MateriaMapper;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Aluno;
import com.example.demo.model.Materia;
import com.example.demo.model.Materia_Aluno;
import com.example.demo.model.Mentoria;
import com.example.demo.repository.Materia_AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MateriaAlunoService {
    @Autowired
    Materia_AlunoRepository materiaAlunoRepository;
    @Autowired
    AlunoService alunoService;
    @Autowired
    AlunoMapper alunoMapper;
    @Autowired
    MateriaService materiaService;
    @Autowired
    MateriaAlunoMapper materiaAlunoMapper;

    public Page<MateriaAlunoDTO> paginateAll(Pageable pageable) {
        Page<Materia_Aluno> page = materiaAlunoRepository.findByActive(true,pageable);
        return  page.map(materiaAlunoMapper::toMateriaAlunoDTO);
    }

    public List<MateriaAlunoDTO> getMateriaAluno(){
        return materiaAlunoRepository.findByActive(true).get().parallelStream().map(materiaAlunoMapper::toMateriaAlunoDTO).collect(Collectors.toList());
    }

    public Optional<MateriaAlunoDTO> getMateriaAlunoByIndex(Long id){
        return materiaAlunoRepository.findByActiveAndId(true,id).map(materiaAlunoMapper::toMateriaAlunoDTO);
    }

    public List<MateriaAlunoDTO> getMateriaAlunoByAluno(Aluno aluno){
        return materiaAlunoRepository.findByActiveAndAluno(true,aluno).get().parallelStream().map(materiaAlunoMapper::toMateriaAlunoDTO).collect(Collectors.toList());
    }

    public List<MateriaAlunoDTO> getMateriaAlunoByMateria(Materia materia){
        return materiaAlunoRepository.findByActiveAndMateria(true,materia).get().parallelStream().map(materiaAlunoMapper::toMateriaAlunoDTO).collect(Collectors.toList());
    }

    public MateriaAlunoDTO createMateriaAluno (MateriaAlunoDTO materiaAlunoDTO)  {
        //se aluno
        alunoService.getAlunoByIndex(materiaAlunoDTO.getId_aluno()).ifPresentOrElse(
                alunoDTO -> {
                    materiaService.getMateriaByIndex(materiaAlunoDTO.getId_materia()).ifPresentOrElse(
                            materiaDTO -> {
                                materiaAlunoDTO.setId(materiaAlunoRepository.save(materiaAlunoMapper.toMateriaAluno(materiaAlunoDTO, alunoMapper.toAluno(alunoDTO), MateriaMapper.toMateria(materiaDTO))).getId());
                            },()-> {throw  new ResourceNotFoundException("Materia não encontrada");});
                },()-> {throw  new ResourceNotFoundException("Aluno não encontrado");});
        return materiaAlunoDTO;
    }

    public Boolean deleteMateriaAluno(Long id){
        materiaAlunoRepository.findByActiveAndId(true,id).ifPresentOrElse(
                materia_aluno -> {
                    materia_aluno.setActive(false);
                    materiaAlunoRepository.save(materia_aluno);
                },()-> {throw  new ResourceNotFoundException("Materia de aluno não encontrada");});
        return true;
    }

    public MateriaAlunoDTO putMateriaAluno(MateriaAlunoDTO materiaAlunoDTO){
        this.getMateriaAlunoByIndex(materiaAlunoDTO.getId()).ifPresentOrElse(
                materiaAlunoDTO1 -> {
                        alunoService.getAlunoByIndex(materiaAlunoDTO.getId_aluno()).ifPresentOrElse(alunoDTO -> {
                            materiaService.getMateriaByIndex(materiaAlunoDTO.getId_materia()).ifPresentOrElse(materiaDTO -> {
                                materiaAlunoRepository.save(materiaAlunoMapper.toMateriaAluno(materiaAlunoDTO,alunoMapper.toAluno(alunoDTO),MateriaMapper.toMateria(materiaDTO)));
                            },()-> {throw  new ResourceNotFoundException("Materia não encontrada");});
                        },()-> {throw  new ResourceNotFoundException("Aluno não encontrado");});
                },()-> {throw  new ResourceNotFoundException("Materia de aluno não encontrada");});
        return materiaAlunoDTO;
    }

    protected void deleteMateriaAlunoByAluno(Aluno aluno){
        materiaAlunoRepository.findByActiveAndAluno(true,aluno).ifPresentOrElse(
                materia_alunos -> {
                    for (Materia_Aluno materiaAluno: materia_alunos  ) {
                        materiaAluno.setActive(false);
                        materiaAlunoRepository.save(materiaAluno);
                    }
                },()-> {
                    //Aluno não possui avaliação
                });
    }

    protected void deleteMateriaAlunoByMateria(Materia materia){
        materiaAlunoRepository.findByActiveAndMateria(true,materia).ifPresentOrElse(
                materia_alunos -> {
                    for (Materia_Aluno materiaAluno: materia_alunos  ) {
                        materiaAluno.setActive(false);
                        materiaAlunoRepository.save(materiaAluno);
                    }
                },()-> {
                    //Aluno não possui avaliação
                });
    }


}
