package com.example.demo.repository;

import com.example.demo.model.Aluno;
import com.example.demo.model.Mentor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MentorRepository extends JpaRepository<Mentor, Long> {
    Page<Mentor> findByActive(Boolean active, Pageable pageable);
    Optional<List<Mentor>> findByActive(Boolean active);
    Optional<Mentor> findByActiveAndId(Boolean active, Long id);
}
