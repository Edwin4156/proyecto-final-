package com.example.Sistema.de.Farmacia.service;

import com.example.Sistema.de.Farmacia.model.PermisosRol;
import com.example.Sistema.de.Farmacia.repository.PermisosRolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class PermisosService {

    @Autowired
    private PermisosRolRepository permisosRepository;

    public Optional<PermisosRol> obtenerPermisosPorRol(String rol) {
        return permisosRepository.findByRol(rol);
    }

    public boolean tienePermiso(String rol, String modulo, String tipo) {
        Optional<PermisosRol> permisos = permisosRepository.findByRol(rol);
        if (permisos.isEmpty()) return false;

        PermisosRol p = permisos.get();
        String campo = modulo.toLowerCase() + "_" + tipo.toLowerCase();

        return switch (campo) {
            case "dashboard_r" -> p.getDashboardR();
            case "dashboard_w" -> p.getDashboardW();
            case "dashboard_d" -> p.getDashboardD();
            case "productos_r" -> p.getProductosR();
            case "productos_w" -> p.getProductosW();
            case "productos_d" -> p.getProductosD();
            case "inventario_r" -> p.getInventarioR();
            case "inventario_w" -> p.getInventarioW();
            case "inventario_d" -> p.getInventarioD();
            case "ventas_r" -> p.getVentasR();
            case "ventas_w" -> p.getVentasW();
            case "ventas_d" -> p.getVentasD();
            case "compras_r" -> p.getComprasR();
            case "compras_w" -> p.getComprasW();
            case "compras_d" -> p.getComprasD();
            case "alertas_r" -> p.getAlertasR();
            case "alertas_w" -> p.getAlertasW();
            case "alertas_d" -> p.getAlertasD();
            case "usuarios_r" -> p.getUsuariosR();
            case "usuarios_w" -> p.getUsuariosW();
            case "usuarios_d" -> p.getUsuariosD();
            case "reportes_r" -> p.getReportesR();
            case "reportes_w" -> p.getReportesW();
            case "reportes_d" -> p.getReportesD();
            case "facturacion_r" -> p.getFacturacionR();
            case "facturacion_w" -> p.getFacturacionW();
            case "facturacion_d" -> p.getFacturacionD();
            case "pos_r" -> p.getPosR();
            case "pos_w" -> p.getPosW();
            case "pos_d" -> p.getPosD();
            case "kardex_r" -> p.getKardexR();
            case "kardex_w" -> p.getKardexW();
            case "kardex_d" -> p.getKardexD();
            case "ia_r" -> p.getIaR();
            case "ia_w" -> p.getIaW();
            case "ia_d" -> p.getIaD();
            default -> false;
        };
    }
}
