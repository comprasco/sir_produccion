/*
 * Procesos_V.java
 * Clase para el manejo de las fases de arranque de procesos
 * en el Workflow. 
 * Created on 12 de agosto de 2004, 14:30
 */

package gov.sir.hermod.dao.impl.jdogenie;

/**
 * Clase para el manejo de las fases de arranque de procesos
 * en el Workflow. 
 * @author  mrios, mortiz, dlopez
 */   
public class Fases_Arranque_V 
{
    
    private String id_proceso; // pk
    private String nombre_proceso;
    private String desc_proceso;
    private String id_fase; // pk
    private String nombre_fase;
    private String desc_fase;
    private String rol;
	private String rol_arranque;

    /**
    *  Crea una nueva instancia de Fases_Arranque_V 
    */
    public Fases_Arranque_V() 
    {
    }
    
    /**
     * Retorna la descripción de la fase. 
     * @return Descripción de la fase. 
     */
    public String getDesc_fase() 
    {
        return desc_fase;
    }
    
    /**
     * Asigna al atributo desc_fase el valor recibido como parámetro. 
     * @param desc_fase la descripción que va a ser asignada a la fase. 
     */
    public void setDesc_fase(String desc_fase) 
    {
        this.desc_fase = desc_fase;
    }
    
    /**
    * Retorna la descripción del proceso.  
    * @return Descripción del proceso. 
    */
    public String getDesc_proceso() 
    {
        return desc_proceso;
    }
    
	/**
	* Asigna al atributo desc_proceso el valor recibido como parámetro. 
	* @param desc_proceso la descripción que va a ser asignada al proceso. 
	*/
    public void setDesc_proceso(String desc_proceso) 
    {
        this.desc_proceso = desc_proceso;
    }
  
	/**
	* Retorna el identificador de la fase. 
	* @return identificador de la fase. 
	*/
    public String getId_fase() 
    {
        return id_fase;
    }
    
	/**
	* Asigna al atributo id_fase el valor recibido como parámetro. 
	* @param id_fase el identificador que va a ser asignado a la fase. 
	*/
    public void setId_fase(String id_fase) 
    {
        this.id_fase = id_fase;
    }
    
	/**
	* Retorna el identificador del proceso. 
	* @return identificador del proceso. 
	*/
    public String getId_proceso() 
    {
        return id_proceso;
    }
    
	/**
	* Asigna al atributo id_proceso el valor recibido como parámetro. 
	* @param id_proceso el identificador que  va a ser asignado al proceso. 
	*/
    public void setId_proceso(String id_proceso) 
    {
        this.id_proceso = id_proceso;
    }
    
	/**
	* Retorna el nombre de la fase. 
	* @return nombre de la fase. 
	*/
    public String getNombre_fase() 
    {
        return nombre_fase;
    }
    
	/**
	* Asigna al atributo nombre_fase el valor recibido como parámetro. 
	* @param nombre_fase el nombre que va a ser asignado a la fase. 
	*/
    public void setNombre_fase(String nombre_fase) 
    {
        this.nombre_fase = nombre_fase;
    }
    
	/**
	* Retorna el nombre del proceso.
	* @return nombre del proceso.
	*/
    public String getNombre_proceso() 
    {
        return nombre_proceso;
    }
    
	/**
	* Asigna al atributo nombre_proceso el valor recibido como parámetro. 
	* @param nombre_proceso el nombre que va a ser asignado al proceso. 
	*/
    public void setNombre_proceso(String nombre_proceso) 
    {
        this.nombre_proceso = nombre_proceso;
    }
    
 

	/**
	* Retorna el nombre del rol.
	* @return nombre del rol.
	*/
	public String getRol() 
	{
		return rol;
	}


	/**
	* Asigna al atributo rol el valor recibido como parámetro. 
	* @param rol el Rol que va a ser asociado a la clase. 
	*/
	public void setRol(String newRol) 
	{
		rol = newRol;
	}



	/**
	* Retorna el nombre del rol de arranque
	* @return nombre del rol de arranque.
	*/
	public String getRol_arranque() 
	{
		return rol_arranque;
	}


	/**
	* Asigna al atributo rol de arranque el valor recibido como parámetro. 
	* @param rol el Rol de arranque que va a ser asociado a la clase. 
	*/
	public void setRol_arranque(String string) 
	{
		rol_arranque = string;
	}


}