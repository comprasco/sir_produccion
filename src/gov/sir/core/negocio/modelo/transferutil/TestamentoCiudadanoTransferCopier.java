package gov.sir.core.negocio.modelo.transferutil;


public class TestamentoCiudadanoTransferCopier {
	
	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.TestamentoCiudadanoEnhanced enhancedObject, java.util.HashMap cache){
		gov.sir.core.negocio.modelo.TestamentoCiudadano transferObject = new gov.sir.core.negocio.modelo.TestamentoCiudadano();
		cache.put(enhancedObject, transferObject );
  	    //IdTestamento
		try {
  	    	transferObject.setIdTestamento(enhancedObject.getIdTestamento());
	  	} catch (Throwable th ) {}
//	  IdCiudadano
		try {
  	    	transferObject.setIdCiudadano(enhancedObject.getIdCiudadano());
	  	} catch (Throwable th ) {}  
	  	//ciudadano
  	     gov.sir.core.negocio.modelo.jdogenie.CiudadanoEnhanced ciudadanoEnh = null;
		  try {
			  ciudadanoEnh = enhancedObject.getCiudadano();
		  	} catch (Throwable th) {}
		  	if (ciudadanoEnh != null){
		  		boolean assigned = false;
		  		if (!assigned){
		  		gov.sir.core.negocio.modelo.Ciudadano objTo =  (gov.sir.core.negocio.modelo.Ciudadano)cache.get(ciudadanoEnh);
		  		if (objTo == null){
		  			objTo = (gov.sir.core.negocio.modelo.Ciudadano)CiudadanoTransferCopier.deepCopy(ciudadanoEnh, cache);
		  		}
		  		transferObject.setCiudadano(objTo);
		  		}
		  	}
		 	
		  	//Testamento
	  	     gov.sir.core.negocio.modelo.jdogenie.TestamentoEnhanced testamentoEnh = null;
			  try {
				  testamentoEnh = enhancedObject.getTestamento();
			  	} catch (Throwable th) {}
			  	if (testamentoEnh != null){
			  		boolean assigned = false;
			  		if (!assigned){
			  		gov.sir.core.negocio.modelo.Testamento objTo =  (gov.sir.core.negocio.modelo.Testamento)cache.get(testamentoEnh);
			  		if (objTo == null){
			  			objTo = (gov.sir.core.negocio.modelo.Testamento)TestamentoTransferCopier.deepCopy(testamentoEnh, cache);
			  		}
			  		transferObject.setTestamento(objTo);
			  		}
			  	}

	  	
	  	
	return transferObject;
	}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.TestamentoCiudadano transferObject, java.util.HashMap cache){
		gov.sir.core.negocio.modelo.jdogenie.TestamentoCiudadanoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TestamentoCiudadanoEnhanced();
		cache.put(transferObject, enhancedObject);
  	    //IdTestamento
		try {
  	    	enhancedObject.setIdTestamento(transferObject.getIdTestamento());
	  	} catch (Throwable th ) {}
  	    //IdCiudadano
		try {
  	    	enhancedObject.setIdCiudadano(transferObject.getIdCiudadano());
	  	} catch (Throwable th ) {}	  	
	  	
	  	//Ciudadano
	    gov.sir.core.negocio.modelo.Ciudadano ciudadano = null;
	  	try {
	  		ciudadano = (gov.sir.core.negocio.modelo.Ciudadano)transferObject.getCiudadano();
	  	} catch (Throwable th ) {}
	  	if (ciudadano != null) 	{
	  		boolean assigned = false;
	  		if (!assigned){
	  		gov.sir.core.negocio.modelo.jdogenie.CiudadanoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.CiudadanoEnhanced)cache.get(ciudadano);
	  		if (objEn == null){
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.CiudadanoEnhanced)CiudadanoTransferCopier.deepCopy(ciudadano, cache);
	  		}
	  		enhancedObject.setCiudadano(objEn);
	  		}
	  			  		
	  	}
	  	//Testamento
	    gov.sir.core.negocio.modelo.Testamento testamento = null;
	  	try {
	  		testamento = (gov.sir.core.negocio.modelo.Testamento)transferObject.getTestamento();
	  	} catch (Throwable th ) {}
	  	if (testamento != null) 	{
	  		boolean assigned = false;
	  		if (!assigned){
	  		gov.sir.core.negocio.modelo.jdogenie.TestamentoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TestamentoEnhanced)cache.get(testamento);
	  		if (objEn == null){
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.TestamentoEnhanced)TestamentoTransferCopier.deepCopy(testamento, cache);
	  		}
	  		enhancedObject.setTestamento(objEn);
	  		}
	  			  		
	  	}
		return enhancedObject;
	}

}
