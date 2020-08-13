package com.ufpb.crdb.exceptions;

public class UsuarioJaCadastradoException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public UsuarioJaCadastradoException(String mensagem) {
    super(mensagem);
  }

  public UsuarioJaCadastradoException(String mensagem, Exception ex) {
    super(mensagem, ex);
  }

}
