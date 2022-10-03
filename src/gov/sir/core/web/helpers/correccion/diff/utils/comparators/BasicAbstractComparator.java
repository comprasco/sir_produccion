package gov.sir.core.web.helpers.correccion.diff.utils.comparators;

/**
 * Compara hacia adelante
 */
public abstract class BasicAbstractComparator implements BasicComparatorComponent {

    public BasicAbstractComparator() {
    }

	public int compare(Object o1, Object o2) {
		Object source = o1;
		Object target = o2;

        if( ( null == source )
		  &&( null == target ) ) {
			// no changes
			return 0;
		}

        if( ( null == source )
		  &&( null != target ) ){
		    // no clone
			return 1;
		 }

		 if( ( null != source )
		   &&( null == target ) ){
		   // no clone
			return -1;
		 }

		 return 2;
	}

}
