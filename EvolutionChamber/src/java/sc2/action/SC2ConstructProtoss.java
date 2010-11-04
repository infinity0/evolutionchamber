package sc2.action;

import sc2.asset.SC2AssetType;

/**
** Represents the construction of a Protoss structure. The worker is freed
** after starting the process.
*/
public class SC2ConstructProtoss extends SC2AssetAction {

	public SC2ConstructProtoss(SC2AssetType type) {
		super(type, SC2AssetAction.Action.CSTR_P);
	}

	@Override protected void launch() throws SC2ActionException {
		// TODO
	}

}
