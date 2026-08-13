package com.devsu.api_banco.repository;

import com.devsu.api_banco.model.Cuenta;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, String> {

}
