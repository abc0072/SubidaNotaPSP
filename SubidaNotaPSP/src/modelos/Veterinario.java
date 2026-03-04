package modelos;

public class Veterinario {

    private final String nombre;
    private int carga; // número de pacientes que está atendiendo

    public Veterinario(String nombre) {
        this.nombre = nombre;
        this.carga = 0;
    }

    public String getNombre() {
        return nombre;
    }

    // Método sincronizado lo que hace que solo un hilo pueda ejecutarlo a la vez
    public synchronized int getCarga() {
        return carga;
    }

    public synchronized void incCarga() {
        carga++;
    }

    public synchronized void decCarga() {
        carga--;
    }

    @Override
    public String toString() {
        return "Veterinario (Nombre:" + nombre + '\'' + ", Carga:" + carga + ")";
    }
}