package gov.sir.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author juan.rave
 */
public final class DateFormatUtil {
	public final static String DEFAULT_FORMAT = "dd/MM/yyyy";
	public final static String LONG_DATETIME_FORMAT = "dd/MM/yyyy hh:mm:ss";
	private static SimpleDateFormat simpleDateFormat;
	
	static 
	{
		simpleDateFormat = new SimpleDateFormat(DEFAULT_FORMAT);
	}
	
	public static String format(Date base) {
		simpleDateFormat.applyPattern(DEFAULT_FORMAT);
		return simpleDateFormat.format(base);
	}
	
	public static String format(String format, Date base) {
		simpleDateFormat.applyPattern(format);
		return simpleDateFormat.format(base);
	}
	
	public static Date parse(String base) throws ParseException {
		simpleDateFormat.applyPattern(DEFAULT_FORMAT);
		
		return simpleDateFormat.parse(base);
	}
	
	public static Date parse(String format, String base) throws ParseException{
		simpleDateFormat.applyPattern(format);
	
		return simpleDateFormat.parse(base);
	}
	
	/**
	 * retorna la jornada horaria (a.m. / p.m.)
	 * @param time
	 * @return
	 */
	public static String getAmPmString(int time) {
		return simpleDateFormat.getDateFormatSymbols().getAmPmStrings()[time].toLowerCase();
	}
	
	
}
