package com.example.Sistema.de.Farmacia.controller;

import com.example.Sistema.de.Farmacia.dto.LoginRequestDTO;
import com.example.Sistema.de.Farmacia.dto.LoginResponseDTO;
import com.example.Sistema.de.Farmacia.model.Usuario;
import com.example.Sistema.de.Farmacia.service.UsuarioService;
import com.example.Sistema.de.Farmacia.service.LogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LogsService logsService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        LoginResponseDTO response = new LoginResponseDTO();
        Optional<Usuario> usuario = usuarioService.obtenerPorUsuario(request.getUsuario());

        if (usuario.isPresent() && usuario.get().getPass().equals(request.getPass()) && usuario.get().getActivo()) {
            Usuario u = usuario.get();
            response.setSuccess(true);
            response.setId(u.getId());
            response.setNombre(u.getNombre());
            response.setUsuario(u.getUsuario());
            response.setEmail(u.getEmail());
            response.setRol(u.getRol());
            response.setToken("TOKEN_" + System.currentTimeMillis());

            logsService.crear(u.getUsuario(), "LOGIN", "Sesión iniciada", "AUTH");
            return ResponseEntity.ok(response);
        }

        response.setSuccess(false);
        logsService.crear(request.getUsuario(), "LOGIN_FALLIDO", "Credenciales incorrectas", "AUTH");
        return ResponseEntity.status(401).body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader(value = "X-Usuario", required = false) String usuario) {
        if (usuario != null) {
            logsService.crear(usuario, "LOGOUT", "Sesión cerrada", "AUTH");
        }
        return ResponseEntity.ok("Sesión cerrada");
    }
}
