package com.example.Sistema.de.Farmacia.service;

import com.example.Sistema.de.Farmacia.model.Usuario;
import com.example.Sistema.de.Farmacia.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LogsService logsService;

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> obtenerPorUsuario(String usuario) {
        return usuarioRepository.findByUsuario(usuario);
    }

    public Usuario crear(Usuario usuario) {
        Usuario guardado = usuarioRepository.save(usuario);
        logsService.crear("CREAR_USUARIO", usuario.getUsuario(), "Sistema");
        return guardado;
    }

    public Usuario actualizar(Integer id, Usuario usuarioActualizado) {
        Optional<Usuario> existing = usuarioRepository.findById(id);
        if (existing.isPresent()) {
            Usuario u = existing.get();
            u.setNombre(usuarioActualizado.getNombre());
            u.setEmail(usuarioActualizado.getEmail());
            u.setRol(usuarioActualizado.getRol());
            u.setUsuario(usuarioActualizado.getUsuario());
            if (usuarioActualizado.getPass() != null && !usuarioActualizado.getPass().isEmpty()) {
                u.setPass(usuarioActualizado.getPass());
            }
            logsService.crear("EDITAR_USUARIO", u.getUsuario(), "Sistema");
            return usuarioRepository.save(u);
        }
        return null;
    }

    public void cambiarContrasena(Integer id, String nuevaPass) {
        Optional<Usuario> existing = usuarioRepository.findById(id);
        if (existing.isPresent()) {
            Usuario u = existing.get();
            u.setPass(nuevaPass);
            usuarioRepository.save(u);
            logsService.crear("CAMBIO_CONTRASEÑA", u.getUsuario(), "Sistema");
        }
    }

    public void toggleActivo(Integer id) {
        Optional<Usuario> existing = usuarioRepository.findById(id);
        if (existing.isPresent()) {
            Usuario u = existing.get();
            u.setActivo(!u.getActivo());
            usuarioRepository.save(u);
            logsService.crear(u.getActivo() ? "ACTIVAR" : "DESACTIVAR", u.getUsuario(), "Sistema");
        }
    }

    public void eliminar(Integer id) {
        Optional<Usuario> existing = usuarioRepository.findById(id);
        if (existing.isPresent()) {
            logsService.crear("ELIMINAR_USUARIO", existing.get().getUsuario(), "Sistema");
            usuarioRepository.deleteById(id);
        }
    }

    public List<Usuario> buscar(String termino) {
        return usuarioRepository.searchByNombreOrUsuario(termino);
    }

    public List<Usuario> obtenerPorRol(String rol) {
        return usuarioRepository.findByRol(rol);
    }

    public boolean validarCredenciales(String usuario, String password) {
        Optional<Usuario> u = usuarioRepository.findByUsuario(usuario);
        return u.isPresent() && u.get().getPass().equals(password) && u.get().getActivo();
    }
}
