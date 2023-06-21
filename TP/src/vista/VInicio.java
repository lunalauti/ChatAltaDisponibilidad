package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import util.Constante;

@SuppressWarnings("deprecation")
public class VInicio extends JFrame implements IInicio, MouseListener, KeyListener {

	private JPanel contentPane;
	private JPanel panelModoEscucha;
	private JPanel panelLabelModoEscucha;
	private JPanel panelDireccionIP;
	private JLabel labelDireccionIP;
	private JTextField textFieldUser;
	private JPanel panelPuerto;
	private JLabel labelPuerto;
	private JTextField textKey;
	private JPanel panelBotones;
	private JButton btnIniciar;
	private JButton btnCerrarSesion;
	private JPanel panelBotonCerrarSesion;
	private ActionListener actionListener;
	private JPanel panelIpPort;
	private JPanel panelConectar;
	private JPanel panel_1;
	private JPanel panelEstado;
	private JPanel panel_2;
	private JCheckBox chckbxNewCheckBox;
	private JLabel lblEstado;
	private JPanel panelUser;
	private JLabel lblBienvenida;

	public VInicio() {
		setTitle("Inicio");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 389, 375);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		this.contentPane.setLayout(new BorderLayout(0, 0));

		panelModoEscucha = new JPanel();
		contentPane.add(panelModoEscucha, BorderLayout.NORTH);
		this.panelModoEscucha.setLayout(new BorderLayout(0, 0));

		panelLabelModoEscucha = new JPanel();
		panelModoEscucha.add(panelLabelModoEscucha, BorderLayout.CENTER);
		this.panelLabelModoEscucha.setLayout(new GridLayout(0, 2, 0, 0));

		this.panel_2 = new JPanel();
		this.panelLabelModoEscucha.add(this.panel_2);
		this.panel_2.setLayout(new BorderLayout(0, 0));

		this.chckbxNewCheckBox = new JCheckBox("Modo escucha");
		this.chckbxNewCheckBox.setActionCommand(Constante.BOTON_MODO_ESCUCHA);
		this.chckbxNewCheckBox.addMouseListener(this);
		this.chckbxNewCheckBox.setFont(new Font("Tahoma", Font.BOLD, 12));
		this.chckbxNewCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
		this.chckbxNewCheckBox.setSelected(true);
		this.panel_2.add(this.chckbxNewCheckBox, BorderLayout.CENTER);

		this.panelEstado = new JPanel();
		this.panelEstado.setBackground(new Color(0, 255, 0));
		this.panelLabelModoEscucha.add(this.panelEstado);
		this.panelEstado.setLayout(new BorderLayout(0, 0));

		this.lblEstado = new JLabel("");
		this.lblEstado.setFont(new Font("Tahoma", Font.BOLD, 11));
		this.lblEstado.setForeground(new Color(0, 0, 0));
		this.lblEstado.setBackground(new Color(0, 255, 0));
		this.lblEstado.setHorizontalAlignment(SwingConstants.CENTER);
		this.panelEstado.add(this.lblEstado, BorderLayout.CENTER);

		this.panelUser = new JPanel();
		this.panelModoEscucha.add(this.panelUser, BorderLayout.NORTH);
		this.panelUser.setLayout(new BorderLayout(0, 0));

		this.lblBienvenida = new JLabel("Bienvenido/a, ");
		this.lblBienvenida.setFont(new Font("Tahoma", Font.BOLD, 15));
		this.lblBienvenida.setHorizontalAlignment(SwingConstants.CENTER);
		this.panelUser.add(this.lblBienvenida);

		this.panelConectar = new JPanel();
		this.panelConectar.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Iniciar chat", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		this.contentPane.add(this.panelConectar, BorderLayout.CENTER);
		this.panelConectar.setLayout(new BorderLayout(0, 0));

		this.panelIpPort = new JPanel();
		this.panelConectar.add(this.panelIpPort, BorderLayout.CENTER);
		this.panelIpPort.setLayout(new GridLayout(2, 2, 0, 0));

		panelDireccionIP = new JPanel();
		this.panelIpPort.add(this.panelDireccionIP);
		panelDireccionIP.setLayout(new GridLayout(2, 0, 0, 0));

		labelDireccionIP = new JLabel("Username:");
		panelDireccionIP.add(labelDireccionIP);

		textFieldUser = new JTextField();
		this.textFieldUser.addKeyListener(this);
		this.textFieldUser.setEnabled(false);
		panelDireccionIP.add(textFieldUser);
		textFieldUser.setColumns(10);

		panelPuerto = new JPanel();
		this.panelIpPort.add(this.panelPuerto);
		panelPuerto.setLayout(new GridLayout(2, 0, 0, 0));

		labelPuerto = new JLabel("Clave de 8 digitos:");
		panelPuerto.add(labelPuerto);

		textKey = new JTextField();
		this.textKey.addKeyListener(this);
		this.textKey.setEnabled(false);
		panelPuerto.add(textKey);
		textKey.setColumns(10);

		btnIniciar = new JButton("Iniciar");
		this.btnIniciar.setActionCommand(Constante.BOTON_INICIAR);
		this.btnIniciar.setEnabled(false);
		this.panelConectar.add(this.btnIniciar, BorderLayout.SOUTH);

		panelBotones = new JPanel();
		contentPane.add(panelBotones, BorderLayout.SOUTH);
		panelBotones.setLayout(new GridLayout(2, 0, 0, 0));

		this.panel_1 = new JPanel();
		this.panelBotones.add(this.panel_1);

		panelBotonCerrarSesion = new JPanel();
		panelBotones.add(panelBotonCerrarSesion);

		btnCerrarSesion = new JButton("Cerrar Sesion");
		this.btnCerrarSesion.setActionCommand(Constante.BOTON_CERRAR_SESION);
		panelBotonCerrarSesion.add(btnCerrarSesion);
		this.setVisible(true);
		validarModo();
	}

	@Override
	public void setActionListener(ActionListener actionListener) {
		this.actionListener = actionListener;
		this.btnIniciar.addActionListener(actionListener);
		this.btnCerrarSesion.addActionListener(actionListener);
		this.chckbxNewCheckBox.addActionListener(actionListener);
	}

	@Override
	public void cerrarse() {
		this.dispose();

	}

	@Override
	public void settearDatos(String user) {
		this.lblBienvenida.setText("Bienvenid@, " + user + "!");
		this.setTitle("Inicio - " + user);
	}

	@Override
	public String getUser() {
		return this.textFieldUser.getText();
	}

	@Override
	public String getKey() {
		return this.textKey.getText();
	}

	private void validarModo() {
		if (this.chckbxNewCheckBox.isSelected()) {
			this.lblEstado.setText("ACTIVADO");
			this.panelEstado.setBackground(Color.GREEN);
			this.textFieldUser.setEnabled(false);
			this.textKey.setEnabled(false);
		} else {
			this.lblEstado.setText("DESACTIVADO");
			this.panelEstado.setBackground(Color.RED);
			this.textFieldUser.setEnabled(true);
			this.textKey.setEnabled(true);
		}

	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
		validarModo();
	}

	@Override
	public boolean getModo() {
		return this.chckbxNewCheckBox.isSelected();
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
		validarIngreso();
	}

	private void validarIngreso() {
		boolean estado = !this.textFieldUser.getText().isBlank() && !this.textFieldUser.getText().isEmpty()
				&& this.textKey.getText().length() == 8;
		this.btnIniciar.setEnabled(estado);
	}

	public void keyTyped(KeyEvent e) {
	}
}
