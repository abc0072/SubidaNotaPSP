package servicios;

import db.Atencion;
import modelos.Paciente;
import modelos.Veterinario;

public class Consulta extends Thread {

    // Atributos de la clase
    private String nombreConsulta;
    private Clinica clinica;

    // Constructor de la clase
    public Consulta(String nombreConsulta, Clinica clinica) {
        this.nombreConsulta = nombreConsulta;
        this.clinica = clinica;
    }

    @Override
    public void run() {
        // Bucle infinito que hace que la consulta siempre este funcionando
        while (true) {

            // Pedimos el siguiente paciente disponible
            Paciente p = clinica.obtenerSiguientePaciente();
            if (p == null) {
                return;
            }

            // Elegimos el veterinario menos ocupado
            Veterinario v = clinica.elegirVeterinarioMenosOcupado();

            // Le aumentamos la carga al veterinario anteriormente seleccionado
            v.incCarga();

            // Guardamos en la base de datos el inicio de la atencion
            int idAtencion = Atencion.insertarInicio(p.getId(), v.getNombre(), nombreConsulta);

            // Lo mostramos en la consola
            System.out.println("[" + nombreConsulta + "] Atiende a " + v.getNombre() + " -> " + p.toString() + " | Carga del veterinario:" + v.getCarga());

            // Notificamos por chat
            clinica.notificar("INICIO -> " + nombreConsulta + ": " + v.getNombre() + " atiende a " + p.getNombre());

            // Simulador de tiempo de atención
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return;
            }

            // Registrar el fin de la atencion en la base de datos
            Atencion.actualizarFin(idAtencion);

            // Reducimos la carga del veterinario
            v.decCarga();

            // Lo mostramos en la consoloa
            System.out.println("[" + nombreConsulta + "] Termina " + v.getNombre() + " -> Paciente " + p.getId() + " | Carga del veterinario:" + v.getCarga());

            // Notificamos por chat
            clinica.notificar("FIN -> " + nombreConsulta + ": " + v.getNombre() + " terminó con " + p.getNombre());
        }
    }
}