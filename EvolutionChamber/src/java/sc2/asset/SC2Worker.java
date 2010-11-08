package sc2.asset;

import sc2.action.SC2Action;
import sc2.action.SC2ActionException;
import sc2.require.SC2RequireException;
import sc2.SC2Player;

/**
** Represents a worker. When not carrying out other actions, a worker will do
** work. A call to {@link #bind(SC2Action)} will temporarily pause this work,
** and resume it after the action is complete.
**
** TODO could model worker movement times in this class (e.g. 2s delay before
** starting an action, and 2s delay before resuming work).
*/
public class SC2Worker extends SC2Asset {

	/** current activity */
	protected Work work = Work.IDLE;

	/** current command structure, if {@code act} is {@code gath_m | gath_v}. */
	protected SC2Command command;

	public SC2Worker(SC2Player play, SC2AssetType type) {
		super(play, type);
	}

	public void setIdle() {
		if (command != null) { command.remGatherer(this); }
		this.command = null;
		this.work = Work.IDLE;
	}

	public void setGatherM(SC2Command command) {
		setIdle();
		command.addGathererM(this);
		this.command = command;
		this.work = Work.GATH_M;
	}

	public void setGatherV(SC2Command command) {
		setIdle();
		command.addGathererV(this);
		this.command = command;
		this.work = Work.GATH_V;
	}

	/** Whether work is currently paused on an action. ({@code IDLE} still
	 * counts as work for these purposes.) */
	public boolean workPaused() {
		return active != null;
	}

	@Override protected SC2Action popAction() {
		switch (work) {
		case GATH_M: command.addGathererM(this); break;
		case GATH_V: command.addGathererV(this); break;
		}
		return super.popAction();
	}

	@Override public boolean bind(SC2Action action) throws SC2ActionException, SC2RequireException {
		boolean nq = super.bind(action);
		switch (work) {
		case GATH_M:
		case GATH_V:
			command.remGatherer(this);
		}
		return nq;
	}

	public enum Work { IDLE, GATH_M, GATH_V; }

	/** mean movement time to switch between tasks. currently not used. */
	final public static double MEAN_MOVE_TIME = 2.0;

}
