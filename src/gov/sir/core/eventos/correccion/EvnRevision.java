package gov.sir.core.eventos.correccion;

import java.util.List;

import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.DatosAntiguoSistema;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Turno;

import org.auriga.core.modelo.transferObjects.Usuario;

/**
 * Envio de solicitudes con respecto a la fase de confrontación en el proceso de
 * correcciones, de la capa web a la capa de negocio.
 * @author ppabon, jvelez
 */
public class EvnRevision extends EvnSIR {


	public static final String AVANZAR = "AVANZAR";
	
	/**Tipo de evento para asociar un rango de matriculas*/
    public static final String ASOCIAR_UN_RANGO = "ASOCIAR_UN_RANGO";

    /**Tipo de evento que se utiliza para asociar una matrícula*/
    public static final String ASOCIAR_UNA_MATRICULA = "ASOCIAR_UNA_MATRICULA ";

    /**Tipo de evento que se utiliza para asociar una matrícula*/
    public static final String ELIMINAR_UNA_MATRICULA =
            "ELIMINAR_UNA_MATRICULA ";

    /**Tipo de envento que se utiliza para confirmar la confrontacion*/
    public static final String CONFIRMAR = "CONFIRMAR";

    /**AVANCE WORKFLOW**/

    /**Tipo de evento que se desea avanzar a correccion simple*/
    public final static String CORRECCION_SIMPLE = "CORRECCION_SIMPLE";

    /**Tipo de evento que se desea avanzar negar*/
    public final static String NEGAR = "NEGAR";

    /**Tipo de evento que se desea avanzar por éxito*/
    public static final String EXITO = "EXITO";

    /**Tipo de evento que se desea avanzar por fracaso*/
    public static final String FRACASO = "FRACASO";
    
    /**Tipo de evento para establecer permisos y avanzar el turno*/
    public static final String AVANZAR_PERMISOS = "AVANZAR_PERMISOS";


    /**
     * Constante que identifica que se desea avanzar por ANTIGUO SISTEMA
     */
    public final static String ANTIGUO_SISTEMA = "ANTIGUO_SISTEMA";

    /**
     * Constante que identifica que se desea avanzar por ACTOS ADMINISTRATIVOS
     */
    public final static String ACTUACION_ADMINISTRATIVA =
            "ACTUACION_ADMINISTRATIVA";

    /**
     * Constante que identifica que se desea avanzar por MAYOR_VALOR
     */
    public final static String MAYOR_VALOR = "MAYOR_VALOR";

    public static final String REDIRECCIONAR_CASO =
        "REDIRECCIONAR_CASO";

	public static final String REDIRECCIONAR_CASO_ACT =
		"REDIRECCIONAR_CASO_ACT";


    private Turno turno;
    private Fase fase;
    private Folio folio;
    private DatosAntiguoSistema datosAntiguoSistema;
    private List folios;
    private String idMatriculaRL;
    private String idMatriculaRR;
    private String respuestaWF;
    private List permisos;
    private gov.sir.core.negocio.modelo.Usuario usuarioSIR;


    /**
     * Evento para añadir y quitar folios
     * @param usuario
     * @param folio
     */
    public EvnRevision(Usuario usuario, String tipo, Turno turno, Folio folio,
                       gov.sir.core.negocio.modelo.Usuario usuarioNeg) {
        super(usuario, tipo);
        this.turno = turno;
        this.folio = folio;
        this.usuarioSIR = usuarioNeg;
    }

    /**
     * Evento para añadir y quitar folios
     * @param usuario
     * @param folio
     */
    public EvnRevision(Usuario usuario, String tipo, Turno turno, List folios) {
        super(usuario, tipo);
        this.turno = turno;
        this.folios = folios;
    }


    /**
     * @param usuario
     * @param string
     * @param turno
     * @param idMatRL
     * @param idMatRR
     */
    public EvnRevision(Usuario usuario, String tipo, Turno turno,
                       String idMatriculaRL, String idMatriculaRR,
                       gov.sir.core.negocio.modelo.Usuario usuarioNeg) {
        super(usuario, tipo);
        this.turno = turno;
        this.idMatriculaRL = idMatriculaRL;
        this.idMatriculaRR = idMatriculaRR;
        this.usuarioSIR = usuarioNeg;
    }


    /**
     * @param usuario
     * @param usuarioSIR
     * @param turno
     * @param fase
     * @param tipoAccion
     * @param respWF
     */
    public EvnRevision(Usuario usuario,
                       gov.sir.core.negocio.modelo.Usuario usuarioSIR,
                       Turno turno, Fase fase, String tipoAccion, String respWF) {
        super(usuario, tipoAccion);
        this.usuarioSIR = usuarioSIR;
        this.turno = turno;
        this.fase = fase;
        this.respuestaWF = respWF;
    }
    
    /**
     * @param usuario
     * @param usuarioSIR
     * @param turno
     * @param fase
     * @param tipoAccion
     * @param respWF
     */
    public EvnRevision(Usuario usuario,
                       gov.sir.core.negocio.modelo.Usuario usuarioSIR,
                       Turno turno, DatosAntiguoSistema datosAntiguoSistema,Fase fase, String tipoAccion, String respWF) {
        super(usuario, tipoAccion);
        this.usuarioSIR = usuarioSIR;
        this.turno = turno;
        this.fase = fase;
        this.respuestaWF = respWF;
        this.datosAntiguoSistema=datosAntiguoSistema;
    }
    
    /**
     * @param usuario
     * @param usuarioSIR
     * @param turno
     * @param fase
     * @param tipoAccion
     * @param respWF
     */
    public EvnRevision(Usuario usuario,
                       gov.sir.core.negocio.modelo.Usuario usuarioSIR,
                       Turno turno, Fase fase, List permisos, String tipoAccion, String respWF) {
        super(usuario, tipoAccion);
        this.usuarioSIR = usuarioSIR;
        this.turno = turno;
        this.fase = fase;
        this.respuestaWF = respWF;
        this.permisos=permisos;
    }


    /**
     * @return
     */
    public Folio getFolio() {
        return folio;
    }

    /**
     * @return
     */
    public Turno getTurno() {
        return turno;
    }

    /**
     * @return
     */
    public Fase getFase() {
        return fase;
    }

    /**
     * @return
     */
    public String getIdMatriculaRL() {
        return idMatriculaRL;
    }

    /**
     * @return
     */
    public String getIdMatriculaRR() {
        return idMatriculaRR;
    }

    /**
     * @return
     */
    public String getRespuestaWF() {
        return respuestaWF;
    }

    /**
     * @return
     */
    public List getFolios() {
        return folios;
    }

    /**
     * @return
     */
    public gov.sir.core.negocio.modelo.Usuario getUsuarioSIR() {
        return usuarioSIR;
    }

    public List getPermisos() {
        return permisos;
    }

    public DatosAntiguoSistema getDatosAntiguoSistema() {
        return datosAntiguoSistema;
    }


}
