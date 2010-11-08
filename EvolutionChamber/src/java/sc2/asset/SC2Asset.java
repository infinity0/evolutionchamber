package sc2.asset;

import sc2.action.SC2Build;
import sc2.action.SC2Morph;
import sc2.action.SC2Action;
import sc2.action.SC2ActionException;
import sc2.require.SC2RequireException;
import sc2.require.SC2QueueException;
import sc2.SC2Player;

import java.util.LinkedList;

/**
** Represents a unit or structure asset. (Not researches.)
**
** Each asset can have a currently active {@link SC2Action}. If this is a
** {@link SC2Build}, additional build actions may be queued.
*/
public class SC2Asset {

	final public SC2AssetType type;

	/** reference to the player state. subclasses might need this, eg. to spawn larva. */
	final protected SC2Player play;

	/** current name. used by {@link SC2Player} for more descriptive messages. */
	protected String name;
	/** current energy. */
	protected double energy;

	/** the currently active action */
	protected SC2Action active;
	/** build queue. */
	final protected LinkedList<SC2Build> queue = new LinkedList<SC2Build>();

	public SC2Asset(SC2Player play, SC2AssetType type, int energy) {
		if (type == null) { throw new NullPointerException(); }
		this.play = play;
		this.type = type;
		this.energy = energy;
	}

	public SC2Asset(SC2Player play, SC2AssetType type) {
		this(play, type, type.stat_ep.init);
	}

	/**
	** Give a name for the asset. This only affects output notifications, not logic.
	*/
	public void setName(String name) {
		// could do sanity checks
		this.name = name;
	}

	/**
	** Whether the asset is currently doing something.
	*/
	public boolean isActive() {
		return active != null;
	}

	/**
	** Return the number of queued actions (including active).
	*/
	public int size() {
		return queue.size() + ((active instanceof SC2Build)? 1: 0);
	}

	/**
	** Pop the next {@link SC2Build} from the queue (excluding active), or
	** {@code null} if the queue is empty.
	*/
	protected SC2Action popAction() {
		assert active instanceof SC2Build || queue.isEmpty();
		return queue.poll();
	}

	/**
	** @return Whether the action is now active (otherwise queued).
	*/
	public boolean bind(SC2Action action) throws SC2ActionException, SC2RequireException {
		if (active != null) {
			if (active instanceof SC2Build && action instanceof SC2Build) {
				if (queue.size() >= MAX_BUILD_QUEUE) {
					// queue will be opened up if active build has begun (i.e. not supply blocked)
					throw new SC2QueueException(this, ((SC2Build)active).begun());
				}
				action.evt_init(this);
				queue.addLast((SC2Build)action);
				return false;
			}
			// TODO optimise the "false".
			// build can wait for morph to finish, in general
			// build can wait for build to finish, always
			// morph can wait for morph to finish, never
			// morph can wait for build to finish, always
			throw new SC2ActionException(action, "already executing action: " + active, false);
		}
		action.evt_init(this);
		active = action;
		return true;
	}

	/**
	** Cancel the given action. This should only be called by the action
	** itself, from {@link SC2Action#cancel()}. TODO rename
	**
	** @return Whether the action was active (otherwise queued).
	*/
	public boolean drop(SC2Action action) {
		if (action == null) { throw new NullPointerException(); }
		if (active != null) {
			if (action == active) {
				action = popAction(); // returns null if queue empty
				return true;
			} else if (action instanceof SC2Build && queue.remove(action)) {
				assert active instanceof SC2Build;
				return false;
			}
		}
		throw new IllegalStateException("action not queued by asset: " + this);
	}

	/**
	** Advance to the next game tick. The default implementation delegates to
	** {@link #advance(double)} at a normal rate.
	*/
	public void advance() {
		advance(1.0);
	}

	/**
	** Called when the asset is destroyed and removed from the player state.
	*/
	public void destroy() {
		// TODO hook into the rest of the code
		//play.removeAsset(this);
		play.res_f -= type.cost_f();
		play.max_f -= type.prov_f;
	}

	/**
	** Advance the asset's state by the given rate. The default implementation
	** recovers energy at a *standard* rate, and advances the current action by
	** the input rate.
	*/
	protected void advance(double rate) {
		advanceAction(rate);
		regenEnergy();
	}

	protected void advanceAction(double rate) {
		if (active != null && active.advance(rate)) {
			active = popAction();
		}
	}

	protected void regenEnergy() {
		energy = type.stat_ep.regen(energy);
	}

	@Override public String toString() {
		return type.name + " " + name;
	}

	/** max queue size. TODO for BO-optimisation we should set this to 0 */
	final public static int MAX_BUILD_QUEUE = 4;

}
