package com.ufpb.crdb.services;

import java.io.IOException;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.util.List;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufpb.crdb.converters.DisciplinaConverter;
import com.ufpb.crdb.dtos.DisciplinaResponseDTO;
import com.ufpb.crdb.dtos.response.DisciplinaResDTO;
import com.ufpb.crdb.exceptions.RecursoNaoEncontradoException;
import com.ufpb.crdb.models.Comentario;
import com.ufpb.crdb.models.Disciplina;
import com.ufpb.crdb.models.Likes;
import com.ufpb.crdb.repositories.ComentarioRepository;
import com.ufpb.crdb.repositories.DisciplinaRepository;
import com.ufpb.crdb.repositories.LikesRepository;
import com.ufpb.crdb.repositories.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DisciplinaService {

  @Autowired
  private DisciplinaRepository disciplinaRepository;

  @Autowired
  private ComentarioRepository comentarioRepository;

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private LikesRepository likesRepository;

  @Autowired
  private DisciplinaConverter disciplinaConverter;

  @Autowired
  private JWTService jwtService;

  @Autowired
  private UsuarioService usuarioService;

  public List<DisciplinaResDTO> buscar(String nome) {
    return disciplinaConverter
      .converterListaEntidadeParaListaDTO(
        disciplinaRepository.findByNomeContaining(nome.toUpperCase())
      );
  }

  public DisciplinaResponseDTO buscarPorId(Long id) {
    // TODO depois refatorar este código
    
    var disciplina = buscarDisciplinaPorId(id);

    DisciplinaResponseDTO disciplinaDTO = new DisciplinaResponseDTO();
    disciplinaDTO.setId(disciplina.getId());
    disciplinaDTO.setNome(disciplina.getNome());
    disciplinaDTO.setNota(disciplina.getNota());
    disciplinaDTO.setLikes((long) disciplina.getLikes().size());

    return disciplinaDTO;
  }

  public Comentario adicionarComentario(Long id, Comentario comentario, String authorization) {
    String subjectEmail = jwtService.getToken(authorization);

    var usuario = usuarioService.consultarUsuario(subjectEmail);

    Disciplina disciplina = buscarDisciplinaPorId(id);

    comentario.setCreatedAt(OffsetDateTime.now());
    comentario.setUpdatedAt(OffsetDateTime.now());
    comentario.setDisciplina(disciplina);
    comentario.setUsuario(usuario);

    return comentarioRepository.save(comentario);
  }

  public void deletarComentario(Long disciplina_id, Long comentario_id) {
    var disciplina = buscarDisciplinaPorId(disciplina_id);

    var optComentario = comentarioRepository.findById(comentario_id);

    if (optComentario.isEmpty()){
      throw new RecursoNaoEncontradoException("Comentário não encontrado.");
    }

    for (var comentario : disciplina.getComentarios()) {
      if (comentario.getId() == comentario_id && comentario.getDisciplina().getId() == disciplina_id) {
        comentarioRepository.delete(optComentario.get());
        return;
      }
    }

    throw new RecursoNaoEncontradoException("Recurso não encontrado.");
  }

  public void adicionarLike(Long disciplina_id) {

    Disciplina disciplina = buscarDisciplinaPorId(disciplina_id);

    var usuario = usuarioRepository.findById((long) 1); // Vem do AuthorizationHeader

    var likes = likesRepository.findAll();

    for (var like : likes) {
      if (like.getDisciplina().getId() == disciplina_id && like.getUsuario().getId() == usuario.get().getId()) {
        if (like.getLikes() == 0) {
          like.setLikes(1);
          likesRepository.save(like);
        } else {
          like.setLikes(0);
          likesRepository.save(like);
        }
        return;
      }
    }

    Likes addLike = new Likes();
    addLike.setDisciplina(disciplina);
    addLike.setUsuario(usuario.get());
    addLike.setLikes(1); // Usuário do Authorization
    addLike.setCreatedAt(OffsetDateTime.now());
    addLike.setUpdatedAt(OffsetDateTime.now());

    likesRepository.save(addLike);
  }
  
  public void adicionarNota(Long id, Disciplina disciplina) {
    Disciplina disciplinaSalva = buscarDisciplinaPorId(id);

    if (disciplinaSalva.getNota() == null) {
      disciplinaSalva.setNota(disciplina.getNota());
    } else {
      disciplinaSalva.setNota((disciplinaSalva.getNota() + disciplina.getNota()) / 2);
    }

    disciplinaRepository.save(disciplinaSalva);
  }

  private Disciplina buscarDisciplinaPorId(Long id) {
    var optDisciplina = disciplinaRepository.findById(id);

    if (optDisciplina.isEmpty()) {
      throw new RecursoNaoEncontradoException("Disciplina não encontrada.");
    }

    return optDisciplina.get();
  }
  
  @PostConstruct
  public void init() {
    ObjectMapper mapper = new ObjectMapper();

    TypeReference<List<Disciplina>> typeReference = new TypeReference<>(){};

    InputStream inputStream = ObjectMapper.class.getResourceAsStream("/json/disciplinas.json");

    try {
      List<Disciplina> disciplinas = mapper.readValue(inputStream, typeReference);

      if (disciplinaRepository.count() == 0) {
        disciplinas.stream().forEach((disciplina) -> {
          disciplina.setCreatedAt(OffsetDateTime.now());
          disciplina.setUpdatedAt(OffsetDateTime.now());
        });

        disciplinaRepository.saveAll(disciplinas);
      }
    } catch (IOException ex) {
      System.out.println("Ocorreu um erro ao inicializar as disicplinas no banco de dados.");
    }
  }

}
