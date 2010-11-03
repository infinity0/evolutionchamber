package sc2.asset;

import sc2.action.SC2Action;
import sc2.action.SC2ActionException;
import sc2.SC2Player;

/**
** Represents a zerg base. Produces larva.
*/
public class SC2ZergBase extends SC2Base {

	/** time until spontaneous larva generation */
	protected int spawn_larva_eta;
	/** time until queen larva generation */
	protected int queen_larva_eta;

	public SC2ZergBase(SC2Player play, SC2AssetType type) {
		super(play, type);
		// TODO
	}

	// TODO

}
