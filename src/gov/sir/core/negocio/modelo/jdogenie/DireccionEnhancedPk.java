package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.DireccionPk;

/**
 * Application identity objectid-class.
 */
    public class DireccionEnhancedPk implements java.io.Serializable {
        public String idDireccion;
        public String idMatricula;

        public DireccionEnhancedPk() {
        }
		public DireccionEnhancedPk(DireccionPk id){
			idDireccion = id.idDireccion;
			idMatricula = id.idMatricula;
		}
		
		public DireccionPk getDireccionID(){
			DireccionPk id = new DireccionPk();
			id.idDireccion = idDireccion;
			id.idMatricula =idMatricula;
			return id;
		}

        public DireccionEnhancedPk(String s) {
            int i;
            int p = 0;
            i = s.indexOf('-', p);
            idDireccion = s.substring(p, i);
            p = i + 1;
            i = s.indexOf('-', p);
            idMatricula = s.substring(p, i);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (!(o instanceof DireccionEnhancedPk)) {
                return false;
            }

            final DireccionEnhancedPk id = (DireccionEnhancedPk) o;

            if ((this.idDireccion != null)
                    ? (!idDireccion.equals(id.idDireccion))
                        : (id.idDireccion != null)) {
                return false;
            }

            if ((this.idMatricula != null)
                    ? (!idMatricula.equals(id.idMatricula))
                        : (id.idMatricula != null)) {
                return false;
            }

            return true;
        }

        public int hashCode() {
            int result = 0;
            result = (29 * result) +
                ((idDireccion != null) ? idDireccion.hashCode() : 0);
            result = (29 * result) +
                ((idMatricula != null) ? idMatricula.hashCode() : 0);

            return result;
        }

        public String toString() {
            StringBuffer buffer = new StringBuffer();
            buffer.append(idDireccion);
            buffer.append('-');
            buffer.append(idMatricula);
    
            return buffer.toString();
        }
    }