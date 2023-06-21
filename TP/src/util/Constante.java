package util;

public abstract class Constante {
	// -----------------IP Y PUERTOS-----------------//
	public static final String IP_SERVIDOR = "localhost";
	public static int PUERTO_PRINCIPAL = 5000;
	public static int PUERTO_SECUNDARIO = 5001;
	public static int PUERTO_SINCRONIZACION = 5002;
	public static int PUERTO_MONITOR_CLIENTE = 5003;
	public static int PUERTO_MONITOR = 5004;

	// -----------------COMANDOS-----------------//
	public static final String COMANDO_FIN = "/fin/"; // para finalizar conexion cliente-servidor
	public static final String COMANDO_DESCONECTAR = "/desconectar/";// para desconectarse del chat
	public static final String COMANDO_CONECTAR = "/conectar/";
	public static final String COMANDO_ENVIAR = "/enviar/";
	public static final String COMANDO_ACEPTAR = "/aceptar/";
	public static final String COMANDO_RECHAZAR = "/rechazar/";
	public static final String COMANDO_MODO_ESCUCHA = "/modoescucha/";
	public static final String COMANDO_CAMBIAR_SERVER = "/cambiarserver/";
	public static final String FIN_CADENA = "/fin/";

	// -----------------INDICES-----------------//
	public static final int INSTRUCCION = 0;
	public static final int DESTINATARIO = 1;
	public static final int CLAVE = 2;
	public static final int MENSAJE = 2;

	// -----------------BOTONES-----------------//
	public static final String BOTON_INICIAR = "/iniciar/";
	public static final String BOTON_INICIAR_SESION = "/iniciarsesion/";
	public static final String BOTON_CERRAR_SESION = "/cerrarsesion/";
	public static final String BOTON_MODO_ESCUCHA = "/modoescucha/";
	public static final String BOTON_ENVIAR = "/enviar/";
	public static final String BOTON_CERRAR_CHAT = "/cerrarchat/";
	public static final String BOTON_APAGAR = "/apagar/";

}
