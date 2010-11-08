package sc2.action;

import sc2.asset.SC2Asset;
import sc2.asset.SC2AssetType;

/**
** Represents the construction of a Protoss structure. The worker is freed
** after starting the process.
*/
public class SC2ConstructProtoss extends SC2AssetAction {

	protected boolean begun = false;

	public SC2ConstructProtoss(SC2AssetType type) {
		super(type, SC2AssetAction.Action.CSTR_P);
	}

	/**
	** {@inheritDoc}
	**
	** This implementation drops this action from the source at the first
	** advancement (allowing the probe to get back to work) and binds it to the
	** game state instead.
	*/
	@Override public boolean advance(double rate) {
		if (!begun) {
			source.drop(this);
			this.source = null;
			play.addAction(this);
			begun = true;
		}
		return super.advance(rate);
	}

}
