package com.iudigital.gestion.service;

import com.iudigital.gestion.dao.FuncionarioDAO;
import com.iudigital.gestion.exception.AccesoDatosException;
import com.iudigital.gestion.model.Funcionario;
import java.time.LocalDate;
import java.util.List;

public class FuncionarioService {
    private final FuncionarioDAO dao;

    public FuncionarioService(FuncionarioDAO dao) {
        this.dao = dao;
    }

    public List<Funcionario> listar() throws AccesoDatosException {
        return dao.listar();
    }

    public Funcionario obtenerPorId(int id) throws AccesoDatosException {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID del funcionario debe ser mayor a 0.");
        }
        return dao.obtenerPorId(id);
    }

    public void crear(Funcionario f) throws AccesoDatosException {
        validar(f);
        dao.crear(f);
    }

    public void actualizar(Funcionario f) throws AccesoDatosException {
        if (f.getIdFuncionario() <= 0) {
            throw new IllegalArgumentException("Selecciona un funcionario válido para actualizar.");
        }
        validar(f);
        dao.actualizar(f);
    }

    public void eliminar(int id) throws AccesoDatosException {
        if (id <= 0) {
            throw new IllegalArgumentException("Selecciona un funcionario válido para eliminar.");
        }
        dao.eliminar(id);
    }

    private void validar(Funcionario f) {
        if (f == null) throw new IllegalArgumentException("No se recibieron datos del funcionario.");
        requerido(f.getTipoIdentificacion(), "tipo de identificación");
        requerido(f.getNumeroIdentificacion(), "número de identificación");
        requerido(f.getNombres(), "nombres");
        requerido(f.getApellidos(), "apellidos");
        requerido(f.getEstadoCivil(), "estado civil");
        requerido(f.getSexo(), "sexo");
        requerido(f.getDireccion(), "dirección");
        requerido(f.getTelefono(), "teléfono");
        if (f.getFechaNacimiento() == null) {
            throw new IllegalArgumentException("La fecha de nacimiento es obligatoria.");
        }
        if (f.getFechaNacimiento().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser futura.");
        }
    }

    private void requerido(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El campo " + campo + " es obligatorio.");
        }
    }
}
