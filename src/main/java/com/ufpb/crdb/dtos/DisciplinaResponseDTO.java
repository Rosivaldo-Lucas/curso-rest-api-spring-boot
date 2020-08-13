package com.ufpb.crdb.dtos;

import lombok.Data;

@Data
public class DisciplinaResponseDTO {
  
  private Long id;

  private String nome;

  private Long likes;

  private Double nota;
  
}
