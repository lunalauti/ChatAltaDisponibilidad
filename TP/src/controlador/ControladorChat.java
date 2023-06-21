package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.Chat;
import util.Constante;
import vista.IChat;
import vista.VInicio;

public class ControladorChat implements ActionListener {
	private Chat chat;
	private IChat vista;
	private static ControladorChat instance = null;

	private ControladorChat() {
	}

	public static ControladorChat getInstance() {
		if (instance == null)
			instance = new ControladorChat();
		return instance;
	}

	private void enviarMensaje() {
		String mensaje = this.vista.getMensaje();
		this.chat.enviarMensaje(mensaje);
		this.vista.actualizarMensajes(this.chat.getMensajes());
		this.vista.actualizarMensajes(this.chat.getMensajes());
	}

	public void recibirMensaje(String cadena) {
		String[] mensaje = cadena.split(";");
		switch (mensaje[Constante.INSTRUCCION]) {

		case (Constante.COMANDO_ENVIAR):
			this.chat.recibirMensaje(mensaje[Constante.MENSAJE]);
			this.vista.actualizarMensajes(this.chat.getMensajes());
			break;

		case (Constante.COMANDO_DESCONECTAR):
			this.cerrarChat();
			break;
		}
	}

	public void setVista(IChat vista) {
		this.vista = vista;
		this.vista.setActionListener(this);
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	public void cerrarChat() {
		this.vista.cerrarse();
		ControladorInicio.getInstance().setVista(new VInicio());
		ControladorInicio.getInstance().setCliente(this.chat.getUserOrig());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {

		case (Constante.BOTON_ENVIAR):
			enviarMensaje();
			break;

		case (Constante.BOTON_CERRAR_CHAT):
			this.chat.desconectarChat(); // envia al otro cliente que se desconecto
			cerrarChat(); // cierra las ventanas
			break;
		}

	}

}
