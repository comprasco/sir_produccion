package gov.sir.core.web.helpers.correccion.diff.utils.comparators;

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
public class BasicDateComparator extends BasicAbstractComparator {

	public int compare(Object o1, Object o2) {

		int comparison = super.compare( o1, o2 );

		if( comparison < 2 )
			return comparison;

        java.util.Date _source = (java.util.Date)o1;
        java.util.Date _target = (java.util.Date)o2;
        comparison = _source.compareTo( _target );
		return comparison;
	}

}
