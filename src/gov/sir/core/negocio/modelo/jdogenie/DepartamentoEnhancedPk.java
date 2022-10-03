package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.DepartamentoPk;

/**
 * Application identity objectid-class.
 */
    public class DepartamentoEnhancedPk implements java.io.Serializable {
        public String idDepartamento;

        public DepartamentoEnhancedPk() {
        }
        
		public DepartamentoEnhancedPk(DepartamentoPk id){
			idDepartamento = id.idDepartamento;
		}
		
		public DepartamentoPk getDepartamentoID(){
			DepartamentoPk id = new DepartamentoPk();
			id.idDepartamento= idDepartamento;
			return id;
		}
        public DepartamentoEnhancedPk(String s) {
            int i;
            int p = 0;
            idDepartamento = s.substring(p);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (!(o instanceof DepartamentoEnhancedPk)) {
                return false;
            }

            final DepartamentoEnhancedPk id = (DepartamentoEnhancedPk) o;

            if ((this.idDepartamento != null)
                    ? (!idDepartamento.equals(id.idDepartamento))
                        : (id.idDepartamento != null)) {
                return false;
            }

            return true;
        }

        public int hashCode() {
            int result = 0;
            result = (29 * result) +
                ((idDepartamento != null) ? idDepartamento.hashCode() : 0);

            return result;
        }

        public String toString() {
            StringBuffer buffer = new StringBuffer();
            buffer.append(idDepartamento);

            return buffer.toString();
        }
    }