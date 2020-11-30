package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.dto.MentoriaDTO;
import com.example.demo.dto.mapper.AlunoMapper;
import com.example.demo.dto.mapper.MentorMapper;
import com.example.demo.dto.mapper.MentoriaMapper;
import com.example.demo.model.*;
import com.example.demo.model.Mentoria;
import com.example.demo.repository.MentoriaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@DisplayName("Teste unitário de Mentoria Service")
@ExtendWith(MockitoExtension.class)
public class MentoriaServiceTest {

    @Mock
    MentoriaRepository mentoriaRepository;


    @Mock
    MentorService mentorService;

    @Spy
    MentorMapper mentorMapper;

    @Mock
    AlunoService alunoService;
    @Spy
    AlunoMapper alunoMapper = Mappers.getMapper(AlunoMapper.class);
    @Spy
    MentoriaMapper mentoriaMapper= Mappers.getMapper(MentoriaMapper.class);
    @InjectMocks
    MentoriaService mentoriaService;

    //Get
    @Test
    public void testGetAll(){
        Mockito.when(mentoriaRepository.findByActive(true)).thenReturn(Optional.of(getListaDeMentorias()));
        List<MentoriaDTO> mentorias = mentoriaService.getMentorias();
        Assertions.assertEquals(2, getListaDeMentorias().size());
        Mentoria mentoria = getListaDeMentorias().get(0);
        MentoriaDTO mentoriaDTO = mentorias.get(0);

        Assertions.assertEquals(mentoria.getId(), mentoriaDTO.getId());
    }

    @Test
    public void testGetMentoria(){
        var id = 1L; //Long
        Mentor mentor= new Mentor();
        mentor.setId(id);

        Aluno aluno = new Aluno();
        aluno.setId(id);


        Mentoria mentoria = new Mentoria();
        mentoria.setId(id);
        mentoria.setAluno(aluno);
        mentoria.setMentor(mentor);

        Mockito.when(mentoriaRepository.findByActiveAndId(true,id)).thenReturn(java.util.Optional.of(mentoria));
        Optional<MentoriaDTO> mentoriaByIndex = this.mentoriaService.getMentoriaByIndex(id);
        Assertions.assertAll(
                () -> Assertions.assertTrue(mentoriaByIndex.isPresent()),
                () -> Assertions.assertEquals(mentoria.getId(),mentoriaByIndex.get().getId()),
                () -> Assertions.assertEquals(mentoria.getMentor().getId(), mentoriaByIndex.get().getId_mentor()),
                () -> Assertions.assertEquals(mentoria.getAluno().getId(),mentoriaByIndex.get().getId_aluno())

        );
    }

    //Create
    @Test
    public void testCreate(){
        var id = 1L; //long
        MentorDTO mentorDTO= new MentorDTO();
        mentorDTO.setId(id);

        AlunoDTO alunoDTO = new AlunoDTO();
        alunoDTO.setId(id);

        MentoriaDTO mentoriaDTO = new MentoriaDTO();
        mentoriaDTO.setId_aluno(alunoDTO.getId());
        mentoriaDTO.setId_mentor(mentorDTO.getId());

        Mockito.when(mentorService.getMentorByIndex(mentoriaDTO.getId_mentor())).thenReturn(Optional.of(mentorDTO));
        Mentor mentor = this.mentorMapper.toMentor(this.mentorService.getMentorByIndex(mentoriaDTO.getId_mentor()).get());

        Mockito.when(alunoService.getAlunoByIndex(mentoriaDTO.getId_aluno())).thenReturn(Optional.of(alunoDTO));
        Aluno aluno = alunoMapper.toAluno(this.alunoService.getAlunoByIndex(mentoriaDTO.getId_aluno()).get());

        Mentoria mentoria = new Mentoria();
        mentoria.setId(id);
        mentoria.setAluno(aluno);
        mentoria.setMentor(mentor);

        Mockito.when(mentoriaRepository.save(mentoriaMapper.toMentoria(mentoriaDTO,aluno,mentor))).thenReturn(mentoria);

        MentoriaDTO createMentoria = this.mentoriaService.createMentoria(mentoriaDTO);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1L,createMentoria.getId()),
                () -> Assertions.assertEquals(mentoria.getAluno().getId(),createMentoria.getId_aluno()),
                () -> Assertions.assertEquals(mentoria.getMentor().getId(),createMentoria.getId_mentor())

        );
    }

    //Put
    @Test
    public void testPut(){
        var id = 1L; //long
        MentorDTO mentorDTO= new MentorDTO();
        mentorDTO.setId(id);

        AlunoDTO alunoDTO = new AlunoDTO();
        alunoDTO.setId(id);

        MentoriaDTO mentoriaDTO = new MentoriaDTO();
        mentoriaDTO.setId(id);
        mentoriaDTO.setId_aluno(alunoDTO.getId());
        mentoriaDTO.setId_mentor(mentorDTO.getId());

        Mentoria mentoria = new Mentoria();
        mentoria.setId(id);

        Mockito.when(mentoriaRepository.findByActiveAndId(true,mentoriaDTO.getId())).thenReturn(Optional.of(mentoria));

        Mockito.when(mentorService.getMentorByIndex(mentoriaDTO.getId_mentor())).thenReturn(Optional.of(mentorDTO));
        Mentor mentor = this.mentorMapper.toMentor(this.mentorService.getMentorByIndex(mentoriaDTO.getId_mentor()).get());

        Mockito.when(alunoService.getAlunoByIndex(mentoriaDTO.getId_aluno())).thenReturn(Optional.of(alunoDTO));
        Aluno aluno = alunoMapper.toAluno(this.alunoService.getAlunoByIndex(mentoriaDTO.getId_aluno()).get());

        mentoria.setMentor(mentor);
        mentoria.setAluno(aluno);

        Mockito.when(mentoriaRepository.save(mentoriaMapper.toMentoria(mentoriaDTO,aluno,mentor))).thenReturn(mentoria);

        MentoriaDTO putMentoria = this.mentoriaService.putMentoria(mentoriaDTO);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1L,putMentoria.getId()),
                () -> Assertions.assertEquals(mentoria.getAluno().getId(),putMentoria.getId_aluno()),
                () -> Assertions.assertEquals(mentoria.getMentor().getId(),putMentoria.getId_mentor())

        );

    }

    //Delete
    @Test
    public void testDelete(){
        var id = 1L;

        Mentoria mentoria = new Mentoria();
        mentoria.setId(id);
        mentoria.setActive(true);
        Mentoria mentoria2 = new Mentoria();
        mentoria2.setId(id);
        mentoria2.setActive(true);

        Mockito.when(mentoriaRepository.findByActiveAndId(true,id)).thenReturn(java.util.Optional.of(mentoria));
        Mockito.when(mentoriaRepository.save(mentoria)).thenReturn(mentoria2);

        //Dependecias de aluno
//        Mockito.doNothing().when(mentoriaService).deleteMentoriasByAluno(aluno);
//        Mockito.doNothing().when(materiaAlunoService).deleteMateriaAlunoByAluno(aluno);
        //Mockito.doNothing().when(avaliacaoAlunoService).deleteAvaliacoesByAluno(aluno);
        //Mockito.verify(avaliacaoAlunoService).deleteAvaliacoesByAluno(aluno);
        Boolean deleteMentoria = this.mentoriaService.deleteMentoria(id);

        Assertions.assertEquals(true,deleteMentoria);
    }


    private List<Aluno> getListaDeAlunos(){
        return List.of(
                new Aluno ("Raissa","classe"),
                new Aluno ("Raissa Cunha","classe")
        );
    }

    private List<Mentor> getListaDeMentores(){
        return List.of(
                new Mentor ("Raissa","Brasil"),
                new Mentor ("Raissa Cunha","Brasil")
        );
    }

    private List<Mentoria> getListaDeMentorias(){
        return List.of(
                new Mentoria (getListaDeAlunos().get(0), getListaDeMentores().get(0)),
                new Mentoria (getListaDeAlunos().get(1), getListaDeMentores().get(1))
        );
    }

}
