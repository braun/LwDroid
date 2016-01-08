package lw.droid.persistence;

import java.util.Collection;
import java.util.List;

/**
 * Persisting service for {@link Persistable} implementators
 * @author Standa
 *
 */
public interface Table<T extends Persistable>
{
	/**
	 * Saves Persistable
	 */
//	void Save(Persistable persistable);
	
	/**
	 * Deletes Persistable from storage
	 */
//	void Delete(Persistable persistable);
	
	
	T  createInstance();
	T  createNewInstance();
	T  load(long pk) throws PersistenceException;

	List<T> find(T example)  throws PersistenceException;
	void delete(long pk) throws PersistenceException;
	
	void deleteAll() throws PersistenceException;
}
