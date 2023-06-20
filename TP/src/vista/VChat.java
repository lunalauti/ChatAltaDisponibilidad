package vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import modelo.Mensaje;
import util.Constante;

public class VChat extends JFrame implements IChat {

	private JPanel contentPane;
	private JTextField textFieldMensaje;
	private JTextField txtNombreDeUsuario;
	private JPanel panelChat;
	private JPanel panelMensajes;
	private JPanel panelNombreUsuarioExterno;
	private JPanel panelEnviarMensajes;
	private JButton btnEnviar;
	private JList<String> listMensajes;
	private DefaultListModel<String> modeloMensajes = new DefaultListModel<String>();
	private ActionListener actionListener;
	private JPanel panel;
	private JButton btnCerrarSesion;

	public VChat(String userOrigin, String userDest) {
		setTitle("Chat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 408, 466);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(this.contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));

		panelChat = new JPanel();
		contentPane.add(panelChat);
		panelChat.setLayout(new GridLayout(0, 1, 0, 0));

		panelMensajes = new JPanel();
		panelChat.add(panelMensajes);
		panelMensajes.setLayout(new BorderLayout(0, 0));

		panelNombreUsuarioExterno = new JPanel();
		panelMensajes.add(panelNombreUsuarioExterno, BorderLayout.NORTH);
		this.panelNombreUsuarioExterno.setLayout(new BorderLayout(0, 0));

		txtNombreDeUsuario = new JTextField();
		this.txtNombreDeUsuario.setEditable(false);
		this.txtNombreDeUsuario.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelNombreUsuarioExterno.add(txtNombreDeUsuario);
		txtNombreDeUsuario.setColumns(10);

		panelEnviarMensajes = new JPanel();
		panelMensajes.add(panelEnviarMensajes, BorderLayout.SOUTH);
		this.panelEnviarMensajes.setLayout(new BorderLayout(0, 0));

		textFieldMensaje = new JTextField();
		panelEnviarMensajes.add(textFieldMensaje);
		textFieldMensaje.setColumns(10);

		btnEnviar = new JButton("Enviar");
		this.btnEnviar.setActionCommand(Constante.BOTON_ENVIAR);
		this.btnEnviar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelEnviarMensajes.add(btnEnviar, BorderLayout.EAST);

		this.panel = new JPanel();
		this.panelEnviarMensajes.add(this.panel, BorderLayout.SOUTH);

		this.btnCerrarSesion = new JButton("Cerrar Chat");
		this.btnCerrarSesion.setActionCommand(Constante.BOTON_CERRAR_CHAT);
		this.btnCerrarSesion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		this.panel.add(this.btnCerrarSesion);

		this.listMensajes = new JList<String>();
		this.listMensajes.setModel(modeloMensajes);
		panelMensajes.add(listMensajes, BorderLayout.CENTER);
		this.setTitle("Inicio - " + userOrigin);
		this.txtNombreDeUsuario.setText("Chat con " + userDest);
		this.setVisible(true);
	}

	@Override
	public void setActionListener(ActionListener actionListener) {
		this.btnEnviar.addActionListener(actionListener);
		this.btnCerrarSesion.addActionListener(actionListener);
		this.actionListener = actionListener;
	}

	@Override
	public void cerrarse() {
		this.dispose();

	}

	@Override
	public void actualizarMensajes(ArrayList<Mensaje> mensajes) {
		this.modeloMensajes.clear();
		for (Mensaje mensaje : mensajes) {
			this.modeloMensajes.addElement(mensaje.toString());
		}
	}

	@Override
	public String getMensaje() {
		String mensaje = this.textFieldMensaje.getText();
		this.textFieldMensaje.setText("");
		return mensaje;
	}

}
