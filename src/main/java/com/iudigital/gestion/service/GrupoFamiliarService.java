package com.iudigital.gestion.service;

import com.iudigital.gestion.dao.GrupoFamiliarDAO;
import com.iudigital.gestion.exception.AccesoDatosException;
import com.iudigital.gestion.model.GrupoFamiliar;
import java.time.LocalDate;
import java.util.List;

public class GrupoFamiliarService {
    private final GrupoFamiliarDAO dao;

    public GrupoFamiliarService(GrupoFamiliarDAO dao) {
        this.dao = dao;
    }

    public List<GrupoFamiliar> listarPorFuncionario(int idFuncionario) throws AccesoDatosException {
        if (idFuncionario <= 0) {
            throw new IllegalArgumentException("Debe seleccionar un funcionario válido para consultar sus familiares.");
        }
        return dao.listarPorFuncionario(idFuncionario);
    }

    public void crear(GrupoFamiliar f) throws AccesoDatosException {
        validar(f);
        dao.crear(f);
    }

    public void actualizar(GrupoFamiliar f) throws AccesoDatosException {
        if (f.getIdFamiliar() <= 0) {
            throw new IllegalArgumentException("Selecciona un familiar válido para actualizar.");
        }
        validar(f);
        dao.actualizar(f);
    }

    public void eliminar(int idFamiliar) throws AccesoDatosException {
        if (idFamiliar <= 0) {
            throw new IllegalArgumentException("Selecciona un familiar válido para eliminar.");
        }
        dao.eliminar(idFamiliar);
    }

    private void validar(GrupoFamiliar f) {
        if (f == null) {
            throw new IllegalArgumentException("No se recibieron datos del familiar.");
        }
        if (f.getIdFuncionario() <= 0) {
            throw new IllegalArgumentException("El familiar debe estar asociado a un funcionario válido.");
        }
        requerido(f.getNombres(), "nombres");
        requerido(f.getApellidos(), "apellidos");
        requerido(f.getRolFamiliar(), "rol familiar o parentesco");

        if (f.getFechaNacimiento() != null && f.getFechaNacimiento().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento del familiar no puede ser futura.");
        }
    }

    private void requerido(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El campo " + campo + " es obligatorio.");
        }
    }
}
