package com.example.Sistema.de.Farmacia.repository;

import com.example.Sistema.de.Farmacia.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findByCategoria(String categoria);
    List<Producto> findByStockLessThanEqual(Integer stock);
    List<Producto> findByFechaCaducidadLessThanEqual(LocalDate fecha);

    @Query("SELECT p FROM Producto p WHERE p.nombre LIKE %:nombre% OR p.categoria LIKE %:nombre% OR p.proveedor LIKE %:nombre%")
    List<Producto> searchByNombreCategoriaProveedor(@Param("nombre") String nombre);

    @Query("SELECT p FROM Producto p WHERE p.stock <= p.stockMin")
    List<Producto> findProductosConStockBajo();

    @Query("SELECT p FROM Producto p WHERE p.fechaCaducidad <= :fecha AND p.fechaCaducidad > CURRENT_DATE")
    List<Producto> findProductosPorVencer(@Param("fecha") LocalDate fecha);
}
