package vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import util.Constante;

public class VLogin extends JFrame implements ILogin, KeyListener {
	private JPanel contentPane;
	private JTextField textPuerto;
	private ActionListener actionListener;
	private JPanel panelLogin;
	private JPanel panelLabelLogin;
	private JLabel labelLogin;
	private JPanel panelNombreUsuario;
	private JPanel panelLabelNU;
	private JLabel labelNombreUsuario;
	private JPanel paneltxtNU;
	private JTextField txtNombreDeUsuario;
	private JPanel panelPuerto;
	private JPanel panelLabelPuerto;
	private JLabel labelPuerto;
	private JPanel panelTextPuerto;
	private JPanel panelIniciarSesion;
	private JPanel panel;
	private JPanel panelBotonIniciarSesion;
	private JButton btnIniciarSesion;

	public VLogin() {
		setTitle("Login");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 264, 360);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(this.contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));

		panelLogin = new JPanel();
		contentPane.add(panelLogin);
		panelLogin.setLayout(new GridLayout(4, 0, 0, 0));

		panelLabelLogin = new JPanel();
		panelLogin.add(panelLabelLogin);
		panelLabelLogin.setLayout(new BorderLayout(0, 0));

		labelLogin = new JLabel("LOGIN");
		labelLogin.setHorizontalAlignment(SwingConstants.CENTER);
		labelLogin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panelLabelLogin.add(labelLogin, BorderLayout.NORTH);

		panelNombreUsuario = new JPanel();
		panelLogin.add(panelNombreUsuario);
		panelNombreUsuario.setLayout(new GridLayout(2, 0, 0, 0));

		panelLabelNU = new JPanel();
		panelNombreUsuario.add(panelLabelNU);
		panelLabelNU.setLayout(new BorderLayout(0, 0));

		labelNombreUsuario = new JLabel("Nombre de usuario:");
		panelLabelNU.add(labelNombreUsuario);

		paneltxtNU = new JPanel();
		panelNombreUsuario.add(paneltxtNU);
		paneltxtNU.setLayout(new BorderLayout(0, 0));

		txtNombreDeUsuario = new JTextField();
		this.txtNombreDeUsuario.addKeyListener(this);
		paneltxtNU.add(txtNombreDeUsuario);
		txtNombreDeUsuario.setColumns(10);

		panelPuerto = new JPanel();
		panelLogin.add(panelPuerto);
		panelPuerto.setLayout(new GridLayout(2, 0, 0, 0));

		panelLabelPuerto = new JPanel();
		panelPuerto.add(panelLabelPuerto);
		panelLabelPuerto.setLayout(new BorderLayout(0, 0));

		labelPuerto = new JLabel("Puerto de recepcion:");
		panelLabelPuerto.add(labelPuerto);

		panelTextPuerto = new JPanel();
		panelPuerto.add(panelTextPuerto);
		panelTextPuerto.setLayout(new BorderLayout(0, 0));

		textPuerto = new JTextField();
		this.textPuerto.addKeyListener(this);
		panelTextPuerto.add(textPuerto);
		textPuerto.setColumns(10);

		panelIniciarSesion = new JPanel();
		panelLogin.add(panelIniciarSesion);
		panelIniciarSesion.setLayout(new GridLayout(2, 0, 0, 0));

		panel = new JPanel();
		panelIniciarSesion.add(panel);

		panelBotonIniciarSesion = new JPanel();
		panelIniciarSesion.add(panelBotonIniciarSesion);
		panelBotonIniciarSesion.setLayout(new BorderLayout(0, 0));

		btnIniciarSesion = new JButton("IniciarSesion");
		this.btnIniciarSesion.setActionCommand(Constante.BOTON_INICIAR_SESION);
		panelBotonIniciarSesion.add(btnIniciarSesion);
		this.btnIniciarSesion.setEnabled(false);
		this.setVisible(true);

	}

	private boolean validar() {
		return !this.textPuerto.getText().isBlank() || !this.textPuerto.getText().isEmpty();
	}

	@Override
	public void setActionListener(ActionListener actionListener) {
		this.btnIniciarSesion.addActionListener(actionListener);
		this.actionListener = actionListener;
	}

	@Override
	public String getNombre() {
		return this.txtNombreDeUsuario.getText();
	}

	@Override
	public Integer getPuerto() {
		return Integer.parseInt(this.textPuerto.getText());
	}

	@Override
	public void cerrarse() {
		this.dispose();
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
		this.btnIniciarSesion.setEnabled(validar());
	}

	public void keyTyped(KeyEvent e) {
	}

	@Override
	public String getKey() {
		return null;
	}

}
