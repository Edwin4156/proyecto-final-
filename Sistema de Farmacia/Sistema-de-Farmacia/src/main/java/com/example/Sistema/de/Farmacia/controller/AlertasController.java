package com.example.Sistema.de.Farmacia.controller;

import com.example.Sistema.de.Farmacia.model.Producto;
import com.example.Sistema.de.Farmacia.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/alertas")
@CrossOrigin(origins = "*")
public class AlertasController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> obtenerAlertas() {
        Map<String, Object> alertas = new HashMap<>();

        List<Producto> caducados = productoService.obtenerCaducados();
        List<Producto> porVencer = productoService.obtenerPorVencer();
        List<Producto> stockBajo = productoService.obtenerConStockBajo();

        alertas.put("caducados", caducados);
        alertas.put("porVencer", porVencer);
        alertas.put("stockBajo", stockBajo);
        alertas.put("totalAlertas", caducados.size() + porVencer.size() + stockBajo.size());

        return ResponseEntity.ok(alertas);
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> contarAlertas() {
        List<Producto> caducados = productoService.obtenerCaducados();
        List<Producto> porVencer = productoService.obtenerPorVencer();
        List<Producto> stockBajo = productoService.obtenerConStockBajo();
        int total = caducados.size() + porVencer.size() + stockBajo.size();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/stock-bajo")
    public ResponseEntity<List<Producto>> obtenerStockBajo() {
        List<Producto> productos = productoService.obtenerConStockBajo();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/por-vencer")
    public ResponseEntity<List<Producto>> obtenerPorVencer() {
        List<Producto> productos = productoService.obtenerPorVencer();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/caducados")
    public ResponseEntity<List<Producto>> obtenerCaducados() {
        List<Producto> productos = productoService.obtenerCaducados();
        return ResponseEntity.ok(productos);
    }
}
