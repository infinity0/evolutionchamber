package sc2.require;

/**
** Thrown when a requirement is not met.
*/
public class SC2RequireException extends Exception {

	/** whether the requirement can be met later */
	final public boolean later;

	public SC2RequireException(String message, boolean later) {
		super(message);
		this.later = later;
	}

	public boolean canSatisfyByWaiting() {
		return later;
	}

}
