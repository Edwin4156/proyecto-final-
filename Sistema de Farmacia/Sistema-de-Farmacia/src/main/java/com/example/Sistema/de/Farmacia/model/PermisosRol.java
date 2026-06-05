package com.example.Sistema.de.Farmacia.model;

import jakarta.persistence.*;

@Entity
@Table(name = "permisos_rol")
public class PermisosRol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)
    private String rol;

    @Column(name = "dashboard_r", nullable = false)
    private Boolean dashboardR;

    @Column(name = "dashboard_w", nullable = false)
    private Boolean dashboardW;

    @Column(name = "dashboard_d", nullable = false)
    private Boolean dashboardD;

    @Column(name = "productos_r", nullable = false)
    private Boolean productosR;

    @Column(name = "productos_w", nullable = false)
    private Boolean productosW;

    @Column(name = "productos_d", nullable = false)
    private Boolean productosD;

    @Column(name = "inventario_r", nullable = false)
    private Boolean inventarioR;

    @Column(name = "inventario_w", nullable = false)
    private Boolean inventarioW;

    @Column(name = "inventario_d", nullable = false)
    private Boolean inventarioD;

    @Column(name = "ventas_r", nullable = false)
    private Boolean ventasR;

    @Column(name = "ventas_w", nullable = false)
    private Boolean ventasW;

    @Column(name = "ventas_d", nullable = false)
    private Boolean ventasD;

    @Column(name = "compras_r", nullable = false)
    private Boolean comprasR;

    @Column(name = "compras_w", nullable = false)
    private Boolean comprasW;

    @Column(name = "compras_d", nullable = false)
    private Boolean comprasD;

    @Column(name = "alertas_r", nullable = false)
    private Boolean alertasR;

    @Column(name = "alertas_w", nullable = false)
    private Boolean alertasW;

    @Column(name = "alertas_d", nullable = false)
    private Boolean alertasD;

    @Column(name = "usuarios_r", nullable = false)
    private Boolean usuariosR;

    @Column(name = "usuarios_w", nullable = false)
    private Boolean usuariosW;

    @Column(name = "usuarios_d", nullable = false)
    private Boolean usuariosD;

    @Column(name = "reportes_r", nullable = false)
    private Boolean reportesR;

    @Column(name = "reportes_w", nullable = false)
    private Boolean reportesW;

    @Column(name = "reportes_d", nullable = false)
    private Boolean reportesD;

    @Column(name = "facturacion_r", nullable = false)
    private Boolean facturacionR;

    @Column(name = "facturacion_w", nullable = false)
    private Boolean facturacionW;

    @Column(name = "facturacion_d", nullable = false)
    private Boolean facturacionD;

    @Column(name = "pos_r", nullable = false)
    private Boolean posR;

    @Column(name = "pos_w", nullable = false)
    private Boolean posW;

    @Column(name = "pos_d", nullable = false)
    private Boolean posD;

    @Column(name = "kardex_r", nullable = false)
    private Boolean kardexR;

    @Column(name = "kardex_w", nullable = false)
    private Boolean kardexW;

    @Column(name = "kardex_d", nullable = false)
    private Boolean kardexD;

    @Column(name = "ia_r", nullable = false)
    private Boolean iaR;

    @Column(name = "ia_w", nullable = false)
    private Boolean iaW;

    @Column(name = "ia_d", nullable = false)
    private Boolean iaD;

    // Constructores
    public PermisosRol() {}

    public PermisosRol(String rol) {
        this.rol = rol;
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public Boolean getDashboardR() { return dashboardR; }
    public void setDashboardR(Boolean dashboardR) { this.dashboardR = dashboardR; }

    public Boolean getDashboardW() { return dashboardW; }
    public void setDashboardW(Boolean dashboardW) { this.dashboardW = dashboardW; }

    public Boolean getDashboardD() { return dashboardD; }
    public void setDashboardD(Boolean dashboardD) { this.dashboardD = dashboardD; }

    public Boolean getProductosR() { return productosR; }
    public void setProductosR(Boolean productosR) { this.productosR = productosR; }

    public Boolean getProductosW() { return productosW; }
    public void setProductosW(Boolean productosW) { this.productosW = productosW; }

    public Boolean getProductosD() { return productosD; }
    public void setProductosD(Boolean productosD) { this.productosD = productosD; }

    public Boolean getInventarioR() { return inventarioR; }
    public void setInventarioR(Boolean inventarioR) { this.inventarioR = inventarioR; }

    public Boolean getInventarioW() { return inventarioW; }
    public void setInventarioW(Boolean inventarioW) { this.inventarioW = inventarioW; }

    public Boolean getInventarioD() { return inventarioD; }
    public void setInventarioD(Boolean inventarioD) { this.inventarioD = inventarioD; }

    public Boolean getVentasR() { return ventasR; }
    public void setVentasR(Boolean ventasR) { this.ventasR = ventasR; }

    public Boolean getVentasW() { return ventasW; }
    public void setVentasW(Boolean ventasW) { this.ventasW = ventasW; }

    public Boolean getVentasD() { return ventasD; }
    public void setVentasD(Boolean ventasD) { this.ventasD = ventasD; }

    public Boolean getComprasR() { return comprasR; }
    public void setComprasR(Boolean comprasR) { this.comprasR = comprasR; }

    public Boolean getComprasW() { return comprasW; }
    public void setComprasW(Boolean comprasW) { this.comprasW = comprasW; }

    public Boolean getComprasD() { return comprasD; }
    public void setComprasD(Boolean comprasD) { this.comprasD = comprasD; }

    public Boolean getAlertasR() { return alertasR; }
    public void setAlertasR(Boolean alertasR) { this.alertasR = alertasR; }

    public Boolean getAlertasW() { return alertasW; }
    public void setAlertasW(Boolean alertasW) { this.alertasW = alertasW; }

    public Boolean getAlertasD() { return alertasD; }
    public void setAlertasD(Boolean alertasD) { this.alertasD = alertasD; }

    public Boolean getUsuariosR() { return usuariosR; }
    public void setUsuariosR(Boolean usuariosR) { this.usuariosR = usuariosR; }

    public Boolean getUsuariosW() { return usuariosW; }
    public void setUsuariosW(Boolean usuariosW) { this.usuariosW = usuariosW; }

    public Boolean getUsuariosD() { return usuariosD; }
    public void setUsuariosD(Boolean usuariosD) { this.usuariosD = usuariosD; }

    public Boolean getReportesR() { return reportesR; }
    public void setReportesR(Boolean reportesR) { this.reportesR = reportesR; }

    public Boolean getReportesW() { return reportesW; }
    public void setReportesW(Boolean reportesW) { this.reportesW = reportesW; }

    public Boolean getReportesD() { return reportesD; }
    public void setReportesD(Boolean reportesD) { this.reportesD = reportesD; }

    public Boolean getFacturacionR() { return facturacionR; }
    public void setFacturacionR(Boolean facturacionR) { this.facturacionR = facturacionR; }

    public Boolean getFacturacionW() { return facturacionW; }
    public void setFacturacionW(Boolean facturacionW) { this.facturacionW = facturacionW; }

    public Boolean getFacturacionD() { return facturacionD; }
    public void setFacturacionD(Boolean facturacionD) { this.facturacionD = facturacionD; }

    public Boolean getPosR() { return posR; }
    public void setPosR(Boolean posR) { this.posR = posR; }

    public Boolean getPosW() { return posW; }
    public void setPosW(Boolean posW) { this.posW = posW; }

    public Boolean getPosD() { return posD; }
    public void setPosD(Boolean posD) { this.posD = posD; }

    public Boolean getKardexR() { return kardexR; }
    public void setKardexR(Boolean kardexR) { this.kardexR = kardexR; }

    public Boolean getKardexW() { return kardexW; }
    public void setKardexW(Boolean kardexW) { this.kardexW = kardexW; }

    public Boolean getKardexD() { return kardexD; }
    public void setKardexD(Boolean kardexD) { this.kardexD = kardexD; }

    public Boolean getIaR() { return iaR; }
    public void setIaR(Boolean iaR) { this.iaR = iaR; }

    public Boolean getIaW() { return iaW; }
    public void setIaW(Boolean iaW) { this.iaW = iaW; }

    public Boolean getIaD() { return iaD; }
    public void setIaD(Boolean iaD) { this.iaD = iaD; }
}
