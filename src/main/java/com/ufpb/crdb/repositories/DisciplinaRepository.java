package com.ufpb.crdb.repositories;

import java.util.List;

import com.ufpb.crdb.models.Disciplina;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {

  List<Disciplina> findByNomeContaining(String nome);

}
