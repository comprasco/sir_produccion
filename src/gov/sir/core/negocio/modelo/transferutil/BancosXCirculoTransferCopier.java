package gov.sir.core.negocio.modelo.transferutil;

public class BancosXCirculoTransferCopier {
	
	
	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.BancosXCirculoEnhanced enhancedObject, java.util.HashMap cache){
		gov.sir.core.negocio.modelo.BancosXCirculo transferObject = new gov.sir.core.negocio.modelo.BancosXCirculo();
		cache.put(enhancedObject, transferObject );
  	    //IdCirculo
		try {
  	    	transferObject.setIdCirculo(enhancedObject.getIdCirculo());
	  	} catch (Throwable th ) {}
  	    //IdBanco
		try {
  	    	transferObject.setIdBanco(enhancedObject.getIdBanco());
	  	} catch (Throwable th ) {}
  	    //principal
		try {
  	    	transferObject.setPrincipal(enhancedObject.getPrincipal());
	  	} catch (Throwable th ) {}
	  	//Circulo
	  	gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced circuloEnh=null;
		try {
			  circuloEnh = enhancedObject.getCirculo();
		}catch (Throwable th) {}
		if (circuloEnh != null){
			boolean assigned = false;
		 	if (!assigned){
		 	gov.sir.core.negocio.modelo.Circulo objTo =  (gov.sir.core.negocio.modelo.Circulo)cache.get(circuloEnh);
		 	if (objTo == null){
		 		objTo = (gov.sir.core.negocio.modelo.Circulo)CirculoTransferCopier.deepCopy(circuloEnh, cache);
		 	}
		 	transferObject.setCirculo(objTo);
		 	}
		 }
	  	//Banco
	  	gov.sir.core.negocio.modelo.jdogenie.BancoEnhanced bancoEnh=null;
		try {
			bancoEnh = enhancedObject.getBanco();
		}catch (Throwable th) {}
		if (bancoEnh != null){
			boolean assigned = false;
		 	if (!assigned){
		 	gov.sir.core.negocio.modelo.Banco objTo =  (gov.sir.core.negocio.modelo.Banco)cache.get(bancoEnh);
		 	if (objTo == null){
		 		objTo = (gov.sir.core.negocio.modelo.Banco)BancoTransferCopier.deepCopy(bancoEnh, cache);
		 	}
		 	transferObject.setBanco(objTo);
		 	}
		 }
		return transferObject;
	}
	
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.BancosXCirculo transferObject, java.util.HashMap cache){
		gov.sir.core.negocio.modelo.jdogenie.BancosXCirculoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.BancosXCirculoEnhanced();
		cache.put(transferObject, enhancedObject);
  	    //IdCirculo
		try {
  	    	enhancedObject.setIdCirculo(transferObject.getIdCirculo());
	  	} catch (Throwable th ) {}
  	    //IdBanco
		try {
  	    	enhancedObject.setIdBanco(transferObject.getIdBanco());
	  	} catch (Throwable th ) {}
  	    //Principal
		try {
  	    	enhancedObject.setPrincipal(transferObject.getPrincipal());
	  	} catch (Throwable th ) {}	  	
	  	
	  	//Circulo
	    gov.sir.core.negocio.modelo.Circulo circulo = null;
	  	try {
	  		circulo = (gov.sir.core.negocio.modelo.Circulo)transferObject.getCirculo();
	  	} catch (Throwable th ) {}
	  	if (circulo != null) 	{
	  		boolean assigned = false;
	  		if (!assigned){
	  		gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced)cache.get(circulo);
	  		if (objEn == null){
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced)CirculoTransferCopier.deepCopy(circulo, cache);
	  		}
	  		enhancedObject.setCirculo(objEn);
	  		}
	  	}
	  	//Banco
	    gov.sir.core.negocio.modelo.Banco banco = null;
	  	try {
	  		banco = (gov.sir.core.negocio.modelo.Banco)transferObject.getBanco();
	  	} catch (Throwable th ) {}
	  	if (banco != null) 	{
	  		boolean assigned = false;
	  		if (!assigned){
	  		gov.sir.core.negocio.modelo.jdogenie.BancoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.BancoEnhanced)cache.get(banco);
	  		if (objEn == null){
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.BancoEnhanced)BancoTransferCopier.deepCopy(banco, cache);
	  		}
	  		enhancedObject.setBanco(objEn);
	  		}
	  	}
	  	

		return enhancedObject;
	}


}
