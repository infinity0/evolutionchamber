package sc2;

import sc2.action.SC2Action;
import sc2.action.SC2ActionException;

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

	final public SC2StatDB db;

	final public Race race;

	final protected Set<SC2Asset> assets = new HashSet<SC2Asset>();

	final protected Set<SC2AssetType> research = new HashSet<SC2AssetType>();

	final protected List<SC2Action> ongoing = new ArrayList<SC2Action>();

	public SC2State(SC2StatDB db, Race race) {
		if (db == null || race == null) { throw new NullPointerException(); }
		this.db = db;
		this.race = race;
	}

	public SC2State(Race race) {
		this(SC2StatDB.getDefault(), race);
	}

	public void advance() {
		for (SC2Action action: ongoing) { action.advance(); }
		for (SC2Asset asset: assets) { asset.advance(); }
		// TODO
	}

	public void addAction(SC2Action action) throws SC2ActionException {
		// TODO
		action.init();
	}

	public void addAsset(SC2Asset asset) {
		// TODO
	}

	//public Set<SC2Asset> getWorkers();
	//public Set<SC2Asset> getUnits();
	//public Set<SC2Asset> getStructures();
	//public Set<SC2Asset> getTechs();

}
