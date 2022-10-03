package gov.sir.core.eventos.correccion;

import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Oficio;
import gov.sir.core.negocio.modelo.Turno;
import java.util.List;

public class Evn_CorrSimpleMain_CopiaSalvedadOptions extends EvnSIR {



   public static final String CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTART__EVENT
       = "CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTART__EVENT";
   public static final String CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP1NEXT__EVENT
       = "CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP1NEXT__EVENT";
   public static final String CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP2NEXT__EVENT
       = "CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP2NEXT__EVENT";
   public static final String CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP3PROCESS__EVENT
       = "CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP3PROCESS__EVENT";

   org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga;
   gov.sir.core.negocio.modelo.Usuario usuarioSir;
   gov.sir.core.negocio.modelo.Folio folio;
   gov.sir.core.negocio.modelo.Turno turno;
   gov.sir.core.negocio.modelo.Oficio oficio;
   String tipoActualizacion;

   java.util.List conjuntoFolios;

  String selectedSource_FolioIdMatricula;

  // List< String >
  String[] selectedTarget_FolioIdMatriculaArray;

  // String
  String salvedadTx;

  // List< Folio >
  List selectedTarget_FolioList;
  // Folio
  Folio selectedSource_FolioItem;
  // String
  String selectedSalvedadTx;



	public Evn_CorrSimpleMain_CopiaSalvedadOptions( String tipoEvento ) {
        super(null, tipoEvento);
    }

    public void setUsuarioAuriga( org.auriga.core.modelo.transferObjects.Usuario newUsuarioAuriga ) {
      usuarioAuriga= newUsuarioAuriga;
    }

    public void setUsuarioSir( gov.sir.core.negocio.modelo.Usuario newUsuarioSir ) {
      usuarioSir = newUsuarioSir;
    }

	public gov.sir.core.negocio.modelo.Usuario getUsuarioSir() {
		return usuarioSir;
	}

	public org.auriga.core.modelo.transferObjects.Usuario getUsuarioAuriga() {
		return usuarioAuriga;
	}

	public Folio getFolio() {
		return folio;
	}

	public Turno getTurno() {
		return turno;
	}

	public String getTipoActualizacion() {
		return tipoActualizacion;
	}

	public Oficio getOficio() {
		return oficio;
	}

	public List getConjuntoFolios() {
		return conjuntoFolios;
	}

	public String getSelectedSource_FolioIdMatricula() {
		return selectedSource_FolioIdMatricula;
	}

	public String[] getSelectedTarget_FolioIdMatriculaArray() {
		return selectedTarget_FolioIdMatriculaArray;
	}

	public String getSalvedadTx() {
		return salvedadTx;
	}

	public String getSelectedSalvedadTx() {
		return selectedSalvedadTx;
	}

	public List getSelectedTarget_FolioList() {
		return selectedTarget_FolioList;
	}

	public Folio getSelectedSource_FolioItem() {
		return selectedSource_FolioItem;
	}

	public void setFolio( Folio newFolio ) {
     folio = newFolio;
  }

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	public void setTipoActualizacion(String tipoActualizacion) {
		this.tipoActualizacion = tipoActualizacion;
	}

	public void setOficio(Oficio oficio) {
		this.oficio = oficio;
	}

	public void setConjuntoFolios(List conjuntoFolios) {
		this.conjuntoFolios = conjuntoFolios;
	}

	public void setSelectedSource_FolioIdMatricula(String
		 selectedSource_FolioIdMatricula) {
		this.selectedSource_FolioIdMatricula = selectedSource_FolioIdMatricula;
	}

	public void setSelectedTarget_FolioIdMatriculaArray(String[]
		 selectedTarget_FolioIdMatriculaArray) {
		this.selectedTarget_FolioIdMatriculaArray =
			 selectedTarget_FolioIdMatriculaArray;
	}

	public void setSalvedadTx(String salvedadTx) {
		this.salvedadTx = salvedadTx;
	}

	public void setSelectedSalvedadTx(String selectedSalvedadTx) {
		this.selectedSalvedadTx = selectedSalvedadTx;
	}

	public void setSelectedSource_FolioItem(Folio selectedSource_FolioItem) {
		this.selectedSource_FolioItem = selectedSource_FolioItem;
	}

	public void setSelectedTarget_FolioList(List selectedTarget_FolioList) {
		this.selectedTarget_FolioList = selectedTarget_FolioList;
	}


}
