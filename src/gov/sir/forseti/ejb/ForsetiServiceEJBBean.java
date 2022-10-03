package gov.sir.forseti.ejb;

import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.Busqueda;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoFestivo;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.DeltaFolio;
import gov.sir.core.negocio.modelo.Departamento;
import gov.sir.core.negocio.modelo.Direccion;
import gov.sir.core.negocio.modelo.Documento;
import gov.sir.core.negocio.modelo.DominioNaturalezaJuridica;
import gov.sir.core.negocio.modelo.Eje;
import gov.sir.core.negocio.modelo.EstadoAnotacion;
import gov.sir.core.negocio.modelo.EstadoFolio;
import gov.sir.core.negocio.modelo.FirmaRegistrador;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.jdogenie.AnotacionTMP;
import gov.sir.core.negocio.modelo.jdogenie.AnotacionTMPPk;
import gov.sir.core.negocio.modelo.jdogenie.CiudadanoTMP;
import gov.sir.core.negocio.modelo.jdogenie.FolioDatosTMP;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.negocio.modelo.ActoresRepartoNotarial;
import gov.sir.core.negocio.modelo.AnotacionCiudadano;
import gov.sir.core.negocio.modelo.Complementacion;
import gov.sir.core.negocio.modelo.FolioDerivado;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica;
import gov.sir.core.negocio.modelo.LlaveBloqueo;
import gov.sir.core.negocio.modelo.Municipio;
import gov.sir.core.negocio.modelo.NaturalezaJuridica;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.PlantillaPertenencia;
import gov.sir.core.negocio.modelo.Prohibicion;
import gov.sir.core.negocio.modelo.SalvedadAnotacion;
import gov.sir.core.negocio.modelo.Texto;
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.negocio.modelo.TipoOficina;
import gov.sir.core.negocio.modelo.TipoPredio;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.negocio.modelo.ZonaRegistral;
import gov.sir.core.negocio.modelo.jdogenie.AnotacionTMP;
import gov.sir.core.negocio.modelo.jdogenie.AnotacionTMPPk;
import gov.sir.core.negocio.modelo.jdogenie.CiudadanoTMP;
import gov.sir.core.negocio.modelo.jdogenie.FolioDatosTMP;
/**
     * @Autor: Edgar Lora
     * @Mantis: 13176
     */
import gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEnhanced;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.ForsetiService;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;


/**
 * @author Mc'Carthy Newball
 *
 * Clase cascaron EJB para el servicio Forseti
 * ForsetiServiceBean
 *
 * @ejb:bean
 *  generate="true"
 *  name="ejb/ForsetiServiceEJB"
 *  display-name="ForsetiService Session Bean"
 *  description="ForsetiService Session Bean"
 *  type="Stateless"
 *  transaction-type="Bean"
 *  jndi-name="ejb/ForsetiServiceEJB"
 *  local-jndi-name="ejb/ForsetiServiceEJBLocal"
 *  view-type="both"
 *
 * @ejb.transaction type="Never"
 *
 * @oc4j:bean
 *
 * @ejb.security-identity use-caller-identity="true"
 */
public class ForsetiServiceEJBBean implements SessionBean, ForsetiServiceInterface {

    private static ForsetiService forseti = null;
    
    public ForsetiServiceEJBBean() {
        try {
            forseti = ForsetiService.getInstance();
        } catch (ForsetiException e) {
            // TODO Auto-generated catch block
        	Log.getInstance().error(ForsetiServiceEJBBean.class, e);
        }
    }

    public void ejbCreate() throws CreateException, EJBException {
        // Write your code here
    }

    /**
     * @see javax.ejb.SessionBean#setSessionContext(javax.ejb.SessionContext)
     */
    public void setSessionContext(SessionContext arg0) throws EJBException {
        // TODO Auto-generated method stub

    }

    /**
     * @see javax.ejb.SessionBean#ejbRemove()
     */
    public void ejbRemove() throws EJBException {
        // TODO Auto-generated method stub

    }

    /**
     * @see javax.ejb.SessionBean#ejbActivate()
     */
    public void ejbActivate() throws EJBException {
        // TODO Auto-generated method stub

    }

    /**
     * @see javax.ejb.SessionBean#ejbPassivate()
     */
    public void ejbPassivate() throws EJBException {
        // TODO Auto-generated method stub

    }
    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getHistorialArea(String)
     * @ejb:interface-method view-type="both"
     */
    public List getHistorialArea(String idMatricula) throws ForsetiException{
        return forseti.getHistorialArea(idMatricula);
    }
    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getTurnsShareFolioNotaDev(java.lang.String) 
     * @ejb:interface-method view-type="both"
     */
    public List getTurnsShareFolioNotaDev(String idMatricula) throws ForsetiException{
        return forseti.getTurnsShareFolioNotaDev(idMatricula);
    }
    
     /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#validarPrincipioPrioridadNotNotaDev(java.lang.String) 
     * @ejb:interface-method view-type="both"
     */
    public List validarPrincipioPrioridadNotNotaDev(String idMatricula) throws ForsetiException{
        return forseti.validarPrincipioPrioridadNotNotaDev(idMatricula);
    }
    
     /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#validarPrincipioPrioridadNotNotaNot(java.lang.String) 
     * @ejb:interface-method view-type="both"
     */
    public List validarPrincipioPrioridadNotNotaNot(String idMatricula) throws ForsetiException{
        return forseti.validarPrincipioPrioridadNotNotaNot(idMatricula);
    }
    
     /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#validarPrincipioPrioridadRecNot(java.lang.String) 
     * @ejb:interface-method view-type="both"
     */
    public List validarPrincipioPrioridadRecNot(String idMatricula) throws ForsetiException{
        return forseti.validarPrincipioPrioridadRecNot(idMatricula);
    }
    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getFolioByID(gov.sir.core.negocio.modelo.Folio.FolioPk)
     * @ejb:interface-method view-type="both"
     */
    public Folio getFolioByID(FolioPk oid) throws ForsetiException {
        return forseti.getFolioByID(oid);
    }
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getFolioByID(String)
     * @ejb:interface-method view-type="both"
     */
    public Folio getFolioByID(String matricula) throws ForsetiException {
        return forseti.getFolioByID(matricula);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getFolioByMatricula(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public Folio getFolioByMatricula(String matricula) throws ForsetiException {
        return forseti.getFolioByMatricula(matricula);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getAnotacionesFolio(gov.sir.core.negocio.modelo.Folio.FolioPk)
     * @ejb:interface-method view-type="both"
     */
    public List getAnotacionesFolio(FolioPk oid) throws ForsetiException {
        return forseti.getAnotacionesFolio(oid);
    }
    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getAnotacionesFolio(gov.sir.core.negocio.modelo.Folio.FolioPk)
     * @ejb:interface-method view-type="both"
     */
    public AnotacionTMP getAnotacionTMPByIDSinPersitence(AnotacionTMPPk oid) throws ForsetiException {
        return forseti.getAnotacionTMPByIDSinPersitence(oid);
    }
    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getAnotacionesFolioTMP(gov.sir.core.negocio.modelo.Folio.FolioPk)
     * @ejb:interface-method view-type="both"
     */
    public List getAnotacionesFolioTMP(FolioPk oid) throws ForsetiException {
        return forseti.getAnotacionesFolioTMP(oid);
    }
    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getAnotacionesFolioTMPFD(gov.sir.core.negocio.modelo.Folio.FolioPk)
     * @ejb:interface-method view-type="both"
     */
    public List getAnotacionesFolioTMPFD(FolioPk oid) throws ForsetiException {
        return forseti.getAnotacionesFolioTMPFD(oid);
    }
    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getAnotacionesFolioTMP(gov.sir.core.negocio.modelo.Folio.FolioPk)
     * @ejb:interface-method view-type="both"
     */
    public int getAnotacionesFolioTMPCount(FolioPk oid) throws ForsetiException {
        return forseti.getAnotacionesFolioTMPCount(oid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#addEstadoFolio(gov.sir.core.negocio.modelo.EstadoFolio, Usuario usuario)
     * @ejb:interface-method view-type="both"
     */
    public gov.sir.core.negocio.modelo.EstadoFolioPk addEstadoFolio(EstadoFolio datos, Usuario usuario) throws ForsetiException {
        return forseti.addEstadoFolio(datos, usuario);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#addDepartamento(gov.sir.core.negocio.modelo.Departamento)
     * @ejb:interface-method view-type="both"
     */
    public gov.sir.core.negocio.modelo.DepartamentoPk addDepartamento(Departamento datos, Usuario usuario) throws ForsetiException {
        return forseti.addDepartamento(datos, usuario);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getDepartamento(gov.sir.core.negocio.modelo.Departamento.DepartamentoPk)
     * @ejb:interface-method view-type="both"
     */
    public Departamento getDepartamento(gov.sir.core.negocio.modelo.DepartamentoPk oid) throws ForsetiException {
        return forseti.getDepartamento(oid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#addMunicipioToDepartamento(gov.sir.core.negocio.modelo.Municipio, gov.sir.core.negocio.modelo.Departamento.ID, Usuario usuario)
     * @ejb:interface-method view-type="both"
     */
    public gov.sir.core.negocio.modelo.MunicipioPk addMunicipioToDepartamento(Municipio datos, gov.sir.core.negocio.modelo.DepartamentoPk oid, Usuario usuario) throws ForsetiException {
        return forseti.addMunicipioToDepartamento(datos, oid, usuario);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#existeFolio(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public boolean existeFolio(String matricula) throws ForsetiException {
        return forseti.existeFolio(matricula);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#existeMatriculaIncluyendoTemporales(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public boolean existeFolioIncluyendoTemporales(String matricula) throws ForsetiException {
        return forseti.existeFolioIncluyendoTemporales(matricula);
    }

    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#bloqueadoFolio(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public boolean bloqueadoFolio(String matricula) throws ForsetiException {
        return forseti.bloqueadoFolio(matricula);
    }
    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#isActuacionAdministrativa(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public boolean isActuacionAdministrativa(String idMatricula) throws ForsetiException {
        return forseti.isActuacionAdministrativa(idMatricula);
    }
    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#estaCiudadanoEnAnotacionCiudadano(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public boolean estaCiudadanoEnAnotacionCiudadano(String idCiudadano) throws ForsetiException {
        return forseti.estaCiudadanoEnAnotacionCiudadano(idCiudadano);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#enTramiteFolio(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public boolean enTramiteFolio(String matricula) throws ForsetiException {
        return forseti.enTramiteFolio(matricula);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#enCustodiaFolio(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public boolean enCustodiaFolio(String matricula) throws ForsetiException {
        return forseti.enCustodiaFolio(matricula);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#actualizarFolioDatosTMP(java.lang.String, gov.sir.core.negocio.modelo.jdogenie.FolioDatosTMP, boolean)
     * @ejb:interface-method view-type="both"
     */
	
	public boolean actualizarFolioDatosTMP(String matricula, FolioDatosTMP datos, boolean eliminarDireccion)
	 throws ForsetiException {
		return forseti.actualizarFolioDatosTMP(matricula, datos, eliminarDireccion);
	}
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#tieneDeudaFolio(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public boolean tieneDeudaFolio(String matricula) throws ForsetiException {
        return forseti.tieneDeudaFolio(matricula);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#mayorExtensionFolio(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public boolean mayorExtensionFolio(String matricula) throws ForsetiException {
        return forseti.mayorExtensionFolio(matricula);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#addCirculo(gov.sir.core.negocio.modelo.Circulo)
     * @ejb:interface-method view-type="both"
     */
    public gov.sir.core.negocio.modelo.CirculoPk addCirculo(Circulo datos, Usuario usuario) throws ForsetiException {
        return forseti.addCirculo(datos, usuario);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#crearFolio(gov.sir.core.negocio.modelo.Folio, gov.sir.core.negocio.modelo.Usuario, gov.sir.core.negocio.modelo.Turno.TurnoPk, boolean)
     * @ejb:interface-method view-type="both"
     */
    public Folio crearFolio(Folio datos, Usuario us, TurnoPk oid, boolean validarTurno) throws ForsetiException {
        return forseti.crearFolio(datos, us, oid,  validarTurno);
    }
    
    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#crearFolio(gov.sir.core.negocio.modelo.Folio, gov.sir.core.negocio.modelo.Usuario, gov.sir.core.negocio.modelo.Turno.TurnoPk, boolean)
     * @ejb:interface-method view-type="both"
     */
    public Folio crearFolioSegeng(Folio datos, Usuario us, TurnoPk oid, boolean validarTurno, Folio folioB) throws ForsetiException {
        return forseti.crearFolioSegeng(datos, us, oid,  validarTurno, folioB);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#validarActualizacionCiudadanoTurno(Turno turno)
     * @ejb:interface-method view-type="both"
     */
    public boolean validarActualizacionCiudadanoTurno(Turno turno) throws ForsetiException {
        return forseti.validarActualizacionCiudadanoTurno(turno);
    }
    
    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#validarActualizacionCiudadanoTurno(Turno turno)
     * @ejb:interface-method view-type="both"
     */
    public List getFolioDerivePadre(String IdMatricula,String IdAnotacion) throws ForsetiException {
        return forseti.getFolioDerivePadre(IdMatricula, IdAnotacion);
    }
    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getBloqueoFolios(gov.sir.core.negocio.modelo.LlaveBloqueo.LlaveBloqueoPk)
     * @ejb:interface-method view-type="both"
     */
    public List getBloqueoFolios(gov.sir.core.negocio.modelo.LlaveBloqueoPk oid) throws ForsetiException {
        return forseti.getBloqueoFolios(oid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getDireccionesFolio(gov.sir.core.negocio.modelo.Folio.FolioPk)
     * @ejb:interface-method view-type="both"
     */
    public List getDireccionesFolio(FolioPk oid) throws ForsetiException {
        return forseti.getDireccionesFolio(oid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getSalvedadesFolio(gov.sir.core.negocio.modelo.Folio.FolioPk)
     * @ejb:interface-method view-type="both"
     */
    public List getSalvedadesFolio(FolioPk oid) throws ForsetiException {
        return forseti.getSalvedadesFolio(oid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getDocumentoAnotacion(gov.sir.core.negocio.modelo.Anotacion.AnotacionPk)
     * @ejb:interface-method view-type="both"
     */
    public Documento getDocumentoAnotacion(gov.sir.core.negocio.modelo.AnotacionPk oid) throws ForsetiException {
        return forseti.getDocumentoAnotacion(oid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getSalvedadesAnotacion(gov.sir.core.negocio.modelo.Anotacion.AnotacionPk)
     * @ejb:interface-method view-type="both"
     */
    public List getSalvedadesAnotacion(gov.sir.core.negocio.modelo.AnotacionPk oid) throws ForsetiException {
        return forseti.getSalvedadesAnotacion(oid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getAnotacionesCiudadano(gov.sir.core.negocio.modelo.Anotacion.AnotacionPk)
     * @ejb:interface-method view-type="both"
     */
    public List getAnotacionesCiudadano(gov.sir.core.negocio.modelo.AnotacionPk oid) throws ForsetiException {
        return forseti.getAnotacionesCiudadano(oid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getAnotacionesHijas(gov.sir.core.negocio.modelo.Anotacion.AnotacionPk)
     * @ejb:interface-method view-type="both"
     */
    public List getAnotacionesHijas(gov.sir.core.negocio.modelo.AnotacionPk oid) throws ForsetiException {
        return forseti.getAnotacionesHijas(oid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getAnotacionesPadre(gov.sir.core.negocio.modelo.Anotacion.AnotacionPk)
     * @ejb:interface-method view-type="both"
     */
    public List getAnotacionesPadre(gov.sir.core.negocio.modelo.AnotacionPk oid) throws ForsetiException {
        return forseti.getAnotacionesPadre(oid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#addDireccionToFolio(gov.sir.core.negocio.modelo.Folio.FolioPk, gov.sir.core.negocio.modelo.Direccion)
     * @ejb:interface-method view-type="both"
     */
    public gov.sir.core.negocio.modelo.DireccionPk addDireccionToFolio(FolioPk oid, Direccion datos) throws ForsetiException {
        return forseti.addDireccionToFolio(oid, datos);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getUltimaDireccion(gov.sir.core.negocio.modelo.Folio.FolioPk)
     * @ejb:interface-method view-type="both"
     */
    public Direccion getUltimaDireccion(FolioPk oid) throws ForsetiException {
        return forseti.getUltimaDireccion(oid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#setDocumentoToAnotacion(gov.sir.core.negocio.modelo.Anotacion.AnotacionPk, gov.sir.core.negocio.modelo.Documento)
     * @ejb:interface-method view-type="both"
     */
    public gov.sir.core.negocio.modelo.DocumentoPk setDocumentoToAnotacion(gov.sir.core.negocio.modelo.AnotacionPk oid, Documento datos) throws ForsetiException {
        return forseti.setDocumentoToAnotacion(oid, datos);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#addAnotacionToFolio(gov.sir.core.negocio.modelo.Folio.FolioPk, gov.sir.core.negocio.modelo.Anotacion)
     * @ejb:interface-method view-type="both"
     */
    public gov.sir.core.negocio.modelo.AnotacionPk addAnotacionToFolio(FolioPk oid, Anotacion datos) throws ForsetiException {
        return forseti.addAnotacionToFolio(oid, datos);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getDepartamentos(gov.sir.core.negocio.modelo.Circulo.CirculoPk)
     * @ejb:interface-method view-type="both"
     */
    public List getDepartamentos(gov.sir.core.negocio.modelo.CirculoPk oid) throws ForsetiException {
        return forseti.getDepartamentos(oid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getEjes()
     * @ejb:interface-method view-type="both"
     */
    public List getEjes() throws ForsetiException {
        return forseti.getEjes();
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getTiposPredio()
     * @ejb:interface-method view-type="both"
     */
    public List getTiposPredio() throws ForsetiException {
        return forseti.getTiposPredio();
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#atenderConsulta(gov.sir.core.negocio.modelo.Busqueda.BusquedaPk)
     * @ejb:interface-method view-type="both"
     */
    public Busqueda atenderConsulta(gov.sir.core.negocio.modelo.BusquedaPk bid) throws ForsetiException {
        return forseti.atenderConsulta(bid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#atenderConsultaAdministracion(gov.sir.core.negocio.modelo.Busqueda)
     * @ejb:interface-method view-type="both"
     */
    public Busqueda atenderConsultaAdministracion(gov.sir.core.negocio.modelo.Busqueda  busqueda) throws ForsetiException {
        return forseti.atenderConsultaAdministracion(busqueda);
    }
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getTiposDocumento()
     * @ejb:interface-method view-type="both"
     */
    public List getTiposDocumento() throws ForsetiException {
        return forseti.getTiposDocumento();
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getGruposNaturalezaJuridica()
     * @ejb:interface-method view-type="both"
     */
    public List getGruposNaturalezaJuridica() throws ForsetiException {
        return forseti.getGruposNaturalezaJuridica();
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getOficinasOrigenByVereda(gov.sir.core.negocio.modelo.Vereda.VeredaPk)
     * @ejb:interface-method view-type="both"
     */
    public List getOficinasOrigenByVereda(gov.sir.core.negocio.modelo.VeredaPk oid) throws ForsetiException {
        return forseti.getOficinasOrigenByVereda(oid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getTiposOficinaByVereda(gov.sir.core.negocio.modelo.Vereda.VeredaPk)
     * @ejb:interface-method view-type="both"
     */
    public Hashtable getTiposOficinaByVereda(gov.sir.core.negocio.modelo.VeredaPk vid) throws ForsetiException {
        return forseti.getTiposOficinaByVereda(vid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getEstadosFolio()
     * @ejb:interface-method view-type="both"
     */
    public List getEstadosFolio() throws ForsetiException {
        return forseti.getEstadosFolio();
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#validarMatriculas(java.util.List, java.util.List)
     * @ejb:interface-method view-type="both"
     */
    public boolean validarMatriculas(List validaciones, List matriculas) throws ForsetiException {
        return forseti.validarMatriculas(validaciones, matriculas);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#validarCrearFolio(gov.sir.core.negocio.modelo.Folio)
     * @ejb:interface-method view-type="both"
     */
    public void validarCrearFolio(Folio datos , boolean validarTurno) throws ForsetiException {
        forseti.validarCrearFolio(datos, validarTurno);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#finalizar()
     * @ejb:interface-method view-type="both"
     */
    public void finalizar() throws ForsetiException {
        forseti.finalizar();
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#bloquearFolios(java.util.List, gov.sir.core.negocio.modelo.Usuario, gov.sir.core.negocio.modelo.Turno.TurnoPk)
     * @ejb:interface-method view-type="both"
     */
    public LlaveBloqueo bloquearFolios(List matriculas, Usuario usuario, gov.sir.core.negocio.modelo.TurnoPk turnoID) throws ForsetiException {
        return forseti.bloquearFolios(matriculas, usuario, turnoID);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#addEstadoAnotacion(gov.sir.core.negocio.modelo.EstadoAnotacion)
     * @ejb:interface-method view-type="both"
     */
    public gov.sir.core.negocio.modelo.EstadoAnotacionPk addEstadoAnotacion(EstadoAnotacion datos) throws ForsetiException {
        return forseti.addEstadoAnotacion(datos);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getEstadosAnotacion()
     * @ejb:interface-method view-type="both"
     */
    public List getEstadosAnotacion() throws ForsetiException {
        return forseti.getEstadosAnotacion();
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#addGrupoNaturalezaJuridica(gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica)
     * @ejb:interface-method view-type="both"
     */
    public gov.sir.core.negocio.modelo.GrupoNaturalezaJuridicaPk addGrupoNaturalezaJuridica(GrupoNaturalezaJuridica naturaleza) throws ForsetiException {
        return forseti.addGrupoNaturalezaJuridica(naturaleza);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getGrupoNaturalezaJuridica(gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica.GrupoNaturalezaJuridicaPk)
     * @ejb:interface-method view-type="both"
     */
    public GrupoNaturalezaJuridica getGrupoNaturalezaJuridica(gov.sir.core.negocio.modelo.GrupoNaturalezaJuridicaPk oid) throws ForsetiException {
        return forseti.getGrupoNaturalezaJuridica(oid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#addOficinaOrigen(gov.sir.core.negocio.modelo.OficinaOrigen)
     * @ejb:interface-method view-type="both"
     */
    public gov.sir.core.negocio.modelo.OficinaOrigenPk addOficinaOrigen(OficinaOrigen oficina) throws ForsetiException {
        return forseti.addOficinaOrigen(oficina);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#addTipoDocumento(gov.sir.core.negocio.modelo.TipoDocumento, Usuario)
     * @ejb:interface-method view-type="both"
     */
    public gov.sir.core.negocio.modelo.TipoDocumentoPk addTipoDocumento(TipoDocumento datos, Usuario usuario) throws ForsetiException {
        return forseti.addTipoDocumento(datos, usuario);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#addTipoOficina(gov.sir.core.negocio.modelo.TipoOficina, Usuario usuario)
     * @ejb:interface-method view-type="both"
     */
    public gov.sir.core.negocio.modelo.TipoOficinaPk addTipoOficina(TipoOficina datos, Usuario usuario) throws ForsetiException {
        return forseti.addTipoOficina(datos, usuario);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#addTipoPredio(gov.sir.core.negocio.modelo.TipoPredio)
     * @ejb:interface-method view-type="both"
     */
    public gov.sir.core.negocio.modelo.TipoPredioPk addTipoPredio(TipoPredio datos) throws ForsetiException {
        return forseti.addTipoPredio(datos);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#addNaturalezaJuridicaToGrupo(gov.sir.core.negocio.modelo.NaturalezaJuridica, gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica.ID, Usuario usuario)
     * @ejb:interface-method view-type="both"
     */
    public gov.sir.core.negocio.modelo.NaturalezaJuridicaPk addNaturalezaJuridicaToGrupo(NaturalezaJuridica datos, gov.sir.core.negocio.modelo.GrupoNaturalezaJuridicaPk gid, Usuario usuario) throws ForsetiException {
        return forseti.addNaturalezaJuridicaToGrupo(datos, gid, usuario);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#addVeredaToMunicipio(gov.sir.core.negocio.modelo.Vereda, gov.sir.core.negocio.modelo.Municipio.MunicipioPk)
     * @ejb:interface-method view-type="both"
     */
    public gov.sir.core.negocio.modelo.VeredaPk addVeredaToMunicipio(Vereda ver, gov.sir.core.negocio.modelo.MunicipioPk oid) throws ForsetiException {
        return forseti.addVeredaToMunicipio(ver, oid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getCirculos()
     * @ejb:interface-method view-type="both"
     */
    public List getCirculos() throws ForsetiException {
        return forseti.getCirculos();
    }
    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getCirculosFechaProd()
     * @ejb:interface-method view-type="both"
     */
    public List getCirculosFechaProd() throws ForsetiException {
        return forseti.getCirculosFechaProd();
    }
    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getCirculosFirmaRegistrador()
     * @ejb:interface-method view-type="both"
     */
    public List getCirculosFirmaRegistrador() throws ForsetiException {
        return forseti.getCirculosFirmaRegistrador();
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#addEje(gov.sir.core.negocio.modelo.Eje, Usuario)
     * @ejb:interface-method view-type="both"
     */
    public gov.sir.core.negocio.modelo.EjePk addEje(Eje datos, Usuario usuario) throws ForsetiException {
        return forseti.addEje(datos, usuario);
    }    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#updateFolio(gov.sir.core.negocio.modelo.Folio, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public boolean updateFolio(Folio folio, Usuario usuario, gov.sir.core.negocio.modelo.TurnoPk tid, boolean validarTurno) throws ForsetiException {
        return forseti.updateFolio(folio, usuario, tid, validarTurno);
    }
    

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#updateFolio(gov.sir.core.negocio.modelo.Folio, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    
    public boolean updateFolioFD(Folio folio, Usuario usuario, gov.sir.core.negocio.modelo.TurnoPk tid, boolean validarTurno, List lstAnotaFolioHijo, List lstAnotaFolioPadre, String anotacionUpdate, String matriculaUpdate, List lstHijosRemove) throws ForsetiException{
        return forseti.updateFolioFD(folio, usuario, tid, validarTurno, lstAnotaFolioHijo, lstAnotaFolioPadre,anotacionUpdate, matriculaUpdate, lstHijosRemove);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#hacerDefinitivoFolio(gov.sir.core.negocio.modelo.Folio, gov.sir.core.negocio.modelo.Usuario, gov.sir.core.negocio.modelo.Turno.TurnoPk)
     * @ejb:interface-method view-type="both"
     */
    public boolean hacerDefinitivoFolio(Folio folio, Usuario usuario, gov.sir.core.negocio.modelo.TurnoPk tid, boolean validarTurno) throws ForsetiException {
        return forseti.hacerDefinitivoFolio(folio, usuario, tid, validarTurno);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#deshacerCambiosFolio(gov.sir.core.negocio.modelo.Folio, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public boolean deshacerCambiosFolio(Folio datos, Usuario usuario) throws ForsetiException {
        return forseti.deshacerCambiosFolio(datos, usuario);
    }
    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#deshacerCambiosCiudadanosTurno(gov.sir.core.negocio.modelo.Turno.TurnoPk, gov.sir.core.negocio.modelo.Usuario, boolean)
     * @ejb:interface-method view-type="both"
     */
    public boolean deshacerCambiosCiudadanosTurno(gov.sir.core.negocio.modelo.TurnoPk turnoID, Usuario usuario, boolean desbloquar) throws ForsetiException {
        return forseti.deshacerCambiosCiudadanosTurno(turnoID, usuario, desbloquar);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getFolioByID(gov.sir.core.negocio.modelo.Folio.FolioPk, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public Folio getFolioByID(FolioPk oid, Usuario usuario) throws ForsetiException {
        return forseti.getFolioByID(oid,usuario);
    }
    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#loadFolioByID(gov.sir.core.negocio.modelo.Folio.FolioPk, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public Folio loadFolioByID(FolioPk oid, Usuario usuario) throws ForsetiException {
        return forseti.loadFolioByID(oid,usuario);
    }
    
    
    /**
	 * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#matriculaNoGrabadaExistente(String circulo, long matricula)
	 * @param datos del id de la matricula no grabada
	 * @ejb:interface-method view-type="both"
	 */
    public boolean matriculaNoGrabadaExistente(String circulo, long idMatricula) throws ForsetiException{
    	return forseti.matriculaNoGrabadaExistente(circulo, idMatricula);
    }
    

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#segregarFolio(gov.sir.core.negocio.modelo.Folio, java.util.List, gov.sir.core.negocio.modelo.Usuario, gov.sir.core.negocio.modelo.Anotacion, boolean, boolean, gov.sir.core.negocio.modelo.Turno.TurnoPk)
     * @ejb:interface-method view-type="both"
     */
    public List segregarFolio(Folio datos, List foliosDerivados, Usuario usuario, Anotacion nuevaAnotacion, boolean copiarComentarioSegregacion, boolean copiarComentarioHeredadas, TurnoPk oid) throws ForsetiException {
        return forseti.segregarFolio(datos, foliosDerivados, usuario, nuevaAnotacion, copiarComentarioSegregacion, copiarComentarioHeredadas, oid);
    }
    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#segregarFolioPLSQL(gov.sir.core.negocio.modelo.Folio, gov.sir.core.negocio.modelo.Turno.TurnoPk, gov.sir.core.negocio.modelo.SalvedadAnotacion salvedad, java.lang.String IdAnotacionDef)
     * @ejb:interface-method view-type="both"
     * @author      :   Julio Alcázar Rivas
     * @change      :   Se modifico el metodo segregarFolioPLSQL agregandole el param SalvedadAnotacion salvedad
     * Caso Mantis  :   04131
     */
    public List segregarFolioPLSQL(Folio datos, TurnoPk oid,SalvedadAnotacion salvedad, String IdAnotacionDef) throws ForsetiException {
    	return forseti.segregarFolioPLSQL(datos, oid, salvedad, IdAnotacionDef);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#englobarFolio(java.util.List, gov.sir.core.negocio.modelo.Folio, gov.sir.core.negocio.modelo.Usuario, java.util.List, gov.sir.core.negocio.modelo.Anotacion, gov.sir.core.negocio.modelo.FolioDerivado, boolean, boolean, gov.sir.core.negocio.modelo.Turno.TurnoPk)
     * @ejb:interface-method view-type="both"
     */
    public Folio englobarFolio(List foliosFuentes, Folio folio, Usuario usuario, List tabla, Anotacion anotacion, FolioDerivado informacionAdicional, boolean copiarComentarioEnglobe, boolean copiarComentarioHeredadas, TurnoPk oid) throws ForsetiException {
        return forseti.englobarFolio(foliosFuentes, folio, usuario, tabla, anotacion, informacionAdicional, copiarComentarioEnglobe, copiarComentarioHeredadas, oid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#delegarBloqueoFolios(gov.sir.core.negocio.modelo.Turno.TurnoPk, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public LlaveBloqueo delegarBloqueoFolios(gov.sir.core.negocio.modelo.TurnoPk oid, Usuario usuario) throws ForsetiException {
        return forseti.delegarBloqueoFolios(oid, usuario);
    }
    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#delegarBloqueoFoliosObligatorio(gov.sir.core.negocio.modelo.Turno.TurnoPk, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public LlaveBloqueo delegarBloqueoFoliosObligatorio(gov.sir.core.negocio.modelo.TurnoPk oid, Usuario usuario) throws ForsetiException {
        return forseti.delegarBloqueoFoliosObligatorio(oid, usuario);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#hasAnotacionesTMP(gov.sir.core.negocio.modelo.Folio.FolioPk)
     * @ejb:interface-method view-type="both"
     */
    public boolean hasAnotacionesTMP(FolioPk oid) throws ForsetiException {
        return forseti.hasAnotacionesTMP(oid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#desbloquearFoliosTurno(gov.sir.core.negocio.modelo.Turno.TurnoPk, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
	public boolean desbloquearFoliosTurno(TurnoPk oid, Usuario usuario) throws ForsetiException {
        return forseti.desbloquearFoliosTurno(oid, usuario);
    }
	
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#updateFolios(gov.sir.core.negocio.modelo.Folio, java.util.List, gov.sir.core.negocio.modelo.Usuario, boolean)
     * @ejb:interface-method view-type="both"
     */
    public boolean updateFolios(Folio datos, List ids, Usuario usuario, boolean validarBloqueo) throws ForsetiException {
        return forseti.updateFolios(datos, ids, usuario, validarBloqueo);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getDominioNaturalezaJuridica(gov.sir.core.negocio.modelo.DominioNaturalezaJuridica.DominioNaturalezaJuridicaPk)
     * @ejb:interface-method view-type="both"
     */
    public DominioNaturalezaJuridica getDominioNaturalezaJuridica(gov.sir.core.negocio.modelo.DominioNaturalezaJuridicaPk oid) throws ForsetiException {
        return forseti.getDominioNaturalezaJuridica(oid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getDominiosNaturalezaJuridica()
     * @ejb:interface-method view-type="both"
     */
    public List getDominiosNaturalezaJuridica() throws ForsetiException {
        return forseti.getDominiosNaturalezaJuridica();
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getTiposOficina()
     * @ejb:interface-method view-type="both"
     */
    public List getTiposOficina() throws ForsetiException {
        return forseti.getTiposOficina();
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#addDominioNaturalezaJuridica(gov.sir.core.negocio.modelo.DominioNaturalezaJuridica)
     * @ejb:interface-method view-type="both"
     */
    public gov.sir.core.negocio.modelo.DominioNaturalezaJuridicaPk addDominioNaturalezaJuridica(DominioNaturalezaJuridica naturaleza) throws ForsetiException {
        return forseti.addDominioNaturalezaJuridica(naturaleza);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#addCirculoFestivo(gov.sir.core.negocio.modelo.Circulo.CirculoPk, gov.sir.core.negocio.modelo.CirculoFestivo)
     * @ejb:interface-method view-type="both"
     */
    public gov.sir.core.negocio.modelo.CirculoFestivoPk addCirculoFestivo(gov.sir.core.negocio.modelo.CirculoPk oid, CirculoFestivo datos) throws ForsetiException {
        return forseti.addCirculoFestivo(oid, datos);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getFestivosCirculo(gov.sir.core.negocio.modelo.Circulo.CirculoPk)
     * @ejb:interface-method view-type="both"
     */
    public List getFestivosCirculo(gov.sir.core.negocio.modelo.CirculoPk oid) throws ForsetiException {
        return forseti.getFestivosCirculo(oid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#isFestivo(java.util.Date, gov.sir.core.negocio.modelo.Circulo.CirculoPk)
     * @ejb:interface-method view-type="both"
     */
    public boolean isFestivo(Date fecha, gov.sir.core.negocio.modelo.CirculoPk oid) throws ForsetiException {
        return forseti.isFestivo(fecha, oid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getFolioByIDSinAnotaciones(gov.sir.core.negocio.modelo.Folio.FolioPk)
     * @ejb:interface-method view-type="both"
     */
    public Folio getFolioByIDSinAnotaciones(FolioPk oid) throws ForsetiException {
        return forseti.getFolioByIDSinAnotaciones(oid);
    }
    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getFolioDatosTMP(import gov.sir.core.negocio.modelo.jdogenie.FolioDatosTMP.ID)
     * @ejb:interface-method view-type="both"
     */
    public FolioDatosTMP getFolioDatosTMP(gov.sir.core.negocio.modelo.jdogenie.FolioDatosTMPPk oid) throws ForsetiException {
        return forseti.getFolioDatosTMP(oid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getFolioByMatriculaSinAnotaciones(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public Folio getFolioByMatriculaSinAnotaciones(String matricula) throws ForsetiException {
        return forseti.getFolioByMatriculaSinAnotaciones(matricula);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#addZonaRegistralToCirculo(gov.sir.core.negocio.modelo.Circulo.CirculoPk, gov.sir.core.negocio.modelo.ZonaRegistral)
     * @ejb:interface-method view-type="both"
     */
    public gov.sir.core.negocio.modelo.ZonaRegistralPk addZonaRegistralToCirculo(gov.sir.core.negocio.modelo.CirculoPk oid, ZonaRegistral datos) throws ForsetiException {
        return forseti.addZonaRegistralToCirculo(oid, datos);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getZonaRegistralesCirculo(gov.sir.core.negocio.modelo.Circulo.CirculoPk)
     * @ejb:interface-method view-type="both"
     */
    public List getZonaRegistralesCirculo(gov.sir.core.negocio.modelo.CirculoPk oid) throws ForsetiException {
        return forseti.getZonaRegistralesCirculo(oid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getCountAnotacionesFolio(gov.sir.core.negocio.modelo.Folio.FolioPk, java.lang.String, java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public long getCountAnotacionesFolio(FolioPk oid, String criterio, String valor) throws ForsetiException {
        return forseti.getCountAnotacionesFolio(oid, criterio, valor);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getTurnoCorreccionActivoFolio(gov.sir.core.negocio.modelo.Folio.FolioPk)
     * @ejb:interface-method view-type="both"
     */
    public Turno getTurnoCorreccionActivoFolio(FolioPk oid) throws ForsetiException {
        return forseti.getTurnoCorreccionActivoFolio(oid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getAnotacionesFolio(gov.sir.core.negocio.modelo.Folio.FolioPk, java.lang.String, java.lang.String, int, int, boolean)
     * @ejb:interface-method view-type="both"
     */
    public List getAnotacionesFolio(FolioPk oid, String criterio, String valor, int posicionInicial, int cantidad, boolean vigente) throws ForsetiException {
        return forseti.getAnotacionesFolio(oid, criterio, valor, posicionInicial, cantidad, vigente);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getAnotacionesFolio(gov.sir.core.negocio.modelo.Folio.FolioPk, java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public List getAnotacionesFolioNJ(FolioPk oid, String naturalezaJuridica) throws ForsetiException {
        return forseti.getAnotacionesFolioNJ(oid, naturalezaJuridica);
    }

   /**
    * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#validarPrincipioPrioridadDeshacerCambiosTemporales(gov.sir.core.negocio.modelo.Turno.TurnoPk)
    * @ejb:interface-method view-type="both"
    */
    public void validarPrincipioPrioridadDeshacerCambiosTemporales(TurnoPk oid)throws ForsetiException {
        forseti.validarPrincipioPrioridadDeshacerCambiosTemporales(oid);
    }
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#validarPrincipioPrioridadCalificacion(gov.sir.core.negocio.modelo.Turno.TurnoPk, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public void validarPrincipioPrioridadCalificacion(gov.sir.core.negocio.modelo.TurnoPk oid, Usuario usuario) throws ForsetiException {
        forseti.validarPrincipioPrioridadCalificacion(oid, usuario);
    }
    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#validarPrincipioPrioridadDigitacion(gov.sir.core.negocio.modelo.Turno.TurnoPk, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public void validarPrincipioPrioridadDigitacion(gov.sir.core.negocio.modelo.TurnoPk oid, Usuario usuario) throws ForsetiException {
        forseti.validarPrincipioPrioridadDigitacion(oid, usuario);
    }
    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#isTurnoValidoCalificacion(gov.sir.core.negocio.modelo.Turno.TurnoPk, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public boolean isTurnoValidoCalificacion(gov.sir.core.negocio.modelo.TurnoPk oid, Usuario usuario) throws ForsetiException {
        return forseti.isTurnoValidoCalificacion(oid, usuario);
    }    
    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#isTurnoValidoSalidaCalificacion(gov.sir.core.negocio.modelo.Turno.TurnoPk, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public void isTurnoValidoSalidaCalificacion(gov.sir.core.negocio.modelo.TurnoPk oid, Usuario usuario) throws ForsetiException {
        forseti.isTurnoValidoSalidaCalificacion(oid, usuario);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getCountAnotacionesFolio(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public long getCountAnotacionesFolio(String matricula) throws ForsetiException {
        return forseti.getCountAnotacionesFolio(matricula);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#addFirmaRegistradorToCirculo(gov.sir.core.negocio.modelo.FirmaRegistrador, gov.sir.core.negocio.modelo.Circulo.CirculoPk)
     * @ejb:interface-method view-type="both"
     */
    public boolean addFirmaRegistradorToCirculo(FirmaRegistrador firmaReg, gov.sir.core.negocio.modelo.CirculoPk oid) throws ForsetiException {
        return forseti.addFirmaRegistradorToCirculo(firmaReg, oid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getCirculo(gov.sir.core.negocio.modelo.Circulo.CirculoPk)
     * @ejb:interface-method view-type="both"
     */
    public Circulo getCirculo(gov.sir.core.negocio.modelo.CirculoPk oid) throws ForsetiException {
        return forseti.getCirculo(oid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#setActivoFirmaRegistrador(gov.sir.core.negocio.modelo.FirmaRegistrador.FirmaRegistradorPk, int)
     * @ejb:interface-method view-type="both"
     */
    public boolean setActivoFirmaRegistrador(gov.sir.core.negocio.modelo.FirmaRegistradorPk oid, int activa) throws ForsetiException {
        return forseti.setActivoFirmaRegistrador(oid, activa);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#validarPrincipioPrioridadCorreccion(gov.sir.core.negocio.modelo.Turno.TurnoPk)
     * @ejb:interface-method view-type="both"
     */
    public void validarPrincipioPrioridadCorreccion(gov.sir.core.negocio.modelo.TurnoPk oid) throws ForsetiException {
        forseti.validarPrincipioPrioridadCorreccion(oid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getFoliosHijos(gov.sir.core.negocio.modelo.Folio.FolioPk)
     * @ejb:interface-method view-type="both"
     */
    public List getFoliosHijos(FolioPk oid) throws ForsetiException {
        return forseti.getFoliosHijos(oid);
    }
    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getFoliosHijosOrdenAnotacion(gov.sir.core.negocio.modelo.Folio.FolioPk)
     * @ejb:interface-method view-type="both"
     */
    public List getFoliosHijosOrdenAnotacion(FolioPk oid) throws ForsetiException {
        return forseti.getFoliosHijosOrdenAnotacion(oid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getFoliosPadre(gov.sir.core.negocio.modelo.Folio.FolioPk)
     * @ejb:interface-method view-type="both"
     */
    public List getFoliosPadre(FolioPk oid) throws ForsetiException {
        return forseti.getFoliosPadre(oid);
    }
    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getFoliosDerivadoHijos(gov.sir.core.negocio.modelo.Folio.FolioPk)
     * @ejb:interface-method view-type="both"
     */
    public List getFoliosDerivadoHijos(FolioPk oid) throws ForsetiException {
        return forseti.getFoliosDerivadoHijos(oid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getFoliosDerivadoPadre(gov.sir.core.negocio.modelo.Folio.FolioPk)
     * @ejb:interface-method view-type="both"
     */
    public List getFoliosDerivadoPadre(FolioPk oid) throws ForsetiException {
        return forseti.getFoliosDerivadoPadre(oid);
    }
    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getFoliosDerivadoHijos(gov.sir.core.negocio.modelo.Folio.FolioPk, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public List getFoliosDerivadoHijos(FolioPk oid, Usuario usuario) throws ForsetiException {
        return forseti.getFoliosDerivadoHijos(oid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getFoliosDerivadoPadre(gov.sir.core.negocio.modelo.Folio.FolioPk, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public List getFoliosDerivadoPadre(FolioPk oid, Usuario usuario) throws ForsetiException {
        return forseti.getFoliosDerivadoPadre(oid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#eliminarDepartamento(gov.sir.core.negocio.modelo.Departamento)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarDepartamento(Departamento dato, Usuario usuario) throws ForsetiException {
        return forseti.eliminarDepartamento(dato, usuario);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#eliminarMunicipio(gov.sir.core.negocio.modelo.Municipio)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarMunicipio(Municipio dato) throws ForsetiException {
        return forseti.eliminarMunicipio(dato);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#eliminarVereda(gov.sir.core.negocio.modelo.Vereda)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarVereda(Vereda dato) throws ForsetiException {
        return forseti.eliminarVereda(dato);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#eliminarCirculo(gov.sir.core.negocio.modelo.Circulo)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarCirculo(Circulo dato, Usuario usuario) throws ForsetiException {
        return forseti.eliminarCirculo(dato, usuario);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#eliminarZonaRegistral(gov.sir.core.negocio.modelo.ZonaRegistral)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarZonaRegistral(ZonaRegistral dato) throws ForsetiException {
        return forseti.eliminarZonaRegistral(dato);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#eliminarEje(gov.sir.core.negocio.modelo.Eje, Usuario)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarEje(Eje dato, Usuario usuario) throws ForsetiException {
        return forseti.eliminarEje(dato, usuario);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#eliminarCirculoFestivo(gov.sir.core.negocio.modelo.CirculoFestivo)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarCirculoFestivo(CirculoFestivo dato) throws ForsetiException {
        return forseti.eliminarCirculoFestivo(dato);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#eliminarEstadoFolio(gov.sir.core.negocio.modelo.EstadoFolio, Usuario usuario)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarEstadoFolio(EstadoFolio dato, Usuario usuario) throws ForsetiException {
        return forseti.eliminarEstadoFolio(dato, usuario);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#eliminarTipoOficina(gov.sir.core.negocio.modelo.TipoOficina, Usuario usuario)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarTipoOficina(TipoOficina dato, Usuario usuario) throws ForsetiException {
        return forseti.eliminarTipoOficina(dato, usuario);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#eliminarTipoDocumento(gov.sir.core.negocio.modelo.TipoDocumento, Usuario usuario)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarTipoDocumento(TipoDocumento dato, Usuario usuario) throws ForsetiException {
        return forseti.eliminarTipoDocumento(dato, usuario);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#eliminarTipoPredio(gov.sir.core.negocio.modelo.TipoPredio)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarTipoPredio(TipoPredio dato) throws ForsetiException {
        return forseti.eliminarTipoPredio(dato);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#eliminarGrupoNaturalezaJuridica(gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarGrupoNaturalezaJuridica(GrupoNaturalezaJuridica dato) throws ForsetiException {
        return forseti.eliminarGrupoNaturalezaJuridica(dato);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#eliminarNaturalezaJuridica(gov.sir.core.negocio.modelo.NaturalezaJuridica, Usuario usuario)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarNaturalezaJuridica(NaturalezaJuridica dato, Usuario usuario) throws ForsetiException {
        return forseti.eliminarNaturalezaJuridica(dato, usuario);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#eliminarEstadoAnotacion(gov.sir.core.negocio.modelo.EstadoAnotacion)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarEstadoAnotacion(EstadoAnotacion dato) throws ForsetiException {
        return forseti.eliminarEstadoAnotacion(dato);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#eliminarDominioNaturalezaJuridica(gov.sir.core.negocio.modelo.DominioNaturalezaJuridica)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarDominioNaturalezaJuridica(DominioNaturalezaJuridica dato) throws ForsetiException {
        return forseti.eliminarDominioNaturalezaJuridica(dato);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#trasladarFolio(gov.sir.core.negocio.modelo.Folio, gov.sir.core.negocio.modelo.Folio, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public Folio trasladarFolio(Folio folioOrigen, Folio folioDestino, Usuario us, String resolucion) throws ForsetiException {
        return forseti.trasladarFolio(folioOrigen, folioDestino, us, resolucion);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#catastro(java.util.Date, java.util.Date, gov.sir.core.negocio.modelo.Circulo, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public boolean catastro(Date fechIni, Date fechFin, Circulo circulo, Usuario us) throws ForsetiException {
        return forseti.catastro(fechIni, fechFin, circulo, us);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#consultarTrasladosMatricula(gov.sir.core.negocio.modelo.Folio.FolioPk)
     * @ejb:interface-method view-type="both"
     */
    public List consultarTrasladosMatricula(FolioPk idFolio) throws ForsetiException {
        return forseti.consultarTrasladosMatricula(idFolio);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getCiudadanoById(gov.sir.core.negocio.modelo.Ciudadano.CiudadanoPk)
     * @ejb:interface-method view-type="both"
     */
    public Ciudadano getCiudadanoById(gov.sir.core.negocio.modelo.CiudadanoPk oid) throws ForsetiException {
        return forseti.getCiudadanoById(oid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getAnotacionesQueRelacionanCiudadano(gov.sir.core.negocio.modelo.Ciudadano.CiudadanoPk)
     * @ejb:interface-method view-type="both"
     */
    public List getAnotacionesQueRelacionanCiudadano(gov.sir.core.negocio.modelo.CiudadanoPk oid) throws ForsetiException {
        return forseti.getAnotacionesQueRelacionanCiudadano(oid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getCiudadanoByDocumento(java.lang.String, java.lang.String, String)
     * @ejb:interface-method view-type="both"
     */
    public Ciudadano getCiudadanoByDocumento(String tipodoc, String doc, String idCirculo) throws ForsetiException {
        return forseti.getCiudadanoByDocumento(tipodoc, doc, idCirculo);
    }
    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getCiudadanoTmpByDocumento(java.lang.String, java.lang.String, String)
     * @ejb:interface-method view-type="both"
     */
    public CiudadanoTMP getCiudadanoTmpByDocumento(String tipodoc, String doc, String idCirculo) throws ForsetiException {
        return forseti.getCiudadanoTmpByDocumento(tipodoc, doc, idCirculo);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#validarTerminacionCalificacion(gov.sir.core.negocio.modelo.Turno.TurnoPk, java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public boolean validarTerminacionCalificacion(gov.sir.core.negocio.modelo.TurnoPk oid, String tipoValidacion) throws ForsetiException {
        return forseti.validarTerminacionCalificacion(oid, tipoValidacion);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getFoliosHijos(gov.sir.core.negocio.modelo.Folio.FolioPk, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public List getFoliosHijos(FolioPk oid, Usuario usuario) throws ForsetiException {
        return forseti.getFoliosHijos(oid, usuario);
    }

    /**
     * @author      :   Henry Gómez Rocha
     * @change      :   Evita que al momento de pasar del role Calificador al role
     *                  Digitador no se verifique que usuario tiene el bloqueo del folio.
     * Caso Mantis  :   0004967
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getFoliosHijos(gov.sir.core.negocio.modelo.Folio.FolioPk, gov.sir.core.negocio.modelo.Usuario, boolean)
     * @ejb:interface-method view-type="both"
     */
    public List getFoliosHijos(FolioPk oid, Usuario usuario, boolean validarTurno) throws ForsetiException {
        return forseti.getFoliosHijos(oid, usuario, validarTurno);
    }
    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getFoliosHijosOrdenAnotacion(gov.sir.core.negocio.modelo.Folio.FolioPk, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public List getFoliosHijosOrdenAnotacion(FolioPk oid, Usuario usuario) throws ForsetiException {
        return forseti.getFoliosHijosOrdenAnotacion(oid, usuario);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getFoliosPadre(gov.sir.core.negocio.modelo.Folio.FolioPk, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public List getFoliosPadre(FolioPk oid, Usuario usuario) throws ForsetiException {
        return forseti.getFoliosPadre(oid, usuario);
    }

    /**
     * @author      :   Henry Gómez Rocha
     * @change      :   Evita que al momento de pasar del role Calificador al role
     *                  Digitador no se verifique que usuario tiene el bloqueo del folio.
     * Caso Mantis  :   0004967
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getFoliosPadre(gov.sir.core.negocio.modelo.Folio.FolioPk, gov.sir.core.negocio.modelo.Usuario, boolean)
     * @ejb:interface-method view-type="both"
     */
    public List getFoliosPadre(FolioPk oid, Usuario usuario, boolean validarTurno) throws ForsetiException {
        return forseti.getFoliosPadre(oid, usuario, validarTurno);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getZonaRegistral(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public String getZonaRegistral(String matricula) throws ForsetiException {
        return forseti.getZonaRegistral(matricula);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getOficinaOrigen(gov.sir.core.negocio.modelo.OficinaOrigen.OficinaOrigenPk)
     * @ejb:interface-method view-type="both"
     */
    public OficinaOrigen getOficinaOrigen(gov.sir.core.negocio.modelo.OficinaOrigenPk oid) throws ForsetiException {
        return forseti.getOficinaOrigen(oid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getAnotacionesConSalvedades(gov.sir.core.negocio.modelo.Folio.FolioPk, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public List getAnotacionesConSalvedades(FolioPk oid, Usuario usuario) throws ForsetiException {
        return forseti.getAnotacionesConSalvedades(oid, usuario);
    }

    /**
     * @author      :   Henry Gómez Rocha
     * @change      :   Evita que al momento de pasar del role Calificador al role
     *                  Digitador no se verifique que usuario tiene el bloqueo del folio.
     * Caso Mantis  :   0004967
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getAnotacionesConSalvedades(gov.sir.core.negocio.modelo.Folio.FolioPk, gov.sir.core.negocio.modelo.Usuario, boolean)
     * @ejb:interface-method view-type="both"
     */
    public List getAnotacionesConSalvedades(FolioPk oid, Usuario usuario, boolean validarTurno) throws ForsetiException {
        return forseti.getAnotacionesConSalvedades(oid, usuario, validarTurno);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getAnotacionesConSalvedades(gov.sir.core.negocio.modelo.Folio.FolioPk)
     * @ejb:interface-method view-type="both"
     */
    public List getAnotacionesConSalvedades(FolioPk oid) throws ForsetiException {
        return forseti.getAnotacionesConSalvedades(oid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getAnotacionesConCancelaciones(gov.sir.core.negocio.modelo.Folio.FolioPk, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public List getAnotacionesConCancelaciones(FolioPk oid, Usuario usuario) throws ForsetiException {
        return forseti.getAnotacionesConCancelaciones(oid, usuario);
    }

    /**
     * @author      :   Henry Gómez Rocha
     * @change      :   Evita que al momento de pasar del role Calificador al role
     *                  Digitador no se verifique que usuario tiene el bloqueo del folio.
     * Caso Mantis  :   0004967
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getAnotacionesConCancelaciones(gov.sir.core.negocio.modelo.Folio.FolioPk, gov.sir.core.negocio.modelo.Usuario, boolean)
     * @ejb:interface-method view-type="both"
     */
    public List getAnotacionesConCancelaciones(FolioPk oid, Usuario usuario, boolean validarTurno) throws ForsetiException {
        return forseti.getAnotacionesConCancelaciones(oid, usuario, validarTurno);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getAnotacionesConCancelaciones(gov.sir.core.negocio.modelo.Folio.FolioPk)
     * @ejb:interface-method view-type="both"
     */
    public List getAnotacionesConCancelaciones(FolioPk oid) throws ForsetiException {
        return forseti.getAnotacionesConCancelaciones(oid);
    }

	/**
	 * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getAnotacionesInvalidas(gov.sir.core.negocio.modelo.Folio.FolioPk, gov.sir.core.negocio.modelo.Usuario)
	 * @ejb:interface-method view-type="both"
	 */
	public List getAnotacionesInvalidas(FolioPk oid, Usuario usuario) throws ForsetiException {
		return forseti.getAnotacionesInvalidas(oid, usuario);
	}

        /**
         * @author      :   Henry Gómez Rocha
         * @change      :   Evita que al momento de pasar del role Calificador al role
         *                  Digitador no se verifique que usuario tiene el bloqueo del folio.
         * Caso Mantis  :   0004967
	 * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getAnotacionesInvalidas(gov.sir.core.negocio.modelo.Folio.FolioPk, gov.sir.core.negocio.modelo.Usuario, boolean)
	 * @ejb:interface-method view-type="both"
	 */
	public List getAnotacionesInvalidas(FolioPk oid, Usuario usuario, boolean validarTurno) throws ForsetiException {
		return forseti.getAnotacionesInvalidas(oid, usuario, validarTurno);
	}

	/**
	 * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getAnotacionesInvalidas(gov.sir.core.negocio.modelo.Folio.FolioPk)
	 * @ejb:interface-method view-type="both"
	 */
	public List getAnotacionesInvalidas(FolioPk oid) throws ForsetiException {
		return forseti.getAnotacionesInvalidas(oid);
	}

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getFolioHijosEnAnotacionesConDireccion(gov.sir.core.negocio.modelo.Folio.FolioPk)
     * @ejb:interface-method view-type="both"
     */
    public List getFolioHijosEnAnotacionesConDireccion(FolioPk oid) throws ForsetiException {
        return forseti.getFolioHijosEnAnotacionesConDireccion(oid);
    }
    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getFolioHijosEnAnotacionesConDireccionOrdenadoAnotaciones(gov.sir.core.negocio.modelo.Folio.FolioPk)
     * @ejb:interface-method view-type="both"
     */
    public List getFolioHijosEnAnotacionesConDireccionOrdenadoAnotaciones(FolioPk oid) throws ForsetiException {
        return forseti.getFolioHijosEnAnotacionesConDireccionOrdenadoAnotaciones(oid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getCountAnotacionesTMPFolio(gov.sir.core.negocio.modelo.Folio.FolioPk, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public long getCountAnotacionesTMPFolio(FolioPk oid, Usuario usuario) throws ForsetiException {
        return forseti.getCountAnotacionesTMPFolio(oid, usuario);
    }

    /**
     * @author      :   Henry Gómez Rocha
     * @change      :   Evita que al momento de pasar del role Calificador al role
     *                  Digitador no se verifique que usuario tiene el bloqueo del folio.
     * Caso Mantis  :   0004967
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getCountAnotacionesTMPFolio(gov.sir.core.negocio.modelo.Folio.FolioPk, gov.sir.core.negocio.modelo.Usuario, boolean)
     * @ejb:interface-method view-type="both"
     */
    public long getCountAnotacionesTMPFolio(FolioPk oid, Usuario usuario, boolean validarTurno) throws ForsetiException {
        return forseti.getCountAnotacionesTMPFolio(oid, usuario, validarTurno);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getNextOrdenAnotacion(gov.sir.core.negocio.modelo.Folio.FolioPk)
     * @ejb:interface-method view-type="both"
     */
    public long getNextOrdenAnotacion(FolioPk fid) throws ForsetiException {
        return forseti.getNextOrdenAnotacion(fid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getCambiosPropuestos(gov.sir.core.negocio.modelo.Folio.FolioPk, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public DeltaFolio getCambiosPropuestos(FolioPk fid, Usuario usuario) throws ForsetiException {
        return forseti.getCambiosPropuestos(fid, usuario);
    }
    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getCambiosPropuestos( gov.sir.core.negocio.modelo.Folio.FolioPk, gov.sir.core.negocio.modelo.Usuario, boolean, boolean )
     * @ejb:interface-method view-type="both"
     */
	public DeltaFolio getCambiosPropuestos(FolioPk fid, Usuario usuario, boolean procesarDelta, boolean incluirSalvedades ) throws ForsetiException {
        return forseti.getCambiosPropuestos( fid, usuario, procesarDelta, incluirSalvedades );
	} // end-method: getCambiosPropuestos     
	

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#addProhibicion(gov.sir.core.negocio.modelo.Prohibicion)
     * @ejb:interface-method view-type="both"
     */
    public gov.sir.core.negocio.modelo.ProhibicionPk addProhibicion(Prohibicion datos) throws ForsetiException {
        return forseti.addProhibicion(datos);
    }


	/**
	 * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#editarProhibicion(gov.sir.core.negocio.modelo.Prohibicion)
	 * @ejb:interface-method view-type="both"
	 */
	public gov.sir.core.negocio.modelo.ProhibicionPk editarProhibicion(Prohibicion datos) throws ForsetiException {
		return forseti.editarProhibicion(datos);
	}


    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#addProhibicionToCiudadano(gov.sir.core.negocio.modelo.Ciudadano.CiudadanoPk, gov.sir.core.negocio.modelo.Prohibicion.ProhibicionPk, java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public gov.sir.core.negocio.modelo.CiudadanoProhibicionPk addProhibicionToCiudadano(gov.sir.core.negocio.modelo.CiudadanoPk oid, gov.sir.core.negocio.modelo.ProhibicionPk pid, gov.sir.core.negocio.modelo.CirculoPk cirid, String comentario, Usuario usuario) throws ForsetiException {
        return forseti.addProhibicionToCiudadano(oid, pid, cirid, comentario, usuario);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#desactivarProhibicionToCiudadano(gov.sir.core.negocio.modelo.CiudadanoProhibicion.CiudadanoProhibicionPk)
     * @ejb:interface-method view-type="both"
     */
    public boolean desactivarProhibicionToCiudadano(gov.sir.core.negocio.modelo.CiudadanoProhibicionPk pid, Usuario usuario,
    												String comentarioAnulacion) throws ForsetiException {
        return forseti.desactivarProhibicionToCiudadano(pid, usuario,comentarioAnulacion);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#eliminarProhibicion(gov.sir.core.negocio.modelo.Prohibicion)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarProhibicion(Prohibicion dato) throws ForsetiException {
        return forseti.eliminarProhibicion(dato);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getProhibiciones()
     * @ejb:interface-method view-type="both"
     */
    public List getProhibiciones() throws ForsetiException {
        return forseti.getProhibiciones();
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getUsuarioBloqueoFolio(gov.sir.core.negocio.modelo.Folio.FolioPk)
     * @ejb:interface-method view-type="both"
     */
    public Usuario getUsuarioBloqueoFolio(FolioPk oid) throws ForsetiException {
        return forseti.getUsuarioBloqueoFolio(oid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getDeltaFolio(gov.sir.core.negocio.modelo.Folio.FolioPk, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public Folio getDeltaFolio(FolioPk oid, Usuario usuario) throws ForsetiException {
        return forseti.getDeltaFolio(oid, usuario);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getCountAnotacionesFolio(gov.sir.core.negocio.modelo.Folio.FolioPk, java.lang.String, java.lang.String, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public long getCountAnotacionesFolio(FolioPk oid, String criterio, String valor, Usuario usuario) throws ForsetiException {
        return forseti.getCountAnotacionesFolio(oid, criterio, valor, usuario);
    }

    /**
     * @author      :   Henry Gómez Rocha
     * @change      :   Evita que al momento de pasar del role Calificador al role
     *                  Digitador no se verifique que usuario tiene el bloqueo del folio.
     * Caso Mantis  :   0004967
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getCountAnotacionesFolio(gov.sir.core.negocio.modelo.Folio.FolioPk, java.lang.String, java.lang.String, gov.sir.core.negocio.modelo.Usuario, boolean)
     * @ejb:interface-method view-type="both"
     */
    public long getCountAnotacionesFolio(FolioPk oid, String criterio, String valor, Usuario usuario, boolean validarTurno) throws ForsetiException {
        return forseti.getCountAnotacionesFolio(oid, criterio, valor, usuario, validarTurno);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getAnotacionesTMPFolioToInsert(gov.sir.core.negocio.modelo.Folio.FolioPk, java.lang.String, java.lang.String, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public List getAnotacionesTMPFolioToInsert(FolioPk oid, String criterio, String valor, Usuario usuario) throws ForsetiException {
        return forseti.getAnotacionesTMPFolioToInsert(oid, criterio, valor, usuario);
    }

    /**
     * @author      :   Henry Gómez Rocha
     * @change      :   Evita que al momento de pasar del role Calificador al role
     *                  Digitador no se verifique que usuario tiene el bloqueo del folio.
     * Caso Mantis  :   0004967
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getAnotacionesTMPFolioToInsert(gov.sir.core.negocio.modelo.Folio.FolioPk, java.lang.String, java.lang.String, gov.sir.core.negocio.modelo.Usuario, java.lang.boolean)
     * @ejb:interface-method view-type="both"
     */
    public List getAnotacionesTMPFolioToInsert(FolioPk oid, String criterio, String valor, Usuario usuario, boolean validarTurno) throws ForsetiException {
        return forseti.getAnotacionesTMPFolioToInsert(oid, criterio, valor, usuario, validarTurno);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getAnotacionesFolio(gov.sir.core.negocio.modelo.Folio.FolioPk, java.lang.String, java.lang.String, int, int, gov.sir.core.negocio.modelo.Usuario, boolean)
     * @ejb:interface-method view-type="both"
     */
    public List getAnotacionesFolio(FolioPk oid, String criterio, String valor, int posicionInicial, int cantidad, Usuario usuario, boolean vigente) throws ForsetiException {
        return forseti.getAnotacionesFolio(oid, criterio, valor, posicionInicial, cantidad, usuario, vigente);
    }

    /**
     * @author      :   Henry Gómez Rocha
     * @change      :   Evita que al momento de pasar del role Calificador al role
     *                  Digitador no se verifique que usuario tiene el bloqueo del folio.
     * Caso Mantis  :   0004967
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getAnotacionesFolio(gov.sir.core.negocio.modelo.Folio.FolioPk, java.lang.String, java.lang.String, int, int, gov.sir.core.negocio.modelo.Usuario, boolean, boolean)
     * @ejb:interface-method view-type="both"
     */
    public List getAnotacionesFolio(FolioPk oid, String criterio, String valor, int posicionInicial, int cantidad, Usuario usuario, boolean vigente, boolean validarTurno) throws ForsetiException {
        return forseti.getAnotacionesFolio(oid, criterio, valor, posicionInicial, cantidad, usuario, vigente, validarTurno);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getDatosDefinitivosDeDatosTemporales(gov.sir.core.negocio.modelo.Folio.FolioPk, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public Folio getDatosDefinitivosDeDatosTemporales(FolioPk oid, Usuario usuario) throws ForsetiException {
        return forseti.getDatosDefinitivosDeDatosTemporales(oid, usuario);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getDeltaFolios(gov.sir.core.negocio.modelo.Turno.TurnoPk, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public List getDeltaFolios(gov.sir.core.negocio.modelo.TurnoPk oid, Usuario usuario) throws ForsetiException {
        return forseti.getDeltaFolios(oid, usuario);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getFirmaRegistradorActiva(gov.sir.core.negocio.modelo.Circulo.CirculoPk, java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public FirmaRegistrador getFirmaRegistradorActiva(gov.sir.core.negocio.modelo.CirculoPk oid, String cargo) throws ForsetiException {
        return forseti.getFirmaRegistradorActiva(oid, cargo);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#updateCirculo(gov.sir.core.negocio.modelo.Circulo.CirculoPk, gov.sir.core.negocio.modelo.Circulo)
     * @ejb:interface-method view-type="both"
     */
    public boolean updateCirculo(gov.sir.core.negocio.modelo.CirculoPk cid, Circulo dato, Usuario usuario) throws ForsetiException {
        return forseti.updateCirculo(cid, dato, usuario);
    }
    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#updateComplementacionConflictiva(gov.sir.core.negocio.modelo.Complementacion, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public boolean updateComplementacionConflictiva(Complementacion complementacion, Usuario usuario) throws ForsetiException {
        return forseti.updateComplementacionConflictiva(complementacion, usuario);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getFolioByIDSinAnotaciones(gov.sir.core.negocio.modelo.Folio.FolioPk, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public Folio getFolioByIDSinAnotaciones(FolioPk oid, Usuario usuario) throws ForsetiException {
        return forseti.getFolioByIDSinAnotaciones(oid, usuario);
    }

    /**
     * @author      :   Henry Gómez Rocha
     * @change      :   Evita que al momento de pasar del role Calificador al role
     *                  Digitador no se verifique que usuario tiene el bloqueo del folio.
     * Caso Mantis  :   0004967
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getFolioByIDSinAnotaciones(gov.sir.core.negocio.modelo.Folio.FolioPk, gov.sir.core.negocio.modelo.Usuario, boolean)
     * @ejb:interface-method view-type="both"
     */
    public Folio getFolioByIDSinAnotaciones(FolioPk oid, Usuario usuario, boolean validarTurno) throws ForsetiException {
        return forseti.getFolioByIDSinAnotaciones(oid, usuario, validarTurno);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getCiudadanoByDocumentoSolicitante(java.lang.String, java.lang.String, String)
     * @ejb:interface-method view-type="both"
     */
    public Ciudadano getCiudadanoByDocumentoSolicitante(String tipodoc, String doc, String idCirculo) throws ForsetiException {
        return forseti.getCiudadanoByDocumentoSolicitante(tipodoc, doc, idCirculo);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getTurnoTramiteFolio(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public Turno getTurnoTramiteFolio(String matricula) throws ForsetiException {
        return forseti.getTurnoTramiteFolio(matricula);
    }
    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getTurnosTramiteFolio(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public List getTurnosTramiteFolio(String matricula) throws ForsetiException {
        return forseti.getTurnosTramiteFolio(matricula);
    }
    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getBloqueoFolio(gov.sir.core.negocio.modelo.Folio.FolioPk)
     * @ejb:interface-method view-type="both"
     */
    public Usuario getBloqueoFolio(FolioPk fid) throws ForsetiException {
        return forseti.getBloqueoFolio(fid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getTurnoDeudaFolio(gov.sir.core.negocio.modelo.Folio.FolioPk)
     * @ejb:interface-method view-type="both"
     */
    public Turno getTurnoDeudaFolio(FolioPk fid) throws ForsetiException {
        return forseti.getTurnoDeudaFolio(fid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getPlantillaPertenenciaByRespuesta(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public PlantillaPertenencia getPlantillaPertenenciaByRespuesta(String respuesta) throws ForsetiException {
        return forseti.getPlantillaPertenenciaByRespuesta(respuesta);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#updatePlantillaPertenenciaByRespuesta(java.lang.String, java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public boolean updatePlantillaPertenenciaByRespuesta(String respuesta, String nuevaDescripcion) throws ForsetiException {
        return forseti.updatePlantillaPertenenciaByRespuesta(respuesta, nuevaDescripcion);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getPlantillaPertenencias()
     * @ejb:interface-method view-type="both"
     */
    public List getPlantillaPertenencias() throws ForsetiException {
        return forseti.getPlantillaPertenencias();
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getTurnoBySolicitud(gov.sir.core.negocio.modelo.Solicitud.SolicitudPk)
     * @ejb:interface-method view-type="both"
     */
    public Turno getTurnoBySolicitud(gov.sir.core.negocio.modelo.SolicitudPk sol) throws ForsetiException {
        return forseti.getTurnoBySolicitud(sol);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getNaturalezaJuridica(gov.sir.core.negocio.modelo.NaturalezaJuridica.NaturalezaJuridicaPk)
     * @ejb:interface-method view-type="both"
     */
    public NaturalezaJuridica getNaturalezaJuridica(gov.sir.core.negocio.modelo.NaturalezaJuridicaPk oid) throws ForsetiException {
        return forseti.getNaturalezaJuridica(oid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getCirculoImpresoras(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public List getCirculoImpresoras(String idCirculo) throws ForsetiException {
        return forseti.getCirculoImpresoras(idCirculo);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#eliminarCirculoImpresoras(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarCirculoImpresoras(String idCirculo) throws ForsetiException {
        return forseti.eliminarCirculoImpresoras(idCirculo);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#addListaCirculoImpresoras(java.lang.String, java.util.List)
     * @ejb:interface-method view-type="both"
     */
    public List addListaCirculoImpresoras(String idCirculo, List impresoras) throws ForsetiException {
        return forseti.addListaCirculoImpresoras(idCirculo, impresoras);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#updateNaturalezaJuridica(gov.sir.core.negocio.modelo.NaturalezaJuridica, usuario usuario)
     * @ejb:interface-method view-type="both"
     */
    public boolean updateNaturalezaJuridica(NaturalezaJuridica naturaleza, Usuario usuario) throws ForsetiException {
        return forseti.updateNaturalezaJuridica(naturaleza, usuario);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getGruposNaturalezaJuridicaCalificacion()
     * @ejb:interface-method view-type="both"
     */
    public List getGruposNaturalezaJuridicaCalificacion() throws ForsetiException {
        return forseti.getGruposNaturalezaJuridicaCalificacion();
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#copiarAnotacion(gov.sir.core.negocio.modelo.Anotacion.AnotacionPk, java.util.List, gov.sir.core.negocio.modelo.Usuario, boolean)
     * @ejb:interface-method view-type="both"
     */
    public boolean copiarAnotacion(gov.sir.core.negocio.modelo.AnotacionPk idAnotacion, List foliosID, Usuario usuario, boolean copiarComentario) throws ForsetiException {
        return forseti.copiarAnotacion(idAnotacion, foliosID, usuario, copiarComentario);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#countDiasNoHabiles(gov.sir.core.negocio.modelo.Circulo.CirculoPk, java.util.Date, java.util.Date)
     * @ejb:interface-method view-type="both"
     */
    public long countDiasNoHabiles(gov.sir.core.negocio.modelo.CirculoPk cirID, Date fechaInicial, Date fechaFinal) throws ForsetiException {
        return forseti.countDiasNoHabiles(cirID, fechaInicial, fechaFinal);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getAnotacionByOrden(gov.sir.core.negocio.modelo.Folio.FolioPk, java.lang.String, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public Anotacion getAnotacionByOrden(FolioPk oid, String orden, Usuario usuario) throws ForsetiException {
        return forseti.getAnotacionByOrden(oid, orden, usuario);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getAnotacionesTemporalesByRangoOrden(gov.sir.core.negocio.modelo.Folio.FolioPk, java.lang.String, java.lang.String, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public List getAnotacionesTemporalesByRangoOrden(FolioPk oid, String ordenInicial, String ordenFinal, Usuario usuario) throws ForsetiException {
        return forseti.getAnotacionesTemporalesByRangoOrden(oid, ordenInicial, ordenFinal, usuario);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#copiarAnotacionCanceladora(gov.sir.core.negocio.modelo.Anotacion.AnotacionPk, java.util.List, gov.sir.core.negocio.modelo.Usuario, boolean)
     * @ejb:interface-method view-type="both"
     */
    public boolean copiarAnotacionCanceladora(gov.sir.core.negocio.modelo.AnotacionPk idAnotacion, List folios, Usuario usuario, boolean copiarComentario) throws ForsetiException {
        return forseti.copiarAnotacionCanceladora(idAnotacion, folios, usuario, copiarComentario);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#validarNuevasAnotacionesTurno(gov.sir.core.negocio.modelo.Turno.TurnoPk)
     * @ejb:interface-method view-type="both"
     */
    public Hashtable validarNuevasAnotacionesTurno(gov.sir.core.negocio.modelo.TurnoPk turnoID) throws ForsetiException {
        return forseti.validarNuevasAnotacionesTurno(turnoID);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#isFolioInTurnoCalificacion(gov.sir.core.negocio.modelo.Folio.FolioPk)
     * @ejb:interface-method view-type="both"
     */
    public boolean isFolioInTurnoCalificacion(FolioPk folioID) throws ForsetiException {
        return forseti.isFolioInTurnoCalificacion(folioID);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#cerradoFolio(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public boolean cerradoFolio(String matricula) throws ForsetiException {
        return forseti.cerradoFolio(matricula);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#cerradoFolio(java.lang.String, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public boolean cerradoFolio(String matricula, Usuario usuario) throws ForsetiException {
        return forseti.cerradoFolio(matricula, usuario);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#inconsistenteFolio(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public boolean inconsistenteFolio(String matricula) throws ForsetiException {
        return forseti.inconsistenteFolio(matricula);
    }
    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#deshacerCambiosTurno(gov.sir.core.negocio.modelo.Turno.TurnoPk, gov.sir.core.negocio.modelo.Usuario, boolean)
     * @ejb:interface-method view-type="both"
     */
    public boolean deshacerCambiosTurno(gov.sir.core.negocio.modelo.TurnoPk turnoID, Usuario usuario, boolean desbloquar) throws ForsetiException {
        return forseti.deshacerCambiosTurno(turnoID, usuario, desbloquar);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#updateOficinaOrigen(gov.sir.core.negocio.modelo.OficinaOrigen)
     * @ejb:interface-method view-type="both"
     */
    public boolean updateOficinaOrigen(OficinaOrigen oficina) throws ForsetiException {
        return forseti.updateOficinaOrigen(oficina);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#hasDatosTemporalesTurno(gov.sir.core.negocio.modelo.Turno.TurnoPk, gov.sir.core.negocio.modelo.Folio.FolioPk)
     * @ejb:interface-method view-type="both"
     */
    public boolean hasDatosTemporalesTurno(gov.sir.core.negocio.modelo.TurnoPk turnoID, FolioPk folioID) throws ForsetiException {
        return forseti.hasDatosTemporalesTurno(turnoID, folioID);
    }
    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#hasDatosTemporalesFolio(gov.sir.core.negocio.modelo.Folio.FolioPk)
     * @ejb:interface-method view-type="both"
     */
    public boolean hasDatosTemporalesFolio(FolioPk folioID) throws ForsetiException {
        return forseti.hasDatosTemporalesFolio(folioID);
    }
    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#isBloqueadoByTurno(gov.sir.core.negocio.modelo.Turno.TurnoPk, gov.sir.core.negocio.modelo.Folio.FolioPk)
     * @ejb:interface-method view-type="both"
     */
    public boolean isBloqueadoByTurno(TurnoPk turnoID, FolioPk folioID) throws ForsetiException {
        return forseti.isBloqueadoByTurno(turnoID, folioID);
    }		
		
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#deleteFolio(gov.sir.core.negocio.modelo.Folio.ID,String concepto)
     * @ejb:interface-method view-type="both"
     */
	public boolean deleteFolio(FolioPk folioID, String concepto)throws ForsetiException {
		 return forseti.deleteFolio(folioID, concepto);
	}

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#deleteFolio(gov.sir.core.negocio.modelo.Folio.FolioPk, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public boolean deleteFolio(FolioPk folioID, Usuario usuario) throws ForsetiException {
        return forseti.deleteFolio(folioID, usuario);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#desasociarFolios(gov.sir.core.negocio.modelo.Anotacion.AnotacionPk, gov.sir.core.negocio.modelo.Anotacion.AnotacionPk, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public boolean desasociarFolios(gov.sir.core.negocio.modelo.AnotacionPk anotaGeneradoraID, gov.sir.core.negocio.modelo.AnotacionPk anotaDerivadaID, Usuario usuario) throws ForsetiException {
        return forseti.desasociarFolios(anotaGeneradoraID, anotaDerivadaID, usuario);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#asociarFolios(gov.sir.core.negocio.modelo.Anotacion.AnotacionPk, gov.sir.core.negocio.modelo.Anotacion.AnotacionPk, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public boolean asociarFolios(gov.sir.core.negocio.modelo.AnotacionPk anotaGeneradoraID, gov.sir.core.negocio.modelo.AnotacionPk anotaDerivadaID, Usuario usuario, Turno turno) throws ForsetiException {
        return forseti.asociarFolios(anotaGeneradoraID, anotaDerivadaID, usuario, turno);
    }
    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#asociarFoliosAdministrativa(gov.sir.core.negocio.modelo.Anotacion.ID, gov.sir.core.negocio.modelo.Anotacion.ID, gov.sir.core.negocio.modelo.Usuario, int tipo)
     * @ejb:interface-method view-type="both"
     */
    public boolean asociarFoliosAdministrativa(gov.sir.core.negocio.modelo.AnotacionPk anotaGeneradoraID, gov.sir.core.negocio.modelo.AnotacionPk anotaDerivadaID, Usuario usuario, int tipo) throws ForsetiException {
        return forseti.asociarFoliosAdministrativa(anotaGeneradoraID, anotaDerivadaID, usuario, tipo);
    }
    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#desasociarFoliosAdministrativa(gov.sir.core.negocio.modelo.Anotacion.ID, gov.sir.core.negocio.modelo.Anotacion.ID, gov.sir.core.negocio.modelo.Usuario, int tipo)
     * @ejb:interface-method view-type="both"
     */
    public boolean desasociarFoliosAdministrativa(gov.sir.core.negocio.modelo.AnotacionPk anotaGeneradoraID, gov.sir.core.negocio.modelo.AnotacionPk anotaDerivadaID, Usuario usuario, int tipo) throws ForsetiException {
        return forseti.desasociarFoliosAdministrativa(anotaGeneradoraID, anotaDerivadaID, usuario, tipo);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getFolioDerivadoEnlace(gov.sir.core.negocio.modelo.Folio.FolioPk, gov.sir.core.negocio.modelo.Folio.FolioPk, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public FolioDerivado getFolioDerivadoEnlace(FolioPk folioIDPadre, FolioPk folioIDHijo, Usuario usuario) throws ForsetiException {
        return forseti.getFolioDerivadoEnlace(folioIDPadre, folioIDHijo, usuario);
    }

	/**
	 * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getFolioDerivadoHijo(gov.sir.core.negocio.modelo.Folio.FolioPk, gov.sir.core.negocio.modelo.Usuario)
	 * @ejb:interface-method view-type="both"
	 */
	public FolioDerivado getFolioDerivadoHijo(FolioPk folioIDHijo, Usuario usuario) throws ForsetiException {
		return forseti.getFolioDerivadoHijo(folioIDHijo, usuario);
	}


    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getCalificacionTurno(gov.sir.core.negocio.modelo.Turno.TurnoPk)
     * @ejb:interface-method view-type="both"
     */
    public List getCalificacionTurno(gov.sir.core.negocio.modelo.TurnoPk oid) throws ForsetiException {
        return forseti.getCalificacionTurno(oid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#delegarBloqueoFolio(gov.sir.core.negocio.modelo.Turno.TurnoPk, gov.sir.core.negocio.modelo.Usuario, gov.sir.core.negocio.modelo.Folio.FolioPk)
     * @ejb:interface-method view-type="both"
     */
    public LlaveBloqueo delegarBloqueoFolio(gov.sir.core.negocio.modelo.TurnoPk oid, Usuario usuario, FolioPk fid) throws ForsetiException {
        return forseti.delegarBloqueoFolio(oid, usuario, fid);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#updateCiudadano(gov.sir.core.negocio.modelo.Ciudadano, gov.sir.core.negocio.modelo.Usuario, java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public boolean updateCiudadano(Ciudadano ciud, Usuario usuario, String numRadicacion) throws ForsetiException {
        return forseti.updateCiudadano(ciud, usuario, numRadicacion);
    }
    
    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#updateCiudadanoAdministrativa(gov.sir.core.negocio.modelo.Ciudadano, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public boolean updateCiudadanoAdministrativa(Ciudadano ciud, Usuario usuario) throws ForsetiException {
        return forseti.updateCiudadanoAdministrativa(ciud, usuario);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#isCiudadanoInAnotacionDefinitiva(gov.sir.core.negocio.modelo.Ciudadano.CiudadanoPk)
     * @ejb:interface-method view-type="both"
     */
    public boolean isCiudadanoInAnotacionDefinitiva(gov.sir.core.negocio.modelo.CiudadanoPk ciudID) throws ForsetiException {
        return forseti.isCiudadanoInAnotacionDefinitiva(ciudID);
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getCiudadanoByIdTMP(gov.sir.core.negocio.modelo.Ciudadano.CiudadanoPk)
     * @ejb:interface-method view-type="both"
     */
    public Ciudadano getCiudadanoByIdTMP(gov.sir.core.negocio.modelo.CiudadanoPk oid) throws ForsetiException {
        return forseti.getCiudadanoByIdTMP(oid);
    }

	/**
	 * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getTexto(gov.sir.core.negocio.modelo.Texto.TextoPk)
	 * @ejb:interface-method view-type="both"
	 */
	public Texto getTexto(gov.sir.core.negocio.modelo.TextoPk oid) throws ForsetiException {
		return forseti.getTexto(oid);
	}

	/**
	 * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#addTexto(gov.sir.core.negocio.modelo.Texto)
	 * @ejb:interface-method view-type="both"
	 */
	public gov.sir.core.negocio.modelo.TextoPk addTexto(Texto texto) throws ForsetiException {
		return forseti.addTexto(texto);
	}

	/**
	 * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#updateTexto(gov.sir.core.negocio.modelo.Texto.TextoPk, java.lang.String)
	 * @ejb:interface-method view-type="both"
	 */
	public boolean updateTexto(gov.sir.core.negocio.modelo.TextoPk cid, String nuevoTexto) throws ForsetiException {
		return forseti.updateTexto(cid, nuevoTexto);
	}

	/**
	 * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getTiposImprimible()
	 * @ejb:interface-method view-type="both"
	 */
	public List getTiposImprimible() throws ForsetiException {
		return forseti.getTiposImprimible();
	}

	/**
	 * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getCirculoImpresorasXTipoImprimible(java.lang.String, java.lang.String)
	 * @ejb:interface-method view-type="both"
	 */
	public List getCirculoImpresorasXTipoImprimible(String idCirculo, String idTipoImprimible) throws ForsetiException {
		return forseti.getCirculoImpresorasXTipoImprimible(idCirculo,idTipoImprimible);
	}

	/**
	 * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getConfiguracionImpresoras(java.lang.String)
	 * @ejb:interface-method view-type="both"
	 */
	public Hashtable getConfiguracionImpresoras(String idCirculo) throws ForsetiException {
		return forseti.getConfiguracionImpresoras(idCirculo);
	}

	/**
	 * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#setConfiguracionImpresoras(java.util.Hashtable, java.lang.String)
	 * @ejb:interface-method view-type="both"
	 */
	public boolean setConfiguracionImpresoras(Hashtable nuevaConfiguracion, String idCirculo) throws ForsetiException {
		return forseti.setConfiguracionImpresoras(nuevaConfiguracion,idCirculo);
	}

	/**
	 * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#desbloquearFolio(gov.sir.core.negocio.modelo.Folio)
	 * @ejb:interface-method view-type="both"
	 */
	public boolean desbloquearFolio(Folio folio) throws ForsetiException {
		return forseti.desbloquearFolio(folio);
	}

       /**
        * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#validarFolioTieneAnotacionesconOrdenRepetido(gov.sir.core.negocio.modelo.Folio.FolioPk)
        * @ejb:interface-method view-type="both"
        */
       public boolean validarFolioTieneAnotacionesconOrdenRepetido( gov.sir.core.negocio.modelo.FolioPk folioId ) throws ForsetiException {
               return forseti.validarFolioTieneAnotacionesconOrdenRepetido( folioId );
       }

       /**
        * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getCiudadanoUltimosFolio(java.lang.String)
        * @ejb:interface-method view-type="both"
        */
   		public List getCiudadanoUltimosFolio( String idMatricula ) throws ForsetiException {
   			return forseti.getCiudadanoUltimosFolio( idMatricula );
   		}
   /**
    * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#eliminarOficinaOrigen(gov.sir.core.negocio.modelo.OficinaOrigen.OficinaOrigenPk)
    * @ejb:interface-method view-type="both"
    */
	public boolean eliminarOficinaOrigen(gov.sir.core.negocio.modelo.OficinaOrigenPk oficinaID) throws ForsetiException {
		return forseti.eliminarOficinaOrigen(oficinaID);
	}




       /**
        * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#countSalvedadesFolio(java.lang.String, java.lang.String, gov.sir.core.negocio.modelo.Usuario)
        * @ejb:interface-method view-type="both"
        */
       public long countSalvedadesFolio( String idMatricula, String idWorkflow, Usuario usuario ) throws ForsetiException {
           return forseti.countSalvedadesFolio( idMatricula, idWorkflow, usuario );
       }

       /**
        * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#countSalvedadesAnotacion(java.lang.String, java.lang.String, gov.sir.core.negocio.modelo.Usuario)
        * @ejb:interface-method view-type="both"
        */
       public long countSalvedadesAnotacion( String idMatricula, String idWorkflow, Usuario usuario ) throws ForsetiException {
           return forseti.countSalvedadesAnotacion( idMatricula, idWorkflow, usuario );
       }

       /**
        * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getOficinasOrigenByMunicipio( gov.sir.core.negocio.modelo.Municipio.MunicipioPk )
        * @ejb:interface-method view-type="both"
        */
       public List getOficinasOrigenByMunicipio( gov.sir.core.negocio.modelo.MunicipioPk oid ) throws ForsetiException {
           return forseti.getOficinasOrigenByMunicipio( oid );
       }

       /**
        * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#crearCiudadano(gov.sir.core.negocio.modelo.Ciudadano)
        * @ejb:interface-method view-type="both"
        */
	public Ciudadano crearCiudadano(Ciudadano ciudadano) throws ForsetiException {
		return forseti.crearCiudadano(ciudadano);
	}
       /**
        * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getCirculosByUsuario(gov.sir.core.negocio.modelo.Usuario.UsuarioPk)
        * @ejb:interface-method view-type="both"
        */
      public List getCirculosByUsuario(gov.sir.core.negocio.modelo.UsuarioPk oid) throws ForsetiException {
          return forseti.getCirculosByUsuario( oid );
      }

      /**
       * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getAnotacionById(gov.sir.core.negocio.modelo.Anotacion.AnotacionPk)
       * @ejb:interface-method view-type="both"
       */
	public Anotacion getAnotacionById( gov.sir.core.negocio.modelo.AnotacionPk oid ) throws ForsetiException {
		return forseti.getAnotacionById( oid );
	}
	
	  /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getAnotacion(gov.sir.core.negocio.modelo.Anotacion.AnotacionPk)
     * @ejb:interface-method view-type="both"
     */
	public Anotacion getAnotacion( gov.sir.core.negocio.modelo.AnotacionPk oid ) throws ForsetiException {
		return forseti.getAnotacion( oid );
	}

	/**
	 * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#trasladadoFolio(String)
	 * @ejb:interface-method view-type="both"
	 */
	public boolean trasladadoFolio(String matricula) throws ForsetiException {
		return forseti.trasladadoFolio(matricula);
	}

	/**
	 * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#inhabilitarCirculo(Circulo, Circulo, List, Map, List, List, Usuario)
	 * @ejb:interface-method view-type="both"
	 */
	public void inhabilitarCirculo(Circulo circuloOrigen, Circulo circuloDestino, List zonasRegistrales, Map usuariosCrear, List usuariosTrasladar, List usuariosRolConsulta, Usuario usuarioInhabilita) throws ForsetiException {
		forseti.inhabilitarCirculo(circuloOrigen, circuloDestino, zonasRegistrales, usuariosCrear, usuariosTrasladar, usuariosRolConsulta,usuarioInhabilita);
	}

	/**
	 * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getCirculosInhabilitados(Circulo)
	 * @ejb:interface-method view-type="both"
	 */
	public List getCirculosInhabilitados(Circulo circuloDestino) throws ForsetiException {
		return forseti.getCirculosInhabilitados(circuloDestino);
	}
	
	/**
	 * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#validarPrincipioPrioridadFirma(List)
	 * @ejb:interface-method view-type="both"
	 */
      public void validarPrincipioPrioridadFirma(List turnos) throws ForsetiException {
  		   forseti.validarPrincipioPrioridadFirma(turnos);
  	}
      
      /**
  	 * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#validarPrincipioPrioridadDevolucion(List)
  	 * @ejb:interface-method view-type="both"
  	 */
        public void validarPrincipioPrioridadDevolucion(List turnos) throws ForsetiException {
    		   forseti.validarPrincipioPrioridadDevolucion(turnos);
    	}
      
      /**
  	 * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#validarPrincipioPrioridadFirmaRelacion(List)
  	 * @ejb:interface-method view-type="both"
  	 */
     public Hashtable validarPrincipioPrioridadFirmaRelacion(List turnos) throws ForsetiException {
    	   return forseti.validarPrincipioPrioridadFirmaRelacion(turnos);
     }
      
      
      /**
  	 * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#validarTurnoFirmaPrincipioPrioridad(List)
  	 * @ejb:interface-method view-type="both"
  	 */
        public boolean validarTurnoFirmaPrincipioPrioridad(List turnos) throws ForsetiException {
    		   return forseti.validarTurnoFirmaPrincipioPrioridad(turnos);
    	}
      
      

      /**
  	 * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#trasladarFolios(Circulo, Circulo, int, int)
  	 * @ejb:interface-method view-type="both"
  	 */
	public void trasladarFolios(Circulo circuloOrigen, Circulo circuloDestino, int trasladoMasivoInicio, int trasladoMasivoFin) throws ForsetiException {
		forseti.trasladarFolios(circuloOrigen, circuloDestino, trasladoMasivoInicio, trasladoMasivoFin);
	}

    /**
  	 * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#validarMatriculaCrearFolio(Folio datos)
  	 * @ejb:interface-method view-type="both"
  	 */
	public void validarMatriculaCrearFolio(Folio datos) throws ForsetiException {
		forseti.validarMatriculaCrearFolio(datos);
	}
    
	 /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#validarAnotacionesCiudadano(List anotacionesCiudadano)
     * @ejb:interface-method view-type="both"
  	 */
    public boolean validarAnotacionesCiudadano(List anotacionesCiudadano)throws ForsetiException {
    	return (forseti.validarAnotacionesCiudadano(anotacionesCiudadano));
    }
	/**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#updateFolio(gov.sir.core.negocio.modelo.Folio, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public boolean updateFolioCreacionDirecta(Folio folio, Usuario usuario) throws ForsetiException {
        return forseti.updateFolioCreacionDirecta(folio, usuario);
    }

	/**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#updateAnotacionesCreacionDirecta(gov.sir.core.negocio.modelo.Anotacion, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
     public boolean updateAnotacionesCreacionDirecta(Anotacion anotacion, Usuario usuario)  throws ForsetiException {
    	 return forseti.updateAnotacionesCreacionDirecta(anotacion, usuario);
     }

	/**
	 *  
	 * @ejb:interface-method view-type="both"
	 */
	public Folio getFolioEvenTempByIDSinAnotaciones(FolioPk oid) throws ForsetiException {
		return forseti.getFolioEvenTempByIDSinAnotaciones(oid);
	}

	/**
	 *  
	 * @ejb:interface-method view-type="both"
	 */
	public String migrarFolioByMatricula(String matricula, Usuario usuario) throws ForsetiException {		
		return forseti.migrarFolioByMatricula(matricula,usuario);
	}

	/**
	 *  
	 * @ejb:interface-method view-type="both"
	 */
	public Turno migrarTurnoNumero(String numTurno) throws ForsetiException {
		return forseti.migrarTurnoNumero(numTurno);
	}

	/**
	 *  
	 * @ejb:interface-method view-type="both"
	 */
	public ActoresRepartoNotarial getActoresRepartoNotarial(gov.sir.core.negocio.modelo.ActoresRepartoNotarialPk oid) throws ForsetiException {
		return forseti.getActoresRepartoNotarial(oid);
	}

	/**
	 *  
	 * @ejb:interface-method view-type="both"
	 */	
	public List getFoliosAnotaActNomenclaturaSinActualizarBySolicitud(gov.sir.core.negocio.modelo.SolicitudPk oid, Usuario usuario) throws ForsetiException {
		return forseti.getFoliosAnotaActNomenclaturaSinActualizarBySolicitud(oid, usuario);
	}
	
	/**
	 *  
	 * @ejb:interface-method view-type="both"
	 */	
	public boolean updateFolioSalvedades(Folio folio, Usuario usuario) throws ForsetiException
	{
		return forseti.updateFolioSalvedades(folio, usuario);
	}
	
	/**
	 *  
	 * @ejb:interface-method view-type="both"
	 */	
	public List getTurnosCorreccionActivosFolio(Folio fol, Turno turnoNoValidar) throws ForsetiException{
		return forseti.getTurnosCorreccionActivosFolio(fol, turnoNoValidar);
	}
	
	/**
	 *  
	 * @ejb:interface-method view-type="both"
	 */
	public boolean isUltimoPropietario(AnotacionCiudadano anota)throws ForsetiException{
		return forseti.isUltimoPropietario(anota);
	}
	
	
	/**
	 *  
	 * @ejb:interface-method view-type="both"
	 */
	public void eliminarFolioCreacionDirecta(Folio datos, Usuario usuario) throws ForsetiException{
		forseti.eliminarFolioCreacionDirecta(datos, usuario);
	}
	
	
	/**
	 *  
	 * @ejb:interface-method view-type="both"
	 */
	public boolean desbloquearFoliosEntradaCalificacion(List lstMatricula, Usuario usuario)throws ForsetiException{
		return forseti.desbloquearFoliosEntradaCalificacion(lstMatricula,usuario);
	}	
	
	/**
	 *  Actualiza folio derivado
	 * @ejb:interface-method view-type="both"
	 */
	public void updateFolioDerivado(List foliosDerivadosModificados,Folio folioPadre, Usuario usuario) throws ForsetiException{
		forseti.updateFolioDerivado(foliosDerivadosModificados,folioPadre, usuario);
	}	
	
	/**
	 *  Retorna una lista de folios derivados temporales para una matrícula padre
	 * @ejb:interface-method view-type="both"
	 */
	public List getFoliosDerivadosTMPByMatricula(String matriculaPadre) throws ForsetiException{
		return forseti.getFoliosDerivadosTMPByMatricula(matriculaPadre);
	}		

	/**
	 *
	 * @ejb:interface-method view-type="both"
	 */
    public boolean deshacerCambiosFolioByTurno(TurnoPk turnoID, Usuario usuario, boolean desbloquear) throws ForsetiException {
        return forseti.deshacerCambiosFolioByTurno(turnoID, usuario, desbloquear);
    }

/*Adiciona Funcionalidad Boton de Pago
 * Author: Ingeniero Diego Hernandez
 * Modificacion en: 2010/02/23 by jvenegas
	 *
	 * @ejb:interface-method view-type="both"
	 */
	public List getAlertasMatriculas(String idMatricula) throws ForsetiException{
		return forseti.getAlertasMatriculas(idMatricula);
	}


    /**
     * @author      :   Henry Gómez Rocha y Fernando Padilla
     * @change      :   Se declara el método getFolioByID(), el cual evita que al momento de
     *                  pasar del role Calificador al role Digitador no se verifique que
     *                  usuario tiene el bloqueo del folio.
     * Caso Mantis  :   0004967
     * @ejb:interface-method view-type="both"
     */
    public Folio getFolioByID(FolioPk oid, Usuario usuario, boolean validarBloqueo) throws ForsetiException {
        return forseti.getFolioByID(oid, usuario, validarBloqueo);
    }


    /**
     * @author      :   Julio Alcazar
     * @change      :   Se declara el método anuladoFolio(), el cual verifica si un folio ha sido anulado
     * Caso Mantis  :   0007123
     * @ejb:interface-method view-type="both"
     */
    public boolean anuladoFolio(String matricula)throws ForsetiException {
        return forseti.anuladoFolio(matricula);
    }
     /**
     * @author      :   Carlos mario Torres Urina
     * @change      :   Se declara el método getAnotacionTMP(), el cual retorna la anotacion temporal
     * @actaReq    : Requerimiento No 089_151_Proceso_correcciones_permitir_varias_correcciones.
     * Caso Mantis  :   11767
     * @ejb:interface-method view-type="both"
     */
    public Anotacion getAnotacionTMP(FolioPk oid, String idanotacion) throws ForsetiException {
        return forseti.getAnotacionTMP(oid, idanotacion);
    }

    /**
     * @Autor: Edgar Lora
     * @Mantis: 13176
     * @ejb:interface-method view-type="both"
     */
    public NaturalezaJuridicaEnhanced getNaturalezaJuridicaById(NaturalezaJuridicaEnhancedPk pk) throws ForsetiException {
        return forseti.getNaturalezaJuridicaById(pk);
    }
	
	/**
     * @author      :   Carlos mario Torres Urina
     * @change      :   Se declara el método getGruposNaturalezaJuridicaAll()
     * @actaReq    :  Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
     * Caso Mantis  :   0012705
     * @ejb:interface-method view-type="both"
     */

    public List getGruposNaturalezaJuridicaAll() throws ForsetiException  {
        return forseti.getGruposNaturalezaJuridicaAll();
    }
/**
     * @author Carlos Torres
     * @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
     * @ejb:interface-method view-type="both"
     */
    public OficinaOrigen getOficinaOrigen(String idOficina) throws ForsetiException {
        return forseti.getOficinaOrigen(idOficina);
    }
}
