package ejecutable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;

import javax.swing.JOptionPane;

import controlador.ControladorChat;
import controlador.ControladorInicio;
import controlador.ControladorLogin;
import util.Constante;

@SuppressWarnings("deprecation")
public class Cliente extends Observable {
	private String user;
	private int port;
	private Socket socket;
	private boolean modoEscucha = true;
	BufferedReader entrada;
	PrintWriter salida;

	// ------------------EJECUTABLE--------------------//
	public static void main(String[] args) {
		ControladorLogin.getInstance();
	}

	// -----------------CONSTRUCTOR------------------//
	public Cliente(String user, int port) {
		this.user = user;
		this.port = port;
	}

	// ------------------METODOS AVANZADOS--------------------//

	/*
	 * Devuelve true si registra correctamente al cliente en el servidor
	 */
	public boolean registroServer(String ip, int port) {
		boolean resp;
		try {
			this.socket = new Socket(ip, port);
			this.entrada = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			this.salida = new PrintWriter(this.socket.getOutputStream(), true);
			salida.println(this.user);
			resp = true;
		} catch (IOException e) {
			resp = false;
		}
		return resp;
	}

	public void IniciaHiloCliente() {
		new Thread(() -> {
			try {
				String cadena;
				do {
					// System.out.println(this.user + ":esperando cadena");
					cadena = recibirCadena();
					// System.out.println(this.user + " recibio " + cadena);
					if (cadena != null)
						interpretarMensaje(cadena.trim());
				} while (cadena != null && !socket.isClosed());
				// System.out.println("salio");
				entrada.close();
				salida.close();
				if (!socket.isClosed())
					socket.close();
			} catch (IOException e) {

			}
		}).start();
	}

	public void interpretarMensaje(String cadena) {
		// System.out.println("interpretar " + cadena);
		String[] mensaje = cadena.split(";");
		switch (mensaje[Constante.INSTRUCCION].trim()) {

		case (Constante.COMANDO_CONECTAR):
			if (modoEscucha == false)
				break;
			String userDest = mensaje[Constante.DESTINATARIO];
			int option = JOptionPane.showConfirmDialog(null, "¿Deseas aceptar la conexion de " + userDest + "?",
					"Confirmacion", JOptionPane.YES_NO_OPTION);

			if (option == JOptionPane.YES_OPTION) {
				enviarCadena(Constante.COMANDO_ACEPTAR + ";" + userDest + ";" + mensaje[Constante.CLAVE]);
				ControladorInicio.getInstance().cargarChat(userDest, mensaje[Constante.CLAVE]);
			} else {
				enviarCadena(Constante.COMANDO_RECHAZAR + ";" + userDest);
			}
			break;

		case (Constante.COMANDO_ACEPTAR):
			ControladorInicio.getInstance().interpretarRespuesta(cadena);
			break;

		case (Constante.COMANDO_RECHAZAR):
			ControladorInicio.getInstance().interpretarRespuesta(cadena);
			break;

		case (Constante.COMANDO_DESCONECTAR):
			ControladorChat.getInstance().recibirMensaje(cadena);
			break;

		case (Constante.COMANDO_ENVIAR):
			ControladorChat.getInstance().recibirMensaje(cadena);
			break;
		}
	}

	public void enviarMensajePrivado(String mensaje, String userDest) {
		enviarCadena(Constante.COMANDO_ENVIAR + ";" + userDest + ";" + mensaje);
	}

	public void establecerConexion(String userDest, String key) {
		enviarCadena(Constante.COMANDO_CONECTAR + ";" + userDest + ";" + key);
	}

	public void finalizarConexion() {
		enviarCadena(Constante.COMANDO_FIN);
	}

	public void desconectarChat(String userDest) {
		enviarCadena(Constante.COMANDO_DESCONECTAR + ";" + userDest);
	}

	public void cambiarModoEscucha() {
		enviarCadena(Constante.COMANDO_MODO_ESCUCHA);

	}

	// ------------------METODOS BASICOS--------------------//
	public String recibirCadena() {
		String cadenaRecibida = null;
		try {
			cadenaRecibida = entrada.readLine();
		} catch (IOException e) {
			cadenaRecibida = null;
		}

		return cadenaRecibida;
	}

	public void enviarCadena(String cadena) {
		if (socket == null || socket.isClosed()) {
			JOptionPane.showMessageDialog(null, "No se puede enviar el mensaje. La conexion no esta establecida :(");
		} else {
			salida.println(cadena);
		}
	}

	// ------------------GETTERS--------------------//
	public String getUser() {
		return this.user;
	}

	public Integer getPort() {
		return this.port;
	}

	public Socket getSocket() {
		return socket;
	}

}