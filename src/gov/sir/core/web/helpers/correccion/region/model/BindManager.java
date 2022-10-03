package gov.sir.core.web.helpers.correccion.region.model;

import java.util.Iterator;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public abstract class BindManager {
    public BindManager() {
        associate();

    }

    protected abstract void associate();


    /*
        String[] jxpath_DeltaPaths = new String[] {
             "documento" + "/" + "oficinaOrigen/vereda/municipio/departamento/idDepartamento"
           , "documento" + "/" + "oficinaOrigen/vereda/municipio/departamento/nombre"
           , "documento" + "/" + "oficinaOrigen/vereda/municipio/idMunicipio"
           , "documento" + "/" + "oficinaOrigen/vereda/municipio/nombre"
           , "documento" + "/" + "oficinaOrigen/vereda/idVereda"
           , "documento" + "/" + "oficinaOrigen/vereda/nombre"
           , "documento" + "/" + "oficinaOrigen/idOficinaOrigen"
           , "documento" + "/" + "oficinaOrigen/numero"
           , "documento" + "/" + "oficinaOrigen/nombre"
           , "documento" + "/" + "idDocumento"
           , "documento" + "/" + "tipoDocumento/idTipoDocumento"
           , "documento" + "/" + "tipoDocumento/nombre" // tipoDoc??
           , "documento" + "/" + "numero"
           , "documento" + "/" + "fecha"
        };
*/



    protected java.util.ArrayList binderList = new java.util.ArrayList();

    public Binder getBinderByPath( String path ) {
        Iterator iterator;
        iterator = binderList.iterator();
        for( ;iterator.hasNext(); ){
            Binder element = (Binder)iterator.next();
            if( path.equalsIgnoreCase( element.getPath() ) ) {
                return element;
            }
        }
        return null;
    }

    public Binder getBinderById( String id ) {
        Iterator iterator;
        iterator = binderList.iterator();
        for( ;iterator.hasNext(); ){
            Binder element = (Binder)iterator.next();
            if( id.equalsIgnoreCase( element.getId() ) ) {
                return element;
            }
        }
        return null;
    }

    // immutable class
    public static class Binder {

        private String id;
        private String path;

        private Object value;

        public Object getValue() {
            return this.value;
        }

        public void setValue( Object value ) {
            this.value = value;
        }

        public String getPath() {
            return this.path;
        }

        public String getId() {
            return this.id;
        }

        // id es el identificador del campo
        // path jxpath del objeto folio
        public Binder(String id, String path) {
            this.id = id;
            this.path = path;
        }

    }
}
