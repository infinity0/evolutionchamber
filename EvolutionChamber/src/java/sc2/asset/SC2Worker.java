package sc2.asset;

import sc2.action.SC2Action;
import sc2.SC2Player;

/**
** Represents a worker.
*/
public class SC2Worker extends SC2Asset {

	/** current action */
	protected SC2Action action;

	public SC2Worker(SC2Player play, SC2AssetType type) {
		super(play, type);
	}

}
