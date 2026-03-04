package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBD {

    private static final String URL = "jdbc:sqlite:clinic.db";

    public static Connection conectar() throws Exception {
        return DriverManager.getConnection(URL);
    }
}