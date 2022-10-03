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
public class BasicLongPlusThresholdComparator extends BasicAbstractComparator {

    long threshold = 0;

    public BasicLongPlusThresholdComparator( long threshold ) {
        this.threshold = threshold;
    }

    /**
     * threshold ~ 0
     * @return true if the values are similar
     */
    public static int lognPlusThresholdComparison( long source, long target, long threshold ) {
		long minusOpResult = Math.abs( source - target );
        if( minusOpResult < threshold ) {
            return 0;
        }
        return 1;
    }

    public BasicLongPlusThresholdComparator() {
    }

	public int compare(Object o1, Object o2) {
            int comparison = super.compare( o1, o2 );

            if( comparison < 2 )
                    return comparison;

        // normal case -----------------------------
        Long _source = (Long)o1;
        Long _target = (Long)o2;

        comparison = lognPlusThresholdComparison( _source.longValue(), _target.longValue(), this.threshold );
		return comparison;
	}

}
