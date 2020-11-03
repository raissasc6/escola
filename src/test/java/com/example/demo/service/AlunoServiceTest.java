package com.example.demo.service;

import com.example.demo.dto.AlunoDTO;
import com.example.demo.dto.MentorDTO;
import com.example.demo.dto.ProgramaDTO;
import com.example.demo.dto.mapper.AlunoMapper;
import com.example.demo.dto.mapper.MentorMapper;
import com.example.demo.dto.mapper.ProgramaMapper;
import com.example.demo.model.Aluno;
import com.example.demo.model.Mentor;
import com.example.demo.model.Programa;
import com.example.demo.repository.AlunoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@DisplayName("Teste unitário de Aluno Service")
@ExtendWith(MockitoExtension.class)
public class AlunoServiceTest {

    @Mock
    AlunoRepository alunoRepository;
    @Mock
    MentorService mentorService;
    @Mock
    ProgramaService programaService;
    @Mock
    MentorMapper mentorMapper;
    @Mock
    ProgramaMapper programaMapper;
    @Mock
    MentoriaService mentoriaService;
    @Mock
    MateriaAlunoService materiaAlunoService;
    @Mock
    AvaliacaoAlunoService avaliacaoAlunoService;

    @InjectMocks
    AlunoService alunoService;

    //Get Aluno
    @Test
    public void testGetAluno()  {
        var id = 1L; //Long

        Mentor mentor = new Mentor();
        mentor.setId(id);

        Programa programa = new Programa();
        programa.setId(id);

        Aluno aluno = new Aluno();
        aluno.setNome("raissa");
        aluno.setClasse("Classe");
        aluno.setMentor(mentor);
        aluno.setPrograma(programa);

        //agora precisa mockar a chamada, só mockamos a classe até agora, para isso usamos o Mockito
        //mapeando uma resposta fake quando solicitado findByActiveAndId
        Mockito.when(alunoRepository.findByActiveAndId(true,id)).thenReturn(java.util.Optional.of(aluno));

        Optional<AlunoDTO> alunoByIndex = this.alunoService.getAlunoByIndex(id);

//
        //Assertions.assertThrows(Exception.class, () -> this.alunoService.getAlunoByIndex(id));

        Assertions.assertAll(
                () -> Assertions.assertTrue(alunoByIndex.isPresent()),
                () -> Assertions.assertEquals("raissa",alunoByIndex.get().getNome()),
                () -> Assertions.assertEquals("Classe",alunoByIndex.get().getClasse()),
                () -> Assertions.assertEquals(1L,alunoByIndex.get().getId_mentor()),
                () -> Assertions.assertEquals(1L,alunoByIndex.get().getId_programa())

        );
    }
    @Test
    public void testGetAll(){
        Mockito.when(alunoRepository.findByActive(true)).thenReturn(Optional.of(getListaDeAlunos()));
        List<AlunoDTO> alunos = alunoService.getAlunos();
        Assertions.assertEquals(2, getListaDeAlunos().size());
        Aluno aluno = getListaDeAlunos().get(0);
        AlunoDTO alunoDTO = alunos.get(0);

        Assertions.assertEquals(aluno.getNome(), alunoDTO.getNome());
    }


    //Create Aluno
    @Test
    public void testCreateAlunoComMentorComPrograma(){
        var id = 1L; //long

        ProgramaDTO programaDTO = new ProgramaDTO();
        programaDTO.setId(id);

        MentorDTO mentorDTO = new MentorDTO();
        mentorDTO.setId(id);

        AlunoDTO alunoDTO = new AlunoDTO();
        alunoDTO.setNome("Raissa");
        alunoDTO.setClasse("Classe");
        alunoDTO.setId_programa(id);
        alunoDTO.setId_mentor(id);

        Aluno aluno = new Aluno();
        aluno.setId(id);

        Mockito.when(mentorService.getMentorByIndex(alunoDTO.getId_mentor())).thenReturn(Optional.of(mentorDTO));
        Mentor mentor = this.mentorMapper.toMentor(this.mentorService.getMentorByIndex(alunoDTO.getId_mentor()).get());

        Mockito.when(programaService.getProgramaByIndex(alunoDTO.getId_programa())).thenReturn(Optional.of(programaDTO));
        Programa programa = programaMapper.toPrograma(this.programaService.getProgramaByIndex(alunoDTO.getId_programa()).get());

        Mockito.when(alunoRepository.save(AlunoMapper.toAluno(alunoDTO,mentor,programa))).thenReturn(aluno);

        AlunoDTO createAluno = this.alunoService.criaAluno(alunoDTO);

        Assertions.assertAll(
                () -> Assertions.assertEquals("Raissa",createAluno.getNome()),
                () -> Assertions.assertEquals("Classe",createAluno.getClasse()),
                () -> Assertions.assertEquals(1L,createAluno.getId_mentor()),
                () -> Assertions.assertEquals(1L,createAluno.getId_programa())

        );
    }
    @Test
    public void testCreateAlunoComMentorSemPrograma(){
        var id = 1L; //long


        MentorDTO mentorDTO = new MentorDTO();
        mentorDTO.setId(id);

        AlunoDTO alunoDTO = new AlunoDTO();
        alunoDTO.setNome("Raissa");
        alunoDTO.setClasse("Classe");
        alunoDTO.setId_mentor(id);

        Aluno aluno = new Aluno();
        aluno.setId(id);

        Mockito.when(mentorService.getMentorByIndex(alunoDTO.getId_mentor())).thenReturn(Optional.of(mentorDTO));
        Mentor mentor = this.mentorMapper.toMentor(this.mentorService.getMentorByIndex(alunoDTO.getId_mentor()).get());

        Mockito.when(alunoRepository.save(AlunoMapper.toAluno(alunoDTO,mentor,null))).thenReturn(aluno);

        AlunoDTO createAluno = this.alunoService.criaAluno(alunoDTO);

        Assertions.assertAll(
                () -> Assertions.assertEquals("Raissa",createAluno.getNome()),
                () -> Assertions.assertEquals("Classe",createAluno.getClasse()),
                () -> Assertions.assertEquals(1L,createAluno.getId_mentor()),
                () -> Assertions.assertEquals(null,createAluno.getId_programa())

        );
    }
    @Test
    public void testCreateAlunoSemMentorComPrograma(){
        var id = 1L; //long

        ProgramaDTO programaDTO = new ProgramaDTO();
        programaDTO.setId(id);

        AlunoDTO alunoDTO = new AlunoDTO();
        alunoDTO.setNome("Raissa");
        alunoDTO.setClasse("Classe");
        alunoDTO.setId_programa(id);

        Aluno aluno = new Aluno();
        aluno.setId(id);

        Mockito.when(programaService.getProgramaByIndex(alunoDTO.getId_programa())).thenReturn(Optional.of(programaDTO));
        Programa programa = programaMapper.toPrograma(this.programaService.getProgramaByIndex(alunoDTO.getId_programa()).get());

        Mockito.when(alunoRepository.save(AlunoMapper.toAluno(alunoDTO,null,programa))).thenReturn(aluno);

        AlunoDTO createAluno = this.alunoService.criaAluno(alunoDTO);

        Assertions.assertAll(
                () -> Assertions.assertEquals("Raissa",createAluno.getNome()),
                () -> Assertions.assertEquals("Classe",createAluno.getClasse()),
                () -> Assertions.assertEquals(null,createAluno.getId_mentor()),
                () -> Assertions.assertEquals(1L,createAluno.getId_programa())

        );
    }
    @Test
    public void testCreateAlunoSemMentorSemPrograma(){
        var id = 1L; //long

        AlunoDTO alunoDTO = new AlunoDTO();
        alunoDTO.setNome("Raissa");
        alunoDTO.setClasse("Classe");

        Aluno aluno = new Aluno();
        aluno.setId(id);


        Mockito.when(alunoRepository.save(AlunoMapper.toAluno(alunoDTO,null,null))).thenReturn(aluno);

        AlunoDTO createAluno = this.alunoService.criaAluno(alunoDTO);

        Assertions.assertAll(
                () -> Assertions.assertEquals("Raissa",createAluno.getNome()),
                () -> Assertions.assertEquals("Classe",createAluno.getClasse()),
                () -> Assertions.assertEquals(null,createAluno.getId_mentor()),
                () -> Assertions.assertEquals(null,createAluno.getId_programa())

        );
    }

    //Put Aluno
    @Test
    public void testPutAluno(){
        var id = 1L;

        ProgramaDTO programaDTO = new ProgramaDTO();
        programaDTO.setId(id);

        MentorDTO mentorDTO = new MentorDTO();
        mentorDTO.setId(id);

        AlunoDTO alunoDTO = new AlunoDTO();
        alunoDTO.setNome("Raissa");
        alunoDTO.setClasse("Classe");
        alunoDTO.setId(id);
        alunoDTO.setId_programa(id);
        alunoDTO.setId_mentor(id);

        Aluno aluno = new Aluno();
        aluno.setId(id);

        Mockito.when(mentorService.getMentorByIndex(alunoDTO.getId_mentor())).thenReturn(Optional.of(mentorDTO));

        Mockito.when(programaService.getProgramaByIndex(alunoDTO.getId_programa())).thenReturn(Optional.of(programaDTO));

        Mockito.when(alunoRepository.findByActiveAndId(true,id)).thenReturn(Optional.of(aluno));



        Optional<Aluno> alunoOptional = this.alunoRepository.findByActiveAndId(true,id);

        Assertions.assertTrue(alunoOptional.isPresent());

        Mentor mentor = this.mentorMapper.toMentor(this.mentorService.getMentorByIndex(alunoDTO.getId_mentor()).get());
        Programa programa = programaMapper.toPrograma(this.programaService.getProgramaByIndex(alunoDTO.getId_programa()).get());

        Mockito.when(alunoRepository.save(AlunoMapper.toAluno(alunoDTO,mentor,programa))).thenReturn(aluno);
        Aluno aluno1 = this.alunoRepository.save(AlunoMapper.toAluno(alunoDTO,mentor,programa));


        AlunoDTO putAluno = this.alunoService.putAluno(alunoDTO);


        Assertions.assertAll(
                () -> Assertions.assertEquals("Raissa",putAluno.getNome()),
                () -> Assertions.assertEquals("Classe",putAluno.getClasse()),
                () -> Assertions.assertEquals(1L,putAluno.getId_mentor()),
                () -> Assertions.assertEquals(1L,putAluno.getId_programa())

        );
    }

    //DeleteAluno
    @Test
    public void deleteAluno(){
        var id = 1L;

        Aluno aluno = new Aluno();
        aluno.setId(id);
        aluno.setActive(true);
        Aluno aluno2 = new Aluno();
        aluno2.setId(id);
        aluno2.setActive(false);

        Mockito.when(alunoRepository.findByActiveAndId(true,id)).thenReturn(java.util.Optional.of(aluno));
        Mockito.when(alunoRepository.save(aluno)).thenReturn(aluno2);

        //Dependecias de aluno
//        Mockito.doNothing().when(mentoriaService).deleteMentoriasByAluno(aluno);
//        Mockito.doNothing().when(materiaAlunoService).deleteMateriaAlunoByAluno(aluno);
//        Mockito.doNothing().when(avaliacaoAlunoService).deleteAvaliacoesByAluno(aluno);

        Boolean deleteAluno = this.alunoService.deleteAluno(id);

        Assertions.assertEquals(true,deleteAluno);

    }

    private List<Aluno> getListaDeAlunos(){
        return List.of(
          new Aluno ("Raissa","classe"),
                new Aluno ("Raissa Cunha","classe")
        );
    }



}
