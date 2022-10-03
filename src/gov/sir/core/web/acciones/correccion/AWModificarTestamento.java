/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.web.acciones.correccion;

import gov.sir.core.eventos.comun.EvnCiudadano;
import gov.sir.core.eventos.comun.EvnRespCiudadano;
import gov.sir.core.eventos.correccion.EvnModificarTestamento;
import gov.sir.core.eventos.correccion.EvnRespCorreccion;
import gov.sir.core.eventos.registro.EvnTestamentos;
import gov.sir.core.negocio.modelo.*;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.CTestamentos;
import gov.sir.core.negocio.modelo.constantes.CAnotacionCiudadano;
import gov.sir.core.negocio.modelo.constantes.CSolicitudRegistro;
import gov.sir.core.negocio.modelo.constantes.CSubtipoAtencion;
import gov.sir.core.negocio.modelo.constantes.CTipoDocumento;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.helpers.comun.ValidacionDatosOficinaOrigen;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.ParametroInvalidoException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosModificarFolioException;
import gov.sir.core.web.acciones.excepciones.ValidacionRegistroTestamentosException;
import gov.sir.core.web.acciones.registro.AWCalificacion;
import gov.sir.core.web.helpers.comun.ElementoLista;
import java.text.ParseException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

/**
 *
 * @author Carlos Torres
 */
public class AWModificarTestamento extends SoporteAccionWeb{
    
    public final static String GUARDAR_DATOS_TEMPORALES ="GUARDAR_DATOS_TEMPORALES";
    public final static String CARGAR_TESTAMENTO ="CARGAR_TESTAMENTO";
    public final static String SALVEDAD_TURNO_TESTAMENTO_ID = "SALVEDAD_TURNO_TESTAMENTO_ID";
    public final static String SALVEDAD_TURNO_TESTAMENTO_DESCRICION_ID="SALVEDAD_TURNO_TESTAMENTO_DESCRICION_ID";
    public final static String REGRESAR_CARGAR_TESTAMENTO = "REGRESAR_CARGAR_TESTAMENTO"; 
    public final static String AGREGAR_TESTADOR = "AGREGAR_TESTADOR";
    public final static String ELIMINAR_TESTADOR = "ELIMINAR_TESTADOR";
    public final static String REGRESAR_MESACONTROL_TESTAMENTO = "REGRESAR_MESACONTROL_TESTAMENTO";
    public final static String REGRESAR_REVISAR_APROBAR_TESTAMENTO = "REGRESAR_REVISAR_APROBAR_TESTAMENTO";
    public final static String REGRESAR_REVISION_ANALISIS_TESTAMENTO = "REGRESAR_REVISION_ANALISIS_TESTAMENTO";
    public static final String REFRESCAR_EDICION ="REFRESCAR_EDICION"; 
    public static final String VALIDAR_CIUDADANO = "VALIDAR_CIUDADANO";
    public static final String CONSULTAR_TESTADOR_OK = "CONSULTAR_TESTADOR_OK";
    public final static int ANIO_MINIMO = 1850;
    
    private String accion;
    private HttpSession session;

    @Override
    public Evento perform(HttpServletRequest request) throws AccionWebException {
        accion = request.getParameter(WebKeys.ACCION);
	session = request.getSession();
        
        if(accion.equals(CARGAR_TESTAMENTO))
        {
            return cargarTestamento(request);
        }else if(accion.equals(AGREGAR_TESTADOR))
        {
            return agregaTestador(request);
        }else if(accion.equals(ELIMINAR_TESTADOR))
        {
            return eliminaTestador(request);
        }else if(accion.equals(REGRESAR_CARGAR_TESTAMENTO)||accion.equals(REGRESAR_MESACONTROL_TESTAMENTO)||accion.equals(REGRESAR_REVISAR_APROBAR_TESTAMENTO))
        {
            return regresar(request);
        }else if(accion.equals(GUARDAR_DATOS_TEMPORALES))
        {
            return guardarDatosTmp(request);
        }   /*
                    *  @author Carlos Torres
                    *  @chage  condicion para la accion refrescar edicion
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
                    */
                else if(accion.equals(REFRESCAR_EDICION)){
			return refrescarEdicion(request);
		}
                 /*
                    *  @author Carlos Torres
                    *  @chage  condicion para la accion validar ciudadano
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
                    */
                else if(accion.equals(VALIDAR_CIUDADANO)){
			return validarCiudadano(request);
		}
        return null;
    }

    private EvnModificarTestamento guardarDatosTmp(HttpServletRequest request) throws AccionWebException{
        preservarInfoFormulario(request);
        Turno turno = (Turno) session.getAttribute( WebKeys.TURNO );
        Turno turnoAnterior = turno.getSolicitud().getTurnoAnterior();
        Documento docRegistro = ((SolicitudRegistro)turnoAnterior.getSolicitud()).getDocumento();
        /**********************************ENCABEZADO**********************************************************************************************/
        String nomDepto = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
        String idDepto = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);

        String nomMunic = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
        String idMunic = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);

        String nomVereda = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
        String idVereda = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);

        String idTipoOficina = request.getParameter(WebKeys.TIPO_OFICINA_I_N);
        idTipoOficina = (idTipoOficina == null) ? (idTipoOficina = "")
                                                : idTipoOficina;

        String oficinaInternacional = request.getParameter(WebKeys.TIPO_OFICINA_INTERNACIONAL);

        if ((idVereda == null) || nomDepto.equals("") || nomMunic.equals("") || idVereda.equals("")) {
            List listaVeredas = (List) request.getSession().getServletContext()
                                              .getAttribute(WebKeys.LISTA_DEPARTAMENTOS);
            idVereda = ValidacionDatosOficinaOrigen.obtenerVereda(listaVeredas,
                    idDepto, idMunic);
        }

        String tipo_oficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO);

        String numero_oficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM);
        String id_oficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
         /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */
        String oficinaVersion = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION);
        List tiposDoc = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO);
        String tipo_encabezado = request.getParameter(CSolicitudRegistro.TIPO_ENCABEZADO);
        String id_tipo_encabezado = request.getParameter(CSolicitudRegistro.ID_TIPO_ENCABEZADO);

        /*********************************TESTAMENTO***********************************************************************************************/
        Testamento testamento =(Testamento)session.getAttribute(WebKeys.TESTAMENTO_SESION);
        String salvedad = request.getParameter(SALVEDAD_TURNO_TESTAMENTO_DESCRICION_ID);
        String tomo = request.getParameter(CTestamentos.TOMO);
	String numAnotaciones = request.getParameter(CTestamentos.NUMERO_ANOTACIONES);
	String numCopias = request.getParameter(CTestamentos.NUMERO_COPIAS);
	String revocaEscritura = request.getParameter(CTestamentos.REVOCA_ESCRITURA);
	String observacion = request.getParameter(CTestamentos.OBSERVACION);
        
        ValidacionParametrosException exception = null;
        exception = new ValidacionParametrosException();
        /*********************************VALIDACIONES ENCABEZADO******************************************/
        
         if ((id_tipo_encabezado != null) && !id_tipo_encabezado.equals("")) {
            tipo_encabezado = existeId(tiposDoc, id_tipo_encabezado);

            if (tipo_encabezado.equals("")) {
                exception.addError(
                    "El tipo de documento digitado no correponde al tipo de documento seleccionado");
            }
        }

       

        String id_encabezado = request.getParameter(CFolio.ANOTACION_NUM_DOCUMENTO);
        String fecha = request.getParameter(CSolicitudRegistro.CALENDAR);

        id_encabezado = (id_encabezado == null) ? "" : id_encabezado;
        tipo_encabezado = (tipo_encabezado == null) ? "" : tipo_encabezado;
        id_tipo_encabezado = (id_tipo_encabezado == null) ? ""
                                                          : id_tipo_encabezado;

        if (tipo_encabezado.equals("SIN_SELECCIONAR") &&
                id_tipo_encabezado.equals("")) {
            exception.addError("El campo tipo del encabezado es inválido");
        }

        if (id_encabezado.trim().equals("")
        		&& !id_tipo_encabezado.equals(CTipoDocumento.ID_TIPO_SENTENCIA)
        		&& !id_tipo_encabezado.equals(CTipoDocumento.ID_TIPO_AUTO)
        		&& !id_tipo_encabezado.equals(CTipoDocumento.ID_TIPO_REMATE)) {
            exception.addError("El campo número del encabezado del documento no puede estar vacio");
        }

        if ((fecha == null) || fecha.trim().equals("")) {
            exception.addError("La fecha del encabezado es inválida");
        }

        // El usuario escoge tipo de oficina nacional
        if (idTipoOficina.equalsIgnoreCase(WebKeys.TIPO_OFICINA_NACIONAL)) {

				if ((idVereda == null) || idVereda.trim().equals("")) {
					exception.addError("La vereda o ciudad es inválida");
				}

				if ((idDepto == null) || idDepto.trim().equals("")) {
					exception.addError("El departamento es inválido");
				}

				if ((idMunic == null) || idMunic.trim().equals("")) {
					exception.addError("El municipio es inválido");
				}

				if ((id_oficina == null) || id_oficina.trim().equals("")) {
					exception.addError("El tipo de oficina es inválido");
				}

        } else {
            // tipo de oficina internacional
            if ((oficinaInternacional == null) ||
                    (oficinaInternacional.length() == 0)) {
                exception.addError(
                    "El campo Oficina Ubicación Internacional no puede estar en blanco");
            }
        }

		Calendar fechaDocumento;
		if(fecha != null)
			fechaDocumento = darFecha(fecha);
		else
			fechaDocumento = null;

        if (fechaDocumento == null) {
            exception.addError("La fecha del documento es invalida");
        } else if (fechaDocumento.get(Calendar.YEAR) < ANIO_MINIMO) {
        	exception.addError("La fecha del encabezado del documento no puede ser inferior a la fecha 01/01/" + ANIO_MINIMO + ".");
        }

        Documento doc = new Documento();
            doc.setIdDocumento(docRegistro.getIdDocumento());
            doc.setComentario(docRegistro.getComentario());
            doc.setFecha(fechaDocumento.getTime());
            doc.setCirculo(docRegistro.getCirculo());
            doc.setNumero(id_encabezado);
            
            TipoDocumento tipoDoc = new TipoDocumento();
            tipoDoc.setIdTipoDocumento(id_tipo_encabezado);
            tipoDoc.setNombre(tipo_encabezado);
            doc.setTipoDocumento(tipoDoc);
            
            doc.setOficinaInternacional(oficinaInternacional);
                OficinaOrigen oficeO = new OficinaOrigen();
                oficeO.setIdOficinaOrigen(id_oficina);
                 /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */
                oficeO.setVersion(Long.parseLong(oficinaVersion));
                TipoOficina tipo = new TipoOficina();
                tipo.setIdTipoOficina(idTipoOficina);
                tipo.setNombre(tipo_oficina);
                oficeO.setTipoOficina(tipo);
                
                Vereda vereda = new Vereda();
                vereda.setIdVereda(idVereda);
                vereda.setIdMunicipio(idMunic);
                vereda.setIdDepartamento(idDepto);
                
                vereda.setMunicipio(new Municipio());
                    vereda.getMunicipio().setIdMunicipio(idMunic);
                    vereda.getMunicipio().setDepartamento(new Departamento());
                        vereda.getMunicipio().getDepartamento().setIdDepartamento(idDepto);
                        
                oficeO.setVereda(vereda);
            doc.setOficinaOrigen(oficeO);
            
        //VALIDACIONES PARA EL TESTAMENTO
		if (tomo == null || tomo.trim().equals("")) {
			exception.addError("Debe ingresar el tomo.");
		}
		if (numAnotaciones == null || numAnotaciones.trim().equals("")) {
			exception.addError("Debe ingresar el número de anotaciones.");
		}
		if (numCopias == null || numCopias.trim().equals("")) {
			exception.addError("Debe ingresar el número de copias.");
		}
		if (revocaEscritura == null || revocaEscritura.trim().equals("")) {
			exception.addError("Debe determinar si revoca escritura.");
		}
		if (observacion == null || observacion.trim().equals("")) {
			exception.addError("Debe ingresar la observación.");
		} else if (observacion.length() >= 32765 ) {
			exception.addError("La observacion contiene " + observacion.length() + " caracteres. (Longitud máxima 32.765)");	
		}
                if(salvedad == null || salvedad.equals(""))
                {
                        exception.addError("Debe digitar una salvedad para la correccion de testamento");
                }else if(salvedad.length()<30)
                {
                        exception.addError("La salvedad debe tener minimo 30 caracteres");
                }
                int i = 0;
                for(Object ciu:testamento.getTestadores())
                {
                    Ciudadano d = (Ciudadano)ciu;
                    if("*E".equals(d.getTelefono()))
                    {
                        i++;
                    }
                }
                if(testamento.getTestadores()==null || i == testamento.getTestadores().size())
                {
                    exception.addError("Debe agregar testadores al testamento");
                }
                if (exception.getErrores().size() > 0) {
       		      throw exception;
		}
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga
        = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute( SMARTKeys.USUARIO_EN_SESION );
        gov.sir.core.negocio.modelo.Usuario usuarioSIR
        = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute( WebKeys.USUARIO );
        EvnModificarTestamento evn = new EvnModificarTestamento(usuarioAuriga,usuarioSIR,turno,EvnModificarTestamento.GUARDAR_DATOS_TEMPORALES_TESTAMENTO);
        evn.setTestamento(testamento);
        evn.setSalvedad(salvedad);
        evn.setEncabezadoDoc(doc);
        return evn;
    }
    private String existeId(List tiposDoc, String id_tipo_encabezado) {
            for (Iterator it = tiposDoc.iterator(); it.hasNext();) {
                ElementoLista e = (ElementoLista) it.next();
                int id = Integer.parseInt(e.getId());

                if (id == Integer.parseInt(id_tipo_encabezado)) {
                    return e.getId();
                }
            }

            return "";
        }
    /**
     * Convertir una cadena fecha en un Calendar de la fecha
     *
     * @param fechaInterfaz
     *            fecha que se va a convertir a Date en formato dd/MM/yyyy;
     * @return Calendar con la fecha
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

            if ((calendar.get(Calendar.YEAR) == año) &&
                    (calendar.get(Calendar.MONTH) == mes) &&
                    (calendar.get(Calendar.DAY_OF_MONTH) == dia)) {
                if (!calendar.after(Calendar.getInstance())) {
                    return calendar;
                }
            }
        }

        return null;
    }
    private EvnModificarTestamento cargarTestamento(HttpServletRequest request) throws AccionWebException{
        
        ValidacionParametrosException exception= new ValidacionParametrosException();
        Turno turno = (Turno) session.getAttribute( WebKeys.TURNO );
        if(request.getParameter(CFolio.ID_MATRICULA)==null)
        {
            exception.addError("No se ha seleccionado turno");
            throw exception;
        }
        if(request.getParameter("TS_EDITABLE")!=null && !request.getParameter("TS_EDITABLE").equals(""))
        {
           java.util.List modelPermisosList = null;
           modelPermisosList = (java.util.List)session.getAttribute(gov.sir.core.web.acciones.correccion.AwCorrecciones_Constants.PARAM__MODEL_PERMISOS_LIST );
           if(modelPermisosList == null)
           {
               modelPermisosList = ((SolicitudCorreccion)turno.getSolicitud()).getPermisos();
           }
           List permisos = new ArrayList();
           for(Object permiso:modelPermisosList)
           {
              permisos.add(((gov.sir.core.negocio.modelo.SolicitudPermisoCorreccion)permiso).getIdPermiso()); 
           }
            if(permisos.contains("TUR_TESTAMENTO")){
                request.getSession().setAttribute("editable", true);
            }else
            {
                request.getSession().setAttribute("editable", false);
            }
        }else
        {
            request.getSession().setAttribute("editable", false);
        }
        

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga
        = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute( SMARTKeys.USUARIO_EN_SESION );
        gov.sir.core.negocio.modelo.Usuario usuarioSIR
        = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute( WebKeys.USUARIO );
        EvnModificarTestamento evn = new EvnModificarTestamento(usuarioAuriga,
                                                                usuarioSIR,
                                                                turno,
                                                                EvnModificarTestamento.CARGAR_TESTAMENTO);
        return evn;
    }
    private Evento regresar(HttpServletRequest request) {
        eliminarInfoFomulario(request);
        return null;
    }
    /**
	 * Agrega testador
	 * @param request
	 * @return
	 */
	private Evento agregaTestador (HttpServletRequest request) throws AccionWebException{
		preservarInfoFormulario(request);
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga =
		(org.auriga.core.modelo.transferObjects.Usuario)
		request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
                Turno turno = (Turno) session.getAttribute( WebKeys.TURNO );
                 gov.sir.core.negocio.modelo.Usuario usuarioSIR
                = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute( WebKeys.USUARIO );
		String tipoId = request.getParameter(CCiudadano.TIPODOC);
		String numId = request.getParameter(CCiudadano.IDCIUDADANO);
		String apellido1 = request.getParameter(CCiudadano.APELLIDO1);
		String apellido2 = request.getParameter(CCiudadano.APELLIDO2);
		String nombres = request.getParameter(CCiudadano.NOMBRE);
		String telefono = request.getParameter(CCiudadano.TELEFONO);
                String idCiudadano = request.getParameter("ID_CIUDADANO");
		ValidacionRegistroTestamentosException exception = new ValidacionRegistroTestamentosException();
		if (tipoId == null || tipoId.length()==0 || tipoId.equals("SIN_SELECCIONAR")){
			exception.addError("Debe seleccionar tipo de identificación del testador");
		}else if (tipoId.equals(COPLookupCodes.SECUENCIAL_DOCUMENTO_IDENTIDAD)) {
			if (apellido1 == null || apellido1.trim().equals("")) {
				exception.addError("Debe ingresar el primer apellido del Ciudadano");
			}
		} else if (tipoId.equals(COPLookupCodes.NIT)) {
			if (apellido1 == null || apellido1.trim().equals("")) {
				exception.addError("Debe ingresar el primer apellido del Ciudadano");
			}
			if (numId == null || numId.trim().equals(""))
				exception.addError("Debe ingresar el número de identificacion del Ciudadano");
		} else {
			double valorId = 0.0d;
			if (numId == null || numId.trim().equals("")) {
				exception.addError("Debe ingresar el número de identificacion del Ciudadano");
			} else {
				if (!tipoId.equals(CCiudadano.TIPO_DOC_ID_TARJETA)) {

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
				throw new ParametroInvalidoException("Debe ingresar el primer apellido del Ciudadano");
			}
		}
		if (exception.getErrores().size() > 0) {
		    throw exception;
		}
		Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
		Ciudadano ciudadano = new Ciudadano();
		ciudadano.setTipoDoc(tipoId);
		ciudadano.setDocumento(numId);
		ciudadano.setApellido1(apellido1);
		ciudadano.setApellido2(apellido2);
		ciudadano.setNombre(nombres);
		if (telefono != null) {
			ciudadano.setTelefono(telefono);
		}
		ciudadano.setIdCiudadano(idCiudadano);
		ciudadano.setIdCirculo(circulo != null ? circulo.getIdCirculo() : null);
		
                borrarFormTestador();
		
                HttpSession session = request.getSession();
		Testamento testamento=(Testamento)session.getAttribute(WebKeys.TESTAMENTO_SESION);
		if(testamento==null)
			testamento=new Testamento();
		
                boolean sw = true;
                for(Object ciu:testamento.getTestadores())
                {
                    Ciudadano d = (Ciudadano)ciu;
                    
                    if(d.getDocumento()!=null && d.getDocumento().equals(ciudadano.getDocumento()) 
                            && d.getTipoDoc().equals(ciudadano.getTipoDoc())
                            && d.getIdCirculo().equals(ciudadano.getIdCirculo()))
                    {
                        d.setTelefono(null);
                        sw=false;
                    }
                }
                if(sw)
                {
                    testamento.getTestadores().add(ciudadano);
                }
		session.setAttribute(WebKeys.TESTAMENTO_SESION, testamento);
		return null;
	}
	
	private Evento eliminaTestador (HttpServletRequest request) throws AccionWebException{
         preservarInfoFormulario(request);
         Turno turno = (Turno) session.getAttribute( WebKeys.TURNO );
         org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga
         = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute( SMARTKeys.USUARIO_EN_SESION );
         gov.sir.core.negocio.modelo.Usuario usuarioSIR
         = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute( WebKeys.USUARIO );
         EvnModificarTestamento evn = new EvnModificarTestamento(usuarioAuriga,
                                                                usuarioSIR,
                                                                turno,
                                                                EvnModificarTestamento.ELIMINAR_TESTADOR);
		HttpSession session = request.getSession();
		Testamento testamento=(Testamento)session.getAttribute(WebKeys.TESTAMENTO_SESION);
		int indexToEliminarTestador=Integer.parseInt(request.getParameter(WebKeys.POSICION));
		Ciudadano ciudadano=(Ciudadano) testamento.getTestadores().get(indexToEliminarTestador);
		if(ciudadano!=null && ciudadano.getIdCiudadano()!=null){
                    ciudadano.setTelefono("*E");
                        TestamentoCiudadano testamentoCiudadano = new TestamentoCiudadano();
                        testamentoCiudadano.setIdCiudadano(ciudadano.getIdCiudadano());
                        testamentoCiudadano.setIdTestamento(testamento.getIdTestamento());
			evn.setTestamentoCiudadano(testamentoCiudadano);
		}else{
			evn.setTestamentoCiudadano(null);
		}
		evn.setTestamento(testamento);
		//testamento.getTestadores().remove(indexToEliminarTestador);
		return null;
	}
    public void preservarInfoFormulario(HttpServletRequest request)
    {
                session = request.getSession();
                /*****************************INFORMACION DE ENCABEZADO*********************************************************/
                SolicitudRegistro solicitudR = new SolicitudRegistro();
                Documento documento = new Documento();
                if(request.getParameter(CSolicitudRegistro.ID_TIPO_ENCABEZADO)!=null){
                    TipoDocumento tp = new TipoDocumento();
                    tp.setIdTipoDocumento(request.getParameter(CSolicitudRegistro.ID_TIPO_ENCABEZADO));
                    documento.setTipoDocumento(tp);
                }
		
		if(request.getParameter(CFolio.ANOTACION_NUM_DOCUMENTO)!=null){
      		      documento.setNumero(request.getParameter(CFolio.ANOTACION_NUM_DOCUMENTO));
                }
		if(request.getParameter(CSolicitudRegistro.CALENDAR)!=null){
      		   Calendar fechaDocumento = darFecha(request.getParameter(CSolicitudRegistro.CALENDAR));
                   documento.setFecha(fechaDocumento.getTime());
		}
                Vereda v = new Vereda();
                Municipio m = new Municipio();
                Departamento d = new Departamento();
                v.setMunicipio(m);
                m.setDepartamento(d);
                if(request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC)!=null)
                {
                    m.setIdMunicipio(request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC));
                }
                if(request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC)!=null)
                {
                     m.setNombre(request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC));
                }
                if(request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO)!=null)
                {
                    d.setIdDepartamento(request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO));
                }
                if(request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC)!=null)
                {
                    d.setNombre(request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC));
                }
                if(documento.getOficinaOrigen()==null)
                {
                    documento.setOficinaOrigen(new OficinaOrigen());
                }
                documento.getOficinaOrigen().setVereda(v);
                
	        if(request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO)!=null)
                {
                    if(documento.getOficinaOrigen()==null)
                    {
                        documento.setOficinaOrigen(new OficinaOrigen());
                    }
                    TipoOficina to = new TipoOficina();
                    to.setIdTipoOficina(request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO));
                    to.setNombre(request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO));
                    documento.getOficinaOrigen().setTipoOficina(to);
                }
         
                if(request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA)!=null){
                    if(documento.getOficinaOrigen()==null)
                    {
                        documento.setOficinaOrigen(new OficinaOrigen());
                    }
                    documento.getOficinaOrigen().setIdOficinaOrigen(request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA));
                    /*
                     *  @author Carlos Torres
                     *  @chage   se agrega validacion de version diferente
                     *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                     */
                    documento.getOficinaOrigen().setVersion(Long.parseLong(request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION)));
                    documento.getOficinaOrigen().setNombre(request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM));
                }
                   
                if(request.getParameter(CSolicitudRegistro.NUMERO_OFICINA)!=null)
                {
                    if(documento.getOficinaOrigen()==null)
                    {
                        documento.setOficinaOrigen(new OficinaOrigen());
                    }
                    documento.getOficinaOrigen().setNumero(request.getParameter(CSolicitudRegistro.NUMERO_OFICINA));
                }
                    
                solicitudR.setDocumento(documento);
                session.setAttribute(CTestamentos.SOLICITUD_MODIFICADA,solicitudR);
                /*****************************ENCABEZADO*********************************************************/
		Testamento testamento=(Testamento)session.getAttribute(WebKeys.TESTAMENTO_SESION);
		if(testamento!=null){
			if(request.getParameter(CTestamentos.TOMO)!=null)
				testamento.setTomo(request.getParameter(CTestamentos.TOMO));
			if(request.getParameter(CTestamentos.NUMERO_ANOTACIONES)!=null)
				testamento.setNumeroAnotaciones(request.getParameter(CTestamentos.NUMERO_ANOTACIONES));
			if(request.getParameter(CTestamentos.NUMERO_COPIAS)!=null)
				testamento.setNumeroCopias(request.getParameter(CTestamentos.NUMERO_COPIAS));
			if(request.getParameter(CTestamentos.REVOCA_ESCRITURA)!=null)
				testamento.setRevocaEscritura(request.getParameter(CTestamentos.REVOCA_ESCRITURA));
			if(request.getParameter(CTestamentos.OBSERVACION)!=null)
				testamento.setObservacion(request.getParameter(CTestamentos.OBSERVACION));
			if(request.getParameter(CTestamentos.COPIAS_IMPRIMIR)!=null)
				request.getSession().setAttribute(CTestamentos.COPIAS_IMPRIMIR,request.getParameter(CTestamentos.COPIAS_IMPRIMIR));
                        if(request.getParameter(SALVEDAD_TURNO_TESTAMENTO_DESCRICION_ID)!=null)
                            session.setAttribute(SALVEDAD_TURNO_TESTAMENTO_DESCRICION_ID, request.getParameter(SALVEDAD_TURNO_TESTAMENTO_DESCRICION_ID));
			session.setAttribute(WebKeys.TESTAMENTO_SESION,testamento);
		}
    }
    public void eliminarInfoFomulario(HttpServletRequest request)
    {
                session = request.getSession();
            
                /*****************************INFORMACION DE ENCABEZADO*********************************************************/
                session.removeAttribute(CTestamentos.SOLICITUD_MODIFICADA);
               /*****************************TESTAMENTO*********************************************************/
		Testamento testamento=(Testamento)session.getAttribute(WebKeys.TESTAMENTO_SESION);
		if(testamento!=null){
                 session.removeAttribute(accion);   
                }
    }
        /**
         * @param request
         * @return
         */
        private Evento refrescarEdicion(HttpServletRequest request) {
                //eliminarInfoBasicaAnotacion(request);
                String tipoId = request.getParameter(CCiudadano.TIPODOC);
                request.getSession().setAttribute(CCiudadano.TIPODOC, tipoId);
                preservarInfoFormulario(request);
                return null;
        }

    private Evento validarCiudadano(HttpServletRequest request) throws ValidacionRegistroTestamentosException {
        //eliminarInfoBasicaAnotacion(request);

                Usuario usuario = (Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
                org.auriga.core.modelo.transferObjects.Usuario usuarioArq= (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
                ValidacionRegistroTestamentosException exception = new ValidacionRegistroTestamentosException();
                String ver = request.getParameter(CAnotacionCiudadano.SECUENCIA);
                preservarInfoFormulario(request);

                String tipoDoc = request.getParameter(CCiudadano.TIPODOC);

                if ((tipoDoc == null) || tipoDoc.equals(WebKeys.SIN_SELECCIONAR)) {
                        exception.addError("Debe escoger un tipo de identificación para la persona en la anotacion");
                }

                String numDoc = request.getParameter(CCiudadano.IDCIUDADANO);
                if ((numDoc == null) || numDoc.equals("")) {
                        exception.addError("Debe digitar un número de identificación");
                }

                if(!exception.getErrores().isEmpty()){
                        preservarInfoFormulario(request);
                        throw exception;
                }

                request.getSession().setAttribute(CCiudadano.IDCIUDADANO, numDoc);

                Ciudadano ciud = new Ciudadano();
                ciud.setDocumento(numDoc);
                ciud.setTipoDoc(tipoDoc);

           		//Se setea el circulo del ciudadano
        		Circulo circulo = (Circulo)request.getSession().getAttribute(WebKeys.CIRCULO);
        		ciud.setIdCirculo(circulo!=null?circulo.getIdCirculo():null);

        		EvnCiudadano evnt = new EvnCiudadano(usuarioArq, EvnCiudadano.CONSULTAR_TESTADOR, ciud);
        		evnt.setCorrecciones(true);
        		evnt.setTurno((Turno)request.getSession().getAttribute(WebKeys.TURNO));
                return evnt;
    }
    public void borrarFormTestador()
    {
        session.setAttribute(CCiudadano.TIPODOC,"");
        session.setAttribute("ID_CIUDADANO", "");
        session.setAttribute(CCiudadano.IDCIUDADANO,"");
        session.setAttribute(CCiudadano.NOMBRE,"");
        session.setAttribute(CCiudadano.APELLIDO1,"");
        session.setAttribute(CCiudadano.APELLIDO2,"");
    }
    @Override
    public void doEnd(HttpServletRequest request, EventoRespuesta evento) 
    {
        if (evento instanceof EvnRespCorreccion) {
            EvnRespCorreccion respuesta = (EvnRespCorreccion)evento;
            if(respuesta.getTipoEvento().equals(EvnRespCorreccion.CARGAR_TESTAMENTO_OK))
            {
                Map salvedad = respuesta.getSalvedad();
                String copiasImpr = respuesta.getNextOrden();
                request.getSession().setAttribute(WebKeys.TESTAMENTO_SESION,respuesta.getTestamento());
                Solicitud solicitud = respuesta.getSolicitudCorreccion();
                if(salvedad !=null){
                    request.getSession().setAttribute(SALVEDAD_TURNO_TESTAMENTO_DESCRICION_ID, salvedad.get("SLTS_DESCRIPCION"));
                }
                if(copiasImpr !=null)
                {
                    request.getSession().setAttribute(CTestamentos.COPIAS_IMPRIMIR, copiasImpr);
                }
                if(solicitud!=null)
                {
                    request.getSession().setAttribute(CTestamentos.SOLICITUD_MODIFICADA, solicitud);
                }
            }
            }else if(evento instanceof EvnRespCiudadano){ 
                EvnRespCiudadano respCiudadano = (gov.sir.core.eventos.comun.EvnRespCiudadano) evento;;
                if (respCiudadano!=null && respCiudadano.getTipoEvento().equals(CONSULTAR_TESTADOR_OK) ){
			Ciudadano ciudadano = respCiudadano.getCiudadano();
			session.setAttribute(CCiudadano.TIPODOC,ciudadano.getTipoDoc());
                        session.setAttribute("ID_CIUDADANO", ciudadano.getIdCiudadano());
			session.setAttribute(CCiudadano.IDCIUDADANO,ciudadano.getDocumento());
			session.setAttribute(CCiudadano.NOMBRE,ciudadano.getNombre());
			session.setAttribute(CCiudadano.APELLIDO1,ciudadano.getApellido1());
			session.setAttribute(CCiudadano.APELLIDO2,ciudadano.getApellido2());
			
		}
            }
    
    }

    
}
