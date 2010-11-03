package sc2.action;

import sc2.asset.SC2AssetType;

/**
** Thrown when a required asset is not found.
*/
public class SC2AssetException extends SC2ActionException {

	final public SC2AssetType asset_type;

	public SC2AssetException(SC2Action action, SC2AssetType asset_type) {
		super(action, "Unsatisfied asset: " + asset_type.name);
		this.asset_type = asset_type;
	}

}
