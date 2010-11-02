package sc2;

import sc2.action.SC2Action;

import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;

/**
** Represents a game state.
**
** Currently this is mutable; it makes heavy use of SC2Action which is mutable.
*/
public class SC2State {

	public enum Race { P, Z, T }

	final public Race race;

	final protected Set<SC2Asset> assets = new HashSet<SC2Asset>();

	final protected List<SC2Action> queue = new ArrayList<SC2Action>();

	public SC2State(Race race) {
		this.race = race;
	}

	public void advance() {
		for (SC2Action action: queue) { action.advance(); }
		for (SC2Asset asset: assets) { asset.advance(); }
		// TODO
	}

	//public Set<SC2Asset> getWorkers();
	//public Set<SC2Asset> getUnits();
	//public Set<SC2Asset> getStructures();
	//public Set<SC2Asset> getTechs();

}
