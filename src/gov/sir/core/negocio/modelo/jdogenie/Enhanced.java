/*
 * Created on 11-oct-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.negocio.modelo.jdogenie;

import org.auriga.core.modelo.TransferObject;

/**
 * @author fceballos
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class Enhanced {
	 
	public static final Object enhance(TransferObject c){
	   return TransferUtils.makeEnhanced(c);
	}
	 
	public final Object toTransferObject(){
	   return TransferUtils.makeTransfer(this);
	}
}
