package com.iudigital.gestion.dao;

import com.iudigital.gestion.config.Conexion;
import com.iudigital.gestion.exception.AccesoDatosException;
import com.iudigital.gestion.model.FormacionAcademica;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FormacionAcademicaDAOImpl implements FormacionAcademicaDAO {
    private static final String COLUMNAS = "id_funcionario, universidad, nivel_estudio, titulo";

    @Override
    public List<FormacionAcademica> listarPorFuncionario(int idFuncionario) throws AccesoDatosException {
        String sql = "SELECT id_formacion, " + COLUMNAS + " FROM formacion_academica WHERE id_funcionario = ? ORDER BY id_formacion";
        List<FormacionAcademica> lista = new ArrayList<>();
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idFuncionario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
            return lista;
        } catch (SQLException ex) {
            throw new AccesoDatosException("No fue posible consultar la formación académica del funcionario con ID " + idFuncionario, ex);
        }
    }

    @Override
    public void crear(FormacionAcademica f) throws AccesoDatosException {
        String sql = "INSERT INTO formacion_academica (" + COLUMNAS + ") VALUES (?, ?, ?, ?)";
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, f.getIdFuncionario());
            ps.setString(2, f.getUniversidad());
            ps.setString(3, f.getNivelEstudio());
            ps.setString(4, f.getTitulo());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new AccesoDatosException("No fue posible registrar la formación académica.", ex);
        }
    }

    @Override
    public void actualizar(FormacionAcademica f) throws AccesoDatosException {
        String sql = "UPDATE formacion_academica SET universidad=?, nivel_estudio=?, titulo=? WHERE id_formacion=?";
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, f.getUniversidad());
            ps.setString(2, f.getNivelEstudio());
            ps.setString(3, f.getTitulo());
            ps.setInt(4, f.getIdFormacion());
            if (ps.executeUpdate() == 0) {
                throw new AccesoDatosException("No se encontró el registro de formación académica que deseas actualizar.");
            }
        } catch (SQLException ex) {
            throw new AccesoDatosException("No fue posible actualizar la formación académica.", ex);
        }
    }

    @Override
    public void eliminar(int idFormacion) throws AccesoDatosException {
        String sql = "DELETE FROM formacion_academica WHERE id_formacion=?";
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idFormacion);
            if (ps.executeUpdate() == 0) {
                throw new AccesoDatosException("No se encontró el registro de formación académica que deseas eliminar.");
            }
        } catch (SQLException ex) {
            throw new AccesoDatosException("No fue posible eliminar la formación académica.", ex);
        }
    }

    private FormacionAcademica mapear(ResultSet rs) throws SQLException {
        return new FormacionAcademica(
            rs.getInt("id_formacion"),
            rs.getInt("id_funcionario"),
            rs.getString("universidad"),
            rs.getString("nivel_estudio"),
            rs.getString("titulo")
        );
    }
}
