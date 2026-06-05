package com.example.Sistema.de.Farmacia.controller;

import com.example.Sistema.de.Farmacia.dto.UsuarioDTO;
import com.example.Sistema.de.Farmacia.model.Usuario;
import com.example.Sistema.de.Farmacia.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> obtenerTodos(@RequestParam(required = false) String q) {
        List<Usuario> usuarios;
        if (q != null && !q.isEmpty()) {
            usuarios = usuarioService.buscar(q);
        } else {
            usuarios = usuarioService.obtenerTodos();
        }
        List<UsuarioDTO> dtos = usuarios.stream()
            .map(u -> new UsuarioDTO(u.getId(), u.getNombre(), u.getUsuario(), u.getEmail(), u.getRol(), u.getActivo()))
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerPorId(@PathVariable Integer id) {
        Optional<Usuario> usuario = usuarioService.obtenerPorId(id);
        if (usuario.isPresent()) {
            Usuario u = usuario.get();
            UsuarioDTO dto = new UsuarioDTO(u.getId(), u.getNombre(), u.getUsuario(), u.getEmail(), u.getRol(), u.getActivo());
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> crear(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setUsuario(usuarioDTO.getUsuario());
        usuario.setPass(usuarioDTO.getNombre());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setRol(usuarioDTO.getRol());
        usuario.setActivo(true);

        Usuario creado = usuarioService.crear(usuario);
        UsuarioDTO dto = new UsuarioDTO(creado.getId(), creado.getNombre(), creado.getUsuario(), creado.getEmail(), creado.getRol(), creado.getActivo());
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizar(@PathVariable Integer id, @RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setUsuario(usuarioDTO.getUsuario());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setRol(usuarioDTO.getRol());

        Usuario actualizado = usuarioService.actualizar(id, usuario);
        if (actualizado != null) {
            UsuarioDTO dto = new UsuarioDTO(actualizado.getId(), actualizado.getNombre(), actualizado.getUsuario(), actualizado.getEmail(), actualizado.getRol(), actualizado.getActivo());
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<String> cambiarPassword(@PathVariable Integer id, @RequestParam String pass) {
        usuarioService.cambiarContrasena(id, pass);
        return ResponseEntity.ok("Contraseña actualizada");
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<String> toggleActivo(@PathVariable Integer id) {
        usuarioService.toggleActivo(id);
        return ResponseEntity.ok("Estado actualizado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        usuarioService.eliminar(id);
        return ResponseEntity.ok("Usuario eliminado");
    }
}
