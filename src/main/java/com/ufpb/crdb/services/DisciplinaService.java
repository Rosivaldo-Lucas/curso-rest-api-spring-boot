package com.ufpb.crdb.services;

import java.io.IOException;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufpb.crdb.models.Comentario;
import com.ufpb.crdb.models.Disciplina;
import com.ufpb.crdb.models.Usuario;
import com.ufpb.crdb.repositories.ComentarioRepository;
import com.ufpb.crdb.repositories.DisciplinaRepository;
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

  public List<Disciplina> buscar(String nome) {
    return disciplinaRepository.findByNomeContaining(nome.toUpperCase());
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
