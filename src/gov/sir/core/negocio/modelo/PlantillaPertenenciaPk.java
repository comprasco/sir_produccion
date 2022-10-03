package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a PlantillaPertenencia	  */

public class PlantillaPertenenciaPk implements java.io.Serializable {

    public String idPlantillaPertenencia;

    public PlantillaPertenenciaPk() {
    }

    public PlantillaPertenenciaPk(String s) {
        int i, p = 0;
        idPlantillaPertenencia = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlantillaPertenenciaPk)) return false;

        final PlantillaPertenenciaPk id = (PlantillaPertenenciaPk) o;

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