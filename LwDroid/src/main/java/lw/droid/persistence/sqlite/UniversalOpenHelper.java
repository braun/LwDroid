package lw.droid.persistence.sqlite;

import java.util.ArrayList;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UniversalOpenHelper extends SQLiteOpenHelper {

	ArrayList<SqliteTableMetadata> mTables;
	
	public UniversalOpenHelper(Context context, String name,int version) {
		super(context, name, null, version);
		mTables = new ArrayList<SqliteTableMetadata>();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		for(SqliteTableMetadata table: mTables)
			CreateTable(table,db);
	}

	private void CreateTable(SqliteTableMetadata table,SQLiteDatabase db) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		boolean firstcol = true;
		sb.append("CREATE TABLE ");
		sb.append(table.getName());
		sb.append('(');
		for(SqliteColumn col: table.getColumns().values())
		{
			if(!firstcol)
				sb.append(',');
			firstcol = false;
			sb.append(col.getName());
			sb.append(' ');
			sb.append(col.getSqlType());
			if(col.getName().equalsIgnoreCase(table.getPrimaryKeyField()))
				sb.append(" PRIMARY KEY ");
			else
				sb.append(col.isNotNull() ? " NOT NULL" : "");
			if(col.getSqlDefaultValue() != null)
			{
				sb.append(" DEFAULT ");			
				sb.append(col.getSqlDefaultValue());
			}
						
		}
		sb.append(')'); 
		
		db.execSQL(sb.toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void addTable(SqliteTableMetadata table)
	{
		mTables.add(table);
	}
	
	
	
	public static void init(Context ctx,String dbName,int version)
	{
		mInstance = new UniversalOpenHelper(ctx, dbName,version);
	}
	
	static UniversalOpenHelper mInstance;
	public static UniversalOpenHelper getInstance()
	{
		
		return mInstance;
	}

}
