package sc2.asset;

import sc2.action.SC2Action;
import sc2.SC2Player;

/**
** Represents a worker.
*/
public class SC2Worker extends SC2Asset {

	/** current activity */
	protected Activity act;

	/** current base, if {@code act} is {@code gath_m | gath_v}. */
	protected SC2Base base;

	/** current action, if {@code act} is {@code morph | cstr_p | cstr_t}. */
	protected SC2Action action;

	public SC2Worker(SC2Player play, SC2AssetType type) {
		super(play, type);
		this.act = Activity.IDLE;
	}

	public void setIdle() {
		if (act == Activity.IDLE) { return; }
		if (action != null) {
			// TODO support this. an action needs to do this on cancel()
			throw new UnsupportedOperationException("cannot idle a worker currently on activity " + act);
		}
		assert base != null;
		base.remGatherer(this);
		this.act = Activity.IDLE;
	}

	public void setGatherM(SC2Base base) {
		setIdle();
		this.base = base;
		base.addGathererM(this);
		this.act = Activity.GATH_M;
	}

	public void setGatherV(SC2Base base) {
		setIdle();
		this.base = base;
		base.addGathererV(this);
		this.act = Activity.GATH_V;
	}

	public enum Activity { IDLE, GATH_M, GATH_V, MORPH, CSTR_P, CSTR_T; }

}
