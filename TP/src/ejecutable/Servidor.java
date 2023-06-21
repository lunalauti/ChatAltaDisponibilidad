package ejecutable;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import controlador.ControladorServer;
import modelo.Datos;
import modelo.HiloServidor;
import modelo.ListaClientes;
import modelo.SocketCliente;
import util.Constante;

public class Servidor {
	private Socket monitor;
	private int idServer;
	private ServerSocket server = null;
	private Socket socketSincronizacion;
	private ListaClientes clientes = new ListaClientes();
	private ControladorServer controlador;
	private BufferedReader entradaMonitor;
	private PrintWriter salidaMonitor;
	private DataInputStream entradaSincronizacion = null;
	private DataOutputStream salidaSincronizacion = null;
	private ArrayList<String> historial = new ArrayList<String>();
	private ArrayList<Datos> datos = new ArrayList<Datos>();

	// ------------------EJECUTABLE--------------------//
	public static void main(String[] args) {
		try {
			new ControladorServer();

		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	// ------------------METODOS--------------------//
	public void iniciarServidor() throws IOException {
		conectarMonitor();
		setID();
		server = (idServer == 1) ? new ServerSocket(Constante.PUERTO_PRINCIPAL)
				: new ServerSocket(Constante.PUERTO_SECUNDARIO);
	}

	public void recibirClientes() {
		while (!server.isClosed()) {
			try {
				Socket socket = server.accept();
				HiloServidor thread = new HiloServidor(socket, this);
				thread.start();
			} catch (IOException e) {
				if (!server.isClosed())
					e.printStackTrace();
			}
		}
	}

	public void setID() {
		try {
			this.idServer = Integer.parseInt(recibirCadenaMonitor());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public void sincronizarServer() {
		new Thread(() -> {
			Socket socket;
			try {
				switch (this.idServer) {
				case 1:
					ServerSocket server = new ServerSocket(Constante.PUERTO_SINCRONIZACION);
					socketSincronizacion = server.accept();
					salidaSincronizacion = new DataOutputStream(socketSincronizacion.getOutputStream());
					notificar("Sincronizado con servidor secundario\n");
					break;
				case 2:
					socketSincronizacion = new Socket(Constante.IP_SERVIDOR, Constante.PUERTO_SINCRONIZACION);
					entradaSincronizacion = new DataInputStream(socketSincronizacion.getInputStream());
					recibirActualizacion();
					notificar("Sincronizado con servidor principal\n");
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();

	}

	public void resincronizarEstado() {
		try {
			if (!socketSincronizacion.isClosed()) {
				JSONArray datosJson = getJsonArray();
				System.out.println("principal " + datosJson.length());
				salidaSincronizacion.writeUTF(datosJson.toString());
			}
		} catch (IOException e) {
			notificar("Fallo en la sincronizacion detectado\n");
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void recibirActualizacion() {
		new Thread(() -> {
			try {
				while (!socketSincronizacion.isClosed() && entradaSincronizacion != null) {
					String jsonStr = entradaSincronizacion.readUTF();
					JSONArray jsonArray = new JSONArray(jsonStr);
					cargarDatos(jsonArray);
					System.out.println(jsonArray.length());
					controlador.actualizarConectados();
				}
			} catch (IOException | JSONException e) {
				e.printStackTrace();
			}
		}).start();
	}

	// ------------------METODOS BASICOS--------------------//

	public void closeServer() {
		try {
			for (HashMap.Entry<String, SocketCliente> entry : clientes.getMapaClientes().entrySet()) {
				entry.getValue().getSocket().close();
			}
			notificar("Cerrando servidor...\n");
			server.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void meterCliente(String user, Socket socket) {
		clientes.add(user, socket);
		notificar(user + " inicio sesion\n");
		datos.add(new Datos(user));
		controlador.actualizarConectados();
		resincronizarEstado();
	}

	public void quitarCliente(String cliente) {
		clientes.remove(cliente);
		notificar(cliente + " finalizo su sesion\n");
		datos.remove(getIndex(cliente));
		controlador.actualizarConectados();
	}

	public void notificar(String notificacion) {
		this.historial.add(notificacion);
		controlador.notificar();
	}

	private void cargarDatos(JSONArray jsonArray) {
		datos.clear();
		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject dato = jsonArray.getJSONObject(i);
				String nombre = dato.getString("nombre");
				Boolean disponible = dato.getBoolean("disponible");
				datos.add(new Datos(nombre, disponible));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	// ------------------METODOS MONITOR--------------------//

	private void conectarMonitor() {
		try {
			this.monitor = new Socket(Constante.IP_SERVIDOR, Constante.PUERTO_MONITOR);
			entradaMonitor = new BufferedReader(new InputStreamReader(monitor.getInputStream()));
			salidaMonitor = new PrintWriter(monitor.getOutputStream(), true);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "No se puede iniciar el servidor :(");
		}
	}

	public void escucharMonitor() {
		new Thread(() -> {
			String cadena;
			do {
				cadena = recibirCadenaMonitor();
				if (cadena.equalsIgnoreCase(Constante.COMANDO_CAMBIAR_SERVER)) {
					// controlador.cambiarServer();
				}
			} while (!monitor.isClosed());
		}).start();
	}

	public void heartbeat() {
		new Thread(() -> {
			try {
				while (!server.isClosed()) {
					enviarCadenaMonitor(String.valueOf(idServer));
					Thread.sleep(5000);
				}
			} catch (InterruptedException e) {

			}
		}).start();
	}

	public String recibirCadenaMonitor() {
		String cadena = null;
		try {
			cadena = entradaMonitor.readLine();
		} catch (IOException e) {
			cadena = null;
		}
		return cadena;
	}

	public void enviarCadenaMonitor(String cadena) {
		if (monitor == null || monitor.isClosed()) {
			JOptionPane.showMessageDialog(null, "El monitor no se encuentra disponible :(\n");
		} else {
			salidaMonitor.println(cadena);
		}
	}

	public JSONArray getJsonArray() {
		JSONArray jsonDatos = new JSONArray();
		for (Datos dato : datos)
			jsonDatos.put(dato.getJSONObject());
		return jsonDatos;
	}

	// ------------------GETTERS--------------------//
	public ListaClientes getClientes() {
		return clientes;
	}

	public Socket getSocketCliente(String cliente) {
		return clientes.get(cliente).getSocket();
	}

	public ServerSocket getServer() {
		return server;
	}

	public int getIdServer() {
		return idServer;
	}

	public int getClientesConectados() {
		return datos.size();
	}

	public void setControlador(ControladorServer controlador) {
		this.controlador = controlador;
	}

	public void setEstado(String user, boolean estado) {
		datos.get(getIndex(user)).setDisponible(estado);
	}

	public int getIndex(String cliente) {
		int i = 0;
		while (!datos.get(i).getNombre().equalsIgnoreCase(cliente) && i < datos.size()) {
			i++;
		}
		if (i >= datos.size())
			i = -1;
		return i;
	}

	public ArrayList<String> getHistorial() {
		return historial;
	}

}
