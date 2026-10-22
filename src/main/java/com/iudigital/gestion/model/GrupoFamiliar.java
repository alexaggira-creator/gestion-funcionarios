package com.iudigital.gestion.model;

import java.time.LocalDate;

public class GrupoFamiliar {
    private int idFamiliar;
    private int idFuncionario;
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String nombres;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private String rolFamiliar;

    public GrupoFamiliar() { }

    public GrupoFamiliar(int idFamiliar, int idFuncionario, String tipoIdentificacion,
                         String numeroIdentificacion, String nombres, String apellidos,
                         LocalDate fechaNacimiento, String rolFamiliar) {
        this.idFamiliar = idFamiliar;
        this.idFuncionario = idFuncionario;
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.rolFamiliar = rolFamiliar;
    }

    public int getIdFamiliar() { return idFamiliar; }
    public void setIdFamiliar(int idFamiliar) { this.idFamiliar = idFamiliar; }

    public int getIdFuncionario() { return idFuncionario; }
    public void setIdFuncionario(int idFuncionario) { this.idFuncionario = idFuncionario; }

    public String getTipoIdentificacion() { return tipoIdentificacion; }
    public void setTipoIdentificacion(String tipoIdentificacion) { this.tipoIdentificacion = tipoIdentificacion; }

    public String getNumeroIdentificacion() { return numeroIdentificacion; }
    public void setNumeroIdentificacion(String numeroIdentificacion) { this.numeroIdentificacion = numeroIdentificacion; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getRolFamiliar() { return rolFamiliar; }
    public void setRolFamiliar(String rolFamiliar) { this.rolFamiliar = rolFamiliar; }

    @Override
    public String toString() {
        return nombres + " " + apellidos + " (" + rolFamiliar + ")";
    }
}
