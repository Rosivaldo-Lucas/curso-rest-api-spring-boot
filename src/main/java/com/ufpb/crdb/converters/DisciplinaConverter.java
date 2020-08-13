package com.ufpb.crdb.converters;

import java.util.List;
import java.util.stream.Collectors;

import com.ufpb.crdb.dtos.response.DisciplinaResDTO;
import com.ufpb.crdb.models.Disciplina;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DisciplinaConverter {
  
  @Autowired
  private ModelMapper modelMapper;

  public List<DisciplinaResDTO> converterListaEntidadeParaListaDTO(List<Disciplina> entidades) {
    return entidades
      .stream()
      .map((entidade) -> modelMapper.map(entidade, DisciplinaResDTO.class))
      .collect(Collectors.toList());
  }

}
