package sc2;

import sc2.action.SC2Action;

/**
** Represents a completed asset.
**
** Can subclass to implement eg.
** - SC2BuildingAsset (with chronoboost)
** - SC2TerranProductionBuildingAsset (with a second queue for reactor)
*/
public class SC2Asset {

	final public SC2AssetType type;

	final protected SC2Action[] queue = new SC2Action[5];

	public SC2Asset(SC2AssetType type) {
		this.type = type;
	}

	public void advance() {
		// TODO
	}

}
