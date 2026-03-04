package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {

    // Variables de la clase Servidor
    private final int puerto;
    private final ArrayList<DataOutputStream> clientes = new ArrayList<>();

    // Constructor de la clase
    public Servidor(int puerto) {
        this.puerto = puerto;
    }

    public void start() {
        // Creamos un hilo para el servidor
        Thread hilo = new Thread(() -> {
            // Creamos el ServerSocket dentro del try para que se cierre automaticamente si hay algun error
            try (ServerSocket server = new ServerSocket(puerto)) {

                System.out.println("Servidor iniciado en puerto " + puerto);

                // Bucle infinito para mantener el servidor siempre encendido
                while (true) {
                    // Esperamos a que se conecte un cliente
                    Socket socket = server.accept();
                    System.out.println("Nuevo cliente conectado: " + socket.getRemoteSocketAddress());

                    // Creamos los flujos de entrada y salida
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    DataInputStream in = new DataInputStream(socket.getInputStream());

                    // Guardamos el cliente para poder enviarle mensajes
                    synchronized (clientes) {
                        clientes.add(out);
                    }

                    // Mensaje de bienvenida
                    out.writeUTF("Conectado al sistema de la clínica");

                    // Creamos un hilo para escuchar lo que este cliente envía al servidor
                    Thread receptor = new Thread(() -> {
                        try {
                            while (true) {
                                // Leemos mensajes del cliente (por ejemplo, la clínica)
                                String mensaje = in.readUTF();

                                // Reenviamos el mensaje a todos los clientes conectados
                                enviarATodos(mensaje);
                            }
                        } catch (IOException e) {
                            // Desconexión o error de lectura
                            System.out.println("Cliente desconectado: " + socket.getRemoteSocketAddress());
                        } finally {
                            // Quitamos el cliente de la lista y cerramos recursos
                            synchronized (clientes) {
                                clientes.remove(out);
                            }
                            try { in.close(); } catch (Exception ignored) {}
                            try { out.close(); } catch (Exception ignored) {}
                            try { socket.close(); } catch (Exception ignored) {}
                        }
                    });

                    receptor.start();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        hilo.start();
    }

    // Envia un mensaje a todos los clientes conectados al servidor
    public void enviarATodos(String mensaje) {
        synchronized (clientes) { // para que nadie modifique la lista mientras se recorre
            // Recorremos con índice para poder eliminar si falla
            for (int i = clientes.size() - 1; i >= 0; i--) {
                DataOutputStream out = clientes.get(i);
                try {
                    out.writeUTF(mensaje);
                    out.flush();
                } catch (Exception e) {
                    // Si falla al enviar, eliminamos ese cliente
                    clientes.remove(i);
                    try { out.close(); } catch (Exception ignored) {}
                }
            }
        }
    }

    // Main para arrancar el servidor
    public static void main(String[] args) {
        Servidor servidor = new Servidor(5000);
        servidor.start();

        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}