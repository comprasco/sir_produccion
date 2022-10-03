package gov.sir.core.web.helpers.correccion.diff.utils.comparators;

import gov.sir.core.util.DateFormatUtil;

import java.text.ParseException;

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
public class BasicDateFormatComparator extends BasicAbstractComparator  {

    protected String formatBaseComparator = null;

    public BasicDateFormatComparator( String formatBaseComparator ) {
        this.formatBaseComparator = formatBaseComparator;
    }

    public static java.util.Date
    fixDateAccordingFormat( java.util.Date date, String format ) {

        String buffer = new String();
        buffer = DateFormatUtil.format(format, date );
        java.util.Date result = null;
        try {
            result = DateFormatUtil.parse(buffer);
        }
        catch (ParseException ex) {
            result = date;
        }

        return result;
    }

	public int compare(Object o1, Object o2) {

		int comparison = super.compare( o1, o2 );

		if( comparison < 2 )
			return comparison;


        java.util.Date _source = (java.util.Date)o1;
        java.util.Date _target = (java.util.Date)o2;

        _source = fixDateAccordingFormat( _source, this.formatBaseComparator );
        _target = fixDateAccordingFormat( _target, this.formatBaseComparator );

		comparison = _source.compareTo( _target );

		return comparison;
	}

}

