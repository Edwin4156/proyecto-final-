package com.example.Sistema.de.Farmacia.service;

import com.example.Sistema.de.Farmacia.model.Kardex;
import com.example.Sistema.de.Farmacia.repository.KardexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class KardexService {

    @Autowired
    private KardexRepository kardexRepository;

    public List<Kardex> obtenerTodos() {
        return kardexRepository.findAll();
    }

    public List<Kardex> obtenerMovimientosProducto(Integer productoId) {
        return kardexRepository.findHistorialProducto(productoId);
    }

    public List<Kardex> obtenerPorFecha(LocalDate fecha) {
        return kardexRepository.findMovimientosPorFecha(fecha);
    }

    public List<Kardex> obtenerPorPeriodo(LocalDate inicio, LocalDate fin) {
        return kardexRepository.findByFechaBetween(inicio, fin);
    }

    public List<Kardex> obtenerEntradas() {
        return kardexRepository.findByTipo("ENTRADA");
    }

    public List<Kardex> obtenerSalidas() {
        return kardexRepository.findByTipo("SALIDA");
    }

    public List<Kardex> buscarProducto(String producto) {
        return kardexRepository.searchByProducto(producto);
    }

    public List<Kardex> obtenerPorUsuario(Integer usuarioId) {
        return kardexRepository.findByUsuarioId(usuarioId);
    }
}
