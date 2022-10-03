/**
 * 
 */
package gov.sir.core.negocio.modelo.jdogenie;

public class TurnoDerivadoEnhancedPk implements java.io.Serializable {

	public String anioPadre; 
    public String idCirculoPadre; 
    public long idProcesoPadre; 
    public String idTurnoPadre; 
    public String anioHijo; 
    public String idCirculoHijo; 
    public long idProcesoHijo; 
    public String idTurnoHijo;

    public TurnoDerivadoEnhancedPk() {
    }

    public TurnoDerivadoEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        anioPadre = s.substring(p, i);
        p = i + 1;
        i= s.indexOf('-', p);
        idCirculoPadre = s.substring(p, i);
        p = i + 1;
        i= s.indexOf('-', p);
        idProcesoPadre = Long.parseLong(s.substring(p, i));
        p = i + 1;
        i= s.indexOf('-', p);
        idTurnoPadre = s.substring(p, i);
        p = i + 1;
        i= s.indexOf('-', p);
        anioHijo = s.substring(p, i);
        p = i + 1;
        i = s.indexOf('-', p);
        idCirculoHijo = s.substring(p, i);
        p = i + 1;
        i = s.indexOf('-', p);
        idProcesoHijo = Long.parseLong(s.substring(p, i));
        p = i + 1;
        idTurnoHijo = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TurnoDerivadoEnhancedPk)) return false;

        final TurnoDerivadoEnhancedPk id = (TurnoDerivadoEnhancedPk) o;

        if (this.anioPadre != null ? !anioPadre.equals(id.anioPadre) : id.anioPadre != null) return false;
        if (this.idCirculoPadre != null ? !idCirculoPadre.equals(id.idCirculoPadre) : id.idCirculoPadre != null) return false;
        if (this.idProcesoPadre != id.idProcesoPadre) return false;
        if (this.idTurnoPadre != null ? !idTurnoPadre.equals(id.idTurnoPadre) : id.idTurnoPadre != null) return false;
        if (this.anioHijo != null ? !anioHijo.equals(id.anioHijo) : id.anioHijo != null) return false;
        if (this.idCirculoHijo != null ? !idCirculoHijo.equals(id.idCirculoHijo) : id.idCirculoHijo != null) return false;
        if (this.idProcesoHijo != id.idProcesoHijo) return false;
        if (this.idTurnoHijo != null ? !idTurnoHijo.equals(id.idTurnoHijo) : id.idTurnoHijo != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (anioPadre != null ? anioPadre.hashCode() : 0);
        result = 29 * result + (idCirculoPadre != null ? idCirculoPadre.hashCode() : 0);
        result = 29 * result + (int) (idProcesoPadre ^ (idProcesoPadre >>> 32));
        result = 29 * result + (idTurnoPadre != null ? idTurnoPadre.hashCode() : 0);
        result = 29 * result + (anioHijo != null ? anioHijo.hashCode() : 0);
        result = 29 * result + (idCirculoHijo != null ? idCirculoHijo.hashCode() : 0);
        result = 29 * result + (int) (idProcesoHijo ^ (idProcesoHijo >>> 32));
        result = 29 * result + (idTurnoHijo != null ? idTurnoHijo.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(anioPadre);
        buffer.append('-');
        buffer.append(idCirculoPadre);
        buffer.append('-');
        buffer.append(idProcesoPadre);
        buffer.append('-');
        buffer.append(idTurnoPadre);
        buffer.append('-');
        buffer.append(anioHijo);
        buffer.append('-');
        buffer.append(idCirculoHijo);
        buffer.append('-');
        buffer.append(idProcesoHijo);
        buffer.append('-');
        buffer.append(idTurnoHijo);
        return buffer.toString();
    }
}