package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class CambioMatriculaCerTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.CambioMatriculaCerEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.CambioMatriculaCer transferObject = new gov.sir.core.negocio.modelo.CambioMatriculaCer();
		cache.put(enhancedObject, transferObject );
		
		  // FechaCreacion
  		try {
		if (enhancedObject.getFechaCreacion() != null)
		{
	  	 transferObject.setFechaCreacion(new Date(enhancedObject.getFechaCreacion().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // IdCirculo
  	    try {
	  	transferObject.setIdCirculo(enhancedObject.getIdCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // IdWorkflow
  	    try {
	  	transferObject.setIdWorkflow(enhancedObject.getIdWorkflow());
	  	} catch (Throwable th ) {}
	  			
		  // IdMatricula
  	    try {
	  	transferObject.setIdMatricula(enhancedObject.getIdMatricula());
	  	} catch (Throwable th ) {}
	  			
		  // Usuario
  	    try {
	  	transferObject.setUsuario(enhancedObject.getUsuario());
	  	} catch (Throwable th ) {}
	  			
		  // IdCambioMatricula
  	    try {
	  	transferObject.setIdCambioMatricula(enhancedObject.getIdCambioMatricula());
	  	} catch (Throwable th ) {}
	  			
		  // IdMatricula1
  	    try {
	  	transferObject.setIdMatricula1(enhancedObject.getIdMatricula1());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.CambioMatriculaCer transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.CambioMatriculaCerEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.CambioMatriculaCerEnhanced();
		cache.put(transferObject, enhancedObject);
		  // FechaCreacion
  	    try {
		if (transferObject.getFechaCreacion() != null)
		{
		  	enhancedObject.setFechaCreacion(new Date(transferObject.getFechaCreacion().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // IdCirculo
  	    try {
	  	enhancedObject.setIdCirculo(transferObject.getIdCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // IdWorkflow
  	    try {
	  	enhancedObject.setIdWorkflow(transferObject.getIdWorkflow());
	  	} catch (Throwable th ) {}
	  			
		  // IdMatricula
  	    try {
	  	enhancedObject.setIdMatricula(transferObject.getIdMatricula());
	  	} catch (Throwable th ) {}
	  			
		  // Usuario
  	    try {
	  	enhancedObject.setUsuario(transferObject.getUsuario());
	  	} catch (Throwable th ) {}
	  			
		  // IdCambioMatricula
  	    try {
	  	enhancedObject.setIdCambioMatricula(transferObject.getIdCambioMatricula());
	  	} catch (Throwable th ) {}
	  			
		  // IdMatricula1
  	    try {
	  	enhancedObject.setIdMatricula1(transferObject.getIdMatricula1());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}