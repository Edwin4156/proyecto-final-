package com.example.Sistema.de.Farmacia.repository;

import com.example.Sistema.de.Farmacia.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Integer> {
    List<Compra> findByFechaBetween(LocalDate inicio, LocalDate fin);
    List<Compra> findByProductoId(Integer productoId);
    List<Compra> findByProveedor(String proveedor);
    List<Compra> findByUsuarioId(Integer usuarioId);

    @Query("SELECT c FROM Compra c WHERE c.fecha >= :fecha ORDER BY c.fecha DESC")
    List<Compra> findComprasPorFecha(@Param("fecha") LocalDate fecha);

    @Query("SELECT SUM(c.total) FROM Compra c WHERE c.fecha BETWEEN :inicio AND :fin")
    BigDecimal sumTotalByPeriodo(@Param("inicio") LocalDate inicio, @Param("fin") LocalDate fin);

    @Query("SELECT c FROM Compra c WHERE c.productoNombre LIKE %:producto% OR c.proveedor LIKE %:proveedor%")
    List<Compra> searchByProductoOrProveedor(@Param("producto") String producto, @Param("proveedor") String proveedor);
}
