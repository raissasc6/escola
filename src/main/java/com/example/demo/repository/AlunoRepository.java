package com.example.demo.repository;

import com.example.demo.model.Aluno;
import com.example.demo.model.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository //interface pq o spring data jpa irá criar a classe
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    //Aluno findById(Integer id);
    Optional<List<Aluno>> findByActive(Boolean active); //Select * aluno where active = 1
    Optional<Aluno> findByActiveAndId(Boolean active, Long id); //transforma a assinatura do método em HQL (Hibernt Query Langu)
    Optional<List<Aluno>> findByActiveAndMentor(Boolean active, Mentor mentor);
}
