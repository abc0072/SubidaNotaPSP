package db;

import java.sql.Connection;
import java.sql.Statement;

public class CrearTablas {

    public static void crear() {
        try (Connection con = ConexionBD.conectar();
             Statement st = con.createStatement()) {

            st.executeUpdate("""
                CREATE TABLE IF NOT EXISTS pacientes(
                    id INTEGER PRIMARY KEY,
                    nombre TEXT NOT NULL,
                    tipo TEXT NOT NULL,
                    prioridad INTEGER NOT NULL
                );
            """);

            st.executeUpdate("""
                CREATE TABLE IF NOT EXISTS atenciones(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    paciente_id INTEGER NOT NULL,
                    veterinario TEXT NOT NULL,
                    consulta TEXT NOT NULL,
                    inicio TEXT NOT NULL,
                    fin TEXT,
                    FOREIGN KEY(paciente_id) REFERENCES pacientes(id)
                );
            """);

            st.executeUpdate("""
                CREATE TABLE IF NOT EXISTS atenciones(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    paciente_id INTEGER NOT NULL,
                    veterinario TEXT NOT NULL,
                    consulta TEXT NOT NULL,
                    inicio TEXT NOT NULL,
                    fin TEXT,
                    FOREIGN KEY(paciente_id) REFERENCES pacientes(id)
                );
            """);

            System.out.println("Tablas creadas / comprobadas OK");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}