package sc2.asset;

import sc2.SC2Player;

/**
** Represents a zerg command structure. Produces larva.
*/
public class SC2ZergCommand extends SC2Command {

	/** time until spontaneous larva generation */
	protected int spawn_larva_eta;
	/** time until queen larva generation */
	protected int queen_larva_eta;

	public SC2ZergCommand(SC2Player play, SC2AssetType type) {
		super(play, type);
		// TODO
	}

	// TODO

}
