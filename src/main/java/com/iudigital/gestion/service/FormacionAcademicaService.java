package com.iudigital.gestion.service;

import com.iudigital.gestion.dao.FormacionAcademicaDAO;
import com.iudigital.gestion.exception.AccesoDatosException;
import com.iudigital.gestion.model.FormacionAcademica;
import java.util.List;

public class FormacionAcademicaService {
    private final FormacionAcademicaDAO dao;

    public FormacionAcademicaService(FormacionAcademicaDAO dao) {
        this.dao = dao;
    }

    public List<FormacionAcademica> listarPorFuncionario(int idFuncionario) throws AccesoDatosException {
        if (idFuncionario <= 0) {
            throw new IllegalArgumentException("Debe seleccionar un funcionario válido para consultar sus estudios.");
        }
        return dao.listarPorFuncionario(idFuncionario);
    }

    public void crear(FormacionAcademica f) throws AccesoDatosException {
        validar(f);
        dao.crear(f);
    }

    public void actualizar(FormacionAcademica f) throws AccesoDatosException {
        if (f.getIdFormacion() <= 0) {
            throw new IllegalArgumentException("Selecciona un registro de formación válido para actualizar.");
        }
        validar(f);
        dao.actualizar(f);
    }

    public void eliminar(int idFormacion) throws AccesoDatosException {
        if (idFormacion <= 0) {
            throw new IllegalArgumentException("Selecciona un registro de formación válido para eliminar.");
        }
        dao.eliminar(idFormacion);
    }

    private void validar(FormacionAcademica f) {
        if (f == null) {
            throw new IllegalArgumentException("No se recibieron datos de la formación académica.");
        }
        if (f.getIdFuncionario() <= 0) {
            throw new IllegalArgumentException("El estudio debe estar asociado a un funcionario válido.");
        }
        requerido(f.getUniversidad(), "universidad o institución educativa");
        requerido(f.getNivelEstudio(), "nivel de estudio");
        requerido(f.getTitulo(), "título obtenido");
    }

    private void requerido(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El campo " + campo + " es obligatorio.");
        }
    }
}
