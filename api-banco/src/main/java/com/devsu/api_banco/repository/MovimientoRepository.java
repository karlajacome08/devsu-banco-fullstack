package com.devsu.api_banco.repository;

import com.devsu.api_banco.model.Movimiento;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

}
