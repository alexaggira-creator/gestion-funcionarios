package com.iudigital.gestion.dao;

import com.iudigital.gestion.config.Conexion;
import com.iudigital.gestion.exception.AccesoDatosException;
import com.iudigital.gestion.model.Funcionario;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAOImpl implements FuncionarioDAO {
    private static final String COLUMNAS =
        "tipo_identificacion, numero_identificacion, nombres, apellidos, estado_civil, sexo, direccion, telefono, fecha_nacimiento";

    @Override
    public List<Funcionario> listar() throws AccesoDatosException {
        String sql = "SELECT id_funcionario, " + COLUMNAS + " FROM funcionarios ORDER BY id_funcionario";
        List<Funcionario> lista = new ArrayList<>();
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
            return lista;
        } catch (SQLException ex) {
            throw new AccesoDatosException("No fue posible consultar los funcionarios. Verifica la conexión y la base de datos.", ex);
        }
    }

    @Override
    public Funcionario obtenerPorId(int id) throws AccesoDatosException {
        String sql = "SELECT id_funcionario, " + COLUMNAS + " FROM funcionarios WHERE id_funcionario = ?";
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
                return null;
            }
        } catch (SQLException ex) {
            throw new AccesoDatosException("No fue posible consultar el funcionario con ID " + id, ex);
        }
    }

    @Override
    public void crear(Funcionario f) throws AccesoDatosException {
        String sql = "INSERT INTO funcionarios (" + COLUMNAS + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            llenarParametros(ps, f);
            ps.executeUpdate();
        } catch (SQLException ex) {
            if ("23000".equals(ex.getSQLState())) {
                throw new AccesoDatosException("La identificación ya existe. Ingresa un número diferente.", ex);
            }
            throw new AccesoDatosException("No fue posible crear el funcionario. Verifica los datos y la conexión.", ex);
        }
    }

    @Override
    public void actualizar(Funcionario f) throws AccesoDatosException {
        String sql = "UPDATE funcionarios SET tipo_identificacion=?, numero_identificacion=?, nombres=?, apellidos=?, estado_civil=?, sexo=?, direccion=?, telefono=?, fecha_nacimiento=? WHERE id_funcionario=?";
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            llenarParametros(ps, f);
            ps.setInt(10, f.getIdFuncionario());
            if (ps.executeUpdate() == 0) {
                throw new AccesoDatosException("No se encontró el funcionario que deseas actualizar.");
            }
        } catch (SQLException ex) {
            if ("23000".equals(ex.getSQLState())) {
                throw new AccesoDatosException("La identificación ya está registrada por otro funcionario.", ex);
            }
            throw new AccesoDatosException("No fue posible actualizar el funcionario.", ex);
        }
    }

    @Override
    public void eliminar(int id) throws AccesoDatosException {
        String sql = "DELETE FROM funcionarios WHERE id_funcionario=?";
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() == 0) {
                throw new AccesoDatosException("No se encontró el funcionario que deseas eliminar.");
            }
        } catch (SQLException ex) {
            throw new AccesoDatosException("No fue posible eliminar el funcionario.", ex);
        }
    }

    private void llenarParametros(PreparedStatement ps, Funcionario f) throws SQLException {
        ps.setString(1, f.getTipoIdentificacion());
        ps.setString(2, f.getNumeroIdentificacion());
        ps.setString(3, f.getNombres());
        ps.setString(4, f.getApellidos());
        ps.setString(5, f.getEstadoCivil());
        ps.setString(6, f.getSexo());
        ps.setString(7, f.getDireccion());
        ps.setString(8, f.getTelefono());
        ps.setDate(9, Date.valueOf(f.getFechaNacimiento()));
    }

    private Funcionario mapear(ResultSet rs) throws SQLException {
        return new Funcionario(
            rs.getInt("id_funcionario"),
            rs.getString("tipo_identificacion"),
            rs.getString("numero_identificacion"),
            rs.getString("nombres"),
            rs.getString("apellidos"),
            rs.getString("estado_civil"),
            rs.getString("sexo"),
            rs.getString("direccion"),
            rs.getString("telefono"),
            rs.getDate("fecha_nacimiento").toLocalDate()
        );
    }
}
