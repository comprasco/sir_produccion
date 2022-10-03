package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a UsuarioCirculo */

public class UsuarioCirculoPk implements java.io.Serializable {
	public String idCirculo;
	public long idUsuario;

	public UsuarioCirculoPk() {
	}


	public UsuarioCirculoPk(UsuarioCirculoPk id) {
		idCirculo = id.idCirculo;
		idUsuario = id.idUsuario;
	}


	public UsuarioCirculoPk getUsuarioCirculoID() {
		UsuarioCirculoPk rta = new UsuarioCirculoPk();
		rta.idCirculo = idCirculo;
		rta.idUsuario = idUsuario;
		return rta;
	}

	public UsuarioCirculoPk(String s) {
		int i;
		int p = 0;
		i = s.indexOf('-', p);
		idCirculo = s.substring(p, i);
		p = i + 1;
		i = s.indexOf('-', p);
		idUsuario = Integer.parseInt(s.substring(p));
	
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof UsuarioCirculoPk)) {
			return false;
		}

		final UsuarioCirculoPk id = (UsuarioCirculoPk) o;

		if ((this.idCirculo != null)
				? (!idCirculo.equals(id.idCirculo))
					: (id.idCirculo != null)) {
			return false;
		}

		if (this.idUsuario != id.idUsuario) {
			   return false;
		   }

		return true;
	}

	public int hashCode() {
		int result = 0;
		result = (29 * result) +
			((idCirculo != null) ? idCirculo.hashCode() : 0);
		result = (29 * result) + (int) idUsuario;
	
		return result;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(idCirculo);
		buffer.append('-');
		buffer.append(idUsuario);

		return buffer.toString();
	}
}