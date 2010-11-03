package sc2.require;

import sc2.asset.SC2AssetType;

/**
** Thrown when a required asset is not found.
**
** Currently also used for missing tech, since I cba writing another exception.
*/
public class SC2AssetException extends SC2RequireException {

	final public SC2AssetType asset_type;

	public SC2AssetException(SC2AssetType asset_type, boolean less, int req, int num, boolean later) {
		super("AssetException: needed " +
		  (less? "<=": ">=") + req + " " + asset_type.name +
		  " but had " + num, later);
		this.asset_type = asset_type;
	}

}
