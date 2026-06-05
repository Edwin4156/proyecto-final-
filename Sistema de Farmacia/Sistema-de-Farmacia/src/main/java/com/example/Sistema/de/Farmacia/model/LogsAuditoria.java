package com.example.Sistema.de.Farmacia.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "logs_auditoria", indexes = {
    @Index(name = "idx_logs_fecha", columnList = "fecha_hora")
})
public class LogsAuditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CreationTimestamp
    @Column(name = "fecha_hora", nullable = false, updatable = false)
    private LocalDateTime fechaHora;

    @Column(length = 50)
    private String usuario;

    @Column(length = 100)
    private String accion;

    @Column(length = 500)
    private String detalle;

    @Column(length = 50)
    private String modulo;

    // Constructores
    public LogsAuditoria() {}

    public LogsAuditoria(String usuario, String accion, String detalle, String modulo) {
        this.usuario = usuario;
        this.accion = accion;
        this.detalle = detalle;
        this.modulo = modulo;
    }

    public LogsAuditoria(String usuario, String accion, String detalle) {
        this.usuario = usuario;
        this.accion = accion;
        this.detalle = detalle;
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getAccion() { return accion; }
    public void setAccion(String accion) { this.accion = accion; }

    public String getDetalle() { return detalle; }
    public void setDetalle(String detalle) { this.detalle = detalle; }

    public String getModulo() { return modulo; }
    public void setModulo(String modulo) { this.modulo = modulo; }
}
