package com.iudigital.gestion.model;

public class FormacionAcademica {
    private int idFormacion;
    private int idFuncionario;
    private String universidad;
    private String nivelEstudio;
    private String titulo;

    public FormacionAcademica() { }

    public FormacionAcademica(int idFormacion, int idFuncionario, String universidad,
                              String nivelEstudio, String titulo) {
        this.idFormacion = idFormacion;
        this.idFuncionario = idFuncionario;
        this.universidad = universidad;
        this.nivelEstudio = nivelEstudio;
        this.titulo = titulo;
    }

    public int getIdFormacion() { return idFormacion; }
    public void setIdFormacion(int idFormacion) { this.idFormacion = idFormacion; }

    public int getIdFuncionario() { return idFuncionario; }
    public void setIdFuncionario(int idFuncionario) { this.idFuncionario = idFuncionario; }

    public String getUniversidad() { return universidad; }
    public void setUniversidad(String universidad) { this.universidad = universidad; }

    public String getNivelEstudio() { return nivelEstudio; }
    public void setNivelEstudio(String nivelEstudio) { this.nivelEstudio = nivelEstudio; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    @Override
    public String toString() {
        return titulo + " - " + universidad + " (" + nivelEstudio + ")";
    }
}
