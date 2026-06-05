package com.example.Sistema.de.Farmacia.controller;

import com.example.Sistema.de.Farmacia.model.PermisosRol;
import com.example.Sistema.de.Farmacia.service.PermisosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/permisos")
@CrossOrigin(origins = "*")
public class PermisosController {

    @Autowired
    private PermisosService permisosService;

    @GetMapping("/rol/{rol}")
    public ResponseEntity<PermisosRol> obtenerPermisosPorRol(@PathVariable String rol) {
        Optional<PermisosRol> permisos = permisosService.obtenerPermisosPorRol(rol);
        if (permisos.isPresent()) {
            return ResponseEntity.ok(permisos.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> tienePermiso(
            @RequestParam String rol,
            @RequestParam String modulo,
            @RequestParam String tipo) {
        boolean tiene = permisosService.tienePermiso(rol, modulo, tipo);
        return ResponseEntity.ok(tiene);
    }
}
