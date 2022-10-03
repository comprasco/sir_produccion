/*
 * Created on 05-may-2005
 *
 */
package gov.sir.core.eventos.comun;

import gov.sir.core.negocio.modelo.Impresion;
import gov.sir.core.negocio.modelo.Imprimible;

import java.util.List;
import java.util.Map;

import org.auriga.core.modelo.transferObjects.Usuario;


/**
 * Este evento envia la información de impresoras recibida por la capa web del 
 * servidorde alguna de las oficinas (ver AWImpresion), hacia la capa de negocio.
 * @see gov.sir.core.web.acciones.AWImpresion
 * @author dcantor
 */
public class EvnImpresorasCirculo extends EvnSIR {
	
	public final static String REGISTRAR_CLIENTE = "REGISTRAR_CLIENTE";
	public final static String ELIMINAR_CLIENTE = "ELIMINAR_CLIENTE";
	public final static String REGISTRAR_IMPRESORAS_CIRCULO = "REGISTRAR_IMPRESORAS_CIRCULO";
	public final static String DESCARGAR_IMPRIMIBLE = "DESCARGAR_IMPRIMIBLE";
	public final static String CONSULTAR_IMPRESIONES_FALLIDAS = "CONSULTAR_IMPRESIONES_FALLIDAS";
	public final static String DISMINUIR_NUMERO_IMPRESIONES = "DISMINUIR_NUMERO_IMPRESIONES";		
	public final static String CONSULTAR_LISTA_REGLAS="CONSULTAR_LISTA_REGLAS";
	public final static String ACTUALIZAR_LISTA_IMPRESORAS="ACTUALIZAR_LISTA_IMPRESORAS";
	public final static String CARGAR_CONFIGURACION_ACTUAL="CARGAR_CONFIGURACION_ACTUAL";
	public final static String CARGAR_PARAMETROS_CONFIGURACION="CARGAR_PARAMETROS_CONFIGURACION";
	
	
	private String idCirculo;
	private List impresoras;
	private Impresion impresion;
	private Imprimible imprimible;
	private Map configuracion;
	private boolean isAdministrador = false;
	private gov.sir.core.negocio.modelo.Usuario usuarioSIR = null;

	/**
	 * Guarda la información de los datos de la clase Impresion, con
	 * el fin de guardar la dirección ip, puerto y UID del cliente, en la base de datos.
	 * @param usuario el usuario de infraestructura (auriga) que puede generar este evento
	 * @param impresion el identificador del circulo registral
 	 * @param tipoEvento determina la acciónque debe ejecutar la acción de negocio.
	 */
	public EvnImpresorasCirculo(Usuario usuario, String tipoEvento, String circulo) {
		super(usuario, tipoEvento);
		this.idCirculo=circulo;
	}    
	
	public EvnImpresorasCirculo(Usuario usuario, String tipoEvento, String circulo, Map configuracion) {
		super(usuario, tipoEvento);
		this.idCirculo=circulo;
		this.configuracion=configuracion;
	}  
	
    /**
     * Crea un nuevo evento del tipo EvnImpresorasCirculo
     * @param usuario el usuario de infraestructura (auriga) que puede generar este evento
     * @param idCirculo el identificador del circulo registral
     * @param impresoras la lista de impresoras (Strings)
     * @param tipoEvento determina la acciónque debe ejecutar la acción de negocio. 
     */
    public EvnImpresorasCirculo(Usuario usuario, String idCirculo, List impresoras, String tipoEvento) {
        super(usuario, tipoEvento);
        this.idCirculo = idCirculo;
        this.impresoras = impresoras;
    }
    
	/**
	 * Guarda la información de los datos de la clase Impresion, con
	 * el fin de guardar la dirección ip, puerto y UID del cliente, en la base de datos.
	 * @param usuario el usuario de infraestructura (auriga) que puede generar este evento
	 * @param impresion el identificador del circulo registral
 	 * @param tipoEvento determina la acciónque debe ejecutar la acción de negocio.
	 */
	public EvnImpresorasCirculo(Usuario usuario, Impresion impresion, String tipoEvento, gov.sir.core.negocio.modelo.Usuario usuarioSIR) {
		super(usuario, tipoEvento);
		this.impresion = impresion;
		this.usuarioSIR = usuarioSIR;
	}    	
	
	/**
	 * Guarda la información de los datos de la clase Impresion, con
	 * el fin de guardar la dirección ip, puerto y UID del cliente, en la base de datos.
	 * @param usuario el usuario de infraestructura (auriga) que puede generar este evento
	 * @param impresion el identificador del circulo registral
 	 * @param tipoEvento determina la acciónque debe ejecutar la acción de negocio.
	 */
	public EvnImpresorasCirculo(Usuario usuario, Impresion impresion, String tipoEvento) {
		super(usuario, tipoEvento);
		this.impresion = impresion;
	}    	
	
	/**
	 * Guarda la información de un imprimible para consultarlo y devolverlo.
	 * @param usuario el usuario de infraestructura (auriga) que puede generar este evento
	 * @param impresion el identificador del imprimible
	 * @param tipoEvento determina la acciónque debe ejecutar la acción de negocio.
	 */
	public EvnImpresorasCirculo(Usuario usuario, Imprimible imprimible, String tipoEvento, boolean isAdministrador) {
		super(usuario, tipoEvento);
		this.imprimible = imprimible;
		this.isAdministrador = isAdministrador;
	}    		
	
    
    /**
	 * Guarda la información de un imprimible para consultarlo y devolverlo.
	 * @param usuario el usuario de infraestructura (auriga) que puede generar este evento
	 * @param impresion el identificador del imprimible
	 * @param tipoEvento determina la acciónque debe ejecutar la acción de negocio.
	 */
	public EvnImpresorasCirculo(Usuario usuario, Imprimible imprimible, String tipoEvento) {
		super(usuario, tipoEvento);
		this.imprimible = imprimible;
	}    	

    
    /**
     * @return el identificador de circulo registral asociado a este evento
     */
    public String getIdCirculo() {
        return idCirculo;
    }

    /**
     * @return la lista de impresoras (Strings)
     */
    public List getImpresoras() {
        return impresoras;
    }
	/**
	 * @return
	 */
	public Impresion getImpresion() {
		return impresion;
	}

	/**
	 * @return
	 */
	public Imprimible getImprimible() {
		return imprimible;
	}

	/**
	 * @param imprimible
	 */
	public void setImprimible(Imprimible imprimible) {
		this.imprimible = imprimible;
	}
	
	/**
	 * @return Returns the configuracion.
	 */
	public Map getConfiguracion() {
		return configuracion;
	}

	public boolean isAdministrador() {
		return isAdministrador;
	}

	public gov.sir.core.negocio.modelo.Usuario getUsuarioSIR() {
		return usuarioSIR;
	}

}