package sc2.asset;

import sc2.action.SC2Build;
import sc2.action.SC2Morph;
import sc2.action.SC2Action;
import sc2.action.SC2ActionException;
import sc2.require.SC2RequireException;
import sc2.SC2Player;

import java.util.LinkedList;

/**
** Represents a unit or structure asset. (Not researches.)
**
** Each asset has a slot for {@link SC2Build}, and a slot for {@link SC2Morph}.
** Both of these actions cannot occur at the same time. Additional build
** actions may be queued up to {@link #BUILD_QUEUE_CAP}, but not morphs.
**
** The build-queue manipulation methods provide a view which treats the current
** action as index 0, the first queued action as index 1, etc.
*/
public class SC2Asset {

	final public SC2AssetType type;

	/** reference to the player state. subclasses might need this, eg. to spawn larva. */
	final protected SC2Player play;

	/** current name. used by {@link SC2Player} for more descriptive messages. */
	protected String name;
	/** current energy. */
	protected double energy;

	/** the currently-active build action */
	protected SC2Build active_b;
	/** the currently-active morph action */
	protected SC2Morph active_m;
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

	@Override public String toString() {
		return type.name + " " + name;
	}

	/**
	** Give a name for the asset. This only affects output notifications, not logic.
	*/
	public void setName(String name) {
		// could do sanity checks
		this.name = name;
	}

	/**
	** @return The current action, or {@code null}.
	*/
	public SC2Action getActive() {
		assert active_b == null || active_m == null;
		return active_b != null? active_b: active_m != null? active_m: null;
	}

	/**
	** Whether the asset is currently doing something.
	*/
	public boolean isActive() {
		assert active_b == null || active_m == null;
		return active_b != null || active_m != null;
	}

	/**
	** Push a build action onto the queue.
	**
	** @return Index of the pushed action, 0 being the current slot.
	*/
	public int pushBuild(SC2Build build) throws SC2ActionException, SC2RequireException {
		if (active_m != null) {
			// build might work after the morph, since they sometimes do the same thing (eg. lair->hive)
			throw new SC2ActionException(build, "can't build whilst morphing", true);
		} else if (active_b != null) {
			if (queue.size() >= BUILD_QUEUE_CAP) {
				// build will always work after the queue clears
				throw new SC2ActionException(build, "build queue full", true);
			}
			build.init();
			queue.addLast(build);
			return queue.size()-1;
		} else {
			build.init();
			active_b = build;
			return 0;
		}
	}

	/**
	** Push a morph action onto the queue.
	**
	** @return Index of the pushed action, 0 being the current slot.
	*/
	public int pushMorph(SC2Morph morph) throws SC2ActionException, SC2RequireException {
		if (active_b != null) {
			// morph will always work since build does not change the asset
			throw new SC2ActionException(morph, "can't morph whilst building", true);
		} else if (active_m != null) {
			// morph will never work since morph changes the asset
			throw new SC2ActionException(morph, "can't morph whilst morphing", false);
		} else {
			morph.init();
			active_m = morph;
			return 0;
		}
	}

	/**
	** Pop an action from the queue. {@link SC2Build#cancel()} is called.
	**
	** @param i Index of the queue to pop, 0 being the current slot.
	** @return The action that was popped or {@code null} if not found.
	*/
	public SC2Build popBuild(int i) {
		if (i == 0) {
			SC2Build build = active_b;
			if (build != null) {
				build.cancel();
				active_b = queue.isEmpty()? null: queue.removeFirst();
			}
			return build;
		} else {
			i -= 1;
			if (i < 0 || i >= queue.size()) { return null; }
			SC2Build build = queue.remove(i);
			build.cancel();
			return build;
		}
	}

	/**
	** Remove an action from the queue, {@link SC2Build#cancel()} is called.
	**
	** @param build Action to remove.
	** @return The index of the popped action, index 0 being the current slot,
	**         or {@code -1} if not found.
	*/
	public int removeBuild(SC2Build build) {
		if (build == null) {
			return -1;
		} else if (build == active_b) {
			popBuild(0);
			return 0;
		} else {
			java.util.Iterator<SC2Build> it = queue.iterator();
			int i = 1;
			while (it.hasNext()) {
				if (build.equals(it.next())) {
					it.remove();
					return i;
				}
			}
			return -1;
		}
	}

	/**
	** Advance to the next game tick. The default implementation delegates to
	** {@link #advance(double)} at a normal rate.
	*/
	public void advance() {
		advance(1.0);
	}

	/**
	** Advance the asset's state by the given rate. The default implementation
	** recovers energy at a *standard* rate, and advances the current action by
	** the input rate.
	*/
	protected void advance(double rate) {
		advanceActions(rate);
		regenEnergy();
	}

	protected void advanceActions(double rate) {
		if (active_m != null) {
			assert active_b == null;
			if (active_m.advance(rate)) {
				active_m = null;
			}
		} else if (active_b != null) {
			assert active_m == null;
			if (active_b.advance(rate)) {
				popBuild(0);
			}
		}
	}

	protected void regenEnergy() {
		energy = type.stat_ep.regen(energy);
	}

	/** max queue size. TODO for BO-optimisation we should set this to 0 */
	final public static int BUILD_QUEUE_CAP = 4;

}
