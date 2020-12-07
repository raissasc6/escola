package com.example.demo.repository;

import com.example.demo.model.Aluno;
import com.example.demo.model.Avaliacao;
import com.example.demo.model.Avaliacao_Aluno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Avaliacao_AlunoRepository extends JpaRepository<Avaliacao_Aluno, Long> {
    Page<Avaliacao_Aluno> findByActive(Boolean active, Pageable pageable);
    Optional<List<Avaliacao_Aluno>> findByActive(Boolean active);
    Optional<Avaliacao_Aluno> findByActiveAndId(Boolean active, Long id);

    Optional<List<Avaliacao_Aluno>> findByActiveAndAluno(Boolean active, Aluno aluno);
    Optional<List<Avaliacao_Aluno>> findByActiveAndAvaliacao(Boolean active, Avaliacao avaliacao);
}
