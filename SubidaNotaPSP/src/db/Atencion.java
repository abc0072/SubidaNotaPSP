package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;

public class Atencion {

    // Este método se llama cuando empieza una atención.
    // Inserta una nueva fila en la tabla "atenciones"
    // guardando el paciente, el veterinario, la consulta y la hora de inicio.
    // Devuelve el id generado automáticamente por la base de datos.
    public static int insertarInicio(int pacienteId, String veterinario, String consulta) {

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO atenciones(paciente_id, veterinario, consulta, inicio) VALUES(?,?,?,?)",
                     PreparedStatement.RETURN_GENERATED_KEYS
             )) {

            // Asignamos los valores a la consulta SQL
            ps.setInt(1, pacienteId);
            ps.setString(2, veterinario);
            ps.setString(3, consulta);
            ps.setString(4, LocalDateTime.now().toString()); // Hora actual

            // Ejecutamos el INSERT
            ps.executeUpdate();

            // Recuperamos el id generado automáticamente
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Si algo falla, devolvemos -1
        return -1;
    }

    // Este método se llama cuando termina la atención.
    // Actualiza el registro correspondiente y añade la hora de finalización.
    public static void actualizarFin(int idAtencion) {

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(
                     "UPDATE atenciones SET fin=? WHERE id=?"
             )) {

            // Guardamos la hora actual como hora de fin
            ps.setString(1, LocalDateTime.now().toString());
            ps.setInt(2, idAtencion);

            // Ejecutamos el UPDATE
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}