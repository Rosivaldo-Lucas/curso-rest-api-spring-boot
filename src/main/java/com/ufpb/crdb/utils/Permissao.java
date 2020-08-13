package com.ufpb.crdb.utils;

import com.ufpb.crdb.services.JWTService;
import com.ufpb.crdb.services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Permissao {
  
  @Autowired
  private JWTService jwtService;

  @Autowired
  private UsuarioService usuarioService;

  // public Boolean temPermisao(String authorization) {
  //   String subjectEmail = jwtService.getToken(authorization);

  //   var usuario = usuarioService.consultarUsuario(subjectEmail);

  //   return usuario.getEmail().equals(email);
  // }

}
