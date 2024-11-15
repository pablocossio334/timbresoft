package capaLogica;

import com.fazecast.jSerialComm.SerialPort;

public class ConexxionUsb {

    public static SerialPort encontrarMicrobit() {
        SerialPort[] ports = SerialPort.getCommPorts();
        System.out.println("Cantidad de puertos detectados: " + ports.length);

        for (SerialPort port : ports) {
            System.out.println(port.getSystemPortName() + " - " + port.getDescriptivePortName() + " - " + port.getPortLocation());
            // Cambia la condición si necesitas un identificador más específico para la micro:bit
            if (port.getDescriptivePortName().contains("USB Serial")) {
                return port;
            }
        }

        return null;
    }

    public static void enviarMensaje(SerialPort puerto, String mensaje) {
        // Configurar el baud rate antes de abrir el puerto
        puerto.setBaudRate(115200);

        // Abrir el puerto
        if (puerto.openPort()) {
            System.out.println("Micro:bit está en el puerto y abierto correctamente.");
        } else {
            System.out.println("Error al abrir el puerto.");
            return;
        }

        // Pausa breve para sincronizar antes de enviar el mensaje
        try {
            Thread.sleep(100); // Pausa de 100 ms
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Convertir el mensaje a bytes y enviarlo
        byte[] buffer = mensaje.getBytes();
        int bytesEscritos = puerto.writeBytes(buffer, buffer.length);
        if (bytesEscritos < 0) {
            System.out.println("Error al enviar el mensaje.");
        } else {
            System.out.println("Mensaje enviado: " + mensaje + " (" + bytesEscritos + " bytes escritos)");
        }

        // Cerrar el puerto después de enviar los datos
        puerto.closePort();
    }

    public static void main(String[] args) {
        SerialPort puerto = encontrarMicrobit();
        if (puerto == null) {
            System.out.println("Micro:bit no detectada.");
            return;
        }

        // Enviar mensaje a la micro:bit
        enviarMensaje(puerto, "hola$"); // El delimitador es '$', asegúrate de que el mensaje sea correcto
    }
}
