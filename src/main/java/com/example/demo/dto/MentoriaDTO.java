package com.example.demo.dto;


import com.example.demo.model.Aluno;
import com.example.demo.model.Mentor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MentoriaDTO {

    private Long id;

    private Long id_aluno;

    private Long id_mentor;


}
