package com.example.demo.service;

import com.example.demo.dto.MateriaDTO;
import com.example.demo.dto.MentorDTO;
import com.example.demo.dto.mapper.MateriaMapper;
import com.example.demo.dto.mapper.MentorMapper;
import com.example.demo.model.*;
import com.example.demo.repository.MateriaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@DisplayName("Teste unitário de Matéria Service")
@ExtendWith(MockitoExtension.class)
public class MateriaServiceTest {

    @Mock
    MateriaRepository materiaRepository;

    @Mock
    MateriaMapper materiaMapper;

    @Mock
    MentorMapper mentorMapper;
    @Mock
    AvaliacaoService avaliacaoService;

    @Mock
    MateriaAlunoService materiaAlunoService;

    @Mock
    MentorService mentorService;

    @InjectMocks
    MateriaService materiaService;


    //Get
    @Test
    public void testGetAll(){
        Mockito.when(materiaRepository.findByActive(true)).thenReturn(Optional.of(getListaDeMaterias()));
        List<MateriaDTO> materias = materiaService.getMaterias();
        Assertions.assertEquals(2, getListaDeMaterias().size());
        Materia materia = getListaDeMaterias().get(0);
        MateriaDTO materiaDTO = materias.get(0);

        Assertions.assertEquals(materia.getId(), materiaDTO.getId());
    }
    @Test
    public void testGetMateria(){
        var id = 1L; //Long
        Materia materia = new Materia();
        materia.setId(id);

        Mockito.when(materiaRepository.findByActiveAndId(true,id)).thenReturn(java.util.Optional.of(materia));
        Optional<MateriaDTO> materiaByIndex = this.materiaService.getMateriaByIndex(id);
        Assertions.assertAll(
                () -> Assertions.assertTrue( materiaByIndex.isPresent()),
                () -> Assertions.assertEquals(materia.getId(),materiaByIndex.get().getId())

        );
    }


    //Create
    @Test
    public void testCreateMateriaSemMentor(){
        var id = 1L;

        MateriaDTO materiaDTO = new MateriaDTO();

        Materia materia = new Materia();
        materia.setId(id);

        Mockito.when(materiaRepository.save(materiaMapper.toMateria(materiaDTO))).thenReturn(materia);

        MateriaDTO createMateria = this.materiaService.createMateria(materiaDTO);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1L,createMateria.getId()),
                () -> Assertions.assertEquals(null,createMateria.getId_mentor())

        );
    }
    @Test
    public void testCreateMateriaComMentor(){
        var id = 1L;
        MentorDTO mentorDTO = new MentorDTO();
        mentorDTO.setId(id);

        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setId_mentor(mentorDTO.getId());

        Materia materia = new Materia();
        materia.setId(id);

        Mockito.when(mentorService.getMentorByIndex(materiaDTO.getId_mentor())).thenReturn(Optional.of(mentorDTO));
        Mentor mentor =  mentorMapper.toMentor(this.mentorService.getMentorByIndex(materiaDTO.getId_mentor()).get());

        materia.setMentor(mentor);

        Mockito.when(materiaRepository.save(materiaMapper.toMateria(materiaDTO, mentor))).thenReturn(materia);

        MateriaDTO createMateria = this.materiaService.createMateria(materiaDTO);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1L,createMateria.getId()),
                () -> Assertions.assertEquals(materia.getMentor().getId(),createMateria.getId_mentor())

        );
    }

    //Put
    @Test
    public void testPutMateriaComMentor(){
        var id = 1L;
        MentorDTO mentorDTO = new MentorDTO();
        mentorDTO.setId(id);

        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setId(id);
        materiaDTO.setId_mentor(mentorDTO.getId());

        Materia materia = new Materia();
        materia.setId(id);


        Mockito.when(materiaRepository.findByActiveAndId(true,materiaDTO.getId())).thenReturn(java.util.Optional.of(materia));

        Mockito.when(mentorService.getMentorByIndex(materiaDTO.getId_mentor())).thenReturn(Optional.of(mentorDTO));
        Mentor mentor =  mentorMapper.toMentor(this.mentorService.getMentorByIndex(materiaDTO.getId_mentor()).get());
        materia.setMentor(mentor);

        Mockito.when(materiaRepository.save(materiaMapper.toMateria(materiaDTO, mentor))).thenReturn(materia);

        MateriaDTO putMateria = this.materiaService.putMateria(materiaDTO);

        Assertions.assertAll(
                () -> Assertions.assertEquals(materia.getId(),putMateria.getId()),
                () -> Assertions.assertEquals(materia.getMentor().getId(),putMateria.getId_mentor())

        );
    }
    @Test
    public void testPutMateriaSemMentor(){
        var id = 1L;
        MentorDTO mentorDTO = new MentorDTO();

        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setId(id);
        materiaDTO.setId_mentor(mentorDTO.getId());

        Materia materia = new Materia();
        materia.setId(id);


        Mockito.when(materiaRepository.findByActiveAndId(true,materiaDTO.getId())).thenReturn(java.util.Optional.of(materia));

        Mockito.when(mentorService.getMentorByIndex(materiaDTO.getId_mentor())).thenReturn(Optional.of(mentorDTO));
        Mentor mentor =  mentorMapper.toMentor(this.mentorService.getMentorByIndex(materiaDTO.getId_mentor()).get());
        materia.setMentor(mentor);

        Mockito.when(materiaRepository.save(materiaMapper.toMateria(materiaDTO, mentor))).thenReturn(materia);

        MateriaDTO putMateria = this.materiaService.putMateria(materiaDTO);

        Assertions.assertAll(
                () -> Assertions.assertEquals(materia.getId(),putMateria.getId()),
                () -> Assertions.assertEquals(null,putMateria.getId_mentor())

        );
    }

    //Delete
    @Test
    public void testDeleteMateria(){
        var id = 1L;

        Materia materia = new Materia();
        materia.setId(id);
        materia.setActive(true);

        Mockito.when(materiaRepository.findByActiveAndId(true,id)).thenReturn(Optional.of(materia));
        materia.setActive(false);
        Mockito.when(materiaRepository.save(materia)).thenReturn(materia);

        //Dependecias de aluno
        Mockito.verify(materiaAlunoService, Mockito.times(0)).deleteMateriaAlunoByMateria(materia);
        Mockito.verify(avaliacaoService, Mockito.times(0)).deleteAvaliacaoByMateria(materia);

        Boolean deleteMateria = this.materiaService.deleteMateria(id);

        Assertions.assertEquals(true,deleteMateria);
    }



    private List<Mentor> getListaDeMentores(){
        return List.of(
                new Mentor ("Raissa","Brasil"),
                new Mentor ("Raissa Cunha","Brasil")
        );
    }

    private List<Aluno> getListaDeAlunos(){
        return List.of(
                new Aluno ("Raissa","classe"),
                new Aluno ("Raissa Cunha","classe")
        );
    }

    private List<Materia> getListaDeMaterias(){
        return List.of(
                new Materia ("Álgebra",getListaDeMentores().get(0)),
                new Materia ("Comunicação",getListaDeMentores().get(1))
        );
    }
}
