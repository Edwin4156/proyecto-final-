package com.example.Sistema.de.Farmacia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDate;

public class VentaDTO {
    private Integer id;
    private LocalDate fecha;
    private Integer productoId;
    @JsonProperty("producto")
    private String producto;
    @Min(value = 0, message = "Cantidad no puede ser negativa")
    private Integer cantidad;
    @Min(value = 0, message = "Precio no puede ser negativo")
    private BigDecimal precio;
    private BigDecimal total;
    private String cliente;
    private String nit;
    @Min(value = 0, message = "Descuento no puede ser negativo")
    private BigDecimal descuento;
    private String metodoPago;
    @JsonProperty("usuario")
    private String usuario;
    private Integer usuarioId;

    public VentaDTO() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Integer getProductoId() { return productoId; }
    public void setProductoId(Integer productoId) { this.productoId = productoId; }

    public String getProducto() { return producto; }
    public void setProducto(String producto) { this.producto = producto; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }

    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }

    public BigDecimal getDescuento() { return descuento; }
    public void setDescuento(BigDecimal descuento) { this.descuento = descuento; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }
}
