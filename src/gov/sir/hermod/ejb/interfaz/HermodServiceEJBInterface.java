package gov.sir.hermod.ejb.interfaz;

import org.auriga.core.modelo.Servicio;

import gov.sir.hermod.interfaz.HermodServiceInterface;

/**
 * @author Mc'Carthy Newball
 * 
 * Interfaz decoradora de la interfaz original HermodServiceInterface, para que
 * esta pueda ser utilizada como un servicio EJB.
 */
public interface HermodServiceEJBInterface extends HermodServiceInterface, Servicio {

}
