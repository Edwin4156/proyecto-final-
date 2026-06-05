package com.example.Sistema.de.Farmacia.repository;

import com.example.Sistema.de.Farmacia.model.PermisosRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PermisosRolRepository extends JpaRepository<PermisosRol, Integer> {
    Optional<PermisosRol> findByRol(String rol);
}
