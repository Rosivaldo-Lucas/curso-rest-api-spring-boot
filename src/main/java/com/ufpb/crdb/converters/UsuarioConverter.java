package com.ufpb.crdb.converters;

import com.ufpb.crdb.dtos.request.UsuarioReqLoginDTO;
import com.ufpb.crdb.dtos.response.UsuarioResLoginDTO;
import com.ufpb.crdb.models.Usuario;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioConverter {
  
  @Autowired
  private ModelMapper modelMapper;

  public Usuario converterDTOParaEntidade(UsuarioReqLoginDTO dto) {
    return modelMapper.map(dto, Usuario.class);
  }

  public UsuarioResLoginDTO converterEntidadeParaDTO(Usuario entidade) {
    return modelMapper.map(entidade, UsuarioResLoginDTO.class);
  }

}
