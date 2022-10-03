package gov.sir.core.eventos.administracion;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Relacion;


public class EvnRespRelacion extends EvnSIRRespuesta {

	public static final String SELECCIONAR_PROCESO_OK = "SELECCIONAR_PROCESO_OK";
	public static final String SELECCIONAR_FASE_OK = "SELECCIONAR_FASE_OK";
	public static final String CARGAR_DATOS_OK = "CARGAR_DATOS_OK";
	public static final String SELECCIONAR_RELACION_OK = "SELECCIONAR_RELACION_OK";
	public static final String CREAR_RELACION_OK = "CREAR_RELACION_OK";
	public static final String IMPRIMIR_OK = "IMPRIMIR_OK";
	public static final String INGRESAR_RELACION_OK = "INGRESAR_RELACION_OK";
        public static final String REL = "REL";
        public static final String SIR = "SIR";
	
	private boolean mostrarIdRelacion;
	private boolean mostrarVencimientoMayorValor;
	private boolean mostrarNumeroDocumento;
	private boolean mostrarBotonImprimir;
	private boolean mostrarDetalle;
        private String radicacion;
	private String idReporte;
	private String idRelacion;
	private long idProceso;
	private String idFase;
	private Hashtable respException;
	private Hashtable respTurnosOk;
	private List turnosMenu;
        private boolean Mostrarverimp;
        private boolean Mostrarverimp2;

        private Map impresoras;
	private String tipoRelacionAvanzar;
	private Circulo circulo;
	private boolean mostrarLabelRelacion;
	
	private Map turnosCertificadosValidos;
	
	private Relacion relacion;
	
	private List relacionesFase;
	
	private List fases;
	
	
	public Relacion getRelacion() {
		return relacion;
	}

	public void setRelacion(Relacion relacion) {
		this.relacion = relacion;
	}

	public Map getTurnosCertificadosValidos() {
		return turnosCertificadosValidos;
	}

	public void setTurnosCertificadosValidos(Map turnosCertificadosValidos) {
		this.turnosCertificadosValidos = turnosCertificadosValidos;
	}

	public EvnRespRelacion(Object payload, String tipoEvento) {
		super(payload, tipoEvento);
	}

	public void setMostrarIdRelacion(boolean mostrarCodigo) {
		this.mostrarIdRelacion = mostrarCodigo;
	}

	public void setMostrarVencimientoMayorValor(boolean mostrarVencimiento) {
		this.mostrarVencimientoMayorValor = mostrarVencimiento;
	}
	
	public boolean isMostrarIdRelacion() {
		return mostrarIdRelacion;
	}
	
	public boolean isMostrarVencimientoMayorValor() {
		return mostrarVencimientoMayorValor;
	}

        public Circulo getCirculo() {
            return circulo;
        }

        public void setCirculo(Circulo circulo) {
            this.circulo = circulo;
        }
        
	public void setMostrarNumeroDocumento(boolean mostrarNumeroDocumento) {
		this.mostrarNumeroDocumento = mostrarNumeroDocumento;
	}
	
	public boolean isMostrarNumeroDocumento() {
		return mostrarNumeroDocumento;
	}
	
	public String getIdReporte() {
		return idReporte;
	}
        public boolean isMostrarverimp2() {
               return Mostrarverimp2;
           }

        public void setMostrarverimp2(boolean Mostrarverimp2) {
            this.Mostrarverimp2 = Mostrarverimp2;
        }
        public boolean isMostrarverimp() {
            return Mostrarverimp;
        }

        public Map getImpresoras() {
            return impresoras;
        }

        public void setImpresoras(Map impresoras) {
            this.impresoras = impresoras;
        }
        
        public void setMostrarverimp(boolean Mostrarverimp) {
            this.Mostrarverimp = Mostrarverimp;
        }
	
	public String getIdRelacion() {
		return idRelacion;
	}
	
	public long getIdProceso() {
		return idProceso;
	}
	
	public String getIdFase() {
		return idFase;
	}
	
	public void setIdReporte(String idReporte) {
		this.idReporte = idReporte;
	}
	
	public void setIdRelacion(String idRelacion) {
		this.idRelacion = idRelacion;
	}
	
	public void setIdProceso(long idProceso) {
		this.idProceso = idProceso;
	}
	
	public void setIdFase(String idFase) {
		this.idFase = idFase;
	}

	public Hashtable getRespException() {
		return respException;
	}

	public void setRespException(Hashtable respException) {
		this.respException = respException;
	}

	public Hashtable getRespTurnosOk() {
		return respTurnosOk;
	}

	public void setRespTurnosOk(Hashtable respTurnosOk) {
		this.respTurnosOk = respTurnosOk;
	}

	public boolean isMostrarBotonImprimir() {
		return mostrarBotonImprimir;
	}

	public void setMostrarBotonImprimir(boolean mostrarBotonImprimir) {
		this.mostrarBotonImprimir = mostrarBotonImprimir;
	}

	public List getTurnosMenu() {
		return turnosMenu;
	}

	public void setTurnosMenu(List turnosMenu) {
		this.turnosMenu = turnosMenu;
	}

	public String getTipoRelacionAvanzar() {
		return tipoRelacionAvanzar;
	}

	public void setTipoRelacionAvanzar(String tipoRelacionAvanzar) {
		this.tipoRelacionAvanzar = tipoRelacionAvanzar;
	}

	public boolean isMostrarLabelRelacion() {
		return mostrarLabelRelacion;
	}

	public void setMostrarLabelRelacion(boolean mostrarLabelRelacion) {
		this.mostrarLabelRelacion = mostrarLabelRelacion;
	}

	public boolean isMostrarDetalle() {
		return mostrarDetalle;
	}

	public void setMostrarDetalle(boolean mostrarDetalle) {
		this.mostrarDetalle = mostrarDetalle;
	}

	public List getRelacionesFase() {
		return relacionesFase;
	}

	public void setRelacionesFase(List relacionesFase) {
		this.relacionesFase = relacionesFase;
	}

	public List getFases() {
		return fases;
	}

	public void setFases(List fases) {
		this.fases = fases;
	}
}
