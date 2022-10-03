package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TurnoHistoria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;



/*
 * Generated by JDO Genie (version:3.0.0 (08 Jun 2004))
 */
public class TurnoHistoriaEnhanced extends Enhanced {

    private String anio; // pk 
    private String idCirculo; // pk 
    private long idProceso; // pk 
    private String idTurno; // pk 
    private String idTurnoHistoria; // pk 
    private String Ncopias;
    private String accion;
    private String fase;
	private String nombreFase;
    private Date fecha;
    private TurnoEnhanced turno; // inverse Turno.turnoHistoria
    private UsuarioEnhanced usuario;
    private ProcesoEnhanced proceso;
    private boolean activo;
    private int stackPos;
	private String respuesta;
	private String faseAnterior;
	private List recursos = new ArrayList(); // contains RecursoEnhanced  inverse RecursoEnhanced.turnoHistoria
	private String idAdministradorSAS;
	private UsuarioEnhanced usuarioAtiende;
	private int numeroCopiasReimpresion;
	private String idRelacion;
	private String idRelacionSiguiente;
	private int lastIdRecurso;
	private String idMatriculaImpresa;
        private int sentJuzgado;
        private Date fechaJuzgado;


    public int getLastIdRecurso() {
		return lastIdRecurso;
	}

	public void setLastIdRecurso(int lastIdRecurso) {
		this.lastIdRecurso = lastIdRecurso;
	}

	public String getIdRelacion() {
		return idRelacion;
	}

	public void setIdRelacion(String idRelacion) {
		this.idRelacion = idRelacion;
	}

	public String getIdRelacionSiguiente() {
		return idRelacionSiguiente;
	}

	public void setIdRelacionSiguiente(String idRelacionSiguiente) {
		this.idRelacionSiguiente = idRelacionSiguiente;
	}

	public TurnoHistoriaEnhanced() {
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getIdCirculo() {
        return idCirculo;
    }

    public void setIdCirculo(String idCirculo) {
        this.idCirculo = idCirculo;
    }

    public long getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(long idProceso) {
        this.idProceso = idProceso;
    }

    public String getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(String idTurno) {
        this.idTurno = idTurno;
    }

    public String getIdTurnoHistoria() {
        return idTurnoHistoria;
    }

    public void setIdTurnoHistoria(String idTurnoHistoria) {
        this.idTurnoHistoria = idTurnoHistoria;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public TurnoEnhanced getTurno() {
        return turno;
    }

    public void setTurno(TurnoEnhanced turno) {
        this.turno = turno;
        setAnio(turno.getAnio());
        setIdCirculo(turno.getIdCirculo());
        setIdProceso(turno.getIdProceso());
        setIdTurno(turno.getIdTurno());
    }

    public UsuarioEnhanced getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEnhanced usuario) {
        this.usuario = usuario;
    }
    

	public static TurnoHistoriaEnhanced enhance(TurnoHistoria turnoHistoria) {
		return (TurnoHistoriaEnhanced) Enhanced.enhance(turnoHistoria);
	}


    /**
	 * @return
	 */
	public ProcesoEnhanced getProceso() {
		return proceso;
	}

	/**
	 * @param proceso
	 */
	public void setProceso(ProcesoEnhanced proceso) {
		this.proceso = proceso;
	}

	/**
	 * @return
	 */
	public boolean isActivo() {
		return activo;
	}

	/**
	 * @param b
	 */
	public void setActivo(boolean b) {
		activo = b;
	}

	/**
	 * @return
	 */
	public int getStackPos() {
		return stackPos;
	}

	/**
	 * @param i
	 */
	public void setStackPos(int i) {
		stackPos = i;
	}

	/**
	 * @return
	 */
	public String getRespuesta() {
		return respuesta;
	}

	/**
	 * @param string
	 */
	public void setRespuesta(String string) {
		respuesta = string;
	}

	/**
	 * @return
	 */
	public String getFaseAnterior() {
		return faseAnterior;
	}

	/**
	 * @param string
	 */
	public void setFaseAnterior(String string) {
		faseAnterior = string;
	}

	/**
	 * @return
	 */
	public String getNombreFase() {
		return nombreFase;
	}

	/**
	 * @param string
	 */
	public void setNombreFase(String string) {
		nombreFase = string;
	}
	

	public List getRecursos() {
		return Collections.unmodifiableList(recursos);
	}

	public boolean addRecurso(RecursoEnhanced newRecursoEnhanced) {
		return recursos.add(newRecursoEnhanced);
	}

	public boolean removeRecurso(RecursoEnhanced oldRecursoEnhanced) {
		return recursos.remove(oldRecursoEnhanced);
	}

	/**
	 * @return
	 */
	public String getIdAdministradorSAS() {
		return idAdministradorSAS;
	}

	/**
	 * @param string
	 */
	public void setIdAdministradorSAS(String string) {
		idAdministradorSAS = string;
	}

	/**
	 * @return
	 */
	public UsuarioEnhanced getUsuarioAtiende() {
		return usuarioAtiende;
	}

	/**
	 * @param enhanced
	 */
	public void setUsuarioAtiende(UsuarioEnhanced enhanced) {
		usuarioAtiende = enhanced;
	}

	/**
	 * @return
	 */
	public int getNumeroCopiasReimpresion() {
		return numeroCopiasReimpresion;
	}

	/**
	 * @param i
	 */
	public void setNumeroCopiasReimpresion(int i) {
		numeroCopiasReimpresion = i;
	}

	public String getIdMatriculaImpresa() {
		return idMatriculaImpresa;
	}

	public void setIdMatriculaImpresa(String idMatriculaImpresa) {
		this.idMatriculaImpresa = idMatriculaImpresa;
	}
        public String getNcopias() {
             return Ncopias;
        }

        public void setNcopias(String Ncopias) {
            this.Ncopias = Ncopias;
        }

        public int getSentJuzgado() {
            return sentJuzgado;
        }

        public void setSentJuzgado(int sentJuzgado) {
            this.sentJuzgado = sentJuzgado;
        }

        public Date getFechaJuzgado() {
            return fechaJuzgado;
        }

        public void setFechaJuzgado(Date fechaJuzgado) {
            this.fechaJuzgado = fechaJuzgado;
        }
        
}