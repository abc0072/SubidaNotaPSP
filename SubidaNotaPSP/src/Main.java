import db.CrearTablas;
import modelos.Paciente;
import modelos.TipoDeCaso;
import modelos.Veterinario;
import servicios.Clinica;
import servicios.Consulta;

public class Main {

    public static void main(String[] args) {

        // Crear tablas si no existen
        CrearTablas.crear();

        // Crear clínica y conectarla al servidor
        Clinica clinica = new Clinica();
        clinica.conectarAlServidor();

        // Añadir veterinarios
        clinica.anadirVeterinario(new Veterinario("Veterinario_1"));
        clinica.anadirVeterinario(new Veterinario("Veterinario_2"));
        clinica.anadirVeterinario(new Veterinario("Veterinario_3"));

        // Registrar pacientes
        clinica.registrarPaciente(new Paciente("Angel", TipoDeCaso.CONSULTA_NORMAL, 5));
        clinica.registrarPaciente(new Paciente("Pablo", TipoDeCaso.CONSULTA_NORMAL, 5));
        clinica.registrarPaciente(new Paciente("Daniel", TipoDeCaso.URGENCIA, 1));
        clinica.registrarPaciente(new Paciente("Carmen", TipoDeCaso.URGENCIA, 2));
        clinica.registrarPaciente(new Paciente("Sara", TipoDeCaso.CONSULTA_NORMAL, 5));

        // Crear consultas (hilos)
        Consulta c1 = new Consulta("Consulta_1", clinica);
        Consulta c2 = new Consulta("Consulta_2", clinica);


        c1.start();
        c2.start();
    }
}