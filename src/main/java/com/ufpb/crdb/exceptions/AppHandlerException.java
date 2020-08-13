package com.ufpb.crdb.exceptions;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class AppHandlerException extends ResponseEntityExceptionHandler {
  
  @Autowired
  private AppErro appErro;

  @ExceptionHandler(UsuarioJaCadastradoException.class)
  public ResponseEntity<Object> handlerUsuarioJaCadastradoException(UsuarioJaCadastradoException ex, WebRequest request) {
    var statusCode = HttpStatus.BAD_REQUEST;

    appErro.setErro(statusCode.name());
    appErro.setStatus(statusCode.value());
    appErro.setMensagem(ex.getMessage());
    appErro.setTimestamps(OffsetDateTime.now());

    return handleExceptionInternal(ex, appErro, new HttpHeaders(), statusCode, request);

  }

}
