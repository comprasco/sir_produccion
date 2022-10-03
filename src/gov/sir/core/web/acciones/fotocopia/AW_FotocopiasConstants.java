package gov.sir.core.web.acciones.fotocopia;

import gov.sir.core.web.WebKeys;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface AW_FotocopiasConstants {
  // TODO: arreglar (high-coupling)
  // Atributos en request --------------------------------
  String PARAMETER_SELECTEDITEMS = WebKeys.TITULO_CHECKBOX_ELIMINAR;

  String PARAMETER_TIPODOCUMENTO = "PARAMETER_TIPODOCUMENTO";
  String PARAMETER_NUMCOPIAS = "PARAMETER_NUMCOPIAS";
  String PARAMETER_DESCRIPCION = "PARAMETER_DESCRIPCION";

  // Atributos en sesion --------------------------------
  String DOCUMENTOS_ASOCIADOS = "DOCUMENTOS_ASOCIADOS";
  // Acciones ---------------------------------------------
  String DOCUMENTOSASOCIADOSADD_ACTION = "DOCUMENTOSASOCIADOSADD_ACTION";
  String DOCUMENTOSASOCIADOSADD_PROCESSDONE = "DOCUMENTOSASOCIADOSADD_PROCESSDONE";

  String DOCUMENTOSASOCIADOSDEL_ACTION = "DOCUMENTOSASOCIADOSDEL_ACTION";
  String DOCUMENTOSASOCIADOSDEL_PROCESSDONE = "DOCUMENTOSASOCIADOSDEL_PROCESSDONE";

  String NOEXPEDIR_SOLICITUDFOTOCOPIAS_ALLIQUIDAR_ACTION = "NOEXPEDIR_SOLICITUDFOTOCOPIAS_ALLIQUIDAR_ACTION";
  String NOEXPEDIR_SOLICITUDFOTOCOPIAS_ALLIQUIDAR_PROCESSDONE = "NOEXPEDIR_SOLICITUDFOTOCOPIAS_ALLIQUIDAR_PROCESSDONE";

  String RELIQUIDAR_ACTION = "RELIQUIDAR";


  // al realizar la liquidacion, el valor que aparece
  // por defecto seleccionado en el listadode tipos de fotocopia.
  // String DEFAULT_TIPOFOTOCOPIA_ID = "2"; // String.valueOf( documentoFotocopia.getNumCopias() )
  // String DEFAULT_NUMCOPIAS_VALUE = "0"; // String.valueOf( documentoFotocopia.getNumCopias() )
  String DEFAULT_TIPOFOTOCOPIA_ID = "2"; // String.valueOf( documentoFotocopia.getNumCopias() )
  String DEFAULT_NUMHOJAS_VALUE = "0"; // String.valueOf( documentoFotocopia.getNumCopias() )



  String VERIFICAR_DOCUMENTOS_ASOCIADOS_OK = "VERIFICAR_DOCUMENTOS_ASOCIADOS_OK";

  // WF ---------------------------------------------
  String WF_LINK_VERIFICAR2CONFIRMAR = "CONFIRMAR";
  String WF_LINK_NOEXPEDIR_SOLICITUDFOTOCOPIAS_ALLIQUIDAR = "NEGAR";
}
