package sc2.action;

import sc2.asset.SC2Asset;
import sc2.asset.SC2AssetType;
import sc2.asset.SC2Structure;
import sc2.action.SC2ActionException;
import sc2.require.SC2CostException;

/**
** Represents a chrono boost action on a {@link sc2.asset.SC2Structure}.
**
** TODO needs a wait(time) action for this to work effectively. or some other
** representation like wait(event) where event might be
**
** - building X just finished
**
** (luckily, "building Y just started" can be done by executing Chrono Boost
** immediately after the "build Y" action.)
**
** another feature is to raise an exception if the building is still under
** chrono boost (helps optimisation)
**
** NEEDS MORE THOUGHT
*/
public class SC2ChronoBoost extends SC2Action {

	final public SC2Structure source;
	final public SC2Structure target;

	public SC2ChronoBoost(SC2Asset source, SC2Asset target) {
		this.source = (SC2Structure)source;
		this.target = (SC2Structure)target;
	}

	@Override protected void launch() throws SC2ActionException {
		try {
			target.spendEnergy(25);
			target.boost();
		} catch (SC2CostException e) {
			throw new SC2ActionException(this, e.getMessage(), e);
		}
	}

}
