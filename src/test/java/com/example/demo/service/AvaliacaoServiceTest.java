package com.example.demo.service;

import com.example.demo.dto.AvaliacaoDTO;
import com.example.demo.dto.MateriaDTO;
import com.example.demo.dto.MentorDTO;
import com.example.demo.dto.mapper.AvaliacaoMapper;
import com.example.demo.dto.mapper.MateriaAlunoMapper;
import com.example.demo.dto.mapper.MateriaMapper;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Avaliacao;
import com.example.demo.model.Materia;
import com.example.demo.model.Materia_Aluno;
import com.example.demo.model.Mentor;
import com.example.demo.repository.AvaliacaoRepository;
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

import java.time.Month;
import java.util.List;
import java.util.Optional;

@DisplayName("Teste unitário de Avaliacao Service")
@ExtendWith(MockitoExtension.class)
public class AvaliacaoServiceTest {
    @Mock
    AvaliacaoRepository avaliacaoRepository;
    @Mock
    MateriaService materiaService;
    @Mock
    MateriaMapper materiaMapper;
    @Mock
    AvaliacaoAlunoService avaliacaoAlunoService;
    @Mock
    AvaliacaoMapper avaliacaoMapper;
    @InjectMocks
    AvaliacaoService avaliacaoService;

    @Test
    public void testGetAll(){
        Mockito.when(avaliacaoRepository.findByActive(true)).thenReturn(Optional.of(getListaDeAvaliacaos()));
        List<AvaliacaoDTO> avaliacaos = avaliacaoService.getAvaliacoes();
        Assertions.assertEquals(2, getListaDeAvaliacaos().size());
        Avaliacao avaliacao = getListaDeAvaliacaos().get(0);
        AvaliacaoDTO avaliacaoDTO = avaliacaos.get(0);

        Assertions.assertEquals(avaliacao.getId(), avaliacaoDTO.getId());
    }
    @Test
    public void testGetAvaliacao(){
        var id = 1L; //Long
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setId(id);

        Mockito.when(avaliacaoRepository.findByActiveAndId(true,id)).thenReturn(java.util.Optional.of(avaliacao));
        Optional<AvaliacaoDTO> avaliacaoByIndex = this.avaliacaoService.getAvaliacaoByIndex(id);
        Assertions.assertAll(
                () -> Assertions.assertTrue( avaliacaoByIndex.isPresent()),
                () -> Assertions.assertEquals(avaliacao.getId(),avaliacaoByIndex.get().getId())

        );
    }

    @Test
    public void testCreateAvaliacao(){
        var id = 1L;
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setId(id);

        AvaliacaoDTO avaliacaoDTO = new AvaliacaoDTO();
        avaliacaoDTO.setId_materia(materiaDTO.getId());

        Mockito.when(materiaService.getMateriaByIndex(avaliacaoDTO.getId_materia())).thenReturn(Optional.of(materiaDTO));
        Materia materia =  materiaMapper.toMateria(this.materiaService.getMateriaByIndex(avaliacaoDTO.getId_materia()).get());

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setId(id);
        avaliacao.setMateria(materia);

        Mockito.when(avaliacaoRepository.save(avaliacaoMapper.toAvaliacao(avaliacaoDTO, materia))).thenReturn(avaliacao);

        AvaliacaoDTO createAvaliacao = avaliacaoService.createAvaliacao(avaliacaoDTO);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1L,createAvaliacao.getId()),
                () -> Assertions.assertEquals(avaliacao.getMateria().getId(),createAvaliacao.getId_materia())

        );
    }
    

    @Test
    public void testPutAvaliacao(){
        var id = 1L; //Long
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setId(id);

        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setId(id);

        AvaliacaoDTO avaliacaoDTO = new AvaliacaoDTO();
        avaliacaoDTO.setId(id);
        avaliacaoDTO.setId_materia(materiaDTO.getId());

        Mockito.when(avaliacaoRepository.findByActiveAndId(true,avaliacaoDTO.getId())).thenReturn(java.util.Optional.of(avaliacao));

        Mockito.when(materiaService.getMateriaByIndex(avaliacaoDTO.getId_materia())).thenReturn(Optional.of(materiaDTO));
        Materia materia =  materiaMapper.toMateria(this.materiaService.getMateriaByIndex(avaliacaoDTO.getId_materia()).get());

        avaliacao.setMateria(materia);

        Mockito.when(avaliacaoRepository.save(avaliacaoMapper.toAvaliacao(avaliacaoDTO, materia))).thenReturn(avaliacao);

        AvaliacaoDTO putAvaliacao = avaliacaoService.putAvaliacao(avaliacaoDTO);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1L,putAvaliacao.getId()),
                () -> Assertions.assertEquals(avaliacao.getMateria().getId(),putAvaliacao.getId_materia())

        );
    }
    @Test
    public void testPutAvaliacaoSemMateria(){
        var id = 1L; //Long
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setId(id);

        AvaliacaoDTO avaliacaoDTO = new AvaliacaoDTO();
        avaliacaoDTO.setId(id);
        Mockito.when(avaliacaoRepository.findByActiveAndId(true,avaliacaoDTO.getId())).thenReturn(java.util.Optional.of(avaliacao));


        Assertions.assertThrows(ResourceNotFoundException.class, () -> this.avaliacaoService.putAvaliacao(avaliacaoDTO));

    }

    //Delete
    @Test
    public void testDeleteAvaliacao(){
        var id = 1L;

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setId(id);
        avaliacao.setActive(true);

        Mockito.when(avaliacaoRepository.findByActiveAndId(true,id)).thenReturn(java.util.Optional.of(avaliacao));

        avaliacao.setActive(false);
        Mockito.when(avaliacaoRepository.save(avaliacao)).thenReturn(avaliacao);

        //Dependecias de aluno
        Mockito.verify(avaliacaoAlunoService, Mockito.times(0)).deleteAvaliacoesByAvaliacao(avaliacao);

        Boolean deleteAvaliacao = this.avaliacaoService.deleteAvaliacao(id);

        Assertions.assertEquals(true,deleteAvaliacao);
    }


    private List<Avaliacao> getListaDeAvaliacaos(){
        return List.of(
                new Avaliacao("Provao", Month.APRIL),
                new Avaliacao("Avaliacao", Month.APRIL)
        );
    }

}
