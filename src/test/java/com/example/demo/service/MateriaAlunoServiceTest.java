package com.example.demo.service;

import com.example.demo.dto.AlunoDTO;
import com.example.demo.dto.MateriaAlunoDTO;
import com.example.demo.dto.MateriaDTO;
import com.example.demo.dto.mapper.AlunoMapper;
import com.example.demo.dto.mapper.MateriaAlunoMapper;
import com.example.demo.dto.mapper.MateriaMapper;
import com.example.demo.model.Aluno;
import com.example.demo.model.Materia;
import com.example.demo.model.Materia_Aluno;
import com.example.demo.repository.Materia_AlunoRepository;
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

@DisplayName("Teste unitário de Matéria Aluno Service")
@ExtendWith(MockitoExtension.class)
public class MateriaAlunoServiceTest {

    @Mock
    Materia_AlunoRepository materiaAlunoRepository;
    @Mock
    AlunoService alunoService;
    @Mock
    MateriaService materiaService;
    @Mock
    MateriaMapper materiaMapper;
    @Spy
    MateriaAlunoMapper materiaAlunoMapper = Mappers.getMapper(MateriaAlunoMapper.class);
    @Spy
    AlunoMapper alunoMapper = Mappers.getMapper(AlunoMapper.class);
    @InjectMocks
    MateriaAlunoService materiaAlunoService;

    @Test
    public void testGetAll(){
        Mockito.when(materiaAlunoRepository.findByActive(true)).thenReturn(Optional.of(getListaDeMateriasAlunos()));
        List<MateriaAlunoDTO> materiasAlunos = materiaAlunoService.getMateriaAluno();
        Assertions.assertEquals(2, getListaDeMateriasAlunos().size());
        Materia_Aluno materiaAluno = getListaDeMateriasAlunos().get(0);
        MateriaAlunoDTO materiaAlunoDTO = materiasAlunos.get(0);

        Assertions.assertEquals(materiaAluno.getId(), materiaAlunoDTO.getId());
    }
    @Test
    public void testGetMateriaAluno(){
        var id = 1L; //Long
        Materia materia = new Materia();
        materia.setId(id);
        Aluno aluno = new Aluno();
        aluno.setId(id);
        Materia_Aluno materiaAluno = new Materia_Aluno();
        materiaAluno.setId(id);
        materiaAluno.setMateria(materia);
        materiaAluno.setAluno(aluno);

        Mockito.when(materiaAlunoRepository.findByActiveAndId(true,id)).thenReturn(java.util.Optional.of(materiaAluno));
        Optional<MateriaAlunoDTO> materiaAlunoByIndex = this.materiaAlunoService.getMateriaAlunoByIndex(id);
        Assertions.assertAll(
                () -> Assertions.assertTrue( materiaAlunoByIndex.isPresent()),
                () -> Assertions.assertEquals(materiaAluno.getId(),materiaAlunoByIndex.get().getId())

        );
    }

    @Test
    public  void testCreateMateriaAluno(){
        var id = 1L;

        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setId(id);

        AlunoDTO alunoDTO = new AlunoDTO();
        alunoDTO.setId(id);

        MateriaAlunoDTO materiaAlunoDTO = new MateriaAlunoDTO();
        materiaAlunoDTO.setId_materia(materiaDTO.getId());
        materiaAlunoDTO.setId_aluno(alunoDTO.getId());

        Mockito.when(alunoService.getAlunoByIndex(materiaAlunoDTO.getId_aluno())).thenReturn(Optional.of(alunoDTO));
        Mockito.when(materiaService.getMateriaByIndex(materiaAlunoDTO.getId_materia())).thenReturn(Optional.of(materiaDTO));
        Materia materia =  materiaMapper.toMateria(materiaService.getMateriaByIndex(materiaAlunoDTO.getId_materia()).get());
        Aluno aluno =  alunoMapper.toAluno(alunoService.getAlunoByIndex(materiaAlunoDTO.getId_aluno()).get());

        Materia_Aluno materiaAluno = new Materia_Aluno();
        materiaAluno.setId(id);
        materiaAluno.setAluno(aluno);
        materiaAluno.setMateria(materia);
        Mockito.when(materiaAlunoRepository.save(materiaAlunoMapper.toMateriaAluno(materiaAlunoDTO, aluno, materia ))).thenReturn(materiaAluno);

        MateriaAlunoDTO createMateriaAluno = this.materiaAlunoService.createMateriaAluno(materiaAlunoDTO);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1L,createMateriaAluno.getId()),
                () -> Assertions.assertEquals(materiaAluno.getAluno().getId(),createMateriaAluno.getId_aluno()),
                () -> Assertions.assertEquals(materiaAluno.getMateria().getId(),createMateriaAluno.getId_materia())

        );
    }

    @Test
    public void testPutMateriaAluno(){
        var id = 1L;

        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setId(id);

        AlunoDTO alunoDTO = new AlunoDTO();
        alunoDTO.setId(id);

        MateriaAlunoDTO materiaAlunoDTO = new MateriaAlunoDTO();
        materiaAlunoDTO.setId(id);
        materiaAlunoDTO.setId_materia(materiaDTO.getId());
        materiaAlunoDTO.setId_aluno(alunoDTO.getId());

        Materia_Aluno materiaAluno = new Materia_Aluno();
        materiaAluno.setId(id);


        Mockito.when(materiaAlunoRepository.findByActiveAndId(true,materiaAlunoDTO.getId())).thenReturn(java.util.Optional.of(materiaAluno));
        Mockito.when(alunoService.getAlunoByIndex(materiaAlunoDTO.getId_aluno())).thenReturn(Optional.of(alunoDTO));
        Mockito.when(materiaService.getMateriaByIndex(materiaAlunoDTO.getId_materia())).thenReturn(Optional.of(materiaDTO));
        Materia materia =  materiaMapper.toMateria(materiaService.getMateriaByIndex(materiaAlunoDTO.getId_materia()).get());
        Aluno aluno =  alunoMapper.toAluno(alunoService.getAlunoByIndex(materiaAlunoDTO.getId_aluno()).get());

        materiaAluno.setAluno(aluno);
        materiaAluno.setMateria(materia);
        Mockito.when(materiaAlunoRepository.save(materiaAlunoMapper.toMateriaAluno(materiaAlunoDTO, aluno, materia ))).thenReturn(materiaAluno);


        MateriaAlunoDTO putMateriaAluno = this.materiaAlunoService.putMateriaAluno(materiaAlunoDTO);

        Assertions.assertAll(
                () -> Assertions.assertEquals(materiaAluno.getId(),putMateriaAluno.getId()),
                () -> Assertions.assertEquals(materiaAluno.getAluno().getId(),putMateriaAluno.getId_aluno()),
                () -> Assertions.assertEquals(materiaAluno.getMateria().getId(),putMateriaAluno.getId_materia())

        );
    }

    @Test
    public void testDeleteMateriaAluno(){
        var id = 1L;

        MateriaAlunoDTO materiaAlunoDTO = new MateriaAlunoDTO();
        materiaAlunoDTO.setId(id);

        Materia_Aluno materiaAluno = new Materia_Aluno();
        materiaAluno.setId(id);
        materiaAluno.setActive(true);


        Mockito.when(materiaAlunoRepository.findByActiveAndId(true,materiaAlunoDTO.getId())).thenReturn(java.util.Optional.of(materiaAluno));
        materiaAluno.setActive(false);
        Mockito.when(materiaAlunoRepository.save(materiaAluno)).thenReturn(materiaAluno);

        Boolean deleteMateriaAluno = this.materiaAlunoService.deleteMateriaAluno(id);


        Assertions.assertEquals(true,deleteMateriaAluno);
    }


    private List<Aluno> getListaDeAlunos(){
        return List.of(
                new Aluno ("Raissa","classe"),
                new Aluno ("Raissa Cunha","classe")
        );
    }

    private List<Materia> getListaDeMaterias(){
        return List.of(
                new Materia ("Álgebra"),
                new Materia ("Comunicação")
        );
    }

    private List<Materia_Aluno> getListaDeMateriasAlunos(){
        return List.of(
                new Materia_Aluno(getListaDeAlunos().get(0),getListaDeMaterias().get(0)),
                new Materia_Aluno(getListaDeAlunos().get(1),getListaDeMaterias().get(1))
        );
    }


}
