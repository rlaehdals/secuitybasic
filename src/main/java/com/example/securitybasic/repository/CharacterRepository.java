package com.example.securitybasic.repository;

import com.example.securitybasic.domain.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CharacterRepository extends JpaRepository<Character, Long> {


    @Query("select c from Character c join fetch c.user where c.id= :id")
    Optional<Character> findFetchById(@Param("id") Long id);
}
