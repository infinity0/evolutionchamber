package sc2.action;

import sc2.SC2State;
import sc2.action.SC2ActionException;

/**
** Represents an ongoing action.
*/
abstract public class SC2Action {

	/** reference to the game state */
	protected SC2State game;

	/** double because chrono boost can increase the decrement to 1.5 */
	public double eta;

	public SC2Action(int cost_t) {
		if (game == null) { throw new NullPointerException(); }
		this.eta = cost_t;
	}

	protected void setGame(SC2State game) {
		if (game != null) { throw new IllegalStateException("action already bound to a game!"); }
		this.game = game;
	}

	/**
	** Bind this action to the given game and launch it. This is called by
	** {@link sc2.SC2BuildOrderExecutor}.
	*/
	final public void launch(SC2State game) throws SC2ActionException {
		if (game == null) { throw new NullPointerException(); }
		setGame(game);
		launch();
	}

	/**
	** Launch the action, integrating it into the appropriate places (e.g.
	** asset queues). Called by {@link #launch(SC2State)}
	*/
	abstract protected void launch() throws SC2ActionException;

	/**
	** Initialise the action. This is called upon integration into the
	** game state, e.g. when a structure is placed, or a unit is queued.
	**
	** Appropriate behavior might be to deduct resources. Note that checking
	** supply should be done in {@code advance()}, to match the actual game
	** mechanics.
	*/
	abstract public void init() throws SC2ActionException;

	/**
	** Advance the action state to the next game tick (i.e. second).
	**
	** This default implementation reduces the action's eta by {@code rate}.
	** Subclasses might add extra restrictions, such as supply cap, pylon
	** power, etc.
	**
	** @param rate Timer decrement; should be 1.0 unless Chrono Boost is used.
	** @return whether the action completed
	*/
	public boolean advance(double rate) {
		if (eta <= 0) { throw new IllegalStateException("action already completed: " + this); }
		eta -= rate;
		if (eta <= 0) { complete(); return true; }
		return false;
	}

	/**
	** Complete the action. Add a new asset to the game state, etc.
	*/
	abstract public void complete();

	/**
	** Cancel the action. Restore resources etc.
	*/
	abstract public void cancel();

	/**
	** Advance the action at a normal rate. This method delegates to {@link
	** #advance(double)}, so subclasses need not (and cannot) override it.
	*/
	final public boolean advance() {
		return advance(1.0);
	}

}
