package sc2;

import java.util.EnumSet;
import java.util.Set;
import static sc2.SC2State.Race;

/**
** Represents an asset type.
*/
public class SC2AssetType {

	public enum Group { UNIT, STRUCT, TECH }
	public enum Modifier { STRUCTURE, ARMORED, LIGHT, BIOLOGICAL,
		// TODO
		/** dummy modifier for techs */ TECH }

	final public Race race;
	final public int cost_m;
	final public int cost_v;
	final public int cost_t;
	/** supply cost. units have >=0 supply, tech/structs have null supply */
	final public Integer cost_f;

	/** built from */
	final public SC2AssetType source;
	/** morphs from */
	final public SC2AssetType parent;
	/**
	** Requirements. eg. cyber core for stalker. Used by {@link SC2BuildAction}
	** and {@link SC2UpgradeAction}. Protoss mothership has a separate build
	** action and this field isn't necessary.
	*/
	final public Set<SC2AssetType> reqs;

	final public int stat_hp;
	final public int stat_sp;
	final public int stat_ep;

	final public double speed;
	final public int range;
	final public int sight;
	/** cargo size */
	final public int size;

	final public SC2Attack atk_g;
	final public SC2Attack atk_a;
	final public int armour;
	final public EnumSet<Modifier> mods;

	public Group type() {
		return (cost_f != null)? Group.UNIT: (mods.contains(Modifier.STRUCTURE))? Group.STRUCT: Group.TECH;
	}

	/** constructor for unit types */
	public static SC2AssetType defineUnit(
		Race race, int cost_m, int cost_v, int cost_t, int cost_f,
		SC2AssetType source, SC2AssetType parent, Set<SC2AssetType> reqs,
		int stat_hp, int stat_sp, int stat_ep,
		double speed, int range, int sight, int size,
		SC2Attack atk_g, SC2Attack atk_a, int armour, Modifier mod_0, Modifier ... mods
	) {
		return new SC2AssetType(race, cost_m, cost_v, cost_t, cost_f,
		  source, parent, reqs, stat_hp, stat_sp, stat_ep,
		  speed, range, sight, size, atk_g, atk_a, armour, mod_0, mods);
	}

	/** constructor for structure types */
	public static SC2AssetType defineStructure(
		Race race, int cost_m, int cost_v, int cost_t,
		SC2AssetType source, Set<SC2AssetType> reqs,
		int stat_hp, int stat_sp, int stat_ep,
		double speed, int range, int sight, int size,
		SC2Attack atk_g, SC2Attack atk_a, int armour, Modifier ... mods
	) {
		return new SC2AssetType(race, cost_m, cost_v, cost_t, null,
		  source, null, reqs, stat_hp, stat_sp, stat_ep,
		  speed, range, sight, size, atk_g, atk_a, armour, Modifier.STRUCTURE, mods);
	}

	/** constructor for structure types that don't move, don't attack, and aren't add-ons */
	public static SC2AssetType defineStructure(
		Race race, int cost_m, int cost_v, int cost_t,
		Set<SC2AssetType> reqs,
		int stat_hp, int stat_sp, int stat_ep,
		int range, int sight, int size,
		int armour, Modifier ... mods
	) {
		return new SC2AssetType(race, cost_m, cost_v, cost_t, null,
		  null, null, reqs, stat_hp, stat_sp, stat_ep,
		  0.0, range, sight, size, null, null, armour, Modifier.STRUCTURE, mods);
	}

	/** constructor for tech types */
	public static SC2AssetType defineTech(
		Race race, int cost_m, int cost_v, int cost_t,
		SC2AssetType source, Set<SC2AssetType> reqs
	) {
		return new SC2AssetType(race, cost_m, cost_v, cost_t, null,
		  source, null, reqs, 0, 0, 0,
		  0.0, 0, 0, 0, null, null, 0, Modifier.TECH);
	}

	/** custom constructor */
	protected SC2AssetType(
		Race race, int cost_m, int cost_v, int cost_t, Integer cost_f,
		SC2AssetType source, SC2AssetType parent, Set<SC2AssetType> reqs,
		int stat_hp, int stat_sp, int stat_ep,
		double speed, int range, int sight, int size,
		SC2Attack atk_g, SC2Attack atk_a, int armour, Modifier mod_0, Modifier ... mods
	) {
		this.race = race;
		this.cost_m = cost_m;
		this.cost_v = cost_v;
		this.cost_t = cost_t;
		this.cost_f = cost_f;
		this.source = source;
		this.parent = parent;
		this.reqs = reqs;
		this.stat_hp = stat_hp;
		this.stat_sp = stat_sp;
		this.stat_ep = stat_ep;
		this.speed = speed;
		this.range = range;
		this.sight = sight;
		this.size = size;
		this.atk_g = atk_g;
		this.atk_a = atk_a;
		this.armour = armour;
		this.mods = EnumSet.of(mod_0, mods);
	}

}
