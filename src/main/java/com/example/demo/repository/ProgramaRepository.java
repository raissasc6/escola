package com.example.demo.repository;

import com.example.demo.model.Mentor;
import com.example.demo.model.Programa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgramaRepository extends CrudRepository<Programa, Long> {

    Optional<List<Programa>> findByActive(Boolean active);
    Optional<Programa> findByActiveAndId(Boolean active, Long id);

}
