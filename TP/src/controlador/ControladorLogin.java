package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import ejecutable.Cliente;
import util.Constante;
import vista.ILogin;
import vista.VInicio;
import vista.VLogin;

public class ControladorLogin implements ActionListener {
	ILogin vista;
	private static ControladorLogin instance = null;

	private ControladorLogin() {
		this.vista = new VLogin();
		this.vista.setActionListener(this);
	}

	public static ControladorLogin getInstance() {
		if (instance == null)
			instance = new ControladorLogin();
		return instance;
	}

	public void registrarse() {
		String nombre = vista.getNombre();
		Integer puerto = vista.getPuerto();
		Cliente cliente = new Cliente(nombre, puerto);
		if (cliente.registroServer(Constante.IP_SERVIDOR, Constante.PUERTO_PRINCIPAL)) {
			this.vista.cerrarse();
			ControladorInicio.getInstance().setVista(new VInicio());
			ControladorInicio.getInstance().setCliente(cliente);
		} else
			JOptionPane.showMessageDialog(null, "No se puede iniciar sesion :(");
	}

	public void setVista(ILogin vista) {
		this.vista = vista;
		this.vista.setActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase(Constante.BOTON_INICIAR_SESION)) {
			registrarse();
		}
	}

}
