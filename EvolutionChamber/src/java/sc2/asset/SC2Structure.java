package sc2.asset;

import sc2.action.SC2Action;
import sc2.action.SC2ActionException;

/**
** Can subclass to implement eg.
** - SC2TerranProductionStructure (with a second queue for reactor)
**
** // TODO
*/
public class SC2Structure extends SC2Asset {

	protected int boost_eta;

	public SC2Structure(SC2AssetType type) {
		super(type);
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
	** Should only be called by {@link SC2ChronoBoostAction}.
	*/
	public void boost() {
		boost_eta = 20;
	}

}
