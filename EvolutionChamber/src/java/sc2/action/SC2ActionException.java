package sc2.action;

import sc2.require.SC2RequireException;

/**
** Thrown when an action cannot be executed.
*/
public class SC2ActionException extends Exception {

	final public SC2Action action;
	/** whether the action can be done later by waiting */
	final public boolean later;

	public SC2ActionException(SC2Action action, String message, boolean later) {
		super(message);
		this.action = action;
		this.later = later;
	}

	public SC2ActionException(SC2Action action, String message) {
		this(action, message, false);
	}

	public SC2ActionException(SC2Action action, String message, SC2RequireException e) {
		this(action, message, (e==null)? false: e.later);
		initCause(e);
	}

	/**
	** Whether the action *may* work later, just by waiting.
	**
	** This essentially causes {@link SC2BuildOrderExecutor} to block on the
	** action until it succeeds, or fails definitively (ie. the {@link
	** #pleaseTryLater()} call returns {@code false}).
	**
	** This should be set te {@code true} as much as possible without causing
	** the game to block indefinitely. Delaying the build is preferable to
	** dropping a to-be-valid action, because it's unlilkely that the genetic
	** algorithm will generate such an action at the exact optimal time.
	*/
	public boolean pleaseTryLater() {
		return later;
	}

}
