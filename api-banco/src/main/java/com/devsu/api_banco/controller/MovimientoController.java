package com.devsu.api_banco.controller;

import com.devsu.api_banco.controller.DTO.MovimientoRequestDto;
import com.devsu.api_banco.model.Movimiento;
import com.devsu.api_banco.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @GetMapping
    public ResponseEntity<List<Movimiento>> obtenerTodos() {
        return ResponseEntity.ok(movimientoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movimiento> obtenerMovimientoPorId(@PathVariable long id) {
        Movimiento movimiento = movimientoService.obtenerMovimientoPorId(id);
        if (movimiento != null) {
            return new ResponseEntity<>(movimiento, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Movimiento> registrarMovimiento(@RequestBody MovimientoRequestDto movimientoRequestDto) {
        Movimiento movimiento = movimientoService.registrarMovimiento(
                movimientoRequestDto.getNumeroCuenta(),
                movimientoRequestDto.getValor(),
                movimientoRequestDto.getTipoMovimiento());
        return new ResponseEntity<>(movimiento, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> eliminarMovimiento(@PathVariable long id) {
        Boolean deleted = movimientoService.eliminarMovimiento(id);
        if (deleted) {
            return new ResponseEntity<>(deleted, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
