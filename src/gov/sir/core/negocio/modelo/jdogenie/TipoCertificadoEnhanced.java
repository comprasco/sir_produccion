package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TipoCertificado;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



/*
 * Generated by JDO Genie (version:3.0.0 (08 Jun 2004))
 */
public class TipoCertificadoEnhanced extends Enhanced{

    private String idTipoCertificado; // pk 
    private String nombre;
    
    
	/**
	* Flag que indica si el tipo de certificado est� asociado con el proceso de 
	* certificados individuales.  
	*/
    private boolean flagIndividual;
    
	private List validaciones = new ArrayList(); // contains TipoCertificadoValidacion  inverse TipoCertificadoValidacion.tipoCertificado

    public TipoCertificadoEnhanced() {
    }

    public String getIdTipoCertificado() {
        return idTipoCertificado;
    }

    public void setIdTipoCertificado(String idTipoCertificado) {
        this.idTipoCertificado = idTipoCertificado;
    }

    public String getNombre() {
        return nombre;
    }


	/**
	* Obtiene el valor del atributo flagIndividual asociado con el tipo de certificado. 
	*/
	public boolean getFlagIndividual() {
		return flagIndividual;
	}
	

	/**
	* Asocia al atributo flagIndividual, el valor recibido como par�metro. 
	* @param flag booleano que ser� asignado al atributo flagIndividual,
	* <code>true</code> si el tipo de certificado est� asociado con el proceso de 
	* certificados individuales, o <code>false</code> en caso contrario. 
	*/
	public void setFlagIndividual (boolean flag) {
		   this.flagIndividual = flag;
	   }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
	public List getValidaciones() {
		 return Collections.unmodifiableList(validaciones);
	 }

	 public boolean addValidacion(TipoCertificadoValidacionEnhanced newValidacione) {
		 return validaciones.add(newValidacione);
	 }

	 public boolean removeValidacion(TipoCertificadoValidacionEnhanced oldValidacione) {
		 return validaciones.remove(oldValidacione);
	 }
	 
	public static TipoCertificadoEnhanced enhance(TipoCertificado tipoCertificado) {
		return (TipoCertificadoEnhanced) Enhanced.enhance(tipoCertificado);
	}
}