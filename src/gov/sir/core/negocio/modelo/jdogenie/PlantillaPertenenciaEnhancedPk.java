package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.PlantillaPertenenciaPk;

/**
 * Application identity objectid-class.
 */
public class PlantillaPertenenciaEnhancedPk implements java.io.Serializable {

    public String idPlantillaPertenencia;

    public PlantillaPertenenciaEnhancedPk() {
    }

    public PlantillaPertenenciaEnhancedPk(String s) {
        int i, p = 0;
        idPlantillaPertenencia = s.substring(p);
    }
    
	public PlantillaPertenenciaEnhancedPk(PlantillaPertenenciaPk id) {
		idPlantillaPertenencia = id.idPlantillaPertenencia;
	}


	  public PlantillaPertenenciaPk getPlantillaPertenenciaID() {
		PlantillaPertenenciaPk id = new PlantillaPertenenciaPk();
		 id.idPlantillaPertenencia = idPlantillaPertenencia;
		  return id;
	  }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlantillaPertenenciaEnhancedPk)) return false;

        final PlantillaPertenenciaEnhancedPk id = (PlantillaPertenenciaEnhancedPk) o;

        if (this.idPlantillaPertenencia != null ? !idPlantillaPertenencia.equals(id.idPlantillaPertenencia) : id.idPlantillaPertenencia != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idPlantillaPertenencia != null ? idPlantillaPertenencia.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idPlantillaPertenencia);
        return buffer.toString();
    }
}