package cliente.modelo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Mensaje implements Serializable {
	String userOrig;
	String cadena;
	String instruccion;
	String userDest;

	public Mensaje(String instruccion, String cadena, String userOrig, String userDest) {
		this.userOrig = userOrig;
		this.instruccion = instruccion;
		this.cadena = cadena;
		this.userDest = userDest;
	}

	@Override
	public String toString() {
		return userOrig + ": " + cadena;
	}

	public String getUserOrig() {
		return userOrig;
	}

	public String getCadena() {
		return cadena;
	}

	public String getInstruccion() {
		return instruccion;
	}

	public String getUserDest() {
		return userDest;
	}

	public void setCadena(String cadena) {
		this.cadena = cadena;
	}

}
