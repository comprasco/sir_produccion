package gov.sir.core.web.navegacion.administracion;

import java.util.ArrayList;
import java.util.List;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.administracion.AWReportes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;
/**
 * Control de Navegación para <CODE>AWReportes</CODE>
 *
 * @author jvelez
 *
 */
public class ControlNavegacionReportes implements ControlNavegacion {

	protected static final String LISTA_REPORTES = "LISTA_REPORTES";


        // id-navegacion
        public static final String CONSULTA_OFICINA_ORIGEN_BY_MUNICIPIO__EVENTRESP_OK
            = "CONSULTA_OFICINA_ORIGEN_BY_MUNICIPIO__EVENTRESP_OK";
        // id-navegacion
        public static final String CONSULTA_OFICINA_ORIGEN_BY_VEREDA__EVENTRESP_OK_31
            = "CONSULTA_OFICINA_ORIGEN_BY_VEREDA__EVENTRESP_OK_31";
        public static final String CONSULTA_OFICINA_ORIGEN_BY_MUNICIPIO__EVENTRESP_OK_154
            = "CONSULTA_OFICINA_ORIGEN_BY_MUNICIPIO__EVENTRESP_OK_154";
        public static final String CONSULTA_OFICINA_ORIGEN_BY_MUNICIPIO__EVENTRESP_OK_26
        = "CONSULTA_OFICINA_ORIGEN_BY_MUNICIPIO__EVENTRESP_OK_26";


	// report xx - graph ------------------------------
	protected static final String REPORTE_XX__BACK__EVENTRESP_OK = LISTA_REPORTES; //same side
	// report yy - graph ------------------------------
	protected static final String REPORTE_YY__SEND__EVENTRESP_OK = "REPORTE_YY__SEND__EVENTRESP_OK"; //same side

	// report 01 - graph ------------------------------
	protected static final String REPORTE_01__SEND__EVENTRESP_OK = "REPORTE_01__SEND__EVENTRESP_OK";
	protected static final String REPORTE_01__BACK__EVENTRESP_OK = REPORTE_XX__BACK__EVENTRESP_OK;
	// ------------------------------------------------
	// report 04 - graph ------------------------------
	protected static final String REPORTE_04__SEND__EVENTRESP_OK = "REPORTE_04__SEND__EVENTRESP_OK";
	protected static final String REPORTE_04__BACK__EVENTRESP_OK = REPORTE_XX__BACK__EVENTRESP_OK;
	// ------------------------------------------------
	protected static final String REPORTE_RUPTA01__SEND__EVENTRESP_OK = "REPORTE_RUPTA01__SEND__EVENTRESP_OK";
	protected static final String REPORTE_RUPTA01__BACK__EVENTRESP_OK = REPORTE_XX__BACK__EVENTRESP_OK;
	//
	protected static final String REPORTE_157__SEND__EVENTRESP_OK = "REPORTE_157__SEND__EVENTRESP_OK";
	protected static final String REPORTE_157__BACK__EVENTRESP_OK = REPORTE_XX__BACK__EVENTRESP_OK;
	
	
	
	// report 04 - graph ------------------------------
	protected static final String REPORTE_05__SEND__EVENTRESP_OK = "REPORTE_05__SEND__EVENTRESP_OK";
	protected static final String REPORTE_05__BACK__EVENTRESP_OK = REPORTE_XX__BACK__EVENTRESP_OK; //same side
	// ------------------------------------------------
        // report-30 - graph
        protected static final String REPORTE_30__ONSELECT_ZONAREGISTRAL_DEPARTAMENTO__EVENTRESP_OK = "REPORTE_30__ONSELECT_ZONAREGISTRAL_DEPARTAMENTO__EVENTRESP_OK";
        protected static final String REPORTE_30__ONSELECT_ZONAREGISTRAL_MUNICIPIO__EVENTRESP_OK    = "REPORTE_30__ONSELECT_ZONAREGISTRAL_MUNICIPIO__EVENTRESP_OK" ;
        protected static final String REPORTE_30__ONSELECT_ZONAREGISTRAL_VEREDA__EVENTRESP_OK       = "REPORTE_30__ONSELECT_ZONAREGISTRAL_VEREDA__EVENTRESP_OK";
        // ------------------------------------------------
        // report-rel-01 - graph
        public static final String REPORTE_REL01__FINDRELATEDFASES_EVENTRESP_OK = "REPORTE_REL01__FINDRELATEDFASES_EVENTRESP_OK";


	protected static final String REPORTE_00__BACK__EVENTRESP_OK = "REPORTE_00__BACK__EVENTRESP_OK";

	public static final String SELECCIONA_DEPARTAMENTO_OK = "SELECCIONA_DEPARTAMENTO_OK";

	public static final String SELECCIONA_MUNICIPIO_OK = "SELECCIONA_MUNICIPIO_OK";

        /*AHERRENO 09/04/2013
        REQ 064_453 DUPLICIDAD TURNOS*/
        public static final String REPORTE_01_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK = "REPORTE_01_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

        public static final String REPORTE_06_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_06_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_04_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_04_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_05_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_05_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_6B_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_6B_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_07_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_07_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_08_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_08_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_09_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_09_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_10_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_10_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_11_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_11_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_15_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_15_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_16_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_16_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_17_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_17_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_18_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_18_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_19_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_19_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_20_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_20_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_21_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_21_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_22_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_22_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_24_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_24_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_25_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_25_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";
  
  public static final String REPORTE_25_LOADUSUARIOSCAJEROSBYCIRCULO_OK =
	  "REPORTE_25_LOADUSUARIOSCAJEROSBYCIRCULO_OK";

  public static final String REPORTE_26_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_26_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_27_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_27_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_30_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_30_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_31_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_31_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_154_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_154_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_32_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_32_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_37_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_37_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_39_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_39_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_45_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_45_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_46_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_46_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_49_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_49_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_51_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_51_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

    public static final String REPORTE_51B_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_51B_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_57_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_57_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_69_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_69_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_74_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_74_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_79_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_79_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_87_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_87_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_88_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_88_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_89_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_89_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_90_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_90_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_91_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_91_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_92_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_92_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_93_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_93_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_94_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_94_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_99_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_99_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_109_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_109_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_110_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_110_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_121_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_121_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_123_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_123_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_124_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_124_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_142_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_142_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_145_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_145_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_151_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_151_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_155_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
    "REPORTE_155_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_156_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
  "REPORTE_156_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_REL02_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_REL02_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_G01_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_G01_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_G02_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_G02_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_G02_LOADUSUARIOBYCIRCULO_EVENTRESP_OK =
      "REPORTE_G02_LOADUSUARIOBYCIRCULO_EVENTRESP_OK";
  
  public static final String REPORTE_180_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_180_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";  
 /**
  * @Autor: Nilson Olaya Gómez
  *  se agrega Reporte Correccion Formas de Pago Canales Recaudo
  */  
  public static final String REPORTE_182_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_182_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";
  
  public static final String REPORTE_181_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_181_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";
  
  /**
   * @Autor: Santiago Vásquez
   * @Change: 1252.REPORTE TURNOS CORRECCION ENCABEZADO
   **/
  public static final String REPORTE_178_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_178_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_178_LOADUSUARIOBYCIRCULO_EVENTRESP_OK =
      "REPORTE_178_LOADUSUARIOBYCIRCULO_EVENTRESP_OK";
  
  /**
   * @Autor: Santiago Vásquez
   * @Change: 2015.REPORTE.ESTADISTICO.POR.NATURALEZA.JURIDICA
   **/
  public static final String REPORTE_179_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_179_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_179_LOADNATJUR_EVENTRESP_OK =
      "REPORTE_179_LOADNATJUR_EVENTRESP_OK";

  public static final String REPORTE_G03_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_G03_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_G03_LOADUSUARIOBYCIRCULO_EVENTRESP_OK =
      "REPORTE_G03_LOADUSUARIOBYCIRCULO_EVENTRESP_OK";

  public static final String REPORTE_G04_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_G04_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_G05_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_G05_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_G06_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_G06_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";
  
  public static final String REPORTE_94_LOADUSUARIOSCAJEROSBYCIRCULO_OK =
	  "REPORTE_94_LOADUSUARIOSCAJEROSBYCIRCULO_OK";
  
  public static final String REPORTE_RUPTA01_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_RUPTA01_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";
  
  public static final String REPORTE_157_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_157_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";
  
  public static final String REPORTE_158_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_158_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";
  
  public static final String REPORTE_159_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_159_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";
  
  public static final String REPORTE_160_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_160_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK"; 
  
  public static final String REPORTE_160_LOADUSUARIOSCAJEROSBYCIRCULO_OK =
	  "REPORTE_160_LOADUSUARIOSCAJEROSBYCIRCULO_OK";

  //AHERRENO 19/08/2009
  //Reporte Folios Masivos
  public static final String REPORTE_FOLIOS_MAS_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_FOLIOS_MAS_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  //AHERRENO 22/09/2009
  //Reporte E01
  public static final String REPORTE_E01_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_E01_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  //AHERRENO 22/09/2009
  //Reporte 161
  public static final String REPORTE_161_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_161_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";
  // report-161 - graph
  protected static final String REPORTE_161__ONSELECT_CIRCULO_MUNICIPIOS_EVENTRESP_OK = "REPORTE_161__ONSELECT_CIRCULO_MUNICIPIOS_EVENTRESP_OK";
  protected static final String REPORTE_161__ONSELECT_ZONAREGISTRAL_MUNICIPIO__EVENTRESP_OK    = "REPORTE_161__ONSELECT_ZONAREGISTRAL_MUNICIPIO__EVENTRESP_OK" ;
  protected static final String REPORTE_161__ONSELECT_ZONAREGISTRAL_VEREDA__EVENTRESP_OK       = "REPORTE_161__ONSELECT_ZONAREGISTRAL_VEREDA__EVENTRESP_OK";
  protected static final String REPORTE_161__ONSELECT_NATURALEZA_JURIDICA__EVENTRESP_OK       = "REPORTE_161__ONSELECT_NATURALEZA_JURIDICA__EVENTRESP_OK";
  
  //AHERRENO 26/04/2010
  //Reporte E02
  public static final String REPORTE_E02_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_E02_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  //AHERRENO 02/08/2010
  //Reporte E03
  public static final String REPORTE_E03_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_E03_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  //AHERRENO 05/01/2011
  //Reporte 162
  public static final String REPORTE_162_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_162_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  //AHERRENO 15/02/2011
  //Reporte 163
  public static final String REPORTE_163_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_163_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  //AHERRENO 23/02/2011
  //Reporte 164
  public static final String REPORTE_164_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_164_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  //AHERRENO 14/03/2011
  //Reporte 165
  public static final String REPORTE_165_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_165_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  //AHERRENO 14/03/2011
  //Reporte 166
  public static final String REPORTE_166_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_166_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  //AHERRENO 14/03/2011
  //Reporte 167
  public static final String REPORTE_167_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_167_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  //AHERRENO 14/03/2011
  //Reporte 168
  public static final String REPORTE_168_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_168_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";
  
  //CTORRES 24/04/2013
  //Reporte 175
  public static final String REPORTE_175_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_175_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";
  
  //CTORRES 24/04/2013
  //Reporte 176
  public static final String REPORTE_176_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_176_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";
  //GCABRERA 28/12/2011
  //Reporte E04
public static final String REPORTE_E04_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_E04_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";

  public static final String REPORTE_E04_LOADUSUARIOBYCIRCULO_EVENTRESP_OK =
      "REPORTE_E04_LOADUSUARIOBYCIRCULO_EVENTRESP_OK";

    /**
    * Autor: Edgar Lora
    * Mantis: 0011319
    * Requerimiento: 075_151
    */
    public static final String REPORTE_170_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK =
      "REPORTE_170_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK";
  // ------------------------------------------------

  private static final List listaReportesJasper = new ArrayList();


private static final String REPORTES_JASPER_SERVLET = "REPORTES_JASPER_SERVLET";
	
	static {
		
		listaReportesJasper.add(AWReportes.REPORTE_01__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_04__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_05__SEND_ACTION);
		
		listaReportesJasper.add(AWReportes.REPORTE_6B__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_07__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_08__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_09__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_10__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_11__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_15__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_16__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_17__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_18__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_19__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_20__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_21__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_22__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_24__SEND_ACTION);
		
		listaReportesJasper.add(AWReportes.REPORTE_26__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_27__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_30__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_31__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_32__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_37__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_39__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_45__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_46__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_49__SEND_ACTION);
		
		
		
		
		listaReportesJasper.add(AWReportes.REPORTE_38__SEND_ACTION);
		
		//listaReportesJasper.add(AWReportes.REPORTE_40__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_41__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_42__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_43__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_44__SEND_ACTION);
		
		listaReportesJasper.add(AWReportes.REPORTE_48__SEND_ACTION);
		
		listaReportesJasper.add(AWReportes.REPORTE_57__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_69__SEND_ACTION);
		//listaReportesJasper.add(AWReportes.REPORTE_73__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_81__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_91__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_92__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_93__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_94__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_95__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_96__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_99__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_109__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_110__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_121__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_123__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_124__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_131__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_142__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_145__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_154__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_156__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_G01__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_G02__SEND_ACTION);
		/**
		 * @Autor: Santiago Vásquez
		 * @Change: 1252.REPORTE TURNOS CORRECCION ENCABEZADO
		**/
		listaReportesJasper.add(AWReportes.REPORTE_178__SEND_ACTION);
                /**
		 * @Autor: Santiago Vásquez
		 * @Change: 2015.REPORTE.ESTADISTICO.POR.NATURALEZA.JURIDICA
		**/
                listaReportesJasper.add(AWReportes.REPORTE_180__SEND_ACTION);                
                listaReportesJasper.add(AWReportes.REPORTE_182__SEND_ACTION);                
                listaReportesJasper.add(AWReportes.REPORTE_181__SEND_ACTION);               
                
		listaReportesJasper.add(AWReportes.REPORTE_179__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_G03__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_G04__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_G05__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_G06__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_REL01__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_REL02__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_06__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_25__SEND_ACTION);
		
		listaReportesJasper.add(AWReportes.REPORTE_51__SEND_ACTION);
                listaReportesJasper.add(AWReportes.REPORTE_51B__SEND_ACTION);
		
		listaReportesJasper.add(AWReportes.REPORTE_79__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_155__SEND_ACTION);
		
		listaReportesJasper.add(AWReportes.REPORTE_74__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_35__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_87__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_88__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_89__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_90__SEND_ACTION);
		
		listaReportesJasper.add(AWReportes.REPORTE_97__SEND_ACTION);		
		listaReportesJasper.add(AWReportes.REPORTE_151__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_155__SEND_ACTION);
		
		listaReportesJasper.add(AWReportes.REPORTE_157__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_158__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_159__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_RUPTA01__SEND_ACTION);
		listaReportesJasper.add(AWReportes.REPORTE_160__SEND_ACTION);
        //AHERRENO 19/08/2009
        //Reporte Folios Masivos
        listaReportesJasper.add(AWReportes.REPORTE_FOLIOS_MAS__SEND_ACTION);
        //AHERRENO 22/09/2009
        //Reporte E01
        listaReportesJasper.add(AWReportes.REPORTE_E01__SEND_ACTION);
        //AHERRENO 22/09/2009
        //Reporte 161
        listaReportesJasper.add(AWReportes.REPORTE_161__SEND_ACTION);
        //AHERRENO 26/04/2010
        //Reporte E02
        listaReportesJasper.add(AWReportes.REPORTE_E02__SEND_ACTION);
        //Reporte E03
        listaReportesJasper.add(AWReportes.REPORTE_E03__SEND_ACTION);
        //AHERRENO 05/01/2011
        //Reporte 162
        listaReportesJasper.add(AWReportes.REPORTE_162__SEND_ACTION);
        //AHERRENO 15/02/2011
        //Reporte 163
        listaReportesJasper.add(AWReportes.REPORTE_163__SEND_ACTION);
        //AHERRENO 23/02/2011
        //Reporte 164
        listaReportesJasper.add(AWReportes.REPORTE_164__SEND_ACTION);
        //AHERRENO 14/03/2011
        //Reporte 165
        listaReportesJasper.add(AWReportes.REPORTE_165__SEND_ACTION);
        //AHERRENO 14/03/2011
        //Reporte 166
        listaReportesJasper.add(AWReportes.REPORTE_166__SEND_ACTION);        
        //AHERRENO 14/03/2011
        //Reporte 167
        listaReportesJasper.add(AWReportes.REPORTE_167__SEND_ACTION);                
        //AHERRENO 14/03/2011
        //Reporte 168
        listaReportesJasper.add(AWReportes.REPORTE_168__SEND_ACTION);
        //CTORRES 24/04/2013
        //Reporte 175
        listaReportesJasper.add(AWReportes.REPORTE_175__SEND_ACTION);
        //CTORRES 24/04/2013
        //Reporte 176
        listaReportesJasper.add(AWReportes.REPORTE_176__SEND_ACTION);
        //Reporte E04
        listaReportesJasper.add(AWReportes.REPORTE_E04__SEND_ACTION);
        //AHERRENO 01/06/2012
        //Reporte 169
        listaReportesJasper.add(AWReportes.REPORTE_169__SEND_ACTION);
		/**
        * Autor: Edgar Lora
        * Mantis: 0011319
        * Requerimiento: 075_151
        */
        listaReportesJasper.add(AWReportes.REPORTE_170_SEND_ACTION);
        // -------------------------------------------------------------------
        //CTORRES 23/05/20012
        //Reporte exportar folios
        listaReportesJasper.add(AWReportes.REPORTE_EXPORTAR_FOLIOS_ARCHIVO_SEND);
 }
  /**
	 *  Constructor por Default de <CODE>ControlNavegacionReportes</CODE>
	 */
	public ControlNavegacionReportes() {
		super();
	}

	/**
	 * Prepara el procesamiento de la navegación.
	 * @param request
	 */
	public void doStart(HttpServletRequest request) {

	}

	/**
	 * Método que procesa la siguiente acción de navegación dentro del flujo de pantallas
	 *
	 * @param request
	 * @return nombre de la acción siguiente
	 * @throws ControlNavegacionException
	 */
	public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
		String accion = (String) request.getParameter(WebKeys.ACCION);
                
                //System.out.println(">>>>> DNilson226 gov.sir.core.web.acciones.administracion.AWReportes.perform() Accion Reporte180 <<<<<<<<<");
                
                System.out.println(">>>>> gov.sir.core.web.navegacion.administracion.ControlNavegacionReportes.procesarNavegacion() vlr accion  "+
                                    accion +" <<<<<<<");

		if ((accion == null) || accion.equals("")) {
			throw new ControlNavegacionException("La accion enviada por la accion web no es válida");
		}

		if(accion.equals(AWReportes.TERMINA)){
			return LISTA_REPORTES;
		}

		if(accion.equals(AWReportes.TERMINA2)){
			return LISTA_REPORTES;
		}
		if (
				accion.equals(AWReportes.SELECCIONA_DEPARTAMENTO)){
//				|| accion.equals(AWReportes.ADICIONA_MUNICIPIO)
//				|| accion.equals(AWReportes.ELIMINA_MUNICIPIO)
//				|| accion.equals(AWReportes.TERMINA_VEREDA)) {
			return ControlNavegacionReportes.SELECCIONA_DEPARTAMENTO_OK;
		}
		
		if(accion.equals(AWReportes.REPORTE_94_LOADUSUARIOSCAJEROSBYCIRCULO)){
			return ControlNavegacionReportes.REPORTE_94_LOADUSUARIOSCAJEROSBYCIRCULO_OK;
		}
		
		
			

		if (
				accion.equals(AWReportes.SELECCIONA_MUNICIPIO)){
//				|| accion.equals(AWReportes.ADICIONA_VEREDA)
//				|| accion.equals(AWReportes.ELIMINA_VEREDA)) {
			return ControlNavegacionReportes.SELECCIONA_MUNICIPIO_OK;
		}


                if( AWReportes.CONSULTA_OFICINA_ORIGEN_BY_MUNICIPIO__ACTION.equals( accion ) ) {
                  // return id-navegacion
                  return CONSULTA_OFICINA_ORIGEN_BY_MUNICIPIO__EVENTRESP_OK;
                }
                if( AWReportes.CONSULTA_OFICINA_ORIGEN_BY_VEREDA__ACTION31.equals( accion ) ) {
                  // return id-navegacion
                  return CONSULTA_OFICINA_ORIGEN_BY_VEREDA__EVENTRESP_OK_31;
                }
                if( AWReportes.CONSULTA_OFICINA_ORIGEN_BY_VEREDA__ACTION121.equals( accion ) ) {
                    // return id-navegacion
                    return REPORTE_121_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }
                if( AWReportes.CONSULTA_OFICINA_ORIGEN_BY_MUNICIPIO__ACTION154.equals( accion ) ) {
                  // return id-navegacion
                  return CONSULTA_OFICINA_ORIGEN_BY_MUNICIPIO__EVENTRESP_OK_154;
                }
                if( AWReportes.CONSULTA_OFICINA_ORIGEN_BY_MUNICIPIO__ACTION26.equals( accion ) ) {
                    // return id-navegacion
                    return CONSULTA_OFICINA_ORIGEN_BY_MUNICIPIO__EVENTRESP_OK_26;
                  }
		if (
				accion.equals(AWReportes.ADICIONA_OFICINA_ORIGEN)
			 || accion.equals(AWReportes.CONSULTA_OFICINA_ORIGEN_POR_VEREDA)
			 || accion.equals(AWReportes.SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_DEPTO)
			 || accion.equals(AWReportes.SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_MUNICIPIO)) {
				return AWReportes.ADICIONA_OFICINA_ORIGEN;
			}

		if (
				accion.equals(AWReportes.ADICIONA_OFICINA_ORIGEN)
			 || accion.equals(AWReportes.CONSULTA_OFICINA_ORIGEN_POR_VDA)
			 || accion.equals(AWReportes.SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_DTO)
			 || accion.equals(AWReportes.SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_MPIO)) {
				return AWReportes.ADICIONA_OFICINA_ORIGEN2;
			}
		if (
				accion.equals(AWReportes.ADICIONA_OFICINA_ORIGEN)
			 || accion.equals(AWReportes.CONSULTA_OFICINA_ORIGEN_X_VEREDA)
			 || accion.equals(AWReportes.SELECCIONA_ZONA_REGISTRAL_OF_ORIGEN_DEPTO)
			 || accion.equals(AWReportes.SELECCIONA_ZONA_REGISTRAL_OF_ORIGEN_MPIO)) {
				return AWReportes.ADICIONA_OFICINA_ORIGEN3;
			}
                if (
                                accion.equals(AWReportes.ADICIONA_OFICINA_ORIGEN)
                         || accion.equals(AWReportes.CONSULTA_OFICINA_ORIGEN_POR_VDA_154)
                         || accion.equals(AWReportes.SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_DTO_154)
                         || accion.equals(AWReportes.SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_MPIO_154))
{                             return AWReportes.ADICIONA_OFICINA_ORIGEN154;
                        }
		if (
			//accion.equals(AWReportes.ADICIONA_VALIDACION_NOTA)
				 accion.equals(AWReportes.CONSULTA_FASE_PROCESO)) {
			return AWReportes.ADICIONA_VALIDACION_NOTA;
		}
		if (accion.equals(AWReportes.SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_DTO_121)) {
				return REPORTE_121_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
			}
		if (accion.equals(AWReportes.USUARIOS_CONSULTAR_POR_CIRCULO_ROL)) {
				return AWReportes.USUARIOS_CONSULTAR;
			}

                if (accion.equals(AWReportes.USUARIOS_CONSULTAR_POR_CIRCULO_REL02)) {
                                return AWReportes.USUARIOS_CONSULTAR_REL02;
                        }


                // -- navegacion sobre pagina

                if( AWReportes.REPORTE_30__ONSELECT_ZONAREGISTRAL_DEPARTAMENTO_ACTION.equals( accion ) ) {
                   return REPORTE_30__ONSELECT_ZONAREGISTRAL_DEPARTAMENTO__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_30__ONSELECT_ZONAREGISTRAL_MUNICIPIO_ACTION.equals( accion ) ) {
                   return REPORTE_30__ONSELECT_ZONAREGISTRAL_MUNICIPIO__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_30__ONSELECT_ZONAREGISTRAL_VEREDA_ACTION.equals( accion ) ) {
                   return REPORTE_30__ONSELECT_ZONAREGISTRAL_VEREDA__EVENTRESP_OK;
                }


                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_REL01__FINDRELATEDFASES_ACTION.equals( accion ) ){
                  return REPORTE_REL01__FINDRELATEDFASES_EVENTRESP_OK;
                }



                // -- navegacion sobre pagina
		/*AHERRENO 09/04/2013
		  064_453 DUPLICIDAD TURNOS*/
                if( AWReportes.REPORTE_01_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_01_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }
                
                if( AWReportes.REPORTE_06_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_06_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_04_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_04_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_05_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_05_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_6B_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_6B_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_07_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_07_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_08_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_08_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_09_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_09_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_10_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_10_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_11_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_11_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_15_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_15_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_16_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_16_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_17_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_17_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_18_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_18_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_19_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_19_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_20_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_20_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_21_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_21_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_22_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_22_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_24_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_24_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_25_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_25_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }
        		if(accion.equals(AWReportes.REPORTE_25_LOADUSUARIOSCAJEROSBYCIRCULO)){
        			return ControlNavegacionReportes.REPORTE_25_LOADUSUARIOSCAJEROSBYCIRCULO_OK;
        		}

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_26_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_26_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_27_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_27_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_30_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_30_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_31_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_31_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }
                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_154_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_154_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_32_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_32_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_37_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_37_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_39_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_39_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_45_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_45_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_46_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_46_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_49_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_49_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_51_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_51_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_51B_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_51B_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_57_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_57_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_69_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_69_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_74_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_74_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_79_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_79_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_87_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_87_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_88_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_88_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_89_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_89_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_90_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_90_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_91_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_91_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_92_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_92_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_93_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_93_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_94_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_94_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_99_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_99_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_109_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_109_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_110_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_110_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_121_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_121_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_123_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_123_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_124_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_124_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_142_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_142_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_145_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_145_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_151_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_151_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                if( AWReportes.REPORTE_155_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_155_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_156_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_156_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_REL02_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_REL02_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                if( AWReportes.REPORTE_G01_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_G01_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_G02_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_G02_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }
                /**
                * @Autor: Santiago Vásquez
                * @Change: 2028.AJUSTE REPORTES 166, 167 y G02
                * Eliminar la opción de usuario
                */
                /*if( AWReportes.REPORTE_G02_LOADUSUARIOSBYCIRCULO.equals( accion ) ){
                  return REPORTE_G02_LOADUSUARIOBYCIRCULO_EVENTRESP_OK;
                }*/
                /**
                 * @Autor: Santiago Vásquez
                 * @Change: 1252.REPORTE TURNOS CORRECCION ENCABEZADO
                 **/
                if( AWReportes.REPORTE_178_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_178_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_178_LOADUSUARIOSBYCIRCULO.equals( accion ) ){
                  return REPORTE_178_LOADUSUARIOBYCIRCULO_EVENTRESP_OK;
                }
                /**
                 * @Autor: Santiago Vásquez
                 * @Change: 2015.REPORTE.ESTADISTICO.POR.NATURALEZA.JURIDICA
                 **/
                if( AWReportes.REPORTE_179_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_179_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }
                
                if( AWReportes.REPORTE_179_NATURALEZA_JURIDICA.equals( accion ) ){
                  return REPORTE_179_LOADNATJUR_EVENTRESP_OK;
                }
                
                /**
                 * @Autor: TSG Desarrollo1
                 * @Change: 
                 **/
                if( AWReportes.REPORTE_180_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_180_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }
                
                if( AWReportes.REPORTE_182_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_182_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }
                
                if( AWReportes.REPORTE_181_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_181_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }
                
                if( AWReportes.REPORTE_G03_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_G03_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_G03_LOADUSUARIOSBYCIRCULO.equals( accion ) ){
                  return REPORTE_G03_LOADUSUARIOBYCIRCULO_EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_G04_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_G04_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_G05_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_G05_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_G06_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_G06_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }
			    if( AWReportes.REPORTE_RUPTA01_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                    return REPORTE_RUPTA01_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }
			    if( AWReportes.REPORTE_157_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                    return REPORTE_157_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_158_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                    return REPORTE_158_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }
			    if( AWReportes.REPORTE_159_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                    return REPORTE_159_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }
			    if( AWReportes.REPORTE_160_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                    return REPORTE_160_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }	
        		if(accion.equals(AWReportes.REPORTE_160_LOADUSUARIOSCAJEROSBYCIRCULO)){
        			return ControlNavegacionReportes.REPORTE_160_LOADUSUARIOSCAJEROSBYCIRCULO_OK;
        		}
                //AHERRENO 19/08/2009
                // navegacion sobre pagina
                if( AWReportes.REPORTE_FOLIOS_MAS_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_FOLIOS_MAS_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                //AHERRENO 22/09/2009
                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_E01_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_E01_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                //AHERRENO 22/09/2009
                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_161_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_161_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_161__ONSELECT_CIRCULO_MUNICIPIOS_ACTION.equals( accion ) ) {
                   return REPORTE_161__ONSELECT_CIRCULO_MUNICIPIOS_EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_161__ONSELECT_ZONAREGISTRAL_MUNICIPIO_ACTION.equals( accion ) ) {
                   return REPORTE_161__ONSELECT_ZONAREGISTRAL_MUNICIPIO__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_161__ONSELECT_ZONAREGISTRAL_VEREDA_ACTION.equals( accion ) ) {
                   return REPORTE_161__ONSELECT_ZONAREGISTRAL_VEREDA__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_161__ONSELECT_NATURALEZA_JURIDICA_ACTION.equals( accion ) ) {
                   return REPORTE_161__ONSELECT_NATURALEZA_JURIDICA__EVENTRESP_OK;
                }
                //AHERRENO 26/04/2010
                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_E02_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_E02_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                //AHERRENO 02/08/2010
                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_E03_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_E03_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }
                //AHERRENO 05/01/2011
                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_162_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_162_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }
                //AHERRENO 15/02/2011
                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_163_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_163_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }
                //AHERRENO 23/02/2011
                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_164_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_164_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }
                //AHERRENO 14/03/2011
                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_165_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_165_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }
                //AHERRENO 14/03/2011
                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_166_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_166_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }
                //AHERRENO 14/03/2011
                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_167_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_167_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }
                //AHERRENO 14/03/2011
                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_168_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_168_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }
                //CTORRES 24/04/2013
                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_175_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_175_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }
                //CTORRES 24/04/2013
                // -- navegacion sobre pagina
                if( AWReportes.REPORTE_176_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                  return REPORTE_176_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }
                // -------------------------------------------------------------------


		if( AWReportes.REPORTE_00__BACK_ACTION.equals( accion ) ) {
			return REPORTE_00__BACK__EVENTRESP_OK;
		}

		// ---

		if( AWReportes.REPORTE_XX__BACK_ACTION.equals( accion ) ) {
			return REPORTE_XX__BACK__EVENTRESP_OK;
		}
                if( AWReportes.REPORTE_01__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_04__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_04__BACK_ACTION.equals( accion ) ) {
			return REPORTE_04__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_05__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_06__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_6B__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_07__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_08__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_09__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_10__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_11__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_15__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_16__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_17__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_18__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_19__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_20__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_21__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_22__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_24__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_25__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_26__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_27__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_30__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_31__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_154__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_32__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_37__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_39__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_45__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_46__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_49__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_51__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_51B__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_57__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_69__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_74__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_79__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_87__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_88__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_180__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_182__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_89__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_90__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_91__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_92__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_93__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_94__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_99__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_109__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_110__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_121__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_123__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_124__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_142__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_145__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_151__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_155__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_156__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_REL01__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_REL02__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }

                if( AWReportes.REPORTE_G01__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_G02__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                /**
                 * @Autor: Santiago Vásquez
                 * @Change: 1252.REPORTE TURNOS CORRECCION ENCABEZADO
                 **/
                if( AWReportes.REPORTE_178__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                /**
                 * @Autor: Santiago Vásquez
                 * @Change: 2015.REPORTE.ESTADISTICO.POR.NATURALEZA.JURIDICA
                 **/
                if( AWReportes.REPORTE_179__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_G03__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_G04__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_G05__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_G06__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_RUPTA01__BACK_ACTION.equals( accion ) ) {
                	return REPORTE_XX__BACK__EVENTRESP_OK;
            	}
                if( AWReportes.REPORTE_157__BACK_ACTION.equals( accion ) ) {
                	return REPORTE_XX__BACK__EVENTRESP_OK;
            	}
            	if( AWReportes.REPORTE_158__BACK_ACTION.equals( accion ) ) {
                	return REPORTE_XX__BACK__EVENTRESP_OK;
            	}
                if( AWReportes.REPORTE_159__BACK_ACTION.equals( accion ) ) {
                	return REPORTE_XX__BACK__EVENTRESP_OK;
            	}
                if( AWReportes.REPORTE_160__BACK_ACTION.equals( accion ) ) {
                	return REPORTE_XX__BACK__EVENTRESP_OK;
            	}
                //AHERRENO 19/08/2009
                //Reporte Folios Masivos
                if( AWReportes.REPORTE_FOLIOS_MAS__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                //AHERRENO 22/09/2009
                //Reporte E01
                if( AWReportes.REPORTE_E01__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }

                //AHERRENO 22/09/2009
                //Reporte 161
                if( AWReportes.REPORTE_161__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }

                //AHERRENO 26/04/2010
                //Reporte E02
                if( AWReportes.REPORTE_E02__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }

                //AHERRENO 02/08/2010
                //Reporte E03
                if( AWReportes.REPORTE_E03__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }

                //AHERRENO 05/01/2011
                //Reporte 162
                if( AWReportes.REPORTE_162__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }

                //AHERRENO 15/02/2011
                //Reporte 163
                if( AWReportes.REPORTE_163__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }

                //AHERRENO 23/02/2011
                //Reporte 164
                if( AWReportes.REPORTE_164__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }

                //AHERRENO 14/03/2011
                //Reporte 165
                if( AWReportes.REPORTE_165__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }

                //AHERRENO 14/03/2011
                //Reporte 166
                if( AWReportes.REPORTE_166__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }

                //AHERRENO 14/03/2011
                //Reporte 167
                if( AWReportes.REPORTE_167__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }

                //AHERRENO 14/03/2011
                //Reporte 168
                if( AWReportes.REPORTE_168__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }
                //CTORRES 24/04/2013
                //Reporte 175 Y 176
                if( AWReportes.REPORTE_175__BACK_ACTION.equals( accion ) || AWReportes.REPORTE_176__BACK_ACTION.equals( accion ) ) {
                        return REPORTE_XX__BACK__EVENTRESP_OK;
                }                
		// if( AWReportes.REPORTE_01__SEND_ACTION.equals( accion ) ) {
		//	return REPORTE_01__SEND__EVENTRESP_OK;
		// }

		// if( AWReportes.REPORTE_04__SEND_ACTION.equals( accion ) ) {
		//	return REPORTE_04__SEND__EVENTRESP_OK;
		//}

		// ---
  /*
            * @author      :   Carlos Mario Torres Urina
            * @change      :   Se modifica la navegacion en el proceso traslado folio
            * Caso Mantis  :   
            */
           else if(accion.equals(AWReportes.ONSELECT_CIRCULO_ORIGEN)){
		   return AWReportes.ONSELECT_CIRCULO_ORIGEN;
	   }else if(accion.equals(AWReportes.ONSELECT_DEPARTAMENTO_ORIGEN)){
		   return AWReportes.ONSELECT_DEPARTAMENTO_ORIGEN;
	   }
           else if(accion.equals(AWReportes.ONSELECT_MUNICIPIO_ORIGEN)){
		   return AWReportes.ONSELECT_MUNICIPIO_ORIGEN;
	   }else if(accion.equals(AWReportes.ONSELECT_CIRCULO_DESTINO)){
		   return AWReportes.ONSELECT_CIRCULO_DESTINO;
	   }else if(accion.equals(AWReportes.ONSELECT_DEPARTAMENTO_DESTINO)){
		   return AWReportes.ONSELECT_DEPARTAMENTO_DESTINO;
	   }else if(accion.equals(AWReportes.ONSELECT_MUNICIPIO_DESTINO)){
		   return AWReportes.ONSELECT_MUNICIPIO_DESTINO;
	   }else if(accion.equals(AWReportes.REPORTE_EXPORTAR_FOLIOS_ARCHIVO_BACK)){
		    return REPORTE_00__BACK__EVENTRESP_OK;
	   }
                

		String param_Page          = (String) request.getAttribute( AWReportes.REPORTSSERVICES_REPORTURI );
/*		Object param_EnableReports = request.getAttribute( AWReportes.REPORTSSERVICES_REPORTDISPLAYENABLED );

		if ( ( null == param_EnableReports ) ) {

			throw new ControlNavegacionException( "ReportInvocation: Invocacion al reporte desahabilitada" );

		}

		if( 	( null == param_Page )
			    ||( "".equals( param_Page ) ) ) {

			throw new ControlNavegacionException( "ReportInvocation: No se ha definido uri de destino" );

		}
*/
		if (listaReportesJasper.contains(accion)) {
			return REPORTES_JASPER_SERVLET;
		}
                //GCABRERA 28/12/2011
                // -- navegacion sobre paginaREPORTE_E04_LOADUSUARIOSCORRECCIONESBYCIRCULO
                if( AWReportes.REPORTE_E04_LOADCIRCULOSBYUSUARIO.equals( accion ) ){
                    return REPORTE_E04_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }
                if( AWReportes.REPORTE_E04_LOADUSUARIOSBYCIRCULO.equals( accion ) ){
                    return REPORTE_E04_LOADUSUARIOBYCIRCULO_EVENTRESP_OK;
                }

                //GCABRERA 28/12/2011
                //Reporte E04
                if( AWReportes.REPORTE_E04__BACK_ACTION.equals( accion ) ) {
                    return REPORTE_XX__BACK__EVENTRESP_OK;
                }

                /**
                * Autor: Edgar Lora
                * Mantis: 0011319
                * Requerimiento: 075_151
                */
                if(AWReportes.REPORTE_170_LOADCIRCULOS.equals(accion)){
                    return REPORTE_170_LOADCIRCULOSBYUSUARIO_EVENTRESP_OK;
                }

                if(AWReportes.REPORTE_170_BACK_ACTION.equals(accion)){
                    return REPORTE_XX__BACK__EVENTRESP_OK;
                }

		HttpSession session  = request.getSession();
		session.setAttribute( AWReportes.REPORTSSERVICES_REPORTURI, param_Page );

		return REPORTE_YY__SEND__EVENTRESP_OK;
                  

	}

	/**
	 * Finalización de la navegación
	 *
	 * @param request
	 * @see org.auriga.smart.web.navegacion.ControlNavegacion#doEnd(javax.servlet.http.HttpServletRequest)
	 */
	public void doEnd(HttpServletRequest arg0) {

	}

}
