package gov.sir.core.negocio.modelo;

/**
* @Autor: Santiago V�squez
* @Change: 2062.TARIFAS.REGISTRALES.2017
*/
public class TarifaRegistralPk {
    
    public String idTarifa;

    public TarifaRegistralPk() {
    }

    public TarifaRegistralPk(String s) {
        int i, p = 0;
        idTarifa = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoActoPk)) return false;

        final TipoActoPk id = (TipoActoPk) o;

        if (this.idTarifa != null ? !idTarifa.equals(id.idTipoActo) : id.idTipoActo != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idTarifa != null ? idTarifa.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTarifa);
        return buffer.toString();
    }
}
