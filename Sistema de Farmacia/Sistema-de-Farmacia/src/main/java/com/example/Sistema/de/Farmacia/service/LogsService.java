package com.example.Sistema.de.Farmacia.service;

import com.example.Sistema.de.Farmacia.model.LogsAuditoria;
import com.example.Sistema.de.Farmacia.repository.LogsAuditoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LogsService {

    @Autowired
    private LogsAuditoriaRepository logsRepository;

    public LogsAuditoria crear(String accion, String detalle, String modulo) {
        LogsAuditoria log = new LogsAuditoria();
        log.setAccion(accion);
        log.setDetalle(detalle);
        log.setModulo(modulo);
        log.setUsuario("SISTEMA");
        return logsRepository.save(log);
    }

    public LogsAuditoria crear(String usuario, String accion, String detalle, String modulo) {
        LogsAuditoria log = new LogsAuditoria();
        log.setUsuario(usuario);
        log.setAccion(accion);
        log.setDetalle(detalle);
        log.setModulo(modulo);
        return logsRepository.save(log);
    }

    public List<LogsAuditoria> obtenerTodos() {
        return logsRepository.findAll();
    }

    public List<LogsAuditoria> obtenerUltimos100() {
        return logsRepository.findLast100();
    }

    public List<LogsAuditoria> obtenerPorUsuario(String usuario) {
        return logsRepository.findHistorialUsuario(usuario);
    }

    public List<LogsAuditoria> obtenerPorAccion(String accion) {
        return logsRepository.findByAccion(accion);
    }

    public List<LogsAuditoria> obtenerPorModulo(String modulo) {
        return logsRepository.findByModulo(modulo);
    }
}
