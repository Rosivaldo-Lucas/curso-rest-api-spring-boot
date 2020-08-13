package com.ufpb.crdb.repositories;

import com.ufpb.crdb.models.Likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {

}
