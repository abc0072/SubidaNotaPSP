package modelos;

import java.time.Instant;
import java.util.ArrayList;

public class Paciente {

    private static int contador = 1;
    private int id;
    private String nombre;
    private TipoDeCaso tipo;
    private int prioridad;      // 1 = más grave, 5 = menos grave
    private Instant creadoEn;   // momento de llegada

    public Paciente(String nombre, TipoDeCaso tipo, int prioridad) {
        this.id = contador++;
        this.nombre = nombre;
        this.tipo = tipo;
        this.prioridad = prioridad;
        this.creadoEn = Instant.now();
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoDeCaso getTipo() {
        return tipo;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public Instant getCreadoEn() {
        return creadoEn;
    }

    public Paciente obtenerMasUrgente(ArrayList<Paciente> lista) {
        if (lista.isEmpty()) {
            return null;
        }

        Paciente masUrgente = lista.get(0);
        for (Paciente p : lista) {
            if (p.getPrioridad() < masUrgente.getPrioridad()) {
                masUrgente = p;
            }
        }

        return masUrgente;
    }

    @Override
    public String toString() {
        return "Paciente (Id:" + id + '\'' + ", nombre:'" + nombre + '\'' + ", tipo:" + tipo + ", prioridad:" + prioridad + ", creado:" + creadoEn + ")";
    }
}