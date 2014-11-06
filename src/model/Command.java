package model;

import java.io.Serializable;

/**
 *	This abstract class defines a serializable command that can be sent
 * 	and executed on either a client or server.
 *  
 *  @author Gabriel Kishi
 */

public abstract class Command<T> implements Serializable {
	private static final long serialVersionUID = -4838155228547508978L;

	/**
	 * Executes the command on the given argument
	 * 
	 * @param executeOn	Object to execute command on
	 */
	public abstract void execute(T executeOn);
}
