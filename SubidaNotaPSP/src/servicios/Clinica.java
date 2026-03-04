package servicios;

import db.Paciente;
import modelos.TipoDeCaso;
import modelos.Veterinario;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Clinica {

    private ArrayList<modelos.Paciente> urgencias = new ArrayList<>();
    private ArrayList<modelos.Paciente> normales = new ArrayList<>();
    private ArrayList<Veterinario> veterinarios = new ArrayList<>();

    // Socket para enviar mensajes al servidor
    private DataOutputStream out;

    // Conexión al servidor
    public void conectarAlServidor() {
        try {
            Socket socket = new Socket("localhost", 5000);
            out = new DataOutputStream(socket.getOutputStream());
            System.out.println("Clínica conectada al servidor de notificaciones.");
        } catch (Exception e) {
            System.out.println("No se pudo conectar al servidor.");
            e.printStackTrace();
        }
    }

    // Enviar notificación al servidor (que luego la reenvía a todos los clientes)
    public void notificar(String mensaje) {
        try {
            if (out != null) {
                out.writeUTF(mensaje);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Añadir veterinario
    public void anadirVeterinario(Veterinario v) {
        veterinarios.add(v);
    }

    // Registrar paciente: guarda en BD, mete en cola y despierta hilos
    public synchronized void registrarPaciente(modelos.Paciente p) {

        // Guardar paciente en SQLite
        Paciente.insertar(p);

        // Meter en cola correspondiente
        if (p.getTipo() == TipoDeCaso.URGENCIA) {
            urgencias.add(p);
        } else {
            normales.add(p);
        }

        // Notificar por chat
        notificar("Nuevo paciente: " + p.getNombre()
                + " (" + p.getTipo() + ", prioridad " + p.getPrioridad() + ")");

        // Despertar a las consultas si estaban esperando
        notifyAll();
    }

    // Obtener siguiente paciente:
    // 1) urgencias -> seleccionar manualmente el de menor prioridad
    // 2) normales -> FIFO
    // 3) si no hay -> wait()
    public synchronized modelos.Paciente obtenerSiguientePaciente() {

        while (urgencias.isEmpty() && normales.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                return null;
            }
        }

        // Prioridad a urgencias
        if (!urgencias.isEmpty()) {
            modelos.Paciente masUrgente = urgencias.get(0);

            for (modelos.Paciente p : urgencias) {
                if (p.getPrioridad() < masUrgente.getPrioridad()) {
                    masUrgente = p;
                }
            }

            urgencias.remove(masUrgente);
            return masUrgente;
        }

        // FIFO para normales
        return normales.remove(0);
    }

    // Elegir veterinario con menor carga
    public synchronized Veterinario elegirVeterinarioMenosOcupado() {

        Veterinario mejor = veterinarios.get(0);

        for (Veterinario v : veterinarios) {
            if (v.getCarga() < mejor.getCarga()) {
                mejor = v;
            }
        }

        return mejor;
    }
}