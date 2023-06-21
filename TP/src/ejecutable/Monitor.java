package ejecutable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import util.Constante;

public class Monitor {

	private Socket principal = null;
	private Socket secundario = null;
	private int serverActivo;
	private ServerSocket serverSocket;
	private static Monitor instance = null;

	public static void main(String[] args) {
		Monitor.getInstance();
	}

	private Monitor() {
		try {
			this.serverSocket = new ServerSocket(Constante.PUERTO_MONITOR);
			System.out.println("Esperando servidores...");
			setConnections();
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

	public void setConnections() {
		while (!this.serverSocket.isClosed()) {
			try {
				this.serverSocket.setSoTimeout(10000);
				if (principal == null) {
					principal = this.serverSocket.accept();
					PrintWriter salida = new PrintWriter(principal.getOutputStream(), true);
					salida.println("1");
					System.out.println("Servidor principal- Conectado");
					serverActivo = 1;
					recibirHeartbeat(principal);
				} else if (secundario == null) {
					this.secundario = this.serverSocket.accept();
					PrintWriter salida = new PrintWriter(this.secundario.getOutputStream(), true);
					salida.println("2");
					System.out.println("Servidor secundario- Conectado");
				}
			} catch (SocketTimeoutException e) {
				if (principal == null)
					System.out.println("TimeOut - Reintentando conectar servidor principal...");
				else
					System.out.println("TimeOut - Reintentando conectar servidor secundario...");
				setConnections();
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
						// cambiarServer();
					}
				}
			} catch (IOException e) {
				System.out.println("Falla de server detectada");
			}
		}).start();
	}

//	public void cambiarServer() {
//		try {
//			System.out.println("Cambiando a servidor secundario...");
//			PrintWriter output = new PrintWriter(this.secundario.getOutputStream(), true);
//			output.println("switch");
//			recibirHeartbeat();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}
