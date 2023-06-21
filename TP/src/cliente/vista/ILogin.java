package cliente.vista;

import java.awt.event.ActionListener;

public interface ILogin {
	public void setActionListener(ActionListener actionListener);

	public String getNombre();

	public Integer getPuerto();

	public void cerrarse();

	String getKey();
}
