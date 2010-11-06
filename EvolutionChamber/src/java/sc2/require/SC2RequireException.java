package sc2.require;

/**
** Thrown when a requirement is not met.
*/
public class SC2RequireException extends Exception {

	/**
	** @see #maybeSatisfiableByWaiting()
	*/
	final public boolean later;

	public SC2RequireException(String message, boolean later) {
		super(message);
		this.later = later;
	}

	/**
	** A *hint* on whether the requirement can be met later, just by waiting.
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
	public boolean maybeSatisfiableByWaiting() {
		return later;
	}

}
