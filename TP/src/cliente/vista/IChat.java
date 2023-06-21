package cliente.vista;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import cliente.modelo.Mensaje;

public interface IChat {
	public void setActionListener(ActionListener actionListener);

	public String getMensaje();

	public void cerrarse();

	void actualizarMensajes(ArrayList<Mensaje> mensajes);
}
