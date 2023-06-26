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

import javax.swing.JOptionPane;

import servidor.controlador.ControladorMonitor;
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
	///Controlador y Vista
	private ControladorMonitor controlador;

	

	public static void main(String[] args) {
		new ControladorMonitor();
	}

	private Monitor(ControladorMonitor controlador) {
		try {
			this.serverSocket = new ServerSocket(Constante.PUERTO_MONITOR);
			System.out.println("Esperando servidores...");
			this.controlador = controlador;
			notificar("Esperando servidores...\n");
			recibirClientes();
			recibirServidores();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Monitor getInstance(ControladorMonitor controlador) {
		if (instance == null)
			instance = new Monitor(controlador);
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
					notificar("Se conecto un servidor principal\n");
					this.controlador.cambiarPrincipal(true);
					System.out.println("Servidor principal:\t Conectado");
					serverActivo = 1;
					recibirHeartbeat(principal);
				} else if (secundario == null) {
					this.secundario = this.serverSocket.accept();
					salidaSecundario = new PrintWriter(this.secundario.getOutputStream(), true);
					salidaSecundario.println("2");
					notificar("Se conecto un servidor secundario\n");
					this.controlador.cambiarSecundario(true);
					System.out.println("Servidor secundario:\t Conectado");
				}
			} catch (SocketTimeoutException e) {
				if (principal == null) {
					System.out.println("[Timeout]\t-Reintentando conectar servidor principal...");
					notificar("[Timeout]\t-Reintentando conectar servidor principal...\n");
				}
				else {
					System.out.println("[Timeout]\t-Reintentando conectar servidor secundario...");
					notificar("[Timeout]\t-Reintentando conectar servidor secundario...\n");
				}
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
					notificar("Servidor " + idServer + "\t disponible\n");
				}
			} catch (SocketTimeoutException | SocketException e) {
				this.controlador.cambiarPrincipal(false);
				System.out.println("Falla de server detectada");
				notificar("Falla de server detectada\n");
				if (secundario != null) {
					notificar("////////Se establecio un nuevo servidor principal////////\n");
					cambiarServer();
					recibirHeartbeat(principal);
				} else {
					System.out.println("No se encontro un servidor de respaldo :(");
					this.principal = null;
					notificar("No se encontro un servidor de respaldo :(\n");		
				}
				recibirServidores();
			} catch (IOException e) {
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
			principal = secundario;
			secundario = null;
			this.controlador.cambiarSecundario(false);
			this.controlador.cambiarPrincipal(true);
			salidaPrincipal = new PrintWriter(principal.getOutputStream(), true);
			salidaPrincipal.println(Constante.COMANDO_CAMBIAR_SERVER);
//			notificar("/////////////Se establecio un nuevo servidor principal/////////////");
			Thread.sleep(2000);
			for (Socket socket : clientes) {
				System.out.println(socket);
				salidaCliente = new PrintWriter(socket.getOutputStream(), true);
				salidaCliente.println(Constante.COMANDO_CAMBIAR_SERVER);
			}

		} catch (IOException | InterruptedException e) {
			System.out.println("Ocurrio un problema en el cambio de servidor");
			notificar("Ocurrio un problema en el cambio de servidor\n");
		}
	}
	
	///Controlador y Vista


	public ControladorMonitor getControlador() {
		return controlador;
	}

	public void setControlador(ControladorMonitor controlador) {
		this.controlador = controlador;
	}

	public void notificar(String notificacion) {
		this.controlador.notificar(notificacion);
	}

	
	
}
