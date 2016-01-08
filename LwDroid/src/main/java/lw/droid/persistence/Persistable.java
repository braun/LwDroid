package lw.droid.persistence;



/**
 * Interface of Persistable objects
 * @author Standa
 *
 */
public interface Persistable {

	Table getTable();
	void save() throws PersistenceException;
	void load() throws PersistenceException;
	void find() throws PersistenceException;
	void setFieldFromString(String name,String value);
}
