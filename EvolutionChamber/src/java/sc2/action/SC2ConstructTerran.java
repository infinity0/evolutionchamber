package sc2.action;

import sc2.asset.SC2AssetType;

/**
** Represents the construction of a Terran structure. The worker remains busy
** until the end of the process.
*/
public class SC2ConstructTerran extends SC2AssetAction {

	public SC2ConstructTerran(SC2AssetType type) {
		super(type);
	}

	@Override protected void launch() throws SC2ActionException {
		// TODO
	}

}
