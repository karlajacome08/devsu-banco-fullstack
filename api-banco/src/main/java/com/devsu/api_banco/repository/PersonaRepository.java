package com.devsu.api_banco.repository;

import com.devsu.api_banco.model.Persona;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {

}
