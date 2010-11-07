package sc2.require;

import sc2.annot.Immutable;

import sc2.SC2Player;
import sc2.asset.SC2AssetType;

/**
** Represents a requirement that an asset is available with the given number.
*/
@Immutable
public class SC2RequiresAsset implements SC2Requires {

	final public SC2AssetType type;
	final public int req;
	/** whether we want a number < {@link #req}, or else >. */
	final public boolean less;

	public SC2RequiresAsset(SC2AssetType type, int req, boolean less) {
		this.type = type;
		this.req = req;
		this.less = less;
	}

	/** Create a requirement that at least one of the asset type is available */
	public SC2RequiresAsset(SC2AssetType type) {
		this(type, 0, false);
	}

	@Override public void require(SC2Player play) throws SC2RequireException {
		int num = play.getAssets(type).size();
		if (less) {
			if (num > req) {
				throw new SC2AssetException(this, num, false);
			}
		} else {
			if (num < req) {
				// TODO check the queues for future satisfiability
				throw new SC2AssetException(this, num, false);
			}
		}
	}

	@Override public String toString() {
		return "[ " + (less? "<": ">") + " " + req + " " + type.name + " ]";
	}

}
