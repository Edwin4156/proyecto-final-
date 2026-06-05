package com.example.Sistema.de.Farmacia.service;

import com.example.Sistema.de.Farmacia.model.Compra;
import com.example.Sistema.de.Farmacia.model.Kardex;
import com.example.Sistema.de.Farmacia.repository.CompraRepository;
import com.example.Sistema.de.Farmacia.repository.KardexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private KardexRepository kardexRepository;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private LogsService logsService;

    public List<Compra> obtenerTodas() {
        return compraRepository.findAll();
    }

    public Optional<Compra> obtenerPorId(Integer id) {
        return compraRepository.findById(id);
    }

    @Transactional
    public Compra crear(Compra compra) {
        productoService.actualizarStock(compra.getProductoId(), compra.getCantidad());
        Compra guardada = compraRepository.save(compra);

        Kardex mov = new Kardex();
        mov.setFecha(compra.getFecha());
        mov.setTipo("ENTRADA");
        mov.setProductoId(compra.getProductoId());
        mov.setProductoNombre(compra.getProductoNombre());
        mov.setCantidad(compra.getCantidad());
        mov.setReferencia("Compra - " + compra.getProveedor());
        mov.setUsuarioId(compra.getUsuarioId());
        kardexRepository.save(mov);

        logsService.crear("CREAR_COMPRA", compra.getProductoNombre() + " x" + compra.getCantidad(), "COMPRAS");
        return guardada;
    }

    public void eliminar(Integer id) {
        Optional<Compra> existing = compraRepository.findById(id);
        if (existing.isPresent()) {
            Compra c = existing.get();
            productoService.actualizarStock(c.getProductoId(), -c.getCantidad());
            kardexRepository.deleteAll(kardexRepository.findByProductoId(c.getProductoId()));
            logsService.crear("ELIMINAR_COMPRA", c.getProductoNombre(), "COMPRAS");
            compraRepository.deleteById(id);
        }
    }

    public List<Compra> obtenerPorFecha(LocalDate fecha) {
        return compraRepository.findComprasPorFecha(fecha);
    }

    public List<Compra> obtenerPorPeriodo(LocalDate inicio, LocalDate fin) {
        return compraRepository.findByFechaBetween(inicio, fin);
    }

    public List<Compra> obtenerPorProveedor(String proveedor) {
        return compraRepository.findByProveedor(proveedor);
    }

    public List<Compra> obtenerPorProducto(Integer productoId) {
        return compraRepository.findByProductoId(productoId);
    }

    public List<Compra> obtenerPorUsuario(Integer usuarioId) {
        return compraRepository.findByUsuarioId(usuarioId);
    }

    public List<Compra> buscar(String producto, String proveedor) {
        return compraRepository.searchByProductoOrProveedor(producto, proveedor);
    }

    public BigDecimal obtenerTotalPorPeriodo(LocalDate inicio, LocalDate fin) {
        return compraRepository.sumTotalByPeriodo(inicio, fin);
    }
}
