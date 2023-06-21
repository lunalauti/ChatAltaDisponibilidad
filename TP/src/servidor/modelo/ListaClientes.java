package servidor.modelo;

import java.net.Socket;
import java.util.HashMap;

public class ListaClientes {
	private HashMap<String, SocketCliente> mapaClientes = new HashMap<String, SocketCliente>();

	public int getClientesConectados() {
		return mapaClientes.size();
	}

	public void add(String nombre, Socket socket) {
		mapaClientes.put(nombre, new SocketCliente(nombre, socket));
	}

	public void remove(String nombre) {
		mapaClientes.remove(nombre);
	}

	public SocketCliente get(String cliente) {
		return mapaClientes.get(cliente);
	}

	public boolean isDisponible(String nombre) {
		return mapaClientes.get(nombre).isDisponible();
	}

	public boolean estaDentro(String nombre) {
		return mapaClientes.containsKey(nombre);
	}

	public HashMap<String, SocketCliente> getMapaClientes() {
		return mapaClientes;
	}

	public void setSocket(String cliente, Socket socket) {
		mapaClientes.get(cliente).setSocket(socket);
	}

	public void clear() {
		mapaClientes.clear();
	}

}
