package com.example.Sistema.de.Farmacia.repository;

import com.example.Sistema.de.Farmacia.model.Kardex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface KardexRepository extends JpaRepository<Kardex, Integer> {
    List<Kardex> findByProductoId(Integer productoId);
    List<Kardex> findByFechaBetween(LocalDate inicio, LocalDate fin);
    List<Kardex> findByTipo(String tipo);
    List<Kardex> findByUsuarioId(Integer usuarioId);

    @Query("SELECT k FROM Kardex k WHERE k.fecha >= :fecha ORDER BY k.fecha DESC")
    List<Kardex> findMovimientosPorFecha(@Param("fecha") LocalDate fecha);

    @Query("SELECT k FROM Kardex k WHERE k.productoNombre LIKE %:producto% ORDER BY k.fecha DESC")
    List<Kardex> searchByProducto(@Param("producto") String producto);

    @Query("SELECT k FROM Kardex k WHERE k.productoId = :productoId ORDER BY k.fecha DESC")
    List<Kardex> findHistorialProducto(@Param("productoId") Integer productoId);
}
