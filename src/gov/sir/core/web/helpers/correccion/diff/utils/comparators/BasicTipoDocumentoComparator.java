package gov.sir.core.web.helpers.correccion.diff.utils.comparators;

import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.web.helpers.correccion.diff.utils.comparators.BasicAbstractComparator;
import gov.sir.core.web.helpers.correccion.diff.utils.comparators.BasicStringComparator;

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
public class BasicTipoDocumentoComparator  extends BasicAbstractComparator {

	private boolean encapsulateNullValues = false;

	public BasicTipoDocumentoComparator(){
		super();
	}
    public BasicTipoDocumentoComparator( boolean encapsulateNullValues ) {
		this();

		// colocar un valor nulo dentro de un nuevo objeto con id nulo;
		this.encapsulateNullValues = encapsulateNullValues;

    }

    protected static TipoDocumento buildEncapsulatedObjectById( String id ){
		TipoDocumento objectResult = new TipoDocumento();
		objectResult.setIdTipoDocumento( id );
        return objectResult;
    }

	public int compare(Object o1, Object o2) {

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

		TipoDocumento _source = (TipoDocumento)o1;
		TipoDocumento _target = (TipoDocumento)o2;

        // compara sobre id
        BasicStringComparator stringComparator = new BasicStringComparator(true,true);
		comparison = stringComparator.compare( _source.getIdTipoDocumento(),_target.getIdTipoDocumento() );

		return comparison;

	}


}
