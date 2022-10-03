/*
 * Sender.java
 *
 * Created on 27 de agosto de 2004, 11:33
 */
package gov.sir.print.server;

import gov.sir.core.negocio.modelo.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 *
 * @author  dcantor
 */
class Sender {

	private Socket socket;
	private static String caracterEspecialInicio = "/";
	private static String caracterEspecialFin = "*";    
    

    /**
     * @param cliente
     * @param bundle
     */
    public Sender(Socket socket) {
        this.socket = socket;
    }
    
    
    
	public void enviarIdentificador(String id) throws Throwable {
	
		boolean CHECKSUM_RECONOCIDO = false;		
		String ipRemota = socket.getRemoteSocketAddress().toString();
		String caracterEspecialInicio = "/";
		String caracterEspecialFin = "*";

		Log.getInstance().debug(this.getClass(),"DIRECCION REMOTA:" +socket.getInetAddress().getHostName() + " " + socket.getRemoteSocketAddress().toString() +" "+socket.getPort());
		Log.getInstance().debug(this.getClass(),"DIRECCION LOCAL:" +""+socket.getLocalSocketAddress().toString() +" "+socket.getLocalAddress().getHostName() +" "+socket.getLocalPort());
			
		DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
		DataInputStream dis = new DataInputStream(socket.getInputStream());
	        
		int intentosEnvio = 1;
			
		while (!(CHECKSUM_RECONOCIDO) && intentosEnvio<4) {
	        	
			Log.getInstance().debug(this.getClass(),"1. (SOCKET ENVIO) : " + ipRemota + " : Intentos : "+  intentosEnvio);
			Log.getInstance().debug(this.getClass(),"1. (SOCKET ENVIO) : " + ipRemota + " : idImprimible : "+ id );
			intentosEnvio++;
			
			String tamanoImprimible = caracterEspecialInicio + id + caracterEspecialFin;
			dos.writeChars(tamanoImprimible);
			dos.flush();
	            
			//RECONOCER EL CHECKSUM
			CHECKSUM_RECONOCIDO = dis.readBoolean();
				            
			Log.getInstance().debug(this.getClass(),"2. (SOCKET ENVIO) : " + ipRemota + " : Checksum reconocido : "+ CHECKSUM_RECONOCIDO);
				
			if (!CHECKSUM_RECONOCIDO) {
				continue;
			}
			            
		}

		if (!CHECKSUM_RECONOCIDO) {
			Log.getInstance().debug(this.getClass(),"3. (SOCKET ENVIO) : " + ipRemota + " : Checksum reconocido definitivo: "+ CHECKSUM_RECONOCIDO);
			Log.getInstance().debug(this.getClass(),"3. FIN DEL PROTOCOLO");
			throw new PrintingException("La comunicación con el cliente " + socket.getInetAddress().getHostName() +" ha fallado. Consecutivo de impresión es :" + id);		
		}
	        
	}    
    
}
