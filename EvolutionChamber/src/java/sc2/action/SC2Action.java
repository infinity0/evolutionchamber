package sc2.action;

/**
** Represents an ongoing action.
*/
abstract public class SC2Action {

	/** double because chrono boost can reduce it */
	public double eta;

	public SC2Action(int cost_t) {
		eta = cost_t;
	}

	public void advance() {
		eta -= 1;
		if (eta <= 0) { run(); }
	}

	abstract public void run();

}
