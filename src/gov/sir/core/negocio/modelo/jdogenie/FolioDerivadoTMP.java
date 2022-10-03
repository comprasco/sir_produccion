package gov.sir.core.negocio.modelo.jdogenie;


import java.util.List;

import gov.sir.core.negocio.modelo.FolioDerivado;


/*
 * Generated by JDO Genie (version:3.2.0beta7 (20 Sep 2004))
 */
public class FolioDerivadoTMP {
    /**
	 * @param enhanced
	 */
	public FolioDerivadoTMP(FolioDerivadoEnhanced der, List cache) {
		idAnotacion1Tmp = der.getIdAnotacion1(); // pk 
		idAnotacionTmp = der.getIdAnotacion(); // pk 
		idMatricula = der.getIdMatricula(); // pk 
		idMatricula1 = der.getIdMatricula1(); // pk 
		porcentaje = der.getPorcentaje();
		area = der.getArea();
                hectareas = der.getHectareas();
                metros = der.getMetros();
                centimetros = der.getCentimetros();
                privMetros = der.getPrivMetros();
                privCentimetros = der.getPrivCentimetros();
                consMetros = der.getConsMetros();
                consCentimetros = der.getConsCentimetros();
                determinaInm = der.getDeterminaInm();
		descripcion = der.getDescripcion();
		if(der.getHijo()!=null)
			hijoTmp = new AnotacionTMP(der.getHijo(), cache);
		if(der.getPadre()!=null){
			padreTmp = new AnotacionTMP(der.getPadre(), cache);
		}
		lote = der.getLote();
	}


    private String idAnotacion1Tmp; // pk 
    private String idAnotacionTmp; // pk 
    private String idMatricula; // pk 
    private String idMatricula1; // pk 
    private String porcentaje;
    private String area;
    private String hectareas;
    private String metros;
    private String centimetros;
    private String privMetros;
    private String privCentimetros;
    private String consMetros;
    private String consCentimetros;
    private String determinaInm; 
    private String descripcion;
    private AnotacionTMP hijoTmp; // inverse AnotacionTMP.anotacionesPadreTMP
    private AnotacionTMP padreTmp; // inverse AnotacionTMP.anotacionesHijosTMP
	private String lote;
	private boolean toDelete;
 
    public FolioDerivadoTMP(FolioDerivado der) {
        idAnotacion1Tmp = der.getIdAnotacion1(); // pk 
        idAnotacionTmp = der.getIdAnotacion(); // pk 
        idMatricula = der.getIdMatricula(); // pk 
        idMatricula1 = der.getIdMatricula1(); // pk 
        porcentaje = der.getPorcentaje();
        area = der.getArea();
        hectareas = der.getHectareas();
        metros = der.getMetros();
        centimetros = der.getCentimetros();
        privMetros = der.getPrivMetros();
        privCentimetros = der.getPrivCentimetros();
        consMetros = der.getConsMetros();
        consCentimetros = der.getConsCentimetros();
        determinaInm = der.getDeterminaInm();
        descripcion = der.getDescripcion();
        lote = der.getLote();
    }
    

    public FolioDerivadoTMP() {
    }

    public String getIdAnotacion1Tmp() {
        return idAnotacion1Tmp;
    }

    public void setIdAnotacion1Tmp(String idAnotacion1Tmp) {
        this.idAnotacion1Tmp = idAnotacion1Tmp;
    }

    public String getIdAnotacionTmp() {
        return idAnotacionTmp;
    }

    public void setIdAnotacionTmp(String idAnotacionTmp) {
        this.idAnotacionTmp = idAnotacionTmp;
    }

    public String getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(String idMatricula) {
        this.idMatricula = idMatricula;
    }

    public String getIdMatricula1() {
        return idMatricula1;
    }

    public void setIdMatricula1(String idMatricula1) {
        this.idMatricula1 = idMatricula1;
    }

    public AnotacionTMP getHijoTmp() {
        return hijoTmp;
    }

    public void setHijoTmp(AnotacionTMP hijoTmp) {
        this.hijoTmp = hijoTmp;
		setIdAnotacion1Tmp(hijoTmp.getIdAnotacionTmp());
		setIdMatricula1(hijoTmp.getIdMatricula());
    }

    public AnotacionTMP getPadreTmp() {
        return padreTmp;
    }

    public void setPadreTmp(AnotacionTMP padreTmp) {
        this.padreTmp = padreTmp;
        setIdAnotacionTmp(padreTmp.getIdAnotacionTmp());
        setIdMatricula(padreTmp.getIdMatricula());
    }

    /**
     * @return
     */
    public String getArea() {
        return area;
    }

    public String getHectareas() {
        return hectareas;
    }

    public void setHectareas(String hectareas) {
        this.hectareas = hectareas;
    }

    public String getMetros() {
        return metros;
    }

    public void setMetros(String metros) {
        this.metros = metros;
    }

    public String getCentimetros() {
        return centimetros;
    }

    public void setCentimetros(String centimetros) {
        this.centimetros = centimetros;
    }

    public String getPrivMetros() {
        return privMetros;
    }

    public void setPrivMetros(String privMetros) {
        this.privMetros = privMetros;
    }

    public String getPrivCentimetros() {
        return privCentimetros;
    }

    public void setPrivCentimetros(String privCentimetros) {
        this.privCentimetros = privCentimetros;
    }

    public String getConsMetros() {
        return consMetros;
    }

    public void setConsMetros(String consMetros) {
        this.consMetros = consMetros;
    }

    public String getConsCentimetros() {
        return consCentimetros;
    }

    public void setConsCentimetros(String consCentimetros) {
        this.consCentimetros = consCentimetros;
    }

    public String getDeterminaInm() {
        return determinaInm;
    }

    public void setDeterminaInm(String determinaInm) {
        this.determinaInm = determinaInm;
    }
    
    /**
     * @return
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @return
     */
    public String getPorcentaje() {
        return porcentaje;
    }

    /**
     * @param string
     */
    public void setArea(String string) {
        area = string;
    }

    /**
     * @param string
     */
    public void setDescripcion(String string) {
        descripcion = string;
    }

    /**
     * @param string
     */
    public void setPorcentaje(String string) {
        porcentaje = string;
    }

    public FolioDerivadoEnhanced getDefinitivo(List cache) {
		 FolioDerivadoEnhanced def = new FolioDerivadoEnhanced();
                def.setArea(this.getArea());
                def.setDescripcion(this.getDescripcion());

                if (this.getHijoTmp() != null) {
                    def.setHijo(this.getHijoTmp().getDefinitivo(cache));
                }
                if (this.getPadreTmp() != null) {
                    def.setPadre(this.getPadreTmp().getDefinitivo(cache));
                }
                def.setIdAnotacion(this.getIdAnotacionTmp());
                def.setIdAnotacion1(this.getIdAnotacion1Tmp());
                def.setIdMatricula(this.getIdMatricula());
                def.setIdMatricula1(this.getIdMatricula1());
                def.setPorcentaje(this.getPorcentaje());
                def.setLote(this.getLote());
                return def;
	}
	/**
	 * @return
	 */
	public String getLote() {
		return lote;
	}

	/**
	 * @param string
	 */
	public void setLote(String string) {
		lote = string;
	}

	/**
	 * @return
	 */
	public boolean isToDelete() {
		return toDelete;
	}

	/**
	 * @param b
	 */
	public void setToDelete(boolean b) {
		toDelete = b;
	}
	

}