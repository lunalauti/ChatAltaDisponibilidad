package modelo;

import java.util.ArrayList;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import ejecutable.Cliente;
import util.Constante;

public class Chat {

	private Cliente userOrig;
	private String userDest;
	private String key;
	private ArrayList<Mensaje> mensajes = new ArrayList<Mensaje>();

	public Chat(Cliente userOrig, String userDest, String key) {
		this.userOrig = userOrig;
		this.userDest = userDest;
		this.key = key;
	}

	public void enviarMensaje(String mensaje) {
		byte[] textoEncriptado;
		try {
			textoEncriptado = encoder(this.key, mensaje, "DES");
			String textoEncriptadoBase64 = Base64.getEncoder().encodeToString(textoEncriptado);
			this.userOrig.enviarMensajePrivado(textoEncriptadoBase64, userDest);
			mensajes.add(new Mensaje(Constante.COMANDO_ENVIAR, mensaje, this.userOrig.getUser(), this.userDest));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void recibirMensaje(String cadena) {
		try {
			byte[] textoEncriptado = Base64.getDecoder().decode(cadena);
			String textoOriginal = decoder(this.key, textoEncriptado, "DES");
			mensajes.add(new Mensaje(Constante.COMANDO_ENVIAR, textoOriginal, this.userDest, this.userOrig.getUser()));
		} catch (Exception e) {

		}
	}

	public void desconectarChat() {
		this.userOrig.desconectarChat(userDest);
	}

	// ------------------METODOS AUXILIARES--------------------//
	public static byte[] encoder(String pass, String texto, String algoritmo) throws Exception {
		java.security.Key key = new SecretKeySpec(pass.getBytes(), algoritmo);
		Cipher cipher = Cipher.getInstance(algoritmo);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(texto.getBytes());
	}

	public static String decoder(String pass, byte[] encriptado, String algoritmo) throws Exception {
		java.security.Key key = new SecretKeySpec(pass.getBytes(), algoritmo);
		Cipher cipher = Cipher.getInstance(algoritmo);
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] bytes = cipher.doFinal(encriptado);
		return new String(bytes);
	}

	// ------------------GETTERS--------------------//
	public Cliente getUserOrig() {
		return userOrig;
	}

	public String getUserDest() {
		return userDest;
	}

	public ArrayList<Mensaje> getMensajes() {
		return mensajes;
	}

}