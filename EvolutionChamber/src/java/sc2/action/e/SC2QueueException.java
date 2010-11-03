package sc2.action.e;

import sc2.action.SC2ActionException;
import sc2.action.SC2Action;
import sc2.asset.SC2Asset;

/**
** Thrown when an asset's queue can't accept the action.
*/
public class SC2QueueException extends SC2ActionException {

	final public SC2Asset asset;
	/** queue will eventual open up, ie. action is potentially valid for the asset */
	final public boolean can_wait;

	public SC2QueueException(SC2Action action, SC2Asset asset, boolean can_wait) {
		super(action, "No available queue slots: " + asset);
		this.asset = asset;
		this.can_wait = can_wait;
	}

}
