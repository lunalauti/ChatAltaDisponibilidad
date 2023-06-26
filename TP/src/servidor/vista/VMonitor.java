package servidor.vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

import util.Constante;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class VMonitor extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private JLabel lblNewLabel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_4;
	private JPanel panel_3;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_5;
	private JPanel panel_5;
	private JPanel panel_6;
	private JButton btnNewButton;
	private JScrollPane scrollPane;
	private JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VMonitor frame = new VMonitor();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VMonitor() {
		setTitle("Monitor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		lblNewLabel = new JLabel("MONITOR");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel, BorderLayout.NORTH);
		
		panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new GridLayout(2, 0, 0, 0));
		
		panel_2 = new JPanel();
		panel_1.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 2, 0, 0));
		
		lblNewLabel_1 = new JLabel("Servidor principal:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel_2.add(lblNewLabel_1);
		
		lblNewLabel_4 = new JLabel("");
		panel_2.add(lblNewLabel_4);
		
		panel_3 = new JPanel();
		panel_1.add(panel_3);
		panel_3.setLayout(new GridLayout(0, 2, 0, 0));
		
		lblNewLabel_2 = new JLabel("Servidor secundario:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel_3.add(lblNewLabel_2);
		
		lblNewLabel_5 = new JLabel("");
		panel_3.add(lblNewLabel_5);
		
		panel_5 = new JPanel();
		contentPane.add(panel_5, BorderLayout.SOUTH);
		panel_5.setLayout(new GridLayout(2, 0, 0, 0));
		
		panel_6 = new JPanel();
		panel_5.add(panel_6);
		
		btnNewButton = new JButton("Apagar");
		panel_5.add(btnNewButton);
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		DefaultCaret caret = (DefaultCaret) textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		this.setVisible(true);
	}
	
	public void setActionListener(ActionListener actionListener) {
		this.btnNewButton.addActionListener(actionListener);

	}
	
	public void settearDatos(String principal, String secundario) {
		this.lblNewLabel_4.setText(principal);
		this.lblNewLabel_5.setText(secundario);
	}
	
	public void cerrarse() {
		System.exit(0);
		this.dispose();
	}

	public void actualizarHistorial(String notificacion) {
		this.textArea.append(notificacion);
	}

	public void actualizarPrincipal(Boolean estado) {
		if (estado) 
			this.lblNewLabel_4.setText("Conectado");
		else
			this.lblNewLabel_4.setText("No hay");
	}
	
	public void actualizarSecundario(Boolean estado) {
		if (estado) 
			this.lblNewLabel_5.setText("Conectado");
		else
			this.lblNewLabel_5.setText("No hay");
	}
	
}