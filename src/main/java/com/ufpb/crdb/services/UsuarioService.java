package com.ufpb.crdb.services;

import java.time.OffsetDateTime;
import java.util.List;

import com.ufpb.crdb.exceptions.UsuarioJaCadastradoException;
import com.ufpb.crdb.models.Usuario;
import com.ufpb.crdb.repositories.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
  
  @Autowired
  private UsuarioRepository usuarioRepository;

  public List<Usuario> listar() {
    return usuarioRepository.findAll();
  }

  public Usuario consultarUsuario(String email) {
    return buscarUsuarioPorEmail(email);
  }

  public Usuario adicionar(Usuario usuario) {
    var optUsuario = usuarioRepository.findByEmail(usuario.getEmail());

    if (optUsuario.isPresent()) {
      throw new UsuarioJaCadastradoException("Usuário já cadastrado no sistema.");
    }

    usuario.setCreatedAt(OffsetDateTime.now());
    usuario.setUpdatedAt(OffsetDateTime.now());
    
    return usuarioRepository.save(usuario);
  }

  public Boolean validaUsaurio(Usuario usuario) {
    var usuarioSalvo = buscarUsuarioPorEmail(usuario.getEmail());

    if (usuarioSalvo.getSenha().equals(usuario.getSenha())) {
      return true;
    }

    return false;
  }

  private Usuario buscarUsuarioPorEmail(String email) {
    var usuario = usuarioRepository.findByEmail(email);

    if (usuario.isEmpty()) {
      throw new IllegalArgumentException();
    }

    return usuario.get();
  }

}
