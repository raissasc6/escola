package com.example.demo;


import com.example.demo.dto.AlunoDTO;
import com.example.demo.model.Aluno;
import com.example.demo.repository.AlunoRepository;
import com.example.demo.service.AlunoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class FirstMockTest {

    @Mock //falando que o repositorio é falso
    AlunoRepository alunoRepository;

    @InjectMocks //mocka o alunoRepositort dentro do aluno Service
    AlunoService alunoService;

    @Test
    public void testGetAluno()  {
        //agora precisa mockar a chamada, só mockamos a classe até agora, para isso usamos o Mockito
        var id = 1L;
        Aluno aluno = new Aluno();
        aluno.setNome("raissa");
        aluno.setClasse("Classe");

        //mapeando uma resposta fake quando solicitado findByActiveAndId
        Mockito.when(alunoRepository.findByActiveAndId(true,id)).thenReturn(java.util.Optional.of(aluno));

        Optional<AlunoDTO> alunoByIndex = this.alunoService.getAlunoByIndex(id);

//        Assertions.assertTrue(alunoByIndex.isPresent());
//        AlunoDTO alunoDTO = alunoByIndex.get();
//        Assertions.assertEquals("t",alunoDTO.getNome());
        //Assertions.assertThrows(Exception.class, () -> this.alunoService.getAlunoByIndex(id));

        Assertions.assertAll(
                () -> Assertions.assertTrue(alunoByIndex.isPresent()),
                () -> Assertions.assertEquals("t",alunoByIndex.get().getNome()),
                () -> Assertions.assertEquals("t",alunoByIndex.get().getClasse())

        );

    }
}
