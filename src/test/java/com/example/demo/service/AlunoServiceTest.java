package com.example.demo.service;

import com.example.demo.Exceptions.Exception;
import com.example.demo.dto.AlunoDTO;
import com.example.demo.model.Aluno;
import com.example.demo.model.Mentor;
import com.example.demo.model.Programa;
import com.example.demo.repository.AlunoRepository;
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

@DisplayName("Teste unitário de Aluno Service")
@ExtendWith(MockitoExtension.class)
public class AlunoServiceTest {

    @Mock
    AlunoRepository alunoRepository;

    @InjectMocks
    AlunoService alunoService;

    @Test
    public void testGetAluno() throws Exception.NotFoundException {
        //agora precisa mockar a chamada, só mockamos a classe até agora, para isso usamos o Mockito
        var id = 1L;
        Aluno aluno = new Aluno();
        Mentor mentor = new Mentor();
        Programa programa = new Programa();
        aluno.setNome("raissa");
        aluno.setClasse("Classe");
        mentor.setId(id);
        aluno.setMentor(mentor);
        programa.setId(id);
        aluno.setPrograma(programa);

        //mapeando uma resposta fake quando solicitado findByActiveAndId
        Mockito.when(alunoRepository.findByActiveAndId(true,id)).thenReturn(java.util.Optional.of(aluno));

        Optional<AlunoDTO> alunoByIndex = this.alunoService.getAlunoByIndex(id);

//
        //Assertions.assertThrows(Exception.class, () -> this.alunoService.getAlunoByIndex(id));

        Assertions.assertAll(
                () -> Assertions.assertTrue(alunoByIndex.isPresent()),
                () -> Assertions.assertEquals("raissa",alunoByIndex.get().getNome()),
                () -> Assertions.assertEquals("Classe",alunoByIndex.get().getClasse()),
                () -> Assertions.assertEquals(1L,alunoByIndex.get().getId_mentor()),
                () -> Assertions.assertEquals(1L,alunoByIndex.get().getId_programa())

        );
    }

    @Test
    public void testGetAll(){
        Mockito.when(alunoRepository.findByActive(true)).thenReturn(Optional.of(getListaDeAlunos()));
        List<AlunoDTO> alunos = alunoService.getAlunos();
        Assertions.assertEquals(2, getListaDeAlunos().size());
        Aluno aluno = getListaDeAlunos().get(0);
        AlunoDTO alunoDTO = alunos.get(0);

        Assertions.assertEquals(aluno.getNome(), alunoDTO.getNome());
    }

    private List<Aluno> getListaDeAlunos(){
        return List.of(
          new Aluno ("Raissa","classe"),
                new Aluno ("Raissa Cunha","classe")
        );
    }



}
