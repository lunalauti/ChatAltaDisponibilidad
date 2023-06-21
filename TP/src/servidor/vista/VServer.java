package servidor.vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.text.DefaultCaret;

import util.Constante;

@SuppressWarnings("serial")
public class VServer extends JFrame {

	private JPanel contentPane;
	private JPanel panel_1;
	private JLabel lblNewLabel_3;
	private JLabel lblPort;
	private JPanel panel_3;
	private JButton btnApagar;
	private JPanel panel_4;
	private JTextArea textAreaHistorial;
	private JScrollPane scrollPane;
	private JLabel lblIP;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblConexiones;
	private JPanel panel;
	private JPanel panel_2;
	private JPanel panel_5;
	private JPanel panel_6;
	private JLabel lblNewLabel_2;

	public VServer() {
		setTitle("Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 497, 312);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(this.contentPane);
		this.contentPane.setLayout(new BorderLayout(0, 0));

		this.panel_6 = new JPanel();
		this.contentPane.add(this.panel_6, BorderLayout.NORTH);
		this.panel_6.setLayout(new BorderLayout(0, 0));

		this.panel_1 = new JPanel();
		this.panel_6.add(this.panel_1);
		this.panel_1.setLayout(new GridLayout(0, 1, 0, 0));

		this.panel = new JPanel();
		this.panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		this.panel_1.add(this.panel);
		this.panel.setLayout(new GridLayout(0, 2, 0, 0));

		this.lblNewLabel = new JLabel("Direccion IP:");
		this.lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		this.panel.add(this.lblNewLabel);

		this.lblIP = new JLabel("");
		this.lblIP.setFont(new Font("Tahoma", Font.PLAIN, 13));
		this.panel.add(this.lblIP);
		this.lblIP.setHorizontalAlignment(SwingConstants.LEFT);

		this.panel_2 = new JPanel();
		this.panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		this.panel_1.add(this.panel_2);
		this.panel_2.setLayout(new GridLayout(0, 2, 0, 0));

		this.lblNewLabel_3 = new JLabel("Puerto:");
		this.lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		this.panel_2.add(this.lblNewLabel_3);

		this.lblPort = new JLabel("");
		this.lblPort.setFont(new Font("Tahoma", Font.PLAIN, 13));
		this.panel_2.add(this.lblPort);
		this.lblPort.setHorizontalAlignment(SwingConstants.LEFT);

		this.panel_5 = new JPanel();
		this.panel_5.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		this.panel_1.add(this.panel_5);
		this.panel_5.setLayout(new GridLayout(0, 2, 0, 0));

		this.lblNewLabel_1 = new JLabel("Conexiones:");
		this.lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		this.panel_5.add(this.lblNewLabel_1);

		this.lblConexiones = new JLabel("");
		this.lblConexiones.setHorizontalAlignment(SwingConstants.LEFT);
		this.lblConexiones.setFont(new Font("Tahoma", Font.PLAIN, 13));
		this.panel_5.add(this.lblConexiones);
		this.lblConexiones.setBounds(new Rectangle(0, 0, 14, 0));
		this.lblConexiones.setAlignmentX(0.5f);

		this.lblNewLabel_2 = new JLabel("SERVER");
		this.lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		this.lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		this.panel_6.add(this.lblNewLabel_2, BorderLayout.NORTH);

		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		textAreaHistorial = new JTextArea();
		textAreaHistorial.setEditable(false);
		scrollPane.setViewportView(textAreaHistorial);
		DefaultCaret caret = (DefaultCaret) textAreaHistorial.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		this.panel_3 = new JPanel();
		this.contentPane.add(this.panel_3, BorderLayout.SOUTH);
		this.panel_3.setLayout(new GridLayout(2, 1, 0, 0));

		this.panel_4 = new JPanel();
		this.panel_3.add(this.panel_4);

		this.btnApagar = new JButton("Apagar");
		this.btnApagar.setActionCommand(Constante.BOTON_APAGAR);
		this.panel_3.add(this.btnApagar);
		this.setVisible(true);

	}

	public void settearDatos(String ip, String puerto, String cant) {
		this.lblConexiones.setText(cant);
		this.lblIP.setText(ip);
		this.lblPort.setText(puerto);
	}

	public void actualizarCant(String cant) {
		this.lblConexiones.setText(cant);
	}

	public void setActionListener(ActionListener actionListener) {
		this.btnApagar.addActionListener(actionListener);

	}

	public void actualizarHistorial(ArrayList<String> historial) {
		this.textAreaHistorial.append(historial.get(historial.size() - 1));
	}

	public void cerrarse() {
		System.exit(0);
		this.dispose();
	}

}
