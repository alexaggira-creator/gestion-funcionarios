package com.iudigital.gestion.dao;

import com.iudigital.gestion.exception.AccesoDatosException;
import com.iudigital.gestion.model.Funcionario;
import java.util.List;

public interface FuncionarioDAO {
    List<Funcionario> listar() throws AccesoDatosException;
    Funcionario obtenerPorId(int id) throws AccesoDatosException;
    void crear(Funcionario funcionario) throws AccesoDatosException;
    void actualizar(Funcionario funcionario) throws AccesoDatosException;
    void eliminar(int id) throws AccesoDatosException;
}
