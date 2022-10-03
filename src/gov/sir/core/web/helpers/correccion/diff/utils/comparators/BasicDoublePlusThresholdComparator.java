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
public class BasicDoublePlusThresholdComparator extends BasicAbstractComparator {

    double threshold = 0d;

    public BasicDoublePlusThresholdComparator( double threshold ) {
        this.threshold = threshold;
    }

    /**
     * threshold ~ 0
     * @return true if the values are similar
     */
    public static int doublePlusThresholdComparison( double source, double target, double threshold ) {
		double minusOpResult = Math.abs( source - target );
        if( minusOpResult < threshold ) {
            return 0;
        }
        return 1;
    }

	public int compare(Object o1, Object o2) {
            int comparison = super.compare( o1, o2 );

            if( comparison < 2 )
                    return comparison;

        // normal case -----------------------------
        Double _source = (Double)o1;
        Double _target = (Double)o2;

        comparison = doublePlusThresholdComparison( _source.doubleValue(), _target.doubleValue(), this.threshold );
		return comparison;
	}

}
