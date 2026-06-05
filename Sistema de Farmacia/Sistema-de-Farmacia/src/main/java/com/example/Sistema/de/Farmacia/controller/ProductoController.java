package com.example.Sistema.de.Farmacia.controller;

import com.example.Sistema.de.Farmacia.dto.ProductoDTO;
import com.example.Sistema.de.Farmacia.model.Producto;
import com.example.Sistema.de.Farmacia.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> obtenerTodos(@RequestParam(required = false) String q) {
        List<Producto> productos;
        if (q != null && !q.isEmpty()) {
            productos = productoService.buscar(q);
        } else {
            productos = productoService.obtenerTodos();
        }
        List<ProductoDTO> dtos = mapToDTO(productos);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtenerPorId(@PathVariable Integer id) {
        Optional<Producto> producto = productoService.obtenerPorId(id);
        if (producto.isPresent()) {
            return ResponseEntity.ok(mapToDTO(producto.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> crear(@RequestBody ProductoDTO productoDTO) {
        Producto producto = mapToEntity(productoDTO);
        Producto creado = productoService.crear(producto);
        return ResponseEntity.ok(mapToDTO(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizar(@PathVariable Integer id, @RequestBody ProductoDTO productoDTO) {
        Producto producto = mapToEntity(productoDTO);
        Producto actualizado = productoService.actualizar(id, producto);
        if (actualizado != null) {
            return ResponseEntity.ok(mapToDTO(actualizado));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        productoService.eliminar(id);
        return ResponseEntity.ok("Producto eliminado");
    }

    @GetMapping("/stock-bajo")
    public ResponseEntity<List<ProductoDTO>> obtenerConStockBajo() {
        List<Producto> productos = productoService.obtenerConStockBajo();
        List<ProductoDTO> dtos = mapToDTO(productos);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/por-vencer")
    public ResponseEntity<List<ProductoDTO>> obtenerPorVencer() {
        List<Producto> productos = productoService.obtenerPorVencer();
        List<ProductoDTO> dtos = mapToDTO(productos);
        return ResponseEntity.ok(dtos);
    }

    private ProductoDTO mapToDTO(Producto p) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(p.getId());
        dto.setNombre(p.getNombre());
        dto.setCategoria(p.getCategoria());
        dto.setPrecio(p.getPrecio());
        dto.setCosto(p.getCosto());
        dto.setStock(p.getStock());
        dto.setStockMin(p.getStockMin());
        dto.setLote(p.getLote());
        dto.setCodigoBarras(p.getCodigoBarras());
        dto.setProveedor(p.getProveedor());
        dto.setFechaIngreso(p.getFechaIngreso());
        dto.setFechaCaducidad(p.getFechaCaducidad());
        return dto;
    }

    private List<ProductoDTO> mapToDTO(List<Producto> productos) {
        return productos.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private Producto mapToEntity(ProductoDTO dto) {
        Producto p = new Producto();
        p.setNombre(dto.getNombre());
        p.setCategoria(dto.getCategoria());
        p.setPrecio(dto.getPrecio());
        p.setCosto(dto.getCosto());
        p.setStock(dto.getStock());
        p.setStockMin(dto.getStockMin());
        p.setLote(dto.getLote());
        p.setCodigoBarras(dto.getCodigoBarras());
        p.setProveedor(dto.getProveedor());
        p.setFechaIngreso(dto.getFechaIngreso());
        p.setFechaCaducidad(dto.getFechaCaducidad());
        return p;
    }
}
