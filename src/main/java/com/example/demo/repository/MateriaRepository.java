package com.example.demo.repository;

import com.example.demo.model.Materia;
import com.example.demo.model.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {

    Optional<List<Materia>> findByActive(Boolean active);
    Optional<Materia> findByActiveAndId(Boolean active, Long id);
    Optional<List<Materia>> findByActiveAndMentor(Boolean active, Mentor mentor);
}
