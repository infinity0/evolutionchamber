package sc2.action;

import sc2.SC2Player;
import sc2.action.SC2ActionException;

/**
** Represents an ongoing action.
**
** An action has a mandatory {@link #launch()} phase which is executed once
** synchronously, and optional asnychronous phases ({@link #init()}, {@link
** #advance()}, {@link #complete()}, {@link #cancel()}), which are triggered
** automatically by certain events in the game.
*/
abstract public class SC2Action {

	/** reference to the player state */
	protected SC2Player play;

	/** double because chrono boost can increase the decrement to 1.5 */
	protected double eta;

	public SC2Action(double eta) {
		if (eta < 0) { throw new IllegalArgumentException("can't have negative eta: " + eta); }
		this.eta = eta;
	}

	/** shortcut constructor for synchronous-only actions */
	public SC2Action() {
		this(0);
	}

	/**
	** Bind this action to the given player, and launch it. This is called by
	** {@link sc2.SC2BuildOrderExecutor}.
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
	** Launch the action. If this action implements the optional asynchronous
	** phases, this method should integrate it into the appropriate places in
	** the game state (e.g. asset queues) for them to be triggered correctly.
	**
	** Called by {@link #launch(SC2Player)}.
	*/
	abstract protected void launch() throws SC2ActionException;

	/**
	** Initialise the action. This is called just before integration into the
	** game state, e.g. when a structure is placed, or a unit is queued, and
	** may prevent this from happening by throwing an exception.
	**
	** The default implementation does nothing. Subclasses might deduct
	** resources, etc.
	**
	** This method should not change the place of the action itself, e.g.
	** remove it from an asset queue. This is already done elsewhere.
	*/
	public void init() throws SC2ActionException { }

	/**
	** Advance the action state to the next game tick (i.e. second).
	**
	** This default implementation reduces the action's eta by {@code rate}.
	** Subclasses might add extra checks, such as supply cap, pylon power, and
	** pause the action if appropriate.
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
	**
	** The default implementation does nothing. Subclasses might add a new
	** asset to the game, etc.
	**
	** This method should not change the place of the action itself, e.g.
	** remove it from an asset queue. This is already done elsewhere.
	*/
	public void complete() { }

	/**
	** Cancel the action. Restore resources etc.
	**
	** The default implementation does nothing. Subclasses might compensate
	** previously-deducted resources, etc.
	**
	** This method should not change the place of the action itself, e.g.
	** remove it from an asset queue. This is already done elsewhere.
	*/
	public void cancel() { }

	/**
	** Advance the action at a normal rate. This method delegates to {@link
	** #advance(double)}, so subclasses need not (and cannot) override it.
	*/
	final public boolean advance() {
		return advance(1.0);
	}

}
