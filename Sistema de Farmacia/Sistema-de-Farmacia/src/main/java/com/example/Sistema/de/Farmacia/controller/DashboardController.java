package com.example.Sistema.de.Farmacia.controller;

import com.example.Sistema.de.Farmacia.dto.DashboardStatsDTO;
import com.example.Sistema.de.Farmacia.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    @Autowired
    private VentaService ventaService;

    @Autowired
    private CompraService compraService;

    @Autowired
    private ProductoService productoService;

    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDTO> obtenerStats() {
        DashboardStatsDTO stats = new DashboardStatsDTO();

        BigDecimal ventasTotal = ventaService.obtenerTotalPorPeriodo(
            LocalDate.now().withDayOfMonth(1), LocalDate.now());
        stats.setVentasTotales(ventasTotal != null ? ventasTotal : BigDecimal.ZERO);
        stats.setTotalVentas(ventaService.obtenerTodas().size());

        BigDecimal comprasTotal = compraService.obtenerTotalPorPeriodo(
            LocalDate.now().withDayOfMonth(1), LocalDate.now());
        stats.setComprasTotales(comprasTotal != null ? comprasTotal : BigDecimal.ZERO);
        stats.setTotalCompras(compraService.obtenerTodas().size());

        // Ganancia = Ventas - Compras
        BigDecimal ganancia = (ventasTotal != null ? ventasTotal : BigDecimal.ZERO)
            .subtract(comprasTotal != null ? comprasTotal : BigDecimal.ZERO);
        stats.setGananciaTotal(ganancia);

        // Margen
        if (ventasTotal != null && ventasTotal.compareTo(BigDecimal.ZERO) > 0) {
            stats.setMargenGanancia(ganancia.multiply(new BigDecimal(100))
                .divide(ventasTotal, 0, java.math.RoundingMode.HALF_UP).intValue());
        } else {
            stats.setMargenGanancia(0);
        }

        // Hoy
        BigDecimal ventasHoy = ventaService.obtenerTotalPorPeriodo(LocalDate.now(), LocalDate.now());
        stats.setVentasHoy(ventasHoy != null ? ventasHoy : BigDecimal.ZERO);
        stats.setVentasHoyCount(ventaService.obtenerHoy().size());

        // Semana
        LocalDate hace7 = LocalDate.now().minusDays(7);
        BigDecimal ventasSemana = ventaService.obtenerTotalPorPeriodo(hace7, LocalDate.now());
        stats.setVentasSemana(ventasSemana != null ? ventasSemana : BigDecimal.ZERO);
        stats.setVentasSemanaCount(ventaService.obtenerPorPeriodo(hace7, LocalDate.now()).size());

        // Mes
        LocalDate primerDia = LocalDate.now().withDayOfMonth(1);
        BigDecimal ventasMes = ventaService.obtenerTotalPorPeriodo(primerDia, LocalDate.now());
        stats.setVentasMes(ventasMes != null ? ventasMes : BigDecimal.ZERO);
        stats.setVentasMesCount(ventaService.obtenerPorPeriodo(primerDia, LocalDate.now()).size());

        // Alertas
        int stockBajo = productoService.obtenerConStockBajo().size();
        int porVencer = productoService.obtenerPorVencer().size();
        stats.setProductosConStockBajo(stockBajo);
        stats.setProductosPorVencer(porVencer);
        stats.setAlertasCount(stockBajo + porVencer);

        return ResponseEntity.ok(stats);
    }
}
