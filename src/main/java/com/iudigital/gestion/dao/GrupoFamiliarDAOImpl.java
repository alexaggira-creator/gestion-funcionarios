package com.iudigital.gestion.dao;

import com.iudigital.gestion.config.Conexion;
import com.iudigital.gestion.exception.AccesoDatosException;
import com.iudigital.gestion.model.GrupoFamiliar;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GrupoFamiliarDAOImpl implements GrupoFamiliarDAO {
    private static final String COLUMNAS =
        "id_funcionario, tipo_identificacion, numero_identificacion, nombres, apellidos, fecha_nacimiento, rol_familiar";

    @Override
    public List<GrupoFamiliar> listarPorFuncionario(int idFuncionario) throws AccesoDatosException {
        String sql = "SELECT id_familiar, " + COLUMNAS + " FROM grupo_familiar WHERE id_funcionario = ? ORDER BY id_familiar";
        List<GrupoFamiliar> lista = new ArrayList<>();
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
            throw new AccesoDatosException("No fue posible consultar el grupo familiar del funcionario con ID " + idFuncionario, ex);
        }
    }

    @Override
    public void crear(GrupoFamiliar f) throws AccesoDatosException {
        String sql = "INSERT INTO grupo_familiar (" + COLUMNAS + ") VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, f.getIdFuncionario());
            ps.setString(2, f.getTipoIdentificacion());
            ps.setString(3, f.getNumeroIdentificacion());
            ps.setString(4, f.getNombres());
            ps.setString(5, f.getApellidos());
            if (f.getFechaNacimiento() != null) {
                ps.setDate(6, Date.valueOf(f.getFechaNacimiento()));
            } else {
                ps.setNull(6, Types.DATE);
            }
            ps.setString(7, f.getRolFamiliar());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new AccesoDatosException("No fue posible agregar el familiar. Verifica los datos y la conexión.", ex);
        }
    }

    @Override
    public void actualizar(GrupoFamiliar f) throws AccesoDatosException {
        String sql = "UPDATE grupo_familiar SET tipo_identificacion=?, numero_identificacion=?, nombres=?, apellidos=?, fecha_nacimiento=?, rol_familiar=? WHERE id_familiar=?";
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, f.getTipoIdentificacion());
            ps.setString(2, f.getNumeroIdentificacion());
            ps.setString(3, f.getNombres());
            ps.setString(4, f.getApellidos());
            if (f.getFechaNacimiento() != null) {
                ps.setDate(5, Date.valueOf(f.getFechaNacimiento()));
            } else {
                ps.setNull(5, Types.DATE);
            }
            ps.setString(6, f.getRolFamiliar());
            ps.setInt(7, f.getIdFamiliar());
            if (ps.executeUpdate() == 0) {
                throw new AccesoDatosException("No se encontró el registro de familiar que deseas actualizar.");
            }
        } catch (SQLException ex) {
            throw new AccesoDatosException("No fue posible actualizar el familiar.", ex);
        }
    }

    @Override
    public void eliminar(int idFamiliar) throws AccesoDatosException {
        String sql = "DELETE FROM grupo_familiar WHERE id_familiar=?";
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idFamiliar);
            if (ps.executeUpdate() == 0) {
                throw new AccesoDatosException("No se encontró el familiar que deseas eliminar.");
            }
        } catch (SQLException ex) {
            throw new AccesoDatosException("No fue posible eliminar el familiar.", ex);
        }
    }

    private GrupoFamiliar mapear(ResultSet rs) throws SQLException {
        Date fechaSql = rs.getDate("fecha_nacimiento");
        LocalDate fecha = (fechaSql != null) ? fechaSql.toLocalDate() : null;
        return new GrupoFamiliar(
            rs.getInt("id_familiar"),
            rs.getInt("id_funcionario"),
            rs.getString("tipo_identificacion"),
            rs.getString("numero_identificacion"),
            rs.getString("nombres"),
            rs.getString("apellidos"),
            fecha,
            rs.getString("rol_familiar")
        );
    }
}
