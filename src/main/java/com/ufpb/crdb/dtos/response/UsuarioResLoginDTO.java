package com.ufpb.crdb.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResLoginDTO {

  private Long id;

  private String nome;

  private String sobrenome;

  private String email;

  private String token;
  
}
