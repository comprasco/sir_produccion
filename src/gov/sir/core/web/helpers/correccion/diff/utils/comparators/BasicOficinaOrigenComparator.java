package gov.sir.core.web.helpers.correccion.diff.utils.comparators;

import gov.sir.core.negocio.modelo.OficinaOrigen;

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
public class BasicOficinaOrigenComparator  extends BasicAbstractComparator {

	private boolean encapsulateNullValues = false;

	public BasicOficinaOrigenComparator(){
		super();
	}
    public BasicOficinaOrigenComparator( boolean encapsulateNullValues ) {
		this();

		// colocar un valor nulo dentro de un nuevo objeto con id nulo;
		this.encapsulateNullValues = encapsulateNullValues;

    }

    protected static OficinaOrigen buildEncapsulatedObjectById( String id,Long version ){
        OficinaOrigen objectResult = new OficinaOrigen();
		objectResult.setIdOficinaOrigen( id );
                 /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */
                objectResult.setVersion(version);
        return objectResult;
    }


	public int compare(Object o1, Object o2) {

        if( encapsulateNullValues ){
            if( null == o1 ){
                o1 = buildEncapsulatedObjectById( null,null );
            }
            if( null == o2 ){
                o2= buildEncapsulatedObjectById( null,null );
            }
        }

		int comparison = super.compare( o1, o2 );

		if( comparison < 2 )
			return comparison;

        OficinaOrigen _source = (OficinaOrigen)o1;
        OficinaOrigen _target = (OficinaOrigen)o2;

        // compara sobre id
        BasicStringComparator stringComparator = new BasicStringComparator(true,true);
		comparison = stringComparator.compare( _source.getIdOficinaOrigen(),_target.getIdOficinaOrigen() );
                    
                    /*
                     *  @author Carlos Torres
                     *  @chage   se agrega validacion de version diferente
                     *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                     */
                if(comparison == 0){
                    comparison = stringComparator.compare( _source.getVersion(),_target.getVersion() );
                }

		return comparison;

	}

}
