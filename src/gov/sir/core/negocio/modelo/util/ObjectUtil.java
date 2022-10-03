/*
 * Created on 24-feb-2005
 *
 */
package gov.sir.core.negocio.modelo.util;

import java.util.Hashtable;

/**
 * @author gvillal
 *
 */
public abstract class ObjectUtil 
{
	/**
	 * Constante para marcar como llave valida.
	 */
	public static final String OK = "OK";
  
	/**
	 * Constante para marcar como llave invalida.
	 */
	public static final String INVALID_KEY = "INVALID_KEY";
  

	/**
	 * Constante para marcar como llave invalida.
	 */
	public static final String NULL_OBJECT = "NULL_OBJECT";

	/**
	 * Valor por defecto del valor retornado por la funcion "getData".
	 */
	protected String defaultValue = "-";
	
	
	
	/**
	 * Tabla de hashing en donde se guardan las llaves validas.
	 */
	protected Hashtable parametros;
	
	/**
	 * Determina las constantes validas 
	 *
	 */
	protected void setMetadata()
	{
 	
	}
	
	/**
	 * Retorna el valor del folio asociado a la llave.
	 * @param key
	 * @return
	 */
	public String getData(String key)
	{
		return null;
	}

	

}
