package com.ufpb.crdb.controllers;

import java.util.List;

import javax.websocket.server.PathParam;

import com.ufpb.crdb.models.Disciplina;
import com.ufpb.crdb.services.DisciplinaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

}
