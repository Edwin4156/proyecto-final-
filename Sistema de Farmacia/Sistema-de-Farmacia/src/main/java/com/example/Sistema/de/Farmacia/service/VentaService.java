package com.example.Sistema.de.Farmacia.service;

import com.example.Sistema.de.Farmacia.model.Venta;
import com.example.Sistema.de.Farmacia.model.Kardex;
import com.example.Sistema.de.Farmacia.repository.VentaRepository;
import com.example.Sistema.de.Farmacia.repository.KardexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private KardexRepository kardexRepository;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private LogsService logsService;

    public List<Venta> obtenerTodas() {
        return ventaRepository.findAll();
    }

    public Optional<Venta> obtenerPorId(Integer id) {
        return ventaRepository.findById(id);
    }

    @Transactional
    public Venta crear(Venta venta) {
        productoService.actualizarStock(venta.getProductoId(), -venta.getCantidad());
        Venta guardada = ventaRepository.save(venta);

        Kardex mov = new Kardex();
        mov.setFecha(venta.getFecha());
        mov.setTipo("SALIDA");
        mov.setProductoId(venta.getProductoId());
        mov.setProductoNombre(venta.getProductoNombre());
        mov.setCantidad(venta.getCantidad());
        mov.setReferencia("Venta #" + String.format("%04d", guardada.getId()));
        mov.setUsuarioId(venta.getUsuarioId());
        kardexRepository.save(mov);

        logsService.crear("CREAR_VENTA", venta.getProductoNombre() + " x" + venta.getCantidad(), "VENTAS");
        return guardada;
    }

    public void eliminar(Integer id) {
        Optional<Venta> existing = ventaRepository.findById(id);
        if (existing.isPresent()) {
            Venta v = existing.get();
            productoService.actualizarStock(v.getProductoId(), v.getCantidad());
            kardexRepository.deleteAll(kardexRepository.findByProductoId(v.getProductoId()));
            logsService.crear("ELIMINAR_VENTA", v.getProductoNombre(), "VENTAS");
            ventaRepository.deleteById(id);
        }
    }

    public List<Venta> obtenerPorFecha(LocalDate fecha) {
        return ventaRepository.findVentasPorFecha(fecha);
    }

    public List<Venta> obtenerPorPeriodo(LocalDate inicio, LocalDate fin) {
        return ventaRepository.findByFechaBetween(inicio, fin);
    }

    public List<Venta> obtenerHoy() {
        return ventaRepository.findVentasHoy();
    }

    public List<Venta> obtenerPorUsuario(Integer usuarioId) {
        return ventaRepository.findByUsuarioId(usuarioId);
    }

    public List<Venta> obtenerPorProducto(Integer productoId) {
        return ventaRepository.findByProductoId(productoId);
    }

    public List<Venta> buscar(String producto, String cliente) {
        return ventaRepository.searchByProductoOrCliente(producto, cliente);
    }

    public BigDecimal obtenerTotalPorPeriodo(LocalDate inicio, LocalDate fin) {
        return ventaRepository.sumTotalByPeriodo(inicio, fin);
    }
}
