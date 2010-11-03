package sc2.action;

import sc2.require.SC2RequireException;

/**
** Thrown when an action cannot be executed.
*/
public class SC2ActionException extends Exception {

	final public SC2Action action;
	/** whether the action can be done later by waiting */
	final public boolean later;

	public SC2ActionException(SC2Action action, String message, SC2RequireException e) {
		super(message);
		this.action = action;
		if (e != null) {
			initCause(e);
			later = e.later;
		} else {
			later = false;
		}
	}

	public SC2ActionException(SC2Action action, String message) {
		this(action, message, null);
	}

	public boolean canSatisfyByWaiting() {
		return later;
	}

}
