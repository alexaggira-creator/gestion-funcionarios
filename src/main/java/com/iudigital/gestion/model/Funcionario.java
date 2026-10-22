package com.iudigital.gestion.model;

import java.time.LocalDate;

public class Funcionario {
    private int idFuncionario;
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String nombres;
    private String apellidos;
    private String estadoCivil;
    private String sexo;
    private String direccion;
    private String telefono;
    private LocalDate fechaNacimiento;

    public Funcionario() { }

    public Funcionario(int idFuncionario, String tipoIdentificacion, String numeroIdentificacion,
            String nombres, String apellidos, String estadoCivil, String sexo,
            String direccion, String telefono, LocalDate fechaNacimiento) {
        this.idFuncionario = idFuncionario;
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.estadoCivil = estadoCivil;
        this.sexo = sexo;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getIdFuncionario() { return idFuncionario; }
    public void setIdFuncionario(int v) { this.idFuncionario = v; }
    public String getTipoIdentificacion() { return tipoIdentificacion; }
    public void setTipoIdentificacion(String v) { this.tipoIdentificacion = v; }
    public String getNumeroIdentificacion() { return numeroIdentificacion; }
    public void setNumeroIdentificacion(String v) { this.numeroIdentificacion = v; }
    public String getNombres() { return nombres; }
    public void setNombres(String v) { this.nombres = v; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String v) { this.apellidos = v; }
    public String getEstadoCivil() { return estadoCivil; }
    public void setEstadoCivil(String v) { this.estadoCivil = v; }
    public String getSexo() { return sexo; }
    public void setSexo(String v) { this.sexo = v; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String v) { this.direccion = v; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String v) { this.telefono = v; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate v) { this.fechaNacimiento = v; }
}
