package com.example.demo.service;

import com.example.demo.dto.MentorDTO;
import com.example.demo.dto.mapper.MentorMapper;
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
//        Optional<Mentor> mentor = mentorRepository.findByActiveAndId(true,id);
//        MentorDTO mentorDTO = new MentorDTO();
//        if(mentor.isPresent()){
//            Mentor mentor2 = mentor.get();
//            mentorDTO.setId(mentor2.getId());
//            mentorDTO.setNome(mentor2.getNome());
//            mentorDTO.setPais(mentor2.getPais());
//            //Lista de alunos
//            List<Long> alunos = new ArrayList<>();
//            for (Aluno aluno: mentor2.getAlunos()) {
//                alunos.add(aluno.getId());
//            }
//            mentorDTO.setId_alunos(alunos);
//        }
//        return mentorDTO;
        return mentorRepository.findByActiveAndId(true, id).map(MentorMapper::toMentorDTO);
    }

    public MentorDTO createMentor(MentorDTO mentorDTO){
//        Mentor mentor2 = new Mentor(mentor.getNome(), mentor.getPais());
//        mentorRepository.save(mentor2);

        mentorDTO.setId(mentorRepository.save(MentorMapper.toMentor(mentorDTO)).getId());
        return mentorDTO;
    }

    public void deleteMentor (Long id){
        //deleta se nenhuma mentoria ou aluno dependente
//        Mentor mentor = mentorRepository.findById(id).orElse(null);
//        mentor.setActive(false);
//        mentorRepository.save(mentor);

        mentorRepository.findByActiveAndId(true, id).ifPresentOrElse(
                mentor -> {
                   inativaDependencias(mentor);
                    mentor.setActive(false);
                    mentorRepository.save(mentor);

                },
                () -> {
                    //****melhorar aqui********
                   System.out.println("Mentor não encontrado");
                }

                );
    }

    public MentorDTO putMentor (MentorDTO mentorDTO){
        //mentorRepository.save(mentor);
        mentorRepository.findByActiveAndId(true,mentorDTO.getId()).ifPresentOrElse(
                mentor -> {
                     mentorRepository.save(MentorMapper.toMentor(mentorDTO));
                     },
                ()  ->  {System.out.println("Mentor não encontrado");}
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
