package com.example.demo.service;

import com.example.demo.dto.MentorDTO;
import com.example.demo.dto.mapper.MentorMapper;
import com.example.demo.model.Mentor;
import com.example.demo.repository.MentorRepository;
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

import java.util.List;
import java.util.Optional;

@DisplayName("Teste unitário de Mentor Service")
@ExtendWith(MockitoExtension.class)
public class MentorServiceTest {

    @Mock
    MentorRepository mentorRepository;

    @Mock
    MateriaService   materiaService;
    @Mock
    MentoriaService mentoriaService;
    @Mock
    AlunoService alunoService;

    @Mock
    MentorMapper mentorMapper;
    @InjectMocks
    MentorService mentorService;

    //get mentor
    @Test
    public void testGetAll(){
        Mockito.when(mentorRepository.findByActive(true)).thenReturn(Optional.of(getListaDeMentores()));
        List<MentorDTO> mentores = mentorService.getMentores();
        Assertions.assertEquals(2, getListaDeMentores().size());
        Mentor mentor = getListaDeMentores().get(0);
        MentorDTO mentorDTO = mentores.get(0);

        Assertions.assertEquals(mentor.getNome(), mentorDTO.getNome());
    }
    @Test
    public void testGetMentor(){
        var id = 1L; //Long
        Mentor mentor = new Mentor();
        mentor.setId(id);
        mentor.setNome("Raissa");
        mentor.setPais("Brasil");
        mentor.setActive(true);

        Mockito.when(mentorRepository.findByActiveAndId(true,id)).thenReturn(java.util.Optional.of(mentor));
        Optional<MentorDTO> mentorByIndex = this.mentorService.getMentorByIndex(id);
        Assertions.assertAll(
                () -> Assertions.assertTrue(mentorByIndex.isPresent()),
                () -> Assertions.assertEquals(mentor.getNome(),mentorByIndex.get().getNome()),
                () -> Assertions.assertEquals(mentor.getPais(),mentorByIndex.get().getPais()),
                () -> Assertions.assertEquals(mentor.getId(),mentorByIndex.get().getId())

        );
    }


    //Create Mentor
    @Test
    public void testCreateMentor(){
        var id = 1L;
        Mentor mentor = new Mentor();
        mentor.setId(id);
        mentor.setNome("Raissa");
        mentor.setPais("Brasil");

        MentorDTO mentorDTO = new MentorDTO();
        mentorDTO.setNome("Raissa");
        mentorDTO.setPais("Brasil");

        Mockito.when(mentorRepository.save(mentorMapper.toMentor(mentorDTO))).thenReturn(mentor);

        MentorDTO createMentor = this.mentorService.createMentor(mentorDTO);

        Assertions.assertAll(
                () -> Assertions.assertEquals(mentor.getNome(),createMentor.getNome()),
                () -> Assertions.assertEquals(mentor.getPais(),createMentor.getPais()),
                () -> Assertions.assertEquals(1L,createMentor.getId())

        );
    }

    //Put Mentor
    @Test
    public void testPutMentor(){
        var id = 1L;

        MentorDTO mentorDTO = new MentorDTO();
        mentorDTO.setId(id);
        mentorDTO.setNome("Raissa");
        mentorDTO.setPais("Brasil");

        Mentor mentor = new Mentor();
        mentor.setId(id);
        mentor.setNome("Raissa");
        mentor.setPais("Brasil");

        Mockito.when(mentorRepository.findByActiveAndId(true,id)).thenReturn(Optional.of(mentor));
        Mockito.when( mentorRepository.save(mentorMapper.toMentor(mentorDTO))).thenReturn(mentor);
        MentorDTO putMentor = this.mentorService.putMentor(mentorDTO);

        Assertions.assertAll(
                () -> Assertions.assertEquals(mentor.getNome(),putMentor.getNome()),
                () -> Assertions.assertEquals(mentor.getPais(),putMentor.getPais()),
                () -> Assertions.assertEquals(mentor.getId(),putMentor.getId())

        );
    }

    //Delete Mentor
    @Test
    public void testDeleteMentor(){
        var id = 1L;
        Mentor mentor = new Mentor();
        mentor.setId(id);
        mentor.setActive(true);

        Mentor mentor2 = new Mentor();
        mentor2.setId(id);
        mentor2.setActive(false);
        Mockito.when(mentorRepository.findByActiveAndId(true,id)).thenReturn(Optional.of(mentor));
        Mockito.when(mentorRepository.save(mentor)).thenReturn(mentor2);

        Boolean deleteMentor = mentorService.deleteMentor(id);
        Assertions.assertEquals(true,deleteMentor);

    }

    private List<Mentor> getListaDeMentores(){
        return List.of(
                new Mentor ("Raissa","Brasil"),
                new Mentor ("Raissa Cunha","Brasil")
        );
    }

}
