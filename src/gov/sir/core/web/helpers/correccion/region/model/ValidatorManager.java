package gov.sir.core.web.helpers.correccion.region.model;

import gov.sir.core.web.helpers.correccion.validate.utils.validators.ConditionalValidator;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
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
public class ValidatorManager {

    public static class Binder {
        private String id;
        private ConditionalValidator conditionalValidator;
        private String errorMessage;

        public ConditionalValidator getConditionalValidator(){
            return conditionalValidator;
        }

        public String getErrorMessage(){
            return errorMessage;
        }

        public String getId() {
            return this.id;
        }


        public Binder( String id, ConditionalValidator conditionalValidator, String errorMessage  ) {
            this.id = id;
            this.conditionalValidator = conditionalValidator;
            this.errorMessage = errorMessage;
        }


    }

    public ValidatorManager() {
    }

    public java.util.ArrayList binderList = new java.util.ArrayList();

    public ConditionalValidator[] getValidationsById( String id ) {
        java.util.List resultList = new java.util.ArrayList();
        Iterator iterator;
        iterator = binderList.iterator();
        for( ;iterator.hasNext(); ){
            Binder element = (Binder)iterator.next();
            if( id.equalsIgnoreCase( element.getId() ) ) {
                resultList.add( element );
            }
        }

        ConditionalValidator[] resultArray = new ConditionalValidator[resultList.size()];
        resultArray = (ConditionalValidator[])resultList.toArray( resultArray );
        return resultArray;
    }

    public void addBinder( Binder binder ){
        binderList.add( binder );
    }

    // se aplican el conjunto de validadores a los campos
    public void trigger( ValidacionParametrosException exceptionWriter ) {

        Iterator iterator;
        iterator = binderList.iterator();
        for( ;iterator.hasNext(); ){
            Binder element = (Binder)iterator.next();

            ConditionalValidator conditionalValidator = element.getConditionalValidator();
            conditionalValidator.validate();
            
            /** @author : HGOMEZ
            *** @change : Se valida que no genere excepciones para el número de documento para cuando
            *** se ingresan información alfanumérica.
            *** Caso Mantis : 11691
            */
            if( conditionalValidator.getResult() != true && !element.id.equals("field:folio:documento/numero")
                && !element.id.equals("field:folio:anotacion[i]:documento/numero")) {
                exceptionWriter.addError( element.getErrorMessage() );
            }

        }

    }
}
