package sc2.action;

import sc2.asset.SC2AssetType;

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

	public SC2Build(SC2AssetType type) {
		super(type, SC2AssetAction.Action.BUILD);
	}

	@Override protected void launch() throws SC2ActionException {
		// TODO
	}

}
