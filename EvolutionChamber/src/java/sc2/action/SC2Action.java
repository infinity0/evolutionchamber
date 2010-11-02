package sc2.action;

import sc2.SC2State;

/**
** Represents an ongoing action.
*/
abstract public class SC2Action {

	/** reference to the game state */
	final protected SC2State game;

	/** double because chrono boost can reduce it */
	public double eta;

	public SC2Action(SC2State game, int cost_t) {
		this.game = game;
		this.eta = cost_t;
	}

	public void advance() {
		eta -= 1;
		if (eta <= 0) { run(); }
	}

	abstract public void run();

}
