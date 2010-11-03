package sc2.asset;

import sc2.action.SC2Action;
import sc2.action.SC2ActionException;
import sc2.SC2Player;

/**
** Represents a structure. Can be chrono boosted.
*/
public class SC2Structure extends SC2Asset {

	protected int chrono_eta;

	public SC2Structure(SC2Player play, SC2AssetType type) {
		super(play, type);
		// TODO
	}

	@Override public void advance() {
		if (chrono_eta > 0) {
			advance(1.5);
			chrono_eta--;
		} else {
			advance();
		}
	}

	/**
	** Should only be called by {@link sc2.action.SC2ChronoBoost}.
	*/
	public void boost() {
		chrono_eta = 20;
	}

}
