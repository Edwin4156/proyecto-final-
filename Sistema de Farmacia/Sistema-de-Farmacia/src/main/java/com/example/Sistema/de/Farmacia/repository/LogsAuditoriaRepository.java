package com.example.Sistema.de.Farmacia.repository;

import com.example.Sistema.de.Farmacia.model.LogsAuditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LogsAuditoriaRepository extends JpaRepository<LogsAuditoria, Integer> {
    List<LogsAuditoria> findByUsuario(String usuario);
    List<LogsAuditoria> findByAccion(String accion);
    List<LogsAuditoria> findByModulo(String modulo);

    @Query("SELECT l FROM LogsAuditoria l WHERE l.fechaHora BETWEEN :inicio AND :fin ORDER BY l.fechaHora DESC")
    List<LogsAuditoria> findByFechaBetween(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);

    @Query(value = "SELECT * FROM logs_auditoria ORDER BY fecha_hora DESC LIMIT 100", nativeQuery = true)
    List<LogsAuditoria> findLast100();

    @Query("SELECT l FROM LogsAuditoria l WHERE l.usuario = :usuario ORDER BY l.fechaHora DESC")
    List<LogsAuditoria> findHistorialUsuario(@Param("usuario") String usuario);
}
