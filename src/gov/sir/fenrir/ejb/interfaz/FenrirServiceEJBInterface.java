package gov.sir.fenrir.ejb.interfaz;

import gov.sir.fenrir.interfaz.FenrirServiceInterface;

import org.auriga.core.modelo.Servicio;

/**
 * @author Mc'Carthy Newball
 * 
 * Interfaz decoradora de la interfaz original FenrirServiceInterface, para que
 * esta pueda ser utilizada como un servicio EJB.
 */
public interface FenrirServiceEJBInterface extends FenrirServiceInterface, Servicio {

}
