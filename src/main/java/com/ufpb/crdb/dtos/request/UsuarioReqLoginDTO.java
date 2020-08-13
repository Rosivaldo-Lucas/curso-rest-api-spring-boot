package com.ufpb.crdb.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioReqLoginDTO {
  
  private String email;

  private String senha;

}
