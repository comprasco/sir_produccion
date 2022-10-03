package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.BancosXCirculoPk;

public class BancosXCirculoEnhancedPk implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String idCirculo;
	public String idBanco;
	
	public BancosXCirculoEnhancedPk() {
	}
	
	public BancosXCirculoEnhancedPk(String s) {
		int i;
		int p = 0;
		i = s.indexOf('-', p);
		idCirculo = s.substring(p, i);
		p = i + 1;
		idBanco = s.substring(p);
	}
	
	public BancosXCirculoEnhancedPk(BancosXCirculoPk id) {
		idCirculo=id.idCirculo;
		idBanco=id.idBanco;
	}
	
	public BancosXCirculoPk getBancosXCirculoId(){
		BancosXCirculoPk id=new BancosXCirculoPk();
		id.idCirculo=idCirculo;
		id.idBanco=idBanco;
		return id;
	}
	
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof BancosXCirculoEnhancedPk)) {
			return false;
		}
		final BancosXCirculoEnhancedPk id = (BancosXCirculoEnhancedPk) o;

		if ((this.idCirculo != null)
				? (!idCirculo.equals(id.idCirculo))
					: (id.idCirculo != null)) {
			return false;
		}
		if ((this.idBanco != null)
				? (!idBanco.equals(id.idBanco))
					: (id.idBanco != null)) {
			return false;
		}
		return true;
	}
	
	public int hashCode() {
		int result = 0;
		result = (29 * result) +
			((idCirculo != null) ? idCirculo.hashCode() : 0);
		result = (29 * result) +
			((idBanco != null) ? idBanco.hashCode() : 0);
		return result;
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(idCirculo);
		buffer.append('-');
		buffer.append(idBanco);
		return buffer.toString();
	}

}
