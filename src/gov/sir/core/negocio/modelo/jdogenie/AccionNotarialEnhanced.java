package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.AccionNotarial;



/**
 * Clase que representa los tipos de contrato que se celebran entre las dos entidades 
 * relacionadas en una minuta, la clase es usada para dar persistencia a trav�s de JDO
 * @author fceballos
 */
public class AccionNotarialEnhanced  extends Enhanced{

	/**
	 * Identificador de la acci�n notarial
	 */
	private String idAccionNotarial; // pk 
    
	/**
	 * Indica si las minutas asociadas a esta acci�n notarial requieren cuant�a
	 */
	private boolean cuantia;
    
	/**
	 * Nombre de la acci�n notarial
	 */
	private String nombre;
    
	/**
	 * Tipo de acci�n notarial
	 */
	private String tipo;
        /**
        * Indica si esta activo
        */
        private boolean activo;
	
	/**
	 * Usuario que modifica la clase
	 */
	private UsuarioEnhanced usuario;

	/**
	 * Retorna el identificador de la acci�n notarial
	 * @return
	 */
	public String getIdAccionNotarial() {
		return idAccionNotarial;
	}

	/**
	 * Cambia el identificador de la acci�n notarial
	 * @param idAccionNotarial
	 */
	public void setIdAccionNotarial(String idAccionNotarial) {
		this.idAccionNotarial = idAccionNotarial;
	}

	/**
	 * Retorna el atributo que indica si las minutas asociadas 
	 * a esta acci�n notarial requieren cuant�a
	 * @return
	 */
	public boolean isCuantia() {
		return cuantia;
	}

	/**
	 * Cambia el atributo que indica si las minutas asociadas 
	 * a esta acci�n notarial requieren cuant�a
	 * @param cuantia
	 */
	public void setCuantia(boolean cuantia) {
		this.cuantia = cuantia;
	}

	/**
	 * Retorna el nombre de la acci�n notarial
	 * @return
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Cambia el nombre de la acci�n notarial
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Retorna el tipo de acci�n notarial
	 * @return
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Cambia el tipo de acci�n notarial
	 * @param tipo
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	/**
	 * Retorna si al canal esta activo
	 * @return antivo
	 */
        public boolean isActivo() {
            return activo;
        }

        /**
         * Cambia el estado de accion notarial
         * @param activo
         */
        public void setActivo(boolean activo) {
            this.activo = activo;
        }
	public UsuarioEnhanced getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioEnhanced usuario) {
		this.usuario = usuario;
	}
        
	public static AccionNotarialEnhanced enhance(AccionNotarial acto) {
		return (AccionNotarialEnhanced) Enhanced.enhance(acto);
	}
}