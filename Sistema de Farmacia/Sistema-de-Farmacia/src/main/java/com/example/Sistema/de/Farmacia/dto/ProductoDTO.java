package com.example.Sistema.de.Farmacia.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ProductoDTO {
    private Integer id;
    private String nombre;
    private String categoria;
    @DecimalMin(value = "0", message = "Precio no puede ser negativo")
    private BigDecimal precio;
    @DecimalMin(value = "0", message = "Costo no puede ser negativo")
    private BigDecimal costo;
    @Min(value = 0, message = "Stock no puede ser negativo")
    private Integer stock;
    @Min(value = 0, message = "Stock mínimo no puede ser negativo")
    private Integer stockMin;
    private String lote;
    private String codigoBarras;
    private String proveedor;
    private LocalDate fechaIngreso;
    private LocalDate fechaCaducidad;

    public ProductoDTO() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public BigDecimal getCosto() { return costo; }
    public void setCosto(BigDecimal costo) { this.costo = costo; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Integer getStockMin() { return stockMin; }
    public void setStockMin(Integer stockMin) { this.stockMin = stockMin; }

    public String getLote() { return lote; }
    public void setLote(String lote) { this.lote = lote; }

    public String getCodigoBarras() { return codigoBarras; }
    public void setCodigoBarras(String codigoBarras) { this.codigoBarras = codigoBarras; }

    public String getProveedor() { return proveedor; }
    public void setProveedor(String proveedor) { this.proveedor = proveedor; }

    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }

    public LocalDate getFechaCaducidad() { return fechaCaducidad; }
    public void setFechaCaducidad(LocalDate fechaCaducidad) { this.fechaCaducidad = fechaCaducidad; }
}
