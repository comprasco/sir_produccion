package gov.sir.core.web.helpers.correccion.diff.utils.comparators;



public class BasicStringComparator extends BasicAbstractComparator {

	boolean treatBlankAsNull = false;
	boolean trimEnabled      = false;
        boolean ignoreBreaks     = false;

	public BasicStringComparator() {
		super();
	}
	public BasicStringComparator( boolean treatBlankAsNull ) {
		this();
		this.treatBlankAsNull = treatBlankAsNull;
	}
	public BasicStringComparator( boolean treatBlankAsNull, boolean trimEnabled ) {
		this( treatBlankAsNull );
		this.trimEnabled = trimEnabled;
	}
        public BasicStringComparator( boolean treatBlankAsNull, boolean trimEnabled, boolean ignoreBreaks ) {
                this( treatBlankAsNull, trimEnabled );
                this.ignoreBreaks = ignoreBreaks;
        }

	Object result;

	public int compare(Object source , Object target) {

		int comparison = super.compare( source,target );

		if( comparison < 2 )
			return comparison;

        // normal case -----------------------------
        String _source = (String)source;
        String _target = (String)target;

		if( treatBlankAsNull ) {
	          _source = ( null == _source )?(""):(_source);
	          _target = ( null == _target )?(""):(_target);
		}

		if( trimEnabled ){
	          _source = _source.trim();
	          _target = _target.trim();
		}
                if( ignoreBreaks ){

                  _source = filterBreaks( _source );
                  _target = filterBreaks( _target );
                }

        comparison = _source.compareTo( _target );

		return comparison;
	}

        public static String filterBreaks( String string ) {

          if( null == string )
            return null;

          String result = string;

          result = result.replaceAll( "\n","" );
          result = result.replaceAll( "\r","" );

          return result;

        }

  public boolean isTreatBlankAsNull() {
    return treatBlankAsNull;
  }

}
