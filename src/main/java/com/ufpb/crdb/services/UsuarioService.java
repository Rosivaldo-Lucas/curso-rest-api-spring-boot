package com.ufpb.crdb.services;

import java.time.OffsetDateTime;

import com.ufpb.crdb.models.Usuario;
import com.ufpb.crdb.repositories.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
  
  @Autowired
  private UsuarioRepository usuarioRepository;

  public Usuario adicionar(Usuario usuario) {
    usuario.setCreatedAt(OffsetDateTime.now());
    usuario.setUpdatedAt(OffsetDateTime.now());
    
    return usuarioRepository.save(usuario);
  }

}
