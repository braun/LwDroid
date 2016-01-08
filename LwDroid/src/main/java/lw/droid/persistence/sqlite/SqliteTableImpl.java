package lw.droid.persistence.sqlite;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.Map.Entry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;
import lw.droid.persistence.Persistable;
import lw.droid.persistence.PersistenceException;
import lw.droid.persistence.Table;



public class SqliteTableImpl<T extends Persistable,R extends SqlitePersistable> implements Table<T >,SqliteTableMetadata {

	Context mContext;
	SQLiteDatabase mDatabase;
    String mPrimaryKeyField;
    
    String mTableName;
    
    String[] mColumnsToLoad;
    PersistableFactory<T> mFactory;
    
	public SqliteTableImpl(Context ctx,String tableName,String primaryKeyField,PersistableFactory f)
	{
		mTableName = tableName;
	
		mContext = ctx;
		mPrimaryKeyField = primaryKeyField;
		UniversalOpenHelper.getInstance().addTable(this);
		mFactory = f;
	}
	
	public interface PersistableFactory<T>
	{
		T createInstance();
		
	}
	private SQLiteDatabase getDatabase()
	{
		if(mDatabase == null)
			mDatabase = UniversalOpenHelper.getInstance().getWritableDatabase();
		return mDatabase;
		
	}
	
	public String getName()
	{
		return mTableName;
	}
	@Override
	public String getPrimaryKeyField() {
		
		return mPrimaryKeyField;
	}

	public T createNewInstance()
	{
		R rv = (R)createInstance();
		rv.onCreate();
		return (T)rv;
	}
	public T createInstance()
	{
		return  mFactory.createInstance();
	}
	@SuppressWarnings("unchecked")
	public T load(long pk) throws PersistenceException
	{
		R rv = (R)createInstance();
		rv.setPk(pk);
		rv.load();
		return (T)rv;
	}
	public void delete( long pk) throws PersistenceException {
		//if(!hasPrimaryKey(persistable))
		//	return;
		
	 getDatabase().delete(mTableName, mPrimaryKeyField+"="+getPrimaryKey(pk),null);
	}
	
	@Override
	public void deleteAll() throws PersistenceException {
		getDatabase().delete(mTableName, null,null);
		
	}
	private String getPrimaryKey(Long pk) {
		
		return pk.toString();
	}

	private void throwBadAffectedRowsCount(Long pk,String string,int c) throws PersistenceException {
		throw new PersistenceException(getRowRelatedMessage(pk,"load","found "+c + " rows instead of 1"));
		
	}

	private String getRowRelatedMessage(Long pk, String method, String msg) {
		
		return "Table " + mTableName + ":"+ pk.toString() + ", "+method+" - " + msg;
	}
	
	public void load(SqlitePersistable persistable) throws PersistenceException {	

		long pk = persistable.getPk();
		Cursor c = getDatabase().query(mTableName,mColumnsToLoad, mPrimaryKeyField+"="+pk, null, null,
				null, null);

		try {

			if(c.getCount() != 1)
				throwBadAffectedRowsCount(pk,"load",c.getCount());

			if(!c.moveToFirst())
				throw new PersistenceException(getRowRelatedMessage(pk, "load", "moveToFirst failed!"));

			persistable.loadFromCursor(c);
		} finally {
			
			c.close();
		}

	}

	public List<T> findAll() throws PersistenceException {							
		
				return find(null,null,null,null);
		}
	
	public List<T> find(
		T persistable) throws PersistenceException {
			
		
	
			return find(persistable,null,null);
	}
	public List<T> find(
			T persistable,String orderBy,String limit) throws PersistenceException {
				
			ArrayList<T> rv = new ArrayList<T>();
			ContentValues qvalues = ((R)persistable).getDirtyFields();
			StringBuilder sb = new StringBuilder();
			String[]  pars = new String[qvalues.size()];
			int i = 0;
			for(Entry<String, Object> e : qvalues.valueSet())
			{
				if(sb.length() > 0)
					sb.append(" and ");
				sb.append(e.getKey());
				sb.append(" = ?");
				pars[i++] = e.getValue().toString();
				
					
			}
				return find(sb.toString(),pars,orderBy,limit);
		}
		
	public List<T> find(
			String where,String[] pars,String orderBy,String limit) throws PersistenceException {
		ArrayList<T> rv = new ArrayList<T>();
				Cursor c = getDatabase().query(mTableName,mColumnsToLoad, where, pars, null,null,
						orderBy, limit);
				
				try
				{
					if(!c.moveToFirst())
						return rv;
					do
					{						
						R it = (R)createInstance();
						it.loadFromCursor(c);
						rv.add((T)it);
						
						c.moveToNext();
					} while (!c.isAfterLast());

					return rv;
				}
				finally
				{
					c.close();
				}
	}

	public void save(SqlitePersistable persistable) throws PersistenceException {
		ContentValues values= persistable.getDirtyFields();
	
		if(persistable.isNew())
		{
			long pk = insert(values);
			persistable.setPk(pk);	
		}
		else
		{
			long pk= persistable.getPk();		
			update(values,pk);
		}
		
	}
	
	
	private long insert(ContentValues values) throws PersistenceException {
		 
		try
		{
			long rv = getDatabase().insertOrThrow(mTableName, null,values );
			return rv;
		}
		catch(SQLException sqle)
		{
			throw new PersistenceException("SQL Insert failed! table='" + mTableName + "', values='" + values.toString()+"'"
					,sqle);
		}
		
		
	}
	private void update(ContentValues values, Long pk) throws PersistenceException {
		int afrows =	getDatabase().update(mTableName, values,mPrimaryKeyField+"="+pk,null);
		if(afrows != 1)
			throwBadAffectedRowsCount(pk,"update",afrows);
		
	}

	

	
	HashMap<String, SqliteColumn> mColMap = new HashMap<String,SqliteColumn>();
	
	public HashMap<String, SqliteColumn> getColumns()
	{
		return mColMap;
	}
	
	public void addColumn(String name,String column,String sqlType,boolean notNull,String defVal)
	{
		mColMap.put(name, new SqliteColumn(column,sqlType,defVal,notNull));
	}

	public void configComplete()
	{
		mColumnsToLoad = new String[mColMap.size()];
		int i = 0;
		for(Entry<String,SqliteColumn> e : mColMap.entrySet())
		{
			mColumnsToLoad[i++] = e.getValue().getName();
		}
	}
	public static  String ConvertFromString(String val) {
		
		return val;
	}
	public static  String ConvertToString(Cursor c, int idx) {
		if(c.isNull(idx))
			return null;
		return c.getString(idx);
	}

	public static  String ConvertToString(String val) {
		return val;
	}

	public static  long ConvertFromLong(long val) {
		
		return val;
	}
	public static  Long ConvertToLong(Cursor c, int idx) {
		if(c.isNull(idx))
			return null;
		return c.getLong(idx);
	}
	public static  long ConvertToLong(String val) {
		return Long.parseLong(val);
	}
	public static  double ConvertFromDouble(double val) {
		
		return val;
	}
	
	public static  Double ConvertToDouble(Cursor c, int idx) {
		if(c.isNull(idx))
			return null;
		return c.getDouble(idx);
	}
	public static  double ConvertToDouble(String val) {
		return Double.parseDouble(val);
	}
	
	public static  String ConvertFromUUID(UUID val) {
		
		return val.toString();
	}
	
	public static  UUID ConvertToUUID(Cursor c, int idx) {
		if(c.isNull(idx))
			return null;
		String uuidstr = c.getString(idx);
		return UUID.fromString(uuidstr);
	}
	
	public static  int ConvertFromInt(int val) {
		
		return val;
	}
	
	public static  Integer ConvertToInt(Cursor c, int idx) {
		if(c.isNull(idx))
			return null;
		return c.getInt(idx);
	}
	public static  int ConvertToInt(String val) {
		return Integer.parseInt(val);
	}
	public static  String ConvertFromTime(Time val) {
		
		 return val.format2445();
	}
	public static  Time ConvertToTime(Cursor c, int idx) {
		if(c.isNull(idx))
			return null;
		
		String tms = c.getString(idx);
		return ConvertToTime(tms);
	}
	public static  Time ConvertToTime(String val) {
		
		Time t = new Time();
		t.parse(val);
		return t;
	}
	
	public static  int ConvertFromBoolean(Boolean val) {
		// 
		return val ? 1 : 0;
	}
	public static Boolean ConvertToBoolean(Cursor c, int idx) {
		if(c.isNull(idx))
			return null;
		return c.getInt(idx) != 0;
	}
	public static  boolean ConvertToBoolean(String val) {
		return ConvertToInt(val) != 0;
	}
	public static  String ConvertFromDecimal(BigDecimal dec) {
		
		return dec.toString();
	}
	

	public static  BigDecimal ConvertToDecimal(Cursor c, int idx) {
		if(c.isNull(idx))
			return null;
		
		String val = c.getString(idx);
		
		return ConvertToDecimal(val);
	}
	public static  BigDecimal ConvertToDecimal(String val) {		
		
		BigDecimal rv = new BigDecimal(val);
		return rv;
	}


	public static Time getNow()
	{
		Time rv = new Time();
		rv.setToNow();
		
		return rv;
	}
	
	public static UUID getNewUUID()
	{
		UUID rv = UUID.randomUUID();
		return rv;
	
	}

	
}

