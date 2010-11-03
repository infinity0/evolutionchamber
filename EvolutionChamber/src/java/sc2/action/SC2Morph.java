package sc2.action;

import sc2.asset.SC2AssetType;

/**
** Represents a 1:1 morph action. Examples:
**
** - Viking fm <-> Viking am
** - Gateway   <-> Warp Gate
** - Lair -> Hive
** - Command Center -> Orbital Command
** - Zergling -> Baneling
*/
public class SC2Morph extends SC2AssetAction {

	public SC2Morph(SC2AssetType type) {
		super(type);
	}

	@Override protected void launch() throws SC2ActionException {
		// TODO
	}

}
