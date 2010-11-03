package sc2.asset;

import sc2.SC2HealthSchema;
import sc2.SC2EnergySchema;
import sc2.SC2Attack;
import static sc2.SC2State.Race;

import java.util.EnumSet;
import java.util.Set;
import java.util.Collections;
import java.util.Arrays;
import java.util.HashSet;

/**
** Represents an asset type.
*/
public class SC2AssetType {

	public enum Group { UNIT, STRUCT, TECH }
	public enum Modifier { STRUCTURE, ARMORED, LIGHT, BIOLOGICAL,
		// TODO
		/** dummy modifier for techs */ TECH }

	final public String name;
	final public Race race;
	final public int cost_m;
	final public int cost_v;
	final public int cost_t;

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

	final public SC2HealthSchema stat_hp;
	final public SC2HealthSchema stat_sp;
	final public SC2EnergySchema stat_ep;

	final public double speed;
	final public int range;
	final public int sight;

	final public Set<Modifier> mods;

	final public SC2Attack atk_g;
	final public SC2Attack atk_a;

	/** provided food, will be 0 for most things */
	final public int prov_f;

	/** supply cost. units have >=0 supply, tech/structs have null supply */
	final public Integer cost_f;
	/** cargo size */
	final public int size;
	/** cargo capacity */
	final public int cargo;

	public Group type() {
		return (cost_f != null)? Group.UNIT: (mods.contains(Modifier.STRUCTURE))? Group.STRUCT: Group.TECH;
	}

	public int supply() {
		return cost_f == null? 0: (int)cost_f;
	}

	public static Builder initAssetType(String name, Race race, int cost_m, int cost_v, int cost_t) {
		return new Builder(name, race, cost_m, cost_v, cost_t);
	}

	/** custom constructor */
	protected SC2AssetType(
		String name, Race race, int cost_m, int cost_v, int cost_t,
		SC2AssetType source, SC2AssetType parent, Set<SC2AssetType> reqs,
		SC2HealthSchema stat_hp, SC2HealthSchema stat_sp, SC2EnergySchema stat_ep,
		double speed, int range, int sight,
		EnumSet<Modifier> mods, SC2Attack atk_g, SC2Attack atk_a,
		int prov_f, Integer cost_f, int size, int cargo
	) {
		this.name = non_null("name", name);
		this.race = non_null("race", race);
		this.cost_m = cost_m;
		this.cost_v = cost_v;
		this.cost_t = cost_t;

		this.source = source;
		this.parent = parent;
		this.reqs = (reqs == null)? Collections.<SC2AssetType>emptySet(): Collections.<SC2AssetType>unmodifiableSet(reqs);

		this.stat_hp = stat_hp;
		this.stat_sp = stat_sp;
		this.stat_ep = (stat_ep == null)? SC2EnergySchema.NONE: stat_ep;

		this.speed = speed;
		this.range = range;
		this.sight = sight;

		this.mods = (mods == null)? Collections.<Modifier>emptySet(): Collections.<Modifier>unmodifiableSet(mods);
		this.atk_g = atk_g;
		this.atk_a = atk_a;

		this.prov_f = prov_f;
		this.cost_f = cost_f;
		this.size = size;
		this.cargo = cargo;
	}

	private static <T> T non_null(String desc, T o) {
		if (o == null) { throw new IllegalArgumentException(desc + " must not be null"); }
		return o;
	}

	public static class Builder {

		final String name;
		final Race race;
		final int cost_m;
		final int cost_v;
		final int cost_t;

		SC2AssetType source;
		SC2AssetType parent;
		Set<SC2AssetType> reqs;

		SC2HealthSchema stat_hp;
		SC2HealthSchema stat_sp;
		SC2EnergySchema stat_ep;

		double speed;
		int range;
		int sight;

		EnumSet<Modifier> mods;

		SC2Attack atk_g;
		SC2Attack atk_a;

		int prov_f;

		public Builder(String name, Race race, int cost_m, int cost_v, int cost_t) {
			this.name = name;
			this.race = race;
			this.cost_m = cost_m;
			this.cost_v = cost_v;
			this.cost_t = cost_t;
		}

		public Builder predecents(SC2AssetType source, SC2AssetType parent, SC2AssetType ... reqs) {
			this.source = source;
			this.parent = parent;
			this.reqs = new HashSet<SC2AssetType>(Arrays.asList(reqs));
			return this;
		}

		public Builder defence(SC2HealthSchema stat_hp, SC2HealthSchema stat_sp, SC2EnergySchema stat_ep) {
			this.stat_hp = stat_hp;
			this.stat_sp = stat_sp;
			this.stat_ep = stat_ep;
			return this;
		}

		public Builder physical(double speed, int range, int sight) {
			this.speed = speed;
			this.range = range;
			this.sight = sight;
			return this;
		}

		public Builder modifier(Modifier mod_0, Modifier ... mods) {
			this.mods = EnumSet.of(mod_0, mods);
			return this;
		}

		public Builder attacks(SC2Attack atk_g, SC2Attack atk_a) {
			this.atk_g = atk_g;
			this.atk_a = atk_a;
			return this;
		}

		/** provide some supply */
		public Builder provide(int prov_f) {
			this.prov_f = prov_f;
			return this;
		}

		public SC2AssetType buildStructure() {
			mods.add(Modifier.STRUCTURE);
			return new SC2AssetType(name, race, cost_m, cost_v, cost_t,
			  source, parent, reqs,
			  stat_hp, stat_sp, stat_ep, speed, range, sight,
			  mods, atk_g, atk_a, prov_f, null, 0, 0);
		}

		public SC2AssetType buildUnit(int cost_f, int size, int cargo) {
			return new SC2AssetType(name, race, cost_m, cost_v, cost_t,
			  source, parent, reqs,
			  stat_hp, stat_sp, stat_ep, speed, range, sight,
			  mods, atk_g, atk_a, prov_f, cost_f, size, cargo);
		}

		public SC2AssetType buildTech() {
			return new SC2AssetType(name, race, cost_m, cost_v, cost_t,
			  source, parent, reqs,
			  stat_hp, stat_sp, stat_ep, speed, range, sight,
			  EnumSet.of(Modifier.TECH), atk_g, atk_a, prov_f, null, 0, 0);
		}

	}

}
