package com.example.demo.service;

import com.example.demo.dto.AlunoDTO;
import com.example.demo.dto.AvaliacaoAlunoDTO;
import com.example.demo.dto.AvaliacaoDTO;
import com.example.demo.dto.MateriaAlunoDTO;
import com.example.demo.dto.mapper.AlunoMapper;
import com.example.demo.dto.mapper.AvaliacaoAlunoMapper;
import com.example.demo.dto.mapper.AvaliacaoMapper;
import com.example.demo.model.Aluno;
import com.example.demo.model.Avaliacao;
import com.example.demo.model.Avaliacao_Aluno;
import com.example.demo.model.Materia_Aluno;
import com.example.demo.repository.Avaliacao_AlunoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Month;
import java.util.List;
import java.util.Optional;

@DisplayName("Teste unitário de Avaliacao Aluno Service")
@ExtendWith(MockitoExtension.class)
public class AvaliacaoAlunoServiceTest {
    @Mock
    Avaliacao_AlunoRepository avaliacaoAlunoRepository;
    @Mock
    AvaliacaoAlunoMapper avaliacaoAlunoMapper;
    @Mock
    AvaliacaoService avaliacaoService;
    @Mock
    AvaliacaoMapper avaliacaoMapper;
    @Mock
    AlunoService alunoService;
    @Mock
    AlunoMapper alunoMapper;
    @InjectMocks
    AvaliacaoAlunoService avaliacaoAlunoService;

    @Test
    public void testGetAll(){
        Mockito.when(avaliacaoAlunoRepository.findByActive(true)).thenReturn(Optional.of(getListaDeAvaliacoesAlunos()));
        List<AvaliacaoAlunoDTO> avaliacaosAlunos = avaliacaoAlunoService.getAvaliacoesAlunos();
        Assertions.assertEquals(2, getListaDeAvaliacoesAlunos().size());
        Avaliacao_Aluno avaliacaoAluno = getListaDeAvaliacoesAlunos().get(0);
        AvaliacaoAlunoDTO avaliacaoAlunoDTO = avaliacaosAlunos.get(0);

        Assertions.assertEquals(avaliacaoAluno.getId(), avaliacaoAlunoDTO.getId());
    }
    @Test
    public void testGetAvaliacaoAluno(){
        var id = 1L; //Long
        Aluno aluno = new Aluno();
        aluno.setId(id);
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setId(id);
        Avaliacao_Aluno avaliacaoAluno = new Avaliacao_Aluno();
        avaliacaoAluno.setId(id);
        avaliacaoAluno.setAluno(aluno);
        avaliacaoAluno.setAvaliacao(avaliacao);


        Mockito.when(avaliacaoAlunoRepository.findByActiveAndId(true,id)).thenReturn(Optional.of(avaliacaoAluno));
        Optional<AvaliacaoAlunoDTO> avaliacaoAlunoByIndex = this.avaliacaoAlunoService.getAvaliacaoAlunoByIndex(id);
        Assertions.assertAll(
                () -> Assertions.assertTrue( avaliacaoAlunoByIndex.isPresent()),
                () -> Assertions.assertEquals(avaliacaoAluno.getId(),avaliacaoAlunoByIndex.get().getId())

        );
    }

    @Test
    public void testCreateAvaliacaoAluno(){
        var id = 1L; //Long
        AlunoDTO alunoDTO = new AlunoDTO();
        alunoDTO.setId(id);
        AvaliacaoDTO avaliacaoDTO = new AvaliacaoDTO();
        avaliacaoDTO.setId(id);
        AvaliacaoAlunoDTO avaliacaoAlunoDTO = new AvaliacaoAlunoDTO();
        avaliacaoAlunoDTO.setId_aluno(alunoDTO.getId());
        avaliacaoAlunoDTO.setId_avaliacao(avaliacaoDTO.getId());


        Mockito.when(avaliacaoService.getAvaliacaoByIndex(avaliacaoAlunoDTO.getId_avaliacao())).thenReturn(Optional.of(avaliacaoDTO));
        Avaliacao avaliacao = avaliacaoMapper.toAvaliacao(avaliacaoService.getAvaliacaoByIndex(avaliacaoAlunoDTO.getId_avaliacao()).get());
        Mockito.when(alunoService.getAlunoByIndex(avaliacaoAlunoDTO.getId_aluno())).thenReturn(Optional.of(alunoDTO));
        Aluno aluno = alunoMapper.toAluno(alunoService.getAlunoByIndex(avaliacaoAlunoDTO.getId_aluno()).get());

        Avaliacao_Aluno avaliacaoAluno = new Avaliacao_Aluno();
        avaliacaoAluno.setId(id);
        avaliacaoAluno.setAluno(aluno);
        avaliacaoAluno.setAvaliacao(avaliacao);

        Mockito.when(avaliacaoAlunoRepository.save(AvaliacaoAlunoMapper.toAvaliacaoAluno(avaliacaoAlunoDTO, aluno, avaliacao))).thenReturn(avaliacaoAluno);

        AvaliacaoAlunoDTO createAvaliacaoAluno = this.avaliacaoAlunoService.createAvaliacaoAluno(avaliacaoAlunoDTO);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1L,createAvaliacaoAluno.getId()),
                () -> Assertions.assertEquals(avaliacaoAluno.getAluno().getId(),createAvaliacaoAluno.getId_aluno()),
                () -> Assertions.assertEquals(avaliacaoAluno.getAvaliacao().getId(),createAvaliacaoAluno.getId_avaliacao())

        );
    }

    @Test
    public void testPutAvaliacaoAluno(){
        var id = 1L; //Long
        Avaliacao_Aluno avaliacaoAluno = new Avaliacao_Aluno();
        avaliacaoAluno.setId(id);

        AlunoDTO alunoDTO = new AlunoDTO();
        alunoDTO.setId(id);
        AvaliacaoDTO avaliacaoDTO = new AvaliacaoDTO();
        avaliacaoDTO.setId(id);
        AvaliacaoAlunoDTO avaliacaoAlunoDTO = new AvaliacaoAlunoDTO();
        avaliacaoAlunoDTO.setId(id);
        avaliacaoAlunoDTO.setId_aluno(alunoDTO.getId());
        avaliacaoAlunoDTO.setId_avaliacao(avaliacaoDTO.getId());


        Mockito.when(avaliacaoAlunoRepository.findByActiveAndId(true,avaliacaoAlunoDTO.getId())).thenReturn(Optional.of(avaliacaoAluno));

        Mockito.when(avaliacaoService.getAvaliacaoByIndex(avaliacaoAlunoDTO.getId_avaliacao())).thenReturn(Optional.of(avaliacaoDTO));
        Avaliacao avaliacao = avaliacaoMapper.toAvaliacao(avaliacaoService.getAvaliacaoByIndex(avaliacaoAlunoDTO.getId_avaliacao()).get());
        Mockito.when(alunoService.getAlunoByIndex(avaliacaoAlunoDTO.getId_aluno())).thenReturn(Optional.of(alunoDTO));
        Aluno aluno = alunoMapper.toAluno(alunoService.getAlunoByIndex(avaliacaoAlunoDTO.getId_aluno()).get());

        avaliacaoAluno.setAluno(aluno);
        avaliacaoAluno.setAvaliacao(avaliacao);

        Mockito.when(avaliacaoAlunoRepository.save(AvaliacaoAlunoMapper.toAvaliacaoAluno(avaliacaoAlunoDTO, aluno, avaliacao))).thenReturn(avaliacaoAluno);

        AvaliacaoAlunoDTO putAvaliacaoAluno = this.avaliacaoAlunoService.putAvaliacaoAluno(avaliacaoAlunoDTO);

        Assertions.assertAll(
                () -> Assertions.assertEquals(avaliacaoAluno.getId(),putAvaliacaoAluno.getId()),
                () -> Assertions.assertEquals(avaliacaoAluno.getAluno().getId(),putAvaliacaoAluno.getId_aluno()),
                () -> Assertions.assertEquals(avaliacaoAluno.getAvaliacao().getId(),putAvaliacaoAluno.getId_avaliacao())

        );
    }

    @Test void testDeleteAvaliacaoAluno(){
        var id = 1L;
        AvaliacaoAlunoDTO avaliacaoAlunoDTO = new AvaliacaoAlunoDTO();
        avaliacaoAlunoDTO.setId(id);


        Avaliacao_Aluno avaliacaoAluno = new Avaliacao_Aluno();
        avaliacaoAluno.setId(id);
        avaliacaoAluno.setActive(true);


        Mockito.when(avaliacaoAlunoRepository.findByActiveAndId(true,avaliacaoAlunoDTO.getId())).thenReturn(java.util.Optional.of(avaliacaoAluno));
        avaliacaoAluno.setActive(false);
        Mockito.when(avaliacaoAlunoRepository.save(avaliacaoAluno)).thenReturn(avaliacaoAluno);

        Boolean deleteAvaliacaoAluno = avaliacaoAlunoService.deleteAvaliacaoAluno(id);

        Assertions.assertEquals(true,deleteAvaliacaoAluno);
    }


    private List<Aluno> getListaDeAlunos(){
        return List.of(
                new Aluno ("Raissa","classe"),
                new Aluno ("Raissa Cunha","classe")
        );
    }

    private List<Avaliacao> getListaDeAvaliacaos(){
        return List.of(
                new Avaliacao("Provao", Month.APRIL),
                new Avaliacao("Avaliacao", Month.APRIL)
        );
    }

    private List<Avaliacao_Aluno> getListaDeAvaliacoesAlunos(){
        return List.of(
                new Avaliacao_Aluno (getListaDeAlunos().get(0),getListaDeAvaliacaos().get(0),Float.valueOf(10)),
                new Avaliacao_Aluno (getListaDeAlunos().get(0),getListaDeAvaliacaos().get(0),Float.valueOf(10))
        );
    }


}
