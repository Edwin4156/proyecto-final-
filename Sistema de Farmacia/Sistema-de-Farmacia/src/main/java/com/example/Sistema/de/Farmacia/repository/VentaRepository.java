package com.example.Sistema.de.Farmacia.repository;

import com.example.Sistema.de.Farmacia.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {
    List<Venta> findByFechaBetween(LocalDate inicio, LocalDate fin);
    List<Venta> findByProductoId(Integer productoId);
    List<Venta> findByUsuarioId(Integer usuarioId);
    List<Venta> findByCliente(String cliente);

    @Query("SELECT v FROM Venta v WHERE v.fecha = CURRENT_DATE")
    List<Venta> findVentasHoy();

    @Query("SELECT v FROM Venta v WHERE v.fecha >= :fecha ORDER BY v.fecha DESC")
    List<Venta> findVentasPorFecha(@Param("fecha") LocalDate fecha);

    @Query("SELECT SUM(v.total) FROM Venta v WHERE v.fecha BETWEEN :inicio AND :fin")
    BigDecimal sumTotalByPeriodo(@Param("inicio") LocalDate inicio, @Param("fin") LocalDate fin);

    @Query("SELECT v FROM Venta v WHERE v.productoNombre LIKE %:producto% OR v.cliente LIKE %:cliente%")
    List<Venta> searchByProductoOrCliente(@Param("producto") String producto, @Param("cliente") String cliente);
}
