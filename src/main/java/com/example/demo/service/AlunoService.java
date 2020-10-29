package com.example.demo.service;


import com.example.demo.Exceptions.Exception;
import com.example.demo.dto.AlunoDTO;
import com.example.demo.dto.mapper.AlunoMapper;
import com.example.demo.dto.mapper.MentorMapper;
import com.example.demo.dto.mapper.ProgramaMapper;
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
    MateriaAlunoService materiaAlunoService;
    @Autowired
    AvaliacaoAlunoService avaliacaoAlunoService;
    @Autowired
    ProgramaService programaService;


    public List<AlunoDTO> getAlunos(){
        return alunoRepository.findByActive(true).get().parallelStream().map(AlunoMapper::toAlunoDTO).collect(Collectors.toList());
    }

    public Optional<AlunoDTO> getAlunoByIndex(Long id) {
       return alunoRepository.findByActiveAndId(true,id).map(AlunoMapper::toAlunoDTO);
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
        alunoDTO.setId(alunoRepository.save(AlunoMapper.toAluno(alunoDTO,mentor,programa)).getId());
        return alunoDTO;
    }

    public void deleteAluno(Long id) throws Exception.NotFoundException {
        if (id == null) {
            throw new IllegalArgumentException(
                    "Aluno não informado corretamente.");
        }
        final Aluno aluno = alunoRepository
                .findByActiveAndId(true,id)
                .orElseThrow(() -> new Exception.NotFoundException("Aluno não encontrado"));
        inativaDependenciaDeAluno(aluno);
        aluno.setActive(false);
        alunoRepository.save(aluno);
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
                    alunoRepository.save(AlunoMapper.toAluno(alunoDTO,mentor,programa));
                },
                 ()  ->  {//Exception aqui
                     System.out.println("Aluno não encontrado");}
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
                    //nao tem
                });
    }
}

