package lw.droid.persistence.sqlite;

import java.util.HashMap;

public interface SqliteTableMetadata {
	HashMap<String, SqliteColumn> getColumns();
	String getName();
	String getPrimaryKeyField();
}
