/*
 * Created on 24-feb-2005
 *
 */
package gov.sir.core.negocio.modelo.util;

import java.util.Hashtable;


import gov.sir.core.negocio.modelo.Folio;

/**
 * @author gvillal
 * Clase que ayuda a tener acceso a los datos de un folio
 * controlando los valores null y sustituyendolos por una constante.
 */
public class FolioUtil extends ObjectUtil implements FolioUtilKeys
{

  /**
   * Folio del cual se quiere obtener datos.
   */	
  private Folio folio;	
	
	

	
  /**
   * Constructor de la clase
   */	
  public FolioUtil(Folio folio)
  {
  	this.folio = folio;
  	this.setMetadata();
  }
  
  /**
   * Determina las constantes validas 
   *
   */
  protected void setMetadata()
  {
  	this.parametros = new Hashtable();
  	this.parametros.put(DEPARTAMENTO,OK);
	this.parametros.put(MUNICIPIO,OK);
	this.parametros.put(VEREDA,OK);
	this.parametros.put(CIRCULO_REGISTRAL_NOMBRE,OK);
	this.parametros.put(CODIGO_CATASTRAL,OK);
	this.parametros.put(TIPODOCUMENTO_NOMBRE,OK);	
	this.parametros.put(DOCUMENTO_NUMERO,OK);	
	this.parametros.put(ESTADO_NOMBRE,OK);
	this.parametros.put(LINDERO,OK);
	this.parametros.put(COMPLEMENTACION_COMPLEMENTACION,OK);
	this.parametros.put(TIPOPREDIO_NOMBRE,OK);	 	
  }
  
  /**
   * Retorna el valor del folio asociado a la llave.
   * @param key
   * @return
   */
  public String getData(String key)
  {
	  Log.getInstance().debug(FolioUtil.class, "..........key="+key);
  	String ok = (String)this.parametros.get(key);
  	//NullPointerException
  	
  	if (ok==null)
  	  return INVALID_KEY;
  	  
  	if (this.folio==null)
  	  return NULL_OBJECT;  
  	  
  	String valor=defaultValue;
  	
  	if ( key.equals(DEPARTAMENTO) )
  	  valor = this.getDepartamento();
  	else  
	if ( key.equals(MUNICIPIO) )
	  valor = this.getMunicipio();
	else  
	if ( key.equals(VEREDA) )
	  valor = this.getVereda();
	else  
	if ( key.equals(CIRCULO_REGISTRAL_NOMBRE) )
	  valor = this.getCirculoRegistralNombre();
	else  
	if ( key.equals(TIPODOCUMENTO_NOMBRE) )
	  valor = this.getTipoDocumentoNombre();
	else  
	if ( key.equals(DOCUMENTO_NUMERO) )
	  valor = this.getDocumentoNumero();
	else  
	if ( key.equals(CODIGO_CATASTRAL) )
	  valor = this.getCodigoCatastral();	  	  
	else  
	if ( key.equals(LINDERO) )
	  valor = this.getLindero();	
	else  
	if ( key.equals(ESTADO_NOMBRE) )
	  valor = this.getEstadoNombre();		  
	else  
	if ( key.equals(COMPLEMENTACION_COMPLEMENTACION) )
	  valor = this.getComplementacionComplementacion();	
	else  
	if ( key.equals(TIPOPREDIO_NOMBRE) )
	  valor = this.getTipopredioNombre();	
	    	
  	if (valor==null)
  	  valor = defaultValue;
  	  
  	Log.getInstance().debug(FolioUtil.class, "..........valor="+valor);  
  	return valor;
  }
  

  private String getDepartamento()
  {
	String valor;  
	try
	{
		valor=folio.getZonaRegistral().getVereda().getMunicipio().getDepartamento().getNombre();
	}
	catch (Throwable t)
	{
		t.printStackTrace();
		valor = this.defaultValue;  
	}  
	return valor;
  }


  private String getMunicipio()
  {
	String valor;  
	try
	{
		valor=folio.getZonaRegistral().getVereda().getMunicipio().getNombre();
	}
	catch (Throwable t)
	{
		t.printStackTrace();
		valor = this.defaultValue;  
	}  
	return valor;
  }


  private String getVereda()
  {
	String valor;  
	try
	{
		valor=folio.getZonaRegistral().getVereda().getNombre();
	}
	catch (Throwable t)
	{
		t.printStackTrace();
		valor = this.defaultValue;  
	}  
	return valor;
  }
  
 

  private String getCirculoRegistralNombre()
  {
	String valor;  
	try
	{
		valor=folio.getZonaRegistral().getCirculo().getNombre();
	}
	catch (Throwable t)
	{
		t.printStackTrace();
		valor = this.defaultValue;  
	}  
	return valor;
  }  
  
  private String getTipoDocumentoNombre()
  {
	String valor;  
	try
	{
		valor=folio.getDocumento().getTipoDocumento().getNombre();
	}
	catch (Throwable t)
	{
		t.printStackTrace();
		valor = this.defaultValue;  
	}  
	return valor;
  }   
  

  private String getDocumentoNumero()
  {
	String valor;  
	try
	{
		valor=folio.getDocumento().getNumero();
	}
	catch (Throwable t)
	{
		t.printStackTrace();
		valor = this.defaultValue;  
	}  
	return valor;
  }

  private String getCodigoCatastral()
  {
	String valor;  
	try
	{
		valor=folio.getCodCatastral();
	}
	catch (Throwable t)
	{
		t.printStackTrace();
		valor = this.defaultValue;  
	}  
	return valor;
  }

  private String getEstadoNombre()
  {
	String valor;  
	try
	{
		valor=folio.getEstado().getNombre();
	}
	catch (Throwable t)
	{
		t.printStackTrace();
		valor = this.defaultValue;  
	}  
	return valor;
  }
  
  private String getLindero()
  {
	String valor;  
	try
	{
		valor=folio.getLindero();
	}
	catch (Throwable t)
	{
		t.printStackTrace();
		valor = this.defaultValue;  
	}  
	return valor;
  }

  private String getComplementacionComplementacion()
  {
	String valor;  
	try
	{
		valor=folio.getComplementacion().getComplementacion();
	}
	catch (Throwable t)
	{
		t.printStackTrace();
		valor = this.defaultValue;  
	}  
	return valor;
  }
  
  private String getTipopredioNombre()
  {
	String valor;  
	try
	{
		valor=folio.getTipoPredio().getNombre();
	}
	catch (Throwable t)
	{
		t.printStackTrace();
		valor = this.defaultValue;  
	}  
	return valor;
  }  
  
	/**
	 * @return
	 */
	public Folio getFolio() {
		return folio;
	}
	
	/**
	 * @param folio
	 */
	public void setFolio(Folio folio) {
		this.folio = folio;
	}
	
	/**
	 * @return
	 */
	public String getDefaultValue() {
		return defaultValue;
	}
	
	/**
	 * @param string
	 */
	public void setDefaultValue(String string) {
		defaultValue = string;
	}

}
