package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.BancoPk;

/**
 * Application identity objectid-class.
 */
public class BancoEnhancedPk implements java.io.Serializable {

	public String idBanco;

	public BancoEnhancedPk() {
	}

	public BancoEnhancedPk(BancoPk id){
		idBanco = id.idBanco;
	}
	
	public BancoPk getBancoID(){
		BancoPk id = new BancoPk();
		id.idBanco = idBanco;
		return id;
	}

	public BancoEnhancedPk(String s) {
		int i, p = 0;
		idBanco = s.substring(p);
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BancoEnhancedPk)) return false;

		final BancoEnhancedPk id = (BancoEnhancedPk) o;

		if (this.idBanco != null ? !idBanco.equals(id.idBanco) : id.idBanco != null) return false;
		return true;
	}

	public int hashCode() {
		int result = 0;
		result = 29 * result + (idBanco != null ? idBanco.hashCode() : 0);
		return result;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(idBanco);
		return buffer.toString();
	}
}