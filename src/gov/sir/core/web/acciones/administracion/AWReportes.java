package gov.sir.core.web.acciones.administracion;

import co.com.iridium.generalSIR.comun.GeneralSIRException;
import co.com.iridium.generalSIR.negocio.comun.UsuarioSIR;
import gov.sir.core.negocio.modelo.constantes.CCirculo;
import gov.sir.core.negocio.modelo.constantes.CUsuario;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.AdminReportes_InvalidParametersExceptionCollector;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;
import gov.sir.core.web.acciones.excepciones.ValidacionZonaRegistralException;
import gov.sir.core.negocio.modelo.constantes.CVereda;
import gov.sir.core.negocio.modelo.constantes.CZonaRegistral;
import gov.sir.core.negocio.modelo.constantes.CDepartamento;
import gov.sir.core.negocio.modelo.constantes.CMunicipio;
import gov.sir.core.negocio.modelo.constantes.CTipoOficina;
import gov.sir.core.negocio.modelo.constantes.CNaturalezaJuridica;
import gov.sir.core.negocio.modelo.Municipio;
import org.auriga.smart.SMARTKeys;
import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.web.acciones.excepciones.ValidacionOficinaOrigenException;
import gov.sir.core.web.acciones.excepciones.ValidacionConsultaUsuariosException;
import gov.sir.core.negocio.modelo.constantes.COficinaOrigen;
import org.auriga.core.modelo.transferObjects.Usuario;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.TipoOficina;
import gov.sir.core.eventos.administracion.EvnAdministracionForseti;
import gov.sir.core.negocio.modelo.constantes.CCategoria;
import gov.sir.core.negocio.modelo.Categoria;
import gov.sir.core.negocio.modelo.Departamento;
import gov.sir.core.negocio.modelo.ZonaRegistral;
import javax.servlet.ServletContext;
import gov.sir.core.web.helpers.comun.ElementoLista;
import gov.sir.core.eventos.administracion.EvnRespAdministracionForseti;
import gov.sir.core.negocio.modelo.ValidacionNota;
import gov.sir.core.web.acciones.excepciones.ValidacionValidaNotaException;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.eventos.administracion.EvnAdministracionHermod;
import gov.sir.core.negocio.modelo.constantes.CFaseProceso;
import gov.sir.core.negocio.modelo.constantes.CValidacionNota;
import gov.sir.core.eventos.administracion.EvnRespAdministracionHermod;
import gov.sir.core.negocio.modelo.constantes.CTipoNota;
import gov.sir.core.negocio.modelo.FaseProceso;
import gov.sir.core.eventos.administracion.EvnReportes;
import gov.sir.core.eventos.administracion.EvnRespReportes;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.modelo.constantes.CRol;
import gov.sir.core.negocio.modelo.constantes.CRoles;
import gov.sir.core.negocio.modelo.util.Log;

import org.auriga.core.modelo.transferObjects.Rol;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoPk;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import gov.sir.core.negocio.modelo.Relacion;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import java.util.GregorianCalendar;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import gov.sir.core.eventos.administracion.EvnAdministracionFenrir;
import gov.sir.core.negocio.modelo.*;
import gov.sir.core.negocio.modelo.constantes.*;
import gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced;
import java.util.Calendar;

/**
 * @autor HGOMEZ
 * @mantis 13407
 * @Requerimiento 064_453_Duplicidad_Nombre_Circulo
 * @descripcion Se importa ValidacionesSIR para tener acceso a los circulos y
 * departamentos asociados a los mismos.
 */
import java.util.Vector;
import gov.sir.core.util.DepartamentosPorCirculoSingletonUtil;

/**
 * Acción Web encargada de manejar solicitudes para generar eventos de consultas
 * a Folios.
 *
 * @author jvelez
 */
public class AWReportes extends SoporteAccionWeb {

    private ForsetiServiceInterface forseti;
    private ServiceLocator service = null;

    public static final String REPORTE_XX__ITEM_CIRCULOSBYUSUARIO = "REPORTE_XX__ITEM_CIRCULOSBYUSUARIO";
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String reportesServletPath;
    public static final String REPORTE_TIPO = "pdf";
    public static final String REPORTE_XX__PARAM_CMDKEY = "cmdkey";
    public static final String REPORTE_XX__BACK_ACTION = "REPORTE_XX__BACK_ACTION";
    public static final String REPORTE_00__BACK_ACTION = "REPORTE_00__BACK_ACTION";
    public static final String REPORTE_01__SEND_ACTION = "REPORTE_01__SEND_ACTION";
    public static final String REPORTE_01__BACK_ACTION = "REPORTE_01__BACK_ACTION";
    public static final String REPORTE_04__SEND_ACTION = "REPORTE_04__SEND_ACTION";
    public static final String REPORTE_04__BACK_ACTION = "REPORTE_04__BACK_ACTION";
    public static final String REPORTE_05__SEND_ACTION = "REPORTE_05__SEND_ACTION";
    public static final String REPORTE_05__BACK_ACTION = "REPORTE_05__BACK_ACTION";
    public static final String REPORTE_06__SEND_ACTION = "REPORTE_06__SEND_ACTION";
    public static final String REPORTE_06__BACK_ACTION = "REPORTE_06__BACK_ACTION";
    public static final String REPORTE_6B__SEND_ACTION = "REPORTE_6B__SEND_ACTION";
    public static final String REPORTE_6B__BACK_ACTION = "REPORTE_6B__BACK_ACTION";
    public static final String REPORTE_07__SEND_ACTION = "REPORTE_07__SEND_ACTION";
    public static final String REPORTE_07__BACK_ACTION = "REPORTE_07__BACK_ACTION";
    public static final String REPORTE_08__SEND_ACTION = "REPORTE_08__SEND_ACTION";
    public static final String REPORTE_08__BACK_ACTION = "REPORTE_08__BACK_ACTION";
    public static final String REPORTE_09__SEND_ACTION = "REPORTE_09__SEND_ACTION";
    public static final String REPORTE_09__BACK_ACTION = "REPORTE_09__BACK_ACTION";
    public static final String REPORTE_10__SEND_ACTION = "REPORTE_10__SEND_ACTION";
    public static final String REPORTE_10__BACK_ACTION = "REPORTE_10__BACK_ACTION";
    public static final String REPORTE_11__SEND_ACTION = "REPORTE_11__SEND_ACTION";
    public static final String REPORTE_11__BACK_ACTION = "REPORTE_11__BACK_ACTION";
    public static final String REPORTE_15__SEND_ACTION = "REPORTE_15__SEND_ACTION";
    public static final String REPORTE_15__BACK_ACTION = "REPORTE_15__BACK_ACTION";
    public static final String REPORTE_16__SEND_ACTION = "REPORTE_16__SEND_ACTION";
    public static final String REPORTE_16__BACK_ACTION = "REPORTE_16__BACK_ACTION";
    public static final String REPORTE_17__SEND_ACTION = "REPORTE_17__SEND_ACTION";
    public static final String REPORTE_17__BACK_ACTION = "REPORTE_17__BACK_ACTION";
    public static final String REPORTE_18__SEND_ACTION = "REPORTE_18__SEND_ACTION";
    public static final String REPORTE_18__BACK_ACTION = "REPORTE_18__BACK_ACTION";
    public static final String REPORTE_19__SEND_ACTION = "REPORTE_19__SEND_ACTION";
    public static final String REPORTE_19__BACK_ACTION = "REPORTE_19__BACK_ACTION";
    public static final String REPORTE_20__SEND_ACTION = "REPORTE_20__SEND_ACTION";
    public static final String REPORTE_20__BACK_ACTION = "REPORTE_20__BACK_ACTION";
    public static final String REPORTE_21__SEND_ACTION = "REPORTE_21__SEND_ACTION";
    public static final String REPORTE_21__BACK_ACTION = "REPORTE_21__BACK_ACTION";
    public static final String REPORTE_24__SEND_ACTION = "REPORTE_24__SEND_ACTION";
    public static final String REPORTE_24__BACK_ACTION = "REPORTE_24__BACK_ACTION";
    public static final String REPORTE_25__SEND_ACTION = "REPORTE_25__SEND_ACTION";
    public static final String REPORTE_25__BACK_ACTION = "REPORTE_25__BACK_ACTION";
    public static final String REPORTE_22__SEND_ACTION = "REPORTE_22__SEND_ACTION";
    public static final String REPORTE_22__BACK_ACTION = "REPORTE_22__BACK_ACTION";
    public static final String REPORTE_26__SEND_ACTION = "REPORTE_26__SEND_ACTION";
    public static final String REPORTE_26__BACK_ACTION = "REPORTE_26__BACK_ACTION";
    public static final String REPORTE_27__SEND_ACTION = "REPORTE_27__SEND_ACTION";
    public static final String REPORTE_27__BACK_ACTION = "REPORTE_27__BACK_ACTION";
    public static final String REPORTE_30__SEND_ACTION = "REPORTE_30__SEND_ACTION";
    public static final String REPORTE_30__BACK_ACTION = "REPORTE_30__BACK_ACTION";
    public static final String REPORTE_30__ONSELECT_ZONAREGISTRAL_DEPARTAMENTO_ACTION = "REPORTE_30__ONSELECT_ZONAREGISTRAL_DEPARTAMENTO_ACTION";
    public static final String REPORTE_30__ONSELECT_ZONAREGISTRAL_MUNICIPIO_ACTION = "REPORTE_30__ONSELECT_ZONAREGISTRAL_MUNICIPIO_ACTION";
    public static final String REPORTE_30__ONSELECT_ZONAREGISTRAL_VEREDA_ACTION = "REPORTE_30__ONSELECT_ZONAREGISTRAL_VEREDA_ACTION";
    public static final String REPORTE_31__SEND_ACTION = "REPORTE_31__SEND_ACTION";
    public static final String REPORTE_31__BACK_ACTION = "REPORTE_31__BACK_ACTION";
    public static final String REPORTE_32__SEND_ACTION = "REPORTE_32__SEND_ACTION";
    public static final String REPORTE_32__BACK_ACTION = "REPORTE_32__BACK_ACTION";
    public static final String REPORTE_35__SEND_ACTION = "REPORTE_35__SEND_ACTION";
    public static final String REPORTE_35__BACK_ACTION = "REPORTE_35__BACK_ACTION";
    public static final String REPORTE_37__SEND_ACTION = "REPORTE_37__SEND_ACTION";
    public static final String REPORTE_37__BACK_ACTION = "REPORTE_37__BACK_ACTION";
    public static final String REPORTE_38__SEND_ACTION = "REPORTE_38__SEND_ACTION";
    public static final String REPORTE_38__BACK_ACTION = REPORTE_XX__BACK_ACTION;
    public static final String REPORTE_39__SEND_ACTION = "REPORTE_39__SEND_ACTION";
    public static final String REPORTE_39__BACK_ACTION = "REPORTE_39__BACK_ACTION";
    public static final String REPORTE_41__SEND_ACTION = "REPORTE_41__SEND_ACTION";
    public static final String REPORTE_41__BACK_ACTION = REPORTE_XX__BACK_ACTION;
    public static final String REPORTE_42__SEND_ACTION = "REPORTE_42__SEND_ACTION";
    public static final String REPORTE_42__BACK_ACTION = REPORTE_XX__BACK_ACTION;
    public static final String REPORTE_43__SEND_ACTION = "REPORTE_43__SEND_ACTION";
    public static final String REPORTE_43__BACK_ACTION = REPORTE_XX__BACK_ACTION;
    public static final String REPORTE_44__SEND_ACTION = "REPORTE_44__SEND_ACTION";
    public static final String REPORTE_44__BACK_ACTION = REPORTE_XX__BACK_ACTION;
    public static final String REPORTE_45__SEND_ACTION = "REPORTE_45__SEND_ACTION";
    public static final String REPORTE_45__BACK_ACTION = "REPORTE_45__BACK_ACTION";
    public static final String REPORTE_46__SEND_ACTION = "REPORTE_46__SEND_ACTION";
    public static final String REPORTE_46__BACK_ACTION = "REPORTE_46__BACK_ACTION";
    public static final String REPORTE_48__SEND_ACTION = "REPORTE_48__SEND_ACTION";
    public static final String REPORTE_48__BACK_ACTION = REPORTE_XX__BACK_ACTION;
    public static final String REPORTE_49__SEND_ACTION = "REPORTE_49__SEND_ACTION";
    public static final String REPORTE_49__BACK_ACTION = "REPORTE_49__BACK_ACTION";
    public static final String REPORTE_51__SEND_ACTION = "REPORTE_51__SEND_ACTION";
    public static final String REPORTE_51__BACK_ACTION = "REPORTE_51__BACK_ACTION";
    public static final String REPORTE_51B__SEND_ACTION = "REPORTE_51B__SEND_ACTION";
    public static final String REPORTE_51B__BACK_ACTION = "REPORTE_51B__BACK_ACTION";
    public static final String REPORTE_57__SEND_ACTION = "REPORTE_57__SEND_ACTION";
    public static final String REPORTE_57__BACK_ACTION = "REPORTE_57__BACK_ACTION";
    public static final String REPORTE_69__SEND_ACTION = "REPORTE_69__SEND_ACTION";
    public static final String REPORTE_69__BACK_ACTION = "REPORTE_69__BACK_ACTION";
    public static final String REPORTE_74__SEND_ACTION = "REPORTE_74__SEND_ACTION";
    public static final String REPORTE_74__BACK_ACTION = "REPORTE_74__BACK_ACTION";
    public static final String REPORTE_79__SEND_ACTION = "REPORTE_79__SEND_ACTION";
    public static final String REPORTE_79__BACK_ACTION = "REPORTE_79__BACK_ACTION";
    public static final String REPORTE_81__SEND_ACTION = "REPORTE_81__SEND_ACTION";
    public static final String REPORTE_81__BACK_ACTION = REPORTE_XX__BACK_ACTION;
    public static final String REPORTE_87__SEND_ACTION = "REPORTE_87_SEND_ACTION";
    public static final String REPORTE_87__BACK_ACTION = "REPORTE_87__BACK_ACTION";
    public static final String REPORTE_88__SEND_ACTION = "REPORTE_88__SEND_ACTION";
    public static final String REPORTE_88__BACK_ACTION = "REPORTE_88__BACK_ACTION";
    public static final String REPORTE_89__SEND_ACTION = "REPORTE_89__SEND_ACTION";
    public static final String REPORTE_89__BACK_ACTION = "REPORTE_89__BACK_ACTION";
    public static final String REPORTE_90__SEND_ACTION = "REPORTE_90__SEND_ACTION";
    public static final String REPORTE_90__BACK_ACTION = "REPORTE_90__BACK_ACTION";
    public static final String REPORTE_91__SEND_ACTION = "REPORTE_91__SEND_ACTION";
    public static final String REPORTE_91__BACK_ACTION = "REPORTE_91__BACK_ACTION";
    public static final String REPORTE_92__SEND_ACTION = "REPORTE_92__SEND_ACTION";
    public static final String REPORTE_92__BACK_ACTION = "REPORTE_92__BACK_ACTION";
    public static final String REPORTE_93__SEND_ACTION = "REPORTE_93__SEND_ACTION";
    public static final String REPORTE_93__BACK_ACTION = "REPORTE_93__BACK_ACTION";
    public static final String REPORTE_94__SEND_ACTION = "REPORTE_94__SEND_ACTION";
    public static final String REPORTE_94__BACK_ACTION = "REPORTE_94__BACK_ACTION";
    public static final String REPORTE_95__SEND_ACTION = "REPORTE_95__SEND_ACTION";
    public static final String REPORTE_95__BACK_ACTION = REPORTE_XX__BACK_ACTION;
    public static final String REPORTE_96__SEND_ACTION = "REPORTE_96__SEND_ACTION";
    public static final String REPORTE_96__BACK_ACTION = REPORTE_XX__BACK_ACTION;
    public static final String REPORTE_97__SEND_ACTION = "REPORTE_97__SEND_ACTION";
    public static final String REPORTE_97__BACK_ACTION = REPORTE_XX__BACK_ACTION;
    public static final String REPORTE_99__SEND_ACTION = "REPORTE_99__SEND_ACTION";
    public static final String REPORTE_99__BACK_ACTION = "REPORTE_99__BACK_ACTION";
    public static final String REPORTE_109__SEND_ACTION = "REPORTE_109__SEND_ACTION";
    public static final String REPORTE_109__BACK_ACTION = "REPORTE_109__BACK_ACTION";
    public static final String REPORTE_110__SEND_ACTION = "REPORTE_110__SEND_ACTION";
    public static final String REPORTE_110__BACK_ACTION = "REPORTE_110__BACK_ACTION";
    public static final String REPORTE_121__SEND_ACTION = "REPORTE_121__SEND_ACTION";
    public static final String REPORTE_121__BACK_ACTION = "REPORTE_121__BACK_ACTION";
    public static final String REPORTE_123__SEND_ACTION = "REPORTE_123__SEND_ACTION";
    public static final String REPORTE_123__BACK_ACTION = "REPORTE_123__BACK_ACTION";
    public static final String REPORTE_124__SEND_ACTION = "REPORTE_124__SEND_ACTION";
    public static final String REPORTE_124__BACK_ACTION = "REPORTE_124__BACK_ACTION";
    public static final String REPORTE_131__SEND_ACTION = "REPORTE_131__SEND_ACTION";
    public static final String REPORTE_131__BACK_ACTION = REPORTE_XX__BACK_ACTION;
    public static final String REPORTE_142__SEND_ACTION = "REPORTE_142__SEND_ACTION";
    public static final String REPORTE_142__BACK_ACTION = "REPORTE_142__BACK_ACTION";
    public static final String REPORTE_145__SEND_ACTION = "REPORTE_145__SEND_ACTION";
    public static final String REPORTE_145__BACK_ACTION = "REPORTE_145__BACK_ACTION";
    public static final String REPORTE_151__SEND_ACTION = "REPORTE_151__SEND_ACTION";
    public static final String REPORTE_151__BACK_ACTION = "REPORTE_151__BACK_ACTION";
    public static final String REPORTE_154__SEND_ACTION = "REPORTE_154__SEND_ACTION";
    public static final String REPORTE_154__BACK_ACTION = "REPORTE_154__BACK_ACTION";
    public static final String REPORTE_155__SEND_ACTION = "REPORTE_155__SEND_ACTION";
    public static final String REPORTE_155__BACK_ACTION = "REPORTE_155__BACK_ACTION";
    public static final String REPORTE_156__SEND_ACTION = "REPORTE_156__SEND_ACTION";
    public static final String REPORTE_156__BACK_ACTION = "REPORTE_156__BACK_ACTION";
    public static final String REPORTE_REL02__SEND_ACTION = "REPORTE_REL02__SEND_ACTION";
    public static final String REPORTE_REL02__BACK_ACTION = "REPORTE_REL02__BACK_ACTION";
    public static final String REPORTE_G01__SEND_ACTION = "REPORTE_G01__SEND_ACTION";
    public static final String REPORTE_G01__BACK_ACTION = "REPORTE_G01__BACK_ACTION";
    public static final String REPORTE_G02__SEND_ACTION = "REPORTE_G02__SEND_ACTION";
    public static final String REPORTE_G02__BACK_ACTION = "REPORTE_G02__BACK_ACTION";
    public static final String REPORTE_G03__SEND_ACTION = "REPORTE_G03__SEND_ACTION";
    public static final String REPORTE_G03__BACK_ACTION = "REPORTE_G03__BACK_ACTION";
    public static final String REPORTE_G04__SEND_ACTION = "REPORTE_G04__SEND_ACTION";
    public static final String REPORTE_G04__BACK_ACTION = "REPORTE_G04__BACK_ACTION";
    public static final String REPORTE_G05__SEND_ACTION = "REPORTE_G05__SEND_ACTION";
    public static final String REPORTE_G05__BACK_ACTION = "REPORTE_G05__BACK_ACTION";
    public static final String REPORTE_G06__SEND_ACTION = "REPORTE_G06__SEND_ACTION";
    public static final String REPORTE_G06__BACK_ACTION = "REPORTE_G06__BACK_ACTION";
    public static final String REPORTE_RUPTA01__SEND_ACTION = "REPORTE_RUPTA01__SEND_ACTION";
    public static final String REPORTE_RUPTA01__BACK_ACTION = "REPORTE_RUPTA01__BACK_ACTION";
    public static final String REPORTE_157__SEND_ACTION = "REPORTE_157__SEND_ACTION";
    public static final String REPORTE_157__BACK_ACTION = "REPORTE_157__BACK_ACTION";
    public static final String REPORTE_158__SEND_ACTION = "REPORTE_158__SEND_ACTION";
    public static final String REPORTE_158__BACK_ACTION = "REPORTE_158__BACK_ACTION";
    public static final String REPORTE_159__SEND_ACTION = "REPORTE_159__SEND_ACTION";
    public static final String REPORTE_159__BACK_ACTION = "REPORTE_159__BACK_ACTION";
    public static final String REPORTE_160__SEND_ACTION = "REPORTE_160__SEND_ACTION";
    public static final String REPORTE_160__BACK_ACTION = "REPORTE_160__BACK_ACTION";
    public static final String REPORTE_180__SEND_ACTION = "REPORTE_180__SEND_ACTION";
    public static final String REPORTE_180__BACK_ACTION = "REPORTE_180__BACK_ACTION";
    public static final String REPORTE_181__SEND_ACTION = "REPORTE_181__SEND_ACTION";
    public static final String REPORTE_181__BACK_ACTION = "REPORTE_181__BACK_ACTION";
    public static final String REPORTE_182__SEND_ACTION = "REPORTE_182__SEND_ACTION";    
    public static final String REPORTE_182__BACK_ACTION = "REPORTE_182__BACK_ACTION";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 19/08/2009
    // Reporte Folios Masivos
    public static final String REPORTE_FOLIOS_MAS__SEND_ACTION = "REPORTE_FOLIOS_MAS__SEND_ACTION";
    public static final String REPORTE_FOLIOS_MAS__BACK_ACTION = "REPORTE_FOLIOS_MAS__BACK_ACTION";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 22/09/2009
    // Reporte E01
    public static final String REPORTE_E01__SEND_ACTION = "REPORTE_E01__SEND_ACTION";
    public static final String REPORTE_E01__BACK_ACTION = "REPORTE_E01__BACK_ACTION";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 22/09/2009
    // Reporte 161
    public static final String REPORTE_161__SEND_ACTION = "REPORTE_161__SEND_ACTION";
    public static final String REPORTE_161__BACK_ACTION = "REPORTE_161__BACK_ACTION";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 26/04/2010
    // Reporte E02
    public static final String REPORTE_E02__SEND_ACTION = "REPORTE_E02__SEND_ACTION";
    public static final String REPORTE_E02__BACK_ACTION = "REPORTE_E02__BACK_ACTION";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 02/08/2010
    // Reporte E03
    public static final String REPORTE_E03__SEND_ACTION = "REPORTE_E03__SEND_ACTION";
    public static final String REPORTE_E03__BACK_ACTION = "REPORTE_E03__BACK_ACTION";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 05/01/2011
    // Reporte 162
    public static final String REPORTE_162__SEND_ACTION = "REPORTE_162__SEND_ACTION";
    public static final String REPORTE_162__BACK_ACTION = "REPORTE_162__BACK_ACTION";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 15/02/2011
    // Reporte 163
    public static final String REPORTE_163__SEND_ACTION = "REPORTE_163__SEND_ACTION";
    public static final String REPORTE_163__BACK_ACTION = "REPORTE_163__BACK_ACTION";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 23/02/2011
    // Reporte 164
    public static final String REPORTE_164__SEND_ACTION = "REPORTE_164__SEND_ACTION";
    public static final String REPORTE_164__BACK_ACTION = "REPORTE_164__BACK_ACTION";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 14/03/2011
    // Reporte 165
    public static final String REPORTE_165__SEND_ACTION = "REPORTE_165__SEND_ACTION";
    public static final String REPORTE_165__BACK_ACTION = "REPORTE_165__BACK_ACTION";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 14/03/2011
    // Reporte 166
    public static final String REPORTE_166__SEND_ACTION = "REPORTE_166__SEND_ACTION";
    public static final String REPORTE_166__BACK_ACTION = "REPORTE_166__BACK_ACTION";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 14/03/2011
    // Reporte 167
    public static final String REPORTE_167__SEND_ACTION = "REPORTE_167__SEND_ACTION";
    public static final String REPORTE_167__BACK_ACTION = "REPORTE_167__BACK_ACTION";
    // AHERRENO 14/03/2011
    // Reporte 168
    public static final String REPORTE_168__SEND_ACTION = "REPORTE_168__SEND_ACTION";
    public static final String REPORTE_168__BACK_ACTION = "REPORTE_168__BACK_ACTION";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 01/06/2012
    // Reporte 169
    public static final String REPORTE_169__SEND_ACTION = "REPORTE_169__SEND_ACTION";
    public static final String REPORTE_169__BACK_ACTION = "REPORTE_169__BACK_ACTION";
    // --------------------------------------------------------------------------------------------------
    // CTORRES 24/04/2013
    // Reporte 175
    public static final String REPORTE_175__SEND_ACTION = "REPORTE_175__SEND_ACTION";
    public static final String REPORTE_175__BACK_ACTION = "REPORTE_175__BACK_ACTION";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 24/04/2013
    // Reporte 176
    public static final String REPORTE_176__SEND_ACTION = "REPORTE_176__SEND_ACTION";
    public static final String REPORTE_176__BACK_ACTION = "REPORTE_176__BACK_ACTION";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_REL01__PARAM_RELACIONID = "PARAM_RELACIONID";
    public static final String REPORTE_REL01__PARAM_FASESELECTED = "PARAM_FASESELECTED";
    // ---------
    public static final String REPORTE_REL01__PARAM_FASESLIST = "PARAM_FASESLIST";
    // ---------
    private final static String REPORTE_REL01__PARAMREPORT_PRELACIONID = "P_RELACION";
    private final static String REPORTE_REL01__PARAMREPORT_PPROCESOID = "P_PROCESO";
    private final static String REPORTE_REL01__PARAMREPORT_PFASEID = "P_FASE";
    private final static String REPORTE_REL01__PARAMREPORT_PCIRCULOID = "P_CIRCULO";
    // ---------
    public static final String REPORTE_REL01__SEND_ACTION = "REPORTE_REL01__SEND_ACTION";
    public static final String REPORTE_REL01__BACK_ACTION = "REPORTE_REL01__BACK_ACTION";
    public static final String REPORTE_REL01__FINDRELATEDFASES_ACTION = "REPORTE_REL01__FINDRELATEDFASES_ACTION";

    // --------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------
    /*AHERRENO 09/04/2013
      REQ 064_453 DUPLICIDAD TURNOS*/
    public static final String REPORTE_01_LOADCIRCULOSBYUSUARIO = "REPORTE_01_LOADCIRCULOSBYUSUARIO";
    //---------------------------------------------------------------------------------------------------
    public static final String REPORTE_06_LOADCIRCULOSBYUSUARIO = "REPORTE_06_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_04_LOADCIRCULOSBYUSUARIO = "REPORTE_04_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_05_LOADCIRCULOSBYUSUARIO = "REPORTE_05_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_6B_LOADCIRCULOSBYUSUARIO = "REPORTE_6B_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_07_LOADCIRCULOSBYUSUARIO = "REPORTE_07_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_08_LOADCIRCULOSBYUSUARIO = "REPORTE_08_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_09_LOADCIRCULOSBYUSUARIO = "REPORTE_09_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_10_LOADCIRCULOSBYUSUARIO = "REPORTE_10_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_11_LOADCIRCULOSBYUSUARIO = "REPORTE_11_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_15_LOADCIRCULOSBYUSUARIO = "REPORTE_15_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_16_LOADCIRCULOSBYUSUARIO = "REPORTE_16_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_17_LOADCIRCULOSBYUSUARIO = "REPORTE_17_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_18_LOADCIRCULOSBYUSUARIO = "REPORTE_18_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_19_LOADCIRCULOSBYUSUARIO = "REPORTE_19_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_20_LOADCIRCULOSBYUSUARIO = "REPORTE_20_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_21_LOADCIRCULOSBYUSUARIO = "REPORTE_21_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_22_LOADCIRCULOSBYUSUARIO = "REPORTE_22_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_24_LOADCIRCULOSBYUSUARIO = "REPORTE_24_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_25_LOADCIRCULOSBYUSUARIO = "REPORTE_25_LOADCIRCULOSBYUSUARIO";
    public static final String REPORTE_25_LOADUSUARIOSCAJEROSBYCIRCULO = "REPORTE_25_LOADUSUARIOSCAJEROSBYCIRCULO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_94_LOADUSUARIOSCAJEROSBYCIRCULO = "REPORTE_94_LOADUSUARIOSCAJEROSBYCIRCULO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_26_LOADCIRCULOSBYUSUARIO = "REPORTE_26_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_27_LOADCIRCULOSBYUSUARIO = "REPORTE_27_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_30_LOADCIRCULOSBYUSUARIO = "REPORTE_30_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_31_LOADCIRCULOSBYUSUARIO = "REPORTE_31_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_32_LOADCIRCULOSBYUSUARIO = "REPORTE_32_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_37_LOADCIRCULOSBYUSUARIO = "REPORTE_37_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_39_LOADCIRCULOSBYUSUARIO = "REPORTE_39_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_45_LOADCIRCULOSBYUSUARIO = "REPORTE_45_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_46_LOADCIRCULOSBYUSUARIO = "REPORTE_46_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_49_LOADCIRCULOSBYUSUARIO = "REPORTE_49_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_51_LOADCIRCULOSBYUSUARIO = "REPORTE_51_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_51B_LOADCIRCULOSBYUSUARIO = "REPORTE_51B_LOADCIRCULOSBYUSUARIO";
//    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_57_LOADCIRCULOSBYUSUARIO = "REPORTE_57_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_69_LOADCIRCULOSBYUSUARIO = "REPORTE_69_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_74_LOADCIRCULOSBYUSUARIO = "REPORTE_74_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_79_LOADCIRCULOSBYUSUARIO = "REPORTE_79_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_87_LOADCIRCULOSBYUSUARIO = "REPORTE_87_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_88_LOADCIRCULOSBYUSUARIO = "REPORTE_88_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_89_LOADCIRCULOSBYUSUARIO = "REPORTE_89_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_90_LOADCIRCULOSBYUSUARIO = "REPORTE_90_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_91_LOADCIRCULOSBYUSUARIO = "REPORTE_91_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_92_LOADCIRCULOSBYUSUARIO = "REPORTE_92_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_93_LOADCIRCULOSBYUSUARIO = "REPORTE_93_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_94_LOADCIRCULOSBYUSUARIO = "REPORTE_94_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    
    public static final String REPORTE_99_LOADCIRCULOSBYUSUARIO = "REPORTE_99_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_109_LOADCIRCULOSBYUSUARIO = "REPORTE_109_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_110_LOADCIRCULOSBYUSUARIO = "REPORTE_110_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_121_LOADCIRCULOSBYUSUARIO = "REPORTE_121_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_123_LOADCIRCULOSBYUSUARIO = "REPORTE_123_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_124_LOADCIRCULOSBYUSUARIO = "REPORTE_124_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_142_LOADCIRCULOSBYUSUARIO = "REPORTE_142_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_145_LOADCIRCULOSBYUSUARIO = "REPORTE_145_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_151_LOADCIRCULOSBYUSUARIO = "REPORTE_151_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_154_LOADCIRCULOSBYUSUARIO = "REPORTE_154_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_155_LOADCIRCULOSBYUSUARIO = "REPORTE_155_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_156_LOADCIRCULOSBYUSUARIO = "REPORTE_156_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_REL02_LOADCIRCULOSBYUSUARIO = "REPORTE_REL02_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_G01_LOADCIRCULOSBYUSUARIO = "REPORTE_G01_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_180_LOADCIRCULOSBYUSUARIO = "REPORTE_180_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
     public static final String REPORTE_182_LOADCIRCULOSBYUSUARIO = "REPORTE_182_LOADCIRCULOSBYUSUARIO";
     // --------------------------------------------------------------------------------------------------
     public static final String REPORTE_181_LOADCIRCULOSBYUSUARIO = "REPORTE_181_LOADCIRCULOSBYUSUARIO";
     // --------------------------------------------------------------------------------------------------
   
    // AHERRENO 18/02/2011
    // Reporte G02 - Se agrega REPORTE_G02_LOADUSUARIOSBYCIRCULO
    public static final String REPORTE_G02_LOADCIRCULOSBYUSUARIO = "REPORTE_G02_LOADCIRCULOSBYUSUARIO";
    
    /**
     * @Autor: Santiago Vásquez
     * @Change: 2028.AJUSTE REPORTES 166, 167 y G02 Eliminar la opción de
     * usuario
     */
    //public static final String REPORTE_G02_LOADUSUARIOSBYCIRCULO = "REPORTE_G02_LOADUSUARIOSBYCIRCULO";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 28/10/2010
    // Reporte G03
    public static final String REPORTE_G03_LOADCIRCULOSBYUSUARIO = "REPORTE_G03_LOADCIRCULOSBYUSUARIO";
    public static final String REPORTE_G03_LOADUSUARIOSBYCIRCULO = "REPORTE_G03_LOADUSUARIOSBYCIRCULO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_G04_LOADCIRCULOSBYUSUARIO = "REPORTE_G04_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_G05_LOADCIRCULOSBYUSUARIO = "REPORTE_G05_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_G06_LOADCIRCULOSBYUSUARIO = "REPORTE_G06_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_RUPTA01_LOADCIRCULOSBYUSUARIO = "REPORTE_RUPTA01_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 19/08/2009
    // Reporte Folios Masivos
    public static final String REPORTE_FOLIOS_MAS_LOADCIRCULOSBYUSUARIO = "REPORTE_FOLIOS_MAS_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 22/09/2009
    // Reporte E01
    public static final String REPORTE_E01_LOADCIRCULOSBYUSUARIO = "REPORTE_E01_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 22/09/2009
    // Reporte 161
    public static final String REPORTE_161_LOADCIRCULOSBYUSUARIO = "REPORTE_161_LOADCIRCULOSBYUSUARIO";
    public static final String REPORTE_161__ONSELECT_CIRCULO_MUNICIPIOS_ACTION = "REPORTE_161__ONSELECT_CIRCULO_MUNICIPIOS_ACTION";
    public static final String REPORTE_161__ONSELECT_ZONAREGISTRAL_MUNICIPIO_ACTION = "REPORTE_161__ONSELECT_ZONAREGISTRAL_MUNICIPIO_ACTION";
    public static final String REPORTE_161__ONSELECT_ZONAREGISTRAL_VEREDA_ACTION = "REPORTE_161__ONSELECT_ZONAREGISTRAL_VEREDA_ACTION";
    public static final String REPORTE_161__ONSELECT_NATURALEZA_JURIDICA_ACTION = "REPORTE_161__ONSELECT_NATURALEZA_JURIDICA_ACTION";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 26/04/2010
    // Reporte E02
    public static final String REPORTE_E02_LOADCIRCULOSBYUSUARIO = "REPORTE_E02_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 02/08/2010
    // Reporte E03
    public static final String REPORTE_E03_LOADCIRCULOSBYUSUARIO = "REPORTE_E03_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_157_LOADCIRCULOSBYUSUARIO = "REPORTE_157_LOADCIRCULOSBYUSUARIO";
    public static final String REPORTE_158_LOADCIRCULOSBYUSUARIO = "REPORTE_158_LOADCIRCULOSBYUSUARIO";
    public static final String REPORTE_159_LOADCIRCULOSBYUSUARIO = "REPORTE_159_LOADCIRCULOSBYUSUARIO";
    public static final String REPORTE_160_LOADCIRCULOSBYUSUARIO = "REPORTE_160_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 05/01/2011
    // Reporte 162    
    public static final String REPORTE_162_LOADCIRCULOSBYUSUARIO = "REPORTE_162_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 15/02/2011
    // Reporte 163
    public static final String REPORTE_163_LOADCIRCULOSBYUSUARIO = "REPORTE_163_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 23/02/2011
    // Reporte 164 
    public static final String REPORTE_164_LOADCIRCULOSBYUSUARIO = "REPORTE_164_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 14/03/2011
    // Reporte 165
    public static final String REPORTE_165_LOADCIRCULOSBYUSUARIO = "REPORTE_165_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 14/03/2011
    // Reporte 166
    public static final String REPORTE_166_LOADCIRCULOSBYUSUARIO = "REPORTE_166_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 14/03/2011
    // Reporte 167
    public static final String REPORTE_167_LOADCIRCULOSBYUSUARIO = "REPORTE_167_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 14/03/2011
    // Reporte 168
    public static final String REPORTE_168_LOADCIRCULOSBYUSUARIO = "REPORTE_168_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    // CTORRES 24/04/2013
    // Reporte 175
    public static final String REPORTE_175_LOADCIRCULOSBYUSUARIO = "REPORTE_175_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    // CTORRES 24/04/2013
    // Reporte 176
    public static final String REPORTE_176_LOADCIRCULOSBYUSUARIO = "REPORTE_176_LOADCIRCULOSBYUSUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_01__PARAM_CMDKEY = "reporte-01.jasper";
    public static final String REPORTE_01__NOMBRE = "Acta de reparto notarial";
    public static final String REPORTE_01__PARAM_IDREPARTONOTARIAL = "ID_REPARTO_NOTARIAL";
    public static final String REPORTE_01__PARAM_VNOMBRECOORD = "V_NOMBRECOORD";
    public static final String REPORTE_01__PARAM_VCIUDAD = "V_CIUDAD";
    public static final String REPORTE_01__PARAM_PCIRCULONOMBRE = "P_CIRCULO_ID";
    public static final String REPORTE_01__PARAM_PNOMBREUSUARIO = "P_NOMBRE_USUARIO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_04__PARAM_CMDKEY = "reporte-04.jasper";
    public static final String REPORTE_04__NOMBRE = "";
    public static final String REPORTE_04__PARAM_PFECHADESANOTACION = "P_FECHA_DESANOTACION";
    public static final String REPORTE_04__PARAM_PCIRCULONOMBRE = "P_CIRCULO_NOMBRE";
    public static final String REPORTE_04__PARAM_PNUMPAGINA = "P_NUM_PAGINA";
    public static final String REPORTE_04__PARAM_PFECHADESANOTACIONFIN = "P_FECHA_DESANOTACION_FIN";
    public static final String REPORTE_04__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_05__PARAM_CMDKEY = "reporte-05.jasper";
    public static final String REPORTE_05__NOMBRE = "";
    public static final String REPORTE_05__PARAM_PPAGOFECHA = "P_PAGO_FECHA";
    public static final String REPORTE_05__PARAM_PNUMPAGINA = "P_NUM_PAGINA";
    public static final String REPORTE_05__PARAM_PCIRCULONOMBRE = "P_CIRCULO_NOMBRE";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_06__PARAM_CMDKEY = "reporte-06.jasper";
    public static final String REPORTE_06__NOMBRE = "";
    public static final String REPORTE_06__PARAM_PNUMPAGINA = "P_NUM_PAGINA";
    public static final String REPORTE_06__PARAM_PTURNOFECHA = "P_TURNO_FECHA";
    public static final String REPORTE_06__PARAM_PCIRCULONOMBRE = "P_CIRCULO_ID";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_6B__PARAM_CMDKEY = "reporte-6B.jasper";
    public static final String REPORTE_6B__NOMBRE = "";
    public static final String REPORTE_6B__PARAM_PNUMPAGINA = "P_NUM_PAGINA";
    public static final String REPORTE_6B__PARAM_PTURNOFECHA = "P_TURNO_FECHA";
    public static final String REPORTE_6B__PARAM_PCIRCULONOMBRE = "P_CIRCULO_ID";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_07__PARAM_CMDKEY = "reporte-07.jasper";
    public static final String REPORTE_07__NOMBRE = "";
    public static final String REPORTE_07__PARAM_PFECHAINI = "P_TURNO_FECHA_INICIO";
    public static final String REPORTE_07__PARAM_PFECHAFIN = "P_TURNO_FECHA_FIN";
    public static final String REPORTE_07__PARAM_PCIRCULONOMBRE = "P_CIRCULO_NOMBRE";
// --------------------------------------------------------------------------------------------------
    // -----------------REPORTE 182 CORRECION FORMAS DE PAGO --------------------------------------------
    public static final String REPORTE_181__PARAM_CMDKEY = "reporte-181.jasper";
    public static final String REPORTE_181__NOMBRE = "";
    public static final String REPORTE_181__PARAM_PFECHA = "P_FECHA";
    public static final String REPORTE_181__PARAM_PCIRCULONOMBRE = "P_CIRCULO";
    public static final String REPORTE_181__PARAM_PFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_181__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // --------------------------------------------------------------------------------------------------
    
    public static final String REPORTE_08_PARAM_CMDKEY = "reporte-08.jasper";
    public static final String REPORTE_08__NOMBRE = "";
    public static final String REPORTE_08__PARAM_PTURNOFECHA = "P_TURNO_FECHA";
    public static final String REPORTE_08__PARAM_PNUMPAGINA = "P_NUM_PAGINA";
    public static final String REPORTE_08__PARAM_PCIRCULONOMBRE = "P_CIRCULO_ID";
    public static final String REPORTE_08__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 02/11/2010
    // Reporte 09 - Se agrega parametro P_USUARIO_LOG
    public static final String REPORTE_09__PARAM_CMDKEY = "reporte-09.jasper";
    public static final String REPORTE_09__NOMBRE = "";
    public static final String REPORTE_09__PARAM_PCIRCULONOMBRE = "P_CIRCULO_NOMBRE";
    public static final String REPORTE_09__PARAM_PNUMPAGINA = "P_NUM_PAGINA";
    public static final String REPORTE_09__PARAM_PTURNOFECHA = "P_TURNO_FECHA";
    public static final String REPORTE_09__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_10__PARAM_CMDKEY = "reporte-10.jasper";
    public static final String REPORTE_10__NOMBRE = "";
    public static final String REPORTE_10__PARAM_PFECHAINI = "V_FECHAINIC";
    public static final String REPORTE_10__PARAM_PFECHAFIN = "V_FECHAFIN";
    public static final String REPORTE_10__PARAM_PCIRCULONOMBRE = "V_CIRCULO";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 02/11/2010
    // Reporte 11 - Se agrega parametro P_USUARIO_LOG
    public static final String REPORTE_11__PARAM_CMDKEY = "reporte-11.jasper";
    public static final String REPORTE_11__NOMBRE = "";
    public static final String REPORTE_11__PARAM_PFECHAINI = "V_FECHAINI";
    public static final String REPORTE_11__PARAM_PFECHAFIN = "V_FECHAFIN";
    public static final String REPORTE_11__PARAM_PCIRCULONOMBRE = "V_CIRCULO";
    public static final String REPORTE_11__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // --------------------------------------------------------------------------------------------------
    //AHERRENO 16/11/2010
    //Reporte 15 - Se agrega parametro P_USUARIO_LOG
    public static final String REPORTE_15__PARAM_CMDKEY = "reporte-15.jasper";
    public static final String REPORTE_15__NOMBRE = "";
    public static final String REPORTE_15__PARAM_PLIQUIDFECHAINI = "P_LIQUIDACION_FECHA_INI";
    public static final String REPORTE_15__PARAM_PLIQUIDFECHAFIN = "P_LIQUIDACION_FECHA_FIN";
    public static final String REPORTE_15__PARAM_PCIRCULONOMBRE = "P_CIRCULO_ID";
    public static final String REPORTE_15__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_16__PARAM_CMDKEY = "reporte-16.jasper";
    public static final String REPORTE_16__NOMBRE = "";
    public static final String REPORTE_16__PARAM_PFECHAINI = "V_FECHAINIC";
    public static final String REPORTE_16__PARAM_PFECHAFIN = "V_FECHAFIN";
    public static final String REPORTE_16__PARAM_PCIRCULONOMBRE = "V_CIRCULO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_17__PARAM_CMDKEY = "reporte-17.jasper";
    public static final String REPORTE_17__NOMBRE = "";
    public static final String REPORTE_17__PARAM_PFECHAINI = "P_FECHA_INI";
    public static final String REPORTE_17__PARAM_PFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_17__PARAM_PCIRCULONOMBRE = "V_CIRCULO";
    // --------------------------------------------------------------------------------------------------
    //AHERRENO 16/11/2010
    //Reporte 18 - Se agrega parametro P_USUARIO_LOG
    public static final String REPORTE_18__PARAM_CMDKEY = "reporte-18.jasper";
    public static final String REPORTE_18__NOMBRE = "";
    public static final String REPORTE_18__PARAM_PFECHAINI = "P_FECHA_INI";
    public static final String REPORTE_18__PARAM_PFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_18__PARAM_PCIRCULONOMBRE = "P_CIRCULO_ID";
    public static final String REPORTE_18__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_19__PARAM_CMDKEY = "reporte-19.jasper";
    public static final String REPORTE_19__NOMBRE = "";
    public static final String REPORTE_19__PARAM_PPAGOFECHAINI = "P_PAGO_FECHA_INI";
    public static final String REPORTE_19__PARAM_PPAGOFECHAFIN = "P_PAGO_FECHA_FIN";
    public static final String REPORTE_19__PARAM_PCIRCULONOMBRE = "P_CIRCULO_ID";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_20__PARAM_CMDKEY = "reporte-20.jasper";
    public static final String REPORTE_20__NOMBRE = "";
    public static final String REPORTE_20__PARAM_PPAGOFECHAINI = "P_PAGO_FECHA_INI";
    public static final String REPORTE_20__PARAM_PPAGOFECHAFIN = "P_PAGO_FECHA_FIN";
    public static final String REPORTE_20__PARAM_PCIRCULONOMBRE = "P_CIRCULO_ID";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_21__PARAM_CMDKEY = "reporte-21.jasper";
    public static final String REPORTE_21__NOMBRE = "";
    public static final String REPORTE_21__PARAM_PLIQUIDACIONFECHAINI = "P_LIQUIDACION_FECHA_INI";
    public static final String REPORTE_21__PARAM_PLIQUIDACIONFECHAFIN = "P_LIQUIDACION_FECHA_FIN";
    public static final String REPORTE_21__PARAM_PCIRCULONOMBRE = "P_CIRCULO_ID";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_22__PARAM_CMDKEY = "reporte-22.jasper";
    public static final String REPORTE_22__NOMBRE = "";
    public static final String REPORTE_22__PARAM_PPAGOFECHAINI = "P_PAGO_FECHA";
    public static final String REPORTE_22__PARAM_PCIRCULONOMBRE = "P_CIRCULO_ID";
    // --------------------------------------------------------------------------------------------------
    //GCABRERA 15/12/2011
    //Se agrega parámetro REPORTE_24__PARAM_USUARIOLOG
    public static final String REPORTE_24__PARAM_CMDKEY = "reporte-24.jasper";
    public static final String REPORTE_24__NOMBRE = "";
    public static final String REPORTE_24__PARAM_PLIQUIDACIONFECHAINI = "P_FECHA_INI";
    public static final String REPORTE_24__PARAM_PLIQUIDACIONFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_24__PARAM_PCIRCULONOMBRE = "P_CIRCULO_ID";
    public static final String REPORTE_24__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_25__PARAM_CMDKEY = "reporte-25.jasper";
    public static final String REPORTE_25__NOMBRE = "";
    public static final String REPORTE_25__PARAM_PAGOFECHA = "P_PAGO_FECHA";
    public static final String REPORTE_25__PARAM_CIRCULONOMBRE = "P_CIRCULO_NOMBRE";
    public static final String REPORTE_25__PARAM_USUARIONOMBRE = "P_USUARIO_NOMBRE";
    public static final String REPORTE_25__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_26__PARAM_CMDKEY = "reporte-26.jasper";
    public static final String REPORTE_26__NOMBRE = "";
    public static final String REPORTE_26__PARAM_PFECHAINI = "P_FECHA_INI";
    public static final String REPORTE_26__PARAM_PFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_26__PARAM_PCIRCULONOMBRE = "P_CIRCULO_ID";
    public static final String REPORTE_26__PARAM_PNOTARIA = "P_NOTARIA";
    public static final String REPORTE_26__PARAM_PDEPARTAMENTO = "P_DEPARTAMENTO";
    public static final String REPORTE_26__PARAM_PMUNICIPIO = "P_MUNICIPIO";
    public static final String REPORTE_26__PARAM_PCATEGORIA = "P_CATEGORIA";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_27__PARAM_CMDKEY = "reporte-27.jasper";
    public static final String REPORTE_27__NOMBRE = "";
    public static final String REPORTE_27__PARAM_PFECHAINI = "P_FECHA_INI";
    public static final String REPORTE_27__PARAM_PFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_27__PARAM_PCIRCULONOMBRE = "P_CIRCULO_ID";
    public static final String REPORTE_27__PARAM_PNOTARIA = "P_NOTARIA";
    public static final String REPORTE_27__PARAM_PDEPARTAMENTO = "P_DEPARTAMENTO";
    public static final String REPORTE_27__PARAM_PMUNICIPIO = "P_MUNICIPIO";
    public static final String REPORTE_27__PARAM_PCATEGORIA = "P_CATEGORIA";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_30__PARAM_CMDKEY = "reporte-30.jasper";
    public static final String REPORTE_30__NOMBRE = "";
    //public static final String REPORTE_30__PARAM_PFECHAINI = "P_FECHA_INI";
    //public static final String REPORTE_30__PARAM_PFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_30__PARAM_PDEPARTAMENTO = "P_DEPARTAMENTO";
    public static final String REPORTE_30__PARAM_PMUNICIPIO = "P_MUNICIPIO";
    public static final String REPORTE_30__PARAM_PNUMREPARTO = "P_NUM_REPARTO";
    public static final String REPORTE_30__PARAM_PCATEGORIA = "P_CATEGORIA";
    public static final String REPORTE_30__PARAM_PNOTARIA = "P_OF_ORIGEN";
    public static final String REPORTE_30__PARAM_PCIRCULONOMBRE = "P_ID_CIRCULO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_31__PARAM_CMDKEY = "reporte-31.jasper";
    public static final String REPORTE_31__NOMBRE = "";
    public static final String REPORTE_31__PARAM_PFECHAINI = "P_FECHA_INI";
    public static final String REPORTE_31__PARAM_PFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_31__PARAM_PCIRCULONOMBRE = "P_CIRCULO_ID";
    public static final String REPORTE_31__PARAM_PNOTARIA = "P_NOTARIA";
    public static final String REPORTE_31__PARAM_PDEPARTAMENTO = "P_DEPARTAMENTO";
    public static final String REPORTE_31__PARAM_PMUNICIPIO = "P_MUNICIPIO";
    public static final String REPORTE_31__PARAM_PCATEGORIA = "P_CATEGORIA";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_32__PARAM_CMDKEY = "reporte-32.jasper";
    public static final String REPORTE_32__NOMBRE = "";
    public static final String REPORTE_32__PARAM_PFECHAREP = "P_FECHA_REP";
    public static final String REPORTE_32__PARAM_PCIRCULONOMBRE = "P_CIRCULO_ID";
    public static final String REPORTE_32__PARAM_PFLAG = "P_FLAG";
    public static final String REPORTE_32__PARAM_PREPARTONOTARIAL = "P_REPARTO_NOTARIAL";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_37__PARAM_CMDKEY = "reporte-37.jasper";
    public static final String REPORTE_37__NOMBRE = "";
    public static final String REPORTE_37__PARAM_PCIRCULONOMBRE = "V_CIRCULO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_38__PARAM_CMDKEY = "reporte-38.jasper";
    public static final String REPORTE_38__NOMBRE = "";
    public static final String REPORTE_38__PARAM_PFECHAINI = "P_FECHA_INI";
    public static final String REPORTE_38__PARAM_PFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_38__PARAM_PCIRCULONOMBRE = "P_CIRCULO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_39__PARAM_CMDKEY = "reporte-39.jasper";
    public static final String REPORTE_39__NOMBRE = "";
    public static final String REPORTE_39__PARAM_PFECHAINI = "P_FECHA_INI";
    public static final String REPORTE_39__PARAM_PFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_39__PARAM_PCIRCULONOMBRE = "P_CIRCULO";
    //GCABRERA 19/12/2011
    //Se agrega parámetro REPORTE_39__PARAM_USUARIOLOG

    public static final String REPORTE_39__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_41__PARAM_CMDKEY = "reporte-41.jasper";
    public static final String REPORTE_41__NOMBRE = "";
    public static final String REPORTE_41__PARAM_PFECHAINI = "P_FECHA_INI";
    public static final String REPORTE_41__PARAM_PFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_41__PARAM_PCIRCULONOMBRE = "P_CIRCULO";
    public static final String REPORTE_41__PARAM_PUSUARIONOMBRE = "P_USUARIO_NOMBRE";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_42__PARAM_CMDKEY = "reporte-42.jasper";
    public static final String REPORTE_42__NOMBRE = "";
    public static final String REPORTE_42__PARAM_PFECHAINI = "P_FECHA_INI";
    public static final String REPORTE_42__PARAM_PFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_42__PARAM_PCIRCULONOMBRE = "P_CIRCULO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_43__PARAM_CMDKEY = "reporte-43.jasper";
    public static final String REPORTE_43__NOMBRE = "";
    public static final String REPORTE_43__PARAM_PFECHAINI = "P_FECHA_INI";
    public static final String REPORTE_43__PARAM_PFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_43__PARAM_PCIRCULONOMBRE = "P_CIRCULO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_44__PARAM_CMDKEY = "reporte-44.jasper";
    public static final String REPORTE_44__NOMBRE = "";
    public static final String REPORTE_44__PARAM_PTURNOFECHAINI = "P_TURNO_FECHA_INI";
    public static final String REPORTE_44__PARAM_PCIRCULONOMBRE = "P_CIRCULO_ID";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_45__PARAM_CMDKEY = "reporte-45.jasper";
    public static final String REPORTE_45__NOMBRE = "";
    public static final String REPORTE_45__PARAM_PTURNOFECHAINI = "P_TURNO_FECHA_INI";
    public static final String REPORTE_45__PARAM_PCIRCULONOMBRE = "P_CIRCULO_ID";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_46__PARAM_CMDKEY = "reporte-46.jasper";
    public static final String REPORTE_46__NOMBRE = "";
    public static final String REPORTE_46__PARAM_PFECHAREP = "P_FECHA";
    public static final String REPORTE_46__PARAM_PCIRCULONOMBRE = "P_CIRCULO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_48__PARAM_CMDKEY = "reporte-48.jasper";
    public static final String REPORTE_48__NOMBRE = "";
    //
    public static final String REPORTE_48__PARAM_LOCAL_FECHAINI_YYMMDD = "PARAM_LOCAL_FECHAINI_YYMMDD";
    public static final String REPORTE_48__PARAM_LOCAL_FECHAINI_HH = "PARAM_LOCAL_FECHAINI_HH";
    public static final String REPORTE_48__PARAM_LOCAL_FECHAINI_MI = "PARAM_LOCAL_FECHAINI_MI";
    public static final String REPORTE_48__PARAM_LOCAL_FECHAINI_AM = "PARAM_LOCAL_FECHAINI_AM";
    //
    public static final String REPORTE_48__PARAM_LOCAL_FECHAFIN_YYMMDD = "PARAM_LOCAL_FECHAFIN_YYMMDD";
    public static final String REPORTE_48__PARAM_LOCAL_FECHAFIN_HH = "PARAM_LOCAL_FECHAFIN_HH";
    public static final String REPORTE_48__PARAM_LOCAL_FECHAFIN_MI = "PARAM_LOCAL_FECHAFIN_MI";
    public static final String REPORTE_48__PARAM_LOCAL_FECHAFIN_AM = "PARAM_LOCAL_FECHAFIN_AM";
    //
    public static final String REPORTE_48__PARAM_LOCAL_CIRCULO_ID = "PARAM_LOCAL_CIRCULOID";
    public static final String REPORTE_48__PARAM_PFECHAINI = "P_FECHA_INI";
    public static final String REPORTE_48__PARAM_PFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_48__PARAM_PCIRCULONOMBRE = "P_CIRCULO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_49__PARAM_CMDKEY = "reporte-49.jasper";
    public static final String REPORTE_49__NOMBRE = "";
    public static final String REPORTE_49__PARAM_PFECHAINI = "P_FECHA_INI";
    public static final String REPORTE_49__PARAM_PFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_49__PARAM_PCIRCULONOMBRE = "P_CIRCULO_ID";
    public static final String REPORTE_49__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // --------------------------------------------------------------------------------------------------
    /*AHERRENO 25/04/2011 SE AGREGA EL PARAMETRO REPORTE_51__PARAM_USUARIOLOG*/
    public static final String REPORTE_51__PARAM_CMDKEY = "reporte-51.jasper";
    public static final String REPORTE_51__NOMBRE = "";
    public static final String REPORTE_51__PARAM_PCIRCULONOMBRE = "P_CIRCULO_NOMBRE";
    public static final String REPORTE_51__PARAM_PFECHA = "V_DIA";
    public static final String REPORTE_51__PARAM_PUSUARIO = "V_USUARIO";
    public static final String REPORTE_51__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // --------------------------------------------------------------------------------------------------
    /*CCASTIBLANCO 21/11/2018 SE AGREGA EL PARAMETRO REPORTE_51B__PARAM_USUARIOLOG*/
    public static final String REPORTE_51B__PARAM_CMDKEY = "reporte-51b.jasper";
    public static final String REPORTE_51B__NOMBRE = "";
    public static final String REPORTE_51B__PARAM_PCIRCULONOMBRE = "P_CIRCULO_NOMBRE";
    public static final String REPORTE_51B__PARAM_PFECHA = "V_DIA";
    public static final String REPORTE_51B__PARAM_PUSUARIO = "V_USUARIO";
    public static final String REPORTE_51B__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_57__PARAM_CMDKEY = "reporte-57.jasper";
    public static final String REPORTE_57__NOMBRE = "";
    public static final String REPORTE_57__PARAM_PFECHAINI = "P_APERTURAFOLIO_FECHA";
    public static final String REPORTE_57__PARAM_PPAGINA = "P_NUM_PAGINA";
    public static final String REPORTE_57__PARAM_PCIRCULONOMBRE = "P_CIRCULO_NOMBRE";
    public static final String REPORTE_57__PARAM_PFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_57__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_69__PARAM_CMDKEY = "reporte-69.jasper";
    public static final String REPORTE_69__NOMBRE = "";
    public static final String REPORTE_69__PARAM_PFECHA = "P_FECHA_DESANOTACION";
    public static final String REPORTE_69__PARAM_PCIRCULONOMBRE = "P_CIRCULO_NOMBRE";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_74__PARAM_CMDKEY = "reporte-74.jasper";
    public static final String REPORTE_74__NOMBRE = "";
    public static final String REPORTE_74__PARAM_PFECHAINI = "V_FECHAINI";
    public static final String REPORTE_74__PARAM_PFECHAFIN = "V_FECHAFIN";
    public static final String REPORTE_74__PARAM_PCIRCULONOMBRE = "V_CIRCULO";
    // --------------------------------------------------------------------------------------------------
    //AHERRENO 16/11/2010
    //Reporte 79 - Se agrega parametro P_USUARIO_LOG
    public static final String REPORTE_79__PARAM_CMDKEY = "reporte-79.jasper";
    public static final String REPORTE_79__NOMBRE = "";
    public static final String REPORTE_79__PARAM_PFECHAINI = "P_FECHA_INI";
    public static final String REPORTE_79__PARAM_PFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_79__PARAM_PCIRCULONOMBRE = "P_CIRCULO_ID";
    public static final String REPORTE_79__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_81__PARAM_CMDKEY = "reporte-81.jasper";
    public static final String REPORTE_81__NOMBRE = "";
    public static final String REPORTE_81__PARAM_PFECHAINI = "P_FECHA_INI";
    public static final String REPORTE_81__PARAM_PFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_81__PARAM_PCIRCULONOMBRE = "P_CIRCULO";
    public static final String REPORTE_81__PARAM_PFASE = "P_FASE";
    public static final String REPORTE_81__PARAM_PPROCESO = "P_PROCESO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_87__PARAM_CMDKEY = "reporte-87.jasper";
    public static final String REPORTE_87__NOMBRE = "";
    public static final String REPORTE_87__PARAM_PFECHA = "P_FECHA_TURNO";
    public static final String REPORTE_87__PARAM_PCIRCULO = "P_CIRCULO";
    public static final String REPORTE_87__PARAM_PESTADO = "P_ESTADO";
    public static final String REPORTE_87__PARAM_PNUMPAGINA = "P_NUM_PAGINA";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_88__PARAM_CMDKEY = "reporte-88.jasper";
    public static final String REPORTE_88__NOMBRE = "";
    public static final String REPORTE_88__PARAM_PFECHA = "P_FECHA";
    public static final String REPORTE_88__PARAM_PCIRCULONOMBRE = "P_CIRCULO";
    public static final String REPORTE_88__PARAM_PFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_88__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_89__PARAM_CMDKEY = "reporte-89.jasper";
    public static final String REPORTE_89__NOMBRE = "";
    public static final String REPORTE_89__PARAM_PFECHA = "P_FECHA";
    public static final String REPORTE_89__PARAM_PCIRCULONOMBRE = "P_CIRCULO";
    public static final String REPORTE_89__PARAM_PFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_89__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_90__PARAM_CMDKEY = "reporte-90.jasper";
    public static final String REPORTE_90__NOMBRE = "";
    public static final String REPORTE_90__PARAM_PFECHA = "P_FECHA";
    public static final String REPORTE_90__PARAM_PCIRCULONOMBRE = "P_CIRCULO";
    public static final String REPORTE_90__PARAM_PNUMPAGINA = "P_NUM_PAGINA";
    public static final String REPORTE_90__PARAM_PFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_90__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_91__PARAM_CMDKEY = "reporte-91.jasper";
    public static final String REPORTE_91__NOMBRE = "";
    public static final String REPORTE_91__PARAM_PFECHAINI = "P_FECHA_INI";
    public static final String REPORTE_91__PARAM_PFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_91__PARAM_PCIRCULONOMBRE = "P_CIRCULO_ID";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_92__PARAM_CMDKEY = "reporte-92.jasper";
    public static final String REPORTE_92__NOMBRE = "";
    public static final String REPORTE_92__PARAM_PFECHAINI = "V_FECHAINI";
    public static final String REPORTE_92__PARAM_PFECHAFIN = "V_FECHAFIN";
    public static final String REPORTE_92__PARAM_PCIRCULONOMBRE = "V_CIRCULO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_93__PARAM_CMDKEY = "reporte-93.jasper";
    public static final String REPORTE_93__NOMBRE = "";
    public static final String REPORTE_93__PARAM_PCIRCULONOMBRE = "P_CIRCULO";
    public static final String REPORTE_93__PARAM_PUSUARIO = "P_USUARIO";
    // --------------------------------------------------------------------------------------------------
/*AHERRENO 02/03/2011
   Se agrega variable para capturar el usuario logueado*/
    public static final String REPORTE_94__PARAM_CMDKEY = "reporte-94.jasper";
    public static final String REPORTE_94__NOMBRE = "";
    public static final String REPORTE_94__PARAM_PFECHAINI = "P_FECHA_INI";
    public static final String REPORTE_94__PPARAM_PUSUARIONOMBRE = "P_USUARIO_NOMBRE";
    public static final String REPORTE_94__PARAM_PCIRCULONOMBRE = "P_CIRCULO_NOMBRE";
    public static final String REPORTE_94__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_95__PARAM_CMDKEY = "reporte-95.jasper";
    public static final String REPORTE_95__NOMBRE = "";
    public static final String REPORTE_95__PARAM_PFECHAINI = "P_FECHA_INI";
    public static final String REPORTE_95__PARAM_PFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_95__PARAM_PCIRCULONOMBRE = "P_CIRCULO_ID";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_96__PARAM_CMDKEY = "reporte-96.jasper";
    public static final String REPORTE_96__NOMBRE = "";
    public static final String REPORTE_96__PARAM_PFECHAINI = "V_FECHAINIC";
    public static final String REPORTE_96__PARAM_PFECHAFIN = "V_FECHAFIN";
    public static final String REPORTE_96__PARAM_PCIRCULONOMBRE = "V_CIRCULO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_97__PARAM_CMDKEY = "reporte-97.jasper";
    public static final String REPORTE_97__NOMBRE = "";
    public static final String REPORTE_97__PARAM_PFECHAINI = "P_FECHA_INI";
    public static final String REPORTE_97__PARAM_PFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_97__PARAM_PCIRCULONOMBRE = "P_CIRCULO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_99__PARAM_CMDKEY = "reporte-99.jasper";
    public static final String REPORTE_99__NOMBRE = "";
    public static final String REPORTE_99__PARAM_PFECHAINI = "P_FECHA_INI";
    public static final String REPORTE_99__PARAM_PFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_99__PARAM_PCIRCULONOMBRE = "P_CIRCULO";
    public static final String REPORTE_99__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_109__PARAM_CMDKEY = "reporte-109.jasper";
    public static final String REPORTE_109__NOMBRE = "";
    public static final String REPORTE_109__PARAM_PFECHAINI = "P_FECHA_INI";
    public static final String REPORTE_109__PARAM_PFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_109__PARAM_PCIRCULONOMBRE = "P_CIRCULO_ID";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_110__PARAM_CMDKEY = "reporte-110.jasper";
    public static final String REPORTE_110__NOMBRE = "";
    public static final String REPORTE_110__PARAM_PFECHAINI = "P_FECHA_INI";
    public static final String REPORTE_110__PARAM_PFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_110__PARAM_PCIRCULONOMBRE = "P_CIRCULO_ID";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_121__PARAM_CMDKEY = "reporte-121.jasper";
    public static final String REPORTE_121__NOMBRE = "";
    public static final String REPORTE_121__PARAM_PFECHAINI = "P_FECHA_INI";
    public static final String REPORTE_121__PARAM_PFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_121__PARAM_PCIRCULONOMBRE = "P_CIRCULO";
    public static final String REPORTE_121__PARAM_POFICINANUMERO = "P_OFICINA_ORIGEN";
    public static final String REPORTE_121__PARAM_PIDCATEGORIA = "P_CATEGORIA";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_123__PARAM_CMDKEY = "reporte-123.jasper";
    public static final String REPORTE_123__NOMBRE = "";
    public static final String REPORTE_123__PARAM_PFECHAINI = "V_FECHAINI";
    public static final String REPORTE_123__PARAM_PFECHAFIN = "V_FECHAFIN";
    public static final String REPORTE_123__PARAM_PCIRCULONOMBRE = "V_CIRCULO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_124__PARAM_CMDKEY = "reporte-124.jasper";
    public static final String REPORTE_124__NOMBRE = "";
    public static final String REPORTE_124__PARAM_PFECHAINI = "P_FECHA_INI";
    public static final String REPORTE_124__PARAM_PFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_124__PARAM_PCIRCULONOMBRE = "P_CIRCULO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_131__PARAM_CMDKEY = "reporte-131.jasper";
    public static final String REPORTE_131__NOMBRE = "";
    public static final String REPORTE_131__PARAM_PFECHAINI = "P_FECHA_INI";
    public static final String REPORTE_131__PARAM_PFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_131__PARAM_PCIRCULONOMBRE = "P_CIRCULO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_142__PARAM_CMDKEY = "reporte-142.jasper";
    public static final String REPORTE_142__NOMBRE = "";
    public static final String REPORTE_142__PARAM_PFECHAINI = "P_FECHA_INI";
    public static final String REPORTE_142__PARAM_PFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_142__PARAM_PCIRCULONOMBRE = "P_CIRCULO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_145__PARAM_CMDKEY = "reporte-145.jasper";
    public static final String REPORTE_145__NOMBRE = "";
    public static final String REPORTE_145__PARAM_PFECHAINI = "P_FECHA_INI";
    public static final String REPORTE_145__PARAM_PFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_145__PARAM_PCIRCULONOMBRE = "P_CIRCULO";
    // --------------------------------------------------------------------------------------------------
    /*AHERRENO 27/08/2012
     *REQ 020_453
     *SE AGREGA PARAMETRO REPORTE_151__PARAM_USUARIOLOG PARA ENVIAR AL JASPER*/
    public static final String REPORTE_151__PARAM_CMDKEY = "reporte-151.jasper";
    public static final String REPORTE_151__NOMBRE = "";
    public static final String REPORTE_151__PARAM_PFECHAINI = "P_TURNO_FECHA";
    public static final String REPORTE_151__PARAM_PPAGINA = "P_NUM_PAGINA";
    public static final String REPORTE_151__PARAM_PCIRCULONOMBRE = "P_CIRCULO";
    public static final String REPORTE_151__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_154__PARAM_CMDKEY = "reporte-154.jasper";
    public static final String REPORTE_154__NOMBRE = "";
    public static final String REPORTE_154__PARAM_PFECHAINI = "P_FECHA_INI";
    public static final String REPORTE_154__PARAM_PFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_154__PARAM_PCIRCULONOMBRE = "P_CIRCULO_ID";
    public static final String REPORTE_154__PARAM_PNOTARIA = "P_NOTARIA";
    public static final String REPORTE_154__PARAM_PDEPARTAMENTO = "P_DEPARTAMENTO";
    public static final String REPORTE_154__PARAM_PMUNICIPIO = "P_MUNICIPIO";
    public static final String REPORTE_154__PARAM_PCATEGORIA = "P_CATEGORIA";

    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_155__PARAM_CMDKEY = "reporte-155.jasper";
    public static final String REPORTE_155__NOMBRE = "";
    public static final String REPORTE_155__PARAM_PROCESOREPARTO = "P_PROCESO_REPARTO";
    public static final String REPORTE_155__PARAM_CIRCULONOMBRE = "P_CIRCULO";

    // --------------------------------------------------------------------------------------------------
    //AHERRENO 19/09/2013
    //Reporte 156
    //SE AGREGA PARAMETRO REPORTE_156__PARAM_USUARIOLOG PARA ENVIAR AL JASPER    
    public static final String REPORTE_156__PARAM_CMDKEY = "reporte-156.jasper";
    public static final String REPORTE_156__NOMBRE = "";
    public static final String REPORTE_156__PARAM_PNUMPAGINA = "P_NUM_PAGINA";
    //public static final String REPORTE_156__PARAM_PTURNOFECHA = "P_FECHA";
    public static final String REPORTE_156__PARAM_PTURNOFECHAINICIO = "P_FECHAINICIO";
    public static final String REPORTE_156__PARAM_PTURNOFECHAFIN = "P_FECHAFIN";
    public static final String REPORTE_156__PARAM_PCIRCULONOMBRE = "P_CIRCULO";
    public static final String REPORTE_156__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_REL02__PARAM_CMDKEY = "reporte-rel-02.jasper";
    public static final String REPORTE_REL02__NOMBRE = "";
    public static final String REPORTE_REL02__PARAM_PAGOFECHAINI = "P_FECHA_INI";
    public static final String REPORTE_REL02__PARAM_PAGOFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_REL02__PARAM_CIRCULONOMBRE = "P_CIRCULO";
    public static final String REPORTE_REL02__PARAM_USUARIONOMBRE = "P_USUARIO";
    // --------------------------------------------------------------------------------------------------
    //AHERRENO 25/10/2010
    //Se agrega parámetro REPORTE_G01__PARAM_IDUSUARIO
    public static final String REPORTE_G01__PARAM_CMDKEY = "reporte-g-01.jasper";
    public static final String REPORTE_G01__NOMBRE = "";
    public static final String REPORTE_G01__PARAM_FECHAINI = "P_FECHA_INI";
    public static final String REPORTE_G01__PARAM_FECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_G01__PARAM_IDCIRCULO = "P_CIRCULO";
    public static final String REPORTE_G01__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 17/02/2011
    // Reporte G02
    // Se agrega parametro REPORTE_G02__PARAM_USUARIOLOG
    public static final String REPORTE_G02__PARAM_CMDKEY = "reporte-g-02.jasper";
    //public static final String REPORTE_G02__NOMBRE = "";
    public static final String REPORTE_G02__PARAM_FECHAINI = "P_FECHA_INI";
    public static final String REPORTE_G02__PARAM_FECHAFIN = "P_FECHA_FIN";
    /**
     * @Autor: Santiago Vásquez
     * @Change: 2028.AJUSTE REPORTES 166, 167 y G02 Eliminar la opción de
     * usuario
     */
    //public static final String REPORTE_G02__PARAM_IDUSUARIO = "P_ID_USUARIO";
    public static final String REPORTE_G02__PARAM_IDCIRCULO = "P_ID_CIRCULO";
    public static final String REPORTE_G02__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 28/10/2010
    // Reporte G03
    public static final String REPORTE_G03__PARAM_CMDKEY = "reporte-g-03.jasper";
    //public static final String REPORTE_G03__NOMBRE = "";
    public static final String REPORTE_G03__PARAM_IDCIRCULO = "P_ID_CIRCULO";
    public static final String REPORTE_G03__PARAM_IDUSUARIO = "P_ID_USUARIO";
    public static final String REPORTE_G03__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_G04__PARAM_CMDKEY = "reporte-g-04.jasper";
    public static final String REPORTE_G04__NOMBRE = "";
    public static final String REPORTE_G04__PARAM_FECHAINI = "P_FECHA_INI";
    public static final String REPORTE_G04__PARAM_FECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_G04__PARAM_IDCIRCULO = "P_ID_CIRCULO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_G05__PARAM_CMDKEY = "reporte-g-05.jasper";
    public static final String REPORTE_G05__NOMBRE = "";
    public static final String REPORTE_G05__PARAM_FECHAINI = "P_FECHA_INI";
    public static final String REPORTE_G05__PARAM_FECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_G05__PARAM_IDCIRCULO = "P_ID_CIRCULO";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_G06__PARAM_CMDKEY = "reporte-g-06.jasper";
    public static final String REPORTE_G06__NOMBRE = "";
    public static final String REPORTE_G06__PARAM_FECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_G06__PARAM_IDPROCESO = "P_ID_PROCESO";
    public static final String REPORTE_G06__PARAM_IDCIRCULO = "P_ID_CIRCULO";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 03/05/2010
    // Declaración de las Variables para el reporte RUPTA
    // Se agrega variable REPORTE_RUPTA01__PARAM_PFECHAFIN
    public static final String REPORTE_RUPTA01__PARAM_CMDKEY = "reporte-RUPTA01.jasper";
    public static final String REPORTE_RUPTA01__NOMBRE = "";
    public static final String REPORTE_RUPTA01__PARAM_PFECHA = "P_FECHA";
    public static final String REPORTE_RUPTA01__PARAM_PFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_RUPTA01__PARAM_PCIRCULONOMBRE = "P_CIRCULO_NOMBRE";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 19/08/2009
    // Declaración de las Variables para el reporte Folios Masivos
    public static final String REPORTE_FOLIOS_MAS__PARAM_CMDKEY = "reporte-FOLIOS_MAS.jasper";
    public static final String REPORTE_FOLIOS_MAS__NOMBRE = "";
    public static final String REPORTE_FOLIOS_MAS__PARAM_PFECHAINI = "P_APERTURAFOLIO_FECHA_INI";
    public static final String REPORTE_FOLIOS_MAS__PARAM_PFECHAFIN = "P_APERTURAFOLIO_FECHA_FIN";
    public static final String REPORTE_FOLIOS_MAS__PARAM_PPAGINA = "P_NUM_PAGINA";
    public static final String REPORTE_FOLIOS_MAS__PARAM_PCIRCULONOMBRE = "P_CIRCULO_NOMBRE";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 22/09/2009
    // Declaración de las Variables para el reporte E01
    public static final String REPORTE_E01__PARAM_CMDKEY = "reporte-E01.jasper";
    public static final String REPORTE_E01__PARAM_PFECHAINI = "V_FECHAINI";
    public static final String REPORTE_E01__PARAM_PFECHAFIN = "V_FECHAFIN";
    public static final String REPORTE_E01__PARAM_PCIRCULONOMBRE = "V_CIRCULO";
    public static final String REPORTE_E01__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 26/04/2010
    // Declaración de las Variables para el reporte E02
    public static final String REPORTE_E02__PARAM_CMDKEY = "reporte-E02.jasper";
    public static final String REPORTE_E02__PARAM_PFECHAINI = "V_FECHAINI";
    public static final String REPORTE_E02__PARAM_PFECHAFIN = "V_FECHAFIN";
    public static final String REPORTE_E02__PARAM_PCIRCULONOMBRE = "V_CIRCULO";
    public static final String REPORTE_E02__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 02/08/2010
    // Declaración de las Variables para el reporte E03
    public static final String REPORTE_E03__PARAM_CMDKEY = "reporte-E03.jasper";
    public static final String REPORTE_E03__PARAM_PFECHAINI = "V_FECHAINI";
    public static final String REPORTE_E03__PARAM_PFECHAFIN = "V_FECHAFIN";
    public static final String REPORTE_E03__PARAM_PCIRCULONOMBRE = "V_CIRCULO";
    public static final String REPORTE_E03__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // --------------------------------------------------------------------------------------------------
    /*AHERRENO 28/08/2012
     *REQ 020_453
     *SE AGREGA PARAMETRO REPORTE_157__PARAM_USUARIOLOG PARA ENVIAR AL JASPER*/
    public static final String REPORTE_157__PARAM_CMDKEY = "reporte-157.jasper";
    public static final String REPORTE_157__NOMBRE = "";
    public static final String REPORTE_157__PARAM_PTURNOFECHA = "P_TURNO_FECHA";
    public static final String REPORTE_157__PARAM_PCIRCULONOMBRE = "P_CIRCULO_ID";
    public static final String REPORTE_157__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_158__PARAM_CMDKEY = "reporte-158.jasper";
    public static final String REPORTE_158__NOMBRE = "";
    public static final String REPORTE_158__PARAM_PTURNOFECHA_INI = "P_TURNO_FECHA_INI";
    public static final String REPORTE_158__PARAM_PTURNOFECHA_FIN = "P_TURNO_FECHA_FIN";
    public static final String REPORTE_158__PARAM_PCIRCULONOMBRE = "P_CIRCULO_ID";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_159__PARAM_CMDKEY = "reporte-159.jasper";
    public static final String REPORTE_159__NOMBRE = "";
    public static final String REPORTE_159__PARAM_PTURNOFECHA_INI = "P_TURNO_FECHA_INI";
    public static final String REPORTE_159__PARAM_PTURNOFECHA_FIN = "P_TURNO_FECHA_FIN";
    public static final String REPORTE_159__PARAM_PCIRCULONOMBRE = "P_CIRCULO_ID";
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_160__PARAM_CMDKEY = "reporte-160.jasper";
    public static final String REPORTE_160__NOMBRE = "";
    public static final String REPORTE_160__PARAM_PTURNOFECHA_INI = "P_TURNO_FECHA_INI";
    public static final String REPORTE_160__PARAM_PTURNOFECHA_FIN = "P_TURNO_FECHA_FIN";
    public static final String REPORTE_160__PARAM_PCIRCULONOMBRE = "P_CIRCULO_ID";
    public static final String REPORTE_160__PARAM_PUSUARIO_CALIFICADOR = "P_USUARIOCALIFICADOR";
    public static final String REPORTE_160_LOADUSUARIOSCAJEROSBYCIRCULO = "REPORTE_160_LOADUSUARIOSCAJEROSBYCIRCULO";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 22/09/2009
    // Declaración de las Variables para el reporte 161
    public static final String REPORTE_161__PARAM_CMDKEY = "reporte-161.jasper";
    public static final String REPORTE_161__PARAM_PFECHAINI = "P_FECHAINI";
    public static final String REPORTE_161__PARAM_PFECHAFIN = "P_FECHAFIN";
    public static final String REPORTE_161__PARAM_PCIRCULONOMBRE = "P_CIRCULO";
    public static final String REPORTE_161__PARAM_PMUNICIPIO = "P_MUNICIPIO";
    public static final String REPORTE_161__PARAM_PVEREDA = "P_VEREDA";
    public static final String REPORTE_161__PARAM_PNATJURIDICA = "P_NATJURIDICA";
    public static final String REPORTE_161__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 05/01/2011
    // Declaración de las Variables para el reporte 162
    public static final String REPORTE_162__PARAM_CMDKEY = "reporte-162.jasper";
    public static final String REPORTE_162__PARAM_PFECHAINI = "V_FECHAINI";
    public static final String REPORTE_162__PARAM_PFECHAFIN = "V_FECHAFIN";
    public static final String REPORTE_162__PARAM_PCIRCULONOMBRE = "V_CIRCULO";
    public static final String REPORTE_162__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 15/02/2011
    // Declaración de las Variables para el reporte 163
    public static final String REPORTE_163__PARAM_CMDKEY = "reporte-163.jasper";
    public static final String REPORTE_163__PARAM_PFECHAINI = "V_FECHAINI";
    public static final String REPORTE_163__PARAM_PFECHAFIN = "V_FECHAFIN";
    public static final String REPORTE_163__PARAM_PCIRCULONOMBRE = "V_CIRCULO";
    public static final String REPORTE_163__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 23/02/2011
    // Declaración de las Variables para el reporte 164
    public static final String REPORTE_164__PARAM_CMDKEY = "reporte-164.jasper";
    public static final String REPORTE_164__PARAM_PFECHAINI = "V_FECHAINI";
    public static final String REPORTE_164__PARAM_PFECHAFIN = "V_FECHAFIN";
    public static final String REPORTE_164__PARAM_PCIRCULONOMBRE = "V_CIRCULO";
    public static final String REPORTE_164__PARAM_PCUANTIA = "V_CUANTIA";
    public static final String REPORTE_164__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // AHERRENO 14/03/2011
    // Declaración de las Variables para el reporte 165
    public static final String REPORTE_165__PARAM_CMDKEY = "reporte-165.jasper";
    public static final String REPORTE_165__PARAM_PFECHAINI = "V_FECHAINI";
    public static final String REPORTE_165__PARAM_PFECHAFIN = "V_FECHAFIN";
    public static final String REPORTE_165__PARAM_PCIRCULONOMBRE = "V_CIRCULO";
    public static final String REPORTE_165__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // AHERRENO 14/03/2011
    // Declaración de las Variables para el reporte 166
    public static final String REPORTE_166__PARAM_CMDKEY = "reporte-166.jasper";
    public static final String REPORTE_166__PARAM_PFECHAINI = "V_FECHAINI";
    public static final String REPORTE_166__PARAM_PFECHAFIN = "V_FECHAFIN";
    public static final String REPORTE_166__PARAM_PCIRCULONOMBRE = "V_CIRCULO";
    public static final String REPORTE_166__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // AHERRENO 14/03/2011
    // Declaración de las Variables para el reporte 167
    public static final String REPORTE_167__PARAM_CMDKEY = "reporte-167.jasper";
    public static final String REPORTE_167__PARAM_PFECHAINI = "V_FECHAINI";
    public static final String REPORTE_167__PARAM_PFECHAFIN = "V_FECHAFIN";
    public static final String REPORTE_167__PARAM_PCIRCULONOMBRE = "V_CIRCULO";
    public static final String REPORTE_167__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // AHERRENO 14/03/2011
    // Declaración de las Variables para el reporte 168
    public static final String REPORTE_168__PARAM_CMDKEY = "reporte-168.jasper";
    public static final String REPORTE_168__PARAM_PFECHAINI = "V_FECHAINI";
    public static final String REPORTE_168__PARAM_PFECHAFIN = "V_FECHAFIN";
    public static final String REPORTE_168__PARAM_PCIRCULONOMBRE = "V_CIRCULO";
    public static final String REPORTE_168__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // --------------------------------------------------------------------------------------------------
    // AHERRENO 01/06/2012
    // Declaración de las Variables para el reporte 169
    public static final String REPORTE_169__PARAM_CMDKEY = "reporte-169.jasper";
    public static final String REPORTE_169__PARAM_TURNO = "P_TURNO";
    public static final String REPORTE_169__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // --------------------------------------------------------------------------------------------------
    // CTORRES 24/04/2013
    // Caso Mantis  :   07123
    // Declaración de las Variables para el reporte 175
    public static final String REPORTE_175__PARAM_CMDKEY = "reporte-175.jasper";
    public static final String REPORTE_175__PARAM_PFECHAINI = "V_FECHAINI";
    public static final String REPORTE_175__PARAM_PFECHAFIN = "V_FECHAFIN";
    public static final String REPORTE_175__PARAM_PCIRCULONOMBRE = "V_CIRCULO";
    public static final String REPORTE_175__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // CTORRES 24/03/2013
    // Caso Mantis  :   07123
    // Declaración de las Variables para el reporte 176
    public static final String REPORTE_176__PARAM_CMDKEY = "reporte-176.jasper";
    public static final String REPORTE_176__PARAM_PFECHAINI = "V_FECHAINI";
    public static final String REPORTE_176__PARAM_PFECHAFIN = "V_FECHAFIN";
    public static final String REPORTE_176__PARAM_PCIRCULONOMBRE = "V_CIRCULO";
    public static final String REPORTE_176__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // --------------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------------
    public static final String REPORTE_180__PARAM_CMDKEY = "reporte-180.jasper";
    public static final String REPORTE_180__NOMBRE = "";
    public static final String REPORTE_180__PARAM_PFECHA = "P_FECHA";
    public static final String REPORTE_180__PARAM_PCIRCULONOMBRE = "P_CIRCULO";
    public static final String REPORTE_180__PARAM_PFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_180__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    // --------------------------------------------------------------------------------------------------
    
    // --------------------------------------------------------------------------------------------------
    // CTORRES
    // Declaración de las Variables para el reporte exportar folios
    /*
         * @author      :   Carlos Mario Torres Urina
         * @change      :   Se agregan constantes para el proceso de traslado de folios masivos
         * Caso Mantis  :   07123
     */
    public static final String REPORTE_EXPORTAR_FOLIOS_ARCHIVO_SEND = "REPORTE_EXPORTAR_FOLIOS_ARCHIVO_SEND";
    public static final String REPORTE_EXPORTAR_FOLIOS_ARCHIVO_BACK = "REPORTE_EXPORTAR_FOLIOS_ARCHIVO_BACK";
    public static final String REPORTE_EXPORTAR_FOLIOS__PARAM_CMDKEY = "reporte-exportar-folios.jasper";
    public static final String REPORTE_EXPORTAR_FOLIOS__PARAM_CIRCULO = "P_CIRCULO";
    public static final String REPORTE_EXPORTAR_FOLIOS__PARAM_DEPARTAMENTO = "P_DEPARTAMENTO";
    public static final String REPORTE_EXPORTAR_FOLIOS__PARAM_MUNICIPIO = "P_MUNICIPIO";
    public static final String REPORTE_EXPORTAR_FOLIOS__PARAM_VEREDA = "P_VEREDA";
    public static final String DEPARTAMENTO_ID = "DEPARTAMENTO_ID";
    public static final String MUNICIPIO_ID = "MUNICIPIO_ID";
    public static final String VEREDA_ID = "VEREDA_ID";
    public static final String CONTENT_TYPE = "text/plain";
    public static final String ONSELECT_CIRCULO_ORIGEN = "ONSELECT_CIRCULO_ORIGEN";
    public static final String ONSELECT_DEPARTAMENTO_ORIGEN = "ONSELECT_DEPARTAMENTO_ORIGEN";
    public static final String ONSELECT_MUNICIPIO_ORIGEN = "ONSELECT_MUNICIPIO_ORIGEN";
    public static final String ONSELECT_CIRCULO_DESTINO = "ONSELECT_CIRCULO_DESTINO";
    public static final String ONSELECT_DEPARTAMENTO_DESTINO = "ONSELECT_DEPARTAMENTO_DESTINO";
    public static final String ONSELECT_MUNICIPIO_DESTINO = "ONSELECT_MUNICIPIO_DESTINO";
// --------------------------------------------------------------------------------------------------
     // -------------------------------------------------------------------------------
    /* Reporte 182 - FORMAS DE PAGO CORREGIDAS
     * desarrollo3@tsg.net.co - DNILSON OLAYA GOMEZ   
     */
    // -------------------------------------------------------------------------------    
    public static final String REPORTE_182__PARAM_CMDKEY = "reporte-182.jasper";
    public static final String REPORTE_182__NOMBRE = "";
    //public static final String REPORTE_182__PARAM_PFECHA = "P_FECHA";
    //bef-guia-d-ref_167 
    //public static final String REPORTE_182__PARAM_PFECHA = "V_FECHAINI";
    public static final String REPORTE_182__PARAM_PFECHA = "V_DIA";
    //public static final String REPORTE_182__PARAM_PCIRCULONOMBRE = "P_CIRCULO";
    //bef-guia-d-ref_167
    //public static final String REPORTE_182__PARAM_PCIRCULONOMBRE = "V_CIRCULO";
    public static final String REPORTE_182__PARAM_PCIRCULONOMBRE = "P_CIRCULO_NOMBRE";
    //public static final String REPORTE_182__PARAM_PFECHAFIN = "P_FECHA_FIN";    
    public static final String REPORTE_182__PARAM_PFECHAFIN = "V_FECHAFIN";
    public static final String REPORTE_182__PARAM_USUARIOLOG = "P_USUARIO_LOG";
    
    // --------------------------------------------------------------------------------------------------

    public static String DIARIO_RADICADOR_MATRICULAS = "DIARIO_RADICADOR_MATRICULAS";

//	public static String REPORTE_25 = "REPORTE_25";
    public static String REPORTE_09 = "REPORTE_09";
//	public static String REPORTE_08 = "REPORTE_08";
//	public static String REPORTE_07 = "REPORTE_07";
    public static String TERMINA = "TERMINA";
    public static String REPORTSSERVICES_REPORTURI = "REPORTSSERVICES_REPORTURI";
    public static String REPORTSSERVICES_REPORTDISPLAYENABLED = "REPORTSSERVICES_REPORTDISPLAYENABLED";
    /**
     * Constante que identifica las acción de terminar la utilización de los
     * servicios de la acción WEB (Para limpiar la sesión y redirigir a la
     * página principal de páginas administrativas
     */
    public static final String TERMINA2 = "TERMINA2";

//	public static final String TERMINAREPORTE93 = "TERMINAREPORTE93";
    /**
     * Constante que identifica la acción de adicionar una oficina origen
     */
    public static final String ADICIONA_OFICINA_ORIGEN = "ADICIONA_OFICINA_ORIGEN";
    /**
     * Constante que identifica la acción de adicionar una oficina origen
     */
    public static final String ADICIONA_OFICINA_ORIGEN2 = "ADICIONA_OFICINA_ORIGEN2";
    /**
     * Constante que identifica la acción de adicionar una oficina origen
     */
    public static final String ADICIONA_OFICINA_ORIGEN154 = "ADICIONA_OFICINA_ORIGEN154";
    /**
     * Constante que identifica la acción de adicionar una oficina origen
     */
    public static final String ADICIONA_OFICINA_ORIGEN3 = "ADICIONA_OFICINA_ORIGEN3";
    /**
     * Constante que identifica la acción de seleccionar una oficina origen
     */
    public static final String SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_DEPTO
            = "SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_DEPTO";
    /**
     * Constante que identifica la acción de seleccionar una oficina origen
     */
    public static final String SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_DTO
            = "SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_DTO";
    /**
     * Constante que identifica la acción de seleccionar una oficina origen
     */
    public static final String SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_DTO_154
            = "SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_DTO_154";
    /**
     * Constante que identifica la acción de seleccionar una oficina origen
     */
    public static final String SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_DTO_121
            = "SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_DTO_121";
    /**
     * Constante que identifica la acción de seleccionar una oficina origen
     */
    public static final String SELECCIONA_ZONA_REGISTRAL_OF_ORIGEN_DEPTO
            = "SELECCIONA_ZONA_REGISTRAL_OF_ORIGEN_DEPTO";
    /**
     * Constante que identifica la acción de seleccionar una oficina origen
     */
    public static final String SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_MUNICIPIO
            = "SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_MUNICIPIO";
    /**
     * Constante que identifica la acción de seleccionar una oficina origen
     */
    public static final String SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_MPIO
            = "SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_MPIO";
    /**
     * Constante que identifica la acción de seleccionar una oficina origen
     */
    public static final String SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_MPIO_154
            = "SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_MPIO_154";
    /**
     * Constante que identifica la acción de seleccionar una oficina origen
     */
    public static final String SELECCIONA_ZONA_REGISTRAL_OF_ORIGEN_MPIO
            = "SELECCIONA_ZONA_REGISTRAL_OF_ORIGEN_MPIO";
    /**
     * Constante que identifica la acción de consultar una oficina origen por
     * vereda
     */
    public static final String CONSULTA_OFICINA_ORIGEN_POR_VEREDA = "CONSULTA_OFICINA_ORIGEN_POR_VEREDA";
    /**
     * Identificador de accion: consultar oficinas por municipio
     */
    public static final String CONSULTA_OFICINA_ORIGEN_BY_MUNICIPIO__ACTION = "CONSULTA_OFICINA_ORIGEN_BY_MUNICIPIO__ACTION";
    /**
     * Identificador de accion: consultar oficinas por vereda
     */
    public static final String CONSULTA_OFICINA_ORIGEN_BY_VEREDA__ACTION31 = "CONSULTA_OFICINA_ORIGEN_BY_VEREDA__ACTION31";
    /**
     * Identificador de accion: consultar oficinas por vereda
     */
    public static final String CONSULTA_OFICINA_ORIGEN_BY_VEREDA__ACTION121 = "CONSULTA_OFICINA_ORIGEN_BY_VEREDA__ACTION121";
    public static final String CONSULTA_OFICINA_ORIGEN_BY_MUNICIPIO__ACTION26 = "CONSULTA_OFICINA_ORIGEN_BY_MUNICIPIO__ACTION26";
    public static final String CONSULTA_OFICINA_ORIGEN_BY_MUNICIPIO__ACTION154 = "CONSULTA_OFICINA_ORIGEN_BY_MUNICIPIO__ACTION154";
    /**
     * Constante que identifica la acción de consultar una oficina origen por
     * vereda
     */
    public static final String CONSULTA_OFICINA_ORIGEN_POR_VDA = "CONSULTA_OFICINA_ORIGEN_POR_VDA";
    /**
     * Constante que identifica la acción de consultar una oficina origen por
     * vereda
     */
    public static final String CONSULTA_OFICINA_ORIGEN_POR_VDA_154 = "CONSULTA_OFICINA_ORIGEN_POR_VDA_154";
    /**
     * Constante que identifica la acción de consultar una oficina origen por
     * vereda
     */
    public static final String CONSULTA_OFICINA_ORIGEN_X_VEREDA = "CONSULTA_OFICINA_ORIGEN_X_VEREDA";
    /**
     * Constante que identifica las variable del HttpSession donde se almacena
     * una lista de oficinas por vereda
     */
    public static final String LISTA_OFICINAS_POR_VEREDA = "LISTA_OFICINAS_POR_VEREDA";
    /**
     * Constante que identifica las variable del HttpSession donde se almacena
     * un Departamento
     */
    public static final String DEPARTAMENTO_SELECCIONADO = "DEPARTAMENTO_SELECCIONADO";
    /**
     * Constante que identifica las variable del HttpSession donde se almacena
     * un Map con los municipios de un departamento
     */
    public static final String MAP_MUNICIPIOS = "MAP_MUNICIPIOS";
    /**
     * Constante que identifica las variable del HttpSession donde se almacena
     * una lista de municipios
     */
    public static final String LISTA_MUNICIPIOS = "LISTA_MUNICIPIOS";
    /**
     * Constante que identifica las variable del HttpSession donde se almacena
     * una lista de departamentos
     */
    public static final String LISTA_DEPARTAMENTOS = "LISTA_DEPARTAMENTOS";
    /**
     * Constante que identifica las variable del HttpSession donde se almacena
     * una lista de veredas
     */
    public static final String LISTA_VEREDAS = "LISTA_VEREDAS";
    /**
     * Constante que identifica las variable del HttpSession donde se almacena
     * un Municipio
     */
    public static final String MUNICIPIO_SELECCIONADO = "MUNICIPIO_SELECCIONADO";
    /**
     * Constante que identifica las variable del HttpSession donde se almacena
     * un Map con las veredas de un municipio
     */
    public static final String MAP_VEREDAS = "MAP_VEREDAS";
    /**
     * Constante que identifica la acción de seleccionar un departamento
     */
    public static final String SELECCIONA_DEPARTAMENTO = "SELECCIONA_DEPARTAMENTO";
    /**
     * Constante que identifica la acción de seleccionar un municipio
     */
    public static final String SELECCIONA_MUNICIPIO = "SELECCIONA_MUNICIPIO";
    /**
     * Constante que identifica las variable del HttpSession donde se almacena
     * una lista de Fotocopias
     */
    public static final String LISTA_FOTOCOPIAS = "LISTA_FOTOCOPIAS";
    /**
     * Constante que identifica las acción de adicionar una validación de nota
     * al sistema
     */
    public static final String ADICIONA_VALIDACION_NOTA = "ADICIONA_VALIDACION_NOTA";
    /**
     * Constante que identifica las acción de consultar las fases de un proceso
     */
    public static final String CONSULTA_FASE_PROCESO = "CONSULTA_FASE_PROCESO";
    /**
     * Constante que identifica las acción de consultar las fases de un proceso
     * visible
     */
//	public static final String CONSULTA_FASE_PROCESO_VISIBLE = "CONSULTA_FASE_PROCESO_VISIBLE";
    /**
     * Constante que identifica las variable del HttpSession donde se almacena
     * una lista de tipos de nota por proceso
     */
    public static final String LISTA_TIPOS_NOTA_POR_PROCESO = "LISTA_TIPOS_NOTA_POR_PROCESO";
    /**
     * Constante que identifica las variable del HttpSession donde se almacena
     * la lista de fases de un proceso determinado
     */
    public static final String LISTA_FASES_DE_PROCESO = "LISTA_FASES_DE_PROCESO";
    public static final String ACCION_VOLVER = "ACCION_VOLVER";
    /**
     * Constante que identifica la acción de consultar los usuarios del sistema
     * por círculo y rol
     */
    public static final String USUARIOS_CONSULTAR_POR_CIRCULO_ROL = "USUARIOS_CONSULTAR_POR_CIRCULO_ROL";
    /**
     * Constante que identifica la acción de consultar los usuarios del sistema
     * por círculo - REPORTE REL 02
     */
    public static final String USUARIOS_CONSULTAR_POR_CIRCULO_REL02 = "USUARIOS_CONSULTAR_POR_CIRCULO_REL02";
    /**
     * Constante que identifica la acción de consultar los usuarios del sistema
     */
    public static final String USUARIOS_CONSULTAR = "USUARIOS_CONSULTAR";
    /**
     * Constante que identifica la acción de consultar los usuarios del sistema
     */
    public static final String USUARIOS_CONSULTAR_REL02 = "USUARIOS_CONSULTAR_REL02";
    /**
     * Constante que identifican la variable de sesión para almacenar la lista
     * de respuesta a la consulta de usuarios
     */
    public static final String LISTA_USUARIOS = "LISTA_USUARIOS";
    /**
     * Constante que identifica el campo fecha inicio para la listar por fecha
     */
    public static final String FECHA_INICIO = "FECHA_INICIO";
    /**
     * Constante que identifica el campo fecha fin para la listar por fecha
     */
    public static final String FECHA_FIN = "FECHA_FIN";
    public static final String USUARIO_REPORTE = "USUARIO_REPORTE";
    public static final String USUARIOS_CONSULTAR_POR_CIRCULO
            = "USUARIOS_CONSULTAR_POR_CIRCULO";
    /**
     * Constante que identifica las variable del HttpSession donde se almacena
     * una lista de Naturalezas Jurídicas
     */
    public static final String LISTA_NATJURIDICAS = "LISTA_NATJURIDICAS";

    // GCABRERA 27/12/2011
    // Reporte E04
    public static final String REPORTE_E04__SEND_ACTION = "REPORTE_E04__SEND_ACTION";
    public static final String REPORTE_E04__BACK_ACTION = "REPORTE_E04__BACK_ACTION";

    public static final String REPORTE_E04__PARAM_CMDKEY = "reporte-E04.jasper";
    public static final String REPORTE_E04__PARAM_PFECHAINI = "P_FECHA_INI";
    public static final String REPORTE_E04__PARAM_PFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_E04__PARAM_USUARIONOMBRE = "P_USUARIO";
    public static final String REPORTE_E04__PARAM_USUARIOSNOMBRE = "P_USUARIOS";
    public static final String REPORTE_E04__PARAM_IDCIRCULO = "P_CIRCULO";
    public static final String REPORTE_E04__PARAM_USUARIOLOG = "P_USUARIO_LOG";

    public static final String REPORTE_E04_LOADCIRCULOSBYUSUARIO = "REPORTE_E04_LOADCIRCULOSBYUSUARIO";
    public static final String REPORTE_E04_LOADUSUARIOSBYCIRCULO = "REPORTE_E04_LOADUSUARIOSBYCIRCULO";

    /**
     * Autor: Edgar Lora Mantis: 0011319 Requerimiento: 075_151
     */
    public static final String REPORTE_170_SEND_ACTION = "REPORTE_170_SEND_ACTION";
    public static final String REPORTE_170_BACK_ACTION = "REPORTE_170_BACK_ACTION";

    public static final String REPORTE_170_LOADCIRCULOS = "REPORTE_170_LOADCIRCULOS";

    public static final String REPORTE_170_PARAM_CMDKEY = "reporte-170.jasper";
    public static final String REPORTE_170_PARAM_PFECHAINI = "P_FECHA_INI";
    public static final String REPORTE_170_PARAM_PFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_170_PARAM_IDCIRCULO = "P_CIRCULO";
    public static final String REPORTE_170_PARAM_USUARIOLOG = "P_USUARIO_LOG";

    /**
     * @Autor: Santiago Vásquez
     * @Change: 1252.REPORTE TURNOS CORRECCION ENCABEZADO
     */
    public static final String REPORTE_178_LOADCIRCULOSBYUSUARIO = "REPORTE_178_LOADCIRCULOSBYUSUARIO";
    public static final String REPORTE_178_LOADUSUARIOSBYCIRCULO = "REPORTE_178_LOADUSUARIOSBYCIRCULO";
    public static final String REPORTE_178__SEND_ACTION = "REPORTE_178__SEND_ACTION";
    public static final String REPORTE_178__BACK_ACTION = "REPORTE_178__BACK_ACTION";

    public static final String REPORTE_178__PARAM_CMDKEY = "reporte-178.jasper";
    public static final String REPORTE_178__PARAM_PFECHAINI = "P_FECHA_INI";
    public static final String REPORTE_178__PARAM_PFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_178__PARAM_IDCIRCULO = "P_ID_CIRCULO";
    public static final String REPORTE_178__PARAM_IDUSUARIO = "P_USUARIO";
    public static final String REPORTE_178__PARAM_USUARIOLOG = "P_USUARIO_LOG";

    /**
     * @Autor: Santiago Vásquez
     * @Change: 2015.REPORTE.ESTADISTICO.POR.NATURALEZA.JURIDICA
     */
    public static final String REPORTE_179_LOADCIRCULOSBYUSUARIO = "REPORTE_179_LOADCIRCULOSBYUSUARIO";
    public static final String REPORTE_179_NATURALEZA_JURIDICA = "REPORTE_179_NATURALEZA_JURIDICA";
    public static final String REPORTE_179__SEND_ACTION = "REPORTE_179__SEND_ACTION";
    public static final String REPORTE_179__BACK_ACTION = "REPORTE_179__BACK_ACTION";
    public static final String REPORTE_179__LISTA_PREDIOS = "REPORTE_179__LISTA_PREDIOS";

    public static final String REPORTE_179__PARAM_CMDKEY = "reporte-179.jasper";
    public static final String REPORTE_179__PARAM_PFECHAINI = "P_FECHA_INICIO";
    public static final String REPORTE_179__PARAM_PFECHAFIN = "P_FECHA_FIN";
    public static final String REPORTE_179__PARAM_IDCIRCULO = "P_CIRCULO";
    public static final String REPORTE_179__PARAM_IDNATJUR = "P_ID_NATURALEZA";
    public static final String REPORTE_179__PARAM_IDTIPOPREDIO = "P_TIPO_PREDIO";
    public static final String REPORTE_179__PARAM_USUARIOLOG = "P_USUARIO_LOG";

    

    /**
     * Este método se encarga de procesar la solicitud del
     * <code>HttpServletRequest</code> de acuerdo al tipo de accion que tenga
     * como parámetro.
     */
    @Override
    public Evento perform(HttpServletRequest request)
            throws AccionWebException {
        String accion = request.getParameter(WebKeys.ACCION).trim();
        HttpSession session = request.getSession();

        reportesServletPath = (String) session.getServletContext().getAttribute(WebKeys.REPORTES_SERVLET_URL);

        if ((accion == null) || (accion.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe indicar una accion válida");
        } /*else if (accion.equals(AWReportes.DIARIO_RADICADOR_MATRICULAS)) {
        diarioRadicadorMatriculas(request);
        } *//*else if (accion.equals(AWReportes.REPORTE_25)) {
        reporte_25(request);*/ //		} else if (accion.equals(AWReportes.REPORTE_09)) {
        //			reporte09(request);
        //		} else if (accion.equals(AWReportes.REPORTE_08)) {
        //			reporte08(request);
        //		} else if (accion.equals(AWReportes.REPORTE_07)) {
        //			reporte07(request);
        else if (accion.equals(AWReportes.ADICIONA_OFICINA_ORIGEN)) {
            return adicionaOficinaOrigen(request);
        } else if (accion.equals(AWReportes.ADICIONA_OFICINA_ORIGEN2)) {
            return adicionaOficinaOrigen(request);
        } else if (accion.equals(AWReportes.ADICIONA_OFICINA_ORIGEN154)) {
            return adicionaOficinaOrigen(request);
        } else if (accion.equals(AWReportes.ADICIONA_OFICINA_ORIGEN3)) {
            return adicionaOficinaOrigen(request);
        } else if (accion.equals(AWReportes.SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_DEPTO)) {
            session.removeAttribute(LISTA_OFICINAS_POR_VEREDA);
            salvarDatosOficinaOrigenEnSesion(request);
            return seleccionaZonaRegistralDepto(request);
        } else if (accion.equals(AWReportes.SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_DTO)) {
            session.removeAttribute(LISTA_OFICINAS_POR_VEREDA);
            salvarDatosOficinaOrigenEnSesion(request);
            return seleccionaZonaRegistralDepto(request);
        } else if (accion.equals(AWReportes.SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_DTO_154)) {
            session.removeAttribute(LISTA_OFICINAS_POR_VEREDA);
            salvarDatosOficinaOrigenEnSesion(request);
            return seleccionaZonaRegistralDepto(request);
        } else if (accion.equals(AWReportes.SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_DTO_121)) {
            session.removeAttribute(LISTA_OFICINAS_POR_VEREDA);
            session.removeAttribute(LISTA_MUNICIPIOS);
            salvarDatosOficinaOrigenEnSesion(request);
            return seleccionaZonaRegistralDepto(request);
        } else if (accion.equals(AWReportes.SELECCIONA_ZONA_REGISTRAL_OF_ORIGEN_DEPTO)) {
            session.removeAttribute(LISTA_OFICINAS_POR_VEREDA);
            salvarDatosOficinaOrigenEnSesion(request);
            return seleccionaZonaRegistralDepto(request);
        } else if (accion.equals(AWReportes.SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_MUNICIPIO)) {
            session.removeAttribute(LISTA_OFICINAS_POR_VEREDA);
            salvarDatosOficinaOrigenEnSesion(request);
            return seleccionaZonaRegistralMunicipio(request);
        } else if (accion.equals(AWReportes.SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_MPIO)) {
            session.removeAttribute(LISTA_OFICINAS_POR_VEREDA);
            salvarDatosOficinaOrigenEnSesion(request);
            return seleccionaZonaRegistralMunicipio(request);
        } else if (accion.equals(AWReportes.SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_MPIO_154)) {
            session.removeAttribute(LISTA_OFICINAS_POR_VEREDA);
            salvarDatosOficinaOrigenEnSesion(request);
            return seleccionaZonaRegistralMunicipio(request);
        } else if (accion.equals(AWReportes.SELECCIONA_ZONA_REGISTRAL_OF_ORIGEN_MPIO)) {
            session.removeAttribute(LISTA_OFICINAS_POR_VEREDA);
            salvarDatosOficinaOrigenEnSesion(request);
            return seleccionaZonaRegistralMunicipio(request);
        } else if (accion.equals(AWReportes.CONSULTA_OFICINA_ORIGEN_POR_VEREDA)) {
            session.removeAttribute(LISTA_OFICINAS_POR_VEREDA);
            return consultaOficinaOrigenByVereda(request);
        } else if (CONSULTA_OFICINA_ORIGEN_BY_MUNICIPIO__ACTION.equals(accion)) {
            //
            session.removeAttribute(LISTA_OFICINAS_POR_VEREDA);
            return consultaOficinaOrigenByMunicipio(request);
        } else if (CONSULTA_OFICINA_ORIGEN_BY_VEREDA__ACTION31.equals(accion)) {
            session.removeAttribute(LISTA_OFICINAS_POR_VEREDA);
            return consultaOficinaByVereda(request);
        } else if (CONSULTA_OFICINA_ORIGEN_BY_VEREDA__ACTION121.equals(accion)) {
            session.removeAttribute(LISTA_OFICINAS_POR_VEREDA);
            return consultaOficinaByVereda(request);
        } else if (CONSULTA_OFICINA_ORIGEN_BY_MUNICIPIO__ACTION154.equals(accion)) {
            //
            session.removeAttribute(LISTA_OFICINAS_POR_VEREDA);
            return consultaOficinaOrigenByMunicipio(request);
        } else if (CONSULTA_OFICINA_ORIGEN_BY_MUNICIPIO__ACTION26.equals(accion)) {
            //
            session.removeAttribute(LISTA_OFICINAS_POR_VEREDA);
            return consultaOficinaOrigenByMunicipio(request);
        } else if (accion.equals(AWReportes.CONSULTA_OFICINA_ORIGEN_POR_VDA)) {
            session.removeAttribute(LISTA_OFICINAS_POR_VEREDA);
            return consultaOficinaOrigenByVereda(request);
        } else if (accion.equals(AWReportes.CONSULTA_OFICINA_ORIGEN_POR_VDA_154)) {
            session.removeAttribute(LISTA_OFICINAS_POR_VEREDA);
            return consultaOficinaOrigenByVereda(request);
        } else if (accion.equals(AWReportes.CONSULTA_OFICINA_ORIGEN_X_VEREDA)) {
            session.removeAttribute(LISTA_OFICINAS_POR_VEREDA);
            return consultaOficinaOrigenByVereda(request);
        } else if (accion.equals(AWReportes.SELECCIONA_DEPARTAMENTO)) {
            return seleccionaDepartamento(request);
        } else if (accion.equals(AWReportes.SELECCIONA_MUNICIPIO)) {
            return seleccionaMunicipio(request);
        } else if (accion.equals(AWAdministracionForseti.TERMINA)) {
            return limpiarSesion(request);
        } else if (accion.equals(AWReportes.ADICIONA_VALIDACION_NOTA)) {
            return adicionaValidacionNota(request);
        } else if (accion.equals(AWReportes.CONSULTA_FASE_PROCESO)) {
            return consultaFaseProceso(request);
        } else if (accion.equals(AWReportes.USUARIOS_CONSULTAR_POR_CIRCULO_ROL)) {
            return consultarUsuariosPorCirculoRol(request);
        } else if (accion.equals(AWReportes.USUARIOS_CONSULTAR)) {
            return consultarUsuarios(request);
        } else if (accion.equals(AWReportes.USUARIOS_CONSULTAR_POR_CIRCULO_REL02)) {
            return consultarUsuariosPorCirculoRel02(request);
        } else if (accion.equals(AWReportes.USUARIOS_CONSULTAR_REL02)) {
            return consultarUsuariosRel02(request);
        } /*else if (accion.equals(AWReportes.CONSULTA_FASE_PROCESO_VISIBLE)) {
        return consultaFaseProcesoVisible(request);
        }*/ else if (accion.equals(AWReportes.TERMINA2)) {
            return limpiaSesion(request);
        } else if (accion.equals(REPORTE_179_NATURALEZA_JURIDICA)) {
            return cargarDescripcionNaturaleza(request);
        }
//                else if (accion.equals(AWReportes.TERMINAREPORTE93)) {
//			return limpiaSesionReport93(request);
//		}

        // - - - - - - - - - - - - - - - - - - - - - - - -
        if (ACCION_VOLVER.equals(accion)) {
            return null; // MAPPED:REDIRECT-TO
        }
        /*AHERRENO 09/04/2013
          REQ 064_453 DUPLICIDAD TURNOS*/
        if (REPORTE_01_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }

        if (REPORTE_06_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_04_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_05_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_6B_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_07_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_08_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_09_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_10_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_11_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_15_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_16_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_17_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_18_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_19_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_20_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_21_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_22_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_24_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_25_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_25_LOADUSUARIOSCAJEROSBYCIRCULO.equals(accion)) {
            return consultarUsuariosCajeroPorCirculo(request, AWReportes.REPORTE_25__PARAM_CIRCULONOMBRE, AWReportes.REPORTE_25__PARAM_PAGOFECHA, EvnReportes.USUARIOS_CONSULTAR_POR_CIRCULO);
        }
        if (REPORTE_94_LOADUSUARIOSCAJEROSBYCIRCULO.equals(accion)) {
            return consultarUsuariosCajeroPorCirculo(request, AWReportes.REPORTE_94__PARAM_PCIRCULONOMBRE, AWReportes.REPORTE_94__PARAM_PFECHAINI, EvnReportes.USUARIOS_CONSULTAR_POR_CIRCULO);
        }
        if (REPORTE_26_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_27_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_30_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_31_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_32_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_37_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_39_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_45_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_46_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_49_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_51_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_51B_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_57_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_69_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_74_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_79_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_87_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_88_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        
        if (REPORTE_180_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        
        if (REPORTE_89_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_90_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_91_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_92_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_93_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_94_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_99_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_109_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_110_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_121_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_123_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_124_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_142_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_145_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_182_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_151_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_154_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_155_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_156_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_REL02_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_G01_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        //-------------------------------------------------------------------------------
        // AHERRENO 18/02/2011
        // Reporte G02 
        if (REPORTE_G02_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        /**
         * @Autor: Santiago Vásquez
         * @Change: 2028.AJUSTE REPORTES 166, 167 y G02 Eliminar la opción de
         * usuario
         */
        /*if (REPORTE_G02_LOADUSUARIOSBYCIRCULO.equals(accion)) {
            session.removeAttribute(AWReportes.USUARIOS_CONSULTAR_POR_CIRCULO);
            do_ReporteG02_SaveState(request);
            return consultarUsuariosPorCirculo (request, AWReportes.REPORTE_G02__PARAM_IDCIRCULO, EvnReportes.USUARIOS_CONSULTAR_POR_CIRCULO_ROL_CALIFICADOR);
        }*/
        //-------------------------------------------------------------------------------
        // AHERRENO 28/10/2010
        // Reporte G03
        if (REPORTE_G03_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            session.removeAttribute(AWReportes.USUARIOS_CONSULTAR_POR_CIRCULO);
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_G03_LOADUSUARIOSBYCIRCULO.equals(accion)) {
            session.removeAttribute(AWReportes.USUARIOS_CONSULTAR_POR_CIRCULO);
            return consultarUsuariosPorCirculo(request, AWReportes.REPORTE_G03__PARAM_IDCIRCULO, EvnReportes.USUARIOS_CONSULTAR_POR_CIRCULO_ROLES);
        }
        //-------------------------------------------------------------------------------
        if (REPORTE_G04_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_G05_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_G06_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_RUPTA01_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_157_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_158_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_159_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_160_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        // - - - - - - - - - - - - - - - - - - - - - - - -
        //AHERRENO 19/08/2009
        //Reporte Folios Masivos
        if (REPORTE_FOLIOS_MAS_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        // - - - - - - - - - - - - - - - - - - - - - - - -
        //AHERRENO 22/09/2009
        //Reporte E01
        if (REPORTE_E01_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }

        // - - - - - - - - - - - - - - - - - - - - - - - -
        //CTORRES 23/05/2012
        //Reporte Exportar Folios
        if (ONSELECT_CIRCULO_ORIGEN.equals(accion)) {
            try {
                session.removeAttribute(LISTA_OFICINAS_POR_VEREDA);
                salvarDatosReporte161EnSesion(request);

                return seleccionaDepartamento_161(request);
            } catch (Throwable ex) {
                Logger.getLogger(AWReportes.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        if (ONSELECT_MUNICIPIO_ORIGEN.equals(accion)) {
            session.removeAttribute(LISTA_OFICINAS_POR_VEREDA);
            salvarDatosReporte161EnSesion(request);
            this.seleccionaZonaRegistralMunicipio(request);
        }

        if (REPORTE_EXPORTAR_FOLIOS_ARCHIVO_SEND.equals(accion)) {
            do_ReporteExportar_Send_Action(request);
        }

        if (REPORTE_EXPORTAR_FOLIOS_ARCHIVO_BACK.equals(accion)) {
            do_ReporteExportarFolios_Back_Action(request);
        }

        // - - - - - - - - - - - - - - - - - - - - - - - -
        //AHERRENO 22/09/2009
        //Reporte 161
        if (REPORTE_161_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }

        if (REPORTE_161__ONSELECT_CIRCULO_MUNICIPIOS_ACTION.equals(accion)) {
            try {
                session.removeAttribute(LISTA_OFICINAS_POR_VEREDA);
                salvarDatosReporte161EnSesion(request);

                return seleccionaDepartamento_161(request);
            } catch (Throwable ex) {
                Logger.getLogger(AWReportes.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        if (REPORTE_161__ONSELECT_ZONAREGISTRAL_MUNICIPIO_ACTION.equals(accion)) {
            session.removeAttribute(LISTA_OFICINAS_POR_VEREDA);
            salvarDatosReporte161EnSesion(request);
            this.seleccionaZonaRegistralMunicipio(request);
        }

        if (REPORTE_161__ONSELECT_ZONAREGISTRAL_VEREDA_ACTION.equals(accion)) {
            this.seleccionaZonaRegistralVereda_161(request);
            salvarDatosReporte161EnSesion(request);
        }

        if (REPORTE_161__ONSELECT_NATURALEZA_JURIDICA_ACTION.equals(accion)) {
            salvarDatosReporte161EnSesion(request);
        }
        // - - - - - - - - - - - - - - - - - - - - - - - -
        //AHERRENO 26/04/2010
        //Reporte E02
        if (REPORTE_E02_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        // - - - - - - - - - - - - - - - - - - - - - - - -
        //AHERRENO 02/08/2010
        //Reporte E03
        if (REPORTE_E03_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        // - - - - - - - - - - - - - - - - - - - - - - - -
        if (REPORTE_160_LOADUSUARIOSCAJEROSBYCIRCULO.equals(accion)) {
            return consultarUsuariosCajeroPorCirculo(request, AWReportes.REPORTE_160__PARAM_PCIRCULONOMBRE, AWReportes.REPORTE_160__PARAM_PTURNOFECHA_INI, EvnReportes.USUARIOS_CONSULTAR_POR_CIRCULO_ROL_CALIFICADOR);
        }

        // - - - - - - - - - - - - - - - - - - - - - - - -
        //AHERRENO 05/01/2011
        //Reporte 162
        if (REPORTE_162_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        // - - - - - - - - - - - - - - - - - - - - - - - -
        //AHERRENO 15/02/2011
        //Reporte 163
        if (REPORTE_163_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        // - - - - - - - - - - - - - - - - - - - - - - - -
        //AHERRENO 23/02/2011
        //Reporte 164
        if (REPORTE_164_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        // - - - - - - - - - - - - - - - - - - - - - - - -
        //AHERRENO 14/03/2011
        //Reporte 165
        if (REPORTE_165_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        // - - - - - - - - - - - - - - - - - - - - - - - -
        //AHERRENO 14/03/2011
        //Reporte 166
        if (REPORTE_166_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        // - - - - - - - - - - - - - - - - - - - - - - - -
        //AHERRENO 14/03/2011
        //Reporte 167
        if (REPORTE_167_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        // - - - - - - - - - - - - - - - - - - - - - - - -
        //AHERRENO 14/03/2011
        //Reporte 168
        if (REPORTE_168_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        // - - - - - - - - - - - - - - - - - - - - - - - -
        //CTORRES 24/04/2013
        //Reporte 175
        if (REPORTE_175_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        // - - - - - - - - - - - - - - - - - - - - - - - -
        //CTORRES 24/04/2013
        //Reporte 176
        if (REPORTE_176_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        // - - - - - - - - - - - - - - - - - - - - - - - -
        // reporte-30 (additional-page-actions)
        if (REPORTE_30__ONSELECT_ZONAREGISTRAL_DEPARTAMENTO_ACTION.equals(accion)) {
            session.removeAttribute(LISTA_OFICINAS_POR_VEREDA);
            salvarDatosOficinaOrigenEnSesion(request);
            this.salvarDatosReporte30EnSesion(request);
            return seleccionaZonaRegistralDepto(request);
        }
        if (REPORTE_30__ONSELECT_ZONAREGISTRAL_MUNICIPIO_ACTION.equals(accion)) {
            session.removeAttribute(LISTA_OFICINAS_POR_VEREDA);
            salvarDatosOficinaOrigenEnSesion(request);
            this.salvarDatosReporte30EnSesion(request);
            this.seleccionaZonaRegistralMunicipio(request);

            session.removeAttribute(LISTA_OFICINAS_POR_VEREDA);
            return consultaNotariasByMunicipio(request);
        }
        if (REPORTE_30__ONSELECT_ZONAREGISTRAL_VEREDA_ACTION.equals(accion)) {
            session.removeAttribute(LISTA_OFICINAS_POR_VEREDA);
            this.salvarDatosReporte30EnSesion(request);
            return consultaNotariasByMunicipio(request);
        }

        // - - - - - - - - - - - - - - - - - - - - - - - -
        // reporte-30 (additional-page-actions)
        if (REPORTE_REL01__FINDRELATEDFASES_ACTION.equals(accion)) {
            return do_ReporteRel01_FindRelatedFases_Action(request);
        }
        if (REPORTE_REL01__SEND_ACTION.equals(accion)) {
            return do_ReporteRel01_Send_Action(request);
        }
        if (REPORTE_REL01__BACK_ACTION.equals(accion)) {
            do_ReporteRel01_Back_Action(request);
        }

        //- - - - - - - - - - - - - - - - - - - - - - -
        if (REPORTE_01__SEND_ACTION.equals(accion)) {
            do_Reporte01_Send_Action(request);
        }
        if (REPORTE_01__BACK_ACTION.equals(accion)) {
            do_Reporte01_Back_Action(request);
        }
        if (REPORTE_04__SEND_ACTION.equals(accion)) {
            do_Reporte04_Send_Action(request);
        }
        if (REPORTE_04__BACK_ACTION.equals(accion)) {
            do_Reporte04_Back_Action(request);
        }
        if (REPORTE_05__SEND_ACTION.equals(accion)) {
            do_Reporte05_Send_Action(request);
        }
        if (REPORTE_05__BACK_ACTION.equals(accion)) {
            do_Reporte05_Back_Action(request);
        }
        if (REPORTE_06__SEND_ACTION.equals(accion)) {
            do_Reporte06_Send_Action(request);
        }
        if (REPORTE_06__BACK_ACTION.equals(accion)) {
            do_Reporte06_Back_Action(request);
        }
        if (REPORTE_6B__SEND_ACTION.equals(accion)) {
            do_Reporte6B_Send_Action(request);
        }
        if (REPORTE_6B__BACK_ACTION.equals(accion)) {
            do_Reporte6B_Back_Action(request);
        }
        if (REPORTE_07__SEND_ACTION.equals(accion)) {
            do_Reporte07_Send_Action(request);
        }
        if (REPORTE_07__BACK_ACTION.equals(accion)) {
            do_Reporte07_Back_Action(request);
        }
        if (REPORTE_08__SEND_ACTION.equals(accion)) {
            do_Reporte08_Send_Action(request);
        }
        if (REPORTE_08__BACK_ACTION.equals(accion)) {
            do_Reporte08_Back_Action(request);
        }
        if (REPORTE_09__SEND_ACTION.equals(accion)) {
            do_Reporte09_Send_Action(request);
        }
        if (REPORTE_09__BACK_ACTION.equals(accion)) {
            do_Reporte09_Back_Action(request);
        }
        if (REPORTE_10__SEND_ACTION.equals(accion)) {
            do_Reporte10_Send_Action(request);
        }
        if (REPORTE_10__BACK_ACTION.equals(accion)) {
            do_Reporte10_Back_Action(request);
        }
        if (REPORTE_11__SEND_ACTION.equals(accion)) {
            do_Reporte11_Send_Action(request);
        }
        if (REPORTE_11__BACK_ACTION.equals(accion)) {
            do_Reporte11_Back_Action(request);
        }
        if (REPORTE_15__SEND_ACTION.equals(accion)) {
            do_Reporte15_Send_Action(request);
        }
        if (REPORTE_15__BACK_ACTION.equals(accion)) {
            do_Reporte15_Back_Action(request);
        }
        if (REPORTE_16__SEND_ACTION.equals(accion)) {
            do_Reporte16_Send_Action(request);
        }
        if (REPORTE_16__BACK_ACTION.equals(accion)) {
            do_Reporte16_Back_Action(request);
        }
        if (REPORTE_17__SEND_ACTION.equals(accion)) {
            do_Reporte17_Send_Action(request);
        }
        if (REPORTE_17__BACK_ACTION.equals(accion)) {
            do_Reporte17_Back_Action(request);
        }
        if (REPORTE_18__SEND_ACTION.equals(accion)) {
            do_Reporte18_Send_Action(request);
        }
        if (REPORTE_18__BACK_ACTION.equals(accion)) {
            do_Reporte18_Back_Action(request);
        }
        if (REPORTE_19__SEND_ACTION.equals(accion)) {
            do_Reporte19_Send_Action(request);
        }
        if (REPORTE_19__BACK_ACTION.equals(accion)) {
            do_Reporte19_Back_Action(request);
        }
        if (REPORTE_20__SEND_ACTION.equals(accion)) {
            do_Reporte20_Send_Action(request);
        }
        if (REPORTE_20__BACK_ACTION.equals(accion)) {
            do_Reporte20_Back_Action(request);
        }
        if (REPORTE_21__SEND_ACTION.equals(accion)) {
            do_Reporte21_Send_Action(request);
        }
        if (REPORTE_21__BACK_ACTION.equals(accion)) {
            do_Reporte21_Back_Action(request);
        }
        if (REPORTE_22__SEND_ACTION.equals(accion)) {
            do_Reporte22_Send_Action(request);
        }
        if (REPORTE_22__BACK_ACTION.equals(accion)) {
            do_Reporte22_Back_Action(request);
        }
        if (REPORTE_24__SEND_ACTION.equals(accion)) {
            do_Reporte24_Send_Action(request);
        }
        if (REPORTE_24__BACK_ACTION.equals(accion)) {
            do_Reporte24_Back_Action(request);
        }
        if (REPORTE_25__SEND_ACTION.equals(accion)) {
            do_Reporte25_Send_Action(request);
        }
        if (REPORTE_25__BACK_ACTION.equals(accion)) {
            do_Reporte25_Back_Action(request);
        }
        if (REPORTE_26__SEND_ACTION.equals(accion)) {
            do_Reporte26_Send_Action(request);
        }
        if (REPORTE_26__BACK_ACTION.equals(accion)) {
            do_Reporte26_Back_Action(request);
        }
        if (REPORTE_27__SEND_ACTION.equals(accion)) {
            do_Reporte27_Send_Action(request);
        }
        if (REPORTE_27__BACK_ACTION.equals(accion)) {
            do_Reporte27_Back_Action(request);
        }
        if (REPORTE_30__SEND_ACTION.equals(accion)) {
            return do_Reporte30_Send_Action(request);
        }
        if (REPORTE_30__BACK_ACTION.equals(accion)) {

            String[] itemIds = new String[]{
                REPORTE_30__PARAM_PDEPARTAMENTO,
                REPORTE_30__PARAM_PMUNICIPIO,
                REPORTE_30__PARAM_PNUMREPARTO,
                REPORTE_30__PARAM_PNOTARIA,
                REPORTE_30__PARAM_PCATEGORIA,
                REPORTE_30__PARAM_PCIRCULONOMBRE};

            do_ReporteXX_Back_Action(itemIds, request);

            session.removeAttribute(CDepartamento.ID_DEPARTAMENTO);
            session.removeAttribute(CMunicipio.ID_MUNICIPIO);
            session.removeAttribute(CVereda.ID_VEREDA);
            session.removeAttribute(CCategoria.ID_CATEGORIA);
            session.removeAttribute(COficinaOrigen.OFICINA_ID_OFICINA_ORIGEN);

            // DELETE LISTS OF VALUES IN CACHE
            itemIds = new String[]{
                AWReportes.LISTA_DEPARTAMENTOS, AWReportes.LISTA_MUNICIPIOS, AWReportes.LISTA_VEREDAS, AWReportes.LISTA_OFICINAS_POR_VEREDA
            };

            delete_PageItemsState(itemIds, request, session);

        }
        // (31)
        if (REPORTE_31__SEND_ACTION.equals(accion)) {
            do_Reporte31_Send_Action(request);
        }
        if (REPORTE_31__BACK_ACTION.equals(accion)) {

            String[] itemIds = new String[]{
                REPORTE_31__PARAM_PFECHAINI, REPORTE_31__PARAM_PFECHAFIN, REPORTE_31__PARAM_PCIRCULONOMBRE, REPORTE_31__PARAM_PNOTARIA, REPORTE_31__PARAM_PDEPARTAMENTO, REPORTE_31__PARAM_PMUNICIPIO, REPORTE_31__PARAM_PCATEGORIA
            };

            do_ReporteXX_Back_Action(itemIds, request);

            session.removeAttribute(CDepartamento.ID_DEPARTAMENTO);
            session.removeAttribute(CMunicipio.ID_MUNICIPIO);
            session.removeAttribute(CVereda.ID_VEREDA);
            session.removeAttribute(CCategoria.ID_CATEGORIA);
            session.removeAttribute(COficinaOrigen.OFICINA_ID_OFICINA_ORIGEN);

            // DELETE LISTS OF VALUES IN CACHE
            itemIds = new String[]{
                AWReportes.LISTA_DEPARTAMENTOS, AWReportes.LISTA_MUNICIPIOS, AWReportes.LISTA_VEREDAS, AWReportes.LISTA_OFICINAS_POR_VEREDA
            };

            delete_PageItemsState(itemIds, request, session);

        }

        /*                   do_ReporteXX_Back_Action( itemIds, request );

        session.removeAttribute(CDepartamento.ID_DEPARTAMENTO);
        session.removeAttribute(CMunicipio.ID_MUNICIPIO);
        session.removeAttribute(CVereda.ID_VEREDA);
        session.removeAttribute(CCategoria.ID_CATEGORIA);
        session.removeAttribute(COficinaOrigen.OFICINA_ID_OFICINA_ORIGEN);

        // DELETE LISTS OF VALUES IN CACHE
        itemIds = new String[] {
        AWReportes.LISTA_DEPARTAMENTOS
        , AWReportes.LISTA_MUNICIPIOS
        , AWReportes.LISTA_VEREDAS
        , AWReportes.LISTA_OFICINAS_POR_VEREDA
        };

        delete_PageItemsState(itemIds, request, session);

        }*/
//-------------------------------------------------------------------------------
        // (154)
        if (REPORTE_154__SEND_ACTION.equals(accion)) {
            do_Reporte154_Send_Action(request);
        }
        if (REPORTE_154__BACK_ACTION.equals(accion)) {

            String[] itemIds = new String[]{
                REPORTE_154__PARAM_PFECHAINI, REPORTE_154__PARAM_PFECHAFIN, REPORTE_154__PARAM_PCIRCULONOMBRE, REPORTE_154__PARAM_PNOTARIA, REPORTE_154__PARAM_PDEPARTAMENTO, REPORTE_154__PARAM_PMUNICIPIO, REPORTE_154__PARAM_PCATEGORIA
            };

            do_ReporteXX_Back_Action(itemIds, request);

            session.removeAttribute(CDepartamento.ID_DEPARTAMENTO);
            session.removeAttribute(CMunicipio.ID_MUNICIPIO);
            session.removeAttribute(CVereda.ID_VEREDA);
            session.removeAttribute(CCategoria.ID_CATEGORIA);
            session.removeAttribute(COficinaOrigen.OFICINA_ID_OFICINA_ORIGEN);

            // DELETE LISTS OF VALUES IN CACHE
            itemIds = new String[]{
                AWReportes.LISTA_DEPARTAMENTOS, AWReportes.LISTA_MUNICIPIOS, AWReportes.LISTA_VEREDAS, AWReportes.LISTA_OFICINAS_POR_VEREDA
            };

            delete_PageItemsState(itemIds, request, session);

        }

//-------------------------------------------------------------------------------
        if (REPORTE_155__SEND_ACTION.equals(accion)) {
            do_Reporte155_Send_Action(request);
        }
        if (REPORTE_155__BACK_ACTION.equals(accion)) {
            do_Reporte155_Back_Action(request);
        }
//-------------------------------------------------------------------------------
        if (REPORTE_156__SEND_ACTION.equals(accion)) {
            do_Reporte156_Send_Action(request);
        }
        if (REPORTE_156__BACK_ACTION.equals(accion)) {
            do_Reporte156_Back_Action(request);
        }
//-------------------------------------------------------------------------------

        if (REPORTE_32__SEND_ACTION.equals(accion)) {
            do_Reporte32_Send_Action(request);
        }
        if (REPORTE_32__BACK_ACTION.equals(accion)) {

            String[] itemIds = new String[]{
                REPORTE_32__PARAM_PFECHAREP, REPORTE_32__PARAM_PCIRCULONOMBRE, REPORTE_32__PARAM_PREPARTONOTARIAL, REPORTE_32__PARAM_PFLAG
            };

            do_ReporteXX_Back_Action(itemIds, request);
        }
        if (REPORTE_37__SEND_ACTION.equals(accion)) {
            do_Reporte37_Send_Action(request);
        }
        if (REPORTE_37__BACK_ACTION.equals(accion)) {
            String[] itemIds = new String[]{
                REPORTE_37__PARAM_PCIRCULONOMBRE
            };
            do_ReporteXX_Back_Action(itemIds, request);
        }
        if (REPORTE_38__SEND_ACTION.equals(accion)) {
            do_Reporte38_Send_Action(request);
        }
        if (REPORTE_39__SEND_ACTION.equals(accion)) {
            do_Reporte39_Send_Action(request);
        }
        if (REPORTE_39__BACK_ACTION.equals(accion)) {
            String[] itemIds = new String[]{
                REPORTE_39__PARAM_PFECHAINI, REPORTE_39__PARAM_PFECHAFIN, REPORTE_39__PARAM_PCIRCULONOMBRE
            };
            do_ReporteXX_Back_Action(itemIds, request);
        }
        if (REPORTE_41__SEND_ACTION.equals(accion)) {
            do_Reporte41_Send_Action(request);
        }
        if (REPORTE_42__SEND_ACTION.equals(accion)) {
            do_Reporte42_Send_Action(request);
        }
        if (REPORTE_43__SEND_ACTION.equals(accion)) {
            do_Reporte43_Send_Action(request);
        }
        if (REPORTE_44__SEND_ACTION.equals(accion)) {
            do_Reporte44_Send_Action(request);
        }
        if (REPORTE_45__SEND_ACTION.equals(accion)) {
            do_Reporte45_Send_Action(request);
        }
        if (REPORTE_45__BACK_ACTION.equals(accion)) {
            String[] itemIds = new String[]{
                REPORTE_45__PARAM_PTURNOFECHAINI, REPORTE_45__PARAM_PCIRCULONOMBRE
            };
            do_ReporteXX_Back_Action(itemIds, request);
        }
        if (REPORTE_46__SEND_ACTION.equals(accion)) {
            do_Reporte46_Send_Action(request);
        }
        if (REPORTE_46__BACK_ACTION.equals(accion)) {
            String[] itemIds = new String[]{
                REPORTE_46__PARAM_PFECHAREP, REPORTE_46__PARAM_PCIRCULONOMBRE
            };
            do_ReporteXX_Back_Action(itemIds, request);
        }
        if (REPORTE_48__SEND_ACTION.equals(accion)) {
            do_Reporte48_Send_Action(request);
        }
        if (REPORTE_49__SEND_ACTION.equals(accion)) {
            do_Reporte49_Send_Action(request);
        }
        if (REPORTE_49__BACK_ACTION.equals(accion)) {
            String[] itemIds = new String[]{
                REPORTE_49__PARAM_PFECHAINI, REPORTE_49__PARAM_PFECHAFIN, REPORTE_49__PARAM_PCIRCULONOMBRE, REPORTE_49__PARAM_USUARIOLOG
            };
            do_ReporteXX_Back_Action(itemIds, request);
        }
        if (REPORTE_51__SEND_ACTION.equals(accion)) {
            do_Reporte51_Send_Action(request);
        }
        if (REPORTE_51__BACK_ACTION.equals(accion)) {
            String[] itemIds = new String[]{
                REPORTE_51__PARAM_PFECHA, REPORTE_51__PARAM_PCIRCULONOMBRE
            };
            do_ReporteXX_Back_Action(itemIds, request);
        }
        if (REPORTE_51B__SEND_ACTION.equals(accion)) {
            do_Reporte51b_Send_Action(request);
        }
        if (REPORTE_51B__BACK_ACTION.equals(accion)) {
            String[] itemIds = new String[]{
                REPORTE_51B__PARAM_PFECHA, REPORTE_51B__PARAM_PCIRCULONOMBRE
            };
            do_ReporteXX_Back_Action(itemIds, request);
        }
        if (REPORTE_57__SEND_ACTION.equals(accion)) {
            do_Reporte57_Send_Action(request);
        }
        if (REPORTE_57__BACK_ACTION.equals(accion)) {
            String[] itemIds = new String[]{
                REPORTE_57__PARAM_PFECHAINI, REPORTE_57__PARAM_PPAGINA, REPORTE_57__PARAM_PCIRCULONOMBRE
            };
            do_ReporteXX_Back_Action(itemIds, request);
        }
        if (REPORTE_69__SEND_ACTION.equals(accion)) {
            do_Reporte69_Send_Action(request);
        }
        if (REPORTE_69__BACK_ACTION.equals(accion)) {
            String[] itemIds = new String[]{
                REPORTE_69__PARAM_PFECHA, REPORTE_69__PARAM_PCIRCULONOMBRE
            };
            do_ReporteXX_Back_Action(itemIds, request);
        }
        if (REPORTE_74__SEND_ACTION.equals(accion)) {
            do_Reporte74_Send_Action(request);
        }
        if (REPORTE_74__BACK_ACTION.equals(accion)) {
            String[] itemIds = new String[]{
                REPORTE_74__PARAM_PFECHAINI, REPORTE_74__PARAM_PFECHAFIN, REPORTE_74__PARAM_PCIRCULONOMBRE
            };
            do_ReporteXX_Back_Action(itemIds, request);
        }
        if (REPORTE_79__SEND_ACTION.equals(accion)) {
            do_Reporte79_Send_Action(request);
        }
        if (REPORTE_79__BACK_ACTION.equals(accion)) {
            String[] itemIds = new String[]{
                REPORTE_79__PARAM_PFECHAINI, REPORTE_79__PARAM_PFECHAFIN, REPORTE_79__PARAM_PCIRCULONOMBRE, REPORTE_79__PARAM_USUARIOLOG
            };
            do_ReporteXX_Back_Action(itemIds, request);
        }
        if (REPORTE_81__SEND_ACTION.equals(accion)) {
            do_Reporte81_Send_Action(request);
        }
        if (REPORTE_87__SEND_ACTION.equals(accion)) {
            do_Reporte87_Send_Action(request);
        }
        if (REPORTE_87__BACK_ACTION.equals(accion)) {
            String[] itemIds = new String[]{
                REPORTE_87__PARAM_PFECHA, REPORTE_87__PARAM_PESTADO, REPORTE_87__PARAM_PNUMPAGINA, REPORTE_87__PARAM_PCIRCULO
            };
            do_ReporteXX_Back_Action(itemIds, request);
        }
        if (REPORTE_88__SEND_ACTION.equals(accion)) {
            do_Reporte88_Send_Action(request);
        }
        if (REPORTE_88__BACK_ACTION.equals(accion)) {
            String[] itemIds = new String[]{
                REPORTE_88__PARAM_PFECHA, REPORTE_88__PARAM_PCIRCULONOMBRE
            };
            do_ReporteXX_Back_Action(itemIds, request);
        }
        if (REPORTE_89__SEND_ACTION.equals(accion)) {
            do_Reporte89_Send_Action(request);
        }
        if (REPORTE_89__BACK_ACTION.equals(accion)) {
            String[] itemIds = new String[]{
                REPORTE_89__PARAM_PFECHA, REPORTE_89__PARAM_PCIRCULONOMBRE
            };
            do_ReporteXX_Back_Action(itemIds, request);
        }
        if (REPORTE_90__SEND_ACTION.equals(accion)) {
            do_Reporte90_Send_Action(request);
        }
        if (REPORTE_90__BACK_ACTION.equals(accion)) {
            String[] itemIds = new String[]{
                REPORTE_XX__PARAM_CMDKEY, REPORTE_90__PARAM_PFECHA, REPORTE_90__PARAM_PCIRCULONOMBRE, REPORTE_90__PARAM_PNUMPAGINA
            };
            do_ReporteXX_Back_Action(itemIds, request);
        }
        if (REPORTE_91__SEND_ACTION.equals(accion)) {
            do_Reporte91_Send_Action(request);
        }
        if (REPORTE_91__BACK_ACTION.equals(accion)) {
            String[] itemIds = new String[]{
                REPORTE_91__PARAM_PFECHAINI, REPORTE_91__PARAM_PFECHAFIN, REPORTE_91__PARAM_PCIRCULONOMBRE
            };
            do_ReporteXX_Back_Action(itemIds, request);
        }
        if (REPORTE_92__SEND_ACTION.equals(accion)) {
            do_Reporte92_Send_Action(request);
        }
        if (REPORTE_92__BACK_ACTION.equals(accion)) {
            String[] itemIds = new String[]{
                REPORTE_92__PARAM_PFECHAINI, REPORTE_92__PARAM_PFECHAFIN, REPORTE_92__PARAM_PCIRCULONOMBRE
            };
            do_ReporteXX_Back_Action(itemIds, request);
        }
        if (REPORTE_93__SEND_ACTION.equals(accion)) {
            do_Reporte93_Send_Action(request);
        }
        if (REPORTE_93__BACK_ACTION.equals(accion)) {
            /*                  String[] itemIds = new String[] {
            REPORTE_93__PARAM_PCIRCULONOMBRE
            , REPORTE_93__PARAM_PUSUARIO
            };
            do_ReporteXX_Back_Action( itemIds, request );
            session.removeAttribute(AWReportes.USUARIOS_CONSULTAR_POR_CIRCULO);
             */
            limpiaSesionReport93(request);
            do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

        }
        if (REPORTE_94__SEND_ACTION.equals(accion)) {
            do_Reporte94_Send_Action(request);
        }
        /*AHERRENO 02/03/2011
   Se agrega variable para capturar el usuario logueado*/
        if (REPORTE_94__BACK_ACTION.equals(accion)) {
            do_ReporteG02_Back_Action(request);
        }
        if (REPORTE_95__SEND_ACTION.equals(accion)) {
            do_Reporte95_Send_Action(request);
        }
        if (REPORTE_96__SEND_ACTION.equals(accion)) {
            do_Reporte96_Send_Action(request);
        }
        if (REPORTE_97__SEND_ACTION.equals(accion)) {
            do_Reporte97_Send_Action(request);
        }
        if (REPORTE_99__SEND_ACTION.equals(accion)) {
            do_Reporte99_Send_Action(request);
        }
        if (REPORTE_99__BACK_ACTION.equals(accion)) {
            String[] itemIds = new String[]{
                REPORTE_99__PARAM_PFECHAINI, REPORTE_99__PARAM_PFECHAFIN, REPORTE_99__PARAM_PCIRCULONOMBRE, REPORTE_99__PARAM_USUARIOLOG
            };
            do_ReporteXX_Back_Action(itemIds, request);
        }
        if (REPORTE_109__SEND_ACTION.equals(accion)) {
            do_Reporte109_Send_Action(request);
        }
        if (REPORTE_109__BACK_ACTION.equals(accion)) {
            String[] itemIds = new String[]{
                REPORTE_109__PARAM_PFECHAINI, REPORTE_109__PARAM_PFECHAFIN, REPORTE_109__PARAM_PCIRCULONOMBRE
            };
            do_ReporteXX_Back_Action(itemIds, request);
        }
        if (REPORTE_110__SEND_ACTION.equals(accion)) {
            do_Reporte110_Send_Action(request);
        }
        if (REPORTE_110__BACK_ACTION.equals(accion)) {
            String[] itemIds = new String[]{
                REPORTE_110__PARAM_PFECHAINI, REPORTE_110__PARAM_PFECHAFIN, REPORTE_110__PARAM_PCIRCULONOMBRE
            };
            do_ReporteXX_Back_Action(itemIds, request);
        }
        if (REPORTE_121__SEND_ACTION.equals(accion)) {
            do_Reporte121_Send_Action(request);
        }
        if (REPORTE_121__BACK_ACTION.equals(accion)) {
            String[] itemIds = new String[]{
                REPORTE_121__PARAM_PFECHAINI, REPORTE_121__PARAM_PFECHAFIN, REPORTE_121__PARAM_PCIRCULONOMBRE, REPORTE_121__PARAM_POFICINANUMERO, REPORTE_121__PARAM_PIDCATEGORIA, CDepartamento.ID_DEPARTAMENTO, CMunicipio.ID_MUNICIPIO
            };
            do_ReporteXX_Back_Action(itemIds, request);

            session.removeAttribute(CDepartamento.ID_DEPARTAMENTO);
            session.removeAttribute(CMunicipio.ID_MUNICIPIO);
            session.removeAttribute(CVereda.ID_VEREDA);
            session.removeAttribute(CCategoria.ID_CATEGORIA);
            session.removeAttribute(COficinaOrigen.OFICINA_ID_OFICINA_ORIGEN);

            // DELETE LISTS OF VALUES IN CACHE
            itemIds = new String[]{
                AWReportes.LISTA_DEPARTAMENTOS, AWReportes.LISTA_MUNICIPIOS, AWReportes.LISTA_VEREDAS, AWReportes.LISTA_OFICINAS_POR_VEREDA
            };

            delete_PageItemsState(itemIds, request, session);

        }
        if (REPORTE_123__SEND_ACTION.equals(accion)) {
            do_Reporte123_Send_Action(request);
        }
        if (REPORTE_123__BACK_ACTION.equals(accion)) {
            String[] itemIds = new String[]{
                REPORTE_123__PARAM_PFECHAINI, REPORTE_123__PARAM_PFECHAFIN, REPORTE_123__PARAM_PCIRCULONOMBRE
            };
            do_ReporteXX_Back_Action(itemIds, request);
        }
        if (REPORTE_124__SEND_ACTION.equals(accion)) {
            do_Reporte124_Send_Action(request);
        }
        if (REPORTE_124__BACK_ACTION.equals(accion)) {
            String[] itemIds = new String[]{
                REPORTE_124__PARAM_PFECHAINI, REPORTE_124__PARAM_PFECHAFIN, REPORTE_124__PARAM_PCIRCULONOMBRE
            };
            do_ReporteXX_Back_Action(itemIds, request);
        }
        if (REPORTE_131__SEND_ACTION.equals(accion)) {
            do_Reporte131_Send_Action(request);
        }
        if (REPORTE_142__SEND_ACTION.equals(accion)) {
            do_Reporte142_Send_Action(request);
        }
        if (REPORTE_142__BACK_ACTION.equals(accion)) {
            String[] itemIds = new String[]{
                REPORTE_142__PARAM_PFECHAINI, REPORTE_142__PARAM_PFECHAFIN, REPORTE_142__PARAM_PCIRCULONOMBRE
            };
            do_ReporteXX_Back_Action(itemIds, request);
        }
        if (REPORTE_145__SEND_ACTION.equals(accion)) {
            do_Reporte145_Send_Action(request);
        }
        if (REPORTE_145__BACK_ACTION.equals(accion)) {
            String[] itemIds = new String[]{
                REPORTE_145__PARAM_PFECHAINI, REPORTE_145__PARAM_PFECHAFIN, REPORTE_145__PARAM_PCIRCULONOMBRE
            };
            do_ReporteXX_Back_Action(itemIds, request);
        }
        if (REPORTE_151__SEND_ACTION.equals(accion)) {
            do_Reporte151_Send_Action(request);
        }
        if (REPORTE_151__BACK_ACTION.equals(accion)) {
            String[] itemIds = new String[]{
                REPORTE_151__PARAM_PFECHAINI, REPORTE_151__PARAM_PPAGINA, REPORTE_151__PARAM_PCIRCULONOMBRE, REPORTE_151__PARAM_USUARIOLOG
            };
            do_ReporteXX_Back_Action(itemIds, request);
        }

        if (REPORTE_REL02__SEND_ACTION.equals(accion)) {
            do_Reporte_Rel02_Send_Action(request);
        }
        if (REPORTE_REL02__BACK_ACTION.equals(accion)) {
            limpiaSesionReportRel02(request);

            String[] itemIds = new String[]{
                REPORTE_REL02__PARAM_PAGOFECHAINI, REPORTE_REL02__PARAM_PAGOFECHAFIN
            };
            do_ReporteXX_Back_Action(itemIds, request);

        }
        if (REPORTE_G01__SEND_ACTION.equals(accion)) {
            do_ReporteG01_Send_Action(request);
        }
        if (REPORTE_G01__BACK_ACTION.equals(accion)) {
            do_ReporteG01_Back_Action(request);
        }
        if (REPORTE_G02__SEND_ACTION.equals(accion)) {
            do_ReporteG02_Send_Action(request);
        }
        if (REPORTE_G02__BACK_ACTION.equals(accion)) {
            do_ReporteG02_Back_Action(request);
        }
        if (REPORTE_G03__SEND_ACTION.equals(accion)) {
            do_ReporteG03_Send_Action(request);
        }
        if (REPORTE_G03__BACK_ACTION.equals(accion)) {
            do_ReporteG03_Back_Action(request);
        }
        if (REPORTE_G04__SEND_ACTION.equals(accion)) {
            do_ReporteG04_Send_Action(request);
        }
        if (REPORTE_G04__BACK_ACTION.equals(accion)) {
            do_ReporteG04_Back_Action(request);
        }
        if (REPORTE_G05__SEND_ACTION.equals(accion)) {
            do_ReporteG05_Send_Action(request);
        }
        if (REPORTE_G05__BACK_ACTION.equals(accion)) {
            do_ReporteG05_Back_Action(request);
        }
        if (REPORTE_G06__SEND_ACTION.equals(accion)) {
            do_ReporteG06_Send_Action(request);
        }
        if (REPORTE_G06__BACK_ACTION.equals(accion)) {
            do_ReporteG06_Back_Action(request);
        }
        if (REPORTE_RUPTA01__SEND_ACTION.equals(accion)) {
            do_ReporteRUPTA01_Send_Action(request);
        }
        if (REPORTE_RUPTA01__BACK_ACTION.equals(accion)) {
            do_ReporteRUPTA01_Back_Action(request);
        }
        if (REPORTE_157__SEND_ACTION.equals(accion)) {
            do_Reporte157_Send_Action(request);
        }
        if (REPORTE_157__BACK_ACTION.equals(accion)) {
            do_Reporte157_Back_Action(request);
        }

        if (REPORTE_158__SEND_ACTION.equals(accion)) {
            do_Reporte158_Send_Action(request);
        }
        if (REPORTE_158__BACK_ACTION.equals(accion)) {
            do_Reporte158_Back_Action(request);
        }

        if (REPORTE_159__SEND_ACTION.equals(accion)) {
            do_Reporte159_Send_Action(request);
        }
        if (REPORTE_159__BACK_ACTION.equals(accion)) {
            do_Reporte159_Back_Action(request);
        }
        if (REPORTE_160__SEND_ACTION.equals(accion)) {
            do_Reporte160_Send_Action(request);
        }
        if (REPORTE_160__BACK_ACTION.equals(accion)) {
            do_Reporte160_Back_Action(request);
        }
        // AHERRENO 19/08/2009
        // Reporte Folios Masivos
        if (REPORTE_FOLIOS_MAS__SEND_ACTION.equals(accion)) {
            do_Reporte_FOLIOS_MAS_Send_Action(request);
        }
        if (REPORTE_FOLIOS_MAS__BACK_ACTION.equals(accion)) {
            String[] itemIds = new String[]{
                REPORTE_FOLIOS_MAS__PARAM_PFECHAINI, REPORTE_FOLIOS_MAS__PARAM_PFECHAFIN, REPORTE_FOLIOS_MAS__PARAM_PPAGINA, REPORTE_FOLIOS_MAS__PARAM_PCIRCULONOMBRE
            };
            do_ReporteXX_Back_Action(itemIds, request);
        }
        // AHERRENO 22/09/2009
        // Reporte E01
        if (REPORTE_E01__SEND_ACTION.equals(accion)) {
            do_ReporteE01_Send_Action(request);
        }
        if (REPORTE_E01__BACK_ACTION.equals(accion)) {
            do_ReporteE01_Back_Action(request);
        }
        // AHERRENO 22/09/2009
        // Reporte 161
        if (REPORTE_161__SEND_ACTION.equals(accion)) {
            do_Reporte161_Send_Action(request);

        }
        if (REPORTE_161__BACK_ACTION.equals(accion)) {
            do_Reporte161_Back_Action(request);
        }
        // AHERRENO 26/04/2010
        // Reporte E02
        if (REPORTE_E02__SEND_ACTION.equals(accion)) {
            do_ReporteE02_Send_Action(request);
        }
        if (REPORTE_E02__BACK_ACTION.equals(accion)) {
            do_ReporteE02_Back_Action(request);
        }
        // AHERRENO 02/08/2010
        // Reporte E03
        if (REPORTE_E03__SEND_ACTION.equals(accion)) {
            do_ReporteE03_Send_Action(request);
        }
        if (REPORTE_E03__BACK_ACTION.equals(accion)) {
            do_ReporteE03_Back_Action(request);
        }
        // AHERRENO 05/01/2011
        // Reporte 162
        if (REPORTE_162__SEND_ACTION.equals(accion)) {
            do_Reporte162_Send_Action(request);
        }
        if (REPORTE_162__BACK_ACTION.equals(accion)) {
            do_Reporte162_Back_Action(request);
        }

        // AHERRENO 15/02/2011
        // Reporte 163
        if (REPORTE_163__SEND_ACTION.equals(accion)) {
            do_Reporte163_Send_Action(request);
        }
        if (REPORTE_163__BACK_ACTION.equals(accion)) {
            do_Reporte163_Back_Action(request);
        }
        // AHERRENO 23/02/2011
        // Reporte 164
        if (REPORTE_164__SEND_ACTION.equals(accion)) {
            do_Reporte164_Send_Action(request);
        }
        if (REPORTE_164__BACK_ACTION.equals(accion)) {
            do_Reporte164_Back_Action(request);
        }
        // AHERRENO 14/03/2011
        // Reporte 165
        if (REPORTE_165__SEND_ACTION.equals(accion)) {
            do_Reporte165_Send_Action(request);
        }
        if (REPORTE_165__BACK_ACTION.equals(accion)) {
            do_Reporte165_Back_Action(request);
        }
        // AHERRENO 14/03/2011
        // Reporte 166
        if (REPORTE_166__SEND_ACTION.equals(accion)) {
            do_Reporte166_Send_Action(request);
        }
        if (REPORTE_166__BACK_ACTION.equals(accion)) {
            do_Reporte166_Back_Action(request);
        }
        // AHERRENO 14/03/2011
        // Reporte 167
        if (REPORTE_167__SEND_ACTION.equals(accion)) {
            do_Reporte167_Send_Action(request);
        }
        if (REPORTE_167__BACK_ACTION.equals(accion)) {
            do_Reporte167_Back_Action(request);
        }
        // AHERRENO 14/03/2011
        // Reporte 168
        if (REPORTE_168__SEND_ACTION.equals(accion)) {
            do_Reporte168_Send_Action(request);
        }
        if (REPORTE_168__BACK_ACTION.equals(accion)) {
            do_Reporte168_Back_Action(request);
        }
        // AHERRENO 01/06/2012
        // Reporte 169
        if (REPORTE_169__SEND_ACTION.equals(accion)) {
            do_Reporte169_Send_Action(request);
        }
        if (REPORTE_169__BACK_ACTION.equals(accion)) {
            do_Reporte169_Back_Action(request);
        }
        // CTORRES 24/04/2013
        // Reporte 175
        if (REPORTE_175__SEND_ACTION.equals(accion)) {
            do_Reporte175_Send_Action(request);
        }
        if (REPORTE_175__BACK_ACTION.equals(accion)) {
            do_Reporte175_Back_Action(request);
        }
        // CTORRES 24/04/2013
        // Reporte 176
        if (REPORTE_176__SEND_ACTION.equals(accion)) {
            do_Reporte176_Send_Action(request);
        }
        if (REPORTE_176__BACK_ACTION.equals(accion)) {
            do_Reporte176_Back_Action(request);
        }
        //***********************************************
        //GCABRERA 27/12/2011
        //Reporte E04
        if (REPORTE_E04_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            session.removeAttribute(AWReportes.USUARIOS_CONSULTAR_POR_CIRCULO);
            do_ReporteE04_SaveState(request);
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }
        if (REPORTE_E04_LOADUSUARIOSBYCIRCULO.equals(accion)) {
            session.removeAttribute(AWReportes.USUARIOS_CONSULTAR_POR_CIRCULO);
            do_ReporteE04_SaveState(request);
            return consultarUsuariosPorCirculo(request, AWReportes.REPORTE_E04__PARAM_IDCIRCULO, EvnReportes.USUARIOS_CONSULTAR_POR_CIRCULO_PROCESO);
        }

        // GCABRERA 27/12/2011
        // Reporte E04
        if (REPORTE_E04__SEND_ACTION.equals(accion)) {
            do_ReporteE04_Send_Action(request);
        }
        if (REPORTE_E04__BACK_ACTION.equals(accion)) {
            do_ReporteE04_Back_Action(request);
        }

        /**
         * Autor: Edgar Lora Mantis: 0011319 Requerimiento: 075_151
         */
        if (REPORTE_170_LOADCIRCULOS.equals(accion)) {
            session.removeAttribute(AWReportes.USUARIOS_CONSULTAR_POR_CIRCULO);
            do_Reporte_170_SaveState(request);
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }

        if (REPORTE_170_SEND_ACTION.equals(accion)) {
            do_Reporte_170_Send_Action(request);
        }

        if (REPORTE_170_BACK_ACTION.equals(accion)) {
            do_Reporte_170_Back_Action(request);
        }
        
        if (REPORTE_180__SEND_ACTION.equals(accion)) {
            do_Reporte180_Send_Action(request);
        }

        /**
         * @Autor: Santiago Vásquez
         * @Change: 1252.REPORTE TURNOS CORRECCION ENCABEZADO
         */
        if (REPORTE_178_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }

        if (REPORTE_178_LOADUSUARIOSBYCIRCULO.equals(accion)) {
            session.removeAttribute(AWReportes.USUARIOS_CONSULTAR_POR_CIRCULO);
            do_Reporte_178_SaveState(request);
            return consultarUsuariosPorCirculo(request, AWReportes.REPORTE_178__PARAM_IDCIRCULO, EvnReportes.USUARIOS_CONSULTAR_POR_CIRCULO_ROL_CALIFICADOR);
        }

        if (REPORTE_178__SEND_ACTION.equals(accion)) {
            do_Reporte_178_Send_Action(request);
        }

        if (REPORTE_178__BACK_ACTION.equals(accion)) {
            do_Reporte_178_Back_Action(request);
        }
        if (REPORTE_182__SEND_ACTION.equals(accion)) {
            do_Reporte182_Send_Action(request);
        }
        /**
         * @Autor: Santiago Vásquez
         * @Change: 2015.REPORTE.ESTADISTICO.POR.NATURALEZA.JURIDICA
         */
        if (REPORTE_179_LOADCIRCULOSBYUSUARIO.equals(accion)) {
            List listaPrediosInmodificable = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_PREDIO);
            List listaPredios = new ArrayList();
            listaPredios.add(new ElementoLista("TODOS", " TODOS"));
            for (int i = 0; i < listaPrediosInmodificable.size(); i++) {
                listaPredios.add(listaPrediosInmodificable.get(i));
            }
            session.setAttribute(REPORTE_179__LISTA_PREDIOS, listaPredios);
            return do_ReporteXX_LoadCirculosByUsuario_Action(request);
        }

        if (REPORTE_179__SEND_ACTION.equals(accion)) {
            do_Reporte_179_Send_Action(request);
        }

        if (REPORTE_179__BACK_ACTION.equals(accion)) {
            do_Reporte_179_Back_Action(request);
        }
        if (REPORTE_180__BACK_ACTION.equals(accion)) {
            String[] itemIds = new String[]{
                REPORTE_180__PARAM_PFECHA, REPORTE_180__PARAM_PCIRCULONOMBRE
            };
            do_ReporteXX_Back_Action(itemIds, request);
        }

        if (REPORTE_182__BACK_ACTION.equals(accion)) {
            String[] itemIds = new String[]{
                REPORTE_182__PARAM_PFECHA, REPORTE_182__PARAM_PCIRCULONOMBRE
            };
            do_ReporteXX_Back_Action(itemIds, request);
        }
        return null;
    }

    /**
     * do_ReporteXX_LoadCirculosByUsuario_Action
     *
     * @param request HttpServletRequest
     * @return Evento
     */
    private Evento do_ReporteXX_LoadCirculosByUsuario_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        HttpSession session;
        session = request.getSession();

        gov.sir.core.negocio.modelo.Usuario param_UsuarioSir;
        param_UsuarioSir = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

        EvnReportes result;
        result = new EvnReportes(EvnReportes.CONSULTA_CIRCULOS_BY_USUARIO);
        result.setUsuarioSir(param_UsuarioSir);

        return result;

    } // end-method: do_ReporteXX_LoadCirculosByUsuario_Action

    /**
     * do_ReporteRel01_FindRelatedFases_Action
     *
     * @param request HttpServletRequest
     * @return Evento
     */
    private Evento do_ReporteRel01_FindRelatedFases_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_REL01__PARAM_RELACIONID);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Relacion: Debe digitar un valor");

        }

        // savestate ---------------------------------------------------
        do_ReporteRel01_SaveState(request);
        // -------------------------------------------------------------

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        HttpSession session = request.getSession();

        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        EvnReportes evento;
        evento = new EvnReportes(usuario, EvnReportes.DO_FINDRELATEDFASESBYRELACIONID_EVENT);
        evento.setRelacionId(param_01);
        return evento;

    } // :do_ReporteRel01_FindRelatedFases_Action

    // ------------------------------------------------------------------------------
    /**
     * do_ReporteRel01_FindRelatedFases_Action
     *
     * @param request HttpServletRequest
     * @return Evento
     */
    private Evento do_ReporteRel01_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_REL01__PARAM_RELACIONID);

        String param_02 = request.getParameter(REPORTE_REL01__PARAM_FASESELECTED);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Relacion: Debe digitar un valor");

        }

        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {
            exception.addError(
                    "parametro: Fase: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_02)) {
                exception.addError(
                        "parametro: Fase: Debe digitar un valor");
            }
        }

        // savestate ---------------------------------------------------
        do_ReporteRel01_SaveState(request);
        // -------------------------------------------------------------

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
/*
        String[] params = new String[]{
        REPORTE_XX__PARAM_CMDKEY
        , REPORTE_87__PARAM_PFECHA
        , REPORTE_87__PARAM_PESTADO
        , REPORTE_87__PARAM_PNUMPAGINA
        , REPORTE_87__PARAM_PCIRCULO
        };

        String[] values = new String[] {
        REPORTE_87__PARAM_CMDKEY
        , param_01
        , param_02
        , param_03
        , param_04

        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri( request, url );
         */
        HttpSession session = request.getSession();

        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        EvnReportes evento;
        evento = new EvnReportes(usuario, EvnReportes.DO_GENERATERELACIONREPORT_EVENT);
        evento.setRelacionId(param_01);
        evento.setFaseId(param_02);
        return evento;

    } // :do_ReporteRel01_Send_Action

    private void doEnd_ReporteRel01_Send_Action(HttpServletRequest request, EvnRespReportes eventoRespuesta) {

        Relacion relacion;
        relacion = eventoRespuesta.getRelacion();

        String param_CmdKey;
        param_CmdKey = relacion.getTipoRelacion().getReporte();

        String param_01 = relacion.getIdRelacion();
        String param_02 = "";
        String param_03 = relacion.getIdFase();

        StringTokenizer token = new StringTokenizer(param_01, "-");
        String anio = token.nextToken();
        String idCirculo = token.nextToken();

        String param_04 = idCirculo;

        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_REL01__PARAMREPORT_PRELACIONID, REPORTE_REL01__PARAMREPORT_PPROCESOID, REPORTE_REL01__PARAMREPORT_PFASEID, REPORTE_REL01__PARAMREPORT_PCIRCULOID
        };

        String[] values = new String[]{
            param_CmdKey, param_01, param_02, param_03, param_04
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

    }

    private void do_ReporteRel01_Back_Action(HttpServletRequest request) {

        do_ReporteRel01_RemoveState(request);

    } // :do_ReporteRel01_Back_Action

    // :doEnd_ReporteRel01_Send_Action
    /**
     * @param request
     */
    /*	private void reporte08(HttpServletRequest request)
    throws ValidacionParametrosException {
    ValidacionParametrosException exception =
    new ValidacionParametrosException();
    String fechaInicioS =
    request.getParameter(AWConsultasReparto.FECHA_INICIO);

    SimpleDateFormat df = new SimpleDateFormat();
    df.applyPattern("dd/MM/yyyy");
    String send1 = null;

    Date fechaInicio = null;
    try {
    fechaInicio = df.parse(fechaInicioS);
    df.applyPattern("yyyy-MM-dd");
    send1 = df.format(fechaInicio);
    } catch (Exception e) {
    exception.addError("La fecha es inválida" + e.getMessage());
    }


    String param_02
    = request.getParameter( REPORTE_08__PARAM_PCIRCULONOMBRE );

    // apply validators
    if(   ( null == param_02 )
    || ( "".equalsIgnoreCase( param_02 ) ) ) {

    exception.addError( "parametro: Circulo: Debe digitar un valor" );

    }
    else {

    if( WebKeys.SIN_SELECCIONAR.equals( param_02 ) ) {
    exception.addError( "parametro: Circulo: Debe digitar un valor" );
    }


    }


    // raise ------------------------------------------------


    if( exception.getErrores().size() > 0 ) {
    throw exception;
    }

    // -----------------------------------------------------


    String send0 = "reporte-08.report";

    String[] params = new String[] {
    "cmdkey"
    , "P_TURNO_FECHA"
    , REPORTE_08__PARAM_PCIRCULONOMBRE
    };
    String[] values = new String[] {
    send0
    , send1
    , param_02
    };
    String url = makeUrl(params, values);
    reportsServices_ConfigureParameterUri( request, url );
    }*/
//------------------------------------------------------------------------------------------
    private void do_ReporteXX_Back_Action(String[] itemIds, HttpServletRequest request) {

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    } // :do_ReporteXX_Back_Action

    private void do_ReporteXX_SaveState(String[] itemIds, HttpServletRequest request) {

        HttpSession session;

        session = request.getSession();

        save_PageItemsState(itemIds, request, session);

    } // :do_ReporteXX_SaveState

    private void do_ReporteXX_RemoveState(String[] itemIds, HttpServletRequest request) {

        HttpSession session;

        session = request.getSession();

        delete_PageItemsState(itemIds, request, session);

    } // :do_ReporteXX_RemoveState

//-------------------------------------------------------------------------------------------
    private void do_Reporte01_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_01__PARAM_IDREPARTONOTARIAL);

        String param_02 = request.getParameter(REPORTE_01__PARAM_VCIUDAD);

        String param_03 = request.getParameter(REPORTE_01__PARAM_VNOMBRECOORD);

        String param_04 = request.getParameter(REPORTE_01__PARAM_PCIRCULONOMBRE);

        HttpSession session;
        session = request.getSession();
        gov.sir.core.negocio.modelo.Usuario param_UsuarioSir;
        param_UsuarioSir = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

        String param_05 = param_UsuarioSir.getNombreCompletoUsuario();

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Id Reparto notarial: Debe digitar un valor");

        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Ciudad: Debe digitar un valor");

        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Nombre Coordinador: Debe digitar un valor");

        }

//		 apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        do_Reporte01_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_01__PARAM_IDREPARTONOTARIAL, REPORTE_01__PARAM_VCIUDAD, REPORTE_01__PARAM_VNOMBRECOORD, REPORTE_01__PARAM_PCIRCULONOMBRE, REPORTE_01__PARAM_PNOMBREUSUARIO
        };

        String[] values = new String[]{
            REPORTE_01__PARAM_CMDKEY, param_01, param_02, param_03, param_04, param_05
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);
        do_Reporte01_RemoveState(request);
    }

    private void do_Reporte01_Back_Action(HttpServletRequest request) {

        do_Reporte01_RemoveState(request);

    } // :do_Reporte01_Back_Action

    private void do_Reporte01_SaveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_01__PARAM_IDREPARTONOTARIAL, REPORTE_01__PARAM_VCIUDAD, REPORTE_01__PARAM_VNOMBRECOORD, REPORTE_01__PARAM_PCIRCULONOMBRE, REPORTE_01__PARAM_PNOMBREUSUARIO
        };

        session = request.getSession();

        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte01_SaveState

    private void do_Reporte01_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_01__PARAM_IDREPARTONOTARIAL, REPORTE_01__PARAM_VCIUDAD, REPORTE_01__PARAM_VNOMBRECOORD, REPORTE_01__PARAM_PCIRCULONOMBRE, REPORTE_01__PARAM_PNOMBREUSUARIO
        };

        session = request.getSession();

        delete_PageItemsState(itemIds, request, session);

    } // :do_Reporte01_RemoveState

    protected void reportsServices_ConfigureParameterUri(HttpServletRequest request, String uri) {

        request.setAttribute(REPORTSSERVICES_REPORTURI, uri);
        request.getSession().setAttribute(REPORTSSERVICES_REPORTURI, uri);
        request.setAttribute(REPORTSSERVICES_REPORTDISPLAYENABLED, Boolean.TRUE);

    }

    // -------------------------------------------------------------------------------
    /* Reporte 04 - DIARIO DESANOTADOR DE DOCUMENTOS DEL DÍA
     * AHERRENO 11/04/2011
     * Se agregan variables: Fecha Final y Usuario que genera el reporte
     */
    private void do_Reporte04_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_04__PARAM_PFECHADESANOTACION);
        String param_02 = request.getParameter(REPORTE_04__PARAM_PCIRCULONOMBRE);
        String param_03 = request.getParameter(REPORTE_04__PARAM_PNUMPAGINA);
        String param_04 = request.getParameter(REPORTE_04__PARAM_PFECHADESANOTACIONFIN);
        String param_05 = request.getParameter(REPORTE_04__PARAM_USUARIOLOG);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Desanotacion Inicio: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Desanotacion Inicio: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_02)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: No. de Página: Debe digitar un valor");

        } else {
            for (int i = 0; i < param_03.length(); i++) {
                if (!Character.isDigit(param_03.charAt(i))) {
                    exception.addError("parametro: No. de Página: Debe ser un valor numerico");
                    i = param_03.length();
                }
            }
        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {

            exception.addError("parametro: Fecha Desanotacion Fin: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_04);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_04 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Desanotacion Fin: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_05) || ("".equalsIgnoreCase(param_05))) {
            exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_05)) {
                exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
            }
        }

        do_Reporte04_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_04__PARAM_PFECHADESANOTACION, REPORTE_04__PARAM_PCIRCULONOMBRE, REPORTE_04__PARAM_PNUMPAGINA, REPORTE_04__PARAM_PFECHADESANOTACIONFIN, REPORTE_04__PARAM_USUARIOLOG
        };

        String[] values = new String[]{
            REPORTE_04__PARAM_CMDKEY, param_01, param_02, param_03, param_04, param_05
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_Reporte04_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte04_Back_Action(HttpServletRequest request) {

        do_Reporte04_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    } // :do_Reporte04_Back_Action

    private void do_Reporte04_SaveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_04__PARAM_PFECHADESANOTACION, REPORTE_04__PARAM_PCIRCULONOMBRE, REPORTE_04__PARAM_PNUMPAGINA, REPORTE_04__PARAM_PFECHADESANOTACIONFIN, REPORTE_04__PARAM_USUARIOLOG
        };

        session = request.getSession();

        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte04_SaveState

    private void do_Reporte04_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_04__PARAM_PFECHADESANOTACION, REPORTE_04__PARAM_PCIRCULONOMBRE, REPORTE_04__PARAM_PNUMPAGINA, REPORTE_04__PARAM_PFECHADESANOTACIONFIN, REPORTE_04__PARAM_USUARIOLOG
        };

        session = request.getSession();

        delete_PageItemsState(itemIds, request, session);

    } // :do_Reporte04_RemoveState

    // -------------------------------------------------------------------------------
    private void do_Reporte05_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_05__PARAM_PPAGOFECHA);

        String param_02 = request.getParameter(REPORTE_05__PARAM_PNUMPAGINA);

        String param_03 = request.getParameter(REPORTE_05__PARAM_PCIRCULONOMBRE);

        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha pago: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha pago : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: No. de Página: Debe digitar un valor");

        } else {
            for (int i = 0; i < param_02.length(); i++) {
                if (!Character.isDigit(param_02.charAt(i))) {
                    exception.addError("parametro: No. de Página: Debe ser un valor numerico");
                    i = param_02.length();
                }
            }
        }

        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        do_Reporte05_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_05__PARAM_PPAGOFECHA, REPORTE_05__PARAM_PNUMPAGINA, REPORTE_05__PARAM_PCIRCULONOMBRE
        };

        String[] values = new String[]{
            REPORTE_05__PARAM_CMDKEY, param_01, param_02, param_03
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_Reporte05_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte05_Back_Action(HttpServletRequest request) {

        do_Reporte05_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    } // :do_Reporte05_Back_Action

    private void do_Reporte05_SaveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_05__PARAM_PPAGOFECHA, REPORTE_05__PARAM_PNUMPAGINA, REPORTE_05__PARAM_PCIRCULONOMBRE
        };

        session = request.getSession();

        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte05_SaveState

    private void do_Reporte05_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_05__PARAM_PPAGOFECHA, REPORTE_05__PARAM_PNUMPAGINA, REPORTE_05__PARAM_PCIRCULONOMBRE
        };

        session = request.getSession();

        delete_PageItemsState(itemIds, request, session);

    } // :do_Reporte05_RemoveState

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte06_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_06__PARAM_PTURNOFECHA);

        String param_02 = request.getParameter(REPORTE_06__PARAM_PCIRCULONOMBRE);

        String param_03 = request.getParameter(REPORTE_06__PARAM_PNUMPAGINA);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Radicaci&oacute;n: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Radicaci&oacute;n : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_02)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: No. de Página: Debe digitar un valor");

        } else {
            for (int i = 0; i < param_03.length(); i++) {
                if (!Character.isDigit(param_03.charAt(i))) {
                    exception.addError("parametro: No. de Página: Debe ser un valor numerico");
                    i = param_03.length();
                }
            }
        }

        do_Reporte06_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_06__PARAM_PTURNOFECHA, REPORTE_06__PARAM_PCIRCULONOMBRE, REPORTE_06__PARAM_PNUMPAGINA
        };

        String[] values = new String[]{
            REPORTE_06__PARAM_CMDKEY, param_01, param_02, param_03
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_Reporte06_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte06_Back_Action(HttpServletRequest request) {

        do_Reporte06_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    } // :do_Reporte06_Back_Action

    private void do_Reporte06_SaveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_06__PARAM_PTURNOFECHA, REPORTE_06__PARAM_PCIRCULONOMBRE, REPORTE_06__PARAM_PNUMPAGINA
        };

        session = request.getSession();

        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte06_SaveState

    private void do_Reporte06_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_06__PARAM_PTURNOFECHA, REPORTE_06__PARAM_PCIRCULONOMBRE, REPORTE_06__PARAM_PNUMPAGINA
        };

        session = request.getSession();

        delete_PageItemsState(itemIds, request, session);

    } // :do_Reporte06_RemoveState

    private void do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_XX__ITEM_CIRCULOSBYUSUARIO, "PAGE_LOAD"
        };

        session = request.getSession();

        delete_PageItemsState(itemIds, request, session);

    } // :do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte6B_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_6B__PARAM_PTURNOFECHA);

        String param_02 = request.getParameter(REPORTE_6B__PARAM_PCIRCULONOMBRE);

        String param_03 = request.getParameter(REPORTE_6B__PARAM_PNUMPAGINA);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Radicaci&oacute;n: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Radicaci&oacute;n : La fecha es inválida" + e.getMessage());
            }

        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_02)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: No. de Página: Debe digitar un valor");

        } else {
            for (int i = 0; i < param_03.length(); i++) {
                if (!Character.isDigit(param_03.charAt(i))) {
                    exception.addError("parametro: No. de Página: Debe ser un valor numerico");
                    i = param_03.length();
                }
            }
        }

        do_Reporte6B_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_6B__PARAM_PTURNOFECHA, REPORTE_6B__PARAM_PCIRCULONOMBRE, REPORTE_6B__PARAM_PNUMPAGINA
        };

        String[] values = new String[]{
            REPORTE_6B__PARAM_CMDKEY, param_01, param_02, param_03
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);
        do_Reporte6B_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte6B_Back_Action(HttpServletRequest request) {

        do_Reporte6B_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    } // :do_Reporte6B_Back_Action

    private void do_Reporte6B_SaveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_6B__PARAM_PTURNOFECHA, REPORTE_6B__PARAM_PCIRCULONOMBRE, REPORTE_6B__PARAM_PNUMPAGINA
        };

        session = request.getSession();

        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte6B_SaveState

    private void do_Reporte6B_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_6B__PARAM_PTURNOFECHA, REPORTE_6B__PARAM_PCIRCULONOMBRE, REPORTE_6B__PARAM_PNUMPAGINA
        };

        session = request.getSession();

        delete_PageItemsState(itemIds, request, session);

    } // :do_Reporte6B_RemoveState

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    // AHERRENO 12/07/2011
    // Reporte 08 - Se obtiene parametro REPORTE_08__PARAM_USUARIOLOG
    private void do_Reporte08_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_08__PARAM_PTURNOFECHA);

        String param_02 = request.getParameter(REPORTE_08__PARAM_PCIRCULONOMBRE);

        String param_03 = request.getParameter(REPORTE_08__PARAM_PNUMPAGINA);

        String param_04 = request.getParameter(REPORTE_08__PARAM_USUARIOLOG);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Turno: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Turno: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_02)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: No. de P&aacute;gina: Debe digitar un valor");

        } else {
            for (int i = 0; i < param_03.length(); i++) {
                if (!Character.isDigit(param_03.charAt(i))) {
                    exception.addError("parametro: No. de P&aacute;gina: Debe ser un valor numerico");
                    i = param_03.length();
                }
            }
        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {

            exception.addError("parametro: Usuario Logueado: Debe digitar un valor");

        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Usuario Logueado: Debe digitar un valor");
            }
        }

        do_Reporte08_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_08__PARAM_PTURNOFECHA, REPORTE_08__PARAM_PCIRCULONOMBRE, REPORTE_08__PARAM_PNUMPAGINA, REPORTE_08__PARAM_USUARIOLOG
        };

        String[] values = new String[]{
            REPORTE_08_PARAM_CMDKEY, param_01, param_02, param_03, param_04
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_Reporte08_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte08_Back_Action(HttpServletRequest request) {

        do_Reporte08_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    } // :do_Reporte08_Back_Action

    private void do_Reporte08_SaveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_08__PARAM_PTURNOFECHA, REPORTE_08__PARAM_PCIRCULONOMBRE, REPORTE_08__PARAM_PNUMPAGINA, REPORTE_08__PARAM_USUARIOLOG
        };

        session = request.getSession();

        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte08_SaveState

    private void do_Reporte08_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_08__PARAM_PTURNOFECHA, REPORTE_08__PARAM_PCIRCULONOMBRE, REPORTE_08__PARAM_PNUMPAGINA, REPORTE_08__PARAM_USUARIOLOG
        };

        session = request.getSession();

        delete_PageItemsState(itemIds, request, session);

    } // :do_Reporte08_RemoveState

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    // AHERRENO 02/11/2010
    // Reporte 09 - Se obtiene parametro REPORTE_09__PARAM_USUARIOLOG
    private void do_Reporte09_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_09__PARAM_PTURNOFECHA);

        String param_02 = request.getParameter(REPORTE_09__PARAM_PCIRCULONOMBRE);

        String param_03 = request.getParameter(REPORTE_09__PARAM_PNUMPAGINA);

        String param_04 = request.getParameter(REPORTE_09__PARAM_USUARIOLOG);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Turno: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Desanotacion : La fecha es inválida" + e.getMessage());
            }

        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_02)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: No. de Página: Debe digitar un valor");

        } else {
            for (int i = 0; i < param_03.length(); i++) {
                if (!Character.isDigit(param_03.charAt(i))) {
                    exception.addError("parametro: No. de Página: Debe ser un valor numerico");
                    i = param_03.length();
                }
            }
        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {

            exception.addError("parametro: Usuario Logueado: Debe digitar un valor");

        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Usuario Logueado: Debe digitar un valor");
            }
        }

        do_Reporte09_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_09__PARAM_PCIRCULONOMBRE, REPORTE_09__PARAM_PNUMPAGINA, REPORTE_09__PARAM_PTURNOFECHA, REPORTE_09__PARAM_USUARIOLOG
        };

        String[] values = new String[]{
            REPORTE_09__PARAM_CMDKEY, param_02, param_03, param_01, param_04
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_Reporte09_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte09_Back_Action(HttpServletRequest request) {

        do_Reporte09_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    } // :do_Reporte09_Back_Action

    private void do_Reporte09_SaveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_09__PARAM_PCIRCULONOMBRE, REPORTE_09__PARAM_PNUMPAGINA, REPORTE_09__PARAM_PTURNOFECHA, REPORTE_09__PARAM_USUARIOLOG
        };

        session = request.getSession();

        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte09_SaveState

    private void do_Reporte09_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_09__PARAM_PCIRCULONOMBRE, REPORTE_09__PARAM_PNUMPAGINA, REPORTE_09__PARAM_PTURNOFECHA, REPORTE_09__PARAM_USUARIOLOG
        };

        session = request.getSession();

        delete_PageItemsState(itemIds, request, session);

    } // :do_Reporte09_RemoveState

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte10_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_10__PARAM_PFECHAINI);

        String param_02 = request.getParameter(REPORTE_10__PARAM_PFECHAFIN);

        String param_03 = request.getParameter(REPORTE_10__PARAM_PCIRCULONOMBRE);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        do_Reporte10_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_10__PARAM_PFECHAINI, REPORTE_10__PARAM_PFECHAFIN, REPORTE_10__PARAM_PCIRCULONOMBRE
        };

        String[] values = new String[]{
            REPORTE_10__PARAM_CMDKEY, param_01, param_02, param_03
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_Reporte10_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte10_Back_Action(HttpServletRequest request) {

        do_Reporte10_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    } // :do_Reporte10_Back_Action

    private void do_Reporte10_SaveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_10__PARAM_PFECHAINI, REPORTE_10__PARAM_PFECHAFIN, REPORTE_10__PARAM_PCIRCULONOMBRE
        };

        session = request.getSession();

        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte10_SaveState

    private void do_Reporte10_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_10__PARAM_PFECHAINI, REPORTE_10__PARAM_PFECHAFIN, REPORTE_10__PARAM_PCIRCULONOMBRE
        };

        session = request.getSession();

        delete_PageItemsState(itemIds, request, session);

    } // :do_Reporte10_RemoveState

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    // AHERRENO 02/11/2010
    // Reporte 11 - Se obtiene parametro REPORTE_11__PARAM_USUARIOLOG
    private void do_Reporte11_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_11__PARAM_PFECHAINI);

        String param_02 = request.getParameter(REPORTE_11__PARAM_PFECHAFIN);

        String param_03 = request.getParameter(REPORTE_11__PARAM_PCIRCULONOMBRE);

        String param_04 = request.getParameter(REPORTE_11__PARAM_USUARIOLOG);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);

                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {

            exception.addError("parametro: Usuario Logueado: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Usuario Logueado: Debe digitar un valor");
            }

        }
        do_Reporte11_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_11__PARAM_PFECHAINI, REPORTE_11__PARAM_PFECHAFIN, REPORTE_11__PARAM_PCIRCULONOMBRE, REPORTE_11__PARAM_USUARIOLOG
        };

        String[] values = new String[]{
            REPORTE_11__PARAM_CMDKEY, param_01, param_02, param_03, param_04
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_Reporte11_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte11_Back_Action(HttpServletRequest request) {

        do_Reporte11_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    } // :do_Reporte11_Back_Action

    private void do_Reporte11_SaveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_11__PARAM_PFECHAINI, REPORTE_11__PARAM_PFECHAFIN, REPORTE_11__PARAM_PCIRCULONOMBRE, REPORTE_11__PARAM_USUARIOLOG
        };

        session = request.getSession();

        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte11_SaveState

    private void do_Reporte11_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_11__PARAM_PFECHAINI, REPORTE_11__PARAM_PFECHAFIN, REPORTE_11__PARAM_PCIRCULONOMBRE, REPORTE_11__PARAM_USUARIOLOG
        };

        session = request.getSession();

        delete_PageItemsState(itemIds, request, session);

    } // :do_Reporte11_RemoveState

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    //AHERRENO 16/11/2010
    //Reporte 15 - Se agrega parametro P_USUARIO_LOG
    private void do_Reporte15_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_15__PARAM_PLIQUIDFECHAINI);

        String param_02 = request.getParameter(REPORTE_15__PARAM_PLIQUIDFECHAFIN);

        String param_03 = request.getParameter(REPORTE_15__PARAM_PCIRCULONOMBRE);

        String param_04 = request.getParameter(REPORTE_15__PARAM_USUARIOLOG);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {

            exception.addError("parametro: Usuario Logueado: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Usuario Logueado: Debe digitar un valor");
            }

        }

        do_Reporte15_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_15__PARAM_PLIQUIDFECHAINI, REPORTE_15__PARAM_PLIQUIDFECHAFIN, REPORTE_15__PARAM_PCIRCULONOMBRE, REPORTE_15__PARAM_USUARIOLOG
        };

        String[] values = new String[]{
            REPORTE_15__PARAM_CMDKEY, param_01, param_02, param_03, param_04
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_Reporte15_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    }

    private void do_Reporte15_Back_Action(HttpServletRequest request) {

        do_Reporte15_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    } // :do_Reporte15_Back_Action

    private void do_Reporte15_SaveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_15__PARAM_PLIQUIDFECHAINI, REPORTE_15__PARAM_PLIQUIDFECHAFIN, REPORTE_15__PARAM_PCIRCULONOMBRE, REPORTE_15__PARAM_USUARIOLOG
        };

        session = request.getSession();

        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte15_SaveState

    private void do_Reporte15_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_15__PARAM_PLIQUIDFECHAINI, REPORTE_15__PARAM_PLIQUIDFECHAFIN, REPORTE_15__PARAM_PCIRCULONOMBRE, REPORTE_15__PARAM_USUARIOLOG
        };

        session = request.getSession();

        delete_PageItemsState(itemIds, request, session);

    } // :do_Reporte15_RemoveState

    // -------------------------------------------------------------------------------
    private void do_Reporte16_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_16__PARAM_PFECHAINI);

        String param_02 = request.getParameter(REPORTE_16__PARAM_PFECHAFIN);

        String param_03 = request.getParameter(REPORTE_16__PARAM_PCIRCULONOMBRE);

        Log.getInstance().debug(AWReportes.class, "parametro 1: " + param_01);
        Log.getInstance().debug(AWReportes.class, "parametro 2: " + param_02);
        Log.getInstance().debug(AWReportes.class, "parametro 3: " + param_03);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }
        do_Reporte16_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_16__PARAM_PFECHAINI, REPORTE_16__PARAM_PFECHAFIN, REPORTE_16__PARAM_PCIRCULONOMBRE
        };

        String[] values = new String[]{
            REPORTE_16__PARAM_CMDKEY, param_01, param_02, param_03
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_Reporte16_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte16_Back_Action(HttpServletRequest request) {

        do_Reporte16_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    } // :do_Reporte16_Back_Action

    private void do_Reporte16_SaveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_16__PARAM_PFECHAINI, REPORTE_16__PARAM_PFECHAFIN, REPORTE_16__PARAM_PCIRCULONOMBRE
        };

        session = request.getSession();

        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte16_SaveState

    private void do_Reporte16_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_16__PARAM_PFECHAINI, REPORTE_16__PARAM_PFECHAFIN, REPORTE_16__PARAM_PCIRCULONOMBRE
        };

        session = request.getSession();

        delete_PageItemsState(itemIds, request, session);

    } // :do_Reporte16_RemoveState

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte17_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_17__PARAM_PFECHAINI);

        String param_02 = request.getParameter(REPORTE_17__PARAM_PFECHAFIN);

        String param_03 = request.getParameter(REPORTE_17__PARAM_PCIRCULONOMBRE);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicial: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicial: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Final: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Final: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }
        do_Reporte17_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_17__PARAM_PFECHAINI, REPORTE_17__PARAM_PFECHAFIN, REPORTE_17__PARAM_PCIRCULONOMBRE
        };

        String[] values = new String[]{
            REPORTE_17__PARAM_CMDKEY, param_01, param_02, param_03
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_Reporte17_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    }

    private void do_Reporte17_Back_Action(HttpServletRequest request) {

        do_Reporte17_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    } // :do_Reporte17_Back_Action

    private void do_Reporte17_SaveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_17__PARAM_PFECHAINI, REPORTE_17__PARAM_PFECHAFIN, REPORTE_17__PARAM_PCIRCULONOMBRE
        };

        session = request.getSession();

        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte17_SaveState

    private void do_Reporte17_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_17__PARAM_PFECHAINI, REPORTE_17__PARAM_PFECHAFIN, REPORTE_17__PARAM_PCIRCULONOMBRE
        };

        session = request.getSession();

        delete_PageItemsState(itemIds, request, session);

    } // :do_Reporte17_RemoveState

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    //AHERRENO 16/11/2010
    //Reporte 18 - Se agrega parametro P_USUARIO_LOG
    private void do_Reporte18_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_18__PARAM_PFECHAINI);

        String param_02 = request.getParameter(REPORTE_18__PARAM_PFECHAFIN);

        String param_03 = request.getParameter(REPORTE_18__PARAM_PCIRCULONOMBRE);

        String param_04 = request.getParameter(REPORTE_18__PARAM_USUARIOLOG);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Usuario Logueado: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Usuario Logueado: Debe digitar un valor");
            }

        }

        do_Reporte18_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_18__PARAM_PFECHAINI, REPORTE_18__PARAM_PFECHAFIN, REPORTE_18__PARAM_PCIRCULONOMBRE, REPORTE_18__PARAM_USUARIOLOG
        };

        String[] values = new String[]{
            REPORTE_18__PARAM_CMDKEY, param_01, param_02, param_03, param_04
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_Reporte18_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte18_Back_Action(HttpServletRequest request) {

        do_Reporte18_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    } // :do_Reporte18_Back_Action

    private void do_Reporte18_SaveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_18__PARAM_PFECHAINI, REPORTE_18__PARAM_PFECHAFIN, REPORTE_18__PARAM_PCIRCULONOMBRE, REPORTE_18__PARAM_USUARIOLOG
        };

        session = request.getSession();

        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte18_SaveState

    private void do_Reporte18_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_18__PARAM_PFECHAINI, REPORTE_18__PARAM_PFECHAFIN, REPORTE_18__PARAM_PCIRCULONOMBRE, REPORTE_18__PARAM_USUARIOLOG
        };

        session = request.getSession();

        delete_PageItemsState(itemIds, request, session);

    } // :do_Reporte18_RemoveState

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte19_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_19__PARAM_PPAGOFECHAINI);

        String param_02 = request.getParameter(REPORTE_19__PARAM_PPAGOFECHAFIN);

        String param_03 = request.getParameter(REPORTE_19__PARAM_PCIRCULONOMBRE);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        do_Reporte19_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_19__PARAM_PPAGOFECHAINI, REPORTE_19__PARAM_PPAGOFECHAFIN, REPORTE_19__PARAM_PCIRCULONOMBRE
        };

        String[] values = new String[]{
            REPORTE_19__PARAM_CMDKEY, param_01, param_02, param_03
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_Reporte19_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte19_Back_Action(HttpServletRequest request) {

        do_Reporte19_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    } // :do_Reporte19_Back_Action

    private void do_Reporte19_SaveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_19__PARAM_PPAGOFECHAINI, REPORTE_19__PARAM_PPAGOFECHAFIN, REPORTE_19__PARAM_PCIRCULONOMBRE
        };

        session = request.getSession();

        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte19_SaveState

    private void do_Reporte19_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_19__PARAM_PPAGOFECHAINI, REPORTE_19__PARAM_PPAGOFECHAFIN, REPORTE_19__PARAM_PCIRCULONOMBRE
        };

        session = request.getSession();

        delete_PageItemsState(itemIds, request, session);

    } // :do_Reporte19_RemoveState

    // -------------------------------------------------------------------------------
    private void do_Reporte20_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_20__PARAM_PPAGOFECHAINI);

        String param_02 = request.getParameter(REPORTE_20__PARAM_PPAGOFECHAFIN);

        String param_03 = request.getParameter(REPORTE_20__PARAM_PCIRCULONOMBRE);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }
        do_Reporte20_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_20__PARAM_PPAGOFECHAINI, REPORTE_20__PARAM_PPAGOFECHAFIN, REPORTE_20__PARAM_PCIRCULONOMBRE
        };

        String[] values = new String[]{
            REPORTE_20__PARAM_CMDKEY, param_01, param_02, param_03
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_Reporte20_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte20_Back_Action(HttpServletRequest request) {

        do_Reporte20_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    } // :do_Reporte20_Back_Action

    private void do_Reporte20_SaveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_20__PARAM_PPAGOFECHAINI, REPORTE_20__PARAM_PPAGOFECHAFIN, REPORTE_20__PARAM_PCIRCULONOMBRE
        };

        session = request.getSession();

        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte20_SaveState

    private void do_Reporte20_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_20__PARAM_PPAGOFECHAINI, REPORTE_20__PARAM_PPAGOFECHAFIN, REPORTE_20__PARAM_PCIRCULONOMBRE
        };

        session = request.getSession();

        delete_PageItemsState(itemIds, request, session);

    } // :do_Reporte20_RemoveState

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte21_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_21__PARAM_PLIQUIDACIONFECHAINI);

        String param_02 = request.getParameter(REPORTE_21__PARAM_PLIQUIDACIONFECHAFIN);

        String param_03 = request.getParameter(REPORTE_21__PARAM_PCIRCULONOMBRE);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        do_Reporte21_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_21__PARAM_PLIQUIDACIONFECHAINI, REPORTE_21__PARAM_PLIQUIDACIONFECHAFIN, REPORTE_21__PARAM_PCIRCULONOMBRE
        };

        String[] values = new String[]{
            REPORTE_21__PARAM_CMDKEY, param_01, param_02, param_03
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_Reporte21_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte21_Back_Action(HttpServletRequest request) {

        do_Reporte21_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    } // :do_Reporte21_Back_Action

    private void do_Reporte21_SaveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_21__PARAM_PLIQUIDACIONFECHAINI, REPORTE_21__PARAM_PLIQUIDACIONFECHAFIN, REPORTE_21__PARAM_PCIRCULONOMBRE
        };

        session = request.getSession();

        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte21_SaveState

    private void do_Reporte21_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_21__PARAM_PLIQUIDACIONFECHAINI, REPORTE_21__PARAM_PLIQUIDACIONFECHAFIN, REPORTE_21__PARAM_PCIRCULONOMBRE
        };

        session = request.getSession();

        delete_PageItemsState(itemIds, request, session);

    } // :do_Reporte21_RemoveState

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte22_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_22__PARAM_PPAGOFECHAINI);

        String param_03 = request.getParameter(REPORTE_22__PARAM_PCIRCULONOMBRE);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        do_Reporte22_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_22__PARAM_PPAGOFECHAINI, REPORTE_22__PARAM_PCIRCULONOMBRE
        };

        String[] values = new String[]{
            REPORTE_22__PARAM_CMDKEY, param_01, param_03
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_Reporte22_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte22_Back_Action(HttpServletRequest request) {

        do_Reporte22_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    } // :do_Reporte22_Back_Action

    private void do_Reporte22_SaveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_22__PARAM_PPAGOFECHAINI, REPORTE_22__PARAM_PCIRCULONOMBRE
        };

        session = request.getSession();

        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte22_SaveState

    private void do_Reporte22_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_22__PARAM_PPAGOFECHAINI, REPORTE_22__PARAM_PCIRCULONOMBRE
        };

        session = request.getSession();

        delete_PageItemsState(itemIds, request, session);

    } // :do_Reporte22_RemoveState

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte24_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_24__PARAM_PLIQUIDACIONFECHAINI);

        String param_02 = request.getParameter(REPORTE_24__PARAM_PLIQUIDACIONFECHAFIN);

        String param_03 = request.getParameter(REPORTE_24__PARAM_PCIRCULONOMBRE);

        //GCABRERA 15/12/2011
        //Se agrega parámetro REPORTE_24__PARAM_USUARIOLOG
        String param_04 = request.getParameter(REPORTE_24__PARAM_USUARIOLOG);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        do_Reporte24_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_24__PARAM_PLIQUIDACIONFECHAINI, REPORTE_24__PARAM_PLIQUIDACIONFECHAFIN, REPORTE_24__PARAM_PCIRCULONOMBRE, REPORTE_24__PARAM_USUARIOLOG
        };

        String[] values = new String[]{
            REPORTE_24__PARAM_CMDKEY, param_01, param_02, param_03, param_04
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_Reporte24_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte24_Back_Action(HttpServletRequest request) {

        do_Reporte24_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    } // :do_Reporte24_Back_Action

    private void do_Reporte24_SaveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_24__PARAM_PLIQUIDACIONFECHAINI, REPORTE_24__PARAM_PLIQUIDACIONFECHAFIN, REPORTE_24__PARAM_PCIRCULONOMBRE, REPORTE_24__PARAM_USUARIOLOG
        };

        session = request.getSession();

        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte24_SaveState

    private void do_Reporte24_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_24__PARAM_PLIQUIDACIONFECHAINI, REPORTE_24__PARAM_PLIQUIDACIONFECHAFIN, REPORTE_24__PARAM_PCIRCULONOMBRE, REPORTE_24__PARAM_USUARIOLOG
        };

        session = request.getSession();

        delete_PageItemsState(itemIds, request, session);

    } // :do_Reporte24_RemoveState

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte26_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_26__PARAM_PFECHAINI);

        String param_02 = request.getParameter(REPORTE_26__PARAM_PFECHAFIN);

        String param_03 = request.getParameter(REPORTE_26__PARAM_PCIRCULONOMBRE);

        String param_04 = request.getParameter(COficinaOrigen.OFICINA_ID_OFICINA_ORIGEN);

        String param_05 = request.getParameter(CDepartamento.ID_DEPARTAMENTO);
        String _param_05 = "";

        String param_07 = request.getParameter(CCategoria.ID_CATEGORIA);

        for (int i = 0; i < param_05.length(); i++) {
            if (Character.isDigit(param_05.charAt(i))) {
                _param_05 = _param_05 + param_05.charAt(i);
            }
        }

        String param_06 = request.getParameter(CMunicipio.ID_MUNICIPIO);
        String _param_06 = "";
        if (param_06 != null) {
            for (int i = 0; i < param_06.length(); i++) {
                if (Character.isDigit(param_06.charAt(i))) {
                    _param_06 = _param_06 + param_06.charAt(i);
                }
            }
        }

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {
            exception.addError("parametro: Notaria: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Notaria: Debe digitar un valor");
            }
        }

        if ((null == param_07) || ("".equalsIgnoreCase(param_07))) {

            exception.addError("parametro: Categoria: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_07)) {
                exception.addError("parametro: Categoria: Debe digitar un valor");
            }

        }

        do_Reporte26_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_26__PARAM_PFECHAINI, REPORTE_26__PARAM_PFECHAFIN, REPORTE_26__PARAM_PCIRCULONOMBRE, REPORTE_26__PARAM_PNOTARIA, REPORTE_26__PARAM_PDEPARTAMENTO, REPORTE_26__PARAM_PMUNICIPIO, REPORTE_26__PARAM_PCATEGORIA
        };

        String[] values = new String[]{
            REPORTE_26__PARAM_CMDKEY, param_01, param_02, param_03, param_04, _param_05, _param_06, param_07
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_Reporte26_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte26_Back_Action(HttpServletRequest request) {

        do_Reporte26_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    } // :do_Reporte26_Back_Action

    private void do_Reporte26_SaveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_26__PARAM_PFECHAINI, REPORTE_26__PARAM_PFECHAFIN, REPORTE_26__PARAM_PCIRCULONOMBRE, REPORTE_26__PARAM_PNOTARIA, REPORTE_26__PARAM_PDEPARTAMENTO, REPORTE_26__PARAM_PMUNICIPIO, REPORTE_26__PARAM_PCATEGORIA
        };

        session = request.getSession();

        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte26_SaveState

    private void do_Reporte26_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds;
        itemIds = new String[]{
            REPORTE_26__PARAM_PFECHAINI, REPORTE_26__PARAM_PFECHAFIN, REPORTE_26__PARAM_PCIRCULONOMBRE, REPORTE_26__PARAM_PNOTARIA, REPORTE_26__PARAM_PDEPARTAMENTO, REPORTE_26__PARAM_PMUNICIPIO, REPORTE_26__PARAM_PCATEGORIA
        };

        session = request.getSession();

        delete_PageItemsState(itemIds, request, session);

        session.removeAttribute(CDepartamento.ID_DEPARTAMENTO);
        session.removeAttribute(CMunicipio.ID_MUNICIPIO);
        session.removeAttribute(CVereda.ID_VEREDA);
        session.removeAttribute(CCategoria.ID_CATEGORIA);
        session.removeAttribute(COficinaOrigen.OFICINA_ID_OFICINA_ORIGEN);

        // DELETE LISTS OF VALUES IN CACHE
        itemIds = new String[]{
            AWReportes.LISTA_DEPARTAMENTOS, AWReportes.LISTA_MUNICIPIOS, AWReportes.LISTA_VEREDAS, AWReportes.LISTA_OFICINAS_POR_VEREDA
        };

        delete_PageItemsState(itemIds, request, session);
    } // :do_Reporte26_RemoveState

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte27_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_27__PARAM_PFECHAINI);

        String param_02 = request.getParameter(REPORTE_27__PARAM_PFECHAFIN);

        String param_03 = request.getParameter(REPORTE_27__PARAM_PCIRCULONOMBRE);

        String param_04 = request.getParameter(COficinaOrigen.OFICINA_ID_OFICINA_ORIGEN);

        String param_05 = request.getParameter(CDepartamento.ID_DEPARTAMENTO);
        String _param_05 = "";
        for (int i = 0; i < param_05.length(); i++) {
            if (Character.isDigit(param_05.charAt(i))) {
                _param_05 = _param_05 + param_05.charAt(i);
            }
        }

        String param_06 = request.getParameter(CMunicipio.ID_MUNICIPIO);
        String _param_06 = "";
        if (param_06 != null) {
            for (int i = 0; i < param_06.length(); i++) {
                if (Character.isDigit(param_06.charAt(i))) {
                    _param_06 = _param_06 + param_06.charAt(i);
                }
            }
        }

        String param_07 = request.getParameter(CCategoria.ID_CATEGORIA);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }

        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {

            exception.addError("parametro: Notaria: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Notaria: Debe digitar un valor");
            }

        }

        if ((null == param_07) || ("".equalsIgnoreCase(param_07))) {

            exception.addError("parametro: Categoria: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_07)) {
                exception.addError("parametro: Categoria: Debe digitar un valor");
            }

        }

        do_Reporte27_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_27__PARAM_PFECHAINI, REPORTE_27__PARAM_PFECHAFIN, REPORTE_27__PARAM_PCIRCULONOMBRE, REPORTE_27__PARAM_PNOTARIA, REPORTE_27__PARAM_PDEPARTAMENTO, REPORTE_27__PARAM_PMUNICIPIO, REPORTE_27__PARAM_PCATEGORIA
        };

        String[] values = new String[]{
            REPORTE_27__PARAM_CMDKEY, param_01, param_02, param_03, param_04, _param_05, _param_06, param_07
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_Reporte27_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte27_Back_Action(HttpServletRequest request) {

        do_Reporte27_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    } // :do_Reporte27_Back_Action

    private void do_Reporte27_SaveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_27__PARAM_PFECHAINI, REPORTE_27__PARAM_PFECHAFIN, REPORTE_27__PARAM_PCIRCULONOMBRE, REPORTE_27__PARAM_PNOTARIA, REPORTE_27__PARAM_PDEPARTAMENTO, REPORTE_27__PARAM_PMUNICIPIO, REPORTE_27__PARAM_PCATEGORIA
        };

        session = request.getSession();

        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte27_SaveState

    private void do_Reporte27_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_27__PARAM_PFECHAINI, REPORTE_27__PARAM_PFECHAFIN, REPORTE_27__PARAM_PCIRCULONOMBRE, REPORTE_27__PARAM_PNOTARIA, REPORTE_27__PARAM_PDEPARTAMENTO, REPORTE_27__PARAM_PMUNICIPIO, REPORTE_27__PARAM_PCATEGORIA
        };

        session = request.getSession();

        delete_PageItemsState(itemIds, request, session);

        session.removeAttribute(CDepartamento.ID_DEPARTAMENTO);
        session.removeAttribute(CMunicipio.ID_MUNICIPIO);
        session.removeAttribute(CVereda.ID_VEREDA);
        session.removeAttribute(CCategoria.ID_CATEGORIA);
        session.removeAttribute(COficinaOrigen.OFICINA_ID_OFICINA_ORIGEN);

        // DELETE LISTS OF VALUES IN CACHE
        itemIds = new String[]{
            AWReportes.LISTA_DEPARTAMENTOS, AWReportes.LISTA_MUNICIPIOS, AWReportes.LISTA_VEREDAS, AWReportes.LISTA_OFICINAS_POR_VEREDA
        };

        delete_PageItemsState(itemIds, request, session);

    } // :do_Reporte27_RemoveState

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte31_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_31__PARAM_PFECHAINI);

        String param_02 = request.getParameter(REPORTE_31__PARAM_PFECHAFIN);

        String param_03 = request.getParameter(REPORTE_31__PARAM_PCIRCULONOMBRE);

        String param_04 = request.getParameter(COficinaOrigen.OFICINA_ID_OFICINA_ORIGEN);

        String param_05 = request.getParameter(CDepartamento.ID_DEPARTAMENTO);
        String _param_05 = "";
        if (param_05 != null) {
            for (int i = 0; i < param_05.length(); i++) {
                if (Character.isDigit(param_05.charAt(i))) {
                    _param_05 = _param_05 + param_05.charAt(i);
                }
            }
        }
        String param_06 = request.getParameter(CMunicipio.ID_MUNICIPIO);
        String _param_06 = "";
        if (param_06 != null) {
            for (int i = 0; i < param_06.length(); i++) {
                if (Character.isDigit(param_06.charAt(i))) {
                    _param_06 = _param_06 + param_06.charAt(i);
                }
            }
        }
        String param_07 = request.getParameter(CCategoria.ID_CATEGORIA);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {

            exception.addError("parametro: Notaria: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Notaria: Debe digitar un valor");
            }

        }

        // apply validators
        if ((null == param_07) || ("".equalsIgnoreCase(param_07))) {

            exception.addError("parametro: Categoria: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_07)) {
                exception.addError("parametro: Categoria: Debe digitar un valor");
            }

        }

        String[] itemIds = new String[]{
            REPORTE_31__PARAM_PFECHAINI, REPORTE_31__PARAM_PFECHAFIN, REPORTE_31__PARAM_PCIRCULONOMBRE, REPORTE_31__PARAM_PNOTARIA, REPORTE_31__PARAM_PDEPARTAMENTO, REPORTE_31__PARAM_PMUNICIPIO, REPORTE_31__PARAM_PCATEGORIA
        };

        do_ReporteXX_SaveState(itemIds, request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_31__PARAM_PFECHAINI, REPORTE_31__PARAM_PFECHAFIN, REPORTE_31__PARAM_PCIRCULONOMBRE, REPORTE_31__PARAM_PNOTARIA, REPORTE_31__PARAM_PDEPARTAMENTO, REPORTE_31__PARAM_PMUNICIPIO, REPORTE_31__PARAM_PCATEGORIA
        };

        String[] values = new String[]{
            REPORTE_31__PARAM_CMDKEY, param_01, param_02, param_03, param_04, _param_05, _param_06, param_07
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    /**
     * do_ReporteXX_LoadCirculosByUsuario_Action
     *
     * @param request HttpServletRequest
     * @return Evento
     */
    private Evento do_Reporte30_Send_Action(HttpServletRequest request) throws ValidacionParametrosException {

        this.salvarDatosOficinaOrigenEnSesion(request);
        this.salvarDatosReporte30EnSesion(request);

        ValidacionParametrosException exception = new AdminReportes_InvalidParametersExceptionCollector();

        //DEPARTAMENTO
        String param_01 = request.getParameter(CDepartamento.ID_DEPARTAMENTO);
        String _param_01 = "";
        if (param_01 != null) {
            for (int i = 0; i < param_01.length(); i++) {
                if (Character.isDigit(param_01.charAt(i))) {
                    _param_01 = _param_01 + param_01.charAt(i);
                }
            }
        }

        //MUNICIPIO
        String param_02 = request.getParameter(CMunicipio.ID_MUNICIPIO);
        String _param_02 = "";
        if (param_02 != null) {
            for (int i = 0; i < param_02.length(); i++) {
                if (Character.isDigit(param_02.charAt(i))) {
                    _param_02 = _param_02 + param_02.charAt(i);
                }
            }
        }

        //NÚMERO DE REPARTO
        String param_03 = request.getParameter(REPORTE_30__PARAM_PNUMREPARTO);

        //ID DE NOTARIA
        String param_04 = request.getParameter(COficinaOrigen.OFICINA_ID_OFICINA_ORIGEN);

        //CATEGORIA
        String param_05 = request.getParameter(CCategoria.ID_CATEGORIA);

        //CIRCULO
        String param_06 = request.getParameter(REPORTE_30__PARAM_PCIRCULONOMBRE);

        // String param_01 = request.getParameter( REPORTE_30__PARAM_PFECHAINI );
        // String param_02 = request.getParameter( REPORTE_30__PARAM_PFECHAFIN );
        // apply validators
        /*
         * if( ( null == param_01 ) || ( "".equalsIgnoreCase( param_01 ) ) ) {
         *
         * exception.addError( "parametro: Fecha Inicio: Debe digitar un valor" );
         *  } else {
         *
         * SimpleDateFormat df = new SimpleDateFormat();
         * df.applyPattern("dd/MM/yyyy"); String send1 = null;
         *
         * Date fecha = null; try { fecha = df.parse(param_01);
         * df.applyPattern("yyyy-MM-dd"); send1 = df.format(fecha); param_01 =
         * send1; } catch (Exception e) { exception.addError("parametro: Fecha
         * Inicio : La fecha es inválida" + e.getMessage() ); } }
         *  // apply validators if( ( null == param_02 ) || (
         * "".equalsIgnoreCase( param_02 ) ) ) {
         *
         * exception.addError( "parametro: Fecha Fin: Debe digitar un valor" );
         *  } else {
         *
         * SimpleDateFormat df = new SimpleDateFormat();
         * df.applyPattern("dd/MM/yyyy"); String send1 = null;
         *
         * Date fecha = null; try { fecha = df.parse(param_02);
         * df.applyPattern("yyyy-MM-dd"); send1 = df.format(fecha); param_02 =
         * send1; } catch (Exception e) { exception.addError("parametro: Fecha
         * Fin : La fecha es inválida" + e.getMessage() ); } }
         */
        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {
            exception.addError("Parámetro: Departamento: Debe seleccionar una opción");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_01)) {
                exception.addError("Parámetro: Departamento: Debe seleccionar una opción");
            }
        }

        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {
            exception.addError("Parámetro: Municipio: Debe seleccionar una opción");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_02)) {
                exception.addError("Parámetro: Municipio: Debe seleccionar una opción");
            }
        }

        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {
            exception.addError("Parámetro: Reparto: Debe ingresar el número de reparto a consultar");
        }

        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {
            exception.addError("Parámetro: Notaria: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("Parámetro: Notaria: Debe digitar un valor");
            }
        }

        if ((null == param_05) || ("".equalsIgnoreCase(param_05))) {
            exception.addError("Parámetro: Categoria: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_05)) {
                exception.addError("Parámetro: Categoria: Debe digitar un valor");
            }
        }

        if ((null == param_06) || ("".equalsIgnoreCase(param_06))) {
            exception.addError("Parámetro: Circulo: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_06)) {
                exception.addError("Parámetro: Circulo: Debe digitar un valor");
            }
        }

        //SI HAY EXCEPCIONES SE LANZAN
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        EvnReportes result;
        result = new EvnReportes(EvnReportes.VALIDAR_NUMERO_REPARTO);
        result.setNumeroRepartoNotarial(param_03);

        return result;

    }

    private void doEnd_Reporte30_Send_Action(HttpServletRequest request) {

        //DEPARTAMENTO
        String param_01 = request.getParameter(CDepartamento.ID_DEPARTAMENTO);
        String _param_01 = "";
        if (param_01 != null) {
            for (int i = 0; i < param_01.length(); i++) {
                if (Character.isDigit(param_01.charAt(i))) {
                    _param_01 = _param_01 + param_01.charAt(i);
                }
            }
        }

        //MUNICIPIO
        String param_02 = request.getParameter(CMunicipio.ID_MUNICIPIO);
        String _param_02 = "";
        if (param_02 != null) {
            for (int i = 0; i < param_02.length(); i++) {
                if (Character.isDigit(param_02.charAt(i))) {
                    _param_02 = _param_02 + param_02.charAt(i);
                }
            }
        }

        //NÚMERO DE REPARTO
        String param_03 = request.getParameter(REPORTE_30__PARAM_PNUMREPARTO);

        //ID DE NOTARIA
        String param_04 = request.getParameter(COficinaOrigen.OFICINA_ID_OFICINA_ORIGEN);

        //CATEGORIA
        String param_05 = request.getParameter(CCategoria.ID_CATEGORIA);

        //CIRCULO
        String param_06 = request.getParameter(REPORTE_30__PARAM_PCIRCULONOMBRE);

        String[] itemIds = new String[]{
            REPORTE_30__PARAM_PDEPARTAMENTO,
            REPORTE_30__PARAM_PMUNICIPIO,
            REPORTE_30__PARAM_PNUMREPARTO,
            REPORTE_30__PARAM_PNOTARIA,
            REPORTE_30__PARAM_PCATEGORIA,
            REPORTE_30__PARAM_PCIRCULONOMBRE};
        do_ReporteXX_SaveState(itemIds, request);

        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY,
            REPORTE_30__PARAM_PDEPARTAMENTO,
            REPORTE_30__PARAM_PMUNICIPIO,
            REPORTE_30__PARAM_PNUMREPARTO,
            REPORTE_30__PARAM_PNOTARIA,
            REPORTE_30__PARAM_PCATEGORIA,
            REPORTE_30__PARAM_PCIRCULONOMBRE};

        param_01 = param_01.substring((param_01.indexOf("-") + 1), param_01.length());
        param_02 = param_02.substring((param_02.indexOf("-") + 1), param_02.length());

        String[] values = new String[]{
            REPORTE_30__PARAM_CMDKEY,
            param_01,
            param_02,
            param_03,
            param_04,
            param_05,
            param_06};

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte32_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_32__PARAM_PFECHAREP);

        String param_02 = request.getParameter(REPORTE_32__PARAM_PCIRCULONOMBRE);

        String param_03 = request.getParameter(REPORTE_32__PARAM_PREPARTONOTARIAL);

        String param_04 = "";

        String param_02isnull = "0";
        String param_03isnull = "0";

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Reparto: Debe digitar un valor");
        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Reparto : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {
            param_02isnull = "1";
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_02)) {
                param_02isnull = "1";
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {
            param_03isnull = "1";
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                param_03isnull = "1";
            }
        }

        String[] itemIds = new String[]{
            REPORTE_32__PARAM_PFECHAREP, REPORTE_32__PARAM_PCIRCULONOMBRE, REPORTE_32__PARAM_PREPARTONOTARIAL, REPORTE_32__PARAM_PFLAG
        };

        do_ReporteXX_SaveState(itemIds, request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        if (param_02isnull.equals("1")) {
            param_02 = "TODOS";
            int cont = 0;
            if (param_01 != null) {
                for (int i = 0; i < 4; i++) {
                    param_03 = param_03 + param_01.charAt(i);
                }
            }
            param_04 = "LIKE";
        } else {
            if (param_03isnull.equals("1")) {
                int cont = 0;
                if (param_01 != null) {
                    for (int i = 0; i < 4; i++) {
                        param_03 = param_03 + param_01.charAt(i);
                    }
                }
                param_03 = param_03 + "-" + param_02;
                param_04 = "LIKE";
            } else {
                param_04 = "IGUAL";
            }
        }
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_32__PARAM_PFECHAREP, REPORTE_32__PARAM_PCIRCULONOMBRE, REPORTE_32__PARAM_PREPARTONOTARIAL, REPORTE_32__PARAM_PFLAG
        };

        String[] values = new String[]{
            REPORTE_32__PARAM_CMDKEY, param_01, param_02, param_03, param_04
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte37_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_37__PARAM_PCIRCULONOMBRE);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {
            exception.addError("parametro: Circulo: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_01)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        String[] itemIds = new String[]{
            REPORTE_37__PARAM_PCIRCULONOMBRE
        };

        do_ReporteXX_SaveState(itemIds, request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_37__PARAM_PCIRCULONOMBRE
        };

        String[] values = new String[]{
            REPORTE_37__PARAM_CMDKEY, param_01
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte38_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_38__PARAM_PFECHAINI);

        String param_02 = request.getParameter(REPORTE_38__PARAM_PFECHAFIN);

        String param_03 = request.getParameter(REPORTE_38__PARAM_PCIRCULONOMBRE);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_38__PARAM_PFECHAINI, REPORTE_38__PARAM_PFECHAFIN, REPORTE_38__PARAM_PCIRCULONOMBRE
        };

        String[] values = new String[]{
            REPORTE_38__PARAM_CMDKEY, param_01, param_02, param_03
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte39_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_39__PARAM_PFECHAINI);

        String param_02 = request.getParameter(REPORTE_39__PARAM_PFECHAFIN);

        String param_03 = request.getParameter(REPORTE_39__PARAM_PCIRCULONOMBRE);

        //GCABRERA 19/12/2011
        //Se agrega parámetro REPORTE_39__PARAM_USUARIOLOG
        String param_04 = request.getParameter(REPORTE_39__PARAM_USUARIOLOG);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        String[] itemIds = new String[]{
            REPORTE_39__PARAM_PFECHAINI, REPORTE_39__PARAM_PFECHAFIN, REPORTE_39__PARAM_PCIRCULONOMBRE, REPORTE_39__PARAM_USUARIOLOG
        };

        do_ReporteXX_SaveState(itemIds, request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_39__PARAM_PFECHAINI, REPORTE_39__PARAM_PFECHAFIN, REPORTE_39__PARAM_PCIRCULONOMBRE, REPORTE_39__PARAM_USUARIOLOG
        };

        String[] values = new String[]{
            REPORTE_39__PARAM_CMDKEY, param_01, param_02, param_03, param_04
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte41_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_41__PARAM_PFECHAINI);

        String param_02 = request.getParameter(REPORTE_41__PARAM_PFECHAFIN);

        String param_03 = request.getParameter(REPORTE_41__PARAM_PCIRCULONOMBRE);

        String param_04 = request.getParameter(REPORTE_41__PARAM_PUSUARIONOMBRE);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {

            exception.addError("parametro: Calificador: Debe digitar un valor");

        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Calificador: Debe digitar un valor");
            }
        }

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_41__PARAM_PFECHAINI, REPORTE_41__PARAM_PFECHAFIN, REPORTE_41__PARAM_PCIRCULONOMBRE, REPORTE_41__PARAM_PUSUARIONOMBRE
        };

        String[] values = new String[]{
            REPORTE_41__PARAM_CMDKEY, param_01, param_02, param_03, param_04
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte42_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_42__PARAM_PFECHAINI);

        String param_02 = request.getParameter(REPORTE_42__PARAM_PFECHAFIN);

        String param_03 = request.getParameter(REPORTE_42__PARAM_PCIRCULONOMBRE);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_42__PARAM_PFECHAINI, REPORTE_42__PARAM_PFECHAFIN, REPORTE_42__PARAM_PCIRCULONOMBRE
        };

        String[] values = new String[]{
            REPORTE_42__PARAM_CMDKEY, param_01, param_02, param_03
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte43_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_43__PARAM_PFECHAINI);

        String param_02 = request.getParameter(REPORTE_43__PARAM_PFECHAFIN);

        String param_03 = request.getParameter(REPORTE_43__PARAM_PCIRCULONOMBRE);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_43__PARAM_PFECHAINI, REPORTE_43__PARAM_PFECHAFIN, REPORTE_43__PARAM_PCIRCULONOMBRE
        };

        String[] values = new String[]{
            REPORTE_43__PARAM_CMDKEY, param_01, param_02, param_03
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte44_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_44__PARAM_PTURNOFECHAINI);

        String param_03 = request.getParameter(REPORTE_44__PARAM_PCIRCULONOMBRE);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_44__PARAM_PTURNOFECHAINI, REPORTE_44__PARAM_PCIRCULONOMBRE
        };

        String[] values = new String[]{
            REPORTE_44__PARAM_CMDKEY, param_01, param_03
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte45_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_45__PARAM_PTURNOFECHAINI);

        String param_03 = request.getParameter(REPORTE_45__PARAM_PCIRCULONOMBRE);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        String[] itemIds = new String[]{
            REPORTE_45__PARAM_PTURNOFECHAINI, REPORTE_45__PARAM_PCIRCULONOMBRE
        };

        do_ReporteXX_SaveState(itemIds, request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_45__PARAM_PTURNOFECHAINI, REPORTE_45__PARAM_PCIRCULONOMBRE
        };

        String[] values = new String[]{
            REPORTE_45__PARAM_CMDKEY, param_01, param_03
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte46_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_46__PARAM_PFECHAREP);

        String param_02 = request.getParameter(REPORTE_46__PARAM_PCIRCULONOMBRE);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha: Debe digitar un valor");
        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_02)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        String[] itemIds = new String[]{
            REPORTE_46__PARAM_PFECHAREP, REPORTE_46__PARAM_PCIRCULONOMBRE
        };

        do_ReporteXX_SaveState(itemIds, request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_46__PARAM_PFECHAREP, REPORTE_46__PARAM_PCIRCULONOMBRE
        };

        String[] values = new String[]{
            REPORTE_46__PARAM_CMDKEY, param_01, param_02
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte74_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_74__PARAM_PFECHAINI);

        String param_02 = request.getParameter(REPORTE_74__PARAM_PFECHAFIN);

        String param_03 = request.getParameter(REPORTE_74__PARAM_PCIRCULONOMBRE);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        String[] itemIds = new String[]{
            REPORTE_74__PARAM_PFECHAINI, REPORTE_74__PARAM_PFECHAFIN, REPORTE_74__PARAM_PCIRCULONOMBRE
        };

        do_ReporteXX_SaveState(itemIds, request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_74__PARAM_PFECHAINI, REPORTE_74__PARAM_PFECHAFIN, REPORTE_74__PARAM_PCIRCULONOMBRE
        };

        String[] values = new String[]{
            REPORTE_74__PARAM_CMDKEY, param_01, param_02, param_03
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte87_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_87__PARAM_PFECHA);

        String param_02 = request.getParameter(REPORTE_87__PARAM_PESTADO);

        String param_03 = request.getParameter(REPORTE_87__PARAM_PNUMPAGINA);

        String param_04 = request.getParameter(REPORTE_87__PARAM_PCIRCULO);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Turno: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Turno : La fecha es inválida" + e.getMessage());
            }
        }

        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {
            exception.addError(
                    "parametro: Subproceso Correcciones: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_02)) {
                exception.addError(
                        "parametro: Subproceso Correcciones: Debe digitar un valor");
            }
        }

        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: No. de Página: Debe digitar un valor");

        } else {
            for (int i = 0; i < param_03.length(); i++) {
                if (!Character.isDigit(param_03.charAt(i))) {
                    exception.addError(
                            "parametro: No. de Página: Debe ser un valor numerico");
                    break;
                }
            }
        }

        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        String[] itemIds = new String[]{
            REPORTE_87__PARAM_PFECHA, REPORTE_87__PARAM_PESTADO, REPORTE_87__PARAM_PNUMPAGINA, REPORTE_87__PARAM_PCIRCULO
        };

        do_ReporteXX_SaveState(itemIds, request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_87__PARAM_PFECHA, REPORTE_87__PARAM_PESTADO, REPORTE_87__PARAM_PNUMPAGINA, REPORTE_87__PARAM_PCIRCULO
        };

        String[] values = new String[]{
            REPORTE_87__PARAM_CMDKEY, param_01, param_02, param_03, param_04
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    /* Reporte 88 - CERTIFICADOS ENTREGADOS EN EL DÍA
     * AHERRENO 11/04/2011
     * Se agregan variables: Fecha Final y Usuario que genera el reporte
     */
    private void do_Reporte88_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_88__PARAM_PFECHA);
        String param_02 = request.getParameter(REPORTE_88__PARAM_PCIRCULONOMBRE);
        String param_03 = request.getParameter(REPORTE_88__PARAM_PFECHAFIN);
        String param_04 = request.getParameter(REPORTE_88__PARAM_USUARIOLOG);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");
        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {
            exception.addError("parametro: Circulo: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_02)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");
        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_03);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_03 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {
            exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
            }
        }

        String[] itemIds = new String[]{
            REPORTE_88__PARAM_PFECHA, REPORTE_88__PARAM_PCIRCULONOMBRE, REPORTE_88__PARAM_PFECHAFIN, REPORTE_88__PARAM_USUARIOLOG
        };

        do_ReporteXX_SaveState(itemIds, request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_88__PARAM_PFECHA, REPORTE_88__PARAM_PCIRCULONOMBRE, REPORTE_88__PARAM_PFECHAFIN, REPORTE_88__PARAM_USUARIOLOG
        };

        String[] values = new String[]{
            REPORTE_88__PARAM_CMDKEY, param_01, param_02, param_03, param_04
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    /* Reporte 89 - LISTADO DE DOCUMENTOS ENTREGADOS EL DÍA
     * AHERRENO 11/04/2011
     * Se agregan variables: Fecha Final y Usuario que genera el reporte
     */
    private void do_Reporte89_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_89__PARAM_PFECHA);
        String param_02 = request.getParameter(REPORTE_89__PARAM_PCIRCULONOMBRE);
        String param_03 = request.getParameter(REPORTE_89__PARAM_PFECHAFIN);
        String param_04 = request.getParameter(REPORTE_89__PARAM_USUARIOLOG);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");
        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {
            exception.addError("parametro: Circulo: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_02)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");
        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_03);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_03 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {
            exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
            }
        }

        String[] itemIds = new String[]{
            REPORTE_89__PARAM_PFECHA, REPORTE_89__PARAM_PCIRCULONOMBRE, REPORTE_89__PARAM_PFECHAFIN, REPORTE_89__PARAM_USUARIOLOG
        };

        do_ReporteXX_SaveState(itemIds, request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_89__PARAM_PFECHA, REPORTE_89__PARAM_PCIRCULONOMBRE, REPORTE_89__PARAM_PFECHAFIN, REPORTE_89__PARAM_USUARIOLOG
        };

        String[] values = new String[]{
            REPORTE_89__PARAM_CMDKEY, param_01, param_02, param_03, param_04
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    /* Reporte 90 - DIARIO DESANOTADOR DE CERTIFICADOS DEL DIA
     * AHERRENO 11/04/2011
     * Se agregan variables: Fecha Final y Usuario que genera el reporte
     */
    private void do_Reporte90_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_90__PARAM_PFECHA);
        String param_02 = request.getParameter(REPORTE_90__PARAM_PCIRCULONOMBRE);
        String param_03 = request.getParameter(REPORTE_90__PARAM_PNUMPAGINA);
        String param_04 = request.getParameter(REPORTE_90__PARAM_PFECHAFIN);
        String param_05 = request.getParameter(REPORTE_90__PARAM_USUARIOLOG);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Incio: Debe digitar un valor");
        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {
            exception.addError("parametro: Circulo: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_02)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: No. de Página: Debe digitar un valor");

        } else {
            for (int i = 0; i < param_03.length(); i++) {
                if (!Character.isDigit(param_03.charAt(i))) {
                    exception.addError(
                            "parametro: No. de Página: Debe ser un valor numerico");
                    break;
                }
            }
        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");
        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_04);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_04 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_05) || ("".equalsIgnoreCase(param_05))) {
            exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_05)) {
                exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
            }
        }

        String[] itemIds = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_90__PARAM_PFECHA, REPORTE_90__PARAM_PCIRCULONOMBRE, REPORTE_90__PARAM_PNUMPAGINA, REPORTE_90__PARAM_PFECHAFIN, REPORTE_90__PARAM_USUARIOLOG
        };

        do_ReporteXX_SaveState(itemIds, request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_90__PARAM_PFECHA, REPORTE_90__PARAM_PCIRCULONOMBRE, REPORTE_90__PARAM_PNUMPAGINA, REPORTE_90__PARAM_PFECHAFIN, REPORTE_90__PARAM_USUARIOLOG
        };

        String[] values = new String[]{
            REPORTE_90__PARAM_CMDKEY, param_01, param_02, param_03, param_04, param_05
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte91_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_91__PARAM_PFECHAINI);

        String param_02 = request.getParameter(REPORTE_91__PARAM_PFECHAFIN);

        String param_03 = request.getParameter(REPORTE_91__PARAM_PCIRCULONOMBRE);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {

            SimpleDateFormat df = new SimpleDateFormat();
            df.applyPattern("dd/MM/yyyy");
            String send1 = null;

            Date fecha = null;
            try {
                fecha = df.parse(param_01);
                df.applyPattern("yyyy-MM-dd");
                send1 = df.format(fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {

            SimpleDateFormat df = new SimpleDateFormat();
            df.applyPattern("dd/MM/yyyy");
            String send1 = null;

            Date fecha = null;
            try {
                fecha = df.parse(param_02);
                df.applyPattern("yyyy-MM-dd");
                send1 = df.format(fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        String[] itemIds = new String[]{
            REPORTE_91__PARAM_PFECHAINI, REPORTE_91__PARAM_PFECHAFIN, REPORTE_91__PARAM_PCIRCULONOMBRE
        };

        do_ReporteXX_SaveState(itemIds, request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_91__PARAM_PFECHAINI, REPORTE_91__PARAM_PFECHAFIN, REPORTE_91__PARAM_PCIRCULONOMBRE
        };

        String[] values = new String[]{
            REPORTE_91__PARAM_CMDKEY, param_01, param_02, param_03
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte92_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_92__PARAM_PFECHAINI);

        String param_02 = request.getParameter(REPORTE_92__PARAM_PFECHAFIN);

        String param_03 = request.getParameter(REPORTE_92__PARAM_PCIRCULONOMBRE);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        String[] itemIds = new String[]{
            REPORTE_92__PARAM_PFECHAINI, REPORTE_92__PARAM_PFECHAFIN, REPORTE_92__PARAM_PCIRCULONOMBRE
        };

        do_ReporteXX_SaveState(itemIds, request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_92__PARAM_PFECHAINI, REPORTE_92__PARAM_PFECHAFIN, REPORTE_92__PARAM_PCIRCULONOMBRE
        };

        String[] values = new String[]{
            REPORTE_92__PARAM_CMDKEY, param_01, param_02, param_03
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte93_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(CCirculo.ID_CIRCULO);

        String param_02 = request.getParameter(CUsuario.ID_USUARIO);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {
            exception.addError("parametro: Circulo: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_01)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {
            exception.addError("parametro: Usuario: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_02)) {
                exception.addError("parametro: Usuario: Debe digitar un valor");
            }
        }

        String[] itemIds = new String[]{
            REPORTE_93__PARAM_PCIRCULONOMBRE, REPORTE_93__PARAM_PUSUARIO
        };

        do_ReporteXX_SaveState(itemIds, request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_93__PARAM_PCIRCULONOMBRE, REPORTE_93__PARAM_PUSUARIO
        };

        String[] values = new String[]{
            REPORTE_93__PARAM_CMDKEY, param_01, param_02
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        try {
            limpiaSesionReport93(request);
        } catch (AccionWebException ex) {
        }
//                do_ReporteXX_RemoveState( itemIds, request );
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
/*AHERRENO 02/03/2011
   Se agrega variable para capturar el usuario logueado*/
    private void do_Reporte94_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_94__PARAM_PFECHAINI);

        String param_02 = request.getParameter(REPORTE_94__PPARAM_PUSUARIONOMBRE);

        String param_03 = request.getParameter(REPORTE_94__PARAM_PCIRCULONOMBRE);

        String param_04 = request.getParameter(REPORTE_94__PARAM_USUARIOLOG);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Usuario: Debe digitar un valor");

        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {

            exception.addError("parametro: Usuario Logueado: No hay usuario logueado");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Usuario Logueado: No hay usuario logueado");
            }

        }

        do_Reporte94_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_94__PARAM_PFECHAINI, REPORTE_94__PPARAM_PUSUARIONOMBRE, REPORTE_94__PARAM_PCIRCULONOMBRE, REPORTE_94__PARAM_USUARIOLOG
        };

        String[] values = new String[]{
            REPORTE_94__PARAM_CMDKEY, param_01, param_02, param_03, param_04
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_Reporte94_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    }

    private void do_Reporte94_Back_Action(HttpServletRequest request) {

        do_Reporte94_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    } // :do_Reporte94_Back_Action

    private void do_Reporte94_SaveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_94__PARAM_PFECHAINI, REPORTE_94__PPARAM_PUSUARIONOMBRE, REPORTE_94__PARAM_PCIRCULONOMBRE, REPORTE_94__PARAM_USUARIOLOG
        };

        session = request.getSession();

        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte94_SaveState

    private void do_Reporte94_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_94__PARAM_PFECHAINI, REPORTE_94__PPARAM_PUSUARIONOMBRE, REPORTE_94__PARAM_PCIRCULONOMBRE, REPORTE_94__PARAM_USUARIOLOG
        };

        session = request.getSession();
        delete_PageItemsState(itemIds, request, session);

        // DELETE LISTS OF VALUES IN CACHE
        itemIds = new String[]{
            AWReportes.USUARIOS_CONSULTAR_POR_CIRCULO
        };
        delete_PageItemsState(itemIds, request, session);
    } // :do_Reporte94_RemoveState

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte95_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_95__PARAM_PFECHAINI);

        String param_02 = request.getParameter(REPORTE_95__PARAM_PFECHAFIN);

        String param_03 = request.getParameter(REPORTE_95__PARAM_PCIRCULONOMBRE);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_95__PARAM_PFECHAINI, REPORTE_95__PARAM_PFECHAFIN, REPORTE_95__PARAM_PCIRCULONOMBRE
        };

        String[] values = new String[]{
            REPORTE_95__PARAM_CMDKEY, param_01, param_02, param_03
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte109_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_109__PARAM_PFECHAINI);

        String param_02 = request.getParameter(REPORTE_109__PARAM_PFECHAFIN);

        String param_03 = request.getParameter(REPORTE_109__PARAM_PCIRCULONOMBRE);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        String[] itemIds = new String[]{
            REPORTE_109__PARAM_PFECHAINI, REPORTE_109__PARAM_PFECHAFIN, REPORTE_109__PARAM_PCIRCULONOMBRE
        };

        do_ReporteXX_SaveState(itemIds, request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_109__PARAM_PFECHAINI, REPORTE_109__PARAM_PFECHAFIN, REPORTE_109__PARAM_PCIRCULONOMBRE
        };

        String[] values = new String[]{
            REPORTE_109__PARAM_CMDKEY, param_01, param_02, param_03
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte110_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_110__PARAM_PFECHAINI);

        String param_02 = request.getParameter(REPORTE_110__PARAM_PFECHAFIN);

        String param_03 = request.getParameter(REPORTE_110__PARAM_PCIRCULONOMBRE);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        String[] itemIds = new String[]{
            REPORTE_110__PARAM_PFECHAINI, REPORTE_110__PARAM_PFECHAFIN, REPORTE_110__PARAM_PCIRCULONOMBRE
        };

        do_ReporteXX_SaveState(itemIds, request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_110__PARAM_PFECHAINI, REPORTE_110__PARAM_PFECHAFIN, REPORTE_110__PARAM_PCIRCULONOMBRE
        };

        String[] values = new String[]{
            REPORTE_110__PARAM_CMDKEY, param_01, param_02, param_03
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte121_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_121__PARAM_PFECHAINI);

        String param_02 = request.getParameter(REPORTE_121__PARAM_PFECHAFIN);

        String param_03 = request.getParameter(REPORTE_121__PARAM_PCIRCULONOMBRE);

        String param_04 = request.getParameter(REPORTE_121__PARAM_POFICINANUMERO);

        String param_05 = request.getParameter(REPORTE_121__PARAM_PIDCATEGORIA);

        String dato = request.getParameter(CDepartamento.ID_DEPARTAMENTO);

        String dato_2 = request.getParameter(CMunicipio.ID_MUNICIPIO);

        // apply validators
        if ((null == dato) || ("".equalsIgnoreCase(dato))) {
            exception.addError("parametro: Departamento: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(dato)) {
                exception.addError("parametro: Departamento: Debe digitar un valor");
            }
        }
        if ((null == dato_2) || ("".equalsIgnoreCase(dato_2))) {
            exception.addError("parametro: Municipio: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(dato_2)) {
                exception.addError("parametro: Municipio: Debe digitar un valor");
            }
        }
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {
            exception.addError("parametro: Notaría: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Notaría: Debe digitar un valor");
            }
        }
        if ((null == param_05) || ("".equalsIgnoreCase(param_05))) {
            exception.addError("parametro: Categoria: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_05)) {
                exception.addError("parametro: Categoria: Debe digitar un valor");
            }
        }
        String[] itemIds = new String[]{
            REPORTE_121__PARAM_PFECHAINI, REPORTE_121__PARAM_PFECHAFIN, REPORTE_121__PARAM_PCIRCULONOMBRE, REPORTE_121__PARAM_POFICINANUMERO, REPORTE_121__PARAM_PIDCATEGORIA
        };

        do_ReporteXX_SaveState(itemIds, request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_121__PARAM_PFECHAINI, REPORTE_121__PARAM_PFECHAFIN, REPORTE_121__PARAM_PCIRCULONOMBRE, REPORTE_121__PARAM_POFICINANUMERO, REPORTE_121__PARAM_PIDCATEGORIA
        };

        String[] values = new String[]{
            REPORTE_121__PARAM_CMDKEY, param_01, param_02, param_03, param_04, param_05
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte123_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_123__PARAM_PFECHAINI);

        String param_02 = request.getParameter(REPORTE_123__PARAM_PFECHAFIN);

        String param_03 = request.getParameter(REPORTE_123__PARAM_PCIRCULONOMBRE);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        String[] itemIds = new String[]{
            REPORTE_123__PARAM_PFECHAINI, REPORTE_123__PARAM_PFECHAFIN, REPORTE_123__PARAM_PCIRCULONOMBRE
        };

        do_ReporteXX_SaveState(itemIds, request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_123__PARAM_PFECHAINI, REPORTE_123__PARAM_PFECHAFIN, REPORTE_123__PARAM_PCIRCULONOMBRE
        };

        String[] values = new String[]{
            REPORTE_123__PARAM_CMDKEY, param_01, param_02, param_03
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte124_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_124__PARAM_PFECHAINI);

        String param_02 = request.getParameter(REPORTE_124__PARAM_PFECHAFIN);

        String param_03 = request.getParameter(REPORTE_124__PARAM_PCIRCULONOMBRE);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        String[] itemIds = new String[]{
            REPORTE_124__PARAM_PFECHAINI, REPORTE_124__PARAM_PFECHAFIN, REPORTE_124__PARAM_PCIRCULONOMBRE
        };

        do_ReporteXX_SaveState(itemIds, request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_124__PARAM_PFECHAINI, REPORTE_124__PARAM_PFECHAFIN, REPORTE_124__PARAM_PCIRCULONOMBRE
        };

        String[] values = new String[]{
            REPORTE_124__PARAM_CMDKEY, param_01, param_02, param_03
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte131_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_131__PARAM_PFECHAINI);

        String param_02 = request.getParameter(REPORTE_131__PARAM_PFECHAFIN);

        String param_03 = request.getParameter(REPORTE_131__PARAM_PCIRCULONOMBRE);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_131__PARAM_PFECHAINI, REPORTE_131__PARAM_PFECHAFIN, REPORTE_131__PARAM_PCIRCULONOMBRE
        };

        String[] values = new String[]{
            REPORTE_131__PARAM_CMDKEY, param_01, param_02, param_03
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);
    }
    // -------------------------------------------------------------------------------
    /* Reporte 182 - FORMAS DE PAGO CORREGIDAS
     * desarrollo3@tsg.net.co - DNILSON OLAYA GOMEZ   
     */
    private void do_Reporte182_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_182__PARAM_PFECHA);
        String param_02 = request.getParameter(REPORTE_182__PARAM_PCIRCULONOMBRE);
        String param_03 = request.getParameter(REPORTE_182__PARAM_PFECHAFIN);
        String param_04 = request.getParameter(REPORTE_182__PARAM_USUARIOLOG);
        
        Date fechaInicial = null;
        Date fechaFinal = null;
        
        System.out.println(">>>>> DNilson226 gov.sir.core.web.acciones.administracion.AWReportes.do_Reporte182_Send_Action() Validando Fechas fecha Inicial y fecha Final<<<<<");
        
        System.out.println(">>>>> DNilson226 Validando Fechas fecha Inicial y fecha Final <<<<<");
        
        
        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicial: Debe digitar un valor");
        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                fechaInicial = fecha;
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicial: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {
            exception.addError("parametro: Circulo Registral: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_02)) {
                exception.addError("parametro: Circulo Registral: Debe digitar un valor");
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Fecha Final: Debe digitar un valor");
        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_03);
                fechaFinal = fecha;
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_03 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Final: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {
            exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
            }
        }
        
        // DNilson Olaya - desarrollo3@tsg.net.co  
        // apply validators
        // Validacion de que la diferencia entre las fechas
        // no se encuentre en un periodo mayor a un mes (30 dias)
        
        if(fechaInicial != null && fechaFinal != null){ 
                                              
            if( fechaInicial.after(fechaFinal)){
                exception.addError("parametros: Fecha Inicial y Fecha Final: La fecha Inicial debe ser Anterior o igual a la fecha Final");
            }        
                                   
            // DNilson Olaya - desarrollo3@tsg.net.co             
            long diferencia = fechaFinal.getTime() - fechaInicial.getTime(); 
            float diasEntreFechas = (diferencia / (1000*60*60*24));

            if(diasEntreFechas > 30){
                exception.addError("parametros: Fecha Inicial y Fecha Final: La diferencia entre las Fechas debe estar en un rango de un mes");
            }
        
        }       
        
        /*
            String[] itemIds = new String[]{
                REPORTE_51B__PARAM_PFECHA, REPORTE_51B__PARAM_PCIRCULONOMBRE, REPORTE_51B__PARAM_USUARIOLOG
            };
        */
        
        String[] itemIds = new String[]{
            REPORTE_182__PARAM_PFECHA, REPORTE_182__PARAM_PCIRCULONOMBRE, REPORTE_182__PARAM_PFECHAFIN, REPORTE_182__PARAM_USUARIOLOG
        };
        
        
        /*
        String[] itemIds = new String[]{
            REPORTE_182__PARAM_PFECHAFIN, REPORTE_182__PARAM_PCIRCULONOMBRE, REPORTE_182__PARAM_USUARIOLOG
        };
        */
        

        do_ReporteXX_SaveState(itemIds, request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
         /**
         * String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_51B__PARAM_PFECHA, REPORTE_51B__PARAM_PCIRCULONOMBRE, REPORTE_51B__PARAM_USUARIOLOG
        };
		
		String[] values = new String[]{
            REPORTE_51B__PARAM_CMDKEY, param_01, param_02, param_03
        };
         */

        // -----------------------------------------------------
        
        
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_182__PARAM_PFECHA, REPORTE_182__PARAM_PCIRCULONOMBRE, REPORTE_182__PARAM_PFECHAFIN, REPORTE_182__PARAM_USUARIOLOG
        };
        
       /*
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_182__PARAM_PFECHAFIN, REPORTE_182__PARAM_PCIRCULONOMBRE, REPORTE_182__PARAM_USUARIOLOG
        };
        */
        /*
        String[] values = new String[]{
            // DNilson226 - REPORTE_182__PARAM_CMDKEY, param_01, param_02, param_03, param_04
            REPORTE_182__PARAM_CMDKEY, param_03, param_02, param_04, param_01 
        };
        */
        
        String[] values = new String[]{
            REPORTE_182__PARAM_CMDKEY, param_01, param_02, param_03, param_04 
        };
        

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }
    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte142_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_142__PARAM_PFECHAINI);

        String param_02 = request.getParameter(REPORTE_142__PARAM_PFECHAFIN);

        String param_03 = request.getParameter(REPORTE_142__PARAM_PCIRCULONOMBRE);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        String[] itemIds = new String[]{
            REPORTE_142__PARAM_PFECHAINI, REPORTE_142__PARAM_PFECHAFIN, REPORTE_142__PARAM_PCIRCULONOMBRE
        };

        do_ReporteXX_SaveState(itemIds, request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_142__PARAM_PFECHAINI, REPORTE_142__PARAM_PFECHAFIN, REPORTE_142__PARAM_PCIRCULONOMBRE
        };

        String[] values = new String[]{
            REPORTE_142__PARAM_CMDKEY, param_01, param_02, param_03
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte145_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_145__PARAM_PFECHAINI);

        String param_02 = request.getParameter(REPORTE_145__PARAM_PFECHAFIN);

        String param_03 = request.getParameter(REPORTE_145__PARAM_PCIRCULONOMBRE);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        String[] itemIds = new String[]{
            REPORTE_145__PARAM_PFECHAINI, REPORTE_145__PARAM_PFECHAFIN, REPORTE_145__PARAM_PCIRCULONOMBRE
        };

        do_ReporteXX_SaveState(itemIds, request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_145__PARAM_PFECHAINI, REPORTE_145__PARAM_PFECHAFIN, REPORTE_145__PARAM_PCIRCULONOMBRE
        };

        String[] values = new String[]{
            REPORTE_145__PARAM_CMDKEY, param_01, param_02, param_03
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    /*AHERRENO 27/08/2012
     *REQ 020_453
     *SE AGREGA PARAMETRO REPORTE_151__PARAM_USUARIOLOG PARA ENVIAR AL JASPER*/
    private void do_Reporte151_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_151__PARAM_PFECHAINI);

        String param_02 = request.getParameter(REPORTE_151__PARAM_PPAGINA);

        String param_03 = request.getParameter(REPORTE_151__PARAM_PCIRCULONOMBRE);

        String param_04 = request.getParameter(REPORTE_151__PARAM_USUARIOLOG);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        // TODO
        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: No. de Página: Debe digitar un valor");

        } else {
            for (int i = 0; i < param_02.length(); i++) {
                if (!Character.isDigit(param_02.charAt(i))) {
                    exception.addError("parametro: No. de Página: Debe ser un valor numerico");
                    break;//i = param_02.length();
                }
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {
            exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
            }
        }

        String[] itemIds = new String[]{
            REPORTE_151__PARAM_PFECHAINI, REPORTE_151__PARAM_PPAGINA, REPORTE_151__PARAM_PCIRCULONOMBRE, REPORTE_151__PARAM_USUARIOLOG
        };

        do_ReporteXX_SaveState(itemIds, request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_151__PARAM_PFECHAINI, REPORTE_151__PARAM_PPAGINA, REPORTE_151__PARAM_PCIRCULONOMBRE, REPORTE_151__PARAM_USUARIOLOG
        };

        String[] values = new String[]{
            REPORTE_151__PARAM_CMDKEY, param_01, param_02, param_03, param_04
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);
        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

//--------------------------------------------------------------------------------
//--------------------------------------------------------------------------------
    private void do_Reporte154_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_154__PARAM_PFECHAINI);

        String param_02 = request.getParameter(REPORTE_154__PARAM_PFECHAFIN);

        String param_03 = request.getParameter(REPORTE_154__PARAM_PCIRCULONOMBRE);

        String param_04 = request.getParameter(COficinaOrigen.OFICINA_ID_OFICINA_ORIGEN);

        String param_05 = request.getParameter(CDepartamento.ID_DEPARTAMENTO);
        String _param_05 = "";
        if (param_05 != null) {
            for (int i = 0; i < param_05.length(); i++) {
                if (Character.isDigit(param_05.charAt(i))) {
                    _param_05 = _param_05 + param_05.charAt(i);
                }
            }
        }
        String param_06 = request.getParameter(CMunicipio.ID_MUNICIPIO);
        String _param_06 = "";
        if (param_06 != null) {
            for (int i = 0; i < param_06.length(); i++) {
                if (Character.isDigit(param_06.charAt(i))) {
                    _param_06 = _param_06 + param_06.charAt(i);
                }
            }
        }
        String param_07 = request.getParameter(CCategoria.ID_CATEGORIA);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {

            exception.addError("parametro: Notaria: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Notaria: Debe digitar un valor");
            }

        }

        // apply validators
        if ((null == param_07) || ("".equalsIgnoreCase(param_07))) {

            exception.addError("parametro: Categoria: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_07)) {
                exception.addError("parametro: Categoria: Debe digitar un valor");
            }

        }

        String[] itemIds = new String[]{
            REPORTE_154__PARAM_PFECHAINI, REPORTE_154__PARAM_PFECHAFIN, REPORTE_154__PARAM_PCIRCULONOMBRE, REPORTE_154__PARAM_PNOTARIA, REPORTE_154__PARAM_PDEPARTAMENTO, REPORTE_154__PARAM_PMUNICIPIO, REPORTE_154__PARAM_PCATEGORIA
        };

        do_ReporteXX_SaveState(itemIds, request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_154__PARAM_PFECHAINI, REPORTE_154__PARAM_PFECHAFIN, REPORTE_154__PARAM_PCIRCULONOMBRE, REPORTE_154__PARAM_PNOTARIA, REPORTE_154__PARAM_PDEPARTAMENTO, REPORTE_154__PARAM_PMUNICIPIO, REPORTE_154__PARAM_PCATEGORIA
        };

        String[] values = new String[]{
            REPORTE_154__PARAM_CMDKEY, param_01, param_02, param_03, param_04, _param_05, _param_06, param_07
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    /**
     * @param request
     */
    private void do_Reporte155_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new ValidacionParametrosException();

        String param_01 = request.getParameter(REPORTE_155__PARAM_PROCESOREPARTO);

        String param_02 = request.getParameter(REPORTE_155__PARAM_CIRCULONOMBRE);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: No. Reparto: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_01)) {
                exception.addError("parametro: No. Reparto: Debe digitar un valor");
            }

        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_02)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        do_Reporte155_SaveState(request);

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY,
            REPORTE_155__PARAM_PROCESOREPARTO,
            REPORTE_155__PARAM_CIRCULONOMBRE
        };

        String[] values = new String[]{
            REPORTE_155__PARAM_CMDKEY,
            param_01,
            param_02
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_Reporte155_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    }

    private void do_Reporte155_Back_Action(HttpServletRequest request) {

        do_Reporte155_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    } // :do_Reporte155_Back_Action

    private void do_Reporte155_SaveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_155__PARAM_PROCESOREPARTO,
            REPORTE_155__PARAM_CIRCULONOMBRE
        };

        session = request.getSession();

        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte155_SaveState

    private void do_Reporte155_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_155__PARAM_PROCESOREPARTO,
            REPORTE_155__PARAM_CIRCULONOMBRE
        };

        session = request.getSession();

        delete_PageItemsState(itemIds, request, session);

    } // :do_Reporte155_RemoveState

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    //AHERRENO 19/09/2013
    //Reporte 156
    //SE AGREGA PARAMETRO REPORTE_156__PARAM_USUARIOLOG PARA ENVIAR AL JASPER      
    private void do_Reporte156_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_00 = request.getParameter(REPORTE_156__PARAM_PTURNOFECHAINICIO);
        String param_01 = request.getParameter(REPORTE_156__PARAM_PTURNOFECHAFIN);

        String param_02 = request.getParameter(REPORTE_156__PARAM_PCIRCULONOMBRE);

        String param_04 = request.getParameter(REPORTE_156__PARAM_USUARIOLOG);
        String send1 = null;
        // apply validators
        if ((null == param_00) || ("".equalsIgnoreCase(param_00))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {

            

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_00);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }
        
        
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {

            String send9 = null;

            Date fecha1 = null;
            try {
                fecha1 = DateFormatUtil.parse(param_01);
                send9 = DateFormatUtil.format("yyyy-MM-dd", fecha1);
                
                Date hoy = DateFormatUtil.parse(param_00);
                long diferencia = fecha1.getTime() - hoy.getTime(); 
                float diasEntreFechas = (diferencia / (1000*60*60*24));
                
                if(diasEntreFechas > 90){
                 exception.addError("parametro: Fecha Fin: El rango de fecha supera los 3 meses.");
                }else{
                    param_00=send1;
                    param_01=send9;
                }
                
                
                
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_02)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        
        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {
            exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
            }
        }

        do_Reporte156_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_156__PARAM_PTURNOFECHAINICIO,REPORTE_156__PARAM_PTURNOFECHAFIN, REPORTE_156__PARAM_PCIRCULONOMBRE, REPORTE_156__PARAM_USUARIOLOG
        };

        String[] values = new String[]{
            REPORTE_156__PARAM_CMDKEY, param_00,param_01, param_02, param_04
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);
        do_Reporte156_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte156_Back_Action(HttpServletRequest request) {

        do_Reporte156_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    } // :do_Reporte156_Back_Action

    //AHERRENO 19/09/2013
    //Reporte 156
    //SE AGREGA PARAMETRO REPORTE_156__PARAM_USUARIOLOG
    private void do_Reporte156_SaveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_156__PARAM_PTURNOFECHAINICIO,REPORTE_156__PARAM_PTURNOFECHAFIN, REPORTE_156__PARAM_PCIRCULONOMBRE, REPORTE_156__PARAM_PNUMPAGINA, REPORTE_156__PARAM_USUARIOLOG
        };

        session = request.getSession();

        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte156_SaveState

    //AHERRENO 19/09/2013
    //Reporte 156
    //SE AGREGA PARAMETRO REPORTE_156__PARAM_USUARIOLOG
    private void do_Reporte156_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_156__PARAM_PTURNOFECHAINICIO,REPORTE_156__PARAM_PTURNOFECHAFIN, REPORTE_156__PARAM_PCIRCULONOMBRE, REPORTE_156__PARAM_PNUMPAGINA, REPORTE_156__PARAM_USUARIOLOG
        };

        session = request.getSession();

        delete_PageItemsState(itemIds, request, session);

    } // :do_Reporte156_RemoveState

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    //AHERRENO 25/10/2010
    //Se agrega validación de parámetros REPORTE_G01__PARAM_IDCIRCULO y REPORTE_G01__PARAM_IDUSUARIO
    private void do_ReporteG01_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_G01__PARAM_FECHAINI);

        String param_02 = request.getParameter(REPORTE_G01__PARAM_FECHAFIN);

        String param_03 = request.getParameter(REPORTE_G01__PARAM_IDCIRCULO);

        String param_04 = request.getParameter(REPORTE_G01__PARAM_USUARIOLOG);

        Log.getInstance().debug(AWReportes.class, "parametro 1: " + param_01);
        Log.getInstance().debug(AWReportes.class, "parametro 2: " + param_02);
        Log.getInstance().debug(AWReportes.class, "parametro 3: " + param_03);
        Log.getInstance().debug(AWReportes.class, "parametro 4: " + param_04);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03)
                || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        if ((null == param_04)
                || ("".equalsIgnoreCase(param_04))) {

            exception.addError("parametro: Usuario: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Usuario: Debe digitar un valor");
            }
        }
        do_ReporteG01_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_G01__PARAM_FECHAINI, REPORTE_G01__PARAM_FECHAFIN, REPORTE_G01__PARAM_IDCIRCULO, REPORTE_G01__PARAM_USUARIOLOG
        };

        String[] values = new String[]{
            REPORTE_G01__PARAM_CMDKEY, param_01, param_02, param_03, param_04
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteG01_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_ReporteG01_Back_Action(HttpServletRequest request) {

        do_ReporteG01_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    } // :do_ReporteG01_Back_Action

    //AHERRENO 25/10/2010
    //Se agrega parámetro REPORTE_G01__PARAM_IDUSUARIO
    private void do_ReporteG01_SaveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_G01__PARAM_FECHAINI, REPORTE_G01__PARAM_FECHAFIN, REPORTE_G01__PARAM_IDCIRCULO, REPORTE_G01__PARAM_USUARIOLOG
        };

        session = request.getSession();

        save_PageItemsState(itemIds, request, session);

    } // :do_ReporteG01_SaveState

    //AHERRENO 25/10/2010
    //Se agrega parámetro REPORTE_G01__PARAM_IDUSUARIO
    private void do_ReporteG01_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_G01__PARAM_FECHAINI, REPORTE_G01__PARAM_FECHAFIN, REPORTE_G01__PARAM_IDCIRCULO, REPORTE_G01__PARAM_USUARIOLOG
        };

        session = request.getSession();

        delete_PageItemsState(itemIds, request, session);

    } // :do_ReporteG01_RemoveState

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    // AHERRENO 17/02/2011
    // Reporte G02
    // Se agrega parametro 4 - REPORTE_G02__PARAM_IDUSUARIO
    // Se agrega parametro 5 - REPORTE_G02__PARAM_USUARIOLOG
    private void do_ReporteG02_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        /**
         * @Autor: Santiago Vásquez
         * @Change: 2028.AJUSTE REPORTES 166, 167 y G02 Eliminar la opción de
         * usuario
         */
        String param_01 = request.getParameter(REPORTE_G02__PARAM_FECHAINI);
        String param_02 = request.getParameter(REPORTE_G02__PARAM_FECHAFIN);
        String param_03 = request.getParameter(REPORTE_G02__PARAM_IDCIRCULO);
        //String param_04 = request.getParameter(REPORTE_G02__PARAM_IDUSUARIO);
        String param_05 = request.getParameter(REPORTE_G02__PARAM_USUARIOLOG);

        Log.getInstance().debug(AWReportes.class, "parametro 1: " + param_01);
        Log.getInstance().debug(AWReportes.class, "parametro 2: " + param_02);
        Log.getInstance().debug(AWReportes.class, "parametro 3: " + param_03);
        //Log.getInstance().debug(AWReportes.class, "parametro 4: " + param_04);
        Log.getInstance().debug(AWReportes.class, "parametro 5: " + param_05);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio Reparto: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio Reparto: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin Reparto: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin Reparto: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe seleccionar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe seleccionar un valor");
            }
        }

        /**
         * @Autor: Santiago Vásquez
         * @Change: 2028.AJUSTE REPORTES 166, 167 y G02 Eliminar la opción de
         * usuario
         */
        // apply validators
        /*if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {

            exception.addError("parametro: Usuario: Debe seleccionar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Usuario: Debe seleccionar un valor");
            }
        }*/
        // apply validators
        if ((null == param_05) || ("".equalsIgnoreCase(param_05))) {

            exception.addError("parametro: Usuario Logueado");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_05)) {
                exception.addError("parametro: Usuario Logueado");
            }
        }
        do_ReporteG02_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        /**
         * @Autor: Santiago Vásquez
         * @Change: 2028.AJUSTE REPORTES 166, 167 y G02 Eliminar la opción de
         * usuario
         */
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_G02__PARAM_FECHAINI, REPORTE_G02__PARAM_FECHAFIN, REPORTE_G02__PARAM_IDCIRCULO, //REPORTE_G02__PARAM_IDUSUARIO, 
            REPORTE_G02__PARAM_USUARIOLOG
        };

        String[] values = new String[]{
            REPORTE_G02__PARAM_CMDKEY, param_01, param_02, param_03, //param_04, 
            param_05
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteG02_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_ReporteG02_Back_Action(HttpServletRequest request) {

        do_ReporteG02_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    } // :do_ReporteG02_Back_Action

    private void do_ReporteG02_SaveState(HttpServletRequest request) {

        HttpSession session;

        /**
         * @Autor: Santiago Vásquez
         * @Change: 2028.AJUSTE REPORTES 166, 167 y G02 Eliminar la opción de
         * usuario
         */
        String[] itemIds = new String[]{
            REPORTE_G02__PARAM_FECHAINI, REPORTE_G02__PARAM_FECHAFIN, REPORTE_G02__PARAM_IDCIRCULO, //REPORTE_G02__PARAM_IDUSUARIO, 
            REPORTE_G02__PARAM_USUARIOLOG
        };

        session = request.getSession();

        save_PageItemsState(itemIds, request, session);

    } // :do_ReporteG02_SaveState

    private void do_ReporteG02_RemoveState(HttpServletRequest request) {

        HttpSession session;

        /**
         * @Autor: Santiago Vásquez
         * @Change: 2028.AJUSTE REPORTES 166, 167 y G02 Eliminar la opción de
         * usuario
         */
        String[] itemIds = new String[]{
            REPORTE_G02__PARAM_FECHAINI, REPORTE_G02__PARAM_FECHAFIN, REPORTE_G02__PARAM_IDCIRCULO, //REPORTE_G02__PARAM_IDUSUARIO, 
            REPORTE_G02__PARAM_USUARIOLOG
        };

        session = request.getSession();
        delete_PageItemsState(itemIds, request, session);

        // DELETE LISTS OF VALUES IN CACHE
        itemIds = new String[]{
            AWReportes.USUARIOS_CONSULTAR_POR_CIRCULO
        };
        delete_PageItemsState(itemIds, request, session);
    } // :do_ReporteG02_RemoveState

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    // AHERRENO 28/10/2010
    // Reporte G03
    private void do_ReporteG03_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_G03__PARAM_IDCIRCULO);
        String param_02 = request.getParameter(REPORTE_G03__PARAM_IDUSUARIO);
        String param_03 = request.getParameter(REPORTE_G03__PARAM_USUARIOLOG);

        Log.getInstance().debug(AWReportes.class, "parametro 1: " + param_01);
        Log.getInstance().debug(AWReportes.class, "parametro 2: " + param_02);
        Log.getInstance().debug(AWReportes.class, "parametro 3: " + param_03);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {
            exception.addError("parametro: Circulo: Debe seleccionar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_01)) {
                exception.addError("parametro: Circulo: Debe seleccionar un valor");
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Usuario: Debe selccionar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_02)) {
                exception.addError("parametro: Usuario: Debe seleccionar un valor");
            }
        }

        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {
            exception.addError("parametro: Usuario Logueado: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Usuario Logueado: Debe digitar un valor");
            }
        }
        do_ReporteG03_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_G03__PARAM_IDCIRCULO, REPORTE_G03__PARAM_IDUSUARIO, REPORTE_G03__PARAM_USUARIOLOG
        };

        String[] values = new String[]{
            REPORTE_G03__PARAM_CMDKEY, param_01, param_02, param_03
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteG03_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_ReporteG03_Back_Action(HttpServletRequest request) {

        do_ReporteG03_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    } // :do_ReporteG03_Back_Action

    private void do_ReporteG03_SaveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_G03__PARAM_IDCIRCULO, REPORTE_G03__PARAM_IDUSUARIO, REPORTE_G03__PARAM_USUARIOLOG
        };

        session = request.getSession();

        save_PageItemsState(itemIds, request, session);

    } // :do_ReporteG03_SaveState

    private void do_ReporteG03_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_G03__PARAM_IDCIRCULO, REPORTE_G03__PARAM_IDUSUARIO, REPORTE_G03__PARAM_USUARIOLOG
        };

        session = request.getSession();
        session.removeAttribute(AWReportes.USUARIOS_CONSULTAR_POR_CIRCULO);
        delete_PageItemsState(itemIds, request, session);

    } // :do_ReporteG03_RemoveState

    //--------------------------------------------------------------------------------------
    // AHERRENO 28/10/2010
    // Reporte G03
    private EvnReportes consultarUsuariosPorCirculo(HttpServletRequest request, String param_pcirculonombre,
            String eventoConsulta) throws AccionWebException {
        HttpSession session = request.getSession();
        ValidacionParametrosException exception = new ValidacionParametrosException();
        String idCirculo = request.getParameter(param_pcirculonombre);

        if ((idCirculo == null) || (idCirculo.trim().length() == 0) || idCirculo.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un Circulo.");
        } else {
            session.setAttribute(param_pcirculonombre, idCirculo);
        }
        if (!exception.getErrores().isEmpty()) {
            throw exception;
        }
        session.setAttribute(CCirculo.ID_CIRCULO, idCirculo);

        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Circulo circulo = new Circulo();
        circulo.setIdCirculo(idCirculo);

        EvnReportes evento = new EvnReportes(usuario, eventoConsulta);
        evento.setCirculo(circulo);
        List roles = new ArrayList();

        if (eventoConsulta.equals(EvnReportes.USUARIOS_CONSULTAR_POR_CIRCULO_ROLES)) {
            roles.add(CRol.SIR_ROL_CONFRONTADOR);
            roles.add(CRoles.SIR_ROL_JEFE_OPERATIVO);
            roles.add(CRol.ROL_ABOGADO);
            roles.add(CRol.SIR_ROL_TESTAMENTOS);
            roles.add(CRol.SIR_ROL_MESA_CONTROL);
            roles.add(CRol.SIR_ROL_REGISTRADOR);
        } else if (eventoConsulta.equals(EvnReportes.USUARIOS_CONSULTAR_POR_CIRCULO_ROL_CALIFICADOR)) {
            roles.add(CRol.ROL_ABOGADO);
        }/**
         * @author: Edgar Lora
         * @change: Se agregó este caso para el manejo de la lista de usuarios
         * del proceso de correcciones que deben salir en el reporte E04.
         */
        else if (eventoConsulta.equals(EvnReportes.USUARIOS_CONSULTAR_POR_CIRCULO_PROCESO)) {
            roles.add(CRol.SIR_ROL_CAJERO_CORRECCIONES);
            roles.add(CRol.SIR_ROL_RESPONSABLE_CORRECCIONES);
            roles.add(CRol.SIR_ROL_MESA_CONTROL_CORRECCIONES);
            roles.add(CRol.SIR_ROL_AUXILIAR_CORRECCIONES);
            roles.add(CRol.SIR_ROL_ACTUACIONES_ADMINISTRATIVAS);
            roles.add(CRol.SIR_ROL_AUXILIAR_ACTUACIONES_ADMINISTRATIVAS);
            roles.add(CRol.SIR_ROL_CAJERO);
            roles.add(CRol.SIR_ROL_REGISTRADOR);
        }

        evento.setRoles(roles);
        evento.setRol(null);

        return evento;
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_ReporteG04_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_G04__PARAM_FECHAINI);

        String param_02 = request.getParameter(REPORTE_G04__PARAM_FECHAFIN);

        String param_03 = request.getParameter(REPORTE_G04__PARAM_IDCIRCULO);

        Log.getInstance().debug(AWReportes.class, "parametro 1: " + param_01);
        Log.getInstance().debug(AWReportes.class, "parametro 2: " + param_02);
        Log.getInstance().debug(AWReportes.class, "parametro 3: " + param_03);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        /*         // apply validators
        if(   ( null == param_03 )
        || ( "".equalsIgnoreCase( param_03 ) ) ) {

        exception.addError( "parametro: Circulo: Debe digitar un valor" );

        }
        else {

        if( WebKeys.SIN_SELECCIONAR.equals( param_03 ) ) {
        exception.addError( "parametro: Circulo: Debe digitar un valor" );
        }


        }*/
        do_ReporteG04_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_G04__PARAM_FECHAINI, REPORTE_G04__PARAM_FECHAFIN, REPORTE_G04__PARAM_IDCIRCULO
        };

        String[] values = new String[]{
            REPORTE_G04__PARAM_CMDKEY, param_01, param_02, param_03
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteG04_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_ReporteG04_Back_Action(HttpServletRequest request) {

        do_ReporteG04_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    } // :do_ReporteG04_Back_Action

    private void do_ReporteG04_SaveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_G04__PARAM_FECHAINI, REPORTE_G04__PARAM_FECHAFIN, REPORTE_G04__PARAM_IDCIRCULO
        };

        session = request.getSession();

        save_PageItemsState(itemIds, request, session);

    } // :do_ReporteG04_SaveState

    private void do_ReporteG04_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_G04__PARAM_FECHAINI, REPORTE_G04__PARAM_FECHAFIN, REPORTE_G04__PARAM_IDCIRCULO
        };

        session = request.getSession();

        delete_PageItemsState(itemIds, request, session);

    } // :do_ReporteG04_RemoveState

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_ReporteG05_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_G05__PARAM_FECHAINI);

        String param_02 = request.getParameter(REPORTE_G05__PARAM_FECHAFIN);

        String param_03 = request.getParameter(REPORTE_G05__PARAM_IDCIRCULO);

        Log.getInstance().debug(AWReportes.class, "parametro 1: " + param_01);
        Log.getInstance().debug(AWReportes.class, "parametro 2: " + param_02);
        Log.getInstance().debug(AWReportes.class, "parametro 3: " + param_03);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }
        do_ReporteG05_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_G05__PARAM_FECHAINI, REPORTE_G05__PARAM_FECHAFIN, REPORTE_G05__PARAM_IDCIRCULO
        };

        String[] values = new String[]{
            REPORTE_G05__PARAM_CMDKEY, param_01, param_02, param_03
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteG05_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_ReporteG05_Back_Action(HttpServletRequest request) {

        do_ReporteG05_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    } // :do_ReporteG05_Back_Action

    private void do_ReporteG05_SaveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_G05__PARAM_FECHAINI, REPORTE_G05__PARAM_FECHAFIN, REPORTE_G05__PARAM_IDCIRCULO
        };

        session = request.getSession();

        save_PageItemsState(itemIds, request, session);

    } // :do_ReporteG05_SaveState

    private void do_ReporteG05_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_G05__PARAM_FECHAINI, REPORTE_G05__PARAM_FECHAFIN, REPORTE_G05__PARAM_IDCIRCULO
        };

        session = request.getSession();

        delete_PageItemsState(itemIds, request, session);

    } // :do_ReporteG05_RemoveState

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_ReporteG06_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_G06__PARAM_FECHAFIN);

        String param_02 = request.getParameter(REPORTE_G06__PARAM_IDPROCESO);

        String param_03 = request.getParameter(REPORTE_G06__PARAM_IDCIRCULO);

        Log.getInstance().debug(AWReportes.class, "parametro 1: " + param_01);
        Log.getInstance().debug(AWReportes.class, "parametro 2: " + param_02);
        Log.getInstance().debug(AWReportes.class, "parametro 3: " + param_03);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Proceso: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_02)) {
                exception.addError("parametro: Proceso: Debe digitar un valor");
            }

        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        do_ReporteG06_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_G06__PARAM_FECHAFIN, REPORTE_G06__PARAM_IDPROCESO, REPORTE_G06__PARAM_IDCIRCULO
        };

        String[] values = new String[]{
            REPORTE_G06__PARAM_CMDKEY, param_01, param_02, param_03
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteG06_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_ReporteG06_Back_Action(HttpServletRequest request) {

        do_ReporteG06_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    } // :do_ReporteG06_Back_Action

    private void do_ReporteG06_SaveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_G06__PARAM_FECHAFIN, REPORTE_G06__PARAM_IDPROCESO, REPORTE_G06__PARAM_IDCIRCULO
        };

        session = request.getSession();

        save_PageItemsState(itemIds, request, session);

    } // :do_ReporteG06_SaveState

    private void do_ReporteG06_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_G06__PARAM_FECHAFIN, REPORTE_G06__PARAM_IDPROCESO, REPORTE_G06__PARAM_IDCIRCULO
        };

        session = request.getSession();

        delete_PageItemsState(itemIds, request, session);

    } // :do_ReporteG06_RemoveState

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_ReporteRUPTA01_Send_Action(HttpServletRequest request) throws ValidacionParametrosException {

        ValidacionParametrosException exception = new AdminReportes_InvalidParametersExceptionCollector();
        String param_01 = request.getParameter(REPORTE_RUPTA01__PARAM_PFECHA);
        // AHERRENO 03/05/2010
        // Declaración de los parametros para el reporte RUPTA
        // Se agrega parametro REPORTE_RUPTA01__PARAM_PFECHAFIN
        String param_02 = request.getParameter(REPORTE_RUPTA01__PARAM_PFECHAFIN);
        String param_03 = request.getParameter(REPORTE_RUPTA01__PARAM_PCIRCULONOMBRE);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {
            exception.addError("parametro: Fecha Inicial Radicación: Debe digitar un valor");
        } else {
            String send1 = null;
            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicial Radicación: La fecha es inválida" + e.getMessage());
            }
        }

        // AHERRENO 03/05/2010
        // Se valida parametro REPORTE_RUPTA01__PARAM_PFECHAFIN
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {
            exception.addError("parametro: Fecha Fin Radicación: Debe digitar un valor");
        } else {
            String send1 = null;
            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin Radicación: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {
            exception.addError("parametro: Circulo: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }
        do_ReporteRUPTA01_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            // AHERRENO 03/05/2010
            // Se agrega parametro REPORTE_RUPTA01__PARAM_PFECHAFIN
            REPORTE_XX__PARAM_CMDKEY, REPORTE_RUPTA01__PARAM_PFECHA, REPORTE_RUPTA01__PARAM_PFECHAFIN, REPORTE_RUPTA01__PARAM_PCIRCULONOMBRE
        };

        String[] values = new String[]{
            // AHERRENO 03/05/2010
            // Se agrega parametro REPORTE_RUPTA01__PARAM_PFECHAFIN  "param_02"
            REPORTE_RUPTA01__PARAM_CMDKEY, param_01, param_02, param_03
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteRUPTA01_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_ReporteRUPTA01_Back_Action(HttpServletRequest request) {

        do_ReporteRUPTA01_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    } // :do_ReporteRUPTA01_Back_Action

    private void do_ReporteRUPTA01_SaveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            // AHERRENO 03/05/2010
            // Se agrega parametro REPORTE_RUPTA01__PARAM_PFECHAFIN
            REPORTE_RUPTA01__PARAM_PFECHA, REPORTE_RUPTA01__PARAM_PFECHAFIN, REPORTE_RUPTA01__PARAM_PCIRCULONOMBRE
        };
        session = request.getSession();
        save_PageItemsState(itemIds, request, session);
    } // :do_ReporteRUPTA01_SaveState

    private void do_ReporteRUPTA01_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            // AHERRENO 03/05/2010
            // Se agrega parametro REPORTE_RUPTA01__PARAM_PFECHAFIN
            REPORTE_RUPTA01__PARAM_PFECHA, REPORTE_RUPTA01__PARAM_PFECHAFIN, REPORTE_RUPTA01__PARAM_PCIRCULONOMBRE
        };
        session = request.getSession();
        delete_PageItemsState(itemIds, request, session);
    } // :do_ReporteRUPTA01_RemoveState

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    //Reporte 157
    private void do_Reporte157_Send_Action(HttpServletRequest request) throws ValidacionParametrosException {
        ValidacionParametrosException exception = new AdminReportes_InvalidParametersExceptionCollector();
        String param_01 = request.getParameter(REPORTE_157__PARAM_PTURNOFECHA);
        String param_02 = request.getParameter(REPORTE_157__PARAM_PCIRCULONOMBRE);
        String param_03 = request.getParameter(REPORTE_157__PARAM_USUARIOLOG);
        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {
            exception.addError("parametro: Fecha Radicaci&oacute;n: Debe digitar un valor");
        } else {
            String send1 = null;
            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Radicaci&oacute;n : La fecha es inválida" + e.getMessage());
            }
        }
        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {
            exception.addError("parametro: Circulo: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_02)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {
            exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
            }
        }

        do_Reporte157_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        // -----------------------------------------------------

        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_157__PARAM_PTURNOFECHA, REPORTE_157__PARAM_PCIRCULONOMBRE, REPORTE_157__PARAM_USUARIOLOG};

        String[] values = new String[]{
            REPORTE_157__PARAM_CMDKEY, param_01, param_02, param_03};
        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);
        do_Reporte157_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte157_Back_Action(HttpServletRequest request) {
        do_Reporte157_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte157_SaveState(HttpServletRequest request) {
        HttpSession session;
        String[] itemIds = new String[]{
            REPORTE_157__PARAM_PTURNOFECHA, REPORTE_157__PARAM_PCIRCULONOMBRE, REPORTE_157__PARAM_USUARIOLOG};
        session = request.getSession();
        save_PageItemsState(itemIds, request, session);
    }

    private void do_Reporte157_RemoveState(HttpServletRequest request) {
        HttpSession session;
        String[] itemIds = new String[]{
            REPORTE_157__PARAM_PTURNOFECHA, REPORTE_157__PARAM_PCIRCULONOMBRE, REPORTE_157__PARAM_USUARIOLOG};
        session = request.getSession();
        delete_PageItemsState(itemIds, request, session);
    }

//  Reporte 158
    private void do_Reporte158_Send_Action(HttpServletRequest request) throws ValidacionParametrosException {
        ValidacionParametrosException exception = new AdminReportes_InvalidParametersExceptionCollector();
        String param_01 = request.getParameter(REPORTE_158__PARAM_PTURNOFECHA_INI);
        String param_02 = request.getParameter(REPORTE_158__PARAM_PTURNOFECHA_FIN);
        String param_03 = request.getParameter(REPORTE_157__PARAM_PCIRCULONOMBRE);
        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {
            exception.addError("parametro: Fecha Inicial");
        } else {
            String send1 = null;
            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Radicaci&oacute;n : La fecha es inválida" + e.getMessage());
            }
        }
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {
            exception.addError("parametro: Fecha Fin");
        } else {
            String send1 = null;
            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Radicaci&oacute;n : La fecha es inválida" + e.getMessage());
            }
        }
        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {
            exception.addError("parametro: Circulo: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }
        do_Reporte158_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        // -----------------------------------------------------

        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_158__PARAM_PTURNOFECHA_INI, REPORTE_158__PARAM_PTURNOFECHA_FIN, REPORTE_158__PARAM_PCIRCULONOMBRE};

        String[] values = new String[]{
            REPORTE_158__PARAM_CMDKEY, param_01, param_02, param_03};
        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);
        do_Reporte158_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte158_Back_Action(HttpServletRequest request) {
        do_Reporte158_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte158_SaveState(HttpServletRequest request) {
        HttpSession session;
        String[] itemIds = new String[]{
            REPORTE_158__PARAM_PTURNOFECHA_INI, REPORTE_158__PARAM_PTURNOFECHA_FIN, REPORTE_158__PARAM_PCIRCULONOMBRE};
        session = request.getSession();
        save_PageItemsState(itemIds, request, session);
    }

    private void do_Reporte158_RemoveState(HttpServletRequest request) {
        HttpSession session;
        String[] itemIds = new String[]{
            REPORTE_158__PARAM_PTURNOFECHA_INI, REPORTE_158__PARAM_PTURNOFECHA_FIN, REPORTE_158__PARAM_PCIRCULONOMBRE};
        session = request.getSession();
        delete_PageItemsState(itemIds, request, session);
    }
//   Reporte 159

    private void do_Reporte159_Send_Action(HttpServletRequest request) throws ValidacionParametrosException {
        ValidacionParametrosException exception = new AdminReportes_InvalidParametersExceptionCollector();
        String param_01 = request.getParameter(REPORTE_159__PARAM_PTURNOFECHA_INI);
        String param_02 = request.getParameter(REPORTE_159__PARAM_PTURNOFECHA_FIN);
        String param_03 = request.getParameter(REPORTE_159__PARAM_PCIRCULONOMBRE);
        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {
            exception.addError("parametro: Fecha Radicaci&oacute;n: Debe digitar un valor");
        } else {
            String send1 = null;
            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Radicaci&oacute;n : La fecha es inválida" + e.getMessage());
            }
        }
        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {
            exception.addError("parametro: Fecha Radicaci&oacute;n: Debe digitar un valor");
        } else {
            String send1 = null;
            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Radicaci&oacute;n : La fecha es inválida" + e.getMessage());
            }
        }
        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {
            exception.addError("parametro: Circulo: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }
        do_Reporte159_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        // -----------------------------------------------------

        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_159__PARAM_PTURNOFECHA_INI, REPORTE_159__PARAM_PTURNOFECHA_FIN, REPORTE_159__PARAM_PCIRCULONOMBRE};

        String[] values = new String[]{
            REPORTE_159__PARAM_CMDKEY, param_01, param_02, param_03};
        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);
        do_Reporte159_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte159_Back_Action(HttpServletRequest request) {
        do_Reporte159_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte159_SaveState(HttpServletRequest request) {
        HttpSession session;
        String[] itemIds = new String[]{
            REPORTE_159__PARAM_PTURNOFECHA_INI, REPORTE_159__PARAM_PTURNOFECHA_FIN, REPORTE_159__PARAM_PCIRCULONOMBRE};
        session = request.getSession();
        save_PageItemsState(itemIds, request, session);
    }

    private void do_Reporte159_RemoveState(HttpServletRequest request) {
        HttpSession session;
        String[] itemIds = new String[]{
            REPORTE_159__PARAM_PTURNOFECHA_INI, REPORTE_159__PARAM_PTURNOFECHA_FIN, REPORTE_159__PARAM_PCIRCULONOMBRE};
        session = request.getSession();
        delete_PageItemsState(itemIds, request, session);
    }

    private void do_Reporte160_Send_Action(HttpServletRequest request) throws ValidacionParametrosException {
        ValidacionParametrosException exception = new ValidacionParametrosException();
        String param_01 = request.getParameter(REPORTE_160__PARAM_PTURNOFECHA_INI);
        String param_02 = request.getParameter(REPORTE_160__PARAM_PTURNOFECHA_FIN);
        String param_03 = request.getParameter(REPORTE_160__PARAM_PCIRCULONOMBRE);
        String param_04 = request.getParameter(REPORTE_160__PARAM_PUSUARIO_CALIFICADOR);
        //Validaciones
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {
            exception.addError("parametro: Fecha Inicial: Debe digitar un valor");
        } else {
            String send1 = null;
            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha: La fecha inicial es inválida" + e.getMessage());
            }
        }

        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {
            exception.addError("parametro: Fecha Final: Debe digitar un valor");
        } else {
            String send1 = null;
            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha: La fecha inicial es inválida" + e.getMessage());
            }
        }

        if ((null == param_03) || ("".equalsIgnoreCase(param_03)) || WebKeys.SIN_SELECCIONAR.equals(param_03)) {
            exception.addError("parametro: Circulo: Debe digitar un valor");
        }

        if ((null == param_04) || ("".equalsIgnoreCase(param_04) || (WebKeys.SIN_SELECCIONAR.equals(param_04)))) {
            exception.addError("parametro: Usuario: Debe seleccionar un valor");
        }

        do_Reporte160_SaveState(request);

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY,
            REPORTE_160__PARAM_PTURNOFECHA_INI,
            REPORTE_160__PARAM_PTURNOFECHA_FIN,
            REPORTE_160__PARAM_PCIRCULONOMBRE,
            REPORTE_160__PARAM_PUSUARIO_CALIFICADOR
        };

        String[] values = new String[]{
            REPORTE_160__PARAM_CMDKEY,
            param_01,
            param_02,
            param_03,
            param_04
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);
        do_Reporte160_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte160_SaveState(HttpServletRequest request) {
        HttpSession session;
        String[] itemIds = new String[]{
            REPORTE_160__PARAM_PTURNOFECHA_INI,
            REPORTE_160__PARAM_PTURNOFECHA_FIN,
            REPORTE_160__PARAM_PCIRCULONOMBRE,
            REPORTE_160__PARAM_PUSUARIO_CALIFICADOR
        };
        session = request.getSession();
        save_PageItemsState(itemIds, request, session);
    }

    private void do_Reporte160_RemoveState(HttpServletRequest request) {
        HttpSession session;
        String[] itemIds = new String[]{
            REPORTE_160__PARAM_PTURNOFECHA_INI,
            REPORTE_160__PARAM_PTURNOFECHA_FIN,
            REPORTE_160__PARAM_PCIRCULONOMBRE,
            REPORTE_160__PARAM_PUSUARIO_CALIFICADOR
        };
        session = request.getSession();
        session.removeAttribute(AWReportes.USUARIOS_CONSULTAR_POR_CIRCULO);
        session.removeAttribute("consultaUsuariosCajeros");
        delete_PageItemsState(itemIds, request, session);
    }

    private void do_Reporte160_Back_Action(HttpServletRequest request) {
        do_Reporte160_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte_Rel02_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(CCirculo.ID_CIRCULO);

        String param_02 = request.getParameter(CUsuario.ID_USUARIO);

        String param_03 = request.getParameter(REPORTE_REL02__PARAM_PAGOFECHAINI);

        String param_04 = request.getParameter(REPORTE_REL02__PARAM_PAGOFECHAFIN);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {
            exception.addError("parametro: Circulo: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_01)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {
            exception.addError("parametro: Usuario: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_02)) {
                exception.addError("parametro: Usuario: Debe digitar un valor");
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Fecha Ini: Debe digitar un valor");

        } else {

            String send1 = null;
            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_03);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_03 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Ini: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {

            String send1 = null;
            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_04);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_04 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin: La fecha es inválida" + e.getMessage());
            }
        }

        String[] itemIds = new String[]{
            REPORTE_REL02__PARAM_CIRCULONOMBRE,
            REPORTE_REL02__PARAM_USUARIONOMBRE,
            REPORTE_REL02__PARAM_PAGOFECHAINI,
            REPORTE_REL02__PARAM_PAGOFECHAFIN
        };

        do_ReporteXX_SaveState(itemIds, request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY,
            REPORTE_REL02__PARAM_CIRCULONOMBRE,
            REPORTE_REL02__PARAM_USUARIONOMBRE,
            REPORTE_REL02__PARAM_PAGOFECHAINI,
            REPORTE_REL02__PARAM_PAGOFECHAFIN,};

        String[] values = new String[]{
            REPORTE_REL02__PARAM_CMDKEY, param_01, param_02, param_03, param_04
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        String[] itemIdsF = new String[]{
            REPORTE_REL02__PARAM_PAGOFECHAINI, REPORTE_REL02__PARAM_PAGOFECHAFIN
        };

        do_ReporteXX_Back_Action(itemIdsF, request);

        try {
            limpiaSesionReportRel02(request);
        } catch (AccionWebException ex) {
        }

        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    }

    /**
     * Este método se encarga de limpiar la sesión luego que el usuario ha
     * terminado de usar el reporte de relacion 02
     */
    private EvnReportes limpiaSesionReportRel02(HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();
        session.removeAttribute(AWReportes.USUARIOS_CONSULTAR_POR_CIRCULO);
        session.removeAttribute(CCirculo.ID_CIRCULO);
        session.removeAttribute(CUsuario.ID_USUARIO);
        return null;
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    public static Date doValidate_ValidateDate(String format, String text, String fieldName)
            throws Exception {

        return doValidate_ValidateDateGeneric(format, text, fieldName);
    } // :doValidate_ValidateDate

    // valida una fecha usando simpledateformat, y devuelve una excepcion si existe error en digitacion de
    // parametros; devuelve el date del parse si no hubo error alguno
    public static Date doValidate_ValidateDateGeneric(String format, String text, String fieldId)
            throws Exception {

        String errorMsg = "";
        String errorMsgBase = fieldId;
        String localValue = text;
        if ((null == localValue) || ("".equalsIgnoreCase(localValue))) {
            errorMsg = "";
            errorMsg += errorMsgBase;
            errorMsg += " Debe digitar un valor";

            throw new Exception(errorMsg);

        } else {

            SimpleDateFormat df = new SimpleDateFormat();
            df.applyPattern(format);
            df.setLenient(false);

            ParsePosition pos = new ParsePosition(0);

            String send1 = null;

            Date fecha = null;
            try {
                fecha = df.parse(localValue);//, pos );
            } catch (ParseException e) {

                errorMsg = "";
                errorMsg += errorMsgBase;
                errorMsg += " fecha inválida ";
                errorMsg += "[";
                errorMsg += "formato: " + format + ";";
                errorMsg += "actual: " + localValue + "";
                errorMsg += "]";
                errorMsg += ": ";
                // errorMsg += "( columna " + pos.getIndex() + ") :";
                // errorMsg += e.getMessage();

                throw new Exception(errorMsg);
            }
            return fecha;
        }
    } // :doValidate_ValidateDateGeneric

    public static String Nvl(Object obj, String replace) {
        if (null == obj) {
            return replace;
        }
        return (String) obj;
    }

    // formato de recepcion de fecha
    // en la forma de parametros
    // tipo 1
    public static String get_DateTx_Format1() {

        final String FORMAT = "dd/MM/yyyy hh mm a";
        return FORMAT;

    }

    public static String get_DateTx_Format2() {

        final String FORMAT = "yyyy-MM-dd HH:mm";
        return FORMAT;

    }

    // construye una cadena de fecha dados unos parametros
    // en el formato dado por get_DateTx_Format1
    public static String build_DateTx_Format1(String date, String hh, String mi, String am) {
        String local_CompleteDate;

        local_CompleteDate = "";
        local_CompleteDate += Nvl(date, "??/??/????");
        local_CompleteDate += " ";
        local_CompleteDate += Nvl(hh, "??");
        local_CompleteDate += " ";
        local_CompleteDate += Nvl(mi, "??");
        local_CompleteDate += " ";
        local_CompleteDate += Nvl(am, "??");

        return local_CompleteDate;
    }

    // dada una fecha la transforma en una cadena con el formato indicado
    public static String build_DateTx_ByFormat(Date date, String format) {
        return DateFormatUtil.format(format, date);
    }

    // exception collector para doValidate_ValidateDate
    public static Date doValidate_ValidateDateGeneric_CollectExceptions(String format, String text, String fieldName, ValidacionParametrosException exception) {
        Date result = null;
        try {
            result = doValidate_ValidateDateGeneric(format, text, fieldName);
        } catch (Exception e) {
            exception.addError(e.getMessage());
        }
        return result;
    } // :doValidate_ValidateDateGeneric_CollectExceptions
    // -------------------------------------------------------------------------------

    private void save_PageItemState_Simple(String itemId, HttpServletRequest request, HttpSession session) {

        Object itemValue;
        itemValue = request.getParameter(itemId);
        session.setAttribute(itemId, itemValue);

    } // save_PageItemState

    private void delete_PageItemState_Simple(String itemId, HttpServletRequest request, HttpSession session) {

        session.removeAttribute(itemId);

    } // save_PageItemState

    private void save_PageItemsState(String[] itemIds, HttpServletRequest request, HttpSession session) {
        if (null == itemIds) {
            return;
        }

        for (int i = 0; i < itemIds.length; i++) {

            if (null == itemIds[i]) {
                continue;
            }
            if ("".equals(itemIds[i])) {
                continue;
            }

            save_PageItemState_Simple(itemIds[i], request, session);
        } // :for
    } // :save_PageItemsState

    private void delete_PageItemsState(String[] itemIds, HttpServletRequest request, HttpSession session) {
        if (null == itemIds) {
            return;
        }

        for (int i = 0; i < itemIds.length; i++) {

            if (null == itemIds[i]) {
                continue;
            }
            if ("".equals(itemIds[i])) {
                continue;
            }

            delete_PageItemState_Simple(itemIds[i], request, session);
        } // :for
    } // :save_PageItemsState

    private void do_Reporte48_SaveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_48__PARAM_LOCAL_FECHAINI_YYMMDD, REPORTE_48__PARAM_LOCAL_FECHAINI_HH, REPORTE_48__PARAM_LOCAL_FECHAINI_MI, REPORTE_48__PARAM_LOCAL_FECHAINI_AM, REPORTE_48__PARAM_LOCAL_FECHAFIN_YYMMDD, REPORTE_48__PARAM_LOCAL_FECHAFIN_HH, REPORTE_48__PARAM_LOCAL_FECHAFIN_MI, REPORTE_48__PARAM_LOCAL_FECHAFIN_AM, REPORTE_48__PARAM_LOCAL_CIRCULO_ID
        };

        session = request.getSession();

        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte48_SaveState

    private void do_ReporteRel01_SaveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_REL01__PARAM_RELACIONID, REPORTE_REL01__PARAM_FASESELECTED
        };

        session = request.getSession();

        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte48_SaveState

    private void do_ReporteRel01_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_REL01__PARAM_RELACIONID, REPORTE_REL01__PARAM_FASESELECTED
        };

        session = request.getSession();

        delete_PageItemsState(itemIds, request, session);

    } // :do_Reporte48_RemoveState

    private void do_Reporte48_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_48__PARAM_LOCAL_FECHAINI_YYMMDD, REPORTE_48__PARAM_LOCAL_FECHAINI_HH, REPORTE_48__PARAM_LOCAL_FECHAINI_MI, REPORTE_48__PARAM_LOCAL_FECHAINI_AM, REPORTE_48__PARAM_LOCAL_FECHAFIN_YYMMDD, REPORTE_48__PARAM_LOCAL_FECHAFIN_HH, REPORTE_48__PARAM_LOCAL_FECHAFIN_MI, REPORTE_48__PARAM_LOCAL_FECHAFIN_AM, REPORTE_48__PARAM_LOCAL_CIRCULO_ID
        };

        session = request.getSession();

        delete_PageItemsState(itemIds, request, session);

    } // :do_Reporte48_RemoveState

    // TODO: modificar el conjunto de formas para que
    // guarden el estado tal y como lo hace AWReportes.do_Reporte48_Send_Action
    private void do_Reporte48_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        // param_01 -------------------------------------------------
        String local_param01_yyyymmdd = request.getParameter(REPORTE_48__PARAM_LOCAL_FECHAINI_YYMMDD);
        String local_param01_hh = request.getParameter(REPORTE_48__PARAM_LOCAL_FECHAINI_HH);
        String local_param01_mi = request.getParameter(REPORTE_48__PARAM_LOCAL_FECHAINI_MI);
        String local_param01_am = request.getParameter(REPORTE_48__PARAM_LOCAL_FECHAINI_AM);

        Date local_value01; // param_01 (date)
        String local_param01; // param_01 (raw)

        local_value01 = null;
        local_param01 = build_DateTx_Format1(local_param01_yyyymmdd,
                local_param01_hh,
                local_param01_mi,
                local_param01_am);
        // validate
        local_value01 = doValidate_ValidateDateGeneric_CollectExceptions(
                get_DateTx_Format1(), local_param01, "fecha inicio", exception);

        // -------------------------------------------------------------
        // param_02 ----------------------------------------------------
        String local_param02_yyyymmdd = request.getParameter(REPORTE_48__PARAM_LOCAL_FECHAFIN_YYMMDD);
        String local_param02_hh = request.getParameter(REPORTE_48__PARAM_LOCAL_FECHAFIN_HH);
        String local_param02_mi = request.getParameter(REPORTE_48__PARAM_LOCAL_FECHAFIN_MI);
        String local_param02_am = request.getParameter(REPORTE_48__PARAM_LOCAL_FECHAFIN_AM);

        Date local_value02; // param_01 (date)
        String local_param02; // param_01 (raw)

        local_value02 = null;
        local_param02 = build_DateTx_Format1(local_param02_yyyymmdd,
                local_param02_hh,
                local_param02_mi,
                local_param02_am);
        // validate
        local_value02 = doValidate_ValidateDateGeneric_CollectExceptions(
                get_DateTx_Format1(), local_param02, "fecha fin", exception);

        // -------------------------------------------------------------
        // param 03 ----------------------------------------------------
        String local_param03 = request.getParameter(REPORTE_48__PARAM_LOCAL_CIRCULO_ID);

        // apply validators
        if ((null == local_param03) || ("".equalsIgnoreCase(local_param03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(local_param03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            } // :if

        } // :if

        // savestate ---------------------------------------------------
        do_Reporte48_SaveState(request);
        // -------------------------------------------------------------

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -------------------------------------------------------------
        // transformar parametros para pasar a la forma --------
        String param_01;
        String param_02;
        String param_03;

        // bind
        param_01 = build_DateTx_ByFormat(local_value01, get_DateTx_Format2());
        param_02 = build_DateTx_ByFormat(local_value02, get_DateTx_Format2());
        param_03 = local_param03;

        // -----------------------------------------------------
        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_48__PARAM_PFECHAINI, REPORTE_48__PARAM_PFECHAFIN, REPORTE_48__PARAM_PCIRCULONOMBRE
        };

        String[] values = new String[]{
            REPORTE_48__PARAM_CMDKEY, param_01, param_02, param_03
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        // removestate ---------------------------------------------------
        do_Reporte48_RemoveState(request);
        // -------------------------------------------------------------

    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte49_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_49__PARAM_PFECHAINI);

        String param_02 = request.getParameter(REPORTE_49__PARAM_PFECHAFIN);

        String param_03 = request.getParameter(REPORTE_49__PARAM_PCIRCULONOMBRE);

        String param_04 = request.getParameter(REPORTE_49__PARAM_USUARIOLOG);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {

            exception.addError("parametro: Usuario Logueado: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Usuario Logueado: Debe digitar un valor");
            }

        }
        String[] itemIds = new String[]{
            REPORTE_49__PARAM_PFECHAINI, REPORTE_49__PARAM_PFECHAFIN, REPORTE_49__PARAM_PCIRCULONOMBRE, REPORTE_49__PARAM_USUARIOLOG
        };

        do_ReporteXX_SaveState(itemIds, request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_49__PARAM_PFECHAINI, REPORTE_49__PARAM_PFECHAFIN, REPORTE_49__PARAM_PCIRCULONOMBRE, REPORTE_49__PARAM_USUARIOLOG
        };

        String[] values = new String[]{
            REPORTE_49__PARAM_CMDKEY, param_01, param_02, param_03, param_04
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    // -------------------------------------------------------------------------------
    /* AHERRENO 25/04/2011 SE AGREGA CAMPO REPORTE_51__PARAM_USUARIOLOG
     */
    private void do_Reporte51_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();
        Date fechaLimite = null;
        Date fechaIngresada = null;
        String param_01 = request.getParameter(REPORTE_51__PARAM_PFECHA);

        String fechaCorte = "10/05/2019";
        
        System.out.print("La fecha sobre el reporte 51 es: "+ param_01);
        
        try {
            fechaLimite = new SimpleDateFormat("dd/MM/yyyy").parse(fechaCorte);       
            fechaIngresada = new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter(REPORTE_51__PARAM_PFECHA));  
        } catch (Exception e) {
            exception.addError("No se pudo convertir a Date la fecha " + e.getMessage());
        }

        if(fechaIngresada.equals(fechaLimite) || fechaIngresada.after(fechaLimite)){
            exception.addError("Fecha: para fechas iguales o superiores al 10/05/2019 utilizar el Reporte 51b");
        }

        String param_02 = request.getParameter(REPORTE_51__PARAM_PCIRCULONOMBRE);

        String param_03 = request.getParameter(REPORTE_51__PARAM_USUARIOLOG);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Desanotacion : La fecha es inválida" + e.getMessage());
            }

        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_02)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Usuario Logueado: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Usuario Logueado: Debe digitar un valor");
            }
        }

        String[] itemIds = new String[]{
            REPORTE_51__PARAM_PFECHA, REPORTE_51__PARAM_PCIRCULONOMBRE, REPORTE_51__PARAM_USUARIOLOG
        };

        do_ReporteXX_SaveState(itemIds, request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_51__PARAM_PFECHA, REPORTE_51__PARAM_PCIRCULONOMBRE, REPORTE_51__PARAM_USUARIOLOG
        };

        String[] values = new String[]{
            REPORTE_51__PARAM_CMDKEY, param_01, param_02, param_03
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte51b_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {
        
        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();
        
        Date fechaLimite = null;
        Date fechaIngresada = null;

        String param_01 = request.getParameter(REPORTE_51B__PARAM_PFECHA);
        
        String fechaCorte = "25/01/2019";
        
        System.out.print("La fecha sobre el reporte 51b es: "+ param_01);
        
//        try {
//            fechaLimite = new SimpleDateFormat("dd/MM/yyyy").parse(fechaCorte);       
//            fechaIngresada = new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter(REPORTE_51__PARAM_PFECHA));  
//        } catch (Exception e) {
//            exception.addError("No se pudo convertir a Date la fecha " + e.getMessage());
//        }
//        
//        if(fechaIngresada.before(fechaLimite)){
//            exception.addError("Fecha: para fechas anteriores al 25/01/2019 utilizar el Reporte 51");
//        }

        String param_02 = request.getParameter(REPORTE_51B__PARAM_PCIRCULONOMBRE);

        String param_03 = request.getParameter(REPORTE_51B__PARAM_USUARIOLOG);
        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Desanotacion : La fecha es inválida" + e.getMessage());
            }

        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_02)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Usuario Logueado: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Usuario Logueado: Debe digitar un valor");
            }
        }

        String[] itemIds = new String[]{
            REPORTE_51B__PARAM_PFECHA, REPORTE_51B__PARAM_PCIRCULONOMBRE, REPORTE_51B__PARAM_USUARIOLOG
        };

        do_ReporteXX_SaveState(itemIds, request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_51B__PARAM_PFECHA, REPORTE_51B__PARAM_PCIRCULONOMBRE, REPORTE_51B__PARAM_USUARIOLOG
        };
        String REPORTE_51B__PARAM_CMDKEY_IF = "";
        System.out.print("Ingreso al metodo param_02:"+param_02);
//        if("999".equals(param_02)){
//            System.out.print("Ingreso al if");
//            REPORTE_51__PARAM_CMDKEY_IF = "reporte-51-prueba.jasper";
//        }else{
//            System.out.print("Ingreso al else");
//            REPORTE_51__PARAM_CMDKEY_IF = REPORTE_51__PARAM_CMDKEY;
//        }                       
        
//        String[] values = new String[]{
//            REPORTE_51__PARAM_CMDKEY, param_01, param_02, param_03
//        };
        System.out.print("Valor del:"+REPORTE_51B__PARAM_CMDKEY_IF);
        String[] values = new String[]{
            REPORTE_51B__PARAM_CMDKEY, param_01, param_02, param_03
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte69_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_69__PARAM_PFECHA);

        String param_02 = request.getParameter(REPORTE_69__PARAM_PCIRCULONOMBRE);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_02)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        String[] itemIds = new String[]{
            REPORTE_69__PARAM_PFECHA, REPORTE_69__PARAM_PCIRCULONOMBRE
        };

        do_ReporteXX_SaveState(itemIds, request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_69__PARAM_PFECHA, REPORTE_69__PARAM_PCIRCULONOMBRE
        };

        String[] values = new String[]{
            REPORTE_69__PARAM_CMDKEY, param_01, param_02
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    //AHERRENO 16/11/2010
    //Reporte 79 - Se agrega parametro P_USUARIO_LOG
    private void do_Reporte79_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_79__PARAM_PFECHAINI);

        String param_02 = request.getParameter(REPORTE_79__PARAM_PFECHAFIN);

        String param_03 = request.getParameter(REPORTE_79__PARAM_PCIRCULONOMBRE);

        String param_04 = request.getParameter(REPORTE_79__PARAM_USUARIOLOG);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {

            exception.addError("parametro: Usuario Logueado: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Usuario Logueado: Debe digitar un valor");
            }
        }

        String[] itemIds = new String[]{
            REPORTE_79__PARAM_PFECHAINI, REPORTE_79__PARAM_PFECHAFIN, REPORTE_79__PARAM_PCIRCULONOMBRE, REPORTE_79__PARAM_USUARIOLOG
        };

        do_ReporteXX_SaveState(itemIds, request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_79__PARAM_PFECHAINI, REPORTE_79__PARAM_PFECHAFIN, REPORTE_79__PARAM_PCIRCULONOMBRE, REPORTE_79__PARAM_USUARIOLOG
        };

        String[] values = new String[]{
            REPORTE_79__PARAM_CMDKEY, param_01, param_02, param_03, param_04
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte81_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_81__PARAM_PFECHAINI);

        String param_02 = request.getParameter(REPORTE_81__PARAM_PFECHAFIN);

        String param_03 = request.getParameter(REPORTE_81__PARAM_PCIRCULONOMBRE);

        String param_04 = request.getParameter(CProceso.PROCESO_ID);

        String param_05 = request.getParameter(CFaseProceso.FASE_PROCESO_ID);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {
            exception.addError("parametro: Circulo: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {
            exception.addError("parametro: Proceso: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Proceso: Debe digitar un valor");
            }
        }

        // apply validators
        if ((null == param_05) || ("".equalsIgnoreCase(param_05))) {
            exception.addError("parametro: Fase: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_05)) {
                exception.addError("parametro: Fase: Debe digitar un valor");
            }
        }

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_81__PARAM_PFECHAINI, REPORTE_81__PARAM_PFECHAFIN, REPORTE_81__PARAM_PCIRCULONOMBRE, REPORTE_81__PARAM_PPROCESO, REPORTE_81__PARAM_PFASE
        };

        String[] values = new String[]{
            REPORTE_81__PARAM_CMDKEY, param_01, param_02, param_03, param_04, param_05
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte96_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_96__PARAM_PFECHAINI);

        String param_02 = request.getParameter(REPORTE_96__PARAM_PFECHAFIN);

        String param_03 = request.getParameter(REPORTE_96__PARAM_PCIRCULONOMBRE);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_96__PARAM_PFECHAINI, REPORTE_96__PARAM_PFECHAFIN, REPORTE_96__PARAM_PCIRCULONOMBRE
        };

        String[] values = new String[]{
            REPORTE_96__PARAM_CMDKEY, param_01, param_02, param_03
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte97_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_97__PARAM_PFECHAINI);

        String param_02 = request.getParameter(REPORTE_97__PARAM_PFECHAFIN);

        String param_03 = request.getParameter(REPORTE_97__PARAM_PCIRCULONOMBRE);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_97__PARAM_PFECHAINI, REPORTE_97__PARAM_PFECHAFIN, REPORTE_97__PARAM_PCIRCULONOMBRE
        };

        String[] values = new String[]{
            REPORTE_97__PARAM_CMDKEY, param_01, param_02, param_03
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    private void do_Reporte99_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_99__PARAM_PFECHAINI);

        String param_02 = request.getParameter(REPORTE_99__PARAM_PFECHAFIN);

        String param_03 = request.getParameter(REPORTE_99__PARAM_PCIRCULONOMBRE);

        String param_04 = request.getParameter(REPORTE_99__PARAM_USUARIOLOG);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {

            exception.addError("parametro: Usuario Logueado: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Usuario Logueado: Debe digitar un valor");
            }

        }

        String[] itemIds = new String[]{
            REPORTE_99__PARAM_PFECHAINI, REPORTE_99__PARAM_PFECHAFIN, REPORTE_99__PARAM_PCIRCULONOMBRE, REPORTE_99__PARAM_USUARIOLOG
        };

        do_ReporteXX_SaveState(itemIds, request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_99__PARAM_PFECHAINI, REPORTE_99__PARAM_PFECHAFIN, REPORTE_99__PARAM_PCIRCULONOMBRE, REPORTE_99__PARAM_USUARIOLOG
        };

        String[] values = new String[]{
            REPORTE_99__PARAM_CMDKEY, param_01, param_02, param_03, param_04
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    /**
     * @param request Reporte 25 - INFORME RECAUDOS DEL DIA POR USUARIO AHERRENO
     * 11/04/2011 Se agrega Variable REPORTE_25__PARAM_USUARIOLOG
     */
    private void do_Reporte25_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new ValidacionParametrosException();

        String param_01 = request.getParameter(REPORTE_25__PARAM_PAGOFECHA);
        String param_02 = request.getParameter(REPORTE_25__PARAM_CIRCULONOMBRE);
        String param_03 = request.getParameter(REPORTE_25__PARAM_USUARIONOMBRE);
        String param_04 = request.getParameter(REPORTE_25__PARAM_USUARIOLOG);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_02)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03) || (WebKeys.SIN_SELECCIONAR.equals(param_03)))) {
            exception.addError("parametro: Usuario: Debe seleccionar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Usuario: Debe digitar un valor");
            }
        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {
            exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
            }
        }

        do_Reporte25_SaveState(request);

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY,
            REPORTE_25__PARAM_PAGOFECHA,
            REPORTE_25__PARAM_CIRCULONOMBRE,
            REPORTE_25__PARAM_USUARIONOMBRE,
            REPORTE_25__PARAM_USUARIOLOG
        };

        String[] values = new String[]{
            REPORTE_25__PARAM_CMDKEY,
            param_01,
            param_02,
            param_03,
            param_04
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_Reporte25_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    }

    private void do_Reporte25_Back_Action(HttpServletRequest request) {

        do_Reporte25_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    } // :do_Reporte25_Back_Action

    private void do_Reporte25_SaveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_25__PARAM_PAGOFECHA,
            REPORTE_25__PARAM_CIRCULONOMBRE,
            REPORTE_25__PARAM_USUARIONOMBRE,
            REPORTE_25__PARAM_USUARIOLOG
        };

        session = request.getSession();

        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte25_SaveState

    private void do_Reporte25_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_25__PARAM_PAGOFECHA,
            REPORTE_25__PARAM_CIRCULONOMBRE,
            REPORTE_25__PARAM_USUARIONOMBRE,
            REPORTE_25__PARAM_USUARIOLOG
        };

        session = request.getSession();

        session.removeAttribute(AWReportes.USUARIOS_CONSULTAR_POR_CIRCULO);
        session.removeAttribute("consultaUsuariosCajeros");

        delete_PageItemsState(itemIds, request, session);

    } // :do_Reporte25_RemoveState

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    /* Reporte 57 - DIARIO RADICADOR DE MATRICULAS
     * AHERRENO 11/04/2011
     * Se agregan variables: Fecha Final y Usuario que genera el reporte
     */
    private void do_Reporte57_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_57__PARAM_PFECHAINI);
        String param_02 = request.getParameter(REPORTE_57__PARAM_PPAGINA);
        String param_03 = request.getParameter(REPORTE_57__PARAM_PCIRCULONOMBRE);
        String param_04 = request.getParameter(REPORTE_57__PARAM_PFECHAFIN);
        String param_05 = request.getParameter(REPORTE_57__PARAM_USUARIOLOG);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");
        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: No. de Página: Debe digitar un valor");
        } else {
            for (int i = 0; i < param_02.length(); i++) {
                if (!Character.isDigit(param_02.charAt(i))) {
                    exception.addError("parametro: No. de Página: Debe ser un valor numerico");
                    break;
                }
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");
        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_04);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_04 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_05) || ("".equalsIgnoreCase(param_05))) {
            exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_05)) {
                exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
            }
        }

        String[] itemIds = new String[]{
            REPORTE_57__PARAM_PFECHAINI, REPORTE_57__PARAM_PPAGINA, REPORTE_57__PARAM_PCIRCULONOMBRE, REPORTE_57__PARAM_PFECHAFIN, REPORTE_57__PARAM_USUARIOLOG
        };

        do_ReporteXX_SaveState(itemIds, request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_57__PARAM_PFECHAINI, REPORTE_57__PARAM_PPAGINA, REPORTE_57__PARAM_PCIRCULONOMBRE, REPORTE_57__PARAM_PFECHAFIN, REPORTE_57__PARAM_USUARIOLOG
        };

        String[] values = new String[]{
            REPORTE_57__PARAM_CMDKEY, param_01, param_02, param_03, param_04, param_05
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    // AHERRENO 19/08/2009
    // Método encargado de validar las Variables del reporte Folios Masivos
    private void do_Reporte_FOLIOS_MAS_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_FOLIOS_MAS__PARAM_PFECHAINI);
        String param_02 = request.getParameter(REPORTE_FOLIOS_MAS__PARAM_PFECHAFIN);
        String param_03 = request.getParameter(REPORTE_FOLIOS_MAS__PARAM_PPAGINA);
        String param_04 = request.getParameter(REPORTE_FOLIOS_MAS__PARAM_PCIRCULONOMBRE);

        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {
            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");
        } else if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {
            exception.addError("parametro: Fecha Final: Debe digitar un valor");
        } else {
            String send1 = null;
            String send2 = null;
            Date fecha1 = null;
            Date fecha2 = null;
            try {
                fecha1 = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha1);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
            try {
                fecha2 = DateFormatUtil.parse(param_02);
                send2 = DateFormatUtil.format("yyyy-MM-dd", fecha2);
                param_02 = send2;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Final : La fecha es inválida" + e.getMessage());
            }
        }

        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {
            exception.addError("parametro: No. de Página: Debe digitar un valor");
        } else {
            for (int i = 0; i < param_03.length(); i++) {
                if (!Character.isDigit(param_03.charAt(i))) {
                    exception.addError("parametro: No. de Página: Debe ser un valor numerico");
                    break;
                }
            }
        }

        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {
            exception.addError("parametro: Circulo: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        String[] itemIds = new String[]{
            REPORTE_FOLIOS_MAS__PARAM_PFECHAINI, REPORTE_FOLIOS_MAS__PARAM_PFECHAFIN, REPORTE_FOLIOS_MAS__PARAM_PPAGINA, REPORTE_FOLIOS_MAS__PARAM_PCIRCULONOMBRE
        };

        do_ReporteXX_SaveState(itemIds, request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        // -----------------------------------------------------

        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_FOLIOS_MAS__PARAM_PFECHAINI, REPORTE_FOLIOS_MAS__PARAM_PFECHAFIN, REPORTE_FOLIOS_MAS__PARAM_PPAGINA, REPORTE_FOLIOS_MAS__PARAM_PCIRCULONOMBRE
        };

        String[] values = new String[]{
            REPORTE_FOLIOS_MAS__PARAM_CMDKEY, param_01, param_02, param_03, param_04
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    //AHERRENO 22/09/2009
    //Reporte E01
    private void do_ReporteE01_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_E01__PARAM_PFECHAINI);
        String param_02 = request.getParameter(REPORTE_E01__PARAM_PFECHAFIN);
        String param_03 = request.getParameter(REPORTE_E01__PARAM_PCIRCULONOMBRE);
        String param_04 = request.getParameter(REPORTE_E01__PARAM_USUARIOLOG);
        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {
            exception.addError("parametro: Fecha Inicio Radicación: Debe digitar un valor");
        } else {
            String send1 = null;
            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio Radicación: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {
            exception.addError("parametro: Fecha Fin Radicación: Debe digitar un valor");
        } else {
            String send1 = null;
            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin Radicación: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {
            exception.addError("parametro: Circulo: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {
            exception.addError("parametro: Usuario Logueado: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Usuario Logueado: Debe digitar un valor");
            }
        }

        do_ReporteE01_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_E01__PARAM_PFECHAINI, REPORTE_E01__PARAM_PFECHAFIN, REPORTE_E01__PARAM_PCIRCULONOMBRE, REPORTE_E01__PARAM_USUARIOLOG
        };
        String[] values = new String[]{
            REPORTE_E01__PARAM_CMDKEY, param_01, param_02, param_03, param_04
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteE01_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_ReporteE01_Back_Action(HttpServletRequest request) {

        do_ReporteE01_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    } // :do_ReporteE01_Back_Action

    private void do_ReporteE01_SaveState(HttpServletRequest request) {
        HttpSession session;
        String[] itemIds = new String[]{
            REPORTE_E01__PARAM_PFECHAINI, REPORTE_E01__PARAM_PFECHAFIN, REPORTE_E01__PARAM_PCIRCULONOMBRE, REPORTE_E01__PARAM_USUARIOLOG
        };

        session = request.getSession();
        save_PageItemsState(itemIds, request, session);

    } // :do_ReporteE01_SaveState

    private void do_ReporteE01_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_E01__PARAM_PFECHAINI, REPORTE_E01__PARAM_PFECHAFIN, REPORTE_E01__PARAM_PCIRCULONOMBRE, REPORTE_E01__PARAM_USUARIOLOG
        };

        session = request.getSession();
        delete_PageItemsState(itemIds, request, session);

    } // :do_ReporteE01_RemoveState
    // -------------------------------------------------------------------------------
    //AHERRENO 22/09/2009
    //Reporte 161

    private void do_Reporte161_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException, AccionWebException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String[] natJuridicas = request.getParameterValues(CNaturalezaJuridica.ID_NATURALEZA_JURIDICA);
        String cadenaNatJur = null;
        boolean bandera = true;
        if (natJuridicas != null) {
            for (int i = 0; i < natJuridicas.length; i++) {
                String idNatJuridica = natJuridicas[i];
                if (bandera) {
                    cadenaNatJur = idNatJuridica;
                    bandera = false;
                } else {
                    cadenaNatJur += "," + idNatJuridica;
                }
            }
        }

        String param_01 = request.getParameter(REPORTE_161__PARAM_PFECHAINI);
        String param_02 = request.getParameter(REPORTE_161__PARAM_PFECHAFIN);
        String param_03 = request.getParameter(REPORTE_161__PARAM_PCIRCULONOMBRE);
        String param_04 = (String) request.getSession().getAttribute(CMunicipio.ID_MUNICIPIO);
        String param_05 = (String) request.getSession().getAttribute(CVereda.ID_VEREDA);
        String param_06 = cadenaNatJur;
        String param_07 = request.getParameter(REPORTE_161__PARAM_USUARIOLOG);

        GregorianCalendar capturaFecha1 = null;
        GregorianCalendar capturaFecha2 = null;
        int ano1 = 0;
        int ano2 = 0;
        int mes1 = 0;
        int mes2 = 0;
        int dia1 = 0;
        int dia2 = 0;
        //*******************************//

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {
            exception.addError("parametro: Fecha Inicio Radicación: Debe digitar un valor");
        } else {
            String send1 = null;
            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;

                String[] fechas = param_01.split("-");//yyyy-MM-dd - 0-1-2
                ano1 = Integer.parseInt(fechas[0]);
                mes1 = Integer.parseInt(fechas[1]);
                dia1 = Integer.parseInt(fechas[2]);
                capturaFecha1 = new GregorianCalendar(ano1, mes1, dia1);

            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio Radicación: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {
            exception.addError("parametro: Fecha Fin Radicación: Debe digitar un valor");
        } else {
            String send1 = null;
            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;

                String[] fechas = param_02.split("-");//yyyy-MM-dd - 0-1-2
                ano2 = Integer.parseInt(fechas[0]);
                mes2 = Integer.parseInt(fechas[1]);
                dia2 = Integer.parseInt(fechas[2]);
                capturaFecha2 = new GregorianCalendar(ano2, mes2 - 1, dia2); //Los meses van de 0 a 11

            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin Radicación: La fecha es inválida" + e.getMessage());
            }
        }

        if (capturaFecha1 != null && capturaFecha2 != null) {
            if (!(mes1 == mes2 && ano1 == ano2)) {

                Calendar valFec = Calendar.getInstance();
                valFec.set(ano1, mes1 - 1, dia1, 0, 0, 0); //Los meses van de 0 a 11
                valFec.add(Calendar.DATE, 30);
                System.out.println(valFec.getTime());
                System.out.println(capturaFecha2.getTime());

                if (capturaFecha2.after(valFec)) {
                    exception.addError("El Rango de fechas no puede exceder a un mes");
                }
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {
            exception.addError("parametro: Circulo: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe Seleccinar un Circulo");
            }
        }

        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {
            exception.addError("parametro: Municipio: Debe Seleccionar un Municipio");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Municipio: Debe Seleccionar un Municipio");
            }
        }

        if ((null == param_05) || ("".equalsIgnoreCase(param_05))) {
            exception.addError("parametro: Vereda: Debe Seleccionar una Vereda");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_05)) {
                exception.addError("parametro: Vereda: Debe Seleccionar una Vereda");
            }
        }

        if ((null == param_06) || ("".equalsIgnoreCase(param_06))) {
            exception.addError("parametro: Naturaleza Juridica: Debe Seleccionar una Naturaleza Juridica");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_06)) {
                exception.addError("parametro: Naturaleza Juridica: Debe Seleccionar una Naturaleza Juridica");
            }
        }

        if ((null == param_07) || ("".equalsIgnoreCase(param_07))) {
            exception.addError("parametro: Usuario Logueado: Usuario no encontrado");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_07)) {
                exception.addError("parametro: Usuario Logueado: Usuario no encontrado");
            }
        }
        do_Reporte161_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_161__PARAM_PFECHAINI, REPORTE_161__PARAM_PFECHAFIN, REPORTE_161__PARAM_PCIRCULONOMBRE, REPORTE_161__PARAM_PMUNICIPIO, REPORTE_161__PARAM_PVEREDA, REPORTE_161__PARAM_PNATJURIDICA, REPORTE_161__PARAM_USUARIOLOG
        };
        String[] values = new String[]{
            REPORTE_161__PARAM_CMDKEY, param_01, param_02, param_03, param_04, param_05, param_06, param_07
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_Reporte161_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    
    private void do_Reporte161_Back_Action(HttpServletRequest request) {

        do_Reporte161_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    } // :do_Reporte161_Back_Action

    private void do_Reporte161_SaveState(HttpServletRequest request) {
        HttpSession session;
        String[] itemIds = new String[]{
            REPORTE_161__PARAM_PFECHAINI, REPORTE_161__PARAM_PFECHAFIN, REPORTE_161__PARAM_PCIRCULONOMBRE, REPORTE_161__PARAM_PMUNICIPIO, REPORTE_161__PARAM_PVEREDA, REPORTE_161__PARAM_PNATJURIDICA, REPORTE_161__PARAM_USUARIOLOG
        };

        session = request.getSession();
        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte161_SaveState

    private void do_Reporte161_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_161__PARAM_PFECHAINI, REPORTE_161__PARAM_PFECHAFIN, REPORTE_161__PARAM_PCIRCULONOMBRE, REPORTE_161__PARAM_PMUNICIPIO, REPORTE_161__PARAM_PVEREDA, REPORTE_161__PARAM_PNATJURIDICA, REPORTE_161__PARAM_USUARIOLOG
        };

        session = request.getSession();
        delete_PageItemsState(itemIds, request, session);
        session.removeAttribute(CMunicipio.ID_MUNICIPIO);
        session.removeAttribute(CVereda.ID_VEREDA);
        session.removeAttribute(CNaturalezaJuridica.ID_NATURALEZA_JURIDICA);

        // DELETE LISTS OF VALUES IN CACHE
        itemIds = new String[]{
            AWReportes.LISTA_DEPARTAMENTOS, AWReportes.LISTA_MUNICIPIOS, AWReportes.LISTA_VEREDAS
        };
        delete_PageItemsState(itemIds, request, session);

    } // :do_Reporte161_RemoveState
    // -------------------------------------------------------------------------------
    //Carlos Mario Torres Urina 22/09/2009
    //Reporte Exportar folios

    private void do_ReporteExportar_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException, AccionWebException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_161__PARAM_PCIRCULONOMBRE);
        String param_02 = (String) request.getSession().getAttribute(CMunicipio.ID_MUNICIPIO);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {
            exception.addError("parametro: Circulo: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_01)) {
                exception.addError("parametro: Circulo: Debe Seleccinar un Circulo");
            }
        }

        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {
            exception.addError("parametro: Municipio: Debe Seleccionar un Municipio");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_02)) {
                exception.addError("parametro: Municipio: Debe Seleccionar un Municipio");
            }
        }

        do_ReporteExportarFolios_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_EXPORTAR_FOLIOS__PARAM_CIRCULO, REPORTE_EXPORTAR_FOLIOS__PARAM_MUNICIPIO
        };
        String[] values = new String[]{
            REPORTE_EXPORTAR_FOLIOS__PARAM_CMDKEY, param_01, param_02
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteExportarFolios_SaveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_ReporteExportarFolios_Back_Action(HttpServletRequest request) {

        do_ReporteExportarFolios_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    } // :do_Reporte161_Back_Action

    private void do_ReporteExportarFolios_SaveState(HttpServletRequest request) {
        HttpSession session;
        String[] itemIds = new String[]{
            REPORTE_EXPORTAR_FOLIOS__PARAM_CIRCULO, REPORTE_EXPORTAR_FOLIOS__PARAM_MUNICIPIO
        };

        session = request.getSession();
        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte161_SaveState

    private void do_ReporteExportarFolios_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_EXPORTAR_FOLIOS__PARAM_CIRCULO, REPORTE_EXPORTAR_FOLIOS__PARAM_MUNICIPIO, REPORTE_EXPORTAR_FOLIOS__PARAM_VEREDA
        };

        session = request.getSession();
        delete_PageItemsState(itemIds, request, session);
        session.removeAttribute(REPORTE_161__PARAM_PCIRCULONOMBRE);
        session.removeAttribute(CMunicipio.ID_MUNICIPIO);
        session.removeAttribute(CVereda.ID_VEREDA);

        // DELETE LISTS OF VALUES IN CACHE
        itemIds = new String[]{
            AWReportes.LISTA_DEPARTAMENTOS, AWReportes.LISTA_MUNICIPIOS, AWReportes.LISTA_VEREDAS
        };
        delete_PageItemsState(itemIds, request, session);

    } // :do_Reporte161_RemoveState

    // -------------------------------------------------------------------------------
    //AHERRENO 26/04/2010
    //Reporte E02
    private void do_ReporteE02_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_E02__PARAM_PFECHAINI);
        String param_02 = request.getParameter(REPORTE_E02__PARAM_PFECHAFIN);
        String param_03 = request.getParameter(REPORTE_E02__PARAM_PCIRCULONOMBRE);
        String param_04 = request.getParameter(REPORTE_E02__PARAM_USUARIOLOG);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {
            exception.addError("parametro: Fecha Inicio Radicación: Debe digitar un valor");
        } else {
            String send1 = null;
            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio Radicación: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {
            exception.addError("parametro: Fecha Fin Radicación: Debe digitar un valor");
        } else {
            String send1 = null;
            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin Radicación: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {
            exception.addError("parametro: Circulo: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {
            exception.addError("parametro: Usuario Logueado: Usuario no encontrado");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Usuario Logueado: Usuario no encontrado");
            }
        }

        do_ReporteE02_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_E02__PARAM_PFECHAINI, REPORTE_E02__PARAM_PFECHAFIN, REPORTE_E02__PARAM_PCIRCULONOMBRE, REPORTE_E02__PARAM_USUARIOLOG
        };
        String[] values = new String[]{
            REPORTE_E02__PARAM_CMDKEY, param_01, param_02, param_03, param_04
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteE02_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_ReporteE02_Back_Action(HttpServletRequest request) {

        do_ReporteE02_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    } // :do_ReporteE02_Back_Action

    private void do_ReporteE02_SaveState(HttpServletRequest request) {
        HttpSession session;
        String[] itemIds = new String[]{
            REPORTE_E02__PARAM_PFECHAINI, REPORTE_E02__PARAM_PFECHAFIN, REPORTE_E02__PARAM_PCIRCULONOMBRE, REPORTE_E02__PARAM_USUARIOLOG
        };

        session = request.getSession();
        save_PageItemsState(itemIds, request, session);

    } // :do_ReporteE02_SaveState

    private void do_ReporteE02_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_E02__PARAM_PFECHAINI, REPORTE_E02__PARAM_PFECHAFIN, REPORTE_E02__PARAM_PCIRCULONOMBRE, REPORTE_E02__PARAM_USUARIOLOG
        };

        session = request.getSession();
        delete_PageItemsState(itemIds, request, session);

    } // :do_ReporteE02_RemoveState
    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    //AHERRENO 02/08/2010
    //Reporte E03

    private void do_ReporteE03_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_E03__PARAM_PFECHAINI);
        String param_02 = request.getParameter(REPORTE_E03__PARAM_PFECHAFIN);
        String param_03 = request.getParameter(REPORTE_E03__PARAM_PCIRCULONOMBRE);
        String param_04 = request.getParameter(REPORTE_E03__PARAM_USUARIOLOG);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {
            exception.addError("parametro: Fecha Inicio Radicación: Debe digitar un valor");
        } else {
            String send1 = null;
            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio Radicación: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {
            exception.addError("parametro: Fecha Fin Radicación: Debe digitar un valor");
        } else {
            String send1 = null;
            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin Radicación: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {
            exception.addError("parametro: Circulo: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {
            exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
            }
        }

        do_ReporteE03_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_E03__PARAM_PFECHAINI, REPORTE_E03__PARAM_PFECHAFIN, REPORTE_E03__PARAM_PCIRCULONOMBRE, REPORTE_E03__PARAM_USUARIOLOG
        };
        String[] values = new String[]{
            REPORTE_E03__PARAM_CMDKEY, param_01, param_02, param_03, param_04
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteE03_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_ReporteE03_Back_Action(HttpServletRequest request) {
        do_ReporteE03_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    } // :do_ReporteE03_Back_Action

    private void do_ReporteE03_SaveState(HttpServletRequest request) {
        HttpSession session;
        String[] itemIds = new String[]{
            REPORTE_E03__PARAM_PFECHAINI, REPORTE_E03__PARAM_PFECHAFIN, REPORTE_E03__PARAM_PCIRCULONOMBRE, REPORTE_E03__PARAM_USUARIOLOG
        };

        session = request.getSession();
        save_PageItemsState(itemIds, request, session);

    } // :do_ReporteE03_SaveState

    private void do_ReporteE03_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_E03__PARAM_PFECHAINI, REPORTE_E03__PARAM_PFECHAFIN, REPORTE_E03__PARAM_PCIRCULONOMBRE, REPORTE_E03__PARAM_USUARIOLOG
        };

        session = request.getSession();
        delete_PageItemsState(itemIds, request, session);

    } // :do_ReporteE03_RemoveState
    // -------------------------------------------------------------------------------

    /*	private void reporte07(HttpServletRequest request)
    throws ValidacionParametrosException {
    ValidacionParametrosException exception =
    new ValidacionParametrosException();
    String fechaInicioS =
    request.getParameter(AWConsultasReparto.FECHA_INICIO);

    String fechaFinS =
    request.getParameter(AWConsultasReparto.FECHA_FIN);

    SimpleDateFormat df = new SimpleDateFormat();

    df.applyPattern("dd/MM/yyyy");
    String send1 = null;

    Date fechaInicio = null;
    try {
    fechaInicio = df.parse(fechaInicioS);
    df.applyPattern("yyyy-MM-dd");
    send1 = df.format(fechaInicio);
    } catch (Exception e) {
    exception.addError("La fecha es inválida" + e.getMessage());
    }

    df.applyPattern("dd/MM/yyyy");
    String send2 = null;

    Date fechaFin = null;
    try {
    fechaFin = df.parse(fechaFinS);
    df.applyPattern("yyyy-MM-dd");
    send2 = df.format(fechaFin);
    } catch (Exception e) {
    exception.addError("La fecha es inválida" + e.getMessage());
    }

    String send3 = request.getParameter(CCirculo.ID_CIRCULO);
    if (send3 == null || send3.length() == 0) {
    exception.addError("El circulo es inválido");
    }

    if (exception.getErrores().size() > 0) {
    throw exception;
    }

    if(fechaFin.before(fechaInicio)){
    exception.addError("La fecha Final no puede ser menor a la fecha Inicial");
    throw exception;
    }

    String send0 = "reporte-07.report";

    String[] params = new String[] {"cmdkey", "P_TURNO_FECHA_INICIO", "P_TURNO_FECHA_FIN", "P_CIRCULO_NOMBRE"};
    String[] values = new String[] {send0, send1, send2, send3};

    String url = makeUrl(params, values);
    reportsServices_ConfigureParameterUri( request, url );
    }*/
    private void do_Reporte07_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_07__PARAM_PFECHAINI);

        String param_02 = request.getParameter(REPORTE_07__PARAM_PFECHAFIN);

        String param_03 = request.getParameter(REPORTE_07__PARAM_PCIRCULONOMBRE);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {

            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin : La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }

        }

        do_Reporte07_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_07__PARAM_PFECHAINI, REPORTE_07__PARAM_PFECHAFIN, REPORTE_07__PARAM_PCIRCULONOMBRE
        };

        String[] values = new String[]{
            REPORTE_07__PARAM_CMDKEY, param_01, param_02, param_03
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_Reporte07_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte07_Back_Action(HttpServletRequest request) {

        do_Reporte07_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    } // :do_Reporte07_Back_Action

    private void do_Reporte07_SaveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_07__PARAM_PFECHAINI, REPORTE_07__PARAM_PFECHAFIN, REPORTE_07__PARAM_PCIRCULONOMBRE
        };

        session = request.getSession();

        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte07_SaveState

    private void do_Reporte07_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_07__PARAM_PFECHAINI, REPORTE_07__PARAM_PFECHAFIN, REPORTE_07__PARAM_PCIRCULONOMBRE
        };

        session = request.getSession();

        delete_PageItemsState(itemIds, request, session);

    } // :do_Reporte07_RemoveState

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    //AHERRENO 05/01/2011
    //Reporte 162
    private void do_Reporte162_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_162__PARAM_PFECHAINI);
        String param_02 = request.getParameter(REPORTE_162__PARAM_PFECHAFIN);
        String param_03 = request.getParameter(REPORTE_162__PARAM_PCIRCULONOMBRE);
        String param_04 = request.getParameter(REPORTE_162__PARAM_USUARIOLOG);
        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio Traslado: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio Traslado: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin Traslado: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin Traslado: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo Origen: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo Origen: Debe digitar un valor");
            }
        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {
            exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
            }
        }

        String[] itemIds = new String[]{
            REPORTE_162__PARAM_PFECHAINI, REPORTE_162__PARAM_PFECHAFIN, REPORTE_162__PARAM_PCIRCULONOMBRE, REPORTE_162__PARAM_USUARIOLOG
        };

        do_Reporte162_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_162__PARAM_PFECHAINI, REPORTE_162__PARAM_PFECHAFIN, REPORTE_162__PARAM_PCIRCULONOMBRE, REPORTE_162__PARAM_USUARIOLOG
        };

        String[] values = new String[]{
            REPORTE_162__PARAM_CMDKEY, param_01, param_02, param_03, param_04
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte162_Back_Action(HttpServletRequest request) {
        do_Reporte162_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    } // :do_Reporte162_Back_Action

    private void do_Reporte162_SaveState(HttpServletRequest request) {
        HttpSession session;
        String[] itemIds = new String[]{
            REPORTE_162__PARAM_PFECHAINI, REPORTE_162__PARAM_PFECHAFIN, REPORTE_162__PARAM_PCIRCULONOMBRE, REPORTE_162__PARAM_USUARIOLOG
        };

        session = request.getSession();
        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte162_SaveState

    private void do_Reporte162_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_162__PARAM_PFECHAINI, REPORTE_162__PARAM_PFECHAFIN, REPORTE_162__PARAM_PCIRCULONOMBRE, REPORTE_162__PARAM_USUARIOLOG
        };

        session = request.getSession();
        delete_PageItemsState(itemIds, request, session);

    } // :do_Reporte162_RemoveState

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    //AHERRENO 15/02/2011
    //Reporte 163
    private void do_Reporte163_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_163__PARAM_PFECHAINI);
        String param_02 = request.getParameter(REPORTE_163__PARAM_PFECHAFIN);
        String param_03 = request.getParameter(REPORTE_163__PARAM_PCIRCULONOMBRE);
        String param_04 = request.getParameter(REPORTE_163__PARAM_USUARIOLOG);
        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio Traslado: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio Traslado: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin Traslado: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin Traslado: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {
            exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
            }
        }

        String[] itemIds = new String[]{
            REPORTE_163__PARAM_PFECHAINI, REPORTE_163__PARAM_PFECHAFIN, REPORTE_163__PARAM_PCIRCULONOMBRE, REPORTE_163__PARAM_USUARIOLOG
        };

        do_Reporte163_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_163__PARAM_PFECHAINI, REPORTE_163__PARAM_PFECHAFIN, REPORTE_163__PARAM_PCIRCULONOMBRE, REPORTE_163__PARAM_USUARIOLOG
        };

        String[] values = new String[]{
            REPORTE_163__PARAM_CMDKEY, param_01, param_02, param_03, param_04
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte163_Back_Action(HttpServletRequest request) {
        do_Reporte163_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    } // :do_Reporte163_Back_Action

    private void do_Reporte163_SaveState(HttpServletRequest request) {
        HttpSession session;
        String[] itemIds = new String[]{
            REPORTE_163__PARAM_PFECHAINI, REPORTE_163__PARAM_PFECHAFIN, REPORTE_163__PARAM_PCIRCULONOMBRE, REPORTE_163__PARAM_USUARIOLOG
        };

        session = request.getSession();
        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte163_SaveState

    private void do_Reporte163_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_163__PARAM_PFECHAINI, REPORTE_163__PARAM_PFECHAFIN, REPORTE_163__PARAM_PCIRCULONOMBRE, REPORTE_163__PARAM_USUARIOLOG
        };

        session = request.getSession();
        delete_PageItemsState(itemIds, request, session);

    } // :do_Reporte163_RemoveState
    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    //AHERRENO 05/01/2011
    //Reporte 164

    private void do_Reporte164_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_164__PARAM_PFECHAINI);
        String param_02 = request.getParameter(REPORTE_164__PARAM_PFECHAFIN);
        String param_03 = request.getParameter(REPORTE_164__PARAM_PCIRCULONOMBRE);
        String param_04 = request.getParameter(REPORTE_164__PARAM_USUARIOLOG);
        String param_05 = request.getParameter(REPORTE_164__PARAM_PCUANTIA);
        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio Traslado: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio Solicitud: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin Solicitud: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin Solicitud: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {
            exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
            }
        }

        // apply validators
        if ((null == param_05) || ("".equalsIgnoreCase(param_05))) {

            exception.addError("parametro: Cuantia: Debe digitar un valor");

        } else {
            boolean bandera = true;
            for (int i = 0; i < param_05.length(); i++) {
                if (!Character.isDigit(param_05.charAt(i))) {
                    exception.addError("parametro: Cuantia: Debe ser un valor numerico");
                    i = param_05.length();
                    if (bandera) {
                        bandera = false;

                    }

                }
            }
            if (bandera) {
                if (Double.valueOf(param_05.trim()).doubleValue() < Double.valueOf("7000000000").doubleValue()) {
                    exception.addError("parametro: Cuantia: Debe ser igual o mayor a 7.000.000.000");
                }
            }
        }

        String[] itemIds = new String[]{
            REPORTE_164__PARAM_PFECHAINI, REPORTE_164__PARAM_PFECHAFIN, REPORTE_164__PARAM_PCIRCULONOMBRE, REPORTE_164__PARAM_USUARIOLOG, REPORTE_164__PARAM_PCUANTIA
        };

        do_Reporte164_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_164__PARAM_PFECHAINI, REPORTE_164__PARAM_PFECHAFIN, REPORTE_164__PARAM_PCIRCULONOMBRE, REPORTE_164__PARAM_USUARIOLOG, REPORTE_164__PARAM_PCUANTIA
        };

        String[] values = new String[]{
            REPORTE_164__PARAM_CMDKEY, param_01, param_02, param_03, param_04, param_05
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte164_Back_Action(HttpServletRequest request) {
        do_Reporte164_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    } // :do_Reporte164_Back_Action

    private void do_Reporte164_SaveState(HttpServletRequest request) {
        HttpSession session;
        String[] itemIds = new String[]{
            REPORTE_164__PARAM_PFECHAINI, REPORTE_164__PARAM_PFECHAFIN, REPORTE_164__PARAM_PCIRCULONOMBRE, REPORTE_164__PARAM_USUARIOLOG, REPORTE_164__PARAM_PCUANTIA
        };

        session = request.getSession();
        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte164_SaveState

    private void do_Reporte164_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_164__PARAM_PFECHAINI, REPORTE_164__PARAM_PFECHAFIN, REPORTE_164__PARAM_PCIRCULONOMBRE, REPORTE_164__PARAM_USUARIOLOG, REPORTE_164__PARAM_PCUANTIA
        };

        session = request.getSession();
        delete_PageItemsState(itemIds, request, session);

    } // :do_Reporte164_RemoveState

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    //AHERRENO 14/03/2011
    //Reporte 165
    private void do_Reporte165_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_165__PARAM_PFECHAINI);
        String param_02 = request.getParameter(REPORTE_165__PARAM_PFECHAFIN);
        String param_03 = request.getParameter(REPORTE_165__PARAM_PCIRCULONOMBRE);
        String param_04 = request.getParameter(REPORTE_165__PARAM_USUARIOLOG);

        GregorianCalendar capturaFecha1 = null;
        GregorianCalendar capturaFecha2 = null;
        int ano1 = 0;
        int ano2 = 0;
        int mes1 = 0;
        int mes2 = 0;
        int dia1 = 0;
        int dia2 = 0;

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio Radicacion: Debe digitar un valor");

        } else {
            String send1 = null;
            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;

                String[] fechas = param_01.split("-");//yyyy-MM-dd - 0-1-2
                ano1 = Integer.parseInt(fechas[0]);
                mes1 = Integer.parseInt(fechas[1]);
                dia1 = Integer.parseInt(fechas[2]);
                capturaFecha1 = new GregorianCalendar(ano1, mes1, dia1);

            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio Radicacion: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin Radicacion: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;

                String[] fechas = param_02.split("-");//yyyy-MM-dd - 0-1-2
                ano2 = Integer.parseInt(fechas[0]);
                mes2 = Integer.parseInt(fechas[1]);
                dia2 = Integer.parseInt(fechas[2]);
                capturaFecha2 = new GregorianCalendar(ano2, mes2 - 1, dia2);//Los meses van de 0 a 11

            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin Radicacion: La fecha es inválida" + e.getMessage());
            }
        }

        if (capturaFecha1 != null && capturaFecha2 != null) {
            if (!(mes1 == mes2 && ano1 == ano2)) {

                Calendar valFec = Calendar.getInstance();
                valFec.set(ano1, mes1 - 1, dia1, 0, 0, 0); //Los meses van de 0 a 11
                valFec.add(Calendar.DATE, 180);

                if (capturaFecha2.after(valFec)) {
                    exception.addError("El Rango de fechas no puede exceder a seis (6) meses");
                }
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {
            exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
            }
        }
        String[] itemIds = new String[]{
            REPORTE_165__PARAM_PFECHAINI, REPORTE_165__PARAM_PFECHAFIN, REPORTE_165__PARAM_PCIRCULONOMBRE, REPORTE_165__PARAM_USUARIOLOG
        };

        do_Reporte165_SaveState(request);
        // raise ------------------------------------------------

        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        // -----------------------------------------------------

        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_165__PARAM_PFECHAINI, REPORTE_165__PARAM_PFECHAFIN, REPORTE_165__PARAM_PCIRCULONOMBRE, REPORTE_165__PARAM_USUARIOLOG
        };

        String[] values = new String[]{
            REPORTE_165__PARAM_CMDKEY, param_01, param_02, param_03, param_04
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte165_Back_Action(HttpServletRequest request) {
        do_Reporte165_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    } // :do_Reporte165_Back_Action

    private void do_Reporte165_SaveState(HttpServletRequest request) {
        HttpSession session;
        String[] itemIds = new String[]{
            REPORTE_165__PARAM_PFECHAINI, REPORTE_165__PARAM_PFECHAFIN, REPORTE_165__PARAM_PCIRCULONOMBRE, REPORTE_165__PARAM_USUARIOLOG
        };

        session = request.getSession();
        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte165_SaveState

    private void do_Reporte165_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_165__PARAM_PFECHAINI, REPORTE_165__PARAM_PFECHAFIN, REPORTE_165__PARAM_PCIRCULONOMBRE, REPORTE_165__PARAM_USUARIOLOG
        };

        session = request.getSession();
        delete_PageItemsState(itemIds, request, session);

    } // :do_Reporte165_RemoveState
    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    //AHERRENO 14/03/2011
    //Reporte 166

    private void do_Reporte166_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_166__PARAM_PFECHAINI);
        String param_02 = request.getParameter(REPORTE_166__PARAM_PFECHAFIN);
        String param_03 = request.getParameter(REPORTE_166__PARAM_PCIRCULONOMBRE);
        String param_04 = request.getParameter(REPORTE_166__PARAM_USUARIOLOG);

        GregorianCalendar capturaFecha1 = null;
        GregorianCalendar capturaFecha2 = null;
        int ano1 = 0;
        int ano2 = 0;
        int mes1 = 0;
        int mes2 = 0;
        int dia1 = 0;
        int dia2 = 0;

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio Reparto: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;

                String[] fechas = param_01.split("-");//yyyy-MM-dd - 0-1-2
                ano1 = Integer.parseInt(fechas[0]);
                mes1 = Integer.parseInt(fechas[1]);
                dia1 = Integer.parseInt(fechas[2]);
                capturaFecha1 = new GregorianCalendar(ano1, mes1, dia1);

            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio Reparto: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin Reparto: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;

                String[] fechas = param_02.split("-");//yyyy-MM-dd - 0-1-2
                ano2 = Integer.parseInt(fechas[0]);
                mes2 = Integer.parseInt(fechas[1]);
                dia2 = Integer.parseInt(fechas[2]);
                capturaFecha2 = new GregorianCalendar(ano2, mes2 - 1, dia2);//Los meses van de 0 a 11
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin Reparto: La fecha es inválida" + e.getMessage());
            }
        }

        if (capturaFecha1 != null && capturaFecha2 != null) {
            if (!(mes1 == mes2 && ano1 == ano2)) {

                Calendar valFec = Calendar.getInstance();
                valFec.set(ano1, mes1 - 1, dia1, 0, 0, 0); //Los meses van de 0 a 11
                valFec.add(Calendar.DATE, 180);

                if (capturaFecha2.after(valFec)) {
                    exception.addError("El Rango de fechas no puede exceder a seis (6) meses");
                }
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {
            exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
            }
        }
        String[] itemIds = new String[]{
            REPORTE_166__PARAM_PFECHAINI, REPORTE_166__PARAM_PFECHAFIN, REPORTE_166__PARAM_PCIRCULONOMBRE, REPORTE_166__PARAM_USUARIOLOG
        };

        do_Reporte166_SaveState(request);
        // raise ------------------------------------------------

        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        // -----------------------------------------------------

        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_166__PARAM_PFECHAINI, REPORTE_166__PARAM_PFECHAFIN, REPORTE_166__PARAM_PCIRCULONOMBRE, REPORTE_166__PARAM_USUARIOLOG
        };

        String[] values = new String[]{
            REPORTE_166__PARAM_CMDKEY, param_01, param_02, param_03, param_04
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte166_Back_Action(HttpServletRequest request) {
        do_Reporte166_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    } // :do_Reporte166_Back_Action

    private void do_Reporte166_SaveState(HttpServletRequest request) {
        HttpSession session;
        String[] itemIds = new String[]{
            REPORTE_166__PARAM_PFECHAINI, REPORTE_166__PARAM_PFECHAFIN, REPORTE_166__PARAM_PCIRCULONOMBRE, REPORTE_166__PARAM_USUARIOLOG
        };

        session = request.getSession();
        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte166_SaveState

    private void do_Reporte166_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_166__PARAM_PFECHAINI, REPORTE_166__PARAM_PFECHAFIN, REPORTE_166__PARAM_PCIRCULONOMBRE, REPORTE_166__PARAM_USUARIOLOG
        };

        session = request.getSession();
        delete_PageItemsState(itemIds, request, session);

    } // :do_Reporte166_RemoveState
    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    //AHERRENO 14/03/2011
    //Reporte 167

    private void do_Reporte167_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_167__PARAM_PFECHAINI);
        String param_02 = request.getParameter(REPORTE_167__PARAM_PFECHAFIN);
        String param_03 = request.getParameter(REPORTE_167__PARAM_PCIRCULONOMBRE);
        String param_04 = request.getParameter(REPORTE_167__PARAM_USUARIOLOG);

        GregorianCalendar capturaFecha1 = null;
        GregorianCalendar capturaFecha2 = null;
        int ano1 = 0;
        int ano2 = 0;
        int mes1 = 0;
        int mes2 = 0;
        int dia1 = 0;
        int dia2 = 0;

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio Radicacion: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;

                String[] fechas = param_01.split("-");//yyyy-MM-dd - 0-1-2
                ano1 = Integer.parseInt(fechas[0]);
                mes1 = Integer.parseInt(fechas[1]);
                dia1 = Integer.parseInt(fechas[2]);
                capturaFecha1 = new GregorianCalendar(ano1, mes1, dia1);

            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio Radicacion: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin Radicacion: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;

                String[] fechas = param_02.split("-");//yyyy-MM-dd - 0-1-2
                ano2 = Integer.parseInt(fechas[0]);
                mes2 = Integer.parseInt(fechas[1]);
                dia2 = Integer.parseInt(fechas[2]);
                capturaFecha2 = new GregorianCalendar(ano2, mes2 - 1, dia2); //Los meses van de 0 a 11
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin Radicacion: La fecha es inválida" + e.getMessage());
            }
        }

        if (capturaFecha1 != null && capturaFecha2 != null) {
            if (!(mes1 == mes2 && ano1 == ano2)) {

                Calendar valFec = Calendar.getInstance();
                valFec.set(ano1, mes1 - 1, dia1, 0, 0, 0); //Los meses van de 0 a 11
                valFec.add(Calendar.DATE, 180);

                if (capturaFecha2.after(valFec)) {
                    exception.addError("El Rango de fechas no puede exceder a seis (6) meses");
                }
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {
            exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
            }
        }
        String[] itemIds = new String[]{
            REPORTE_167__PARAM_PFECHAINI, REPORTE_167__PARAM_PFECHAFIN, REPORTE_167__PARAM_PCIRCULONOMBRE, REPORTE_167__PARAM_USUARIOLOG
        };

        do_Reporte167_SaveState(request);
        // raise ------------------------------------------------

        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        // -----------------------------------------------------

        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_167__PARAM_PFECHAINI, REPORTE_167__PARAM_PFECHAFIN, REPORTE_167__PARAM_PCIRCULONOMBRE, REPORTE_167__PARAM_USUARIOLOG
        };

        String[] values = new String[]{
            REPORTE_167__PARAM_CMDKEY, param_01, param_02, param_03, param_04
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte167_Back_Action(HttpServletRequest request) {
        do_Reporte167_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    } // :do_Reporte167_Back_Action

    private void do_Reporte167_SaveState(HttpServletRequest request) {
        HttpSession session;
        String[] itemIds = new String[]{
            REPORTE_167__PARAM_PFECHAINI, REPORTE_167__PARAM_PFECHAFIN, REPORTE_167__PARAM_PCIRCULONOMBRE, REPORTE_167__PARAM_USUARIOLOG
        };

        session = request.getSession();
        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte167_SaveState

    private void do_Reporte167_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_167__PARAM_PFECHAINI, REPORTE_167__PARAM_PFECHAFIN, REPORTE_167__PARAM_PCIRCULONOMBRE, REPORTE_167__PARAM_USUARIOLOG
        };

        session = request.getSession();
        delete_PageItemsState(itemIds, request, session);

    } // :do_Reporte167_RemoveState

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    //AHERRENO 14/03/2011
    //Reporte 168
    private void do_Reporte168_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_168__PARAM_PFECHAINI);
        String param_02 = request.getParameter(REPORTE_168__PARAM_PFECHAFIN);
        String param_03 = request.getParameter(REPORTE_168__PARAM_PCIRCULONOMBRE);
        String param_04 = request.getParameter(REPORTE_168__PARAM_USUARIOLOG);

        GregorianCalendar capturaFecha1 = null;
        GregorianCalendar capturaFecha2 = null;
        int ano1 = 0;
        int ano2 = 0;
        int mes1 = 0;
        int mes2 = 0;
        int dia1 = 0;
        int dia2 = 0;
        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio Reimpresion: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;

                String[] fechas = param_01.split("-");//yyyy-MM-dd - 0-1-2
                ano1 = Integer.parseInt(fechas[0]);
                mes1 = Integer.parseInt(fechas[1]);
                dia1 = Integer.parseInt(fechas[2]);
                capturaFecha1 = new GregorianCalendar(ano1, mes1, dia1);
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio Reimpresion: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin Reimpresion: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;

                String[] fechas = param_02.split("-");//yyyy-MM-dd - 0-1-2
                ano2 = Integer.parseInt(fechas[0]);
                mes2 = Integer.parseInt(fechas[1]);
                dia2 = Integer.parseInt(fechas[2]);
                capturaFecha2 = new GregorianCalendar(ano2, mes2 - 1, dia2);  //Los meses van de 0 a 11
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin Reimpresion: La fecha es inválida" + e.getMessage());
            }
        }

        if (capturaFecha1 != null && capturaFecha2 != null) {
            if (!(mes1 == mes2 && ano1 == ano2)) {

                Calendar valFec = Calendar.getInstance();
                valFec.set(ano1, mes1 - 1, dia1, 0, 0, 0); //Los meses van de 0 a 11
                valFec.add(Calendar.DATE, 180);

                if (capturaFecha2.after(valFec)) {
                    exception.addError("El Rango de fechas no puede exceder a seis (6) meses");
                }
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {
            exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
            }
        }
        String[] itemIds = new String[]{
            REPORTE_168__PARAM_PFECHAINI, REPORTE_168__PARAM_PFECHAFIN, REPORTE_168__PARAM_PCIRCULONOMBRE, REPORTE_168__PARAM_USUARIOLOG
        };

        do_Reporte168_SaveState(request);
        // raise ------------------------------------------------

        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        // -----------------------------------------------------

        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_168__PARAM_PFECHAINI, REPORTE_168__PARAM_PFECHAFIN, REPORTE_168__PARAM_PCIRCULONOMBRE, REPORTE_168__PARAM_USUARIOLOG
        };

        String[] values = new String[]{
            REPORTE_168__PARAM_CMDKEY, param_01, param_02, param_03, param_04
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte168_Back_Action(HttpServletRequest request) {
        do_Reporte168_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    } // :do_Reporte168_Back_Action

    private void do_Reporte168_SaveState(HttpServletRequest request) {
        HttpSession session;
        String[] itemIds = new String[]{
            REPORTE_168__PARAM_PFECHAINI, REPORTE_168__PARAM_PFECHAFIN, REPORTE_168__PARAM_PCIRCULONOMBRE, REPORTE_168__PARAM_USUARIOLOG
        };

        session = request.getSession();
        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte168_SaveState

    private void do_Reporte168_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_168__PARAM_PFECHAINI, REPORTE_168__PARAM_PFECHAFIN, REPORTE_168__PARAM_PCIRCULONOMBRE, REPORTE_168__PARAM_USUARIOLOG
        };

        session = request.getSession();
        delete_PageItemsState(itemIds, request, session);

    } // :do_Reporte168_RemoveState

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    /**
     * @param request Reporte 169 - INFORME TRANSACCIONES POR TURNO AHERRENO
     * 01/06/2012
     */
    private void do_Reporte169_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new ValidacionParametrosException();

        String param_01 = request.getParameter(REPORTE_169__PARAM_TURNO).trim();
        String param_02 = request.getParameter(REPORTE_169__PARAM_USUARIOLOG);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Turno: Debe digitar un valor");

        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {
            exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_02)) {
                exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
            }
        }

        do_Reporte169_SaveState(request);

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY,
            REPORTE_169__PARAM_TURNO,
            REPORTE_169__PARAM_USUARIOLOG
        };

        String[] values = new String[]{
            REPORTE_169__PARAM_CMDKEY,
            param_01,
            param_02
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_Reporte169_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    }

    private void do_Reporte169_Back_Action(HttpServletRequest request) {

        do_Reporte169_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    } // :do_Reporte169_Back_Action

    private void do_Reporte169_SaveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_169__PARAM_TURNO,
            REPORTE_169__PARAM_USUARIOLOG
        };

        session = request.getSession();

        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte169_SaveState

    private void do_Reporte169_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_169__PARAM_TURNO,
            REPORTE_169__PARAM_USUARIOLOG
        };

        session = request.getSession();

        session.removeAttribute(AWReportes.USUARIOS_CONSULTAR_POR_CIRCULO);
        session.removeAttribute("consultaUsuariosCajeros");

        delete_PageItemsState(itemIds, request, session);

    } // :do_Reporte169_RemoveState    
    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------

    private String makeUrl(String[] params, String[] values) {
        String url = reportesServletPath + "?";
        for (int i = 0; i < params.length; i++) {
            url += params[i] + "=" + values[i] + "&";
        }

        return url;
    }

    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar una <code>ValidacionNota</code> del
     * <code>HttpServletRequest</code>
     *
     * @param request
     * @return dato <code>ValidacionNota</code>
     * @throws AccionWebException
     */
    private ValidacionNota getValidacionNota(HttpServletRequest request)
            throws ValidacionValidaNotaException {
        HttpSession session = request.getSession();

        ValidacionValidaNotaException exception = new ValidacionValidaNotaException();

        String idProceso = request.getParameter(CProceso.PROCESO_ID);
        if ((idProceso == null) || (idProceso.trim().length() == 0) || idProceso.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un Proceso.");
        } else {
            session.setAttribute(CProceso.PROCESO_ID, idProceso);
        }

        long idPrc = 0;
        try {
            idPrc = Long.parseLong(idProceso);
        } catch (NumberFormatException e) {
            exception.addError(idProceso + " No es un valor numérico válido.");
        }

        String idFaseProceso = request.getParameter(CFaseProceso.FASE_PROCESO_ID);
        if ((idFaseProceso == null) || (idFaseProceso.trim().length() == 0) || idFaseProceso.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar una Fase.");
        } else {
            session.setAttribute(CFaseProceso.FASE_PROCESO_ID, idFaseProceso);
        }

        String idRespuesta = request.getParameter(CValidacionNota.VALIDACION_NOTA_RESPUESTA);
        if ((idFaseProceso == null) || (idFaseProceso.trim().length() == 0)) {
            exception.addError("Debe Digitar una Respuesta.");
        } else {
            session.setAttribute(CValidacionNota.VALIDACION_NOTA_RESPUESTA, idRespuesta);
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        ValidacionNota dato = new ValidacionNota();
        dato.setIdFase(idFaseProceso);
        dato.setIdProceso(idPrc);
        dato.setIdRespuesta(idRespuesta);

        return dato;
    }

    // --------------------------------------------------------------------------------------------------
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de consulta de
     * usuarios por círculo
     *
     * @param request
     * @return evento <code>EvnAdministracionFenrir</code> con la información
     * del circulo.
     * @throws AccionWebException
     */
    private EvnReportes consultarUsuariosPorCirculoRel02(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();

        ValidacionConsultaUsuariosException exception = new ValidacionConsultaUsuariosException();

        String idCirculo = request.getParameter(CCirculo.ID_CIRCULO);
        if ((idCirculo == null) || (idCirculo.trim().length() == 0) || idCirculo.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un Circulo.");
        } else {
            session.setAttribute(CCirculo.ID_CIRCULO, idCirculo);
        }

        if (!exception.getErrores().isEmpty()) {
            throw exception;
        }
        session.setAttribute(CCirculo.ID_CIRCULO, idCirculo);

        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Circulo circulo = new Circulo();
        circulo.setIdCirculo(idCirculo);

        EvnReportes evento
                = new EvnReportes(usuario, EvnReportes.USUARIOS_CONSULTAR_POR_CIRCULO_REL02);
        evento.setCirculo(circulo);

        return evento;
    }
    // --------------------------------------------------------------------------------------------------

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de consulta de
     * usuarios
     *
     * @param request
     * @return evento <code>EvnAdministracionFenrir</code> con la información
     * del nombre de usuario a consultar a crear.
     * @throws AccionWebException
     */
    private EvnReportes consultarUsuariosRel02(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();
        String nombre = request.getParameter(CUsuario.NOMBRE_USUARIO);
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        EvnReportes evento
                = new EvnReportes(usuario, EvnReportes.CONSULTAR_USUARIOS);
        if ((nombre != null) && !nombre.trim().equals("")) {
            evento.setNombreUsuario(nombre);
        }
        return evento;
    }

    // --------------------------------------------------------------------------------------------------
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de consulta de
     * usuarios por círculo y rol
     *
     * @param request
     * @return evento <code>EvnAdministracionFenrir</code> con la información
     * del circulo.
     * @throws AccionWebException
     */
    private EvnReportes consultarUsuariosPorCirculoRol(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();

        ValidacionConsultaUsuariosException exception = new ValidacionConsultaUsuariosException();

        String idCirculo = request.getParameter(CCirculo.ID_CIRCULO);
        if ((idCirculo == null) || (idCirculo.trim().length() == 0) || idCirculo.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un Circulo.");
        } else {
            session.setAttribute(CCirculo.ID_CIRCULO, idCirculo);
        }

        if (!exception.getErrores().isEmpty()) {
            throw exception;
        }
        session.setAttribute(CCirculo.ID_CIRCULO, idCirculo);

        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Circulo circulo = new Circulo();
        circulo.setIdCirculo(idCirculo);
        Rol rol = new Rol();
        rol.setRolId(CRol.ROL_ABOGADO);

        EvnReportes evento
                = new EvnReportes(usuario, EvnReportes.USUARIOS_CONSULTAR_POR_CIRCULO_ROL);
        evento.setCirculo(circulo);
        evento.setRol(rol);
        return evento;
    }

    // --------------------------------------------------------------------------------------------------
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de consulta de
     * usuarios por círculo y rol
     *
     * @param request
     * @return evento <code>EvnAdministracionFenrir</code> con la información
     * del circulo.
     * @throws AccionWebException
     */
    private EvnReportes consultarUsuariosCajeroPorCirculo(HttpServletRequest request, String param_pcirculonombre,
            String param_pfechaini, String eventoConsulta) throws AccionWebException {
        HttpSession session = request.getSession();
        ValidacionParametrosException exception = new ValidacionParametrosException();
        String idCirculo = request.getParameter(param_pcirculonombre);
        String fecha = request.getParameter(param_pfechaini);
        if (fecha != null && fecha.length() > 0) {
            session.setAttribute(param_pfechaini, fecha);
        }
        if ((idCirculo == null) || (idCirculo.trim().length() == 0) || idCirculo.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un Circulo.");
        } else {
            session.setAttribute(param_pcirculonombre, idCirculo);
        }
        if (!exception.getErrores().isEmpty()) {
            throw exception;
        }
        session.setAttribute(CCirculo.ID_CIRCULO, idCirculo);
        session.setAttribute("consultaUsuariosCajeros", "true");
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Circulo circulo = new Circulo();
        circulo.setIdCirculo(idCirculo);
        Rol rol = new Rol();
        rol.setRolId(CRol.SIR_ROL_CAJERO_CONSULTAS);
        EvnReportes evento = new EvnReportes(usuario, eventoConsulta);
        evento.setCirculo(circulo);
        evento.setRol(rol);
        if (eventoConsulta.equals(EvnReportes.USUARIOS_CONSULTAR_POR_CIRCULO_ROLES)) {
            List roles = new ArrayList();
            roles.add(CRol.SIR_ROL_CAJERO);
            roles.add(CRol.SIR_ROL_CAJERO_CONSULTAS);
            roles.add(CRol.SIR_ROL_CAJERO_CERTIFICADOS);
            roles.add(CRol.SIR_ROL_CAJERO_CERTIFICADOS_SIR);
            roles.add(CRol.SIR_ROL_CAJERO_CERT_MASIVOS);
            roles.add(CRol.SIR_ROL_CAJERO_REGISTRO);
            evento.setRoles(roles);
            evento.setRol(null);
        } else if (eventoConsulta.equals(EvnReportes.USUARIOS_CONSULTAR_POR_CIRCULO_ROL_CALIFICADOR)) {
            List roles = new ArrayList();
            roles.add(CRol.ROL_ABOGADO);
            evento.setRoles(roles);
            evento.setRol(null);
        }
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de consulta de
     * usuarios
     *
     * @param request
     * @return evento <code>EvnAdministracionFenrir</code> con la información
     * del nombre de usuario a consultar a crear.
     * @throws AccionWebException
     */
    private EvnReportes consultarUsuarios(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();
        String nombre = request.getParameter(CUsuario.NOMBRE_USUARIO);
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        EvnReportes evento
                = new EvnReportes(usuario, EvnReportes.CONSULTAR_USUARIOS);
        if ((nombre != null) && !nombre.trim().equals("")) {
            evento.setNombreUsuario(nombre);
        }
        return evento;
    }

    // --------------------------------------------------------------------------------------------------
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de consulta de las
     * fases relacionadas con un proceso
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * proceso
     * @throws AccionWebException
     */
    private EvnAdministracionHermod consultaFaseProceso(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();

        ValidacionValidaNotaException exception = new ValidacionValidaNotaException();

        String idProceso = request.getParameter(CProceso.PROCESO_ID);
        if ((idProceso == null) || (idProceso.trim().length() == 0) || idProceso.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un Proceso.");
        } else {
            session.setAttribute(CProceso.PROCESO_ID, idProceso);
        }

        long idPrc = 0;
        try {
            idPrc = Long.parseLong(idProceso);
        } catch (NumberFormatException e) {
            exception.addError(idProceso + " No es un valor numérico válido.");
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        Proceso dato = new Proceso();
        dato.setIdProceso(idPrc);

        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.CONSULTA_FASE_PROCESO);
        evento.setProceso(dato);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de consulta de las
     * fases relacionadas con un proceso
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * proceso
     * @throws AccionWebException
     */
    /*	private EvnAdministracionHermod consultaFaseProcesoVisible (HttpServletRequest request)
    throws AccionWebException {

    HttpSession session = request.getSession();

    ValidacionValidaNotaException exception = new ValidacionValidaNotaException();

    String idProceso = request.getParameter(CProceso.PROCESO_ID);
    if ((idProceso == null)
    || (idProceso.trim().length() == 0)
    || idProceso.equals(WebKeys.SIN_SELECCIONAR)) {
    exception.addError("Debe Seleccionar un Proceso.");
    } else {
    session.setAttribute(CProceso.PROCESO_ID, idProceso);
    }

    long idPrc = 0;
    try {
    idPrc = Long.parseLong(idProceso);
    } catch (NumberFormatException e) {
    exception.addError(idProceso + " No es un valor numérico válido.");
    }

    if (exception.getErrores().size() > 0) {
    throw exception;
    }

    Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

    Proceso dato = new Proceso();
    dato.setIdProceso(idPrc);

    EvnAdministracionHermod evento =
    new EvnAdministracionHermod(usuario, EvnAdministracionHermod.CONSULTA_FASE_PROCESO_VISIBLE);
    evento.setProceso(dato);
    return evento;
    }

     */
    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de creación de una
     * validación nota
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos de la
     * validación nota
     * @throws AccionWebException
     */
    private EvnAdministracionHermod adicionaValidacionNota(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();

        ValidacionNota dato = getValidacionNota(request);
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ADICIONA_VALIDACION_NOTA);
        evento.setValidacionNota(dato);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento para la adición de
     * un tipo de oficina origen
     *
     * @param request
     * @return evento <code>EvnAdministracionForseti</code> con la información
     * del tipo de oficina origen
     * @throws AccionWebException
     */
    private EvnAdministracionForseti adicionaOficinaOrigen(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();
        salvarDatosOficinaOrigenEnSesion(request);

        ValidacionOficinaOrigenException exception = new ValidacionOficinaOrigenException();

        String nombre = (String) session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_NOMBRE);
        if (nombre == null) {
            exception.addError("Debe Proporcionar un Nombre para la oficina.");
        }

        String numero = (String) session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_NUMERO);
        if (nombre == null) {
            exception.addError("Debe Proporcionar un Número para la oficina.");
        }

        String exento
                = (String) session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_EXENTO_DERECHO_NOTARIAL);

        String descripcion
                = (String) session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_DESCRIPCION_NAT_JURIDICA);

        String telefono = (String) session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_TELEFONO);

        String direccion = (String) session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_DIRECCION);

        String fax = (String) session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_FAX);

        String email = (String) session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_EMAIL);

        String funcionario = (String) session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_FUNCIONARIO);

        String idTipoOficina = (String) session.getAttribute(CTipoOficina.ID_TIPO_OFICINA);
        if ((idTipoOficina == null) || (idTipoOficina.trim().length() == 0) || idTipoOficina.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un Tipo de oficina.");
        } else {
            session.setAttribute(CTipoOficina.ID_TIPO_OFICINA, idTipoOficina);
        }

        List listaCategorias = new ArrayList();
        if (idTipoOficina.equals(CTipoOficina.TIPO_OFICINA_NOTARIA)) {
            //TODO: VALIDACION DATOS OBLIGATORIOS OFICINA
            String[] categorias = request.getParameterValues(CCategoria.ID_CATEGORIA);
            if (categorias != null) {
                for (int i = 0; i < categorias.length; i++) {
                    Categoria categoria = new Categoria();
                    categoria.setIdCategoria(categorias[i]);
                    listaCategorias.add(categoria);
                }
            }
        }

        String idDepto = request.getParameter(CDepartamento.ID_DEPARTAMENTO);
        if ((idDepto == null) || (idDepto.trim().length() == 0) || idDepto.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un Departamento.");
        } else {
            session.setAttribute(CDepartamento.ID_DEPARTAMENTO, idDepto);
        }

        String idMunicipio = request.getParameter(CMunicipio.ID_MUNICIPIO);
        if ((idMunicipio == null) || (idMunicipio.trim().length() == 0) || idMunicipio.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un Municipio.");
        } else {
            session.setAttribute(CMunicipio.ID_MUNICIPIO, idMunicipio);
        }

        String idVereda = request.getParameter(CVereda.ID_VEREDA);
        if ((idVereda == null) || (idVereda.trim().length() == 0) || idVereda.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar una Vereda.");
        } else {
            session.setAttribute(CVereda.ID_VEREDA, idVereda);
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        Departamento deptoSeleccionado
                = (Departamento) session.getAttribute(AWReportes.DEPARTAMENTO_SELECCIONADO);
        Municipio munSeleccionado
                = (Municipio) session.getAttribute(AWReportes.MUNICIPIO_SELECCIONADO);

        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        Vereda vereda = new Vereda();
        vereda.setIdDepartamento(deptoSeleccionado.getIdDepartamento());
        vereda.setIdMunicipio(munSeleccionado.getIdMunicipio());
        vereda.setIdVereda(idVereda);

        TipoOficina tipoOficina = new TipoOficina();
        tipoOficina.setIdTipoOficina(idTipoOficina);

        OficinaOrigen dato = new OficinaOrigen();
        dato.setNombre(nombre);
        dato.setNumero(numero);
        dato.setExentoDerechoNotarial((exento == null) ? false : true);
        dato.setNaturalezaJuridicaEntidad(descripcion);
        dato.setTipoOficina(tipoOficina);
        dato.setVereda(vereda);
        dato.setTelefono(telefono);
        dato.setDireccion(direccion);
        dato.setFax(fax);
        dato.setEncargado(funcionario);
        dato.setEmail(email);

        EvnAdministracionForseti evento
                = new EvnAdministracionForseti(usuario, EvnAdministracionForseti.ADICIONA_OFICINA_ORIGEN);
        evento.setOficinaOrigen(dato);
        evento.setVereda(vereda);
        evento.setCategorias(listaCategorias);
        return evento;

    }

    /**
     * Metodo para salvar los datos de una oficina origen en la
     * <code>HttpSession</code> del usuario
     *
     * @param request
     */
    private void salvarDatosOficinaOrigenEnSesion(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String nombre = request.getParameter(COficinaOrigen.OFICINA_ORIGEN_NOMBRE);
        if (nombre != null && !nombre.trim().equals("")) {
            session.setAttribute(COficinaOrigen.OFICINA_ORIGEN_NOMBRE, nombre);
        }

        String numero = request.getParameter(COficinaOrigen.OFICINA_ORIGEN_NUMERO);
        if (numero != null && !numero.trim().equals("")) {
            session.setAttribute(COficinaOrigen.OFICINA_ORIGEN_NUMERO, numero);
        }

        String exento = request.getParameter(COficinaOrigen.OFICINA_ORIGEN_EXENTO_DERECHO_NOTARIAL);
        if (exento != null && !exento.trim().equals("")) {
            session.setAttribute(COficinaOrigen.OFICINA_ORIGEN_EXENTO_DERECHO_NOTARIAL, exento);
        }

        String descripcionNatJuridica
                = request.getParameter(COficinaOrigen.OFICINA_ORIGEN_DESCRIPCION_NAT_JURIDICA);
        if (descripcionNatJuridica != null && !descripcionNatJuridica.trim().equals("")) {
            session.setAttribute(
                    COficinaOrigen.OFICINA_ORIGEN_DESCRIPCION_NAT_JURIDICA,
                    descripcionNatJuridica);
        }

        String telefono = request.getParameter(COficinaOrigen.OFICINA_ORIGEN_TELEFONO);
        if (telefono != null && !telefono.trim().equals("")) {
            session.setAttribute(COficinaOrigen.OFICINA_ORIGEN_TELEFONO, telefono);
        }

        String direccion = request.getParameter(COficinaOrigen.OFICINA_ORIGEN_DIRECCION);
        if (direccion != null && !direccion.trim().equals("")) {
            session.setAttribute(COficinaOrigen.OFICINA_ORIGEN_DIRECCION, direccion);
        }

        String fax = request.getParameter(COficinaOrigen.OFICINA_ORIGEN_FAX);
        if (fax != null && !fax.trim().equals("")) {
            session.setAttribute(COficinaOrigen.OFICINA_ORIGEN_FAX, fax);
        }

        String email = request.getParameter(COficinaOrigen.OFICINA_ORIGEN_EMAIL);
        if (email != null && !email.trim().equals("")) {
            session.setAttribute(COficinaOrigen.OFICINA_ORIGEN_EMAIL, email);
        }

        String funcionario = request.getParameter(COficinaOrigen.OFICINA_ORIGEN_FUNCIONARIO);
        if (funcionario != null && !funcionario.trim().equals("")) {
            session.setAttribute(COficinaOrigen.OFICINA_ORIGEN_FUNCIONARIO, funcionario);
        }

        String idTipoOficina = request.getParameter(CTipoOficina.ID_TIPO_OFICINA);
        if (idTipoOficina != null && !idTipoOficina.trim().equals("")) {
            session.setAttribute(CTipoOficina.ID_TIPO_OFICINA, idTipoOficina);
        }

    }

    //salvarDatosOficinaOrigenEnSesion
    private void salvarDatosReporte30EnSesion(HttpServletRequest request) {

        HttpSession session = request.getSession();
        String numReparto = request.getParameter(REPORTE_30__PARAM_PNUMREPARTO);
        if (numReparto != null && !numReparto.trim().equals("")) {
            session.setAttribute(REPORTE_30__PARAM_PNUMREPARTO, numReparto);
        }

        String categoria = request.getParameter(CCategoria.ID_CATEGORIA);
        if (categoria != null && !categoria.trim().equals("")) {
            session.setAttribute(CCategoria.ID_CATEGORIA, categoria);
        }

        String notaria = request.getParameter(COficinaOrigen.OFICINA_ID_OFICINA_ORIGEN);
        if (notaria != null && !notaria.trim().equals("")) {
            session.setAttribute(COficinaOrigen.OFICINA_ID_OFICINA_ORIGEN, notaria);
        }

    }

    //AHERRENO 23/09/2009
    //Reporte 161
    //salvarDatos
    private void salvarDatosReporte161EnSesion(HttpServletRequest request) {

        HttpSession session = request.getSession();
        String FechaInicial = request.getParameter(REPORTE_161__PARAM_PFECHAINI);
        if (FechaInicial != null && !FechaInicial.trim().equals("")) {
            session.setAttribute(REPORTE_161__PARAM_PFECHAINI, FechaInicial);
        }

        String FechaFinal = request.getParameter(REPORTE_161__PARAM_PFECHAFIN);
        if (FechaFinal != null && !FechaFinal.trim().equals("")) {
            session.setAttribute(REPORTE_161__PARAM_PFECHAFIN, FechaFinal);
        }

        String circulo = request.getParameter(REPORTE_161__PARAM_PCIRCULONOMBRE);
        if (circulo != null && !circulo.trim().equals("")) {
            session.setAttribute(REPORTE_161__PARAM_PCIRCULONOMBRE, circulo);
        }
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento para la adición de
     * una zona registral por departametno
     *
     * @param request
     * @return evento <code>EvnAdministracionForseti</code>
     * @throws AccionWebException
     */
    private EvnAdministracionForseti seleccionaZonaRegistralDepto(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();

        ValidacionZonaRegistralException exception = new ValidacionZonaRegistralException();

        String id = request.getParameter(CZonaRegistral.ID_ZONA_REGISTRAL);
        if ((id != null) && (id.trim().length() != 0)) {
            session.setAttribute(CZonaRegistral.ID_ZONA_REGISTRAL, id);
        }

        String idDepto = request.getParameter(CDepartamento.ID_DEPARTAMENTO);
        if ((idDepto == null) || (idDepto.trim().length() == 0) || idDepto.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un Departamento.");
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        seleccionaDepartamento(request);
        Departamento depto
                = (Departamento) session.getAttribute(AWReportes.DEPARTAMENTO_SELECCIONADO);

        Map mapMunicipios = (Map) session.getAttribute(AWReportes.MAP_MUNICIPIOS);
        List listaMunicipios = new ArrayList();
        for (Iterator itMun = mapMunicipios.keySet().iterator(); itMun.hasNext();) {
            String key = (String) itMun.next();
            Municipio municipio = (Municipio) mapMunicipios.get(key);
            listaMunicipios.add(new ElementoLista(key, municipio.getNombre()));
        }

        session.setAttribute(AWReportes.LISTA_MUNICIPIOS, listaMunicipios);
        session.setAttribute(CDepartamento.ID_DEPARTAMENTO, idDepto);
        session.removeAttribute(AWReportes.LISTA_VEREDAS);
        session.removeAttribute(CMunicipio.ID_MUNICIPIO);

        return null;
    }

    /**
     * AHERRENO 07/10/2010 Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento para la selección
     * del departamento del circulo seleccionado
     *
     * @param request
     * @return evento <code>EvnAdministracionForseti</code> con la información
     * del departamento
     * @throws AccionWebException
     */
    private EvnAdministracionForseti seleccionaDepartamento_161(HttpServletRequest request)
            throws AccionWebException, ServicioNoEncontradoException, Throwable {

        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();

        Vereda idVereda = null;
        String idMunicipio = null;
        String idDepartamento = null;
        Map mapMunicipios = Collections.synchronizedMap(new TreeMap());

        String circuloCap = request.getParameter(AWReportes.REPORTE_161__PARAM_PCIRCULONOMBRE);
        List zonaR = null;

        if ((circuloCap == null) || (circuloCap.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Código de Circulo");
        }

        service = ServiceLocator.getInstancia();
        try {
            forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");
            if (forseti == null) {
                throw new ServicioNoEncontradoException("Servicio forseti no encontrado");
            }
            CirculoPk circuloPk = new CirculoPk();
            circuloPk.idCirculo = circuloCap;
            zonaR = forseti.getZonaRegistralesCirculo(circuloPk);

            for (Iterator itZona = zonaR.iterator(); itZona.hasNext();) {
                ZonaRegistral zonaRegis = (ZonaRegistral) itZona.next();
                idVereda = zonaRegis.getVereda();
                idMunicipio = idVereda.getIdMunicipio();
                idDepartamento = idVereda.getIdDepartamento();
                Map map = (Map) context.getAttribute(WebKeys.MAPA_DEPARTAMENTOS);
                Departamento depto = null;
                List listaMunDep = new ArrayList();

                for (Iterator itDep = map.keySet().iterator(); itDep.hasNext();) {
                    String key = (String) itDep.next();
                    depto = (Departamento) map.get(key);
                    if (depto.getIdDepartamento().equals(idDepartamento)) {
                        listaMunDep = depto.getMunicipios();
                        break;
                    }
                }

                for (Iterator itMun = listaMunDep.iterator(); itMun.hasNext();) {
                    Municipio municipio = (Municipio) itMun.next();
                    String llave = municipio.getNombre() + "-" + municipio.getIdMunicipio();
                    if (municipio.getIdMunicipio().equals(idMunicipio) && municipio.getIdDepartamento().equals(idDepartamento)) {
                        mapMunicipios.put(llave, municipio);
                        break;
                    }

                }

            }
        } catch (ServiceLocatorException e) {
            throw new ServicioNoEncontradoException("Error instanciando el servicio forseti");
        }

        session.setAttribute(AWReportes.MAP_MUNICIPIOS, mapMunicipios);

        List listaMunicipios = new ArrayList();
        for (Iterator itMun = mapMunicipios.keySet().iterator(); itMun.hasNext();) {
            String key = (String) itMun.next();
            Municipio municipio = (Municipio) mapMunicipios.get(key);
            listaMunicipios.add(new ElementoLista(key, municipio.getNombre()));
        }

        if (listaMunicipios.size() > 0) {
            listaMunicipios.add(new ElementoLista("TODOS", " TODOS"));
        }

        session.setAttribute(AWReportes.LISTA_MUNICIPIOS, listaMunicipios);
        session.removeAttribute(AWReportes.LISTA_VEREDAS);
        session.removeAttribute(CMunicipio.ID_MUNICIPIO);
        return null;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento para la selección
     * de un departamento
     *
     * @param request
     * @return evento <code>EvnAdministracionForseti</code> con la información
     * del departamento
     * @throws AccionWebException
     */
    private EvnAdministracionForseti seleccionaDepartamento(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();

        String key = request.getParameter(CDepartamento.ID_DEPARTAMENTO);
        if ((key == null) || (key.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Código de Departamento.");
        }
        Map map = (Map) context.getAttribute(WebKeys.MAPA_DEPARTAMENTOS);
        Departamento depto = (Departamento) map.get(key);
        seleccionarDepartamentoEnSesion(depto, session);
        return null;
    }

    /**
     * Este método se encarga de seleccionar un departamento en la
     * <code>HttpSession</code>
     *
     * @param request
     * @throws AccionWebException
     */
    private void seleccionarDepartamentoEnSesion(Departamento depto, HttpSession session) {
        List municipios = depto.getMunicipios();
        //		  Organizar alfabeticamente
        Map mapMunicipios = Collections.synchronizedMap(new TreeMap());
        for (Iterator itMun = municipios.iterator(); itMun.hasNext();) {
            Municipio municipio = (Municipio) itMun.next();
            String llave = municipio.getNombre() + "-" + municipio.getIdMunicipio();
            mapMunicipios.put(llave, municipio);
        }
        session.setAttribute(AWReportes.DEPARTAMENTO_SELECCIONADO, depto);
        session.setAttribute(AWReportes.MAP_MUNICIPIOS, mapMunicipios);
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento para la adición de
     * una zona registral por municipio
     *
     * @param request
     * @return evento <code>EvnAdministracionForseti</code>
     * @throws AccionWebException
     */
    private EvnAdministracionForseti seleccionaZonaRegistralMunicipio(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();

        session.removeAttribute(AWReportes.LISTA_VEREDAS);
        ValidacionZonaRegistralException exception = new ValidacionZonaRegistralException();

        String id = request.getParameter(CZonaRegistral.ID_ZONA_REGISTRAL);
        if ((id != null) && (id.trim().length() != 0)) {
            session.setAttribute(CZonaRegistral.ID_ZONA_REGISTRAL, id);
        }

        String idMunicipio = request.getParameter(CMunicipio.ID_MUNICIPIO);
        if ((idMunicipio == null) || (idMunicipio.trim().length() == 0) || idMunicipio.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un Municipio.");
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        if (!idMunicipio.equals("TODOS")) {
            seleccionaMunicipio(request);

            Municipio municipio
                    = (Municipio) session.getAttribute(AWReportes.MUNICIPIO_SELECCIONADO);
            List veredas = municipio.getVeredas();
            List listaVeredas = new ArrayList();
            for (Iterator itMun = veredas.iterator(); itMun.hasNext();) {
                Vereda vereda = (Vereda) itMun.next();
                listaVeredas.add(new ElementoLista(vereda.getIdVereda(), vereda.getNombre()));
            }

            session.setAttribute(AWReportes.LISTA_VEREDAS, listaVeredas);
            session.removeAttribute(CVereda.ID_VEREDA);
        } else {
            session.setAttribute(CVereda.ID_VEREDA, "TODOS");
        }

        session.setAttribute(CMunicipio.ID_MUNICIPIO, idMunicipio);

        return null;
    }

    /**
     * AHERRENO 23/09/2009 Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento para la adición de
     * una vereda
     *
     * @param request
     * @return evento <code>EvnAdministracionForseti</code>
     * @throws AccionWebException
     *
     */
    private EvnAdministracionForseti seleccionaZonaRegistralVereda_161(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();
        salvarDatosOficinaOrigenEnSesion(request);
        ValidacionOficinaOrigenException exception = new ValidacionOficinaOrigenException();

        String idMunicipio = request.getParameter(CMunicipio.ID_MUNICIPIO);
        if ((idMunicipio == null) || (idMunicipio.trim().length() == 0) || idMunicipio.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un Municipio.");
        } else {
            session.setAttribute(CMunicipio.ID_MUNICIPIO, idMunicipio);
        }

        String idVereda = request.getParameter(CVereda.ID_VEREDA);
        if ((idVereda == null) || (idVereda.trim().length() == 0) || idVereda.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("No se encontró la cabecera municipal");
        } else {
            session.setAttribute(CVereda.ID_VEREDA, idVereda);
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        //session.setAttribute(CVereda.ID_VEREDA, idVereda);
        //Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);        
        return null;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento para la selección
     * de un Municipio
     *
     * @param request
     * @return evento <code>EvnAdministracionForseti</code>
     * @throws AccionWebException
     */
    private EvnAdministracionForseti seleccionaMunicipio(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();

        String key = request.getParameter(CMunicipio.ID_MUNICIPIO);
        if ((key == null) || (key.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe seleccionar un municipio.");
        }

        Map municipios = (Map) session.getAttribute(AWReportes.MAP_MUNICIPIOS);
        Municipio municipio = (Municipio) municipios.get(key);

        if (municipio == null) {
            throw new AccionInvalidaException("Debe seleccionar un municipio.");
        }

        seleccionarMunicipioEnSesion(municipio, session);
        return null;
    }

    /**
     * Este método se encarga de seleccionar un municipio en la
     * <code>HttpSession</code>
     *
     * @param municipio
     * @param session
     * @throws AccionWebException
     */
    private void seleccionarMunicipioEnSesion(Municipio municipio, HttpSession session) {
        List veredas = municipio.getVeredas();
        //		  Organizar alfabeticamente
        Map mapVeredas = Collections.synchronizedMap(new TreeMap());
        for (Iterator iter = veredas.iterator(); iter.hasNext();) {
            Vereda vereda = (Vereda) iter.next();
            String llave = vereda.getNombre() + "-" + vereda.getIdVereda();
            mapVeredas.put(llave, vereda);
        }
        session.setAttribute(AWReportes.MUNICIPIO_SELECCIONADO, municipio);
        session.setAttribute(AWReportes.MAP_VEREDAS, mapVeredas);
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento para la consulta
     * oficinas origen por vereda
     *
     * @param request
     * @return evento <code>EvnAdministracionForseti</code> con la información
     * de la vereda
     * @throws AccionWebException
     */
    private EvnAdministracionForseti consultaOficinaByVereda(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();
        salvarDatosOficinaOrigenEnSesion(request);
        ValidacionOficinaOrigenException exception = new ValidacionOficinaOrigenException();

        String idDepto = request.getParameter(CDepartamento.ID_DEPARTAMENTO);
        if ((idDepto == null) || (idDepto.trim().length() == 0) || idDepto.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un Departamento.");
        } else {
            session.setAttribute(CDepartamento.ID_DEPARTAMENTO, idDepto);
        }

        String idMunicipio = request.getParameter(CMunicipio.ID_MUNICIPIO);
        if ((idMunicipio == null) || (idMunicipio.trim().length() == 0) || idMunicipio.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un Municipio.");
        } else {
            session.setAttribute(CMunicipio.ID_MUNICIPIO, idMunicipio);
        }

        Map municipios = (Map) session.getAttribute(AWAdministracionForseti.MAP_MUNICIPIOS);
        Municipio municipio = (Municipio) municipios.get(idMunicipio);

        List veredas = municipio.getVeredas();
        Iterator itVeredas = veredas.iterator();
        String idVereda = null;
        while (itVeredas.hasNext()) {
            Vereda vereda = (Vereda) itVeredas.next();
            if (vereda.isCabecera()) {
                idVereda = vereda.getIdVereda();
            }
        }

        if ((idVereda == null) || (idVereda.trim().length() == 0) || idVereda.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("No se encontró la cabecera municipal");
        } else {
            session.setAttribute(CVereda.ID_VEREDA, idVereda);
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        String idTipo = (String) session.getAttribute(CTipoOficina.ID_TIPO_OFICINA);
        session.setAttribute(CTipoOficina.ID_TIPO_OFICINA, idTipo);

        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        Departamento deptoSeleccionado
                = (Departamento) session.getAttribute(AWAdministracionForseti.DEPARTAMENTO_SELECCIONADO);

        Vereda vereda = new Vereda();
        vereda.setIdDepartamento(deptoSeleccionado.getIdDepartamento());
        vereda.setIdMunicipio(municipio.getIdMunicipio());
        vereda.setIdVereda(idVereda);

        EvnAdministracionForseti evento
                = new EvnAdministracionForseti(usuario, EvnAdministracionForseti.CONSULTA_OFICINA_POR_VEREDA);
        evento.setVereda(vereda);
        return evento;

    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento para la consulta
     * oficinas origen por vereda
     *
     * @param request
     * @return evento <code>EvnAdministracionForseti</code> con la información
     * de la vereda
     * @throws AccionWebException
     */
    private EvnAdministracionForseti consultaOficinaOrigenByVereda(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();
        salvarDatosOficinaOrigenEnSesion(request);

        ValidacionOficinaOrigenException exception = new ValidacionOficinaOrigenException();

        String idDepto = request.getParameter(CDepartamento.ID_DEPARTAMENTO);
        if ((idDepto == null) || (idDepto.trim().length() == 0) || idDepto.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un Departamento.");
        } else {
            session.setAttribute(CDepartamento.ID_DEPARTAMENTO, idDepto);
        }

        String idMunicipio = request.getParameter(CMunicipio.ID_MUNICIPIO);
        if ((idMunicipio == null) || (idMunicipio.trim().length() == 0) || idMunicipio.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un Municipio.");
        } else {
            session.setAttribute(CMunicipio.ID_MUNICIPIO, idMunicipio);
        }

        String idVereda = request.getParameter(CVereda.ID_VEREDA);
        if ((idVereda == null) || (idVereda.trim().length() == 0) || idVereda.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar una Vereda.");
        } else {
            session.setAttribute(CVereda.ID_VEREDA, idVereda);
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        session.setAttribute(CTipoOficina.ID_TIPO_OFICINA, request.getParameter(CTipoOficina.ID_TIPO_OFICINA));

        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        Departamento deptoSeleccionado
                = (Departamento) session.getAttribute(AWReportes.DEPARTAMENTO_SELECCIONADO);
        Municipio munSeleccionado
                = (Municipio) session.getAttribute(AWReportes.MUNICIPIO_SELECCIONADO);

        Vereda vereda = new Vereda();
        vereda.setIdDepartamento(deptoSeleccionado.getIdDepartamento());
        vereda.setIdMunicipio(munSeleccionado.getIdMunicipio());
        vereda.setIdVereda(idVereda);

        EvnAdministracionForseti evento
                = new EvnAdministracionForseti(
                        usuario,
                        EvnAdministracionForseti.CONSULTA_OFICINA_ORIGEN_POR_VEREDA);
        evento.setVereda(vereda);
        return evento;

    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento para la consulta
     * oficinas origen de tipo notaria por vereda
     *
     * @param request
     * @return evento <code>EvnAdministracionForseti</code> con la información
     * de la vereda
     * @throws AccionWebException
     */
    private EvnAdministracionForseti consultaNotariasByMunicipio(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();
        salvarDatosOficinaOrigenEnSesion(request);

        ValidacionOficinaOrigenException exception = new ValidacionOficinaOrigenException();

        String idDepto = request.getParameter(CDepartamento.ID_DEPARTAMENTO);
        if ((idDepto == null) || (idDepto.trim().length() == 0) || idDepto.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un Departamento.");
        } else {
            session.setAttribute(CDepartamento.ID_DEPARTAMENTO, idDepto);
        }

        String idMunicipio = request.getParameter(CMunicipio.ID_MUNICIPIO);
        if ((idMunicipio == null) || (idMunicipio.trim().length() == 0) || idMunicipio.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un Municipio.");
        } else {
            session.setAttribute(CMunicipio.ID_MUNICIPIO, idMunicipio);
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        session.setAttribute(CTipoOficina.ID_TIPO_OFICINA, request.getParameter(CTipoOficina.ID_TIPO_OFICINA));

        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        Departamento deptoSeleccionado
                = (Departamento) session.getAttribute(AWReportes.DEPARTAMENTO_SELECCIONADO);
        Municipio munSeleccionado
                = (Municipio) session.getAttribute(AWReportes.MUNICIPIO_SELECCIONADO);

        Vereda vereda = new Vereda();
        vereda.setIdDepartamento(deptoSeleccionado.getIdDepartamento());
        vereda.setIdMunicipio(munSeleccionado.getIdMunicipio());
        vereda.setIdVereda(CVereda.CODIGO_VEREDA_CABECERA_URBANA);

        EvnAdministracionForseti evento
                = new EvnAdministracionForseti(
                        usuario,
                        EvnAdministracionForseti.CONSULTA_NOTARIAS_POR_MUNICIPIO);
        evento.setVereda(vereda);
        return evento;

    }

    /**
     */
    private Evento consultaOficinaOrigenByMunicipio(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();
        Log.getInstance().debug(AWReportes.class, "INGRESA AL METODO");
        salvarDatosOficinaOrigenEnSesion(request);

        ValidacionOficinaOrigenException exception = new ValidacionOficinaOrigenException();

        String idDepto = request.getParameter(CDepartamento.ID_DEPARTAMENTO);
        if ((idDepto == null) || (idDepto.trim().length() == 0) || idDepto.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un Departamento.");
        } else {
            session.setAttribute(CDepartamento.ID_DEPARTAMENTO, idDepto);
        }

        String idMunicipio = request.getParameter(CMunicipio.ID_MUNICIPIO);
        if ((idMunicipio == null) || (idMunicipio.trim().length() == 0) || idMunicipio.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un Municipio.");
        } else {
            session.setAttribute(CMunicipio.ID_MUNICIPIO, idMunicipio);
        }

        String key = idMunicipio;
        Map municipios = (Map) session.getAttribute(AWReportes.MAP_MUNICIPIOS);
        Log.getInstance().debug(AWReportes.class, "MUNICIPIO: " + municipios);
        Municipio municipio = (Municipio) municipios.get(key);

        if (municipio == null) {
            throw new AccionInvalidaException("Debe seleccionar un municipio.");
        }

        seleccionarMunicipioEnSesion(municipio, session);

        Log.getInstance().debug(AWReportes.class, "PASO LA VALIDACION DEL MUNICIPIO ");

        /*
        String idVereda = request.getParameter(CVereda.ID_VEREDA);
        if ((idVereda == null)
        || (idVereda.trim().length() == 0)
        || idVereda.equals(WebKeys.SIN_SELECCIONAR)) {
        exception.addError("Debe Seleccionar una Vereda.");
        } else {
        session.setAttribute(CVereda.ID_VEREDA, idVereda);
        }
         */
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        session.setAttribute(CTipoOficina.ID_TIPO_OFICINA, request.getParameter(CTipoOficina.ID_TIPO_OFICINA));

        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        Departamento deptoSeleccionado
                = (Departamento) session.getAttribute(AWReportes.DEPARTAMENTO_SELECCIONADO);
        Municipio munSeleccionado
                = (Municipio) session.getAttribute(AWReportes.MUNICIPIO_SELECCIONADO);

        /*

        Vereda vereda = new Vereda();
        vereda.setIdDepartamento(deptoSeleccionado.getIdDepartamento());
        vereda.setIdMunicipio(munSeleccionado.getIdMunicipio());
        vereda.setIdVereda(idVereda);

         */
        EvnReportes result;
        result = new EvnReportes(EvnReportes.CONSULTA_OFICINA_ORIGEN_BY_MUNICIPIO);

        Log.getInstance().debug(AWReportes.class, "MUNICIPIO SELECCIONADO: " + munSeleccionado);

        result.setMunicipioSeleccionado(munSeleccionado);
        result.setUsuarioAuriga(usuario);

        return result;

    }

    /**
     * Este método se encarga de limpiar la sesión luego que el usuario ha
     * terminado de usar las pantallas administrativas relacionadas con Forseti
     */
    private EvnAdministracionForseti limpiarSesion(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();
        session.removeAttribute(AWReportes.DEPARTAMENTO_SELECCIONADO);
        session.removeAttribute(AWReportes.LISTA_FOTOCOPIAS);
        session.removeAttribute(AWReportes.LISTA_MUNICIPIOS);
        session.removeAttribute(AWReportes.LISTA_OFICINAS_POR_VEREDA);
        session.removeAttribute(AWReportes.LISTA_VEREDAS);
        session.removeAttribute(AWReportes.MAP_MUNICIPIOS);
        session.removeAttribute(AWReportes.MUNICIPIO_SELECCIONADO);
        session.removeAttribute(AWReportes.MAP_VEREDAS);

        session.removeAttribute(CCirculo.ID_CIRCULO);
        session.removeAttribute(CCirculo.NIT_CIRCULO);
        session.removeAttribute(CCirculo.NOMBRE_CIRCULO);
        session.removeAttribute(CDepartamento.ID_DEPARTAMENTO);
        session.removeAttribute(CMunicipio.ID_MUNICIPIO);
        session.removeAttribute(CZonaRegistral.ID_ZONA_REGISTRAL);

        session.removeAttribute(CCategoria.ID_CATEGORIA);
        session.removeAttribute(COficinaOrigen.OFICINA_ID_OFICINA_ORIGEN);
        return null;
    }

    /**
     * Este método se encarga de limpiar la sesión luego que el usuario ha
     * terminado de usar las pantallas administrativas relacionadas con Hermod
     */
    private EvnAdministracionHermod limpiaSesion(HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();
        session.removeAttribute(AWReportes.LISTA_FASES_DE_PROCESO);
        session.removeAttribute(CFaseProceso.FASE_PROCESO_ID);
        session.removeAttribute(CProceso.PROCESO_ID);
        return null;
    }

    /**
     * Este método se encarga de limpiar la sesión luego que el usuario ha
     * terminado de usar el reporte 93
     */
    private EvnReportes limpiaSesionReport93(HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();
        session.removeAttribute(AWReportes.USUARIOS_CONSULTAR_POR_CIRCULO);
        session.removeAttribute(CCirculo.ID_CIRCULO);
        session.removeAttribute(CUsuario.ID_USUARIO);
        return null;
    }

    /**
     * Modificada por Angelica Herreño el dia 04/06/2009 Este método se encarga
     * de manejar el evento de respuesta proveniente de la acción de negocio.
     * Sube datos a sesión de acuerdo al tipo de respuesta proveniente en el
     * evento
     *
     */
    public void doEnd(HttpServletRequest request, EventoRespuesta evento) {

        // maneja clases de eventos
        if (evento instanceof EvnRespAdministracionHermod) {
            EvnRespAdministracionHermod respuesta2 = (EvnRespAdministracionHermod) evento;
            HttpSession session = request.getSession();
            context = session.getServletContext();

            if (respuesta2 != null) {
                String tipoEvento = respuesta2.getTipoEvento();
                if (tipoEvento.equals(EvnRespAdministracionHermod.ADICIONA_TIPO_NOTA_OK)) {
                    session.setAttribute(
                            AWReportes.LISTA_TIPOS_NOTA_POR_PROCESO,
                            respuesta2.getPayload());

                    //Eliminación de los Atributos del tipo de nota de la sesión
                    session.removeAttribute(CTipoNota.ID_TIPO_NOTA);
                    session.removeAttribute(CTipoNota.DESCRIPCION);
                    session.removeAttribute(CTipoNota.DEVOLUTIVA);
                    session.removeAttribute(CTipoNota.NOMBRE_TIPO_NOTA);
                    session.removeAttribute(CTipoNota.VISIBILIDAD_TIPO_NOTA);
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.CONSULTA_TIPO_NOTA_POR_PROCESO_OK)) {
                    //	Fases relacionadas con un proceso
                    List listaObjetos = respuesta2.getFases();
                    List elementosLista = new ArrayList();
                    for (Iterator iter = listaObjetos.iterator(); iter.hasNext();) {
                        FaseProceso dato = (FaseProceso) iter.next();
                        elementosLista.add(new ElementoLista(dato.getIdFase(), dato.getNombre()));
                    }
                    session.setAttribute(AWReportes.LISTA_FASES_DE_PROCESO, elementosLista);

                    session.setAttribute(
                            AWReportes.LISTA_TIPOS_NOTA_POR_PROCESO,
                            respuesta2.getPayload());

                } else if (tipoEvento.equals(EvnRespAdministracionHermod.CONSULTA_FASE_PROCESO_OK)) {
                    List listaObjetos = (List) respuesta2.getPayload();
                    List elementosLista = new ArrayList();
                    for (Iterator iter = listaObjetos.iterator(); iter.hasNext();) {
                        FaseProceso dato = (FaseProceso) iter.next();
                        elementosLista.add(new ElementoLista(dato.getIdFase(), dato.getNombre()));
                    }
                    session.setAttribute(AWReportes.LISTA_FASES_DE_PROCESO, elementosLista);
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.ADICIONA_VALIDACION_NOTA_OK)) {
                    context.setAttribute(WebKeys.LISTA_VALIDACIONES_NOTA, respuesta2.getPayload());
                    session.removeAttribute(CValidacionNota.VALIDACION_NOTA_RESPUESTA);
                    return;
                }
            }
        } else if (evento instanceof EvnRespAdministracionForseti) {
            EvnRespAdministracionForseti respuesta = (EvnRespAdministracionForseti) evento;

            HttpSession session = request.getSession();
            context = session.getServletContext();

            String tipoEvento = respuesta.getTipoEvento();
            Log.getInstance().debug(AWReportes.class, "RESPUESTA: " + tipoEvento);
            if (tipoEvento.equals(EvnRespAdministracionForseti.ADICIONA_DEPARTAMENTO_OK) || tipoEvento.equals(EvnRespAdministracionForseti.ADICIONA_MUNICIPIO_OK) || tipoEvento.equals(EvnRespAdministracionForseti.ELIMINA_MUNICIPIO_OK) || tipoEvento.equals(EvnRespAdministracionForseti.ADICIONA_VEREDA_OK) || tipoEvento.equals(EvnRespAdministracionForseti.ELIMINA_VEREDA_OK)) {
                Departamento depto = (Departamento) respuesta.getPayload();
                String key = depto.getNombre() + "-" + depto.getIdDepartamento();
                Map mapDeptos = (Map) context.getAttribute(WebKeys.MAPA_DEPARTAMENTOS);
                mapDeptos.put(key, depto);
                if (tipoEvento.equals(EvnRespAdministracionForseti.ADICIONA_MUNICIPIO_OK) || tipoEvento.equals(EvnRespAdministracionForseti.ELIMINA_MUNICIPIO_OK)) {
                    seleccionarDepartamentoEnSesion(depto, session);
                } else if (tipoEvento.equals(EvnRespAdministracionForseti.ADICIONA_VEREDA_OK) || tipoEvento.equals(EvnRespAdministracionForseti.ELIMINA_VEREDA_OK)) {
                    seleccionarDepartamentoEnSesion(depto, session);
                    Municipio municipioAnterior
                            = (Municipio) session.getAttribute(AWAdministracionForseti.MUNICIPIO_SELECCIONADO);
                    Map mapMunicipiosActualizados
                            = (Map) session.getAttribute(AWAdministracionForseti.MAP_MUNICIPIOS);
                    String llave
                            = municipioAnterior.getNombre() + "-" + municipioAnterior.getIdMunicipio();
                    Municipio municipioActualizado = (Municipio) mapMunicipiosActualizados.get(llave);
                    seleccionarMunicipioEnSesion(municipioActualizado, session);
                }
                return;
            } else if (tipoEvento.equals(EvnRespAdministracionForseti.ELIMINA_DEPARTAMENTO_OK)) {
                List elementos = (List) respuesta.getPayload();
                Map mapDeptos = Collections.synchronizedMap(new TreeMap());
                for (Iterator itDepto = elementos.iterator(); itDepto.hasNext();) {
                    Departamento depto = (Departamento) itDepto.next();
                    String key = depto.getNombre() + "-" + depto.getIdDepartamento();
                    mapDeptos.put(key, depto);
                }
                context.setAttribute(WebKeys.MAPA_DEPARTAMENTOS, mapDeptos);
                return;
            } else if (tipoEvento.equals(EvnRespAdministracionForseti.ADICIONA_ESTADO_FOLIO_OK) || tipoEvento.equals(EvnRespAdministracionForseti.ELIMINA_ESTADO_FOLIO_OK)) {
                List elementos = (List) respuesta.getPayload();
                context.setAttribute(WebKeys.LISTA_ESTADOS_FOLIO, elementos);
                return;
            } else if (tipoEvento.equals(EvnRespAdministracionForseti.ADICIONA_TIPO_OFICINA_OK) || tipoEvento.equals(EvnRespAdministracionForseti.ELIMINA_TIPO_OFICINA_OK)) {
                List elementos = (List) respuesta.getPayload();
                context.setAttribute(WebKeys.LISTA_TIPOS_OFICINA, elementos);
                return;
            } else if (tipoEvento.equals(EvnRespAdministracionForseti.ADICIONA_GRUPO_NAT_JURIDICA_OK) || tipoEvento.equals(EvnRespAdministracionForseti.ELIMINA_GRUPO_NAT_JURIDICA_OK)) {
                List elementos = (List) respuesta.getPayload();
                context.setAttribute(WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS, elementos);
                return;
            } else if (tipoEvento.equals(EvnRespAdministracionForseti.ADICIONA_CIRCULO_REGISTRAL_OK) || tipoEvento.equals(EvnRespAdministracionForseti.ELIMINA_CIRCULO_REGISTRAL_OK)) {
                List elementos = (List) respuesta.getPayload();
                context.setAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES, elementos);
                return;
            } else if (tipoEvento.equals(EvnRespAdministracionForseti.EDITA_CIRCULO_NIT_OK)) {
                List elementos = (List) respuesta.getPayload();
                context.setAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES, elementos);
                session.removeAttribute(CCirculo.ID_CIRCULO);
                session.removeAttribute(CCirculo.NIT_CIRCULO);
                session.removeAttribute(CCirculo.NOMBRE_CIRCULO);
                return;
            } else if (tipoEvento.equals(EvnRespAdministracionForseti.EDITA_CIRCULO_OK)) {
                List elementos = (List) respuesta.getPayload();
                context.setAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES, elementos);
                session.removeAttribute(CCirculo.ID_CIRCULO);
                session.removeAttribute(CCirculo.NIT_CIRCULO);
                session.removeAttribute(CCirculo.NOMBRE_CIRCULO);
                session.removeAttribute(CCirculo.IMPUESTO_CIRCULO);
                return;
            } else if (tipoEvento.equals(EvnRespAdministracionForseti.ADICIONA_ESTADO_ANOTACION_OK) || tipoEvento.equals(EvnRespAdministracionForseti.ELIMINA_ESTADO_ANOTACION_OK)) {
                List elementos = (List) respuesta.getPayload();
                context.setAttribute(WebKeys.LISTA_ESTADOS_ANOTACION, elementos);
                return;
            } else if (tipoEvento.equals(EvnRespAdministracionForseti.ADICIONA_OFICINA_ORIGEN_OK) || tipoEvento.equals(EvnRespAdministracionForseti.ELIMINA_OFICINA_ORIGEN_OK) || tipoEvento.equals(
                    EvnRespAdministracionForseti.CONSULTA_OFICINA_ORIGEN_BY_VEREDA_OK)) {
                Log.getInstance().debug(AWReportes.class, "TIPO OFICINA: " + respuesta);
                List elementos = (List) respuesta.getPayload();
                String tipoOf = (String) session.getAttribute(CTipoOficina.TIPO_OFICINA_NOTARIA);
                session.setAttribute(LISTA_OFICINAS_POR_VEREDA, elementos);
                return;
            } else if (tipoEvento.equals(EvnRespAdministracionForseti.CONSULTA_OFICINA_BY_VEREDA_OK)) {
                List elementos = (List) respuesta.getPayload();
                String tipoOf = (String) session.getAttribute(CTipoOficina.ID_TIPO_OFICINA);
                Log.getInstance().debug(AWReportes.class, "TIPO OFICINA: " + tipoOf);
                if (!tipoOf.equals(WebKeys.SIN_SELECCIONAR)) {
                    Iterator itElm = elementos.iterator();
                    List nElementos = new ArrayList();
                    while (itElm.hasNext()) {
                        OficinaOrigen dato = (OficinaOrigen) itElm.next();
                        if (dato.getTipoOficina().getIdTipoOficina().equals(tipoOf)) {
                            nElementos.add(dato);
                        }
                    }
                    session.setAttribute(LISTA_OFICINAS_POR_VEREDA, nElementos);
                } else {
                    session.setAttribute(LISTA_OFICINAS_POR_VEREDA, elementos);
                }
                return;
            } else if (tipoEvento.equals(EvnRespAdministracionForseti.OFICINA_ORIGEN_EDICION)) {
                session.setAttribute(AWAdministracionForseti.OFICINA_PARA_EDITAR, respuesta.getPayload());
            }
        } else if (evento instanceof EvnRespReportes) {

            EvnRespReportes respuesta = (EvnRespReportes) evento;

            HttpSession session = request.getSession();
            context = session.getServletContext();

            if (respuesta != null) {

                String tipoEvento = respuesta.getTipoEvento();

                // handle events
                if (EvnRespReportes.CONSULTA_CIRCULOS_BY_USUARIO_EVENTRESPOK.equals(tipoEvento)) {
                    doEnd_ReporteXX_LoadCirculosByUsuario_Action(request, respuesta);
                }

                if (EvnRespReportes.DO_FINDRELATEDFASESBYRELACIONID_EVENTRESP_OK.equals(tipoEvento)) {
                    List local_ParamFasesList = respuesta.getRelatedFases();
                    session.setAttribute(AWReportes.REPORTE_REL01__PARAM_FASESLIST, local_ParamFasesList);
                }
                if (EvnRespReportes.DO_GENERATERELACIONREPORT_EVENTRESP_OK.equals(tipoEvento)) {
                    // TODO: generar el reporte
                    doEnd_ReporteRel01_Send_Action(request, respuesta);
                    do_ReporteRel01_RemoveState(request);
                }

                if (tipoEvento.equals(EvnRespReportes.CONSULTA_USUARIOS_OK) || tipoEvento.equals(EvnRespReportes.USUARIOS_CONSULTAR_POR_CIRCULO_ROL_OK)) {
                    List listaObjetos = (List) respuesta.getPayload();
                    List elementosLista = new ArrayList();

                    //AHERRENO 04/06/2009
                    //Teniendo en cuenta la observacion de Javier Rocha
                    //El elemento TODOS no se desplegara para el reporte 25 ya que
                    //es un reporte que se debe desplegar por usuario.
                    String accion = request.getParameter(WebKeys.ACCION).trim();

                    if (!accion.equals(REPORTE_25_LOADUSUARIOSCAJEROSBYCIRCULO)) {
                        if (listaObjetos.size() > 0) {
                            elementosLista.add(new ElementoLista("TODOS", " TODOS"));

                        }
                    }

                    boolean esConsultaCajeros
                            = session.getAttribute("consultaUsuariosCajeros") != null ? true : false;

                    if (accion.equals(REPORTE_E04_LOADUSUARIOSBYCIRCULO)) {
                        esConsultaCajeros = true;
                    }

                    for (Iterator iter = listaObjetos.iterator(); iter.hasNext();) {
                        gov.sir.core.negocio.modelo.Usuario dato = (gov.sir.core.negocio.modelo.Usuario) iter.next();
                        elementosLista.add(new ElementoLista(
                                esConsultaCajeros ? "" + dato.getUsername() : "" + dato.getIdUsuario(),
                                dato.getNombreCompletoUsuario()));
                    }

                    /**
                     * @autor fernando padilla req 1214
                     * @change se agrega la lista de usuarios de los circulos
                     * nodo central y ctl
                     *
                     */
                    if (accion.equals(REPORTE_25_LOADUSUARIOSCAJEROSBYCIRCULO)) {
                        try {
                            UsuarioSIR usuarioSIR = new UsuarioSIR();
                            List<co.com.iridium.generalSIR.comun.modelo.Usuario> usuarios = usuarioSIR.getUsuariosAPP();
                            for (co.com.iridium.generalSIR.comun.modelo.Usuario u : usuarios) {
                                elementosLista.add(new ElementoLista(u.getIdUsuario(), u.getNombre()));
                            }
                        } catch (GeneralSIRException ex) {
                            Logger.getLogger(AWReportes.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    session.setAttribute(AWReportes.USUARIOS_CONSULTAR_POR_CIRCULO, elementosLista);
                    return;
                }

                if (EvnRespReportes.CONSULTA_OFICINA_ORIGEN_BY_MUNICIPIO_EVENTRESPOK.equals(tipoEvento)) {
                    // TODO: cargar los datos
                    List result_OficinasOrigenByMunicipio;
                    result_OficinasOrigenByMunicipio = respuesta.getOficinasOrigenByMunicipioList();

                    session.setAttribute(LISTA_OFICINAS_POR_VEREDA, result_OficinasOrigenByMunicipio);

                } // if

                if (EvnRespReportes.VALIDAR_NUMERO_REPARTO_NOTARIAL.equals(tipoEvento)) {
                    this.doEnd_Reporte30_Send_Action(request);
                } // if

            } // if
        }
    }

    /* end-method:do-end*/
    /**
     * doEnd_ReporteXX_LoadCirculosByUsuario_Action
     *
     * @param request HttpServletRequest
     * @param respuesta EvnRespReportes
     */
    private void doEnd_ReporteXX_LoadCirculosByUsuario_Action(HttpServletRequest request, EvnRespReportes respuesta) {

        HttpSession session;
        session = request.getSession();

        // bug 3738
        // cuando el usuario tiene el rol administrador
        // nacional, deben salirle todos los circulos
        // List <org.auriga.core.modelo.transferObjects.Rol>
        List local_RolesList;
        local_RolesList = (List) session.getAttribute(WebKeys.LISTA_ROLES);

        if (null == local_RolesList) {
            local_RolesList = new ArrayList();
        } // if

        boolean isAdministradorNacional;
        isAdministradorNacional = false;
        /**
         * @author: David Panesso
         * @change: 1246.ASIGNACION.REPORTES.165.166.167.ROL.REGISTRADOR Nueva
         * variable para verificar el rol del usuario
         */
        boolean isAdministradorRegional;
        isAdministradorRegional = false;
        String accion = request.getParameter(WebKeys.ACCION).trim();
        // search
        search:
        {
            /*CRoles.ADMINISTRADOR_NACIONAL;
            final String ADMINISTRADOR_NACIONAL = "SIR_ROL_ADMINISTRADOR_NACIONAL";
            final String GESTIOM = "SIR_ROL_GESTION";
            final String CONSULTA_NACIONAL = "SIR_ROL_ADMINISTRADOR_NACIONAL";
             */
            Iterator local_RolesListIterator;
            org.auriga.core.modelo.transferObjects.Rol local_RolesListElement;
            local_RolesListIterator = local_RolesList.iterator();

            //AHERRENO 28/10/2010
            //Se modifica metodo para validación de roles
            /**
             * @author: David Panesso
             * @change: 1246.ASIGNACION.REPORTES.165.166.167.ROL.REGISTRADOR Se
             * modifica metodo para validación de roles
             */
            for (; local_RolesListIterator.hasNext();) {
                local_RolesListElement = (org.auriga.core.modelo.transferObjects.Rol) local_RolesListIterator.next();
                String rol = local_RolesListElement.getRolId();

                if (accion.equals(REPORTE_161_LOADCIRCULOSBYUSUARIO)) {
                    if (rol.equals(CRoles.ADMINISTRADOR_NACIONAL)) {
                        isAdministradorNacional = true;
                        break;
                    } //SIR_ROL_CONSULTAS_ESPECIALES                        
                } else if (accion.equals(REPORTE_165_LOADCIRCULOSBYUSUARIO) || accion.equals(REPORTE_166_LOADCIRCULOSBYUSUARIO) || accion.equals(REPORTE_167_LOADCIRCULOSBYUSUARIO)) {
                    if (rol.equals(CRoles.ADMINISTRADOR_NACIONAL) || rol.equals(CRoles.SIR_ROL_CONSULTAS_ESPECIALES)) {
                        isAdministradorNacional = true;
                        break;
                    }
                    if (rol.equals(CRoles.ADMINISTRADOR_REGIONAL) || rol.equals(CRoles.SIR_ROL_REGISTRADOR)) {
                        isAdministradorRegional = true;
                        break;
                    }
                } else if (accion.equals(REPORTE_E02_LOADCIRCULOSBYUSUARIO)) {
                    if (rol.equals(CRoles.ADMINISTRADOR_REGIONAL) || rol.equals(CRoles.SIR_ROL_REGISTRADOR)) {
                        isAdministradorRegional = true;
                        break;
                    }
                } else if (accion.equals(REPORTE_168_LOADCIRCULOSBYUSUARIO)) {
                    if (rol.equals(CRoles.ADMINISTRADOR_NACIONAL) || rol.equals(CRoles.SIR_ROL_CONSULTAS_ESPECIALES)) {
                        isAdministradorNacional = true;
                        break;
                    }
                } else if (rol.equals(CRoles.ADMINISTRADOR_NACIONAL) || rol.equals(CRoles.SIR_ROL_GESTION) || rol.equals(CRoles.CONSULTA_NACIONAL) || rol.equals(CRoles.REPORTES_NACIONAL)) {
                    isAdministradorNacional = true;
                    break;
                } // if
            } // for

        } // :search

        List param_CirculosByUsuario;

        /**
         * @author: David Panesso
         * @change: 1246.ASIGNACION.REPORTES.165.166.167.ROL.REGISTRADOR Nueva
         * validación con nueva variable
         */
        if (!isAdministradorNacional || isAdministradorRegional) {
            param_CirculosByUsuario = respuesta.getCirculosByUsuarioList();
        } else {
            // TODO: cargarlo del contexto
            ServletContext context = session.getServletContext();
            param_CirculosByUsuario = (List) context.getAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES);
        } // if

        if (null == param_CirculosByUsuario) {
            param_CirculosByUsuario = new ArrayList();
        } // end if

        Iterator param_CirculosByUsuarioIterator;
        Circulo param_CirculosByUsuarioElement;

        List param_HelperCirculoListModel;
        ElementoLista param_HelperCirculoListModelElement;

        param_HelperCirculoListModel = new ArrayList();

        param_CirculosByUsuarioIterator = param_CirculosByUsuario.iterator();

        /**
         * @autor HGOMEZ
         * @mantis 13407
         * @Requerimiento 064_453_Duplicidad_Nombre_Circulo
         * @descripcion Se instancia DepartamentosPorCirculoSingletonUtil para
         * obtener el listado de departamentos por circulo.
         */
        List listaCirculoDepartamento = new Vector();
        DepartamentosPorCirculoSingletonUtil departamentosPorCirculoSingletonUtil = DepartamentosPorCirculoSingletonUtil.getInstance();
        listaCirculoDepartamento = departamentosPorCirculoSingletonUtil.getDepartamentosPorCirculo();

        int idCirculoInt = 0;
        String nombreCirculoDepartamento = "";
        String idCirculoString = "";

        for (; param_CirculosByUsuarioIterator.hasNext();) {
            param_CirculosByUsuarioElement = (Circulo) param_CirculosByUsuarioIterator.next();
            idCirculoString = param_CirculosByUsuarioElement.getIdCirculo();
            if (departamentosPorCirculoSingletonUtil.isNumber(idCirculoString)) {
                idCirculoInt = Integer.parseInt(idCirculoString);
                nombreCirculoDepartamento = departamentosPorCirculoSingletonUtil.getNombreCirculoDepartamento(listaCirculoDepartamento, idCirculoInt);
                if (nombreCirculoDepartamento != "") {
                    param_HelperCirculoListModelElement = new ElementoLista(idCirculoString, nombreCirculoDepartamento);
                    param_HelperCirculoListModel.add(param_HelperCirculoListModelElement);
                }
            }

        }

        if (accion.equals(REPORTE_165_LOADCIRCULOSBYUSUARIO) || accion.equals(REPORTE_166_LOADCIRCULOSBYUSUARIO) || accion.equals(REPORTE_167_LOADCIRCULOSBYUSUARIO) || accion.equals(REPORTE_168_LOADCIRCULOSBYUSUARIO)) {
            if (!isAdministradorRegional) {
                param_HelperCirculoListModel.add(new ElementoLista("TODOS", " TODOS"));
            }
        }

        session.setAttribute(REPORTE_XX__ITEM_CIRCULOSBYUSUARIO, param_HelperCirculoListModel);

    } // end-method:doEnd_ReporteXX_LoadCirculosByUsuario_Action

    //GCABRERA 27/12/2011
    //Reporte E04
    private void do_ReporteE04_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_E04__PARAM_PFECHAINI);
        String param_02 = request.getParameter(REPORTE_E04__PARAM_PFECHAFIN);
        String param_03 = request.getParameter(REPORTE_E04__PARAM_IDCIRCULO);
        String param_04 = request.getParameter(REPORTE_E04__PARAM_USUARIOSNOMBRE);
        String param_05 = request.getParameter(REPORTE_E04__PARAM_USUARIOLOG);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {
            exception.addError("parametro: Fecha Inicio Radicación: Debe digitar un valor");
        } else {
            String send1 = null;
            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio Radicación: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {
            exception.addError("parametro: Fecha Fin Radicación: Debe digitar un valor");
        } else {
            String send1 = null;
            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin Radicación: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {
            exception.addError("parametro: Circulo: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        /*elora: obtiene listado de usuarios del proceso de correccione*/
        if (null == param_04 || (param_04.trim().length() == 0)) {
            exception.addError("parametro: Usuario Seleccionado: Debe seleccionar un usuario");
        } else {
            if (param_04.compareToIgnoreCase("todos") == 0) {
                String todos = "";
                List usuarios = (List) request.getSession().getAttribute(USUARIOS_CONSULTAR_POR_CIRCULO);
                for (int i = 1; i < usuarios.size(); i = i + 1) {
                    if (i > 1) {
                        todos = todos + ",";
                    }
                    ElementoLista elem = (ElementoLista) usuarios.get(i);
                    todos = todos + elem.getId();
                }
                if (todos.trim().length() > 0) {
                    param_04 = todos;
                }
            }
        }

        // apply validators
        if ((null == param_05) || ("".equalsIgnoreCase(param_05))) {
            exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_05)) {
                exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
            }
        }

        do_ReporteE03_SaveState(request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_E04__PARAM_PFECHAINI, REPORTE_E04__PARAM_PFECHAFIN, REPORTE_E04__PARAM_IDCIRCULO, REPORTE_E04__PARAM_USUARIONOMBRE, REPORTE_E04__PARAM_USUARIOLOG
        };
        String[] values = new String[]{
            REPORTE_E04__PARAM_CMDKEY, param_01, param_02, param_03, param_04, param_05
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteE04_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_ReporteE04_Back_Action(HttpServletRequest request) {
        do_ReporteE04_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);

    }

    private void do_ReporteE04_SaveState(HttpServletRequest request) {
        HttpSession session;
        String[] itemIds = new String[]{
            REPORTE_E04__PARAM_PFECHAINI, REPORTE_E04__PARAM_PFECHAFIN, REPORTE_E04__PARAM_IDCIRCULO, REPORTE_E04__PARAM_USUARIONOMBRE, REPORTE_E04__PARAM_USUARIOLOG
        };

        session = request.getSession();
        save_PageItemsState(itemIds, request, session);

    }

    private void do_ReporteE04_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_E04__PARAM_PFECHAINI, REPORTE_E04__PARAM_PFECHAFIN, REPORTE_E04__PARAM_IDCIRCULO, REPORTE_E04__PARAM_USUARIONOMBRE, REPORTE_E04__PARAM_USUARIOLOG
        };

        session = request.getSession();
        delete_PageItemsState(itemIds, request, session);

    }

    /**
     * Autor: Edgar Lora Mantis: 0011319 Requerimiento: 075_151
     */
    private void do_Reporte_170_SaveState(HttpServletRequest request) {
        HttpSession session;
        String[] itemIds = new String[]{
            REPORTE_170_PARAM_PFECHAINI,
            REPORTE_170_PARAM_PFECHAFIN,
            REPORTE_170_PARAM_IDCIRCULO,
            REPORTE_170_PARAM_USUARIOLOG
        };

        session = request.getSession();
        save_PageItemsState(itemIds, request, session);
    }

    private void do_Reporte_170_RemoveState(HttpServletRequest request) {
        HttpSession session;
        String[] itemIds = new String[]{
            REPORTE_170_PARAM_PFECHAINI,
            REPORTE_170_PARAM_PFECHAFIN,
            REPORTE_170_PARAM_IDCIRCULO,
            REPORTE_170_PARAM_USUARIOLOG
        };

        session = request.getSession();
        delete_PageItemsState(itemIds, request, session);
    }

    private void do_Reporte_170_Send_Action(HttpServletRequest request) throws ValidacionParametrosException {
        ValidacionParametrosException exception = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_170_PARAM_PFECHAINI);
        String param_02 = request.getParameter(REPORTE_170_PARAM_PFECHAFIN);
        String param_03 = request.getParameter(REPORTE_170_PARAM_IDCIRCULO);
        String param_04 = request.getParameter(REPORTE_170_PARAM_USUARIOLOG);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {
            exception.addError("parametro: Fecha Inicio Radicación: Debe digitar un valor");
        } else {
            String send1 = null;
            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio Radicación: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {
            exception.addError("parametro: Fecha Fin Radicación: Debe digitar un valor");
        } else {
            String send1 = null;
            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin Radicación: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {
            exception.addError("parametro: Circulo: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {
            exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
            }
        }

        do_Reporte_170_SaveState(request);

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY,
            REPORTE_170_PARAM_PFECHAINI,
            REPORTE_170_PARAM_PFECHAFIN,
            REPORTE_170_PARAM_IDCIRCULO,
            REPORTE_170_PARAM_USUARIOLOG
        };
        String[] values = new String[]{
            REPORTE_170_PARAM_CMDKEY,
            param_01,
            param_02,
            param_03,
            param_04
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_Reporte_170_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte_170_Back_Action(HttpServletRequest request) {
        do_Reporte_170_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    /**
     * @Autor: Santiago Vásquez
     * @Change: 1252.REPORTE TURNOS CORRECCION ENCABEZADO
     */
    private void do_Reporte_178_SaveState(HttpServletRequest request) {
        HttpSession session;
        String[] itemIds = new String[]{
            REPORTE_178__PARAM_PFECHAINI,
            REPORTE_178__PARAM_PFECHAFIN,
            REPORTE_178__PARAM_IDCIRCULO,
            REPORTE_178__PARAM_USUARIOLOG,
            REPORTE_178__PARAM_IDUSUARIO
        };

        session = request.getSession();
        save_PageItemsState(itemIds, request, session);
    }

    private void do_Reporte_178_RemoveState(HttpServletRequest request) {
        HttpSession session;
        String[] itemIds = new String[]{
            REPORTE_178__PARAM_PFECHAINI,
            REPORTE_178__PARAM_PFECHAFIN,
            REPORTE_178__PARAM_IDCIRCULO,
            REPORTE_178__PARAM_USUARIOLOG,
            REPORTE_178__PARAM_IDUSUARIO
        };

        session = request.getSession();
        delete_PageItemsState(itemIds, request, session);
    }

    private void do_Reporte_178_Send_Action(HttpServletRequest request) throws ValidacionParametrosException {
        ValidacionParametrosException exception = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_178__PARAM_PFECHAINI);
        String param_02 = request.getParameter(REPORTE_178__PARAM_PFECHAFIN);
        String param_03 = request.getParameter(REPORTE_178__PARAM_IDCIRCULO);
        String param_04 = request.getParameter(REPORTE_178__PARAM_USUARIOLOG);
        String param_05 = request.getParameter(REPORTE_178__PARAM_IDUSUARIO);

        Log.getInstance().debug(AWReportes.class, "parametro 1: " + param_01);
        Log.getInstance().debug(AWReportes.class, "parametro 2: " + param_02);
        Log.getInstance().debug(AWReportes.class, "parametro 3: " + param_03);
        Log.getInstance().debug(AWReportes.class, "parametro 4: " + param_04);
        Log.getInstance().debug(AWReportes.class, "parametro 5: " + param_05);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {
            exception.addError("parametro: Fecha Inicio Radicación: Debe digitar un valor");
        } else {
            String send1 = null;
            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio Radicación: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {
            exception.addError("parametro: Fecha Fin Radicación: Debe digitar un valor");
        } else {
            String send1 = null;
            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin Radicación: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {
            exception.addError("parametro: Circulo: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {
            exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
            }
        }

        if ((null == param_05) || ("".equalsIgnoreCase(param_05))) {
            exception.addError("parametro: Usuario: Debe seleccionar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_05)) {
                exception.addError("parametro: Usuario: Debe seleccionar un valor");
            }
        }

        do_Reporte_178_SaveState(request);

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY,
            REPORTE_178__PARAM_PFECHAINI,
            REPORTE_178__PARAM_PFECHAFIN,
            REPORTE_178__PARAM_IDCIRCULO,
            REPORTE_178__PARAM_USUARIOLOG,
            REPORTE_178__PARAM_IDUSUARIO
        };
        String[] values = new String[]{
            REPORTE_178__PARAM_CMDKEY,
            param_01,
            param_02,
            param_03,
            param_04,
            param_05
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_Reporte_178_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte_178_Back_Action(HttpServletRequest request) {
        do_Reporte_178_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    /**
     * @Autor: Santiago Vásquez
     * @Change: 1252.REPORTE TURNOS CORRECCION ENCABEZADO
     */
    private void do_Reporte_179_SaveState(HttpServletRequest request) {
        HttpSession session;
        String[] itemIds = new String[]{
            REPORTE_179__PARAM_PFECHAINI,
            REPORTE_179__PARAM_PFECHAFIN,
            REPORTE_179__PARAM_IDCIRCULO,
            REPORTE_179__PARAM_IDNATJUR,
            REPORTE_179__PARAM_IDTIPOPREDIO,
            REPORTE_179__PARAM_USUARIOLOG,
            CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID,
            CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER,
            CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM,};

        session = request.getSession();
        save_PageItemsState(itemIds, request, session);
    }

    private void do_Reporte_179_RemoveState(HttpServletRequest request) {
        HttpSession session;
        String[] itemIds = new String[]{
            REPORTE_179__PARAM_PFECHAINI,
            REPORTE_179__PARAM_PFECHAFIN,
            REPORTE_179__PARAM_IDCIRCULO,
            REPORTE_179__PARAM_IDNATJUR,
            REPORTE_179__PARAM_IDTIPOPREDIO,
            REPORTE_179__PARAM_USUARIOLOG,
            CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID,
            CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER,
            CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM,};

        session = request.getSession();
        delete_PageItemsState(itemIds, request, session);
    }

    private void do_Reporte_179_Send_Action(HttpServletRequest request) throws ValidacionParametrosException {
        ValidacionParametrosException exception = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_179__PARAM_PFECHAINI);
        String param_02 = request.getParameter(REPORTE_179__PARAM_PFECHAFIN);
        String param_03 = request.getParameter(REPORTE_179__PARAM_IDCIRCULO);
        String param_04 = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
        String param_05 = request.getParameter(REPORTE_179__PARAM_IDTIPOPREDIO);
        String param_06 = request.getParameter(REPORTE_179__PARAM_USUARIOLOG);

        Log.getInstance().debug(AWReportes.class, "parametro 1: " + param_01);
        Log.getInstance().debug(AWReportes.class, "parametro 2: " + param_02);
        Log.getInstance().debug(AWReportes.class, "parametro 3: " + param_03);
        Log.getInstance().debug(AWReportes.class, "parametro 4: " + param_04);
        Log.getInstance().debug(AWReportes.class, "parametro 5: " + param_05);
        Log.getInstance().debug(AWReportes.class, "parametro 6: " + param_06);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {
            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");
        } else {
            String send1 = null;
            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {
            exception.addError("parametro: Fecha Fin: Debe digitar un valor");
        } else {
            String send1 = null;
            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {
            exception.addError("parametro: Circulo: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {
            exception.addError("parametro: Naturaleza Jurídica: Debe ingresar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Naturaleza Jurídica: Debe ingresar un valor");
            }
        }

        // apply validators
        if ((null == param_05) || ("".equalsIgnoreCase(param_05))) {
            exception.addError("parametro: Tipo Predio: Debe ingresar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_05)) {
                exception.addError("parametro: Tipo Predio: Debe ingresar un valor");
            }
        }

        if ((null == param_06) || ("".equalsIgnoreCase(param_06))) {
            exception.addError("parametro: Usuario: Debe seleccionar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_06)) {
                exception.addError("parametro: Usuario: Debe seleccionar un valor");
            }
        }

        do_Reporte_179_SaveState(request);

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY,
            REPORTE_179__PARAM_PFECHAINI,
            REPORTE_179__PARAM_PFECHAFIN,
            REPORTE_179__PARAM_IDCIRCULO,
            REPORTE_179__PARAM_IDNATJUR,
            REPORTE_179__PARAM_IDTIPOPREDIO,
            REPORTE_179__PARAM_USUARIOLOG
        };
        String[] values = new String[]{
            REPORTE_179__PARAM_CMDKEY,
            param_01,
            param_02,
            param_03,
            param_04,
            param_05,
            param_06,};

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_Reporte_179_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte_179_Back_Action(HttpServletRequest request) {
        do_Reporte_179_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    /**
     * @param request
     * @return
     * @throws AccionWebException
     */
    private Evento cargarDescripcionNaturaleza(HttpServletRequest request) throws AccionWebException {
        ValidacionParametrosException exception = new AdminReportes_InvalidParametersExceptionCollector();
        String idNaturaleza = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
        request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID, idNaturaleza);

        for (java.util.Enumeration enumeration = request.getParameterNames(); enumeration.hasMoreElements();) {
            String key = (String) enumeration.nextElement();
            request.getSession().setAttribute(key, request.getParameter(key));
        }

        List grupoNaturalezas = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS);

        GrupoNaturalezaJuridica grupo = new GrupoNaturalezaJuridica();
        NaturalezaJuridica nat;
        boolean found = false;
        String descripcion = "";
        long version = 0;
        for (Iterator it = grupoNaturalezas.iterator(); it.hasNext() && !found;) {
            grupo = (GrupoNaturalezaJuridica) it.next();
            for (Iterator it2 = grupo.getNaturalezaJuridicas().iterator(); it2.hasNext() && !found;) {
                nat = (NaturalezaJuridica) it2.next();
                if (nat.getIdNaturalezaJuridica().equals(idNaturaleza)) {
                    descripcion = nat.getNombre();
                    version = nat.getVersion();
                    found = true;
                }
            }
        }

        if (!found) { // Sí no existe
            request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
            request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
            request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER);
            exception.addError("La naturaleza jurídica (" + idNaturaleza + ") no existe.");
            throw exception;
        } else {
            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM, descripcion);
            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER, version);
        }

        return null;
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    //CTORRES 24/04/2013
    //Reporte 175
    private void do_Reporte175_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_175__PARAM_PFECHAINI);
        String param_02 = request.getParameter(REPORTE_175__PARAM_PFECHAFIN);
        String param_03 = request.getParameter(REPORTE_175__PARAM_PCIRCULONOMBRE);
        String param_04 = request.getParameter(REPORTE_175__PARAM_USUARIOLOG);

        GregorianCalendar capturaFecha1 = null;
        GregorianCalendar capturaFecha2 = null;
        int ano1 = 0;
        int ano2 = 0;
        int mes1 = 0;
        int mes2 = 0;
        int dia1 = 0;
        int dia2 = 0;
        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;

                String[] fechas = param_01.split("-");//yyyy-MM-dd - 0-1-2
                ano1 = Integer.parseInt(fechas[0]);
                mes1 = Integer.parseInt(fechas[1]);
                dia1 = Integer.parseInt(fechas[2]);
                capturaFecha1 = new GregorianCalendar(ano1, mes1, dia1);
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;

                String[] fechas = param_02.split("-");//yyyy-MM-dd - 0-1-2
                ano2 = Integer.parseInt(fechas[0]);
                mes2 = Integer.parseInt(fechas[1]);
                dia2 = Integer.parseInt(fechas[2]);
                capturaFecha2 = new GregorianCalendar(ano2, mes2 - 1, dia2);  //Los meses van de 0 a 11
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin: La fecha es inválida" + e.getMessage());
            }
        }

        if (capturaFecha1 != null && capturaFecha2 != null) {
            if (!(mes1 == mes2 && ano1 == ano2)) {

                Calendar valFec = Calendar.getInstance();
                valFec.set(ano1, mes1 - 1, dia1, 0, 0, 0); //Los meses van de 0 a 11
                valFec.add(Calendar.DATE, 180);

                if (capturaFecha2.after(valFec)) {
                    exception.addError("El Rango de fechas no puede exceder a seis (6) meses");
                }
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {
            exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
            }
        }
        String[] itemIds = new String[]{
            REPORTE_175__PARAM_PFECHAINI, REPORTE_175__PARAM_PFECHAFIN, REPORTE_175__PARAM_PCIRCULONOMBRE, REPORTE_175__PARAM_USUARIOLOG
        };

        do_Reporte175_SaveState(request);
        // raise ------------------------------------------------

        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        // -----------------------------------------------------

        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_175__PARAM_PFECHAINI, REPORTE_175__PARAM_PFECHAFIN, REPORTE_175__PARAM_PCIRCULONOMBRE, REPORTE_175__PARAM_USUARIOLOG
        };

        String[] values = new String[]{
            REPORTE_175__PARAM_CMDKEY, param_01, param_02, param_03, param_04
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte175_Back_Action(HttpServletRequest request) {
        do_Reporte175_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    } // :do_Reporte175_Back_Action

    private void do_Reporte175_SaveState(HttpServletRequest request) {
        HttpSession session;
        String[] itemIds = new String[]{
            REPORTE_175__PARAM_PFECHAINI, REPORTE_175__PARAM_PFECHAFIN, REPORTE_175__PARAM_PCIRCULONOMBRE, REPORTE_175__PARAM_USUARIOLOG
        };

        session = request.getSession();
        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte175_SaveState

    private void do_Reporte175_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_175__PARAM_PFECHAINI, REPORTE_175__PARAM_PFECHAFIN, REPORTE_175__PARAM_PCIRCULONOMBRE, REPORTE_175__PARAM_USUARIOLOG
        };

        session = request.getSession();
        delete_PageItemsState(itemIds, request, session);

    } // :do_Reporte175_RemoveState

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    //CTORRES 24/04/2013
    //Reporte 175
    private void do_Reporte176_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_176__PARAM_PFECHAINI);
        String param_02 = request.getParameter(REPORTE_176__PARAM_PFECHAFIN);
        String param_03 = request.getParameter(REPORTE_176__PARAM_PCIRCULONOMBRE);
        String param_04 = request.getParameter(REPORTE_176__PARAM_USUARIOLOG);

        GregorianCalendar capturaFecha1 = null;
        GregorianCalendar capturaFecha2 = null;
        int ano1 = 0;
        int ano2 = 0;
        int mes1 = 0;
        int mes2 = 0;
        int dia1 = 0;
        int dia2 = 0;
        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;

                String[] fechas = param_01.split("-");//yyyy-MM-dd - 0-1-2
                ano1 = Integer.parseInt(fechas[0]);
                mes1 = Integer.parseInt(fechas[1]);
                dia1 = Integer.parseInt(fechas[2]);
                capturaFecha1 = new GregorianCalendar(ano1, mes1, dia1);
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");

        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_02);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_02 = send1;

                String[] fechas = param_02.split("-");//yyyy-MM-dd - 0-1-2
                ano2 = Integer.parseInt(fechas[0]);
                mes2 = Integer.parseInt(fechas[1]);
                dia2 = Integer.parseInt(fechas[2]);
                capturaFecha2 = new GregorianCalendar(ano2, mes2 - 1, dia2);  //Los meses van de 0 a 11
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin: La fecha es inválida" + e.getMessage());
            }
        }

        if (capturaFecha1 != null && capturaFecha2 != null) {
            if (!(mes1 == mes2 && ano1 == ano2)) {

                Calendar valFec = Calendar.getInstance();
                valFec.set(ano1, mes1 - 1, dia1, 0, 0, 0); //Los meses van de 0 a 11
                valFec.add(Calendar.DATE, 180);

                if (capturaFecha2.after(valFec)) {
                    exception.addError("El Rango de fechas no puede exceder a seis (6) meses");
                }
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Circulo: Debe digitar un valor");

        } else {

            if (WebKeys.SIN_SELECCIONAR.equals(param_03)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {
            exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
            }
        }
        String[] itemIds = new String[]{
            REPORTE_176__PARAM_PFECHAINI, REPORTE_176__PARAM_PFECHAFIN, REPORTE_176__PARAM_PCIRCULONOMBRE, REPORTE_176__PARAM_USUARIOLOG
        };

        do_Reporte176_SaveState(request);
        // raise ------------------------------------------------

        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        // -----------------------------------------------------

        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_176__PARAM_PFECHAINI, REPORTE_176__PARAM_PFECHAFIN, REPORTE_176__PARAM_PCIRCULONOMBRE, REPORTE_176__PARAM_USUARIOLOG
        };

        String[] values = new String[]{
            REPORTE_176__PARAM_CMDKEY, param_01, param_02, param_03, param_04
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }

    private void do_Reporte176_Back_Action(HttpServletRequest request) {
        do_Reporte176_RemoveState(request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    } // :do_Reporte176_Back_Action

    private void do_Reporte176_SaveState(HttpServletRequest request) {
        HttpSession session;
        String[] itemIds = new String[]{
            REPORTE_176__PARAM_PFECHAINI, REPORTE_176__PARAM_PFECHAFIN, REPORTE_176__PARAM_PCIRCULONOMBRE, REPORTE_176__PARAM_USUARIOLOG
        };

        session = request.getSession();
        save_PageItemsState(itemIds, request, session);

    } // :do_Reporte176_SaveState

    private void do_Reporte176_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            REPORTE_176__PARAM_PFECHAINI, REPORTE_176__PARAM_PFECHAFIN, REPORTE_176__PARAM_PCIRCULONOMBRE, REPORTE_176__PARAM_USUARIOLOG
        };

        session = request.getSession();
        delete_PageItemsState(itemIds, request, session);

    } // :do_Reporte176_RemoveState
    
    // -------------------------------------------------------------------------------
    /* Reporte 180 - VALOR CONSERVACION DOCUMENTAL
     * TSG DESARROLLO1 10/05/2018
     * Se agregan variables: Fecha Final y Usuario que genera el reporte
     */
    private void do_Reporte180_Send_Action(HttpServletRequest request)
            throws ValidacionParametrosException {

        ValidacionParametrosException exception
                = new AdminReportes_InvalidParametersExceptionCollector();

        String param_01 = request.getParameter(REPORTE_180__PARAM_PFECHA);
        String param_02 = request.getParameter(REPORTE_180__PARAM_PCIRCULONOMBRE);
        String param_03 = request.getParameter(REPORTE_180__PARAM_PFECHAFIN);
        String param_04 = request.getParameter(REPORTE_180__PARAM_USUARIOLOG);

        // apply validators
        if ((null == param_01) || ("".equalsIgnoreCase(param_01))) {

            exception.addError("parametro: Fecha Inicio: Debe digitar un valor");
        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_01);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_01 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Inicio: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_02) || ("".equalsIgnoreCase(param_02))) {
            exception.addError("parametro: Circulo: Debe digitar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_02)) {
                exception.addError("parametro: Circulo: Debe digitar un valor");
            }
        }

        // apply validators
        if ((null == param_03) || ("".equalsIgnoreCase(param_03))) {

            exception.addError("parametro: Fecha Fin: Debe digitar un valor");
        } else {
            String send1 = null;

            Date fecha = null;
            try {
                fecha = DateFormatUtil.parse(param_03);
                send1 = DateFormatUtil.format("yyyy-MM-dd", fecha);
                param_03 = send1;
            } catch (Exception e) {
                exception.addError("parametro: Fecha Fin: La fecha es inválida" + e.getMessage());
            }
        }

        // apply validators
        if ((null == param_04) || ("".equalsIgnoreCase(param_04))) {
            exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
        } else {
            if (WebKeys.SIN_SELECCIONAR.equals(param_04)) {
                exception.addError("parametro: Usuario Logueado: Debe ingresar un valor");
            }
        }

        String[] itemIds = new String[]{
            REPORTE_180__PARAM_PFECHA, REPORTE_180__PARAM_PCIRCULONOMBRE, REPORTE_180__PARAM_PFECHAFIN, REPORTE_180__PARAM_USUARIOLOG
        };

        do_ReporteXX_SaveState(itemIds, request);

        // raise ------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // -----------------------------------------------------
        String[] params = new String[]{
            REPORTE_XX__PARAM_CMDKEY, REPORTE_180__PARAM_PFECHA, REPORTE_180__PARAM_PCIRCULONOMBRE, REPORTE_180__PARAM_PFECHAFIN, REPORTE_180__PARAM_USUARIOLOG
        };

        String[] values = new String[]{
            REPORTE_180__PARAM_CMDKEY, param_01, param_02, param_03, param_04
        };

        String url = makeUrl(params, values);
        reportsServices_ConfigureParameterUri(request, url);

        do_ReporteXX_RemoveState(itemIds, request);
        do_ReporteXX_RemoveState_LoadCirculosByUsuario_Action(request);
    }
}
/* end-class*/
