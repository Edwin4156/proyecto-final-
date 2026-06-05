package com.example.Sistema.de.Farmacia.controller;

import com.example.Sistema.de.Farmacia.dto.CompraDTO;
import com.example.Sistema.de.Farmacia.model.Compra;
import com.example.Sistema.de.Farmacia.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/compras")
@CrossOrigin(origins = "*")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @GetMapping
    public ResponseEntity<List<CompraDTO>> obtenerTodas(
            @RequestParam(required = false) String filtro,
            @RequestParam(required = false) String q) {
        List<Compra> compras;

        if ("semana".equals(filtro)) {
            LocalDate hace7 = LocalDate.now().minusDays(7);
            compras = compraService.obtenerPorPeriodo(hace7, LocalDate.now());
        } else if ("mes".equals(filtro)) {
            LocalDate primerDiaMes = LocalDate.now().withDayOfMonth(1);
            compras = compraService.obtenerPorPeriodo(primerDiaMes, LocalDate.now());
        } else {
            compras = compraService.obtenerTodas();
        }

        if (q != null && !q.isEmpty()) {
            compras = compraService.buscar(q, q);
        }

        List<CompraDTO> dtos = mapToDTO(compras);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompraDTO> obtenerPorId(@PathVariable Integer id) {
        Optional<Compra> compra = compraService.obtenerPorId(id);
        if (compra.isPresent()) {
            return ResponseEntity.ok(mapToDTO(compra.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CompraDTO> crear(@RequestBody CompraDTO compraDTO) {
        Compra compra = mapToEntity(compraDTO);
        Compra creada = compraService.crear(compra);
        return ResponseEntity.ok(mapToDTO(creada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        compraService.eliminar(id);
        return ResponseEntity.ok("Compra eliminada");
    }

    @GetMapping("/stats/total")
    public ResponseEntity<BigDecimal> obtenerTotal(
            @RequestParam(required = false) String desde,
            @RequestParam(required = false) String hasta) {
        LocalDate inicio = desde != null ? LocalDate.parse(desde) : LocalDate.now().withDayOfMonth(1);
        LocalDate fin = hasta != null ? LocalDate.parse(hasta) : LocalDate.now();
        BigDecimal total = compraService.obtenerTotalPorPeriodo(inicio, fin);
        return ResponseEntity.ok(total != null ? total : BigDecimal.ZERO);
    }

    private CompraDTO mapToDTO(Compra c) {
        CompraDTO dto = new CompraDTO();
        dto.setId(c.getId());
        dto.setFecha(c.getFecha());
        dto.setProductoId(c.getProductoId());
        dto.setProductoNombre(c.getProductoNombre());
        dto.setCantidad(c.getCantidad());
        dto.setCosto(c.getCosto());
        dto.setTotal(c.getTotal());
        dto.setProveedor(c.getProveedor());
        dto.setUsuarioId(c.getUsuarioId());
        return dto;
    }

    private List<CompraDTO> mapToDTO(List<Compra> compras) {
        return compras.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private Compra mapToEntity(CompraDTO dto) {
        Compra c = new Compra();
        c.setFecha(dto.getFecha());
        c.setProductoId(dto.getProductoId());
        c.setProductoNombre(dto.getProductoNombre());
        c.setCantidad(dto.getCantidad());
        c.setCosto(dto.getCosto());
        c.setTotal(dto.getTotal());
        c.setProveedor(dto.getProveedor());
        c.setUsuarioId(dto.getUsuarioId());
        return c;
    }
}
