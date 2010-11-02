package sc2.action;

/**
** Thrown when a required asset is not found.
*/
public class SC2ActionException extends Exception {

	final public SC2Action action;

	public SC2ActionException(SC2Action action, String message) {
		super(message);
		this.action = action;
	}

}
