package gov.sir.print.client;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * @author gardila
 * 
 */
public class ListenerCerrarVentana extends WindowAdapter implements Runnable {

	private Receiver receiver;

	private JFrame frame;
	
	private int minutos = 5;

	public void run() {
		while(true){
			try {
				long tiempo = this.minutos * 60 * 1000;
				System.out.println("running: " + tiempo);
				Thread.sleep(tiempo);
				System.out.println("after running");
				this.cerrar();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 
	 */
	public ListenerCerrarVentana(Receiver receiver, JFrame frame, int minutos) {
		this.receiver = receiver;
		this.frame = frame;
		this.minutos = minutos;
	}

	public void windowClosing(WindowEvent evt) {
		this.cerrar();
	}

	public void cerrar() {
		try {
			boolean sesion = this.receiver.verificarSesionUsuario();

			if (!sesion) {
				System.out.println("siiii");
				frame.dispose();
				System.exit(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane
					.showOptionDialog(
							frame,
							"Ocurrió un error al conectarse con el servidor. "
									+ "Por favor vuelva a abir el cliente de impresión",
							"Error", JOptionPane.OK_OPTION,
							JOptionPane.ERROR_MESSAGE, null, null, null);
			frame.dispose();
			System.exit(0);
		}
	}
}
