package gov.sir.core.negocio.modelo;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import org.auriga.core.modelo.TransferObject;

/*
 * Generated by Versant Open Access (version:3.2.9 (17 Jan 2005)) */

/** Clase que modela la informacion de minutas solicitadas  */

public class Minuta implements TransferObject{
    private static final long serialVersionUID = 1L;
    private String idMinuta; // pk 
    private String comentario;
    private long estado;
    private Date fechaCreacion;
    private boolean normal;
    private long numeroFolios;
    private long unidades;
    private double valor;
    //private AccionNotarial accionNotarial;
    private Categoria categoria;
    private OficinaCategoria oficinaCategoriaAsignada;
    private RepartoNotarial repartoNotarial;
    private Solicitud solicitud;
	private CirculoNotarial circuloNotarial;
    private List entidadesPublicas = new ArrayList(); // contains MinutaEntidadPublicaEnhanced  inverse MinutaEntidadPublicaEnhanced.minuta
    private List otorgantesNaturales = new ArrayList(); // contains OtorganteNaturalEnhanced  inverse OtorganteNaturalEnhanced.minuta
    private List accionesNotariales = new ArrayList(); // contains AccionNotarialEnhanced  inverse AccionNotarial.minuta
    private Usuario usuarioAnula;
    private Date fechaAnulada;
    
    private int numModificaciones;

    /** Constructor por defecto */
    public Minuta() {
    }

    /** Retorna el identificador de la minuta correspondiente al identificador de la solicitud */
    public String getIdMinuta() {
        return idMinuta;
    }

    /** Cambia el identificador de la minuta correspondiente al identificador de la solicitud */
    public void setIdMinuta(String idMinuta) {
        this.idMinuta = idMinuta;
    }

    /** Retorna el comentario de la minuta */
    public String getComentario() {
        return comentario;
    }

    /** Cambia el comentario de la minuta */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /** Indica si la minuta es anulada */
    public long getEstado() {
        return estado;
    }

    /** Cambia el flag que indica si la minuta es anulada */
    public void setEstado(long estado) {
        this.estado = estado;
    }

    /** Retorna la fecha de creaci�n del registro en la base de datos */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /** Cambia la fecha de creaci�n del registro en la base de datos */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /** Retorna el n�umero de folios asociados a la minuta */
    public long getNumeroFolios() {
        return numeroFolios;
    }

    /** Cambia el n�umero de folios asociados a la minuta */
    public void setNumeroFolios(long numeroFolios) {
        this.numeroFolios = numeroFolios;
    }

    /** Retorna las unidades inmobiliarias asociadas a la matr�cula */
    public long getUnidades() {
        return unidades;
    }

    /** Cambia las unidades inmobiliarias asociadas a la matr�cula */
    public void setUnidades(long unidades) {
        this.unidades = unidades;
    }

    /** Retorna el valor de la minuta  */
    public double getValor() {
        return valor;
    }
    
    /** Cambia el valor de la minuta  */
    public void setValor(double valor) {
        this.valor = valor;
    }

    /** Retorna la acci�n notarial asociada a la minuta  */
    /*public AccionNotarial getAccionNotarial() {
        return accionNotarial;
    }*/

    /** Cambia la acci�n notarial asociada a la minuta  */
    /*public void setAccionNotarial(AccionNotarial accionNotarial) {
        this.accionNotarial = accionNotarial;
    }*/

    /** Retorna el identificador de la categor�a asociada a la minuta */
    public Categoria getCategoria() {
        return categoria;
    }

    /** Cambia el identificador de la categor�a asociada a la minuta */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    /** Retorna el identificador de la oficina asignada de acuerdo a la minuta */
    public OficinaCategoria getOficinaCategoriaAsignada() {
        return oficinaCategoriaAsignada;
    }

    /** Modifica el identificador de la oficina asignada de acuerdo a la minuta */
    public void setOficinaCategoriaAsignada(OficinaCategoria oficinaCategoriaAsignada) {
        this.oficinaCategoriaAsignada = oficinaCategoriaAsignada;
    }

    /** Retorna el identificador del reparto notarial en que se reparte la minuta  */
    public RepartoNotarial getRepartoNotarial() {
        return repartoNotarial;
    }

    /** Cambia el identificador del reparto notarial en que se reparte la minuta  */
    public void setRepartoNotarial(RepartoNotarial repartoNotarial) {
        this.repartoNotarial = repartoNotarial;
    }

    /** Retorna el identificador de la minuta asociado a la solicitud  */
    public Solicitud getSolicitud() {
        return solicitud;
    }

    /** Modifica el identificador de la minuta asociado a la solicitud  */
    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    /** Retorna la lista entidadesPublicas */
    public List getEntidadesPublicas() {
        return Collections.unmodifiableList(entidadesPublicas);
    }

    /** A�ade una entidad p�blica a la lista entidadesPublicas */
    public boolean addEntidadesPublica(MinutaEntidadPublica newEntidadesPublica) {
        return entidadesPublicas.add(newEntidadesPublica);
    }

    /** Elimina una entidad p�blica a la lista entidadesPublicas */
    public boolean removeEntidadesPublica(MinutaEntidadPublica oldEntidadesPublica) {
        return entidadesPublicas.remove(oldEntidadesPublica);
    }

    /** Retorna la lista otorgantesNaturales  */
    public List getOtorgantesNaturales() {
        return Collections.unmodifiableList(otorgantesNaturales);
    }

    /** A�ade un otorgante natural a la lista otorgantesNaturales */
    public boolean addOtorgantesNaturale(OtorganteNatural newOtorgantesNaturale) {
        return otorgantesNaturales.add(newOtorgantesNaturale);
    }

    /** Elimina un otorgante natural a la lista otorgantesNaturales */
    public boolean removeOtorgantesNaturale(OtorganteNatural oldOtorgantesNaturale) {
        return otorgantesNaturales.remove(oldOtorgantesNaturale);
    }
    
    /** Retorna la lista de accciones notariales*/
    public List getAccionesNotariales(){
    	return Collections.unmodifiableList(accionesNotariales);
    }
    
    /** Anade una accion notarial a la lista de acciones notariales*/
    public boolean addAccionesNotariale(MinutaAccionNotarial newAccionNotariale){
    	return accionesNotariales.add(newAccionNotariale);
    }
    
    /** Elimina una accion notarial de la lista de acciones notariales*/
    public boolean removeAccionesNotariale(MinutaAccionNotarial oldAccionNotariale){
    	return accionesNotariales.remove(oldAccionNotariale);
    }
    

    /** Indica si la minuta es normal o extraordinaria
	 * @return normal
	 */
	public boolean isNormal() {
		return normal;
	}

	/** Modifica la marca que indica si la minuta es normal o extraordinaria
	 * @param b
	 */
	public void setNormal(boolean b) {
		normal = b;
	}

	/** Rertorna el identificador del c�rculo notarial asociado a la minuta
	 * @return circuloNotarial
	 */
	public CirculoNotarial getCirculoNotarial() {
		return circuloNotarial;
	}

	/** Cambia el identificador del c�rculo notarial asociado a la minuta
	 * @param notarial
	 */
	public void setCirculoNotarial(CirculoNotarial notarial) {
		circuloNotarial = notarial;
	}

	/** Retorna el n�mero de ediciones que se han realizado a la minuta
	 * @return El n�mero de modificaciones que se han hecho sobre la minuta	 */
	public int getNumModificaciones() {
		return numModificaciones;
	}

	/** Cambia el n�mero de ediciones que se han realizado a la minuta
	 * @param El n�mero de modificaciones que se han hecho sobre la minuta    */
	public void setNumModificaciones(int i) {
		numModificaciones = i;
	}

	/**
	 * @return Returns the fechaAnulada.
	 */
	public Date getFechaAnulada() {
		return fechaAnulada;
	}
	/**
	 * @param fechaAnulada The fechaAnulada to set.
	 */
	public void setFechaAnulada(Date fechaAnulada) {
		this.fechaAnulada = fechaAnulada;
	}
	/**
	 * @return Returns the usuarioAnula.
	 */
	public Usuario getUsuarioAnula() {
		return usuarioAnula;
	}
	/**
	 * @param usuarioAnula The usuarioAnula to set.
	 */
	public void setUsuarioAnula(Usuario usuarioAnula) {
		this.usuarioAnula = usuarioAnula;
	}
}
