package sc2.action;

import sc2.SC2Asset;

/**
** Thrown when an asset's queue can't accept the action.
*/
public class SC2QueueException extends SC2ActionException {

	final public SC2Asset asset;

	public SC2QueueException(SC2Action action, SC2Asset asset) {
		super(action, "Unaccepting queue: " + asset);
		this.asset = asset;
	}

}
