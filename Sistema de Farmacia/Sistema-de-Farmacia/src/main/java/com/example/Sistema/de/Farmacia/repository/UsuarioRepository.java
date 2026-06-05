package com.example.Sistema.de.Farmacia.repository;

import com.example.Sistema.de.Farmacia.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByUsuario(String usuario);
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByActivoTrue();
    List<Usuario> findByRol(String rol);

    @Query("SELECT u FROM Usuario u WHERE u.nombre LIKE %:nombre% OR u.usuario LIKE %:nombre%")
    List<Usuario> searchByNombreOrUsuario(@Param("nombre") String nombre);
}
