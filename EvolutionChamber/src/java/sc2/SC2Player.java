package sc2;

import sc2.action.SC2Action;
import sc2.action.SC2ActionException;
import sc2.asset.SC2Asset;
import sc2.asset.SC2AssetType;
import sc2.asset.SC2Base;
import sc2.asset.SC2Worker;
import static sc2.SC2World.Race;

import com.google.common.collect.SetMultimap;
import com.google.common.collect.LinkedHashMultimap;

import java.util.Iterator;
import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Formatter;

/**
** Represents a game state, from the point of view of one player.
**
** Currently this is mutable; it makes heavy use of SC2Action which is mutable.
*/
public class SC2Player {

	/** database of game statistics */
	final public SC2World world;
	/** player's race */
	final public Race race;
	/** player's name */
	final public String name;

	/** current assets, stored by type */
	final protected SetMultimap<SC2AssetType, SC2Asset> assets = LinkedHashMultimap.create();
	/** completed research */
	final protected Set<SC2AssetType> research = new HashSet<SC2AssetType>();
	/** ongoing actions that don't belong to asset queues */
	final protected List<SC2Action> ongoing = new ArrayList<SC2Action>();

	/** mineral resources. representing as a double allows for better averaging. */
	public double res_m;
	/** vespene resources. representing as a double allows for better averaging. */
	public double res_v;
	/** food resources. representing as a double due to zergling 0.5 supply. */
	public double res_f;
	/** food capacity. */
	public int max_f;
	/** game ticks, currently measured in seconds */
	protected int time;

	public SC2Player(SC2World world, Race race, String name) {
		if (world == null || race == null) { throw new NullPointerException(); }
		this.world = world;
		this.race = race;
		this.name = name;
	}

	public SC2Player(SC2World world, Race race) {
		this(world, race, "Anonymous");
	}

	public String timestamp() {
		int h = time / 60, m = time % 60;
		return ((h<10)?"0":"") + h + ((m<10)?":0":":") + m;
	}

	public String getDesc() {
		return new Formatter().format(
		  "SC2Player %s [%s] @ %s @ %4.0fm %4.0fv %3.0f/%3df\nasset: %s\n tech: %s\n",
		  name, race, timestamp(), res_m, res_v, res_f, max_f,
		  assets.values(), research).toString();
	}

	/**
	** @param base_workers Number of workers at each base.
	*/
	public void initAssets(int[] base_workers, double res_m, double res_v) {
		this.res_m = res_m;
		this.res_v = res_v;

		SC2AssetType type_b = world.macro.get(race).command();
		SC2AssetType type_w = world.macro.get(race).worker();

		for (int i=0; i<base_workers.length; ++i) {
			SC2Base curr_base = new SC2Base(this, type_b);
			addAsset(curr_base);
			this.max_f += type_b.prov_f;

			for (int j=0; j<base_workers[i]; ++j) {
				SC2Worker curr_worker = new SC2Worker(this, type_w);
				addAsset(curr_worker);
				this.res_f += type_w.cost_f;
				curr_worker.setGatherM(curr_base);
			}
		}
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
		asset.setName("#" + assets.get(asset.type).size());
		assets.put(asset.type, asset);
	}

	public Set<SC2Asset> getAssets(SC2AssetType type) {
		return assets.get(type);
	}

	public boolean hasTech(SC2AssetType type) {
		return research.contains(type);
	}

	//public Set<SC2Asset> getWorkers();
	//public Set<SC2Asset> getUnits();
	//public Set<SC2Asset> getStructures();
	//public Set<SC2Asset> getTechs();

}
