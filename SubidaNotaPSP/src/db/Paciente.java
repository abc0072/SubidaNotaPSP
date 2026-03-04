package db;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Paciente {

    // Este método inserta un paciente en la tabla "pacientes".
    // Recibe un objeto Paciente del modelo y guarda sus datos en SQLite.
    public static void insertar(modelos.Paciente p) {

        // Se abre conexión a la base de datos.
        // El try-with-resources hace que la conexión se cierre automáticamente.
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO pacientes(id,nombre,tipo,prioridad) VALUES(?,?,?,?)"
             )) {

            // Se asignan los valores del objeto Paciente a la consulta SQL
            ps.setInt(1, p.getId());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getTipo().name()); // Se guarda el nombre del enum (CONSULTA_NORMAL o URGENCIA)
            ps.setInt(4, p.getPrioridad());

            // Se ejecuta la inserción en la base de datos
            ps.executeUpdate();

        } catch (Exception e) {
            // Si ocurre algún error, se muestra por consola
            e.printStackTrace();
        }
    }
}