package com.example.demo.service;


import com.example.demo.dto.AlunoDTO;
import com.example.demo.dto.mapper.AlunoMapper;
import com.example.demo.dto.mapper.MentorMapper;
import com.example.demo.dto.mapper.ProgramaMapper;
import com.example.demo.exception.ResourceIllegalArgumentException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Aluno;
import com.example.demo.model.Mentor;
import com.example.demo.model.Programa;
import com.example.demo.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlunoService {

    @Autowired
    AlunoRepository alunoRepository;
    @Autowired
    MentorService mentorService;
    @Autowired
    MentoriaService mentoriaService;
    @Autowired
    AlunoMapper alunoMapper;
    @Autowired
    MateriaAlunoService materiaAlunoService;
    @Autowired
    AvaliacaoAlunoService avaliacaoAlunoService;
    @Autowired
    ProgramaService programaService;


    public List<AlunoDTO> getAlunos(){
        return alunoRepository.findByActive(true).get().parallelStream().map(alunoMapper::toAlunoDTO).collect(Collectors.toList());
    }

    public Optional<AlunoDTO> getAlunoByIndex(Long id) {
       return alunoRepository.findByActiveAndId(true,id).map(alunoMapper::toAlunoDTO);
    }

    public AlunoDTO criaAluno(AlunoDTO alunoDTO){
        Mentor mentor = null;
        if(alunoDTO.getId_mentor()!= null){
            mentor= MentorMapper.toMentor(mentorService.getMentorByIndex(alunoDTO.getId_mentor()).get());
        }
        Programa programa=null;
        if(alunoDTO.getId_programa()!= null){
            programa= ProgramaMapper.toPrograma(programaService.getProgramaByIndex(alunoDTO.getId_programa()).get());
        }
        alunoDTO.setId(alunoRepository.save(alunoMapper.toAluno(alunoDTO,mentor,programa)).getId());
        return alunoDTO;
    }

    public Boolean deleteAluno(Long id) {

        final Aluno aluno = alunoRepository
                .findByActiveAndId(true,id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não ecnontrado"));
        inativaDependenciaDeAluno(aluno);
        aluno.setActive(false);
        alunoRepository.save(aluno);
        return true;
    }

    public AlunoDTO putAluno(AlunoDTO alunoDTO){
         alunoRepository.findByActiveAndId(true,alunoDTO.getId()).ifPresentOrElse(
                aluno -> {
                    Mentor mentor = null;
                    if(alunoDTO.getId_mentor()!= null){
                        mentor= MentorMapper.toMentor(mentorService.getMentorByIndex(alunoDTO.getId_mentor()).get());
                    }
                    Programa programa=null;
                    if(alunoDTO.getId_programa()!= null){
                        programa= ProgramaMapper.toPrograma(programaService.getProgramaByIndex(alunoDTO.getId_programa()).get());
                    }
                     alunoRepository.save(alunoMapper.toAluno(alunoDTO,mentor,programa));

                },
                 ()  ->  {
                     throw new ResourceNotFoundException("Aluno não ecnontrado");}
                );
        return alunoDTO;
    }

    private void inativaDependenciaDeAluno(Aluno aluno){
        //Mentoria
        mentoriaService.deleteMentoriasByAluno(aluno);
        //materia
        materiaAlunoService.deleteMateriaAlunoByAluno(aluno);
        //mavaliação
        avaliacaoAlunoService.deleteAvaliacoesByAluno(aluno);

    }

    protected void inativaMentorDeAluno(Mentor mentor){
        alunoRepository.findByActiveAndMentor(true,mentor).ifPresentOrElse(
                alunos -> {
                    for (Aluno aluno: alunos) {
                        aluno.setMentor(null);
                        alunoRepository.save(aluno);
                    }
                },()->{
                    throw new ResourceNotFoundException(
                            "Aluno não informado corretamente.");
                });
    }
}

