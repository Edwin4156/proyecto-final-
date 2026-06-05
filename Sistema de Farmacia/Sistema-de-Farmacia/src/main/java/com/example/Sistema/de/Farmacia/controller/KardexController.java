package com.example.Sistema.de.Farmacia.controller;

import com.example.Sistema.de.Farmacia.model.Kardex;
import com.example.Sistema.de.Farmacia.service.KardexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/kardex")
@CrossOrigin(origins = "*")
public class KardexController {

    @Autowired
    private KardexService kardexService;

    @GetMapping
    public ResponseEntity<List<Kardex>> obtenerTodos(@RequestParam(required = false) String q) {
        List<Kardex> movimientos;
        if (q != null && !q.isEmpty()) {
            movimientos = kardexService.buscarProducto(q);
        } else {
            movimientos = kardexService.obtenerTodos();
        }
        return ResponseEntity.ok(movimientos);
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<Kardex>> obtenerMovimientosProducto(@PathVariable Integer productoId) {
        List<Kardex> movimientos = kardexService.obtenerMovimientosProducto(productoId);
        return ResponseEntity.ok(movimientos);
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<Kardex>> obtenerPorPeriodo(
            @RequestParam String desde,
            @RequestParam String hasta) {
        LocalDate inicio = LocalDate.parse(desde);
        LocalDate fin = LocalDate.parse(hasta);
        List<Kardex> movimientos = kardexService.obtenerPorPeriodo(inicio, fin);
        return ResponseEntity.ok(movimientos);
    }

    @GetMapping("/entradas")
    public ResponseEntity<List<Kardex>> obtenerEntradas() {
        List<Kardex> movimientos = kardexService.obtenerEntradas();
        return ResponseEntity.ok(movimientos);
    }

    @GetMapping("/salidas")
    public ResponseEntity<List<Kardex>> obtenerSalidas() {
        List<Kardex> movimientos = kardexService.obtenerSalidas();
        return ResponseEntity.ok(movimientos);
    }
}
