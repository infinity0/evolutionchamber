package sc2.require;

import sc2.asset.SC2Asset;
import sc2.asset.SC2AssetType;

import java.util.Arrays;

/**
** Thrown when a required asset is not found.
**
** Currently also used for missing tech, since I cba writing another exception.
**
** TODO make this class less of a hack.
*/
public class SC2AssetException extends SC2RequireException {

	final public SC2AssetType type;

	public SC2AssetException(SC2RequiresAsset req, int num, boolean later) {
		super("need " + req + " but had " + num, later);
		this.type = req.type;
	}

	public SC2AssetException(SC2RequiresTech req, boolean later) {
		super("unavailable tech " + req.type.name, later);
		this.type = req.type;
	}

	public SC2AssetException(SC2AssetType[] types, boolean later) {
		super("could not find any of " + Arrays.toString(types), later);
		this.type = null;
	}

	public SC2AssetException(SC2Asset[] assets, boolean later) {
		super("none of candidate assets suitable: " + Arrays.toString(assets), later);
		this.type = null;
	}

}
