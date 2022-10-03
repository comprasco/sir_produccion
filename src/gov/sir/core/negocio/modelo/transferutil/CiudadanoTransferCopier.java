package gov.sir.core.negocio.modelo.transferutil;

import java.util.Iterator;
import java.util.List;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class CiudadanoTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.CiudadanoEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.Ciudadano transferObject = new gov.sir.core.negocio.modelo.Ciudadano();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdCirculo
  	    try {
	  	transferObject.setIdCirculo(enhancedObject.getIdCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // Documento
  	    try {
	  	transferObject.setDocumento(enhancedObject.getDocumento());
	  	} catch (Throwable th ) {}
	  			
		  // IdCiudadano
  	    try {
	  	transferObject.setIdCiudadano(enhancedObject.getIdCiudadano());
	  	} catch (Throwable th ) {}
	  			
		  // TipoDoc
  	    try {
	  	transferObject.setTipoDoc(enhancedObject.getTipoDoc());
	  	} catch (Throwable th ) {}
                
                  // TipoPersona
            try {
	  	transferObject.setTipoPersona(enhancedObject.getTipoPersona());
	  	} catch (Throwable th ) {}
                
                  // Sexo
            try {
	  	transferObject.setSexo(enhancedObject.getSexo());
	  	} catch (Throwable th ) {}
             
		  // Apellido1
  	    try {
	  	transferObject.setApellido1(enhancedObject.getApellido1());
	  	} catch (Throwable th ) {}
	  			
		  // Apellido2
  	    try {
	  	transferObject.setApellido2(enhancedObject.getApellido2());
	  	} catch (Throwable th ) {}
	  			
		  // Telefono
  	    try {
	  	transferObject.setTelefono(enhancedObject.getTelefono());
	  	} catch (Throwable th ) {}
	  			
		  // Prohibicione
    	List prohibicione = null;
  	try
  	{
  	prohibicione = enhancedObject.getProhibiciones();
  	} catch (Throwable th) {}
	  	if (prohibicione != null)
	  	{
		  	for(Iterator itera = prohibicione.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.CiudadanoProhibicionEnhanced prohibicioneEnh = (gov.sir.core.negocio.modelo.jdogenie.CiudadanoProhibicionEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.CiudadanoProhibicion objTo = (gov.sir.core.negocio.modelo.CiudadanoProhibicion)cache.get(prohibicioneEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.CiudadanoProhibicion)CiudadanoProhibicionTransferCopier.deepCopy(prohibicioneEnh, cache);
		  		}
		  		transferObject.addProhibicione(objTo);
		  		}
		  				  	}
		}
				
		  // Solicitante
    try {
  	transferObject.setSolicitante(enhancedObject.isSolicitante());
  } catch (Throwable th ) {}
  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.Ciudadano transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.CiudadanoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.CiudadanoEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdCirculo
  	    try {
	  	enhancedObject.setIdCirculo(transferObject.getIdCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // Documento
  	    try {
	  	enhancedObject.setDocumento(transferObject.getDocumento());
	  	} catch (Throwable th ) {}
	  			
		  // IdCiudadano
  	    try {
	  	enhancedObject.setIdCiudadano(transferObject.getIdCiudadano());
	  	} catch (Throwable th ) {}
	  			
		  // TipoDoc
  	    try {
	  	enhancedObject.setTipoDoc(transferObject.getTipoDoc());
	  	} catch (Throwable th ) {}
            
                  // TipoPersona
  	    try {
	  	enhancedObject.setTipoPersona(transferObject.getTipoPersona());
	  	} catch (Throwable th ) {}
            
                 // Sexo
  	    try {
	  	enhancedObject.setSexo(transferObject.getSexo());
	  	} catch (Throwable th ) {}
	  			
		  // Apellido1
  	    try {
	  	enhancedObject.setApellido1(transferObject.getApellido1());
	  	} catch (Throwable th ) {}
	  			
		  // Apellido2
  	    try {
	  	enhancedObject.setApellido2(transferObject.getApellido2());
	  	} catch (Throwable th ) {}
	  			
		  // Telefono
  	    try {
	  	enhancedObject.setTelefono(transferObject.getTelefono());
	  	} catch (Throwable th ) {}
	  			
		  // Prohibicione
    	List prohibicione = null;
  	try
  	{
  	prohibicione = transferObject.getProhibiciones();
  	} catch (Throwable th) { }
	  	if (prohibicione != null)
	  	{
		  	for(Iterator itera = prohibicione.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.CiudadanoProhibicion prohibicioneto = (gov.sir.core.negocio.modelo.CiudadanoProhibicion)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.CiudadanoProhibicionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.CiudadanoProhibicionEnhanced)cache.get(prohibicioneto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.CiudadanoProhibicionEnhanced)CiudadanoProhibicionTransferCopier.deepCopy(prohibicioneto, cache);
		  		}
		  		enhancedObject.addProhibicione(objEn);
		  				  		}
		  				  	}
		}
				
		  // Solicitante
      try {
  	enhancedObject.setSolicitante(transferObject.isSolicitante());
  	} catch (Throwable th ) {}
  			
		
		return enhancedObject;
	}
}