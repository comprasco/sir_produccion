package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.NaturalezaJuridica;
/*
 * Generated by JDO Genie (version:3.0.0 (08 Jun 2004))
 */
public class NaturalezaJuridicaEnhanced extends Enhanced {

    private String idNaturalezaJuridica; // pk 
    private String nombre;
    private GrupoNaturalezaJuridicaEnhanced grupoNaturalezaJuridica; // inverse GrupoNaturalezaJuridica.naturalezaJuridicas
	private DominioNaturalezaJuridicaEnhanced dominioNaturalezaJuridica;
	private boolean habilitadoCalificacion;
	private UsuarioEnhanced usuario;
        /*
        *  @author Carlos Torres
        *  @chage   se agrega validacion de version diferente
        *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
        */
   private long version;

    public NaturalezaJuridicaEnhanced() {
    }

    public String getIdNaturalezaJuridica() {
        return idNaturalezaJuridica;
    }

    public void setIdNaturalezaJuridica(String idNaturalezaJuridica) {
        this.idNaturalezaJuridica = idNaturalezaJuridica;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

	public UsuarioEnhanced getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioEnhanced usuario) {
		this.usuario = usuario;
	}
    public GrupoNaturalezaJuridicaEnhanced getGrupoNaturalezaJuridica() {
        return grupoNaturalezaJuridica;
    }

    public void setGrupoNaturalezaJuridica(GrupoNaturalezaJuridicaEnhanced grupoNaturalezaJuridica) {
        this.grupoNaturalezaJuridica = grupoNaturalezaJuridica;
    }
    
    public static NaturalezaJuridicaEnhanced enhance(NaturalezaJuridica naturaleza){
        return (NaturalezaJuridicaEnhanced)Enhanced.enhance(naturaleza);
    }
    

    /**
	 * @return
	 */
	public DominioNaturalezaJuridicaEnhanced getDominioNaturalezaJuridica() {
		return dominioNaturalezaJuridica;
	}

	/**
	 * @param enhanced
	 */
	public void setDominioNaturalezaJuridica(DominioNaturalezaJuridicaEnhanced enhanced) {
		dominioNaturalezaJuridica = enhanced;
	}


	/**
	 * @return
	 */
	public boolean isHabilitadoCalificacion() {
		return habilitadoCalificacion;
	}

	/**
	 * @param b
	 */
	public void setHabilitadoCalificacion(boolean b) {
		habilitadoCalificacion = b;
	}
        
        /*
        *  @author Carlos Torres
        *  @chage   se agrega validacion de version diferente
        *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
        */
        public long getVersion() {
            return version;
        }

        public void setVersion(long version) {
            this.version = version;
        }

}
