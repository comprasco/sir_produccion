package gov.sir.core.web.acciones.certificadosmasivos;

import co.com.iridium.generalSIR.comun.GeneralSIRException;
/**
* @autor Carlos Torres
* @mantis 11309
*/
import co.com.iridium.generalSIR.negocio.validaciones.TrasladoSIR;
import co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

import gov.sir.core.eventos.certificadosmasivos.EvnCertificadoMasivo;
import gov.sir.core.eventos.certificadosmasivos.EvnRespCertificadoMasivo;
import gov.sir.core.eventos.comun.EvnRespLiquidacion;
import gov.sir.core.eventos.comun.EvnRespPago;
import gov.sir.core.negocio.modelo.AplicacionPago;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.Departamento;
import gov.sir.core.negocio.modelo.DocPagoEfectivo;
import gov.sir.core.negocio.modelo.Documento;
import gov.sir.core.negocio.modelo.DocumentoPago;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificadoMasivo;
import gov.sir.core.negocio.modelo.Municipio;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.SolicitudAsociada;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudCertificadoMasivo;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.TipoCertificado;
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CRoles;
import gov.sir.core.negocio.modelo.constantes.CSolicitudRegistro;
import gov.sir.core.negocio.modelo.constantes.CTipoCertificado;
import gov.sir.core.negocio.modelo.constantes.CTipoTarifa;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.ValidacionLiquidacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionMasivosException;
import gov.sir.core.web.helpers.comun.ElementoLista;
import gov.sir.core.web.helpers.comun.ValidacionDatosOficinaOrigen;
import gov.sir.hermod.HermodProperties;
import java.util.Calendar;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.HermodService;

import java.text.ParseException;

/**
 *
 * @author  Ing. Diego Cantor, Mario Rios, mmunoz
 */
public class AWLiquidacionCertificadosMasivos extends SoporteAccionWeb{
	
	/** Constante que identifica el campo del jsp donde se solicita el numero de la matricula*/
	public final static String MATRICULA = CFolio.ID_MATRICULA;

	/** Constante que identifica el campo del jsp donde se solicita el numero total de matriculas*/
	public final static String NUM_MATRICULAS = CFolio.NUM_MATRICULAS;

	/** Constante que identifica el numero de anotaciones de un folio*/
	public final static String NUM_ANOTACIONES_FOLIO = CFolio.NUMERO_ANOTACIONES;

	/** Constante que identifica si el folio es de mayro extension*/
	public final static String MAYOR_EXTENSION_FOLIO = CFolio.MAYOR_EXTENSION;

    
    /** Constante utilizada con el parametro ACCION del request. Indica que la accion
     * a realizar es de adicion de un certificado a los  certificados masivos en memoria
     */
    public static final String AGREGAR = "AGREGAR";
    
    /** Constante utilizada con el parametro ACCION del request. Indica que la accion
     * a realizar es de eliminacion de un certificado de los certificados masivos en memoria
     */
    public static final String ELIMINAR = "ELIMINAR";
    
	/**
	 * Constante que identifica que se desea eliminar
	 * una matrícula de la solicitud
	 */
	public final static String ELIMINAR_VERIFICADO = "ELIMINAR_VERIFICADO";
    
	/**
	 * Constante que identifica que se desea agregar una
	 * nueva matrícula a la solicitud
	 */
	public final static String AGREGAR_VERIFICADO = "AGREGAR_VERIFICADO";
	
	/**
	 * Constante que identifica que se van agregar matriculas desde archivo
	 * daniel 
	 */
	public final static String AGREGAR_DE_ARCHIVO = "AGREGAR_DE_ARCHIVO";
    
    /** Constante utilizada con el parametro ACCION del request. Indica que la accion
     * a realizar es de creacion de certificados masivos de memoria a persistencia y workflow
     */
    public static final String LIQUIDAR = "LIQUIDAR";
    
	public static final String TIPO = "TIPO_CERTIFICADO";
    
	public static final String UPLOAD = "UPLOAD";
	
	private static final String CONTENT_TYPE = "text/plain";
	
	
	

	private HttpSession session;
    
    private List lista;
    
    private List copias;
    
    /** Crea una nueva instancia de esta accion web */
    public AWLiquidacionCertificadosMasivos() {
    }
    
    public void doStart(HttpServletRequest request) {
        HttpSession sesion = request.getSession();
        lista = (List)sesion.getAttribute(WebKeys.LISTA_CERTIFICADOS_MASIVOS);
		copias = (List)sesion.getAttribute("LISTA_COPIAS");
		
        if (lista == null){
            lista = new ArrayList();
            copias = new ArrayList();
            sesion.setAttribute(WebKeys.LISTA_CERTIFICADOS_MASIVOS, lista);
			sesion.setAttribute("LISTA_COPIAS", copias);
        }
    }
    
    /**
     * A partir de la accion recibida realiza el proceso correspondiente en la capa web.
     * Si necesita informacion de la accion de negocio para completar su trabajo (doEnd), entonces
     * genera un evento.
     * @param request la peticion web del usuario
     */
    public Evento perform(HttpServletRequest request) throws AccionWebException {
        String accion = request.getParameter(WebKeys.ACCION);
        session = request.getSession();
        
		boolean isMultipart = FileUpload.isMultipartContent(request);
		
		if ( isMultipart ) {
			Rol rol = (Rol)session.getAttribute(WebKeys.ROL);
			if(rol.getRolId().equals(CRoles.SIR_ROL_USUARIO_OPERATIVO)){
				uploadFileExento(request);
				return null;
			}
			return uploadFile(request);
		}
		
        if ((accion == null) || (accion.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe indicar una accion valida");
        }
        if (accion.equals(AGREGAR)){
            agregarMatricula(request);
            return null;
        } else if (accion.equals(AGREGAR_VERIFICADO)) {
			return adicionarMatriculaVerificando(request);
        } else if (accion.equals(ELIMINAR)|| accion.equals(ELIMINAR_VERIFICADO)){
            eliminarMatriculas(request);
            return null;
        }
        else if (accion.equals(LIQUIDAR)){
			preservarInfo(request);
            return liquidar(request);
        }
        else {
            throw new AccionInvalidaException("La accion " + accion +" no es valida.");
        }
    }
    
	public void agregarMatricula(HttpServletRequest request) throws AccionWebException {
		ValidacionMasivosException exception = new ValidacionMasivosException();
		Circulo circ= (Circulo) session.getAttribute(WebKeys.CIRCULO);
		String matricula = circ.getIdCirculo()+ "-";
		matricula += request.getParameter(CFolio.ID_MATRICULA);
		String copias = request.getParameter("COPIAS");
		int numCopias = 1;
		
		try {
			numCopias = Integer.parseInt(copias);
			
			if ( numCopias < 1 ) {
				exception.addError("Número de copias inválido: "+copias);
			}
		}
		catch (NumberFormatException nfe) {
			exception.addError("Número de copias inválido: "+copias);
		}
                /**
                 * @autor Carlos Torres
                 * @mantis 11309
                 */
                TrasladoSIR trasladoSir = new TrasladoSIR();
                /**
                 * @autor Edgar Lora
                 * @mantis 11987
                 */
                ValidacionesSIR validacionesSIR = new ValidacionesSIR();
                try {
                    if(validacionesSIR.isEstadoFolioBloqueado(matricula)){
                        exception.addError("La matricula que desea agregar tiene como estado 'Bloqueado'.");
                /**
                 * @autor Carlos Torres
                 * @mantis 11309
                 */
                    }else if(trasladoSir.trasladadoFolio(matricula)){
                             exception.addError("El folio " + matricula + " esta en estado Trasladado");
                    } else if(trasladoSir.isBloqueDeTraslado(matricula)){
                            exception.addError("El folio " + matricula + " esta pendiente por confirmar traslado.");
                    }
                } catch (GeneralSIRException ex) {
                   if(ex.getMessage() != null){
                       exception.addError(ex.getMessage());
                   }
                }

		if (exception.getErrores().size() > 0) {
			throw exception;
		}
		else {
			if ((matricula != null) && !matricula.equals("") && !this.lista.contains(matricula) ) {
				this.lista.add(matricula.toUpperCase());
				this.copias.add("Copias: "+numCopias);
			}
		}
	}
	
	/**
	 * Agregar una matricula la matricula asociada verificando sus datos.
	 * @param request HttpServletRequest
	 * @return EvnLiquidacion nulo ya que no requiere viajar al negocio
	 */

	private EvnCertificadoMasivo adicionarMatriculaVerificando(HttpServletRequest request) throws AccionWebException {
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		
		ValidacionMasivosException exception = new ValidacionMasivosException();

		
		Circulo circ= (Circulo) session.getAttribute(WebKeys.CIRCULO);
		String matricula = circ.getIdCirculo()+ "-";
		matricula += request.getParameter(CFolio.ID_MATRICULA);
		if ((matricula == null || matricula.equals(""))) {
			exception.addError("el numero de matricula es inválida");
		}
                /**
                 * @autor Edgar Lora
                 * @mantis 11987
                 */
                ValidacionesSIR validacionesSIR = new ValidacionesSIR();
                try {
                    if(validacionesSIR.isEstadoFolioBloqueado(matricula)){
                        exception.addError("La matricula que desea agregar tiene como estado 'Bloqueado'.");
                    }
                } catch (GeneralSIRException ex) {
                   if(ex.getMessage() != null){
                       exception.addError(ex.getMessage());
                   }
                }
	
		if (exception.getErrores().size() > 0) {
				throw exception;
		}
		EvnCertificadoMasivo evento = null;
	
		
	
		if ((matricula != null) && !matricula.equals("")) {
			evento = new EvnCertificadoMasivo(usuarioAuriga, matricula, AGREGAR_VERIFICADO);
		}
		preservarInfo(request);
		return evento;
	}
		
    /**
     *Adiciona una solicitud de certificados a la lista en sesion
     */
    
    private void eliminarMatriculas(HttpServletRequest request) throws AccionWebException {
		try {
			String matriculas[] = request.getParameterValues(WebKeys.TITULO_CHECKBOX_ELIMINAR);
	
			for ( int i = 0; i < matriculas.length; i++ ) {
				this.copias.remove(lista.indexOf(matriculas[i]));
				this.lista.remove(matriculas[i]);
			}
		}
		catch (Exception e) {}
    }
    
    private void uploadFileExento(HttpServletRequest request) throws AccionWebException {
		ValidacionMasivosException exception = new ValidacionMasivosException();
                /**
                 * @autor Edgar Lora
                 * @mantis 11987
                 */
		boolean errorCopia = false, errorFormato = false, errorMatriculasInvalidas = false, errorMatriculasBloqueadas = false;
		String strErrorCopia = "La cantidad de copias debe ser un valor númerico mayor que 0. Se presentaron errores para las matrículas:";
		String strErrorFormato = "El formato de los registros debe ser circulo;matricula;copias. Se presentaron errores en:";
		String strErrorMatriculasInvalidas= "Las matriculas a liquidar deben pertenecer al mismo circulo. Se presentaron errores para las matrículas:";
		List lstCopia = new ArrayList(), lstFormato = new ArrayList();
		List matriculasInvalidas = new ArrayList();
		
    	try {
			DiskFileUpload upload = new DiskFileUpload();
			List list = upload.parseRequest(request);
			Iterator it = list.iterator();
			HttpSession session = request.getSession();
			String tipoCert = "";
                            /**
                            * @autor Carlos Torres
                            * @mantis 11309
                            */
                            TrasladoSIR trasladoSir = new TrasladoSIR();
                        if ( session.getAttribute("ID_TIPO_CERTIFICADO")!= null ) {
				tipoCert = (String) session.getAttribute("ID_TIPO_CERTIFICADO");
			}
	
			while ( it.hasNext() ) {
				FileItem fi = (FileItem) it.next();
		
				if ( !fi.isFormField() ) {
					String fileName = fi.getName();
					
					if ( !fileName.endsWith(".txt") ) {
						exception.addError("El archivo debe tener extensión .txt, y el procesado no corresponde: "+fileName);
					}
					else {					
						if ( fi.getContentType().equals(CONTENT_TYPE) ) {
							BufferedReader in = new BufferedReader(new InputStreamReader(fi.getInputStream()));
					
							String record = "", recArray[] = null, strMat = "";
							
							Circulo circ = null;
							while ( (record = in.readLine()) != null ) {
								int numCopias = 1;
								recArray = record.split(";");
								
								if ( recArray.length != 3 ) {
									lstFormato.add(record);
									errorFormato = true;
								}
								else {
									strMat = recArray[0]+"-"+recArray[1];
                                                                        /**
                                                                        * @autor Edgar Lora
                                                                        * @mantis 11987
                                                                        */
                                                                        ValidacionesSIR validacionesSIR = new ValidacionesSIR();
                                                                        if(validacionesSIR.isEstadoFolioBloqueado(strMat)){
                                                                            errorMatriculasBloqueadas = true;
                                                                            exception.addError("La matricula " + strMat + " se encuentra con un estado de 'Bloqueado'.");
                                                                           /**
                                                                            * @autor Carlos Torres
                                                                            * @mantis 11309
                                                                            */
                                                                        }else if(trasladoSir.trasladadoFolio(strMat)){
                                                                            errorMatriculasBloqueadas = true;
                                                                            exception.addError("El folio " + strMat + " esta en estado Trasladado");
                                                                        } else if(trasladoSir.isBloqueDeTraslado(strMat)){
                                                                            errorMatriculasBloqueadas = true;
                                                                            exception.addError("El folio " + strMat + " esta pendiente por confirmar traslado.");
                                                                        }
									circ = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
									strErrorMatriculasInvalidas= "Las matriculas a liquidar deben pertenecer al circulo: " + circ.getIdCirculo() + ". Se presentaron errores para las matrículas:";
									String circuloMatricula = null;
									StringTokenizer st = new StringTokenizer(strMat, "-");
							        if (st.countTokens() > 0) {
							        	circuloMatricula = (st. nextToken());
							        }
							        if (!circuloMatricula.equals(circ.getIdCirculo())) {
							            matriculasInvalidas.add(strMat.toUpperCase());
							        }
							        else{
                                                                        /**
                                                                        * @autor Edgar Lora
                                                                        * @mantis 11987
                                                                        */
								        if ( !this.lista.contains(strMat) && !errorMatriculasBloqueadas) {
											try {
												numCopias = Integer.parseInt(recArray[2].trim());
												if ( tipoCert.equals(CTipoCertificado.TIPO_INMEDIATO_ID)||
														tipoCert.equals(CTipoCertificado.TIPO_EXENTO_ID)) {
													if ( numCopias < 1 ) {
														lstCopia.add(strMat.toUpperCase());
														errorCopia = true;
													}
													else {
												    	this.lista.add(strMat.toUpperCase());
														this.copias.add("Copias: "+numCopias);
													}
												}
												else {
													numCopias = 1;
													this.lista.add(strMat.toUpperCase());
													this.copias.add("Copias: "+numCopias);
												}
											}
											catch (NumberFormatException e) {
												lstCopia.add(strMat.toUpperCase());
												errorCopia = true;
											}
										}
									}
								}
							}
							if(matriculasInvalidas.size()> 0){
								errorMatriculasInvalidas = true;
							}				
							in.close();
						}
						else {
							exception.addError("El archivo debe tener formato de texto plano.");
						}
					}
				}
			}
		}
		catch (Exception e) {
			exception.addError("Ocurrió un error en el procesamiento del archivo.");
		}
		
		if ( errorCopia ) {
			exception.addError(strErrorCopia);
			
			Iterator it = lstCopia.iterator();
			
			while ( it.hasNext() ) {
				exception.addError((String) it.next());
			}
		}
		
		if ( errorFormato ) {
			exception.addError(strErrorFormato);
			
			Iterator it = lstFormato.iterator();
			
			while ( it.hasNext() ) {
				exception.addError((String) it.next());
			}
		}
		
		if(errorMatriculasInvalidas){
			exception.addError(strErrorMatriculasInvalidas);
			Iterator it = matriculasInvalidas.iterator();
			
			while ( it.hasNext() ) {
				exception.addError((String) it.next());
			}
		}
		
		if (exception.getErrores().size() > 0) {
			throw exception;
		}
    }
    
    private EvnCertificadoMasivo uploadFile(HttpServletRequest request) throws AccionWebException {
		ValidacionMasivosException exception = new ValidacionMasivosException();
                /**
                 * @autor Edgar Lora
                 * @mantis 11987
                 */
		boolean errorCopia = false, errorFormato = false, errorMatriculasInvalidas = false, errorMatriculasBloqueadas = false;
		String strErrorCopia = "La cantidad de copias debe ser un valor númerico mayor que 0. Se presentaron errores para las matrículas:";
		String strErrorFormato = "El formato de los registros debe ser circulo;matricula;copias. Se presentaron errores en:";
		String strErrorMatriculasInvalidas= "Las matriculas a liquidar deben pertenecer al mismo circulo. Se presentaron errores para las matrículas:";
		List lstCopia = new ArrayList(), lstFormato = new ArrayList();
		List matriculasInvalidas = new ArrayList();
		//daniel
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		List listaMatriculas = new ArrayList();
		List listaCopias = new ArrayList();
		
    	try {
			DiskFileUpload upload = new DiskFileUpload();
			List list = upload.parseRequest(request);
			Iterator it = list.iterator();
			HttpSession session = request.getSession();
			String tipoCert = "";
			
			if ( session.getAttribute("ID_TIPO_CERTIFICADO")!= null ) {
				tipoCert = (String) session.getAttribute("ID_TIPO_CERTIFICADO");
			}
	
			while ( it.hasNext() ) {
				FileItem fi = (FileItem) it.next();
		
				if ( !fi.isFormField() ) {
					String fileName = fi.getName();
					
					if ( !fileName.endsWith(".txt") ) {
						exception.addError("El archivo debe tener extensión .txt, y el procesado no corresponde: "+fileName);
					}
					else {					
						if ( fi.getContentType().equals(CONTENT_TYPE) ) {
							BufferedReader in = new BufferedReader(new InputStreamReader(fi.getInputStream()));
					
							String record = "", recArray[] = null, strMat = "";
							
							Circulo circ = null;
							while ( (record = in.readLine()) != null ) {
								int numCopias = 1;
								recArray = record.split(";");
								
								if ( recArray.length != 3 ) {
									lstFormato.add(record);
									errorFormato = true;
								}
								else {
									strMat = recArray[0].trim()+"-"+recArray[1].trim();
                                                                        /**
                                                                        * @autor Edgar Lora
                                                                        * @mantis 11987
                                                                        */
                                                                        ValidacionesSIR validacionesSIR = new ValidacionesSIR();
                                                                        if(validacionesSIR.isEstadoFolioBloqueado(strMat)){
                                                                            errorMatriculasBloqueadas = true;
                                                                            exception.addError("La matricula " + strMat + " se encuentra con un estado de 'Bloqueado'.");                                                                            
                                                                        }
									circ = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
									strErrorMatriculasInvalidas= "Las matriculas a liquidar deben pertenecer al circulo: " + circ.getIdCirculo() + ". Se presentaron errores para las matrículas:";
									String circuloMatricula = null;
									StringTokenizer st = new StringTokenizer(strMat, "-");
							        if (st.countTokens() > 0) {
							        	circuloMatricula = (st. nextToken());
							        }
							        if (!circuloMatricula.equals(circ.getIdCirculo())) {
							            matriculasInvalidas.add(strMat.toUpperCase());
							        }
							        else{
                                                                        /**
                                                                        * @autor Edgar Lora
                                                                        * @mantis 11987
                                                                        */
								        if ( !this.lista.contains(strMat) && !errorMatriculasBloqueadas) {
											try {
												numCopias = Integer.parseInt(recArray[2].trim());
												if ( tipoCert.equals(CTipoCertificado.TIPO_INMEDIATO_ID)||
														tipoCert.equals(CTipoCertificado.TIPO_EXENTO_ID)) {
													if ( numCopias < 1 ) {
														lstCopia.add(strMat.toUpperCase());
														errorCopia = true;
													}
													else {
														listaMatriculas.add(strMat.toUpperCase());
														listaCopias.add("Copias: "+numCopias);
													}
												}
												else {
													numCopias = 1;
													listaMatriculas.add(strMat.toUpperCase());
													listaCopias.add("Copias: "+numCopias);
												}
											}
											catch (NumberFormatException e) {
												lstCopia.add(strMat.toUpperCase());
												errorCopia = true;
											}
										}
									}
								}
							}
							if(matriculasInvalidas.size()> 0){
								errorMatriculasInvalidas = true;
							}				
							in.close();
						}
						else {
							exception.addError("El archivo debe tener formato de texto plano.");
						}
					}
				}
			}
		}
		catch (Exception e) {
			exception.addError("Ocurrió un error en el procesamiento del archivo.");
		}
		
		if ( errorCopia ) {
			exception.addError(strErrorCopia);
			
			Iterator it = lstCopia.iterator();
			
			while ( it.hasNext() ) {
				exception.addError((String) it.next());
			}
		}
		
		if ( errorFormato ) {
			exception.addError(strErrorFormato);
			
			Iterator it = lstFormato.iterator();
			
			while ( it.hasNext() ) {
				exception.addError((String) it.next());
			}
		}
		
		if(errorMatriculasInvalidas){
			exception.addError(strErrorMatriculasInvalidas);
			Iterator it = matriculasInvalidas.iterator();
			
			while ( it.hasNext() ) {
				exception.addError((String) it.next());
			}
		}
		
		if (exception.getErrores().size() > 0) {
			throw exception;
		}
		//daniel
		return new EvnCertificadoMasivo(usuarioAuriga, listaMatriculas, listaCopias, AGREGAR_DE_ARCHIVO);
    }
    
    private EvnCertificadoMasivo liquidar(HttpServletRequest request) throws AccionWebException{
		session.removeAttribute(WebKeys.PAGO);
		session.removeAttribute(WebKeys.LIQUIDACION);
		session.removeAttribute(WebKeys.LISTA_CHEQUES);
		session.removeAttribute(WebKeys.LISTA_CONSIGNACIONES);
		session.removeAttribute(WebKeys.APLICACION_EFECTIVO);
		
		String imprimeNota = request.getParameter(AWCertificadoMasivo.NOTA_INF_CERTIFICADOS_MASIVOS);
		if(imprimeNota!=null && !imprimeNota.trim().equals(""))
			request.getSession().setAttribute(AWCertificadoMasivo.NOTA_INF_CERTIFICADOS_MASIVOS, new Boolean(true));
		
		// Obtención del usuario
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga =
					(org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(
						SMARTKeys.USUARIO_EN_SESION);
		
		//1. Construir la solicitud
		ValidacionLiquidacionException exception = new ValidacionLiquidacionException();
		Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO);
		Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
		Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);
		
		if ( this.lista.size() == 0 ) {
			exception.addError("La lista de matrículas está vacía. Debe agregar al menos una matrícula");
		}
		/*
		 * @author David Panesso
		 * @change REQ 1195: Controlar el máximo número de pdfs a generar.
		 */
		else if(this.lista.size() > Integer.parseInt(HermodProperties.getInstancia().getProperty(HermodProperties.HERMOD_MAX_MATRICULAS_PDF))){
			exception.addError("El número máximo de matrículas a asociar debe ser " + HermodProperties.getInstancia().getProperty(HermodProperties.HERMOD_MAX_MATRICULAS_PDF));
		}
		
		String tipoId = request.getParameter(CCiudadano.TIPODOC);
		String numId = request.getParameter(CCiudadano.DOCUMENTO);
		String apellido1 = request.getParameter(CCiudadano.APELLIDO1);
		String apellido2 = request.getParameter(CCiudadano.APELLIDO2);
		String nombres = request.getParameter(CCiudadano.NOMBRE);
		String telefono = request.getParameter(CCiudadano.TELEFONO);

		if (tipoId.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe seleccionar un tipo de identificación para el ciudadano");
		} else if (tipoId.equals(COPLookupCodes.SECUENCIAL_DOCUMENTO_IDENTIDAD)) {
			if (apellido1 == null || apellido1.trim().equals("")) {
				exception.addError("Debe ingresar el primer apellido del Ciudadano");
			}
		} else if (tipoId.equals(COPLookupCodes.NIT)) {
			if (apellido1 == null || apellido1.trim().equals("")) {
				exception.addError("Debe ingresar el primer apellido del Ciudadano");
			}
		} else {
			double valorId = 0.0d;
			if (numId == null || numId.trim().equals("")) {
				exception.addError("Debe ingresar el número de identificación del Ciudadano");
			} else {
            /* @author : CTORRES
             * @change : Se implemento la validación para solo permitir caracteres alfanuméricos en 
             *           numId cuando el tipo de identificación es PASAPORTE.
             * Caso Mantis : 11056
             * No Requerimiento: 073_151
             */
                            if(tipoId.contains("PS"))
                                    {
                                        String regexSL = "^[a-zA-Z]+$";
                                        String regexSN = "^[0-9]+$";
                                        String regexLN = "^[a-zA0-Z9]+$";
                                        java.util.regex.Pattern patternSL = java.util.regex.Pattern.compile(regexSL);
                                        java.util.regex.Pattern patternSN = java.util.regex.Pattern.compile(regexSN);
                                        java.util.regex.Pattern patternLN = java.util.regex.Pattern.compile(regexLN);
                                        boolean esC = false;
                                        if(patternSL.matcher(numId).matches()) esC = true;
                                        else if(patternSN.matcher(numId).matches()) esC = true;
                                        else if(patternLN.matcher(numId).matches()) esC = true;
                                        else exception.addError("El número de identificación de la persona es inválido. Debe ser alfanumérico");
                                        
                                    }
                                else{
				if (!tipoId.equals(CCiudadano.TIPO_DOC_ID_TARJETA)) {
					try {
						valorId = Double.parseDouble(numId);
						if (valorId <= 0) {
							exception.addError("El valor del documento no puede ser negativo o cero");
						}
					} catch (NumberFormatException e) {
						exception.addError("El número de identificación de la persona es inválido. No puede ser alfanumérico");
					}
				}
                            }
			}
			if (nombres == null || nombres.trim().equals("")) {
				exception.addError("Debe ingresar el nombre del Ciudadano");
			}
			if (apellido1 == null || apellido1.trim().equals("")) {
				exception.addError("Debe ingresar el primer apellido del Ciudadano");
			}
		}

		Ciudadano ciudadano = new Ciudadano();
		ciudadano.setTipoDoc(tipoId);
		ciudadano.setDocumento(numId);
		ciudadano.setNombre(nombres);
		ciudadano.setApellido1(apellido1);
		ciudadano.setApellido2(apellido2);
		ciudadano.setTelefono(telefono);

             /*
             * @author      :   Julio Alcázar Rivas
             * @change      :   Se agrega el documento a la información de la solicitud
             * Caso Mantis  :   000941
             */
                 String rol = "";
                        if ( session.getAttribute(WebKeys.ROL) != null ) {
                            Rol rolClass = (Rol) session.getAttribute(WebKeys.ROL);
                            rol = rolClass.getRolId();

                            if ( rol != null ) {
                              rol = rol.trim();
                            }
                          }
                Documento documento = new Documento();
                if (rol.equals(CRoles.SIR_ROL_USUARIO_OPERATIVO)){
                boolean isInternacional = false;


			String TOficina= request.getParameter(WebKeys.TIPO_OFICINA_I_N);
			if(TOficina.equalsIgnoreCase(WebKeys.TIPO_OFICINA_INTERNACIONAL)){
				isInternacional=true;
			}

			String tipo_encabezado = request.getParameter(CSolicitudRegistro.TIPO_ENCABEZADO);
			String id_encabezado = request.getParameter(CSolicitudRegistro.ID_ENCABEZADO);
			String fecha = request.getParameter(CSolicitudRegistro.CALENDAR);

			session.setAttribute(CSolicitudRegistro.TIPO_ENCABEZADO, tipo_encabezado);
			session.setAttribute(CSolicitudRegistro.ID_ENCABEZADO, id_encabezado);
			session.setAttribute(CSolicitudRegistro.CALENDAR, fecha);

			String nomOficinaInternacional="";
			String nomDepto="";
			String idDepto="";
			String nomMunic="";
			String idMunic="";
			String nomVereda="";
			String idVereda="";
			String tipo_oficina="";
			String numero_oficina="";
			String id_oficina="";
                        /*
                         *  @author Carlos Torres
                         *  @chage   se agrega validacion de version diferente
                         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                         */
                        String version = "";
                        

			if(isInternacional){
				nomOficinaInternacional = request.getParameter(WebKeys.TIPO_OFICINA_INTERNACIONAL);
				session.setAttribute(WebKeys.TIPO_OFICINA_INTERNACIONAL, nomOficinaInternacional);
			}else{

				nomDepto = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
				idDepto = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
				session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO, nomDepto);
				session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO, idDepto);

				nomMunic = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
				idMunic = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
				session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC, nomMunic);
				session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC, idMunic);


				nomVereda = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
				idVereda = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
				session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA, nomVereda);
				session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA, idVereda);

				if ( idVereda == null || nomDepto.equals("") || nomMunic.equals("") ) {
					idVereda = ValidacionDatosOficinaOrigen.obtenerVereda((List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_DEPARTAMENTOS), idDepto, idMunic);
				}

				tipo_oficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO);
				numero_oficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM);
				id_oficina =	request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
				session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO, tipo_oficina);
				session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM, numero_oficina);
				session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA, id_oficina);
                                /*
                                *  @author Carlos Torres
                                *  @chage   se agrega validacion de version diferente
                                *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                                */
                                version = 	request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION);
                                session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION, version);

			}

			if ((tipo_encabezado == null) || tipo_encabezado.equals("SIN_SELECCIONAR")) {
				exception.addError("Debe Seleccionar un tipo de Documento ");
			}

                         /*
                          * @author      :   Julio Alcázar Rivas
                          * @change      :   Validaciones del numero del documento de la solicitud
                          * Caso Mantis  :   000941
                          */
                        if (id_encabezado == null || id_encabezado.trim().equals("")) {
				exception.addError("Debe ingresar el numero de documento");
			}/*
                          * @author      :   Julio Alcázar Rivas
                          * @change      :   Se eliminó la validacion de tipo numerico en el numero del documento
                          * Caso Mantis  :   0007915 Acta - Requerimiento No 255 - SOLICITUD CERTIFICADOS EXENTOS MASIVOS NUMERO DEL DOCUMENTO
                          */
			Calendar fechaDocumento = darFecha(fecha);

			if (fechaDocumento == null) {
				exception.addError("La fecha del documento es invalida, debe ingresar una fecha valida");
			}
			 /*
                          * @author      :   Julio Alcázar Rivas
                          * @change      :   se elimo dos if y se modificaron los mensajes de error
                          * Caso Mantis  :   000941
                          */
                        if(isInternacional){
                            if((nomOficinaInternacional == null) || nomOficinaInternacional.trim().equals("")) {
                                exception.addError("Debe ingresar El nombre de la oficina internacional");
                            }
			}else{

                            if ((idDepto == null) || idDepto.trim().equals("")) {
				exception.addError("Debe ingresar El departamento ");
			    }

			    if ((idMunic == null) || idMunic.trim().equals("")) {
				exception.addError("Debe ingresar El municipio ");
			    }

                            if ((tipo_oficina == null) || tipo_oficina.trim().equals("")) {
				exception.addError("Debe ingresar El tipo de Oficina Origen");
			    }

                            if ((numero_oficina == null) || numero_oficina.trim().equals("")) {
				exception.addError("Debe ingresar El número de Oficina Origen ");
			    }

			}			

			if (exception.getErrores().size() > 0) {
				throw exception;
			}

			String valorDepartamento =
				request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
			String valorMunicipio =
				request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
			String valorVereda =
				request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
			String idOficina =
				request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
                        /*
                         *  @author Carlos Torres
                         *  @chage   se agrega validacion de version diferente
                         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                         */
                        String oficinaVersion = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION);
			String nomOficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO);
			String numOficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM);

			OficinaOrigen oficinaOrigen = new OficinaOrigen();

			if(!isInternacional){

				if (nomOficina != null) {
					oficinaOrigen.setNombre(nomOficina);
				}

				if (numOficina != null) {
					oficinaOrigen.setNumero(numOficina);
				}

				oficinaOrigen.setIdOficinaOrigen(idOficina);
                                /*
                                 *  @author Carlos Torres
                                 *  @chage   se agrega validacion de version diferente
                                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                                 */
                                oficinaOrigen.setVersion(Long.parseLong(oficinaVersion));

				Vereda vereda = new Vereda();
                                Municipio municipio = new Municipio();
                                Departamento departamento = new Departamento();

                                departamento.setNombre(nomDepto);
                                municipio.setDepartamento(departamento);
                                vereda.setMunicipio(municipio);

                                if (nomVereda != null) {
					vereda.setNombre(nomVereda);
				}

				vereda.setIdVereda(valorVereda);
				vereda.setIdDepartamento(valorDepartamento);
				vereda.setIdMunicipio(valorMunicipio);

                                oficinaOrigen.setVereda(vereda);
			}


			documento.setFecha(fechaDocumento.getTime());

			/*
                          * @author      :   Julio Alcázar Rivas
                          * @change      :   se elimo un if
                          * Caso Mantis  :   000941
                          */
                        if(!isInternacional){
                            documento.setOficinaInternacional(null);
			    documento.setOficinaOrigen(oficinaOrigen);
			}else{
			    documento.setOficinaOrigen(null);
			    documento.setOficinaInternacional(nomOficinaInternacional);
			}

		

			TipoDocumento tipoDoc = new TipoDocumento();
			tipoDoc.setIdTipoDocumento(tipo_encabezado);

			List tiposDocs = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO);
			Iterator itTiposDocs = tiposDocs.iterator();

			while (itTiposDocs.hasNext()) {
				ElementoLista elemento = (ElementoLista) itTiposDocs.next();

				if (elemento.getId().equals(tipo_encabezado)) {
					tipoDoc.setNombre(elemento.getValor());
				}
			}

			documento.setTipoDocumento(tipoDoc);
			documento.setNumero(id_encabezado);
                }
       	//Se setea el circulo del ciudadano
		ciudadano.setIdCirculo(circulo!=null?circulo.getIdCirculo():null);
        
		SolicitudCertificadoMasivo solicitud = new SolicitudCertificadoMasivo();
		solicitud.setCirculo(circulo);
		solicitud.setCiudadano(ciudadano);
		solicitud.setUsuario(usuario);
		solicitud.setProceso(proceso);
		
		Iterator it = this.lista.iterator();
		
		while ( it.hasNext() ) {
			String mat = (String) it.next();
			String num[] = ((String) this.copias.get(this.lista.indexOf(mat))).split(":"); 
			int copiaMat = Integer.parseInt(num[1].trim());
						
			SolicitudCertificado sol = new SolicitudCertificado();
			sol.setCirculo(circulo);
			sol.setCiudadano(ciudadano);
			sol.setUsuario(usuario);
			
			Proceso procesoCert = new Proceso();
			procesoCert.setIdProceso(new Integer(CProceso.PROCESO_CERTIFICADOS).longValue());
			sol.setProceso(procesoCert); 
			sol.setNumeroCertificados(copiaMat);
			
			SolicitudFolio sfolio = new SolicitudFolio();
			Folio folio = new Folio();
			folio.setIdMatricula(mat);
			sfolio.setFolio(folio);
			sol.addSolicitudFolio(sfolio);
			
			SolicitudAsociada sAso = new SolicitudAsociada();
			sAso.setSolicitudHija(sol);
			
			solicitud.addSolicitudesHija(sAso);
		}
        
        String tipoCert = (String) session.getAttribute("ID_TIPO_CERTIFICADO");
        String tipoTarifa = (String) request.getParameter(CTipoTarifa.ID_TIPO_TARIFA);
		LiquidacionTurnoCertificadoMasivo liquidacion = new LiquidacionTurnoCertificadoMasivo();
		TipoCertificado tipoCertificado = new TipoCertificado();
		
		if ( tipoCert.equals(CTipoCertificado.TIPO_INMEDIATO_ID) ) {
			tipoCertificado.setIdTipoCertificado(CTipoCertificado.TIPO_INMEDIATO_ID);
			tipoCertificado.setNombre(CTipoCertificado.TIPO_INMEDIATO_NOMBRE);	
		}
		else if ( tipoCert.equals(CTipoCertificado.TIPO_EXENTO_ID) ) {
			tipoCertificado.setIdTipoCertificado(CTipoCertificado.TIPO_EXENTO_ID);
			tipoCertificado.setNombre(CTipoCertificado.TIPO_EXENTO_NOMBRE);
		}
		else {
			exception.addError("No se ha podido determinar el tipo de certificado para la solicitud.");
		}
		
		liquidacion.setTipoCertificado(tipoCertificado);
		liquidacion.setTipoTarifa(tipoTarifa);
                 /*
                 * @author      :   Julio Alcázar Rivas
                 * @change      :   SET del documento en la solicitud
                 * Caso Mantis  :   000941
                 */
                if (rol.equals(CRoles.SIR_ROL_USUARIO_OPERATIVO)){
                    solicitud.setDocumento(documento);
                }
		solicitud.addLiquidacion(liquidacion);
		liquidacion.setSolicitud(solicitud);	
		
		Estacion estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);
		String UID = request.getSession().getId();
		String idCirculo = circulo.getIdCirculo();		
		
		EvnCertificadoMasivo evento = new EvnCertificadoMasivo(usuarioAuriga, EvnCertificadoMasivo.LIQUIDAR, liquidacion, proceso, estacion , UID , circulo, idCirculo, usuario);
		evento.setUID(request.getSession().getId());
		
		if (exception.getErrores().size() > 0) {
			throw exception;
		}
		
		return evento;		
	}
	
	/* (non-Javadoc)
	 * @see org.auriga.smart.web.acciones.AccionWeb#doEnd(javax.servlet.http.HttpServletRequest, org.auriga.smart.eventos.EventoRespuesta)
	 */
	public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
		if(evento instanceof EvnRespLiquidacion){
			EvnRespLiquidacion respuesta = (EvnRespLiquidacion) evento;

			if (respuesta != null) {
				if (respuesta.getTipoEvento().equals(EvnRespLiquidacion.LIQUIDACION)) {
					Liquidacion liquidacion = respuesta.getLiquidacion();
					session.setAttribute(WebKeys.LIQUIDACION, liquidacion);
					NumberFormat nf = NumberFormat.getInstance();
					session.setAttribute(WebKeys.VALOR_LIQUIDACION,String.valueOf(liquidacion.getValor()));
					removerInfo(request);
					
					DocumentoPago documentoEfectivo = new DocPagoEfectivo(liquidacion.getValor());
					AplicacionPago aplicacionEfectivo = new AplicacionPago(documentoEfectivo, liquidacion.getValor());
					session.setAttribute(WebKeys.APLICACION_EFECTIVO,aplicacionEfectivo);
				}
			}
		} else if (evento instanceof EvnRespPago){
			EvnRespPago respuesta = (EvnRespPago) evento;
			if (respuesta != null) {
				if (respuesta.getTipoEvento().equals(EvnRespPago.PROCESAMIENTO_PAGO)) {
					Turno turno = respuesta.getTurno();
					session.setAttribute(WebKeys.TURNO, turno);
				}
			} 
		} else if (evento instanceof EvnRespCertificadoMasivo){
			EvnRespCertificadoMasivo respuesta = (EvnRespCertificadoMasivo) evento;
			if (respuesta != null) {
				if (respuesta.getTipoEvento().equals(EvnRespCertificadoMasivo.LIQUIDAR)) {
					Liquidacion liquidacion = respuesta.getLiquidacion();
					session.setAttribute(WebKeys.LIQUIDACION, liquidacion);
					NumberFormat nf = NumberFormat.getInstance();
					session.setAttribute(WebKeys.VALOR_LIQUIDACION,String.valueOf(liquidacion.getValor()));
					removerInfo(request);
					
					DocumentoPago documentoEfectivo = new DocPagoEfectivo(liquidacion.getValor());
					AplicacionPago aplicacionEfectivo = new AplicacionPago(documentoEfectivo, liquidacion.getValor());
					session.setAttribute(WebKeys.APLICACION_EFECTIVO,aplicacionEfectivo);
					
					if(respuesta.getValidacionesMasivos()!=null){
						session.setAttribute(WebKeys.LISTA_VALIDACIONES_MASIVOS,respuesta.getValidacionesMasivos());	
					}
				} else if (respuesta.getTipoEvento().equals(EvnRespCertificadoMasivo.AGREGAR_VERIFICADO)){
					String matricula = (String)respuesta.getPayload();
					if ((matricula != null) && !matricula.equals("")) {
						ValidacionMasivosException exception = new ValidacionMasivosException();
						String copias = request.getParameter("COPIAS");
						int numCopias = 1;
		
						try {
							numCopias = Integer.parseInt(copias);
	
							if ( numCopias < 1 ) {
								exception.addError("Número de copias inválido: "+copias);
							}
						}
						catch (NumberFormatException nfe) {
							exception.addError("Número de copias inválido: "+copias);
						}
						
						if (exception.getErrores().size() > 0) {
							numCopias=1;
						}
						if ((matricula != null) && !matricula.equals("") && !this.lista.contains(matricula) ) {
							this.lista.add(matricula.toUpperCase());
							this.copias.add("Copias: "+numCopias);
						}
						String numeroAnota = respuesta.getNumeroAnotaciones();
						String mayorExtension = respuesta.getMayorExtension();
						request.getSession().setAttribute(MAYOR_EXTENSION_FOLIO, mayorExtension);
						request.getSession().setAttribute(NUM_ANOTACIONES_FOLIO, numeroAnota);
					}
				}else if(respuesta.getTipoEvento().equals(EvnRespCertificadoMasivo.AGREGAR_DE_ARCHIVO)){
					this.lista.addAll(respuesta.getMatriculasArchivo());
					this.copias.addAll(respuesta.getCopiasMatriculas());
				}
			} 
		}
	}
	
	public void removerInfo(HttpServletRequest request) {
		session.removeAttribute(CCiudadano.TIPODOC);
		session.removeAttribute(CCiudadano.DOCUMENTO);
		session.removeAttribute(CCiudadano.APELLIDO1);
		session.removeAttribute(CCiudadano.APELLIDO2);
		session.removeAttribute(CCiudadano.NOMBRE);
	}
		
	/**
	 * Este metodo pone en la sesion la informacion que se puso en los campos,
	 * @param request HttpServletRequest
	 * @return Evento nulo ya que no se requiere que viaje hasta el negocio
	 */
	public void preservarInfo(HttpServletRequest request) {
            
        HermodService hs;
        List canalesXCirculo = new ArrayList() ;
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        try {
            hs = HermodService.getInstance();
           canalesXCirculo = hs.getCanalesRecaudoXCirculo(circulo);
        } catch (HermodException e) {
        }
        session.setAttribute(WebKeys.LISTA_CANALES_RECAUDO_CIRCULO, canalesXCirculo);
            
		String tipoDoc="", documento="", ap1="", ap2="", nombre="";
		
		if ( request.getParameter(CCiudadano.TIPODOC) != null ) {
			tipoDoc = (String) request.getParameter(CCiudadano.TIPODOC);
			session.setAttribute(CCiudadano.TIPODOC, tipoDoc);
		}
		if ( request.getParameter(CCiudadano.DOCUMENTO) != null ) {
			documento = (String) request.getParameter(CCiudadano.DOCUMENTO);
			session.setAttribute(CCiudadano.DOCUMENTO, documento);
		}
		if ( request.getParameter(CCiudadano.APELLIDO1) != null ) {
			ap1 = (String) request.getParameter(CCiudadano.APELLIDO1);
			session.setAttribute(CCiudadano.APELLIDO1, ap1);
		}
		if ( request.getParameter(CCiudadano.APELLIDO2) != null ) {
			ap2 = (String) request.getParameter(CCiudadano.APELLIDO2);
			session.setAttribute(CCiudadano.APELLIDO2, ap2);
		}
		if ( request.getParameter(CCiudadano.NOMBRE) != null ) {
			nombre = (String) request.getParameter(CCiudadano.NOMBRE);
			session.setAttribute(CCiudadano.NOMBRE, nombre);
		}
                /*
                 * @author      :   Julio Alcázar Rivas
                 * @change      :   Se agrega el documento a la session
                 * Caso Mantis  :   000941
                 */
                if (request.getParameter(CSolicitudRegistro.TIPO_ENCABEZADO) != null ){
                    String tipo_encabezado = request.getParameter(CSolicitudRegistro.TIPO_ENCABEZADO);
                    session.setAttribute(CSolicitudRegistro.TIPO_ENCABEZADO, tipo_encabezado);
                }

                if (request.getParameter(CSolicitudRegistro.ID_ENCABEZADO) != null ){
                    String id_encabezado = request.getParameter(CSolicitudRegistro.ID_ENCABEZADO);
                    session.setAttribute(CSolicitudRegistro.ID_ENCABEZADO, id_encabezado);
                }

                if (request.getParameter(CSolicitudRegistro.CALENDAR) != null ){
                    String fecha = request.getParameter(CSolicitudRegistro.CALENDAR);
                    session.setAttribute(CSolicitudRegistro.CALENDAR, fecha);
                }

		if (request.getParameter(WebKeys.TIPO_OFICINA_INTERNACIONAL) != null ){
                     String nomOficinaInternacional = request.getParameter(WebKeys.TIPO_OFICINA_INTERNACIONAL);
		     session.setAttribute(WebKeys.TIPO_OFICINA_INTERNACIONAL, nomOficinaInternacional);
                }

                if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO) != null ){
                     String nomDepto = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
		     session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO, nomDepto);
                }

                if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO) != null ){
                     String idDepto = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
		     session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO, idDepto);
                }

                if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC) != null ){
                     String nomMunic = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
		     session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC, nomMunic);
                }

                if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC) != null ){
                     String idMunic = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
		     session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC, idMunic);
                }

                if (request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA) != null ){
                    String nomVereda = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
		    session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA, nomVereda);
                }

                if (request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA) != null ){
                    String idVereda = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
		    session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA, idVereda);
                }

                if (request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO) != null ){
                    String tipo_oficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO);
		    session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO, tipo_oficina);
                }

                if (request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM) != null ){
                    String numero_oficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM);
		    session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM, numero_oficina);
                }

                if (request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA) != null ){
                    String id_oficina =	request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
		   session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA, id_oficina);
                }
                /*
                *  @author Carlos Torres
                *  @chage   se agrega validacion de version diferente
                *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                */
                if (request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION) != null ){
                    String version =	request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION);
		   session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION, version);
                }
	
	}

        /**
         * @author      :   Julio Alcázar Rivas
         * @change      :   metodo formato fecha
         * Caso Mantis  :   000941
         * @param fechaInterfaz
	 * @return Calendar
	 */
	private Calendar darFecha(String fechaInterfaz) {
		java.util.Date date = null;

		try {
			date = DateFormatUtil.parse(fechaInterfaz);
			if(fechaInterfaz.indexOf("-")!=-1){
				return null;
			}
		} catch (ParseException e) {
			return null;
		} catch (Throwable t) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		String[] partido = fechaInterfaz.split("/");
		if (partido.length == 3) {
			int dia = Integer.parseInt(partido[0]);
			int mes = Integer.parseInt(partido[1]) - 1;
			int año = Integer.parseInt(partido[2]);
			calendar.set(año, mes, dia);

			if ((calendar.get(Calendar.YEAR) == año) && (calendar.get(Calendar.MONTH) == mes) && (calendar.get(Calendar.DAY_OF_MONTH) == dia)) {
				return calendar;
			}
		}
		return null;
	}
}
