package servidor.modelo;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("serial")
public class Datos implements Serializable {
	private String nombre;
	private boolean disponible;

	public Datos(String nombre) {
		this.nombre = nombre;
		this.disponible = true;
	}

	public Datos(String nombre, boolean disponible) {
		this.nombre = nombre;
		this.disponible = disponible;
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

	public JSONObject getJSONObject() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("nombre", nombre);
			obj.put("disponible", disponible);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}

}
