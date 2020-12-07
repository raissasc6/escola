package com.example.demo.repository;

import com.example.demo.model.Aluno;
import com.example.demo.model.Materia;
import com.example.demo.model.Materia_Aluno;
import com.example.demo.model.Mentor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Materia_AlunoRepository extends JpaRepository<Materia_Aluno, Long> {
    Page<Materia_Aluno> findByActive(Boolean active, Pageable pageable);
    Optional<List<Materia_Aluno>> findByActive(Boolean active);
    Optional<Materia_Aluno> findByActiveAndId(Boolean active, Long id);

    Optional<List<Materia_Aluno>> findByActiveAndAluno(Boolean active, Aluno aluno);
    Optional<List<Materia_Aluno>> findByActiveAndMateria(Boolean active, Materia materia);
}
