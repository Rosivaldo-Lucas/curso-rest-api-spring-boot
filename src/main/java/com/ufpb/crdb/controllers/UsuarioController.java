package com.ufpb.crdb.controllers;

import com.ufpb.crdb.models.Usuario;
import com.ufpb.crdb.services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

  @Autowired
  private UsuarioService usuarioService;

  @PostMapping
  public ResponseEntity<Usuario> adicionar(@RequestBody Usuario usuario) {
    return new ResponseEntity<>(usuarioService.adicionar(usuario), HttpStatus.CREATED);
  }

}
