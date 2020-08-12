package com.ufpb.crdb.controllers;

import java.util.List;

import javax.websocket.server.PathParam;

import com.ufpb.crdb.models.Comentario;
import com.ufpb.crdb.models.Disciplina;
import com.ufpb.crdb.services.DisciplinaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController {
  
  @Autowired
  private DisciplinaService disciplinaService;

  @GetMapping
  public ResponseEntity<List<Disciplina>> buscar(@PathParam(value = "nome") String nome) {
    return new ResponseEntity<>(disciplinaService.buscar(nome), HttpStatus.OK);
  }

  @PostMapping("/{id}/comentario")
  public ResponseEntity<Comentario> adicionarComentario(@PathVariable Long id, @RequestBody Comentario comentario) {
    Comentario comentarioSalvo = disciplinaService.adicionarComentario(id, comentario);
  
    return new ResponseEntity<>(comentarioSalvo, HttpStatus.CREATED);
  }

  @DeleteMapping("/{disciplina_id}/comentario/{comentario_id}")
  public ResponseEntity<Void> deletarComentario(@PathVariable Long disciplina_id, @PathVariable Long comentario_id) {
    try {
      disciplinaService.deletarComentario(disciplina_id, comentario_id);

      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (IllegalArgumentException ex) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

}
