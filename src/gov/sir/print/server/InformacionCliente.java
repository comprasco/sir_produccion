/*
 * Created on 03-abr-2005
 *
 */
package gov.sir.print.server;

import java.io.Serializable;
import java.net.Socket;

/**
 * @author Dcantor
 *
 */
public class InformacionCliente implements Serializable{
	private String uid;
	private String host;
	private int listeningPort;
	//private boolean isAdmin =false;
	private transient Socket currentConnection;
	
	public InformacionCliente (String uid, String host, int listeningPort, Socket s){
		this.uid = uid;
		this.host = host;
		this.listeningPort = listeningPort;
		this.currentConnection = s;
	}
    /**
     * @return
     */
    public Socket getCurrentConnection() {
        return currentConnection;
    }

    /**
     * @return
     */
    public String getHost() {
        return host;
    }

    /**
     * @return
     */
    public int getListeningPort() {
        return listeningPort;
    }

    /**
     * @return
     */
    public String getUID() {
        return uid;
    }

    /**
     * @param socket
     */
    public void setCurrentConnection(Socket socket) {
        currentConnection = socket;
    }

    /**
     * @param string
     */
    public void setHost(String string) {
        host = string;
    }

    /**
     * @param i
     */
    public void setListeningPort(int i) {
        listeningPort = i;
    }

    /**
     * @param string
     */
    public void setUID(String string) {
        uid = string;
    }
    
    /*ublic void setAdmin(boolean isAdmin){
    	this.isAdmin = isAdmin;
    }
    
    public boolean isAdmin(){
    	return isAdmin;
    }*/
    
    public String toString(){
    	
    	return "Cliente de Impresion : [host="+host+
		", listeningPort="+listeningPort+
		", UID="+uid+
		", socket = "+(currentConnection!=null? currentConnection.toString():" ES NULO")+"]";
    }

}
