package lw.droid.persistence.sqlite;


import lw.droid.persistence.Persistable;
import android.content.ContentValues;
import android.database.Cursor;

public interface SqlitePersistable extends Persistable {

	
	void loadFromCursor(Cursor c);
	void onCreate();
	
	ContentValues getDirtyFields();
	
	boolean isNew();
	long getPk();
	void setPk(long pk);

}
