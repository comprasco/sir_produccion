package gov.sir.core.eventos.comun;

import java.util.HashMap;

import org.auriga.core.modelo.transferObjects.Usuario;

public class EvnReportesJasper extends EvnSIR {

	public static final String GENERAR_PDF_REPORTE = "GENERAR_PDF_REPORTE";  
	
	private String nombreReporte;
	private HashMap parametros; 
	
	public EvnReportesJasper(Usuario usuario, String tipoEvento) {
		super(usuario, tipoEvento);
	}
	
	public EvnReportesJasper(Usuario usuario, String tipoEvento, String nombreReporte, HashMap parametros) {
		super (usuario, tipoEvento);
		this.nombreReporte = nombreReporte;
		this.parametros = parametros;
	}

	public String getNombreReporte() {
		return nombreReporte;
	}

	public void setNombreReporte(String nombreReporte) {
		this.nombreReporte = nombreReporte;
	}

	public HashMap getParametros() {
		return parametros;
	}

	public void setParametros(HashMap parametros) {
		this.parametros = parametros;
	}
	
}
