package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import ejecutable.Cliente;
import modelo.Chat;
import util.Constante;
import vista.IInicio;
import vista.VChat;
import vista.VLogin;

@SuppressWarnings("deprecation")
public class ControladorInicio implements ActionListener {

	private Cliente cliente;
	private IInicio vista;
	private static ControladorInicio instance = null;
	private boolean esperando = false;

	// ------------------------METODO SINGLETON------------------------//
	private ControladorInicio() {
	}

	public static ControladorInicio getInstance() {
		if (instance == null)
			instance = new ControladorInicio();
		return instance;
	}

	// ------------------------METODOS AVANZADOS------------------------//
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {

		case (Constante.BOTON_INICIAR):
			conectar(this.vista.getUser(), this.vista.getKey());
			break;

		case (Constante.BOTON_CERRAR_SESION):
			this.cliente.finalizarConexion();
			this.vista.cerrarse();
			ControladorLogin.getInstance().setVista(new VLogin());
			break;

		case (Constante.BOTON_MODO_ESCUCHA):
			this.cliente.cambiarModoEscucha();
			break;
		}
	}

	public void conectar(String userDest, String key) {
		if (!esperando) {
			this.cliente.establecerConexion(userDest, key);
			esperando = true;
		} else
			JOptionPane.showMessageDialog(null, "Solo puedes enviar una solicitud a la vez :(");
	}

	public void interpretarRespuesta(String cadena) {
		String[] mensaje = cadena.split(";");
		switch (mensaje[Constante.INSTRUCCION]) {
		case (Constante.COMANDO_ACEPTAR):
			cargarChat(mensaje[Constante.DESTINATARIO], mensaje[Constante.CLAVE]);
			break;

		case (Constante.COMANDO_RECHAZAR):
			JOptionPane.showMessageDialog(null, "No se puede establecer la conexion :(");
			break;
		}
		esperando = false;
	}

	// ------------------------SETTERS------------------------//
	public void setVista(IInicio vista) {
		this.vista = vista;
		this.vista.setActionListener(this);
	}

	public void cargarChat(String userDest, String key) {
		ControladorChat.getInstance().setChat(new Chat(this.cliente, userDest, key));
		ControladorChat.getInstance().setVista(new VChat(this.cliente.getUser(), userDest));
		this.vista.cerrarse();
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
		this.vista.settearDatos(cliente.getUser());
		this.cliente.IniciaHiloCliente();
	}

}
