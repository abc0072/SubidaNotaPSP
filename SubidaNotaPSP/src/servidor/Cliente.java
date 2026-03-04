package servidor;

import java.io.DataInputStream;
import java.net.Socket;

public class Cliente {

    public static void main(String[] args) {

        try {
            // Creamos el socket y nos conectamos al servidor
            Socket socket = new Socket("localhost", 5000);

            // Creamos el flujo de entrada para poder recibir mensajes del servidor
            DataInputStream in = new DataInputStream(socket.getInputStream());

            // Mostramos por pantalla que el cliente se ha conectado correctamente
            System.out.println("Cliente conectado...");

            // Bucle infinito para estar siempre escuchando mensajes
            while (true) {

                // Espera hasta que el servidor envíe un mensaje
                String mensaje = in.readUTF();

                // Mostramos el mensaje recibido
                System.out.println("NOTIFICACIÓN: " + mensaje);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}