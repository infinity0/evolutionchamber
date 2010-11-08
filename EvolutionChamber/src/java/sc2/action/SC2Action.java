package sc2.action;

import sc2.SC2Player;
import sc2.asset.SC2Asset;
import sc2.action.SC2ActionException;

/**
** Represents an ongoing action.
**
** An action can be controlled with {@link #launch()}, {@link #advance()}, and
** {@link #cancel()}. These methods are designed to be used directly by an
** external client (such as {@link sc2.SC2BuildOrderExecutor}), and help to
** integrate the action with the rest of the game state.
**
** An action also supports asynchronous events: {@link #evt_init(SC2Asset)} and
** {@link #evt_done()}. These are fired automatically as the game advances
** (e.g. by {@link SC2Player#advance()}), and should not be called directly.
*/
abstract public class SC2Action {

	/** reference to the player state */
	protected SC2Player play;

	/** time to completion. double because chrono boost can increase the decrement to 1.5 */
	protected double eta;

	public SC2Action(double eta) {
		if (eta < 0) { throw new IllegalArgumentException("can't have negative eta: " + eta); }
		this.eta = eta;
	}

	/** shortcut constructor for eventless actions */
	public SC2Action() {
		this(0);
	}

	public double getETA() {
		return eta;
	}

	/**
	** Launch the action for the given player. This method just delegates to
	** {@link #launch()}.
	*/
	final public void launch(SC2Player play) throws SC2ActionException {
		if (play == null) { throw new NullPointerException(); }
		if (this.play != null) { throw new IllegalStateException("action already bound to a player!"); }

		try {
			this.play = play;
			launch();

		} catch (SC2ActionException e) {
			this.play = null;
			throw e;
		}
	}

	/**
	** Launch the action. This should integrate the action into the game state
	** (e.g. {@link SC2Asset#bind(SC2Action)} to asset queues), so that events
	** will be fired correctly.
	**
	** Used by {@link #launch(SC2Player)}.
	*/
	abstract protected void launch() throws SC2ActionException;

	/**
	** Advance the action at a normal rate. This method just delegates to
	** {@link #advance(double)}.
	*/
	final public boolean advance() {
		return advance(1.0);
	}

	/**
	** Advance the action state to the next game tick (i.e. second).
	**
	** This default implementation reduces the action's eta by {@code rate}.
	** Subclasses might add extra checks, such as supply cap, pylon power, and
	** pause the action if appropriate.
	**
	** @param rate Timer decrement; should be 1.0 unless Chrono Boost is used.
	** @return Whether the action completed
	*/
	public boolean advance(double rate) {
		if (eta <= 0) { throw new IllegalStateException("action already completed: " + this); }
		eta -= rate;
		if (eta <= 0) { evt_done(); return true; }
		return false;
	}

	/**
	** Cancel the action. This should detach the action from the game state
	** (e.g. {@link SC2Asset#drop(SC2Action)}) from asset queues), and reverse
	** any {@link #evt_init(SC2Asset)} events that might have been fired.
	**
	** The default implementation throws {@link UnsupportedOperationException}.
	** Subclasses might restore previously-deducted resources, etc.
	**
	** There is no corresponding {@code evt_cancel} (as {@code evt_init} for
	** {@code launch}), since cancel orders are not supposed to fail.
	*/
	public void cancel() {
		throw new UnsupportedOperationException("cancel not currently supported on action " + this);
	}

	/**
	** Event: initialise. Fired when the action is about to be integrated into
	** the game state, e.g. when a structure is placed, or a unit is queued.
	** If an exception is thrown, this will abort integration.
	**
	** The default implementation does nothing. Subclasses might check and
	** deduct resources, etc.
	**
	** This method should not change the place of the action itself, e.g.
	** bind it to an asset queue. This is already done elsewhere.
	**
	** @param source The asset that this action was bound to, or {@code null}.
	*/
	public void evt_init(SC2Asset source) throws SC2ActionException { }

	/**
	** Event: completed. Fired when the action has just completed, e.g. when a
	** build timer has expired. This cannot be aborted, and should not throw
	** an exception during normal operation.
	**
	** The default implementation does nothing. Subclasses might add a new
	** asset to the game state, etc.
	**
	** This method should not change the place of the action itself, e.g.
	** drop it from an asset queue. This is already done elsewhere.
	*/
	public void evt_done() { }

}
