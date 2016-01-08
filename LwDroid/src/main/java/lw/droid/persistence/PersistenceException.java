package lw.droid.persistence;

/**
 * Exception thrown from droid persistence engine
 * @author Standa
 *
 */
public class PersistenceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4129770329850805994L;

	public PersistenceException()
	{
		super();
	}
	public PersistenceException(String msg)
	{
		super(msg);
	}
	public PersistenceException(String msg,Throwable th)
	{
		super(msg,th);
	}
}
