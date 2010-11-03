package sc2.action.e;

import sc2.action.SC2ActionException;
import sc2.action.SC2Action;
import sc2.asset.SC2AssetType;

/**
** Thrown when there is not enough larva.
*/
public class SC2LarvaException extends SC2AssetException {

	/** larva will eventually become available by waiting.
	** ie. currently spawning, or at least 1 hatchery have < 3 */
	final public boolean can_wait;

	public SC2LarvaException(SC2Action action, SC2AssetType larva_type, boolean can_wait) {
		super(action, larva_type);
		this.can_wait = can_wait;
	}

}
