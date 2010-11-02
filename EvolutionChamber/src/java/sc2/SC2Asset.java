package sc2;

import sc2.action.SC2Action;
import sc2.action.SC2ActionException;

import java.util.LinkedList;

/**
** Represents a completed asset. (Not researches.)
*/
public class SC2Asset {

	/** max queue size. TODO for BO-optimisation we should probably set this to 1 */
	final public static int qcap = 5;

	final public SC2AssetType type;

	final protected LinkedList<SC2Action> queue = new LinkedList<SC2Action>();

	protected double energy;

	public SC2Asset(SC2AssetType type, int energy) {
		if (type == null) { throw new NullPointerException(); }
		this.type = type;
		this.energy = energy;
	}

	public SC2Asset(SC2AssetType type) {
		this(type, type.stat_ep.init);
	}

	protected void advance(double dec) {
		// advance queue
		SC2Action current = queue.getFirst();
		if (current == null) {
			return;
		} else if (current.advance(dec)) {
			queue.removeFirst();
		}
		// regen energy
		energy = type.stat_ep.regen(energy);
	}

	public void advance() {
		advance(1.0);
	}

	/**
	** @return The action that was pushed or {@code null} if unchanged
	** @throws NullPointerException if the action was {@code null}
	*/
	public SC2Action pushQueue(SC2Action action) throws SC2ActionException {
		if (action == null) { throw new NullPointerException(); }
		if (queue.size() >= qcap) { return null; }
		action.init();
		queue.addLast(action);
		return action;
	}

	/**
	** @return The action that was popped or {@code null} if unchanged
	*/
	public SC2Action popQueue(int i) {
		SC2Action action = queue.remove(i);
		if (action != null) { action.cancel(); }
		return action;
	}

	/**
	** @return The action that was popped or {@code null} if unchanged
	*/
	public SC2Action popQueue(SC2Action action) {
		int i = queue.indexOf(action);
		return (i < 0)? null: popQueue(i);
	}

}
