package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a TipoNota */

public class TipoNotaPk implements java.io.Serializable {

    public String idTipoNota;
    /**
     * * @author : HGOMEZ, FPADILLA ** @change : Se agregan los campos versio y activo.
     * version de cada una de ellas. ** Caso Mantis : 12621
     */
    public long version;
    public int activo;

    public TipoNotaPk() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TipoNotaPk other = (TipoNotaPk) obj;
        if ((this.idTipoNota == null) ? (other.idTipoNota != null) : !this.idTipoNota.equals(other.idTipoNota)) {
            return false;
        }
        if (this.version != other.version) {
            return false;
        }
        if (this.activo != other.activo) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + (this.idTipoNota != null ? this.idTipoNota.hashCode() : 0);
        hash = 23 * hash + (int) (this.version ^ (this.version >>> 32));
        hash = 23 * hash + this.activo;
        return hash;
    }

    @Override
    public String toString() {
        return "TipoNotaPk{" + "idTipoNota=" + idTipoNota + ", version=" + version + ", activo=" + activo + '}';
    }

    public TipoNotaPk(String idTipoNota, long version, int activo) {
        this.idTipoNota = idTipoNota;
        this.version = version;
        this.activo = activo;
    }
}
