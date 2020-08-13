package com.ufpb.crdb.services;

import java.io.IOException;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufpb.crdb.converters.DisciplinaConverter;
import com.ufpb.crdb.dtos.DisciplinaResponseDTO;
import com.ufpb.crdb.dtos.response.DisciplinaResDTO;
import com.ufpb.crdb.models.Comentario;
import com.ufpb.crdb.models.Disciplina;
import com.ufpb.crdb.models.Likes;
import com.ufpb.crdb.models.Usuario;
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

  public List<DisciplinaResDTO> buscar(String nome) {
    return disciplinaConverter
      .converterListaEntidadeParaListaDTO(
        disciplinaRepository.findByNomeContaining(nome.toUpperCase())
      );
  }

  public DisciplinaResponseDTO buscarPorId(Long id) {
    var disciplina = disciplinaRepository.findById(id);
    
    if (disciplina.isEmpty()) {
      throw new IllegalArgumentException();
    }

    DisciplinaResponseDTO disciplinaDTO = new DisciplinaResponseDTO();
    disciplinaDTO.setId(disciplina.get().getId());
    disciplinaDTO.setNome(disciplina.get().getNome());
    // disciplinaDTO.setNota(disciplina.get().getNota());
    disciplinaDTO.setLikes((long) disciplina.get().getLikes().size());

    return disciplinaDTO;
  }

  public Comentario adicionarComentario(Long id, Comentario comentario) {
    Optional<Disciplina> optDisciplina = disciplinaRepository.findById(id);

    if (optDisciplina.isEmpty()) {
      throw new IllegalArgumentException();
    }

    Optional<Usuario> optUsuario = usuarioRepository.findById((long) 1);

    comentario.setCreatedAt(OffsetDateTime.now());
    comentario.setUpdatedAt(OffsetDateTime.now());
    comentario.setDisciplina(optDisciplina.get());
    comentario.setUsuario(optUsuario.get());

    return comentarioRepository.save(comentario);
  }

  public void deletarComentario(Long disciplina_id, Long comentario_id) {
    var optDisciplina = disciplinaRepository.findById(disciplina_id);

    if (optDisciplina.isEmpty()) {
      throw new IllegalArgumentException();
    }

    var optComentario = comentarioRepository.findById(comentario_id);

    if (optComentario.isEmpty()){
      throw new IllegalArgumentException();
    }

    for (var comentario : optDisciplina.get().getComentarios()) {
      if (comentario.getId() == comentario_id && comentario.getDisciplina().getId() == disciplina_id) {
        comentarioRepository.delete(optComentario.get());
        return;
      }
    }

    throw new IllegalArgumentException();
  }

  public void adicionarLike(Long disciplina_id) {

    Optional<Disciplina> optDisciplina = disciplinaRepository.findById(disciplina_id);

    if (optDisciplina.isEmpty()) {
      throw new IllegalArgumentException();
    }

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
    addLike.setDisciplina(optDisciplina.get());
    addLike.setUsuario(usuario.get());
    addLike.setLikes(1);
    addLike.setCreatedAt(OffsetDateTime.now());
    addLike.setUpdatedAt(OffsetDateTime.now());

    likesRepository.save(addLike);
  }
  
  public void adicionarNota(Long id, Disciplina disciplina) {
    var optDisciplina = disciplinaRepository.findById(id);

    if (optDisciplina.isEmpty()) {
      throw new IllegalArgumentException();
    }

    if (optDisciplina.get().getNota() == null) {
      optDisciplina.get().setNota(disciplina.getNota());
    } else {
      optDisciplina.get().setNota((optDisciplina.get().getNota() + disciplina.getNota()) / 2);
    }

    disciplinaRepository.save(optDisciplina.get());
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
