package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.CancelacionPk;

/**
 * Application identity objectid-class.
 */
    public class CancelacionEnhancedPk implements java.io.Serializable {
        public String idAnotacion;
        public String idAnotacion1;
        public String idMatricula;

        public CancelacionEnhancedPk() {
        }

		public CancelacionEnhancedPk(CancelacionPk id){
			idAnotacion = id.idAnotacion;
			idAnotacion1 = id.idAnotacion1;
			idMatricula = id.idMatricula;
		}
		
		public CancelacionPk getCancelacionID(){
			CancelacionPk id = new CancelacionPk();
			id.idAnotacion = idAnotacion;
			id.idAnotacion1 = idAnotacion1;
			id.idMatricula = idMatricula;
			return id;
		}

        public CancelacionEnhancedPk(String s) {
            int i;
            int p = 0;
            i = s.indexOf('-', p);
            idAnotacion = s.substring(p, i);
            p = i + 1;
            i = s.indexOf('-', p);
            idAnotacion1 = s.substring(p, i);
            p = i + 1;
            i = s.indexOf('-', p);
            idMatricula = s.substring(p, i);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (!(o instanceof CancelacionEnhancedPk)) {
                return false;
            }

            final CancelacionEnhancedPk id = (CancelacionEnhancedPk) o;

            if ((this.idAnotacion != null)
                    ? (!idAnotacion.equals(id.idAnotacion))
                        : (id.idAnotacion != null)) {
                return false;
            }

            if ((this.idAnotacion1 != null)
                    ? (!idAnotacion1.equals(id.idAnotacion1))
                        : (id.idAnotacion1 != null)) {
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
                ((idAnotacion != null) ? idAnotacion.hashCode() : 0);
            result = (29 * result) +
                ((idAnotacion1 != null) ? idAnotacion1.hashCode() : 0);
            result = (29 * result) +
                ((idMatricula != null) ? idMatricula.hashCode() : 0);
     
            return result;
        }

        public String toString() {
            StringBuffer buffer = new StringBuffer();
            buffer.append(idAnotacion);
            buffer.append('-');
            buffer.append(idAnotacion1);
            buffer.append('-');
            buffer.append(idMatricula);

            return buffer.toString();
        }
    }