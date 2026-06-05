package com.example.Sistema.de.Farmacia.dto;

import java.math.BigDecimal;

public class DashboardStatsDTO {
    private BigDecimal ventasTotales;
    private BigDecimal gananciaTotal;
    private Integer totalVentas;
    private Integer totalCompras;
    private BigDecimal comprasTotales;
    private Integer alertasCount;
    private Integer productosConStockBajo;
    private Integer productosPorVencer;
    private BigDecimal ventasHoy;
    private Integer ventasHoyCount;
    private BigDecimal ventasSemana;
    private Integer ventasSemanaCount;
    private BigDecimal ventasMes;
    private Integer ventasMesCount;
    private Integer margenGanancia;

    public DashboardStatsDTO() {}

    // Getters y Setters
    public BigDecimal getVentasTotales() { return ventasTotales; }
    public void setVentasTotales(BigDecimal ventasTotales) { this.ventasTotales = ventasTotales; }

    public BigDecimal getGananciaTotal() { return gananciaTotal; }
    public void setGananciaTotal(BigDecimal gananciaTotal) { this.gananciaTotal = gananciaTotal; }

    public Integer getTotalVentas() { return totalVentas; }
    public void setTotalVentas(Integer totalVentas) { this.totalVentas = totalVentas; }

    public Integer getTotalCompras() { return totalCompras; }
    public void setTotalCompras(Integer totalCompras) { this.totalCompras = totalCompras; }

    public BigDecimal getComprasTotales() { return comprasTotales; }
    public void setComprasTotales(BigDecimal comprasTotales) { this.comprasTotales = comprasTotales; }

    public Integer getAlertasCount() { return alertasCount; }
    public void setAlertasCount(Integer alertasCount) { this.alertasCount = alertasCount; }

    public Integer getProductosConStockBajo() { return productosConStockBajo; }
    public void setProductosConStockBajo(Integer productosConStockBajo) { this.productosConStockBajo = productosConStockBajo; }

    public Integer getProductosPorVencer() { return productosPorVencer; }
    public void setProductosPorVencer(Integer productosPorVencer) { this.productosPorVencer = productosPorVencer; }

    public BigDecimal getVentasHoy() { return ventasHoy; }
    public void setVentasHoy(BigDecimal ventasHoy) { this.ventasHoy = ventasHoy; }

    public Integer getVentasHoyCount() { return ventasHoyCount; }
    public void setVentasHoyCount(Integer ventasHoyCount) { this.ventasHoyCount = ventasHoyCount; }

    public BigDecimal getVentasSemana() { return ventasSemana; }
    public void setVentasSemana(BigDecimal ventasSemana) { this.ventasSemana = ventasSemana; }

    public Integer getVentasSemanaCount() { return ventasSemanaCount; }
    public void setVentasSemanaCount(Integer ventasSemanaCount) { this.ventasSemanaCount = ventasSemanaCount; }

    public BigDecimal getVentasMes() { return ventasMes; }
    public void setVentasMes(BigDecimal ventasMes) { this.ventasMes = ventasMes; }

    public Integer getVentasMesCount() { return ventasMesCount; }
    public void setVentasMesCount(Integer ventasMesCount) { this.ventasMesCount = ventasMesCount; }

    public Integer getMargenGanancia() { return margenGanancia; }
    public void setMargenGanancia(Integer margenGanancia) { this.margenGanancia = margenGanancia; }
}
