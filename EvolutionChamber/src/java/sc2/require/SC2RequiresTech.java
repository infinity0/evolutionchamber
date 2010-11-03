package sc2.require;

import sc2.SC2Player;
import sc2.asset.SC2AssetType;

/**
** Represents a requirement that a technology is available.
*/
public class SC2RequiresTech implements SC2Requires {

	final protected SC2AssetType type;

	public SC2RequiresTech(SC2AssetType type) {
		this.type = type;
	}

	@Override public void require(SC2Player play) throws SC2RequireException {
		if (!play.hasTech(type)) {
			// cba writing another exception class
			throw new SC2AssetException(type, false, 1, 0, false);
		}
	}

}
