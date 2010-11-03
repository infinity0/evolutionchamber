package sc2.action;

import sc2.asset.SC2AssetType;
import static sc2.asset.SC2AssetType.non_null;

/**
** Represents a task on some sort of {@link sc2.asset.SC2Asset}.
*/
abstract public class SC2AssetAction extends SC2Action {

	final protected SC2AssetType type;

	public SC2AssetAction(SC2AssetType type) {
		super(non_null("asset type", type).cost_t);
		this.type = type;
	}

}
