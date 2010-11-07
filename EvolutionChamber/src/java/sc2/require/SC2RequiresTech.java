package sc2.require;

import sc2.annot.Immutable;

import sc2.SC2Player;
import sc2.asset.SC2AssetType;

/**
** Represents a requirement that a technology is available.
*/
@Immutable
public class SC2RequiresTech implements SC2Requires {

	final public SC2AssetType type;

	public SC2RequiresTech(SC2AssetType type) {
		this.type = type;
	}

	@Override public void require(SC2Player play) throws SC2RequireException {
		if (!play.hasTech(type)) {
			// TODO check the queues for future satisfiability
			throw new SC2AssetException(this, false);
		}
	}

}
