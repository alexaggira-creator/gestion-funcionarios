package com.iudigital.gestion.dao;

import com.iudigital.gestion.exception.AccesoDatosException;
import com.iudigital.gestion.model.FormacionAcademica;
import java.util.List;

public interface FormacionAcademicaDAO {
    List<FormacionAcademica> listarPorFuncionario(int idFuncionario) throws AccesoDatosException;
    void crear(FormacionAcademica formacion) throws AccesoDatosException;
    void actualizar(FormacionAcademica formacion) throws AccesoDatosException;
    void eliminar(int idFormacion) throws AccesoDatosException;
}
