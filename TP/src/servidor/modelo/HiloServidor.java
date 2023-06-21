package servidor.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

import util.Constante;

public class HiloServidor extends Thread {
	private Socket cliente;
	private BufferedReader entrada;
	private PrintWriter salida;
	private String nombre;
	private Servidor servidor;

	// --------------------CONSTRUCTOR-----------------------------//
	public HiloServidor(Socket cliente, Servidor servidor) throws IOException {
		this.cliente = cliente;
		this.nombre = "";
		this.entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
		this.salida = new PrintWriter(cliente.getOutputStream(), true);
		this.servidor = servidor;
	}

	// ------------------------EJECUCION------------------------//
	public void run() {
		String cadena;
		inicializacionCliente();
		try {
			do {
				cadena = recibirMensaje();
				interpretarMensaje(cadena);
				servidor.resincronizarEstado();
			} while (!cadena.equals(Constante.COMANDO_FIN) && !this.cliente.isClosed()
					&& !this.servidor.getServer().isClosed());
			this.entrada.close();
			this.salida.close();
			if (!cliente.isClosed())
				this.cliente.close();
			servidor.quitarCliente(nombre);
			servidor.resincronizarEstado();

		} catch (IOException e) {
		}
	}

	// ------------------------METODOS AVANZADOS------------------------//
	/*
	 * Se le da un nombre al cliente y se lo carga en el mapa de clientes del
	 * servidor
	 */
	private void inicializacionCliente() {
		this.nombre = recibirMensaje();
		if (servidor.estaDentro(nombre)) {
			servidor.setSocket(nombre, cliente);
		} else
			servidor.meterCliente(this.nombre, this.cliente);
	}

	private void interpretarMensaje(String cadena) {
		String[] data = cadena.split(";");

		switch (data[Constante.INSTRUCCION].trim()) {

		case (Constante.COMANDO_CONECTAR):
			String dest = data[Constante.DESTINATARIO];
			servidor.notificar(nombre + " solicita conectarse con " + dest + "\n");
			if (isPosibleConectar(dest))
				enviarCadena(dest, Constante.COMANDO_CONECTAR + ";" + nombre + ";" + data[Constante.CLAVE]);
			else
				enviarCadena(nombre, Constante.COMANDO_RECHAZAR);
			break;

		case (Constante.COMANDO_ENVIAR):
			enviarCadena(data[Constante.DESTINATARIO],
					Constante.COMANDO_ENVIAR + ";" + data[Constante.DESTINATARIO] + ";" + data[Constante.MENSAJE]);
			servidor.notificar(
					nombre + " envia " + data[Constante.MENSAJE] + " a " + data[Constante.DESTINATARIO] + "\n");
			break;

		case (Constante.COMANDO_ACEPTAR):
			enviarCadena(data[Constante.DESTINATARIO],
					Constante.COMANDO_ACEPTAR + ";" + nombre + ";" + data[Constante.CLAVE]);
			setEstado(nombre, false);
			setEstado(data[Constante.DESTINATARIO], false);
			servidor.notificar(nombre + " acepta la conexion de " + data[Constante.DESTINATARIO] + "\n");
			break;

		case (Constante.COMANDO_RECHAZAR):
			enviarCadena(data[Constante.DESTINATARIO], Constante.COMANDO_RECHAZAR);
			servidor.notificar(nombre + " rechaza la conexion de " + data[Constante.DESTINATARIO] + "\n");
			break;

		case (Constante.COMANDO_DESCONECTAR):
			enviarCadena(data[Constante.DESTINATARIO], Constante.COMANDO_DESCONECTAR);
			setEstado(nombre, true);
			setEstado(data[Constante.DESTINATARIO], true);
			servidor.notificar(nombre + " finaliza el chat con " + data[Constante.DESTINATARIO] + "\n");
			break;

		case (Constante.COMANDO_MODO_ESCUCHA):
			cambiarEstado(nombre);
			String estado = (servidor.getClientes().isDisponible(nombre)) ? "DISPONIBLE" : "NO DISPONIBLE";
			servidor.notificar(nombre + " cambio su estado a " + estado + "\n");
			break;
		}
	}

	// ------------------------METODOS BASICOS------------------------//
	public String recibirMensaje() {
		String cadenaRecibida = null;
		do {
			try {
				cadenaRecibida = entrada.readLine();
			} catch (IOException e) {
				cadenaRecibida = null;
			}
		} while (cadenaRecibida == null);

		return cadenaRecibida;
	}

	public void enviarCadena(String destinatario, String cadena) {
		PrintWriter salida;
		try {
			salida = new PrintWriter(servidor.getSocketCliente(destinatario).getOutputStream(), true);
			salida.println(cadena);
		} catch (IOException e) {
		}
	}

	// ------------------------METODOS AUXILIARES------------------------//

	// Intercambia el estado del cliente, si esta disponible pasa a estar no
	// disponible, y viceversa
	public void cambiarEstado(String cliente) {
		boolean estado = !servidor.getClientes().get(cliente).isDisponible();
		servidor.getClientes().get(cliente).setDisponible(estado);
		servidor.setEstado(cliente, estado);
	}

	public void setEstado(String cliente, Boolean estado) {
		servidor.getClientes().get(cliente).setDisponible(estado);
		servidor.setEstado(cliente, estado);
	}

	/*
	 * Devuelve true si un cliente puede conectarse con el cliente pasado por
	 * parámetro -Si el cliente no esta registrado o no esta disponible devuelve
	 * false
	 */
	public boolean isPosibleConectar(String cliente) {
		return servidor.getClientes().estaDentro(cliente) && servidor.getClientes().isDisponible(cliente);
	}

	// ------------------------GETTERS Y SETTERS------------------------//
	public String getNombre() {
		return nombre;
	}

	public void setServidor(Servidor servidor) {
		this.servidor = servidor;
	}

}
