package sc2.action;

import sc2.SC2State;
import sc2.action.SC2ActionException;

/**
** Represents an ongoing action.
*/
abstract public class SC2Action {

	/** reference to the game state */
	final protected SC2State game;

	/** double because chrono boost can increase the decrement to 1.5 */
	public double eta;

	public SC2Action(SC2State game, int cost_t) {
		if (game == null) { throw new NullPointerException(); }
		this.game = game;
		this.eta = cost_t;
	}

	/**
	** Initiate the action. Check all requirements, deduct resources, etc.
	*/
	abstract public void init() throws SC2ActionException;

	/**
	** Complete the action. Add a new asset to the game, etc.
	*/
	abstract public void complete();

	/**
	** Cancel the action. Restore resources etc.
	*/
	abstract public void cancel();

	/**
	** Advance the action's timer.
	**
	** @param dec Number of seconds to shave off
	** @return whether the action completed
	*/
	public boolean advance(double dec) {
		if (eta <= 0) { throw new IllegalStateException("action already completed: " + this); }
		eta -= dec;
		if (eta <= 0) { complete(); return true; }
		return false;
	}

	/**
	** Advance to the next second.
	**
	** @return whether the action completed
	*/
	public boolean advance() {
		return advance(1.0);
	}

}
