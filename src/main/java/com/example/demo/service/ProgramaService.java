package com.example.demo.service;

import com.example.demo.dto.AlunoDTO;
import com.example.demo.dto.ProgramaDTO;
import com.example.demo.dto.mapper.ProgramaMapper;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Aluno;
import com.example.demo.model.Programa;
import com.example.demo.repository.ProgramaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProgramaService {

    @Autowired
    ProgramaRepository programaRepository;
    @Autowired
    AlunoService alunoService;

    public Page<ProgramaDTO> paginateAll(Pageable pageable) {
        Page<Programa> page = programaRepository.findByActive(true,pageable);
        return  page.map(ProgramaMapper::toProgramaDTO);
    }

    public List<ProgramaDTO> getProgramas(){
        return programaRepository.findByActive(true).get().parallelStream().map(ProgramaMapper::toProgramaDTO).collect(Collectors.toList());
    }

    public Optional<ProgramaDTO> getProgramaByIndex(Long id){
        return programaRepository.findByActiveAndId(true,id).map(ProgramaMapper::toProgramaDTO);
    }

    public ProgramaDTO createPrograma(ProgramaDTO programaDTO){
        programaDTO.setId(programaRepository.save(ProgramaMapper.toPrograma(programaDTO)).getId());
       return programaDTO;

    }

    public Boolean deletePrograma(Long id){
        programaRepository.findByActiveAndId(true,id).ifPresentOrElse(
                programa -> {
                    programa.setActive(false);
                    programaRepository.save(programa);
                },()->{throw new ResourceNotFoundException("Programa não ecnontrado");});
        return true;
    }

    public ProgramaDTO putPrograma(ProgramaDTO programaDTO){
        programaRepository.findByActiveAndId(true,programaDTO.getId()).ifPresentOrElse(
                programa -> {
                    programaRepository.save(ProgramaMapper.toPrograma(programaDTO));
                },()->{throw new ResourceNotFoundException("Programa não ecnontrado");});
        return programaDTO;
    }

}
