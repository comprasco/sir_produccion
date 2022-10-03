package gov.sir.core.negocio.modelo;

import java.util.Date;
import org.auriga.core.modelo.TransferObject;

/**
 * Esta clase identifica al usuario remoto que hace una solicitud web
 * a la aplicación
 * @author eacosta, dcantor
 */
public class InfoUsuario implements TransferObject{
    private static final long serialVersionUID = 1L;
	/**
	 * Dirección IP del equipo remoto utilizado
	 */
	private String address;
	/**
	 * Nombre del equipo remoto empleado
	 */
	private String host;
	/**
	 * Nombre del usuario
	 */
	private String user;
	/**
	 * identificador unico del usuario
	 */
	private String uid;
        
        private Date logonTime;

	/**
	 * Constructor del objeto que inicializa los atributos privados: address, host y  user.
	 * @param address direccion remota del usuario
	 * @param host host del usuario
	 * @param user nombre del usuario
	 * @param uid identificador unico del usuario.
	 */
	public InfoUsuario(String address, String host, String user, String uid) {
		super();
		this.address = address;
		this.host = host;
		this.user = user;
		this.uid = uid; 
	}

	/** Retorna la direccion remota del usuario
	 * @return address
	 */
	public String getAddress() {
		return address;
	}

	/** Rertorna el host del usuario
	 * @return host
	 */
	public String getHost() {
		return host;
	}

	/** Retorna el nombre del usuario
	 * @return usuario
	 */
	public String getUser() {
		return user;
	}
	
	/** Retorna el identificador unico del usuario
	 * @return identificador del usuario
	 */
	public String getUID(){
		return uid;
	}

    public Date getLogonTime() {
        return logonTime;
    }

    public void setLogonTime(Date logonTime) {
        this.logonTime = logonTime;
    }

}
