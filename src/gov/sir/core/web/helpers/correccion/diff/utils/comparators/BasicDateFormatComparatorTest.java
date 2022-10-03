package gov.sir.core.web.helpers.correccion.diff.utils.comparators;

import gov.sir.core.util.DateFormatUtil;

import java.text.ParseException;

import junit.framework.TestCase;

public class BasicDateFormatComparatorTest extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(BasicDateFormatComparatorTest.class);
	}

	public void testCompare() {
		final String DATE_FORMAT1 ="dd/MM/yyyy";
		BasicDateFormatComparator comparator = new BasicDateFormatComparator( DATE_FORMAT1 );

		java.util.Date source = null;
		java.util.Date target = null;

		assertEquals( comparator.compare( source, target ), 0 );

		try {
			source = DateFormatUtil.parse("25/08/2005");
			target = DateFormatUtil.parse("25/08/2005");
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertNotNull( source );
		assertNotNull( target );
		assertEquals( comparator.compare( source, target ), 0 );
	}

}
