package com.example.demo.service;


import com.example.demo.dto.ProgramaDTO;
import com.example.demo.dto.mapper.ProgramaMapper;
import com.example.demo.model.Programa;
import com.example.demo.repository.ProgramaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@DisplayName("Teste unitário de Programa Service")
@ExtendWith(MockitoExtension.class)
public class ProgramaServiceTest {

    @Mock
    ProgramaRepository programaRepository;

    @Mock
    ProgramaMapper programaMapper;

    @InjectMocks
    ProgramaService programaService;

    //Get
    @Test
    public void testGetAll(){
        Mockito.when(programaRepository.findByActive(true)).thenReturn(Optional.of(getListaDeProgramas()));

        List<ProgramaDTO> programas = programaService.getProgramas();

        Programa programa = getListaDeProgramas().get(0);
        ProgramaDTO programaDTO = programas.get(0);


        Assertions.assertEquals(programas.size(), getListaDeProgramas().size());
        Assertions.assertEquals(programa.getNome(), programaDTO.getNome());
    }
    @Test
    public void testGetPrograma(){
        var id = 1L; //Long
        Programa programa = new Programa();
        programa.setId(id);
        programa.setNome("Estagio");
        programa.setAno(Year.of(2020));

        Mockito.when(programaRepository.findByActiveAndId(true,id)).thenReturn(java.util.Optional.of(programa));
        Optional<ProgramaDTO> programaByIndex = this.programaService.getProgramaByIndex(id);
        Assertions.assertAll(
                () -> Assertions.assertTrue(programaByIndex.isPresent()),
                () -> Assertions.assertEquals(programa.getNome(),programaByIndex.get().getNome()),
                () -> Assertions.assertEquals(programa.getAno(),programaByIndex.get().getAno()),
                () -> Assertions.assertEquals(programa.getId(),programaByIndex.get().getId())

        );
    }

    //Create
    @Test
    public void testCreate(){
        var id = 1L;
        Programa programa = new Programa();
        programa.setId(id);
        programa.setNome("Estágio");
        programa.setAno(Year.of(2020));

        ProgramaDTO programaDTO = new ProgramaDTO();
        programaDTO.setNome("Estágio");
        programaDTO.setAno(Year.of(2020));

        Mockito.when(programaRepository.save(programaMapper.toPrograma(programaDTO))).thenReturn(programa);

        ProgramaDTO createPrograma = this.programaService.createPrograma(programaDTO);

        Assertions.assertAll(
                () -> Assertions.assertEquals(programa.getNome(),createPrograma.getNome()),
                () -> Assertions.assertEquals(programa.getAno(),createPrograma.getAno()),
                () -> Assertions.assertEquals(programa.getId(),createPrograma.getId())

        );
    }

    //Put
    @Test
    public void testPut(){
        var id = 1L;

        ProgramaDTO programaDTO = new ProgramaDTO();
        programaDTO.setId(id);
        programaDTO.setNome("Raissa");
        programaDTO.setAno(Year.of(2020));

        Programa programa = new Programa();
        programa.setId(id);
        programa.setNome("Raissa");
        programa.setAno(Year.of(2020));

        Mockito.when(programaRepository.findByActiveAndId(true,id)).thenReturn(Optional.of(programa));
        Mockito.when( programaRepository.save(ProgramaMapper.toPrograma(programaDTO))).thenReturn(programa);
        ProgramaDTO putPrograma = this.programaService.putPrograma(programaDTO);

        Assertions.assertAll(
                () -> Assertions.assertEquals(programa.getNome(),putPrograma.getNome()),
                () -> Assertions.assertEquals(programa.getAno(),putPrograma.getAno()),
                () -> Assertions.assertEquals(programa.getId(),putPrograma.getId())

        );
    }

    //Delete
    @Test
    public void testDelete(){
        var id = 1L;
        Programa programa = new Programa();
        programa.setId(id);
        programa.setActive(true);

        Programa programa2 = new Programa();
        programa2.setId(id);
        programa2.setActive(false);
        Mockito.when(programaRepository.findByActiveAndId(true,id)).thenReturn(Optional.of(programa));
        Mockito.when(programaRepository.save(programa)).thenReturn(programa2);

        Boolean deletePrograma = programaService.deletePrograma(id);
        Assertions.assertEquals(true,deletePrograma);
    }

    private List<Programa> getListaDeProgramas(){
        return List.of(
                new Programa ("Estágio",Year.of(2021)),
                new Programa ("Intercambio",Year.of(2020))
        );
    }

}
