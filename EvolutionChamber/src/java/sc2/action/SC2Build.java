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

	@Override protected void launch() throws SC2ActionException {
		try {
			checkRequirements();
		} catch (SC2RequireException e) {
			throw new SC2ActionException(this, "build requirements not satisfied", e);
		}

		try {
			SC2Asset source = getSourceAsset();
			source.pushBuild(this);
		} catch (SC2AssetException e) {
			throw new SC2ActionException(this, "could not find suitable source asset", e);
		} catch (SC2RequireException e) {
			throw new SC2ActionException(this, "general execution error", e);
		}
	}

	@Override public void init() throws SC2ActionException {
		try {
			deductResources();
		} catch (SC2CostException e) {
			throw new SC2ActionException(this, "not enough resources", e);
		}
	}

	@Override public boolean advance(double rate) {
		if (!begun) {
			boolean checks = true;
			// TODO check supply the first time this is advanced
			if (!checks) { return false; }
			begun = true;
		}
		return super.advance(rate);
	}

	@Override public void complete() {
		// TODO add an asset
	}

}
