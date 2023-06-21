package cliente.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;

import cliente.modelo.Cliente;
import cliente.vista.ILogin;
import cliente.vista.VInicio;
import cliente.vista.VLogin;
import util.Constante;

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
		try {
			cliente.registroServer(Constante.IP_SERVIDOR, Constante.PUERTO_PRINCIPAL);
			cliente.conectarMonitor();
			cliente.escucharMonitor();

			this.vista.cerrarse();
			ControladorInicio.getInstance().setVista(new VInicio());
			ControladorInicio.getInstance().setCliente(cliente);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "No se puede iniciar sesion :(");
		}
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
