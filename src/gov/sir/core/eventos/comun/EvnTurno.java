package gov.sir.core.eventos.comun;

import java.util.List;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.core.modelo.transferObjects.Usuario;

import gov.sir.core.eventos.TieneNotaInformativa;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Turno;

/**
 * Envio de solicitudes con respecto a turnos, a la capa de negocio
 * @author mmunoz
 */
public class EvnTurno extends EvnSIR implements TieneNotaInformativa {
   
    /**
     * Constante que identifica que se desea crear un turno para
     * alguno de los procesos
     */
    public static final String CREAR = "CREAR";
    
	/**
	 * Constante que identifica que se desea obtener los datos para iniciar una fase
	 * entre ellos la lista de notas informativas.
	 */
	public static final String FASE_INICIAL = "FASE_INICIAL";

    /**
     * Constante que identifica que se desea listar el conjunto de
     * turnos que se encuentran en una fase de un proceso
     */
    public static final String LISTAR = "LISTAR";

    /**
     * Constante que identifica que se desea consultar los detalles del
     * turno, para el proceso que corresponda tendrá diferentes atributos
     */
    public static final String CONSULTAR = "CONSULTAR";
    
    /**
     * Constante que identifica que se desea consultar los detalles del
     * turno, para el proceso que corresponda tendrá diferentes atributos
     */
    public static final String ENTREGA_MASIVA_REPARTO_NOTARIAL = "ENTREGA_MASIVA_REPARTO_NOTARIAL";
    
	/**
	 * Constante que identifica que se desea consultar los detalles del
	 * turno, para el proceso que corresponda tendrá diferentes atributos
	 */
	public static final String CONSULTAR_CONFIRMACION = "CONSULTAR_CONFIRMACION";
	
	public static final String CONSULTAR_RELACION = "CONSULTAR_RELACION";
	
	/**
	 * Constante que identifica que se desea obtener la instancia de IListadoPorRangos
	 * que itera sobre el listado de turnos en ejecución
	 */
	public static final String OBTENER_LISTADO_TURNOS_EJECUCION = "LISTAR_TURNOS_ENTREGA_REG";
    

    /**Turno al cual se le va a consultar la informacion*/
    private Turno turno;
    
    /**Fase a la cual se le quiere listar los turno*/
    private Fase fase;
    
    /**Proceso para poder listar los turno*/
    private Proceso proceso;
    
    /**Estacion para poder listar los turnos*/
    private Estacion estacion;
    
    /**Rol par poder listar los turno*/
    private Rol rol;
    
    private Nota nota;
    
    /**Usuario del Negocio*/
	private gov.sir.core.negocio.modelo.Usuario usuarioNeg;    
	
	/**
    * Identificador workflow del turno
	*/
	private String idTurno;
	
    /**Turno al cual se le va a consultar la informacion*/
    private List turnos;
    
	/**
	* Identificador del Relación
	*/
	private String idRelacion;
	
	/**
	* Identificador para el circulo
	*/	
	private Circulo circulo;   
	
	private boolean validarIndividual = false;
	
    
    /**
     * Usado para crear turnos y consultar
     * CREAR
     * CONSULTAR
     * @param turno el turno a crear o consultar
     * @param usuario el usuario que genera este evento
     * @param tipoEvento el tipo de evento. Puede ser <CODE>CREAR</CODE> o <CODE>CONSULTAR</CODE> (Ver constantes)
     */
    public EvnTurno(Usuario usuario, String tipoEvento, Turno turno, gov.sir.core.negocio.modelo.Usuario usuarioNeg) {
        super(usuario, tipoEvento);
        this.turno = turno;
        this.usuarioNeg = usuarioNeg;
    }
    
    /**
     * Usado para crear turnos y consultar
     * CREAR
     * CONSULTAR
     * @param turno el turno a crear o consultar
     * @param usuario el usuario que genera este evento
     * @param tipoEvento el tipo de evento. Puede ser <CODE>CREAR</CODE> o <CODE>CONSULTAR</CODE> (Ver constantes)
     */
    public EvnTurno(Usuario usuario, String tipoEvento, List turnos, gov.sir.core.negocio.modelo.Usuario usuarioNeg) {
        super(usuario, tipoEvento);
        this.turnos = turnos;
        this.usuarioNeg = usuarioNeg;
    }

    /**
     * Usado para solicitar la lista de turnos que se encuentran en una fase
     * particular de un proceso en particular
     * LISTAR
     * @param usuario el usuario que genera este evento
     * @param tipoEvento el tipo de evento
     * @param fase la fase que se va a consultar
     */
    public EvnTurno(Usuario usuario, String tipoEvento, Fase fase, Proceso proceso,Estacion estacion, Rol rol, Circulo circulo) {
        super(usuario, tipoEvento);
        this.fase = fase;
        this.proceso = proceso;
        this.estacion = estacion;
        this.rol = rol;
        this.circulo=circulo;
    }
    
	/**
	 * Usado para solicitar la lista de turnos que se encuentran en una fase
	 * particular de un proceso en particular
	 * LISTAR
	 * @param usuario el usuario que genera este evento
	 * @param tipoEvento el tipo de evento
	 * @param fase la fase que se va a consultar
	 */
	public EvnTurno(Usuario usuario, String tipoEvento, Proceso proceso,Estacion estacion, Rol rol) {
		super(usuario, tipoEvento);
		this.proceso = proceso;
		this.estacion = estacion;
		this.rol = rol;
	}
	
	
	/**
	 * Usado para recuperar la información completa de un turno. 
	 * @param identificador workflow del turno.
	 * @param accion, acción solicitada.
	 */
	public EvnTurno(String idWorkflow, String accion) {
		super (null, accion);
		this.idTurno = idWorkflow;
	}



    /**
     * Usado para generar eventos que requieren una nota informativa
     * asociada
     * ENTREGAR
     * CANCELAR
     * @param turno el turno seleccionado
     * @param nota la nota informativa
     * @param usuario el usuario que genera este evento
     * @param tipoEvento el tipo de evento
     */
    public EvnTurno(Usuario usuario, String tipoEvento, Turno turno,
        Nota nota) {
        super(usuario, tipoEvento);
        this.turno = turno;
        this.nota = nota;
    }
    
	/**
	 * Usado para inicializar el evento únicamente con la acción a realizar. 
	 * @param accion, acción solicitada.
	 */
	public EvnTurno(String accion) {
		super (null, accion);
	}
    
    
    /**
     * Devuelve la nota informativa
     * @return la nota informativa
     */
    public Nota getNotaInf() {
        return nota;
    }

    /**
     * Devuelve la fase
     * @return la fase
     */
    public Fase getFase() {
        return fase;
    }

    /**
     * Devuelve el proceso
     * @return el proceso
     */
    public Proceso getProceso() {
        return proceso;
    }
    
	/**
	 * Retorna la estacion
	 * @return Estacion
	 */
	public Estacion getEstacion() {
		return estacion;
	}

	public void setEstacion(Estacion estacion) {
		this.estacion = estacion;
	}
	
	/**
	 * Retorna el rol
	 * @return Rol
	 */
	public Rol getRol() {
		return rol;
	}
	/**
	 * Retorna el turno que esta en el evento
	 * @return Turno
	 */
	public Turno getTurno() {
		return turno;
	}

	/**
	 * @return
	 */
	public gov.sir.core.negocio.modelo.Usuario getUsuarioNeg() {
		return usuarioNeg;
	}

	/**
	 * @param usuario
	 */
	public void setUsuarioNeg(gov.sir.core.negocio.modelo.Usuario usuario) {
		usuarioNeg = usuario;
	}

	/**
	 * @return
	 */
	public String getIdTurno() {
		return idTurno;
	}

	/**
	 * @param string
	 */
	public void setIdTurno(String string) {
		idTurno = string;
	}

	public List getTurnos() {
		return turnos;
	}

	public void setTurnos(List turnos) {
		this.turnos = turnos;
	}
	
	/**
	 * @param string
	 */
	public void setIdRelacion(String string) {
		idRelacion = string;
	}

	/**
	 * @param fase
	 */
	public void setFase(Fase fase) {
		this.fase = fase;
	}

	/**
	 * @param proceso
	 */
	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
	}

	/**
	 * @return
	 */
	public Circulo getCirculo() {
		return circulo;
	}

	/**
	 * @param circulo
	 */
	public void setCirculo(Circulo circulo) {
		this.circulo = circulo;
	}

	public String getIdRelacion() {
		return idRelacion;
	}

	public boolean isValidarIndividual() {
		return validarIndividual;
	}

	public void setValidarIndividual(boolean validarIndividual) {
		this.validarIndividual = validarIndividual;
	}	

}
