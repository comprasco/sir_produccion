package gov.sir.hermod;

import org.auriga.core.properties.BasicProperties;
import com.is21.util.cifrar.CargarPropertiesCifrado;

/**
 *
 * @author mrios
 */
public class HermodProperties extends BasicProperties implements HermodPropertiesMBean {

    /**
     * Constructora
     */
    private HermodProperties() {
        super("gov.sir.hermod.properties");
        try {
            ClassLoader loader = HermodProperties.class.getClassLoader();
            CargarPropertiesCifrado cpc = new CargarPropertiesCifrado("gov.sir.hermod.properties", loader);
            prop = cpc.desencriptar();
        } catch (Exception e) {
        }
    }

    /**
     * Obtiene la instancia de HermodProperties
     */
    public static synchronized HermodProperties getInstancia() {
        if (INSTANCIA == null) {
            INSTANCIA = new HermodProperties();
        }
        return INSTANCIA;
    }

    private static HermodProperties INSTANCIA = null;
    public static final String HERMOD_DAO_FACTORY = "gov.sir.hermod.dao.factory";
    public static final String HERMOD_MAYOR_EXTENSION = "gov.sir.hermod.mayor_extension";
    public static final String HERMOD_WF_FACTORY = "gov.sir.hermod.wf.factory";
    public static final String HERMOD_WORKFLOW_NAME = "gov.sir.hermod.wf.name";
    public static final String HERMOD_PROCESO_CERTIFICADOS = "gov.sir.hermod.wf.certificados";
    public static final String HERMOD_PROCESO_REGISTRO = "gov.sir.hermod.wf.registro";
    public static final String HERMOD_RUTA_FIRMAS_REGISTRADORES = "gov.sir.hermod.firmas.path";
    public static final String HERMOD_RUTA_FIRMAS_REGISTRADORES_A_ASOCIAR = "gov.sir.hermod.firmasaasociar.path";
    public static final String HERMOD_CONTENT_TYPE = "gov.sir.hermod.firmas.content";
    public static final String HERMOD_NUMERO_IMPRESIONES_CERTIFICADOS = "gov.sir.hermod.certificados.maximo.copias";
    public static final String HERMOD_TEXTO_EXENTO = "gov.sir.hermod.certificados.exentos.texto";

    public static final String URL_SERVLET_REPORTES = "gov.sir.hermod.reportes.servlet.url";
    public static final String JDO_JCA = "gov.sir.hermod.jca";
    public static final String P_JNDI_NAME = "gov.sir.hermod.pmf.jndi";

    public static final String VALIDAR_SESION_IMPRESION = "gov.sir.hermod.validar.impresion";

    public static final String SECUENCIALES_POR_SECUENCIA = "gov.sir.hermod.secuencias.tablas";
    public static final String RUTA_REPORTES_JASPER = "gov.sir.hermod.jasper.ruta";

    public static final String HERMOD_SGD_CIRCULOS = "gov.sir.hermod.SGD.circulos";

    public static final String HERMOD_CIRCULOS_IMPUESTO_META = "gov.sir.hermod.circulos.impuesto.meta";

    public static final String HERMOD_RUTA_TEMP_GENERACION = "gov.sir.hermod.generacion.path";

    public static final String HERMOD_MAX_MATRICULAS_PDF = "gov.sir.hermod.max.matriculas.pdf";
    /**
     * @author ctorres
     **@requerimiento Tarifa Diferencia * * 
          *
     */
    public static final String HERMOD_TARIFA_DIFERENCIAL = "gov.sir.hermod.tarifa_diferencial";

    /**
     * @author David Panesso
     * @change 1241.ADAPTACION DEL PROCESO DE AUTENTICACIÓN Y SSO DE CA
     * SITEMINDER
     */
    public static final String URL_CA_LOGOUT = "gov.sir.hermod.ca.logout.url";
}
