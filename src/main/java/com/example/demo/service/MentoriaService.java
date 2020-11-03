package com.example.demo.service;

import com.example.demo.dto.MentoriaDTO;
import com.example.demo.dto.mapper.AlunoMapper;
import com.example.demo.dto.mapper.MentorMapper;
import com.example.demo.dto.mapper.MentoriaMapper;
import com.example.demo.model.Aluno;
import com.example.demo.model.Mentor;
import com.example.demo.model.Mentoria;
import com.example.demo.repository.MentoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MentoriaService {

    @Autowired
    MentoriaRepository mentoriaRepository;
    @Autowired
    AlunoService alunoService;
    @Autowired
    MentorService mentorService;


    public List<MentoriaDTO> getMentorias(){
        return mentoriaRepository.findByActive(true).get().parallelStream().map(MentoriaMapper::toMentoriaDTO).collect(Collectors.toList());
    }


    public Optional<MentoriaDTO> getMentoriaByIndex (Long id) {
        return mentoriaRepository.findByActiveAndId(true, id).map(MentoriaMapper::toMentoriaDTO);
    }

    public List<MentoriaDTO> getMentoriaByAluno (Aluno aluno) {
        return mentoriaRepository.findByActiveAndAluno(true, aluno).get().parallelStream().map(MentoriaMapper::toMentoriaDTO).collect(Collectors.toList());
    }

    public List<MentoriaDTO> getMentoriaByMentor (Mentor mentor) {
        return mentoriaRepository.findByActiveAndMentor(true, mentor).get().parallelStream().map(MentoriaMapper::toMentoriaDTO).collect(Collectors.toList());
    }


    public MentoriaDTO createMentoria(MentoriaDTO mentoriaDTO) {
        alunoService.getAlunoByIndex(mentoriaDTO.getId_aluno()).ifPresentOrElse(
                alunoDTO -> {
                    //mentor
                    mentorService.getMentorByIndex(mentoriaDTO.getId_mentor()).ifPresentOrElse(
                            mentorDTO -> {
                               mentoriaDTO.setId(mentoriaRepository.save(MentoriaMapper.toMentoria(mentoriaDTO, AlunoMapper.toAluno(alunoDTO), MentorMapper.toMentor(mentorDTO))).getId());
                            },()->{System.out.println("Mentor não encontrado");});
                },()->{System.out.println("Aluno não encontrado");});
        return mentoriaDTO;
    }


    public void deleteMentoria (Long id){
        mentoriaRepository.findByActiveAndId(true,id).ifPresentOrElse(
                mentoria -> {
                    mentoria.setActive(false);
                    mentoriaRepository.save(mentoria);
                },()->{System.out.println("Mentoria não encontrada");});
    }


    public MentoriaDTO putMentoria (MentoriaDTO mentoriaDTO){
        mentoriaRepository.findByActiveAndId(true,mentoriaDTO.getId()).ifPresentOrElse(
                mentoria -> {
                    //se aluno

                        alunoService.getAlunoByIndex(mentoriaDTO.getId_aluno()).ifPresentOrElse(
                                alunoDTO -> {
                                    //mentor
                                    mentorService.getMentorByIndex(mentoriaDTO.getId_mentor()).ifPresentOrElse(
                                            mentorDTO -> {
                                                mentoriaRepository.save(MentoriaMapper.toMentoria(mentoriaDTO, AlunoMapper.toAluno(alunoDTO), MentorMapper.toMentor(mentorDTO)));
                                            },()->{System.out.println("Mentor não encontrado");});
                                },()->{System.out.println("Aluno não encontrado");});

                },()->{System.out.println("Mentoria não encontrada");});
        return mentoriaDTO;
    }


    protected void deleteMentoriasByAluno(Aluno aluno){
        mentoriaRepository.findByActiveAndAluno(true,aluno).ifPresentOrElse(
                mentorias -> {
                    for (Mentoria mentoria: mentorias ) {
                        mentoria.setActive(false);
                        mentoriaRepository.save(mentoria);
                    }
                },()->{
                    //Aluno não possui mentoria
                });
    }

    protected void deleteMentoriasByMentor(Mentor mentor){
        mentoriaRepository.findByActiveAndMentor(true,mentor).ifPresentOrElse(
                mentorias -> {
                    for (Mentoria mentoria: mentorias ) {
                        mentoria.setActive(false);
                        mentoriaRepository.save(mentoria);
                    }
                },()->{
                    //Mentor não possui mentoria
                });
    }

}
