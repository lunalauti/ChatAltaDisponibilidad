package servidor.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import util.Constante;

public class Monitor {

	private Socket principal = null;
	private PrintWriter salidaPrincipal;
	private Socket secundario = null;
	private PrintWriter salidaSecundario;
	private int serverActivo;
	private ServerSocket serverSocket;
	private ArrayList<Socket> clientes = new ArrayList<Socket>();
	private static Monitor instance = null;

	public static void main(String[] args) {
		Monitor.getInstance();
	}

	private Monitor() {
		try {
			this.serverSocket = new ServerSocket(Constante.PUERTO_MONITOR);
			System.out.println("Esperando servidores...");
			recibirClientes();
			recibirServidores();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Monitor getInstance() {
		if (instance == null)
			instance = new Monitor();
		return instance;
	}

	public Socket getPrincipal() {
		return principal;
	}

	public Socket getSecundario() {
		return secundario;
	}

	public void setPrincipal(Socket principal) {
		this.principal = principal;
	}

	public void setSecundario(Socket secundario) {
		this.secundario = secundario;
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void recibirServidores() {
		while (!this.serverSocket.isClosed()) {
			try {
				this.serverSocket.setSoTimeout(10000);
				if (principal == null) {
					principal = this.serverSocket.accept();
					salidaPrincipal = new PrintWriter(principal.getOutputStream(), true);
					salidaPrincipal.println("1");
					System.out.println("Servidor principal:\t Conectado");
					serverActivo = 1;
					recibirHeartbeat(principal);
				} else if (secundario == null) {
					this.secundario = this.serverSocket.accept();
					salidaSecundario = new PrintWriter(this.secundario.getOutputStream(), true);
					salidaSecundario.println("2");
					System.out.println("Servidor secundario:\t Conectado");
				}
			} catch (SocketTimeoutException e) {
				if (principal == null)
					System.out.println("[Timeout]\t-Reintentando conectar servidor principal...");
				else
					System.out.println("[Timeout]\t-Reintentando conectar servidor secundario...");
				recibirServidores();
			} catch (IOException e) {
				if (!this.serverSocket.isClosed())
					e.printStackTrace();
			}
		}
	}

	public void recibirHeartbeat(Socket socket) {
		new Thread(() -> {
			try {
				socket.setSoTimeout(6500);
				BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				while (true) {
					String idServer = entrada.readLine();
					System.out.println("Servidor " + idServer + "\t disponible");
					if (idServer.equalsIgnoreCase("1") && serverActivo != 1) {
						cambiarServer();
					}
				}
			} catch (SocketTimeoutException | SocketException e) {
				System.out.println("Falla de server detectada");
				principal = null;
				if (secundario != null)
					cambiarServer();
				else
					System.out.println("No se encontro un servidor de respaldo :(");
				recibirServidores();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
	}

	public void recibirClientes() {
		new Thread(() -> {
			try {
				ServerSocket serverCliente = new ServerSocket(Constante.PUERTO_MONITOR_CLIENTE);
				while (!serverCliente.isClosed()) {
					Socket socket = serverCliente.accept();
					clientes.add(socket);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
	}

	public void cambiarServer() {
		try {
			String comando;
			PrintWriter salidaCliente;
			if (principal == null) {
				comando = Constante.COMANDO_CAMBIAR_SERVER_SECUNDARIO;
				System.out.println("Cambiando a servidor secundario..");
				salidaSecundario.println(comando);
			} else {
				comando = Constante.COMANDO_CAMBIAR_SERVER_PRINCIPAL;
				System.out.println("Cambiando a servidor principal..");
				salidaPrincipal.println(comando);
			}
			for (Socket socket : clientes) {
				System.out.println("enviando a cliente");
				salidaCliente = new PrintWriter(socket.getOutputStream(), true);
				salidaCliente.println(comando);
			}
		} catch (

		IOException e) {
			System.out.println("Ocurrio un problema en el cambio de servidor");
		}
	}
}
