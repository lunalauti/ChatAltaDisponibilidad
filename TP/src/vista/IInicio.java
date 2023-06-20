package vista;

import java.awt.event.ActionListener;

public interface IInicio {

	public void setActionListener(ActionListener actionListener);

	public void settearDatos(String user);

	public String getUser();

	public String getKey();

	public boolean getModo();

	public void cerrarse();
}
