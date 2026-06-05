package com.example.Sistema.de.Farmacia.controller;

import com.example.Sistema.de.Farmacia.dto.VentaDTO;
import com.example.Sistema.de.Farmacia.model.Venta;
import com.example.Sistema.de.Farmacia.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ventas")
@CrossOrigin(origins = "*")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<VentaDTO>> obtenerTodas(
            @RequestParam(required = false) String filtro,
            @RequestParam(required = false) String q) {
        List<Venta> ventas;

        if ("dia".equals(filtro)) {
            ventas = ventaService.obtenerHoy();
        } else if ("semana".equals(filtro)) {
            LocalDate hace7 = LocalDate.now().minusDays(7);
            ventas = ventaService.obtenerPorPeriodo(hace7, LocalDate.now());
        } else if ("mes".equals(filtro)) {
            LocalDate primerDiaMes = LocalDate.now().withDayOfMonth(1);
            ventas = ventaService.obtenerPorPeriodo(primerDiaMes, LocalDate.now());
        } else {
            ventas = ventaService.obtenerTodas();
        }

        if (q != null && !q.isEmpty()) {
            ventas = ventaService.buscar(q, q);
        }

        List<VentaDTO> dtos = mapToDTO(ventas);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaDTO> obtenerPorId(@PathVariable Integer id) {
        Optional<Venta> venta = ventaService.obtenerPorId(id);
        if (venta.isPresent()) {
            return ResponseEntity.ok(mapToDTO(venta.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<VentaDTO> crear(@RequestBody VentaDTO ventaDTO) {
        Venta venta = mapToEntity(ventaDTO);
        Venta creada = ventaService.crear(venta);
        return ResponseEntity.ok(mapToDTO(creada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        ventaService.eliminar(id);
        return ResponseEntity.ok("Venta eliminada");
    }

    @GetMapping("/stats/total")
    public ResponseEntity<BigDecimal> obtenerTotal(
            @RequestParam(required = false) String desde,
            @RequestParam(required = false) String hasta) {
        LocalDate inicio = desde != null ? LocalDate.parse(desde) : LocalDate.now().withDayOfMonth(1);
        LocalDate fin = hasta != null ? LocalDate.parse(hasta) : LocalDate.now();
        BigDecimal total = ventaService.obtenerTotalPorPeriodo(inicio, fin);
        return ResponseEntity.ok(total != null ? total : BigDecimal.ZERO);
    }

    private VentaDTO mapToDTO(Venta v) {
        VentaDTO dto = new VentaDTO();
        dto.setId(v.getId());
        dto.setFecha(v.getFecha());
        dto.setProductoId(v.getProductoId());
        dto.setProducto(v.getProductoNombre());
        dto.setCantidad(v.getCantidad());
        dto.setPrecio(v.getPrecio());
        dto.setTotal(v.getTotal());
        dto.setCliente(v.getCliente());
        dto.setNit(v.getNit());
        dto.setDescuento(v.getDescuento());
        dto.setMetodoPago(v.getMetodoPago());
        dto.setUsuario(v.getUsuario() != null ? v.getUsuario().getUsuario() : "");
        return dto;
    }

    private List<VentaDTO> mapToDTO(List<Venta> ventas) {
        return ventas.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private Venta mapToEntity(VentaDTO dto) {
        Venta v = new Venta();
        v.setFecha(dto.getFecha());
        v.setProductoId(dto.getProductoId());
        v.setProductoNombre(dto.getProducto());
        v.setCantidad(dto.getCantidad());
        v.setPrecio(dto.getPrecio());
        v.setTotal(dto.getTotal());
        v.setCliente(dto.getCliente());
        v.setNit(dto.getNit());
        v.setDescuento(dto.getDescuento());
        v.setMetodoPago(dto.getMetodoPago());
        if (dto.getUsuarioId() != null) {
            v.setUsuarioId(dto.getUsuarioId());
        }
        return v;
    }
}
