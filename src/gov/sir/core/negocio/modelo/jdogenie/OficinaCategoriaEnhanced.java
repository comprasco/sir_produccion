/*
 * OficinaCategoria.java
 * Clase que modela la relación entre Oficinas de Origen y Categorías.
 * Se utiliza básicamente para mantener las colas utilizadas en los
 * procesos de Reparto Notarial y Restitución de Reparto Notarial.
 */

package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.OficinaCategoria;


/**
 * Clase que modela la relación entre Oficinas de Origen y Categorías.
 * Se utiliza básicamente para mantener las colas utilizadas en los
 * procesos de Reparto Notarial y Restitución de Reparto Notarial.
 * @author dlopez
 */
public class OficinaCategoriaEnhanced extends Enhanced {

	/**
	* Identificador de la Categoría a la que pertenece la Oficina Origen.
	* <p>Llave Primaria de la Clase. 
	*/
    private String idCategoria; // pk 
    
	/**
	* Identificador de la Oficina Origen.
	* <p>Llave Primaria de la Clase.  
	*/
    private String idOficinaOrigen; 
    
	/**
	* Categoría a la que pertenece la Oficina Origen. 
	*/
    private CategoriaEnhanced categoria; 
    
    
	/**
	* Oficina OrigenEnhanced
	*/
    private OficinaOrigenEnhanced oficinaOrigen; 
    
    
	/**
	* Atributo utilizado para mantener la posición de la Oficina
	* Origen dentro de la Cola de Repartos Notariales.   
	* <p>
	* El valor de este atributo se va incrementando en uno cada vez
	* que se realiza un reparto y se disminuye en uno en el caso en el que
	* se haga una restitución. 
	*/
	private long pesoReparto;
    
	/**
	* Atributo utilizado para establecer un criterio inicial
	* de ordenamiento de las Oficinas Origen dentro de una Categoría.
	*/
	private int ordenInicial;
    
            /*
             *  @author Carlos Torres
             *  @chage   se agrega validacion de version diferente
             *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
             */
        private Long version;
	/**
	* Método Constructor de la Clase OficinaCategoriaEnhanced
	*/
    public OficinaCategoriaEnhanced() {
    }


	/**
	* Retorna el identificador de la <code>Categoria</code>.
	* @return el identificador de la <code>Categoria</code>.
	*/
    public String getIdCategoria() {
        return idCategoria;
    }

    
	/**
	* Asigna al atributo idCategoria el valor recibido como parámetro.  
	* @param idCategoria el identificador que va a ser asignado a la
	* <code>Categoria</code>
	*/
    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    
	/**
	* Retorna el identificador de la <code>OficinaOrigen</code>.
	* @return el identificador de la <code>OficinaOrigen</code>.
	*/
    public String getIdOficinaOrigen() {
        return idOficinaOrigen;
    }

    
    
	/**
	* Asigna al atributo idOficinaOrigen el valor recibido como parámetro.  
	* @param idOficinaOrigen el identificador que va a ser asignado a la
	* <code>OficinaOrigen</code>
	*/
    public void setIdOficinaOrigen(String idOficinaOrigen) {
        this.idOficinaOrigen = idOficinaOrigen;
    }
    
    
	/**
	* Retorna el objeto <code>CategoriaEnhanced</code> asociado con la instancia.
	* @return objeto <code>CategoriaEnhanced</code> asociado con la instancia.
	*/
    public CategoriaEnhanced getCategoria() {
        return categoria;
    }

    
    
	/**
	* Asocia al atributo Categoria el objeto recibido como parámetro e 
	* inicializa el identificador de la Categoria. 
	* @param categoria Objeto de tipo <code>CategoriaEnhanced</code>
	*/
    public void setCategoria(CategoriaEnhanced categoria) {
        this.categoria = categoria;
        setIdCategoria(categoria.getIdCategoria());
    }

    
	/**
	* Retorna el objeto <code>OficinaOrigenEnhanced</code> asociado con la instancia.
	* @return objeto <code>OficinaOrigenEnhanced</code> asociado con la instancia.
	*/
    public OficinaOrigenEnhanced getOficinaOrigen() {
        return oficinaOrigen;
    }



	/**
	* Asocia al atributo OficinaOrigen el objeto recibido como parámetro
	* e inicializa el identificador de la Oficina Origen.  
	* @param oficinaOrigen Objeto de tipo <code>OficinaOrigenEnhanced</code>
	*/
    public void setOficinaOrigen(OficinaOrigenEnhanced oficinaOrigen) {
        this.oficinaOrigen = oficinaOrigen;
        setIdOficinaOrigen(oficinaOrigen.getIdOficinaOrigen());
        /*
             *  @author Carlos Torres
             *  @chage   se agrega validacion de version diferente
             *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
             */
        setVersion(oficinaOrigen.getVersion());
    }
    
    
	/**
	* Asocia al atributo pesoReparto el valor recibido como parámetro.
	* @param pesoReparto El valor que va a ser asignado al atributo. 
	*/
	public void setPesoReparto(long pesoReparto) {
		this.pesoReparto = pesoReparto;
	}


	/**
	* Asocia al atributo ordenInicial el valor recibido como parámetro.
	* @param ordenInicial El valor que va a ser asignado al atributo. 
	*/
	public void setOrdenInicial(int ordenInicial) {
		this.ordenInicial = ordenInicial;
	}


	/**
	* Retorna el atributo pesoReparto.
	* @return el atributo pesoReparto
	*/
	public long getPesoReparto() {
		return this.pesoReparto;
	}
	
	/**
	* Retorna el atributo ordenInicial.
	* @return el atributo ordenInicial
	*/
	public int getOrdenInicial() {
		return this.ordenInicial;
	}


    public static OficinaCategoriaEnhanced enhanced(OficinaCategoria oficinaCategoria) {
		return (OficinaCategoriaEnhanced) Enhanced.enhance(oficinaCategoria);
	}
            /*
             *  @author Carlos Torres
             *  @chage   se agrega validacion de version diferente
             *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
             */
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}
