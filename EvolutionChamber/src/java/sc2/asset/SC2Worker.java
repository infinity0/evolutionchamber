package sc2.asset;

import sc2.action.SC2Action;
import sc2.SC2Player;

/**
** Represents a worker.
*/
public class SC2Worker extends SC2Asset {

	/** current activity */
	protected Activity act = Activity.IDLE;

	/** current command structure, if {@code act} is {@code gath_m | gath_v}. */
	protected SC2Command command;

	public SC2Worker(SC2Player play, SC2AssetType type) {
		super(play, type);
	}

	public void setIdle() {
		if (act == Activity.IDLE) { return; }
		if (active != null) {
			// TODO support this. an action needs to do this on cancel()
			throw new UnsupportedOperationException("cannot idle a worker currently on activity " + act);
		}
		assert command != null;
		command.remGatherer(this);
		this.act = Activity.IDLE;
	}

	public void setGatherM(SC2Command command) {
		setIdle();
		this.command = command;
		command.addGathererM(this);
		this.act = Activity.GATH_M;
	}

	public void setGatherV(SC2Command command) {
		setIdle();
		this.command = command;
		command.addGathererV(this);
		this.act = Activity.GATH_V;
	}

	public enum Activity { IDLE, GATH_M, GATH_V, MORPH, CSTR_P, CSTR_T; }

}
