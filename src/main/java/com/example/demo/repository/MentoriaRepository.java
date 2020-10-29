package com.example.demo.repository;

import com.example.demo.model.Aluno;
import com.example.demo.model.Mentor;
import com.example.demo.model.Mentoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MentoriaRepository extends JpaRepository<Mentoria, Long> {

    Optional<List<Mentoria>> findByActive(Boolean active);
    Optional<Mentoria> findByActiveAndId(Boolean active, Long id);

    Optional<List<Mentoria>> findByActiveAndAluno(Boolean active, Aluno aluno);
    Optional<List<Mentoria>> findByActiveAndMentor(Boolean active, Mentor mentor);
}
