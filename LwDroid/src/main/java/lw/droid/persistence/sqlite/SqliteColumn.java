package lw.droid.persistence.sqlite;

public class SqliteColumn {

	public SqliteColumn(String columnName,String sqlType,String sqlDefaultValue,boolean notNull)
	{
		mNotNull = notNull;
		mName = columnName;
		mSqlType = sqlType;
		mSqlDefaultValue = sqlDefaultValue;
	}
	
	boolean mNotNull;
	/**
	 * @return the mNotNull
	 */
	public boolean isNotNull() {
		return mNotNull;
	}
	/**
	 * @return the mName
	 */
	public String getName() {
		return mName;
	}
	/**
	 * @return the mSqlType
	 */
	public String getSqlType() {
		return mSqlType;
	}
	/**
	 * @return the mSqlDefaultValue
	 */
	public String getSqlDefaultValue() {
		return mSqlDefaultValue;
	}
	
	String mName;
	String mSqlType;
	String mSqlDefaultValue;
	
	
}
