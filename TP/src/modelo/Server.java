package modelo;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;

public class Server extends Observable {
	private Socket monitor;
	private ServerSocket server = null;
	private Socket servidorAuxiliar;
	private int idServer;
	private ArrayList<String> historial = new ArrayList<String>();

//	private HashMap<String, Connection> connections = new HashMap<String, Connection>();
//	private ArrayList<Datos> datos = new ArrayList<Datos>();
//
//	public void iniciaServer() throws BindException, IOException, NumberFormatException {
//		this.monitor = new Socket(Constante.IP, Constante.PUERTO_MONITOR);
//		setID();
//		int serverPort = (idServer == 1) ? Constante.PUERTO_PRINCIPAL : Constante.PUERTO_SECUNDARIO;
//		this.server = new ServerSocket(serverPort);
//		if (this.idServer == 2) {
//			recibirActualizacion();
//			escucharMonitor();
//		} else
//			heartbeat();
//	}
//
//	public void escucharMonitor() {
//		new Thread(() -> {
//			try {
//				BufferedReader input = new BufferedReader(new InputStreamReader(monitor.getInputStream()));
//				String cadena = input.readLine();
//				if (cadena.equalsIgnoreCase("switch")) {
//					cambiarServer();
//					heartbeat();
//				}
//			} catch (IOException e) {
//
//			}
//		}).start();
//	}
//
//	public void cambiarServer() {
//		for (Datos dato : datos) {
//			try {
//				Socket socket = new Socket(dato.getIp(), dato.getPuerto());
//				PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
//				output.println("switch");
//				connections.put(dato.getNombre(), new Connection(dato.getNombre(), socket));
//			} catch (IOException e) {
//
//			}
//		}
//	}
//
//	// mientras que el server este abierto, recibe conexiones de usuarios y las
//	// almacena
//	public void setConnections() {
//		while (!this.server.isClosed()) {
//			try {
//				Socket socket = this.server.accept();
//				BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//
//				String user = input.readLine();
//				this.connections.put(user, new Connection(user, socket));
//				this.datos.add(new Datos(socket.getInetAddress().getHostAddress(), user, socket.getLocalPort()));
//				setChanged();
//				notifyObservers();
//				resincronizarEstado();
//				recibirMensaje(user);
//			} catch (IOException e) {
//				if (!this.server.isClosed())
//					e.printStackTrace();
//			}
//		}
//	}
//
//	public void recibirMensaje(String userOrigin) {
//		new Thread(() -> {
//			Socket socket = this.connections.get(userOrigin).getSocket();
//			try {
//				BufferedReader inOrigin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//				PrintWriter outOrigin = new PrintWriter(socket.getOutputStream(), true);
//
//				while (!socket.isClosed()) {
//
//					String[] data = inOrigin.readLine().split(";");
//					String instr = data[0];
//
//					if (data.length > 1) {
//						String userDest = data[1];
//						PrintWriter outDest = new PrintWriter(
//								this.connections.get(userDest).getSocket().getOutputStream(), true);
//
//						if (instr.equalsIgnoreCase("CONNECT")) {
//							String key = data[2];
//							if (this.connections.containsKey(userDest)
//									&& this.connections.get(userDest).isAvailable()) {
//								outDest.println("CONNECT" + ";" + userOrigin + ";" + key);
//							} else
//								outOrigin.println("DECLINE");
//
//						} else if (instr.equalsIgnoreCase("SEND")) {
//							outDest.println(data[2]); // envia el texto
//							this.historial.add(userOrigin + " le mando un mensaje a " + userDest + "\n");
//
//						} else if (instr.equalsIgnoreCase("ACCEPT")) {
//							outDest.println("ACCEPT");
//							this.connections.get(userOrigin).setAvailable(false);
//							this.connections.get(userDest).setAvailable(false);
//							this.historial.add(userOrigin + " se conecto con " + userDest + "\n");
//
//						} else if (instr.equalsIgnoreCase("DECLINE")) {
//							outDest.println("DECLINE");
//
//						} else if (instr.equalsIgnoreCase("END")) {
//							outDest.println("END");
//							this.connections.get(userOrigin).setAvailable(true);
//							this.connections.get(userDest).setAvailable(true);
//						}
//					}
//					if (instr.equalsIgnoreCase("CLOSE")) {
//						socket.close();
//
//					} else if (instr.equalsIgnoreCase("DESHABILITAR")) {
//						this.connections.get(userOrigin).setAvailable(false);
//
//					} else if (instr.equalsIgnoreCase("HABILITAR")) {
//						this.connections.get(userOrigin).setAvailable(true);
//					}
//					setChanged();
//					notifyObservers();
//				}
//				this.connections.remove(userOrigin);
//				resincronizarEstado();
//				setChanged();
//				notifyObservers();
//			} catch (IOException e) {
//
//			}
//		}).start();
//	}
//
//	public ServerSocket getServer() {
//		return server;
//	}
//
//	public HashMap<String, Connection> getConnections() {
//		return connections;
//	}
//
//	public void closeServer() {
//		try {
//			this.server.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public void heartbeat() {
//		new Thread(() -> {
//			try {
//				Socket socket;
//				while (!this.server.isClosed()) {
//					socket = new Socket(Constante.IP, Constante.PUERTO_HEARTBEAT);
//					PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
//					output.println(this.idServer);
//					output.flush();
//					Thread.sleep(5000);
//				}
//			} catch (UnknownHostException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}).start();
//	}
//
//	public void setID() {
//		try {
//			BufferedReader input = new BufferedReader(new InputStreamReader(monitor.getInputStream()));
//			this.idServer = Integer.parseInt(input.readLine());
//		} catch (NumberFormatException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public void resincronizarEstado() {
//		try {
//			this.servidorAuxiliar = new Socket(Constante.IP, Constante.PUERTO_SINCRONIZACION);
//			ObjectOutputStream output = new ObjectOutputStream(this.servidorAuxiliar.getOutputStream());
//			output.writeObject(datos);
//
//			output.close();
//			servidorAuxiliar.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	public void recibirActualizacion() {
//		new Thread(() -> {
//			try {
//				while (true) {
//					ServerSocket socketServidores = new ServerSocket(Constante.PUERTO_SINCRONIZACION);
//					this.servidorAuxiliar = socketServidores.accept();
//					ObjectInputStream input = new ObjectInputStream(servidorAuxiliar.getInputStream());
//					this.datos = (ArrayList<Datos>) input.readObject();
//					setChanged();
//					notifyObservers();
//					input.close();
//					socketServidores.close();
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			} catch (ClassNotFoundException e) {
//				e.printStackTrace();
//			}
//		}).start();
//	}
//
//	public ArrayList<Datos> getDatos() {
//		return datos;
//	}
//
//	public ArrayList<String> getHistorial() {
//		return historial;
//	}

}
