package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class TrasladoDatosTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.TrasladoDatosEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.TrasladoDatos transferObject = new gov.sir.core.negocio.modelo.TrasladoDatos();
		cache.put(enhancedObject, transferObject );
		
		  // numeroTraslado
  	    try {
	  	transferObject.setNumeroTraslado(enhancedObject.getNumeroTraslado());
	  	} catch (Throwable th ) {}
            
            		  // fechaCreacion
  	    try {
                if(enhancedObject.getFechaCreacion()!=null){
                    transferObject.setFechaCreacion(new Date(enhancedObject.getFechaCreacion().getTime()));
                }
	  	} catch (Throwable th ) {}
	  			
	  // traslados
  	  List traslados = null;
	  try {
	  	traslados = enhancedObject.getTraslados();
	  	} catch (Throwable th) {}
	  	if (traslados != null)
	  	{
	  		for(Iterator itera = traslados.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.TrasladoEnhanced trasladoEnh = (gov.sir.core.negocio.modelo.jdogenie.TrasladoEnhanced)itera.next();
		  		if (!assigned)
		  		{
                                    gov.sir.core.negocio.modelo.Traslado objTo = (gov.sir.core.negocio.modelo.Traslado)cache.get(trasladoEnh);
                                    if (objTo == null)
                                    {
                                        objTo = (gov.sir.core.negocio.modelo.Traslado)TrasladoTransferCopier.deepCopy(trasladoEnh, cache);
                                    }
                                    transferObject.addTraslado(objTo);
		  		}
		  				  	}
	  			  		
	  	}
	  			
            // trasladoFundamentos
  	  List trasladoFundamento = null;
	  try {
	  	trasladoFundamento = enhancedObject.getTrasladosFundamentos();
	  	} catch (Throwable th) {}
	  	if (trasladoFundamento != null)
	  	{
	  		for(Iterator itera = trasladoFundamento.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.TrasladoFundamentoEnhanced trasladoFunEnh = (gov.sir.core.negocio.modelo.jdogenie.TrasladoFundamentoEnhanced)itera.next();
		  		if (!assigned)
		  		{
                                    gov.sir.core.negocio.modelo.TrasladoFundamento objTo = (gov.sir.core.negocio.modelo.TrasladoFundamento)cache.get(trasladoFunEnh);
                                    if (objTo == null)
                                    {
                                        objTo = (gov.sir.core.negocio.modelo.TrasladoFundamento)TrasladoFundamentoTransferCopier.deepCopy(trasladoFunEnh, cache);
                                    }
                                    transferObject.addTrasladoFundamento(objTo);
		  		}
		  				  	}
	  			  		
	  	}
	  			
		return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.TrasladoDatos transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.TrasladoDatosEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TrasladoDatosEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdTraslado
  	    try {
	  	enhancedObject.setNumeroTraslado(transferObject.getNumeroTraslado());
	  	} catch (Throwable th ) {}
            
            		  // IdTraslado
  	    try {
	  	enhancedObject.setFechaCreacion(transferObject.getFechaCreacion());
	  	} catch (Throwable th ) {}
	  			
	// traslados
  	  List traslados = null;
	  try {
	  	traslados = transferObject.getTraslados();
	  	} catch (Throwable th) {}
	  	if (traslados != null)
	  	{
	  		for(Iterator itera = traslados.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.Traslado trasladoTranf = (gov.sir.core.negocio.modelo.Traslado)itera.next();
		  		if (!assigned)
		  		{
                                    gov.sir.core.negocio.modelo.jdogenie.TrasladoEnhanced objTo = (gov.sir.core.negocio.modelo.jdogenie.TrasladoEnhanced)cache.get(trasladoTranf);
                                    if (objTo == null)
                                    {
                                        objTo = (gov.sir.core.negocio.modelo.jdogenie.TrasladoEnhanced)TrasladoTransferCopier.deepCopy(trasladoTranf, cache);
                                    }
                                    enhancedObject.addTraslado(objTo);
		  		}
		  				  	}
	  			  		
	  	}
	  			
            // trasladoFundamentos
  	  List trasladoFundamento = null;
	  try {
	  	trasladoFundamento = transferObject.getFundamentosTraslados();
	  	} catch (Throwable th) {}
	  	if (traslados != null)
	  	{
	  		for(Iterator itera = trasladoFundamento.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.TrasladoFundamento trasladoFunTras = (gov.sir.core.negocio.modelo.TrasladoFundamento)itera.next();
		  		if (!assigned)
		  		{
                                    gov.sir.core.negocio.modelo.jdogenie.TrasladoFundamentoEnhanced objTo = (gov.sir.core.negocio.modelo.jdogenie.TrasladoFundamentoEnhanced)cache.get(trasladoFunTras);
                                    if (objTo == null)
                                    {
                                        objTo = (gov.sir.core.negocio.modelo.jdogenie.TrasladoFundamentoEnhanced)TrasladoFundamentoTransferCopier.deepCopy(trasladoFunTras,cache);
                                    }
                                    enhancedObject.addTrasladoFundamento(objTo);
		  		}
		  				  	}
	  			  		
	  	}
	  			
		
		return enhancedObject;
	}
}