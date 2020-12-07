package com.example.demo.repository;

import com.example.demo.model.Avaliacao;
import com.example.demo.model.Materia;
import com.example.demo.model.Materia_Aluno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    Page<Avaliacao> findByActive(Boolean active, Pageable pageable);
    Optional<List<Avaliacao>> findByActive(Boolean active);
    Optional<Avaliacao> findByActiveAndId(Boolean active, Long id);
    Optional<List<Avaliacao>> findByActiveAndMateria(Boolean active, Materia materia);

}
