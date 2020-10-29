package com.example.demo.repository;

import com.example.demo.model.Aluno;
import com.example.demo.model.Materia;
import com.example.demo.model.Materia_Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Materia_AlunoRepository extends JpaRepository<Materia_Aluno, Long> {

    Optional<List<Materia_Aluno>> findByActive(Boolean active);
    Optional<Materia_Aluno> findByActiveAndId(Boolean active, Long id);

    Optional<List<Materia_Aluno>> findByActiveAndAluno(Boolean active, Aluno aluno);
    Optional<List<Materia_Aluno>> findByActiveAndMateria(Boolean active, Materia materia);
}
