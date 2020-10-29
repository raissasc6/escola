package com.example.demo.service;

import com.example.demo.Exceptions.Exception;
import com.example.demo.dto.MateriaAlunoDTO;
import com.example.demo.dto.mapper.AlunoMapper;
import com.example.demo.dto.mapper.MateriaAlunoMapper;
import com.example.demo.dto.mapper.MateriaMapper;
import com.example.demo.model.Aluno;
import com.example.demo.model.Materia;
import com.example.demo.model.Materia_Aluno;
import com.example.demo.repository.Materia_AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    MateriaService materiaService;

    public List<MateriaAlunoDTO> getMateriaAluno(){
        return materiaAlunoRepository.findByActive(true).get().parallelStream().map(MateriaAlunoMapper::toMateriaAlunoDTO).collect(Collectors.toList());
    }

    public Optional<MateriaAlunoDTO> getMateriaAlunoByIndex(Long id){
        return materiaAlunoRepository.findByActiveAndId(true,id).map(MateriaAlunoMapper::toMateriaAlunoDTO);
    }

    public List<MateriaAlunoDTO> getMateriaAlunoByAluno(Aluno aluno){
        return materiaAlunoRepository.findByActiveAndAluno(true,aluno).get().parallelStream().map(MateriaAlunoMapper::toMateriaAlunoDTO).collect(Collectors.toList());
    }

    public List<MateriaAlunoDTO> getMateriaAlunoByMateria(Materia materia){
        return materiaAlunoRepository.findByActiveAndMateria(true,materia).get().parallelStream().map(MateriaAlunoMapper::toMateriaAlunoDTO).collect(Collectors.toList());
    }

    public MateriaAlunoDTO createMateriaAluno (MateriaAlunoDTO materiaAlunoDTO) throws Exception.NotFoundException {
        //se aluno
        alunoService.getAlunoByIndex(materiaAlunoDTO.getId_aluno()).ifPresentOrElse(
                alunoDTO -> {
                    //se materia
                    materiaService.getMateriaByIndex(materiaAlunoDTO.getId_materia()).ifPresentOrElse(
                            materiaDTO -> {
                                materiaAlunoDTO.setId(materiaAlunoRepository.save(MateriaAlunoMapper.toMateriaAluno(materiaAlunoDTO, AlunoMapper.toAluno(alunoDTO), MateriaMapper.toMateria(materiaDTO))).getId());
                            },()-> {System.out.println("Materia nao encontrada");});
                },()-> {System.out.println("Aluno não encontrado");});
        return materiaAlunoDTO;
    }

    public void deleteMateriaAluno(Long id){
        materiaAlunoRepository.findByActiveAndId(true,id).ifPresentOrElse(
                materia_aluno -> {
                    materia_aluno.setActive(false);
                },()-> {System.out.println("Materia de aluno nao encontrado");});
    }

    public MateriaAlunoDTO putMateriaAluno(MateriaAlunoDTO materiaAlunoDTO){
        this.getMateriaAlunoByIndex(materiaAlunoDTO.getId()).ifPresentOrElse(
                materiaAlunoDTO1 -> {
                    //se aluno

                        alunoService.getAlunoByIndex(materiaAlunoDTO.getId_aluno()).ifPresentOrElse(alunoDTO -> {
                            //se materia
                            materiaService.getMateriaByIndex(materiaAlunoDTO.getId_materia()).ifPresentOrElse(materiaDTO -> {
                                materiaAlunoRepository.save(MateriaAlunoMapper.toMateriaAluno(materiaAlunoDTO,AlunoMapper.toAluno(alunoDTO),MateriaMapper.toMateria(materiaDTO)));
                            },()-> {System.out.println("Materia nao encontrada");});
                        },()-> {System.out.println("Aluno nao encontrado");});

                },()-> {System.out.println("Materia Aluno nao encontrado");});
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
