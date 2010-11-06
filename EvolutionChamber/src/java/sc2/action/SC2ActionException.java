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
	** A *hint* on whether the action may execute successfully later, with no
	** intermediate actions.
	**
	** It's always safe to leave this as false, since it's only regarded as a
	** hint. To predict it perfectly, would be infeasible, since you must take
	** into account the consequences of all implemented actions.
	**
	** (To work around this, the genetic algorithm automatically inserts wait
	** actions during the mutation. However, if this field is set to true, it
	** will help speed up optimisation.)
	**
	** TODO actually implement an explicit wait action.
	*/
	public boolean pleaseTryLater() {
		return later;
	}

}
