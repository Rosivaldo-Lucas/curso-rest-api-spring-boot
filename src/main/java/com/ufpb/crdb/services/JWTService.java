package com.ufpb.crdb.services;

import java.util.Date;

import com.ufpb.crdb.converters.UsuarioConverter;
import com.ufpb.crdb.dtos.request.UsuarioReqLoginDTO;
import com.ufpb.crdb.dtos.response.UsuarioResLoginDTO;
import com.ufpb.crdb.models.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Service
public class JWTService {
  
  public static final String TOKEN_SECRET = "1d8fe9f1802842d141c3fec3e408519e";

  @Autowired
  private UsuarioService usuarioService;

  @Autowired
  private UsuarioConverter usuarioConverter;

  public UsuarioResLoginDTO autentica(UsuarioReqLoginDTO usuarioLogin) {
    Usuario usuario = usuarioConverter.converterDTOParaEntidade(usuarioLogin);

    if (!usuarioService.validaUsaurio(usuario)) {
      throw new IllegalArgumentException();
    }

    String token = geraToken(usuarioLogin.getEmail());

    var usuarioSalvo = usuarioService.consultarUsuario(usuarioLogin.getEmail());

    var usuarioResponse = usuarioConverter.converterEntidadeParaDTO(usuarioSalvo);
    
    usuarioResponse.setToken(token);

    return usuarioResponse;
  }

  public String getToken(String authorizationHeader) {

    if (authorizationHeader == null || authorizationHeader.equals("Bearer ")) {
      throw new SecurityException("Token inexistente ou mal formatado.");
    }

    String token = authorizationHeader.substring(7);

    String subject = null;

    try {
      subject = Jwts
        .parser()
        .setSigningKey(TOKEN_SECRET)
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
    } catch (SignatureException ex) {
      throw new SecurityException("Token inválido ou expirado.");
    }

    return subject;
  }

  private String geraToken(String email) {
    String token = Jwts
      .builder()
      .setHeaderParam("typ", "JWT")
      .setSubject(email)
      .signWith(SignatureAlgorithm.HS512, TOKEN_SECRET)
      .setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
      .compact();

    return token;
  }

}
