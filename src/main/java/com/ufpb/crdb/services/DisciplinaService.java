package com.ufpb.crdb.services;

import java.io.IOException;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.util.List;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufpb.crdb.models.Disciplina;
import com.ufpb.crdb.repositories.DisciplinaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DisciplinaService {
  
  @Autowired
  private DisciplinaRepository disciplinaRepository;

  public List<Disciplina> buscar(String nome) {
    return disciplinaRepository.findByNomeContaining(nome.toUpperCase());
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
