package servidor.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import servidor.modelo.Monitor;
import servidor.vista.VMonitor;
import servidor.vista.VServer;
import util.Constante;

public class ControladorMonitor implements ActionListener
{
	VMonitor vista;
	Monitor monitor;	
	
	public ControladorMonitor() {
		settearVista();
		this.monitor = Monitor.getInstance(this);
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		if (comando.equalsIgnoreCase("Apagar")) {
			int option = JOptionPane.showConfirmDialog(null,
					"¿Seguro que desea apagar el monitor? Ningun dato quedara almacenado", "Confirmacion",
					JOptionPane.YES_NO_OPTION);
			if (option == JOptionPane.YES_OPTION) {
				vista.cerrarse();
			}
		}
		
	}
	
	public void settearVista() {
			vista = new VMonitor();
			vista.settearDatos("No hay", "No hay");
			vista.setActionListener(this);
	}
	
	public void notificar(String notificacion) {
		this.vista.actualizarHistorial(notificacion);
	}
	
	public void cambiarPrincipal(Boolean estado) {
		this.vista.actualizarPrincipal(estado);
	}
	
	public void cambiarSecundario(Boolean estado) {
		this.vista.actualizarSecundario(estado);
	}

}
