package sc2.action;

import sc2.asset.SC2AssetType;
import sc2.require.SC2Requires;
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

	public SC2Build(SC2AssetType type) {
		super(type, BUILD);
	}

	@Override protected void launch() throws SC2ActionException {
		SC2AssetActionSchema schema = type.getSchema(BUILD);
		System.out.println("trying to build " + type + " " + schema);

		// TODO maybe much of this can be moved into SC2AssetAction
		// check requirements
		try {
			for (SC2Requires req: schema.req()) {
				req.require(play);
			}
		} catch (SC2RequireException e) {
			throw new SC2ActionException(this, "build requirement not satisfied", e);
		}

		// TODO find an appropriate source building
		//SC2Asset source = null;
		//source.pushBuild(this);
	}

}
