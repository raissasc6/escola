package com.example.demo.service;

import com.example.demo.dto.MentorDTO;
import com.example.demo.dto.mapper.MentorMapper;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Aluno;
import com.example.demo.model.Mentor;
import com.example.demo.model.Mentoria;
import com.example.demo.repository.AlunoRepository;
import com.example.demo.repository.MentorRepository;
import com.example.demo.repository.MentoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MentorService {

    @Autowired
    MentorRepository mentorRepository;

    @Autowired
    MentoriaService mentoriaService;

    @Autowired
    MateriaService materiaService;

    @Autowired
    AlunoService alunoService;

    public List<MentorDTO> getMentores(){
       return mentorRepository.findByActive(true).get().parallelStream().map(MentorMapper::toMentorDTO).collect(Collectors.toList());
    }

    public Optional<MentorDTO> getMentorByIndex (Long id){
        return mentorRepository.findByActiveAndId(true, id).map(MentorMapper::toMentorDTO);
    }

    public MentorDTO createMentor(MentorDTO mentorDTO){
        mentorDTO.setId(mentorRepository.save(MentorMapper.toMentor(mentorDTO)).getId());
        return mentorDTO;
    }

    public boolean deleteMentor (Long id){
        mentorRepository.findByActiveAndId(true, id).ifPresentOrElse(
                mentor -> {
                   inativaDependencias(mentor);
                    mentor.setActive(false);
                    mentorRepository.save(mentor);
                },
                () -> {throw new ResourceNotFoundException("Mentor não ecnontrado");}
                );
        return true;
    }

    public MentorDTO putMentor (MentorDTO mentorDTO){
        //mentorRepository.save(mentor);
        mentorRepository.findByActiveAndId(true,mentorDTO.getId()).ifPresentOrElse(
                mentor -> {
                     mentorRepository.save(MentorMapper.toMentor(mentorDTO));
                     },
                ()  ->  { throw new ResourceNotFoundException("Mentor não ecnontrado");}
        );
        return mentorDTO;
    }

    private void inativaDependencias(Mentor mentor){
        //Materia
        materiaService.inativaMentorDeMateria(mentor);
        //Mentoria
        mentoriaService.deleteMentoriasByMentor(mentor);
        //aluno
        alunoService.inativaMentorDeAluno(mentor);
    }
}
