package sc2.action;

import sc2.asset.SC2AssetType;

/**
** Morph a supply depot to an augmented supply depot. This lets us avoid
** having a separate asset subclass for supply depots.
**
** TODO might be better to subclass a future SC2Ability
*/
public class SC2CalldownSupplies extends SC2Morph {

	public SC2CalldownSupplies(SC2AssetType type) {
		super(type);
	}

	@Override protected void launch() {
		// TODO
	}

}
