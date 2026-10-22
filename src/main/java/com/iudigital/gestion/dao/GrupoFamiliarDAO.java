package com.iudigital.gestion.dao;

import com.iudigital.gestion.exception.AccesoDatosException;
import com.iudigital.gestion.model.GrupoFamiliar;
import java.util.List;

public interface GrupoFamiliarDAO {
    List<GrupoFamiliar> listarPorFuncionario(int idFuncionario) throws AccesoDatosException;
    void crear(GrupoFamiliar familiar) throws AccesoDatosException;
    void actualizar(GrupoFamiliar familiar) throws AccesoDatosException;
    void eliminar(int idFamiliar) throws AccesoDatosException;
}
