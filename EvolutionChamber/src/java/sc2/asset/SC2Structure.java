package sc2.asset;

import sc2.action.SC2Action;
import sc2.action.SC2ActionException;
import sc2.SC2State;

/**
** Represents a structure. Can be chrono boosted.
*/
public class SC2Structure extends SC2Asset {

	protected int boost_eta;

	public SC2Structure(SC2State game, SC2AssetType type) {
		super(game, type);
		// TODO
	}

	@Override public void advance() {
		if (boost_eta > 0) {
			advance(1.5);
			boost_eta--;
		} else {
			advance();
		}
	}

	/**
	** Should only be called by {@link sc2.action.SC2ChronoBoost}.
	*/
	public void boost() {
		boost_eta = 20;
	}

}
