package com.example.Sistema.de.Farmacia.dto;

public class LoginResponseDTO {
    private Integer id;
    private String nombre;
    private String usuario;
    private String email;
    private String rol;
    private String token;
    private Boolean success;

    public LoginResponseDTO() {}

    public LoginResponseDTO(Boolean success, String mensaje) {
        this.success = success;
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

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public Boolean getSuccess() { return success; }
    public void setSuccess(Boolean success) { this.success = success; }
}
