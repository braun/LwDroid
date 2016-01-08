package lw.droid.http;

public class ComException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6333547050355999641L;

	public ComException(String context,Exception ex)
	{
		super(context + ": "+ ex.getLocalizedMessage(), ex);
	}

}
