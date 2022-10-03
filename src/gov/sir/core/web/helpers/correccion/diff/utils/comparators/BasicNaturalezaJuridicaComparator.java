package gov.sir.core.web.helpers.correccion.diff.utils.comparators;

import gov.sir.core.negocio.modelo.NaturalezaJuridica;

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
public class BasicNaturalezaJuridicaComparator  extends BasicAbstractComparator {

        private boolean encapsulateNullValues = false;

        public BasicNaturalezaJuridicaComparator(){
                super();
        }
        public BasicNaturalezaJuridicaComparator( boolean encapsulateNullValues ) {
                    this();

                    // colocar un valor nulo dentro de un nuevo objeto con id nulo;
                    this.encapsulateNullValues = encapsulateNullValues;

        }

        protected static NaturalezaJuridica buildEncapsulatedObjectById( String id ){
            NaturalezaJuridica objectResult = new NaturalezaJuridica();
            objectResult.setIdNaturalezaJuridica( id );
            return objectResult;
        }


	public int compare(Object o1, Object o2) {

		// inicialmente la comparacion se realiza
		// solo sobre el id

    if( encapsulateNullValues ){
        if( null == o1 ){
            o1 = buildEncapsulatedObjectById( null );
        }
        if( null == o2 ){
            o2= buildEncapsulatedObjectById( null );
        }
    }

            int comparison = super.compare( o1, o2 );

            if( comparison < 2 )
                    return comparison;

        NaturalezaJuridica _source = (NaturalezaJuridica)o1;
        NaturalezaJuridica _target = (NaturalezaJuridica)o2;

        // compara sobre id
        BasicStringComparator stringComparator = new BasicStringComparator();
		comparison = stringComparator.compare( _source.getIdNaturalezaJuridica(),_target.getIdNaturalezaJuridica() );
		return comparison;
	}

}
