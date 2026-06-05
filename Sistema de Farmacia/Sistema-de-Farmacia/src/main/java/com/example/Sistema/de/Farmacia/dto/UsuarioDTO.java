package com.example.Sistema.de.Farmacia.dto;

public class UsuarioDTO {
    private Integer id;
    private String nombre;
    private String usuario;
    private String email;
    private String rol;
    private Boolean activo;

    public UsuarioDTO() {}

    public UsuarioDTO(Integer id, String nombre, String usuario, String email, String rol, Boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.email = email;
        this.rol = rol;
        this.activo = activo;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
