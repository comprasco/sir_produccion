package gov.sir.core.eventos.administracion;

import gov.sir.core.eventos.comun.EvnSIR;
import org.auriga.core.modelo.transferObjects.Usuario;

/**
 *
 * @author Ellery David Robles Gómez
 */
public class EvnGestionDocumental extends EvnSIR {

    /** Esta constante se utiliza  para identificar el evento de selección de una zona registral  */
    public static final String SELECCIONA_CIRCULO = "SELECCIONA_CIRCULO";

    public static final String MOSTRAR_CIRCULO_USUARIO = "MOSTRAR_CIRCULO_USUARIO";

    /** Esta constante se utiliza  para identificar el evento de selección de una zona registral  */
    public static final String TURNOS_GESTION_DOCUMENTAL = "TURNOS_GESTION_DOCUMENTAL";

    /** Esta constante se utiliza  para identificar el evento de selección de un TURNO A REENCOLAR */
    public static final String TURNO_REENCOLAR = "TURNO_REENCOLAR";

    /** Esta constante se utiliza  para identificar el evento de selección de un RANGO DE FECHAS */
    public static final String SELECCIONA_TURNOS_POR_FECHA = "SELECCIONA_TURNOS_POR_FECHA";

    /** Esta constante se utiliza  para identificar el evento de selección de un RANGO DE FECHAS */
    public static final String DEPURAR_TURNOS_POR_FECHA = "DEPURAR_TURNOS_POR_FECHA";

    private String anio;
    private String circulo;
    private String proceso;
    private String turno;
    private String fechaInicio;
    private String fechaFin;

    /**
     * @param usuario
     */
    public EvnGestionDocumental(Usuario usuario) {
            super(usuario);
    }

    /**
     * @param usuario
     * @param tipoEvento
     */
    public EvnGestionDocumental(Usuario usuario, String tipoEvento) {
            super(usuario, tipoEvento);
    }

    /**
     * @return
     */
    public String getAnio() {
        return anio;
    }

    /**
     * @param anio
     */
    public void setAnio(String anio) {
        this.anio = anio;
    }

    /**
     * @return
     */
    public String getCirculo() {
            return circulo;
    }

    /**
     * @param circulo
     */
    public void setCirculo(String circulo) {
            this.circulo = circulo;
    }

    /**
     * @return
     */
    public String getProceso() {
        return proceso;
    }

    /**
     * @param proceso
     */
    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    /**
     * @param turno
     */
    public void setTurno(String turno) {
            this.turno = turno;
    }

    /**
     * @return turno
     */
    public String getTurno() {
            return turno;
    }

    /**
     * @param fechaInicio
     */
    public void setFechaInicio(String fechaInicio) {
            this.fechaInicio = fechaInicio;
    }

    /**
     * @return fechaInicio
     */
    public String getFechaInicio() {
            return fechaInicio;
    }

    /**
     * @param fechaFin
     */
    public void setFechaFin(String fechaFin) {
            this.fechaFin = fechaFin;
    }

    /**
     * @return fechaFin
     */
    public String getFechaFin() {
            return fechaFin;
    }

}
