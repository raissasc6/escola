package com.example.demo.service;

import com.example.demo.model.Aluno;
import com.example.demo.model.Mentor;
import com.example.demo.repository.MentorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@DisplayName("Teste unitário de Mentor Service")
@ExtendWith(MockitoExtension.class)
public class MentorServiceTest {

    @Mock
    MentorRepository mentorRepository;

    @InjectMocks
    MentorService mentorService;

    @Test
    public void getAll(){

    }

    private List<Mentor> getListaDeMentores(){
        return List.of(
                new Mentor ("Raissa","Brasil"),
                new Mentor ("Raissa Cunha","Brasil")
        );
    }

}
