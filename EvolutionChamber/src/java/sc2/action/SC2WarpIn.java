package sc2.action;

import sc2.asset.SC2AssetType;

/**
** Represents a warp-in task on a Warp Gate.
*/
public class SC2WarpIn extends SC2AssetAction implements SC2Ability {

	public SC2WarpIn(SC2AssetType type) {
		super(type, SC2AssetAction.Action.WARPIN);
	}

	@Override protected void launch() throws SC2ActionException {
		// TODO
	}

	@Override public int getCooldown() {
		// TODO
		return -1;
	}

}
