package com.ufpb.crdb.controllers;

import com.ufpb.crdb.dtos.request.UsuarioReqLoginDTO;
import com.ufpb.crdb.dtos.response.UsuarioResLoginDTO;
import com.ufpb.crdb.services.JWTService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/usuarios")
public class LoginController {

  @Autowired
  private JWTService jwtService;

  @PostMapping
  public ResponseEntity<UsuarioResLoginDTO> autenticar(@RequestBody UsuarioReqLoginDTO usuarioLogin) {
    return new ResponseEntity<>(jwtService.autentica(usuarioLogin), HttpStatus.OK);
  }

}
