package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class TestamentoTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.TestamentoEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.Testamento transferObject = new gov.sir.core.negocio.modelo.Testamento();
		cache.put(enhancedObject, transferObject );
		
		  // FechaCreacion
  		try {
		if (enhancedObject.getFechaCreacion() != null)
		{
	  	 transferObject.setFechaCreacion(new Date(enhancedObject.getFechaCreacion().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // NumeroAnotaciones
  	    try {
	  	transferObject.setNumeroAnotaciones(enhancedObject.getNumeroAnotaciones());
	  	} catch (Throwable th ) {}
	  			
		  // NumeroCopias
  	    try {
	  	transferObject.setNumeroCopias(enhancedObject.getNumeroCopias());
	  	} catch (Throwable th ) {}
	  			
		  // RevocaEscritura
  	    try {
	  	transferObject.setRevocaEscritura(enhancedObject.getRevocaEscritura());
	  	} catch (Throwable th ) {}
	  			
		  // Tomo
  	    try {
	  	transferObject.setTomo(enhancedObject.getTomo());
	  	} catch (Throwable th ) {}
	  			
		  // IdTestamento
  	    try {
	  	transferObject.setIdTestamento(enhancedObject.getIdTestamento());
	  	} catch (Throwable th ) {}
	  			
		  // NumeroRadicacion
  	    try {
	  	transferObject.setNumeroRadicacion(enhancedObject.getNumeroRadicacion());
	  	} catch (Throwable th ) {}
	  			
		  // Observacion
  	    try {
	  	transferObject.setObservacion(enhancedObject.getObservacion());
	  	} catch (Throwable th ) {}
                 /*
        * @author : CTORRES
        * @change : Se agregan las sentencias para estableser los valores de los atributos usuarioCreacion,usuarioCreacionTMP
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        */
                // usuarioCreacion
            try {
	  	transferObject.setUsuarioCreacion((gov.sir.core.negocio.modelo.Usuario)enhancedObject.getUsuarioCreacion().toTransferObject());
	  	} catch (Throwable th ) {}
                // usuarioCreacionTmp
	    try {
	  	transferObject.setUsuarioCreacionTMP((gov.sir.core.negocio.modelo.Usuario)enhancedObject.getUsuarioCreacionTMP().toTransferObject());
	  	} catch (Throwable th ) {}
    	List testadores = null;
      	try{
      		testadores = enhancedObject.getTestadores();
      	}catch (Throwable th) {
      		
      	}
   	  	if (testadores != null){
   		  	for(Iterator itera = testadores.iterator();itera.hasNext();){
   		  		boolean assigned = false;
   		  		gov.sir.core.negocio.modelo.jdogenie.TestamentoCiudadanoEnhanced testamentoCiudadanoEnh=(gov.sir.core.negocio.modelo.jdogenie.TestamentoCiudadanoEnhanced)itera.next();
   		  		if (!assigned){
   		  			gov.sir.core.negocio.modelo.TestamentoCiudadano objTo = (gov.sir.core.negocio.modelo.TestamentoCiudadano)cache.get(testamentoCiudadanoEnh);
   		  			if (objTo == null){
   		  				objTo = (gov.sir.core.negocio.modelo.TestamentoCiudadano)TestamentoCiudadanoTransferCopier.deepCopy(testamentoCiudadanoEnh, cache);
   		  			}
   		  		transferObject.addTestador(objTo);
   		  		}
   		  	}
   		}
	  	
	  	
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.Testamento transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.TestamentoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TestamentoEnhanced();
		cache.put(transferObject, enhancedObject);
		  // FechaCreacion
  	    try {
		if (transferObject.getFechaCreacion() != null)
		{
		  	enhancedObject.setFechaCreacion(new Date(transferObject.getFechaCreacion().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // NumeroAnotaciones
  	    try {
	  	enhancedObject.setNumeroAnotaciones(transferObject.getNumeroAnotaciones());
	  	} catch (Throwable th ) {}
	  			
		  // NumeroCopias
  	    try {
	  	enhancedObject.setNumeroCopias(transferObject.getNumeroCopias());
	  	} catch (Throwable th ) {}
	  			
		  // RevocaEscritura
  	    try {
	  	enhancedObject.setRevocaEscritura(transferObject.getRevocaEscritura());
	  	} catch (Throwable th ) {}
	  			
		  // Tomo
  	    try {
	  	enhancedObject.setTomo(transferObject.getTomo());
	  	} catch (Throwable th ) {}
	  			
		  // IdTestamento
  	    try {
	  	enhancedObject.setIdTestamento(transferObject.getIdTestamento());
	  	} catch (Throwable th ) {}
	  			
		  // NumeroRadicacion
  	    try {
	  	enhancedObject.setNumeroRadicacion(transferObject.getNumeroRadicacion());
	  	} catch (Throwable th ) {}
	  			
		  // Observacion
  	    try {
	  	enhancedObject.setObservacion(transferObject.getObservacion());
	  	} catch (Throwable th ) {}
	        // usuarioCreacion
             /*
        * @author : CTORRES
        * @change : Se agregan las sentencias para estableser los valores de los atributos usuarioCreacion,usuarioCreacionTMP
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        */
            try {
	  	enhancedObject.setUsuarioCreacion((gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced)cache.get(transferObject.getUsuarioCreacion()));
	  	} catch (Throwable th ) {}
                // usuarioCreacionTmp
	    try {
	  	enhancedObject.setUsuarioCreacionTMP((gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced) cache.get(transferObject.getUsuarioCreacionTMP()));
	  	} catch (Throwable th ) {}			
		  // Causante//Pablo Quintana
	  	List testadores=null;
	  	try {
	  		testadores= transferObject.getTestadores();
	  	} catch (Throwable th ) {}
	  	if (testadores != null){
	  		for(Iterator itera = testadores.iterator();itera.hasNext();){
	  			boolean assigned = false;
	  			gov.sir.core.negocio.modelo.TestamentoCiudadano testamentoCiudadano=(gov.sir.core.negocio.modelo.TestamentoCiudadano)itera.next();
			  	if (!assigned){
			  		gov.sir.core.negocio.modelo.jdogenie.TestamentoCiudadanoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TestamentoCiudadanoEnhanced)cache.get(testamentoCiudadano);
			  		if (objEn == null){
			  			objEn = (gov.sir.core.negocio.modelo.jdogenie.TestamentoCiudadanoEnhanced)TestamentoCiudadanoTransferCopier.deepCopy(testamentoCiudadano, cache);
			  		}
			  		enhancedObject.addTestadorEnhanced(objEn);
			  	}
	  		}
	  	}
		return enhancedObject;
	}
}