package sc2;

import sc2.action.SC2Action;
import sc2.action.SC2ActionException;

import com.google.common.collect.Multimap;
import com.google.common.collect.HashMultimap;

import java.util.Iterator;
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

	final protected Multimap<SC2AssetType, SC2Asset> assets = HashMultimap.create();

	final protected Set<SC2AssetType> research = new HashSet<SC2AssetType>();

	final protected List<SC2Action> ongoing = new ArrayList<SC2Action>();

	/** mineral resources. representing as a double allows for better averaging. */
	protected double res_m;
	/** vespene resources. representing as a double allows for better averaging. */
	protected double res_v;
	/** game ticks, currently measured in seconds */
	protected int time;

	public SC2State(SC2StatDB db, Race race) {
		if (db == null || race == null) { throw new NullPointerException(); }
		this.db = db;
		this.race = race;
		this.res_m = 50;
		this.res_v = 0;
		// TODO populate assets with 6 workers, 1 cc
	}

	public SC2State(Race race) {
		this(SC2StatDB.getDefault(), race);
	}

	/**
	** Return supply used.
	*/
	public int res_f() {
		int f = act_f();
		// TODO add units currently being produced. need to implement
		// SC2BuildAction properly first
		return f;
	}

	/**
	** Return supply active, ie. in completed units, not on production queues.
	*/
	public int act_f() {
		int f = 0;
		for (SC2Asset asset: assets.values()) { f += asset.type.cost_f; }
		return f;
	}

	/**
	** Return supply available.
	*/
	public int cap_f() {
		int f = 0;
		for (SC2Asset asset: assets.values()) { f += asset.type.prov_f; }
		return f;
	}

	/**
	** Advance by 1 game second.
	*/
	public void advance() {
		// advance asset queues. these will automatically drop completed actions
		for (SC2Asset asset: assets.values()) { asset.advance(); }

		// advance ongoing actions, dropping actions if appropriate
		Iterator<SC2Action> it = ongoing.iterator();
		while (it.hasNext()) {
			SC2Action action = it.next();
			if (action.advance()) { it.remove(); }
		}

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
