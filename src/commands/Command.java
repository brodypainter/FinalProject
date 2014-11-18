package commands;

import java.io.Serializable;

/**
 * This is an abstract class for a serializable Command Object that will be used to send commands to some type of receiver
 */

public abstract class Command<T> implements Serializable {
	private static final long serialVersionUID = -4838155228547508978L;

	/**
	 * Executes the command on the given argument
	 * 
	 * @param executeOn	Object to execute the command on
	 */
	public abstract void execute(T executeOn);
}
