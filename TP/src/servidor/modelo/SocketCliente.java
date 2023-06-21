package servidor.modelo;

import java.net.Socket;

public class SocketCliente {
	private String user;
	private Socket socket;
	private boolean disponible;

	public String getUsuario() {
		return user;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public SocketCliente(String user, Socket socket) {
		this.user = user;
		this.socket = socket;
		this.disponible = true;
	}

}
