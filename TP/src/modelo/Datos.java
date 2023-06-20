package modelo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Datos implements Serializable {
	private String nombre;
	private boolean disponible;

	public Datos(String nombre) {
		this.nombre = nombre;
		this.disponible = true;
	}

	public String getNombre() {
		return nombre;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

}
