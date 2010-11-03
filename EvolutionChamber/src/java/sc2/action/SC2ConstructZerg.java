package sc2.action;

import sc2.asset.SC2AssetType;

/**
** Represents the construction of a Zerg structure. The worker is morphed into
** the structure.
*/
public class SC2ConstructZerg extends SC2Morph {

	public SC2ConstructZerg(SC2AssetType type) {
		super(type);
	}

	@Override protected void launch() throws SC2ActionException {
		// TODO
	}

}
