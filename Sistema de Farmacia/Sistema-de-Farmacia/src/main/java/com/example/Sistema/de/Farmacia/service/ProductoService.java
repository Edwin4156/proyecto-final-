package com.example.Sistema.de.Farmacia.service;

import com.example.Sistema.de.Farmacia.model.Producto;
import com.example.Sistema.de.Farmacia.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private LogsService logsService;

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> obtenerPorId(Integer id) {
        return productoRepository.findById(id);
    }

    public Producto crear(Producto producto) {
        Producto guardado = productoRepository.save(producto);
        logsService.crear("CREAR_PRODUCTO", producto.getNombre(), "PRODUCTOS");
        return guardado;
    }

    public Producto actualizar(Integer id, Producto productoActualizado) {
        Optional<Producto> existing = productoRepository.findById(id);
        if (existing.isPresent()) {
            Producto p = existing.get();
            p.setNombre(productoActualizado.getNombre());
            p.setCategoria(productoActualizado.getCategoria());
            p.setPrecio(productoActualizado.getPrecio());
            p.setCosto(productoActualizado.getCosto());
            p.setStock(productoActualizado.getStock());
            p.setStockMin(productoActualizado.getStockMin());
            p.setLote(productoActualizado.getLote());
            p.setCodigoBarras(productoActualizado.getCodigoBarras());
            p.setProveedor(productoActualizado.getProveedor());
            p.setFechaIngreso(productoActualizado.getFechaIngreso());
            p.setFechaCaducidad(productoActualizado.getFechaCaducidad());
            logsService.crear("EDITAR_PRODUCTO", p.getNombre(), "PRODUCTOS");
            return productoRepository.save(p);
        }
        return null;
    }

    public void eliminar(Integer id) {
        Optional<Producto> existing = productoRepository.findById(id);
        if (existing.isPresent()) {
            logsService.crear("ELIMINAR_PRODUCTO", existing.get().getNombre(), "PRODUCTOS");
            productoRepository.deleteById(id);
        }
    }

    public List<Producto> buscar(String termino) {
        return productoRepository.searchByNombreCategoriaProveedor(termino);
    }

    public List<Producto> obtenerPorCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria);
    }

    public List<Producto> obtenerConStockBajo() {
        return productoRepository.findProductosConStockBajo();
    }

    public List<Producto> obtenerPorVencer() {
        LocalDate fecha30Dias = LocalDate.now().plusDays(30);
        return productoRepository.findProductosPorVencer(fecha30Dias);
    }

    public List<Producto> obtenerCaducados() {
        return productoRepository.findByFechaCaducidadLessThanEqual(LocalDate.now());
    }

    public void actualizarStock(Integer productoId, Integer cantidad) {
        Optional<Producto> p = productoRepository.findById(productoId);
        if (p.isPresent()) {
            Producto producto = p.get();
            producto.setStock(producto.getStock() + cantidad);
            productoRepository.save(producto);
        }
    }
}
