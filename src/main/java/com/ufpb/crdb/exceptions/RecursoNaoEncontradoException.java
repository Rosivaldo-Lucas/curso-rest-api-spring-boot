package com.ufpb.crdb.exceptions;

public class RecursoNaoEncontradoException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public RecursoNaoEncontradoException(String mensagem) {
    super(mensagem);
  }

  public RecursoNaoEncontradoException(String mensagem, Exception ex) {
    super(mensagem, ex);
  }
  
}
