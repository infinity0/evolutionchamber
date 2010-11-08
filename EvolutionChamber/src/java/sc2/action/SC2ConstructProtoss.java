package sc2.action;

import sc2.asset.SC2Asset;
import sc2.asset.SC2AssetType;

/**
** Represents the construction of a Protoss structure. The worker is freed
** after starting the process.
*/
public class SC2ConstructProtoss extends SC2AssetAction {

	public SC2ConstructProtoss(SC2AssetType type) {
		super(type, SC2AssetAction.Action.CSTR_P);
	}

	/**
	** {@inheritDoc}
	**
	** This implementation also drops this action from the source immediately
	** (allowing the probe to get back to work) and binds it to the game state
	** instead.
	*/
	@Override public void evt_init(SC2Asset source) throws SC2ActionException {
		super.evt_init(source);
		source.drop(this);
		this.source = null;
		play.addAction(this);
	}

}
