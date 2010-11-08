package sc2.action;

import sc2.asset.SC2Asset;
import sc2.asset.SC2AssetType;
import sc2.require.SC2Requires;
import sc2.require.SC2AssetException;
import sc2.require.SC2CostException;
import sc2.require.SC2RequireException;
import static sc2.action.SC2AssetAction.Action.BUILD;

/**
** Represents a build that uses a queue slot on an {@link sc2.asset.SC2Asset}.
** This does *not* include structure construction, which are done by workers.
**
** Examples:
** - Hive builds Queen
** - Stargate builds Carrier
** - Carrier builds interceptor
*/
public class SC2Build extends SC2AssetAction {

	/**
	** Whether the build has begun. This matches the game mechanics of supply
	** checking, which blocks a build action on first run, rather than on
	** initialisation (i.e. when added to the asset queue).
	*/
	protected boolean begun = false;

	public SC2Build(SC2AssetType type) {
		super(type, BUILD);
	}

	/**
	** {@inheritDoc}
	**
	** This implementation checks the supply cost, and
	*/
	@Override public boolean advance(double rate) {
		if (!begun) {
			float cost_f = type.cost_f();
			// check supply the first time this is advanced
			if (play.res_f + cost_f > play.max_f) { return false; }
			play.res_f += cost_f;
			begun = true;
		}
		return super.advance(rate);
	}

	public boolean begun() {
		return begun;
	}

}
