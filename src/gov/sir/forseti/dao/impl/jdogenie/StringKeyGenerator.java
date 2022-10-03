/*
 * Created on 18-ago-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.forseti.dao.impl.jdogenie;

import gov.sir.forseti.ForsetiProperties;

import java.sql.Connection;
import java.sql.SQLException;

import com.versant.core.jdbc.JdbcKeyGenerator;
import com.versant.core.jdbc.JdbcKeyGeneratorFactory;
import com.versant.core.jdbc.metadata.JdbcTable;
import com.versant.core.jdbc.sql.HighLowJdbcKeyGenerator;
import com.versant.core.jdbc.sql.HighLowJdbcKeyGenerator$Args;

/**
 * @author fceballos
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class StringKeyGenerator extends HighLowJdbcKeyGenerator {

	
	public static class Argumentos extends HighLowJdbcKeyGenerator$Args{
		
		public Argumentos(){
			super();
			ForsetiProperties fp = ForsetiProperties.getInstancia();
			String tableName = fp.getProperty(JDOGenieKeys.TABLA_SECUENCIA);
			String keyColumnName = fp.getProperty(JDOGenieKeys.COLUMNA_LLAVE);
			String valueColumnName = fp.getProperty(JDOGenieKeys.COLUMNA_VALOR);
			int grabSize = Integer.parseInt(fp.getProperty(JDOGenieKeys.GRAB_SIZE_SECUENCIA));
			
			this.setTableName(tableName);
			this.setKeyColumnName(keyColumnName);
			this.setGrabSize(grabSize);
			this.setValueColumnName(valueColumnName);
		}
	}
	
	/**
	 * @param arg0
	 * @param arg1
	 */
	public StringKeyGenerator(JdbcTable arg0, HighLowJdbcKeyGenerator$Args arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Our factory.
	 */
	public static class Factory implements JdbcKeyGeneratorFactory {

		/**
		 * Create a javabean to hold args for a createJdbcKeyGenerator call or null
		 * if the key generator does not accept any arguments.
		 */
		public Object createArgsBean() {
			return new Argumentos();
		}

		/**
		 * Create a JdbcKeyGenerator for class using props as parameters. The
		 * instance returned may be new or may be a shared instance.
		 */
		public JdbcKeyGenerator createJdbcKeyGenerator(String className,
				JdbcTable classTable, Object args) {
			StringKeyGenerator kg = new StringKeyGenerator(
					classTable, (HighLowJdbcKeyGenerator$Args)args);
			return kg;
		}
	}
	

	/**
	 * Generate a new primary key value for a new instance of the supplied
	 * class prior to the row being inserted. The values generated will be used
	 * to populate a new OID and then set on a PreparedStatement for the
	 * insert. This is called if isPostInsertGenerator returns false.
	 *
	 * The newObjectCount parameter indicates the number of new objects that
	 * will be inserted (including this one) in the same transaction using
	 * this key generator. This may be used to optimize the behavior of the
	 * key generator or be ignored. The highlow key generator uses this value
	 * instead of its grabSize to avoid executing redundant updates and
	 * selects.<p>
	 *
	 * @param className The name of the class
	 * @param classTable The table for the class
	 * @param newObjectCount The number of new objects being created
	 * @param data The array to store the key values in.
	 * @param con Connection to the DataSource for the class.
	 *
	 * @exception SQLException on errors
	 */
	public synchronized void generatePrimaryKeyPre(String className,
				JdbcTable classTable, int newObjectCount, Object[] data,
				Connection con)
			throws SQLException {
		int pk;
		if (grabSize == 1) {
			pk = lookupNewNumber(con, grabSize);
		} else {
			if (grabLeft == 0) {
				int effectiveGrabSize = newObjectCount;
				if (effectiveGrabSize < grabSize) effectiveGrabSize = grabSize;
				lastUsed = lookupNewNumber(con, effectiveGrabSize);
				grabLeft = effectiveGrabSize - 1;
			} else {
				--grabLeft;
			}
			pk = lastUsed++;
		}
		data[0] = String.valueOf(pk);
	}
	
	

}
