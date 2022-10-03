package gov.sir.core.eventos.administracion;

import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.CiudadanoProhibicion;
import gov.sir.core.negocio.modelo.Prohibicion;

import org.auriga.core.modelo.transferObjects.Usuario;


/**
 * Evento de Envio de solicitudes a la capa de negocio relacionadas
 * con la administración de ciudadanos.
 * @author jmendez
 */
public class EvnAdministracionCiudadano extends EvnSIR {
    /**Esta constante se utiliza  para identificar el evento de creación de una prohibición */
    public static final String PROHIBICION_CREAR = "PROHIBICION_CREAR";

    /**Esta constante se utiliza para identificar el evento de eliminación de una prohibición */
    public static final String PROHIBICION_ELIMINAR = "PROHIBICION_ELIMINAR";
    
	/**Esta constante se utiliza para identificar el evento de edición de una prohibición */
	public static final String PROHIBICION_EDITAR = "PROHIBICION_EDITAR";

    /**Esta constante se utiliza para identificar el evento de consulta de ciudadanos */
    public static final String CIUDADANO_CONSULTAR = "CIUDADANO_CONSULTAR";

    /**Esta constante se utiliza para identificar el evento de adicion de una prohibición a un ciudadano */
    public static final String CIUDADANO_PROHIBICION_ADICIONAR = "CIUDADANO_PROHIBICION_ADICIONAR";

    /**Esta constante se utiliza para identificar el evento de elmininación de una prohibición a un ciudadano */
    public static final String CIUDADANO_PROHIBICION_ELIMINAR = "CIUDADANO_PROHIBICION_ELIMINAR";

	public static final String CIUDADANO_CREAR = "CIUDADANO_CREAR";

    /** Prohibición asociada a este evento */
    private Prohibicion prohibicion;

    /** Ciudadano asociado a este evento */
    private Ciudadano ciudadano;

    /** Prohibición del ciudadano asociado a este evento */
    private CiudadanoProhibicion ciudadanoProhibicion;

    /**
     * @param usuario
     */
    public EvnAdministracionCiudadano(Usuario usuario) {
        super(usuario);
    }

    /**
     * @param usuario
     * @param tipoEvento
     */
    public EvnAdministracionCiudadano(Usuario usuario, String tipoEvento) {
        super(usuario, tipoEvento);
    }

    /**
     * Obtener el atributo ciudadano
     *
     * @return Retorna el atributo ciudadano.
     */
    public Ciudadano getCiudadano() {
        return ciudadano;
    }

    /**
     * Actualizar el valor del atributo ciudadano
     * @param ciudadano El nuevo valor del atributo ciudadano.
     */
    public void setCiudadano(Ciudadano ciudadano) {
        this.ciudadano = ciudadano;
    }

    /**
     * Obtener el atributo ciudadanoProhibicion
     *
     * @return Retorna el atributo ciudadanoProhibicion.
     */
    public CiudadanoProhibicion getCiudadanoProhibicion() {
        return ciudadanoProhibicion;
    }

    /**
     * Actualizar el valor del atributo ciudadanoProhibicion
     * @param ciudadanoProhibicion El nuevo valor del atributo ciudadanoProhibicion.
     */
    public void setCiudadanoProhibicion(
        CiudadanoProhibicion ciudadanoProhibicion) {
        this.ciudadanoProhibicion = ciudadanoProhibicion;
    }

    /**
     * Obtener el atributo prohibicion
     *
     * @return Retorna el atributo prohibicion.
     */
    public Prohibicion getProhibicion() {
        return prohibicion;
    }

    /**
     * Actualizar el valor del atributo prohibicion
     * @param prohibicion El nuevo valor del atributo prohibicion.
     */
    public void setProhibicion(Prohibicion prohibicion) {
        this.prohibicion = prohibicion;
    }
}
