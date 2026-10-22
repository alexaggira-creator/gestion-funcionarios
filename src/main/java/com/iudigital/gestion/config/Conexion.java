package com.iudigital.gestion.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Conexion {
    private static final String URL =
            System.getenv().getOrDefault("DB_URL",
            "jdbc:mysql://localhost:3306/gestion_funcionarios?useSSL=false&serverTimezone=America/Bogota&allowPublicKeyRetrieval=true");
    private static final String USUARIO = System.getenv().getOrDefault("DB_USER", "root");
    private static final String CLAVE = System.getenv().getOrDefault("DB_PASSWORD", "");

    private Conexion() { }

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }
}
