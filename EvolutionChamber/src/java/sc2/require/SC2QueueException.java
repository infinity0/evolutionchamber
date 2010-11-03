package sc2.require;

import sc2.asset.SC2Asset;

/**
** Thrown when an asset's queue can't accept the action.
*/
public class SC2QueueException extends SC2RequireException {

	final public SC2Asset asset;

	public SC2QueueException(SC2Asset asset, boolean later) {
		super("No available queue slots: " + asset, later);
		this.asset = asset;
	}

}
