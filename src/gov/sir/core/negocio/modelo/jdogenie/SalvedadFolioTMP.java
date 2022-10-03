package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.SalvedadFolio;
import java.util.Date;



/*
 * Generated by JDO Genie (version:3.2.0beta7 (20 Sep 2004))
 */
public class SalvedadFolioTMP {
    private String idMatricula; // pk
    private String idSalvedadFoTmp; // pk
    private String descripcion;
    private FolioEnhanced folio;
	private Date fechaCreacion;
	private UsuarioEnhanced usuario;
   private String numRadicacion;

    /**
     * @param enhanced
     */
    public SalvedadFolioTMP(SalvedadFolioEnhanced auxSal) {
        idMatricula = auxSal.getIdMatricula();
        idSalvedadFoTmp = auxSal.getIdSalvedad();
        descripcion = auxSal.getDescripcion();
        folio = auxSal.getFolio();
        fechaCreacion = auxSal.getFechaCreacion();
        numRadicacion = auxSal.getNumRadicacion();
    }

    public SalvedadFolioTMP() {
    }

    public SalvedadFolioTMP(SalvedadFolio salvedad) {
        SalvedadFolioEnhanced auxSal = SalvedadFolioEnhanced.enhance(salvedad);
        idMatricula = auxSal.getIdMatricula();
        idSalvedadFoTmp = auxSal.getIdSalvedad();
        descripcion = auxSal.getDescripcion();
        folio = auxSal.getFolio();
		fechaCreacion = auxSal.getFechaCreacion();
      numRadicacion = auxSal.getNumRadicacion();
    }

    public String getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(String idMatricula) {
        this.idMatricula = idMatricula;
    }

    public String getIdSalvedadFoTmp() {
        return idSalvedadFoTmp;
    }

    public void setIdSalvedadFoTmp(String idSalvedadFoTmp) {
        this.idSalvedadFoTmp = idSalvedadFoTmp;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public FolioEnhanced getFolio() {
        return folio;
    }

    public void setFolio(FolioEnhanced folio) {
        this.folio = folio;
        setIdMatricula(folio.getIdMatricula());
    }

    public SalvedadFolioEnhanced getDefinitivo() {
        SalvedadFolioEnhanced sal = new SalvedadFolioEnhanced();
        sal.setDescripcion(this.getDescripcion());
        sal.setIdMatricula(this.getIdMatricula());
        sal.setIdSalvedad(this.getIdSalvedadFoTmp());
		sal.setFechaCreacion(this.getFechaCreacion());
		sal.setUsuarioCreacionTMP(this.getUsuario());
        sal.setNumRadicacion(this.getNumRadicacion());

        return sal;
    }

    /**
	 * @return
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * @param date
	 */
	public void setFechaCreacion(Date date) {
		fechaCreacion = date;
	}

	/**
	 * @return
	 */
	public UsuarioEnhanced getUsuario() {
		return usuario;
	}

	public String getNumRadicacion() {
		return numRadicacion;
	}

	/**
	 * @param enhanced
	 */
	public void setUsuario(UsuarioEnhanced enhanced) {
		usuario = enhanced;
	}

	public void setNumRadicacion(String numRadicacion) {
		this.numRadicacion = numRadicacion;
	}

}