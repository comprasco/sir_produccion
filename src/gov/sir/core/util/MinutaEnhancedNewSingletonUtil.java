/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.util;

import gov.sir.core.negocio.modelo.jdogenie.MinutaEnhanced;

/**
* @autor HGOMEZ 
* @mantis 13176 
* @Requerimiento 061_453_Requerimiento_Auditoria 
* @descripcion Mantiene la información de la minuta nueva.
*/
public class MinutaEnhancedNewSingletonUtil {
    
    
    private static MinutaEnhancedNewSingletonUtil instanciaUnica = null;
    MinutaEnhanced minutaEnhanced = new MinutaEnhanced();

    private MinutaEnhancedNewSingletonUtil() {
        
    }

    public static synchronized MinutaEnhancedNewSingletonUtil getInstance() {
        if (instanciaUnica == null) {
            instanciaUnica = new MinutaEnhancedNewSingletonUtil();
        }
        return instanciaUnica;
    }
    
    public void setMinutaEnhanced(MinutaEnhanced pMinutaEnhanced)
    {
        minutaEnhanced = pMinutaEnhanced;
    }  
    
    public MinutaEnhanced getMinutaEnhanced()
    {
        return minutaEnhanced;
    }  
}
